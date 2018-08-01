package controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConstants;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;

import aos.framework.core.id.AOSId;
import aos.framework.core.service.CDZBaseController;
import aos.framework.core.typewrap.Dto;
import aos.framework.core.typewrap.Dtos;
import aos.framework.core.utils.AOSCxt;
import aos.framework.core.utils.AOSJson;
import aos.framework.core.utils.AOSUtils;
import aos.framework.web.router.HttpModel;
import aos.system.common.utils.SystemCons;
import dao.DepositListDao;
import dao.MembersDao;
import dao.OrdersPayDao;
import pay.wxpay.GetWxOrderno;
import pay.wxpay.TenpayUtil;
import pay.wxpay.refund.ClientCustomSSL;
import pay.wxpay.refund.RequestHandler2;
import po.DepositListPO;
import po.MembersPO;
import po.OrdersPayPO;

/**
 * deposit_list管理
 * 
 * @author duanchongfeng
 * @date 2017-07-06 20:19:04
 *
 */
@Service
public class DepositListController extends CDZBaseController {

	@Autowired
	private DepositListDao depositListDao;
	@Autowired
	private MembersDao membersDao;
	@Autowired
	OrdersPayDao ordersPayDao;

	/**
	 * deposit_list管理页面初始化
	 * 
	 * @param httpModel
	 * @return
	 */
	public void init(HttpModel httpModel) {
		httpModel.setViewPath("myproject/depositList.jsp");
	}
	public void init1(HttpModel httpModel) {
		httpModel.setViewPath("myproject/depositList1.jsp");
	}
	public void init2(HttpModel httpModel) {
		httpModel.setViewPath("myproject/depositList2.jsp");
	}

	public void balance(HttpModel httpModel) {
		httpModel.setViewPath("myproject/depositBalanceList.jsp");
	}
	public void balance1(HttpModel httpModel) {
		httpModel.setViewPath("myproject/depositBalanceList1.jsp");
	}
	public void balance2(HttpModel httpModel) {
		httpModel.setViewPath("myproject/depositBalanceList2.jsp");
	}
	/**
	 * 查询deposit_list列表
	 * 
	 * @param httpModel
	 * @return
	 */
	public void listDepositList(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		List<Dto> depositListDtos = sqlDao.list("DepositList.listDepositListsPage", qDto);
		httpModel.setOutMsg(AOSJson.toGridJson(depositListDtos, qDto.getPageTotal()));
	}
	public void list1DepositList(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		List<Dto> depositListDtos = sqlDao.list("DepositList.list1DepositListsPage", qDto);
		httpModel.setOutMsg(AOSJson.toGridJson(depositListDtos, qDto.getPageTotal()));
	}
	public void list2DepositList(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		List<Dto> depositListDtos = sqlDao.list("DepositList.list2DepositListsPage", qDto);
		httpModel.setOutMsg(AOSJson.toGridJson(depositListDtos, qDto.getPageTotal()));
	}
	/**
	 * 查询deposit_list信息
	 * 
	 * @param httpModel
	 */
	public void getDepositList(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
	     DepositListPO depositListPO = depositListDao.selectByKey(inDto.getString("deposit_id")); 
	     depositListPO.setUser_id(membersDao.selectByKey(depositListPO.getUser_id()).getAccount_());
		httpModel.setOutMsg(AOSJson.toJson(depositListPO));
	}

	/**
	 * 保存deposit_list
	 * 
	 * @param httpModel
	 * @return
	 */
	 @Transactional
	public void saveDepositList(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		DepositListPO depositListPO = new DepositListPO();
		depositListPO.copyProperties(inDto);
		depositListPO.setDeposit_id(AOSId.appId(SystemCons.ID.SYSTEM));
		depositListPO.setCreate_date(AOSUtils.getDateTime());
		depositListDao.insert(depositListPO);
		httpModel.setOutMsg("新增成功。");
	}

	/**
	 * 修改deposit_list
	 * 
	 * @param httpModel
	 * @return
	 */
	 @Transactional
	public void updateDepositList(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		DepositListPO depositListPO = new DepositListPO();
		depositListPO.copyProperties(inDto);
		depositListDao.updateByKey(depositListPO);
		httpModel.setOutMsg("修改成功。");
	}
	 @Transactional
	public void auditDepositList(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		String deposit_id=inDto.getString("deposit_id");
		String status=inDto.getString("status");
		DepositListPO depositListPO = new DepositListPO();
		depositListPO.setDeposit_id(deposit_id);
		depositListPO.setStatus(status);
		depositListPO.setAudit_date(AOSUtils.getDateTime());
		depositListPO.setAudit_id(httpModel.getUserModel().getId_());
		depositListPO.setRemark(inDto.getString("remark"));
        depositListDao.updateByKey(depositListPO);
        
        //退还押金且审核不通过
        DepositListPO depositListPO_ =depositListDao.selectByKey(deposit_id);
        if(null!=depositListPO_&&depositListPO_.getOper_id().equals("0")&&status.equals("-1")){
        	String user_id=depositListPO_.getUser_id();
        	MembersPO membersPO=membersDao.selectByKey(user_id);
        	//更新押金状态为支付状态
			MembersPO membersPO_=new MembersPO();
			membersPO_.setId_(membersPO.getId_());
			membersPO_.setDeposit_status("1");
			membersDao.updateByKey(membersPO_);
        }
	}
	 @Transactional
	public void auditDepositList2(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		String deposit_id=inDto.getString("deposit_id");
		String status=inDto.getString("status");
		DepositListPO depositListPO = new DepositListPO();
		depositListPO.setDeposit_id(deposit_id);
		depositListPO.setStatus(status);
		depositListPO.setAudit_date(AOSUtils.getDateTime());
		depositListPO.setAudit_id(httpModel.getUserModel().getId_());
		depositListPO.setRemark(inDto.getString("remark"));
        depositListDao.updateByKey(depositListPO);
        
        //退还余额且审核不通过
        DepositListPO depositListPO_ =depositListDao.selectByKey(deposit_id);
        if(null!=depositListPO_&&depositListPO_.getOper_id().equals("1")&&status.equals("-1")){
        	String user_id=depositListPO_.getUser_id();
        	MembersPO membersPO=membersDao.selectByKey(user_id);
        	//更新押金状态为支付状态
			MembersPO membersPO_=new MembersPO();
			membersPO_.setId_(membersPO.getId_());
			membersPO_.setGold_coins_status("1");
			membersDao.updateByKey(membersPO_);
        }
		}
	 
	/**
	 * 删除deposit_list
	 * 
	 * @param httpModel
	 */
	@Transactional
	public void deleteDepositList(HttpModel httpModel) {
		String[] selectionIds = httpModel.getInDto().getRows();
		if(null!=selectionIds&&selectionIds.length>0){
			for (String id_ : selectionIds) {
				DepositListPO depositListPO = new DepositListPO();
				depositListPO.setDeposit_id(id_);
				depositListPO.setIs_del(SystemCons.IS.YES);
	            depositListDao.updateByKey(depositListPO); 
			}
		}else{
				String deposit_id=httpModel.getInDto().getString("deposit_id");
				DepositListPO depositListPO = new DepositListPO();
				depositListPO.setDeposit_id(deposit_id);
				depositListPO.setIs_del(SystemCons.IS.YES);
	            depositListDao.updateByKey(depositListPO); 
			
		}
		httpModel.setOutMsg("删除成功。");
	}
	@Transactional
	public void depositListPay(HttpModel httpModel) {
		
				String deposit_id=httpModel.getInDto().getString("deposit_id");
				DepositListPO depositListPO =depositListDao.selectByKey(deposit_id);
				String user_id=depositListPO.getUser_id();
				Dto pDto=Dtos.newDto("is_del", "0");
				pDto.put("pay_type","2" );
				pDto.put("pay_direction","0" );
				pDto.put("oper_id","1" );
				pDto.put("status","1" );
				pDto.put("user_id",user_id );
			    OrdersPayPO	ordersPayPO=ordersPayDao.selectOne(pDto);
			    if(null!=ordersPayPO){
			    	String out_trade_no=ordersPayPO.getOut_trade_no();
			    	if("1".equals(ordersPayPO.getPay_source())){//微信退款
			    		String resultStr=wxPayRefund(out_trade_no, ordersPayPO.getPay_amt().doubleValue(), httpModel);
			    		if("success".equalsIgnoreCase(resultStr)){
			    			ordersPayPO.setOper_id("0");
			    			ordersPayDao.updateByKey(ordersPayPO);
			    			
			    			DepositListPO depositListPO_ =new DepositListPO();
			    			depositListPO_.setDeposit_id(deposit_id);
			    			depositListPO_.setStatus("2");
			    			depositListPO_.setPay_date(AOSUtils.getDateTime());
			    			depositListPO_.setPay_id(httpModel.getUserModel().getId_());
			    			depositListDao.updateByKey(depositListPO_);
			    			
			    			MembersPO membersPO_=new MembersPO();
			    			membersPO_.setId_(user_id);
			    			membersPO_.setDeposit_status("0");
			    			membersPO_.setDeposit_amt(BigDecimal.ZERO);
			    			membersDao.updateByKey(membersPO_);
			    			
			    			httpModel.setOutMsg("押金退款成功。");
			    		}else{
			    			httpModel.setOutMsg("押金退款失败。");
			    		}
			    		
			    	}else if("0".equals(ordersPayPO.getPay_source())){//支付宝退款
			    		String resultStr=aliPayRefund(out_trade_no,"" , ordersPayPO.getPay_amt().doubleValue(), httpModel);
			    		if("退款成功".equals(resultStr)){
			    			ordersPayPO.setOper_id("0");
			    			ordersPayDao.updateByKey(ordersPayPO);
			    			
			    			DepositListPO depositListPO_ =new DepositListPO();
			    			depositListPO_.setDeposit_id(deposit_id);
			    			depositListPO_.setStatus("2");
			    			depositListPO_.setPay_date(AOSUtils.getDateTime());
			    			depositListPO_.setPay_id(httpModel.getUserModel().getId_());
			    			depositListDao.updateByKey(depositListPO_);
			    			
			    			MembersPO membersPO_=new MembersPO();
			    			membersPO_.setId_(user_id);
			    			membersPO_.setDeposit_status("0");
			    			membersPO_.setDeposit_amt(BigDecimal.ZERO);
			    			membersDao.updateByKey(membersPO_);
			    			
			    			httpModel.setOutMsg("押金退款成功。");
			    		}else{
			    			httpModel.setOutMsg(resultStr);
			    		}
			    	}
			    	//httpModel.setOutMsg("押金退款失败1。");
			    }else{
			    	httpModel.setOutMsg("押金退款失败。");
			    }
			
		        
	}
	@Transactional
	public void depositListPay2(HttpModel httpModel) {
		
				String deposit_id=httpModel.getInDto().getString("deposit_id");
				DepositListPO depositListPO =depositListDao.selectByKey(deposit_id);
				String user_id=depositListPO.getUser_id();
				
				Object gold_coins_amt=sqlDao.selectOne("OrdersPay.gold_coins_amt", Dtos.newDto("user_id",user_id));
				
				
			    if(new BigDecimal(gold_coins_amt.toString()).compareTo(BigDecimal.ZERO)==1){
			    	//https://pay.weixin.qq.com/wiki/doc/api/tools/mch_pay.php?chapter=14_2
			    	/*if("1".equals(ordersPayPO.getPay_source())){//微信退款
			    		String resultStr=wxPayRefund(out_trade_no, ordersPayPO.getPay_amt().doubleValue(), httpModel);
			    		if("success".equalsIgnoreCase(resultStr)){
			    			ordersPayPO.setOper_id("0");
			    			ordersPayDao.updateByKey(ordersPayPO);
			    			
			    			DepositListPO depositListPO_ =new DepositListPO();
			    			depositListPO_.setDeposit_id(deposit_id);
			    			depositListPO_.setStatus("2");
			    			depositListPO_.setPay_date(AOSUtils.getDateTime());
			    			depositListPO_.setPay_id(httpModel.getUserModel().getId_());
			    			depositListDao.updateByKey(depositListPO_);
			    			
			    			MembersPO membersPO_=new MembersPO();
			    			membersPO_.setId_(user_id);
			    			membersPO_.setDeposit_status("1");
			    			membersPO_.setDeposit_amt(BigDecimal.ZERO);
			    			membersDao.updateByKey(membersPO_);
			    			
			    			httpModel.setOutMsg("押金退款成功。");
			    		}else{
			    			httpModel.setOutMsg("押金退款失败。");
			    		}
			    		
			    	}else if("0".equals(ordersPayPO.getPay_source())){*/ //支付宝退款
			    		String resultStr=aliPayFundTransToaccountTransfer(String.valueOf(System.currentTimeMillis()), depositListPO.getAccount(), depositListPO.getName(), new BigDecimal(gold_coins_amt.toString()).doubleValue(), httpModel);
			    		if("转帐成功".equals(resultStr)){
			    			OrdersPayPO paymentPO = new OrdersPayPO();
			    			paymentPO.setCreate_date(AOSUtils.getDateTime());
							paymentPO.setUser_id(user_id);
							paymentPO.setPay_id(AOSId.appId(SystemCons.ID.SYSTEM));
							paymentPO.setPay_amt(new BigDecimal(gold_coins_amt.toString()));
							paymentPO.setPay_direction("-1");
							paymentPO.setPay_source("2");
							paymentPO.setPay_type("3");
							paymentPO.setStatus("1");
							paymentPO.setOut_trade_no("-9999");
							paymentPO.setSuccess_date(AOSUtils.getDateTime());
							ordersPayDao.insert(paymentPO);
			    			
			    			DepositListPO depositListPO_ =new DepositListPO();
			    			depositListPO_.setDeposit_id(deposit_id);
			    			depositListPO_.setStatus("2");
			    			depositListPO_.setPay_date(AOSUtils.getDateTime());
			    			depositListPO_.setPay_id(httpModel.getUserModel().getId_());//放款人
			    			depositListDao.updateByKey(depositListPO_);
			    			
			    			MembersPO membersPO_=new MembersPO();
			    			membersPO_.setId_(user_id);
			    			membersPO_.setGold_coins_status("1");
			    			membersPO_.setGold_coins(BigDecimal.ZERO);
			    			membersDao.updateByKey(membersPO_);
			    			
			    			httpModel.setOutMsg("余额退款成功。");
			    		}else{
			    			httpModel.setOutMsg(resultStr);
			    		}
			    	//}
			    }else{
			    	httpModel.setOutMsg("退款失败,余额必须大于0。");
			    }
			
		        
	}
	/**
	 * 支付宝退款
	 * @param out_trade_no
	 * @param trade_no
	 * @param refund_amount
	 * @param httpModel
	 * @return
	 */
	private String aliPayRefund(String out_trade_no,String trade_no,double refund_amount,HttpModel httpModel){
		//out_trade_no 订单编号，trade_no 交易编号
		String result="";
		AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",AOSCxt.getParam("alipay.appid"),AOSCxt.getParam("alipay.private_key"),"json",AlipayConstants.CHARSET_UTF8,AOSCxt.getParam("alipay.public_key"),AlipayConstants.SIGN_TYPE_RSA2);
		AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
		request.setBizContent("{" +
		"\"out_trade_no\":\""+out_trade_no+"\"," +
		"\"trade_no\":\""+trade_no+"\"," +
		"\"refund_amount\":"+refund_amount+"," +
		"\"refund_reason\":\"正常退款\"" +
		"  }");
		AlipayTradeRefundResponse response;
		try {
			response = alipayClient.execute(request);
			if(response.isSuccess()){
				result="退款成功";
			}else {
				result=response.getSubMsg();
				this.saveLogs2("退款调用失败", httpModel);
			}
		} catch (AlipayApiException e) {
			this.saveLogs2("退款失败:"+e.getErrMsg(), httpModel);
			result="退款失败";
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 支付宝单笔转帐到用户
	 * @param out_trade_no
	 * @param trade_no
	 * @param refund_amount
	 * @param httpModel
	 * @return
	 */
	private String aliPayFundTransToaccountTransfer(String out_biz_no,String payee_account,String real_name,double refund_amount,HttpModel httpModel){
		//out_trade_no 订单编号，trade_no 交易编号
		String result="";
		AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",AOSCxt.getParam("alipay.appid"),AOSCxt.getParam("alipay.private_key"),"json",AlipayConstants.CHARSET_UTF8,AOSCxt.getParam("alipay.public_key"),AlipayConstants.SIGN_TYPE_RSA2);
		AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
		String remark=real_name+"的电动汽车共享充电桩APP余额退款："+refund_amount+"元";
		request.setBizContent("{" +
		"\"out_biz_no\":\""+out_biz_no+"\"," +
		"\"payee_type\":\"ALIPAY_LOGONID\"," +
		"\"payee_account\":\""+payee_account+"\"," +
		"\"amount\":"+refund_amount+"," +
		"\"payer_show_name\":\""+real_name+"退款\"," +
		"\"payee_real_name\":\""+real_name+"\"," +
		"\"remark\":\""+remark+"\"," +
		"  }");
		AlipayFundTransToaccountTransferResponse  response;
		try {
			response = alipayClient.execute(request);
			if(response.isSuccess()){
				result="转帐成功";
			}else {
				result=response.getSubMsg();
				this.saveLogs2("转帐调用失败", httpModel);
			}
		} catch (AlipayApiException e) {
			this.saveLogs2("转帐失败:"+e.getErrMsg(), httpModel);
			result="转帐失败";
			e.printStackTrace();
		}
		return result;
	}
	
	private String wxPayRefund(String trade_no,double refund_amount,HttpModel httpModel){
		String result="";
				//api地址：http://mch.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_4
		 		String currTime = TenpayUtil.getCurrTime();  
		 		//8位日期  
		 		String strTime = currTime.substring(8, currTime.length());  
				String out_refund_no = strTime+TenpayUtil.buildRandom(4) + "";// 退款单号
				String out_trade_no = trade_no;// 订单号
				 //金额转化为分为单位  
			    float sessionmoney = Float.parseFloat(String.valueOf(refund_amount));  
			    String finalmoney = String.format("%.2f", sessionmoney);  
			    finalmoney = finalmoney.replace(".", ""); 
			    finalmoney=String.valueOf(Integer.parseInt(finalmoney));
				String total_fee = finalmoney;// 总金额
				String refund_fee = finalmoney;// 退款金额
			   
		        //四位随机数  
		        String strRandom = TenpayUtil.buildRandom(4) + "";  
		        //10位序列号,可以自行调整。  
		        String strReq = strTime + strRandom;
				String nonce_str = strReq;// 随机字符串
				String appid = AOSCxt.getParam("appid"); //微信公众号apid
				String appsecret = AOSCxt.getParam("appsecret"); //微信公众号appsecret
				String mch_id = AOSCxt.getParam("partner");  //微信商户id
				String op_user_id = mch_id;//就是MCHID
				String partnerkey = AOSCxt.getParam("partnerkey");//商户平台上的那个KEY
				SortedMap<String, String> packageParams = new TreeMap<String, String>();
				packageParams.put("appid", appid);
				packageParams.put("mch_id", mch_id);
				packageParams.put("nonce_str", nonce_str);
				packageParams.put("out_trade_no", out_trade_no);
				packageParams.put("out_refund_no", out_refund_no);
				packageParams.put("total_fee",total_fee );
				packageParams.put("refund_fee",refund_fee);
				packageParams.put("op_user_id", op_user_id);

				RequestHandler2 reqHandler = new RequestHandler2(
						null, null);
				reqHandler.init(appid, appsecret, partnerkey);

				String sign = reqHandler.createSign(packageParams);
				String xml = "<xml>" + "<appid>" + appid + "</appid>" + "<mch_id>"
						+ mch_id + "</mch_id>" + "<nonce_str>" + nonce_str
						+ "</nonce_str>" + "<sign><![CDATA[" + sign + "]]></sign>"
						+ "<out_trade_no>" + out_trade_no + "</out_trade_no>"
						+ "<out_refund_no>" + out_refund_no + "</out_refund_no>"
						+ "<total_fee>" + total_fee + "</total_fee>"
						+ "<refund_fee>" + refund_fee + "</refund_fee>"
						+ "<op_user_id>" + op_user_id + "</op_user_id>" + "</xml>";
				String createOrderURL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
				try {
				    String	msgxml= ClientCustomSSL.doRefund(createOrderURL, xml);
					Map map =  new GetWxOrderno().doXMLParse(msgxml);  
				    result=(String) map.get("return_code");
					System.out.println(msgxml);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return result;
	}

}
