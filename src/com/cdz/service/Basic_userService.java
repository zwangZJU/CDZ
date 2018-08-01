package service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

/*
import aos.framework.core.id.AOSId;
import aos.framework.core.service.CDZBaseController;
import aos.framework.web.router.HttpModel;
import aos.system.common.utils.SystemCons;
import cn.core.test.dao.mapper.Basic_userMapper;
import cn.core.test.dao.po.Basic_userPO;
import cn.core.test.modules.service.Basic_userService;
import cn.osworks.aos.core.asset.AOSJson;
import cn.osworks.aos.core.asset.AOSUtils;
import cn.osworks.aos.core.asset.WebCxt;
import cn.osworks.aos.core.dao.SqlDao;
import cn.osworks.aos.core.excel.POIExcelUtil;
import cn.osworks.aos.core.typewrap.Dto;
import cn.osworks.aos.core.typewrap.Dtos;
import cn.osworks.aos.core.typewrap.impl.HashDto;
import dao.Basic_userDao;
import po.Basic_userPO;
*/

import aos.framework.core.id.AOSId;
import aos.framework.core.utils.AOSUtils;
import aos.framework.core.service.CDZBaseController;
import aos.framework.core.typewrap.Dto;
import aos.framework.core.utils.AOSJson;
import aos.framework.web.router.HttpModel;
import aos.system.common.utils.SystemCons;
import dao.Basic_userDao;
import dao.Basic_userDao;
import po.Basic_userPO;
import po.Basic_userPO;

/**
 * <b>用户信息[basic_user] controller</b>
 * <p>
 * </p>
 * 
 * @author koney
 * @date 2018-07-10 20:06:01
 */
@Service
public class Basic_userService extends CDZBaseController {

	@Autowired
	private Basic_userDao basic_userDao;

	/**
	 * charging_pile管理页面初始化
	 * 
	 * @param httpModel
	 * @return
	 */
	public void init(HttpModel httpModel) {
		httpModel.setViewPath("myproject/basic_user.jsp");
	}

	/**
	 * 查询charging_pile列表
	 * 
	 * @param httpModel
	 * @return
	 */
	public void listBasic_user(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		List<Dto> basic_userDtos = sqlDao.list("Basic_user.listBasic_usersPage", qDto);
		httpModel.setOutMsg(AOSJson.toGridJson(basic_userDtos, qDto.getPageTotal()));
	}
	/**
	 * 查询charging_pile信息
	 * 
	 * @param httpModel
	 */
	public void getBasic_user(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
	     Basic_userPO basic_userPO = basic_userDao.selectByKey(inDto.getString("cp_id")); 
		httpModel.setOutMsg(AOSJson.toJson(basic_userPO));
	}

	/**
	 * 保存charging_pile
	 * 
	 * @param httpModel
	 * @return
	 */
	 @Transactional
	public void saveBasic_user(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		Basic_userPO basic_userPO = new Basic_userPO();
		basic_userPO.copyProperties(inDto);
		basic_userPO.setId_(AOSId.appId(SystemCons.ID.SYSTEM));
		basic_userPO.setCreate_at(AOSUtils.getDateTime());
		basic_userDao.insert(basic_userPO);
		httpModel.setOutMsg("新增成功。");
	}

	/**
	 * 修改charging_pile
	 * 
	 * @param httpModel
	 * @return
	 */
	 @Transactional
	public void updateBasic_user(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		Basic_userPO basic_userPO = new Basic_userPO();
		basic_userPO.copyProperties(inDto);
		basic_userDao.updateByKey(basic_userPO);
		httpModel.setOutMsg("修改成功。");
	}

	/**
	 * 删除charging_pile
	 * 
	 * @param httpModel
	 */
	@Transactional
	public void deleteBasic_user(HttpModel httpModel) {
		String[] selectionIds = httpModel.getInDto().getRows();
		if(null!=selectionIds&&selectionIds.length>0){
			for (String id_ : selectionIds) {
				/*Basic_userPO basic_userPO = new Basic_userPO();
				basic_userPO.setCp_id(id_);
				basic_userPO.setIs_del(SystemCons.IS.YES);
	            basic_userDao.updateByKey(basic_userPO);*/ 
	            basic_userDao.deleteByKey(id_);
			}
		}else{
				String cp_id=httpModel.getInDto().getString("cp_id");
				/*Basic_userPO basic_userPO = new Basic_userPO();
				basic_userPO.setCp_id(cp_id);
				basic_userPO.setIs_del(SystemCons.IS.YES);
	            basic_userDao.updateByKey(basic_userPO); */
			     basic_userDao.deleteByKey(cp_id);
			
		}
		httpModel.setOutMsg("删除成功。");
	}

	

}
