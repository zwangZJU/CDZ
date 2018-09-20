package service;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import aos.framework.core.id.AOSId;
import aos.framework.core.service.CDZBaseController;
import aos.framework.core.typewrap.Dto;
import aos.framework.core.typewrap.Dtos;
import aos.framework.core.utils.AOSJson;
import aos.framework.core.utils.AOSUtils;
import aos.framework.web.router.HttpModel;
import aos.system.common.utils.SystemCons;
import dao.Basic_userDao;
import dao.DeviceDao;
import po.Alarm_logPO;
import po.Basic_userPO;
import po.DevicePO;
import po.Repair_logPO;
import utils.Constant;
import utils.ExcelUtils;
import utils.Helper;
import utils.Request;

@Service
public class DeviceService extends CDZBaseController {

	@Autowired
	private DeviceDao deviceDao;
	
	@Autowired
	Basic_userDao basic_userDao;

	/**
	 * charging_pile管理页面初始化
	 * 
	 * @param httpModel
	 * @return
	 */
	public void init(HttpModel httpModel) {
		httpModel.setViewPath("myproject/device.jsp");
	}
	
	public void queryDevice(HttpModel httpModel) {
		//Dto qDto = httpModel.getInDto();
		List<Dto> list = sqlDao.list("Device.queryDevice", httpModel.getInDto());
		httpModel.setOutMsg(AOSJson.toGridJson(list));
	}

	/**
	 * 查询charging_pile列
	 * 
	 * @param httpModel
	 * @return
	 */
	public void listDevice(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		List<Dto> deviceDtos = sqlDao.list("Device.listDevicesPage", qDto);
		httpModel.setOutMsg(AOSJson.toGridJson(deviceDtos, qDto.getPageTotal()));
	}
	
	/**
	 * 查询charging_pile信息
	 * 
	 * @param httpModel
	 */
	public void getDevice(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		DevicePO devicePO = deviceDao.selectByKey(inDto.getString("device_id"));
		httpModel.setOutMsg(AOSJson.toJson(devicePO));
	}
	
	public void listCoordinate(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		/*
		 * List<Dto> list = sqlDao.list("Repair_log.listHandler", httpModel.getInDto());
		 */
		Dto odto = Dtos.newDto();

		/* odto.put("hhhh", "44"); */

		Dto pDto = Dtos.newDto();
		int rows = deviceDao.rows(pDto);
		pDto.put("limit", rows);
		pDto.put("start", 0);
		List<Dto> deviceDtos = sqlDao.list("Device.listDevicesPage", pDto);
		List<Dto> newListDtos = new ArrayList<Dto>();

		for (Dto dto : deviceDtos) {
			Dto newDto = Dtos.newDto();
			String user_address = dto.getString("user_address");
			String[] info = user_address.split("#");

			int num = info.length;
			String info1 = info[num - 1];

			String[] info2 = info1.split(" ");

			String lat1 = info2[1];
			String lat = lat1.replace(",", "");

			String lon = info2[3];

			String address = info[0];

			newDto.put("lat", lat);
			newDto.put("lon", lon);
			newDto.put("number", Integer.toString(rows));
			newDto.put("device_id", dto.getString("device_id"));
			newDto.put("user_name", dto.getString("user_name"));
			newDto.put("phone", dto.getString("phone"));
			newDto.put("user_address", address);
			newDto.put("is_alarming", dto.getString("is_alarming"));

			newListDtos.add(newDto);

		}

		odto.put("coor", newListDtos);

		httpModel.setOutMsg(AOSJson.toJson(odto));

	}


	/**
	 * 保存charging_pile
	 * 
	 * @param httpModel
	 * @return
	 */
	@Transactional
	public void saveDevice(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		DevicePO devicePO = new DevicePO();
		devicePO.copyProperties(inDto);

		devicePO.setId_(AOSId.appId(SystemCons.ID.SYSTEM));
		// devicePO.setCreate_date(AOSUtils.getDateTime());

		deviceDao.insert(devicePO);
		httpModel.setOutMsg("新增成功。");
	}

	/**
	 * 修改charging_pile
	 * 
	 * @param httpModel
	 * @return
	 */
	@Transactional
	public void updateDevice(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		DevicePO devicePO = new DevicePO();
		devicePO.copyProperties(inDto);
		deviceDao.updateByKey(devicePO);
		httpModel.setOutMsg("修改成功。");
	}
	
	public void updateDevice2(HttpModel httpModel) {
		String name = "";

		Dto inDto = httpModel.getInDto();
		
		String combox_value = inDto.getString("combox_value");
		String[] deviceIds = httpModel.getInDto().getRows();

		if (null != deviceIds && deviceIds.length > 0) {
			for (String device_id : deviceIds) {
				DevicePO devicePO1;
				if(device_id.substring(device_id.length()-1,device_id.length()).equals(","))
					devicePO1 =deviceDao.selectByDeviceId(device_id.substring(0,device_id.length()-1)); 
				else
					devicePO1 =deviceDao.selectByDeviceId(device_id); 
				
				Dto pDto1 = Dtos.newDto("account", combox_value);
				Basic_userPO basic_userPO = basic_userDao.selectOne(pDto1);
				
				devicePO1.setHead_phone(combox_value);
				devicePO1.setHead(basic_userPO.getName());
				deviceDao.updateByKey(devicePO1);
			}
		}

		//DevicePO.setHandler_(name);
		//deviceDao.updateByKey(devicePO);
		httpModel.setOutMsg("修改成功。");
	}

	/**
	 * 删除charging_pile
	 * 
	 * @param httpModel
	 */
	@Transactional
	public void deleteDevice(HttpModel httpModel) {
		String[] selectionIds = httpModel.getInDto().getRows();
		if (null != selectionIds && selectionIds.length > 0) {
			for (String device_id : selectionIds) {
				/*
				 * DevicePO devicePO = new DevicePO(); devicePO.setCp_id(id_);
				 * devicePO.setIs_del(SystemCons.IS.YES); deviceDao.updateByKey(devicePO);
				 */
				deviceDao.deleteByKey(device_id);
//				deviceDao.deleteByKey(id_);
				
			}
		} else {
			String device_id = httpModel.getInDto().getString("device_id");
			/*
			 * DevicePO devicePO = new DevicePO(); devicePO.setCp_id(cp_id);
			 * devicePO.setIs_del(SystemCons.IS.YES); deviceDao.updateByKey(devicePO);
			 */
			deviceDao.deleteByKey(device_id);

		}
		httpModel.setOutMsg("删除成功。");
	}
	public void exportExcel(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto pdto1 = Dtos.newDto();
		int rows = deviceDao.rows(pdto1);
		qDto.put("limit", rows);//

		qDto.put("start", 0);

		HttpServletResponse response = httpModel.getResponse();
		String filename = AOSUtils.encodeChineseDownloadFileName(httpModel.getRequest().getHeader("USER-AGENT"), "设备导出列表.xlsx");
		response.setHeader("Content-Disposition", "attachment; filename=" + filename + ";");
		String path = httpModel.getRequest().getServletContext().getRealPath("/");
		File templetFile = new File(path + "/templet/device.xlsx");
		BufferedInputStream in;
		ServletOutputStream os = null;
		try {
			in = new BufferedInputStream(new FileInputStream(templetFile));
			ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
			byte[] temp = new byte[1024];
			int size = 0;
			while ((size = in.read(temp)) != -1) {
				out.write(temp, 0, size);
			}
			in.close();
			os = response.getOutputStream();

			List<String[]> datas = new ArrayList<String[]>();
			/*
			 * String[] s = new String[127]; for (int j = 0; j < 10; j++) { for (int i = 0;
			 * i < s.length; i++) { s[i] = i + "数据"; } datas.add(s); }
			 */
			List<Dto> membersDtos = sqlDao.list("Device.listDevicesPage", qDto);
			for (Dto dto : membersDtos) {
				String[] s = new String[69];

				s[0] = dto.getString("device_id");
				s[1] = dto.getString("status");
				s[2] = dto.getString("arrange_withdraw");
				s[3] = dto.getString("id_");
				s[4] = dto.getString("user_id");
				s[5] = dto.getString("user_name");
				s[6] = dto.getString("user_address");
				s[7] = dto.getString("sub_center");
				s[8] = dto.getString("signal_quality");
				s[9] = dto.getString("user_type");
				s[10] = dto.getString("is_alarming");
				s[11] = dto.getString("host_type");
				s[12] = dto.getString("video_linkage");
				s[13] = dto.getString("head");
				s[14] = dto.getString("head_phone");
				s[15] = dto.getString("test_period");
				s[16] = dto.getString("pay_date");
				s[17] = dto.getString("guarantee_time");
				s[18] = dto.getString("check_status");
				s[19] = dto.getString("arrearage");
				s[20] = dto.getString("shut_down");
				s[21] = dto.getString("town");
				s[22] = dto.getString("town_phone");
				s[23] = dto.getString("police_station");
				s[24] = dto.getString("network");
				s[25] = dto.getString("police_phone");
				s[26] = dto.getString("host_address");
				s[27] = dto.getString("install_date");
				s[28] = dto.getString("shutdown_number");
				s[29] = dto.getString("withdraw_date");
				s[30] = dto.getString("arrange_date");
				s[31] = dto.getString("last_date");
				s[32] = dto.getString("builders");
				s[33] = dto.getString("police_unit");
				s[34] = dto.getString("phone");
				s[35] = dto.getString("repair_record");
				s[36] = dto.getString("repair_progress");
				s[37] = dto.getString("group");
				s[38] = dto.getString("entry_clerk");
				s[39] = dto.getString("inspection_staff");
				s[40] = dto.getString("downtime");
				s[41] = dto.getString("phone1");
				s[42] = dto.getString("product_type");
				s[43] = dto.getString("police_station");
				s[44] = dto.getString("head_phone2");
				s[45] = dto.getString("net_date");
				s[46] = dto.getString("communication_line");
				s[47] = dto.getString("users_id");
				s[48] = dto.getString("users_name");
				s[49] = dto.getString("users_identity");
				s[50] = dto.getString("remarks");
				s[51] = dto.getString("arrangeandwithdraw_time");
				s[52] = dto.getString("network_setting_substation");
				s[53] = dto.getString("network_setting_name");
				s[54] = dto.getString("online_state");
				s[55] = dto.getString("area_map");
				s[56] = dto.getString("network_setting_remarks");
				s[57] = dto.getString("network_setting_number");
				s[58] = dto.getString("network_setting_users");
				s[59] = dto.getString("alarm_type");
				s[60] = dto.getString("communication_format");
				s[61] = dto.getString("alarm_sound");
				s[62] = dto.getString("host_alarm_sms");
				s[63] = dto.getString("gg_");
				s[64] = dto.getString("shutdown_time");
				s[65] = dto.getString("trigger_");
				s[66] = dto.getString("user_acct");
				s[67] = dto.getString("review_confirm");
				s[68] = dto.getString("blacklist");
			

				datas.add(s);
			}
			ExcelUtils.exportExcel(1, templetFile, datas, os, 69);
			os.write(out.toByteArray());
			os.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//多个拉黑
		public void toBlacklist(HttpModel httpModel) {
			String[] selectionIds = httpModel.getInDto().getRows();
			if (null != selectionIds && selectionIds.length > 0) {
				for (String device_id : selectionIds) {
					DevicePO devicePO1;
					if(device_id.substring(device_id.length()-1,device_id.length()).equals(","))
						devicePO1 =deviceDao.selectByDeviceId(device_id.substring(0,device_id.length()-1)); 
					else
						devicePO1 =deviceDao.selectByDeviceId(device_id); 
					
					devicePO1.setBlacklist("1");
					deviceDao.updateByKey(devicePO1);
					
				}
			} 
			/*else {
				String alarm_id = httpModel.getInDto().getString("alarm_id");
				Alarm_logPO alarm_logPO1;
				if(alarm_id.substring(alarm_id.length()-1,alarm_id.length()).equals(","))
					alarm_logPO1 =alarm_logDao.selectByAlarmId(alarm_id.substring(0,alarm_id.length()-1)); 
				else
					alarm_logPO1 =alarm_logDao.selectByAlarmId(alarm_id); 
				
				alarm_logPO1.setProcess("1");
				alarm_logDao.updateByKey(alarm_logPO1);
				
				DevicePO devicePO =deviceDao.selectByDeviceId(alarm_logPO1.getDevice_id());
				if(null != devicePO)
				{
					devicePO.setIs_alarming("0");  
					deviceDao.updateByKey(devicePO);
				}

			}*/
			httpModel.setOutMsg("多个拉黑成功。");
		}

		//多个解除拉黑
		public void outBlacklist(HttpModel httpModel) {
			String[] selectionIds = httpModel.getInDto().getRows();
			if (null != selectionIds && selectionIds.length > 0) {
				for (String device_id : selectionIds) {
					DevicePO devicePO1;
					if(device_id.substring(device_id.length()-1,device_id.length()).equals(","))
						devicePO1 =deviceDao.selectByDeviceId(device_id.substring(0,device_id.length()-1)); 
					else
						devicePO1 =deviceDao.selectByDeviceId(device_id); 
					
					devicePO1.setBlacklist("0");
					deviceDao.updateByKey(devicePO1);
					
				}
			} 
			/*else {
				String alarm_id = httpModel.getInDto().getString("alarm_id");
				Alarm_logPO alarm_logPO1;
				if(alarm_id.substring(alarm_id.length()-1,alarm_id.length()).equals(","))
					alarm_logPO1 =alarm_logDao.selectByAlarmId(alarm_id.substring(0,alarm_id.length()-1)); 
				else
					alarm_logPO1 =alarm_logDao.selectByAlarmId(alarm_id); 
				
				alarm_logPO1.setProcess("1");
				alarm_logDao.updateByKey(alarm_logPO1);
				
				DevicePO devicePO =deviceDao.selectByDeviceId(alarm_logPO1.getDevice_id());
				if(null != devicePO)
				{
					devicePO.setIs_alarming("0");  
					deviceDao.updateByKey(devicePO);
				}

			}*/
			httpModel.setOutMsg("多个解除拉黑成功。");
		}
		
		//设置多个布防
		public void setArrangeMany(HttpModel httpModel) {
			int num = 0;
			int failure_number = 0;
			String[] selectionIds = httpModel.getInDto().getRows();
			if (null != selectionIds && selectionIds.length > 0) {
				for (String device_id : selectionIds) {
					
					if(device_id.substring(device_id.length()-1,device_id.length()).equals(","))
						device_id = device_id.substring(0,device_id.length()-1);
					
					num = deployDefense(device_id,"05");
					failure_number = failure_number+num;
					if(num ==0)
					{
						DevicePO devicePO1;
						devicePO1 =deviceDao.selectByDeviceId(device_id); 
						devicePO1.setArrange_withdraw("布防");
						//devicePO.setIs_alarming(Q);
						devicePO1.setWithdraw_date(AOSUtils.getDateTime());
						deviceDao.updateByKey(devicePO1);
					}
					
				}
			} 
			if(failure_number>0)
				httpModel.setOutMsg(String.valueOf(failure_number)+"个布防失败。");
			else 
				httpModel.setOutMsg("布防成功。");
		}
		
		//设置多个撤防
		public void setWithdrawMany(HttpModel httpModel) {
			int num = 0;
			int failure_number = 0;
			String[] selectionIds = httpModel.getInDto().getRows();
			if (null != selectionIds && selectionIds.length > 0) {
				for (String device_id : selectionIds) {
					
					if(device_id.substring(device_id.length()-1,device_id.length()).equals(","))
						device_id = device_id.substring(0,device_id.length()-1);
					
					num = deployDefense(device_id,"04");
					failure_number = failure_number+num;
					if(num ==0)
					{
						DevicePO devicePO1;
						devicePO1 =deviceDao.selectByDeviceId(device_id); 
						devicePO1.setArrange_withdraw("撤防");
						//devicePO.setIs_alarming(Q);
						devicePO1.setWithdraw_date(AOSUtils.getDateTime());
						deviceDao.updateByKey(devicePO1);
					}
					
				}
			} 
			if(failure_number>0)
				httpModel.setOutMsg(String.valueOf(failure_number)+"个撤防失败。");
			else 
				httpModel.setOutMsg("撤防成功。");
		}
		
		public String getData(String value) {
			if (value == null || value.length() == 0) {
				
				return " ";
			} else {
				return value;
			}

		}
		
		public int deployDefense(String deviceid,String command) {
			final String device_id=deviceid;
			final String cmd=command; 
			int failure_num = 0;
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
							/*if(cmd.equals("4"))
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
								
							}*/
						}
						
						if(flag != "1"){
							failure_num = 1;
							/*
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
							}*/
							
						}
			return failure_num;
			
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
					//saveLogs3("APP发送布防撤防请求数据:"+cmd,sg_id,"SC④");
					flag="1";
					socket = null;
				}else{
					//this.failMsg(odto, "充电桩未连接");
					System.out.println("APP发送布防撤防请求数据:模块未连接"+msg_welcome);
					//saveLogs3("APP发送布防撤防请求数据:模块未连接",sg_id,"SC④");
					flag = "设备不在线，请检查";
				}
				
				
			} catch (IOException e) {
				if("Socket is closed".equals(e.getMessage())){
					//saveLogs3("APP发送布防撤防请求数据:模块未连接"+msg_welcome,sg_id,"SC④");
					//this.failMsg(odto, "APP发送充电请求数据异常:充电桩未连接")
					
				}else{
					//saveLogs3("APP发送布防撤防请求数据异常:"+msg_welcome,sg_id,"SC④");
					//this.failMsg(odto, "APP发送充电请求数据异常");
				}
				e.printStackTrace();
			}
			return flag;
		}
	
		@Transactional
		public void uploadPicture(HttpModel httpModel) {
			Dto qDto = httpModel.getInDto();
			String id = qDto.getString("id");

			HttpServletRequest request = httpModel.getRequest();
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
			if (multipartResolver.isMultipart(request)) {
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

				Iterator<String> iter = multiRequest.getFileNames();
				while (iter.hasNext()) {
					MultipartFile file = multiRequest.getFile((String) iter.next());
					if (file != null) {
						String fileName = file.getOriginalFilename();
						String url = Constant.SERVERIP+ "/zhaf/myupload/zonemap/" + fileName;
						Dto pDto = Dtos.newDto("area_map", url);
						DevicePO devicePO = deviceDao.selectOne(pDto);
						if (null != devicePO) {
							httpModel.setOutMsg("{\"success\":\"false\",\"msg\":\"文件已存在！\"}");
							break;
						}

						
						 String path = "C:/zhihuianfang/code/CDZ7.09/webapp/myupload/zonemap/" + fileName;

						File localFile = new File(path);

						try {
							file.transferTo(localFile);
						} catch (IllegalStateException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Dto pDto1 = Dtos.newDto("device_id", id);
						DevicePO devicePO1 = deviceDao.selectOne(pDto1);
						devicePO1.setArea_map(url);
						deviceDao.updateByKey(devicePO1);
						httpModel.setOutMsg("{\"success\":\"true\",\"msg\":\"上传成功！\"}");
					}

				}

			}

		}

		


}
