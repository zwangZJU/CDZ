package controller;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import aos.framework.core.id.AOSId;
import aos.framework.core.service.CDZBaseController;
import aos.framework.core.typewrap.Dto;
import aos.framework.core.utils.AOSJson;
import aos.framework.core.utils.AOSPropertiesHandler;
import aos.framework.core.utils.AOSUtils;
import aos.framework.web.router.HttpModel;
import aos.system.common.utils.SystemCons;
import dao.CommonBackupDao;
import po.CommonBackupPO;

/**
 * common_backup管理
 * 
 * @author duanchongfeng
 * @date 2017-06-30 09:37:16
 *
 */
@Service
public class CommonBackupController extends CDZBaseController {

	@Autowired
	private CommonBackupDao commonBackupDao;

	/**
	 * common_backup管理页面初始化
	 * 
	 * @param httpModel
	 * @return
	 */
	public void init(HttpModel httpModel) {
		httpModel.setViewPath("myproject/commonBackup.jsp");
	}

	/**
	 * 查询common_backup列表
	 * 
	 * @param httpModel
	 * @return
	 */
	public void listCommonBackup(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		List<Dto> commonBackupDtos = sqlDao.list("CommonBackup.listCommonBackupsPage", qDto);
		httpModel.setOutMsg(AOSJson.toGridJson(commonBackupDtos, qDto.getPageTotal()));
	}
	/**
	 * 查询common_backup信息
	 * 
	 * @param httpModel
	 */
	public void getCommonBackup(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
	     CommonBackupPO commonBackupPO = commonBackupDao.selectByKey(inDto.getString("backup_id")); 
		httpModel.setOutMsg(AOSJson.toJson(commonBackupPO));
	}

	/**
	 * 保存common_backup
	 * 
	 * @param httpModel
	 * @return
	 */
	 @Transactional
	public void saveCommonBackup(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		CommonBackupPO commonBackupPO = new CommonBackupPO();
		commonBackupPO.copyProperties(inDto);
		commonBackupPO.setBackup_id(AOSId.appId(SystemCons.ID.SYSTEM));
		commonBackupPO.setCreate_date(AOSUtils.getDateTime());
		commonBackupDao.insert(commonBackupPO);
		httpModel.setOutMsg("新增成功。");
	}

	/**
	 * 修改common_backup
	 * 
	 * @param httpModel
	 * @return
	 */
	 @Transactional
	public void updateCommonBackup(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		CommonBackupPO commonBackupPO = new CommonBackupPO();
		commonBackupPO.copyProperties(inDto);
		commonBackupDao.updateByKey(commonBackupPO);
		httpModel.setOutMsg("修改成功。");
	}

	/**
	 * 删除common_backup
	 * 
	 * @param httpModel
	 */
	@Transactional
	public void deleteCommonBackup(HttpModel httpModel) {
		String[] selectionIds = httpModel.getInDto().getRows();
		if(null!=selectionIds&&selectionIds.length>0){
			for (String id_ : selectionIds) {
				CommonBackupPO commonBackupPO = new CommonBackupPO();
				commonBackupPO.setBackup_id(id_);
				commonBackupPO.setIs_del(SystemCons.IS.YES);
	            commonBackupDao.updateByKey(commonBackupPO); 
			}
		}else{
				String backup_id=httpModel.getInDto().getString("backup_id");
				CommonBackupPO commonBackupPO = new CommonBackupPO();
				commonBackupPO.setBackup_id(backup_id);
				commonBackupPO.setIs_del(SystemCons.IS.YES);
	            commonBackupDao.updateByKey(commonBackupPO); 
			
		}
		httpModel.setOutMsg("删除成功。");
	}

	 @Transactional
	public void addBackup(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		
		
		 try {
	            Runtime rt = Runtime.getRuntime();
	 
	            // 调用 调用mysql的安装目录的命令
	            Process child = rt
	                    .exec(AOSPropertiesHandler.getProperty("mysqldumpPath"));
	            // 设置导出编码为utf-8。这里必须是utf-8
	            // 把进程执行中的控制台输出信息写入.sql文件，即生成了备份文件。注：如果不对控制台信息进行读出，则会导致进程堵塞无法运行
	            InputStream in = child.getInputStream();// 控制台的输出信息作为输入流
	 
	            InputStreamReader xx = new InputStreamReader(in, "utf-8");
	            // 设置输出流编码为utf-8。这里必须是utf-8，否则从流中读入的是乱码
	 
	            String inStr;
	            StringBuffer sb = new StringBuffer("");
	            String outStr;
	            // 组合控制台输出信息字符串
	            BufferedReader br = new BufferedReader(xx);
	            while ((inStr = br.readLine()) != null) {
	                sb.append(inStr + "\r\n");
	            }
	            outStr = sb.toString();
	            String fileName=AOSUtils.getDateStr("yyyyMMddHHmmss")+".sql";
	            // 要用来做导入用的sql目标文件：
	            FileOutputStream fout = new FileOutputStream(AOSPropertiesHandler.getProperty("mysqlBackUpFilePath")+"\\"+fileName);
	            OutputStreamWriter writer = new OutputStreamWriter(fout, "utf-8");
	            writer.write(outStr);
	            writer.flush();
	            in.close();
	            xx.close();
	            br.close();
	            writer.close();
	            fout.close();
	            CommonBackupPO commonBackupPO = new CommonBackupPO();
	    		commonBackupPO.copyProperties(inDto);
	    		commonBackupPO.setFile_name(fileName);
	    		commonBackupPO.setFile_path(AOSPropertiesHandler.getProperty("mysqlBackUpFilePath"));
	    		commonBackupPO.setBackup_id(AOSId.appId(SystemCons.ID.SYSTEM));
	    		commonBackupPO.setCreate_date(AOSUtils.getDateTime());
	    		commonBackupPO.setIs_del("0");
	    		commonBackupPO.setOper_id(httpModel.getUserModel().getName_()+"手动备份");
	    		commonBackupDao.insert(commonBackupPO);
	            httpModel.setOutMsg("备份成功。");
	        } catch (Exception e) {
	            e.printStackTrace();
	            httpModel.setOutMsg("备份失败。");
	        }
		
		
		
	}

}
