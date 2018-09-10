/**
 * 
 */
package utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import aos.framework.core.id.AOSId;
import aos.framework.dao.AosParamsDao;
import aos.framework.dao.AosParamsPO;
import aos.system.common.utils.SystemCons;


/**
 * @author Administrator
 *
 */
 
public class HttpRequester {
	
	

	private AosParamsDao aosParamsDao;
	public HttpRequester(AosParamsDao aosParamsDao){
		this.aosParamsDao = aosParamsDao;
	}
	
	/**
	 * @param args
	 */
	//每隔六天延时1s后发送一个请求
	public void runtask() {
		 
		Timer time=new Timer();
		MyTask task=new MyTask(aosParamsDao);
		time.scheduleAtFixedRate(task, 1000, 1000*60*60*24*6);
	}

 public class  MyTask extends TimerTask{
     private AosParamsDao aosParamsDao;
//	  
 public MyTask(AosParamsDao aosParamsDao) {
		 this.aosParamsDao = aosParamsDao;
	 }

		 public void run() {
			 String s=Request.sendPost("https://open.ys7.com/api/lapp/token/get","appKey=7f139f95ccab4ad0be82630b443edb0b&appSecret=68309273efbb8cbe20bca9155cb225d5");
			 
			 JsonObject jsonObject = (JsonObject) new JsonParser().parse(s);
			// 获取accesstoken中的字符串
			 final String accessToken = jsonObject.get("data").getAsJsonObject().get("accessToken").getAsString();
			// System.out.println("device_id:" + jsonObject.get("data").getAsJsonObject().get("device_id").getAsString());
			 new Thread() {
				 public void run() {
					 saveAccessToken(accessToken,aosParamsDao);
				 }
			 }.start();
			 
		 }

		/**
		 * @param accessToken
		 */
		//设置base_params数据库中的内容
		private void saveAccessToken(String accessToken,AosParamsDao aosParamsDao)  {
			// TODO Auto-generated method stub
			AosParamsPO aosparamsPO=new AosParamsPO();
			aosparamsPO.setId_(AOSId.appId(SystemCons.ID.SYSTEM));
			aosparamsPO.setKey_("access_token");
			aosparamsPO.setName_("萤石云平台访问令牌");
			aosparamsPO.setValue_(accessToken);
			//aosParamsDao.insert(aosparamsPO);
			//若导入内容为空则插入,若不为空则更新
			AosParamsPO a = aosParamsDao.selectByAccess();
			if(a!=null) {
				aosParamsDao.updateByKey_(aosparamsPO);
			}
			else {
			aosParamsDao.insert(aosparamsPO);
			}
			
			
		}
		
		
  
	 }
}
