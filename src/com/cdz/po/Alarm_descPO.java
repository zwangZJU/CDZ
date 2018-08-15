package po;

import aos.framework.core.typewrap.PO;

/**
 * <b>alarm_desc[alarm_desc]数据持久化对象</b>
 * <p>
 * 注意:此文件由AOS平台自动生成-禁止手工修改。
 * </p>
 * 
 * @author koney
 * @date 2018-08-10 16:39:36
 */
public class Alarm_descPO extends PO {

	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	private String id_;
	
	/**
	 * 报警代码
	 */
	private String eee;
	
	/**
	 * 报警类型
	 */
	private String alarm_type;
	

	/**
	 * id
	 * 
	 * @return id_
	 */
	public String getId_() {
		return id_;
	}
	
	/**
	 * 报警代码
	 * 
	 * @return eee
	 */
	public String getEee() {
		return eee;
	}
	
	/**
	 * 报警类型
	 * 
	 * @return alarm_type
	 */
	public String getAlarm_type() {
		return alarm_type;
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
	 * 报警代码
	 * 
	 * @param eee
	 */
	public void setEee(String eee) {
		this.eee = eee;
	}
	
	/**
	 * 报警类型
	 * 
	 * @param alarm_type
	 */
	public void setAlarm_type(String alarm_type) {
		this.alarm_type = alarm_type;
	}
	

}