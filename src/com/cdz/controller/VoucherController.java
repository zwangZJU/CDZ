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
import dao.VoucherDao;
import po.VoucherPO;

/**
 * 代金券表管理
 * 
 * @author duanchongfeng
 * @date 2017-04-19 20:27:11
 *
 */
@Service
public class VoucherController extends CDZBaseController {

	@Autowired
	private VoucherDao voucherDao;

	/**
	 * 代金券表管理页面初始化
	 * 
	 * @param httpModel
	 * @return
	 */
	public void init(HttpModel httpModel) {
		httpModel.setViewPath("myproject/voucher.jsp");
	}

	/**
	 * 查询代金券表列表
	 * 
	 * @param httpModel
	 * @return
	 */
	public void listVoucher(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		List<Dto> voucherDtos = sqlDao.list("Voucher.listVouchersPage", qDto);
		httpModel.setOutMsg(AOSJson.toGridJson(voucherDtos, qDto.getPageTotal()));
	}
	/**
	 * 查询代金券表信息
	 * 
	 * @param httpModel
	 */
	public void getVoucher(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
	     VoucherPO voucherPO = voucherDao.selectByKey(inDto.getString("voucher_id")); 
		httpModel.setOutMsg(AOSJson.toJson(voucherPO));
	}

	/**
	 * 保存代金券表
	 * 
	 * @param httpModel
	 * @return
	 */
	 @Transactional
	public void saveVoucher(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		VoucherPO voucherPO = new VoucherPO();
		voucherPO.copyProperties(inDto);
		voucherPO.setVoucher_id(AOSId.appId(SystemCons.ID.SYSTEM));
		voucherPO.setCreate_date(AOSUtils.getDateTime());
		voucherDao.insert(voucherPO);
		httpModel.setOutMsg("新增成功。");
	}

	/**
	 * 修改代金券表
	 * 
	 * @param httpModel
	 * @return
	 */
	 @Transactional
	public void updateVoucher(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		VoucherPO voucherPO = new VoucherPO();
		voucherPO.copyProperties(inDto);
		voucherDao.updateByKey(voucherPO);
		httpModel.setOutMsg("修改成功。");
	}

	/**
	 * 删除代金券表
	 * 
	 * @param httpModel
	 */
	@Transactional
	public void deleteVoucher(HttpModel httpModel) {
		String[] selectionIds = httpModel.getInDto().getRows();
		if(null!=selectionIds&&selectionIds.length>0){
			for (String id_ : selectionIds) {
				VoucherPO voucherPO = new VoucherPO();
				voucherPO.setVoucher_id(id_);
				voucherPO.setIs_del(SystemCons.IS.YES);
	            voucherDao.updateByKey(voucherPO); 
			}
		}else{
				String voucher_id=httpModel.getInDto().getString("voucher_id");
				VoucherPO voucherPO = new VoucherPO();
				voucherPO.setVoucher_id(voucher_id);
				voucherPO.setIs_del(SystemCons.IS.YES);
	            voucherDao.updateByKey(voucherPO); 
			
		}
		httpModel.setOutMsg("删除成功。");
	}

	

}
