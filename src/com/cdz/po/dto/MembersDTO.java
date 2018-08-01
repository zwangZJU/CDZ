/**
 * 
 */
package po.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Administrator
 *
 */
public class MembersDTO {
	
	private String id_;
	
	/**
	 * 用户登录帐号
	 */
	private String account_;
	
	
	
	/**
	 * 昵称
	 */
	private String name_;
	
	
	/**
	 * 金币
	 */
	private BigDecimal gold_coins;
	
	
	
	/**
	 * 押金
	 */
	private BigDecimal deposit_amt;
	
	private String deposit_amt_text;
	
	public String getDeposit_amt_text() {
		return deposit_amt_text;
	}

	public void setDeposit_amt_text(String deposit_amt_text) {
		this.deposit_amt_text = deposit_amt_text;
	}

	/**
	 * 押金支付时间
	 */
	private Date deposit_date;
	
	/**
	 * 押金状态，0:未交，1：已交，-1：已退
	 */
	private String deposit_status;
	
	private String deposit_status_name;
	
	private String gold_coins_status;
	
	public String getGold_coins_status() {
		return gold_coins_status;
	}

	public void setGold_coins_status(String gold_coins_status) {
		this.gold_coins_status = gold_coins_status;
	}

	public String getGold_coins_status_name() {
		return gold_coins_status_name;
	}

	public void setGold_coins_status_name(String gold_coins_status_name) {
		this.gold_coins_status_name = gold_coins_status_name;
	}

	private String gold_coins_status_name;
	
	public String getDeposit_status_name() {
		return deposit_status_name;
	}

	public void setDeposit_status_name(String deposit_status_name) {
		this.deposit_status_name = deposit_status_name;
	}

	/**
	 * 级别，silver：银牌会员，gold
		：金牌会员，platinum
		：白金会员
	 */
	private String grade_;
	
	private String grade_name;
	
	public String getId_() {
		return id_;
	}

	public void setId_(String id_) {
		this.id_ = id_;
	}

	public String getAccount_() {
		return account_;
	}

	public void setAccount_(String account_) {
		this.account_ = account_;
	}

	public String getName_() {
		return name_;
	}

	public void setName_(String name_) {
		this.name_ = name_;
	}

	public BigDecimal getGold_coins() {
		return gold_coins;
	}

	public void setGold_coins(BigDecimal gold_coins) {
		this.gold_coins = gold_coins;
	}

	public BigDecimal getDeposit_amt() {
		return deposit_amt;
	}

	public void setDeposit_amt(BigDecimal deposit_amt) {
		this.deposit_amt = deposit_amt;
	}

	public Date getDeposit_date() {
		return deposit_date;
	}

	public void setDeposit_date(Date deposit_date) {
		this.deposit_date = deposit_date;
	}

	public String getDeposit_status() {
		return deposit_status;
	}

	public void setDeposit_status(String deposit_status) {
		this.deposit_status = deposit_status;
	}

	public String getGrade_() {
		return grade_;
	}

	public void setGrade_(String grade_) {
		this.grade_ = grade_;
	}

	public String getGrade_name() {
		return grade_name;
	}

	public void setGrade_name(String grade_name) {
		this.grade_name = grade_name;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getId_card() {
		return id_card;
	}

	public void setId_card(String id_card) {
		this.id_card = id_card;
	}

	public String getVehicle_license() {
		return vehicle_license;
	}

	public void setVehicle_license(String vehicle_license) {
		this.vehicle_license = vehicle_license;
	}

	public String getDriver_license() {
		return driver_license;
	}

	public void setDriver_license(String driver_license) {
		this.driver_license = driver_license;
	}

	public String getVehicle_img() {
		return vehicle_img;
	}

	public void setVehicle_img(String vehicle_img) {
		this.vehicle_img = vehicle_img;
	}

	public String getDriver_img() {
		return driver_img;
	}

	public void setDriver_img(String driver_img) {
		this.driver_img = driver_img;
	}

	public String getUser_img() {
		return user_img;
	}

	public void setUser_img(String user_img) {
		this.user_img = user_img;
	}

	public String getIs_cert() {
		return is_cert;
	}

	public void setIs_cert(String is_cert) {
		this.is_cert = is_cert;
	}

	public String getIs_cert_name() {
		return is_cert_name;
	}

	public void setIs_cert_name(String is_cert_name) {
		this.is_cert_name = is_cert_name;
	}

	/**
	 * 姓名
	 */
	private String user_name;
	
	/**
	 * 身份证号码
	 */
	private String id_card;
	
	/**
	 * 行驶证号
	 */
	private String vehicle_license;
	
	/**
	 * 驾驶证号
	 */
	private String driver_license;
	
	/**
	 * 行驶证图片
	 */
	private String vehicle_img;
	
	/**
	 * 驾驶证图片
	 */
	private String driver_img;
	
	/**
	 * 用户头像
	 */
	private String user_img;
	
	/**
	 * 是否认证，0：未认证，1：认证通过，-1：认证不通过
	 */
	private String is_cert;
	
	private String is_cert_name;
}
