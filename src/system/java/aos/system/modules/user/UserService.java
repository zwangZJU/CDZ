package aos.system.modules.user;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import aos.framework.core.id.AOSId;
import aos.framework.core.service.CDZBaseController;
import aos.framework.core.typewrap.Dto;
import aos.framework.core.typewrap.Dtos;
import aos.framework.core.utils.AOSCodec;
import aos.framework.core.utils.AOSJson;
import aos.framework.core.utils.AOSUtils;
import aos.framework.dao.AosUserDao;
import aos.framework.dao.AosUserPO;
import aos.framework.web.router.HttpModel;
import aos.system.common.model.UserModel;
import aos.system.common.utils.SystemCons;
import aos.system.common.utils.SystemUtils;
import aos.system.dao.AosOrgDao;
import aos.system.dao.AosOrgPO;
import aos.system.dao.AosRolePO;
import aos.system.dao.AosUserRoleDao;
import aos.system.dao.AosUserRolePO;
import aos.system.modules.cache.CacheUserDataService;

import com.google.common.collect.Lists;

/**
 * 用户管理
 * 
 * @author xiongchun
 *
 */
@Service
public class UserService extends CDZBaseController {
	private static Logger logger = LoggerFactory.getLogger(UserService.class);
	@Autowired
	private AosUserDao aosUserDao;
	@Autowired
	private AosOrgDao aosOrgDao;
	@Autowired
	private AosUserRoleDao aosUserRoleDao;
	@Autowired
	private CacheUserDataService cacheUserDataService;
	
	/**
	 * 用户管理页面初始化
	 * 
	 * @param httpModel
	 * @return
	 */
	public void init(HttpModel httpModel) {
		//AosOrgPO aosOrgPO = aosOrgDao.selectByKey(httpModel.getUserModel().getAosOrgPO().getId_());
		//httpModel.setAttribute("rootPO", aosOrgPO);
		httpModel.setAttribute("super_user_id", SystemCons.SUPER_USER_ID);
		httpModel.setViewPath("system/user.jsp");
	}
	
	public void sales_init(HttpModel httpModel) {
		httpModel.setViewPath("system/sales.jsp");
	}

	/**
	 * 获取部门导航树
	 * 
	 * @param httpModel
	 * @return
	 */
	public void getTreeData(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		inDto.setOrder("sort_no_");
		inDto.put("is_del_", SystemCons.IS.NO);
		List<AosOrgPO> aosOrgPOs = aosOrgDao.list(inDto);
		List<Dto> modelDtos = Lists.newArrayList();
		for (AosOrgPO aosOrgPO : aosOrgPOs) {
			modelDtos.add(aosOrgPO.toDto());
		}
		String treeJson = SystemUtils.toTreeModalAsyncLoad(modelDtos);
		httpModel.setOutMsg(treeJson);
	}

	/**
	 * 查询用户列表
	 * 
	 * @param httpModel
	 * @return
	 */
	public void listUsers(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		AosOrgPO aosOrgPO = httpModel.getUserModel().getAosOrgPO();
		/*if (AOSUtils.isEmpty(qDto.getString("org_id_"))) {
			qDto.put("org_id_", aosOrgPO.getId_());
		}
		if (AOSUtils.isEmpty(qDto.getString("org_cascade_id_"))) {
			qDto.put("org_cascade_id_", aosOrgPO.getCascade_id_());
		}*/
		qDto.put("is_del_", SystemCons.IS.NO);
		qDto.put("type_", "1");
		//qDto.put("cascade_", qDto.getBoolean("cascade_") ? 1 : 0);
		List<Dto> userDtos = sqlDao.list("User.listUsersPage", qDto);
		for (Dto dto : userDtos) {
			String roles_ = "";
			List<AosRolePO> aosRolePOs = sqlDao.list("User.listRolesOfUser", dto.getString("id_"));
			for (AosRolePO aosRolePO : aosRolePOs) {
				roles_ = roles_ + aosRolePO.getName_() + " | ";
			}
			dto.put("roles_", StringUtils.substringBeforeLast(roles_, "|"));
		}
		httpModel.setOutMsg(AOSJson.toGridJson(userDtos, qDto.getPageTotal()));
	}
	public void listSalesUsers(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		AosOrgPO aosOrgPO = httpModel.getUserModel().getAosOrgPO();
		qDto.put("is_del_", SystemCons.IS.NO);
		qDto.put("type_", "2");
		List<Dto> userDtos = sqlDao.list("User.listUsersPage", qDto);
		httpModel.setOutMsg(AOSJson.toGridJson(userDtos, qDto.getPageTotal()));
	}
	/**
	 * 保存用户
	 * 
	 * @param httpModel
	 * @return
	 */
	@Transactional
	public void saveUser(HttpModel httpModel) {
		Dto outDto = Dtos.newOutDto();
		Dto inDto = httpModel.getInDto();
		UserModel userModel = httpModel.getUserModel();
		AosUserPO aosUserPO = new AosUserPO();
		aosUserPO.copyProperties(inDto);
		Dto checkDto = Dtos.newDto("account_", aosUserPO.getAccount_());
		String password_b_=inDto.getString("password_b_");
		String password_=inDto.getString("password_");
		checkDto.put("is_del_", SystemCons.IS.NO);
		if(!password_.equals(password_b_)){
			outDto.setAppCode(SystemCons.ERROR);
			outDto.setAppMsg("两次密码输入不一致，请重新输入。");
		}else if (aosUserDao.rows(checkDto) == 0) {
			aosUserPO.setId_(AOSId.appId(SystemCons.ID.SYSTEM));
			aosUserPO.setCreate_by_(userModel.getId_());
			aosUserPO.setCreate_time_(AOSUtils.getDateTime());
			aosUserPO.setIs_del_(SystemCons.IS.NO);
			aosUserPO.setType_("1");
			aosUserPO.setPassword_(AOSCodec.password(aosUserPO.getPassword_()));
			aosUserDao.insert(aosUserPO);
			outDto.setAppMsg("管理员添加成功。");
		} else {
			outDto.setAppCode(SystemCons.ERROR);
			outDto.setAppMsg(AOSUtils.merge("添加管理员失败，管理员账号[{0}]已经存在。", aosUserPO.getAccount_()));
		}
		httpModel.setOutMsg(AOSJson.toJson(outDto));
	}

	
	/**
	 * 修改用户
	 * 
	 * @param httpModel
	 * @return
	 */
	public void updateUser(HttpModel httpModel) {
		Dto outDto = Dtos.newOutDto();
		Dto inDto = httpModel.getInDto();
		AosUserPO aosUserPO = new AosUserPO();
		aosUserPO.copyProperties(inDto);
		AosUserPO oldPO = aosUserDao.selectByKey(aosUserPO.getId_());
		String password_b_=inDto.getString("password_b_");
		String password_=inDto.getString("password_");
		if(!password_.equals(password_b_)){
			outDto.setAppCode(SystemCons.ERROR);
			outDto.setAppMsg("两次密码输入不一致，请重新输入。");
			httpModel.setOutMsg(AOSJson.toJson(outDto));
			return ;
		}
		if (!StringUtils.equalsIgnoreCase(aosUserPO.getAccount_(), oldPO.getAccount_())) {
			Dto checkDto = Dtos.newDto("account_", aosUserPO.getAccount_());
			checkDto.put("is_del_", SystemCons.IS.NO);
			if (aosUserDao.rows(checkDto) > 0) {
				outDto.setAppCode(SystemCons.ERROR);
				outDto.setAppMsg(AOSUtils.merge("修改失败，管理员账号[{0}]已经存在。", aosUserPO.getAccount_()));
			} 
		}
		if (StringUtils.equals(outDto.getAppCode(), SystemCons.SUCCESS)) {
			aosUserPO.setPassword_(AOSCodec.password(password_b_));
			aosUserDao.updateByKey(aosUserPO);
			outDto.setAppMsg("管理员信息修改成功。");
		}
		httpModel.setOutMsg(AOSJson.toJson(outDto));
	}
	/**
	 * 修改用户
	 * 
	 * @param httpModel
	 * @return
	 */
	public void updateSalesUser(HttpModel httpModel) {
		Dto outDto = Dtos.newOutDto();
		Dto inDto = httpModel.getInDto();
		AosUserPO aosUserPO = new AosUserPO();
		aosUserPO.copyProperties(inDto);
		AosUserPO oldPO = aosUserDao.selectByKey(aosUserPO.getId_());
		String password_b_=inDto.getString("password_b_");
		String password_=inDto.getString("password_");
		if(!password_.equals(password_b_)){
			outDto.setAppCode(SystemCons.ERROR);
			outDto.setAppMsg("两次密码输入不一致，请重新输入。");
			httpModel.setOutMsg(AOSJson.toJson(outDto));
			return ;
		}
		if (!StringUtils.equalsIgnoreCase(aosUserPO.getAccount_(), oldPO.getAccount_())) {
			Dto checkDto = Dtos.newDto("account_", aosUserPO.getAccount_());
			checkDto.put("is_del_", SystemCons.IS.NO);
			if (aosUserDao.rows(checkDto) > 0) {
				outDto.setAppCode(SystemCons.ERROR);
				outDto.setAppMsg(AOSUtils.merge("修改失败，销售员账号[{0}]已经存在。", aosUserPO.getAccount_()));
			} 
		}
		if (StringUtils.equals(outDto.getAppCode(), SystemCons.SUCCESS)) {
			aosUserPO.setPassword_(AOSCodec.password(password_b_));
			aosUserDao.updateByKey(aosUserPO);
			outDto.setAppMsg("销售员信息修改成功。");
		}
		httpModel.setOutMsg(AOSJson.toJson(outDto));
	}
	/**
	 * 查询用户信息
	 * 
	 * @param httpModel
	 * @return
	 */
	public void getUser(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		AosUserPO aosUserPO = aosUserDao.selectByKey(inDto.getString("id_"));
		aosUserPO.setPassword_(null);
		Dto outDto = aosUserPO.toDto();
		httpModel.setOutMsg(AOSJson.toJson(outDto));
	}

	/**
	 * 删除用户
	 * 
	 * @param httpModel
	 */
	@Transactional
	public void deleteUser(HttpModel httpModel) {
		String[] selectionIds = httpModel.getInDto().getRows();
		Dto outDto = Dtos.newOutDto();
		if(selectionIds.length>0){
			for (String id_ : selectionIds) {
				// 自己不能删除自己
				if (StringUtils.equals(httpModel.getUserModel().getId_().toString(), id_)) {
					continue;
				}
				AosUserPO aosUserPO = new AosUserPO();
				aosUserPO.setId_(id_);
				aosUserPO.setIs_del_(SystemCons.IS.YES);
				aosUserDao.updateByKey(aosUserPO);
			}
		}else{
			String id_=httpModel.getInDto().getString("id_"); 
			// 自己不能删除自己
			if (StringUtils.equals(httpModel.getUserModel().getId_().toString(), id_)) {
				outDto.setAppCode(SystemCons.ERROR);
				outDto.setAppMsg("自己不能删除自己。");
				httpModel.setOutMsg(AOSJson.toJson(outDto));
				return ;
			}
			AosUserPO aosUserPO = new AosUserPO();
			aosUserPO.setId_(id_);
			aosUserPO.setIs_del_(SystemCons.IS.YES);
			aosUserDao.updateByKey(aosUserPO);
		}
		httpModel.setOutMsg("删除管理员成功。");
	}
	
	
	/**
	 * 重置用户密码
	 * 
	 * @param httpModel
	 */
	@Transactional
	public void resetPassword(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		String[] selectionIds = inDto.getRows();
		for (String id_ : selectionIds) {
			AosUserPO aosUserPO = new AosUserPO();
			aosUserPO.setId_(id_);
			aosUserPO.setPassword_(AOSCodec.password(inDto.getString("password_")));
			aosUserDao.updateByKey(aosUserPO);
		}
		httpModel.setOutMsg("用户密码重置成功。");
	}
	
	/**
	 * 查询待选角色列表
	 * 
	 * @param httpModel
	 * @return
	 */
	public void listRoles(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		List<Dto> roleDtos = sqlDao.list("User.listToSelectRoles", qDto);
		
		httpModel.setOutMsg(AOSJson.toGridJson(roleDtos, roleDtos.size()));
	}
	
	/**
	 * 查询已选角色列表
	 * 
	 * @param httpModel
	 * @return
	 */
	public void listSelectedRoles(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		
		List<Dto> roleDtos = sqlDao.list("User.listSelectedRoles", qDto);
		
		httpModel.setOutMsg(AOSJson.toGridJson(roleDtos, roleDtos.size()));
	}
	
	/**
	 * 保存用户角色关联关系
	 * 
	 * @param httpModel
	 */
	@Transactional
	public void saveUserRoleGrantInfo(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		String user_id_ = inDto.getString("user_id_");
		String[] selectionIds = httpModel.getInDto().getRows();
		for (String id_ : selectionIds) {
			AosUserRolePO aosUserRolePO = new AosUserRolePO();
			aosUserRolePO.setId_(AOSId.appId(SystemCons.ID.SYSTEM));
			aosUserRolePO.setRole_id_(id_);
			aosUserRolePO.setUser_id_(user_id_);
			aosUserRolePO.setCreate_by_(httpModel.getUserModel().getId_());
			aosUserRolePO.setCreate_time_(AOSUtils.getDateTime());
			aosUserRoleDao.insert(aosUserRolePO);
		}
		cacheUserDataService.resetGrantInfoOfUser(user_id_);
		httpModel.setOutMsg("用户角色关联关系保存成功。");
	}
	
	/**
	 * 撤消用户角色关联关系
	 * 
	 * @param httpModel
	 */
	@Transactional
	public void delUserRoleGrantInfo(HttpModel httpModel) {
		String[] selectionIds = httpModel.getInDto().getRows();
		for (String id_ : selectionIds) {
			AosUserRolePO aosUserRolePO = aosUserRoleDao.selectByKey(id_);
			aosUserRoleDao.deleteByKey(id_);
			cacheUserDataService.resetGrantInfoOfUser(aosUserRolePO.getUser_id_());
		}
		httpModel.setOutMsg("撤消用户角色关联关系成功。");
	}

}
