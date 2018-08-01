package po;

import aos.framework.core.typewrap.PO;
import java.util.Date;

/**
 * <b>文章表[article]数据持久化对象</b>
 * <p>
 * 注意:此文件由AOS平台自动生成-禁止手工修改。
 * </p>
 * 
 * @author duanchongfeng
 * @date 2017-04-21 20:54:18
 */
public class ArticlePO extends PO {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private String article_id;
	
	/**
	 * 标题
	 */
	private String title;
	
	/**
	 * 类型
	 */
	private String type_;
	
	/**
	 * 内容
	 */
	private String content;
	
	/**
	 * 状态，0：新建，1：发布
	 */
	private String status_;
	
	/**
	 * 发布时间
	 */
	private Date publish_date;
	
	/**
	 * 是否删除，0：未删除，1：删除
	 */
	private String is_del;
	
	/**
	 * 创建时间
	 */
	private Date create_date;
	
	/**
	 * 操作人ID
	 */
	private String oper_id;
	

	/**
	 * 主键
	 * 
	 * @return article_id
	 */
	public String getArticle_id() {
		return article_id;
	}
	
	/**
	 * 标题
	 * 
	 * @return title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * 类型
	 * 
	 * @return type_
	 */
	public String getType_() {
		return type_;
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
	 * 状态，0：新建，1：发布
	 * 
	 * @return status_
	 */
	public String getStatus_() {
		return status_;
	}
	
	/**
	 * 发布时间
	 * 
	 * @return publish_date
	 */
	public Date getPublish_date() {
		return publish_date;
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
	 * 主键
	 * 
	 * @param article_id
	 */
	public void setArticle_id(String article_id) {
		this.article_id = article_id;
	}
	
	/**
	 * 标题
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * 类型
	 * 
	 * @param type_
	 */
	public void setType_(String type_) {
		this.type_ = type_;
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
	 * 状态，0：新建，1：发布
	 * 
	 * @param status_
	 */
	public void setStatus_(String status_) {
		this.status_ = status_;
	}
	
	/**
	 * 发布时间
	 * 
	 * @param publish_date
	 */
	public void setPublish_date(Date publish_date) {
		this.publish_date = publish_date;
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