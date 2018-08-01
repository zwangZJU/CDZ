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
import dao.RegionDao;
import po.RegionPO;

/**
 * region管理
 * 
 * @author duanchongfeng
 * @date 2017-04-19 20:27:12
 *
 */
@Service
public class RegionController extends CDZBaseController {

	@Autowired
	private RegionDao regionDao;

	/**
	 * region管理页面初始化
	 * 
	 * @param httpModel
	 * @return
	 */
	public void init(HttpModel httpModel) {
		httpModel.setViewPath("myproject/region.jsp");
	}

	/**
	 * 查询region列表
	 * 
	 * @param httpModel
	 * @return
	 */
	public void listRegion(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		List<Dto> regionDtos = sqlDao.list("Region.listRegionsPage", qDto);
		httpModel.setOutMsg(AOSJson.toGridJson(regionDtos, qDto.getPageTotal()));
	}
	/**
	 * 查询region信息
	 * 
	 * @param httpModel
	 */
	public void getRegion(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
	     RegionPO regionPO = regionDao.selectByKey(inDto.getString("region_id")); 
		httpModel.setOutMsg(AOSJson.toJson(regionPO));
	}

	/**
	 * 保存region
	 * 
	 * @param httpModel
	 * @return
	 */
	 @Transactional
	public void saveRegion(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		RegionPO regionPO = new RegionPO();
		regionPO.copyProperties(inDto);
		regionPO.setRegion_id(AOSId.appId(SystemCons.ID.SYSTEM));
		regionDao.insert(regionPO);
		httpModel.setOutMsg("新增成功。");
	}

	/**
	 * 修改region
	 * 
	 * @param httpModel
	 * @return
	 */
	 @Transactional
	public void updateRegion(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		RegionPO regionPO = new RegionPO();
		regionPO.copyProperties(inDto);
		regionDao.updateByKey(regionPO);
		httpModel.setOutMsg("修改成功。");
	}

	/**
	 * 删除region
	 * 
	 * @param httpModel
	 */
	@Transactional
	public void deleteRegion(HttpModel httpModel) {
		String[] selectionIds = httpModel.getInDto().getRows();
		if(null!=selectionIds&&selectionIds.length>0){
			for (String id_ : selectionIds) {
	            regionDao.deleteByKey(id_); 
			}
		}else{
				String region_id=httpModel.getInDto().getString("region_id");
	            regionDao.deleteByKey(region_id);
			
		}
		httpModel.setOutMsg("删除成功。");
	}

	

}
