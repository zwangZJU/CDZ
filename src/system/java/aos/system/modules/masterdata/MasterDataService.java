package aos.system.modules.masterdata;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import aos.framework.core.cache.CacheMasterDataService;
import aos.framework.core.id.AOSId;
import aos.framework.core.service.CDZBaseController;
import aos.framework.core.typewrap.Dto;
import aos.framework.core.typewrap.Dtos;
import aos.framework.core.utils.AOSCons;
import aos.framework.core.utils.AOSJson;
import aos.framework.core.utils.AOSUtils;
import aos.framework.dao.AosDicDao;
import aos.framework.dao.AosDicPO;
import aos.framework.dao.AosParamsDao;
import aos.framework.dao.AosParamsPO;
import aos.framework.web.router.HttpModel;
import aos.system.common.utils.SystemCons;

/**
 * 基础数据管理
 * 
 * @author xiongchun
 *
 */
@Service
public class MasterDataService extends CDZBaseController{
	
	@Autowired
	private AosParamsDao aosParamsDao;
	@Autowired
	private AosDicDao aosDicDao;
	@Autowired
	private CacheMasterDataService cacheMasterDataService;

	/**
	 * 键值参数管理页面初始化
	 * 
	 * @param httpModel
	 * @return
	 */
	public void initParam(HttpModel httpModel) {

		httpModel.setViewPath("system/param.jsp");
	}
	
	public void initParam1(HttpModel httpModel) {

		httpModel.setViewPath("system/param1.jsp");
	}
	
	/**
	 * 数据字典管理页面初始化
	 * 
	 * @param httpModel
	 * @return
	 */
	public void initDictionary(HttpModel httpModel) {

		httpModel.setViewPath("system/dictionary.jsp");
	}
	public void initDictionary1(HttpModel httpModel) {
		httpModel.setAttribute("date_start",  AOSUtils.getDateStr());
		httpModel.setViewPath("system/dictionary.jsp");
	}
	
	/**
	 * 数据字典管理页面初始化
	 * 
	 * @param httpModel
	 * @return
	 */
	public void initLabelDictionary(HttpModel httpModel) {

		httpModel.setViewPath("myproject/dictionary.jsp");
	}
	
	/**
	 * 查询键值参数列表
	 * 
	 * @param httpModel
	 * @return
	 */
	public void listParam(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		qDto.put("group_", 2);
		List<Dto> paramDtos = sqlDao.list("MasterData.listParamInfosPage", qDto);
		httpModel.setOutMsg(AOSJson.toGridJson(paramDtos, qDto.getPageTotal()));
	}
	public void listParam1(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		qDto.put("group_", 3);
		List<Dto> paramDtos = sqlDao.list("MasterData.listParamInfosPage", qDto);
		httpModel.setOutMsg(AOSJson.toGridJson(paramDtos, qDto.getPageTotal()));
	}
	
	/**
	 * 保存键值参数
	 * 
	 * @param httpModel
	 * @return
	 */
	public void saveParam(HttpModel httpModel) {
		Dto outDto = Dtos.newOutDto();
		Dto inDto = httpModel.getInDto();
		if (aosParamsDao.rows(Dtos.newDto("key_", inDto.getString("key_"))) > 0) {
			outDto.setAppCode(AOSCons.ERROR);
			outDto.setAppMsg(AOSUtils.merge("操作失败，键[{0}]已经存在。", inDto.getString("key_")));
		}else {
			AosParamsPO aos_paramsPO = new AosParamsPO();
			aos_paramsPO.copyProperties(inDto);
			aos_paramsPO.setId_(AOSId.appId(SystemCons.ID.SYSTEM));
			aosParamsDao.insert(aos_paramsPO);
			cacheMasterDataService.cacheParamOption(aos_paramsPO.getKey_(), aos_paramsPO.getValue_());
			outDto.setAppMsg("键值参数新增成功");
		}
		httpModel.setOutMsg(AOSJson.toJson(outDto));
	}
	
	/**
	 * 修改键值参数
	 * 
	 * @param httpModel
	 * @return
	 */
	public void updateParam(HttpModel httpModel) {
		Dto outDto = Dtos.newOutDto();
		Dto inDto = httpModel.getInDto();
		AosParamsPO aos_paramsOldPO = aosParamsDao.selectByKey(inDto.getString("id_"));
		if (!StringUtils.equals(aos_paramsOldPO.getKey_(), inDto.getString("key_"))) {
			if (aosParamsDao.rows(Dtos.newDto("key_", inDto.getString("key_"))) > 0) {
				outDto.setAppCode(AOSCons.ERROR);
				outDto.setAppMsg(AOSUtils.merge("操作失败，键[{0}]已经存在。", inDto.getString("key_")));
			}
		}
		if (StringUtils.equals(outDto.getAppCode(), SystemCons.SUCCESS)) {
			AosParamsPO aos_paramsPO = new AosParamsPO();
			aos_paramsPO.copyProperties(inDto);
			aosParamsDao.updateByKey(aos_paramsPO);
			cacheMasterDataService.cacheParamOption(aos_paramsPO.getKey_(), aos_paramsPO.getValue_());
			outDto.setAppMsg("键值参数修改成功");
		}
		httpModel.setOutMsg(AOSJson.toJson(outDto));
	}
	public void updateParam1(HttpModel httpModel) {
		Dto outDto = Dtos.newOutDto();
		Dto inDto = httpModel.getInDto();
		AosParamsPO aos_paramsOldPO = aosParamsDao.selectByKey(inDto.getString("id_"));
		if (StringUtils.equals(outDto.getAppCode(), SystemCons.SUCCESS)) {
			AosParamsPO aos_paramsPO = new AosParamsPO();
			aos_paramsPO.copyProperties(inDto);
			aosParamsDao.updateByKey(aos_paramsPO);
			cacheMasterDataService.cacheParamOption(aos_paramsOldPO.getKey_(), aos_paramsPO.getValue_());
			outDto.setAppMsg("参数修改成功");
		}
		httpModel.setOutMsg(AOSJson.toJson(outDto));
	}
	/**
	 * 删除参数数据
	 * 
	 * @param httpModel
	 */
	@Transactional
	public void deleteParam(HttpModel httpModel) {
		String[] selectionIds = httpModel.getInDto().getRows();
		int rows = 0;
		for (String id_ : selectionIds) {
			AosParamsPO aos_paramsPO = aosParamsDao.selectByKey(id_);
			aosParamsDao.deleteByKey(id_);
			cacheMasterDataService.delParamOption(aos_paramsPO.getKey_());
			rows++;
		}
		httpModel.setOutMsg(AOSUtils.merge("操作成功，成功删除[{0}]条参数数据。", rows));
	}
	
	/**
	 * 查询字典列表
	 * 
	 * @param httpModel
	 * @return
	 */
	public void listDic(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		List<Dto> paramDtos = sqlDao.list("MasterData.listDicsPage", qDto);
		httpModel.setOutMsg(AOSJson.toGridJson(paramDtos, qDto.getPageTotal()));
	}
	/**
	 * 查询字典列表
	 * 
	 * @param httpModel
	 * @return
	 */
	public void listLabelDic(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		List<Dto> paramDtos = sqlDao.list("MasterData.listLabelDicsPage", qDto);
		httpModel.setOutMsg(AOSJson.toGridJson(paramDtos, qDto.getPageTotal()));
	}
	public void listPositionDic(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		List<Dto> paramDtos = sqlDao.list("MasterData.listPositionDicsPage", qDto);
		httpModel.setOutMsg(AOSJson.toGridJson(paramDtos, qDto.getPageTotal()));
	}
	
	public void listBankTypeDic(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		List<Dto> paramDtos = sqlDao.list("MasterData.listBankTypeDicsPage", qDto);
		httpModel.setOutMsg(AOSJson.toGridJson(paramDtos, qDto.getPageTotal()));
	}
	public void wapListBankTypeDic(HttpModel httpModel) {
		Map<String,String> inMap=this.getWapDto(httpModel);
		Dto qDto=Dtos.newDto("title",inMap.get("title"));
		qDto.put("limit", inMap.get("limit"));
		qDto.put("start", inMap.get("start"));
		qDto.put("page", inMap.get("page"));
		List<Dto> paramDtos = sqlDao.list("MasterData.listBankTypeDicsPage", qDto);
		httpModel.setOutMsg(AOSJson.toGridJson(paramDtos, qDto.getPageTotal()));
	}
	/**
	 * 保存字典
	 * 
	 * @param httpModel
	 * @return
	 */
	public void saveDic(HttpModel httpModel) {
		Dto outDto = Dtos.newOutDto();
		Dto inDto = httpModel.getInDto();
		Dto checkDto = Dtos.newDto("key_", inDto.getString("key_"));
		checkDto.put("code_", inDto.getString("code_"));
		if (aosDicDao.rows(checkDto) > 0) {
			outDto.setAppCode(AOSCons.ERROR);
			outDto.setAppMsg(AOSUtils.merge("操作失败，字典[{0}]的代码对照[{1}]已经存在。", inDto.getString("key_"), inDto.getString("code_")));
		}else {
			AosDicPO aos_dicPO = new AosDicPO();
			aos_dicPO.copyProperties(inDto);
			aos_dicPO.setId_(AOSId.appId(SystemCons.ID.SYSTEM));
			aosDicDao.insert(aos_dicPO);
			cacheMasterDataService.cacheDic(aos_dicPO);
			outDto.setAppMsg("数据字典新增成功。");
		}
		httpModel.setOutMsg(AOSJson.toJson(outDto));
	}
	public void saveLabelDic(HttpModel httpModel) {
		Dto outDto = Dtos.newOutDto();
		Dto inDto = httpModel.getInDto();
		AosDicPO aos_dicPO = new AosDicPO();
		aos_dicPO.copyProperties(inDto);
		aos_dicPO.setId_(AOSId.appId(SystemCons.ID.SYSTEM));
		aos_dicPO.setCode_(aos_dicPO.getId_());
		aos_dicPO.setKey_("article_label");
		aos_dicPO.setName_("文章标签");
		aos_dicPO.setIs_enable_("1");
		aos_dicPO.setGroup_("2");
		aosDicDao.insert(aos_dicPO);
		cacheMasterDataService.cacheDic(aos_dicPO);
		outDto.setAppMsg("文章标签添加成功。");
		httpModel.setOutMsg(AOSJson.toJson(outDto));
	}
	public void saveBankTypeDic(HttpModel httpModel) {
		Dto outDto = Dtos.newOutDto();
		Dto inDto = httpModel.getInDto();
		AosDicPO aos_dicPO = new AosDicPO();
		aos_dicPO.copyProperties(inDto);
		aos_dicPO.setId_(AOSId.appId(SystemCons.ID.SYSTEM));
		aos_dicPO.setCode_(aos_dicPO.getId_());
		aos_dicPO.setKey_("banktype");
		aos_dicPO.setName_("银行类型");
		aos_dicPO.setIs_enable_("1");
		aos_dicPO.setGroup_("2");
		aosDicDao.insert(aos_dicPO);
		cacheMasterDataService.cacheDic(aos_dicPO);
		outDto.setAppMsg("银行卡类别添加成功。");
		httpModel.setOutMsg(AOSJson.toJson(outDto));
	}
	
	
	/**
	 * 修改字典
	 * 
	 * @param httpModel
	 * @return
	 */
	public void updateDic(HttpModel httpModel) {
		Dto outDto = Dtos.newOutDto();
		Dto inDto = httpModel.getInDto();
		AosDicPO aos_dicOldPO = aosDicDao.selectByKey(inDto.getString("id_"));
		if (!StringUtils.equals(aos_dicOldPO.getCode_(), inDto.getString("code_"))) {
			Dto checkDto = Dtos.newDto("key_", inDto.getString("key_"));
			checkDto.put("code_", inDto.getString("code_"));
			if (aosDicDao.rows(checkDto) > 0) {
				outDto.setAppCode(AOSCons.ERROR);
				outDto.setAppMsg(AOSUtils.merge("操作失败，字典[{0}]的代码对照[{1}]已经存在。", inDto.getString("key_"), inDto.getString("code_")));
			}
		}else {
			AosDicPO aos_dicPO = new AosDicPO();
			aos_dicPO.copyProperties(inDto);
			aosDicDao.updateByKey(aos_dicPO);
			cacheMasterDataService.cacheDic(aos_dicPO);
			outDto.setAppMsg("数据字典修改成功。");
		}
		httpModel.setOutMsg(AOSJson.toJson(outDto));
	}
	
	public void updateBankTypeDic(HttpModel httpModel) {
		Dto outDto = Dtos.newOutDto();
		Dto inDto = httpModel.getInDto();
		AosDicPO aos_dicPO = new AosDicPO();
		aos_dicPO.copyProperties(inDto);
		//aos_dicPO.setDesc_(desc_);
		//aos_dicPO.setId_(id);
		aosDicDao.updateByKey(aos_dicPO);
		aos_dicPO=aosDicDao.selectByKey(aos_dicPO.getId_());
		cacheMasterDataService.cacheDic(aos_dicPO);
		outDto.setAppMsg("银行卡类别修改成功。");
		httpModel.setOutMsg(AOSJson.toJson(outDto));
	}
	public void getShareBonusDic(HttpModel httpModel) {
		Dto outDto = Dtos.newOutDto();
		outDto.put("mfwzmrssycs", aosDicDao.selectOne(Dtos.newDto("key_", "mfwzmrssycs")).getDesc_());
		outDto.put("sfwzmrssycs", aosDicDao.selectOne(Dtos.newDto("key_", "sfwzmrssycs")).getDesc_());
		outDto.put("yqfhbl", aosDicDao.selectOne(Dtos.newDto("key_", "yqfhbl")).getDesc_());
		outDto.put("dsfhbl", aosDicDao.selectOne(Dtos.newDto("key_", "dsfhbl")).getDesc_());
		outDto.put("xsfhbl", aosDicDao.selectOne(Dtos.newDto("key_", "xsfhbl")).getDesc_());
		httpModel.setOutMsg(AOSJson.toJson(outDto));
	}
	
	public void saveShareBonusDic(HttpModel httpModel) {
		/*
		 * Dto outDto = Dtos.newOutDto();
		Dto inDto = httpModel.getInDto();
		AosDicPO aos_dicPO = new AosDicPO();
		aos_dicPO.copyProperties(inDto);
		aos_dicPO.setId_(AOSId.appId(SystemCons.ID.SYSTEM));
		aos_dicPO.setCode_(aos_dicPO.getId_());
		aos_dicPO.setKey_("banktype");
		aos_dicPO.setName_("银行类型");
		aos_dicPO.setIs_enable_("1");
		aos_dicPO.setGroup_("2");
		aosDicDao.insert(aos_dicPO);
		cacheMasterDataService.cacheDic(aos_dicPO);
		outDto.setAppMsg("银行卡类别添加成功。");
		httpModel.setOutMsg(AOSJson.toJson(outDto));
		 */
		Dto outDto = Dtos.newOutDto();
		Dto inDto = httpModel.getInDto();
		String mfwzmrssycs=inDto.getString("mfwzmrssycs");
		AosDicPO mfwzmrssycsPO=aosDicDao.selectOne(Dtos.newDto("key_", "mfwzmrssycs"));
		mfwzmrssycsPO.setDesc_(mfwzmrssycs);
		aosDicDao.updateByKey(mfwzmrssycsPO);
		cacheMasterDataService.cacheDic(mfwzmrssycsPO);
		
		String sfwzmrssycs=inDto.getString("sfwzmrssycs");
		AosDicPO sfwzmrssycsPO=aosDicDao.selectOne(Dtos.newDto("key_", "sfwzmrssycs"));
		sfwzmrssycsPO.setDesc_(sfwzmrssycs);
		aosDicDao.updateByKey(sfwzmrssycsPO);
		cacheMasterDataService.cacheDic(sfwzmrssycsPO);
		
		String yqfhbl=inDto.getString("yqfhbl");
		AosDicPO yqfhblPO=aosDicDao.selectOne(Dtos.newDto("key_", "yqfhbl"));
		yqfhblPO.setDesc_(yqfhbl);
		aosDicDao.updateByKey(yqfhblPO);
		cacheMasterDataService.cacheDic(yqfhblPO);
		
		String dsfhbl=inDto.getString("dsfhbl");
		AosDicPO dsfhblPO=aosDicDao.selectOne(Dtos.newDto("key_", "dsfhbl"));
		dsfhblPO.setDesc_(dsfhbl);
		aosDicDao.updateByKey(dsfhblPO);
		cacheMasterDataService.cacheDic(dsfhblPO);
		
		String xsfhbl=inDto.getString("xsfhbl");
		AosDicPO xsfhblPO=aosDicDao.selectOne(Dtos.newDto("key_", "xsfhbl"));
		xsfhblPO.setDesc_(xsfhbl);
		aosDicDao.updateByKey(xsfhblPO);
		cacheMasterDataService.cacheDic(xsfhblPO);
		
		outDto.setAppMsg("设置保存成功。");
		httpModel.setOutMsg(AOSJson.toJson(outDto));
	}
	
	public void updateTypeDic(HttpModel httpModel,String id,String desc_) {
		Dto outDto = Dtos.newOutDto();
		Dto inDto = httpModel.getInDto();
		AosDicPO aos_dicPO = new AosDicPO();
		aos_dicPO.copyProperties(inDto);
		aos_dicPO.setId_(id);
		aos_dicPO.setDesc_(desc_);
		aosDicDao.updateByKey(aos_dicPO);
		aos_dicPO=aosDicDao.selectByKey(id);
		cacheMasterDataService.cacheDic(aos_dicPO);
	}
	/**
	 * 删除字典
	 * 
	 * @param httpModel
	 */
	public void deleteDic(HttpModel httpModel) {
		String[] selectionIds = httpModel.getInDto().getRows();
		int rows = 0;
		for (String id_ : selectionIds) {
			AosDicPO aos_dicPO = aosDicDao.selectByKey(id_);
			aosDicDao.deleteByKey(id_);
			cacheMasterDataService.delDic(aos_dicPO);
			rows++;
		}
		httpModel.setOutMsg(AOSUtils.merge("操作成功，成功删除[{0}]条字典数据。", rows));
	}
	public void deleteLabelDic(HttpModel httpModel) {
		String[] selectionIds = httpModel.getInDto().getRows();
		int rows = 0;
		for (String id_ : selectionIds) {
			AosDicPO aos_dicPO = aosDicDao.selectByKey(id_);
			aosDicDao.deleteByKey(id_);
			cacheMasterDataService.delDic(aos_dicPO);
			rows++;
		}
		httpModel.setOutMsg(AOSUtils.merge("操作成功，成功删除[{0}]条文章标签。", rows));
	}
	public void deleteBankTypeDic(HttpModel httpModel) {
		String[] selectionIds = httpModel.getInDto().getRows();
		int rows = 0;
		for (String id_ : selectionIds) {
			AosDicPO aos_dicPO = aosDicDao.selectByKey(id_);
			aosDicDao.deleteByKey(id_);
			cacheMasterDataService.delDic(aos_dicPO);
			rows++;
		}
		httpModel.setOutMsg(AOSUtils.merge("操作成功，成功删除[{0}]条银行卡类型。", rows));
	}
	public void deleteTypeDic(HttpModel httpModel) {
		String[] selectionIds = httpModel.getInDto().getRows();
		int rows = 0;
		for (String id_ : selectionIds) {
			AosDicPO aos_dicPO = aosDicDao.selectByKey(id_);
			aosDicDao.deleteByKey(id_);
			if(null!=aos_dicPO){
				cacheMasterDataService.delDic(aos_dicPO);
			}
			rows++;
		}
	}
	public void deleteTypeDic(String id) {
			AosDicPO aos_dicPO = aosDicDao.selectByKey(id);
			aosDicDao.deleteByKey(id);
			if(null!=aos_dicPO){
				cacheMasterDataService.delDic(aos_dicPO);
			}
	}
}
