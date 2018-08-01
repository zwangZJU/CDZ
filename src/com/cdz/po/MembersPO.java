package po;

import aos.framework.core.typewrap.PO;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <b>members[members]数据持久化对象</b>
 * <p>
 * 注意:此文件由AOS平台自动生成-禁止手工修改。
 * </p>
 * 
 * @author duanchongfeng
 * @date 2017-07-06 20:19:03
 */
public class MembersPO extends PO {

	private static final long serialVersionUID = 1L;

	/**
	 * 流水号
	 */
	private String id_;
	
	/**
	 * 用户登录帐号
	 */
	private String account_;
	
	/**
	 * 密码
	 */
	private String password_;
	
	/**
	 * 昵称
	 */
	private String name_;
	
	/**
	 * 性别
	 */
	private String sex_;
	
	/**
	 * 用户状态（是否屏蔽）
	 */
	private String status_;
	
	/**
	 * 用户类型用户类型，1：管理员,2：销售员
	 */
	private String type_;
	
	/**
	 * 图片地址
	 */
	private String org_id_;
	
	/**
	 * 电子邮件
	 */
	private String email_;
	
	/**
	 * 手机号
	 */
	private String mobile_;
	
	/**
	 * 身份证号
	 */
	private String idno_;
	
	/**
	 * 用户界面皮肤
	 */
	private String skin_;
	
	/**
	 * 业务对照码
	 */
	private String biz_code_;
	
	/**
	 * 联系地址
	 */
	private String address_;
	
	/**
	 * 介绍/备注
	 */
	private String remark_;
	
	/**
	 * 是否已删除
	 */
	private String is_del_;
	
	/**
	 * 创建时间
	 */
	private Date create_time_;
	
	/**
	 * 创建人ID
	 */
	private String create_by_;
	
	/**
	 * 最后登录时间
	 */
	private Date login_time_;
	
	/**
	 * 排序
	 */
	private Integer sort_;
	
	/**
	 * 设备ID
	 */
	private String registration_id;
	
	/**
	 * 设备类型
	 */
	private String registration_type;
	
	/**
	 * 是否注册环信
	 */
	private String is_huanxin;
	
	/**
	 * 金币
	 */
	private BigDecimal gold_coins;
	
	/**
	 * 支付密码
	 */
	private String pay_password;
	
	/**
	 * 押金
	 */
	private BigDecimal deposit_amt;
	
	/**
	 * 押金支付时间
	 */
	private Date deposit_date;
	
	/**
	 * 押金状态，0:未交，1：已交，-1：已退
	 */
	private String deposit_status;
	
	/**
	 * 级别，silver：银牌会员，gold
：金牌会员，platinum
：白金会员
	 */
	private String grade_;
	
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
	/**
	 * 金币状态,1：正常，-1：已退,2：申请退款中
	 */
	private String gold_coins_status;
	
	private String gold_coins_status_name;
	public String getGold_coins_status_name() {
		return gold_coins_status_name;
	}

	public void setGold_coins_status_name(String gold_coins_status_name) {
		this.gold_coins_status_name = gold_coins_status_name;
	}

	public String getGold_coins_status() {
		return gold_coins_status;
	}

	public void setGold_coins_status(String gold_coins_status) {
		this.gold_coins_status = gold_coins_status;
	}

	/**
	 * 流水号
	 * 
	 * @return id_
	 */
	public String getId_() {
		return id_;
	}
	
	/**
	 * 用户登录帐号
	 * 
	 * @return account_
	 */
	public String getAccount_() {
		return account_;
	}
	
	/**
	 * 密码
	 * 
	 * @return password_
	 */
	public String getPassword_() {
		return password_;
	}
	
	/**
	 * 昵称
	 * 
	 * @return name_
	 */
	public String getName_() {
		return name_;
	}
	
	/**
	 * 性别
	 * 
	 * @return sex_
	 */
	public String getSex_() {
		return sex_;
	}
	
	/**
	 * 用户状态（是否屏蔽）
	 * 
	 * @return status_
	 */
	public String getStatus_() {
		return status_;
	}
	
	/**
	 * 用户类型用户类型，1：管理员,2：销售员
	 * 
	 * @return type_
	 */
	public String getType_() {
		return type_;
	}
	
	/**
	 * 图片地址
	 * 
	 * @return org_id_
	 */
	public String getOrg_id_() {
		return org_id_;
	}
	
	/**
	 * 电子邮件
	 * 
	 * @return email_
	 */
	public String getEmail_() {
		return email_;
	}
	
	/**
	 * 手机号
	 * 
	 * @return mobile_
	 */
	public String getMobile_() {
		return mobile_;
	}
	
	/**
	 * 身份证号
	 * 
	 * @return idno_
	 */
	public String getIdno_() {
		return idno_;
	}
	
	/**
	 * 用户界面皮肤
	 * 
	 * @return skin_
	 */
	public String getSkin_() {
		return skin_;
	}
	
	/**
	 * 业务对照码
	 * 
	 * @return biz_code_
	 */
	public String getBiz_code_() {
		return biz_code_;
	}
	
	/**
	 * 联系地址
	 * 
	 * @return address_
	 */
	public String getAddress_() {
		return address_;
	}
	
	/**
	 * 介绍/备注
	 * 
	 * @return remark_
	 */
	public String getRemark_() {
		return remark_;
	}
	
	/**
	 * 是否已删除
	 * 
	 * @return is_del_
	 */
	public String getIs_del_() {
		return is_del_;
	}
	
	/**
	 * 创建时间
	 * 
	 * @return create_time_
	 */
	public Date getCreate_time_() {
		return create_time_;
	}
	
	/**
	 * 创建人ID
	 * 
	 * @return create_by_
	 */
	public String getCreate_by_() {
		return create_by_;
	}
	
	/**
	 * 最后登录时间
	 * 
	 * @return login_time_
	 */
	public Date getLogin_time_() {
		return login_time_;
	}
	
	/**
	 * 排序
	 * 
	 * @return sort_
	 */
	public Integer getSort_() {
		return sort_;
	}
	
	/**
	 * 设备ID
	 * 
	 * @return registration_id
	 */
	public String getRegistration_id() {
		return registration_id;
	}
	
	/**
	 * 设备类型
	 * 
	 * @return registration_type
	 */
	public String getRegistration_type() {
		return registration_type;
	}
	
	/**
	 * 是否注册环信
	 * 
	 * @return is_huanxin
	 */
	public String getIs_huanxin() {
		return is_huanxin;
	}
	
	/**
	 * 金币
	 * 
	 * @return gold_coins
	 */
	public BigDecimal getGold_coins() {
		return gold_coins;
	}
	
	/**
	 * 支付密码
	 * 
	 * @return pay_password
	 */
	public String getPay_password() {
		return pay_password;
	}
	
	/**
	 * 押金
	 * 
	 * @return deposit_amt
	 */
	public BigDecimal getDeposit_amt() {
		return deposit_amt;
	}
	
	/**
	 * 押金支付时间
	 * 
	 * @return deposit_date
	 */
	public Date getDeposit_date() {
		return deposit_date;
	}
	
	/**
	 * 押金状态，0:未交，1：已交，-1：已退
	 * 
	 * @return deposit_status
	 */
	public String getDeposit_status() {
		return deposit_status;
	}
	
	/**
	 * 级别，silver：银牌会员，gold
：金牌会员，platinum
：白金会员
	 * 
	 * @return grade_
	 */
	public String getGrade_() {
		return grade_;
	}
	
	/**
	 * 姓名
	 * 
	 * @return user_name
	 */
	public String getUser_name() {
		return user_name;
	}
	
	/**
	 * 身份证号码
	 * 
	 * @return id_card
	 */
	public String getId_card() {
		return id_card;
	}
	
	/**
	 * 行驶证号
	 * 
	 * @return vehicle_license
	 */
	public String getVehicle_license() {
		return vehicle_license;
	}
	
	/**
	 * 驾驶证号
	 * 
	 * @return driver_license
	 */
	public String getDriver_license() {
		return driver_license;
	}
	
	/**
	 * 行驶证图片
	 * 
	 * @return vehicle_img
	 */
	public String getVehicle_img() {
		return vehicle_img;
	}
	
	/**
	 * 驾驶证图片
	 * 
	 * @return driver_img
	 */
	public String getDriver_img() {
		return driver_img;
	}
	
	/**
	 * 用户头像
	 * 
	 * @return user_img
	 */
	public String getUser_img() {
		return user_img;
	}
	
	/**
	 * 是否认证，0：未认证，1：认证通过，-1：认证不通过
	 * 
	 * @return is_cert
	 */
	public String getIs_cert() {
		return is_cert;
	}
	

	/**
	 * 流水号
	 * 
	 * @param id_
	 */
	public void setId_(String id_) {
		this.id_ = id_;
	}
	
	/**
	 * 用户登录帐号
	 * 
	 * @param account_
	 */
	public void setAccount_(String account_) {
		this.account_ = account_;
	}
	
	/**
	 * 密码
	 * 
	 * @param password_
	 */
	public void setPassword_(String password_) {
		this.password_ = password_;
	}
	
	/**
	 * 昵称
	 * 
	 * @param name_
	 */
	public void setName_(String name_) {
		this.name_ = name_;
	}
	
	/**
	 * 性别
	 * 
	 * @param sex_
	 */
	public void setSex_(String sex_) {
		this.sex_ = sex_;
	}
	
	/**
	 * 用户状态（是否屏蔽）
	 * 
	 * @param status_
	 */
	public void setStatus_(String status_) {
		this.status_ = status_;
	}
	
	/**
	 * 用户类型用户类型，1：管理员,2：销售员
	 * 
	 * @param type_
	 */
	public void setType_(String type_) {
		this.type_ = type_;
	}
	
	/**
	 * 图片地址
	 * 
	 * @param org_id_
	 */
	public void setOrg_id_(String org_id_) {
		this.org_id_ = org_id_;
	}
	
	/**
	 * 电子邮件
	 * 
	 * @param email_
	 */
	public void setEmail_(String email_) {
		this.email_ = email_;
	}
	
	/**
	 * 手机号
	 * 
	 * @param mobile_
	 */
	public void setMobile_(String mobile_) {
		this.mobile_ = mobile_;
	}
	
	/**
	 * 身份证号
	 * 
	 * @param idno_
	 */
	public void setIdno_(String idno_) {
		this.idno_ = idno_;
	}
	
	/**
	 * 用户界面皮肤
	 * 
	 * @param skin_
	 */
	public void setSkin_(String skin_) {
		this.skin_ = skin_;
	}
	
	/**
	 * 业务对照码
	 * 
	 * @param biz_code_
	 */
	public void setBiz_code_(String biz_code_) {
		this.biz_code_ = biz_code_;
	}
	
	/**
	 * 联系地址
	 * 
	 * @param address_
	 */
	public void setAddress_(String address_) {
		this.address_ = address_;
	}
	
	/**
	 * 介绍/备注
	 * 
	 * @param remark_
	 */
	public void setRemark_(String remark_) {
		this.remark_ = remark_;
	}
	
	/**
	 * 是否已删除
	 * 
	 * @param is_del_
	 */
	public void setIs_del_(String is_del_) {
		this.is_del_ = is_del_;
	}
	
	/**
	 * 创建时间
	 * 
	 * @param create_time_
	 */
	public void setCreate_time_(Date create_time_) {
		this.create_time_ = create_time_;
	}
	
	/**
	 * 创建人ID
	 * 
	 * @param create_by_
	 */
	public void setCreate_by_(String create_by_) {
		this.create_by_ = create_by_;
	}
	
	/**
	 * 最后登录时间
	 * 
	 * @param login_time_
	 */
	public void setLogin_time_(Date login_time_) {
		this.login_time_ = login_time_;
	}
	
	/**
	 * 排序
	 * 
	 * @param sort_
	 */
	public void setSort_(Integer sort_) {
		this.sort_ = sort_;
	}
	
	/**
	 * 设备ID
	 * 
	 * @param registration_id
	 */
	public void setRegistration_id(String registration_id) {
		this.registration_id = registration_id;
	}
	
	/**
	 * 设备类型
	 * 
	 * @param registration_type
	 */
	public void setRegistration_type(String registration_type) {
		this.registration_type = registration_type;
	}
	
	/**
	 * 是否注册环信
	 * 
	 * @param is_huanxin
	 */
	public void setIs_huanxin(String is_huanxin) {
		this.is_huanxin = is_huanxin;
	}
	
	/**
	 * 金币
	 * 
	 * @param gold_coins
	 */
	public void setGold_coins(BigDecimal gold_coins) {
		this.gold_coins = gold_coins;
	}
	
	/**
	 * 支付密码
	 * 
	 * @param pay_password
	 */
	public void setPay_password(String pay_password) {
		this.pay_password = pay_password;
	}
	
	/**
	 * 押金
	 * 
	 * @param deposit_amt
	 */
	public void setDeposit_amt(BigDecimal deposit_amt) {
		this.deposit_amt = deposit_amt;
	}
	
	/**
	 * 押金支付时间
	 * 
	 * @param deposit_date
	 */
	public void setDeposit_date(Date deposit_date) {
		this.deposit_date = deposit_date;
	}
	
	/**
	 * 押金状态，0:未交，1：已交，-1：已退
	 * 
	 * @param deposit_status
	 */
	public void setDeposit_status(String deposit_status) {
		this.deposit_status = deposit_status;
	}
	
	/**
	 * 级别，silver：银牌会员，gold
：金牌会员，platinum
：白金会员
	 * 
	 * @param grade_
	 */
	public void setGrade_(String grade_) {
		this.grade_ = grade_;
	}
	
	/**
	 * 姓名
	 * 
	 * @param user_name
	 */
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	
	/**
	 * 身份证号码
	 * 
	 * @param id_card
	 */
	public void setId_card(String id_card) {
		this.id_card = id_card;
	}
	
	/**
	 * 行驶证号
	 * 
	 * @param vehicle_license
	 */
	public void setVehicle_license(String vehicle_license) {
		this.vehicle_license = vehicle_license;
	}
	
	/**
	 * 驾驶证号
	 * 
	 * @param driver_license
	 */
	public void setDriver_license(String driver_license) {
		this.driver_license = driver_license;
	}
	
	/**
	 * 行驶证图片
	 * 
	 * @param vehicle_img
	 */
	public void setVehicle_img(String vehicle_img) {
		this.vehicle_img = vehicle_img;
	}
	
	/**
	 * 驾驶证图片
	 * 
	 * @param driver_img
	 */
	public void setDriver_img(String driver_img) {
		this.driver_img = driver_img;
	}
	
	/**
	 * 用户头像
	 * 
	 * @param user_img
	 */
	public void setUser_img(String user_img) {
		this.user_img = user_img;
	}
	
	/**
	 * 是否认证，0：未认证，1：认证通过，-1：认证不通过
	 * 
	 * @param is_cert
	 */
	public void setIs_cert(String is_cert) {
		this.is_cert = is_cert;
	}
	

}