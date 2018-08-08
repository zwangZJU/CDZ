package po;

import java.util.Date;

import aos.framework.core.typewrap.PO;

/**
 * <b>repair_log[repair_log]数据持久化对象</b>
 * <p>
 * 注意:此文件由AOS平台自动生成-禁止手工修改。
 * </p>
 * 
 * @author Administrator
 * @date 2018-08-07 12:33:47
 */
public class Repair_logPO extends PO {

	private static final long serialVersionUID = 1L;

	/**
	 * 报修序号
	 */
	private String repair_id;
	
	/**
	 * 设备id
	 */
	private String device_id;
	
	/**
	 * 用户手机号
	 */
	private String user_phone;
	
	/**
	 * 报修内容
	 */
	private String repair_content;
	
	/**
	 * 报修时间
	 */
	private Date repair_time;
	
	/**
	 * 修复时间
	 */
	private Date renovate_time;
	
	/**
	 * 处理状态
	 */
	private String processing_state;
	
	/**
	 * 状态信息
	 */
	private String state_info;
	
	/**
	 * 处理者
	 */
	private String handler_;
	
	/**
	 * 处理者电话
	 */
	private String handler_phone;
	
	/**
	 * 故障原因
	 */
	private String error_reason;
	
	/**
	 * 修理完成
	 */
	private String is_completed;
	
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
	private String baiyong3_;
	

	/**
	 * 报修序号
	 * 
	 * @return repair_id
	 */
	public String getRepair_id() {
		return repair_id;
	}
	
	/**
	 * 设备id
	 * 
	 * @return device_id
	 */
	public String getDevice_id() {
		return device_id;
	}
	
	/**
	 * 用户手机号
	 * 
	 * @return user_phone
	 */
	public String getUser_phone() {
		return user_phone;
	}
	
	/**
	 * 报修内容
	 * 
	 * @return repair_content
	 */
	public String getRepair_content() {
		return repair_content;
	}
	
	/**
	 * 报修时间
	 * 
	 * @return repair_time
	 */
	public Date getRepair_time() {
		return repair_time;
	}
	
	/**
	 * 修复时间
	 * 
	 * @return renovate_time
	 */
	public Date getRenovate_time() {
		return renovate_time;
	}
	
	/**
	 * 处理状态
	 * 
	 * @return processing_state
	 */
	public String getProcessing_state() {
		return processing_state;
	}
	
	/**
	 * 状态信息
	 * 
	 * @return state_info
	 */
	public String getState_info() {
		return state_info;
	}
	
	/**
	 * 处理者
	 * 
	 * @return handler_
	 */
	public String getHandler_() {
		return handler_;
	}
	
	/**
	 * 处理者电话
	 * 
	 * @return handler_phone
	 */
	public String getHandler_phone() {
		return handler_phone;
	}
	
	/**
	 * 故障原因
	 * 
	 * @return error_reason
	 */
	public String getError_reason() {
		return error_reason;
	}
	
	/**
	 * 修理完成
	 * 
	 * @return is_completed
	 */
	public String getIs_completed() {
		return is_completed;
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
	 * @return baiyong3_
	 */
	public String getBaiyong3_() {
		return baiyong3_;
	}
	

	/**
	 * 报修序号
	 * 
	 * @param repair_id
	 */
	public void setRepair_id(String repair_id) {
		this.repair_id = repair_id;
	}
	
	/**
	 * 设备id
	 * 
	 * @param device_id
	 */
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
	
	/**
	 * 用户手机号
	 * 
	 * @param user_phone
	 */
	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}
	
	/**
	 * 报修内容
	 * 
	 * @param repair_content
	 */
	public void setRepair_content(String repair_content) {
		this.repair_content = repair_content;
	}
	
	/**
	 * 报修时间
	 * 
	 * @param repair_time
	 */
	public void setRepair_time(Date repair_time) {
		this.repair_time = repair_time;
	}
	
	/**
	 * 修复时间
	 * 
	 * @param renovate_time
	 */
	public void setRenovate_time(Date renovate_time) {
		this.renovate_time = renovate_time;
	}
	
	/**
	 * 处理状态
	 * 
	 * @param processing_state
	 */
	public void setProcessing_state(String processing_state) {
		this.processing_state = processing_state;
	}
	
	/**
	 * 状态信息
	 * 
	 * @param state_info
	 */
	public void setState_info(String state_info) {
		this.state_info = state_info;
	}
	
	/**
	 * 处理者
	 * 
	 * @param handler_
	 */
	public void setHandler_(String handler_) {
		this.handler_ = handler_;
	}
	
	/**
	 * 处理者电话
	 * 
	 * @param handler_phone
	 */
	public void setHandler_phone(String handler_phone) {
		this.handler_phone = handler_phone;
	}
	
	/**
	 * 故障原因
	 * 
	 * @param error_reason
	 */
	public void setError_reason(String error_reason) {
		this.error_reason = error_reason;
	}
	
	/**
	 * 修理完成
	 * 
	 * @param is_completed
	 */
	public void setIs_completed(String is_completed) {
		this.is_completed = is_completed;
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
	 * @param baiyong3_
	 */
	public void setBaiyong3_(String baiyong3_) {
		this.baiyong3_ = baiyong3_;
	}
	

}