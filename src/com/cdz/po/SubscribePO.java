package po;

import aos.framework.core.typewrap.PO;
import java.util.Date;

/**
 * <b>subscribe[subscribe]数据持久化对象</b>
 * <p>
 * 注意:此文件由AOS平台自动生成-禁止手工修改。
 * </p>
 * 
 * @author duanchongfeng
 * @date 2017-07-06 20:19:05
 */
public class SubscribePO extends PO {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private String s_id;
	
	/**
	 * 用户ID
	 */
	private String user_id;
	
	/**
	 * 充电桩ID
	 */
	private String cp_id;
	
	/**
	 * 开始时间
	 */
	private Date start_date;
	
	/**
	 * 结束时间
	 */
	private Date end_date;
	
	/**
	 * 完成时间
	 */
	private Date complete_date;
	
	/**
	 * 状态，0：预约中，1：已充电，-1：失效,2:取消
	 */
	private String status;
	
	/**
	 * 创建时间
	 */
	private Date create_date;
	
	/**
	 * 是否删除，0：未删除，1：删除
	 */
	private String is_del;
	

	/**
	 * 主键
	 * 
	 * @return s_id
	 */
	public String getS_id() {
		return s_id;
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
	 * 开始时间
	 * 
	 * @return start_date
	 */
	public Date getStart_date() {
		return start_date;
	}
	
	/**
	 * 结束时间
	 * 
	 * @return end_date
	 */
	public Date getEnd_date() {
		return end_date;
	}
	
	/**
	 * 完成时间
	 * 
	 * @return complete_date
	 */
	public Date getComplete_date() {
		return complete_date;
	}
	
	/**
	 * 状态，0：预约中，1：已充电，-1：失效,2:取消
	 * 
	 * @return status
	 */
	public String getStatus() {
		return status;
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
	 * 是否删除，0：未删除，1：删除
	 * 
	 * @return is_del
	 */
	public String getIs_del() {
		return is_del;
	}
	

	/**
	 * 主键
	 * 
	 * @param s_id
	 */
	public void setS_id(String s_id) {
		this.s_id = s_id;
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
	 * 开始时间
	 * 
	 * @param start_date
	 */
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
	
	/**
	 * 结束时间
	 * 
	 * @param end_date
	 */
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}
	
	/**
	 * 完成时间
	 * 
	 * @param complete_date
	 */
	public void setComplete_date(Date complete_date) {
		this.complete_date = complete_date;
	}
	
	/**
	 * 状态，0：预约中，1：已充电，-1：失效,2:取消
	 * 
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
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
	 * 是否删除，0：未删除，1：删除
	 * 
	 * @param is_del
	 */
	public void setIs_del(String is_del) {
		this.is_del = is_del;
	}
	

}