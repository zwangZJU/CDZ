package po;

import aos.framework.core.typewrap.PO;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <b>basic_user[basic_user]数据持久化对象</b>
 * <p>
 * 注意:此文件由AOS平台自动生成-禁止手工修改。
 * </p>
 * 
 * @author koney
 * @date 2018-07-10 20:05:26
 */
public class Basic_userPO extends PO {

	private static final long serialVersionUID = 1L;

	/**
	 * 流水号
	 */
	private String id_;
	
	/**
	 * 用户登录帐号
	 */
	private String account;
	
	/**
	 * 密码
	 */
	private String password;
	
	/**
	 * 昵称
	 */
	private String nickname;
	
	/**
	 * 性别
	 */
	private String sex;
	
	/**
	 * 用户状态（是否屏蔽）
	 */
	private String status;
	
	/**
	 * 用户类型用户类型，1：管理员,2：会员
	 */
	private String user_type;
	
	/**
	 * 图片地址
	 */
	private String img_url;
	
	/**
	 * 电子邮件
	 */
	private String email;
	
	/**
	 * 手机号
	 */
	private String mobile;
	
	/**
	 * 用户界面皮肤
	 */
	private String skin;
	
	/**
	 * 业务对照码
	 */
	private String biz_code;
	
	/**
	 * 联系地址
	 */
	private String address;
	
	/**
	 * 介绍/备注
	 */
	private String note;
	
	/**
	 * 是否已删除
	 */
	private String is_del;
	
	/**
	 * 创建时间
	 */
	private Date create_at;
	
	/**
	 * 创建人ID
	 */
	private String create_by;
	
	/**
	 * 最后登录时间
	 */
	private Date login_time;
	
	/**
	 * 排序
	 */
	private Integer sort;
	
	/**
	 * 手机ID
	 */
	private String phone_id;
	
	/**
	 * 手机类型
	 */
	private String phone_type;
	
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
	 * 押金状态，0:未交，1：已交，-1：已退,2：申请退款中
	 */
	private String deposit_status;
	
	/**
	 * 级别，silver：银牌会员，gold
：金牌会员，platinum
：白金会员
	 */
	private String grade;
	
	/**
	 * 姓名
	 */
	private String name;
	
	/**
	 * 身份证号码
	 */
	private String id_card;
	
	/**
	 * 用户头像
	 */
	private String avatar;
	
	/**
	 * 是否认证，0：未认证，1：认证通过，-1：认证不通过,2：已填写认证信息
	 */
	private String is_cert;
	
	/**
	 * 金币状态,1：正常，-1：已退,2：申请退款中
	 */
	private String gold_coins_status;
	
	/**
	 * 设备编号
	 */
	private String device_number;
	
	/**
	 * 登录会话id
	 */
	private String token;
	
	/**
	 * qq号码
	 */
	private String qq;
	
	/**
	 * 微信
	 */
	private String wechat;
	

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
	 * @return account
	 */
	public String getAccount() {
		return account;
	}
	
	/**
	 * 密码
	 * 
	 * @return password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * 昵称
	 * 
	 * @return nickname
	 */
	public String getNickname() {
		return nickname;
	}
	
	/**
	 * 性别
	 * 
	 * @return sex
	 */
	public String getSex() {
		return sex;
	}
	
	/**
	 * 用户状态（是否屏蔽）
	 * 
	 * @return status
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * 用户类型用户类型，1：管理员,2：会员
	 * 
	 * @return user_type
	 */
	public String getUser_type() {
		return user_type;
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
	 * 电子邮件
	 * 
	 * @return email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * 手机号
	 * 
	 * @return mobile
	 */
	public String getMobile() {
		return mobile;
	}
	
	/**
	 * 用户界面皮肤
	 * 
	 * @return skin
	 */
	public String getSkin() {
		return skin;
	}
	
	/**
	 * 业务对照码
	 * 
	 * @return biz_code
	 */
	public String getBiz_code() {
		return biz_code;
	}
	
	/**
	 * 联系地址
	 * 
	 * @return address
	 */
	public String getAddress() {
		return address;
	}
	
	/**
	 * 介绍/备注
	 * 
	 * @return note
	 */
	public String getNote() {
		return note;
	}
	
	/**
	 * 是否已删除
	 * 
	 * @return is_del
	 */
	public String getIs_del() {
		return is_del;
	}
	
	/**
	 * 创建时间
	 * 
	 * @return create_at
	 */
	public Date getCreate_at() {
		return create_at;
	}
	
	/**
	 * 创建人ID
	 * 
	 * @return create_by
	 */
	public String getCreate_by() {
		return create_by;
	}
	
	/**
	 * 最后登录时间
	 * 
	 * @return login_time
	 */
	public Date getLogin_time() {
		return login_time;
	}
	
	/**
	 * 排序
	 * 
	 * @return sort
	 */
	public Integer getSort() {
		return sort;
	}
	
	/**
	 * 手机ID
	 * 
	 * @return phone_id
	 */
	public String getPhone_id() {
		return phone_id;
	}
	
	/**
	 * 手机类型
	 * 
	 * @return phone_type
	 */
	public String getPhone_type() {
		return phone_type;
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
	 * 押金状态，0:未交，1：已交，-1：已退,2：申请退款中
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
	 * @return grade
	 */
	public String getGrade() {
		return grade;
	}
	
	/**
	 * 姓名
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
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
	 * 用户头像
	 * 
	 * @return avatar
	 */
	public String getAvatar() {
		return avatar;
	}
	
	/**
	 * 是否认证，0：未认证，1：认证通过，-1：认证不通过,2：已填写认证信息
	 * 
	 * @return is_cert
	 */
	public String getIs_cert() {
		return is_cert;
	}
	
	/**
	 * 金币状态,1：正常，-1：已退,2：申请退款中
	 * 
	 * @return gold_coins_status
	 */
	public String getGold_coins_status() {
		return gold_coins_status;
	}
	
	/**
	 * 设备编号
	 * 
	 * @return device_number
	 */
	public String getDevice_number() {
		return device_number;
	}
	
	/**
	 * 登录会话id
	 * 
	 * @return token
	 */
	public String getToken() {
		return token;
	}
	
	/**
	 * qq号码
	 * 
	 * @return qq
	 */
	public String getQq() {
		return qq;
	}
	
	/**
	 * 微信
	 * 
	 * @return wechat
	 */
	public String getWechat() {
		return wechat;
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
	 * @param account
	 */
	public void setAccount(String account) {
		this.account = account;
	}
	
	/**
	 * 密码
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * 昵称
	 * 
	 * @param nickname
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	/**
	 * 性别
	 * 
	 * @param sex
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	/**
	 * 用户状态（是否屏蔽）
	 * 
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * 用户类型用户类型，1：管理员,2：会员
	 * 
	 * @param user_type
	 */
	public void setUser_type(String user_type) {
		this.user_type = user_type;
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
	 * 电子邮件
	 * 
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * 手机号
	 * 
	 * @param mobile
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	/**
	 * 用户界面皮肤
	 * 
	 * @param skin
	 */
	public void setSkin(String skin) {
		this.skin = skin;
	}
	
	/**
	 * 业务对照码
	 * 
	 * @param biz_code
	 */
	public void setBiz_code(String biz_code) {
		this.biz_code = biz_code;
	}
	
	/**
	 * 联系地址
	 * 
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	
	/**
	 * 介绍/备注
	 * 
	 * @param note
	 */
	public void setNote(String note) {
		this.note = note;
	}
	
	/**
	 * 是否已删除
	 * 
	 * @param is_del
	 */
	public void setIs_del(String is_del) {
		this.is_del = is_del;
	}
	
	/**
	 * 创建时间
	 * 
	 * @param create_at
	 */
	public void setCreate_at(Date create_at) {
		this.create_at = create_at;
	}
	
	/**
	 * 创建人ID
	 * 
	 * @param create_by
	 */
	public void setCreate_by(String create_by) {
		this.create_by = create_by;
	}
	
	/**
	 * 最后登录时间
	 * 
	 * @param login_time
	 */
	public void setLogin_time(Date login_time) {
		this.login_time = login_time;
	}
	
	/**
	 * 排序
	 * 
	 * @param sort
	 */
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	/**
	 * 手机ID
	 * 
	 * @param phone_id
	 */
	public void setPhone_id(String phone_id) {
		this.phone_id = phone_id;
	}
	
	/**
	 * 手机类型
	 * 
	 * @param phone_type
	 */
	public void setPhone_type(String phone_type) {
		this.phone_type = phone_type;
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
	 * 押金状态，0:未交，1：已交，-1：已退,2：申请退款中
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
	 * @param grade
	 */
	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	/**
	 * 姓名
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
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
	 * 用户头像
	 * 
	 * @param avatar
	 */
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	/**
	 * 是否认证，0：未认证，1：认证通过，-1：认证不通过,2：已填写认证信息
	 * 
	 * @param is_cert
	 */
	public void setIs_cert(String is_cert) {
		this.is_cert = is_cert;
	}
	
	/**
	 * 金币状态,1：正常，-1：已退,2：申请退款中
	 * 
	 * @param gold_coins_status
	 */
	public void setGold_coins_status(String gold_coins_status) {
		this.gold_coins_status = gold_coins_status;
	}
	
	/**
	 * 设备编号
	 * 
	 * @param device_number
	 */
	public void setDevice_number(String device_number) {
		this.device_number = device_number;
	}
	
	/**
	 * 登录会话id
	 * 
	 * @param token
	 */
	public void setToken(String token) {
		this.token = token;
	}
	
	/**
	 * qq号码
	 * 
	 * @param qq
	 */
	public void setQq(String qq) {
		this.qq = qq;
	}
	
	/**
	 * 微信
	 * 
	 * @param wechat
	 */
	public void setWechat(String wechat) {
		this.wechat = wechat;
	}
	

}