package po;

import aos.framework.core.typewrap.PO;
import java.util.Date;

/**
 * <b>messages[messages]数据持久化对象</b>
 * <p>
 * 注意:此文件由AOS平台自动生成-禁止手工修改。
 * </p>
 * 
 * @author duanchongfeng
 * @date 2017-06-13 20:40:40
 */
public class MessagesPO extends PO {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private String msg_id;
	
	/**
	 * 内容
	 */
	private String msg_content;
	
	/**
	 * 消息类型，0：经销商、销售员，1：经销商，2：销售员
	 */
	private String msg_type;
	
	/**
	 * 创建时间
	 */
	private Date create_date;
	
	/**
	 * 操作人ID
	 */
	private String oper_id;
	
	/**
	 * 是否删除，0：未删除，1：删除
	 */
	private String is_del;
	

	/**
	 * 主键
	 * 
	 * @return msg_id
	 */
	public String getMsg_id() {
		return msg_id;
	}
	
	/**
	 * 内容
	 * 
	 * @return msg_content
	 */
	public String getMsg_content() {
		return msg_content;
	}
	
	/**
	 * 消息类型，0：经销商、销售员，1：经销商，2：销售员
	 * 
	 * @return msg_type
	 */
	public String getMsg_type() {
		return msg_type;
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
	 * @param msg_id
	 */
	public void setMsg_id(String msg_id) {
		this.msg_id = msg_id;
	}
	
	/**
	 * 内容
	 * 
	 * @param msg_content
	 */
	public void setMsg_content(String msg_content) {
		this.msg_content = msg_content;
	}
	
	/**
	 * 消息类型，0：经销商、销售员，1：经销商，2：销售员
	 * 
	 * @param msg_type
	 */
	public void setMsg_type(String msg_type) {
		this.msg_type = msg_type;
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
	 * 是否删除，0：未删除，1：删除
	 * 
	 * @param is_del
	 */
	public void setIs_del(String is_del) {
		this.is_del = is_del;
	}
	

}