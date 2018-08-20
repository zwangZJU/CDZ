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
import dao.CameraDao;
import po.CameraPO;

@Service
public class CameraService extends CDZBaseController {

	@Autowired
	private CameraDao cameraDao;

	/**
	 * charging_pile管理页面初始化
	 * 
	 * @param httpModel
	 * @return
	 */
	public void init(HttpModel httpModel) {
		httpModel.setViewPath("myproject/camera.jsp");
	}

	/**
	 * 查询charging_pile列表
	 * 
	 * @param httpModel
	 * @return
	 */
	public void listCamera(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		List<Dto> cameraDtos = sqlDao.list("Camera.listCamerasPage", qDto);
		httpModel.setOutMsg(AOSJson.toGridJson(cameraDtos, qDto.getPageTotal()));
	}
	
	/**
	 * 查询charging_pile信息
	 * 
	 * @param httpModel
	 */
	public void getCamera(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		CameraPO cameraPO = cameraDao.selectByKey(inDto.getString("camera_id"));
		httpModel.setOutMsg(AOSJson.toJson(cameraPO));
	}

	/**
	 * 保存charging_pile
	 * 
	 * @param httpModel
	 * @return
	 */
	@Transactional
	public void saveCamera(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		CameraPO cameraPO = new CameraPO();
		cameraPO.copyProperties(inDto);
		cameraPO.setCamera_id(AOSId.appId(SystemCons.ID.SYSTEM));
		// cameraPO.setCreate_date(AOSUtils.getDateTime());
		cameraDao.insert(cameraPO);
		httpModel.setOutMsg("新增成功。");
	}

	/**
	 * 修改charging_pile
	 * 
	 * @param httpModel
	 * @return
	 */
	@Transactional
	public void updateCamera(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		CameraPO cameraPO = new CameraPO();
		cameraPO.copyProperties(inDto);
		cameraDao.updateByKey(cameraPO);
		httpModel.setOutMsg("修改成功。");
	}

	/**
	 * 删除charging_pile
	 * 
	 * @param httpModel
	 */
	@Transactional
	public void deleteCamera(HttpModel httpModel) {
		String[] selectionIds = httpModel.getInDto().getRows();
		if (null != selectionIds && selectionIds.length > 0) {
			for (String camera_id : selectionIds) {
				/*
				 * CameraPO cameraPO = new CameraPO(); cameraPO.setCp_id(id_);
				 * cameraPO.setIs_del(SystemCons.IS.YES); cameraDao.updateByKey(cameraPO);
				 */
				cameraDao.deleteByKey(camera_id);
			}
		} else {
			String camera_id = httpModel.getInDto().getString("camera_id");
			/*
			 * CameraPO cameraPO = new CameraPO(); cameraPO.setCp_id(cp_id);
			 * cameraPO.setIs_del(SystemCons.IS.YES); cameraDao.updateByKey(cameraPO);
			 */
			cameraDao.deleteByKey(camera_id);

		}
		httpModel.setOutMsg("删除成功。");
	}

}
