package controller;

import java.math.BigDecimal;
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
import dao.ActivityDao;
import dao.ActivityRuleDao;
import po.ActivityPO;
import po.ActivityRulePO;

/**
 * 活动规则表管理
 * 
 * @author duanchongfeng
 * @date 2017-04-21 20:57:29
 *
 */
@Service
public class ActivityRuleController extends CDZBaseController {

	@Autowired
	private ActivityRuleDao activityRuleDao;
	
	@Autowired
	private ActivityDao activityDao;

	/**
	 * 活动规则表管理页面初始化
	 * 
	 * @param httpModel
	 * @return
	 */
	public void init(HttpModel httpModel) {
		httpModel.setViewPath("myproject/activityRule.jsp");
	}

	/**
	 * 查询活动规则表列表
	 * 
	 * @param httpModel
	 * @return
	 */
	public void listActivityRule(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		List<Dto> activityRuleDtos = sqlDao.list("ActivityRule.listActivityRulesPage", qDto);
		httpModel.setOutMsg(AOSJson.toGridJson(activityRuleDtos, qDto.getPageTotal()));
	}
	/**
	 * 查询活动规则表信息
	 * 
	 * @param httpModel
	 */
	public void getActivityRule(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
	     ActivityRulePO activityRulePO = activityRuleDao.selectByKey(inDto.getString("ar_id")); 
		httpModel.setOutMsg(AOSJson.toJson(activityRulePO));
	}

	/**
	 * 保存活动规则表
	 * 
	 * @param httpModel
	 * @return
	 */
	 @Transactional
	public void saveActivityRule(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();
		ActivityRulePO activityRulePO = new ActivityRulePO();
		activityRulePO.copyProperties(inDto);
		ActivityPO activityPO =activityDao.selectByKey(activityRulePO.getActivity_id());
		activityRulePO.setAr_id(AOSId.appId(SystemCons.ID.SYSTEM));
		activityRuleDao.insert(activityRulePO);
		this.sucessMsg(odto, "新增成功。");
		
		httpModel.setOutMsg(AOSJson.toJson(odto));
	}

	/**
	 * 修改活动规则表
	 * 
	 * @param httpModel
	 * @return
	 */
	 @Transactional
	public void updateActivityRule(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();
		ActivityRulePO activityRulePO = new ActivityRulePO();
		activityRulePO.copyProperties(inDto);
		ActivityPO activityPO =activityDao.selectByKey(activityRulePO.getActivity_id());
		activityRuleDao.updateByKey(activityRulePO);
		this.sucessMsg(odto, "新增成功。");
		
		httpModel.setOutMsg(AOSJson.toJson(odto));
	}

	/**
	 * 删除活动规则表
	 * 
	 * @param httpModel
	 */
	@Transactional
	public void deleteActivityRule(HttpModel httpModel) {
		String[] selectionIds = httpModel.getInDto().getRows();
		if(null!=selectionIds&&selectionIds.length>0){
			for (String id_ : selectionIds) {
				
	            activityRuleDao.deleteByKey(id_);
			}
		}else{
				String ar_id=httpModel.getInDto().getString("ar_id");
	            activityRuleDao.deleteByKey(ar_id);
			
		}
		httpModel.setOutMsg("删除成功。");
	}

	

}
