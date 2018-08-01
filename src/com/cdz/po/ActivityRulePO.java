package po;

import java.math.BigDecimal;

import aos.framework.core.typewrap.PO;

/**
 * <b>活动规则表[activity_rule]数据持久化对象</b>
 * <p>
 * 注意:此文件由AOS平台自动生成-禁止手工修改。
 * </p>
 * 
 * @author duanchongfeng
 * @date 2017-04-21 20:57:29
 */
public class ActivityRulePO extends PO {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private String ar_id;
	
	/**
	 * 活动ID
	 */
	private String activity_id;
	
	/**
	 * 规则名称
	 */
	private String name_;
	
	/**
	 * 符号
	 */
	private String symbol;
	
	/**
	 * 参数
	 */
	private BigDecimal param;
	
	/**
	 * 表达式
	 */
	private String expression;
	
	/**
	 * 描述
	 */
	private String desc_;
	
	/**
	 * 奖励金额
	 */
	private BigDecimal award_amt;
	
	/**
	 * 使用条件值
	 */
	private BigDecimal cond_value;
	/**
	 * 优惠券数量
	 */
	private BigDecimal ar_num;
	public BigDecimal getAr_num() {
		return ar_num;
	}

	public void setAr_num(BigDecimal ar_num) {
		this.ar_num = ar_num;
	}

	public BigDecimal getCond_value() {
		return cond_value;
	}

	public void setCond_value(BigDecimal cond_value) {
		this.cond_value = cond_value;
	}

	public BigDecimal getAward_amt() {
		return award_amt;
	}

	public void setAward_amt(BigDecimal award_amt) {
		this.award_amt = award_amt;
	}

	/**
	 * 主键
	 * 
	 * @return ar_id
	 */
	public String getAr_id() {
		return ar_id;
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
	 * 规则名称
	 * 
	 * @return name_
	 */
	public String getName_() {
		return name_;
	}
	
	/**
	 * 符号
	 * 
	 * @return symbol
	 */
	public String getSymbol() {
		return symbol;
	}
	
	/**
	 * 参数
	 * 
	 * @return param
	 */
	public BigDecimal getParam() {
		return param;
	}
	
	/**
	 * 表达式
	 * 
	 * @return expression
	 */
	public String getExpression() {
		return expression;
	}
	
	/**
	 * 描述
	 * 
	 * @return desc_
	 */
	public String getDesc_() {
		return desc_;
	}
	

	/**
	 * 主键
	 * 
	 * @param ar_id
	 */
	public void setAr_id(String ar_id) {
		this.ar_id = ar_id;
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
	 * 规则名称
	 * 
	 * @param name_
	 */
	public void setName_(String name_) {
		this.name_ = name_;
	}
	
	/**
	 * 符号
	 * 
	 * @param symbol
	 */
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	/**
	 * 参数
	 * 
	 * @param param
	 */
	public void setParam(BigDecimal param) {
		this.param = param;
	}
	
	/**
	 * 表达式
	 * 
	 * @param expression
	 */
	public void setExpression(String expression) {
		this.expression = expression;
	}
	
	/**
	 * 描述
	 * 
	 * @param desc_
	 */
	public void setDesc_(String desc_) {
		this.desc_ = desc_;
	}
	

}