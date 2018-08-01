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
import dao.SubscribeDao;
import po.SubscribePO;

/**
 * subscribe管理
 * 
 * @author duanchongfeng
 * @date 2017-07-06 20:19:05
 *
 */
@Service
public class SubscribeController extends CDZBaseController {

	@Autowired
	private SubscribeDao subscribeDao;

	/**
	 * subscribe管理页面初始化
	 * 
	 * @param httpModel
	 * @return
	 */
	public void init(HttpModel httpModel) {
		httpModel.setViewPath("myproject/subscribe.jsp");
	}

	/**
	 * 查询subscribe列表
	 * 
	 * @param httpModel
	 * @return
	 */
	public void listSubscribe(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		List<Dto> subscribeDtos = sqlDao.list("Subscribe.listSubscribesPage", qDto);
		httpModel.setOutMsg(AOSJson.toGridJson(subscribeDtos, qDto.getPageTotal()));
	}
	/**
	 * 查询subscribe信息
	 * 
	 * @param httpModel
	 */
	public void getSubscribe(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
	     SubscribePO subscribePO = subscribeDao.selectByKey(inDto.getString("s_id")); 
		httpModel.setOutMsg(AOSJson.toJson(subscribePO));
	}

	/**
	 * 保存subscribe
	 * 
	 * @param httpModel
	 * @return
	 */
	 @Transactional
	public void saveSubscribe(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		SubscribePO subscribePO = new SubscribePO();
		subscribePO.copyProperties(inDto);
		subscribePO.setS_id(AOSId.appId(SystemCons.ID.SYSTEM));
		subscribePO.setCreate_date(AOSUtils.getDateTime());
		subscribeDao.insert(subscribePO);
		httpModel.setOutMsg("新增成功。");
	}

	/**
	 * 修改subscribe
	 * 
	 * @param httpModel
	 * @return
	 */
	 @Transactional
	public void updateSubscribe(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		SubscribePO subscribePO = new SubscribePO();
		subscribePO.copyProperties(inDto);
		subscribeDao.updateByKey(subscribePO);
		httpModel.setOutMsg("修改成功。");
	}

	/**
	 * 删除subscribe
	 * 
	 * @param httpModel
	 */
	@Transactional
	public void deleteSubscribe(HttpModel httpModel) {
		String[] selectionIds = httpModel.getInDto().getRows();
		if(null!=selectionIds&&selectionIds.length>0){
			for (String id_ : selectionIds) {
				SubscribePO subscribePO = new SubscribePO();
				subscribePO.setS_id(id_);
				subscribePO.setIs_del(SystemCons.IS.YES);
	            subscribeDao.updateByKey(subscribePO); 
			}
		}else{
				String s_id=httpModel.getInDto().getString("s_id");
				SubscribePO subscribePO = new SubscribePO();
				subscribePO.setS_id(s_id);
				subscribePO.setIs_del(SystemCons.IS.YES);
	            subscribeDao.updateByKey(subscribePO); 
			
		}
		httpModel.setOutMsg("删除成功。");
	}

	

}
