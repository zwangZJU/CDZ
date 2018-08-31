package service;

import java.util.ArrayList;
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

	
	public void listUrl(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();

		Dto odto = Dtos.newDto();
		String device_id = qDto.getString("id");
		/* odto.put("hhhh", "44"); */

		Dto pDto = Dtos.newDto("device_id", device_id);
		
		Dto pDto1 = Dtos.newDto();
		int rows = cameraDao.rows(pDto1);
		pDto.put("limit", rows);// 默认查询出100个

		pDto.put("start", 0);
		String num;
		List<Dto> newListDtos = new ArrayList<Dto>();
		List<Dto> cameraDtos = sqlDao.list("Camera.listcameras", pDto);
		if (null != cameraDtos && !cameraDtos.isEmpty()) {
			num = String.valueOf(cameraDtos.size());
		for (Dto dto : cameraDtos) {
			Dto newDto = Dtos.newDto();
				
				newDto.put("rtmp", dto.getString("rtmp_"));
				newDto.put("hls", dto.getString("hls_"));
				/* newDto.put("is_completed", ""); */
				newDto.put("num", num);
			newListDtos.add(newDto);

		}
			odto.put("data", newListDtos);

		} else {
			num = "0";
			Dto newDto = Dtos.newDto();
			newDto.put("rtmp", "666");
			newDto.put("hls", "666");
			newDto.put("num", num);
			/* newDto.put("is_completed", ""); */

			newListDtos.add(newDto);
			odto.put("data", newListDtos);
		}



		httpModel.setOutMsg(AOSJson.toJson(odto));

	}


}
