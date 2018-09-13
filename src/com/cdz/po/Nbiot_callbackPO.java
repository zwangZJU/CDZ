package po;

import aos.framework.core.typewrap.PO;

/**
 * <b>nbiot_callback[nbiot_callback]数据持久化对象</b>
 * <p>
 * 注意:此文件由AOS平台自动生成-禁止手工修改。
 * </p>
 * 
 * @author Administrator
 * @date 2018-09-13 12:34:23
 */
public class Nbiot_callbackPO extends PO {

	private static final long serialVersionUID = 1L;

	/**
	 * 序号
	 */
	private String id_;
	
	/**
	 * 功能
	 */
	private String function_;
	
	/**
	 * 网址
	 */
	private String url_;
	

	/**
	 * 序号
	 * 
	 * @return id_
	 */
	public String getId_() {
		return id_;
	}
	
	/**
	 * 功能
	 * 
	 * @return function_
	 */
	public String getFunction_() {
		return function_;
	}
	
	/**
	 * 网址
	 * 
	 * @return url_
	 */
	public String getUrl_() {
		return url_;
	}
	

	/**
	 * 序号
	 * 
	 * @param id_
	 */
	public void setId_(String id_) {
		this.id_ = id_;
	}
	
	/**
	 * 功能
	 * 
	 * @param function_
	 */
	public void setFunction_(String function_) {
		this.function_ = function_;
	}
	
	/**
	 * 网址
	 * 
	 * @param url_
	 */
	public void setUrl_(String url_) {
		this.url_ = url_;
	}
	

}