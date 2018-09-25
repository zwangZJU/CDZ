/**
 * 
 */
package controller;

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
import dao.ChargingOrdersDao;
import dao.ChargingPileDao;
import dao.CommonLogsDao;
import po.ChargingOrdersPO;
import po.ChargingPilePO;
import po.CommonLogsPO;
import utils.Helper;

import po.DevicePO;
import po.Liu_testPO;
import dao.DeviceDao;
import dao.Liu_testDao;

/**
 * @author Administrator
 *
 */
public class MultiServerThread extends Thread {
	    private Socket socket = null;
	    private String ascii="";
	    private ChargingPileDao chargingPileDao;
	    private ChargingOrdersDao chargingOrdersDao;
	    private CommonLogsDao commonLogsDao ;
	    private SqlDao sqlDao;
	    
	    private DeviceDao deviceDao;
	    
	    private Liu_testDao liu_testDao;
	    
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
	    
	    public void run() {

	        try (
	            InputStream in   = this.socket.getInputStream();
	            OutputStream out = this.socket.getOutputStream();
	        ) {
	            int length = 0;
	            byte[] data_in  = new byte[1024];
	            byte[] data_out;
	            
	            //DevicePO devicePO1=new DevicePO();
				
				//devicePO1.setId_("10");
				
				//deviceDao.insert(devicePO);
	            
	            //Liu_testPO Liu_testPO1=new Liu_testPO();
				
	            //Liu_testPO1.setId_("10");
	            
				//this.liu_testDao.insert(Liu_testPO1);
	            
	            // welcome message here
	            String msg_welcome = Helper.fillString('0', 32*2);
	            msg_welcome        = msg_welcome.replaceFirst("^00000000", "E1010410");   //请求注册包
	            System.out.println("msg_welcome: " + msg_welcome);
	            data_out           = Helper.hexStringToByteArray(msg_welcome);
	            out.write(data_out);
	            this.saveLogs("SC①服务器发出欢迎指令："+msg_welcome,"","SC①");
	            while ((length = in.read(data_in)) > 0) {   //接收的数据的长度
	                byte[] data = new byte[length];
	                //this.saveLogs("--->>"+new String(data_in), "");
	                System.arraycopy(data_in, 0, data, 0, length);  //把接收的数据放到data_in中，这里指回发的注册包或者报警包
	                String hex = DatatypeConverter.printHexBinary(data);
	                char[] hex_byte= hex.toCharArray();
	                String msgType=String.valueOf(hex_byte, 2, 2);   //这个msgType是心跳中的CMD,第一个2表示位置是第二位，第二个2表示两个位
	                this.saveLogs("原始数据:"+hex,this.ascii);
	                if("01".equals(msgType)){//注册    
	                	 this.saveLogs("注册原始数据:"+hex,this.ascii,"CS①");
	                	//注册信息
	                	String Corp_ID=new BigInteger(String.valueOf(hex_byte, 6, 2), 16).toString(); //注册包中的Corp_ID,模块型号
	                	System.out.println("Corp_ID:"+Corp_ID);
	                	String ascii_=Helper.AsciiStringToString(String.valueOf(hex_byte, 8, 56));   //注册包中第8位开始后面总共24个位
	                	//String ascii_=0x11;
	                	this.ascii=ascii_;
	                	if(this.ascii == null)
	                		System.out.println("ascii是空的");
	                	else 
	                		System.out.println("ascii不是空的");
	                		System.out.println(ascii);
	                	
	                	String ACCT1 =new BigInteger(String.valueOf(hex_byte, 8, 2), 16).toString();
	                	System.out.println("ACCT1:"+ACCT1);   	
	                	String ACCT2 =new BigInteger(String.valueOf(hex_byte, 10, 2), 16).toString();
	                	System.out.println("ACCT2:"+ACCT2); 
	                	String ACCT3 =new BigInteger(String.valueOf(hex_byte, 12, 2), 16).toString();
	                	System.out.println("ACCT3:"+ACCT3); 
	                	String ACCT4 =new BigInteger(String.valueOf(hex_byte, 14, 2), 16).toString();
	                	System.out.println("ACCT4:"+ACCT4); 
	                	String Key7 =new BigInteger(String.valueOf(hex_byte, 16, 2), 16).toString();
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
	                	String Key8 =new BigInteger(String.valueOf(hex_byte, 46, 2), 16).toString();
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
	                	
	                	Helper.socketMap.put(this.ascii, this.socket);//保存socket对象
	                	if("0x01".equals(Corp_ID)){
	                		Corp_ID="中创公司";
	                	}else{
	                		Corp_ID="xx公司";
	                	}
	                	System.out.println(Corp_ID);
	                	String id_ = "10";
	                	//DevicePO  devicePO=deviceDao.selectByKey(this.ascii);  //到表中查询
	                	//DevicePO  devicePO=deviceDao.selectByKey(id_);  //到表中查询
	                	DevicePO  devicePO = null;
	                	if(null==devicePO){//新增对象   //如果表中没有，就新增对象，把接收到的数据插入到表中
	                		/*ChargingPilePO  chargingPilePO_=new ChargingPilePO();
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
	                		chargingPileDao.insert(chargingPilePO_);   //把新增的对象插入到表中*/
	                		
	                		ChargingPilePO  chargingPilePO_=new ChargingPilePO();
	                		chargingPilePO_.setCp_id("1");
	                		chargingPilePO_.setCp_no("2");
	                		chargingPilePO_.setCp_type("3");
	                		chargingPilePO_.setSupplier(Corp_ID);
	                		chargingPilePO_.setCreate_date(AOSUtils.getDateTime());
	                		chargingPilePO_.setOper_id("socket");
	                		/*if("0".equals(chongdianzhuantai)){
	                			chargingPilePO_.setCp_status("1");
	                		}else if("0".equals(chongdianzhuantai)){
	                			chargingPilePO_.setCp_status("0");
	                		}else{
	                			chargingPilePO_.setCp_status(chongdianzhuantai);
	                		}*/
	                		
	                		//chargingPilePO_.setElectricity(new BigDecimal(AOSCxt.getParam("electricity")));
	                		chargingPileDao.insert(chargingPilePO_);   //把新增的对象插入到表中
	                		
	                		DevicePO devicePO_ = new DevicePO();
	                		devicePO_.setDevice_id(Corp_ID);
	                		devicePO_.setPay_date(AOSUtils.getDateTime());
	                		System.out.println("devicePO_");
	                		System.out.println(devicePO_);
	                		//deviceDao.insert(devicePO_);   //把新增的对象插入到表中
	                		
	                	}else{//已存在，则修改状态
	                		/*ChargingPilePO  chargingPilePO_=new ChargingPilePO();
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
	                		
	                		chargingPileDao.updateByKey(chargingPilePO_);   //更新表中的数据*/
	                		
	                		DevicePO devicePO_ = new DevicePO();
	                		devicePO_.setDevice_id(Corp_ID);
	                		//devicePO_.setCreate_date(AOSUtils.getDateTime());
	                		deviceDao.updateByKey(devicePO_);   //把新增的对象插入到表中*/
	                	}
	                	//String content="注册转换后参数：：：充电状态"+chongdianzhuantai+"  ICode[1]:"+ICode1+"  ICode[2]:"+ICode2+"  ICode[3]:"+ICode3+"  ICode[4]:"+ICode4+"  ICode[5]:"+ICode5+"  ICode[6]:"+ICode6+"  ICode[7]:"+ICode7+"  ICode[8]:"+ICode8+"  ICode[9]:"+ICode9+"  ICode[10]:"+ICode10+"  ICode[11]:"+ICode11+"  ICode[12]:"+ICode12+"  ICode[13]:"+ICode13+"  ICode[14]:"+ICode14+"  ICode[15]:"+ICode15+"  ICode[16]:"+ICode16+"  TYPE_ID:"+TYPE_ID+"  Corp_ID:"+Corp_ID;
	                	//String content="CS①注册转换后参数：：：ascii:"+this.ascii+" 充电状态:"+chongdianzhuantai+" 桩的型号:"+TYPE_ID+" 桩生产公司:"+Corp_ID;
	                	//System.out.println(content);
	                	//this.saveLogs(content,this.ascii,"CS①");
	                }else if("02".equals(msgType)){//心跳
	                	
	                	
	        
	                	
	                	
	                }else if("03".equals(msgType)){//经纬度
	                	
	                	
	                	
	                }else if("04".equals(msgType)){//充电指令
	                	  
	                	
	                }else if("05".equals(msgType)){//开始充电
	                	
	                	
	                }else if("06".equals(msgType)){//插枪指令
	                	
	                	
	                }else if("07".equals(msgType)){//停止充电指令（桩发起）
	                	
	                	
	                }else if("08".equals(msgType)){//停止充电指令（APP发起）
	                	
	                }else if("09".equals(msgType)){//修改IP指令
	                	
	                	
	                }else if("0A".equals(msgType)){//激活指令
	             
	                	
	                }
	            }  
	            this.socket.close();
	            /*if(AOSUtils.isNotEmpty(this.ascii)){
	            	ChargingPilePO  chargingPilePO_=new ChargingPilePO();
            		chargingPilePO_.setCp_id(this.ascii);
            		chargingPilePO_.setCp_status("3");
            		chargingPileDao.updateByKey(chargingPilePO_);
	            }*/
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
