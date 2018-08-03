/**
 * 
 */
package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.cloopen.rest.sdk.CCPRestSmsSDK;

import aos.framework.core.cache.CacheMasterDataService;
import aos.framework.core.id.AOSId;
import aos.framework.core.service.CDZBaseController;
import aos.framework.core.typewrap.Dto;
import aos.framework.core.typewrap.Dtos;
import aos.framework.core.utils.AOSCodec;
import aos.framework.core.utils.AOSJson;
import aos.framework.core.utils.AOSUtils;
import aos.framework.dao.AosParamsDao;
import aos.framework.dao.AosUserDao;
import aos.framework.web.router.HttpModel;
import aos.system.common.utils.SystemCons;
import aos.system.modules.cache.CacheUserDataService;
import dao.ActivityDao;
import dao.ActivityRuleDao;
import dao.AdvertDao;
import dao.AdvertTrafficDao;
import dao.ArticleDao;
import dao.Basic_userDao;
import dao.ChargingOrdersDao;
import dao.ChargingPileDao;
import dao.CommonCommentDao;
import dao.DepositListDao;
import dao.DeviceDao;
import dao.MembersDao;
import dao.MessagesDao;
import dao.OrdersPayDao;
import dao.RegionDao;
import dao.SubscribeDao;
import dao.VoucherDao;
import po.Basic_userPO;
import po.DevicePO;

/**
 * @author Administrator
 *cacheUserDataService.getDealerDTO(juid)
 */
@Service
public class AppApiService extends CDZBaseController {
	@Autowired
	private AdvertDao advertDao;
	@Autowired
	private ArticleDao articleDao;
	@Autowired
	private VoucherDao voucherDao;
	
	@Autowired
	private RegionDao regionDao;
	@Autowired
	private ActivityDao activityDao;
	
	@Autowired
	private CacheMasterDataService cacheMasterDataService;
	
	@Autowired
	private CacheUserDataService cacheUserDataService;
	@Autowired
	AosUserDao aosUserDao;
	
	@Autowired
	ActivityRuleDao activityRuleDao;
	@Autowired
	AdvertTrafficDao advertTrafficDao;
	
	@Autowired
	MembersDao membersDao;
	
	@Autowired
	AosParamsDao aosParamsDao;
	
	@Autowired
	ChargingPileDao chargingPileDao;
	@Autowired
	ChargingOrdersDao chargingOrdersDao;
	@Autowired
	MessagesDao messagesDao;
	@Autowired
	CommonCommentDao commonCommentDao;
	@Autowired
	OrdersPayDao ordersPayDao;
	@Autowired
	Basic_userDao basic_userDao;
	@Autowired
	DepositListDao depositListDao;
	@Autowired
	SubscribeDao subscribeDao;
	@Autowired
	DeviceDao deviceDao;

	private static CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
	
	
	public void getDeviceList(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();
	
		String phone = qDto.getString("phone");
		//phone = "15547245873";

		if (!qDto.containsKey("phone") || AOSUtils.isEmpty(phone)) {
			this.failMsg(odto, "phone值不能为空");
		} else {

			/*
			 * UserModel userModel = cacheUserDataService.getUserModel(phone); Dto pDto =
			 * Dtos.newDto("phone1", phone);
			 */
			/* Dto pDto = Dtos.newDto("phone1", phone); */

			Dto pDto = Dtos.newDto();
			pDto.put("limit", 10);// 默认查询出100个
			pDto.put("start", 0);

			/* pDto.put("phone1", phone); */

			pDto.put("phone", phone);

		List<Dto> deviceDtos = sqlDao.list("Device.listDevicesPage", pDto);
		List<Dto> newListDtos = new ArrayList<Dto>();
	
		
		for (Dto dto : deviceDtos) {
			Dto newDto = Dtos.newDto();	
				newDto.put("device_id", dto.getString("device_id"));
			newDto.put("user_id", dto.getString("user_id"));
			newDto.put("user_name", dto.getString("user_name"));
			newDto.put("user_address", dto.getString("user_address"));
			newDto.put("status",dto.getString("status"));
				newDto.put("is_alarming", dto.getString("is_alarming"));
			newDto.put("head", dto.getString("head"));
			newDto.put("head_phone", dto.getString("head_phone"));
			newDto.put("police_station", dto.getString("police_station"));
			newListDtos.add(newDto);

			}

			odto.put("data", newListDtos);
			odto.put("status", "1");
			odto.put("msg", "共查到一条数据");

		}
		httpModel.setOutMsg(AOSJson.toJson(odto));
	}
	
	
	public void getDeviceDetails(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();

		String id = qDto.getString("id");
		//id = "4gw123456";
		Dto pDto = Dtos.newDto("device_id", id);
		DevicePO devicePO = deviceDao.selectOne(pDto);
		if (null == devicePO) {
			this.failMsg(odto, "设备id错误。");
		} else {

			/* List<Dto> newListDtos = new ArrayList<Dto>(); */

			Dto newDto = Dtos.newDto();
			newDto.put("device_id", devicePO.getDevice_id());
			newDto.put("product_type", devicePO.getProduct_type());
			newDto.put("status", devicePO.getStatus());
			newDto.put("is_alarming", devicePO.getIs_alarming());
			newDto.put("production_date", devicePO.getProduction_date());
			newDto.put("install_date", devicePO.getInstall_date());
			newDto.put("guarantee_time", devicePO.getGuarantee_time());

			newDto.put("user_address", devicePO.getUser_address());
			newDto.put("repair_record", devicePO.getRepair_record());
			newDto.put("repair_progress", devicePO.getRepair_progress());

			newDto.put("head", devicePO.getHead());
			newDto.put("head_phone", devicePO.getHead_phone());
			newDto.put("police_station", devicePO.getPolice_station());
			/* newListDtos.add(newDto); */

			odto.put("data", newDto);
			odto.put("status", "1");
			odto.put("msg", "成功");

		}
		httpModel.setOutMsg(AOSJson.toJson(odto));

	}

	public void deviceBinding(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();
		// TODO
		String phone = qDto.getString("phone");// phone,即为登陆时的用户手机号,15356002207
		String address = qDto.getString("address");
		String deviceInfo = qDto.getString("deviceInfo");
		String[] info = deviceInfo.split("#");
		Dto pDto=Dtos.newDto("account", phone);
		Basic_userPO basic_userPO=basic_userDao.selectOne(pDto);   //用于检查账户是否存在
		Dto p1Dto = Dtos.newDto("device_id", info[0]);
		DevicePO devicePO1 = deviceDao.selectOne(p1Dto);
		if(null==basic_userPO){
			this.failMsg(odto, "手机号输入错误。");
		}else {

			if (null != devicePO1) {
				this.failMsg(odto, "绑定失败，该设备已被绑定");

			} else {
			String info1 = basic_userPO.getDevice_number();
			info1=info1+ "#" + info[0];
			basic_userPO.setDevice_number(info1);//device_number,代表设备编号
			// TODO
				DevicePO devicePO = new DevicePO();
			devicePO.setId_(AOSId.appId(SystemCons.ID.SYSTEM));
			devicePO.setUser_id(basic_userPO.getId_());
			devicePO.setUser_address(address);
			devicePO.setUser_type(basic_userPO.getUser_type());
				devicePO.setDevice_id(info[0]);
			devicePO.setProduct_type(info[1]);
			devicePO.setProduction_date(info[2]);
			

			deviceDao.insert(devicePO);
			basic_userDao.updateByKey(basic_userPO);
			// TODO
		
			odto.put("status", "1");
			odto.put("msg", "绑定成功");
			}

		}
		httpModel.setOutMsg(AOSJson.toJson(odto));
	}
	
	
	public void userLogin(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();
		String phone=qDto.getString("phone");
		String type=qDto.getString("type");
		String role = qDto.getString("role");
		
		Dto pDto=Dtos.newDto("account", phone);
		pDto.put("is_del", "0");
		//pDto.put("type_", "2");
		Basic_userPO basic_userPO=basic_userDao.selectOne(pDto);   //用于检查账户是否存在
		if(null==basic_userPO){
			this.failMsg(odto, "手机号输入错误或已被删除，请重新输入。");
		}else if(type.equals("1")) {
			
			String smsCode=qDto.getString("smsCode");
			String smsSessionId=qDto.getString("smsSessionId");
			
			if(AOSUtils.isEmpty(smsCode)){
				this.failMsg(odto, "smsCode不能为空");
			}else if(AOSUtils.isEmpty(smsSessionId)){
				this.failMsg(odto, "smsSessionId不能为空");
			}else{
			String sms_Code = cacheMasterDataService.getSMSCode(smsSessionId);
				if (AOSUtils.isEmpty(sms_Code)) {
					this.failMsg(odto, "验证码已失效!");
				}else if (!sms_Code.equals(smsCode)) {
					this.failMsg(odto, "验证码不正确!");
				}else{
			//String smsCode=AOSUtils.createRandomVcode();
			//String sessionId=httpModel.getRequest().getSession().getId();
			//cacheMasterDataService.cacheSMSCode(sessionId, smsCode);
			//发送短信，已实现
			//saveLogs("getSmsCode:smsCode-------------->>"+smsCode+",sessionId---------------->>"+sessionId,null);
			//dto.put(AOSCons.APPCODE_KEY, AOSCons.SUCCESS);
					odto.put("token", "150e18b7fdf84cb18cb2493b0d354f16");
					odto.put("uid", "f84cb18cb2493b0d354f12");
					odto.put("msg", "登录成功");
					odto.put("status", "1");
			//this.sendSms(phone, "1", smsCode);
			//dto.put(AOSCons.APPMSG_KEY, AOSCons.APPMSG_SUCCESS);
					}
				}
			}else if(type.equals("0")) {
				
				String password=qDto.getString("password");
				String password_ = AOSCodec.password(password);
				
				if(!StringUtils.equals(password_, basic_userPO.getPassword())){   //用于检查密码是否正确
					this.failMsg(odto, "密码不正确，请重新输入");
				}else {
					//this.failMsg(odto, "密码正确");
					odto.put("token", "150e18b7fdf84cb18cb2493b0d354f16");
					odto.put("uid", "f84cb18cb2493b0d354f12");
					odto.put("msg", "登录成功");
					odto.put("status", "1");
					
				}
			}else 
				this.failMsg(odto, "type不为1也不为0!");
		
			Basic_userPO basic_userPO1=new Basic_userPO();
			basic_userPO1.setToken("1");
			basic_userPO1.setId_(basic_userPO.getId_());
			basic_userDao.updateByKey(basic_userPO1);
				
			httpModel.setOutMsg(AOSJson.toJson(odto));
	}
	
	
	/**
	 * 获取验证码
	 * @param httpModel
	 */
	public void getSmsCode(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto dto = Dtos.newDto();
		//String mobile=qDto.getString("mobile");
		String phone=qDto.getString("phone");
		String type=qDto.getString("type");
		//if(AOSUtils.isEmpty(mobile)){
		if(AOSUtils.isEmpty(phone)){
			this.failMsg(dto, "手机号码不能为空");
		}else if(AOSUtils.isEmpty(type)){
			this.failMsg(dto, "type不能为空");
		}else{
			String smsCode=AOSUtils.createRandomVcode();
			String sessionId=httpModel.getRequest().getSession().getId();
			cacheMasterDataService.cacheSMSCode(sessionId, smsCode);
			//发送短信，已实现
			saveLogs("getSmsCode:smsCode-------------->>"+smsCode+",sessionId---------------->>"+sessionId,null);
			//dto.put(AOSCons.APPCODE_KEY, AOSCons.SUCCESS);
			//dto.put("smsSessionId", sessionId);
			//dto.put("smsCode", smsCode);//暂时未去掉
			//this.sendSms(mobile, "1", smsCode);
			
			dto.put("msg", "验证码已发送");
			//dto.put("smsSessionId", "rj9kq6ghpvwv5n6jy6qtd2ep");
			dto.put("smsSessionId", sessionId);
			dto.put("status", "1");
			this.sendSms(phone, "1", smsCode);
			//dto.put(AOSCons.APPMSG_KEY, AOSCons.APPMSG_SUCCESS);
		}
		httpModel.setOutMsg(AOSJson.toJson(dto));
	}
	
	
	/**
	 * 会员注册
	 */
	public void userRegister(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();
		
		String account=qDto.getString("phone");
		String password=qDto.getString("password");
		String sms_code=qDto.getString("smsCode");
		String smsSessionId=qDto.getString("smsSessionId");
		String role=qDto.getString("role");
		/*if(!qDto.containsKey("account")||AOSUtils.isEmpty(account)){
			this.failMsg(odto, "account不能为空");
		}else if(!qDto.containsKey("password")||AOSUtils.isEmpty(password)){
			this.failMsg(odto, "password不能为空");
		}else if(!qDto.containsKey("sms_code")||AOSUtils.isEmpty(sms_code)){
			this.failMsg(odto, "sms_code不能为空");
		}else */
		if(!qDto.containsKey("smsSessionId")||AOSUtils.isEmpty(smsSessionId)){
			this.failMsg(odto, "smsSessionId不能为空");
		}else{
			Dto pDto=Dtos.newDto("account", account);
			pDto.put("is_del_", "0");   //0表示未删除
			//pDto.put("type_", "2");     
			//AosUserPO aosUserPO=aosUserDao.selectOne(pDto);
			Basic_userPO basic_userPO1=basic_userDao.selectOne(pDto);
			String smsCode = cacheMasterDataService.getSMSCode(smsSessionId);
			if(null!=basic_userPO1){
				this.failMsg(odto, "当前手机号已存在，请重新输入。");
			}else if (AOSUtils.isEmpty(smsCode)) {
				this.failMsg(odto, "验证码已失效!");
			} else if (!smsCode.equals(sms_code)) {
				this.failMsg(odto, "验证码不正确!");
			}else{
				String password_ = AOSCodec.password(password);
				Basic_userPO basic_userPO=new Basic_userPO();
				basic_userPO.setUser_type("1");
				basic_userPO.setId_(AOSId.appId(SystemCons.ID.SYSTEM));
				basic_userPO.setAccount(account);
				basic_userPO.setPassword(password_);
				basic_userPO.setCreate_at(AOSUtils.getDateTime());
				basic_userPO.setStatus("0");  //0表示未屏蔽
				basic_userPO.setIs_del("0");   //0表示未删除
				basic_userPO.setUser_type(role);
				basic_userDao.insert(basic_userPO);
				// TODO
				//注册送优惠券
				/*
				Dto pDto_=Dtos.newDto("is_del", "0");//
				pDto_.put("status_", "1");
				pDto_.put("type_", "3");
				List<ActivityPO> list=activityDao.list(pDto_);
				if(null!=list&&list.size()>0){
					ActivityPO activityPO=list.get(0);
					if(null!=activityPO){
						for(int i=0;i<activityPO.getAr_num().intValue();i++){
							VoucherPO voucherPO=new VoucherPO();
							voucherPO.setActivity_id(activityPO.getActivity_id());
							//voucherPO.setOrder_id(order_id);
							//voucherPO.setCond_value(activityRulePO.getCond_value());
							voucherPO.setIs_del("0");
							voucherPO.setDealer_id(membersPO.getId_());
							voucherPO.setDirection("0");
							voucherPO.setVoucher_amt(activityPO.getAmount());
							voucherPO.setVoucher_id(AOSId.appId(SystemCons.ID.SYSTEM));
							voucherPO.setCreate_date(AOSUtils.getDateTime());
							voucherPO.setEffec_date(AOSUtils.dateAdd(AOSUtils.getCurDayTimestamp(), Calendar.MONTH, Integer.valueOf(AOSCxt.getParam("voucherValidity"))));
							voucherDao.insert(voucherPO);
						}
					}
				}
				*/
				//odto.put(AOSCons.APPCODE_KEY, AOSCons.SUCCESS);
				//odto.put("uid", membersPO.getId_());
				//odto.put(AOSCons.APPMSG_KEY, AOSCons.APPMSG_SUCCESS);
				odto.put("uid",basic_userPO.getId_());
				odto.put("msg","注册成功");
				odto.put("status","1");
			}
		}
		httpModel.setOutMsg(AOSJson.toJson(odto));
	}
	
	
	/**
	 * 忘记密码
	 */
	public void forgetPwd(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();
		
		String mobile=qDto.getString("phone");
		String new_pwd=qDto.getString("newPwd");
		String sms_code=qDto.getString("smsCode");
		//String confirm_pwd=qDto.getString("confirmPwd");
		String smsSessionId=qDto.getString("smsSessionId");
		//this.failMsg(odto, new_pwd);
		/*
		if(!qDto.containsKey("mobile")||AOSUtils.isEmpty(mobile)){
			this.failMsg(odto, "mobile不能为空");
		}else if(!qDto.containsKey("new_pwd")||AOSUtils.isEmpty(new_pwd)){
			this.failMsg(odto, "new_pwd不能为空");
		}else if(!qDto.containsKey("sms_code")||AOSUtils.isEmpty(sms_code)){
			this.failMsg(odto, "sms_code不能为空");
		}else if(!qDto.containsKey("confirm_pwd")||AOSUtils.isEmpty(confirm_pwd)){
			this.failMsg(odto, "confirm_pwd不能为空");
		}else 
		if(!confirm_pwd.equals(new_pwd)){
			this.failMsg(odto, "新密码与确认密码不一致");
		}else */
		if(!qDto.containsKey("smsSessionId")||AOSUtils.isEmpty(smsSessionId)){
			this.failMsg(odto, "smsSessionId不能为空");
		}else{
			Dto pDto=Dtos.newDto("account", mobile);
			pDto.put("is_del", "0");
			//pDto.put("type_", "2");
			Basic_userPO basic_userPO=basic_userDao.selectOne(pDto);
			
			if(null==basic_userPO){
				this.failMsg(odto, "手机号码不存在，请重新输入。");
			}else{
				String smsCode = cacheMasterDataService.getSMSCode(smsSessionId);
				if (AOSUtils.isEmpty(smsCode)) {
					this.failMsg(odto, "验证码已失效!");
				} else if (!smsCode.equals(sms_code)) {
					this.failMsg(odto, "验证码不正确!");
				} else {
					//new_pwd = "1234567890";
					String password = AOSCodec.password(new_pwd);
					
					Basic_userPO basic_userPO_=new Basic_userPO();
					basic_userPO_.setPassword(password);
					basic_userPO_.setId_(basic_userPO.getId_());
					basic_userDao.updateByKey(basic_userPO_);
					//odto.put(AOSCons.APPCODE_KEY, AOSCons.SUCCESS);
					//odto.put(AOSCons.APPMSG_KEY, AOSCons.APPMSG_SUCCESS);
					odto.put("status","1");
					odto.put("msg","密码修改成功");
				}
			}
		}
		httpModel.setOutMsg(AOSJson.toJson(odto));
	}
	
	public void userSignOut(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();
		String token=qDto.getString("token");
		
		Dto pDto=Dtos.newDto("token", token);
		//pDto.put("is_del", "0");
		//pDto.put("type_", "2");
		Basic_userPO basic_userPO=basic_userDao.selectOne(pDto);   //用于检查账户是否存在
		if(null==basic_userPO){
			this.failMsg(odto, "手机号输入错误或已被删除，请重新输入。");
		}else {
			
			Basic_userPO basic_userPO1=new Basic_userPO();
			basic_userPO1.setQq(AOSUtils.getDateTime().toString());
			basic_userPO1.setId_(basic_userPO.getId_());
			basic_userDao.updateByKey(basic_userPO1);
			
			odto.put("status","1");
			odto.put("msg","退出成功");
			
			}
				
			httpModel.setOutMsg(AOSJson.toJson(odto));
	}
	
	
	
	private void initSmsConfig(){
		//restAPI.init(AOSCxt.getParam("sms_url"), AOSCxt.getParam("sms_port"));
		//restAPI.setAccount(AOSCxt.getParam("account_sid"), AOSCxt.getParam("auth_token"));
		//restAPI.setAppId(AOSCxt.getParam("app_id"));
		restAPI.init("app.cloopen.com", "8883");
		restAPI.setAccount("8aaf07086436008b01643c2a00dd05b7", "bcf4e77d8e0b41c88732e51eeb6139db");
		restAPI.setAppId("8aaf07086436008b01643c2a014105be");
	}
	private void sendSms(String mobile,String templateId,String smsCode){
		this.initSmsConfig();
		HashMap<String, Object> result= restAPI.sendTemplateSMS(mobile,templateId,new String[]{smsCode,"2"});
		this.printSmsInfo(result, "");
		
	}
	private void printSmsInfo(HashMap<String, Object> result,String mobile){
		saveLogs("printSmsInfo:短信发送 手机号:"+mobile+"  SDKTestGetSubAccounts result=" + result,null);
		if("000000".equals(result.get("statusCode"))){
			//正常返回输出data包体信息（map）
			HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
			Set<String> keySet = data.keySet();
			for(String key:keySet){
				Object object = data.get(key);
				System.out.println(key +" = "+object);
			}
		}else{
			//异常返回输出错误码和错误信息
			saveLogs("printSmsInfo:错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"),null);
		}
	}
	


}
