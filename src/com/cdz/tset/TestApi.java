/**
 * 
 */
package tset;

import java.io.IOException;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import aos.framework.web.httpclient.AOSHttpClient;
import aos.framework.web.httpclient.HttpRequestVO;
import aos.framework.web.httpclient.HttpResponseVO;

/**
 * @author Administrator
 *
 */
public class TestApi {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {

	}
	@Test
	public  void  getArticle(){
		HttpRequestVO httpRequestVO = new HttpRequestVO("http://116.62.215.62:80/cdz/api/do.jhtml?router=appApiService.getArticle");
		 Map<String,String> paramMap =new HashMap<String,String>();
		 paramMap.put("type", "0");
	     httpRequestVO.setParamMap(paramMap);
	     HttpResponseVO httpResponseVO = AOSHttpClient.execute(httpRequestVO);
	     System.out.println("HTTP状态码：" + httpResponseVO.getStatus());
	     System.out.println("返回值：" + httpResponseVO.getOut());
		
	}
	@Test
	public  void  getAdvertList(){
		HttpRequestVO httpRequestVO = new HttpRequestVO("http://116.62.215.62:80/cdz/api/do.jhtml?router=appApiService.getAdvertList");
		 Map<String,String> paramMap =new HashMap<String,String>();
	     httpRequestVO.setParamMap(paramMap);
	     HttpResponseVO httpResponseVO = AOSHttpClient.execute(httpRequestVO);
	     System.out.println("HTTP状态码：" + httpResponseVO.getStatus());
	     System.out.println("返回值：" + httpResponseVO.getOut());
		
	}
	@Test
	public  void  addAdvertTraffic(){
		HttpRequestVO httpRequestVO = new HttpRequestVO("http://116.62.215.62:80/cdz/api/do.jhtml?router=appApiService.addAdvertTraffic");
		 Map<String,String> paramMap =new HashMap<String,String>();
		 paramMap.put("advert_id", "0");
		 paramMap.put("at_type", "0");
	     httpRequestVO.setParamMap(paramMap);
	     HttpResponseVO httpResponseVO = AOSHttpClient.execute(httpRequestVO);
	     System.out.println("HTTP状态码：" + httpResponseVO.getStatus());
	     System.out.println("返回值：" + httpResponseVO.getOut());
		
	}
	@Test
	public  void  addPayment(){
		HttpRequestVO httpRequestVO = new HttpRequestVO("http://116.62.215.62:80/cdz/api/do.jhtml?router=appApiService.addPayment");
		 Map<String,String> paramMap =new HashMap<String,String>();
		 paramMap.put("pay_source", "0");
		 paramMap.put("juid", "0253bd13c574416a9a9ad60dcd14c1bf");
		 paramMap.put("pay_amt", "28.8");
		 paramMap.put("co_id", "1710191653422580");
		 paramMap.put("voucher_id", "0");
	     httpRequestVO.setParamMap(paramMap);
	     HttpResponseVO httpResponseVO = AOSHttpClient.execute(httpRequestVO);
	     System.out.println("HTTP状态码：" + httpResponseVO.getStatus());
	     System.out.println("返回值：" + httpResponseVO.getOut());
		
	}
	@Test
	public  void  loginUser(){
		HttpRequestVO httpRequestVO = new HttpRequestVO("http://116.62.215.62:80/cdz/api/do.jhtml?router=appApiService.loginUser");
		 Map<String,String> paramMap =new HashMap<String,String>();
		 paramMap.put("account", "13128608283");
		 paramMap.put("password", "123456");
	     httpRequestVO.setParamMap(paramMap);
	     HttpResponseVO httpResponseVO = AOSHttpClient.execute(httpRequestVO);
	     System.out.println("HTTP状态码：" + httpResponseVO.getStatus());
	     System.out.println("返回值：" + httpResponseVO.getOut());
		
	}//
	@Test
	public  void  getUserInfo(){
		HttpRequestVO httpRequestVO = new HttpRequestVO("http://116.62.215.62:80/cdz/api/do.jhtml?router=appApiService.getUserInfo");
		 Map<String,String> paramMap =new HashMap<String,String>();
		 paramMap.put("juid", "e77ad463049e46c2a6909963ef8b3eec");
	     httpRequestVO.setParamMap(paramMap);
	     HttpResponseVO httpResponseVO = AOSHttpClient.execute(httpRequestVO);
	     System.out.println("HTTP状态码：" + httpResponseVO.getStatus());
	     System.out.println("返回值：" + httpResponseVO.getOut());
		
	}
	@Test
	public  void  getChargingPileList(){
		HttpRequestVO httpRequestVO = new HttpRequestVO("http://116.62.215.62:80/cdz/api/do.jhtml?router=appApiService.getChargingPileList");
		 Map<String,String> paramMap =new HashMap<String,String>();
		 paramMap.put("lon", "113.331575");
		 paramMap.put("lat", "23.143232");
	     httpRequestVO.setParamMap(paramMap);
	     HttpResponseVO httpResponseVO = AOSHttpClient.execute(httpRequestVO);
	     System.out.println("HTTP状态码：" + httpResponseVO.getStatus());
	     System.out.println("返回值：" + httpResponseVO.getOut());
		
	}
	@Test
	public  void  getChargingPileById(){
		HttpRequestVO httpRequestVO = new HttpRequestVO("http://116.62.215.62:80/cdz/api/do.jhtml?router=appApiService.getChargingPileById");
		 Map<String,String> paramMap =new HashMap<String,String>();
		 paramMap.put("cp_id", "1706201012030856");
		 paramMap.put("lon", "113.331575");
		 paramMap.put("lat", "23.143232");
	     httpRequestVO.setParamMap(paramMap);
	     HttpResponseVO httpResponseVO = AOSHttpClient.execute(httpRequestVO);
	     System.out.println("HTTP状态码：" + httpResponseVO.getStatus());
	     System.out.println("返回值：" + httpResponseVO.getOut());
		
	}
	@Test
	public  void  getMyChargingList(){
		HttpRequestVO httpRequestVO = new HttpRequestVO("http://116.62.215.62:80/cdz/api/do.jhtml?router=appApiService.getMyChargingList");
		 Map<String,String> paramMap =new HashMap<String,String>();
		 paramMap.put("juid", "d2483385c2844471bfa1a6772cbaf614");
		 paramMap.put("limit", "-1");
		 paramMap.put("start", "-1");
	     httpRequestVO.setParamMap(paramMap);
	     HttpResponseVO httpResponseVO = AOSHttpClient.execute(httpRequestVO);
	     System.out.println("HTTP状态码：" + httpResponseVO.getStatus());
	     System.out.println("返回值：" + httpResponseVO.getOut());
		
	}
	@Test
	public  void  getMyChargingById(){
		HttpRequestVO httpRequestVO = new HttpRequestVO("http://116.62.215.62:80/cdz/api/do.jhtml?router=appApiService.getMyChargingById");
		 Map<String,String> paramMap =new HashMap<String,String>();
		 paramMap.put("juid", "d2483385c2844471bfa1a6772cbaf614");
		 paramMap.put("co_id", "1704271854270030");
	     httpRequestVO.setParamMap(paramMap);
	     HttpResponseVO httpResponseVO = AOSHttpClient.execute(httpRequestVO);
	     System.out.println("HTTP状态码：" + httpResponseVO.getStatus());
	     System.out.println("返回值：" + httpResponseVO.getOut());
		
	}
	@Test
	public  void  getMyVoucherList(){
		HttpRequestVO httpRequestVO = new HttpRequestVO("http://116.62.215.62:80/cdz/api/do.jhtml?router=appApiService.getMyVoucherList");
		 Map<String,String> paramMap =new HashMap<String,String>();
		 paramMap.put("juid", "d2483385c2844471bfa1a6772cbaf614");
		 paramMap.put("limit", "-1");
		 paramMap.put("start", "-1");
	     httpRequestVO.setParamMap(paramMap);
	     HttpResponseVO httpResponseVO = AOSHttpClient.execute(httpRequestVO);
	     System.out.println("HTTP状态码：" + httpResponseVO.getStatus());
	     System.out.println("返回值：" + httpResponseVO.getOut());
		
	}
	@Test
	public  void  getMyMessagesList(){
		HttpRequestVO httpRequestVO = new HttpRequestVO("http://116.62.215.62:80/cdz/api/do.jhtml?router=appApiService.getMyMessagesList");
		 Map<String,String> paramMap =new HashMap<String,String>();
		 paramMap.put("juid", "d2483385c2844471bfa1a6772cbaf614");
		 paramMap.put("limit", "-1");
		 paramMap.put("start", "-1");
	     httpRequestVO.setParamMap(paramMap);
	     HttpResponseVO httpResponseVO = AOSHttpClient.execute(httpRequestVO);
	     System.out.println("HTTP状态码：" + httpResponseVO.getStatus());
	     System.out.println("返回值：" + httpResponseVO.getOut());
		
	}
	
	@Test
	public  void  getMyAdviceList(){
		HttpRequestVO httpRequestVO = new HttpRequestVO("http://116.62.215.62:80/cdz/api/do.jhtml?router=appApiService.getMyAdviceList");
		 Map<String,String> paramMap =new HashMap<String,String>();
		 paramMap.put("juid", "adeb4c6305ad43db89135fedbdb86eab");
		 paramMap.put("limit", "-1");
		 paramMap.put("start", "-1");
	     httpRequestVO.setParamMap(paramMap);
	     HttpResponseVO httpResponseVO = AOSHttpClient.execute(httpRequestVO);
	     System.out.println("HTTP状态码：" + httpResponseVO.getStatus());
	     System.out.println("返回值：" + httpResponseVO.getOut());
		
	}//
	@Test
	public  void  getApplyDepositList(){
		HttpRequestVO httpRequestVO = new HttpRequestVO("http://116.62.215.62:80/cdz/api/do.jhtml?router=appApiService.getApplyDepositList");
		 Map<String,String> paramMap =new HashMap<String,String>();
		 paramMap.put("juid", "c3ab3740b9eb423c8c19b21955139e86");
		 paramMap.put("limit", "-1");
		 paramMap.put("start", "-1");
	     httpRequestVO.setParamMap(paramMap);
	     HttpResponseVO httpResponseVO = AOSHttpClient.execute(httpRequestVO);
	     System.out.println("HTTP状态码：" + httpResponseVO.getStatus());
	     System.out.println("返回值：" + httpResponseVO.getOut());
		
	}
	@Test
	public  void  applyDeposit(){
		HttpRequestVO httpRequestVO = new HttpRequestVO("http://116.62.215.62:80/cdz/api/do.jhtml?router=appApiService.applyDeposit");
		 Map<String,String> paramMap =new HashMap<String,String>();
		 paramMap.put("juid", "8a7eb6e7558345f3a9f5c65bb6ce9e63");
	     httpRequestVO.setParamMap(paramMap);
	     HttpResponseVO httpResponseVO = AOSHttpClient.execute(httpRequestVO);
	     System.out.println("HTTP状态码：" + httpResponseVO.getStatus());
	     System.out.println("返回值：" + httpResponseVO.getOut());
		
	}
	@Test
	public  void  applyBalance(){
		HttpRequestVO httpRequestVO = new HttpRequestVO("http://116.62.215.62:80/cdz/api/do.jhtml?router=appApiService.applyBalance");
		 Map<String,String> paramMap =new HashMap<String,String>();
		 paramMap.put("juid", "8a4b0cf6864b4a4da4ff046a3c8d6716");
	     httpRequestVO.setParamMap(paramMap);
	     HttpResponseVO httpResponseVO = AOSHttpClient.execute(httpRequestVO);
	     System.out.println("HTTP状态码：" + httpResponseVO.getStatus());
	     System.out.println("返回值：" + httpResponseVO.getOut());
		
	}
	@Test
	public  void  addSubscribe(){
		HttpRequestVO httpRequestVO = new HttpRequestVO("http://116.62.215.62:80/cdz/api/do.jhtml?router=appApiService.addSubscribe");
		 Map<String,String> paramMap =new HashMap<String,String>();
		 paramMap.put("juid", "195feec6a061485da24c7316fcfb4150");
		 paramMap.put("cp_id", "1706201012030853");
	     httpRequestVO.setParamMap(paramMap);
	     HttpResponseVO httpResponseVO = AOSHttpClient.execute(httpRequestVO);
	     System.out.println("HTTP状态码：" + httpResponseVO.getStatus());
	     System.out.println("返回值：" + httpResponseVO.getOut());
		
	}
	@Test
	public  void  cancelSubscribe(){
		HttpRequestVO httpRequestVO = new HttpRequestVO("http://116.62.215.62:80/cdz/api/do.jhtml?router=appApiService.cancelSubscribe");
		 Map<String,String> paramMap =new HashMap<String,String>();
		 paramMap.put("juid", "195feec6a061485da24c7316fcfb4150");
	     httpRequestVO.setParamMap(paramMap);
	     HttpResponseVO httpResponseVO = AOSHttpClient.execute(httpRequestVO);
	     System.out.println("HTTP状态码：" + httpResponseVO.getStatus());
	     System.out.println("返回值：" + httpResponseVO.getOut());
		
	}
	@Test
	public  void  getSubscribe(){
		HttpRequestVO httpRequestVO = new HttpRequestVO("http://116.62.215.62:80/cdz/api/do.jhtml?router=appApiService.getSubscribe");
		 Map<String,String> paramMap =new HashMap<String,String>();
		 paramMap.put("juid", "5c962dbfac524f478fad0e8684669f8a");
		 paramMap.put("cp_id", "1706201012030857");
	     httpRequestVO.setParamMap(paramMap);
	     HttpResponseVO httpResponseVO = AOSHttpClient.execute(httpRequestVO);
	     System.out.println("HTTP状态码：" + httpResponseVO.getStatus());
	     System.out.println("返回值：" + httpResponseVO.getOut());
		
	}
	@Test
	public  void  checkSubscribe(){
		HttpRequestVO httpRequestVO = new HttpRequestVO("http://116.62.215.62:80/cdz/api/do.jhtml?router=appApiService.checkSubscribe");
		 Map<String,String> paramMap =new HashMap<String,String>();
		 paramMap.put("juid", "195feec6a061485da24c7316fcfb4150");
		 paramMap.put("cp_id", "1706201012030853");
	     httpRequestVO.setParamMap(paramMap);
	     HttpResponseVO httpResponseVO = AOSHttpClient.execute(httpRequestVO);
	     System.out.println("HTTP状态码：" + httpResponseVO.getStatus());
	     System.out.println("返回值：" + httpResponseVO.getOut());
		
	}
	@Test
	public  void  addOrder(){//新增订单并向模拟器发送数据
		HttpRequestVO httpRequestVO = new HttpRequestVO("http://116.62.215.62:80/cdz/api/do.jhtml?router=appApiService.addOrder");
		 Map<String,String> paramMap =new HashMap<String,String>();
		 paramMap.put("juid", "8f2ce6d069ed4291bd3c01339b6224af");
		 paramMap.put("cp_id", "654321987");
		 paramMap.put("co_type", "3");
		 paramMap.put("co_num", "20");
	     httpRequestVO.setParamMap(paramMap);
	     HttpResponseVO httpResponseVO = AOSHttpClient.execute(httpRequestVO);
	     System.out.println("HTTP状态码：" + httpResponseVO.getStatus());
	     System.out.println("返回值：" + httpResponseVO.getOut());
		
	}
	@Test
	public  void  stopOrder(){//新增订单并向模拟器发送数据
		HttpRequestVO httpRequestVO = new HttpRequestVO("http://116.62.215.62:80/cdz/api/do.jhtml?router=appApiService.stopOrder");
		 Map<String,String> paramMap =new HashMap<String,String>();
		 paramMap.put("juid", "8f2ce6d069ed4291bd3c01339b6224af");
		 paramMap.put("cp_id", "654321987");
	     httpRequestVO.setParamMap(paramMap);
	     HttpResponseVO httpResponseVO = AOSHttpClient.execute(httpRequestVO);
	     System.out.println("HTTP状态码：" + httpResponseVO.getStatus());
	     System.out.println("返回值：" + httpResponseVO.getOut());
		
	}
	@Test
	public  void  getSmsCode(){//新增订单并向模拟器发送数据
		HttpRequestVO httpRequestVO = new HttpRequestVO("http://116.62.215.62:80/cdz/api/do.jhtml?router=appApiService.getSmsCode");
		 Map<String,String> paramMap =new HashMap<String,String>();
		 paramMap.put("mobile", "13539433418");
		 paramMap.put("type", "1");
	     httpRequestVO.setParamMap(paramMap);
	     HttpResponseVO httpResponseVO = AOSHttpClient.execute(httpRequestVO);
	     System.out.println("HTTP状态码：" + httpResponseVO.getStatus());
	     System.out.println("返回值：" + httpResponseVO.getOut());
		
	}
	@Test
	public  void  getChargingPileListByAddrName(){//
		HttpRequestVO httpRequestVO = new HttpRequestVO("http://112.74.210.6:9090/cdz/api/do.jhtml?router=appApiService.getChargingPileListByAddrName");
		 Map<String,String> paramMap =new HashMap<String,String>();
		 paramMap.put("addrName", "体育中心");
	     httpRequestVO.setParamMap(paramMap);
	     HttpResponseVO httpResponseVO = AOSHttpClient.execute(httpRequestVO);
	     System.out.println("HTTP状态码：" + httpResponseVO.getStatus());
	     System.out.println("返回值：" + httpResponseVO.getOut());
		
	}
	@Test
	public  void  getDepositAmt(){//
		HttpRequestVO httpRequestVO = new HttpRequestVO("http://116.62.215.62:80/cdz/api/do.jhtml?router=appApiService.getDepositAmt");
		 Map<String,String> paramMap =new HashMap<String,String>();
		 paramMap.put("addrName", "体育中心");
	     httpRequestVO.setParamMap(paramMap);
	     HttpResponseVO httpResponseVO = AOSHttpClient.execute(httpRequestVO);
	     System.out.println("HTTP状态码：" + httpResponseVO.getStatus());
	     System.out.println("返回值：" + httpResponseVO.getOut());
		
	}
	
}
