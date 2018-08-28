package service;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import aos.framework.core.id.AOSId;
import aos.framework.core.service.CDZBaseController;
import aos.framework.core.typewrap.Dto;
import aos.framework.core.typewrap.Dtos;
import aos.framework.core.utils.AOSJson;
import aos.framework.core.utils.AOSUtils;
import aos.framework.web.router.HttpModel;
import aos.system.common.utils.SystemCons;
import dao.Alarm_logDao;
import dao.Basic_userDao;
import dao.DeviceDao;
import po.Alarm_logPO;
import po.Basic_userPO;
import po.DevicePO;
import utils.ExcelUtils;

@Service
public class Alarm_logService extends CDZBaseController {

	@Autowired
	private Alarm_logDao alarm_logDao;
	
	@Autowired
	DeviceDao deviceDao;
	
	@Autowired
	Basic_userDao basic_userDao;
	
	int num_before = 0;
	int i = 0;

	/**
	 * charging_pile管理页面初始化
	 * 
	 * @param httpModel
	 * @return
	 */
	public void init(HttpModel httpModel) {
		httpModel.setViewPath("myproject/alarm_log.jsp");
	}

	/**
	 * 查询charging_pile列表
	 * 
	 * @param httpModel
	 * @return
	 */
	public void listAlarm_log(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		List<Dto> alarm_logDtos = sqlDao.list("Alarm_log.listAlarm_logsPage", qDto);
		//Collections.reverse(alarm_logDtos);
		httpModel.setOutMsg(AOSJson.toGridJson(alarm_logDtos, qDto.getPageTotal()));
	}
	
	/**
	 * 查询charging_pile信息
	 * 
	 * @param httpModel
	 */
	public void getAlarm_log(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		Alarm_logPO alarm_logPO = alarm_logDao.selectByKey(inDto.getString("cp_id"));
		httpModel.setOutMsg(AOSJson.toJson(alarm_logPO));
	}

	/**
	 * 保存charging_pile
	 * 
	 * @param httpModel
	 * @return
	 */
	@Transactional
	public void saveAlarm_log(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		Alarm_logPO alarm_logPO = new Alarm_logPO();
		alarm_logPO.copyProperties(inDto);
		alarm_logPO.setAlarm_id(AOSId.appId(SystemCons.ID.SYSTEM));
		/* alarm_logPO.setCreate_date(AOSUtils.getDateTime()); */
		alarm_logDao.insert(alarm_logPO);
		httpModel.setOutMsg("新增成功。");
	}

	@Transactional
	public void updateWebpage(HttpModel httpModel) {
		//Dto inDto = httpModel.getInDto();
		
		Dto newDto = Dtos.newDto();
		//newDto.put("handler_phone", alarm_logPO1.getHandler_phone());
		newDto.put("data","<name>"+"1234567890"+"</name>");
		
		if(i == 0)
		{
			num_before = alarm_logDao.row_num();
			i=1;
		}
		int num = alarm_logDao.row_num();
		System.out.println("num"+num);
		
		if(num > num_before)
		{
			PrintWriter pw=null;
			try {
				pw = httpModel.getResponse().getWriter();
				pw.write("1");
				pw.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				pw.close();
			}
			num_before = num;
		}
		//byte[] buff = pw.toByteArray(); 
	   // for (int i = 0; i < buff.length; i++) 
	       // pw.println(buff[i]); 
		//os.println("456");
		//os.close();
		//os.close(); 
		//httpModel.setOutMsg1("<name>"+"1234567890"+"</name>");
		//httpModel.setOutMsg1("</response>");
	}
	
	/**
	 * 修改charging_pile
	 * 
	 * @param httpModel
	 * @return
	 */
	@Transactional
	public void updateAlarm_log(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		Alarm_logPO alarm_logPO = new Alarm_logPO();
		alarm_logPO.copyProperties(inDto);
		alarm_logDao.updateByKey(alarm_logPO);
		httpModel.setOutMsg("修改成功。");
	}
	
	public void updateAlarm_log2(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		Alarm_logPO alarm_logPO = new Alarm_logPO();
		alarm_logPO.copyProperties(inDto);
		System.out.println(alarm_logPO.getHandler_());
		alarm_logDao.updateByKey(alarm_logPO);
		httpModel.setOutMsg("修改成功。");
		
		Dto pDto = Dtos.newDto("alarm_id",alarm_logPO.getAlarm_id() );
		Alarm_logPO alarm_logPO1 =alarm_logDao.selectOne(pDto); // 用
		
		DevicePO devicePO=new DevicePO();
		devicePO.setIs_alarming("0");
		devicePO.setDevice_id(alarm_logPO1.getDevice_id());
		deviceDao.updateByKey(devicePO);
		
		System.out.println(alarm_logPO1.getDevice_id());
		System.out.println("deviceDao.updateByKey(devicePO)");
	}
	
	//单个接警
	public void receive_alarmAlarm_log(HttpModel httpModel) {
		/*Dto inDto = httpModel.getInDto();
		Alarm_logPO alarm_logPO = new Alarm_logPO();
		alarm_logPO.copyProperties(inDto);
		System.out.println(alarm_logPO.getHandler_());*/
		//alarm_logDao.updateByKey(alarm_logPO);
		
		Alarm_logPO alarm_logPO = new Alarm_logPO();
		Dto qDto = httpModel.getInDto();
		Dto pDto = Dtos.newDto();
		String alarm_id = qDto.getString("id");
		//pDto.put("alarm_id", "1808091745501987");
		//pDto.put("device_id", device_id);
		Alarm_logPO alarm_logPO1;
		//Dto pDto = Dtos.newDto("alarm_id",alarm_logPO.getAlarm_id());
		//Alarm_logPO alarm_logPO1 =alarm_logDao.selectOne(pDto); 
		if(alarm_id.substring(alarm_id.length()-1,alarm_id.length()).equals(","))
			alarm_logPO1 =alarm_logDao.selectByAlarmId(alarm_id.substring(0,alarm_id.length()-1)); 
		else
			alarm_logPO1 =alarm_logDao.selectByAlarmId(alarm_id); 
		
		Dto newDto = Dtos.newDto();
		//newDto.put("handler_phone", alarm_logPO1.getHandler_phone());
		newDto.put("alarm_time", alarm_logPO1.getAlarm_time());
		newDto.put("user_phone", alarm_logPO1.getUser_phone());
		newDto.put("device_id", alarm_logPO1.getDevice_id());
		newDto.put("handler_", alarm_logPO1.getHandler_());
		newDto.put("handler_phone", alarm_logPO1.getHandler_phone());
		
		//Dto pDto1 = Dtos.newDto("device_id",alarm_logPO.getDevice_id());
		//DevicePO devicePO =deviceDao.selectByDeviceId(device_id.substring(0,device_id.length()-1));
		
		/*DevicePO devicePO =deviceDao.selectByDeviceId(alarm_logPO1.getDevice_id());
		newDto.put("user_address", devicePO.getUser_address());
		newDto.put("user_name", devicePO.getUser_name());
		newDto.put("user_id", devicePO.getUser_id());*/
	 
		Dto Dto_user = Dtos.newDto("account", alarm_logPO1.getUser_phone());
		//Dto_user.put("account", alarm_logPO1.getUser_phone());
		Basic_userPO basic_userPO = basic_userDao.selectOne(Dto_user);
		newDto.put("user_address", basic_userPO.getAddress());
		newDto.put("user_name", basic_userPO.getName());
		//newDto.put("user_id", basic_userPO.getUser_id());
		
		//几个问题：用户编号，basic_user和device里面的用户编号不一样。一键报警时不能根据设备查询用户。
		//双机后不能动
		//一进alarm_log就报警弹窗
		
		httpModel.setOutMsg(AOSJson.toJson(newDto));
		//httpModel.setOutMsg(alarm_logPO1.getHandler_phone()+"#"+devicePO.getUser_address());
		
		/*Dto qDto = httpModel.getInDto();
		List<Dto> list = sqlDao.list("Basic_user.listHandler1", httpModel.getInDto());
		httpModel.setOutMsg(AOSJson.toGridJson(list));*/
	}
	
	//确认单个接警，对is_alarming进行修改
	public void receiveAlarmSave(HttpModel httpModel) {
		//Alarm_logPO alarm_logPO = new Alarm_logPO();
		Dto qDto = httpModel.getInDto();
		Dto pDto = Dtos.newDto();
		String alarm_id = qDto.getString("id");
		
		Alarm_logPO alarm_logPO1;
		if(alarm_id.substring(alarm_id.length()-1,alarm_id.length()).equals(","))
			alarm_logPO1 =alarm_logDao.selectByAlarmId(alarm_id.substring(0,alarm_id.length()-1)); 
		else
			alarm_logPO1 =alarm_logDao.selectByAlarmId(alarm_id); 
		
		alarm_logPO1.setBeiyong2_("1");
		alarm_logDao.updateByKey(alarm_logPO1);
		
		DevicePO devicePO =deviceDao.selectByDeviceId(alarm_logPO1.getDevice_id());
		if(null != devicePO)
		{
			devicePO.setIs_alarming("0");  
			deviceDao.updateByKey(devicePO);
		}
		
		//Dto newDto = Dtos.newDto();
		//newDto.put("handler_phone", alarm_logPO1.getHandler_phone());
		//newDto.put("is_alarming","0");
		
		httpModel.setOutMsg("接警成功");
	}
	
	
	//多个接警
	public void receiveAlarmMany(HttpModel httpModel) {
		String[] selectionIds = httpModel.getInDto().getRows();
		if (null != selectionIds && selectionIds.length > 0) {
			for (String alarm_id : selectionIds) {
				Alarm_logPO alarm_logPO1;
				if(alarm_id.substring(alarm_id.length()-1,alarm_id.length()).equals(","))
					alarm_logPO1 =alarm_logDao.selectByAlarmId(alarm_id.substring(0,alarm_id.length()-1)); 
				else
					alarm_logPO1 =alarm_logDao.selectByAlarmId(alarm_id); 
				
				alarm_logPO1.setBeiyong2_("1");
				alarm_logDao.updateByKey(alarm_logPO1);
				
				DevicePO devicePO =deviceDao.selectByDeviceId(alarm_logPO1.getDevice_id());
				if(null != devicePO)
				{
					devicePO.setIs_alarming("0");  
					deviceDao.updateByKey(devicePO);
				}
			}
		} else {
			String alarm_id = httpModel.getInDto().getString("alarm_id");
			/*
			 * Alarm_logPO alarm_logPO = new Alarm_logPO(); alarm_logPO.setCp_id(cp_id);
			 * alarm_logPO.setIs_del(SystemCons.IS.YES);
			 * alarm_logDao.updateByKey(alarm_logPO);
			 */
			Alarm_logPO alarm_logPO1;
			if(alarm_id.substring(alarm_id.length()-1,alarm_id.length()).equals(","))
				alarm_logPO1 =alarm_logDao.selectByAlarmId(alarm_id.substring(0,alarm_id.length()-1)); 
			else
				alarm_logPO1 =alarm_logDao.selectByAlarmId(alarm_id); 
			
			alarm_logPO1.setBeiyong2_("1");
			alarm_logDao.updateByKey(alarm_logPO1);
			
			DevicePO devicePO =deviceDao.selectByDeviceId(alarm_logPO1.getDevice_id());
			if(null != devicePO)
			{
				devicePO.setIs_alarming("0");  
				deviceDao.updateByKey(devicePO);
			}

		}
		httpModel.setOutMsg("多个接警成功。");
	}

	/**
	 * 删除charging_pile
	 * 
	 * @param httpModel
	 */
	@Transactional
	public void deleteAlarm_log(HttpModel httpModel) {
		String[] selectionIds = httpModel.getInDto().getRows();
		if (null != selectionIds && selectionIds.length > 0) {
			for (String alarm_id : selectionIds) {
				/*
				 * Alarm_logPO alarm_logPO = new Alarm_logPO(); alarm_logPO.setCp_id(id_);
				 * alarm_logPO.setIs_del(SystemCons.IS.YES);
				 * alarm_logDao.updateByKey(alarm_logPO);
				 */
				alarm_logDao.deleteByKey(alarm_id);
			}
		} else {
			String alarm_id = httpModel.getInDto().getString("alarm_id");
			/*
			 * Alarm_logPO alarm_logPO = new Alarm_logPO(); alarm_logPO.setCp_id(cp_id);
			 * alarm_logPO.setIs_del(SystemCons.IS.YES);
			 * alarm_logDao.updateByKey(alarm_logPO);
			 */
			alarm_logDao.deleteByKey(alarm_id);

		}
		httpModel.setOutMsg("删除成功。");
	}
	
	public void exportExcel(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		int rows = alarm_logDao.row_num();
		qDto.put("limit", rows);// 默认查询出100个

		qDto.put("start", 0);

		HttpServletResponse response = httpModel.getResponse();
		String filename = AOSUtils.encodeChineseDownloadFileName(httpModel.getRequest().getHeader("USER-AGENT"), "报警日志导出列表.xlsx");
		response.setHeader("Content-Disposition", "attachment; filename=" + filename + ";");
		String path = httpModel.getRequest().getServletContext().getRealPath("/");
		File templetFile = new File(path + "/templet/alarm.xlsx");
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
			List<Dto> membersDtos = sqlDao.list("Alarm_log.listAlarm_logsPage", qDto);
			for (Dto dto : membersDtos) {
				String[] s = new String[14];
				s[0] = dto.getString("alarm_id");
				s[1] = dto.getString("device_id");
				s[2] = dto.getString("user_phone");
				s[3] = dto.getString("aime");
				s[4] = dto.getString("response_time");
				s[5] = dto.getString("type_");
				s[6] = dto.getString("handler_");
				s[7] = dto.getString("handler_phone");
				s[8] = dto.getString("reason_");
				s[9] = dto.getString("alarm_release");
				s[10] = dto.getString("is_cancel");
				s[11] = dto.getString("beiyong1_");
				s[12] = dto.getString("beiyong2_");
				s[13] = dto.getString("beiyong3_");

				datas.add(s);
			}
			ExcelUtils.exportExcel(1, templetFile, datas, os, 14);
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

}
