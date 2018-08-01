package controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import aos.framework.core.cache.CacheMasterDataService;
import aos.framework.core.id.AOSId;
import aos.framework.core.service.CDZBaseController;
import aos.framework.core.typewrap.Dto;
import aos.framework.core.utils.AOSJson;
import aos.framework.core.utils.AOSUtils;
import aos.framework.web.router.HttpModel;
import aos.system.common.utils.SystemCons;
import dao.ChargingOrdersDao;
import po.ChargingOrdersPO;
import utils.ExcelUtils;

/**
 * charging_orders管理
 * 
 * @author duanchongfeng
 * @date 2017-07-06 20:19:04
 *
 */
@Service
public class ChargingOrdersController extends CDZBaseController {

	@Autowired
	private ChargingOrdersDao chargingOrdersDao;
	@Autowired
	private CacheMasterDataService cacheMasterDataService;

	/**
	 * charging_orders管理页面初始化
	 * 
	 * @param httpModel
	 * @return
	 */
	public void init(HttpModel httpModel) {
		httpModel.setAttribute("juid", httpModel.getInDto().getString("juid"));
		httpModel.setViewPath("myproject/chargingOrders.jsp");
	}

	/**
	 * 查询charging_orders列表
	 * 
	 * @param httpModel
	 * @return
	 */
	public void listChargingOrders(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		List<Dto> chargingOrdersDtos = sqlDao.list("ChargingOrders.listChargingOrderssPage", qDto);
		httpModel.setOutMsg(AOSJson.toGridJson(chargingOrdersDtos, qDto.getPageTotal()));
	}
	/**
	 * 查询charging_orders信息
	 * 
	 * @param httpModel
	 */
	public void getChargingOrders(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
	     ChargingOrdersPO chargingOrdersPO = chargingOrdersDao.selectByKey(inDto.getString("co_id")); 
		httpModel.setOutMsg(AOSJson.toJson(chargingOrdersPO));
	}

	/**
	 * 保存charging_orders
	 * 
	 * @param httpModel
	 * @return
	 */
	 @Transactional
	public void saveChargingOrders(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		ChargingOrdersPO chargingOrdersPO = new ChargingOrdersPO();
		chargingOrdersPO.copyProperties(inDto);
		chargingOrdersPO.setCo_id(AOSId.appId(SystemCons.ID.SYSTEM));
		chargingOrdersPO.setCreate_date(AOSUtils.getDateTime());
		chargingOrdersDao.insert(chargingOrdersPO);
		httpModel.setOutMsg("新增成功。");
	}

	/**
	 * 修改charging_orders
	 * 
	 * @param httpModel
	 * @return
	 */
	 @Transactional
	public void updateChargingOrders(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		ChargingOrdersPO chargingOrdersPO = new ChargingOrdersPO();
		chargingOrdersPO.copyProperties(inDto);
		chargingOrdersDao.updateByKey(chargingOrdersPO);
		httpModel.setOutMsg("修改成功。");
	}

	/**
	 * 删除charging_orders
	 * 
	 * @param httpModel
	 */
	@Transactional
	public void deleteChargingOrders(HttpModel httpModel) {
		String[] selectionIds = httpModel.getInDto().getRows();
		if(null!=selectionIds&&selectionIds.length>0){
			for (String id_ : selectionIds) {
				ChargingOrdersPO chargingOrdersPO = new ChargingOrdersPO();
				chargingOrdersPO.setCo_id(id_);
				chargingOrdersPO.setIs_del(SystemCons.IS.YES);
	            chargingOrdersDao.updateByKey(chargingOrdersPO); 
			}
		}else{
				String co_id=httpModel.getInDto().getString("co_id");
				ChargingOrdersPO chargingOrdersPO = new ChargingOrdersPO();
				chargingOrdersPO.setCo_id(co_id);
				chargingOrdersPO.setIs_del(SystemCons.IS.YES);
	            chargingOrdersDao.updateByKey(chargingOrdersPO); 
			
		}
		httpModel.setOutMsg("删除成功。");
	}

	public void exportExcel(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		HttpServletResponse response = httpModel.getResponse();
		String filename = AOSUtils.encodeChineseDownloadFileName(httpModel.getRequest().getHeader("USER-AGENT"),
				"充电记录导出列表.xlsx");
		response.setHeader("Content-Disposition", "attachment; filename=" + filename + ";");
		String path = httpModel.getRequest().getServletContext().getRealPath("/");
		File templetFile = new File(path + "/templet/order.xlsx");
		BufferedInputStream in;
		ServletOutputStream os = null;
		try {
			in = new BufferedInputStream(new FileInputStream(templetFile));
			ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
			byte[] temp = new byte[1024];
			int size = 0;
			while ((size = in.read(temp)) != -1) {
				out.write(temp, 0, size);
			}
			in.close();
			os = response.getOutputStream();
			
			List<String[]> datas = new ArrayList<String[]>();
			/*String[] s = new String[127];
			for (int j = 0; j < 10; j++) {
				for (int i = 0; i < s.length; i++) {
					s[i] = i + "数据";
				}
				datas.add(s);
			}*/
			List<Dto> chargingOrdersDtos = sqlDao.list("ChargingOrders.listChargingOrderss", qDto);
			for(Dto dto:chargingOrdersDtos){
				String[] s = new String[14];
				s[0]=dto.getString("b1_account_");
				s[1]=dto.getString("cp_no");
				s[2]=dto.getString("amounted");
				s[3]=dto.getString("dateed");
				s[4]=dto.getString("electricity");
				s[5]=dto.getString("rest_date");
				s[6]=dto.getString("total_amt");
				s[7]=dto.getString("deduction_amt");
				s[8]=dto.getString("real_amt");
				s[9]=cacheMasterDataService.getDicDesc("order_status_", dto.getString("status_"));
				s[10]=cacheMasterDataService.getDicDesc("co_type", dto.getString("co_type"));
				s[11]=dto.getString("co_num");
				s[12]=cacheMasterDataService.getDicDesc("deduction_type", dto.getString("deduction_type"));
				s[13]=dto.getString("create_date");
				datas.add(s);
			}
			ExcelUtils.exportExcel(1, templetFile, datas, os, 14);
			os.write(out.toByteArray());
			os.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
