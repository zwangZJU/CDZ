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
import dao.OrdersPayDao;
import po.OrdersPayPO;

/**
 * orders_pay管理
 * 
 * @author duanchongfeng
 * @date 2017-07-18 21:56:19
 *
 */
@Service
public class OrdersPayController extends CDZBaseController {

	@Autowired
	private OrdersPayDao ordersPayDao;

	/**
	 * orders_pay管理页面初始化
	 * 
	 * @param httpModel
	 * @return
	 */
	public void init(HttpModel httpModel) {
		httpModel.setViewPath("myproject/ordersPay.jsp");
	}

	/**
	 * 查询orders_pay列表
	 * 
	 * @param httpModel
	 * @return
	 */
	public void listOrdersPay(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		List<Dto> ordersPayDtos = sqlDao.list("OrdersPay.listOrdersPaysPage", qDto);
		httpModel.setOutMsg(AOSJson.toGridJson(ordersPayDtos, qDto.getPageTotal()));
	}
	/**
	 * 查询orders_pay信息
	 * 
	 * @param httpModel
	 */
	public void getOrdersPay(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
	     OrdersPayPO ordersPayPO = ordersPayDao.selectByKey(inDto.getString("pay_id")); 
		httpModel.setOutMsg(AOSJson.toJson(ordersPayPO));
	}

	/**
	 * 保存orders_pay
	 * 
	 * @param httpModel
	 * @return
	 */
	 @Transactional
	public void saveOrdersPay(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		OrdersPayPO ordersPayPO = new OrdersPayPO();
		ordersPayPO.copyProperties(inDto);
		ordersPayPO.setPay_id(AOSId.appId(SystemCons.ID.SYSTEM));
		ordersPayPO.setCreate_date(AOSUtils.getDateTime());
		ordersPayDao.insert(ordersPayPO);
		httpModel.setOutMsg("新增成功。");
	}

	/**
	 * 修改orders_pay
	 * 
	 * @param httpModel
	 * @return
	 */
	 @Transactional
	public void updateOrdersPay(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		OrdersPayPO ordersPayPO = new OrdersPayPO();
		ordersPayPO.copyProperties(inDto);
		ordersPayDao.updateByKey(ordersPayPO);
		httpModel.setOutMsg("修改成功。");
	}

	/**
	 * 删除orders_pay
	 * 
	 * @param httpModel
	 */
	@Transactional
	public void deleteOrdersPay(HttpModel httpModel) {
		String[] selectionIds = httpModel.getInDto().getRows();
		if(null!=selectionIds&&selectionIds.length>0){
			for (String id_ : selectionIds) {
				OrdersPayPO ordersPayPO = new OrdersPayPO();
				ordersPayPO.setPay_id(id_);
				ordersPayPO.setIs_del(SystemCons.IS.YES);
	            ordersPayDao.updateByKey(ordersPayPO); 
			}
		}else{
				String pay_id=httpModel.getInDto().getString("pay_id");
				OrdersPayPO ordersPayPO = new OrdersPayPO();
				ordersPayPO.setPay_id(pay_id);
				ordersPayPO.setIs_del(SystemCons.IS.YES);
	            ordersPayDao.updateByKey(ordersPayPO); 
			
		}
		httpModel.setOutMsg("删除成功。");
	}

	

}
