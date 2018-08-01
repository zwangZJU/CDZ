package po;

import aos.framework.core.typewrap.PO;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <b>charging_orders[charging_orders]数据持久化对象</b>
 * <p>
 * 注意:此文件由AOS平台自动生成-禁止手工修改。
 * </p>
 * 
 * @author duanchongfeng
 * @date 2017-07-06 20:19:04
 */
public class ChargingOrdersPO extends PO {

	private static final long serialVersionUID = 1L;

	/**
	 * co_id
	 */
	private String co_id;
	
	/**
	 * 用户ID
	 */
	private String user_id;
	
	/**
	 * 充电桩ID
	 */
	private String cp_id;
	
	/**
	 * 已充金额
	 */
	private BigDecimal amounted;
	
	/**
	 * 已充时间
	 */
	private BigDecimal dateed;
	
	/**
	 * 已充电量
	 */
	private BigDecimal electricity;
	
	/**
	 * 剩余时间
	 */
	private BigDecimal rest_date;
	
	/**
	 * 总金额
	 */
	private BigDecimal total_amt;
	
	/**
	 * 抵扣金额
	 */
	private BigDecimal deduction_amt;
	
	/**
	 * 实付金额
	 */
	private BigDecimal real_amt;
	
	/**
	 * 状态，0：新增，1：支付成功，2：支付失败，-1：取消
	 */
	private String status_;
	
	/**
	 * 计算类型，0：金额，1：时间，2：度数，3：充满
	 */
	private String co_type;
	
	/**
	 * 数量
	 */
	private BigDecimal co_num;
	
	private BigDecimal discount;
	
	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	/**
	 * 抵扣类型，0：折扣，1：优惠券
	 */
	private String deduction_type;
	
	/**
	 * 是否删除，0：未删除，1：删除
	 */
	private String is_del;
	/**
	 * 是否插枪
	 */
	private String put_gun;
	/**
	 * 不同意原因
	 */
	private String reason;
	
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getPut_gun() {
		return put_gun;
	}

	public void setPut_gun(String put_gun) {
		this.put_gun = put_gun;
	}

	/**
	 * 创建时间
	 */
	private Date create_date;
	
	private Date puted_gun_date;
	
	private Date put_gun_date;
	
	private Date complete_date;
	
	private Date update_date;
	
	public Date getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}

	public Date getPuted_gun_date() {
		return puted_gun_date;
	}

	public void setPuted_gun_date(Date puted_gun_date) {
		this.puted_gun_date = puted_gun_date;
	}

	public Date getPut_gun_date() {
		return put_gun_date;
	}

	public void setPut_gun_date(Date put_gun_date) {
		this.put_gun_date = put_gun_date;
	}

	public Date getComplete_date() {
		return complete_date;
	}

	public void setComplete_date(Date complete_date) {
		this.complete_date = complete_date;
	}

	/**
	 * 操作人ID
	 */
	private String oper_id;
	
	/**
	 * 电量度数（需换算）
	 */
	private BigDecimal degree;
	

	public BigDecimal getDegree() {
		return degree;
	}

	public void setDegree(BigDecimal degree) {
		this.degree = degree;
	}

	/**
	 * co_id
	 * 
	 * @return co_id
	 */
	public String getCo_id() {
		return co_id;
	}
	
	/**
	 * 用户ID
	 * 
	 * @return user_id
	 */
	public String getUser_id() {
		return user_id;
	}
	
	/**
	 * 充电桩ID
	 * 
	 * @return cp_id
	 */
	public String getCp_id() {
		return cp_id;
	}
	
	/**
	 * 已充金额
	 * 
	 * @return amounted
	 */
	public BigDecimal getAmounted() {
		return amounted;
	}
	
	/**
	 * 已充时间
	 * 
	 * @return dateed
	 */
	public BigDecimal getDateed() {
		return dateed;
	}
	
	/**
	 * 已充电量
	 * 
	 * @return electricity
	 */
	public BigDecimal getElectricity() {
		return electricity;
	}
	
	/**
	 * 剩余时间
	 * 
	 * @return rest_date
	 */
	public BigDecimal getRest_date() {
		return rest_date;
	}
	
	/**
	 * 总金额
	 * 
	 * @return total_amt
	 */
	public BigDecimal getTotal_amt() {
		return total_amt;
	}
	
	/**
	 * 抵扣金额
	 * 
	 * @return deduction_amt
	 */
	public BigDecimal getDeduction_amt() {
		return deduction_amt;
	}
	
	/**
	 * 实付金额
	 * 
	 * @return real_amt
	 */
	public BigDecimal getReal_amt() {
		return real_amt;
	}
	
	/**
	 * 状态，0：新增，1：支付成功，2：支付失败，-1：取消
	 * 
	 * @return status_
	 */
	public String getStatus_() {
		return status_;
	}
	
	/**
	 * 计算类型，0：金额，1：时间，2：度数，3：充满
	 * 
	 * @return co_type
	 */
	public String getCo_type() {
		return co_type;
	}
	
	/**
	 * 数量
	 * 
	 * @return co_num
	 */
	public BigDecimal getCo_num() {
		return co_num;
	}
	
	/**
	 * 抵扣类型，0：折扣，1：优惠券
	 * 
	 * @return deduction_type
	 */
	public String getDeduction_type() {
		return deduction_type;
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
	 * co_id
	 * 
	 * @param co_id
	 */
	public void setCo_id(String co_id) {
		this.co_id = co_id;
	}
	
	/**
	 * 用户ID
	 * 
	 * @param user_id
	 */
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
	/**
	 * 充电桩ID
	 * 
	 * @param cp_id
	 */
	public void setCp_id(String cp_id) {
		this.cp_id = cp_id;
	}
	
	/**
	 * 已充金额
	 * 
	 * @param amounted
	 */
	public void setAmounted(BigDecimal amounted) {
		this.amounted = amounted;
	}
	
	/**
	 * 已充时间
	 * 
	 * @param dateed
	 */
	public void setDateed(BigDecimal dateed) {
		this.dateed = dateed;
	}
	
	/**
	 * 已充电量
	 * 
	 * @param electricity
	 */
	public void setElectricity(BigDecimal electricity) {
		this.electricity = electricity;
	}
	
	/**
	 * 剩余时间
	 * 
	 * @param rest_date
	 */
	public void setRest_date(BigDecimal rest_date) {
		this.rest_date = rest_date;
	}
	
	/**
	 * 总金额
	 * 
	 * @param total_amt
	 */
	public void setTotal_amt(BigDecimal total_amt) {
		this.total_amt = total_amt;
	}
	
	/**
	 * 抵扣金额
	 * 
	 * @param deduction_amt
	 */
	public void setDeduction_amt(BigDecimal deduction_amt) {
		this.deduction_amt = deduction_amt;
	}
	
	/**
	 * 实付金额
	 * 
	 * @param real_amt
	 */
	public void setReal_amt(BigDecimal real_amt) {
		this.real_amt = real_amt;
	}
	
	/**
	 * 状态，0：新增，1：支付成功，2：支付失败，-1：取消
	 * 
	 * @param status_
	 */
	public void setStatus_(String status_) {
		this.status_ = status_;
	}
	
	/**
	 * 计算类型，0：金额，1：时间，2：度数，3：充满
	 * 
	 * @param co_type
	 */
	public void setCo_type(String co_type) {
		this.co_type = co_type;
	}
	
	/**
	 * 数量
	 * 
	 * @param co_num
	 */
	public void setCo_num(BigDecimal co_num) {
		this.co_num = co_num;
	}
	
	/**
	 * 抵扣类型，0：折扣，1：优惠券
	 * 
	 * @param deduction_type
	 */
	public void setDeduction_type(String deduction_type) {
		this.deduction_type = deduction_type;
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
	

}