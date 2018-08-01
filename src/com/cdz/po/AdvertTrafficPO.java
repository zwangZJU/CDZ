package po;

import aos.framework.core.typewrap.PO;
import java.util.Date;

/**
 * <b>advert_traffic[advert_traffic]数据持久化对象</b>
 * <p>
 * 注意:此文件由AOS平台自动生成-禁止手工修改。
 * </p>
 * 
 * @author duanchongfeng
 * @date 2017-07-06 20:19:05
 */
public class AdvertTrafficPO extends PO {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private String at_id;
	
	/**
	 * 广告id
	 */
	private String ad_id;
	
	/**
	 * 设备类型，0：IOS，1：android
	 */
	private String at_type;
	
	/**
	 * 展示次数
	 */
	private Integer at_mun;
	
	/**
	 * 是否删除，0：未删除，1：删除
	 */
	private String is_del;
	
	/**
	 * 展示时间
	 */
	private Date create_date;
	
	/**
	 * 操作人ID
	 */
	private String oper_id;
	

	/**
	 * 主键
	 * 
	 * @return at_id
	 */
	public String getAt_id() {
		return at_id;
	}
	
	/**
	 * 广告id
	 * 
	 * @return ad_id
	 */
	public String getAd_id() {
		return ad_id;
	}
	
	/**
	 * 设备类型，0：IOS，1：android
	 * 
	 * @return at_type
	 */
	public String getAt_type() {
		return at_type;
	}
	
	/**
	 * 展示次数
	 * 
	 * @return at_mun
	 */
	public Integer getAt_mun() {
		return at_mun;
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
	 * 展示时间
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
	 * @param at_id
	 */
	public void setAt_id(String at_id) {
		this.at_id = at_id;
	}
	
	/**
	 * 广告id
	 * 
	 * @param ad_id
	 */
	public void setAd_id(String ad_id) {
		this.ad_id = ad_id;
	}
	
	/**
	 * 设备类型，0：IOS，1：android
	 * 
	 * @param at_type
	 */
	public void setAt_type(String at_type) {
		this.at_type = at_type;
	}
	
	/**
	 * 展示次数
	 * 
	 * @param at_mun
	 */
	public void setAt_mun(Integer at_mun) {
		this.at_mun = at_mun;
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
	 * 展示时间
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