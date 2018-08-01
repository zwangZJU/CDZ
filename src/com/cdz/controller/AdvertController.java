package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import aos.framework.core.id.AOSId;
import aos.framework.core.utils.AOSPropertiesHandler;
import aos.framework.core.utils.AOSUtils;
import aos.framework.core.service.CDZBaseController;
import aos.framework.core.typewrap.Dto;
import aos.framework.core.typewrap.Dtos;
import aos.framework.core.utils.AOSJson;
import aos.framework.web.router.HttpModel;
import aos.system.common.model.UserModel;
import aos.system.common.utils.SystemCons;
import dao.AdvertDao;
import po.AdvertPO;

/**
 * 广告表管理
 * 
 * @author duanchongfeng
 * @date 2017-04-21 20:56:28
 *
 */
@Service
public class AdvertController extends CDZBaseController {

	@Autowired
	private AdvertDao advertDao;

	/**
	 * 广告表管理页面初始化
	 * 
	 * @param httpModel
	 * @return
	 */
	public void init(HttpModel httpModel) {
		httpModel.setAttribute("juid", httpModel.getInDto().getString("juid"));
		httpModel.setViewPath("myproject/advert.jsp");
	}

	/**
	 * 查询广告表列表
	 * 
	 * @param httpModel
	 * @return
	 */
	public void listAdvert(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		List<Dto> advertDtos = sqlDao.list("Advert.listAdvertsPage", qDto);
		httpModel.setOutMsg(AOSJson.toGridJson(advertDtos, qDto.getPageTotal()));
	}
	/**
	 * 查询广告表信息
	 * 
	 * @param httpModel
	 */
	public void getAdvert(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
	     AdvertPO advertPO = advertDao.selectByKey(inDto.getString("advert_id")); 
		httpModel.setOutMsg(AOSJson.toJson(advertPO));
	}

	/**
	 * 保存广告表
	 * 
	 * @param httpModel
	 * @return
	 */
	 @Transactional
	public void saveAdvert(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		Dto outDto = Dtos.newOutDto();
		AdvertPO advertPO = new AdvertPO();
		advertPO.copyProperties(inDto);
		try {
			outDto=this.uploadFile(httpModel.getRequest(), httpModel.getResponse(), outDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(SystemCons.SUCCESS.equals(outDto.getAppCode())){
			UserModel userModel = httpModel.getUserModel();
			advertPO.setAdvert_id(AOSId.appId(SystemCons.ID.SYSTEM));
			advertPO.setCreate_date(AOSUtils.getDateTime());
			advertPO.setOper_id(userModel.getId_());
			advertPO.setImg_url(outDto.getAppMsg().replace("\\", "/"));
			advertDao.insert(advertPO);
			outDto.setAppMsg("新增成功。");
			outDto.put("success", true);
		}
		httpModel.setOutMsg(AOSJson.toJson(outDto));
	}

	/**
	 * 修改广告表
	 * 
	 * @param httpModel
	 * @return
	 */
	 @Transactional
	public void updateAdvert(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		Dto outDto = Dtos.newOutDto();
		AdvertPO advertPO = new AdvertPO();
		advertPO.copyProperties(inDto);
		try {
			outDto=this.uploadFile(httpModel.getRequest(), httpModel.getResponse(), outDto);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		if(SystemCons.SUCCESS.equals(outDto.getAppCode())){
			advertPO.setImg_url(outDto.getAppMsg().replace("\\", "/"));
		}
		advertDao.updateByKey(advertPO);
		outDto.setAppMsg("修改成功。");
		outDto.put("success", true);
		httpModel.setOutMsg(AOSJson.toJson(outDto));
	}

	/**
	 * 删除广告表
	 * 
	 * @param httpModel
	 */
	@Transactional
	public void deleteAdvert(HttpModel httpModel) {
		String[] selectionIds = httpModel.getInDto().getRows();
		if(null!=selectionIds&&selectionIds.length>0){
			for (String id_ : selectionIds) {
				AdvertPO advertPO = new AdvertPO();
				advertPO.setAdvert_id(id_);
				advertPO.setIs_del(SystemCons.IS.YES);
	            advertDao.updateByKey(advertPO); 
			}
		}else{
				String advert_id=httpModel.getInDto().getString("advert_id");
				AdvertPO advertPO = new AdvertPO();
				advertPO.setAdvert_id(advert_id);
				advertPO.setIs_del(SystemCons.IS.YES);
	            advertDao.updateByKey(advertPO); 
			
		}
		httpModel.setOutMsg("删除成功。");
	}

	

}
