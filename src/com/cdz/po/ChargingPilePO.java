package po;

import aos.framework.core.typewrap.PO;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <b>charging_pile[charging_pile]数据持久化对象</b>
 * <p>
 * 注意:此文件由AOS平台自动生成-禁止手工修改。
 * </p>
 * 
 * @author duanchongfeng
 * @date 2017-07-06 20:31:43
 */
public class ChargingPilePO extends PO {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private String cp_id;
	
	/**
	 * 编号
	 */
	private String cp_no;
	
	/**
	 * 供应商
	 */
	private String supplier;
	
	/**
	 * 省
	 */
	private String province;
	
	/**
	 * 市
	 */
	private String city;
	
	/**
	 * 区
	 */
	private String county;
	
	/**
	 * 地址
	 */
	private String addr;
	
	/**
	 * 型号
	 */
	private String cp_type;
	
	/**
	 * 充电费
	 */
	private BigDecimal electricity;
	
	/**
	 * 状态
	 */
	private String cp_status;
	
	/**
	 * 经度
	 */
	private String lon;
	
	/**
	 * 纬度
	 */
	private String lat;
	
	/**
	 * 创建时间
	 */
	private Date create_date;
	
	/**
	 * 更新时间
	 */
	private Date update_date;
	
	public Date getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}

	/**
	 * 是否删除，0：未删除，1：删除
	 */
	private String is_del;
	
	private String net_way;
	
	private String signal_quality;
	
	private String is_activation;
	
	private String fault_code;
	
	public String getNet_way() {
		return net_way;
	}

	public void setNet_way(String net_way) {
		this.net_way = net_way;
	}

	public String getSignal_quality() {
		return signal_quality;
	}

	public void setSignal_quality(String signal_quality) {
		this.signal_quality = signal_quality;
	}

	public String getIs_activation() {
		return is_activation;
	}

	public void setIs_activation(String is_activation) {
		this.is_activation = is_activation;
	}

	public String getFault_code() {
		return fault_code;
	}

	public void setFault_code(String fault_code) {
		this.fault_code = fault_code;
	}

	/**
	 * 操作人ID
	 */
	private String oper_id;
	

	/**
	 * 主键
	 * 
	 * @return cp_id
	 */
	public String getCp_id() {
		return cp_id;
	}
	
	/**
	 * 编号
	 * 
	 * @return cp_no
	 */
	public String getCp_no() {
		return cp_no;
	}
	
	/**
	 * 供应商
	 * 
	 * @return supplier
	 */
	public String getSupplier() {
		return supplier;
	}
	
	/**
	 * 省
	 * 
	 * @return province
	 */
	public String getProvince() {
		return province;
	}
	
	/**
	 * 市
	 * 
	 * @return city
	 */
	public String getCity() {
		return city;
	}
	
	/**
	 * 区
	 * 
	 * @return county
	 */
	public String getCounty() {
		return county;
	}
	
	/**
	 * 地址
	 * 
	 * @return addr
	 */
	public String getAddr() {
		return addr;
	}
	
	/**
	 * 型号
	 * 
	 * @return cp_type
	 */
	public String getCp_type() {
		return cp_type;
	}
	
	/**
	 * 充电费
	 * 
	 * @return electricity
	 */
	public BigDecimal getElectricity() {
		return electricity;
	}
	
	/**
	 * 状态
	 * 
	 * @return cp_status
	 */
	public String getCp_status() {
		return cp_status;
	}
	
	/**
	 * 经度
	 * 
	 * @return lon
	 */
	public String getLon() {
		return lon;
	}
	
	/**
	 * 纬度
	 * 
	 * @return lat
	 */
	public String getLat() {
		return lat;
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
	 * 主键
	 * 
	 * @param cp_id
	 */
	public void setCp_id(String cp_id) {
		this.cp_id = cp_id;
	}
	
	/**
	 * 编号
	 * 
	 * @param cp_no
	 */
	public void setCp_no(String cp_no) {
		this.cp_no = cp_no;
	}
	
	/**
	 * 供应商
	 * 
	 * @param supplier
	 */
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	
	/**
	 * 省
	 * 
	 * @param province
	 */
	public void setProvince(String province) {
		this.province = province;
	}
	
	/**
	 * 市
	 * 
	 * @param city
	 */
	public void setCity(String city) {
		this.city = city;
	}
	
	/**
	 * 区
	 * 
	 * @param county
	 */
	public void setCounty(String county) {
		this.county = county;
	}
	
	/**
	 * 地址
	 * 
	 * @param addr
	 */
	public void setAddr(String addr) {
		this.addr = addr;
	}
	
	/**
	 * 型号
	 * 
	 * @param cp_type
	 */
	public void setCp_type(String cp_type) {
		this.cp_type = cp_type;
	}
	
	/**
	 * 充电费
	 * 
	 * @param electricity
	 */
	public void setElectricity(BigDecimal electricity) {
		this.electricity = electricity;
	}
	
	/**
	 * 状态
	 * 
	 * @param cp_status
	 */
	public void setCp_status(String cp_status) {
		this.cp_status = cp_status;
	}
	
	/**
	 * 经度
	 * 
	 * @param lon
	 */
	public void setLon(String lon) {
		this.lon = lon;
	}
	
	/**
	 * 纬度
	 * 
	 * @param lat
	 */
	public void setLat(String lat) {
		this.lat = lat;
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
	

}