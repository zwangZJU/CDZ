package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import aos.framework.core.id.AOSId;
import aos.framework.core.utils.AOSUtils;
import aos.framework.core.service.CDZBaseController;
import aos.framework.core.typewrap.Dto;
import aos.framework.core.typewrap.Dtos;
import aos.framework.core.utils.AOSJson;
import aos.framework.web.router.HttpModel;
import aos.system.common.model.UserModel;
import aos.system.common.utils.SystemCons;
import dao.ActivityDao;
import po.ActivityPO;

/**
 * 活动表管理
 * 
 * @author duanchongfeng
 * @date 2017-04-20 15:04:10
 *
 */
@Service
public class ActivityController extends CDZBaseController {

	@Autowired
	private ActivityDao activityDao;

	/**
	 * 活动表管理页面初始化
	 * 
	 * @param httpModel
	 * @return
	 */
	public void init(HttpModel httpModel) {
		httpModel.setAttribute("juid", httpModel.getInDto().getString("juid"));
		httpModel.setViewPath("myproject/activity.jsp");
	}

	public void init1(HttpModel httpModel) {
		httpModel.setAttribute("juid", httpModel.getInDto().getString("juid"));
		httpModel.setViewPath("myproject/activity1.jsp");
	}
	public void init2(HttpModel httpModel) {
		httpModel.setAttribute("juid", httpModel.getInDto().getString("juid"));
		httpModel.setViewPath("myproject/activity2.jsp");
	}
	public void init3(HttpModel httpModel) {
		httpModel.setAttribute("juid", httpModel.getInDto().getString("juid"));
		httpModel.setViewPath("myproject/activity3.jsp");
	}
	public void init4(HttpModel httpModel) {
		httpModel.setAttribute("juid", httpModel.getInDto().getString("juid"));
		httpModel.setViewPath("myproject/activity4.jsp");
	}
	/**
	 * 查询活动表列表
	 * 
	 * @param httpModel
	 * @return
	 */
	public void listActivity(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		List<Dto> activityDtos = sqlDao.list("Activity.listActivitysPage", qDto);
		httpModel.setOutMsg(AOSJson.toGridJson(activityDtos, qDto.getPageTotal()));
	}
	/**
	 * 查询活动表信息
	 * 
	 * @param httpModel
	 */
	public void getActivity(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
	     ActivityPO activityPO = activityDao.selectByKey(inDto.getString("activity_id")); 
		httpModel.setOutMsg(AOSJson.toJson(activityPO));
	}

	/**
	 * 保存活动表
	 * 
	 * @param httpModel
	 * @return
	 */
	 @Transactional
	public void saveActivity(HttpModel httpModel) {
		 Dto inDto = httpModel.getInDto();
			//Dto odto = Dtos.newDto();
			Dto outDto = Dtos.newOutDto();
			ActivityPO activityPO = new ActivityPO();
			activityPO.copyProperties(inDto);
			if(activityPO.getStart_date().getTime()>activityPO.getEnd_date().getTime()){
				outDto.setAppMsg("起始时间不能大于结束时间");
				outDto.put("success", false);
			}else if(!activityPO.getStatus_().equals("0")){
				boolean flag=true;
				String start_date=inDto.getString("start_date");
				String end_date=inDto.getString("end_date");
				Dto qDto=Dtos.newDto("date_string",start_date);
				qDto.put("type_", activityPO.getType_());
				List<Dto> activityDtos = sqlDao.list("Activity.listActivity", qDto);
				if(null!=activityDtos&&activityDtos.size()>0){
					flag=false;
					outDto.setAppMsg("起始时间不正确，已存在奖励记录中");
					outDto.put("success", false);
				}
				qDto=Dtos.newDto("date_string",end_date);
				qDto.put("type_", activityPO.getType_());
				activityDtos = sqlDao.list("Activity.listActivity", qDto);
				if(null!=activityDtos&&activityDtos.size()>0){
					flag=false;
					outDto.setAppMsg("结束时间不正确，已存在奖励记录中");
					outDto.put("success", false);
				}
				if(flag){
					
						UserModel userModel = httpModel.getUserModel();
						activityPO.setActivity_id(AOSId.appId(SystemCons.ID.SYSTEM));
						activityPO.setCreate_date(AOSUtils.getDateTime());
						activityPO.setImg_url(outDto.getAppMsg().replace("\\", "/"));
						activityPO.setOper_id(userModel.getId_());
						activityDao.insert(activityPO);
						outDto.setAppMsg("新增成功。");
						outDto.put("success", true);
				}
				
			}else{
				
					UserModel userModel = httpModel.getUserModel();
					activityPO.setActivity_id(AOSId.appId(SystemCons.ID.SYSTEM));
					activityPO.setCreate_date(AOSUtils.getDateTime());
					activityPO.setImg_url(outDto.getAppMsg().replace("\\", "/"));
					activityPO.setOper_id(userModel.getId_());
					activityDao.insert(activityPO);
					outDto.setAppMsg("新增成功。");
					outDto.put("success", true);
			}
		 
		 
		 
		 
		/*Dto inDto = httpModel.getInDto();
		Dto outDto = Dtos.newOutDto();
		ActivityPO activityPO = new ActivityPO();
		activityPO.copyProperties(inDto);
		try {
			outDto=this.uploadFile(httpModel.getRequest(), httpModel.getResponse(), outDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(SystemCons.SUCCESS.equals(outDto.getAppCode())){
			UserModel userModel = httpModel.getUserModel();
			activityPO.setActivity_id(AOSId.appId(SystemCons.ID.SYSTEM));
			activityPO.setCreate_date(AOSUtils.getDateTime());
			activityPO.setImg_url(outDto.getAppMsg().replace("\\", "/"));
			activityPO.setOper_id(userModel.getId_());
			activityDao.insert(activityPO);
			outDto.setAppMsg("新增成功。");
			outDto.put("success", true);
		}*/
		httpModel.setOutMsg(AOSJson.toJson(outDto));
	}

	/**
	 * 修改活动表
	 * 
	 * @param httpModel
	 * @return
	 */
	 @Transactional
	public void updateActivity(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		Dto outDto = Dtos.newOutDto();
		ActivityPO activityPO = new ActivityPO();
		activityPO.copyProperties(inDto);
		
		if(activityPO.getStart_date().getTime()>activityPO.getEnd_date().getTime()){
			outDto.setAppMsg("起始时间不能大于结束时间");
			outDto.put("success", false);
		}else if(!activityPO.getStatus_().equals("0")){
			boolean flag=true;
			String start_date=inDto.getString("start_date");
			String end_date=inDto.getString("end_date");
			Dto qDto=Dtos.newDto("date_string",start_date);
			qDto.put("type_", activityPO.getType_());
			qDto.put("not_activity_id", activityPO.getActivity_id());
			List<Dto> activityDtos = sqlDao.list("Activity.listActivity", qDto);
			if(null!=activityDtos&&activityDtos.size()>0){
				flag=false;
				outDto.setAppMsg("起始时间不正确，已存在奖励记录中");
				outDto.put("success", false);
			}
			qDto=Dtos.newDto("date_string",end_date);
			qDto.put("type_", activityPO.getType_());
			qDto.put("not_activity_id", activityPO.getActivity_id());
			activityDtos = sqlDao.list("Activity.listActivity", qDto);
			if(null!=activityDtos&&activityDtos.size()>0){
				flag=false;
				outDto.setAppMsg("结束时间不正确，已存在奖励记录中");
				outDto.put("success", false);
			}
			if(flag){
				
				activityDao.updateByKey(activityPO);
				outDto.setAppMsg("修改成功。");
				outDto.put("success", true);
			}
			
		}else{
				activityDao.updateByKey(activityPO);
				outDto.setAppMsg("修改成功。");
				outDto.put("success", true);
		}
		
		
		
		httpModel.setOutMsg(AOSJson.toJson(outDto));
	}

	/**
	 * 删除活动表
	 * 
	 * @param httpModel
	 */
	@Transactional
	public void deleteActivity(HttpModel httpModel) {
		String[] selectionIds = httpModel.getInDto().getRows();
		if(null!=selectionIds&&selectionIds.length>0){
			for (String id_ : selectionIds) {
				ActivityPO activityPO = new ActivityPO();
				activityPO.setActivity_id(id_);
				activityPO.setIs_del(SystemCons.IS.YES);
	            activityDao.updateByKey(activityPO); 
			}
		}else{
				String activity_id=httpModel.getInDto().getString("activity_id");
				ActivityPO activityPO = new ActivityPO();
				activityPO.setActivity_id(activity_id);
				activityPO.setIs_del(SystemCons.IS.YES);
	            activityDao.updateByKey(activityPO); 
			
		}
		httpModel.setOutMsg("删除成功。");
	}

	

}
