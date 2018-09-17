/**
 * 
 */
package service;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

 
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iotplatform.client.NorthApiException;

import aos.framework.core.id.AOSId;
import aos.framework.core.service.CDZBaseController;
import aos.framework.core.typewrap.Dto;
import aos.framework.core.typewrap.Dtos;
import aos.framework.dao.AosParamsDao;
import aos.framework.dao.AosParamsPO;
import aos.framework.web.router.HttpModel;
import aos.system.common.utils.SystemCons;
import dao.Alarm_descDao;
import dao.Alarm_logDao;
import dao.DeviceDao;
import po.Alarm_descPO;
import po.Alarm_logPO;
import po.DevicePO;
import utils.Constant;
import utils.HttpsUtil;
import utils.JsonUtil;
import utils.Request;
import utils.StreamClosedHttpResponse;

/**
 * @author wzlab
 *
 */
@Service
public class NbIotService  {

	@Autowired
	AosParamsDao aosParamsDao;
	
	@Autowired
	DeviceDao deviceDao;
	
	@Autowired
	Alarm_logDao alarm_logDao;
	
	@Autowired
	Alarm_descDao alarm_descDao;
	
	public static String serverIP = "180.101.147.89:8743";
	
	public static String appId ="VDU9XeS5QvYZxAgiLp2jK7wvDlQa";
	
	public static String secret = "K1aghh5VRTvbcCzlPqj5unz6cd0a";
	
	public static String nbAccessToken = "";
	public static String accessToken= "";
	public static String accessToken1= "";
	public static StreamClosedHttpResponse bodyQuery;
	@SuppressWarnings("resource")
    public static void queryScribe(HttpModel httpModel) {
		
		 HttpsUtil httpsUtil = new HttpsUtil();
	        try {
				httpsUtil.initSSLConfigForTwoWay();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

	        // Authentication锛実et token
	      
			try {
				accessToken1 = login(httpsUtil);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

	        //Please make sure that the following parameter values have been modified in the Constant file.
			String appId = Constant.APPID;

	        //please replace the deviceId, when you use the demo.
	       // String deviceId = "9a445dda-f62e-4c78-be05-ef0f0c1b447a";
	        String queryurl = Constant.urlQuery;
	        		
	        Map<String, String> header = new HashMap<>();
	        header.put(Constant.HEADER_APP_KEY, appId);
	        header.put(Constant.HEADER_APP_AUTH, "Bearer" + " " + accessToken1);

	       
			try {
				bodyQuery = httpsUtil.doGetWithParasGetStatusLine(
				        queryurl, null, header);
				System.out.println("queryresult:"+bodyQuery.getContent());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	


	        System.out.println("QueryDeviceActivationStatus, response content:");
	       
		
		
	}
	
	
	
	@SuppressWarnings("resource")
    public static void scribe(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		

		
		String notifyType = qDto.getString("function_");
		String callbackurl = qDto.getString("url"); // please replace the IP and Port of BASE_URL, when you use the demo.
	        
	    //SimpleHttpServer.startServer(8888); // start server to receive message

        // Two-Way Authentication
        HttpsUtil httpsUtil = new HttpsUtil();
        try {
			httpsUtil.initSSLConfigForTwoWay();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		
		try {
			accessToken = login(httpsUtil);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String appId = Constant.APPID; // please replace the appId, when you use the demo.
        String urlSubscribe = Constant.SUBSCRIBE_NOTIFYCATION; // please replace the IP and Port of BASE_URL, when you use the demo.

       
        /*
         * na to subscribe notification from the IoT platform
         * notifyTypes: 
         * bindDevice/serviceInfoChanged/deviceInfoChanged/deviceDataChanged/deviceAdded/deviceDeleted
         * messageConfirm/commandRsp/deviceEvent/appDeleted/ruleEvent/deviceDatasChanged
         */
        List<String> notifyTypes = NotifyType.getNotifyTypes();
      
            
            Map<String, Object> paramSubscribe = new HashMap<>();
            paramSubscribe.put("notifyType", notifyType);
            paramSubscribe.put("callbackurl", callbackurl);
            
            String jsonRequest = JsonUtil.jsonObj2Sting(paramSubscribe);
            
            Map<String, String> header = new HashMap<>();
            header.put(Constant.HEADER_APP_KEY, appId);
            header.put(Constant.HEADER_APP_AUTH, "Bearer" + " " + accessToken);
            
            HttpResponse httpResponse = httpsUtil.doPostJson(urlSubscribe, header, jsonRequest);
            
            String bodySubscribe;
			try {
				bodySubscribe = httpsUtil.getHttpResponseBody(httpResponse);
			} catch (UnsupportedOperationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            System.out.println("SubscribeNotification, notifyType:" + notifyType + ", callbackurl:" + callbackurl +", response content:");
            System.out.print(httpResponse.getStatusLine());
           
            
        
    }

    /**
     * Authentication锛実et token
     * */
    @SuppressWarnings("unchecked")
    public static String login(HttpsUtil httpsUtil) throws Exception {

        String appId = Constant.APPID;
        String secret = Constant.SECRET;
        String urlLogin = Constant.APP_AUTH;

        Map<String, String> paramLogin = new HashMap<>();
        paramLogin.put("appId", appId);
        paramLogin.put("secret", secret);

        StreamClosedHttpResponse responseLogin = httpsUtil.doPostFormUrlEncodedGetStatusLine(urlLogin, paramLogin);

        System.out.println("app auth success,return accessToken:");
        System.out.print(responseLogin.getStatusLine());
        System.out.println(responseLogin.getContent());
        System.out.println();

        Map<String, String> data = new HashMap<>();
        data = JsonUtil.jsonString2SimpleObj(responseLogin.getContent(), data.getClass());
        return data.get("accessToken");
    }

	
	
	
	

	
	
	public static String getAccessTocken(HttpModel httpModel) {
		String result = Request.sendPost("https://"+serverIP+"/iocm/app/sec/v1.1.0/login"
				, "appId="+appId+"&secret="+secret);
		
		 JsonObject jsonObject = (JsonObject) new JsonParser().parse(result);
			// 获取accesstoken中的字符串
		String accessToken = jsonObject.get("Body").getAsJsonObject().get("accessToken").getAsString();
		
		return accessToken;
	}
	
	public static String initPlatform() throws NorthApiException
    {    	
		 HttpsUtil httpsUtil = new HttpsUtil();
	        try {
				httpsUtil.initSSLConfigForTwoWay();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

	        String appId = Constant.APPID;
	        String secret = Constant.SECRET;
	        String urlLogin = Constant.APP_AUTH;
	        String accessToken = null;

	        Map<String, String> param = new HashMap<>();
	        param.put("appId", appId);
	        param.put("secret", secret);

	        StreamClosedHttpResponse responseLogin;
			try {
				responseLogin = httpsUtil.doPostFormUrlEncodedGetStatusLine(urlLogin, param);
				System.out.println("app auth success,return accessToken:");
		        System.out.print(responseLogin.getStatusLine());
		        System.out.println(responseLogin.getContent());
		        System.out.println();

		        //resolve the value of accessToken from responseLogin.
		        Map<String, String> data = new HashMap<>();
		        data = JsonUtil.jsonString2SimpleObj(responseLogin.getContent(), data.getClass());
		        accessToken = data.get("accessToken");
		        System.out.println("accessToken:" + accessToken);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			nbAccessToken = accessToken;
	     return accessToken;  


    }
	
	public static void modifyDeviceInfo(String deviceId,String accessToken) {
		 HttpsUtil httpsUtil = new HttpsUtil();
	        try {
				httpsUtil.initSSLConfigForTwoWay();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	        // Authentication锛実et token

	        //Please make sure that the following parameter values have been modified in the Constant file.
			String appId = Constant.APPID;

	        //please replace the deviceId, when you use the demo.
	        String urlModifyDeviceInfo = Constant.MODIFY_DEVICE_INFO + "/" + deviceId;

	        //please replace the following parameter values, when you use the demo.
	        //And those parameter values must be consistent with the content of profile that have been preset to IoT platform.
	        //The following parameter values of this demo are use the watermeter profile that already initialized to IoT platform.
	        String manufacturerId= "zctc";
	        String manufacturerName = "ZhejiangZhongchuangTianchengTechnologyCoLtd";
	        String deviceType = "OneButtonAlarm";
	        String model = "ZC-SAOBA";
	        String protocolType = "CoAP";
	        String location = "LA";
	        

	        Map<String, Object> paramModifyDeviceInfo = new HashMap<>();
	        paramModifyDeviceInfo.put("manufacturerId", manufacturerId);
	        paramModifyDeviceInfo.put("manufacturerName", manufacturerName);
	        paramModifyDeviceInfo.put("deviceType", deviceType);
	        paramModifyDeviceInfo.put("model", model);
	        paramModifyDeviceInfo.put("protocolType", protocolType);
	        paramModifyDeviceInfo.put("location", location);

	        String jsonRequest = JsonUtil.jsonObj2Sting(paramModifyDeviceInfo);

	        Map<String, String> header = new HashMap<>();
	        header.put(Constant.HEADER_APP_KEY, appId);
	        header.put(Constant.HEADER_APP_AUTH, "Bearer" + " " + accessToken);

	        StreamClosedHttpResponse responseModifyDeviceInfo = httpsUtil.doPutJsonGetStatusLine(urlModifyDeviceInfo,
	                header, jsonRequest);

	        System.out.println("ModifyDeviceInfo, response content:");
	        System.out.print(responseModifyDeviceInfo.getStatusLine());
	        System.out.println(responseModifyDeviceInfo.getContent());
	        System.out.println();
	}
	
	public void getNBIoTAccessToken(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		String token=qDto.getString("accessToken");
		
			AosParamsPO aosparamsPO=new AosParamsPO();
			aosparamsPO.setId_(AOSId.appId(SystemCons.ID.SYSTEM));
			aosparamsPO.setKey_("nb_iot_access_token");
			aosparamsPO.setName_("NBIoT平台访问令牌");
			aosparamsPO.setValue_(token);
			//aosParamsDao.insert(aosparamsPO);
			//若导入内容为空则插入,若不为空则更新
			AosParamsPO a = aosParamsDao.selectByNBIoTAccess();
			if(a!=null) {
				aosParamsDao.updateByKey_(aosparamsPO);
			}
			else {
			aosParamsDao.insert(aosparamsPO);
			}
  
		
	}
	
	public void deviceAddedCallback(HttpModel httpModel) {
		System.out.println(httpModel.toString());
		httpModel.setOutMsg("ok");
	}
	
	public void deviceInfoChangedCallback(HttpModel httpModel) {
		System.out.println(httpModel.toString());
		httpModel.setOutMsg("ok");
	}
	
	public void deviceDatasChangedCallback(HttpModel httpModel) {
		System.out.println(httpModel.toString());
		httpModel.setOutMsg("ok");
	}
	public void deviceDeletedCallback(HttpModel httpModel) {
		System.out.println(httpModel.toString());
		httpModel.setOutMsg("ok");
	}
	
	public void messageConfirmCallback(HttpModel httpModel) {
		System.out.println(httpModel.toString());
		httpModel.setOutMsg("ok");
	}
	
	public void commandRspCallback(HttpModel httpModel) {
		System.out.println(httpModel.toString());
		httpModel.setOutMsg("ok");
	}
	
	public void deviceEventCallback(HttpModel httpModel) {
		System.out.println(httpModel.toString());
		httpModel.setOutMsg("ok");
	}
	
	public void serviceInfoChangedCallback(HttpModel httpModel) {
		System.out.println(httpModel.toString());
		httpModel.setOutMsg("ok");
	}
	
	public void ruleEvent(HttpModel httpModel) {
		System.out.println(httpModel.toString());
		httpModel.setOutMsg("ok");
	}
	public void deviceModelAddedCallback(HttpModel httpModel) {
		System.out.println(httpModel.toString());
		httpModel.setOutMsg("ok");
	}
	
	public void deviceModelDeletedCallback(HttpModel httpModel) {
		System.out.println(httpModel.toString());
		httpModel.setOutMsg("ok");
	}
	
	
	
	
	public void deviceDataChangedCallback(HttpModel httpModel) {
		ServletInputStream ris;
		
		try {
			ris = httpModel.getRequest().getInputStream();
			StringBuilder content = new StringBuilder();
			byte[] b = new byte[1024];
			int lens = -1;
			while ((lens = ris.read(b)) > 0) {
				content.append(new String(b, 0, lens));
			}
			String strcont = content.toString();

			System.out.println(strcont);
			JsonObject jsonObject = (JsonObject) new JsonParser().parse(strcont);
			 
			JsonObject data = jsonObject.get("service").getAsJsonObject().get("data").getAsJsonObject();
			String IMEI = data.get("IMEI").getAsString();  //机器码，对应device表里的id_
			String Alarm_ER = data.get("Alarm_ER").getAsString();   //触发\恢复状态
			String Alarm_Code = data.get("Alarm_Code").getAsString();   //警情代号
			String Alarm_Zone = data.get("Alarm_Zone").getAsString();   //防区号
			String Voltage = data.get("Voltage").getAsString();    //电池电压
			String Signal = data.get("Signal").getAsString();     //信号质量
			String State = data.get("State").getAsString();      //模块的撤布防状态
			
    		String str_EEE = Alarm_Code;  //警情代码
    		String str_CCC = Alarm_Zone;   //防区号
    		//NumberFormat nf = NumberFormat.getPercentInstance();
			
    		Dto pDto = Dtos.newDto("id_", IMEI);
 //   		DevicePO devicePO = deviceDao.selectOne(pDto);
		//	devicePO.setTrigger_("触发");
//			devicePO.setIs_alarming("1");
//			devicePO.setSignal_quality(String.valueOf((Integer.parseInt(Signal)+1.0)/32.0));
//			deviceDao.updateByKey(devicePO);
//			
//			Dto pDto3 = Dtos.newDto("eee", str_EEE);
//			Alarm_descPO alarm_descPO = alarm_descDao.selectOne(pDto3);
//			
//			Alarm_logPO alarm_logPO = new Alarm_logPO();   
//			alarm_logPO.setAlarm_id(AOSId.appId(SystemCons.ID.SYSTEM));
//			alarm_logPO.setDevice_id(devicePO.getDevice_id());
//			alarm_logPO.setUser_phone(devicePO.getPhone());
//			alarm_logPO.setAlarm_time(new Date());
//			alarm_logPO.setReason_(alarm_descPO.getAlarm_type());
//			alarm_logPO.setAlert_code(str_EEE);  //警情代码
//			alarm_logPO.setProcess("0");  //是否接警
//			alarm_logPO.setDefence_area(str_CCC);  //防区号
//			alarm_logPO.setType_("0");  //报警类型
//			alarm_logPO.setHandler_(devicePO.getHead());  //负责人名字
//			alarm_logPO.setHandler_phone(devicePO.getHead_phone());  //负责人手机号
//			
//			//加CCC和GG
//			alarm_logDao.insert(alarm_logPO);
			
		 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
//		String s1 = httpModel.getRequest().getParameter("deviceId");
//		Dto qDto = httpModel.getInDto();
//		System.out.println(s1);
//		String body= qDto.toString();
//		System.out.println(body);
//		 
//		
//		JsonObject jsonObject = (JsonObject) new JsonParser().parse(body);
//		// 获取accesstoken中的字符串
//		 
//		String deviceId = jsonObject.get("deviceId").getAsString();
//			
		//System.out.println(deviceId);
		httpModel.setOutMsg1("ok");
	//操作数据库
		
		
		/*Dto pDto4 = Dtos.newDto("device_id", device_id);
		DevicePO devicePO3 = deviceDao.selectOne(pDto4);
		Alarm_logPO alarm_logPO = new Alarm_logPO();   
		alarm_logPO.setAlarm_id(AOSId.appId(SystemCons.ID.SYSTEM));
		alarm_logPO.setDevice_id(device_id);
		alarm_logPO.setUser_phone(devicePO3.getPhone());
		alarm_logPO.setAlarm_time(new Date());
		alarm_logPO.setReason_(alarm_descPO.getAlarm_type());
		alarm_logPO.setAlert_code(str_EEE);  //警情代码
		alarm_logPO.setProcess("0");  //是否接警
		alarm_logPO.setDefence_area(str_CCC);  //防区号
		alarm_logPO.setType_("0");  //报警类型
		alarm_logPO.setHandler_(devicePO3.getHead());  //负责人名字
		alarm_logPO.setHandler_phone(devicePO3.getHead_phone());  //负责人手机号
		
		//加CCC和GG
		alarm_logDao.insert(alarm_logPO);*/
	}
	
	
	
}
