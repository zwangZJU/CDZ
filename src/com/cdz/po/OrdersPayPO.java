package po;

import aos.framework.core.typewrap.PO;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <b>orders_pay[orders_pay]数据持久化对象</b>
 * <p>
 * 注意:此文件由AOS平台自动生成-禁止手工修改。
 * </p>
 * 
 * @author duanchongfeng
 * @date 2017-07-18 21:56:19
 */
public class OrdersPayPO extends PO {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private String pay_id;
	
	/**
	 * 支付金额
	 */
	private BigDecimal pay_amt;
	
	/**
	 * 订单ID
	 */
	private String co_id;
	
	/**
	 * 支付类型，0：微信，1：支付宝，2:钱包
	 */
	private String pay_source;
	
	/**
	 * 消费类型，0：充值，1：支付充电，2：押金
	 */
	private String pay_type;
	
	/**
	 * 计算方向，1：增加，-1：减少
	 */
	private String pay_direction;
	
	/**
	 * 创建时间
	 */
	private Date create_date;
	
	/**
	 * 是否删除，0：未删除，1：已删除
	 */
	private String is_del;
	
	/**
	 * 操作人
	 */
	private String oper_id;
	
	/**
	 * user_id
	 */
	private String user_id;
	
	/**
	 * 状态，0：未支付，1：已支付
	 */
	private String status;
	
	/**
	 * 支付时间
	 */
	private Date success_date;
	
	private String voucher_id;
	
	public String getVoucher_id() {
		return voucher_id;
	}

	public void setVoucher_id(String voucher_id) {
		this.voucher_id = voucher_id;
	}

	private String out_trade_no;//支付订单编号
	

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	/**
	 * 主键
	 * 
	 * @return pay_id
	 */
	public String getPay_id() {
		return pay_id;
	}
	
	/**
	 * 支付金额
	 * 
	 * @return pay_amt
	 */
	public BigDecimal getPay_amt() {
		return pay_amt;
	}
	
	/**
	 * 订单ID
	 * 
	 * @return co_id
	 */
	public String getCo_id() {
		return co_id;
	}
	
	/**
	 * 支付类型，0：微信，1：支付宝，2:钱包
	 * 
	 * @return pay_source
	 */
	public String getPay_source() {
		return pay_source;
	}
	
	/**
	 * 消费类型，0：充值，1：支付充电，2：押金
	 * 
	 * @return pay_type
	 */
	public String getPay_type() {
		return pay_type;
	}
	
	/**
	 * 计算方向，1：增加，-1：减少
	 * 
	 * @return pay_direction
	 */
	public String getPay_direction() {
		return pay_direction;
	}
	
	/**
	 * 创建时间
	 * 
	 * @return create_date
	 */
	public Date getCreate_date() {
		return create_date;
	}
	
	/**
	 * 是否删除，0：未删除，1：已删除
	 * 
	 * @return is_del
	 */
	public String getIs_del() {
		return is_del;
	}
	
	/**
	 * 操作人
	 * 
	 * @return oper_id
	 */
	public String getOper_id() {
		return oper_id;
	}
	
	/**
	 * user_id
	 * 
	 * @return user_id
	 */
	public String getUser_id() {
		return user_id;
	}
	
	/**
	 * 状态，0：未支付，1：已支付
	 * 
	 * @return status
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * 支付时间
	 * 
	 * @return success_date
	 */
	public Date getSuccess_date() {
		return success_date;
	}
	

	/**
	 * 主键
	 * 
	 * @param pay_id
	 */
	public void setPay_id(String pay_id) {
		this.pay_id = pay_id;
	}
	
	/**
	 * 支付金额
	 * 
	 * @param pay_amt
	 */
	public void setPay_amt(BigDecimal pay_amt) {
		this.pay_amt = pay_amt;
	}
	
	/**
	 * 订单ID
	 * 
	 * @param co_id
	 */
	public void setCo_id(String co_id) {
		this.co_id = co_id;
	}
	
	/**
	 * 支付类型，0：微信，1：支付宝，2:钱包
	 * 
	 * @param pay_source
	 */
	public void setPay_source(String pay_source) {
		this.pay_source = pay_source;
	}
	
	/**
	 * 消费类型，0：充值，1：支付充电，2：押金
	 * 
	 * @param pay_type
	 */
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}
	
	/**
	 * 计算方向，1：增加，-1：减少
	 * 
	 * @param pay_direction
	 */
	public void setPay_direction(String pay_direction) {
		this.pay_direction = pay_direction;
	}
	
	/**
	 * 创建时间
	 * 
	 * @param create_date
	 */
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	
	/**
	 * 是否删除，0：未删除，1：已删除
	 * 
	 * @param is_del
	 */
	public void setIs_del(String is_del) {
		this.is_del = is_del;
	}
	
	/**
	 * 操作人
	 * 
	 * @param oper_id
	 */
	public void setOper_id(String oper_id) {
		this.oper_id = oper_id;
	}
	
	/**
	 * user_id
	 * 
	 * @param user_id
	 */
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
	/**
	 * 状态，0：未支付，1：已支付
	 * 
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * 支付时间
	 * 
	 * @param success_date
	 */
	public void setSuccess_date(Date success_date) {
		this.success_date = success_date;
	}
	

}