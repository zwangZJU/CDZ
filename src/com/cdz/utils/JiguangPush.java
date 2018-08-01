/**
 * 
 */
package utils;

import net.sf.json.JSONObject;
import po.CommonLogsPO;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import sun.misc.BASE64Encoder;

import aos.framework.core.asset.AOSBeanLoader;
import aos.framework.core.id.AOSId;
import aos.framework.core.utils.AOSCxt;
import aos.framework.core.utils.AOSUtils;
import aos.system.common.utils.SystemCons;
import aos.system.modules.cache.CacheUserDataService;
import dao.CommonLogsDao;

import com.alibaba.fastjson.JSONArray;

/**
 * @author Administrator
 *
 */
public class JiguangPush {
	    private static Logger logger = LoggerFactory.getLogger(JiguangPush.class);
	    /**
	     * 极光推送
	     */
		private static CommonLogsDao commonLogsDao = (CommonLogsDao)AOSBeanLoader.getSpringBean("commonLogsDao");
	    public void jiguangPush(){
	       
	    }
	    public void sendMsg(String alias,String content,String appKey,String masterSecret,String pushUrl){
		        try{
		        	String apns_production=AOSCxt.getParam("apns_production");
		            String result = push(pushUrl,alias,content,appKey,masterSecret,Boolean.valueOf(apns_production),86400);
		            JSONObject resData = JSONObject.fromObject(result);
		                if(resData.containsKey("error")){
		                	logger.info("针对registration_id为" + alias + "的信息推送失败！apns_production:"+apns_production);
		                	this.saveLogs("针对registration_id为" + alias + "的信息推送失败！apns_production:"+apns_production);
		                    JSONObject error = JSONObject.fromObject(resData.get("error"));
		                    logger.info("错误信息为：" + error.get("message").toString());
		                }else{
		                	logger.info("针对registration_id为" + alias + "的信息推送成功！apns_production:"+apns_production);
		                	this.saveLogs("针对registration_id为" + alias + "的信息推送成功！apns_production:"+apns_production);
		                }
		                
		        }catch(Exception e){
		        	logger.error("针对registration_id为" + alias + "的信息推送失败！",e);
		        	this.saveLogs("针对registration_id为" + alias + "的信息推送失败！");
		        }
	    }
	    /**
	     * 组装极光推送专用json串
	     * @param alias
	     * @param alert
	     * @return json
	     */
	    public static JSONObject generateJson(String alias,String alert,boolean apns_production,int time_to_live){
	        JSONObject json = new JSONObject();
	        JSONArray platform = new JSONArray();//平台
	        platform.add("android");
	        platform.add("ios");
	        
	        JSONObject audience = new JSONObject();//推送目标
	        JSONArray alias1 = new JSONArray();
	        alias1.add(alias);
	        audience.put("registration_id", alias1);
	        
	        JSONObject notification = new JSONObject();//通知内容
	        JSONObject android = new JSONObject();//android通知内容
	        android.put("alert", alert);
	        android.put("builder_id", 1);
	        JSONObject android_extras = new JSONObject();//android额外参数
	        android_extras.put("type", "infomation");
	        android.put("extras", android_extras);
	        
	        JSONObject ios = new JSONObject();//ios通知内容
	        ios.put("alert", alert);
	        ios.put("sound", "default");
	        ios.put("badge", "+1");
	        JSONObject ios_extras = new JSONObject();//ios额外参数
	        ios_extras.put("type", "infomation");
	        ios.put("extras", ios_extras);
	        notification.put("android", android);
	        notification.put("ios", ios);
	        
	        JSONObject options = new JSONObject();//设置参数
	        options.put("time_to_live", Integer.valueOf(time_to_live));
	        options.put("apns_production", apns_production);// IOS True 表示推送生产环境,False 表示要推送开发环境
	        
	        json.put("platform", platform);
	        json.put("audience", audience);
	        json.put("notification", notification);
	        json.put("options", options);
	        return json;
	        
	    }
	    
	    /**
	     * 推送方法-调用极光API
	     * @param reqUrl
	     * @param alias
	     * @param alert
	     * @return result
	     */
	    public static String push(String reqUrl,String alias,String alert,String appKey,String masterSecret,boolean apns_production,int time_to_live){
	        String base64_auth_string = encryptBASE64(appKey + ":" + masterSecret);
	        String authorization = "Basic " + base64_auth_string;
	        return sendPostRequest(reqUrl,generateJson(alias,alert,apns_production,time_to_live).toString(),"UTF-8",authorization);
	    }
	    
	    /**
	     * 发送Post请求（json格式）
	     * @param reqURL
	     * @param data
	     * @param encodeCharset
	     * @param authorization
	     * @return result
	     */
	    @SuppressWarnings({ "resource" })
	    public static String sendPostRequest(String reqURL, String data, String encodeCharset,String authorization){
	        HttpPost httpPost = new HttpPost(reqURL);
	        HttpClient client = new DefaultHttpClient();
	        HttpResponse response = null;
	        String result = "";
	        try {
	             StringEntity entity = new StringEntity(data, encodeCharset);
	             entity.setContentType("application/json");
	             httpPost.setEntity(entity);
	             httpPost.setHeader("Authorization",authorization.trim());
	             response = client.execute(httpPost);
	             result = EntityUtils.toString(response.getEntity(), encodeCharset);
	        } catch (Exception e) {
	        	logger.error("请求通信[" + reqURL + "]时偶遇异常,堆栈轨迹如下", e);  
	        }finally{
	            client.getConnectionManager().shutdown();
	        }
	        return result;
	    }
	     /** 
	　　　　* BASE64加密工具
	　　　　*/
	     public static String encryptBASE64(String str) {
	         byte[] key = str.getBytes();
	       BASE64Encoder base64Encoder = new BASE64Encoder();
	       String strs = base64Encoder.encodeBuffer(key);
	         return strs;
	     }
	     private void saveLogs(String content){
	 		CommonLogsPO commonLogsPO=new CommonLogsPO();
	 		commonLogsPO.setLog_id(AOSId.appId(SystemCons.ID.SYSTEM));
	 		commonLogsPO.setCreate_date(AOSUtils.getDateTime());
	 		commonLogsPO.setIs_del("0");
	 		commonLogsPO.setOper_id("接口");
	 		commonLogsPO.setContent(content);
	 		commonLogsDao.insert(commonLogsPO);
	 	}
}
