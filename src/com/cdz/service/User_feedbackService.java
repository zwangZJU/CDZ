package service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import aos.framework.core.id.AOSId;
import aos.framework.core.service.CDZBaseController;
import aos.framework.core.typewrap.Dto;
import aos.framework.core.utils.AOSJson;
import aos.framework.web.router.HttpModel;
import aos.system.common.utils.SystemCons;
import dao.User_feedbackDao;
import po.User_feedbackPO;

/**
 * <b>用户反馈建议[user_feedback] controller</b>
 * <p>
 * </p>
 * 
 * @author koney
 * @date 2018-09-05 10:48:30
 */
@Service
public class User_feedbackService extends CDZBaseController {

	@Autowired
	private User_feedbackDao user_feedbackDao;

	/**
	 * charging_pile管理页面初始化
	 * 
	 * @param httpModel
	 * @return
	 */
	public void init(HttpModel httpModel) {
		httpModel.setViewPath("myproject/user_feedback.jsp");
	}

	/**
	 * 查询charging_pile列表
	 * 
	 * @param httpModel
	 * @return
	 */
	public void listUser_feedback(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		List<Dto> user_feedbackDtos = sqlDao.list("User_feedback.listUser_feedbacksPage", qDto);
		httpModel.setOutMsg(AOSJson.toGridJson(user_feedbackDtos, qDto.getPageTotal()));
	}
	/**
	 * 查询charging_pile信息
	 * 
	 * @param httpModel
	 */
	public void getUser_feedback(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
	     User_feedbackPO user_feedbackPO = user_feedbackDao.selectByKey(inDto.getString("cp_id")); 
		httpModel.setOutMsg(AOSJson.toJson(user_feedbackPO));
	}

	/**
	 * 保存charging_pile
	 * 
	 * @param httpModel
	 * @return
	 */
	 @Transactional
	public void saveUser_feedback(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		User_feedbackPO user_feedbackPO = new User_feedbackPO();
		user_feedbackPO.copyProperties(inDto);
		//user_feedbackPO.setCp_id(AOSId.appId(SystemCons.ID.SYSTEM));
		//user_feedbackPO.setCreate_date(AOSUtils.getDateTime());
		user_feedbackDao.insert(user_feedbackPO);
		httpModel.setOutMsg("新增成功。");
	}

	/**
	 * 修改charging_pile
	 * 
	 * @param httpModel
	 * @return
	 */
	 @Transactional
	public void updateUser_feedback(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		User_feedbackPO user_feedbackPO = new User_feedbackPO();
		user_feedbackPO.copyProperties(inDto);
		user_feedbackDao.updateByKey(user_feedbackPO);
		httpModel.setOutMsg("修改成功。");
	}

	/**
	 * 删除charging_pile
	 * 
	 * @param httpModel
	 */
	@Transactional
	public void deleteUser_feedback(HttpModel httpModel) {
		String[] selectionIds = httpModel.getInDto().getRows();
		if(null!=selectionIds&&selectionIds.length>0){
			for (String id_ : selectionIds) {
				/*User_feedbackPO user_feedbackPO = new User_feedbackPO();
				user_feedbackPO.setCp_id(id_);
				user_feedbackPO.setIs_del(SystemCons.IS.YES);
	            user_feedbackDao.updateByKey(user_feedbackPO);*/ 
	            user_feedbackDao.deleteByKey(id_);
			}
		}else{
				String cp_id=httpModel.getInDto().getString("cp_id");
				/*User_feedbackPO user_feedbackPO = new User_feedbackPO();
				user_feedbackPO.setCp_id(cp_id);
				user_feedbackPO.setIs_del(SystemCons.IS.YES);
	            user_feedbackDao.updateByKey(user_feedbackPO); */
			     user_feedbackDao.deleteByKey(cp_id);
			
		}
		httpModel.setOutMsg("删除成功。");
	}

	

}
