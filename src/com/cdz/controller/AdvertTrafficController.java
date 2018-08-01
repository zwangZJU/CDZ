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
import dao.AdvertTrafficDao;
import po.AdvertTrafficPO;

/**
 * advert_traffic管理
 * 
 * @author duanchongfeng
 * @date 2017-07-06 20:19:05
 *
 */
@Service
public class AdvertTrafficController extends CDZBaseController {

	@Autowired
	private AdvertTrafficDao advertTrafficDao;

	/**
	 * advert_traffic管理页面初始化
	 * 
	 * @param httpModel
	 * @return
	 */
	public void init(HttpModel httpModel) {
		httpModel.setViewPath("myproject/advertTraffic.jsp");
	}

	/**
	 * 查询advert_traffic列表
	 * 
	 * @param httpModel
	 * @return
	 */
	public void listAdvertTraffic(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		List<Dto> advertTrafficDtos = sqlDao.list("AdvertTraffic.listAdvertTrafficsPage", qDto);
		httpModel.setOutMsg(AOSJson.toGridJson(advertTrafficDtos, qDto.getPageTotal()));
	}
	/**
	 * 查询advert_traffic信息
	 * 
	 * @param httpModel
	 */
	public void getAdvertTraffic(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
	     AdvertTrafficPO advertTrafficPO = advertTrafficDao.selectByKey(inDto.getString("at_id")); 
		httpModel.setOutMsg(AOSJson.toJson(advertTrafficPO));
	}

	/**
	 * 保存advert_traffic
	 * 
	 * @param httpModel
	 * @return
	 */
	 @Transactional
	public void saveAdvertTraffic(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		AdvertTrafficPO advertTrafficPO = new AdvertTrafficPO();
		advertTrafficPO.copyProperties(inDto);
		advertTrafficPO.setAt_id(AOSId.appId(SystemCons.ID.SYSTEM));
		advertTrafficPO.setCreate_date(AOSUtils.getDateTime());
		advertTrafficDao.insert(advertTrafficPO);
		httpModel.setOutMsg("新增成功。");
	}

	/**
	 * 修改advert_traffic
	 * 
	 * @param httpModel
	 * @return
	 */
	 @Transactional
	public void updateAdvertTraffic(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		AdvertTrafficPO advertTrafficPO = new AdvertTrafficPO();
		advertTrafficPO.copyProperties(inDto);
		advertTrafficDao.updateByKey(advertTrafficPO);
		httpModel.setOutMsg("修改成功。");
	}

	/**
	 * 删除advert_traffic
	 * 
	 * @param httpModel
	 */
	@Transactional
	public void deleteAdvertTraffic(HttpModel httpModel) {
		String[] selectionIds = httpModel.getInDto().getRows();
		if(null!=selectionIds&&selectionIds.length>0){
			for (String id_ : selectionIds) {
				AdvertTrafficPO advertTrafficPO = new AdvertTrafficPO();
				advertTrafficPO.setAt_id(id_);
				advertTrafficPO.setIs_del(SystemCons.IS.YES);
	            advertTrafficDao.updateByKey(advertTrafficPO); 
			}
		}else{
				String at_id=httpModel.getInDto().getString("at_id");
				AdvertTrafficPO advertTrafficPO = new AdvertTrafficPO();
				advertTrafficPO.setAt_id(at_id);
				advertTrafficPO.setIs_del(SystemCons.IS.YES);
	            advertTrafficDao.updateByKey(advertTrafficPO); 
			
		}
		httpModel.setOutMsg("删除成功。");
	}

	

}
