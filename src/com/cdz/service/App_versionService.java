package service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import aos.framework.core.id.AOSId;
import aos.framework.core.service.CDZBaseController;
import aos.framework.core.typewrap.Dto;
import aos.framework.core.typewrap.Dtos;
import aos.framework.core.utils.AOSJson;
import aos.framework.web.router.HttpModel;
import aos.system.common.utils.SystemCons;
import dao.App_versionDao;
import po.App_versionPO;

@Service
public class App_versionService extends CDZBaseController {

	@Autowired
	private App_versionDao app_versionDao;

	/**
	 * charging_pile管理页面初始化
	 * 
	 * @param httpModel
	 * @return
	 */
	public void init(HttpModel httpModel) {
		httpModel.setViewPath("myproject/app_version.jsp");
	}

	/**
	 * 查询charging_pile列表
	 * 
	 * @param httpModel
	 * @return
	 */
	public void listApp_version(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		List<Dto> app_versionDtos = sqlDao.list("App_version.listApp_versionsPage", qDto);
		httpModel.setOutMsg(AOSJson.toGridJson(app_versionDtos, qDto.getPageTotal()));
	}
	
	/**
	 * 查询charging_pile信息
	 * 
	 * @param httpModel
	 */
	public void getApp_version(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		App_versionPO app_versionPO = app_versionDao.selectByKey(inDto.getString("cp_id"));
		httpModel.setOutMsg(AOSJson.toJson(app_versionPO));
	}

	/**
	 * 保存charging_pile
	 * 
	 * @param httpModel
	 * @return
	 */
	@Transactional
	public void saveApp_version(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		App_versionPO app_versionPO = new App_versionPO();
		app_versionPO.copyProperties(inDto);
		app_versionPO.setApp_vesino_id(AOSId.appId(SystemCons.ID.SYSTEM));
		/* app_versionPO.setCreate_date(AOSUtils.getDateTime()); */
		app_versionDao.insert(app_versionPO);
		httpModel.setOutMsg("新增成功。");
	}

	/**
	 * 修改charging_pile
	 * 
	 * @param httpModel
	 * @return
	 */
	@Transactional
	public void updateApp_version(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		App_versionPO app_versionPO = new App_versionPO();
		app_versionPO.copyProperties(inDto);
		app_versionDao.updateByKey(app_versionPO);
		httpModel.setOutMsg("修改成功。");
	}

	/**
	 * 删除charging_pile
	 * 
	 * @param httpModel
	 */
	@Transactional
	public void deleteApp_version(HttpModel httpModel) {
		String[] selectionIds = httpModel.getInDto().getRows();
		if (null != selectionIds && selectionIds.length > 0) {
			for (String app_vesino_id : selectionIds) {
				/*
				 * App_versionPO app_versionPO = new App_versionPO();
				 * app_versionPO.setCp_id(id_); app_versionPO.setIs_del(SystemCons.IS.YES);
				 * app_versionDao.updateByKey(app_versionPO);
				 */
				app_versionDao.deleteByKey(app_vesino_id);
			}
		} else {
			String app_vesino_id = httpModel.getInDto().getString("app_vesino_id");
			/*
			 * App_versionPO app_versionPO = new App_versionPO();
			 * app_versionPO.setCp_id(cp_id); app_versionPO.setIs_del(SystemCons.IS.YES);
			 * app_versionDao.updateByKey(app_versionPO);
			 */
			app_versionDao.deleteByKey(app_vesino_id);

		}
		httpModel.setOutMsg("删除成功。");
	}
	
	@Transactional
	public void uploadVersionFile(HttpModel httpModel) {

		

		HttpServletRequest request = httpModel.getRequest();
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		if (multipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				MultipartFile file = multiRequest.getFile((String) iter.next());
				if (file != null) {
					String fileName = file.getOriginalFilename();

					Dto pDto = Dtos.newDto("version_no", fileName);
					App_versionPO app_versionPO1 = app_versionDao.selectOne(pDto);
					if (null != app_versionPO1) {
						httpModel.setOutMsg("{\"success\":\"false\",\"msg\":\"文件已存在！\"}");
						break;
					}
					/*String path = "D:/github/CDZ7.09/webapp/app/" + fileName;*/
					 String path = "C:/zhihuianfang/code/CDZ7.09/webapp/app/" + fileName; 

					File localFile = new File(path);

					try {
						file.transferTo(localFile);
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					String url = "http://118.126.95.215:9090/cdz/app/" + fileName;

					App_versionPO app_versionPO = new App_versionPO();
					app_versionPO.setApp_vesino_id(AOSId.appId(SystemCons.ID.SYSTEM));
					/* app_versionPO.setVersion_no(fileName); */
					Date date = new Date();
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					app_versionPO.setRelease_time(date);
					app_versionPO.setDownload_url(url);
					app_versionPO.setDownload_times("0");
					app_versionDao.insert(app_versionPO);
					httpModel.setOutMsg("{\"success\":\"true\",\"msg\":\"上传成功！\"}");
				}

			}

		}

	}


}
