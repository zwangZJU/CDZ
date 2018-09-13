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
import dao.Nbiot_callbackDao;
import po.Nbiot_callbackPO;

@Service
public class Nbiot_callbackService extends CDZBaseController {

	@Autowired
	private Nbiot_callbackDao nbiot_callbackDao;

	/**
	 * charging_pile管理页面初始化
	 * 
	 * @param httpModel
	 * @return
	 */
	public void init(HttpModel httpModel) {
		httpModel.setViewPath("myproject/nbiot_callback.jsp");
	}

	/**
	 * 查询charging_pile列表
	 * 
	 * @param httpModel
	 * @return
	 */
	public void listNbiot_callback(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		List<Dto> nbiot_callbackDtos = sqlDao.list("Nbiot_callback.listNbiot_callbacksPage", qDto);
		httpModel.setOutMsg(AOSJson.toGridJson(nbiot_callbackDtos, qDto.getPageTotal()));
	}
	
	/**
	 * 查询charging_pile信息
	 * 
	 * @param httpModel
	 */
	public void getNbiot_callback(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		Nbiot_callbackPO nbiot_callbackPO = nbiot_callbackDao.selectByKey(inDto.getString("cp_id"));
		httpModel.setOutMsg(AOSJson.toJson(nbiot_callbackPO));
	}

	/**
	 * 保存charging_pile
	 * 
	 * @param httpModel
	 * @return
	 */
	@Transactional
	public void saveNbiot_callback(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		Nbiot_callbackPO nbiot_callbackPO = new Nbiot_callbackPO();
		nbiot_callbackPO.copyProperties(inDto);
		nbiot_callbackPO.setId_(AOSId.appId(SystemCons.ID.SYSTEM));

		nbiot_callbackDao.insert(nbiot_callbackPO);
		httpModel.setOutMsg("新增成功。");
	}

	/**
	 * 修改charging_pile
	 * 
	 * @param httpModel
	 * @return
	 */
	@Transactional
	public void updateNbiot_callback(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		Nbiot_callbackPO nbiot_callbackPO = new Nbiot_callbackPO();
		nbiot_callbackPO.copyProperties(inDto);
		nbiot_callbackDao.updateByKey(nbiot_callbackPO);
		httpModel.setOutMsg("修改成功。");
	}

	/**
	 * 删除charging_pile
	 * 
	 * @param httpModel
	 */
	@Transactional
	public void deleteNbiot_callback(HttpModel httpModel) {
		String[] selectionIds = httpModel.getInDto().getRows();
		if (null != selectionIds && selectionIds.length > 0) {
			for (String id_ : selectionIds) {
				/*
				 * Nbiot_callbackPO nbiot_callbackPO = new Nbiot_callbackPO();
				 * nbiot_callbackPO.setCp_id(id_);
				 * nbiot_callbackPO.setIs_del(SystemCons.IS.YES);
				 * nbiot_callbackDao.updateByKey(nbiot_callbackPO);
				 */
				nbiot_callbackDao.deleteByKey(id_);
			}
		} else {
			String cp_id = httpModel.getInDto().getString("cp_id");
			/*
			 * Nbiot_callbackPO nbiot_callbackPO = new Nbiot_callbackPO();
			 * nbiot_callbackPO.setCp_id(cp_id);
			 * nbiot_callbackPO.setIs_del(SystemCons.IS.YES);
			 * nbiot_callbackDao.updateByKey(nbiot_callbackPO);
			 */
			nbiot_callbackDao.deleteByKey(cp_id);

		}
		httpModel.setOutMsg("删除成功。");
	}

}
