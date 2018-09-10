/**
 * 
 */
package start;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.iotplatform.client.NorthApiClient;
import com.iotplatform.client.NorthApiException;
import com.iotplatform.client.dto.ClientInfo;
import com.iotplatform.utils.PropertyUtil;

import utils.Constant;
import utils.HttpsUtil;
import utils.JsonUtil;
import utils.Request;
import utils.StreamClosedHttpResponse;

/**
 * @author Administrator
 *
 */
public class ConnectPlatform {
	
	public static String serverIP = "180.101.147.89:8743";
	
	public static String appId ="VDU9XeS5QvYZxAgiLp2jK7wvDlQa";
	
	public static String secret = "K1aghh5VRTvbcCzlPqj5unz6cd0a";
	
	public static String getAccessTocken() {
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
	        String manufacturerName = "ZCTC";
	        String deviceType = "OneButtonAlarm";
	        String model = "NBOBA";
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
	
}
