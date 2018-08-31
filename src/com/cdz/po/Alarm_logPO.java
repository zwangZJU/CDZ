package po;

import java.util.Date;

import aos.framework.core.typewrap.PO;

/**
 * <b>alarm_log[alarm_log]数据持久化对象</b>
 * <p>
 * 注意:此文件由AOS平台自动生成-禁止手工修改。
 * </p>
 * 
 * @author Administrator
 * @date 2018-08-06 22:34:53
 */
public class Alarm_logPO extends PO {

	private static final long serialVersionUID = 1L;

	/**
	 * 报警序号
	 */
	private String alarm_id;
	
	/**
	 * 设备id
	 */
	private String device_id;
	
	/**
	 * 用户手机号
	 */
	private String user_phone;
	
	/**
	 * 报警时间
	 */
	private Date alarm_time;
	
	/**
	 * 出警时间
	 */
	private Date response_time;
	
	/**
	 * 报警方式
	 */
	private String type_;
	
	/**
	 * 处理者
	 */
	private String handler_;
	
	/**
	 * 处理者电话
	 */
	private String handler_phone;
	
	/**
	 * 报警原因
	 */
	private String reason_;
	
	/**
	 * 报警解除
	 */
	private String alarm_release;
	
	/**
	 * 取消报警
	 */
	private String is_cancel;
	
	/**
	 * 备用1
	 */
	private String alert_code;
	
	/**
	 * 备用2
	 */
	private String process;
	
	/**
	 * 备用3
	 */
	private String defence_area;
	

	/**
	 * 报警序号
	 * 
	 * @return alarm_id
	 */
	public String getAlarm_id() {
		return alarm_id;
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
	 * 报警时间
	 * 
	 * @return alarm_time
	 */
	public Date getAlarm_time() {
		return alarm_time;
	}
	
	/**
	 * 出警时间
	 * 
	 * @return response_time
	 */
	public Date getResponse_time() {
		return response_time;
	}
	
	/**
	 * 报警方式
	 * 
	 * @return type_
	 */
	public String getType_() {
		return type_;
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
	 * 报警原因
	 * 
	 * @return reason_
	 */
	public String getReason_() {
		return reason_;
	}
	
	/**
	 * 报警解除
	 * 
	 * @return alarm_release
	 */
	public String getAlarm_release() {
		return alarm_release;
	}
	
	/**
	 * 取消报警
	 * 
	 * @return is_cancel
	 */
	public String getIs_cancel() {
		return is_cancel;
	}
	
	/**
	 * 备用1
	 * 
	 * @return alert_code
	 */
	public String getAlert_code() {
		return alert_code;
	}
	
	/**
	 * 备用2
	 * 
	 * @return process
	 */
	public String getProcess() {
		return process;
	}
	
	/**
	 * 备用3
	 * 
	 * @return defence_area
	 */
	public String getDefence_area() {
		return defence_area;
	}
	

	/**
	 * 报警序号
	 * 
	 * @param alarm_id
	 */
	public void setAlarm_id(String alarm_id) {
		this.alarm_id = alarm_id;
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
	 * 报警时间
	 * 
	 * @param alarm_time
	 */
	public void setAlarm_time(Date alarm_time) {
		this.alarm_time = alarm_time;
	}
	
	/**
	 * 出警时间
	 * 
	 * @param response_time
	 */
	public void setResponse_time(Date response_time) {
		this.response_time = response_time;
	}
	
	/**
	 * 报警方式
	 * 
	 * @param type_
	 */
	public void setType_(String type_) {
		this.type_ = type_;
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
	 * 报警原因
	 * 
	 * @param reason_
	 */
	public void setReason_(String reason_) {
		this.reason_ = reason_;
	}
	
	/**
	 * 报警解除
	 * 
	 * @param alarm_release
	 */
	public void setAlarm_release(String alarm_release) {
		this.alarm_release = alarm_release;
	}
	
	/**
	 * 取消报警
	 * 
	 * @param is_cancel
	 */
	public void setIs_cancel(String is_cancel) {
		this.is_cancel = is_cancel;
	}
	
	/**
	 * 备用1
	 * 
	 * @param alert_code
	 */
	public void setAlert_code(String alert_code) {
		this.alert_code = alert_code;
	}
	
	/**
	 * 备用2
	 * 
	 * @param process
	 */
	public void setProcess(String process) {
		this.process = process;
	}
	
	/**
	 * 备用3
	 * 
	 * @param defence_area
	 */
	public void setDefence_area(String defence_area) {
		this.defence_area = defence_area;
	}
	

}