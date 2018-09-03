package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import aos.framework.core.id.AOSId;
import aos.framework.core.service.CDZBaseController;
import aos.framework.core.typewrap.Dto;
import aos.framework.core.utils.AOSJson;
import aos.framework.web.router.HttpModel;
import aos.system.common.utils.SystemCons;
import dao.App_versionDao;
import po.App_versionPO;

@Service
public class App_versionService extends CDZBaseController {

	@Autowired
	private App_versionDao app_versionDao;

	/**
	 * charging_pile管理页面初始化
	 * 
	 * @param httpModel
	 * @return
	 */
	public void init(HttpModel httpModel) {
		httpModel.setViewPath("myproject/app_version.jsp");
	}

	/**
	 * 查询charging_pile列表
	 * 
	 * @param httpModel
	 * @return
	 */
	public void listApp_version(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		List<Dto> app_versionDtos = sqlDao.list("App_version.listApp_versionsPage", qDto);
		httpModel.setOutMsg(AOSJson.toGridJson(app_versionDtos, qDto.getPageTotal()));
	}
	
	/**
	 * 查询charging_pile信息
	 * 
	 * @param httpModel
	 */
	public void getApp_version(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		App_versionPO app_versionPO = app_versionDao.selectByKey(inDto.getString("cp_id"));
		httpModel.setOutMsg(AOSJson.toJson(app_versionPO));
	}

	/**
	 * 保存charging_pile
	 * 
	 * @param httpModel
	 * @return
	 */
	@Transactional
	public void saveApp_version(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		App_versionPO app_versionPO = new App_versionPO();
		app_versionPO.copyProperties(inDto);
		app_versionPO.setApp_vesino_id(AOSId.appId(SystemCons.ID.SYSTEM));
		/* app_versionPO.setCreate_date(AOSUtils.getDateTime()); */
		app_versionDao.insert(app_versionPO);
		httpModel.setOutMsg("新增成功。");
	}

	/**
	 * 修改charging_pile
	 * 
	 * @param httpModel
	 * @return
	 */
	@Transactional
	public void updateApp_version(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		App_versionPO app_versionPO = new App_versionPO();
		app_versionPO.copyProperties(inDto);
		app_versionDao.updateByKey(app_versionPO);
		httpModel.setOutMsg("修改成功。");
	}

	/**
	 * 删除charging_pile
	 * 
	 * @param httpModel
	 */
	@Transactional
	public void deleteApp_version(HttpModel httpModel) {
		String[] selectionIds = httpModel.getInDto().getRows();
		if (null != selectionIds && selectionIds.length > 0) {
			for (String app_vesino_id : selectionIds) {
				/*
				 * App_versionPO app_versionPO = new App_versionPO();
				 * app_versionPO.setCp_id(id_); app_versionPO.setIs_del(SystemCons.IS.YES);
				 * app_versionDao.updateByKey(app_versionPO);
				 */
				app_versionDao.deleteByKey(app_vesino_id);
			}
		} else {
			String app_vesino_id = httpModel.getInDto().getString("app_vesino_id");
			/*
			 * App_versionPO app_versionPO = new App_versionPO();
			 * app_versionPO.setCp_id(cp_id); app_versionPO.setIs_del(SystemCons.IS.YES);
			 * app_versionDao.updateByKey(app_versionPO);
			 */
			app_versionDao.deleteByKey(app_vesino_id);

		}
		httpModel.setOutMsg("删除成功。");
	}

}
