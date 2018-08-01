package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import aos.framework.core.id.AOSId;
import aos.framework.core.utils.AOSUtils;
import aos.framework.core.service.CDZBaseController;
import aos.framework.core.typewrap.Dto;
import aos.framework.core.utils.AOSJson;
import aos.framework.web.router.HttpModel;
import aos.system.common.utils.SystemCons;
import dao.ChargingPileDao;
import po.ChargingPilePO;

/**
 * charging_pile管理
 * 
 * @author duanchongfeng
 * @date 2017-07-06 20:31:43
 *
 */
@Service
public class ChargingPileController extends CDZBaseController {

	@Autowired
	private ChargingPileDao chargingPileDao;

	/**
	 * charging_pile管理页面初始化
	 * 
	 * @param httpModel
	 * @return
	 */
	public void init(HttpModel httpModel) {
		httpModel.setViewPath("myproject/chargingPile.jsp");
	}

	/**
	 * 查询charging_pile列表
	 * 
	 * @param httpModel
	 * @return
	 */
	public void listChargingPile(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		List<Dto> chargingPileDtos = sqlDao.list("ChargingPile.listChargingPilesPage", qDto);
		httpModel.setOutMsg(AOSJson.toGridJson(chargingPileDtos, qDto.getPageTotal()));
	}
	/**
	 * 查询charging_pile信息
	 * 
	 * @param httpModel
	 */
	public void getChargingPile(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
	     ChargingPilePO chargingPilePO = chargingPileDao.selectByKey(inDto.getString("cp_id")); 
		httpModel.setOutMsg(AOSJson.toJson(chargingPilePO));
	}

	/**
	 * 保存charging_pile
	 * 
	 * @param httpModel
	 * @return
	 */
	 @Transactional
	public void saveChargingPile(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		ChargingPilePO chargingPilePO = new ChargingPilePO();
		chargingPilePO.copyProperties(inDto);
		chargingPilePO.setCp_id(AOSId.appId(SystemCons.ID.SYSTEM));
		chargingPilePO.setCreate_date(AOSUtils.getDateTime());
		chargingPileDao.insert(chargingPilePO);
		httpModel.setOutMsg("新增成功。");
	}

	/**
	 * 修改charging_pile
	 * 
	 * @param httpModel
	 * @return
	 */
	 @Transactional
	public void updateChargingPile(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		ChargingPilePO chargingPilePO = new ChargingPilePO();
		chargingPilePO.copyProperties(inDto);
		chargingPileDao.updateByKey(chargingPilePO);
		httpModel.setOutMsg("修改成功。");
	}

	/**
	 * 删除charging_pile
	 * 
	 * @param httpModel
	 */
	@Transactional
	public void deleteChargingPile(HttpModel httpModel) {
		String[] selectionIds = httpModel.getInDto().getRows();
		if(null!=selectionIds&&selectionIds.length>0){
			for (String id_ : selectionIds) {
				/*ChargingPilePO chargingPilePO = new ChargingPilePO();
				chargingPilePO.setCp_id(id_);
				chargingPilePO.setIs_del(SystemCons.IS.YES);
	            chargingPileDao.updateByKey(chargingPilePO);*/ 
	            chargingPileDao.deleteByKey(id_);
			}
		}else{
				String cp_id=httpModel.getInDto().getString("cp_id");
				/*ChargingPilePO chargingPilePO = new ChargingPilePO();
				chargingPilePO.setCp_id(cp_id);
				chargingPilePO.setIs_del(SystemCons.IS.YES);
	            chargingPileDao.updateByKey(chargingPilePO); */
			     chargingPileDao.deleteByKey(cp_id);
			
		}
		httpModel.setOutMsg("删除成功。");
	}

	

}
