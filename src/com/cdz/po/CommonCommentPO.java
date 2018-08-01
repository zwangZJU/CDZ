package po;

import aos.framework.core.typewrap.PO;
import java.util.Date;

/**
 * <b>common_comment[common_comment]数据持久化对象</b>
 * <p>
 * 注意:此文件由AOS平台自动生成-禁止手工修改。
 * </p>
 * 
 * @author duanchongfeng
 * @date 2017-07-06 20:31:42
 */
public class CommonCommentPO extends PO {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private String cc_id;
	
	/**
	 * 用户ID
	 */
	private String user_id;
	
	/**
	 * 投诉内容
	 */
	private String comment_;
	
	/**
	 * 状态,0:新建，1：发布，2：不发布
	 */
	private String status_;
	
	/**
	 * 创建时间
	 */
	private Date create_date;
	
	/**
	 * 发布时间
	 */
	private Date publish_date;
	
	/**
	 * 发布人ID
	 */
	private String oper_id;
	
	/**
	 * 图片地址1
	 */
	private String img_url1;
	
	/**
	 * 图片地址2
	 */
	private String img_url2;
	
	/**
	 * 图片地址3
	 */
	private String img_url3;
	
	/**
	 * 图片地址4
	 */
	private String img_url4;
	
	/**
	 * 图片地址5
	 */
	private String img_url5;
	
	/**
	 * 图片地址6
	 */
	private String img_url6;
	
	/**
	 * 是否删除，0：未删除，1：删除
	 */
	private String is_del;
	

	/**
	 * 主键
	 * 
	 * @return cc_id
	 */
	public String getCc_id() {
		return cc_id;
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
	 * 投诉内容
	 * 
	 * @return comment_
	 */
	public String getComment_() {
		return comment_;
	}
	
	/**
	 * 状态,0:新建，1：发布，2：不发布
	 * 
	 * @return status_
	 */
	public String getStatus_() {
		return status_;
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
	 * 发布时间
	 * 
	 * @return publish_date
	 */
	public Date getPublish_date() {
		return publish_date;
	}
	
	/**
	 * 发布人ID
	 * 
	 * @return oper_id
	 */
	public String getOper_id() {
		return oper_id;
	}
	
	/**
	 * 图片地址1
	 * 
	 * @return img_url1
	 */
	public String getImg_url1() {
		return img_url1;
	}
	
	/**
	 * 图片地址2
	 * 
	 * @return img_url2
	 */
	public String getImg_url2() {
		return img_url2;
	}
	
	/**
	 * 图片地址3
	 * 
	 * @return img_url3
	 */
	public String getImg_url3() {
		return img_url3;
	}
	
	/**
	 * 图片地址4
	 * 
	 * @return img_url4
	 */
	public String getImg_url4() {
		return img_url4;
	}
	
	/**
	 * 图片地址5
	 * 
	 * @return img_url5
	 */
	public String getImg_url5() {
		return img_url5;
	}
	
	/**
	 * 图片地址6
	 * 
	 * @return img_url6
	 */
	public String getImg_url6() {
		return img_url6;
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
	 * @param cc_id
	 */
	public void setCc_id(String cc_id) {
		this.cc_id = cc_id;
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
	 * 投诉内容
	 * 
	 * @param comment_
	 */
	public void setComment_(String comment_) {
		this.comment_ = comment_;
	}
	
	/**
	 * 状态,0:新建，1：发布，2：不发布
	 * 
	 * @param status_
	 */
	public void setStatus_(String status_) {
		this.status_ = status_;
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
	 * 发布时间
	 * 
	 * @param publish_date
	 */
	public void setPublish_date(Date publish_date) {
		this.publish_date = publish_date;
	}
	
	/**
	 * 发布人ID
	 * 
	 * @param oper_id
	 */
	public void setOper_id(String oper_id) {
		this.oper_id = oper_id;
	}
	
	/**
	 * 图片地址1
	 * 
	 * @param img_url1
	 */
	public void setImg_url1(String img_url1) {
		this.img_url1 = img_url1;
	}
	
	/**
	 * 图片地址2
	 * 
	 * @param img_url2
	 */
	public void setImg_url2(String img_url2) {
		this.img_url2 = img_url2;
	}
	
	/**
	 * 图片地址3
	 * 
	 * @param img_url3
	 */
	public void setImg_url3(String img_url3) {
		this.img_url3 = img_url3;
	}
	
	/**
	 * 图片地址4
	 * 
	 * @param img_url4
	 */
	public void setImg_url4(String img_url4) {
		this.img_url4 = img_url4;
	}
	
	/**
	 * 图片地址5
	 * 
	 * @param img_url5
	 */
	public void setImg_url5(String img_url5) {
		this.img_url5 = img_url5;
	}
	
	/**
	 * 图片地址6
	 * 
	 * @param img_url6
	 */
	public void setImg_url6(String img_url6) {
		this.img_url6 = img_url6;
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