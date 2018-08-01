/**
 * 
 */
package po.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Administrator
 *
 */
public class ActivityDTO implements Serializable{
	public String getActivity_id() {
		return activity_id;
	}

	public void setActivity_id(String activity_id) {
		this.activity_id = activity_id;
	}

	public String getName_() {
		return name_;
	}

	public void setName_(String name_) {
		this.name_ = name_;
	}

	public String getType_() {
		return type_;
	}

	public void setType_(String type_) {
		this.type_ = type_;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImg_url() {
		return img_url;
	}

	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	public String getDesc_() {
		return desc_;
	}

	public void setDesc_(String desc_) {
		this.desc_ = desc_;
	}

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
}
