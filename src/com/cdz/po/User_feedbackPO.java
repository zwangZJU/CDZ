package po;

import aos.framework.core.typewrap.PO;
import java.util.Date;

/**
 * <b>user_feedback[user_feedback]数据持久化对象</b>
 * <p>
 * 注意:此文件由AOS平台自动生成-禁止手工修改。
 * </p>
 * 
 * @author koney
 * @date 2018-09-05 10:45:12
 */
public class User_feedbackPO extends PO {

	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	private String id_;
	
	/**
	 * 账号（手机号）
	 */
	private String account_;
	
	/**
	 * 反馈建议
	 */
	private String advice_;
	
	/**
	 * 时间
	 */
	private Date time_;
	
	/**
	 * 备用1
	 */
	private String beiyong1_;
	
	/**
	 * 备用2
	 */
	private String beiyong2_;
	
	/**
	 * 备用3
	 */
	private String beiyong3_;
	

	/**
	 * id
	 * 
	 * @return id_
	 */
	public String getId_() {
		return id_;
	}
	
	/**
	 * 账号（手机号）
	 * 
	 * @return account_
	 */
	public String getAccount_() {
		return account_;
	}
	
	/**
	 * 反馈建议
	 * 
	 * @return advice_
	 */
	public String getAdvice_() {
		return advice_;
	}
	
	/**
	 * 时间
	 * 
	 * @return time_
	 */
	public Date getTime_() {
		return time_;
	}
	
	/**
	 * 备用1
	 * 
	 * @return beiyong1_
	 */
	public String getBeiyong1_() {
		return beiyong1_;
	}
	
	/**
	 * 备用2
	 * 
	 * @return beiyong2_
	 */
	public String getBeiyong2_() {
		return beiyong2_;
	}
	
	/**
	 * 备用3
	 * 
	 * @return beiyong3_
	 */
	public String getBeiyong3_() {
		return beiyong3_;
	}
	

	/**
	 * id
	 * 
	 * @param id_
	 */
	public void setId_(String id_) {
		this.id_ = id_;
	}
	
	/**
	 * 账号（手机号）
	 * 
	 * @param account_
	 */
	public void setAccount_(String account_) {
		this.account_ = account_;
	}
	
	/**
	 * 反馈建议
	 * 
	 * @param advice_
	 */
	public void setAdvice_(String advice_) {
		this.advice_ = advice_;
	}
	
	/**
	 * 时间
	 * 
	 * @param time_
	 */
	public void setTime_(Date time_) {
		this.time_ = time_;
	}
	
	/**
	 * 备用1
	 * 
	 * @param beiyong1_
	 */
	public void setBeiyong1_(String beiyong1_) {
		this.beiyong1_ = beiyong1_;
	}
	
	/**
	 * 备用2
	 * 
	 * @param beiyong2_
	 */
	public void setBeiyong2_(String beiyong2_) {
		this.beiyong2_ = beiyong2_;
	}
	
	/**
	 * 备用3
	 * 
	 * @param beiyong3_
	 */
	public void setBeiyong3_(String beiyong3_) {
		this.beiyong3_ = beiyong3_;
	}
	

}