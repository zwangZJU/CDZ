/**
 * 
 */
package service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.bind.DatatypeConverter;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import aos.framework.core.cache.CacheMasterDataService;
import aos.framework.core.id.AOSId;
import aos.framework.core.service.CDZBaseController;
import aos.framework.core.typewrap.Dto;
import aos.framework.core.typewrap.Dtos;
import aos.framework.core.utils.AOSCodec;
import aos.framework.core.utils.AOSCxt;
import aos.framework.core.utils.AOSJson;
import aos.framework.core.utils.AOSUtils;
import aos.framework.dao.AosParamsDao;
import aos.framework.dao.AosParamsPO;
import aos.framework.dao.AosUserDao;
import aos.framework.web.router.HttpModel;
import aos.system.common.utils.SystemCons;
import aos.system.modules.cache.CacheUserDataService;
import controller.MultiServerThread;
import dao.ActivityDao;
import dao.ActivityRuleDao;
import dao.AdvertDao;
import dao.AdvertTrafficDao;
import dao.Alarm_logDao;
import dao.App_versionDao;
import dao.ArticleDao;
import dao.Basic_userDao;
import dao.CameraDao;
import dao.ChargingOrdersDao;
import dao.ChargingPileDao;
import dao.CommonCommentDao;
import dao.DepositListDao;
import dao.DeviceDao;
import dao.MembersDao;
import dao.MessagesDao;
import dao.OrdersPayDao;
import dao.RegionDao;
import dao.Repair_logDao;
import dao.SubscribeDao;
import dao.User_feedbackDao;
import dao.VoucherDao;
import po.Alarm_logPO;
import po.Basic_userPO;
import po.CameraPO;
import po.ChargingPilePO;
import po.CommonLogsPO;
import po.DevicePO;
import po.Repair_logPO;
import po.User_feedbackPO;
 
import utils.Constant;
import utils.Helper;
import utils.HttpRequester;
import utils.HttpsUtil;
import utils.JsonUtil;
import utils.Request;
import utils.StreamClosedHttpResponse;


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
	
	private Socket socket = null;
	
	private String ascii="";
	
	private String ascii1="";
	
	private int j=0;
	
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
	@Autowired
	Repair_logDao repair_logDao;
	@Autowired
	Alarm_logDao alarm_logDao;
	@Autowired
	CameraDao cameraDao;
	@Autowired
	User_feedbackDao user_feedbackDao;
	@Autowired
	App_versionDao app_versionDao;
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
	public void init(HttpModel httpModel) {
		httpModel.setViewPath("myproject/map.jsp");
	}

	private static CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
	static String Alias = "18392888103";
	
	
	
	public void getCameraList(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();
	
		String phone = qDto.getString("phone");
		/* System.out.println(phone); */

		Dto pDto = Dtos.newDto();
		pDto.put("device_id", phone);
		int rows = cameraDao.rows(pDto);
		pDto.put("limit", rows);// 默认查询出100个

			pDto.put("start", 0);

			Dto pDto1=Dtos.newDto("key_", "access_token");
			AosParamsPO aosParamsPO=aosParamsDao.selectOne(pDto1);
			String access_token1 = aosParamsPO.getValue_();

		List<Dto> cameraDtos = sqlDao.list("Camera.listCamerasPage", pDto);
		List<Dto> newListDtos = new ArrayList<Dto>();
	
		
		for (Dto dto : cameraDtos) {
			Dto newDto = Dtos.newDto();	
			newDto.put("camera_serial", getData(dto.getString("camera_serial")));
			newDto.put("access_token", getData(access_token1));
			newDto.put("camera_type", getData(dto.getString("camera_type")));
			newDto.put("verification_code", getData(dto.getString("verification_code")));
		
			newListDtos.add(newDto);

			}

			odto.put("camera", newListDtos);
			odto.put("status", "1");
		odto.put("msg", "共查到" + rows + "条数据");


		httpModel.setOutMsg(AOSJson.toJson(odto));
	}
	
	
	
	public void resultReturn(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();

		String id = qDto.getString("id");
		String type = qDto.getString("type");
		String result = qDto.getString("result");
		if (id.equals("0")) {
			Dto pDto = Dtos.newDto("alarm_id", id);
			Alarm_logPO alarm_logPO = alarm_logDao.selectOne(pDto);

			 alarm_logPO.setReason_(result);
			 alarm_logPO.setAlarm_release("1");
			 alarm_logPO.setProcess("1");
			 alarm_logPO.setResponse_time(new Date());
			 alarm_logDao.updateByKey(alarm_logPO);
			
		}
         if (id.equals("1")) {
        	 Dto pDto = Dtos.newDto("repair_id", id);
     		Repair_logPO repair_logPO = repair_logDao.selectOne(pDto);
     		String info = repair_logPO.getState_info();
     		String device_id = repair_logPO.getDevice_id();
    		Calendar cal = Calendar.getInstance();
    		int year = cal.get(Calendar.YEAR);// 获取年份
    		int month = (cal.get(Calendar.MONTH) + 1);// 获取月份
    		int day = cal.get(Calendar.DAY_OF_MONTH);// 获取日
    		int hour = cal.get(Calendar.HOUR_OF_DAY);// 小时
    		int minute = cal.get(Calendar.MINUTE);// 分
    		info = info + "#维修完成%" + year + "年" + month + "月" + day + "日" + hour + ":" + minute;
			repair_logPO.setProcessing_state("3");
			repair_logPO.setState_info(info);
			repair_logPO.setRenovate_time(new Date());
			repair_logPO.setIs_completed("1");
			 repair_logPO.setError_reason(result); 
			 repair_logDao.updateByKey(repair_logPO);
			 
			 Dto pDto2 = Dtos.newDto("device_id", device_id);
				DevicePO devicePO = deviceDao.selectOne(pDto2);
				
				devicePO.setRepair_progress("1");//已完成
				deviceDao.updateByKey(devicePO);
			
		}
		
		

		
		odto.put("status", "1");
		odto.put("msg", "回传成功");

		httpModel.setOutMsg(AOSJson.toJson(odto));
	}
	
	public void submitSuggestion(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();
		
		String phone = qDto.getString("phone");
		String suggestion = qDto.getString("suggestion");
		
		User_feedbackPO user_feedbackPO = new User_feedbackPO();
		user_feedbackPO.setId_(AOSId.appId(SystemCons.ID.SYSTEM));
		user_feedbackPO.setAccount_(phone);
		user_feedbackPO.setAdvice_(suggestion);
		user_feedbackPO.setTime_(new Date());
		user_feedbackDao.insert(user_feedbackPO);

		odto.put("status", "1");
		odto.put("msg", "反馈成功");
		httpModel.setOutMsg(AOSJson.toJson(odto));
		
	}
	
	
	public void getPoliceMission(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();
		
		String phone = qDto.getString("phone");
		
		Dto pDto = Dtos.newDto();
		pDto.put("handler_phone", phone);
		int rows = alarm_logDao.rows(pDto);
		pDto.put("limit", rows);

		pDto.put("start", 0);
		List<Dto> newListDtos = new ArrayList<Dto>();
		List<Dto> alarmDtos = sqlDao.list("Alarm_log.listAlarmOrders", pDto);
		
		if (null != alarmDtos && !alarmDtos.isEmpty()) {

			for (Dto dto : alarmDtos) {
				Dto newDto = Dtos.newDto();
					//newDto.put("alarm_id", getData(dto.getString("alarm_id")));
					//newDto.put("device_id", getData(dto.getString("device_id")));

					//String id = dto.getString("device_id");
					//Dto pDto1 = Dtos.newDto("device_id", id);
					//DevicePO devicePO1 = deviceDao.selectOne(pDto1);
					//String address = devicePO1.getUser_address();

					newDto.put("alarm_time", dto.getString("alarm_time"));
					newDto.put("user_phone", getData(dto.getString("user_phone")));
					newDto.put("alarm_id", getData(dto.getString("alarm_id")));
					/* newDto.put("is_completed", ""); */
					String zzz = dto.getString("type_");
					
					if(dto.getString("type_").equals("1"))
					{
						newDto.put("reason","手动报警");
						newDto.put("type","1");
						
						//手动报警地址
						Dto pDto2 = Dtos.newDto("account", dto.getString("user_phone"));
						Basic_userPO basic_userPO = basic_userDao.selectOne(pDto2);
						newDto.put("user_address",basic_userPO.getAddress());
					}
					else if(dto.getString("type_").equals("2"))
					{
						newDto.put("reason","一键报警");
						newDto.put("type","2");
						
						//一键报警地址
						Dto pDto3 = Dtos.newDto("device_id", dto.getString("device_id"));
						DevicePO devicePO3 = deviceDao.selectOne(pDto3);
						newDto.put("user_address",devicePO3.getUser_address());
					}
					else if(dto.getString("type_").equals("0"))
					{
						newDto.put("reason",dto.getString("reason_") == "0");
						newDto.put("type","0");
						
						//设备地址
						Dto pDto4 = Dtos.newDto("device_id", dto.getString("device_id"));
						DevicePO devicePO4 = deviceDao.selectOne(pDto4);
						newDto.put("user_address",devicePO4.getUser_address());
					}

				newListDtos.add(newDto);

			}

			odto.put("data", newListDtos);
			odto.put("status", "1");
			odto.put("msg", "成功");
			} else {
				Dto newDto = Dtos.newDto();
				newDto.put("alarm_id", " ");
				newDto.put("type", " ");
				newDto.put("alarm_time", " ");

				newDto.put("user_phone", " ");
				newDto.put("reason", " ");
				newDto.put("user_address", " ");
				/* newDto.put("is_completed", " "); */
				newListDtos.add(newDto);
				odto.put("data", newListDtos);
				odto.put("status", "-1");
				odto.put("msg", "失败");

			}

		httpModel.setOutMsg(AOSJson.toJson(odto));
		
	}
	
	public void updateRepairProcessingState(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();

		String repair_id = qDto.getString("repair_id");
		String processing_state = qDto.getString("processing_state");
		
		Dto pDto = Dtos.newDto("repair_id", repair_id);
		Repair_logPO repair_logPO = repair_logDao.selectOne(pDto);

		String info = repair_logPO.getState_info();

		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);// 获取年份
		int month = (cal.get(Calendar.MONTH) + 1);// 获取月份
		int day = cal.get(Calendar.DAY_OF_MONTH);// 获取日
		int hour = cal.get(Calendar.HOUR_OF_DAY);// 小时
		int minute = cal.get(Calendar.MINUTE);// 分
		if (processing_state.equals("2")) {

			info = info + "#开始维修%" + year + "年" + month + "月" + day + "日" + hour + ":" + minute;
			repair_logPO.setProcessing_state(processing_state);
			repair_logPO.setState_info(info);
			repair_logDao.updateByKey(repair_logPO);
			odto.put("status", "1");
			odto.put("msg", "上传成功");
		}
		

		

		httpModel.setOutMsg(AOSJson.toJson(odto));
	}
	
	public void checkAndUpdate(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();

		String current_version = qDto.getString("current_version");
		String role = qDto.getString("role");

		String version = current_version.substring(1);
		version = version.replace(".", "");

		Dto pDto1 = Dtos.newDto();
		Dto pDto = Dtos.newDto();


		int rows = app_versionDao.rows(pDto1);

		pDto.put("limit", rows);// 默认查询出100个

		pDto.put("start", 0);

		List<Dto> appDtos = sqlDao.list("App_version.listApp", pDto);
		int num = appDtos.size();
		Dto dto = appDtos.get(num - 1);
		String version1 = dto.getString("version_no");
		version1 = version1.substring(1);
		version1 = version1.replace(".", "");
		int num1 = Integer.parseInt(version);
		int num2 = Integer.parseInt(version1);
		if (num2 > num1) {
			odto.put("status", "1");
			odto.put("msg", "有新版本");
			odto.put("new_version_url", dto.getString("download_url"));
			odto.put("update_content", getData(dto.getString("update_content")));
			odto.put("version_no", version1);
			odto.put("package_size", getData(dto.getString("package_size")));

		} else {
			odto.put("status", "-1");
			odto.put("msg", "已经是最新版本");
			odto.put("new_version_url", " ");
			odto.put("update_content", " ");
			odto.put("version_no", " ");
			odto.put("package_size", " ");

		}


		httpModel.setOutMsg(AOSJson.toJson(odto));
	}

	/*
	 * ##########################################################################
	 * 取消报警
	 */
	/*
	 * Desktop.getDesktop().open(new File("C:/Users/Administrator/Desktop/0.jpg"));
	 */

	public void uploadRealTimeLocation(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();

		String phone = qDto.getString("phone");
		String lat = qDto.getString("latitude");
		String lon = qDto.getString("longitude");
		String addr = qDto.getString("address");

		String address = addr + "#latitude: " + lat+ ", longtitude: " +lon;

		Dto pDto = Dtos.newDto("account", phone);
		Basic_userPO basic_userPO = basic_userDao.selectOne(pDto);
		basic_userPO.setAddress(address);

		basic_userDao.updateByKey(basic_userPO);

		Dto pDto3 = Dtos.newDto();
		Dto pDto2 = Dtos.newDto();
		pDto2.put("user_phone", phone);

		int rows = alarm_logDao.rows(pDto3);

		pDto2.put("limit", rows);// 默认查询出100个

		pDto2.put("start", 0);

		List<Dto> sosDtos = sqlDao.list("Alarm_log.listSos", pDto2);
		int num = sosDtos.size();
		Dto dto = sosDtos.get(num - 1);
		String process = dto.getString("process");

		if (process.equals("0")) {
			odto.put("status", "1");
			odto.put("msg", "上传结果成功");

		} else {
			odto.put("status", "2");
			odto.put("msg", "已经接警");
		}




		httpModel.setOutMsg(AOSJson.toJson(odto));
	}
	public void cancelAlarm(HttpModel httpModel)  {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();

		String phone = qDto.getString("phone");
		String reason = qDto.getString("reason");
		
		Dto pDto1 = Dtos.newDto();

		Dto pDto = Dtos.newDto();
		pDto.put("user_phone", phone);
		
		int rows = alarm_logDao.rows(pDto1);

		pDto.put("limit", rows);// 默认查询出100个

		pDto.put("start", 0);
		

		List<Dto> sosDtos = sqlDao.list("Alarm_log.listSos", pDto);
		int num = sosDtos.size();
		Dto dto = sosDtos.get(num - 1);
		String id = dto.getString("alarm_id");
		
		Dto pDto2 = Dtos.newDto("alarm_id", id);
		Alarm_logPO alarm_logPO = alarm_logDao.selectOne(pDto2);
		alarm_logPO.setIs_cancel("1");
		alarm_logPO.setReason_(reason);
		
		alarm_logDao.updateByKey(alarm_logPO);
	


		odto.put("status", "1");
		odto.put("msg", "取消报警成功");

		httpModel.setOutMsg(AOSJson.toJson(odto));
	}

	public void deleteCamera(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();

		String camera_serial = qDto.getString("camera_serial");

		Dto pDto = Dtos.newDto("camera_serial", camera_serial);
		CameraPO cameraPO = cameraDao.selectOne(pDto);
		String camera_id = cameraPO.getCamera_id();
		cameraDao.deleteByKey(camera_id);

		odto.put("status", "1");
		odto.put("msg", "删除成功");

		httpModel.setOutMsg(AOSJson.toJson(odto));
	}
	
	public void bindingCamera(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();

		String device_id = qDto.getString("device_id");
		
		
		String cameraInfo = qDto.getString("cameraInfo");
		
		String[] info = cameraInfo.split("\r");

		CameraPO cameraPO  = new CameraPO();
		cameraPO.setCamera_id(AOSId.appId(SystemCons.ID.SYSTEM));
		cameraPO.setDevice_id(device_id);
		cameraPO.setCamera_serial(info[1]);
		
		Dto pDto=Dtos.newDto("key_", "access_token");
		AosParamsPO aosParamsPO=aosParamsDao.selectOne(pDto);
		String access_token1 = aosParamsPO.getValue_();
		
		cameraPO.setAccess_token(access_token1);
		cameraPO.setVerification_code(info[2]);
		cameraPO.setCamera_type(info[3]);
		
		

		cameraDao.insert(cameraPO);
		

			odto.put("status", "1");
			odto.put("msg", "绑定成功");



		httpModel.setOutMsg(AOSJson.toJson(odto));
	}
	
	public void supplementCameraInfo( HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		 Dto odto=Dtos.newDto();
		String camera_serial=qDto.getString("camera_serial");
		String camera_no=qDto.getString("camera_no");
		String camera_label=qDto.getString("camera_label");
		
		 CameraPO cameraPO  = new CameraPO();
//		cameraPO.setCamera_serial(camera_serial);
//		cameraPO.setCamera_no(camera_no);
//		cameraPO.setCamera_label(camera_label);
		//String Camera_serial=cameraPO.getCamera_serial();
		Dto pDto=Dtos.newDto("key_", "access_token");
		AosParamsPO aosParamsPO=aosParamsDao.selectOne(pDto);
		 String access_token1 = aosParamsPO.getValue_();
		 //String Camera_serial=cameraPO.getCamera_serial();
		
			
		String url="https://open.ys7.com/api/lapp/live/address/get";
		String s=Request.sendPost(url, "accessToken="+access_token1+"&source="+camera_serial+":"+camera_no);
		//cameraDao.updateByKey_(cameraPO);
		JsonObject jsonObject = (JsonObject) new JsonParser().parse(s);
		
		JsonElement je=jsonObject.get("data");
		String str = je.toString();
		System.out.println(str);
		 JSONArray jsonArray=new JSONArray(str);
		 
		final String hlsHd = jsonArray.getJSONObject(0).get("hlsHd").toString();
		System.out.println(hlsHd);
		final String rtmpHd = jsonArray.getJSONObject(0).get("rtmpHd").toString();
		
		cameraPO.setHls_(hlsHd);
		cameraPO.setRtmp_(rtmpHd);	
		cameraPO.setCamera_serial(camera_serial);
		cameraPO.setCamera_no(camera_no);
		cameraPO.setCamera_label(camera_label);
		cameraDao.updateBySerial(cameraPO);
		odto.put("status", "1");
	    odto.put("msg", "成功");
	    httpModel.setOutMsg(AOSJson.toJson(odto));
		
	}


	/* ############################################weixiu######################### */
	public void getRepairOrders(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();

		String handler_phone = qDto.getString("phone");
		handler_phone="18392888103";
		
		Dto pDto = Dtos.newDto();
		pDto.put("handler_phone", handler_phone);
		int rows = repair_logDao.rows(pDto);
		pDto.put("limit", rows);

		pDto.put("start", 0);
		List<Dto> newListDtos = new ArrayList<Dto>();
		List<Dto> repairDtos = sqlDao.list("Repair_log.listRepairOrders", pDto);
		if (null != repairDtos && !repairDtos.isEmpty()) {

		for (Dto dto : repairDtos) {
			Dto newDto = Dtos.newDto();
				newDto.put("repair_id", getData(dto.getString("repair_id")));
				newDto.put("device_id", getData(dto.getString("device_id")));

				String id = dto.getString("device_id");
				Dto pDto1 = Dtos.newDto("device_id", id);
				DevicePO devicePO1 = deviceDao.selectOne(pDto1);
				String address = devicePO1.getUser_address();

				newDto.put("repair_time", dto.getString("repair_time"));
				newDto.put("repair_content", getData(dto.getString("repair_content")));
				newDto.put("user_phone", getData(dto.getString("user_phone")));
				newDto.put("user_address", getData(address));
				newDto.put("processing_state", getData(dto.getString("processing_state")));
				/* newDto.put("is_completed", ""); */

			newListDtos.add(newDto);

		}

		odto.put("data", newListDtos);
		odto.put("status", "1");
		odto.put("msg", "获取维修订单成功");
		} else {
			Dto newDto = Dtos.newDto();
			newDto.put("repair_id", " ");
			newDto.put("device_id", " ");
			newDto.put("repair_time", " ");

			newDto.put("repair_content", " ");
			newDto.put("user_phone", " ");
			newDto.put("user_address", " ");
			/* newDto.put("is_completed", " "); */
			newDto.put("processing_state", " ");
			newListDtos.add(newDto);
			odto.put("data", newListDtos);
			odto.put("status", "-1");
			odto.put("msg", "获取失败");

		}

		httpModel.setOutMsg(AOSJson.toJson(odto));
	}

	
	
	
	
	/*public void uploadRepairResult(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();

		

			odto.put("msg", "上传结果成功");
			odto.put("status", "1");

		



		httpModel.setOutMsg(AOSJson.toJson(odto));
	}*/
	
	
	/* ############################################保存头像######################### */
	public void uploadUserAvatar(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();

		String phone = qDto.getString("phone");
		String avatar = qDto.getString("avatar");
		//avatar = avatar.replaceAll(" ", "+");
		
        Dto pDto = Dtos.newDto("account", phone);
		Basic_userPO basic_userPO = basic_userDao.selectOne(pDto); // 用于检

		Dto outDto = Dtos.newDto();
		outDto = this.base64ToFile(httpModel.getRequest(), httpModel.getResponse(), avatar, outDto);
		if (SystemCons.SUCCESS.equals(outDto.getAppCode())) {

			basic_userPO.setAvatar(outDto.getAppMsg().replace("\\", "/"));
			avatar = "http://118.126.95.215:9090/cdz/myupload" + outDto.getAppMsg().replace("\\", "/");
			basic_userDao.updateByKey(basic_userPO);

			odto.put("avatar", getData(avatar));

			odto.put("msg", "更新成功");
			odto.put("status", "1");
		}
		else {
			odto.put("avatar", " ");

			odto.put("msg", "更新失败");
			odto.put("status", "-1");

		}



		httpModel.setOutMsg(AOSJson.toJson(odto));
	}

	
	
	
	public void getUserBasicInfo(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();

		String phone = qDto.getString("phone");
		
		Dto pDto1 = Dtos.newDto("key_", "access_token");
		AosParamsPO aosParamsPO = aosParamsDao.selectOne(pDto1);
		String access_token1 = aosParamsPO.getValue_();

	

		Dto pDto = Dtos.newDto("account", phone);
		Basic_userPO basic_userPO = basic_userDao.selectOne(pDto); // 用
		String avatar = basic_userPO.getAvatar();
		if (null != avatar && !avatar.isEmpty()) {
			avatar = "http://118.126.95.215:9090/cdz/myupload" + basic_userPO.getAvatar();

		} else {
			avatar = "";

		}


		odto.put("name", getData(basic_userPO.getName()));
		odto.put("is_cert", getData(basic_userPO.getIs_cert()));
		odto.put("avatar", getData(avatar));
		odto.put("access_token", access_token1);

		odto.put("msg", "查询成功");
		odto.put("status", "1");

		httpModel.setOutMsg(AOSJson.toJson(odto));
	}

	/* ############################################报警######################### */
	public void getAlarmLogList(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();

		String phone = qDto.getString("phone");
		

		Dto pDto = Dtos.newDto();
		pDto.put("user_phone", phone);
		int rows = alarm_logDao.rows(pDto);
		pDto.put("limit", rows);// 默认查询出100个

		pDto.put("start", 0);

		List<Dto> alarmDtos = sqlDao.list("Alarm_log.listAlarm_logsPage", pDto);
		List<Dto> newListDtos = new ArrayList<Dto>();
		if (rows != 0) {

		for (Dto dto : alarmDtos) {
				Dto newDto = Dtos.newDto();

			newDto.put("alarm_time", getData(dto.getString("alarm_time")));
			newDto.put("device_id", getData(dto.getString("device_id")));

			String deviceid = dto.getString("device_id");
			String type = dto.getString("type_");
			String useraddress = "";
			if (type.equals("1")) {
				useraddress = "";

			} else {
				Dto pDto1 = Dtos.newDto("device_id", deviceid);
				DevicePO devicePO1 = deviceDao.selectOne(pDto1);
				useraddress = devicePO1.getUser_address();
			}

			newDto.put("user_address", getData(useraddress));
			newDto.put("reason", getData(dto.getString("reason_")));
			newDto.put("type", getData(dto.getString("type_")));
				newListDtos.add(newDto);
				odto.put("data", newListDtos);
				odto.put("status", "1");
				odto.put("msg", "查询成功");

		}
		} else {
			Dto newDto = Dtos.newDto();
			newDto.put("alarm_time", " ");
			newDto.put("device_id", " ");
			newDto.put("user_address", " ");
			newDto.put("reason", " ");
			newDto.put("type", " ");
			newListDtos.add(newDto);
			odto.put("data", newListDtos);
			odto.put("status", "-1");
			odto.put("msg", "查询失败");

		}

		httpModel.setOutMsg(AOSJson.toJson(odto));
	}

	public void sos(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();

		String phone = qDto.getString("phone");
		
		Alarm_logPO alarm_logPO = new Alarm_logPO();
		alarm_logPO.setAlarm_id(AOSId.appId(SystemCons.ID.SYSTEM));
		alarm_logPO.setUser_phone(phone);
		alarm_logPO.setAlarm_time(new Date());
		alarm_logPO.setType_("1");
		alarm_logPO.setProcess("0");
		alarm_logDao.insert(alarm_logPO);



		odto.put("status", "1");
		odto.put("msg", "报警成功");
		httpModel.setOutMsg(AOSJson.toJson(odto));

	}
	
	/*
	 * 报修 #### ########,同一个手机号，is_complete,必须只有一个为0，正在维修，其他为1表示已完成
	 */

	public void deviceFaultReport(HttpModel httpModel) {
		//SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();

		String phone = qDto.getString("phone");// phone,即为登陆时的用户手机号,15356002207
		String device_id = qDto.getString("device_id");
		String fault_description = qDto.getString("fault_description");
		
		Dto pDto = Dtos.newDto("user_phone", phone);
		Dto pDto1 = Dtos.newDto("phone", phone);
		pDto1.put("device_id", device_id);// 默认查
		
		int rows = repair_logDao.rows(pDto);
		pDto.put("limit", rows);// 默认查

		pDto.put("start", 0);
		List<Dto> repairDtos = sqlDao.list("Repair_log.listRepair_logsPage2", pDto);
		List<Dto> repairDtos1 = sqlDao.list("Device.listDevicesPage3", pDto1);
		if (null != repairDtos1 && !repairDtos1.isEmpty()) {

			if (null != repairDtos && !repairDtos.isEmpty()) {

				this.fail(odto, "报修失败，您有正在维修的设备");

		} else {

			Repair_logPO repair_logPO = new Repair_logPO();
			repair_logPO.setRepair_id(AOSId.appId(SystemCons.ID.SYSTEM));
			repair_logPO.setDevice_id(device_id);
			repair_logPO.setUser_phone(phone);
			repair_logPO.setRepair_time(new Date());
			repair_logPO.setRepair_content(fault_description);
			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);// 获取年份
			int month = (cal.get(Calendar.MONTH) + 1);// 获取月份
			int day = cal.get(Calendar.DAY_OF_MONTH);// 获取日
			int hour = cal.get(Calendar.HOUR_OF_DAY);// 小时
			int minute = cal.get(Calendar.MINUTE);// 分
			
			Dto pDto2 = Dtos.newDto("device_id", device_id);
			DevicePO devicePO = deviceDao.selectOne(pDto2);
			String record = devicePO.getRepair_record();
			record=record+"#"+year+month+day;
			
			devicePO.setRepair_record(record);
			devicePO.setRepair_progress("0");
			deviceDao.updateByKey(devicePO);
			repair_logPO.setState_info("已报修" + "%" + year + "年" + month + "月" + day + "日" + hour + ":" + minute);
			
			repair_logPO.setProcessing_state("0");
			repair_logPO.setIs_completed("0");
			repair_logDao.insert(repair_logPO);
			

			odto.put("status", "1");
			odto.put("msg", "报修成功");
		}

		} else {
			this.fail(odto, "报修失败，这不是您的设备");

		}
		httpModel.setOutMsg(AOSJson.toJson(odto));
	}

	public void getRepairProgress(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();

		String phone = qDto.getString("phone");
		String repair_id = qDto.getString("repair_id");
	
		if (null != repair_id && !repair_id.isEmpty()) {
			
			Dto pDto = Dtos.newDto("repair_id", repair_id);
			Repair_logPO repair_logPO = repair_logDao.selectOne(pDto);
			
			odto.put("processing_state", getData(repair_logPO.getProcessing_state()));
			odto.put("state_info", getData(repair_logPO.getState_info()));
			odto.put("repair_content", getData(repair_logPO.getRepair_content()));

			odto.put("status", "1");
			odto.put("msg", "查询成功");
			
			

		} else {

		Dto pDto = Dtos.newDto("user_phone", phone);

		int rows = repair_logDao.rows(pDto);
		pDto.put("limit", rows);

		pDto.put("start", 0);


		List<Dto> repairDtos = sqlDao.list("Repair_log.listRepair_logsPage2", pDto);
		if (null != repairDtos && !repairDtos.isEmpty()) {
			for (Dto dto : repairDtos) {
			odto.put("processing_state", getData(dto.getString("processing_state")));
			odto.put("state_info", getData(dto.getString("state_info")));
				odto.put("repair_content", getData(dto.getString("repair_content")));
			odto.put("status", "1");
			odto.put("msg", "查询成功");
			}
		} else {

			odto.put("processing_state", " ");
			odto.put("state_info", " ");
			odto.put("repair_content", " ");
			odto.put("status", "-1");
			odto.put("msg", "查询失败");
		}
		}
		httpModel.setOutMsg(AOSJson.toJson(odto));


	}

	public void getRepairLogList(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();

		String phone = qDto.getString("phone");
	

		Dto pDto = Dtos.newDto();
		pDto.put("user_phone", phone);
		int rows = repair_logDao.rows(pDto);
		pDto.put("limit", rows);// 

		pDto.put("start", 0);

		List<Dto> repairDtos = sqlDao.list("Repair_log.listRepair_logsPage", pDto);
		List<Dto> newListDtos = new ArrayList<Dto>();
		if (rows != 0) {

			for (Dto dto : repairDtos) {
				Dto newDto = Dtos.newDto();
				newDto.put("device_id", getData(dto.getString("device_id")));
				newDto.put("repair_id", getData(dto.getString("repair_id")));
				newDto.put("repair_time", getData(dto.getString("repair_time")));
				newDto.put("renovate_time", getData(dto.getString("renovate_time")));
				newDto.put("handler_", getData(dto.getString("handler_")));
				newDto.put("handler_phone", getData(dto.getString("handler_phone")));
				newDto.put("error_reason", getData(dto.getString("error_reason")));
				newDto.put("processing_state", getData(dto.getString("processing_state")));
				
				newDto.put("repair_content", getData(dto.getString("repair_content")));
				newListDtos.add(newDto);
				odto.put("data", newListDtos);
				odto.put("status", "1");
				odto.put("msg", "查询成功");

			}
		} else {
			Dto newDto = Dtos.newDto();
			newDto.put("device_id", " ");
			newDto.put("repair_id"," ");
			newDto.put("repair_time", " ");
			newDto.put("renovate_time", " ");
			newDto.put("handler_", " ");
			newDto.put("handler_phone", " ");
			newDto.put("error_reason", " ");
			newListDtos.add(newDto);
			odto.put("data", newListDtos);
			odto.put("status", "-1");
			odto.put("msg", "查询失败");

		}

		httpModel.setOutMsg(AOSJson.toJson(odto));
	}

//	public void pushToSingle(HttpModel httpModel) {
//
//
//
//		try {
//			Push.pushToSingle();
//
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}

	public void pushToApp(HttpModel httpModel) {

		try {
			Push.pushToApp();
			sendSms("18392888103,15356002207", "1", null);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void saveLogs1(String content,String cp_id){
    	
		CommonLogsPO commonLogsPO=new CommonLogsPO();
		commonLogsPO.setLog_id(AOSId.appId(SystemCons.ID.SYSTEM));
		commonLogsPO.setCreate_date(AOSUtils.getDateTime());
		commonLogsPO.setIs_del("0");
		commonLogsPO.setContent(content);
		commonLogsPO.setOper_id(cp_id);
		commonLogsPO.setOper_name("socket");
		this.commonLogsDao.insert(commonLogsPO);
		//System.out.println("保存日志进数据库....");
	}
	
    public void saveLogs(String content,String cp_id,String log_type){
    	
		CommonLogsPO commonLogsPO=new CommonLogsPO();
		commonLogsPO.setLog_id(AOSId.appId(SystemCons.ID.SYSTEM));
		commonLogsPO.setCreate_date(AOSUtils.getDateTime());
		commonLogsPO.setIs_del("0");
		commonLogsPO.setContent(content);
		commonLogsPO.setOper_id(cp_id);
		commonLogsPO.setOper_name("socket");
		commonLogsPO.setLog_type(log_type);
		this.commonLogsDao.insert(commonLogsPO);
		//System.out.println("保存日志进数据库....");
	}
    
    /**
	 * 撤防布防
     * 
	 */
    
	public void deployDefense(final HttpModel httpModel) throws InterruptedException {
		final Dto qDto = httpModel.getInDto();
		final Dto odto = Dtos.newDto();
		final String device_id=qDto.getString("device_id");
		final String cmd=qDto.getString("cmd"); 
		//String co_type=qDto.getString("co_type");
		//String co_num=qDto.getString("co_num");
		
		final Dto pDto = Dtos.newDto("device_id", device_id);
		final DevicePO devicePO = deviceDao.selectOne(pDto);
		final Dto newDto = Dtos.newDto();
		newDto.put("device_id", getData(devicePO.getArrange_withdraw()));
		System.out.println(newDto);
		
		 String flag = sendCharging(device_id,cmd);
		
		
		
		if(flag == "1"){
			
			System.out.println("发送成功");
			
			//run();
			//Thread(1000);
		
			
			//while((cmd.equals("4")&&devicePO.getArrange_withdraw().equals("撤防"))||(cmd.equals("5")&&devicePO.getArrange_withdraw().equals("布防")));
		
						
						//if(cmd.equals("4")&&devicePO.getArrange_withdraw().equals("1"))  //布防
						if(cmd.equals("4"))
						{	
							odto.put("status", "1");
							odto.put("msg", "发送成功");
							odto.put("deploy_status","0" );
							
						}
						//if(cmd.equals("5")&&devicePO.getArrange_withdraw().equals("0"))  //撤防
						
						if(cmd.equals("5"))
						{
							odto.put("status", "1");
							odto.put("msg", "发送成功");
							odto.put("deploy_status","1" );
							
						}
					}
					
					if(flag != "1"){
						if(flag == "设备不在线，请检查")
						{
							if(devicePO.getArrange_withdraw().equals("布防"))
							{
								odto.put("status", "-1");
								odto.put("msg", "设备不在线，请检查");
								//odto.put("msg", getData(flag));
							    odto.put("deploy_status","1" );
							}
							if(devicePO.getArrange_withdraw().equals("撤防"))
							{
								odto.put("status", "-1");
								odto.put("msg", "设备不在线，请检查");
								//odto.put("msg", getData(flag));
							    odto.put("deploy_status","0" );
							}
						}
						else {
							if(devicePO.getArrange_withdraw().equals("布防"))
							{
								odto.put("status", "-1");
								odto.put("msg", "发送失败");
								//odto.put("msg", getData(flag));
							    odto.put("deploy_status","1" );
							}
							if(devicePO.getArrange_withdraw().equals("撤防"))
							{
								odto.put("status", "-1");
								odto.put("msg", "发送失败");
								//odto.put("msg", getData(flag));
							    odto.put("deploy_status","0" );
							}
						}
						
					}
		httpModel.setOutMsg(AOSJson.toJson(odto));
		
	}
	
	private String sendCharging(String sg_id,String cmd_app){
		String flag="0";
		//Integer.toHexString(); 0：金额，1：时间，2：度数，3：充满
		 byte[] data_out;
         String msg_welcome = Helper.fillString('0', 32*2);
          
         
		String cmd="E1";
		
		BigDecimal co_num_=new BigDecimal(cmd_app);
		System.out.println("co_num_:"+co_num_);
		cmd_app=Integer.toHexString(co_num_.intValue());//转为十六进制
		//cmd_app=String.format("%2s",cmd_app);
		String finalResult = cmd_app;
		//cmd=cmd+"00000000"+finalResult;
		cmd = "e1"+"0"+finalResult+"0410"+"000000000000000000000000";
		System.out.println("cmd:"+cmd);
		data_out= Helper.hexStringToByteArray(cmd);
		try {
			sg_id= sg_id.substring(1);
			System.out.println("sg_id:"+sg_id);
			Socket socket=Helper.socketMap.get(sg_id);
			//boolean state = socket.isClosed();
			try{
				socket.sendUrgentData(0xFF);
			} catch(Exception e) {
				System.out.println("socket已经断开连接");
				socket = null;
			}
			if(null!=socket){
				socket.getOutputStream().write(data_out);
				System.out.println("APP发送布防撤防请求数据:"+cmd);
				saveLogs3("APP发送布防撤防请求数据:"+cmd,sg_id,"SC④");
				flag="1";
				socket = null;
			}else{
				//this.failMsg(odto, "充电桩未连接");
				System.out.println("APP发送布防撤防请求数据:模块未连接"+msg_welcome);
				saveLogs3("APP发送布防撤防请求数据:模块未连接",sg_id,"SC④");
				flag = "设备不在线，请检查";
			}
			
			
		} catch (IOException e) {
			if("Socket is closed".equals(e.getMessage())){
				saveLogs3("APP发送布防撤防请求数据:模块未连接"+msg_welcome,sg_id,"SC④");
				//this.failMsg(odto, "APP发送充电请求数据异常:充电桩未连接")
				
			}else{
				saveLogs3("APP发送布防撤防请求数据异常:"+msg_welcome,sg_id,"SC④");
				//this.failMsg(odto, "APP发送充电请求数据异常");
			}
			e.printStackTrace();
		}
		return flag;
	}
	
	 public void saveLogs3(String content,String cp_id){
			CommonLogsPO commonLogsPO=new CommonLogsPO();
			commonLogsPO.setLog_id(AOSId.appId(SystemCons.ID.SYSTEM));
			commonLogsPO.setCreate_date(AOSUtils.getDateTime());
			commonLogsPO.setIs_del("0");
			commonLogsPO.setContent(content);
			commonLogsPO.setOper_id(cp_id);
			commonLogsPO.setOper_name("api");
			commonLogsDao.insert(commonLogsPO);
		}
	 public void saveLogs3(String content,String cp_id,String log_type){
			CommonLogsPO commonLogsPO=new CommonLogsPO();
			commonLogsPO.setLog_id(AOSId.appId(SystemCons.ID.SYSTEM));
			commonLogsPO.setCreate_date(AOSUtils.getDateTime());
			commonLogsPO.setIs_del("0");
			commonLogsPO.setContent(content);
			commonLogsPO.setOper_id(cp_id);
			commonLogsPO.setOper_name("api");
			commonLogsPO.setLog_type(log_type);
			commonLogsDao.insert(commonLogsPO);
		}




	public void getDeviceList(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();
	
		String phone = qDto.getString("phone");
		/* System.out.println(phone); */

		Dto pDto = Dtos.newDto();
		pDto.put("phone", phone);
		int rows = deviceDao.rows(pDto);
		pDto.put("limit", rows);// 默认查询出100个

			pDto.put("start", 0);


			/* pDto.put("phone1", phone); */

		/*
		 * pDto.put("phone", phone);
		 * 
		 */

		List<Dto> deviceDtos = sqlDao.list("Device.listDevicesPage", pDto);
		List<Dto> newListDtos = new ArrayList<Dto>();
	
		
		for (Dto dto : deviceDtos) {
			Dto newDto = Dtos.newDto();	
				newDto.put("device_id", getData(dto.getString("device_id")));
			newDto.put("user_id", getData(dto.getString("user_id")));
			newDto.put("user_name", getData(dto.getString("user_name")));
			newDto.put("user_address", getData(dto.getString("user_address")));
			//newDto.put("status",dto.getString("status"));
			newDto.put("arrange_withdraw",getData(dto.getString("arrange_withdraw").equals("布防")?"1":"0"));
				newDto.put("is_alarming", getData(dto.getString("is_alarming")));
			newDto.put("head", getData(dto.getString("head")));
			newDto.put("loc_label", getData(dto.getString("loc_label")));
			newDto.put("head_phone", getData(dto.getString("head_phone")));
			newDto.put("police_station", getData(dto.getString("police_station")));
			newListDtos.add(newDto);

			}

			odto.put("data", newListDtos);
			odto.put("status", "1");
		odto.put("msg", "共查到" + rows + "条数据");


		httpModel.setOutMsg(AOSJson.toJson(odto));
	}
	
	
	public void getDeviceDetails(HttpModel httpModel) throws ParseException {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();

		String device_id = qDto.getString("device_id");
		
		Dto pDto1 = Dtos.newDto("key_", "access_token");
		AosParamsPO aosParamsPO = aosParamsDao.selectOne(pDto1);
		String access_token1 = aosParamsPO.getValue_();

		Dto pDto = Dtos.newDto("device_id", device_id);
		DevicePO devicePO = deviceDao.selectOne(pDto);

			/* List<Dto> newListDtos = new ArrayList<Dto>(); */
		
			Dto newDto = Dtos.newDto();
		newDto.put("device_id", getData(devicePO.getDevice_id()));
			newDto.put("product_type", getData(devicePO.getProduct_type()));
			newDto.put("status", getData(devicePO.getStatus()));
			newDto.put("is_alarming", getData(devicePO.getIs_alarming()));
			newDto.put("production_date", getData(devicePO.getProduction_date()));
			newDto.put("install_date", devicePO.getInstall_date());
			newDto.put("arrange_withdraw", getData(devicePO.getArrange_withdraw().equals("布防")?"1":"0"));

		
		
		newDto.put("guarantee_time", devicePO.getGuarantee_time());
			newDto.put("user_address", getData(devicePO.getUser_address()));
			newDto.put("repair_record", getData(devicePO.getRepair_record()));
			newDto.put("repair_progress", getData(devicePO.getRepair_progress()));


			newDto.put("head", getData(devicePO.getHead()));
			newDto.put("head_phone", getData(devicePO.getHead_phone()));
			newDto.put("police_station", getData(devicePO.getPolice_station()));
		

			
			
			
		List<Dto> newListDtos1 = new ArrayList<Dto>();
			List<Dto> cameraDtos = sqlDao.list("Camera.listcameras", pDto);
			if (null != cameraDtos && !cameraDtos.isEmpty()) {

			for (Dto dto : cameraDtos) {
				Dto newDto1 = Dtos.newDto();
				newDto1.put("access_token", getData(access_token1));
				newDto1.put("camera_serial", getData(dto.getString("camera_serial")));
				newDto1.put("camera_type", getData(dto.getString("camera_type")));
				newDto1.put("verification_code", getData(dto.getString("verification_code")));
			


				newListDtos1.add(newDto1);
			}
			odto.put("data", newDto);
			odto.put("camera", newListDtos1);
			odto.put("status", "1");
			odto.put("msg", "成功");

		} else {
			List<Dto> newListDtos2 = new ArrayList<Dto>();


			Dto newDto2 = Dtos.newDto();
			newDto2.put("access_token", " ");
			newDto2.put("camera_serial", " ");
			newDto2.put("product_type", " ");
			newDto2.put("verification_code", " ");

			newListDtos2.add(newDto2);

			odto.put("data", newDto);
			odto.put("camera", newListDtos2);
			odto.put("status", "1");
			odto.put("msg", "没有绑定摄像头");

			}
		

		httpModel.setOutMsg(AOSJson.toJson(odto));

	}

	public void deviceBinding(HttpModel httpModel) throws ParseException {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();
		
		String deviceType = qDto.getString("deviceType");
		// TODO
		if(deviceType.equals("1"))
		{
			bindModule(httpModel);
		}else if(deviceType.equals("2")) {
			bindOneButtonAlarm(httpModel);
		}else if(deviceType.equals("3")) {
			bindingCamera(httpModel);
		}
	
	}
	
	/**
	 * @param httpModel
	 */
	private void bindOneButtonAlarm(HttpModel httpModel) {
		// TODO Auto-generated method stub
		
		Dto qDto = httpModel.getInDto();
		String deviceInfo = qDto.getString("deviceInfo");
		
		HttpsUtil httpsUtil = new HttpsUtil();
        try {
			httpsUtil.initSSLConfigForTwoWay();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        Dto pDto = Dtos.newDto("key_", "nb_iot_access_token");
		AosParamsPO aosParamsPO = aosParamsDao.selectOne(pDto);
		String access_token = aosParamsPO.getValue_();
        // Authentication锛実et token
        String accessToken = access_token;

        //Please make sure that the following parameter values have been modified in the Constant file.
		String appId = Constant.APPID;
		String urlReg = Constant.REGISTER_DEVICE;

        //please replace the verifyCode and nodeId and timeout, when you use the demo.
        String verifyCode = deviceInfo.split("#")[4];
		String nodeId = verifyCode;
        Integer timeout = 0;

        Map<String, Object> paramReg = new HashMap<>();
        paramReg.put("verifyCode", verifyCode.toUpperCase());
        paramReg.put("nodeId", nodeId.toUpperCase());
        paramReg.put("timeout", timeout);

        String jsonRequest = JsonUtil.jsonObj2Sting(paramReg);

        Map<String, String> header = new HashMap<>();
        header.put(Constant.HEADER_APP_KEY, appId);
        header.put(Constant.HEADER_APP_AUTH, "Bearer" + " " + accessToken);

        StreamClosedHttpResponse responseReg = httpsUtil.doPostJsonGetStatusLine(urlReg, header, jsonRequest);

        System.out.println("RegisterDirectlyConnectedDevice, response content:");
        System.out.print(responseReg.getStatusLine());
        System.out.println(responseReg.getContent());
        System.out.println();
        
        String result = responseReg.getContent();
        JsonObject jsonObject = (JsonObject) new JsonParser().parse(result);
		// 获取accesstoken中的字符串
		String deviceId = jsonObject.get("deviceId").getAsString();
		
        NbIotService.modifyDeviceInfo(deviceId, accessToken);
        
        
        
        
        
     // 数据库操作
        Dto qDto1 = httpModel.getInDto();
		Dto odto = Dtos.newDto();
		
		String phone = qDto1.getString("phone");// phone,即为登陆时的用户手机号,15356002207
		String address = qDto1.getString("address");
		String IMEI = qDto1.getString("IMEI");
		String loc_label = qDto1.getString("loc_label");
		String deviceInfo1 = qDto1.getString("deviceInfo");
		String[] info = deviceInfo1.split("#");
		Dto pDto1=Dtos.newDto("account", phone);
		Basic_userPO basic_userPO=basic_userDao.selectOne(pDto1);   //用于检查账户是否存在
		Dto p1Dto = Dtos.newDto("device_id", info[0]);
		DevicePO devicePO1 = deviceDao.selectOne(p1Dto);
		if(null==basic_userPO){
			this.fail(odto, "手机号输入错误。");
		}else {

			if (null != devicePO1) {
				this.fail(odto, "绑定失败，该设备已被绑定");

			} else {
			String info1 = basic_userPO.getDevice_number();
			info1=info1+ "#" + info[0];
			basic_userPO.setDevice_number(info1);//device_number,代表设备编号
			// TODO
				DevicePO devicePO = new DevicePO();
			devicePO.setId_(info[4]);
			devicePO.setUser_id("");
				devicePO.setPhone(phone);
			devicePO.setUser_address(address);
			devicePO.setUser_type(basic_userPO.getUser_type());
				devicePO.setDevice_id(info[0]);
			devicePO.setProduct_type(info[1]);
			devicePO.setProduction_date(info[2]);
			devicePO.setLoc_label(loc_label);
		
			
			devicePO.setInstall_date(new Date());
			devicePO.setRepair_record("");
			
			Calendar c = Calendar.getInstance();// 可以对每个时间域单独修改
			c.setTime(new Date());
			c.add(Calendar.YEAR, 1);//一年保修
			Date y = c.getTime();
			String guarantee_time = format.format(y);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			try {
				devicePO.setGuarantee_time(sdf.parse(guarantee_time));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			deviceDao.insert(devicePO);
			basic_userDao.updateByKey(basic_userPO);
			// TODO
		
			odto.put("status", "1");
			odto.put("msg", "绑定成功");
			}

		}
		httpModel.setOutMsg(AOSJson.toJson(odto));  
        
	}
	
	public void bindModule(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();
		
		String phone = qDto.getString("phone");// phone,即为登陆时的用户手机号,15356002207
		String address = qDto.getString("address");
		String loc_label = qDto.getString("loc_label");
		String deviceInfo = qDto.getString("deviceInfo");
		String[] info = deviceInfo.split("#");
		Dto pDto=Dtos.newDto("account", phone);
		Basic_userPO basic_userPO=basic_userDao.selectOne(pDto);   //用于检查账户是否存在
		Dto p1Dto = Dtos.newDto("device_id", info[0]);
		DevicePO devicePO1 = deviceDao.selectOne(p1Dto);
		if(null==basic_userPO){
			this.fail(odto, "手机号输入错误。");
		}else {

			if (null != devicePO1) {
				this.fail(odto, "绑定失败，该设备已被绑定");

			} else {
			String info1 = basic_userPO.getDevice_number();
			info1=info1+ "#" + info[0];
			basic_userPO.setDevice_number(info1);//device_number,代表设备编号
			// TODO
				DevicePO devicePO = new DevicePO();
			devicePO.setId_(AOSId.appId(SystemCons.ID.SYSTEM));
			devicePO.setUser_id("");
				devicePO.setPhone(phone);
			devicePO.setUser_address(address);
			devicePO.setUser_type(basic_userPO.getUser_type());
				devicePO.setDevice_id(info[0]);
			devicePO.setProduct_type(info[1]);
			devicePO.setProduction_date(info[2]);
			devicePO.setLoc_label(loc_label);
			devicePO.setArrange_withdraw("撤防");
			devicePO.setShutdown_number("0");
			devicePO.setInstall_date(new Date());
			devicePO.setRepair_record("");
			
			Calendar c = Calendar.getInstance();// 可以对每个时间域单独修改
			c.setTime(new Date());
			c.add(Calendar.YEAR, 1);
			Date y = c.getTime();
			String guarantee_time = format.format(y);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			try {
				devicePO.setGuarantee_time(sdf.parse(guarantee_time));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			deviceDao.insert(devicePO);
			basic_userDao.updateByKey(basic_userPO);
			// TODO
		
			odto.put("status", "1");
			odto.put("msg", "绑定成功");
			}

		}
		httpModel.setOutMsg(AOSJson.toJson(odto));
	}

	public void userLogout(HttpModel httpModel) {
		
		Dto odto = Dtos.newDto();
		odto.put("msg", "登出成功");
		odto.put("status", "1");
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
			this.fail(odto, "手机号输入错误或已被删除，请重新输入。");
		}else if(type.equals("1")) {
			
			String smsCode=qDto.getString("smsCode");
			String smsSessionId=qDto.getString("smsSessionId");
			
			if(AOSUtils.isEmpty(smsCode)){
				this.fail(odto, "smsCode不能为空");
			}else if(AOSUtils.isEmpty(smsSessionId)){
				this.fail(odto, "smsSessionId不能为空");
			}else{
			String sms_Code = cacheMasterDataService.getSMSCode(smsSessionId);
				if (AOSUtils.isEmpty(sms_Code)) {
					this.fail(odto, "验证码已失效!");
				}else if (!sms_Code.equals(smsCode)) {
					this.fail(odto, "验证码不正确!");
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
					this.fail(odto, "密码不正确，请重新输入");
				}else {
					//this.fail(odto, "密码正确");
					odto.put("token", "150e18b7fdf84cb18cb2493b0d354f16");
					odto.put("uid", "f84cb18cb2493b0d354f12");
					odto.put("msg", "登录成功");
					odto.put("status", "1");
					
				}
			}else 
				this.fail(odto, "信息缺失");  //type不为1也不为0!
		
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
			this.fail(dto, "手机号码不能为空");
		}else if(AOSUtils.isEmpty(type)){
			this.fail(dto, "type不能为空");
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
			dto.put("smsSessionId", getData(sessionId));
			dto.put("status", "1");
			this.sendSms(phone, "1", getData(smsCode));
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
			this.fail(odto, "account不能为空");
		}else if(!qDto.containsKey("password")||AOSUtils.isEmpty(password)){
			this.fail(odto, "password不能为空");
		}else if(!qDto.containsKey("sms_code")||AOSUtils.isEmpty(sms_code)){
			this.fail(odto, "sms_code不能为空");
		}else */
		if(!qDto.containsKey("smsSessionId")||AOSUtils.isEmpty(smsSessionId)){
			this.fail(odto, "smsSessionId不能为空");
		}else{
			Dto pDto=Dtos.newDto("account", account);
			pDto.put("is_del_", "0");   //0表示未删除
			//pDto.put("type_", "2");     
			//AosUserPO aosUserPO=aosUserDao.selectOne(pDto);
			Basic_userPO basic_userPO1=basic_userDao.selectOne(pDto);
			String smsCode = cacheMasterDataService.getSMSCode(smsSessionId);
			if(null!=basic_userPO1){
				this.fail(odto, "当前手机号已存在，请重新输入。");
			}else if (AOSUtils.isEmpty(smsCode)) {
				this.fail(odto, "验证码已失效!");
			} else if (!smsCode.equals(sms_code)) {
				this.fail(odto, "验证码不正确!");
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
				odto.put("uid",getData(basic_userPO.getId_()));
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
		//this.fail(odto, new_pwd);
		/*
		if(!qDto.containsKey("mobile")||AOSUtils.isEmpty(mobile)){
			this.fail(odto, "mobile不能为空");
		}else if(!qDto.containsKey("new_pwd")||AOSUtils.isEmpty(new_pwd)){
			this.fail(odto, "new_pwd不能为空");
		}else if(!qDto.containsKey("sms_code")||AOSUtils.isEmpty(sms_code)){
			this.fail(odto, "sms_code不能为空");
		}else if(!qDto.containsKey("confirm_pwd")||AOSUtils.isEmpty(confirm_pwd)){
			this.fail(odto, "confirm_pwd不能为空");
		}else 
		if(!confirm_pwd.equals(new_pwd)){
			this.fail(odto, "新密码与确认密码不一致");
		}else */
		if(!qDto.containsKey("smsSessionId")||AOSUtils.isEmpty(smsSessionId)){
			this.fail(odto, "smsSessionId不能为空");
		}else{
			Dto pDto=Dtos.newDto("account", mobile);
			pDto.put("is_del", "0");
			//pDto.put("type_", "2");
			Basic_userPO basic_userPO=basic_userDao.selectOne(pDto);
			
			if(null==basic_userPO){
				this.fail(odto, "手机号码不存在，请重新输入。");
			}else{
				String smsCode = cacheMasterDataService.getSMSCode(smsSessionId);
				if (AOSUtils.isEmpty(smsCode)) {
					this.fail(odto, "验证码已失效!");
				} else if (!smsCode.equals(sms_code)) {
					this.fail(odto, "验证码不正确!");
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
			this.fail(odto, "手机号输入错误或已被删除，请重新输入。");
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
		restAPI.init(AOSCxt.getParam("sms_url"), AOSCxt.getParam("sms_port"));
		restAPI.setAccount(AOSCxt.getParam("account_sid"), AOSCxt.getParam("auth_token"));
		restAPI.setAppId(AOSCxt.getParam("app_id"));
		//restAPI.init("app.cloopen.com", "8883");
		//restAPI.setAccount("8aaf07086436008b01643c2a00dd05b7", "bcf4e77d8e0b41c88732e51eeb6139db");
		//restAPI.setAppId("8aaf07086436008b01643c2a014105be");
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
	public String getData(String value) {
		if (value == null || value.length() == 0) {
			
			return " ";
		} else {
			return value;
		}

	}
	public Date getDate(Date value) throws ParseException {
		if (value == null || value.toString().length()==0) {
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(" ");
			return date;
		} else {
			return value;
		}

	}
	
	
	public void getAccessToken(HttpModel httpModel) {
		HttpRequester hrq=new HttpRequester(aosParamsDao);
		hrq.runtask();
	}
	


}
