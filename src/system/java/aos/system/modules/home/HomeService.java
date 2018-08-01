package aos.system.modules.home;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import aos.framework.core.cache.CacheMasterDataService;
import aos.framework.core.dao.asset.DBDialectUtils;
import aos.framework.core.id.AOSId;
import aos.framework.core.redis.JedisUtil;
import aos.framework.core.service.CDZBaseController;
import aos.framework.core.typewrap.Dto;
import aos.framework.core.typewrap.Dtos;
import aos.framework.core.utils.AOSCodec;
import aos.framework.core.utils.AOSCons;
import aos.framework.core.utils.AOSCxt;
import aos.framework.core.utils.AOSJson;
import aos.framework.core.utils.AOSUtils;
import aos.framework.dao.AosUserDao;
import aos.framework.dao.AosUserPO;
import aos.framework.web.router.HttpModel;
import aos.system.common.model.UserModel;
import aos.system.common.utils.ErrorCode;
import aos.system.common.utils.SystemCons;
import aos.system.common.utils.SystemUtils;
import aos.system.modules.cache.CacheUserDataService;

/**
 * 首页服务实现
 * 
 * @author xiongchun
 *
 */
@Service
public class HomeService extends CDZBaseController {

	private static Logger logger = LoggerFactory.getLogger(HomeService.class);

	@Autowired
	private CacheUserDataService cacheUserDataService;
	@Autowired
	private AosUserDao aosUserDao;
	@Autowired
	private CacheMasterDataService cacheMasterDataService;
	
	
	

	/**
	 * 注册页面初始化
	 * 
	 * @param httpModel
	 * @return
	 */
	public void initLogin(HttpModel httpModel) {

		httpModel.setAttribute("app_title_", AOSCxt.getParam("app_title_"));
		httpModel.setAttribute("left_logo_", AOSCxt.getParam("left_logo_"));
		httpModel.setAttribute("vercode_characters_", AOSCxt.getParam("vercode_characters_"));
		httpModel.setAttribute("vercode_length_", AOSCxt.getParam("vercode_length_"));
		httpModel.setAttribute("is_show_vercode_", AOSCxt.getParam("is_show_vercode_"));
		httpModel.setAttribute("login_wait_msg_", AOSCxt.getParam("login_wait_msg_"));
		httpModel.setAttribute("north_back_img_", AOSCxt.getParam("skin_") + ".png");
		httpModel.setAttribute("login_dev_", AOSCxt.getParam("login_dev_"));
		httpModel.setAttribute("login_dev_account_", AOSCxt.getParam("login_dev_account_"));
		String login_back_img_ = AOSCxt.getParam("login_back_img_");
		if (StringUtils.equals(login_back_img_, "-1")) {
			int backImgIndex = RandomUtils.nextInt(3); // [0,3)
			login_back_img_ = backImgIndex + ".jpg";
		}
		httpModel.setAttribute("login_back_img_", login_back_img_);
		int row_space_ = 25, padding_ = 50;
		if (StringUtils.equals(AOSCxt.getParam("is_show_vercode_"), SystemCons.IS.YES)) {
			row_space_ = 15;
			padding_ = 30;
		}
		httpModel.setAttribute("row_space_", row_space_);
		httpModel.setAttribute("padding_", padding_);
		// 用来标识登录页面校验验证码用的
		httpModel.setAttribute("vercode_uuid_", AOSId.uuid());
		httpModel.setViewPath("login.jsp");
	}

	/**
	 * 首页页面初始化
	 * 
	 * @param httpModel
	 * @return
	 */
	public void initIndex(HttpModel httpModel) {
		UserModel userModel = httpModel.getUserModel();
		// 构造卡片菜单
		httpModel.setAttribute("cardDtos", getCardListFromCache(userModel.getId_()));

		httpModel.setAttribute("north_back_img_", userModel.getSkin_() + ".png");
		httpModel.setAttribute("south_back_color_", AOSCxt.getDicDesc("south_back_color_", userModel.getSkin_()));
		httpModel.setAttribute("copyright_", AOSCxt.getParam("copyright_"));
		httpModel.setAttribute("app_title_", AOSCxt.getParam("app_title_"));
		httpModel.setAttribute("welcome_page_title_", AOSCxt.getParam("welcome_page_title_"));
		httpModel.setAttribute("user_name_", userModel.getName_());
		//httpModel.setAttribute("org_name_", userModel.getAosOrgPO().getName_());
		httpModel.setAttribute("welcome_msg_", getWelcomeMsg());
		httpModel.setAttribute("date_", AOSUtils.getDateStr());
		httpModel.setAttribute("week_", AOSUtils.getWeekDayByDate(AOSUtils.getDateStr()));
		httpModel.setAttribute("main_text_color_", AOSCxt.getDicDesc("main_text_color_", userModel.getSkin_()));
		httpModel.setAttribute("nav_text_color_", AOSCxt.getDicDesc("nav_text_color_", userModel.getSkin_()));
		httpModel.setAttribute("page_load_msg_", AOSCxt.getParam("page_load_msg_"));
		httpModel.setAttribute("run_mode_", AOSCxt.getParam("run_mode_"));
		httpModel.setAttribute("page_load_gif_", AOSCxt.getParam("page_load_gif_"));
		httpModel.setAttribute("navDto", initNavBarStyle(userModel.getSkin_()));
		//httpModel.setAttribute("qq_group_link_", AOSCxt.getParam("qq_group_link_"));
		
		httpModel.setAttribute("juid", httpModel.getInDto().getString("juid"));
		httpModel.setAttribute("type_", httpModel.getUserModel().getType_());

		if("2".equals(httpModel.getUserModel().getType_())){
			
		}
		
		httpModel.setViewPath("index.jsp");
	}

	/**
	 * Portal页面初始化
	 * 
	 * @param httpModel
	 * @return
	 */
	public void initPortal(HttpModel httpModel) {
		UserModel userModel = httpModel.getUserModel();
		httpModel.setAttribute("curSkin", userModel.getSkin_());
		httpModel.setAttribute("run_mode_", AOSCxt.getParam("run_mode_"));
		httpModel.setAttribute("day", AOSUtils.getDateStr());
		 Calendar cal = Calendar.getInstance();
        //cal.setTime();
        int d = 0;
        if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
            d = -6;
        } else {
            d = 2 - cal.get(Calendar.DAY_OF_WEEK);
        }
        cal.add(Calendar.DAY_OF_WEEK, d);
        String data1 = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        cal.add(Calendar.DAY_OF_WEEK, 6);
        String data2 = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        httpModel.setAttribute("zhou_start", data1);
        httpModel.setAttribute("zhou_end", data2);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
        Calendar c = Calendar.getInstance();    
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
        String first = format.format(c.getTime());
        //获取当前月最后一天
        Calendar ca = Calendar.getInstance();    
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));  
        String last = format.format(ca.getTime());
        httpModel.setAttribute("month_start", first);
        httpModel.setAttribute("month_end", last);
        List<Dto>  list=sqlDao.list("Members.membersSummary", Dtos.newDto());
        if(null!=list&&list.size()>0){
        	Dto dto=list.get(0);
        	 httpModel.setAttribute("hy_total", dto.getString("count"));
        	 httpModel.setAttribute("hy_bjj", dto.getString("bj"));
        	 httpModel.setAttribute("hy_jj", dto.getString("jp"));
        	 httpModel.setAttribute("hy_yj", dto.getString("yp"));
        	 httpModel.setAttribute("hy_pt", dto.getString("pt"));
        }
       list=sqlDao.list("ChargingPile.chargingPileSummary", Dtos.newDto());
        if(null!=list&&list.size()>0){
        	Dto dto=list.get(0);
        	 httpModel.setAttribute("cp_total", dto.getString("count"));
        	 httpModel.setAttribute("keyong", dto.getString("keyong"));
        	 httpModel.setAttribute("zhanyong", dto.getString("zhanyong"));
        	 httpModel.setAttribute("guzhang", dto.getString("guzhang"));
        }
         Dto pDto= Dtos.newDto();
         pDto.put("date_start", AOSUtils.getDateStr());
         pDto.put("date_end", AOSUtils.getDateStr());
        list=sqlDao.list("ChargingOrders.chargingOrdersSummary",pDto);
        if(null!=list&&list.size()>0){
        	Dto dto=list.get(0);
        	 httpModel.setAttribute("d_o_total", dto.getString("count"));
        	 httpModel.setAttribute("d_o_yfk", dto.getString("pay"));
        	 httpModel.setAttribute("d_o_dfk", dto.getString("nopay"));
        }
        //周
        pDto.put("date_start", data1);
        pDto.put("date_end", data2);
       list=sqlDao.list("ChargingOrders.chargingOrdersSummary",pDto);
       if(null!=list&&list.size()>0){
       	Dto dto=list.get(0);
       	 httpModel.setAttribute("z_o_total", dto.getString("count"));
       	 httpModel.setAttribute("z_o_yfk", dto.getString("pay"));
       	 httpModel.setAttribute("z_o_dfk", dto.getString("nopay"));
       }
       //月
       pDto.put("date_start", first);
       pDto.put("date_end", last);
      list=sqlDao.list("ChargingOrders.chargingOrdersSummary",pDto);
      if(null!=list&&list.size()>0){
      	Dto dto=list.get(0);
      	 httpModel.setAttribute("m_o_total", dto.getString("count"));
      	 httpModel.setAttribute("m_o_yfk", dto.getString("pay"));
      	 httpModel.setAttribute("m_o_dfk", dto.getString("nopay"));
      }
     list=sqlDao.list("ChargingOrders.chargingOrdersSummary",Dtos.newDto());
     if(null!=list&&list.size()>0){
     	Dto dto=list.get(0);
     	 httpModel.setAttribute("t_o_total", dto.getString("count"));
     	 httpModel.setAttribute("t_o_yfk", dto.getString("pay"));
     	 httpModel.setAttribute("t_o_dfk", dto.getString("nopay"));
     }
		httpModel.setViewPath("system/portal.jsp");
	}

	/**
	 * 用户登录
	 * 
	 * @param httpModel
	 * @return
	 */
	@Transactional
	public void login(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		Dto outDto = Dtos.newDto();
		outDto.setAppCode(AOSCons.SUCCESS);
		String is_show_vercode_ = AOSCxt.getParam("is_show_vercode_");
		// 配置为无验证码机制则跳过验证
		if (StringUtils.equals(is_show_vercode_, SystemCons.IS.YES)) {
			String cached_vercode_ = JedisUtil.getString(inDto.getString("vercode_uuid_"));
			if (!StringUtils.equalsIgnoreCase(cached_vercode_, inDto.getString("vercode_"))) {
				outDto.setAppCode(ErrorCode.VERCODE_ERROR);
				outDto.setAppMsg("验证码不对，请重新输入。");
				httpModel.setOutMsg(AOSJson.toJson(outDto));
				return;
			}
		}

		// 帐号存在校验
		Dto qDto = Dtos.newDto("account_", inDto.getString("account_"));
		qDto.put("is_del_", SystemCons.IS.NO);
		AosUserPO aosUserPO = aosUserDao.selectOne(qDto);
		Boolean is_pass = true;
		if (AOSUtils.isEmpty(aosUserPO)) {
			outDto.setAppCode(ErrorCode.ACCOUNT_ERROR);
			outDto.setAppMsg("帐号输入错误或已被删除，请重新输入。");
			is_pass = false;
		} else {
			// 密码校验
			String password = AOSCodec.password(inDto.getString("password_"));
			if (!StringUtils.equals(password, aosUserPO.getPassword_())) {
				outDto.setAppCode(ErrorCode.PASSWORD_ERROR);
				outDto.setAppMsg("密码输入错误，请重新输入。");
				is_pass = false;
			} else {
				// 状态校验
				if (!aosUserPO.getStatus_().equals(SystemCons.USER_STATUS.NORMAL)) {
					outDto.setAppCode(ErrorCode.LOCKED_ERROR);
					outDto.setAppMsg("该帐号已禁用，请联系管理员。");
					is_pass = false;
				}
			}
		}
		if(null!=aosUserPO&&!"1".equals(aosUserPO.getType_())){
			outDto.setAppCode(ErrorCode.LOCKED_ERROR);
			outDto.setAppMsg("当前账号不存在，请联系管理员");
			is_pass = false;
		}
		
		if (!is_pass) {
			httpModel.setOutMsg(AOSJson.toJson(outDto));
			return;
		}

		// 通过检查
		outDto.setAppCode(AOSCons.SUCCESS);
		String juid = cacheUserDataService.login(aosUserPO, httpModel.getRequest());
		outDto.put("juid", juid);
		
		AosUserPO aosUserPO_ = new AosUserPO();
		aosUserPO_.setId_(aosUserPO.getId_());
		aosUserPO_.setLogin_time_(AOSUtils.getDateTime());
		aosUserDao.updateByKey(aosUserPO_);
		
		httpModel.setOutMsg(AOSJson.toJson(outDto));

	}

	public void getSMSCode(HttpModel httpModel){
		
		Dto inDto = httpModel.getInDto();
		Dto outDto = Dtos.newDto();
		String account_=inDto.getString("account_");
		if(AOSUtils.isEmpty(account_)){
			outDto.setAppCode(SystemCons.ERROR);
			outDto.setAppMsg("账号不能为空");
			httpModel.setOutMsg(AOSJson.toJson(outDto));
			return;
		}
		Dto pDto=Dtos.newDto("account_", account_);
		AosUserPO aosUserPO_ =aosUserDao.selectOne(pDto);
		if(null==aosUserPO_){
			outDto.setAppCode(SystemCons.ERROR);
			outDto.setAppMsg("账号不存在，请重新输入");
			httpModel.setOutMsg(AOSJson.toJson(outDto));
			return;
		}else if(!"2".equals(aosUserPO_.getType_())){
			outDto.setAppCode(SystemCons.ERROR);
			outDto.setAppMsg("当前帐号为非专家用户，请重新输入");
			httpModel.setOutMsg(AOSJson.toJson(outDto));
			return;
		}
		String smsCode=AOSUtils.createRandomVcode();
		String sessionId=httpModel.getRequest().getSession().getId();
		cacheMasterDataService.cacheSMSCode(sessionId, smsCode);
		System.out.println("smsCode-------------->>"+smsCode+",sessionId---------------->>"+sessionId);
		outDto.setAppCode(SystemCons.SUCCESS);
		outDto.setAppMsg("获取验证码成功");
		httpModel.setOutMsg(AOSJson.toJson(outDto));
	}
	
	
	/**
	 * 开发用户快捷登录
	 * 
	 * @param httpModel
	 * @return
	 */
	public void loginDev(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		Dto outDto = Dtos.newOutDto();
		AosUserPO aosUserPO = aosUserDao.selectOne(Dtos.newDto("account_", inDto.getString("account_")));
		outDto.setAppCode(AOSCons.SUCCESS);
		String juid = cacheUserDataService.login(aosUserPO, httpModel.getRequest());
		outDto.put("juid", juid);
		httpModel.setOutMsg(AOSJson.toJson(outDto));
	}

	/**
	 * 用户注销
	 * 
	 * @param httpModel
	 * @return
	 */
	public void logout(HttpModel httpModel) {
		String juid = httpModel.getInDto().getString("juid");
		if (AOSUtils.isNotEmpty(juid)) {
			logger.info(AOSUtils.merge("用户[{0}]成功注销。", httpModel.getUserModel().getName_()));
			cacheUserDataService.logout(juid);
		}
	}

	/**
	 * 获取用户卡片菜单
	 * 
	 * @param inDto
	 * @return
	 */
	private List<Dto> getCardListFromCache(String user_id_) {
		final String cacheKey = SystemCons.KEYS.CARDLIST + user_id_;
		List<Dto> cardList = null;
		String cardListJson = JedisUtil.getString(cacheKey);
		if (AOSUtils.isNotEmpty(cardListJson)) {
			cardList = AOSJson.fromJson(cardListJson);
		} else {
			Dto qDto = Dtos.newDto();
			qDto.put("user_id_", user_id_);
			qDto.put("grant_type_", SystemCons.GRANT_TYPE_.BIZ);
			// 取CASCADE_ID_长度为5的菜单出来作为卡片
			qDto.put("length", '5');
			qDto.put("fnLength", DBDialectUtils.fnLength(sqlDao.getDatabaseId()));
			cardList = sqlDao.list("Home.selectModulesOfUser", qDto);
			if (AOSUtils.isNotEmpty(cardList)) {
				JedisUtil.setString(cacheKey, AOSJson.toJson(cardList), 0);
			}
		}
		return cardList;
	}

	/**
	 * 获取卡片导航菜单树
	 * 
	 * @param inDto
	 * @return
	 */
	public void getCardTree(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		String user_id_ = httpModel.getUserModel().getId_();
		String cascade_id_ = inDto.getString("cascade_id_");
		String cardTree = null;
		// 每个用户会有多个卡片树
		final String cacheKey = SystemCons.KEYS.CARD_TREE + user_id_ + "." + cascade_id_;
		List<Dto> moduleList = null;
		cardTree = JedisUtil.getString(cacheKey);
		if (AOSUtils.isEmpty(cardTree)) {
			Dto qDto = Dtos.newDto();
			qDto.put("user_id_", user_id_);
			qDto.put("grant_type_", SystemCons.GRANT_TYPE_.BIZ);
			qDto.put("cascade_id_", cascade_id_);
			moduleList = sqlDao.list("Home.selectModulesOfUser", qDto);
			if (AOSUtils.isNotEmpty(moduleList)) {
				cardTree = SystemUtils.toTreeModalAllInOne(moduleList);
				JedisUtil.setString(cacheKey, cardTree, 0);
			}
		}
		httpModel.setOutMsg(cardTree);
	}

	/**
	 * 获取首选项页面的用户初始信息
	 * 
	 * @param inDto
	 * @return
	 */
	public void getUser(HttpModel httpModel) {
		AosUserPO aosUserPO = aosUserDao.selectByKey(httpModel.getUserModel().getId_());
		aosUserPO.setPassword_(StringUtils.EMPTY);
		Dto outDto = aosUserPO.toDto();
		outDto.put("org_name_", httpModel.getUserModel().getAosOrgPO().getName_());
		httpModel.setOutMsg(AOSJson.toJson(outDto));
	}

	/**
	 * 修改我的个人资料信息
	 * 
	 * @param httpModel
	 * @return
	 */
	public void updateMyInfo(HttpModel httpModel) {
		Dto outDto = Dtos.newOutDto();
		Dto inDto = httpModel.getInDto();
		AosUserPO aosUserPO = new AosUserPO();
		aosUserPO.copyProperties(inDto);
		aosUserPO.setId_(httpModel.getUserModel().getId_());
		aosUserDao.updateByKey(aosUserPO);
		if (!StringUtils.equals(aosUserPO.getSkin_(), httpModel.getUserModel().getSkin_())) {
			outDto.put("is_skin_changed_", AOSCons.YES);
		}
		UserModel userModel = httpModel.getUserModel();
		AOSUtils.copyProperties(aosUserDao.selectByKey(aosUserPO.getId_()), userModel);
		cacheUserDataService.cacheUserModel(userModel);
		outDto.setAppMsg("我的个人资料数据保存成功。");
		httpModel.setOutMsg(AOSJson.toJson(outDto));
	}

	/**
	 * 修改我的个人密码
	 * 
	 * @param httpModel
	 * @return
	 */
	public void updateMyPwd(HttpModel httpModel) {
		Dto outDto = Dtos.newOutDto();
		Dto inDto = httpModel.getInDto();
		if (!StringUtils.equals(inDto.getString("confirm_new_password_"), inDto.getString("new_password_"))) {
			outDto.setAppCode(AOSCons.NO);
			outDto.setAppMsg("两次密码不一致，请确认。");
		} else {
			AosUserPO aosUserPO = aosUserDao.selectByKey(httpModel.getUserModel().getId_());
			if (!StringUtils.equals(aosUserPO.getPassword_(), AOSCodec.password(inDto.getString("password_")))) {
				outDto.setAppCode(AOSCons.NO);
				outDto.setAppMsg("原始密码有误，请确认。");
			} else {
				aosUserPO.setPassword_(AOSCodec.password(inDto.getString("new_password_")));
				aosUserDao.updateByKey(aosUserPO);
				outDto.setAppMsg("密码修改成功。");
			}
		}
		httpModel.setOutMsg(AOSJson.toJson(outDto));
	}
	public void updateMyPwd2(HttpModel httpModel) {
		Dto outDto = Dtos.newOutDto();
		Dto inDto = httpModel.getInDto();
		if (!StringUtils.equals(inDto.getString("confirm_new_password_"), inDto.getString("new_password_"))) {
			outDto.setAppCode(AOSCons.NO);
			outDto.setAppMsg("两次密码不一致，请确认。");
		} else {
			String sessionId=httpModel.getRequest().getSession().getId();
			String smsCode=cacheMasterDataService.getSMSCode(sessionId);
			System.out.println("smsCode2-------------->>"+smsCode+",sessionId2---------------->>"+sessionId);
			Dto pDto=Dtos.newDto("account_", inDto.getString("account_"));
			AosUserPO aosUserPO = aosUserDao.selectOne(pDto);
			if (AOSUtils.isEmpty(smsCode)) {
				outDto.setAppCode(AOSCons.NO);
				outDto.setAppMsg("验证码已失效!");
			}else if(!smsCode.equals(inDto.getString("smscode_"))){
				outDto.setAppCode(AOSCons.NO);
				outDto.setAppMsg("验证码不正确!");
			}else if(null==aosUserPO||!"2".equals(aosUserPO.getType_())){
				outDto.setAppCode(AOSCons.NO);
				outDto.setAppMsg("当前账号不是专家用户!");
			}else {
				aosUserPO.setPassword_(AOSCodec.password(inDto.getString("new_password_")));
				aosUserDao.updateByKey(aosUserPO);
				outDto.setAppMsg("密码修改成功。");
			}
		}
		httpModel.setOutMsg(AOSJson.toJson(outDto));
	}
	/**
	 * 计算水平导航条、banner右侧注销等按钮样式风格
	 * 
	 * @param curSkin
	 * @return
	 */
	private Dto initNavBarStyle(String curSkin) {
		Dto navDto = Dtos.newDto();
		navDto.put("is_show_top_nav_", AOSCxt.getParam("is_show_top_nav_"));
		String nav_button_style_ = AOSCxt.getParam("nav_button_style_");
		navDto.put("nav_button_style_", nav_button_style_);
		String nav_bar_style = StringUtils.EMPTY;
		String nav_bar_style_visited = StringUtils.EMPTY;
		String right_button_style = StringUtils.EMPTY;
		// 根据SKIN和navbar_btn_style_来判断
		if (curSkin.equalsIgnoreCase(AOSCons.SKIN.BLUE)) {
			if (nav_button_style_.equalsIgnoreCase("tight")) {
				nav_bar_style = "button button-pill button-border-blue";
				nav_bar_style_visited = "button button-pill button-border-blue button-border-blue-visited";
				right_button_style = "button-small button-pill button-small-blue";
			} else if (nav_button_style_.equalsIgnoreCase("standalone")) {
				nav_bar_style = "button button-rounded button-border-blue";
				nav_bar_style_visited = "button button-rounded button-border-blue button-border-blue-visited";
				right_button_style = "button-small button-rounded button-small-blue";
			}
		} else if (curSkin.equalsIgnoreCase(AOSCons.SKIN.GRAY)) {
			if (nav_button_style_.equalsIgnoreCase("tight")) {
				nav_bar_style = "button button-pill button-border-gray";
				nav_bar_style_visited = "button button-pill button-border-gray button-border-gray-visited";
				right_button_style = "button-small button-pill button-small-gray";
			} else if (nav_button_style_.equalsIgnoreCase("standalone")) {
				nav_bar_style = "button button-rounded button-border-gray";
				nav_bar_style_visited = "button button-rounded button-border-gray button-border-gray-visited";
				right_button_style = "button-small button-rounded button-small-gray";
			}
		}
		navDto.put("nav_bar_style", nav_bar_style);
		navDto.put("nav_bar_style_visited", nav_bar_style_visited);
		navDto.put("right_button_style", right_button_style);
		return navDto;
	}

	/**
	 * 生成问候信息
	 * 
	 * @return
	 */
	private String getWelcomeMsg() {
		String welcome = "晚上好";
		Integer timeInteger = new Integer(AOSUtils.getDateTimeStr("HH"));
		if (timeInteger.intValue() >= 7 && timeInteger.intValue() <= 12) {
			welcome = "上午好";
		} else if (timeInteger.intValue() > 12 && timeInteger.intValue() < 19) {
			welcome = "下午好";
		}
		return welcome;
	}

}
