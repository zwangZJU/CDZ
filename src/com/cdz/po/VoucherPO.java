package po;

import aos.framework.core.typewrap.PO;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <b>代金券表[voucher]数据持久化对象</b>
 * <p>
 * 注意:此文件由AOS平台自动生成-禁止手工修改。
 * </p>
 * 
 * @author duanchongfeng
 * @date 2017-04-19 20:27:11
 */
public class VoucherPO extends PO {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private String voucher_id;
	
	/**
	 * 券金额
	 */
	private BigDecimal voucher_amt;
	
	/**
	 * 活动ID
	 */
	private String activity_id;
	
	/**
	 * 经销商ID
	 */
	private String dealer_id;
	
	/**
	 * 状态，0：新增，-1：过期，1：使用
	 */
	private String direction;
	
	/**
	 * 订单ID
	 */
	private String order_id;
	
	/**
	 * 是否删除，0：未删除，1：删除
	 */
	private String is_del;
	
	/**
	 * 创建时间
	 */
	private Date create_date;
	
	/**
	 * 操作人ID
	 */
	private String oper_id;
	
	/**
	 * 有效日期
	 */
	private Date effec_date;
	/**
	 * 使用时间
	 */
	private Date use_date;
	
	
	
	public Date getUse_date() {
		return use_date;
	}

	public void setUse_date(Date use_date) {
		this.use_date = use_date;
	}

	/**
	 * 使用条件值
	 */
	private BigDecimal cond_value;
	

	/**
	 * 主键
	 * 
	 * @return voucher_id
	 */
	public String getVoucher_id() {
		return voucher_id;
	}
	
	/**
	 * 券金额
	 * 
	 * @return voucher_amt
	 */
	public BigDecimal getVoucher_amt() {
		return voucher_amt;
	}
	
	/**
	 * 活动ID
	 * 
	 * @return activity_id
	 */
	public String getActivity_id() {
		return activity_id;
	}
	
	/**
	 * 经销商ID
	 * 
	 * @return dealer_id
	 */
	public String getDealer_id() {
		return dealer_id;
	}
	
	/**
	 * 状态，0：新增，-1：过期，1：使用
	 * 
	 * @return direction
	 */
	public String getDirection() {
		return direction;
	}
	
	/**
	 * 订单ID
	 * 
	 * @return order_id
	 */
	public String getOrder_id() {
		return order_id;
	}
	
	/**
	 * 是否删除，0：未删除，1：删除
	 * 
	 * @return is_del
	 */
	public String getIs_del() {
		return is_del;
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
	 * 操作人ID
	 * 
	 * @return oper_id
	 */
	public String getOper_id() {
		return oper_id;
	}
	
	/**
	 * 有效日期
	 * 
	 * @return effec_date
	 */
	public Date getEffec_date() {
		return effec_date;
	}
	
	/**
	 * 使用条件值
	 * 
	 * @return cond_value
	 */
	public BigDecimal getCond_value() {
		return cond_value;
	}
	

	/**
	 * 主键
	 * 
	 * @param voucher_id
	 */
	public void setVoucher_id(String voucher_id) {
		this.voucher_id = voucher_id;
	}
	
	/**
	 * 券金额
	 * 
	 * @param voucher_amt
	 */
	public void setVoucher_amt(BigDecimal voucher_amt) {
		this.voucher_amt = voucher_amt;
	}
	
	/**
	 * 活动ID
	 * 
	 * @param activity_id
	 */
	public void setActivity_id(String activity_id) {
		this.activity_id = activity_id;
	}
	
	/**
	 * 经销商ID
	 * 
	 * @param dealer_id
	 */
	public void setDealer_id(String dealer_id) {
		this.dealer_id = dealer_id;
	}
	
	/**
	 * 计算方向，1：发放金券，-1：抵扣消费
	 * 
	 * @param direction
	 */
	public void setDirection(String direction) {
		this.direction = direction;
	}
	
	/**
	 * 订单ID
	 * 
	 * @param order_id
	 */
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	
	/**
	 * 是否删除，0：未删除，1：删除
	 * 
	 * @param is_del
	 */
	public void setIs_del(String is_del) {
		this.is_del = is_del;
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
	 * 操作人ID
	 * 
	 * @param oper_id
	 */
	public void setOper_id(String oper_id) {
		this.oper_id = oper_id;
	}
	
	/**
	 * 有效日期
	 * 
	 * @param effec_date
	 */
	public void setEffec_date(Date effec_date) {
		this.effec_date = effec_date;
	}
	
	/**
	 * 使用条件值
	 * 
	 * @param cond_value
	 */
	public void setCond_value(BigDecimal cond_value) {
		this.cond_value = cond_value;
	}
	

}