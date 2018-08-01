package po;

import aos.framework.core.typewrap.PO;
import java.util.Date;

/**
 * <b>common_logs[common_logs]数据持久化对象</b>
 * <p>
 * 注意:此文件由AOS平台自动生成-禁止手工修改。
 * </p>
 * 
 * @author duanchongfeng
 * @date 2017-07-06 22:16:46
 */
public class CommonLogsPO extends PO {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private String log_id;
	
	/**
	 * 内容
	 */
	private String content;
	
	/**
	 * 创建时间
	 */
	private Date create_date;
	
	/**
	 * 是否删除，0：未删除，1：删除
	 */
	private String is_del;
	
	/**
	 * 操作人ID
	 */
	private String oper_id;
	
	/**
	 * 操作人名称
	 */
	private String oper_name;
	
	private String log_type;

	public String getLog_type() {
		return log_type;
	}

	public void setLog_type(String log_type) {
		this.log_type = log_type;
	}

	/**
	 * 主键
	 * 
	 * @return log_id
	 */
	public String getLog_id() {
		return log_id;
	}
	
	/**
	 * 内容
	 * 
	 * @return content
	 */
	public String getContent() {
		return content;
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
	 * 操作人ID
	 * 
	 * @return oper_id
	 */
	public String getOper_id() {
		return oper_id;
	}
	
	/**
	 * 操作人名称
	 * 
	 * @return oper_name
	 */
	public String getOper_name() {
		return oper_name;
	}
	

	/**
	 * 主键
	 * 
	 * @param log_id
	 */
	public void setLog_id(String log_id) {
		this.log_id = log_id;
	}
	
	/**
	 * 内容
	 * 
	 * @param content
	 */
	public void setContent(String content) {
		this.content = content;
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
	
	/**
	 * 操作人ID
	 * 
	 * @param oper_id
	 */
	public void setOper_id(String oper_id) {
		this.oper_id = oper_id;
	}
	
	/**
	 * 操作人名称
	 * 
	 * @param oper_name
	 */
	public void setOper_name(String oper_name) {
		this.oper_name = oper_name;
	}
	

}