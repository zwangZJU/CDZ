package po;

import aos.framework.core.typewrap.PO;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <b>活动表[activity]数据持久化对象</b>
 * <p>
 * 注意:此文件由AOS平台自动生成-禁止手工修改。
 * </p>
 * 
 * @author duanchongfeng
 * @date 2017-04-20 15:04:10
 */
public class ActivityPO extends PO {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private String activity_id;
	
	/**
	 * 名称
	 */
	private String name_;
	
	/**
	 * 类型，1：满减，2：满折，3：满送，4满赠
	 */
	private String type_;
	
	/**
	 * 短名称
	 */
	private String shortname;
	
	/**
	 * 跳转地址
	 */
	private String url;
	
	/**
	 * 图片地址
	 */
	private String img_url;
	
	/**
	 * 开始时间
	 */
	private Date start_date;
	
	/**
	 * 结束时间
	 */
	private Date end_date;
	
	/**
	 * 活动描述
	 */
	private String desc_;
	
	/**
	 * 状态
	 */
	private String status_;
	
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
	
	private BigDecimal amount;
	
	private BigDecimal ar_num;
	

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getAr_num() {
		return ar_num;
	}

	public void setAr_num(BigDecimal ar_num) {
		this.ar_num = ar_num;
	}

	/**
	 * 主键
	 * 
	 * @return activity_id
	 */
	public String getActivity_id() {
		return activity_id;
	}
	
	/**
	 * 名称
	 * 
	 * @return name_
	 */
	public String getName_() {
		return name_;
	}
	
	/**
	 * 类型，1：满减，2：满折，3：满送，4满赠
	 * 
	 * @return type_
	 */
	public String getType_() {
		return type_;
	}
	
	/**
	 * 短名称
	 * 
	 * @return shortname
	 */
	public String getShortname() {
		return shortname;
	}
	
	/**
	 * 跳转地址
	 * 
	 * @return url
	 */
	public String getUrl() {
		return url;
	}
	
	/**
	 * 图片地址
	 * 
	 * @return img_url
	 */
	public String getImg_url() {
		return img_url;
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
	 * 活动描述
	 * 
	 * @return desc_
	 */
	public String getDesc_() {
		return desc_;
	}
	
	/**
	 * 状态
	 * 
	 * @return status_
	 */
	public String getStatus_() {
		return status_;
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
	 * @param activity_id
	 */
	public void setActivity_id(String activity_id) {
		this.activity_id = activity_id;
	}
	
	/**
	 * 名称
	 * 
	 * @param name_
	 */
	public void setName_(String name_) {
		this.name_ = name_;
	}
	
	/**
	 * 类型，1：满减，2：满折，3：满送，4满赠
	 * 
	 * @param type_
	 */
	public void setType_(String type_) {
		this.type_ = type_;
	}
	
	/**
	 * 短名称
	 * 
	 * @param shortname
	 */
	public void setShortname(String shortname) {
		this.shortname = shortname;
	}
	
	/**
	 * 跳转地址
	 * 
	 * @param url
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
	/**
	 * 图片地址
	 * 
	 * @param img_url
	 */
	public void setImg_url(String img_url) {
		this.img_url = img_url;
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
	 * 活动描述
	 * 
	 * @param desc_
	 */
	public void setDesc_(String desc_) {
		this.desc_ = desc_;
	}
	
	/**
	 * 状态
	 * 
	 * @param status_
	 */
	public void setStatus_(String status_) {
		this.status_ = status_;
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