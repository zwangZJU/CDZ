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
/*import cn.core.test.dao.mapper.Alarm_descMapper;
import cn.core.test.dao.po.Alarm_descPO;
import cn.core.test.modules.service.Alarm_descService;
import cn.osworks.aos.core.asset.AOSJson;
import cn.osworks.aos.core.asset.AOSUtils;
import cn.osworks.aos.core.asset.WebCxt;
import cn.osworks.aos.core.dao.SqlDao;
import cn.osworks.aos.core.excel.POIExcelUtil;
import cn.osworks.aos.core.typewrap.Dto;
import cn.osworks.aos.core.typewrap.Dtos;
import cn.osworks.aos.core.typewrap.impl.HashDto;*/
import dao.Alarm_descDao;
import po.Alarm_descPO;

/**
 * <b>警情代码对照表[alarm_desc] controller</b>
 * <p>
 * </p>
 * 
 * @author koney
 * @date 2018-08-10 16:40:12
 */
@Service
public class Alarm_descService extends CDZBaseController {

	@Autowired
	private Alarm_descDao alarm_descDao;

	/**
	 * charging_pile管理页面初始化
	 * 
	 * @param httpModel
	 * @return
	 */
	public void init(HttpModel httpModel) {
		httpModel.setViewPath("myproject/alarm_desc.jsp");
	}

	/**
	 * 查询charging_pile列表
	 * 
	 * @param httpModel
	 * @return
	 */
	public void listAlarm_desc(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		List<Dto> alarm_descDtos = sqlDao.list("Alarm_desc.listAlarm_descsPage", qDto);
		httpModel.setOutMsg(AOSJson.toGridJson(alarm_descDtos, qDto.getPageTotal()));
	}
	/**
	 * 查询charging_pile信息
	 * 
	 * @param httpModel
	 */
	public void getAlarm_desc(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
	     Alarm_descPO alarm_descPO = alarm_descDao.selectByKey(inDto.getString("cp_id")); 
		httpModel.setOutMsg(AOSJson.toJson(alarm_descPO));
	}

	/**
	 * 保存charging_pile
	 * 
	 * @param httpModel
	 * @return
	 */
	 @Transactional
	public void saveAlarm_desc(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		Alarm_descPO alarm_descPO = new Alarm_descPO();
		alarm_descPO.copyProperties(inDto);
		alarm_descPO.setId_(AOSId.appId(SystemCons.ID.SYSTEM));
		alarm_descDao.insert(alarm_descPO);
		httpModel.setOutMsg("新增成功。");
	}

	/**
	 * 修改charging_pile
	 * 
	 * @param httpModel
	 * @return
	 */
	 @Transactional
	public void updateAlarm_desc(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		Alarm_descPO alarm_descPO = new Alarm_descPO();
		alarm_descPO.copyProperties(inDto);
		alarm_descDao.updateByKey(alarm_descPO);
		httpModel.setOutMsg("修改成功。");
	}

	/**
	 * 删除charging_pile
	 * 
	 * @param httpModel
	 */
	@Transactional
	public void deleteAlarm_desc(HttpModel httpModel) {
		String[] selectionIds = httpModel.getInDto().getRows();
		if(null!=selectionIds&&selectionIds.length>0){
			for (String id_ : selectionIds) {
				/*Alarm_descPO alarm_descPO = new Alarm_descPO();
				alarm_descPO.setCp_id(id_);
				alarm_descPO.setIs_del(SystemCons.IS.YES);
	            alarm_descDao.updateByKey(alarm_descPO);*/ 
	            alarm_descDao.deleteByKey(id_);
			}
		}else{
				String cp_id=httpModel.getInDto().getString("cp_id");
				/*Alarm_descPO alarm_descPO = new Alarm_descPO();
				alarm_descPO.setCp_id(cp_id);
				alarm_descPO.setIs_del(SystemCons.IS.YES);
	            alarm_descDao.updateByKey(alarm_descPO); */
			     alarm_descDao.deleteByKey(cp_id);
			
		}
		httpModel.setOutMsg("删除成功。");
	}

	

}
