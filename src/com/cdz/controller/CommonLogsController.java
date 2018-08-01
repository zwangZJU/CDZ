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
import aos.system.common.utils.SystemCons;
import dao.CommonLogsDao;
import po.CommonLogsPO;

/**
 * common_logs管理
 * 
 * @author duanchongfeng
 * @date 2017-07-06 22:16:46
 *
 */
@Service
public class CommonLogsController extends CDZBaseController {

	@Autowired
	private CommonLogsDao commonLogsDao;

	/**
	 * common_logs管理页面初始化
	 * 
	 * @param httpModel
	 * @return
	 */
	public void init(HttpModel httpModel) {
		httpModel.setAttribute("date_start",  AOSUtils.getDateStr());
		httpModel.setAttribute("date_end", AOSUtils.getDateStr());
		httpModel.setViewPath("myproject/commonLogs.jsp");
	}

	/**
	 * 查询common_logs列表
	 * 
	 * @param httpModel
	 * @return
	 */
	public void listCommonLogs(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		List<Dto> commonLogsDtos = sqlDao.list("CommonLogs.listCommonLogssPage", qDto);
		httpModel.setOutMsg(AOSJson.toGridJson(commonLogsDtos, qDto.getPageTotal()));
	}
	/**
	 * 查询common_logs信息
	 * 
	 * @param httpModel
	 */
	public void getCommonLogs(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
	     CommonLogsPO commonLogsPO = commonLogsDao.selectByKey(inDto.getString("log_id")); 
		httpModel.setOutMsg(AOSJson.toJson(commonLogsPO));
	}

	/**
	 * 保存common_logs
	 * 
	 * @param httpModel
	 * @return
	 */
	 @Transactional
	public void saveCommonLogs(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		CommonLogsPO commonLogsPO = new CommonLogsPO();
		commonLogsPO.copyProperties(inDto);
		commonLogsPO.setLog_id(AOSId.appId(SystemCons.ID.SYSTEM));
		commonLogsPO.setCreate_date(AOSUtils.getDateTime());
		commonLogsDao.insert(commonLogsPO);
		httpModel.setOutMsg("新增成功。");
	}

	/**
	 * 修改common_logs
	 * 
	 * @param httpModel
	 * @return
	 */
	 @Transactional
	public void updateCommonLogs(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		CommonLogsPO commonLogsPO = new CommonLogsPO();
		commonLogsPO.copyProperties(inDto);
		commonLogsDao.updateByKey(commonLogsPO);
		httpModel.setOutMsg("修改成功。");
	}

	/**
	 * 删除common_logs
	 * 
	 * @param httpModel
	 */
	@Transactional
	public void deleteCommonLogs(HttpModel httpModel) {
		String[] selectionIds = httpModel.getInDto().getRows();
		if(null!=selectionIds&&selectionIds.length>0){
			for (String id_ : selectionIds) {
				CommonLogsPO commonLogsPO = new CommonLogsPO();
				commonLogsPO.setLog_id(id_);
				commonLogsPO.setIs_del(SystemCons.IS.YES);
	            commonLogsDao.updateByKey(commonLogsPO); 
			}
		}else{
				String log_id=httpModel.getInDto().getString("log_id");
				CommonLogsPO commonLogsPO = new CommonLogsPO();
				commonLogsPO.setLog_id(log_id);
				commonLogsPO.setIs_del(SystemCons.IS.YES);
	            commonLogsDao.updateByKey(commonLogsPO); 
			
		}
		httpModel.setOutMsg("删除成功。");
	}
	@Transactional
	public void deleteAllCommonLogs(HttpModel httpModel) {
		int count=sqlDao.update("CommonLogs.deleteAllCommonLogs", Dtos.newDto());
		httpModel.setOutMsg("删除成功"+count+"条记录");
	}

	

}
