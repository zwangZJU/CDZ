package service;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
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
import dao.DeviceDao;
import po.DevicePO;
import utils.ExcelUtils;

@Service
public class DeviceService extends CDZBaseController {

	@Autowired
	private DeviceDao deviceDao;

	/**
	 * charging_pile管理页面初始化
	 * 
	 * @param httpModel
	 * @return
	 */
	public void init(HttpModel httpModel) {
		httpModel.setViewPath("myproject/device.jsp");
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
			String[] info = user_address.split(" ");
			String lat = info[1];
			lat = lat.replace(",", "");
			String lon = info[3];
			String address = info[0];
			address = address.replace("#latitude:", "");
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
				s[8] = dto.getString("cross_road");
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
				s[28] = dto.getString("code42");
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
				s[54] = dto.getString("network_setting_type");
				s[55] = dto.getString("network_setting_template");
				s[56] = dto.getString("network_setting_remarks");
				s[57] = dto.getString("network_setting_number");
				s[58] = dto.getString("network_setting_users");
				s[59] = dto.getString("alarm_type");
				s[60] = dto.getString("communication_format");
				s[61] = dto.getString("alarm_sound");
				s[62] = dto.getString("host_alarm_sms");
				s[63] = dto.getString("substation_number");
				s[64] = dto.getString("shutdown_time");
				s[65] = dto.getString("pause");
				s[66] = dto.getString("breakdown");
				s[67] = dto.getString("review_confirm");
				s[68] = dto.getString("management_remarks");
			

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

}
