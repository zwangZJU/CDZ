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
import dao.CommonCommentDao;
import po.CommonCommentPO;

/**
 * common_comment管理
 * 
 * @author duanchongfeng
 * @date 2017-07-06 20:31:42
 *
 */
@Service
public class CommonCommentController extends CDZBaseController {

	@Autowired
	private CommonCommentDao commonCommentDao;

	/**
	 * common_comment管理页面初始化
	 * 
	 * @param httpModel
	 * @return
	 */
	public void init(HttpModel httpModel) {
		httpModel.setViewPath("myproject/commonComment.jsp");
	}

	/**
	 * 查询common_comment列表
	 * 
	 * @param httpModel
	 * @return
	 */
	public void listCommonComment(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		List<Dto> commonCommentDtos = sqlDao.list("CommonComment.listCommonCommentsPage", qDto);
		httpModel.setOutMsg(AOSJson.toGridJson(commonCommentDtos, qDto.getPageTotal()));
	}
	/**
	 * 查询common_comment信息
	 * 
	 * @param httpModel
	 */
	public void getCommonComment(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
	     CommonCommentPO commonCommentPO = commonCommentDao.selectByKey(inDto.getString("cc_id")); 
		httpModel.setOutMsg(AOSJson.toJson(commonCommentPO));
	}

	/**
	 * 保存common_comment
	 * 
	 * @param httpModel
	 * @return
	 */
	 @Transactional
	public void saveCommonComment(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		CommonCommentPO commonCommentPO = new CommonCommentPO();
		commonCommentPO.copyProperties(inDto);
		commonCommentPO.setCc_id(AOSId.appId(SystemCons.ID.SYSTEM));
		commonCommentPO.setCreate_date(AOSUtils.getDateTime());
		commonCommentDao.insert(commonCommentPO);
		httpModel.setOutMsg("新增成功。");
	}

	/**
	 * 修改common_comment
	 * 
	 * @param httpModel
	 * @return
	 */
	 @Transactional
	public void updateCommonComment(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		CommonCommentPO commonCommentPO = new CommonCommentPO();
		commonCommentPO.copyProperties(inDto);
		commonCommentDao.updateByKey(commonCommentPO);
		httpModel.setOutMsg("修改成功。");
	}

	/**
	 * 删除common_comment
	 * 
	 * @param httpModel
	 */
	@Transactional
	public void deleteCommonComment(HttpModel httpModel) {
		String[] selectionIds = httpModel.getInDto().getRows();
		if(null!=selectionIds&&selectionIds.length>0){
			for (String id_ : selectionIds) {
				CommonCommentPO commonCommentPO = new CommonCommentPO();
				commonCommentPO.setCc_id(id_);
				commonCommentPO.setIs_del(SystemCons.IS.YES);
	            commonCommentDao.updateByKey(commonCommentPO); 
			}
		}else{
				String cc_id=httpModel.getInDto().getString("cc_id");
				CommonCommentPO commonCommentPO = new CommonCommentPO();
				commonCommentPO.setCc_id(cc_id);
				commonCommentPO.setIs_del(SystemCons.IS.YES);
	            commonCommentDao.updateByKey(commonCommentPO); 
			
		}
		httpModel.setOutMsg("删除成功。");
	}

	

}
