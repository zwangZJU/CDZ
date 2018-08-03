/**
 * 
 */
package controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

//import com.sun.org.apache.xpath.internal.functions.Function;

import aos.framework.core.asset.AOSBeanLoader;
import aos.framework.core.dao.SqlDao;
import aos.framework.core.id.AOSId;
import aos.framework.core.typewrap.Dto;
import aos.framework.core.typewrap.Dtos;
import aos.framework.core.utils.AOSCxt;
import aos.framework.core.utils.AOSJson;
import aos.framework.core.utils.AOSUtils;
import aos.framework.web.httpclient.AOSHttpClient;
import aos.framework.web.httpclient.HttpRequestVO;
import aos.framework.web.httpclient.HttpResponseVO;
import aos.system.common.utils.SystemCons;
import cn.com.tcc.TCC;
import dao.ChargingOrdersDao;
import dao.ChargingPileDao;
import dao.CommonLogsDao;
import po.ChargingOrdersPO;
import po.ChargingPilePO;
import po.CommonLogsPO;
import utils.Helper;

import dao.DeviceDao;
import po.DevicePO;

import cn.com.tcc.State;
import cn.com.tcc.TCC;

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
	    
	    private DeviceDao deviceDao;
	    
	    private int i=0;
	    private int j=0;
	    
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
		    for (int i = 0; i < s.length(); i++) {
		        int ch = (int) s.charAt(i);
		        String s4 = Integer.toHexString(ch);
		        str = str + s4;
		    }
		    return str;
		}
	    
	    public void run() {

	        try (
	            InputStream in   = this.socket.getInputStream();
	            OutputStream out = this.socket.getOutputStream();
	        ) {
	            int length = 0;
	            byte[] data_in  = new byte[1024];
	            byte[] data_out;
	            
		            // welcome message here
		        String msg_welcome = Helper.fillString('0', 32*2);
		            //msg_welcome        = msg_welcome.replaceFirst("^000000", "E20103");
		        msg_welcome        = msg_welcome.replaceFirst("^00000000", "E1010410");   //请求注册包
		        System.out.println("msg_welcome: " + msg_welcome);
		        data_out           = Helper.hexStringToByteArray(msg_welcome);
		        out.write(data_out);
		        this.saveLogs("SC①服务器发出欢迎指令："+msg_welcome,"","SC①");
		        i++;
		        System.out.println("iiiiiiii:"+i);

		     // 初始化TCC
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
		        
		        
	            while ((length = in.read(data_in)) > 0) {
	            	
	            	System.out.println("data_in_toString():"+data_in.toString());
	            	//System.out.println("length:"+length);
	                byte[] data = new byte[length];
	                
	                System.arraycopy(data_in, 0, data, 0, length);
	                System.out.println(data);
	                
	                hex = DatatypeConverter.printHexBinary(data);
	                System.out.println("hex:"+hex);
	                String data_string = decode(hex.toString());
			        String data_string_1 = hex.toString();
	                
	                char[] hex_byte= hex.toCharArray();
	                String msgType=String.valueOf(hex_byte, 2, 2);   // cmd
	                this.saveLogs("原始数据:"+hex,this.ascii);
	                
	                String eight = String.valueOf(hex_byte, 0, 64);
	                System.out.println("数据16进制:"+eight);
	                
	                System.out.println("msgType:"+msgType); 
	                j++;
	                System.out.println("jjjjjjjj:"+j); 
	                
	                if("01".equals(msgType)){//注册
	                	 this.saveLogs("注册原始数据:"+hex,this.ascii,"CS①");
	                	//注册信息
	                	String chongdianzhuantai=new BigInteger(String.valueOf(hex_byte, 6, 2), 16).toString();
	                	String ascii_=Helper.AsciiStringToString(String.valueOf(hex_byte, 8, 18));
	                	this.ascii=ascii_;
	                	
	                	//String Corp_ID=new BigInteger(String.valueOf(hex_byte, 6, 2), 16).toString(); //注册包中的Corp_ID,模块型号
	                	String Corp_ID=String.valueOf(hex_byte, 6, 2);
	                	System.out.println("Corp_ID:"+Corp_ID);
	                	String ascii_1=Helper.AsciiStringToString(String.valueOf(hex_byte, 8, 56));   //注册包中第8位开始后面总共24个位
	                	this.ascii1=ascii_1;
	                	/*String ICode1=new BigInteger(String.valueOf(hex_byte, 8, 2), 16).toString();
	                	String ICode2=new BigInteger(String.valueOf(hex_byte, 10, 2), 16).toString();
	                	String ICode3=new BigInteger(String.valueOf(hex_byte, 12, 2), 16).toString();
	                	String ICode4=new BigInteger(String.valueOf(hex_byte, 14, 2), 16).toString();
	                	String ICode5=new BigInteger(String.valueOf(hex_byte, 16, 2), 16).toString();
	                	String ICode6=new BigInteger(String.valueOf(hex_byte, 18, 2), 16).toString();
	                	String ICode7=new BigInteger(String.valueOf(hex_byte, 20, 2), 16).toString();
	                	String ICode8=new BigInteger(String.valueOf(hex_byte, 22, 2), 16).toString();
	                	String ICode9=new BigInteger(String.valueOf(hex_byte, 24, 2), 16).toString();
	                	String ICode10=new BigInteger(String.valueOf(hex_byte, 26, 2), 16).toString();
	                	String ICode11=new BigInteger(String.valueOf(hex_byte, 28, 2), 16).toString();
	                	String ICode12=new BigInteger(String.valueOf(hex_byte, 30, 2), 16).toString();
	                	String ICode13=new BigInteger(String.valueOf(hex_byte, 32, 2), 16).toString();
	                	String ICode14=new BigInteger(String.valueOf(hex_byte, 34, 2), 16).toString();
	                	String ICode15=new BigInteger(String.valueOf(hex_byte, 36, 2), 16).toString();
	                	String ICode16=new BigInteger(String.valueOf(hex_byte, 38, 2), 16).toString();*/
	                	String TYPE_ID=new BigInteger(String.valueOf(hex_byte, 40, 2), 16).toString();
	                	//String Corp_ID=new BigInteger(String.valueOf(hex_byte, 42, 2), 16).toString();
	                	Helper.socketMap.put(this.ascii, this.socket);//保存socket对象
	                	
	                	String ACCT1 =new BigInteger(String.valueOf(hex_byte, 8, 2), 16).toString();
	                	System.out.println("ACCT1:"+ACCT1);   	
	                	String ACCT2 =new BigInteger(String.valueOf(hex_byte, 10, 2), 16).toString();
	                	System.out.println("ACCT2:"+ACCT2); 
	                	String ACCT3 =new BigInteger(String.valueOf(hex_byte, 12, 2), 16).toString();
	                	System.out.println("ACCT3:"+ACCT3); 
	                	String ACCT4 =new BigInteger(String.valueOf(hex_byte, 14, 2), 16).toString();
	                	System.out.println("ACCT4:"+ACCT4); 
	                	/*String Key7 =new BigInteger(String.valueOf(hex_byte, 16, 2), 16).toString();
	                	String Key6 =new BigInteger(String.valueOf(hex_byte, 18, 2), 16).toString();
	                	String Key5 =new BigInteger(String.valueOf(hex_byte, 20, 2), 16).toString();
	                	String Key4 =new BigInteger(String.valueOf(hex_byte, 22, 2), 16).toString();
	                	String Key3 =new BigInteger(String.valueOf(hex_byte, 24, 2), 16).toString();
	                	String Key2 =new BigInteger(String.valueOf(hex_byte, 26, 2), 16).toString();
	                	String Key1 =new BigInteger(String.valueOf(hex_byte, 28, 2), 16).toString();
	                	String Key0 =new BigInteger(String.valueOf(hex_byte, 30, 2), 16).toString();
	                	String Key15 =new BigInteger(String.valueOf(hex_byte, 32, 2), 16).toString();
	                	String Key14 =new BigInteger(String.valueOf(hex_byte, 34, 2), 16).toString();
	                	String Key13 =new BigInteger(String.valueOf(hex_byte, 36, 2), 16).toString();
	                	String Key12 =new BigInteger(String.valueOf(hex_byte, 38, 2), 16).toString();
	                	String Key11 =new BigInteger(String.valueOf(hex_byte, 40, 2), 16).toString();
	                	String Key10 =new BigInteger(String.valueOf(hex_byte, 42, 2), 16).toString();
	                	String Key9 =new BigInteger(String.valueOf(hex_byte, 44, 2), 16).toString();
	                	String Key8 =new BigInteger(String.valueOf(hex_byte, 46, 2), 16).toString();*/
	                	
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
	                	
	                	//byte[] key = {Key0,Key1,Key2,Key3,Key4,Key5,Key6,Key7,Key8,
	                			//Key9,Key10,Key11,Key12,Key13,Key14,Key15};
	                	byte[] key = {data_in[15],data_in[14],data_in[13],data_in[12],data_in[11],data_in[10],
	                			data_in[9],data_in[8],data_in[23],data_in[22],data_in[21],data_in[20],
	                			data_in[19],data_in[18],data_in[17],data_in[16]};
	                	Key= key;
	                	
	                	Helper.socketMap.put(this.ascii1, this.socket);//保存socket对象
	                	if("0x01".equals(Corp_ID)){
	                		Corp_ID="中创公司";
	                	}else{
	                		Corp_ID="xx公司";
	                	}
	                	ChargingPilePO  chargingPilePO=chargingPileDao.selectByKey(this.ascii);
	                	//ChargingPilePO  chargingPilePO=chargingPileDao.selectByKey("1");
	                	
	                	//DevicePO devicePO = deviceDao.selectByKey(this.ascii1);
	                	//DevicePO devicePO = null;
	                	DevicePO devicePO = deviceDao.selectByKey(Corp_ID);
	                	
	                	if(null==chargingPilePO){//新增对象
	                		ChargingPilePO  chargingPilePO_=new ChargingPilePO();
	                		chargingPilePO_.setCp_id(this.ascii);
	                		chargingPilePO_.setCp_no(this.ascii);
	                		chargingPilePO_.setCp_type(TYPE_ID);
	                		chargingPilePO_.setSupplier(Corp_ID);
	                		chargingPilePO_.setCreate_date(AOSUtils.getDateTime());
	                		chargingPilePO_.setOper_id("socket");
	                		if("0".equals(chongdianzhuantai)){
	                			chargingPilePO_.setCp_status("1");
	                		}else if("0".equals(chongdianzhuantai)){
	                			chargingPilePO_.setCp_status("0");
	                		}else{
	                			chargingPilePO_.setCp_status(chongdianzhuantai);
	                		}
	                		
	                		chargingPilePO_.setElectricity(new BigDecimal(AOSCxt.getParam("electricity")));
	                		chargingPileDao.insert(chargingPilePO_);
	                	}else{//已存在，则修改状态
	                		ChargingPilePO  chargingPilePO_=new ChargingPilePO();
	                		chargingPilePO_.setCp_id(this.ascii);
	                		chargingPilePO_.setCp_type(TYPE_ID);
	                		chargingPilePO_.setSupplier(Corp_ID);
	                		chargingPilePO_.setUpdate_date(AOSUtils.getDateTime());
	                		if("0".equals(chongdianzhuantai)){
	                			chargingPilePO_.setCp_status("1");
	                		}else if("0".equals(chongdianzhuantai)){
	                			chargingPilePO_.setCp_status("0");
	                		}else{
	                			chargingPilePO_.setCp_status(chongdianzhuantai);
	                		}
	                		chargingPilePO_.setElectricity(new BigDecimal(AOSCxt.getParam("electricity")));
	                		
	                		chargingPileDao.updateByKey(chargingPilePO_);
	                	}
	                	
	                	if(null==devicePO)
	                	{
	                		System.out.println("yes");
	                		
	                		DevicePO  devicePO_=new DevicePO();
	                		devicePO_.setId_(this.ascii1);
	                		devicePO_.setDevice_id(Corp_ID);
	                		//devicePO_.setId_("10000");
	                		//chargingPilePO_.setCp_no(this.ascii);
	                		//chargingPilePO_.setCp_type(TYPE_ID);
	                		//chargingPilePO_.setSupplier(Corp_ID);
	                		//chargingPilePO_.setCreate_date(AOSUtils.getDateTime());
	                		//chargingPilePO_.setOper_id("socket");
	                		//if("0".equals(chongdianzhuantai)){
	                		//	chargingPilePO_.setCp_status("1");
	                		//}else if("0".equals(chongdianzhuantai)){
	                		//	chargingPilePO_.setCp_status("0");
	                		//}else{
	                		//	chargingPilePO_.setCp_status(chongdianzhuantai);
	                		//}
	                		
	                		//chargingPilePO_.setElectricity(new BigDecimal(AOSCxt.getParam("electricity")));
	                		
	                		deviceDao.insert(devicePO_);
	                		System.out.println("yes two");
	                		
	                	}else {//已存在，则修改状态
	                		System.out.println("no");
	                	}
	                	
	                	//String content="注册转换后参数：：：充电状态"+chongdianzhuantai+"  ICode[1]:"+ICode1+"  ICode[2]:"+ICode2+"  ICode[3]:"+ICode3+"  ICode[4]:"+ICode4+"  ICode[5]:"+ICode5+"  ICode[6]:"+ICode6+"  ICode[7]:"+ICode7+"  ICode[8]:"+ICode8+"  ICode[9]:"+ICode9+"  ICode[10]:"+ICode10+"  ICode[11]:"+ICode11+"  ICode[12]:"+ICode12+"  ICode[13]:"+ICode13+"  ICode[14]:"+ICode14+"  ICode[15]:"+ICode15+"  ICode[16]:"+ICode16+"  TYPE_ID:"+TYPE_ID+"  Corp_ID:"+Corp_ID;
	                	String content="CS①注册转换后参数：：：ascii:"+this.ascii+" 充电状态:"+chongdianzhuantai+" 桩的型号:"+TYPE_ID+" 桩生产公司:"+Corp_ID;
	                	System.out.println(content);
	                	this.saveLogs(content,this.ascii,"CS①");
	                }else if(1==1){//心跳
	                	/*
	                	String hex_1 = hex.substring(0,2);
	                	byte databuffer1 =Byte.parseByte(hex.substring(0,2));
	                	byte databuffer2 =(byte) Integer.parseInt(String.valueOf(hex_byte, 2, 2));
	                	byte databuffer3 =(byte) Integer.parseInt(String.valueOf(hex_byte, 4, 2));
	                	byte databuffer4 =(byte) Integer.parseInt(String.valueOf(hex_byte, 6, 2));
	                	byte databuffer5 =(byte) Integer.parseInt(String.valueOf(hex_byte, 8, 2));
	                	byte databuffer6 =(byte) Integer.parseInt(String.valueOf(hex_byte, 10, 2));
	                	byte databuffer7 =(byte) Integer.parseInt(String.valueOf(hex_byte, 12, 2));
	                	byte databuffer8 =(byte) Integer.parseInt(String.valueOf(hex_byte, 14, 2));
	                	byte databuffer9 =(byte) Integer.parseInt(String.valueOf(hex_byte, 16, 2));
	                	byte databuffer10 =(byte) Integer.parseInt(String.valueOf(hex_byte, 18, 2));
	                	byte databuffer11 =(byte) Integer.parseInt(String.valueOf(hex_byte, 20, 2));
	                	byte databuffer12 =(byte) Integer.parseInt(String.valueOf(hex_byte, 22, 2));
	                	byte databuffer13 =(byte) Integer.parseInt(String.valueOf(hex_byte, 24, 2));
	                	byte databuffer14 =(byte) Integer.parseInt(String.valueOf(hex_byte, 26, 2));
	                	byte databuffer15 =(byte) Integer.parseInt(String.valueOf(hex_byte, 28, 2));
	                	byte databuffer16 =(byte) Integer.parseInt(String.valueOf(hex_byte, 30, 2));
	                	
	                	byte[] databuffer = {databuffer1,databuffer2,databuffer3,databuffer4,databuffer5,
	                			databuffer6,databuffer7,databuffer8,databuffer9,databuffer10,
	                			databuffer11,databuffer12,databuffer13,databuffer14,databuffer15,databuffer16};
	                			*/
	                	System.out.println("12345");
	                	byte[] databuffer = new byte[16];
	                	for(int k=0;k<16;k++)
	                		databuffer[k] = data_in[k];
	                	
	                	byte[] databuffer1= {0x5A,0x29,0x1C,0x6E,0x41,(byte) 0xC0,(byte) 0xAE,0x3E,0x7D,(byte) 0xD4,(byte) 0xBA,(byte) 0xC0,0x2F,0x69,0x3F,(byte) 0xF6};
	            		byte[] Key1={0x20,0x37,0x62,0x39,0x31,0x31,0x31,0x37,0x63,0x30,0x30,0x32,0x31,0x38,0x33,0x33};
	            		byte[] data1 = new byte[16];
	                	
	                	Object[] object = new Object[]{ databuffer , Key,data1};
	            		//string =
	            		//System.out.println(sumFunc.invokePointer(object));
	            		String b= sumFunc.invokeString(object, false);
	            		//String s = sumFunc.invokeObject(object).toString();
	            		System.out.println("b:"+b);
	            		String c = strTo16(b.substring(1));
	            		System.out.println(c);
	            		//System.out.println(c.substring(2, 3));
	            		System.out.println(c.substring(2, 4));
	            		//System.out.println(c.substring(4, 5));
	            		System.out.println(c.substring(4, 6));
	            		if(c.substring(2, 4).equals("4f")&&c.substring(4, 6).equals("4b"))
	            			System.out.println("xintiao");
	            		//if(c.substring(2, 3))
	            		//String a = sumFunc.invokeString(object, false);
	            		//Pointer b = sumFunc.invokePointer(object);
	                	
	                	
	                	
	                }else if("03".equals(msgType)){//经纬度
	                	System.out.println("经纬度："+hex);
	                	 this.saveLogs("CS③经纬度原始数据:"+hex,this.ascii,"CS③");
	                	//经纬度信息
	                	String Sig_ID=new BigInteger(String.valueOf(hex_byte, 6, 2), 16).toString();
	                	/*String jindu_a=new BigInteger(String.valueOf(hex_byte, 8, 2), 16).toString();
	                	String jindu_b=new BigInteger(String.valueOf(hex_byte, 10, 2), 16).toString();
	                	String jindu_c=new BigInteger(String.valueOf(hex_byte, 12, 2), 16).toString();
	                	String jindu_d=new BigInteger(String.valueOf(hex_byte, 14, 2), 16).toString();
	                	String jindu_e=new BigInteger(String.valueOf(hex_byte, 16, 2), 16).toString();
	                	String jindu_f=new BigInteger(String.valueOf(hex_byte, 18, 2), 16).toString();
	                	String jindu_g=new BigInteger(String.valueOf(hex_byte, 20, 2), 16).toString();
	                	String jindu_h=new BigInteger(String.valueOf(hex_byte, 22, 2), 16).toString();
	                	String jindu_j=new BigInteger(String.valueOf(hex_byte, 24, 2), 16).toString();
	                	String weidu_a=new BigInteger(String.valueOf(hex_byte, 26, 2), 16).toString();
	                	String weidu_b=new BigInteger(String.valueOf(hex_byte, 28, 2), 16).toString();
	                	String weidu_c=new BigInteger(String.valueOf(hex_byte, 30, 2), 16).toString();
	                	String weidu_d=new BigInteger(String.valueOf(hex_byte, 32, 2), 16).toString();
	                	String weidu_e=new BigInteger(String.valueOf(hex_byte, 34, 2), 16).toString();
	                	String weidu_f=new BigInteger(String.valueOf(hex_byte, 36, 2), 16).toString();
	                	String weidu_g=new BigInteger(String.valueOf(hex_byte, 38, 2), 16).toString();
	                	String weidu_h=new BigInteger(String.valueOf(hex_byte, 40, 2), 16).toString();*/
	                	String jindu_a=Helper.AsciiStringToString(String.valueOf(hex_byte, 8, 2));
	                	String jindu_b=Helper.AsciiStringToString(String.valueOf(hex_byte, 10, 2));
	                	String jindu_c=Helper.AsciiStringToString(String.valueOf(hex_byte, 12, 2));
	                	String jindu_d=Helper.AsciiStringToString(String.valueOf(hex_byte, 14, 2));
	                	String jindu_e=Helper.AsciiStringToString(String.valueOf(hex_byte, 16, 2));
	                	String jindu_f=Helper.AsciiStringToString(String.valueOf(hex_byte, 18, 2));
	                	String jindu_g=Helper.AsciiStringToString(String.valueOf(hex_byte, 20, 2));
	                	String jindu_h=Helper.AsciiStringToString(String.valueOf(hex_byte, 22, 2));
	                	String jindu_i=Helper.AsciiStringToString(String.valueOf(hex_byte, 24, 2));
	                	String jindu_j=Helper.AsciiStringToString(String.valueOf(hex_byte, 26, 2));
	                	String weidu_a=Helper.AsciiStringToString(String.valueOf(hex_byte, 28, 2));
	                	String weidu_b=Helper.AsciiStringToString(String.valueOf(hex_byte, 30, 2));
	                	String weidu_c=Helper.AsciiStringToString(String.valueOf(hex_byte, 32, 2));
	                	String weidu_d=Helper.AsciiStringToString(String.valueOf(hex_byte, 34, 2));
	                	String weidu_e=Helper.AsciiStringToString(String.valueOf(hex_byte, 36, 2));
	                	String weidu_f=Helper.AsciiStringToString(String.valueOf(hex_byte, 38, 2));
	                	String weidu_g=Helper.AsciiStringToString(String.valueOf(hex_byte, 40, 2));
	                	String weidu_h=Helper.AsciiStringToString(String.valueOf(hex_byte, 42, 2));
	                	String weidu_i=Helper.AsciiStringToString(String.valueOf(hex_byte, 44, 2));
	                	//String Satellite=new BigInteger(String.valueOf(hex_byte, 42, 2), 16).toString();
	                	String Satellite=String.valueOf(hex_byte, 42, 2);
	                	if("0x0A".equals(Satellite)){
	                		Satellite="10";
	                	}else if("0x01".equals(Satellite)){
	                		Satellite="10";
	                	}else if("0x02".equals(Satellite)){
	                		Satellite="2";
	                	}else if("0x03".equals(Satellite)){
	                		Satellite="3";
	                	}else if("0x04".equals(Satellite)){
	                		Satellite="4";
	                	}else if("0x05".equals(Satellite)){
	                		Satellite="5";
	                	}else if("0x06".equals(Satellite)){
	                		Satellite="6";
	                	}else if("0x07".equals(Satellite)){
	                		Satellite="7";
	                	}else if("0x08".equals(Satellite)){
	                		Satellite="8";
	                	}else if("0x09".equals(Satellite)){
	                		Satellite="9";
	                	}else{
	                		Satellite="1";
	                	}
	                	
	                	
	                	String ascii_msg="";
	                	if(AOSUtils.isEmpty(this.ascii)){
	                		ascii_msg="：：：：：没有收到CS①注册数据";
	                	}
	                	String content="CS③经纬度转换后参数：：：Sig_ID:"+Sig_ID+"  经度（度）A:"
	                	+jindu_a+"  经度（度）B:"+jindu_b+"  经度（度）C:"+jindu_c+"  经度（分）D:"
	                			+jindu_d+"  经度（分）E:"+jindu_e+"  经度（分）F:"+jindu_f+
	                			"  经度（分）G:"+jindu_g+"  经度（分）H:"+jindu_h+"  经度（分）I:"+jindu_i+"  经度（分）J:"+jindu_j+
	                			"  维度（度）A:"+weidu_a+"  维度（度）B:"+weidu_b+"  维度（分）C:"+weidu_c+
	                			"  维度（分）D:"+weidu_d+"  维度（分）E:"+weidu_e+"  维度（分）F:"+weidu_f+
	                			"  维度（分）G:"+weidu_g+"  维度（分）H:"+weidu_h+"  维度（分）I:"+weidu_i+"  Satellite:"+Satellite+ascii_msg;
	                	System.out.println(content);
	                	this.saveLogs(content,this.ascii,"CS③");
	                	ChargingPilePO  chargingPilePO=chargingPileDao.selectByKey(this.ascii);
	                	String jindu="";
	                	String weidu="";
	                	int n=5;
	                	if(null!=chargingPilePO){//修改
	                		ChargingPilePO  chargingPilePO_=new ChargingPilePO();
	                		chargingPilePO_.setCp_id(this.ascii);
	                		 jindu=jindu_d+jindu_e+jindu_f+jindu_g+jindu_h+jindu_i+jindu_j;
	                		 weidu=weidu_c+weidu_d+weidu_e+weidu_f+weidu_g+weidu_h+weidu_i;
	                		jindu=new BigDecimal(jindu).divide(new BigDecimal("60"),0,BigDecimal.ROUND_HALF_DOWN).toString();
	                		jindu=jindu.length()>=6?jindu.substring(0, 5):jindu;
	                		chargingPilePO_.setLon(jindu_a+jindu_b+jindu_c+"."+jindu);
	                		weidu=new BigDecimal(weidu).divide(new BigDecimal("60"),0,BigDecimal.ROUND_HALF_DOWN).toString();
	                		weidu=weidu.length()>=6?weidu.substring(0, 5):weidu;
	                		chargingPilePO_.setLat(weidu_a+weidu_b+"."+weidu);
	                		chargingPilePO_.setUpdate_date(AOSUtils.getDateTime());
	                		try{
		                		Map<String,String> locationMap=MultiServerThread.getLocationinfor(weidu_a+weidu_b+"."+weidu+","+jindu_a+jindu_b+jindu_c+"."+jindu);
		                		if(null!=locationMap){
		                			String province=locationMap.get("province");
		                			chargingPilePO_.setProvince(province);
		                			String city=locationMap.get("city");
		                			chargingPilePO_.setCity(city);
		                			String dist=locationMap.get("dist");
		                			chargingPilePO_.setCounty(dist);
		                			String area=locationMap.get("area");
		                			String town=locationMap.get("town");
		                			String village=locationMap.get("village");
		                			String poi=locationMap.get("poi");
		                			String direction=locationMap.get("direction");
		                			chargingPilePO_.setAddr(area+town+village+poi+direction);
		                		}
	                		}catch (Exception e) {
								e.printStackTrace();
							}
	                		
	                		chargingPileDao.updateByKey(chargingPilePO_);
	                	}
	                	//确认经纬度 "E20303"
	                	String que_ren_jin_wei=Helper.fillString('0', 32*2);
	                	que_ren_jin_wei=que_ren_jin_wei.replaceFirst("^000000", "E20303");
	                	out.write(Helper.hexStringToByteArray(que_ren_jin_wei));
	                	content="SC③服务器发出确认经纬度指令："+que_ren_jin_wei;
	                	this.saveLogs(content,this.ascii,"SC③");
	                }else if("04".equals(msgType)){//充电指令
	                	System.out.println("充电指令确认："+hex);
	                	this.saveLogs("CS④充电确认指令原始数据:"+hex,this.ascii,"CS④");
	                	//充电指令确认信息
	                	String is_tongyi=new BigInteger(String.valueOf(hex_byte, 6, 2), 16).toString();
	                	String reason=new BigInteger(String.valueOf(hex_byte, 8, 2), 16).toString();
	                	Dto pDto=Dtos.newDto("cp_id",this.ascii);
                		ChargingOrdersPO chargingOrdersPO=new ChargingOrdersPO();
                		List<Dto> list=sqlDao.list("ChargingOrders.listChargingOrderssSocket1", pDto);
                		if(null!=list&&list.size()>0){
                			System.out.println("list.get(0)"+list.get(0).getString("co_id"));
                			AOSUtils.copyProperties(list.get(0), chargingOrdersPO);
                			ChargingPilePO chargingPilePO=new ChargingPilePO();
                			if("1".equals(is_tongyi)){
    	                		chargingOrdersPO.setStatus_("0");
    	                		//更新充电桩为占用状态
    	                		chargingPilePO.setCp_status("2");
    	                		chargingPilePO.setCp_id(this.ascii);
    	                		chargingPilePO.setUpdate_date(AOSUtils.getDateTime());
    	                		chargingPileDao.updateByKey(chargingPilePO);
    	                	}else{
    	                		chargingOrdersPO.setStatus_("-3");
    	                		if("1".equals(reason)){
    	                			reason="未插枪";
    	                			chargingOrdersPO.setPut_gun("0");
    	                		}else if("2".equals(reason)){
    	                			reason="充电桩未激活";
    	                			chargingPilePO.setCp_status("3");
    	                			chargingPilePO.setIs_activation("0");
    	                			
    	                			String que_ren_xin_tiao=Helper.fillString('0', 32*2);
    	                			que_ren_xin_tiao=Helper.fillString('0', 32*2);
    			                	que_ren_xin_tiao=que_ren_xin_tiao.replaceFirst("^00000000", "E20A0401");
    			                	String content="SC⑩服务器发出激活指令："+que_ren_xin_tiao;
    			                	System.out.println(content);
    			                	this.saveLogs(content,this.ascii);
    			                	out.write(Helper.hexStringToByteArray(que_ren_xin_tiao));
    	                		}else if("3".equals(reason)){
    	                			reason="充电桩占用";
    	                			chargingPilePO.setCp_status("2");
    	                		}else{
    	                			reason="其它原因";
    	                			chargingPilePO.setCp_status("1");//更新充电桩为可用状态
    	                		} 
    	                		chargingOrdersPO.setReason(reason);
    	                		chargingPilePO.setCp_id(this.ascii);
    	                		chargingPilePO.setUpdate_date(AOSUtils.getDateTime());
    	                		chargingPileDao.updateByKey(chargingPilePO);
    	                	}
                			//更新已充电金额
                			if(null!=chargingOrdersPO.getElectricity()){
                			chargingOrdersPO.setAmounted(chargingOrdersPO.getElectricity().multiply(new BigDecimal(AOSCxt.getParam("electricity"))));
                			chargingOrdersPO.setTotal_amt(chargingOrdersPO.getAmounted());
                			chargingOrdersPO.setDeduction_amt(new BigDecimal("0"));
                			chargingOrdersPO.setReal_amt(chargingOrdersPO.getTotal_amt());
                			}
                			chargingOrdersDao.updateByKey(chargingOrdersPO);
                		}
                		String ascii_msg="";
	                	if(AOSUtils.isEmpty(this.ascii)){
	                		ascii_msg="：：：：：没有收到CS①注册数据";
	                	}
	                	String content="CS④充电确认指令转换后参数：：：是否同意:"+is_tongyi+":::::"+chargingOrdersPO.getCo_id()+"：：：不同意原因:"+reason+":::::status_"+chargingOrdersPO.getStatus_()+ascii_msg;
	                	System.out.println(content);
	                	this.saveLogs(content,this.ascii,"CS④");
	                }else if("05".equals(msgType)){//开始充电
	                	System.out.println("开始充电："+hex);
	                	this.saveLogs("CS⑤开始充电原始数据:"+hex,this.ascii,"CS⑤");
	                	//开始充电信息
	                	String shifouchaqian=new BigInteger(String.valueOf(hex_byte, 6, 2), 16).toString();
	                	String jindu_a=new BigInteger(String.valueOf(hex_byte, 8, 2), 16).toString();
	                	String jindu_b=new BigInteger(String.valueOf(hex_byte, 10, 2), 16).toString();
	                	String jindu_c=new BigInteger(String.valueOf(hex_byte, 12, 2), 16).toString();
	                	String jindu_d=new BigInteger(String.valueOf(hex_byte, 14, 2), 16).toString();
	                	String jindu_e=new BigInteger(String.valueOf(hex_byte, 16, 2), 16).toString();
	                	String jindu_f=new BigInteger(String.valueOf(hex_byte, 18, 4), 16).toString();
	                	
	                	String ascii_msg="";
	                	if(AOSUtils.isEmpty(this.ascii)){
	                		ascii_msg="：：：：：没有收到CS①注册数据";
	                	}
	                	String content="CS⑤开始充电转换后参数：：：是否插枪:"+shifouchaqian+"  充电状态:"
	                	+jindu_a+"  故障码:"+jindu_b+"  已充电时间（天）:"+jindu_c+"  已充电时间（时）:"
	                			+jindu_d+"  已充电时间（分）:"+jindu_e+"  已充电度数:"+jindu_f+ascii_msg;
	                	System.out.println(content);
	                	
	                	ChargingPilePO  chargingPilePO_=new ChargingPilePO();
                		chargingPilePO_.setCp_id(this.ascii);
                		if("0".equals(jindu_a)){
                			chargingPilePO_.setCp_status("1");
                		}else if("3".equals(jindu_a)){
                			chargingPilePO_.setCp_status("3");
                			chargingPilePO_.setFault_code(jindu_b);
                		}else if("1".equals(jindu_a)){
                			chargingPilePO_.setCp_status("2");
                		}else if("2".equals(jindu_a)&&"1".equals(shifouchaqian)){
                			chargingPilePO_.setCp_status("2");
                		}else if("2".equals(jindu_a)&&"0".equals(shifouchaqian)){
                			chargingPilePO_.setCp_status("1");
                		}else{
                			chargingPilePO_.setCp_status("1");
                		}
                		chargingPilePO_.setUpdate_date(AOSUtils.getDateTime());
                		chargingPileDao.updateByKey(chargingPilePO_);
	                	
	                	this.saveLogs(content,this.ascii,"CS⑤");
	                	Dto pDto=Dtos.newDto("cp_id",this.ascii);
                		ChargingOrdersPO chargingOrdersPO=new ChargingOrdersPO();
                		List<Dto> list=sqlDao.list("ChargingOrders.listChargingOrderssSocket", pDto);
                		if(null!=list&&list.size()>0){
                			AOSUtils.copyProperties(list.get(0), chargingOrdersPO);
                			chargingOrdersPO.setElectricity(new BigDecimal(jindu_f));
                			BigDecimal yichongdian_tian_=new BigDecimal(jindu_c).multiply(new BigDecimal(24*60));
                			BigDecimal yichongdian_shi_=new BigDecimal(jindu_d).multiply(new BigDecimal(60));
                			chargingOrdersPO.setDateed(yichongdian_tian_.add(yichongdian_shi_).add(new BigDecimal(jindu_e)));
                			chargingOrdersPO.setPut_gun(shifouchaqian);
                			
                			//充满或故障，订单充电完成
                			if("2".equals(jindu_a)||"3".equals(jindu_a)){
                				chargingOrdersPO.setStatus_("1");
                				chargingOrdersPO.setComplete_date(AOSUtils.getDateTime());
                			}else if("1".equals(jindu_a)){
                				chargingOrdersPO.setStatus_("0");
                			}else{
                				chargingOrdersPO.setStatus_("-2");
                			}
                			/*if("0".equals(shifouchaqian)&&"1".equals(chargingOrdersPO.getStatus_())){//拔枪
                				chargingOrdersPO.setPuted_gun_date(AOSUtils.getDateTime());
                			}else if("1".equals(shifouchaqian)&&"0".equals(jindu_a)){//插枪
                				chargingOrdersPO.setPut_gun_date(AOSUtils.getDateTime());
                			}*/
                			//更新已充电金额
                			if(null!=chargingOrdersPO.getElectricity()){
                			chargingOrdersPO.setAmounted(chargingOrdersPO.getElectricity().multiply(new BigDecimal(AOSCxt.getParam("electricity"))));
                			chargingOrdersPO.setTotal_amt(chargingOrdersPO.getAmounted());
                			chargingOrdersPO.setDeduction_amt(new BigDecimal("0"));
                			chargingOrdersPO.setReal_amt(chargingOrdersPO.getTotal_amt());
                			}
                			chargingOrdersDao.updateByKey(chargingOrdersPO);
                		}
	                	
	                }else if("06".equals(msgType)){//插枪指令
	                	System.out.println("插枪指令："+hex);
	                	this.saveLogs("CS⑥插枪指令原始数据:"+hex,this.ascii,"CS⑥");
	                	//插枪指令
	                	String shifouchaqiang=new BigInteger(String.valueOf(hex_byte, 6, 2), 16).toString();
	                	String chongdianzhuantai=new BigInteger(String.valueOf(hex_byte, 8, 2), 16).toString();
	                	String guzhangma=new BigInteger(String.valueOf(hex_byte, 10, 2), 16).toString();
	                	String yichongdian_tian=new BigInteger(String.valueOf(hex_byte, 12, 2), 16).toString();
	                	String yichongdian_shi=new BigInteger(String.valueOf(hex_byte, 14, 2), 16).toString();
	                	String yichongdian_fen=new BigInteger(String.valueOf(hex_byte, 16, 2), 16).toString();
	                	String yichongdiandushu=new BigInteger(String.valueOf(hex_byte, 18, 4), 16).toString();
	                	//BigInteger srch = new BigInteger(h, 16);//十六进制转十进制
	                	
	                	ChargingPilePO  chargingPilePO_=new ChargingPilePO();
                		chargingPilePO_.setCp_id(this.ascii);
                		if("0".equals(chongdianzhuantai)){
                			chargingPilePO_.setCp_status("1");
                		}else if("3".equals(chongdianzhuantai)){
                			chargingPilePO_.setCp_status("3");
                			chargingPilePO_.setFault_code(guzhangma);
                		}else if("1".equals(chongdianzhuantai)){
                			chargingPilePO_.setCp_status("2");
                		}else if("2".equals(chongdianzhuantai)&&"1".equals(shifouchaqiang)){
                			chargingPilePO_.setCp_status("2");
                		}else if("2".equals(chongdianzhuantai)&&"0".equals(shifouchaqiang)){
                			chargingPilePO_.setCp_status("1");
                		}else{
                			chargingPilePO_.setCp_status("1");
                		}
                		chargingPilePO_.setUpdate_date(AOSUtils.getDateTime());
                		chargingPileDao.updateByKey(chargingPilePO_);
	                	
	                	
	                	Dto pDto=Dtos.newDto("cp_id",this.ascii);
                		ChargingOrdersPO chargingOrdersPO=new ChargingOrdersPO();
                		List<Dto> list=sqlDao.list("ChargingOrders.listChargingOrderssSocket", pDto);
                		if(null!=list&&list.size()>0){
                			AOSUtils.copyProperties(list.get(0), chargingOrdersPO);
                			BigDecimal yichongdian_tian_=new BigDecimal(yichongdian_tian).multiply(new BigDecimal(24*60));
                			BigDecimal yichongdian_shi_=new BigDecimal(yichongdian_shi).multiply(new BigDecimal(60));
                			chargingOrdersPO.setPut_gun(shifouchaqiang);
                			chargingOrdersPO.setDateed(yichongdian_tian_.add(yichongdian_shi_).add(new BigDecimal(yichongdian_fen)));//保存换算为分钟
                			//充满或故障，订单充电完成
                			if("2".equals(chongdianzhuantai)||"3".equals(chongdianzhuantai)){
                				chargingOrdersPO.setStatus_("1");
                				chargingOrdersPO.setComplete_date(AOSUtils.getDateTime());
                			}
                			if("1".equals(shifouchaqiang)&&"0".equals(chongdianzhuantai)){//插枪
                				chargingOrdersPO.setPut_gun_date(AOSUtils.getDateTime());
                			}
                			
                			//更新已充电金额
                			if(null!=chargingOrdersPO.getElectricity()){
                			chargingOrdersPO.setAmounted(chargingOrdersPO.getElectricity().multiply(new BigDecimal(AOSCxt.getParam("electricity"))));
                			chargingOrdersPO.setTotal_amt(chargingOrdersPO.getAmounted());
                			chargingOrdersPO.setDeduction_amt(new BigDecimal("0"));
                			chargingOrdersPO.setReal_amt(chargingOrdersPO.getTotal_amt());
                			}
                			chargingOrdersDao.updateByKey(chargingOrdersPO);
                		}
                		
                		String ascii_msg="";
	                	if(AOSUtils.isEmpty(this.ascii)){
	                		ascii_msg="：：：：：没有收到CS①注册数据";
	                	}
	                	String content="CS⑥插枪指定转换后参数：：：是否插枪:"+shifouchaqiang+"  充电状态:"+chongdianzhuantai+"  故障码:"+guzhangma+"  已充电时间（天）:"+yichongdian_tian+"  已充电时间（时）:"+yichongdian_shi+"  已充电时间（分）:"+yichongdian_fen+"  已充电度数:"+yichongdiandushu+ascii_msg;
	                	System.out.println(content);
	                	this.saveLogs(content,this.ascii,"CS⑥");
	                }else if("07".equals(msgType)){//停止充电指令（桩发起）
	                	System.out.println("停止充电指令（桩发起）："+hex);
	                	this.saveLogs("CS⑦停止充电指令（桩发起）原始数据:"+hex,this.ascii,"CS⑦");
	                	//停止充电指令（桩发起）信息
	                	String shifouchaqian=new BigInteger(String.valueOf(hex_byte, 6, 2), 16).toString();
	                	String jindu_a=new BigInteger(String.valueOf(hex_byte, 8, 2), 16).toString();
	                	String jindu_b=new BigInteger(String.valueOf(hex_byte, 10, 2), 16).toString();
	                	String jindu_c=new BigInteger(String.valueOf(hex_byte, 12, 2), 16).toString();
	                	String jindu_d=new BigInteger(String.valueOf(hex_byte, 14, 2), 16).toString();
	                	String jindu_e=new BigInteger(String.valueOf(hex_byte, 16, 2), 16).toString();
	                	String jindu_f=new BigInteger(String.valueOf(hex_byte, 18, 4), 16).toString();
	                	String jindu_g=new BigInteger(String.valueOf(hex_byte, 22, 2), 16).toString();
	                	
	                	Dto pDto=Dtos.newDto("cp_id",this.ascii);
                		ChargingOrdersPO chargingOrdersPO=new ChargingOrdersPO();
                		List<Dto> list=sqlDao.list("ChargingOrders.listChargingOrderssSocket", pDto);
                		if(null!=list&&list.size()>0){
                			AOSUtils.copyProperties(list.get(0), chargingOrdersPO);
                			BigDecimal yichongdian_tian_=new BigDecimal(jindu_c).multiply(new BigDecimal(24*60));
                			BigDecimal yichongdian_shi_=new BigDecimal(jindu_d).multiply(new BigDecimal(60));
                			chargingOrdersPO.setElectricity(new BigDecimal(jindu_f));
                			chargingOrdersPO.setPut_gun(shifouchaqian);
                			chargingOrdersPO.setPut_gun(shifouchaqian);
                			chargingOrdersPO.setDateed(yichongdian_tian_.add(yichongdian_shi_).add(new BigDecimal(jindu_e)));//保存换算为分钟
                			ChargingPilePO  chargingPilePO=chargingPileDao.selectByKey(this.ascii);
                			chargingOrdersPO.setTotal_amt(chargingOrdersPO.getDateed().multiply(chargingPilePO.getElectricity()));
                			chargingOrdersPO.setDeduction_amt(new BigDecimal("0"));
                			chargingOrdersPO.setReal_amt(chargingOrdersPO.getTotal_amt());
                			//订单充电完成
                				chargingOrdersPO.setStatus_("1");
                				chargingOrdersPO.setComplete_date(AOSUtils.getDateTime());
                			if("0".equals(shifouchaqian)){//拔枪
                				chargingOrdersPO.setPuted_gun_date(AOSUtils.getDateTime());
                			}
                			//更新已充电金额
                			if(null!=chargingOrdersPO.getElectricity()){
                			chargingOrdersPO.setAmounted(chargingOrdersPO.getElectricity().multiply(new BigDecimal(AOSCxt.getParam("electricity"))));
                			chargingOrdersPO.setTotal_amt(chargingOrdersPO.getAmounted());
                			chargingOrdersPO.setDeduction_amt(new BigDecimal("0"));
                			chargingOrdersPO.setReal_amt(chargingOrdersPO.getTotal_amt());
                			}
                			chargingOrdersDao.updateByKey(chargingOrdersPO);
                			
    	                	if("0".equals(shifouchaqian)){//未插枪
    	                		ChargingPilePO  chargingPilePO_=new ChargingPilePO();
    	                		chargingPilePO_.setCp_id(this.ascii);
    	                		chargingPilePO_.setCp_status("1");//设为可用
    	                		chargingPilePO_.setUpdate_date(AOSUtils.getDateTime());
    	                		chargingPileDao.updateByKey(chargingPilePO_);
    	                	}
                		}
                		
                		String ascii_msg="";
	                	if(AOSUtils.isEmpty(this.ascii)){
	                		ascii_msg="：：：：：没有收到CS①注册数据";
	                	}
	                	String content="CS⑦停止充电指令（桩发起）转换后参数：：：是否插枪:"+shifouchaqian+"  充电状态:"
	                	+jindu_a+"  故障码:"+jindu_b+"  已充电时间（天）:"+jindu_c+"  已充电时间（时）:"
	                			+jindu_d+"  已充电时间（分）:"+jindu_e+"  已充电度数:"+jindu_f+"  停止方式:"+jindu_g+ascii_msg;
	                	System.out.println(content);
	                	this.saveLogs(content,this.ascii,"CS⑦");
	                	//确认停止充电 "E20703"
	                	String que_ren_xin_tiao=Helper.fillString('0', 32*2);
	                	que_ren_xin_tiao=que_ren_xin_tiao.replaceFirst("^000000", "E20703");
	                	out.write(Helper.hexStringToByteArray(que_ren_xin_tiao));
	                	content="SC⑦服务器发送确认停止充电指令："+que_ren_xin_tiao;
	                	this.saveLogs(content,this.ascii,"SC⑦");
	                }else if("08".equals(msgType)){//停止充电指令（APP发起）
	                	System.out.println("停止充电指令（APP发起）："+hex);
	                	this.saveLogs("CS⑧接收到的停止充电指令（APP发起）原始数据:"+hex,this.ascii,"CS⑧");
	                	//停止充电指令（APP发起）信息
	                	String shifouchaqian=new BigInteger(String.valueOf(hex_byte, 6, 2), 16).toString();
	                	String jindu_a=new BigInteger(String.valueOf(hex_byte, 8, 2), 16).toString();
	                	String jindu_b=new BigInteger(String.valueOf(hex_byte, 10, 2), 16).toString();
	                	String jindu_c=new BigInteger(String.valueOf(hex_byte, 12, 2), 16).toString();
	                	String jindu_d=new BigInteger(String.valueOf(hex_byte, 14, 2), 16).toString();
	                	String jindu_e=new BigInteger(String.valueOf(hex_byte, 16, 2), 16).toString();
	                	String jindu_f=new BigInteger(String.valueOf(hex_byte, 18, 4), 16).toString();
	                	
	                	Dto pDto=Dtos.newDto("cp_id",this.ascii);
                		ChargingOrdersPO chargingOrdersPO=new ChargingOrdersPO();
                		List<Dto> list=sqlDao.list("ChargingOrders.listChargingOrderssSocket", pDto);
                		if(null!=list&&list.size()>0){
                			AOSUtils.copyProperties(list.get(0), chargingOrdersPO);
                			BigDecimal yichongdian_tian_=new BigDecimal(jindu_c).multiply(new BigDecimal(24*60));
                			BigDecimal yichongdian_shi_=new BigDecimal(jindu_d).multiply(new BigDecimal(60));
                			chargingOrdersPO.setElectricity(new BigDecimal(jindu_f));
                			chargingOrdersPO.setPut_gun(shifouchaqian);
                			chargingOrdersPO.setDateed(yichongdian_tian_.add(yichongdian_shi_).add(new BigDecimal(jindu_e)));//保存换算为分钟
                			//ChargingPilePO  chargingPilePO=chargingPileDao.selectByKey(this.ascii);
                			//chargingOrdersPO.setTotal_amt(chargingOrdersPO.getDateed().multiply(chargingPilePO.getElectricity()));
                			//chargingOrdersPO.setDeduction_amt(new BigDecimal("0"));
                			//chargingOrdersPO.setReal_amt(chargingOrdersPO.getTotal_amt());
                			//订单充电完成
            				chargingOrdersPO.setStatus_("1");
            				chargingOrdersPO.setComplete_date(AOSUtils.getDateTime());
	            			if("0".equals(shifouchaqian)){//拔枪
	            				chargingOrdersPO.setPuted_gun_date(AOSUtils.getDateTime());
	            			}
	            			//更新已充电金额
	            			if(null!=chargingOrdersPO.getElectricity()){
                			chargingOrdersPO.setAmounted(chargingOrdersPO.getElectricity().multiply(new BigDecimal(AOSCxt.getParam("electricity"))));
                			chargingOrdersPO.setTotal_amt(chargingOrdersPO.getAmounted());
                			chargingOrdersPO.setDeduction_amt(new BigDecimal("0"));
                			chargingOrdersPO.setReal_amt(chargingOrdersPO.getTotal_amt());
	            			}
	            			chargingOrdersDao.updateByKey(chargingOrdersPO);
    	                	if("0".equals(shifouchaqian)){//未插枪
    	                		ChargingPilePO  chargingPilePO_=new ChargingPilePO();
    	                		chargingPilePO_.setCp_id(this.ascii);
    	                		chargingPilePO_.setCp_status("1");//设为可用
    	                		chargingPilePO_.setUpdate_date(AOSUtils.getDateTime());
    	                		chargingPileDao.updateByKey(chargingPilePO_);
    	                	}
                		}
                		
                		String ascii_msg="";
	                	if(AOSUtils.isEmpty(this.ascii)){
	                		ascii_msg="：：：：：没有收到CS①注册数据";
	                	}
	                	String content="CS⑧停止充电指令（APP发起）转换后参数：：：是否插枪:"+shifouchaqian+"  充电状态:"
	                	+jindu_a+"  故障码:"+jindu_b+"  已充电时间（天）:"+jindu_c+"  已充电时间（时）:"
	                			+jindu_d+"  已充电时间（分）:"+jindu_e+"  已充电度数:"+jindu_f+ascii_msg;
	                	System.out.println(content);
	                	this.saveLogs(content,this.ascii,"CS⑧");
	                }else if("09".equals(msgType)){//修改IP指令
	                	String content="修改IP指令："+hex;
	                	System.out.println(content);
	                	this.saveLogs(content,this.ascii);
	                	
	                }else if("0A".equals(msgType)){//激活指令
	                	String content="激活指令："+hex;
	                	System.out.println(content);
	                	this.saveLogs(content,this.ascii);
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
}
