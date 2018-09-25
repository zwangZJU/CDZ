/**
 * 
 */
package controller;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;

//import com.sun.org.apache.xpath.internal.functions.Function;

import aos.framework.core.asset.AOSBeanLoader;
import aos.framework.core.dao.SqlDao;
import aos.framework.core.id.AOSId;
import aos.framework.core.typewrap.Dto;
import aos.framework.core.typewrap.Dtos;
import aos.framework.core.utils.AOSCodec;
import aos.framework.core.utils.AOSCxt;
import aos.framework.core.utils.AOSJson;
import aos.framework.core.utils.AOSUtils;
import aos.framework.web.httpclient.AOSHttpClient;
import aos.framework.web.httpclient.HttpRequestVO;
import aos.framework.web.httpclient.HttpResponseVO;
import aos.system.common.utils.SystemCons;
import cn.com.tcc.TCC;
import dao.Alarm_descDao;
import dao.Alarm_logDao;
import dao.ChargingOrdersDao;
import dao.ChargingPileDao;
import dao.CommonLogsDao;
import po.Alarm_descPO;
import po.Alarm_logPO;
import po.Basic_userPO;
import po.ChargingOrdersPO;
import po.ChargingPilePO;
import po.CommonLogsPO;
import utils.Helper;

import dao.DeviceDao;
import po.DevicePO;
import service.AppApiService;
import service.DeviceService;
import service.Push;
import cn.com.tcc.State;
import cn.com.tcc.TCC;

import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.sun.jna.Function;

//import com.sun.jna.Function;

//package com.microwisdom.utils;

/**
 * @author Administrator
 *
 */
public class MultiServerThread extends Thread {
	    private Socket socket = null;
	    private String ascii="";
	    
	    private String ascii1="";
	    
	    private ChargingPileDao chargingPileDao;
	    private ChargingOrdersDao chargingOrdersDao;
	    private CommonLogsDao commonLogsDao ;
	    private SqlDao sqlDao;
	    
	    //private DeviceDao deviceDao;
	    
		@Autowired
		DeviceDao deviceDao;
		@Autowired
		Alarm_logDao alarm_logDao;
		@Autowired
		Alarm_descDao alarm_descDao;
	    
	    private int i=0;
	    private int j=0;
		private boolean start = true;
	    
	    private static int t=0;
	    
	    private String device_id = null;
	    
	    private static CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
	    
	    public static ArrayList<Alarm_descPO> readTxt(String path) throws Exception {
			File file = new File(path);
			//����һ����ȡ�ļ���������
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			ArrayList<Alarm_descPO> al= new ArrayList();	
			ArrayList<String> arry = new ArrayList();
			//ȥ�����в���ȡ
			String line = "";
			while ((line = bufferedReader.readLine()) != null) {
				 if(!line.equals("")){
					 arry.add(line);	                   
		            }    
			}	
			// �ͷ���Դ
			bufferedReader.close();
			// ��������
			for (String s : arry) {
				String s1=s.trim();
				if(s1.equals("")){
	                continue;
	                }  
				
				//ȥ�����������
				String result = s1.substring(0,s1.length()-2);
				String s2=result.trim();    
				if(s2.equals("")){
	                continue;
	                }			
				//��ʾ����λ��ʼ������
				String s3=s2.trim().substring(3,s2.length());
				//��ʾǰ��λ������
				String s4=s2.trim().substring(0,3);		
				Alarm_descPO type = new Alarm_descPO();
				//�����������ͺͱ��
				type.setAlarm_type(s3);	  
				type.setEee(s4);          
				al.add(type);								
			}
			return al;		
			}	
		
		
	    
	    public MultiServerThread(Socket socket) {
	        super("MultiServerThread");
	        /*try {
				socket.setSoTimeout(18000000);
			} catch (SocketException e) {
				this.saveLogs("设置socket超时", "");
				e.printStackTrace();
			}*/
	        this.socket = socket;
	        
	        chargingPileDao = (ChargingPileDao)AOSBeanLoader.getSpringBean("chargingPileDao");
	        sqlDao = (SqlDao)AOSBeanLoader.getSpringBean("sqlDao");
	        commonLogsDao=(CommonLogsDao)AOSBeanLoader.getSpringBean("commonLogsDao");
	        chargingOrdersDao=(ChargingOrdersDao)AOSBeanLoader.getSpringBean("chargingOrdersDao");
	        
	        deviceDao = (DeviceDao)AOSBeanLoader.getSpringBean("deviceDao");
	        alarm_logDao = (Alarm_logDao)AOSBeanLoader.getSpringBean("alarm_logDao");
	        alarm_descDao = (Alarm_descDao)AOSBeanLoader.getSpringBean("alarm_descDao");
	    }
	    public void saveLogs(String content,String cp_id){
	    	
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
	    
	    private static String hexString = "0123456789ABCDEF";
	    /*
	     * 将16进制数字解码成字符串,适用于所有字符（包括中文）
	     */
	    public static String decode(String bytes) {
	        ByteArrayOutputStream baos = new ByteArrayOutputStream(bytes.length() / 2);
	        // 将每2位16进制整数组装成一个字节
	        for (int i = 0; i < bytes.length(); i += 2)
	        baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString
	                        .indexOf(bytes.charAt(i + 1))));
	        return new String(baos.toByteArray());
	    }
	    
	    public static String strTo16(String s) {
		    String str = "";
		    String str1= "";
		    for (int i = 0; i < s.length(); i++) {
		        int ch = (int) s.charAt(i);
		        String s4 = Integer.toHexString(ch);
		        str = str +s4;
		        str1= str1+" "+s4;
		    }
		    System.out.println("str1:"+str1);
		    
		    return str;
		}
	    
	    public static void timer1() {
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				public void run() {
					System.out.println("-------设定要指定任务--------");
					try {
						t = t+1;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}, 1000);// 设定指定的时间time,此处为1000毫秒
		}
	    
	    public void run() {

	        try (
	            InputStream in   = this.socket.getInputStream(); //云平台得到数据
	            OutputStream out = this.socket.getOutputStream();
	        ) {
	            int length = 0;
	            byte[] data_in  = new byte[1024];
	            byte[] data_out;
	            
		            // welcome message here
		        String msg_welcome = Helper.fillString('0', 32*2);
		            //msg_welcome        = msg_welcome.replaceFirst("^000000", "E20103");
		        msg_welcome        = msg_welcome.replaceFirst("^00000000", "E1010410");   //msg_welcome即请求注册包
		        System.out.println("msg_welcome: " + msg_welcome);
		        data_out           = Helper.hexStringToByteArray(msg_welcome);
		        out.write(data_out);
		        this.saveLogs("SC①服务器发出欢迎指令："+msg_welcome,"","SC①");
		        i++;
		        System.out.println("iiiiiiii:"+i);

		     // 初始化TCC,用于java调用c文件
				TCC.init("./tcc");

				// 编译C文件
				cn.com.tcc.State state = new cn.com.tcc.State();
				try {
					state.compile("./function.c");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				state.relocateAuto();

				// 获取C文件里面定义的函数
				//Function sumFunc = state.getFunction("sum");
				Function sumFunc = state.getFunction("use_aesDecrypt");
				
				byte[] Key = null;
				
				String hex;
				
				DevicePO devicePO = null;
				
				String blacklist_info ;   //黑名单信息
		        
	            while ((length = in.read(data_in)) > 0) {  //如果有数据传到云平台
	            	
	            	System.out.println("data_in_toString():"+data_in.toString());
	                byte[] data = new byte[length];
	                
	                System.arraycopy(data_in, 0, data, 0, length);  //把data_in赋值给data
	                System.out.println(data);
	                
	                hex = DatatypeConverter.printHexBinary(data);  //把data转换为16进制字符串
	                System.out.println("hex:"+hex);
	                
	                char[] hex_byte= hex.toCharArray();  //转为字符数组
	                String msgType=String.valueOf(hex_byte, 2, 2);   // 从第2个字节开始，取2个字节，即cmd
	                System.out.println("msgType:"+msgType); 
	                this.saveLogs("原始数据:"+hex,this.ascii);
	                
	                String eight = String.valueOf(hex_byte, 0, 64);  //从第0个字节开始，取64个字节，即全部
	                System.out.println("数据16进制:"+eight);
	                
	                j++;  //j就用来查看进入run的次数和进入if的次数
	                System.out.println("jjjjjjjj:"+j); 
	                
	                if("01".equals(msgType)&&"E2".equals(String.valueOf(hex_byte, 0, 2))){  //注册
	                	 
	                	String Corp_ID=String.valueOf(hex_byte, 6, 2);  //corp_ID，产品型号
	                	System.out.println("Corp_ID:"+Corp_ID);
	                	
	                	String ascii_1=Helper.AsciiStringToString(String.valueOf(hex_byte, 48, 14));  //ICode 
	                	this.ascii1=ascii_1;  //ICode,产品序列号
	                	
	                	/* 得到ACCT，即用户编号 */
	                	String ACCT1 =decode(String.valueOf(hex_byte, 8, 2));
	                	System.out.println("ACCT1:"+ACCT1);   	
	                	String ACCT2 =decode(String.valueOf(hex_byte, 10, 2));
	                	System.out.println("ACCT2:"+ACCT2); 
	                	String ACCT3 =decode(String.valueOf(hex_byte, 12, 2));
	                	System.out.println("ACCT3:"+ACCT3); 
	                	String ACCT4 =decode(String.valueOf(hex_byte, 14, 2));
	                	System.out.println("ACCT4:"+ACCT4); 
	                	//decode把十六进制转字符串
	                	System.out.println("ACCT  decode :"+decode(String.valueOf(hex_byte, 8, 2))+","+decode(String.valueOf(hex_byte, 10, 2))
	                	+","+decode(String.valueOf(hex_byte, 12, 2))+","+decode(String.valueOf(hex_byte, 14, 2)));
	                	
	                	// 得到密钥key，用于解密
	                	byte Key7 =(byte) Integer.parseInt(String.valueOf(hex_byte, 16, 2));
	                	byte Key6 =(byte) Integer.parseInt(String.valueOf(hex_byte, 18, 2));
	                	byte Key5 =(byte) Integer.parseInt(String.valueOf(hex_byte, 20, 2));
	                	byte Key4 =(byte) Integer.parseInt(String.valueOf(hex_byte, 22, 2));
	                	byte Key3 =(byte) Integer.parseInt(String.valueOf(hex_byte, 24, 2));
	                	byte Key2 =(byte) Integer.parseInt(String.valueOf(hex_byte, 26, 2));
	                	byte Key1 =(byte) Integer.parseInt(String.valueOf(hex_byte, 28, 2));
	                	byte Key0 =(byte) Integer.parseInt(String.valueOf(hex_byte, 30, 2));
	                	byte Key15 =(byte) Integer.parseInt(String.valueOf(hex_byte, 32, 2));
	                	byte Key14 =(byte) Integer.parseInt(String.valueOf(hex_byte, 34, 2));
	                	byte Key13 =(byte) Integer.parseInt(String.valueOf(hex_byte, 36, 2));
	                	byte Key12 =(byte) Integer.parseInt(String.valueOf(hex_byte, 38, 2));
	                	byte Key11 =(byte) Integer.parseInt(String.valueOf(hex_byte, 40, 2));
	                	byte Key10 =(byte) Integer.parseInt(String.valueOf(hex_byte, 42, 2));
	                	byte Key9 =(byte) Integer.parseInt(String.valueOf(hex_byte, 44, 2));
	                	byte Key8 =(byte) Integer.parseInt(String.valueOf(hex_byte, 46, 2));
	                	
	                	String ICode1 =new BigInteger(String.valueOf(hex_byte, 48, 2), 16).toString();
	                	String ICode2 =new BigInteger(String.valueOf(hex_byte, 50, 2), 16).toString();
	                	String ICode3 =new BigInteger(String.valueOf(hex_byte, 52, 2), 16).toString();
	                	String ICode4 =new BigInteger(String.valueOf(hex_byte, 54, 2), 16).toString();
	                	String ICode5 =new BigInteger(String.valueOf(hex_byte, 56, 2), 16).toString();
	                	String ICode6 =new BigInteger(String.valueOf(hex_byte, 58, 2), 16).toString();
	                	String ICode7 =new BigInteger(String.valueOf(hex_byte, 60, 2), 16).toString();
	                	String ICode8 =new BigInteger(String.valueOf(hex_byte, 62, 2), 16).toString();
	                	
	                	System.out.println("Key_all:"+Key0+Key1+Key2+Key3+Key4+Key5+Key6+Key7+Key8+
	                			Key9+Key10+Key11+Key12+Key13+Key14+Key15); 
	                	System.out.println("ICode_all:"+ICode1+ICode2+ICode3+ICode4+ICode5+ICode6+ICode7+ICode8); 
	                	
	                	byte[] key = {data_in[15],data_in[14],data_in[13],data_in[12],data_in[11],data_in[10],
	                			data_in[9],data_in[8],data_in[23],data_in[22],data_in[21],data_in[20],
	                			data_in[19],data_in[18],data_in[17],data_in[16]};
	                	Key= key;  //密钥
	                	
	                	String str_ACCT =ACCT1+ACCT2+ACCT3+ACCT4;
	                	String ICode = null;
	                	String str_ascii = null;
	                	//String str_ascii = "4gw765431";
	                	
	                	Helper.socketMap.put(this.ascii1, this.socket);//保存socket对象，产品序列号
	                	System.out.println("this.ascii1："+this.ascii1);
	                	
	                	switch(Corp_ID) {   //根据Corp_ID可知模块型号，但现在采用扫码注册，型号直接在扫码时直接注册，所以后面没有插入型号。
	                	
		                	case "09":
		                		System.out.println("TN-G100网络模块");
		                		break;
		                	case "10":
		                		System.out.println("TN-G200网络模块");
		                		break;
		                	case "11":
		                		System.out.println("TN-G300网络模块");
		                		break;
		                	case "12":
		                		System.out.println("TN-S100网络模块");
		                		break;
		                	case "13":
		                		System.out.println("TN-G201网络模块");
		                		break;
		                	case "14":
		                		System.out.println("TN-G211网络模块");
		                		break;
		                	case "15":
		                		System.out.println("TN-G202网络模块");
		                		break;
		                	case "16":
		                		System.out.println("TN-G212网络模块");
		                		break;
		                	case "17":
		                		System.out.println("TN-G221网络模块");
		                		break;
		                	case "18":
		                		System.out.println("TN-G311网络模块");
		                		break;
		                	case "19":
		                		System.out.println("TN-G321网络模块");
		                		break;
		                	case "20":
		                		System.out.println("TN-G231网络模块");
		                		break;
		                	case "30":
		                		System.out.println("终端测试模拟程序");
		                		break;
	                	}
	                	
	                	
	                	Dto pDto=Dtos.newDto("device_id",this.ascii1);  //把序列号this.ascii1给device_id
	            		List<DevicePO> deviceDtos = deviceDao.like(pDto);  //模糊查询是否有该设备
	            		device_id = deviceDtos.get(0).getDevice_id();
	            		//device_id = "26666666";  //模拟终端测试时用这一句，否则用上面那句
	            		//device_id = "18888888";
	            		
	            		Dto pDto1 = Dtos.newDto("device_id", device_id);
	    				devicePO = deviceDao.selectOne(pDto1);  //根据设备id得到deviceDao
	                	
	                	if(null==devicePO)  //若设备不存在，则不做其他操作，因为只能通过扫码注册设备
	                	{
	                		System.out.println("设备未扫码注册");
	                		
	                	}else {//已存在，则修改状态，这里只需增加ACCT用户编号就可以，其他的设备信息在扫码的时候已经注册进取
	                		
	                		blacklist_info  = devicePO.getBlacklist();
		    				if(blacklist_info.equals("1"))
		    					return ;
	                		
	                		Dto pDto1_ = Dtos.newDto("device_id", device_id);
	                		DevicePO  devicePO_=deviceDao.selectOne(pDto1_);;
	                		devicePO_.setUser_acct(str_ACCT);    //设置设备账号（每个保安公司不会重复），对保安公司有意义
	                		deviceDao.updateByKey(devicePO_);
	                		System.out.println("update");
	                	}
	                	
	                	
	                }else {   //心跳和报警，需解密
	                	byte[] databuffer = new byte[16];   //先取心跳或报警包的前16字节，因为每次解密只能解16字节
	                	for(int k=0;k<16;k++)
	                		databuffer[k] = data_in[k];
	                	
	            		byte[] data1 = new byte[16];
	            		byte[] data2 = new byte[16];
	            		String Q = null;
	            		String EEE = null;
	            		String GG = null;
	            		String CCC = null;
	                	
	                	Object[] object = new Object[]{ databuffer , Key,data1};
	            		String decodeResult_1= sumFunc.invokeString(object, false);  //开始解密，调用C文件，decodeResult_1是解密后的结果。
	            		System.out.println("decodeResult:"+decodeResult_1);
	            		String resultLatter_1 = null;
	            		if(decodeResult_1 != null)
	            			resultLatter_1 = strTo16(decodeResult_1.substring(1));  //因为前面1位是乱码，所以取第1位后面的，是从包的LEN开始的，后面的解密后再substring的原因一般都是因为乱码。
	            		System.out.println("resultLatter:"+resultLatter_1);
	            		System.out.println("resultLatter.length():"+resultLatter_1.length());            		
	            		
	            		if(resultLatter_1.length()>1) {
	            			//4f,4d,30分别对应十进制的o,k,0,对应协议
		            		if(resultLatter_1.substring(2, 4).equals("4f")&&resultLatter_1.substring(4, 6).equals("4b")&&!resultLatter_1.substring(6,8).equals("30"))
		            			System.out.println("xintiao");
		            		
		            		String signal_quality = null;  //信号质量
		            		String resultLatter_1_signal_quality = null;
		            		if(resultLatter_1.substring(6,8) != null)
		            			resultLatter_1_signal_quality = resultLatter_1.substring(6,8);  //根据协议得到信号质量
		            		
		            		switch(resultLatter_1_signal_quality) {
		                	
		                	case "31":
		                		System.out.println("10%");
		                		signal_quality="10%";
		                		break;
		                	case "32":
		                		System.out.println("20%");
		                		signal_quality="20%";
		                		break;
		                	case "33":
		                		System.out.println("30%");
		                		signal_quality="30%";
		                		break;
		                	case "34":
		                		System.out.println("40%");
		                		signal_quality="40%";
		                		break;
		                	case "35":
		                		System.out.println("50%");
		                		signal_quality="50%";
		                		break;
		                	case "36":
		                		System.out.println("60%");
		                		signal_quality="60%";
		                		break;
		                	case "37":
		                		System.out.println("70%");
		                		signal_quality="70%";
		                		break;
		                	case "38":
		                		System.out.println("80%");
		                		signal_quality="80%";
		                		break;
		                	case "39":
		                		System.out.println("90%");
		                		signal_quality="90%";
		                		break;
		                	case "3a":
		                		System.out.println("100%");
		                		signal_quality="100%";
		                		break;
	                	}
		            			
		            		Dto pDto6 = Dtos.newDto("device_id",device_id);
		        			DevicePO devicePO6=deviceDao.selectOne(pDto6);   //更新信息，这里是信号质量
							devicePO6.setLast_date(AOSUtils.getDateTime());  //最后来心跳信号时间
							devicePO6.setSignal_quality(signal_quality);
							//devicePO6.setShutdown_time(AOSUtils.getDateTime());  //离线时间
							deviceDao.updateByKey(devicePO6);
							
							if(start) {    //这个if下面用来判断是否离线
		            			start = false;
		            			System.out.println("Thread()");
			            		new Thread() {   //开线程
			            			public void run() {
			            				boolean start1=true;
			            				
										while(start1) {
			            				try {
			            					
											Thread.sleep(120000);   //睡眠2分钟，后面查看这两分钟内是否有心跳发生，若有心跳上来，则在线，否则离线。
									        Date now = new Date();
											System.out.println("now:"+now);// new Date()为获取当前系统时间
									     
									         Dto pDto11 = Dtos.newDto("device_id", device_id);
											DevicePO devicePO = deviceDao.selectOne(pDto11);
											Date last_date = devicePO.getLast_date();
											System.out.println("last_date:"+last_date);
											
											long time1 = now.getTime()-last_date.getTime();
											System.out.println("时间间隔:"+time1);
											
											if(time1 <120000)  //两分钟内有心跳上来，则在线，否则离线
											{
												//start = true;
												//System.out.println("time1 <180000");
												System.out.println("在线");
												devicePO.setOnline_state("在线");
												deviceDao.updateByKey(devicePO);
												
											}else {
												start1 = false;
												start = true;
												System.out.println("离线");
												
												devicePO.setShutdown_time(last_date);
												devicePO.setOnline_state("离线");
												//判断为离线后，更新设备表里的离线次数和布撤防状态
												String shutdown_num = Integer.toString(Integer.parseInt(devicePO.getShutdown_number())+1);
												devicePO.setShutdown_number(shutdown_num);  //离线次数
												devicePO.setArrange_withdraw("撤防");   
												
												 //alarm_logPO.setResponse_time(new Date());
												deviceDao.updateByKey(devicePO);
											}
											
										} catch (InterruptedException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
			            				}
			            			}
			            		}.start();
			            		
		            		}
	            		}
	            		
	    				if(resultLatter_1.length()>5) {
		            		if(resultLatter_1.substring(6,8).equals("30")&&resultLatter_1.substring(1,2).equals("5")&&resultLatter_1.substring(2, 4).equals("4f")&&resultLatter_1.substring(4, 6).equals("4b"))
		            		{
		            			resultLatter_1 = "0";
		            			System.out.println("收到OK");
		            			//受到ok对布撤防没什么用，所以这里没什么操作，这里把resultLatter_1 = "0"是为了防止收到ok后直接进入报警包
		            		}
	    				}
	    				if(resultLatter_1.length()>20)  //说明是报警包
	            		{
	            			byte[] databuffer2 = new byte[16];   //报警包的后16位，后面要解密
		                	for(int n=16;n<32;n++)
		                		databuffer2[n-16] = data_in[n];   			
		            		
	            			System.out.println("resultLatter_1.length()>20");	
	            			System.out.println(resultLatter_1.substring(13, 15));	
	            			System.out.println(resultLatter_1.substring(15, 17));
	            			//根据协议，如果某个固定的位置出现了1和8，则说明是报警包
		            		if(resultLatter_1.substring(13, 15).equals("31")&&resultLatter_1.substring(15, 17).equals("38")) {
		            			System.out.println("baojing");	
				            	
		            			Object[] object2 = new Object[]{ databuffer2 , Key,data2};
			            		String decodeResult_2= sumFunc.invokeString(object2, false);  //解密后16位
			            		System.out.println("decodeResult_2:"+decodeResult_2);
			            		String resultLatter_2 = strTo16(decodeResult_2);
			            		System.out.println("resultLatter_2:"+resultLatter_2);
			            		System.out.println("resultLatter_2.length():"+resultLatter_2.length());	
			            		CCC = resultLatter_2.substring(resultLatter_2.length()-2, resultLatter_2.length());  //得到CCC防区，用substring是因为乱码
		            			System.out.println("CCC:"+CCC);
		            			String str_CCC = decode(CCC);
		            			System.out.println("str_CCC:"+str_CCC);
			            		
		            			if(resultLatter_1.substring(17, 19).equals("31"))	
			            			Q = "1";  //触发，表示新事件，撤防是触发
		            			else if(resultLatter_1.substring(17, 19).equals("33"))
		            				Q = "0";  //恢复，表示恢复事件，布防是恢复
		            			
		            			EEE = resultLatter_1.substring(19, 25);
		            			System.out.println("EEE:"+EEE);  //警情代码
		            			String str_EEE = decode(EEE);
		            			
		            			GG = resultLatter_1.substring(25, 29);
		            			System.out.println("GG:"+GG);
		            			String str_GG = decode(GG);
		            			
		            			Dto pDto3 = Dtos.newDto("eee", str_EEE);
		            			Alarm_descPO alarm_descPO = alarm_descDao.selectOne(pDto3);
		            			
		            			Dto pDto4 = Dtos.newDto("device_id", device_id);
		            			DevicePO devicePO3 = deviceDao.selectOne(pDto4);
		            			
		            			/*下面是修改alarm_log表里面的值.......*/
		            			Alarm_logPO alarm_logPO = new Alarm_logPO();   
		            			alarm_logPO.setAlarm_id(AOSId.appId(SystemCons.ID.SYSTEM));
		            			alarm_logPO.setDevice_id(device_id);
		            			alarm_logPO.setUser_phone(devicePO3.getPhone());
		            			alarm_logPO.setAlarm_time(new Date());
		            			if(alarm_descPO != null)
		            				if(str_EEE.equals("401")&&Q.equals("0"))
		            					alarm_logPO.setReason_("布防");
		            				else if(str_EEE.equals("401")&&Q.equals("1"))
		            					alarm_logPO.setReason_("撤防");
		            				else 
		            					alarm_logPO.setReason_(alarm_descPO.getAlarm_type());
		            			else
		            				alarm_logPO.setReason_("未定义的警情");
		            			alarm_logPO.setAlert_code(str_EEE);  //警情代码
		            			alarm_logPO.setProcess("0");  //是否接警
		            			alarm_logPO.setDefence_area(str_CCC);  //防区号
		            			alarm_logPO.setType_("0");  //报警类型
		            			alarm_logPO.setHandler_(devicePO3.getHead());  //负责人名字
		            			alarm_logPO.setHandler_phone(devicePO3.getHead_phone());  //负责人手机号
		            			
		            			//加CCC和GG
		            			alarm_logDao.insert(alarm_logPO);
		            			
		            			Push.pushToSingle(devicePO3.getPhone());  //推送给手机
		            			
		            			//sendSms(devicePO.getPhone(),"1",str_EEE);
		            			Dto pDto10 = Dtos.newDto("device_id", device_id);
			    				devicePO = deviceDao.selectOne(pDto10);
			            		System.out.println(devicePO.getArrange_withdraw());
		            			
		            			/*下面是修改device表里的值*/
		            			Dto pDto5 = Dtos.newDto("device_id",device_id);
			        			DevicePO devicePO4=deviceDao.selectOne(pDto5);
								devicePO4.setIs_alarming(Q);  //
								if(str_EEE.equals("401")&&Q.equals("1"))
								{
									devicePO4.setArrange_withdraw("撤防");
									devicePO4.setWithdraw_date(AOSUtils.getDateTime());
								}
								else if(str_EEE.equals("401")&&Q.equals("0"))
								{
									devicePO4.setArrange_withdraw("布防");
									devicePO4.setWithdraw_date(AOSUtils.getDateTime());
								}
								deviceDao.updateByKey(devicePO4);
								
		            		}
	            		}
	    
	                }
	            }  
	            this.socket.close();
	            if(AOSUtils.isNotEmpty(this.ascii)){
	            	ChargingPilePO  chargingPilePO_=new ChargingPilePO();
            		chargingPilePO_.setCp_id(this.ascii);
            		chargingPilePO_.setCp_status("3");
            		chargingPileDao.updateByKey(chargingPilePO_);
	            }
	            String content="客户端主动断开连接";
	            System.out.println(content);
	            this.saveLogs(content,this.ascii);
	        }catch (SocketTimeoutException e){
	        	 e.printStackTrace();
	             if(AOSUtils.isNotEmpty(this.ascii)){
	            	ChargingPilePO  chargingPilePO_=new ChargingPilePO();
            		chargingPilePO_.setCp_id(this.ascii);
            		chargingPilePO_.setCp_status("3");
            		chargingPileDao.updateByKey(chargingPilePO_);
            		Helper.socketMap.remove(this.ascii);
	             }
	             System.out.println("连接超时，断开连接");
	             this.saveLogs("连接超时，服务器断开连接",this.ascii);
	        }catch (IOException e) {
	            e.printStackTrace();
	            /*if(AOSUtils.isNotEmpty(this.ascii)){
	            	ChargingPilePO  chargingPilePO_=new ChargingPilePO();
            		chargingPilePO_.setCp_id(this.ascii);
            		chargingPilePO_.setCp_status("3");
            		chargingPileDao.updateByKey(chargingPilePO_);
	            }*/
	            if("Connection reset".equals(e.getMessage())){
	            	if(AOSUtils.isNotEmpty(this.ascii)){
		            	ChargingPilePO  chargingPilePO_=new ChargingPilePO();
	            		chargingPilePO_.setCp_id(this.ascii);
	            		chargingPilePO_.setCp_status("3");
	            		chargingPileDao.updateByKey(chargingPilePO_);
	            		
	            		Dto pDto=Dtos.newDto("cp_id",this.ascii);
	            		ChargingOrdersPO chargingOrdersPO=new ChargingOrdersPO();
                		List<Dto> list=sqlDao.list("ChargingOrders.listChargingOrderssSocket", pDto);
                		if(null!=list&&list.size()>0){
                			AOSUtils.copyProperties(list.get(0), chargingOrdersPO);
                			if("-2".equals(chargingOrdersPO.getStatus_())){
                				ChargingOrdersPO chargingOrdersPO_=new ChargingOrdersPO();
                				chargingOrdersPO_.setCo_id(chargingOrdersPO.getCo_id());
                				chargingOrdersPO_.setStatus_("-1");
                				chargingOrdersDao.updateByKey(chargingOrdersPO_);
                			}else if("0".equals(chargingOrdersPO.getStatus_())){
                				if(BigDecimal.ZERO.compareTo(chargingOrdersPO.getTotal_amt())==0){
                					ChargingOrdersPO chargingOrdersPO_=new ChargingOrdersPO();
                					chargingOrdersPO_.setCo_id(chargingOrdersPO.getCo_id());
                					chargingOrdersPO_.setStatus_("-1");
                					chargingOrdersDao.updateByKey(chargingOrdersPO_);
                				}else{
                					ChargingOrdersPO chargingOrdersPO_=new ChargingOrdersPO();
                					chargingOrdersPO_.setCo_id(chargingOrdersPO.getCo_id());
                					chargingOrdersPO_.setStatus_("1");
                					chargingOrdersDao.updateByKey(chargingOrdersPO_);
                				}
                			}
                		}
	            		
	            		
	            		
	            		Helper.socketMap.remove(this.ascii);
		            }
	            	System.out.println("硬件异常断开连接");
		            this.saveLogs("硬件异常断开连接"+e.getMessage(),this.ascii);
	            }else{
	            	System.out.println("数据解析异常");
		            this.saveLogs("数据解析异常"+e.getMessage(),this.ascii);
	            }
	            
	        }catch (Exception e) {
	            e.printStackTrace();
	            /*if(AOSUtils.isNotEmpty(this.ascii)){
	            	ChargingPilePO  chargingPilePO_=new ChargingPilePO();
            		chargingPilePO_.setCp_id(this.ascii);
            		chargingPilePO_.setCp_status("3");
            		chargingPileDao.updateByKey(chargingPilePO_);
	            }*/
	            
	            	System.out.println("数据解析异常2");
		            this.saveLogs("数据解析异常2"+e.getMessage(),this.ascii);
	            
	        }
	    }
	    
		public static  Map<String,String>  getLocationinfor(String latlng){//
			HttpRequestVO httpRequestVO = new HttpRequestVO("http://getlocat.market.alicloudapi.com/api/getLocationinfor");
			 Map<String,String> paramMap =new HashMap<String,String>();
			 paramMap.put("latlng", latlng);
			 paramMap.put("type", "2");
			 Map<String,String> headerMap =new HashMap<String,String>();
			 headerMap.put("Authorization", "APPCODE " + "5d2c5c9875794d0b97f20e16ed6d7e09");
			 httpRequestVO.setHeadMap(headerMap);
		     httpRequestVO.setParamMap(paramMap);
		     HttpResponseVO httpResponseVO = AOSHttpClient.execute(httpRequestVO);
		     //System.out.println("HTTP状态码：" + httpResponseVO.getStatus());
		    // System.out.println("返回值：" + httpResponseVO.getOut());
		     if("200".equals(httpResponseVO.getStatus())){
		    	 Map<String,Object> map=AOSJson.fromJson(httpResponseVO.getOut(), Map.class);
				 System.out.println(map.get("result"));
				 Map<String,String> result=(Map)map.get("result");
		    	 return result;
		     }else{
		    	 return null;
		     }
			
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
}
