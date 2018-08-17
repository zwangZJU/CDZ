package service;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import aos.framework.core.id.AOSId;
import aos.framework.core.service.CDZBaseController;
import aos.framework.core.typewrap.Dto;
import aos.framework.core.typewrap.Dtos;
import aos.framework.core.utils.AOSJson;
import aos.framework.web.router.HttpModel;
import aos.system.common.utils.SystemCons;
import dao.Basic_userDao;
import dao.Repair_logDao;
import po.Basic_userPO;
import po.Repair_logPO;

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

}
