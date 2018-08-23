package service;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
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
import dao.Basic_userDao;
import dao.Repair_logDao;
import po.Basic_userPO;
import po.Repair_logPO;
import utils.ExcelUtils;

@Service
public class Repair_logService extends CDZBaseController {

	@Autowired
	private Repair_logDao repair_logDao;
	@Autowired
	Basic_userDao basic_userDao;


	/**
	 * charging_pile管理页面初始化
	 * 
	 * @param httpModel
	 * @return
	 */
	public void init(HttpModel httpModel) {
		httpModel.setViewPath("myproject/repair_log.jsp");
	}

	/**
	 * 查询charging_pile列表
	 * 
	 * @param httpModel
	 * @return
	 */
	public void listRepair_log(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		List<Dto> repair_logDtos = sqlDao.list("Repair_log.listRepair_logsPage", qDto);
		httpModel.setOutMsg(AOSJson.toGridJson(repair_logDtos, qDto.getPageTotal()));
	}
	
	/**
	 * 查询charging_pile信息
	 * 
	 * @param httpModel
	 */
	public void getRepair_log(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		Repair_logPO repair_logPO = repair_logDao.selectByKey(inDto.getString("repair_id"));
		httpModel.setOutMsg(AOSJson.toJson(repair_logPO));
	}
	
	public void listrepair3(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		/*
		 * List<Dto> list = sqlDao.list("Repair_log.listHandler", httpModel.getInDto());
		 */
		Dto odto = Dtos.newDto();

		/* odto.put("hhhh", "44"); */

		Dto pDto = Dtos.newDto();
		int rows = repair_logDao.rows(pDto);
		pDto.put("limit", rows);
		pDto.put("start", 0);
		List<Dto> deviceDtos = sqlDao.list("Repair_log.listRepair_logsPage4", pDto);
		List<Dto> newListDtos = new ArrayList<Dto>();
		int num = deviceDtos.size();

		for (Dto dto : deviceDtos) {
			Dto newDto = Dtos.newDto();

			newDto.put("device_id", dto.getString("device_id"));
			newDto.put("repair_id", dto.getString("repair_id"));
			newDto.put("repair_time", dto.getString("repair_time"));
			newDto.put("renovate_time", dto.getString("renovate_time"));
			newDto.put("handler_", dto.getString("handler_"));
			newDto.put("handler_phone", dto.getString("handler_phone"));
			newDto.put("error_reason", dto.getString("error_reason"));
			newDto.put("processing_state", dto.getString("processing_state"));

			newDto.put("repair_content", dto.getString("repair_content"));

			newDto.put("number", Integer.toString(num));

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
	public void saveRepair_log(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		Repair_logPO repair_logPO = new Repair_logPO();
		repair_logPO.copyProperties(inDto);
		repair_logPO.setRepair_id(AOSId.appId(SystemCons.ID.SYSTEM));
		/* repair_logPO.setCreate_date(AOSUtils.getDateTime()); */
		repair_logDao.insert(repair_logPO);
		httpModel.setOutMsg("新增成功。");
	}

	/**
	 * 修改charging_pile
	 * 
	 * @param httpModel
	 * @return
	 */
	@Transactional
	public void updateRepair_log(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		Repair_logPO repair_logPO = new Repair_logPO();
		repair_logPO.copyProperties(inDto);
		repair_logDao.updateByKey(repair_logPO);
		httpModel.setOutMsg("修改成功。");
	}
	
	
	@Transactional
	public void updateRepair_log2(HttpModel httpModel) {
		String name = "";

		Dto inDto = httpModel.getInDto();

		Repair_logPO repair_logPO = new Repair_logPO();
		repair_logPO.copyProperties(inDto);

		String account = repair_logPO.getHandler_phone();
		String repair_id = repair_logPO.getRepair_id();
		Dto pDto1 = Dtos.newDto("repair_id", repair_id);
		Repair_logPO repair_logPO1 = repair_logDao.selectOne(pDto1);
		String state_info = repair_logPO1.getState_info();

		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);// 获取年份
		int month = (cal.get(Calendar.MONTH) + 1);// 获取月份
		int day = cal.get(Calendar.DAY_OF_MONTH);// 获取日
		int hour = cal.get(Calendar.HOUR_OF_DAY);// 小时
		int minute = cal.get(Calendar.MINUTE);// 分
		state_info = state_info + "#已接单%" + year + "年" + month + "月" + day + "日" + hour + ":" + minute;
		
		
		
		if (null != account && !account.isEmpty()) {
			Dto pDto = Dtos.newDto("account", account);
			Basic_userPO basic_userPO = basic_userDao.selectOne(pDto);
			name = basic_userPO.getName();

		}

		repair_logPO.setHandler_(name);
		repair_logPO.setState_info(state_info);
		repair_logDao.updateByKey(repair_logPO);
		httpModel.setOutMsg("修改成功。");
	}

	/**
	 * 删除charging_pile
	 * 
	 * @param httpModel
	 */
	@Transactional
	public void deleteRepair_log(HttpModel httpModel) {
		String[] selectionIds = httpModel.getInDto().getRows();
		if (null != selectionIds && selectionIds.length > 0) {
			for (String repair_id : selectionIds) {
				/*
				 * Repair_logPO repair_logPO = new Repair_logPO(); repair_logPO.setCp_id(id_);
				 * repair_logPO.setIs_del(SystemCons.IS.YES);
				 * repair_logDao.updateByKey(repair_logPO);
				 */
				repair_logDao.deleteByKey(repair_id);
			}
		} else {
			String repair_id = httpModel.getInDto().getString("repair_id");
			/*
			 * Repair_logPO repair_logPO = new Repair_logPO(); repair_logPO.setCp_id(cp_id);
			 * repair_logPO.setIs_del(SystemCons.IS.YES);
			 * repair_logDao.updateByKey(repair_logPO);
			 */
			repair_logDao.deleteByKey(repair_id);

		}
		httpModel.setOutMsg("删除成功。");
	}
	
	public void exportExcel(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		int rows = repair_logDao.rows1(qDto);
		qDto.put("limit", rows);// 默认查询出100个

		qDto.put("start", 0);

		HttpServletResponse response = httpModel.getResponse();
		String filename = AOSUtils.encodeChineseDownloadFileName(httpModel.getRequest().getHeader("USER-AGENT"), "报修日志导出列表.xlsx");
		response.setHeader("Content-Disposition", "attachment; filename=" + filename + ";");
		String path = httpModel.getRequest().getServletContext().getRealPath("/");
		File templetFile = new File(path + "/templet/repair.xlsx");
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
			List<Dto> membersDtos = sqlDao.list("Repair_log.listRepair_logsPage", qDto);
			for (Dto dto : membersDtos) {
				String[] s = new String[12];
				s[0] = dto.getString("repair_id");
				s[1] = dto.getString("device_id");
				s[2] = dto.getString("user_phone");
				s[3] = dto.getString("repair_content");
				s[4] = dto.getString("repair_time");
				s[5] = dto.getString("renovate_time");
				s[6] = dto.getString("processing_state");
				s[7] = dto.getString("state_info");
				s[8] = dto.getString("handler_");
				s[9] = dto.getString("handler_phone");
				s[10] = dto.getString("error_reason");
				s[11] = dto.getString("is_completed");

				datas.add(s);
			}
			ExcelUtils.exportExcel(1, templetFile, datas, os, 12);
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
