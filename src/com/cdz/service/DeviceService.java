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
import dao.DeviceDao;
import po.DevicePO;

@Service
public class DeviceService extends CDZBaseController {

	@Autowired
	private DeviceDao deviceDao;

	/**
	 * charging_pile管理页面初始化
	 * 
	 * @param httpModel
	 * @return
	 */
	public void init(HttpModel httpModel) {
		httpModel.setViewPath("myproject/device.jsp");
	}

	/**
	 * 查询charging_pile列
	 * 
	 * @param httpModel
	 * @return
	 */
	public void listDevice(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		List<Dto> deviceDtos = sqlDao.list("Device.listDevicesPage", qDto);
		httpModel.setOutMsg(AOSJson.toGridJson(deviceDtos, qDto.getPageTotal()));
	}
	
	/**
	 * 查询charging_pile信息
	 * 
	 * @param httpModel
	 */
	public void getDevice(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		DevicePO devicePO = deviceDao.selectByKey(inDto.getString("device_id"));
		httpModel.setOutMsg(AOSJson.toJson(devicePO));
	}
	
	public void listCoordinate(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		/*
		 * List<Dto> list = sqlDao.list("Repair_log.listHandler", httpModel.getInDto());
		 */
		Dto odto = Dtos.newDto();

		/* odto.put("hhhh", "44"); */

		Dto pDto = Dtos.newDto();
		int rows = deviceDao.rows(pDto);
		pDto.put("limit", rows);
		pDto.put("start", 0);
		List<Dto> deviceDtos = sqlDao.list("Device.listDevicesPage", pDto);
		List<Dto> newListDtos = new ArrayList<Dto>();

		for (Dto dto : deviceDtos) {
			Dto newDto = Dtos.newDto();
			String user_address = dto.getString("user_address");
			String[] info = user_address.split(" ");
			String lat = info[1];
			lat = lat.replace(",", "");
			String lon = info[3];
			newDto.put("lat", lat);
			newDto.put("lon", lon);
			newDto.put("number", Integer.toString(rows));

			newListDtos.add(newDto);

		}

		odto.put("coor", newListDtos);

		httpModel.setOutMsg(AOSJson.toJson(odto));

	}


	/**
	 * 保存charging_pile
	 * 
	 * @param httpModel
	 * @return
	 */
	@Transactional
	public void saveDevice(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		DevicePO devicePO = new DevicePO();
		devicePO.copyProperties(inDto);

		devicePO.setId_(AOSId.appId(SystemCons.ID.SYSTEM));
		// devicePO.setCreate_date(AOSUtils.getDateTime());

		deviceDao.insert(devicePO);
		httpModel.setOutMsg("新增成功。");
	}

	/**
	 * 修改charging_pile
	 * 
	 * @param httpModel
	 * @return
	 */
	@Transactional
	public void updateDevice(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		DevicePO devicePO = new DevicePO();
		devicePO.copyProperties(inDto);
		deviceDao.updateByKey(devicePO);
		httpModel.setOutMsg("修改成功。");
	}

	/**
	 * 删除charging_pile
	 * 
	 * @param httpModel
	 */
	@Transactional
	public void deleteDevice(HttpModel httpModel) {
		String[] selectionIds = httpModel.getInDto().getRows();
		if (null != selectionIds && selectionIds.length > 0) {
			for (String device_id : selectionIds) {
				/*
				 * DevicePO devicePO = new DevicePO(); devicePO.setCp_id(id_);
				 * devicePO.setIs_del(SystemCons.IS.YES); deviceDao.updateByKey(devicePO);
				 */
				deviceDao.deleteByKey(device_id);
//				deviceDao.deleteByKey(id_);
				
			}
		} else {
			String device_id = httpModel.getInDto().getString("device_id");
			/*
			 * DevicePO devicePO = new DevicePO(); devicePO.setCp_id(cp_id);
			 * devicePO.setIs_del(SystemCons.IS.YES); deviceDao.updateByKey(devicePO);
			 */
			deviceDao.deleteByKey(device_id);

		}
		httpModel.setOutMsg("删除成功。");
	}

}
