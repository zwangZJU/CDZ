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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
				
				boolean fang = false;
				
				
		        
		        
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
	                
	                if("01".equals(msgType)&&"E2".equals(String.valueOf(hex_byte, 0, 2))){//注册
	                	 this.saveLogs("注册原始数据:"+hex,this.ascii,"CS①");
	                	//注册信息
	                	String chongdianzhuantai=new BigInteger(String.valueOf(hex_byte, 6, 2), 16).toString();
	                	String ascii_=Helper.AsciiStringToString(String.valueOf(hex_byte, 8, 18));
	                	this.ascii=ascii_;
	                	
	                	//String Corp_ID=new BigInteger(String.valueOf(hex_byte, 6, 2), 16).toString(); //注册包中的Corp_ID,模块型号
	                	String Corp_ID=String.valueOf(hex_byte, 6, 2);
	                	System.out.println("Corp_ID:"+Corp_ID);
	                	System.out.println(String.valueOf(hex_byte, 48, 14));
	                	
	                	String ascii_1=Helper.AsciiStringToString(String.valueOf(hex_byte, 48, 14));   //注册包中第8位开始后面总共24个位
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
	                	Key= key;  //密钥
	                	
	                	//String str_ACCT = decode(ACCT1+ACCT2+ACCT3+ACCT4);
	                	//String ICode = decode(ICode1+ICode2+ICode3+ICode4+ICode5+ICode6+ICode7+ICode8);
	                	//String str_ascii = decode(this.ascii1);
	                	
	                	String str_ACCT =null;
	                	String ICode = null;
	                	String str_ascii = null;
	                	//String str_ascii = "4gw765431";
	                	
	                	Helper.socketMap.put(this.ascii1, this.socket);//保存socket对象
	                	System.out.println("this.ascii1："+this.ascii1);
	                	if("0x01".equals(Corp_ID)){
	                		Corp_ID="中创公司";
	                	}else{
	                		Corp_ID="xx公司";
	                	}
	                	ChargingPilePO  chargingPilePO=chargingPileDao.selectByKey(this.ascii);
	                	//ChargingPilePO  chargingPilePO=chargingPileDao.selectByKey("1");
	                	
	                	//DevicePO devicePO = deviceDao.selectByKey(this.ascii1);
	                	//DevicePO devicePO = null;
	                	//DevicePO devicePO = deviceDao.selectByKey(Corp_ID);
	                	DevicePO devicePO = deviceDao.selectByKey("4gw765431");
	                	//DevicePO devicePO = deviceDao.selectByKey(ICode);
	                	
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
	                		//devicePO_.setId_(this.ascii1);
	                		//devicePO_.setDevice_id(Corp_ID);
	                		
	                		devicePO_.setDevice_id(str_ascii);
	                		devicePO_.setUser_id(str_ACCT);
	                		deviceDao.insert(devicePO_);
	                		System.out.println("yes two");
	                		
	                	}else {//已存在，则修改状态
	                		Dto pDto;
		            		
		            		//pDto = Dtos.newDto("device_id",str_ascii);
		        			//DevicePO devicePO3=deviceDao.selectOne(pDto);
		        			//devicePO3.setUser_id(str_ACCT);
							//deviceDao.updateByKey(devicePO3);
							
	                		System.out.println("update");
	                	}
	                	
	                	String content="CS①注册转换后参数：：：ascii:"+this.ascii+" 充电状态:"+chongdianzhuantai+" 桩的型号:"+TYPE_ID+" 桩生产公司:"+Corp_ID;
	                	System.out.println(content);
	                	this.saveLogs(content,this.ascii,"CS①");
	                }else {//心跳
	                	byte[] databuffer = new byte[16];
	                	for(int k=0;k<16;k++)
	                		databuffer[k] = data_in[k];
	                	
	                	//byte[] databuffer1= {0x5A,0x29,0x1C,0x6E,0x41,(byte) 0xC0,(byte) 0xAE,0x3E,0x7D,(byte) 0xD4,(byte) 0xBA,(byte) 0xC0,0x2F,0x69,0x3F,(byte) 0xF6};
	            		//byte[] Key1={0x20,0x37,0x62,0x39,0x31,0x31,0x31,0x37,0x63,0x30,0x30,0x32,0x31,0x38,0x33,0x33};
	            		byte[] data1 = new byte[16];
	            		byte[] data2 = new byte[16];
	            		String Q = null;
	            		String EEE = null;
	            		String GG = null;
	            		String CCC = null;
	                	
	                	Object[] object = new Object[]{ databuffer , Key,data1};
	            		String b= sumFunc.invokeString(object, false);
	            		System.out.println("b:"+b);
	            		String c = strTo16(b.substring(1));
	            		System.out.println("c:"+c);
	            		System.out.println("c.length():"+c.length());            		
	            		
	            		if(c.length()>1) {
		            		if(c.substring(2, 4).equals("4f")&&c.substring(4, 6).equals("4b")&&!c.substring(6,8).equals("30"))
		            			System.out.println("xintiao");
	            		}
	            		
	            		Dto pDto=Dtos.newDto("device_id",this.ascii1);
	            		List<DevicePO> deviceDtos = deviceDao.like(pDto);
	            		//String device_id = deviceDtos.get(0).getDevice_id();
	            		String device_id = "26666666";
	            		
	            		Dto pDto1 = Dtos.newDto("device_id", device_id);
	    				DevicePO devicePO1 = deviceDao.selectOne(pDto1);
	    				
	    				//Dto newDto = Dtos.newDto();
	    				//newDto.put("device_id", devicePO1.getArrange_withdraw());
	    				//System.out.println(newDto);
	    				
	    				
	    				if(devicePO1.getArrange_withdraw().equals("0"))   //撤防
	    					fang=false;
	    				else if(devicePO1.getArrange_withdraw().equals("1"))  //布防
	    					fang=true;
	    					
	    				if(c.length()>5) {
		            		if(c.substring(6,8).equals("30")&&c.substring(1,2).equals("5")&&fang==false)
		            		{
		            			System.out.println("bufang");
			            		System.out.println("yes");
			            		Q = "1";  //触发
		                		
			            		//Dto pDto=Dtos.newDto("device_id",this.ascii1);
			            		//List<DevicePO> deviceDtos = deviceDao.like(pDto);
			            		pDto = Dtos.newDto("device_id",device_id);
			        			DevicePO devicePO=deviceDao.selectOne(pDto);
								devicePO.setArrange_withdraw("1");  //布防
								//devicePO.setIs_alarming(Q);
								devicePO.setArrange_date(AOSUtils.getDateTime());
								deviceDao.updateByKey(devicePO);
								
								fang = true;
		            		}else if(c.substring(6,8).equals("30")&&c.substring(1,2).equals("5")&&fang==true)
		            		{
		            			System.out.println("chefang");	
				            	System.out.println("yes");
				            	Q = "0";  //恢复
				            	   
			            		pDto = Dtos.newDto("device_id",device_id);
			        			DevicePO devicePO=deviceDao.selectOne(pDto);
								devicePO.setArrange_withdraw("0"); //撤防
								//devicePO.setIs_alarming(Q);
								devicePO.setWithdraw_date(AOSUtils.getDateTime());
								deviceDao.updateByKey(devicePO);
								
								fang = false;
			                		
				            	/*
			                	DevicePO  devicePO_=new DevicePO();
			                	devicePO_.setId_(this.ascii1);
			                	devicePO_.setDevice_id(Corp_ID);
		                		
			                	deviceDao.insert(devicePO_);
			                	*/
		            		}
	    				}
	            		if(c.length()>20)
	            		{
	            			byte[] databuffer2 = new byte[16];
	            			//for(int u=0;u<6;u++)
	            				//databuffer2[u] = 1;
		                	for(int n=16;n<32;n++)
		                		databuffer2[n-16] = data_in[n];
		            		
		            		Object[] object2 = new Object[]{ databuffer2 , Key,data2};
		            		String b2= sumFunc.invokeString(object2, false);
		            		System.out.println("b2:"+b2);
		            		String c2 = strTo16(b2);
		            		System.out.println("c2:"+c2);
		            		System.out.println("c2.length():"+c2.length());	
	            			
		            		
	            			System.out.println("c.length()>20");	
	            			System.out.println(c.substring(13, 15));	
	            			System.out.println(c.substring(15, 17));
		            		if(c.substring(13, 15).equals("31")&&c.substring(15, 17).equals("38")) {
		            			System.out.println("baojing");	
				            	
		            			if(c.substring(17, 19).equals("31"))	
			            			Q = "1";  //触发
		            			else if(c.substring(17, 19).equals("33"))
		            				Q = "0";  //恢复
		            			
		            			EEE = c.substring(19, 25);
		            			System.out.println("EEE:"+EEE);
		            			String str_EEE = decode(EEE);
		            			
		            			
		            			GG = c.substring(25, 29);
		            			
		            			//CCC = c.substring(25, 29);
		            			
		            			/*
			            		Dto pDto2;
			            		pDto2 = Dtos.newDto("device_id",device_id);
			            		DevicePO devicePO2=deviceDao.selectOne(pDto2);
								devicePO2.setIs_alarming(Q);
								//devicePO2.set
								deviceDao.updateByKey(devicePO2);
								*/
		            			
		            			Dto pDto3 = Dtos.newDto("eee", str_EEE);
		            			Alarm_descPO alarm_descPO = alarm_descDao.selectOne(pDto3);
		            			
		            			Dto pDto4 = Dtos.newDto("device_id", "10000000");
		            			DevicePO devicePO5 = deviceDao.selectOne(pDto4);
		            			
		            			Dto pDto2 = Dtos.newDto("device_id", device_id);
		            			DevicePO devicePO = deviceDao.selectOne(pDto2);
		            			//Dto newDto = Dtos.newDto();
		            			
		            			Alarm_logPO alarm_logPO = new Alarm_logPO();
		            			alarm_logPO.setAlarm_id(AOSId.appId(SystemCons.ID.SYSTEM));
		            			alarm_logPO.setDevice_id(device_id);
		            			alarm_logPO.setUser_phone(devicePO.getPhone());
		            			alarm_logPO.setAlarm_time(new Date());
		            			alarm_logPO.setReason_(alarm_descPO.getAlarm_type());
		            			alarm_logPO.setBeiyong1_(str_EEE);
		            			alarm_logPO.setBeiyong2_("0");
		            			alarm_logPO.setType_("0");
		            			alarm_logDao.insert(alarm_logPO);
		            			
		            			Push.pushToSingle(devicePO.getPhone());
		            			
		            			//sendSms(devicePO.getPhone(),"1",str_EEE);
		            			
		            			pDto = Dtos.newDto("device_id",device_id);
			        			DevicePO devicePO4=deviceDao.selectOne(pDto);
								devicePO4.setIs_alarming(Q);
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
