package po;

import aos.framework.core.typewrap.PO;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <b>deposit_list[deposit_list]数据持久化对象</b>
 * <p>
 * 注意:此文件由AOS平台自动生成-禁止手工修改。
 * </p>
 * 
 * @author duanchongfeng
 * @date 2017-07-06 20:19:04
 */
public class DepositListPO extends PO {

	private static final long serialVersionUID = 1L;

	/**
	 * 主建
	 */
	private String deposit_id;
	
	/**
	 * 用户ID
	 */
	private String user_id;
	
	/**
	 * 押金金额
	 */
	private BigDecimal amt;
	
	/**
	 * 0:提交申请，1：审核通过，-1：审核不通过，2：退款成功
	 */
	private String status;
	
	private String account;
	
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String name;
	
	/**
	 * 审核时间
	 */
	private Date audit_date;
	
	/**
	 * 审核人
	 */
	private String audit_id;
	
	/**
	 * 放款时间
	 */
	private Date pay_date;
	
	/**
	 * 放款人
	 */
	private String pay_id;
	
	/**
	 * 备注
	 */
	private String remark;
	
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
	 * 主建
	 * 
	 * @return deposit_id
	 */
	public String getDeposit_id() {
		return deposit_id;
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
	 * 押金金额
	 * 
	 * @return amt
	 */
	public BigDecimal getAmt() {
		return amt;
	}
	
	/**
	 * 0:提交申请，1：审核通过，-1：审核不通过，2：退款成功
	 * 
	 * @return status
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * 审核时间
	 * 
	 * @return audit_date
	 */
	public Date getAudit_date() {
		return audit_date;
	}
	
	/**
	 * 审核人
	 * 
	 * @return audit_id
	 */
	public String getAudit_id() {
		return audit_id;
	}
	
	/**
	 * 放款时间
	 * 
	 * @return pay_date
	 */
	public Date getPay_date() {
		return pay_date;
	}
	
	/**
	 * 放款人
	 * 
	 * @return pay_id
	 */
	public String getPay_id() {
		return pay_id;
	}
	
	/**
	 * 备注
	 * 
	 * @return remark
	 */
	public String getRemark() {
		return remark;
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
	 * 主建
	 * 
	 * @param deposit_id
	 */
	public void setDeposit_id(String deposit_id) {
		this.deposit_id = deposit_id;
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
	 * 押金金额
	 * 
	 * @param amt
	 */
	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}
	
	/**
	 * 0:提交申请，1：审核通过，-1：审核不通过，2：退款成功
	 * 
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * 审核时间
	 * 
	 * @param audit_date
	 */
	public void setAudit_date(Date audit_date) {
		this.audit_date = audit_date;
	}
	
	/**
	 * 审核人
	 * 
	 * @param audit_id
	 */
	public void setAudit_id(String audit_id) {
		this.audit_id = audit_id;
	}
	
	/**
	 * 放款时间
	 * 
	 * @param pay_date
	 */
	public void setPay_date(Date pay_date) {
		this.pay_date = pay_date;
	}
	
	/**
	 * 放款人
	 * 
	 * @param pay_id
	 */
	public void setPay_id(String pay_id) {
		this.pay_id = pay_id;
	}
	
	/**
	 * 备注
	 * 
	 * @param remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
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
	

}