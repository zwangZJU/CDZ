/**
 * 
 */
package po.dto;

import java.io.Serializable;

/**
 * @author Administrator
 *
 */
public class AdvertDTO implements Serializable{
	/**
	 * 主键
	 */
	private String advert_id;
	
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 短名称
	 */
	private String shortname;
	
	/**
	 * 状态，0：使用中，1：禁用
	 */
	private String status;
	
	/**
	 * 跳转地址
	 */
	private String url;
	
	/**
	 * 图片地址
	 */
	private String img_url;
	
	public String getAdvert_id() {
		return advert_id;
	}

	public void setAdvert_id(String advert_id) {
		this.advert_id = advert_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	/**
	 * 排序
	 */
	private Integer sort;
}
