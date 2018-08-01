/**
 * 
 */
package controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.Socket;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wltea.expression.ExpressionEvaluator;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayConstants;
import com.alipay.api.internal.util.AlipaySignature;
import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.spatial4j.core.context.SpatialContext;
import com.spatial4j.core.distance.DistanceUtils;
import com.spatial4j.core.shape.Rectangle;

import aos.framework.core.cache.CacheMasterDataService;
import aos.framework.core.id.AOSId;
import aos.framework.core.service.CDZBaseController;
import aos.framework.core.typewrap.Dto;
import aos.framework.core.typewrap.Dtos;
import aos.framework.core.utils.AOSCodec;
import aos.framework.core.utils.AOSCons;
import aos.framework.core.utils.AOSCxt;
import aos.framework.core.utils.AOSJson;
import aos.framework.core.utils.AOSUtils;
import aos.framework.dao.AosDicPO;
import aos.framework.dao.AosParamsDao;
import aos.framework.dao.AosParamsPO;
import aos.framework.dao.AosUserDao;
import aos.framework.dao.AosUserPO;
import aos.framework.web.router.HttpModel;
import aos.system.common.model.UserModel;
import aos.system.common.utils.SystemCons;
import aos.system.modules.cache.CacheUserDataService;
import dao.ActivityDao;
import dao.ActivityRuleDao;
import dao.AdvertDao;
import dao.AdvertTrafficDao;
import dao.ArticleDao;
import dao.ChargingOrdersDao;
import dao.ChargingPileDao;
import dao.CommonCommentDao;
import dao.DepositListDao;
import dao.MembersDao;
import dao.MessagesDao;
import dao.OrdersPayDao;
import dao.RegionDao;
import dao.SubscribeDao;
import dao.VoucherDao;
import pay.alipay.util.DatetimeUtil;
import pay.alipay.util.PayUtil;
import pay.wxpay.GetWxOrderno;
import pay.wxpay.RequestHandler;
import pay.wxpay.Sha1Util;
import pay.wxpay.TenpayUtil;
import po.ActivityPO;
import po.ActivityRulePO;
import po.AdvertPO;
import po.AdvertTrafficPO;
import po.ArticlePO;
import po.ChargingOrdersPO;
import po.ChargingPilePO;
import po.CommonCommentPO;
import po.CommonLogsPO;
import po.DepositListPO;
import po.MembersPO;
import po.OrdersPayPO;
import po.RegionPO;
import po.SubscribePO;
import po.VoucherPO;
import po.dto.ActivityDTO;
import po.dto.AdvertDTO;
import po.dto.MembersDTO;
import po.dto.RegionDTO;
import utils.Helper;
import utils.IdcardValidator;

/**
 * @author Administrator
 *cacheUserDataService.getDealerDTO(juid)
 */
@Service
public class AppApiController extends CDZBaseController {
	@Autowired
	private AdvertDao advertDao;
	@Autowired
	private ArticleDao articleDao;
	@Autowired
	private VoucherDao voucherDao;
	
	@Autowired
	private RegionDao regionDao;
	@Autowired
	private ActivityDao activityDao;
	
	@Autowired
	private CacheMasterDataService cacheMasterDataService;
	
	@Autowired
	private CacheUserDataService cacheUserDataService;
	@Autowired
	AosUserDao aosUserDao;
	
	@Autowired
	ActivityRuleDao activityRuleDao;
	@Autowired
	AdvertTrafficDao advertTrafficDao;
	
	@Autowired
	MembersDao membersDao;
	
	@Autowired
	AosParamsDao aosParamsDao;
	
	@Autowired
	ChargingPileDao chargingPileDao;
	@Autowired
	ChargingOrdersDao chargingOrdersDao;
	@Autowired
	MessagesDao messagesDao;
	@Autowired
	CommonCommentDao commonCommentDao;
	@Autowired
	OrdersPayDao ordersPayDao;
	
	@Autowired
	DepositListDao depositListDao;
	@Autowired
	SubscribeDao subscribeDao;
	
	private static CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
	
	
	public void demo(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();
		String is_del=qDto.getString("is_del");
		Dto pDto=Dtos.newDto("is_del", is_del);
		if(AOSUtils.isEmpty(is_del)){
			this.failMsg(odto, "手机号码不能为空");
		}else{
		List<Dto> list=new ArrayList<Dto>();
		odto.put(AOSCons.APPCODE_KEY, AOSCons.SUCCESS);
		odto.put(AOSCons.APPDATA_KEY, list);
		odto.put(AOSCons.APPMSG_KEY, AOSCons.APPMSG_SUCCESS);
		}
		httpModel.setOutMsg(AOSJson.toJson(odto));
	}
	
	/**
	 * 获取app分享地址
	 * @param httpModel
	 */
	public void getAppShareLink(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		String goods_id=qDto.getString("goods_id");
		
		Dto dto = Dtos.newDto();
		if(AOSUtils.isEmpty(goods_id)){
			this.failMsg(dto, "goods_id不能为空");
		}
		dto.put(AOSCons.APPCODE_KEY, AOSCons.SUCCESS);
		dto.put("link", "http://112.74.210.6:9090/xhm/share/do.jhtml?goods_id="+goods_id);
		dto.put(AOSCons.APPMSG_KEY, AOSCons.APPMSG_SUCCESS);
		httpModel.setOutMsg(AOSJson.toJson(dto));
	}
	
	/**
	 * 获取广告列表
	 * @param httpModel
	 */
	public void getAdvertList(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto pDto=Dtos.newDto("is_del", SystemCons.IS.NO);
		pDto.put("status", SystemCons.IS.NO);
		List<AdvertPO> list=advertDao.list(pDto);
		
		List<AdvertDTO> list_=new ArrayList<AdvertDTO>();
		for(AdvertPO advertPO :list){
			AdvertDTO advertDTO=new AdvertDTO();
			AOSUtils.copyProperties(advertPO, advertDTO);
			list_.add(advertDTO);
		}
		Dto dto = Dtos.newDto();
		dto.put(AOSCons.APPCODE_KEY, AOSCons.SUCCESS);
		dto.put(AOSCons.APPDATA_KEY, list_);
		dto.put(AOSCons.APPMSG_KEY, AOSCons.APPMSG_SUCCESS);
		httpModel.setOutMsg(AOSJson.toJson(dto));
	}
	/**
	 * 新增广告点击
	 * @param httpModel
	 */
	public void addAdvertTraffic(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto dto = Dtos.newDto();
		String advert_id=qDto.getString("advert_id");
		String at_type=qDto.getString("at_type");
		if(AOSUtils.isEmpty(advert_id)){
			this.failMsg(dto, "advert_id 值不能为空");
		}else if(AOSUtils.isEmpty(at_type)){
			this.failMsg(dto, "at_type 值不能为空");
		}else{
			if(null==advertDao.selectByKey(advert_id)){
				this.failMsg(dto, "advert_id的值不存在");
			}else{
				AdvertTrafficPO advertTrafficPO = new AdvertTrafficPO();
				advertTrafficPO.setAt_id(AOSId.appId(SystemCons.ID.SYSTEM));
				advertTrafficPO.setCreate_date(AOSUtils.getDateTime());//
				advertTrafficPO.setAt_type(at_type);
				advertTrafficPO.setAt_mun(1);
				advertTrafficPO.setAd_id(advert_id);
				advertTrafficDao.insert(advertTrafficPO);
				dto.put(AOSCons.APPCODE_KEY, AOSCons.SUCCESS);
				dto.put(AOSCons.APPMSG_KEY, AOSCons.APPMSG_SUCCESS);
			}
		}
		httpModel.setOutMsg(AOSJson.toJson(dto));
	}
	
	/**
	 * 获取验证码
	 * @param httpModel
	 */
	public void getSmsCode(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto dto = Dtos.newDto();
		String mobile=qDto.getString("mobile");
		String type=qDto.getString("type");
		if(AOSUtils.isEmpty(mobile)){
			this.failMsg(dto, "手机号码不能为空");
		}else if(AOSUtils.isEmpty(type)){
			this.failMsg(dto, "type不能为空");
		}else{
			String smsCode=AOSUtils.createRandomVcode();
			String sessionId=httpModel.getRequest().getSession().getId();
			cacheMasterDataService.cacheSMSCode(sessionId, smsCode);
			//发送短信，已实现
			saveLogs("getSmsCode:smsCode-------------->>"+smsCode+",sessionId---------------->>"+sessionId,null);
			dto.put(AOSCons.APPCODE_KEY, AOSCons.SUCCESS);
			dto.put("smsSessionId", sessionId);
			dto.put("smsCode", smsCode);//暂时未去掉
			this.sendSms(mobile, "1", smsCode);
			dto.put(AOSCons.APPMSG_KEY, AOSCons.APPMSG_SUCCESS);
		}
		httpModel.setOutMsg(AOSJson.toJson(dto));
	}
	/*
	 * 获取区县列表
	 */
	public void getCountyList(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();
		String city_id=qDto.getString("city_id");
		Dto pDto=Dtos.newDto("region_code", city_id);
		if(AOSUtils.isEmpty(city_id)){
			this.failMsg(odto, "city_id 值不能为空");
		}else{
		RegionPO regionPO_=regionDao.selectOne(pDto);
		if(null==regionPO_){
			this.failMsg(odto, "未查询到数据");
		}else{
			Dto newDto=Dtos.newDto("parent_id", regionPO_.getRegion_id());
			List<RegionPO> list=regionDao.list(newDto);
			List<RegionDTO> list_=new ArrayList<RegionDTO>();
			for(RegionPO regionPO :list){
				RegionDTO regionDTO=new RegionDTO();
				AOSUtils.copyProperties(regionPO, regionDTO);
				list_.add(regionDTO);
			}
			odto.put(AOSCons.APPCODE_KEY, AOSCons.SUCCESS);
			odto.put(AOSCons.APPDATA_KEY, list_);
			odto.put(AOSCons.APPMSG_KEY, AOSCons.APPMSG_SUCCESS);
		}
		}
		httpModel.setOutMsg(AOSJson.toJson(odto));
	}
	/*
	 * 获取市列表
	 */
	public void getCityList(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();
		String province_id=qDto.getString("province_id");
		Dto pDto=Dtos.newDto("region_code", province_id);
		if(AOSUtils.isEmpty(province_id)){
			this.failMsg(odto, "province_id 值不能为空");
		}else{
			RegionPO regionPO_=regionDao.selectOne(pDto);
			if(null==regionPO_){
				this.failMsg(odto, "未查询到数据");
			}else{
				Dto newDto=Dtos.newDto("parent_id", regionPO_.getRegion_id());
				List<RegionPO> list=regionDao.list(newDto);
				List<RegionDTO> list_=new ArrayList<RegionDTO>();
				for(RegionPO regionPO :list){
					RegionDTO RegionDTO=new RegionDTO();
					AOSUtils.copyProperties(regionPO, RegionDTO);
					list_.add(RegionDTO);
				}
				odto.put(AOSCons.APPCODE_KEY, AOSCons.SUCCESS);
				odto.put(AOSCons.APPDATA_KEY, list_);
				odto.put(AOSCons.APPMSG_KEY, AOSCons.APPMSG_SUCCESS);
			}
		
		}
		httpModel.setOutMsg(AOSJson.toJson(odto));
	}
	/*
	 * 获取省份列表
	 */
	public void getProvinceList(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();
		Dto pDto=Dtos.newDto("parent_id", 1);
		List<RegionPO> list=regionDao.list(pDto);
		List<RegionDTO> list_=new ArrayList<RegionDTO>();
		for(RegionPO regionPO :list){
			RegionDTO RegionDTO=new RegionDTO();
			AOSUtils.copyProperties(regionPO, RegionDTO);
			list_.add(RegionDTO);
		}
		odto.put(AOSCons.APPCODE_KEY, AOSCons.SUCCESS);
		odto.put(AOSCons.APPDATA_KEY, list_);
		odto.put(AOSCons.APPMSG_KEY, AOSCons.APPMSG_SUCCESS);
		httpModel.setOutMsg(AOSJson.toJson(odto));
	}
	/*
	 * 获取文章  0:帮助，1：关于我们，2：用户协议，3押金说明，4：充值协议
	 */
	public void getArticle(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();
		String type=qDto.getString("type");
		if(AOSUtils.isEmpty(type)){
			this.failMsg(odto, "type值不能为空");
		}else{
			String atricle_id="111111";
			if(type.equals("0")){
				atricle_id="222222";
			}else if(type.equals("1")){
				atricle_id="111111";
			}else if(type.equals("2")){
				atricle_id="333333";
			}else if(type.equals("3")){
				atricle_id="444444";
			}else if(type.equals("4")){
				atricle_id="555555";
			}
			ArticlePO articlePO=articleDao.selectByKey(atricle_id);
			Map<String,String> map=new HashMap<String,String>();
			map.put("title", articlePO.getTitle());
			map.put("content", articlePO.getContent().replaceAll("\"", "\'"));
			odto.put(AOSCons.APPCODE_KEY, AOSCons.SUCCESS);
			odto.put(AOSCons.APPDATA_KEY, map);
			odto.put(AOSCons.APPMSG_KEY, AOSCons.APPMSG_SUCCESS);
			
		}
		httpModel.setOutMsg(AOSJson.toJson(odto));
		
	}
	/**
	 * 平台会员押金金额
	 * @param httpModel
	 */
	public void getDepositAmt(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();
		Map<String,String> map=new HashMap<String,String>();
		map.put("deposit_amt", AOSCxt.getParam("deposit_amt"));
		odto.put(AOSCons.APPCODE_KEY, AOSCons.SUCCESS);
		odto.put(AOSCons.APPDATA_KEY, map);
		odto.put(AOSCons.APPMSG_KEY, AOSCons.APPMSG_SUCCESS);
		httpModel.setOutMsg(AOSJson.toJson(odto));
		
	}
	/**
	 * 获取充值奖励说明
	 * @param httpModel
	 */
	public void getRewardInstructions(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();
		Dto pDto_=Dtos.newDto("is_del", "0");//
		pDto_.put("status_", "1");
		pDto_.put("type_", "1");
		List<ActivityPO> list=activityDao.list(pDto_);
		List<String> array=new ArrayList<String>();
		if(null!=list&&list.size()>0){
			ActivityPO activityPO=list.get(0);
			if(null!=activityPO){
				List<ActivityRulePO> activityRuleList=activityRuleDao.list(Dtos.newDto("activity_id", activityPO.getActivity_id()));
				for(ActivityRulePO activityRulePO:activityRuleList){
					array.add(activityRulePO.getDesc_());
				}
			}
		}
		String remark="";
		for(int i=array.size()-1;i>=0;i--){
			if(null!=array.get(i)&&array.get(i).length()>0){
				remark+=array.get(i)+",";
			}
			
		}
		AosParamsPO aos_paramsOldPO1 = aosParamsDao.selectByKey("1707021816011097");
		AosParamsPO aos_paramsOldPO2 = aosParamsDao.selectByKey("1707021816461098");
		AosParamsPO aos_paramsOldPO3 = aosParamsDao.selectByKey("1707021817171099");
		remark+=aos_paramsOldPO1.getRemark_()+","+aos_paramsOldPO2.getRemark_()+","+aos_paramsOldPO3.getRemark_()+",不同会员可享受不同折扣优惠。";
		odto.put(AOSCons.APPCODE_KEY, AOSCons.SUCCESS);
		odto.put(AOSCons.APPDATA_KEY, remark);
		odto.put(AOSCons.APPMSG_KEY, AOSCons.APPMSG_SUCCESS);
		httpModel.setOutMsg(AOSJson.toJson(odto));
		
	}
	
	/*
	 * 获得活动列表
	 */
	public void getActivityList(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();
		Dto pDto=Dtos.newDto("is_del", "0");//
		pDto.put("status_", "1");
		List<ActivityPO> list=activityDao.list(pDto);
		List<ActivityDTO> list_=new ArrayList<ActivityDTO>();
		for(ActivityPO activityPO :list){
			ActivityDTO activityDTO=new ActivityDTO();
			AOSUtils.copyProperties(activityPO, activityDTO);
			list_.add(activityDTO);
		}
		
		odto.put(AOSCons.APPCODE_KEY, AOSCons.SUCCESS);
		odto.put(AOSCons.APPDATA_KEY, list_);
		odto.put(AOSCons.APPMSG_KEY, AOSCons.APPMSG_SUCCESS);
		httpModel.setOutMsg(AOSJson.toJson(odto));
	}
	
	
	
	
	
	
	
	
	
	/**
	 * 获取代金券列表	 
	 */
	public void getVoucherList(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();
		String juid=qDto.getString("juid");
		int limit=-1;
		int start=-1;
		if(!qDto.containsKey("juid")||AOSUtils.isEmpty(juid)){
			this.failMsg(odto, "juid不能为空");
		}else if(!qDto.containsKey("limit")){
			this.failMsg(odto, "limit不能为空");
		}else if(!qDto.containsKey("start")){
			this.failMsg(odto, "start不能为空");
		}else{
			try {
				limit=qDto.getInteger("limit");
			} catch (Exception e) {
				
			}
			try {
				start=qDto.getInteger("start");
			} catch (Exception e) {
				
			}
			UserModel  userModel=cacheUserDataService.getUserModel(juid);
			if(null==userModel){
				this.failMsg(odto, "请先登录再操作!");
			}else{
				Dto pDto=Dtos.newDto("dealer_id",userModel.getId_());
				pDto.put("direction", "0");
				pDto.put("is_del", "0");
				if(limit==-1&&start==-1){
					int rows=voucherDao.rows(pDto);
					pDto.put("limit", rows);
					pDto.put("start", 0);
				}else{
					pDto.put("limit", limit);
					pDto.put("start", start);
				}
				List<Dto> voucherListDtos = sqlDao.list("Voucher.apiListVouchersPage", pDto);
				odto.put(AOSCons.APPCODE_KEY, AOSCons.SUCCESS);
				odto.put(AOSCons.APPDATA_KEY, AOSJson.getPageGrid(voucherListDtos, pDto.getPageTotal()));
				odto.put(AOSCons.APPMSG_KEY, AOSCons.APPMSG_SUCCESS);
			}
		}
		httpModel.setOutMsg(AOSJson.toJson(odto));
	}
	
	
	
	
	
	/**
	 * 会员注册
	 */
	public void registerUser(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();
		
		String account=qDto.getString("account");
		String password=qDto.getString("password");
		String sms_code=qDto.getString("sms_code");
		String smsSessionId=qDto.getString("smsSessionId");
		if(!qDto.containsKey("account")||AOSUtils.isEmpty(account)){
			this.failMsg(odto, "account不能为空");
		}else if(!qDto.containsKey("password")||AOSUtils.isEmpty(password)){
			this.failMsg(odto, "password不能为空");
		}else if(!qDto.containsKey("sms_code")||AOSUtils.isEmpty(sms_code)){
			this.failMsg(odto, "sms_code不能为空");
		}else if(!qDto.containsKey("smsSessionId")||AOSUtils.isEmpty(smsSessionId)){
			this.failMsg(odto, "smsSessionId不能为空");
		}else{
			Dto pDto=Dtos.newDto("account_", account);
			pDto.put("is_del_", "0");
			pDto.put("type_", "2");
			AosUserPO aosUserPO=aosUserDao.selectOne(pDto);
			String smsCode = cacheMasterDataService.getSMSCode(smsSessionId);
			if(null!=aosUserPO){
				this.failMsg(odto, "当前手机号已存在，请重新输入。");
			}else if (AOSUtils.isEmpty(smsCode)) {
				this.failMsg(odto, "验证码已失效!");
			} else if (!smsCode.equals(sms_code)) {
				this.failMsg(odto, "验证码不正确!");
			}else{
				String password_ = AOSCodec.password(password);
				MembersPO membersPO=new MembersPO();
				membersPO.setType_("2");
				membersPO.setId_(AOSId.appId(SystemCons.ID.SYSTEM));
				membersPO.setAccount_(account);
				membersPO.setPassword_(password_);
				membersPO.setCreate_time_(AOSUtils.getDateTime());
				membersPO.setStatus_("1");
				membersPO.setIs_del_("0");
				membersDao.insert(membersPO);
				//注册送优惠券
				Dto pDto_=Dtos.newDto("is_del", "0");//
				pDto_.put("status_", "1");
				pDto_.put("type_", "3");
				List<ActivityPO> list=activityDao.list(pDto_);
				if(null!=list&&list.size()>0){
					ActivityPO activityPO=list.get(0);
					if(null!=activityPO){
						for(int i=0;i<activityPO.getAr_num().intValue();i++){
							VoucherPO voucherPO=new VoucherPO();
							voucherPO.setActivity_id(activityPO.getActivity_id());
							//voucherPO.setOrder_id(order_id);
							//voucherPO.setCond_value(activityRulePO.getCond_value());
							voucherPO.setIs_del("0");
							voucherPO.setDealer_id(membersPO.getId_());
							voucherPO.setDirection("0");
							voucherPO.setVoucher_amt(activityPO.getAmount());
							voucherPO.setVoucher_id(AOSId.appId(SystemCons.ID.SYSTEM));
							voucherPO.setCreate_date(AOSUtils.getDateTime());
							voucherPO.setEffec_date(AOSUtils.dateAdd(AOSUtils.getCurDayTimestamp(), Calendar.MONTH, Integer.valueOf(AOSCxt.getParam("voucherValidity"))));
							voucherDao.insert(voucherPO);
						}
					}
				}
				odto.put(AOSCons.APPCODE_KEY, AOSCons.SUCCESS);
				odto.put("uid", membersPO.getId_());
				odto.put(AOSCons.APPMSG_KEY, AOSCons.APPMSG_SUCCESS);
			}
		}
		httpModel.setOutMsg(AOSJson.toJson(odto));
	}
	
	/**
	 * 会员登录
	 */
	/*
	 * public void loginUser(HttpModel httpModel) { Dto qDto = httpModel.getInDto();
	 * Dto odto = Dtos.newDto();
	 * 
	 * String account=qDto.getString("account"); String
	 * password=qDto.getString("password");
	 * if(!qDto.containsKey("account")||AOSUtils.isEmpty(account)){
	 * this.failMsg(odto, "account不能为空"); }else
	 * if(!qDto.containsKey("password")||AOSUtils.isEmpty(password)){
	 * this.failMsg(odto, "password不能为空"); }else{ Dto pDto=Dtos.newDto("account_",
	 * account); pDto.put("is_del_", "0"); pDto.put("type_", "2"); AosUserPO
	 * aosUserPO=aosUserDao.selectOne(pDto); String password_ =
	 * AOSCodec.password(password); if(null==aosUserPO){ this.failMsg(odto,
	 * "手机号输入错误或已被删除，请重新输入。"); }else if(!StringUtils.equals(password_,
	 * aosUserPO.getPassword_())){ this.failMsg(odto, "密码不正确，请重新输入"); }else{
	 * 
	 * if(qDto.containsKey("registration_id ")){
	 * aosUserPO_.setRegistration_id(qDto.getString("registration_id ")); } }
	 * aosUserPO_.setRegistration_type(qDto.getString("registration_type"));
	 * aosUserDao.updateByKey(aosUserPO_); AOSUtils.copyProperties(aosUserPO,
	 * userModel); userModel.setJuid(juid);
	 * cacheUserDataService.cacheUserModel(userModel); odto.put(AOSCons.APPCODE_KEY,
	 * AOSCons.SUCCESS); odto.put("juid", juid); odto.put("uid",
	 * aosUserPO_.getId_()); odto.put(AOSCons.APPMSG_KEY, AOSCons.APPMSG_SUCCESS); }
	 * } httpModel.setOutMsg(AOSJson.toJson(odto)); }
	 */

	public void getDeviceList(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();

		String phone = qDto.getString("phone");

		if (!qDto.containsKey("phone") || AOSUtils.isEmpty(phone)) {
			this.failMsg(odto, "phone不能");
		} else {
			odto.put("status", "1");
			odto.put("data",
				"[\r\n" + "      {\r\n" + "        \"user_id \": \"0031\",\r\n" + "        \"user_name \": \"王小乖\",\r\n" + "        \"user_address \"浙江省宁波市鄞州区浙江大学宁波理工学院\",\r\n"
					+ "        \"device_status\": \"0\"\r\n" + "\"head\": \"小马哥\"\r\n" + "\"head_phone \": \"110\"\r\n" + "\"police_station\": \"鄞州派出所\"\r\n" + "      }\r\n"
					+ "    ]");

			odto.put("msg", "共查到一条数据");
		}

		httpModel.setOutMsg(AOSJson.toJson(odto));
	}

	/**
	 * 退出账号
	 */
	public void cancelUser(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();
		
		String juid =qDto.getString("juid");
		if(!qDto.containsKey("juid")||AOSUtils.isEmpty(juid)){
			this.failMsg(odto, "juid 不能为空");
		}else{
			UserModel  userModel=cacheUserDataService.getUserModel(juid);
			if(null==userModel){
				this.failMsg(odto, "请先登录再操作!");
			}else{
				cacheUserDataService.logout(juid);
				odto.put(AOSCons.APPCODE_KEY, AOSCons.SUCCESS);
				odto.put(AOSCons.APPMSG_KEY, AOSCons.APPMSG_SUCCESS);
			}	
		}
		httpModel.setOutMsg(AOSJson.toJson(odto));
	}
	/**
	 * 忘记密码
	 */
	public void forgetPwd(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();
		
		String mobile=qDto.getString("mobile");
		String new_pwd=qDto.getString("new_pwd");
		String sms_code=qDto.getString("sms_code");
		String confirm_pwd=qDto.getString("confirm_pwd");
		String smsSessionId=qDto.getString("smsSessionId");
		if(!qDto.containsKey("mobile")||AOSUtils.isEmpty(mobile)){
			this.failMsg(odto, "mobile不能为空");
		}else if(!qDto.containsKey("new_pwd")||AOSUtils.isEmpty(new_pwd)){
			this.failMsg(odto, "new_pwd不能为空");
		}else if(!qDto.containsKey("sms_code")||AOSUtils.isEmpty(sms_code)){
			this.failMsg(odto, "sms_code不能为空");
		}else if(!qDto.containsKey("confirm_pwd")||AOSUtils.isEmpty(confirm_pwd)){
			this.failMsg(odto, "confirm_pwd不能为空");
		}else if(!confirm_pwd.equals(new_pwd)){
			this.failMsg(odto, "新密码与确认密码不一致");
		}else if(!qDto.containsKey("smsSessionId")||AOSUtils.isEmpty(smsSessionId)){
			this.failMsg(odto, "smsSessionId不能为空");
		}else{
			Dto pDto=Dtos.newDto("account_", mobile);
			pDto.put("is_del_", "0");
			pDto.put("type_", "2");
			AosUserPO aosUserPO=aosUserDao.selectOne(pDto);
			String password = AOSCodec.password(new_pwd);
			if(null==aosUserPO){
				this.failMsg(odto, "手机号码不存在，请重新输入。");
			}else{
				String smsCode = cacheMasterDataService.getSMSCode(smsSessionId);
				if (AOSUtils.isEmpty(smsCode)) {
					this.failMsg(odto, "验证码已失效!");
				} else if (!smsCode.equals(sms_code)) {
					this.failMsg(odto, "验证码不正确!");
				} else {
					AosUserPO aosUserPO_=new AosUserPO();
					aosUserPO_.setPassword_(password);
					aosUserPO_.setId_(aosUserPO.getId_());
					aosUserDao.updateByKey(aosUserPO_);
					odto.put(AOSCons.APPCODE_KEY, AOSCons.SUCCESS);
					odto.put(AOSCons.APPMSG_KEY, AOSCons.APPMSG_SUCCESS);
				}
			}
		}
		httpModel.setOutMsg(AOSJson.toJson(odto));
	}
	/**
	 * 修改密码
	 */
	/**public void salesUpdatePwd2(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();
		
		String juid=qDto.getString("juid");
		String new_pwd=qDto.getString("new_pwd");
		String confirm_pwd=qDto.getString("confirm_pwd");
		String old_pwd=qDto.getString("old_pwd");
		if(!qDto.containsKey("juid")||AOSUtils.isEmpty(juid)){
			this.failMsg(odto, "juid不能为空");
		}else if(!qDto.containsKey("new_pwd")||AOSUtils.isEmpty(new_pwd)){
			this.failMsg(odto, "new_pwd不能为空");
		}else if(!qDto.containsKey("confirm_pwd")||AOSUtils.isEmpty(confirm_pwd)){
			this.failMsg(odto, "confirm_pwd不能为空");
		}else if(!new_pwd.equals(confirm_pwd)){
			this.failMsg(odto, "新密码与确认密码不一致");
		}else if(!qDto.containsKey("old_pwd")||AOSUtils.isEmpty(old_pwd)){
			this.failMsg(odto, "old_pwd不能为空");
		}else{
			UserModel userModel=cacheUserDataService.getUserModel(juid);	
			if(null==userModel){
				this.failMsg(odto, "请先登录再操作!");
			}else{
				AosUserPO aosUserPO=aosUserDao.selectByKey(userModel.getId_());
				old_pwd = AOSCodec.password(old_pwd);
				if(!StringUtils.equals(old_pwd, aosUserPO.getPassword_())){
					this.failMsg(odto, "旧密码不正确，请重新输入");
				}else{
					AosUserPO aosUserPO_=new AosUserPO();
					aosUserPO_.setPassword_(aosUserPO.getPassword_());
					aosUserPO_.setId_(aosUserPO.getId_());
					aosUserDao.updateByKey(aosUserPO_);
					odto.put(AOSCons.APPCODE_KEY, AOSCons.SUCCESS);
					odto.put(AOSCons.APPMSG_KEY, AOSCons.APPMSG_SUCCESS);
				}
				
			
			}
			
		}
		httpModel.setOutMsg(AOSJson.toJson(odto));
	}*/
	/**
	 * 绑定手机号
	 */
	public void updateMobile(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();
		String juid=qDto.getString("juid");
		String mobile=qDto.getString("mobile");
		String sms_code=qDto.getString("sms_code");
		String smsSessionId=qDto.getString("smsSessionId");
		if(!qDto.containsKey("juid")||AOSUtils.isEmpty(juid)){
			this.failMsg(odto, "juid不能为空");
		}else if(!qDto.containsKey("sms_code")||AOSUtils.isEmpty(sms_code)){
			this.failMsg(odto, "sms_code不能为空");
		}else if(!qDto.containsKey("mobile")||AOSUtils.isEmpty(mobile)){
			this.failMsg(odto, "mobile不能为空");
		}else if(!qDto.containsKey("smsSessionId")||AOSUtils.isEmpty(smsSessionId)){
			this.failMsg(odto, "smsSessionId不能为空");
		}else{
			UserModel  userModel=cacheUserDataService.getUserModel(juid);
			if(null==userModel){
				this.failMsg(odto, "请先登录再操作!");
			}else{
				Dto pDto=Dtos.newDto("account_", mobile);
				pDto.put("is_del_", "0");
				pDto.put("type_", "2");
				AosUserPO aosUserPO=aosUserDao.selectOne(pDto);
				if(null!=aosUserPO){
					this.failMsg(odto, "当前手机号码已存在，无法绑定。");
				}else{
					String smsCode = cacheMasterDataService.getSMSCode(smsSessionId);
					if (AOSUtils.isEmpty(smsCode)) {
						this.failMsg(odto, "验证码已失效!");
					} else if (!smsCode.equals(sms_code)) {
						this.failMsg(odto, "验证码不正确!");
					} else {
						AosUserPO aosUserPO_=new AosUserPO();
						aosUserPO_.setMobile_(mobile);
						aosUserPO_.setId_(userModel.getId_());
						aosUserDao.updateByKey(aosUserPO_);
						odto.put(AOSCons.APPCODE_KEY, AOSCons.SUCCESS);
						odto.put(AOSCons.APPMSG_KEY, AOSCons.APPMSG_SUCCESS);
		
					}
				}
			}
			
		}
		httpModel.setOutMsg(AOSJson.toJson(odto));
	}
	/**
	 * 更新个人资料
	 */
	public void updateUserInfo(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();
		String juid=qDto.getString("juid");
		String nickname=qDto.getString("nickname");
		String user_name=qDto.getString("user_name");
		String image=qDto.getString("image");
		String vehicle_img=qDto.getString("vehicle_img");
		String driver_img=qDto.getString("driver_img");
		String id_card=qDto.getString("id_card");
		String vehicle_license=qDto.getString("vehicle_license");
		String driver_license=qDto.getString("driver_license");
		if(!qDto.containsKey("juid")||AOSUtils.isEmpty(juid)){
			this.failMsg(odto, "juid不能为空");
		}else if(AOSUtils.isEmpty(nickname)&&AOSUtils.isEmpty(image)){
			this.failMsg(odto, "昵称或头像不能为空");
		}else{
			UserModel  userModel=cacheUserDataService.getUserModel(juid);
			boolean flag=true;
			if(AOSUtils.isNotEmpty(id_card)){
				flag=IdcardValidator.isValidatedAllIdcard(id_card);
			}
			if(null==userModel){
				this.failMsg(odto, "请先登录再操作!");
			}else if(!flag){
				this.failMsg(odto, "身份证号码不正确!");
			}else{
				MembersPO membersPO=new MembersPO();
				membersPO.setId_(userModel.getId_());
				if(AOSUtils.isNotEmpty(nickname)){
					membersPO.setName_(nickname);
				}
				if(AOSUtils.isNotEmpty(user_name)){
					membersPO.setUser_name(user_name);
				}
				if(AOSUtils.isNotEmpty(id_card)){
					membersPO.setId_card(id_card);
				}
				if(AOSUtils.isNotEmpty(vehicle_license)){
					membersPO.setVehicle_license(vehicle_license);
				}
				if(AOSUtils.isNotEmpty(driver_license)){
					membersPO.setDriver_license(driver_license);
				}
				if(AOSUtils.isNotEmpty(image)){
					Dto outDto = Dtos.newDto();
					outDto=this.base64ToFile(httpModel.getRequest(), httpModel.getResponse(), image, outDto);
					if(SystemCons.SUCCESS.equals(outDto.getAppCode())){
						membersPO.setUser_img(outDto.getAppMsg().replace("\\", "/"));
					}
				}
				if(AOSUtils.isNotEmpty(vehicle_img)){
					Dto outDto = Dtos.newDto();
					outDto=this.base64ToFile(httpModel.getRequest(), httpModel.getResponse(), vehicle_img, outDto);
					if(SystemCons.SUCCESS.equals(outDto.getAppCode())){
						membersPO.setVehicle_img(outDto.getAppMsg().replace("\\", "/"));
					}
				}
				if(AOSUtils.isNotEmpty(driver_img)){
					Dto outDto = Dtos.newDto();
					outDto=this.base64ToFile(httpModel.getRequest(), httpModel.getResponse(), driver_img, outDto);
					if(SystemCons.SUCCESS.equals(outDto.getAppCode())){
						membersPO.setDriver_img(outDto.getAppMsg().replace("\\", "/"));
					}
				}
				membersDao.updateByKey(membersPO);
				odto.put(AOSCons.APPCODE_KEY, AOSCons.SUCCESS);
				odto.put(AOSCons.APPMSG_KEY, AOSCons.APPMSG_SUCCESS);
			}	
			
		}
		httpModel.setOutMsg(AOSJson.toJson(odto));
	}
	public void checkJuid(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();
		String juid=qDto.getString("juid");
		if(!qDto.containsKey("juid")||AOSUtils.isEmpty(juid)){
			this.failMsg(odto, "juid 值不能为空");
		}else{
			UserModel  userModel=cacheUserDataService.getUserModel(juid);
			if(null==userModel){
				this.failMsg(odto, "juid已失效");
			}else{
				odto.put(AOSCons.APPCODE_KEY, AOSCons.SUCCESS);
				odto.put(AOSCons.APPMSG_KEY, "juid有效");
			}
		}
		httpModel.setOutMsg(AOSJson.toJson(odto));
	}
	public void getUserInfo(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();
		String juid=qDto.getString("juid");
		if(!qDto.containsKey("juid")||AOSUtils.isEmpty(juid)){
			this.failMsg(odto, "juid 值不能为空");
		}else{
			UserModel  userModel=cacheUserDataService.getUserModel(juid);
			if(null==userModel){
				this.failMsg(odto, "请先登录再操作!");
			}else{
				MembersPO membersPO=membersDao.selectByKey(userModel.getId_());
				MembersDTO membersDTO=new MembersDTO();
				AOSUtils.copyProperties(membersPO, membersDTO);
				if("1".equals(membersDTO.getIs_cert())){
					membersDTO.setIs_cert_name("已认证");
				}else{
					membersDTO.setIs_cert_name("未认证");
				}
				membersDTO.setDeposit_status_name(cacheMasterDataService.getDicDesc("deposit_status", membersDTO.getDeposit_status()));
				membersDTO.setGold_coins_status_name(cacheMasterDataService.getDicDesc("gold_coins_status", membersDTO.getGold_coins_status()));
				if("silver".equals(membersDTO.getGrade_())){
					membersDTO.setGrade_name("银牌会员");
				}else if("gold".equals(membersDTO.getGrade_())){
					membersDTO.setGrade_name("金牌会员");
				}else if("platinum".equals(membersDTO.getGrade_())){
					membersDTO.setGrade_name("白金会员");
				}else{
					membersDTO.setGrade_name("普通会员");
				}
				Object gold_coins_amt=sqlDao.selectOne("OrdersPay.gold_coins_amt", Dtos.newDto("user_id",userModel.getId_()));
				membersDTO.setGold_coins(new BigDecimal(gold_coins_amt.toString()));
				Object deposit_amt=sqlDao.selectOne("OrdersPay.deposit_amt", Dtos.newDto("user_id",userModel.getId_()));
				membersDTO.setDeposit_amt(new BigDecimal(deposit_amt.toString()));
				membersDTO.setDeposit_amt_text(AOSCxt.getParam("deposit_amt"));
				odto.put(AOSCons.APPCODE_KEY, AOSCons.SUCCESS);
				odto.put(AOSCons.APPDATA_KEY, membersDTO);
				odto.put(AOSCons.APPMSG_KEY, AOSCons.APPMSG_SUCCESS);
			}
		
		}
		httpModel.setOutMsg(AOSJson.toJson(odto));
	}

	public void getChargingPileList(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();
		String lon=qDto.getString("lon");//经度
		String lat=qDto.getString("lat");	//纬度
		if(!qDto.containsKey("lon")||AOSUtils.isEmpty(lon)){
			this.failMsg(odto, "lon 值不能为空");
		}else if(!qDto.containsKey("lat")||AOSUtils.isEmpty(lat)){
			this.failMsg(odto, "lat 值不能为空");//113.331575,23.143232
		}else{
			Dto pDto = Dtos.newDto();
			double d_lon=113.331575d;
			double d_lat=23.143232d;
			String radius_str=AOSCxt.getParam("radius");
			int radius = AOSUtils.isEmpty(radius_str)==true?1:Integer.valueOf(radius_str);// 千米  
			try {
				d_lon=qDto.getBigDecimal("lon").doubleValue();
			} catch (Exception e) {
				this.saveLogs2("getChargingPileList接口经度("+lon+")转换出错", httpModel);
			}
			try {
				d_lat=qDto.getBigDecimal("lat").doubleValue();
			} catch (Exception e) {
				this.saveLogs2("getChargingPileList接口纬度("+lat+")转换出错", httpModel);
			}
			
			SpatialContext geo = SpatialContext.GEO;  
			Rectangle rectangle = geo.getDistCalc().calcBoxByDistFromPt(  
			        geo.makePoint(d_lon, d_lat), radius * DistanceUtils.KM_TO_DEG, geo, null); 
			double lon_minX=rectangle.getMinX();
			double lon_maxX=rectangle.getMaxX();
			double lat_minY=rectangle.getMinY();
			double lat_maxY=rectangle.getMaxY();
			System.out.println(rectangle.getMinX() + "-" + rectangle.getMaxX());// 经度范围  
			System.out.println(rectangle.getMinY() + "-" + rectangle.getMaxY());// 纬度范围
			pDto.put("limit", 100);//默认查询出100个
			pDto.put("start", 0);
			pDto.put("lon_minX", lon_minX);
			pDto.put("lon_maxX", lon_maxX);
			pDto.put("lat_minY", lat_minY);
			pDto.put("lat_maxY", lat_maxY);
			pDto.put("lon", d_lon);
			pDto.put("lat", d_lat);
			List<Dto> chargingPilesListDtos = sqlDao.list("ChargingPile.appListChargingPilesPage", pDto);
			List<Dto> newListDtos=new ArrayList<Dto>();
			SpatialContext geo_ = SpatialContext.GEO; 
			for(Dto dto:chargingPilesListDtos){
				double lon_=dto.getBigDecimal("lon").doubleValue();
				double lat_=dto.getBigDecimal("lat").doubleValue();
				double distance = geo_.calcDistance(geo_.makePoint(d_lon, d_lat), geo_.makePoint(lon_, lat_)) * DistanceUtils.DEG_TO_KM;
				if(distance<=Double.valueOf(String.valueOf(radius))){//小于1千米的充电桩
					Dto newDto = Dtos.newDto();
					newDto.put("cp_id", dto.getString("cp_id"));
					newDto.put("cp_no", dto.getString("cp_no"));
					newDto.put("supplier", dto.getString("supplier"));
					newDto.put("province", dto.getString("province"));
					newDto.put("city", dto.getString("city"));
					newDto.put("county", dto.getString("county"));
					newDto.put("addr", dto.getString("addr"));
					newDto.put("cp_type", dto.getString("cp_type"));
					List<AosDicPO> listDic_=cacheMasterDataService.getDicList("cp_type");
					for(AosDicPO aosDicPO:listDic_){
						if(dto.getString("cp_type").equals(aosDicPO.getCode_())){
							newDto.put("cp_type_name", aosDicPO.getDesc_());
							break;
						}
					}
					newDto.put("electricity", dto.getString("electricity"));
					
					BigDecimal   b   =   new   BigDecimal(distance); 
					newDto.put("distance", b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
					
					newDto.put("cp_status", dto.getString("cp_status"));
					listDic_=cacheMasterDataService.getDicList("cp_status");
					for(AosDicPO aosDicPO:listDic_){
						if(dto.getString("cp_status").equals(aosDicPO.getCode_())){
							newDto.put("cp_status_name", aosDicPO.getDesc_());
							break;
						}
					}
					newDto.put("lon", dto.getString("lon"));
					newDto.put("lat", dto.getString("lat"));
					newListDtos.add(newDto);
				}
			}
			//排序
			Collections.sort(newListDtos,new Comparator<Dto>(){  
	            @Override  
	            public int compare(Dto b1, Dto b2) {  
	                return Double.valueOf(b1.getString("distance")).compareTo(Double.valueOf(b2.getString("distance")));  
	            }  
	              
	        });
		odto.put(AOSCons.APPCODE_KEY, AOSCons.SUCCESS);
		odto.put(AOSCons.APPDATA_KEY, newListDtos);
		odto.put(AOSCons.APPMSG_KEY, AOSCons.APPMSG_SUCCESS);
		
		}
		httpModel.setOutMsg(AOSJson.toJson(odto));
	}
	public void getChargingPileListByAddrName(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();
		String addrName=qDto.getString("addrName");//地名
		String lon=qDto.getString("lon");//经度
		String lat=qDto.getString("lat");	//纬度
		if(!qDto.containsKey("addrName")||AOSUtils.isEmpty(addrName)){
			this.failMsg(odto, "addrName 值不能为空");
		}else{
			Dto pDto = Dtos.newDto();
			pDto.put("limit", 100);//默认查询出100个
			pDto.put("start", 0);
			pDto.put("addrName", addrName);
			
			List<Dto> chargingPilesListDtos = sqlDao.list("ChargingPile.appListChargingPilesPage", pDto);
			List<Dto> newListDtos=new ArrayList<Dto>();
			SpatialContext geo_ = SpatialContext.GEO;
			double d_lon=113.331575d;
			double d_lat=23.143232d;
			if((qDto.containsKey("lon"))&&qDto.containsKey("lat")){
				
				String radius_str=AOSCxt.getParam("radius");
				int radius = AOSUtils.isEmpty(radius_str)==true?1:Integer.valueOf(radius_str);// 千米  
				try {
					d_lon=qDto.getBigDecimal("lon").doubleValue();
				} catch (Exception e) {
					this.saveLogs2("getChargingPileList接口经度("+lon+")转换出错", httpModel);
				}
				try {
					d_lat=qDto.getBigDecimal("lat").doubleValue();
				} catch (Exception e) {
					this.saveLogs2("getChargingPileList接口纬度("+lat+")转换出错", httpModel);
				}
			}
			for(Dto dto:chargingPilesListDtos){
				Dto newDto = Dtos.newDto();	
				if((qDto.containsKey("lon"))&&qDto.containsKey("lat")){
						double lon_=dto.getBigDecimal("lon").doubleValue();
					    double lat_=dto.getBigDecimal("lat").doubleValue();
					    double distance = geo_.calcDistance(geo_.makePoint(d_lon, d_lat), geo_.makePoint(lon_, lat_)) * DistanceUtils.DEG_TO_KM;
					  
							BigDecimal   b   =   new   BigDecimal(distance); 
							newDto.put("distance", b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
						
					}else{
						newDto.put("distance", 0);
					}
					
					newDto.put("cp_id", dto.getString("cp_id"));
					newDto.put("cp_no", dto.getString("cp_no"));
					newDto.put("supplier", dto.getString("supplier"));
					newDto.put("province", dto.getString("province"));
					newDto.put("city", dto.getString("city"));
					newDto.put("county", dto.getString("county"));
					newDto.put("addr", dto.getString("addr"));
					newDto.put("cp_type", dto.getString("cp_type"));
					List<AosDicPO> listDic_=cacheMasterDataService.getDicList("cp_type");
					for(AosDicPO aosDicPO:listDic_){
						if(dto.getString("cp_type").equals(aosDicPO.getCode_())){
							newDto.put("cp_type_name", aosDicPO.getDesc_());
							break;
						}
					}
					newDto.put("electricity", dto.getString("electricity"));
					newDto.put("cp_status", dto.getString("cp_status"));
					listDic_=cacheMasterDataService.getDicList("cp_status");
					for(AosDicPO aosDicPO:listDic_){
						if(dto.getString("cp_status").equals(aosDicPO.getCode_())){
							newDto.put("cp_status_name", aosDicPO.getDesc_());
							break;
						}
					}
					newDto.put("lon", dto.getString("lon"));
					newDto.put("lat", dto.getString("lat"));
					newListDtos.add(newDto);
			}
			
		odto.put(AOSCons.APPCODE_KEY, AOSCons.SUCCESS);
		odto.put(AOSCons.APPDATA_KEY, newListDtos);
		odto.put(AOSCons.APPMSG_KEY, AOSCons.APPMSG_SUCCESS);
		
		}
		httpModel.setOutMsg(AOSJson.toJson(odto));
	}
	public void getChargingPileById(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();
		String cp_id=qDto.getString("cp_id");//id
		String lon=qDto.getString("lon");//经度
		String lat=qDto.getString("lat");	//纬度
		if(!qDto.containsKey("lon")||AOSUtils.isEmpty(lon)){
			this.failMsg(odto, "lon 值不能为空");
		}else if(!qDto.containsKey("lat")||AOSUtils.isEmpty(lat)){
			this.failMsg(odto, "lat 值不能为空");
		}else if(!qDto.containsKey("cp_id")||AOSUtils.isEmpty(cp_id)){
			this.failMsg(odto, "cp_id 值不能为空");
		}else{
			Dto pDto = Dtos.newDto();
			double d_lon=113.331575d;
			double d_lat=23.143232d;
			try {
				d_lon=qDto.getBigDecimal("lon").doubleValue();
			} catch (Exception e) {
				this.saveLogs2("getChargingPileById接口经度("+lon+")转换出错", httpModel);
			}
			try {
				d_lat=qDto.getBigDecimal("lat").doubleValue();
			} catch (Exception e) {
				this.saveLogs2("getChargingPileById接口纬度("+lat+")转换出错", httpModel);
			}
			ChargingPilePO chargingPilePO= chargingPileDao.selectByKey(cp_id);
			Dto newDto = Dtos.newDto();
			if(null!=chargingPilePO){
				SpatialContext geo_ = SpatialContext.GEO; 
				double lon_=Double.valueOf(chargingPilePO.getLon());
				double lat_=Double.valueOf(chargingPilePO.getLat());
				double distance = geo_.calcDistance(geo_.makePoint(d_lon, d_lat), geo_.makePoint(lon_, lat_)) * DistanceUtils.DEG_TO_KM;
					newDto.put("cp_id", chargingPilePO.getCp_id());
					newDto.put("cp_no", chargingPilePO.getCp_no());
					newDto.put("supplier", chargingPilePO.getSupplier());
					newDto.put("province", chargingPilePO.getProvince());
					newDto.put("city", chargingPilePO.getCity());
					newDto.put("county", chargingPilePO.getCounty());
					newDto.put("addr", chargingPilePO.getAddr());
					newDto.put("cp_type", chargingPilePO.getCp_type());
					List<AosDicPO> listDic_=cacheMasterDataService.getDicList("cp_type");
					for(AosDicPO aosDicPO:listDic_){
						if(chargingPilePO.getCp_type().equals(aosDicPO.getCode_())){
							newDto.put("cp_type_name", aosDicPO.getDesc_());
							break;
						}
					}
					newDto.put("electricity", chargingPilePO.getElectricity());
					BigDecimal   b   =   new   BigDecimal(distance); 
					newDto.put("distance", b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
					newDto.put("cp_status", chargingPilePO.getCp_status());
					listDic_=cacheMasterDataService.getDicList("cp_status");
					for(AosDicPO aosDicPO:listDic_){
						if(chargingPilePO.getCp_status().equals(aosDicPO.getCode_())){
							newDto.put("cp_status_name", aosDicPO.getDesc_());
							break;
						}
					}
					newDto.put("lon", chargingPilePO.getLon());
					newDto.put("lat", chargingPilePO.getLat());
			}		
			
			odto.put(AOSCons.APPCODE_KEY, AOSCons.SUCCESS);
			odto.put(AOSCons.APPDATA_KEY, newDto);
			odto.put(AOSCons.APPMSG_KEY, AOSCons.APPMSG_SUCCESS);
		
		}
			httpModel.setOutMsg(AOSJson.toJson(odto));
	}
	
	/*
	 * 获得充电记录列表
	 */
	public void getMyChargingList(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();
		String juid=qDto.getString("juid");
		String type=qDto.getString("type");
		int limit=-1;
		int start=-1;
		if(!qDto.containsKey("juid")||AOSUtils.isEmpty(juid)){
			this.failMsg(odto, "juid不能为空");
		}else if(!qDto.containsKey("limit")){
			this.failMsg(odto, "limit不能为空");
		}else if(!qDto.containsKey("start")){
			this.failMsg(odto, "start不能为空");
		}else{
			try {
				limit=qDto.getInteger("limit");
			} catch (Exception e) {
				
			}
			try {
				start=qDto.getInteger("start");
			} catch (Exception e) {
				
			}
			UserModel  userModel=cacheUserDataService.getUserModel(juid);
			if(null==userModel){
				this.failMsg(odto, "请先登录再操作!");
			}else{
				if(AOSUtils.isEmpty(type)){
					type="0";
				}
				
				Dto pDto=Dtos.newDto("user_id",userModel.getId_());
				if("1".equals(type)){//已支付
					pDto.put("status_", "2");
				}else if("-1".equals(type)){
					//pDto.put("noPay", type);
					pDto.put("status_", "1");
				}
				if(limit==-1&&start==-1){
					int rows=chargingOrdersDao.rows(pDto);
					pDto.put("limit", rows);
					pDto.put("start", 0);
				}else{
					pDto.put("limit", limit);
					pDto.put("start", start);
				}
				List<Dto> chargingOrdersDtos = sqlDao.list("ChargingOrders.listMyChargingOrdersPage", pDto);
				for(Dto dto:chargingOrdersDtos){
					
					String status_name=cacheMasterDataService.getDicDesc("order_status_", dto.getString("status_"));
					String deduction_type_name=cacheMasterDataService.getDicDesc("deduction_type", dto.getString("deduction_type"));
					dto.put("status_name", status_name);
					dto.put("deduction_type_name", deduction_type_name);
				
				}
				odto.put(AOSCons.APPCODE_KEY, AOSCons.SUCCESS);
				odto.put(AOSCons.APPDATA_KEY, AOSJson.getPageGrid(chargingOrdersDtos, pDto.getPageTotal()));
				odto.put(AOSCons.APPMSG_KEY, AOSCons.APPMSG_SUCCESS);
			}
		
		}
		httpModel.setOutMsg(AOSJson.toJson(odto));
	}
	/*
	 *获得充电记录详细
	 */
	public void getMyChargingById(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();
		String juid=qDto.getString("juid");
		if(!qDto.containsKey("juid")||AOSUtils.isEmpty(juid)){
			this.failMsg(odto, "juid不能为空");
		}else if(!qDto.containsKey("co_id")){
			this.failMsg(odto, "co_id不能为空");
		}else{
			UserModel  userModel=cacheUserDataService.getUserModel(juid);
			if(null==userModel){
				this.failMsg(odto, "请先登录再操作!");
			}else{
				Dto pDto=Dtos.newDto("user_id",userModel.getId_());
				pDto.put("co_id", qDto.getString("co_id"));
				pDto.put("limit", 1);
				pDto.put("start", 0);
				List<Dto> chargingOrdersDtos = sqlDao.list("ChargingOrders.listMyChargingOrdersPage", pDto);
				Dto dto=Dtos.newDto();
				if(null!=chargingOrdersDtos&&chargingOrdersDtos.size()>0){
					dto=chargingOrdersDtos.get(0);
					String status_name=cacheMasterDataService.getDicDesc("order_status_", dto.getString("status_"));
					String deduction_type_name=cacheMasterDataService.getDicDesc("deduction_type", dto.getString("deduction_type"));
					dto.put("status_name", status_name);
					dto.put("deduction_type_name", deduction_type_name);
				}
				
				odto.put(AOSCons.APPCODE_KEY, AOSCons.SUCCESS);
				odto.put(AOSCons.APPDATA_KEY, dto);
				odto.put(AOSCons.APPMSG_KEY, AOSCons.APPMSG_SUCCESS);
			}
		
		}
		httpModel.setOutMsg(AOSJson.toJson(odto));
	}
	public void getMyChargingOrder(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();
		String juid=qDto.getString("juid");
		if(!qDto.containsKey("juid")||AOSUtils.isEmpty(juid)){
			this.failMsg(odto, "juid不能为空");
		}else{
			UserModel  userModel=cacheUserDataService.getUserModel(juid);
			if(null==userModel){
				this.failMsg(odto, "请先登录再操作!");
			}else{
				Dto pDto=Dtos.newDto("user_id",userModel.getId_());
				List<Dto> chargingOrdersDtos=sqlDao.list("ChargingOrders.listChargingOrderssDoing", pDto);
				Dto dto=Dtos.newDto();
				if(null!=chargingOrdersDtos&&chargingOrdersDtos.size()>0){
					dto=Dtos.newDto();
					dto=chargingOrdersDtos.get(0);
					String status_name=cacheMasterDataService.getDicDesc("order_status_", dto.getString("status_"));
					String deduction_type_name=cacheMasterDataService.getDicDesc("deduction_type", dto.getString("deduction_type"));
					dto.put("status_name", status_name);
					dto.put("deduction_type_name", deduction_type_name);
				}
				
				odto.put(AOSCons.APPCODE_KEY, AOSCons.SUCCESS);
				odto.put(AOSCons.APPDATA_KEY, dto);
				odto.put(AOSCons.APPMSG_KEY, AOSCons.APPMSG_SUCCESS);
			}
		
		}
		httpModel.setOutMsg(AOSJson.toJson(odto));
	}
	
	/*
	 * 获取优惠券记录
	 */
	public void getMyVoucherList(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();
		String juid=qDto.getString("juid");
		String type=qDto.getString("type");
		int limit=-1;
		int start=-1;
		if(!qDto.containsKey("juid")||AOSUtils.isEmpty(juid)){
			this.failMsg(odto, "juid不能为空");
		}else if(!qDto.containsKey("limit")){
			this.failMsg(odto, "limit不能为空");
		}else if(!qDto.containsKey("start")){
			this.failMsg(odto, "start不能为空");
		}else{
			try {
				limit=qDto.getInteger("limit");
			} catch (Exception e) {
				
			}
			try {
				start=qDto.getInteger("start");
			} catch (Exception e) {
				
			}
			UserModel  userModel=cacheUserDataService.getUserModel(juid);
			if(null==userModel){
				this.failMsg(odto, "请先登录再操作!");
			}else{
				Dto pDto=Dtos.newDto("dealer_id",userModel.getId_());
				if(qDto.containsKey("type")&&AOSUtils.isNotEmpty(type)&&"1".equals(type)){
					pDto.put("direction", "1");
				}else if(qDto.containsKey("type")&&AOSUtils.isNotEmpty(type)&&"2".equals(type)){
					pDto.put("direction", "-1");
				}else{
					pDto.put("direction", "0");
				}
				
				if(limit==-1&&start==-1){
					int rows=voucherDao.rows(pDto);
					pDto.put("limit", rows);
					pDto.put("start", 0);
				}else{
					pDto.put("limit", limit);
					pDto.put("start", start);
				}
				List<Dto> voucherDtos = sqlDao.list("Voucher.apiListVouchersPage", pDto);
				odto.put(AOSCons.APPCODE_KEY, AOSCons.SUCCESS);
				odto.put(AOSCons.APPDATA_KEY, AOSJson.getPageGrid(voucherDtos, pDto.getPageTotal()));
				odto.put(AOSCons.APPMSG_KEY, AOSCons.APPMSG_SUCCESS);
			}
		
		}
		httpModel.setOutMsg(AOSJson.toJson(odto));
	}
	/*
	 * 获取我的消息
	 */
	public void getMyMessagesList(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();
		String juid=qDto.getString("juid");
		int limit=-1;
		int start=-1;
		if(!qDto.containsKey("juid")||AOSUtils.isEmpty(juid)){
			this.failMsg(odto, "juid不能为空");
		}else if(!qDto.containsKey("limit")){
			this.failMsg(odto, "limit不能为空");
		}else if(!qDto.containsKey("start")){
			this.failMsg(odto, "start不能为空");
		}else{
			try {
				limit=qDto.getInteger("limit");
			} catch (Exception e) {
				
			}
			try {
				start=qDto.getInteger("start");
			} catch (Exception e) {
				
			}
			UserModel  userModel=cacheUserDataService.getUserModel(juid);
			if(null==userModel){
				this.failMsg(odto, "请先登录再操作!");
			}else{
				Dto pDto=Dtos.newDto();
				if(limit==-1&&start==-1){
					int rows=messagesDao.rows(pDto);
					pDto.put("limit", rows);
					pDto.put("start", 0);
				}else{
					pDto.put("limit", limit);
					pDto.put("start", start);
				}
				List<Dto> messagesDtos = sqlDao.list("Messages.apiListMessagessPage", pDto);
				odto.put(AOSCons.APPCODE_KEY, AOSCons.SUCCESS);
				odto.put(AOSCons.APPDATA_KEY, AOSJson.getPageGrid(messagesDtos, pDto.getPageTotal()));
				odto.put(AOSCons.APPMSG_KEY, AOSCons.APPMSG_SUCCESS);
			}
		
		}
		httpModel.setOutMsg(AOSJson.toJson(odto));
	}
	
	/**
	 * 新增建议
	 */
	public void addAdvice(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();
		String juid=qDto.getString("juid");
		String comment=qDto.getString("comment");
		String image1=qDto.getString("image1");
		String image2=qDto.getString("image2");
		String image3=qDto.getString("image3");
		String image4=qDto.getString("image4");
		String image5=qDto.getString("image5");
		String image6=qDto.getString("image6");
		if(!qDto.containsKey("juid")||AOSUtils.isEmpty(juid)){
			this.failMsg(odto, "juid不能为空");
		}else if(!qDto.containsKey("comment")||AOSUtils.isEmpty(comment)){
			this.failMsg(odto, "comment不能为空");
		}else{
			UserModel  userModel=cacheUserDataService.getUserModel(juid);
			if(null==userModel){
				this.failMsg(odto, "请先登录再操作!");
			}else{
				CommonCommentPO commonCommentPO = new CommonCommentPO();
				commonCommentPO.setCc_id(AOSId.appId(SystemCons.ID.SYSTEM));
				commonCommentPO.setCreate_date(AOSUtils.getDateTime());
				commonCommentPO.setIs_del("0");
				commonCommentPO.setUser_id(userModel.getId_());
				commonCommentPO.setStatus_("0");
				commonCommentPO.setComment_(comment);
				if(AOSUtils.isNotEmpty(image1)){
					Dto outDto = Dtos.newDto();
					outDto=this.base64ToFile(httpModel.getRequest(), httpModel.getResponse(), image1, outDto);
					if(SystemCons.SUCCESS.equals(outDto.getAppCode())){
						commonCommentPO.setImg_url1(outDto.getAppMsg().replace("\\", "/"));
					}
				}
				if(AOSUtils.isNotEmpty(image2)){
					Dto outDto = Dtos.newDto();
					outDto=this.base64ToFile(httpModel.getRequest(), httpModel.getResponse(), image2, outDto);
					if(SystemCons.SUCCESS.equals(outDto.getAppCode())){
						commonCommentPO.setImg_url2(outDto.getAppMsg().replace("\\", "/"));
					}
				}
				if(AOSUtils.isNotEmpty(image3)){
					Dto outDto = Dtos.newDto();
					outDto=this.base64ToFile(httpModel.getRequest(), httpModel.getResponse(), image3, outDto);
					if(SystemCons.SUCCESS.equals(outDto.getAppCode())){
						commonCommentPO.setImg_url3(outDto.getAppMsg().replace("\\", "/"));
					}
				}
				if(AOSUtils.isNotEmpty(image4)){
					Dto outDto = Dtos.newDto();
					outDto=this.base64ToFile(httpModel.getRequest(), httpModel.getResponse(), image4, outDto);
					if(SystemCons.SUCCESS.equals(outDto.getAppCode())){
						commonCommentPO.setImg_url4(outDto.getAppMsg().replace("\\", "/"));
					}
				}
				if(AOSUtils.isNotEmpty(image5)){
					Dto outDto = Dtos.newDto();
					outDto=this.base64ToFile(httpModel.getRequest(), httpModel.getResponse(), image5, outDto);
					if(SystemCons.SUCCESS.equals(outDto.getAppCode())){
						commonCommentPO.setImg_url5(outDto.getAppMsg().replace("\\", "/"));
					}
				}
				if(AOSUtils.isNotEmpty(image6)){
					Dto outDto = Dtos.newDto();
					outDto=this.base64ToFile(httpModel.getRequest(), httpModel.getResponse(), image6, outDto);
					if(SystemCons.SUCCESS.equals(outDto.getAppCode())){
						commonCommentPO.setImg_url6(outDto.getAppMsg().replace("\\", "/"));
					}
				}
				commonCommentDao.insert(commonCommentPO);
				odto.put(AOSCons.APPCODE_KEY, AOSCons.SUCCESS);
				odto.put(AOSCons.APPMSG_KEY, AOSCons.APPMSG_SUCCESS);
			}
		}
		httpModel.setOutMsg(AOSJson.toJson(odto));
	}
	
	/**
	 * 预约充电
	 */
	public void addSubscribe(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();
		String juid=qDto.getString("juid");
		String cp_id=qDto.getString("cp_id");
		if(!qDto.containsKey("juid")||AOSUtils.isEmpty(juid)){
			this.failMsg(odto, "juid不能为空");
		}else if(!qDto.containsKey("cp_id")||AOSUtils.isEmpty(cp_id)){
			this.failMsg(odto, "cp_id不能为空");
		}else{
			UserModel  userModel=cacheUserDataService.getUserModel(juid);
			ChargingPilePO  chargingPilePO=chargingPileDao.selectByKey(cp_id);
			if(null==userModel){
				this.failMsg(odto, "请先登录再操作!");
			}else if(null==chargingPilePO){	
				this.failMsg(odto, "当前充电桩不存在");
			}else if(null!=chargingPilePO&&chargingPilePO.getCp_status().equals("2")){	
				this.failMsg(odto, "当前充电桩已被占用");
			}else if(null!=chargingPilePO&&chargingPilePO.getCp_status().equals("3")){	
				this.failMsg(odto, "当前充电桩故障");
			}else{
				//更新已过期预约记录的状态
				Dto pDto_=Dtos.newDto("is_del","0");
				pDto_.put("status", "0");
				pDto_.put("user_id", userModel.getId_());
				pDto_.put("overdue","true");
				List<SubscribePO> list_= subscribeDao.list(pDto_);
				for(SubscribePO po:list_){
					SubscribePO po_=new SubscribePO();
					po_.setS_id(po.getS_id());
					po_.setStatus("2");
					subscribeDao.updateByKey(po_);
				}
				
				Dto pDto=Dtos.newDto("is_del","0");
				pDto.put("status", "0");
				pDto.put("cp_id", cp_id);
				int rows=subscribeDao.rows(pDto);
				pDto.put("user_id", userModel.getId_());
				int rows2=subscribeDao.rows(pDto);
				if(rows>0){
					this.failMsg(odto, "当前充电桩已被其他人预约");
				}else if(rows2>0){
					this.failMsg(odto, "您已预约,请勿重复预约。");
				}else{
					SubscribePO subscribePO=new SubscribePO();
					String subscribe_date=AOSCxt.getParam("subscribe_date");
					int subscribe = AOSUtils.isEmpty(subscribe_date)==true?15:Integer.valueOf(subscribe_date);// 分钟
					subscribePO.setS_id(AOSId.appId(SystemCons.ID.SYSTEM));
					subscribePO.setCreate_date(AOSUtils.getDateTime());
					subscribePO.setStart_date(AOSUtils.getDateTime());
					subscribePO.setEnd_date(AOSUtils.dateAdd(new Date(), Calendar.MINUTE, subscribe));
					subscribePO.setUser_id(userModel.getId_());
					subscribePO.setCp_id(cp_id);
					subscribeDao.insert(subscribePO);
					odto.put(AOSCons.APPCODE_KEY, AOSCons.SUCCESS);
					odto.put(AOSCons.APPMSG_KEY, AOSCons.APPMSG_SUCCESS);
				}
			}
		}
		httpModel.setOutMsg(AOSJson.toJson(odto));
	}
	/**
	 * 获取预约充电信息
	 */
	public void getSubscribe(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();
		String juid=qDto.getString("juid");
		String lon=qDto.getString("lon");//经度
		String lat=qDto.getString("lat");	//纬度
		if(!qDto.containsKey("juid")||AOSUtils.isEmpty(juid)){
			this.failMsg(odto, "juid不能为空");
		}else if(!qDto.containsKey("lon")||AOSUtils.isEmpty(lon)){
			this.failMsg(odto, "lon 值不能为空");
		}else if(!qDto.containsKey("lat")||AOSUtils.isEmpty(lat)){
			this.failMsg(odto, "lat 值不能为空");
		}else{
			UserModel  userModel=cacheUserDataService.getUserModel(juid);
			if(null==userModel){
				this.failMsg(odto, "请先登录再操作!");
			}else{
				Dto pDto=Dtos.newDto("is_del","0");
				pDto.put("status", "0");
				pDto.put("user_id", userModel.getId_());
				
				Dto pDto_=Dtos.newDto("is_del","0");
				pDto_.put("status", "0");
				pDto_.put("user_id", userModel.getId_());
				pDto_.put("overdue","true");
				List<SubscribePO> list_= subscribeDao.list(pDto_);
				for(SubscribePO po:list_){
					SubscribePO po_=new SubscribePO();
					po_.setS_id(po.getS_id());
					po_.setStatus("2");
					subscribeDao.updateByKey(po_);
				}
				
				List<SubscribePO> list= subscribeDao.list(pDto);
				if(null!=list&&list.size()>0){
					SubscribePO po=list.get(0);
					Map<String,Object> map=new HashMap<String,Object>();
					map.put("s_id", po.getS_id());
					map.put("cp_id", po.getCp_id());
					
					pDto = Dtos.newDto();
					double d_lon=113.331575d;
					double d_lat=23.143232d;
					try {
						d_lon=qDto.getBigDecimal("lon").doubleValue();
					} catch (Exception e) {
						this.saveLogs2("getSubscribe接口经度("+lon+")转换出错", httpModel);
					}
					try {
						d_lat=qDto.getBigDecimal("lat").doubleValue();
					} catch (Exception e) {
						this.saveLogs2("getSubscribe接口纬度("+lat+")转换出错", httpModel);
					}
					ChargingPilePO chargingPilePO= chargingPileDao.selectByKey(po.getCp_id());
					Dto newDto = Dtos.newDto();
					if(null!=chargingPilePO){
						SpatialContext geo_ = SpatialContext.GEO; 
						double lon_=Double.valueOf(chargingPilePO.getLon());
						double lat_=Double.valueOf(chargingPilePO.getLat());
						double distance = geo_.calcDistance(geo_.makePoint(d_lon, d_lat), geo_.makePoint(lon_, lat_)) * DistanceUtils.DEG_TO_KM;
						    map.put("cp_id", chargingPilePO.getCp_id());
							map.put("cp_no", chargingPilePO.getCp_no());
							map.put("supplier", chargingPilePO.getSupplier());
							map.put("province", chargingPilePO.getProvince());
							map.put("city", chargingPilePO.getCity());
							map.put("county", chargingPilePO.getCounty());
							map.put("addr", chargingPilePO.getAddr());
							map.put("cp_type", chargingPilePO.getCp_type());
							List<AosDicPO> listDic_=cacheMasterDataService.getDicList("cp_type");
							for(AosDicPO aosDicPO:listDic_){
								if(chargingPilePO.getCp_type().equals(aosDicPO.getCode_())){
									map.put("cp_type_name", aosDicPO.getDesc_());
									break;
								}
							}
							map.put("electricity", chargingPilePO.getElectricity());
							BigDecimal   b   =   new   BigDecimal(distance); 
							map.put("distance", b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
							map.put("cp_status", chargingPilePO.getCp_status());
							listDic_=cacheMasterDataService.getDicList("cp_status");
							for(AosDicPO aosDicPO:listDic_){
								if(chargingPilePO.getCp_status().equals(aosDicPO.getCode_())){
									map.put("cp_status_name", aosDicPO.getDesc_());
									break;
								}
							}
							map.put("lon", chargingPilePO.getLon());
							map.put("lat", chargingPilePO.getLat());
					}
					map.put("start_date", AOSUtils.date2String(po.getStart_date(), "yyyy-MM-dd HH:mm:ss"));
					map.put("end_date", AOSUtils.date2String(po.getEnd_date(), "yyyy-MM-dd HH:mm:ss"));
					odto.put(AOSCons.APPDATA_KEY, map);
				}else{
					odto.put(AOSCons.APPDATA_KEY, new HashMap<String,Object>());
				}
					odto.put(AOSCons.APPCODE_KEY, AOSCons.SUCCESS);
					odto.put(AOSCons.APPMSG_KEY, AOSCons.APPMSG_SUCCESS);
			}
		}
		httpModel.setOutMsg(AOSJson.toJson(odto));
	}
	/**
	 * 检测充电桩是否被其他用户预约
	 */
	public void checkSubscribe(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();
		String juid=qDto.getString("juid");
		String cp_id=qDto.getString("cp_id");
		if(!qDto.containsKey("juid")||AOSUtils.isEmpty(juid)){
			this.failMsg(odto, "juid不能为空");
		}else if(!qDto.containsKey("cp_id")||AOSUtils.isEmpty(cp_id)){
			this.failMsg(odto, "cp_id不能为空");
		}else{
			UserModel  userModel=cacheUserDataService.getUserModel(juid);
			if(null==userModel){
				this.failMsg(odto, "请先登录再操作!");
			}else{
				Dto pDto=Dtos.newDto("is_del","0");
				pDto.put("status", "0");
				pDto.put("not_user_id", userModel.getId_());
				int rows= subscribeDao.rows(pDto);
				if(rows>0){
					odto.put(AOSCons.APPDATA_KEY, true);
				}else{
					odto.put(AOSCons.APPDATA_KEY, false);
				}
					odto.put(AOSCons.APPCODE_KEY, AOSCons.SUCCESS);
					odto.put(AOSCons.APPMSG_KEY, AOSCons.APPMSG_SUCCESS);
			}
		}
		httpModel.setOutMsg(AOSJson.toJson(odto));
	}
	/**
	 * 取消预约充电
	 */
	public void cancelSubscribe(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();
		String juid=qDto.getString("juid");
		if(!qDto.containsKey("juid")||AOSUtils.isEmpty(juid)){
			this.failMsg(odto, "juid不能为空");
		}else{
			UserModel  userModel=cacheUserDataService.getUserModel(juid);
			if(null==userModel){
				this.failMsg(odto, "请先登录再操作!");
			}else{
				Dto pDto=Dtos.newDto("is_del","0");
				pDto.put("status", "0");
				pDto.put("user_id", userModel.getId_());
				List<SubscribePO> list= subscribeDao.list(pDto);
				for(SubscribePO po:list){
					SubscribePO subscribePO=new SubscribePO();
					subscribePO.setS_id(po.getS_id());
					subscribePO.setStatus("2");
					subscribeDao.updateByKey(subscribePO);
				}
				odto.put(AOSCons.APPCODE_KEY, AOSCons.SUCCESS);
				odto.put(AOSCons.APPMSG_KEY, AOSCons.APPMSG_SUCCESS);
			}
		}
		httpModel.setOutMsg(AOSJson.toJson(odto));
	}
	/*
	 * 获取我的建议列表
	 */
	public void getMyAdviceList(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();
		String juid=qDto.getString("juid");
		int limit=-1;
		int start=-1;
		if(!qDto.containsKey("juid")||AOSUtils.isEmpty(juid)){
			this.failMsg(odto, "juid不能为空");
		}else if(!qDto.containsKey("limit")){
			this.failMsg(odto, "limit不能为空");
		}else if(!qDto.containsKey("start")){
			this.failMsg(odto, "start不能为空");
		}else{
			try {
				limit=qDto.getInteger("limit");
			} catch (Exception e) {
				
			}
			try {
				start=qDto.getInteger("start");
			} catch (Exception e) {
				
			}
			UserModel  userModel=cacheUserDataService.getUserModel(juid);
			if(null==userModel){
				this.failMsg(odto, "请先登录再操作!");
			}else{
				Dto pDto=Dtos.newDto("user_id",userModel.getId_());
				pDto.put("is_del", "0");
				if(limit==-1&&start==-1){
					int rows=commonCommentDao.rows(pDto);
					pDto.put("limit", rows);
					pDto.put("start", 0);
				}else{
					pDto.put("limit", limit);
					pDto.put("start", start);
				}
				List<Dto> adviceDtos = sqlDao.list("CommonComment.apiListCommonCommentsPage", pDto);
				odto.put(AOSCons.APPCODE_KEY, AOSCons.SUCCESS);
				odto.put(AOSCons.APPDATA_KEY, AOSJson.getPageGrid(adviceDtos, pDto.getPageTotal()));
				odto.put(AOSCons.APPMSG_KEY, AOSCons.APPMSG_SUCCESS);
			}
		
		}
		httpModel.setOutMsg(AOSJson.toJson(odto));
	}
	
	/*
	 * 获取押金申请退款记录
	 */
	public void getApplyDepositList(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();
		String juid=qDto.getString("juid");
		int limit=-1;
		int start=-1;
		if(!qDto.containsKey("juid")||AOSUtils.isEmpty(juid)){
			this.failMsg(odto, "juid不能为空");
		}else if(!qDto.containsKey("limit")){
			this.failMsg(odto, "limit不能为空");
		}else if(!qDto.containsKey("start")){
			this.failMsg(odto, "start不能为空");
		}else{
			try {
				limit=qDto.getInteger("limit");
			} catch (Exception e) {
				
			}
			try {
				start=qDto.getInteger("start");
			} catch (Exception e) {
				
			}
			UserModel  userModel=cacheUserDataService.getUserModel(juid);
			if(null==userModel){
				this.failMsg(odto, "请先登录再操作!");
			}else{
				Dto pDto=Dtos.newDto("user_id",userModel.getId_());
				pDto.put("is_del", "0");
				pDto.put("oper_id", "0");
				if(limit==-1&&start==-1){
					int rows=depositListDao.rows(pDto);
					pDto.put("limit", rows);
					pDto.put("start", 0);
				}else{
					pDto.put("limit", limit);
					pDto.put("start", start);
				}
				List<Dto> adviceDtos = sqlDao.list("DepositList.apiListDepositPage", pDto);
				for(Dto dto:adviceDtos){
					String depositStatus_name=cacheMasterDataService.getDicDesc("depositStatus", dto.getString("status"));
					dto.put("status_name", depositStatus_name);
				}
				odto.put(AOSCons.APPCODE_KEY, AOSCons.SUCCESS);
				odto.put(AOSCons.APPDATA_KEY, AOSJson.getPageGrid(adviceDtos, pDto.getPageTotal()));
				odto.put(AOSCons.APPMSG_KEY, AOSCons.APPMSG_SUCCESS);
			}
		
		}
		httpModel.setOutMsg(AOSJson.toJson(odto));
	}
	/*
	 * 获取余额申请退款记录
	 */
	public void getApplyBalanceList(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();
		String juid=qDto.getString("juid");
		int limit=-1;
		int start=-1;
		if(!qDto.containsKey("juid")||AOSUtils.isEmpty(juid)){
			this.failMsg(odto, "juid不能为空");
		}else if(!qDto.containsKey("limit")){
			this.failMsg(odto, "limit不能为空");
		}else if(!qDto.containsKey("start")){
			this.failMsg(odto, "start不能为空");
		}else{
			try {
				limit=qDto.getInteger("limit");
			} catch (Exception e) {
				
			}
			try {
				start=qDto.getInteger("start");
			} catch (Exception e) {
				
			}
			UserModel  userModel=cacheUserDataService.getUserModel(juid);
			if(null==userModel){
				this.failMsg(odto, "请先登录再操作!");
			}else{
				Dto pDto=Dtos.newDto("user_id",userModel.getId_());
				pDto.put("is_del", "0");
				pDto.put("oper_id", "1");
				if(limit==-1&&start==-1){
					int rows=depositListDao.rows(pDto);
					pDto.put("limit", rows);
					pDto.put("start", 0);
				}else{
					pDto.put("limit", limit);
					pDto.put("start", start);
				}
				List<Dto> adviceDtos = sqlDao.list("DepositList.apiListDepositPage", pDto);
				for(Dto dto:adviceDtos){
					String depositStatus_name=cacheMasterDataService.getDicDesc("depositStatus", dto.getString("status"));
					dto.put("status_name", depositStatus_name);
				}
				odto.put(AOSCons.APPCODE_KEY, AOSCons.SUCCESS);
				odto.put(AOSCons.APPDATA_KEY, AOSJson.getPageGrid(adviceDtos, pDto.getPageTotal()));
				odto.put(AOSCons.APPMSG_KEY, AOSCons.APPMSG_SUCCESS);
			}
		
		}
		httpModel.setOutMsg(AOSJson.toJson(odto));
	}
	/**
	 * 用户充值
	 */
	@Transactional
	public void payRecharge(HttpModel httpModel) {
		this.payRecharge_(httpModel, "0");
	}
	
	
	/**
	 * 支付押金
	 */
	@Transactional
	public void payDeposit(HttpModel httpModel) {
		this.payRecharge_(httpModel, "2");
	}
	
	@Transactional
	public void payRecharge_(HttpModel httpModel,String pay_type) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();
		String juid=qDto.getString("juid");
		OrdersPayPO paymentPO = new OrdersPayPO();
		paymentPO.copyProperties(qDto);
		if(!qDto.containsKey("juid")||AOSUtils.isEmpty(juid)){
			this.failMsg(odto, "juid不能为空");
		}else if(AOSUtils.isEmpty(paymentPO.getPay_source())){//支付类型，0：微信，1：支付宝
			this.failMsg(odto, "pay_source不能为空");
		}else if(AOSUtils.isEmpty(paymentPO.getPay_amt())){
			this.failMsg(odto, "pay_amt不能为空");
		}else{
			UserModel  userModel=cacheUserDataService.getUserModel(juid);
			
			
			if(null==userModel){
				this.failMsg(odto, "请先登录再操作!");
			}else {
				Object gold_coins_amt=sqlDao.selectOne("OrdersPay.deposit_amt", Dtos.newDto("user_id",userModel.getId_()));
				MembersPO membersPO=membersDao.selectByKey(userModel.getId_());
				if("2".equals(pay_type)&&new BigDecimal(String.valueOf(gold_coins_amt)).compareTo(BigDecimal.ZERO)==1){//押金大于0
					this.failMsg(odto, "您已支付押金。");
				}else if("2".equals(pay_type)&&new BigDecimal(AOSCxt.getParam("deposit_amt")).compareTo(paymentPO.getPay_amt())!=0){//押金大于0
					this.failMsg(odto, "押金金额不正确。");
				}else if("0".equals(pay_type)&&"2".equals(membersPO.getGold_coins_status())){//申请退余额期间，无法充值
					this.failMsg(odto, "您的余额正在申请退款，暂无法充值。");
				}else{
						Map<String,String> pay_data=new HashMap<>();
						String pay_id= AOSId.appId(SystemCons.ID.SYSTEM);
						paymentPO.setCreate_date(AOSUtils.getDateTime());
						paymentPO.setUser_id(userModel.getId_());
						paymentPO.setPay_id(pay_id);
						paymentPO.setCo_id(AOSId.appId(SystemCons.ID.SYSTEM));
						if("2".equals(pay_type)){
							paymentPO.setPay_direction("0");
						}else{
							paymentPO.setPay_direction("1");
						}
						paymentPO.setPay_type(pay_type);
						paymentPO.setStatus("0");
						if(paymentPO.getPay_source().equals("1")){//微信支付
							paymentPO.setOut_trade_no(pay_id+"|"+System.currentTimeMillis());
							this.saveLogs3("微信支付订单号："+paymentPO.getOut_trade_no(), userModel.getId_());
						}else if(paymentPO.getPay_source().equals("0")){//支付宝支付
							paymentPO.setOut_trade_no(pay_id+PayUtil.getTradeNo());
							this.saveLogs3("支付宝支付订单号："+paymentPO.getOut_trade_no(), userModel.getId_());
						}
						ordersPayDao.insert(paymentPO);
						odto.put(AOSCons.APPCODE_KEY, AOSCons.SUCCESS);
						if(paymentPO.getPay_source().equals("1")){//微信支付
							pay_data=wxPay(httpModel, paymentPO,paymentPO.getOut_trade_no());
						}else if(paymentPO.getPay_source().equals("0")){//支付宝支付
							pay_data=aliPay(httpModel, paymentPO,paymentPO.getOut_trade_no());
						}
						odto.put("pay_result_text", "");
						if("0".equals(pay_type)){
							//送优惠券和升级会员
							//送优惠券
							Dto pDto_=Dtos.newDto("is_del", "0");//
							pDto_.put("status_", "1");
							pDto_.put("type_", "1");
							List<ActivityPO> list=activityDao.list(pDto_);
							if(null!=list&&list.size()>0){
								ActivityPO activityPO=list.get(0);
								if(null!=activityPO){
									List<ActivityRulePO> activityRuleList=activityRuleDao.list(Dtos.newDto("activity_id", activityPO.getActivity_id()));
									for(ActivityRulePO activityRulePO:activityRuleList){
										String symbol=activityRulePO.getSymbol();
										BigDecimal param=activityRulePO.getParam();//条件消费金额
										String c=String.valueOf(paymentPO.getPay_amt())+symbol+param.toString();
										Object result = ExpressionEvaluator.evaluate(c, null); 
										if(result.toString().equals("true")){
											odto.put("pay_result_text", "恭喜您获得"+activityRulePO.getAr_num().intValue()+"张"+activityRulePO.getAward_amt()+"元券");
										}
										
									}
								}
							}
						}
						odto.put("pay_id", paymentPO.getPay_id());
						odto.put("pay_data", pay_data);
						odto.put(AOSCons.APPMSG_KEY, AOSCons.APPMSG_SUCCESS);
					
				}
			}
		}
		httpModel.setOutMsg(AOSJson.toJson(odto));
		
		  
               
	}
	/**
	 * 保存支付信息
	 */
	@Transactional
	public void addPayment(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();
		String juid=qDto.getString("juid");
		String voucher_id=qDto.getString("voucher_id");//订单ID
		if("0".equals(voucher_id)){
			voucher_id="";
		}
		OrdersPayPO paymentPO = new OrdersPayPO();
		paymentPO.copyProperties(qDto);
		if(!qDto.containsKey("juid")||AOSUtils.isEmpty(juid)){
			this.failMsg(odto, "juid不能为空");
		}else if(AOSUtils.isEmpty(paymentPO.getCo_id())){
			this.failMsg(odto, "co_id不能为空");
		}else if(AOSUtils.isEmpty(paymentPO.getPay_source())){
			this.failMsg(odto, "pay_source不能为空");
		}else if(AOSUtils.isEmpty(paymentPO.getPay_amt())){
			this.failMsg(odto, "pay_amt不能为空");
		}else{
			this.saveLogs2("addPayment::"+paymentPO.getPay_source(), httpModel);
			//金额只接收小数点后两位
			paymentPO.setPay_amt(paymentPO.getPay_amt().setScale(2, BigDecimal.ROUND_HALF_UP));
			
			UserModel  userModel=cacheUserDataService.getUserModel(juid);
		    ChargingOrdersPO	ordersPO=chargingOrdersDao.selectByKey(paymentPO.getCo_id());
		    VoucherPO voucherPO=null;
		    BigDecimal voucher_amt=BigDecimal.ZERO;
		    BigDecimal voucher_amt_=BigDecimal.ZERO;
		    if(null!=ordersPO){
		    	voucher_amt_=ordersPO.getReal_amt();
		    }
		    if(AOSUtils.isNotEmpty(voucher_id)){
		    	 voucherPO=voucherDao.selectByKey(voucher_id);
		    	 voucher_amt=voucherPO.getVoucher_amt();
		    	 voucher_amt_=ordersPO.getReal_amt().subtract(voucher_amt);
		    }
			if(null==userModel){
				this.failMsg(odto, "请先登录再操作!");
			}else if(null!=voucherPO&&!voucherPO.getDealer_id().equals(userModel.getId_())){
				this.failMsg(odto, "优惠券不属于当前用户!");
			}else if(null!=voucherPO&&voucherPO.getDirection().equals("-1")){
				this.failMsg(odto, "优惠券已失效!");
			}else if(null!=voucherPO&&voucherPO.getDirection().equals("1")){
				this.failMsg(odto, "优惠券已被使用!");
			}else if(ordersPO==null){
				this.failMsg(odto, "当前订单不存在!");
			}else if("2".equals(ordersPO.getStatus_())){
				this.failMsg(odto, "当前订单已支付!");
			}else if("-1".equals(ordersPO.getStatus_())){
				this.failMsg(odto, "当前订单已取消!");
			//}else if(voucher_amt_.compareTo(paymentPO.getPay_amt())!=0){
				//this.failMsg(odto, "支付金额不正确!");
			}else if("1".equals(ordersPO.getStatus_())){
				
				if(AOSUtils.isNotEmpty(voucher_id)&&(voucher_amt.compareTo(ordersPO.getReal_amt())==1||voucher_amt.compareTo(ordersPO.getReal_amt())==0)){//优惠券金额大于或等于总金额
					//
					OrdersPayPO noPaymentPO=new OrdersPayPO();
					noPaymentPO.setCreate_date(AOSUtils.getDateTime());
					noPaymentPO.setUser_id(userModel.getId_());
					noPaymentPO.setPay_id(AOSId.appId(SystemCons.ID.SYSTEM));
					noPaymentPO.setPay_direction("0");//不参与计算
					noPaymentPO.setPay_type("1");
					noPaymentPO.setStatus("1");
					noPaymentPO.setPay_source("3");//券支付
					noPaymentPO.setCo_id(paymentPO.getCo_id());
					noPaymentPO.setPay_amt(BigDecimal.ZERO);
					if(AOSUtils.isNotEmpty(voucher_id)){
						noPaymentPO.setVoucher_id(voucher_id);
						useVoucher(voucher_id);//更新优惠券状态
					}
					noPaymentPO.setSuccess_date(AOSUtils.getDateTime());
					ordersPayDao.insert(noPaymentPO);
					//更新订单状态
					ChargingOrdersPO chargingOrdersPO=new ChargingOrdersPO();
					chargingOrdersPO.setCo_id(noPaymentPO.getCo_id());
					chargingOrdersPO.setStatus_("2");
					chargingOrdersPO.setDeduction_amt(voucher_amt);
					if(AOSUtils.isNotEmpty(voucher_id)){
						chargingOrdersPO.setDeduction_type("1");
					}
					chargingOrdersDao.updateByKey(chargingOrdersPO);
					odto.put(AOSCons.APPCODE_KEY, AOSCons.SUCCESS);
					odto.put("pay_id", noPaymentPO.getPay_id());
					odto.put("pay_data", "true");
					odto.put(AOSCons.APPMSG_KEY, AOSCons.APPMSG_SUCCESS);
					
				}else{
					
				if(AOSUtils.isNotEmpty(voucher_id)){
					paymentPO.setPay_amt(ordersPO.getReal_amt().subtract(voucher_amt));//支付金额减去优惠卷金额
				}	
				//钱包支付
				if(paymentPO.getPay_source().equals("2")){
					Object gold_coins_amt=sqlDao.selectOne("OrdersPay.gold_coins_amt", Dtos.newDto("user_id",userModel.getId_()));
					MembersPO membersPO=membersDao.selectByKey(userModel.getId_());
					if("2".equals(membersPO.getGold_coins_status())){
						this.failMsg(odto, "您的余额申请退款中，请选择其他支付方式。");
					}else if(paymentPO.getPay_amt().compareTo(new BigDecimal(String.valueOf(gold_coins_amt)))==1){//支付金额大于余额
						this.failMsg(odto, "你的余额不足。");
					}else{
						paymentPO.setCreate_date(AOSUtils.getDateTime());
						paymentPO.setUser_id(userModel.getId_());
						paymentPO.setPay_id(AOSId.appId(SystemCons.ID.SYSTEM));
						paymentPO.setPay_direction("-1");
						paymentPO.setPay_type("1");
						paymentPO.setStatus("1");
						if(AOSUtils.isNotEmpty(voucher_id)){
							paymentPO.setVoucher_id(voucher_id);
							useVoucher(voucher_id);//更新优惠券状态
						}
						paymentPO.setSuccess_date(AOSUtils.getDateTime());
						ordersPayDao.insert(paymentPO);
						//更新订单状态
						ChargingOrdersPO chargingOrdersPO=new ChargingOrdersPO();
						chargingOrdersPO.setCo_id(paymentPO.getCo_id());
						chargingOrdersPO.setStatus_("2");
						chargingOrdersPO.setDeduction_amt(voucher_amt);
						if(AOSUtils.isNotEmpty(voucher_id)){
							chargingOrdersPO.setDeduction_type("1");
						}
						chargingOrdersDao.updateByKey(chargingOrdersPO);
						odto.put(AOSCons.APPCODE_KEY, AOSCons.SUCCESS);
						odto.put("pay_id", paymentPO.getPay_id());
						odto.put("pay_data", "true");
						odto.put(AOSCons.APPMSG_KEY, AOSCons.APPMSG_SUCCESS);
					}
				}else{
					Dto pDto=Dtos.newDto("co_id",paymentPO.getCo_id());
					pDto.put("user_id", userModel.getId_());
					OrdersPayPO paymentPO_=ordersPayDao.selectOne(pDto);
					//paymentPO.setPay_amt(ordersPO.getReal_amt());
					Map<String,String> pay_data=new HashMap<>();
					if(null!=paymentPO_){
						paymentPO_.setPay_amt(ordersPO.getReal_amt());
						//paymentPO_.setPay_type(paymentPO.getPay_type());
						paymentPO_.setCreate_date(AOSUtils.getDateTime());
						if(paymentPO.getPay_source().equals("1")){//微信支付
							paymentPO.setOut_trade_no(paymentPO_.getPay_id()+"|"+System.currentTimeMillis());
						}else if(paymentPO.getPay_source().equals("0")){//支付宝支付
							paymentPO.setOut_trade_no(paymentPO_.getPay_id()+PayUtil.getTradeNo());
							
						}
						ordersPayDao.updateByKey(paymentPO_);
						
						if(AOSUtils.isNotEmpty(voucher_id)){//有优惠券，更新支付金额
							paymentPO_.setPay_amt(ordersPO.getReal_amt().subtract(voucher_amt));//支付金额减去优惠卷金额
							OrdersPayPO paymentPO_new=new OrdersPayPO();
							paymentPO_new.setPay_id(paymentPO_.getPay_id());
							paymentPO_new.setPay_amt(ordersPO.getReal_amt().subtract(voucher_amt));
							ordersPayDao.updateByKey(paymentPO_new);
						}
						
						
						odto.put(AOSCons.APPCODE_KEY, AOSCons.SUCCESS);
						odto.put("pay_id", paymentPO_.getPay_id());
						if(paymentPO.getPay_source().equals("1")){//微信支付
							pay_data=wxPay(httpModel, paymentPO_,paymentPO.getOut_trade_no());
						}else if(paymentPO.getPay_source().equals("0")){//支付宝支付
							pay_data=aliPay(httpModel, paymentPO_,paymentPO.getOut_trade_no());
						} 
						odto.put("pay_data", pay_data);
						odto.put(AOSCons.APPMSG_KEY, AOSCons.APPMSG_SUCCESS);
					}else{
						String pay_id=AOSId.appId(SystemCons.ID.SYSTEM);
						paymentPO.setCreate_date(AOSUtils.getDateTime());
						paymentPO.setUser_id(userModel.getId_());
						paymentPO.setPay_id(pay_id);
						paymentPO.setStatus("0");
						paymentPO.setPay_type("1");
						if(AOSUtils.isNotEmpty(voucher_id)){
							paymentPO.setVoucher_id(voucher_id);
						}
						paymentPO.setPay_direction("0");
						if(paymentPO.getPay_source().equals("1")){//微信支付
							paymentPO.setOut_trade_no(pay_id+"|"+System.currentTimeMillis());
						}else if(paymentPO.getPay_source().equals("0")){//支付宝支付
							paymentPO.setOut_trade_no(pay_id+PayUtil.getTradeNo());
						}
						ordersPayDao.insert(paymentPO);
						odto.put(AOSCons.APPCODE_KEY, AOSCons.SUCCESS);
						if(paymentPO.getPay_source().equals("1")){//微信支付
							pay_data=wxPay(httpModel, paymentPO,paymentPO.getOut_trade_no());
						}else if(paymentPO.getPay_source().equals("0")){//支付宝支付
							pay_data=aliPay(httpModel, paymentPO,paymentPO.getOut_trade_no());
						}
						odto.put("pay_id", paymentPO.getPay_id());
						odto.put("pay_data", pay_data);
						odto.put(AOSCons.APPMSG_KEY, AOSCons.APPMSG_SUCCESS);
					}
				}
			 }
			}else{
				this.failMsg(odto, "支付失败，订单状态不正确");
			
			}
		}
		httpModel.setOutMsg(AOSJson.toJson(odto));
		
		  
               
	}
	/**
	 * 更新优惠券为已使用状态
	 * @param voucher_id
	 */
	private VoucherPO useVoucher(String voucher_id){
		VoucherPO voucherPO_=voucherDao.selectByKey(voucher_id);
		if(null!=voucherPO_&&voucherPO_.getDirection().equals("0")){
			VoucherPO voucherPO=new VoucherPO();
			voucherPO.setVoucher_id(voucher_id);
			voucherPO.setDirection("1");
			voucherPO.setUse_date(AOSUtils.getDateTime());
			voucherDao.updateByKey(voucherPO);
		}
		return voucherPO_;
	}
	/**
	 * 微信支付回调方法
	 * @param httpModel
	 */
	public void wxNotify(HttpModel httpModel) {
		String appid=AOSCxt.getParam("appid");  
		String appsecret=AOSCxt.getParam("appsecret");
		String partnerkey=AOSCxt.getParam("partnerkey");
		String partner=AOSCxt.getParam("partner");
		HttpServletRequest request=httpModel.getRequest();
		HttpServletResponse response=httpModel.getResponse();
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}  
	    response.setCharacterEncoding("UTF-8");  
	    response.setContentType("text/html;charset=UTF-8");  
	    response.setHeader("Access-Control-Allow-Origin", "*");
	    String msgxml="";
	    try {
	    InputStream in=request.getInputStream();  
	    ByteArrayOutputStream out=new ByteArrayOutputStream();  
	    byte[] buffer =new byte[1024];  
	    int len=0;  
	    while((len=in.read(buffer))!=-1){  
	        out.write(buffer, 0, len);  
	    }  
	    out.close();  
	    in.close();  
	    msgxml=new String(out.toByteArray(),"utf-8");//xml数据  
	    this.saveLogs2("wxNotify::::msgxml="+msgxml, httpModel);
	    Map map =  new GetWxOrderno().doXMLParse(msgxml);  
	    String result_code=(String) map.get("result_code");  
	    String out_trade_no  = (String) map.get("out_trade_no");  
	    String total_fee  = (String) map.get("total_fee");  
	    String sign  = (String) map.get("sign");  
	    Double amount=new Double(total_fee)/100;//获取订单金额  
	    String attach= (String) map.get("attach");  
	    String sn=out_trade_no.split("\\|")[0];//获取订单编号  
	    String out_trade_no_=out_trade_no.split("\\|")[1];
	   // Long memberid=Long.valueOf(attach);  
	    this.saveLogs2("wxNotify::::sn="+sn, httpModel);
	    if(result_code.equals("SUCCESS")){ 
	    	this.saveLogs2("wxNotify::::SUCCESS", httpModel);
	    	Dto pDto=Dtos.newDto("pay_id",sn);
			OrdersPayPO ordersPayPO=ordersPayDao.selectOne(pDto);
	        //验证签名  
	        float sessionmoney = Float.parseFloat(ordersPayPO.getPay_amt().toString());  
	        String finalmoney = String.format("%.2f", sessionmoney);  
	        finalmoney = finalmoney.replace(".", "");  
	        int intMoney = Integer.parseInt(finalmoney);              
	        //总金额以分为单位，不带小数点  
	        String order_total_fee = String.valueOf(intMoney);  
	        String fee_type  = (String) map.get("fee_type");  
	        String bank_type  = (String) map.get("bank_type");  
	        String cash_fee  = (String) map.get("cash_fee");  
	        String is_subscribe  = (String) map.get("is_subscribe");  
	        String nonce_str  = (String) map.get("nonce_str");  
	        String openid  = (String) map.get("openid");  
	        String return_code  = (String) map.get("return_code");  
	        String sub_mch_id  = (String) map.get("sub_mch_id");  
	        String time_end  = (String) map.get("time_end");  
	        String trade_type  = (String) map.get("trade_type");  
	        String transaction_id  = (String) map.get("transaction_id");  
	        //需要对以下字段进行签名  
	        SortedMap<String, String> packageParams = new TreeMap<String, String>();  
	        packageParams.put("appid", appid);    
	        //packageParams.put("attach", ""); //附加数据 
	        packageParams.put("bank_type", bank_type);    
	        packageParams.put("cash_fee", cash_fee);    
	        packageParams.put("fee_type", fee_type);      
	        packageParams.put("is_subscribe", is_subscribe);    
	        packageParams.put("mch_id", partner);    
	        packageParams.put("nonce_str", nonce_str);        
	        packageParams.put("openid", openid);   
	        packageParams.put("out_trade_no", out_trade_no);  
	        packageParams.put("result_code", result_code);    
	        packageParams.put("return_code", return_code);        
	        packageParams.put("sub_mch_id", sub_mch_id);   
	        packageParams.put("time_end", time_end);  
	        packageParams.put("total_fee", order_total_fee);    //用自己系统的数据：订单金额  
	        packageParams.put("trade_type", trade_type);   
	        packageParams.put("transaction_id", transaction_id);  
	        RequestHandler reqHandler = new RequestHandler(request, response);  
	        reqHandler.init(appid, appsecret, partnerkey);        
	        String endsign = reqHandler.createSign(packageParams);  
	        if(sign.equals(endsign)){//验证签名是否正确  官方签名工具地址：https://pay.weixin.qq.com/wiki/tools/signverify/ 
	        	 this.saveLogs2("wxNotify::::验证签名通过", httpModel);
	        	// ChargingOrdersPO chargingOrdersPO=chargingOrdersDao.selectByKey(sn);
	        	  //OrdersPayPO ordersPayPO=ordersPayDao.selectByKey(sn);
	        	 if(null!=ordersPayPO){
	        		 if("0".equals(ordersPayPO.getStatus())){ // 订单未支付
	 	                try{  
	 	                //处理自己的业务逻辑 
	 	                if("1".equals(ordersPayPO.getPay_type())){//订单支付
	 	                	this.orderPaySuccess(ordersPayPO.getCo_id());
	 	                }else {//充值或押金
	 	                	this.paySuccess(sn,out_trade_no);
	 	                }	
	 	                response.getWriter().write(setXml("SUCCESS", "OK"));    //告诉微信已经收到通知了  
	 	                }catch(Exception e){  
	 	                	 this.saveLogs2("wxNotify::::异常:"+"微信支付异步通知异常"+e.getMessage(), httpModel);
	 	                	 e.printStackTrace();
	 	                }  
	 	            }else if("1".equals(ordersPayPO.getStatus())){ // 订单已支付
	 	            	try{ 
	 	                 response.getWriter().write(setXml("SUCCESS", "OK"));    //告诉微信已经收到通知了  
	 	            	}catch(Exception e){  
	 	            		this.saveLogs2("wxNotify::::异常:"+"微信支付异步通知异常", httpModel);
	 	                }
	 	            }
	        	 }
	             
	      
	        }  
	    }
	    } catch (Exception e1) {
	    	e1.printStackTrace();
	    	 this.saveLogs2("wxNotify::::异常:"+e1.getMessage(), httpModel);
	    }
	     
	}
	@Transactional
	private void orderPaySuccess(String co_id){
		ChargingOrdersPO chargingOrdersPO=new ChargingOrdersPO();
		chargingOrdersPO.setCo_id(co_id);
		chargingOrdersPO.setStatus_("2");
		
		Dto pDto=Dtos.newDto("co_id",co_id);
		OrdersPayPO ordersPayPO=ordersPayDao.selectOne(pDto);
		ordersPayPO.setStatus("1");
		ordersPayPO.setSuccess_date(AOSUtils.getDateTime());
		ordersPayDao.updateByKey(ordersPayPO);
		if(AOSUtils.isNotEmpty(ordersPayPO.getVoucher_id())){
			VoucherPO voucherPO=useVoucher(ordersPayPO.getVoucher_id());//更新优惠券状态
			if(null!=voucherPO){
				chargingOrdersPO.setDeduction_amt(voucherPO.getVoucher_amt());
				chargingOrdersPO.setDeduction_type("1");
			}
		}
		chargingOrdersDao.updateByKey(chargingOrdersPO);
		
		
	}
	@Transactional
	private void paySuccess(String pay_id,String out_trade_no){
		Dto pDto=Dtos.newDto("pay_id",pay_id);
		OrdersPayPO ordersPayPO=ordersPayDao.selectOne(pDto);
		ordersPayPO.setStatus("1");
		ordersPayPO.setSuccess_date(AOSUtils.getDateTime());
		ordersPayPO.setOut_trade_no(out_trade_no);
		ordersPayPO.setOper_id("1");
		ordersPayDao.updateByKey(ordersPayPO);
		if("2".equals(ordersPayPO.getPay_type())){//
			String user_id=ordersPayPO.getUser_id();
			MembersPO aosUserPO=membersDao.selectByKey(user_id);
			MembersPO aosUserPO_=new MembersPO();
			aosUserPO_.setId_(user_id);
			aosUserPO_.setDeposit_amt(aosUserPO.getDeposit_amt().add(ordersPayPO.getPay_amt()));
			if(aosUserPO_.getDeposit_amt().compareTo(BigDecimal.ZERO)==1){
				aosUserPO_.setDeposit_date(AOSUtils.getDateTime());
				aosUserPO_.setDeposit_status("1");
			}
			membersDao.updateByKey(aosUserPO_);
		}else{
			String user_id=ordersPayPO.getUser_id();
			MembersPO aosUserPO=membersDao.selectByKey(user_id);
			//送优惠券和升级会员
			//送优惠券
			Dto pDto_=Dtos.newDto("is_del", "0");//
			pDto_.put("status_", "1");
			pDto_.put("type_", "1");
			List<ActivityPO> list=activityDao.list(pDto_);
			if(null!=list&&list.size()>0){
				ActivityPO activityPO=list.get(0);
				if(null!=activityPO){
					List<ActivityRulePO> activityRuleList=activityRuleDao.list(Dtos.newDto("activity_id", activityPO.getActivity_id()));
					for(ActivityRulePO activityRulePO:activityRuleList){
						String symbol=activityRulePO.getSymbol();
						BigDecimal param=activityRulePO.getParam();//条件消费金额
						String c=String.valueOf(ordersPayPO.getPay_amt())+symbol+param.toString();
						Object result = ExpressionEvaluator.evaluate(c, null); 
						if(result.toString().equals("true")){
							for(int i=0;i<activityRulePO.getAr_num().intValue();i++){
								VoucherPO voucherPO=new VoucherPO();
								voucherPO.setActivity_id(activityPO.getActivity_id());
								//voucherPO.setOrder_id(order_id);
								//voucherPO.setCond_value(activityRulePO.getCond_value());
								voucherPO.setIs_del("0");
								voucherPO.setDealer_id(ordersPayPO.getUser_id());
								voucherPO.setDirection("0");
								voucherPO.setVoucher_amt(activityRulePO.getAward_amt());
								voucherPO.setVoucher_id(AOSId.appId(SystemCons.ID.SYSTEM));
								voucherPO.setCreate_date(AOSUtils.getDateTime());
								voucherPO.setEffec_date(AOSUtils.dateAdd(AOSUtils.getCurDayTimestamp(), Calendar.MONTH, Integer.valueOf(AOSCxt.getParam("voucherValidity"))));
								voucherDao.insert(voucherPO);
							}
							break;
						}
						
					}
					/*
					for(int i=0;i<activityPO.getAr_num().intValue();i++){
						VoucherPO voucherPO=new VoucherPO();
						voucherPO.setActivity_id(activityPO.getActivity_id());
						//voucherPO.setOrder_id(order_id);
						//voucherPO.setCond_value(activityRulePO.getCond_value());
						voucherPO.setIs_del("0");
						voucherPO.setDealer_id(ordersPayPO.getUser_id());
						voucherPO.setDirection("0");
						voucherPO.setVoucher_amt(activityPO.getAmount());
						voucherPO.setVoucher_id(AOSId.appId(SystemCons.ID.SYSTEM));
						voucherPO.setCreate_date(AOSUtils.getDateTime());
						voucherPO.setEffec_date(AOSUtils.dateAdd(AOSUtils.getCurDayTimestamp(), Calendar.MONTH, Integer.valueOf(AOSCxt.getParam("voucherValidity"))));
						voucherDao.insert(voucherPO);
					}*/
				}
			}
			
			//升级会员
			String platinum=AOSCxt.getParam("platinum");//白金会员
			if(AOSUtils.isEmpty(platinum)){
				platinum="99999999";
			}
			String gold=AOSCxt.getParam("gold");//金牌会员
			if(AOSUtils.isEmpty(gold)){
				gold="99999999";
			}
			String silver=AOSCxt.getParam("silver");//银牌会员
			if(AOSUtils.isEmpty(silver)){
				silver="99999999";
			}
			MembersPO aosUserPO_=new MembersPO();
			aosUserPO_.setId_(aosUserPO.getId_());
			if("platinum".equals(aosUserPO.getGrade_())){//白金会员，不做处理
				
			}else if("gold".equals(aosUserPO.getGrade_())){
				if(ordersPayPO.getPay_amt().compareTo(new BigDecimal(platinum))!=-1){
					aosUserPO_.setGrade_("platinum");
				}
			}else if("silver".equals(aosUserPO.getGrade_())){
				if(ordersPayPO.getPay_amt().compareTo(new BigDecimal(platinum))!=-1){
					aosUserPO_.setGrade_("platinum");
				}else if(ordersPayPO.getPay_amt().compareTo(new BigDecimal(gold))!=-1){
					aosUserPO_.setGrade_("gold");
				}
			}else{
				if(ordersPayPO.getPay_amt().compareTo(new BigDecimal(platinum))!=-1){
					aosUserPO_.setGrade_("platinum");
				}else if(ordersPayPO.getPay_amt().compareTo(new BigDecimal(gold))!=-1){
					aosUserPO_.setGrade_("gold");
				}else if(ordersPayPO.getPay_amt().compareTo(new BigDecimal(silver))!=-1){
					aosUserPO_.setGrade_("silver");
				}
			}
			membersDao.updateByKey(aosUserPO_);
			
			
			
		}
		
		
		
	}
	private static String setXml(String return_code,String return_msg){  
	        return "<xml><return_code><![CDATA["+return_code+"]]></return_code><return_msg><![CDATA["+return_msg+"]]></return_msg></xml>";  
	}
	
	private Map<String,String> wxPay(HttpModel httpModel,OrdersPayPO paymentPO,String out_trade_no){
		HttpServletRequest request=httpModel.getRequest();
		HttpServletResponse response=httpModel.getResponse();
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		}  
	    response.setCharacterEncoding("UTF-8");  
	    response.setContentType("text/html;charset=UTF-8");  
	    Map<String,String> retMsg=new HashMap<String,String>();
	    
	    String userId =paymentPO.getUser_id();   
	    String money = paymentPO.getPay_amt().toString();//获取订单金额  
	    //金额转化为分为单位  
	    float sessionmoney = Float.parseFloat(money);  
	    String finalmoney = String.format("%.2f", sessionmoney);  
	    finalmoney = finalmoney.replace(".", "");  
        String currTime = TenpayUtil.getCurrTime();  
        //8位日期  
        String strTime = currTime.substring(8, currTime.length());  
        //四位随机数  
        String strRandom = TenpayUtil.buildRandom(4) + "";  
        //10位序列号,可以自行调整。  
        String strReq = strTime + strRandom;          
        //商户号  
        String mch_id = AOSCxt.getParam("partner");  
        //随机数   
        String nonce_str = strReq;  
        String body = paymentPO.getCo_id();  
        //附加数据  
        String attach = "";  
        //商户订单号  
       
        int intMoney = Integer.parseInt(finalmoney);              
        //总金额以分为单位，不带小数点  
        String total_fee = String.valueOf(intMoney);  
        //订单生成的机器 IP  
        String spbill_create_ip = request.getRemoteAddr();  
        String notify_url =AOSCxt.getParam("notify_url");//微信异步通知地址           
        String trade_type = "APP";//app支付必须填写为APP  
        //对以下字段进行签名  
        SortedMap<String, String> packageParams = new TreeMap<String, String>();  
        packageParams.put("appid", AOSCxt.getParam("appid"));    
        packageParams.put("attach", attach);   
        packageParams.put("body", body);    
        packageParams.put("mch_id", mch_id);      
        packageParams.put("nonce_str", nonce_str);    
        packageParams.put("notify_url", notify_url);    
        packageParams.put("out_trade_no", out_trade_no);      
        packageParams.put("spbill_create_ip", spbill_create_ip);   
        packageParams.put("total_fee", total_fee);  
        packageParams.put("trade_type", trade_type);    
        RequestHandler reqHandler = new RequestHandler(request, response);  
        reqHandler.init(AOSCxt.getParam("appid"),AOSCxt.getParam("appsecret") ,AOSCxt.getParam("partnerkey"));        
        String sign = reqHandler.createSign(packageParams);//获取签名  
        String xml="<xml>"+  
                "<appid>"+AOSCxt.getParam("appid")+"</appid>"+  
                "<attach>"+attach+"</attach>"+  
                "<body><![CDATA["+body+"]]></body>"+  
                "<mch_id>"+mch_id+"</mch_id>"+  
                "<nonce_str>"+nonce_str+"</nonce_str>"+  
                "<notify_url>"+notify_url+"</notify_url>"+  
                "<out_trade_no>"+out_trade_no+"</out_trade_no>"+  
                "<spbill_create_ip>"+spbill_create_ip+"</spbill_create_ip>"+  
                "<total_fee>"+total_fee+"</total_fee>"+  
                "<trade_type>"+trade_type+"</trade_type>"+  
                "<sign>"+sign+"</sign>"+  
                "</xml>";  
        String allParameters = "";  
        try {  
            allParameters =  reqHandler.genPackage(packageParams);  
         } catch (Exception e) {  
            e.printStackTrace();  
        }  
        String createOrderURL = AOSCxt.getParam("createOrderURL");  
        String prepay_id="";  
        try {  
            prepay_id = new GetWxOrderno().getPayNo(createOrderURL, xml);  
            if(prepay_id.equals("")){  
             
             }  
        } catch (Exception e1) {  
            // TODO Auto-generated catch block  
            e1.printStackTrace();  
         }  
        //获取到prepayid后对以下字段进行签名最终发送给app  
        SortedMap<String, String> finalpackage = new TreeMap<String, String>();  
        String timestamp = Sha1Util.getTimeStamp();  
        finalpackage.put("appid", AOSCxt.getParam("appid"));    
        finalpackage.put("timestamp", timestamp);    
        finalpackage.put("noncestr", nonce_str);    
        finalpackage.put("partnerid", AOSCxt.getParam("partner"));   
        finalpackage.put("package", "Sign=WXPay");                
        finalpackage.put("prepayid", prepay_id);    
        String finalsign = reqHandler.createSign(finalpackage);  
        retMsg.put("appid", AOSCxt.getParam("appid"));  
        retMsg.put("timestamp", timestamp);  
        retMsg.put("noncestr", nonce_str);  
        retMsg.put("partnerid", AOSCxt.getParam("partner"));  
        retMsg.put("prepayid", prepay_id);  
        retMsg.put("package", "Sign=WXPay");  
        retMsg.put("sign", finalsign);
        this.saveLogs2("wxPay:微信支付返回值："+retMsg.toString(), httpModel);
        return retMsg;
	}
	
	private Map<String,String> aliPay(HttpModel httpModel,OrdersPayPO paymentPO,String out_trade_no){
		 Map<String,String> retMsg=new HashMap<String,String>();
		 Map<String, String> param = new HashMap<>();
		 String content="";
		 String encodeContent="";
		 try {
			// 公共请求参数
			param.put("app_id", AOSCxt.getParam("alipay.appid"));// 支付宝分配给开发者的应用ID
			content="app_id="+AOSCxt.getParam("alipay.appid");
			encodeContent="app_id="+encode(AOSCxt.getParam("alipay.appid"));
			Map<String, Object> pcont = new HashMap<>();
			// 支付业务请求参数
			pcont.put("out_trade_no", out_trade_no); // 商户订单号 2817080302400000001TNO2017062817080302400000001
			pcont.put("total_amount", String.valueOf(paymentPO.getPay_amt()));// 交易金额
			try{
				pcont.put("subject", "电动汽车共享充电桩APP"); // 订单标题
				pcont.put("body", "电动汽车共享充电桩APP");// 对交易或商品的描述
			}catch (Exception e) {
				this.saveLogs2("aliPay:支付宝支付转码失败："+e.toString(), httpModel);
			}
			pcont.put("product_code", "QUICK_MSECURITY_PAY");// 销售产品码
			pcont.put("goods_type", "1");// 商品主类型：0—虚拟类商品，1—实物类商品 注：虚拟类商品不支持使用花呗渠道
			
				param.put("biz_content", JSON.toJSONString(pcont));
				content+="&biz_content="+JSON.toJSONString(pcont);
				encodeContent+="&biz_content="+encode(JSON.toJSONString(pcont));
			 // 业务请求参数  不需要对json字符串转义 
			
			
			
			param.put("charset", AlipayConstants.CHARSET_UTF8);
			content+="&charset="+AlipayConstants.CHARSET_UTF8;
			encodeContent+="&charset="+encode(AlipayConstants.CHARSET_UTF8);
			param.put("format", AlipayConstants.FORMAT_JSON);
			content+="&format="+AlipayConstants.FORMAT_JSON;
			encodeContent+="&format="+encode(AlipayConstants.FORMAT_JSON);
			param.put("method", "alipay.trade.app.pay");// 接口名称
			content+="&method="+"alipay.trade.app.pay";
			encodeContent+="&method="+encode("alipay.trade.app.pay");
			param.put("notify_url", AOSCxt.getParam("alipay.notify_url")); // 支付宝服务器主动通知商户服务
			content+="&notify_url="+AOSCxt.getParam("alipay.notify_url");
			encodeContent+="&notify_url="+encode(AOSCxt.getParam("alipay.notify_url"));
			param.put("sign_type", AlipayConstants.SIGN_TYPE_RSA2);
			content+="&sign_type="+AlipayConstants.SIGN_TYPE_RSA2;
			encodeContent+="&sign_type="+encode(AlipayConstants.SIGN_TYPE_RSA2);
			param.put("timestamp", DatetimeUtil.formatDateTime(new Date()));
			content+="&timestamp="+DatetimeUtil.formatDateTime(new Date());
			encodeContent+="&timestamp="+encode(DatetimeUtil.formatDateTime(new Date()));
			param.put("version", "1.0");
			content+="&version="+"1.0";
			encodeContent+="&version="+encode("1.0");
				String sign=AlipaySignature.rsaSign(content, AOSCxt.getParam("alipay.private_key"), AlipayConstants.CHARSET_UTF8, AlipayConstants.SIGN_TYPE_RSA2);
				//param.put("sign", PayUtil.getSign(param, AOSCxt.getParam("alipay.private_key"))); // 业务请求参数
				param.put("sign", sign);
				retMsg.put("sign", encodeContent+"&sign="+encode(sign));
			} catch (Exception e) {
				e.printStackTrace();
			}
		 this.saveLogs2("aliPay:支付宝支付返回值："+retMsg.toString(), httpModel);
		 return retMsg;
	}
	private String encode(String sign) throws UnsupportedEncodingException {  
        return URLEncoder.encode(sign, "utf-8").replaceAll("\\+", "%20");  
    } 
	/**
	 * 支付宝支付回调方法
	 * @param httpModel
	 */
	public void aliNotify(HttpModel httpModel) {
		HttpServletRequest request=httpModel.getRequest();
		HttpServletResponse response=httpModel.getResponse();
		this.saveLogs2("aliNotify::::"+request.getParameterMap().toString(), httpModel);
		// 获取到返回的所有参数 先判断是否交易成功trade_status 再做签名校验
		// 1、商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
		// 2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
		// 3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email），
		// 4、验证app_id是否为该商户本身。上述1、2、3、4有任何一个验证不通过，则表明本次通知是异常通知，务必忽略。在上述验证通过后商户必须根据支付宝不同类型的业务通知，正确的进行不同的业务处理，并且过滤重复的通知结果数据。在支付宝的业务通知中，只有交易通知状态为TRADE_SUCCESS或TRADE_FINISHED时，支付宝才会认定为买家付款成功。
		if ("TRADE_SUCCESS".equals(request.getParameter("trade_status"))) {
			Enumeration<?> pNames = request.getParameterNames();
			Map<String, String> param = new HashMap<String, String>();
			try {
				while (pNames.hasMoreElements()) {
					String pName = (String) pNames.nextElement();
					param.put(pName, request.getParameter(pName));
				}
				boolean signVerified = AlipaySignature.rsaCheckV1(param, AOSCxt.getParam("alipay.public_key"),
						AlipayConstants.CHARSET_UTF8,"RSA2"); // 校验签名是否正确
				if (signVerified) {
					// TODO 验签成功后
					// 按照支付结果异步通知中的描述，对支付结果中的业务内容进行1\2\3\4二次校验，校验成功后在response中返回success，校验失败返回failure
					String order_id=request.getParameter("out_trade_no");
					String out_trade_no=order_id.substring(order_id.indexOf("TNO")+3,order_id.length());
					String out_trade_no_=order_id;
					order_id=order_id.substring(0,order_id.indexOf("TNO"));
					
					//ChargingOrdersPO chargingOrdersPO=chargingOrdersDao.selectByKey(order_id);
					OrdersPayPO ordersPayPO=ordersPayDao.selectByKey(order_id);
					if(null!=ordersPayPO){
						if("0".equals(ordersPayPO.getStatus())){ // 订单未支付
							//处理自己的业务逻辑 
							if("1".equals(ordersPayPO.getPay_type())){//订单支付
								this.orderPaySuccess(ordersPayPO.getCo_id());
		 	                }else {//充值或押金
		 	                	this.paySuccess(order_id,out_trade_no_);
		 	                }
									
							}else if("1".equals(ordersPayPO.getStatus())){ // 订单已支付
								this.saveLogs2("aliNotify::::"+"支付宝支付返回,订单已支付", httpModel);
							}
					}
						 
					
				} else {
					// TODO 验签失败则记录异常日志，并在response中返回failure.
					this.saveLogs2("aliNotify::::"+"支付宝支付返回验证失败", httpModel);
				}
			} catch (Exception e) {
				e.printStackTrace();
				this.saveLogs2("aliNotify::::"+e.getMessage(), httpModel);
			}
		}
	}
	
	
	
	private String getParentRegionCode(String range_){
		Dto regionDto=Dtos.newDto("region_code",range_);
	    RegionPO	regionPO=regionDao.selectOne(regionDto);
	    Dto regionDto_=Dtos.newDto("region_id",regionPO.getParent_id());
	    RegionPO	p_regionPO=regionDao.selectOne(regionDto_);
	    return p_regionPO.getRegion_code();
	}
	
	/**
	 * 申请退押金
	 */
	public void applyDeposit(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();
		String juid=qDto.getString("juid");
		if(!qDto.containsKey("juid")||AOSUtils.isEmpty(juid)){
			this.failMsg(odto, "juid不能为空");
		}else{
			UserModel  userModel=cacheUserDataService.getUserModel(juid);
			if(null==userModel){
				this.failMsg(odto, "请先登录再操作!");
			}else{
				Dto pDto=Dtos.newDto("user_id", userModel.getId_());
				pDto.put("is_del", "0");
				pDto.put("status_", "1");
				int count_num=chargingOrdersDao.rows(pDto);
				if(count_num>0){
					odto.put(AOSCons.APPCODE_KEY, "-2");
					odto.put(AOSCons.APPMSG_KEY, "您有未支付的订单。");
				}else{
					MembersPO membersPO=membersDao.selectByKey(userModel.getId_());
					if("2".equals(membersPO.getDeposit_status())){
						this.failMsg(odto, "您的押金退款申请正在处理中");
					}else if(membersPO.getDeposit_amt().compareTo(BigDecimal.ZERO)==1){//大于0
						DepositListPO depositListPO=new DepositListPO();
						depositListPO.setDeposit_id(AOSId.appId(SystemCons.ID.SYSTEM));
						depositListPO.setCreate_date(AOSUtils.getDateTime());
						depositListPO.setUser_id(userModel.getId_());
						depositListPO.setOper_id("0");
						Object deposit_amt=sqlDao.selectOne("OrdersPay.deposit_amt", Dtos.newDto("user_id",userModel.getId_()));
						depositListPO.setAmt(new BigDecimal(deposit_amt.toString()));
						depositListDao.insert(depositListPO);
						
						//更新押金状态
						MembersPO membersPO_=new MembersPO();
						membersPO_.setId_(membersPO.getId_());
						membersPO_.setDeposit_status("2");
						membersDao.updateByKey(membersPO_);
						
						odto.put(AOSCons.APPCODE_KEY, AOSCons.SUCCESS);
						odto.put(AOSCons.APPMSG_KEY, AOSCons.APPMSG_SUCCESS);
					}else{
						this.failMsg(odto, "押金金额为0，无法申请");
					}
				}
			}
		}
		httpModel.setOutMsg(AOSJson.toJson(odto));
	}
	/**
	 * 新增订单
	 */
	public void addOrder(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();
		String juid=qDto.getString("juid");
		String cp_id=qDto.getString("cp_id"); 
		String co_type=qDto.getString("co_type");
		String co_num=qDto.getString("co_num");
		if(!qDto.containsKey("juid")||AOSUtils.isEmpty(juid)){
			this.failMsg(odto, "juid不能为空");
		}else if(!qDto.containsKey("cp_id")||AOSUtils.isEmpty(cp_id)){
				this.failMsg(odto, "cp_id不能为空");
		}else if(!qDto.containsKey("co_type")||AOSUtils.isEmpty(co_type)){
			this.failMsg(odto, "co_type不能为空");
		}else if(!qDto.containsKey("co_num")||AOSUtils.isEmpty(co_num)){
			this.failMsg(odto, "co_num不能为空");
		}else{
			UserModel  userModel=cacheUserDataService.getUserModel(juid);
			ChargingPilePO chargingPilePO=chargingPileDao.selectByKey(cp_id);
			if(null==userModel){
				this.failMsg(odto, "请先登录再操作!");
			}else if(null==chargingPilePO){
				this.failMsg(odto, "未查询到充电桩记录!");
			}else if(null!=chargingPilePO&&"2".equals(chargingPilePO.getCp_status())){
				this.failMsg(odto, "充电桩已被占用!");
			}else if(null!=chargingPilePO&&"3".equals(chargingPilePO.getCp_status())){
				this.failMsg(odto, "充电桩故障!");
			} else{
				
				MembersPO membersPO=membersDao.selectByKey(userModel.getId_());
				
				if(membersPO.getDeposit_status().equals("0")){
					this.failMsg(odto, "您未支付押金，无法充电!");
				}else if(membersPO.getDeposit_status().equals("-1")){
					this.failMsg(odto, "您押金已退，无法充电!");
				}else if(membersPO.getDeposit_status().equals("2")){
					this.failMsg(odto, "您的押金在申请退款中，无法充电!");
				}else{
					Dto pDto=Dtos.newDto("user_id", userModel.getId_());
					pDto.put("is_del", "0");
					pDto.put("status_", "1");
					int count_num=chargingOrdersDao.rows(pDto);
					pDto.put("status_", "-2");
					int count_num1=chargingOrdersDao.rows(pDto);
					pDto.put("status_", "0");
					int count_num2=chargingOrdersDao.rows(pDto);
					if(count_num>0){
						this.failMsg(odto, "您有未支付的订单!");
					}else if(count_num1>0){
						this.failMsg(odto, "您有订单正在充电确认中!");
					}else if(count_num2>0){
						this.failMsg(odto, "您有订单正在充电中!");
					}else{
						if(sendCharging(cp_id, co_type, co_num,chargingPilePO,odto)){
							ChargingOrdersPO chargingOrdersPO=new ChargingOrdersPO();
							chargingOrdersPO.setCo_id(AOSId.appId(SystemCons.ID.SYSTEM));
							chargingOrdersPO.setCreate_date(AOSUtils.getDateTime());
							chargingOrdersPO.setUser_id(userModel.getId_());
							chargingOrdersPO.setCp_id(cp_id);
							chargingOrdersPO.setCo_type(co_type);
							chargingOrdersPO.setStatus_("-2");
							chargingOrdersPO.setCo_num(new BigDecimal(co_num));
							chargingOrdersDao.insert(chargingOrdersPO);
							
							odto.put(AOSCons.APPCODE_KEY, AOSCons.SUCCESS);
							odto.put("co_id", chargingOrdersPO.getCo_id());
							odto.put(AOSCons.APPMSG_KEY, AOSCons.APPMSG_SUCCESS);
						}
						
					}
				}
				
			}
		}
		httpModel.setOutMsg(AOSJson.toJson(odto));
	}
	public void stopCharging(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();
		String juid=qDto.getString("juid");
		String co_id=qDto.getString("co_id");
		if(!qDto.containsKey("juid")||AOSUtils.isEmpty(juid)){
			this.failMsg(odto, "juid不能为空");
		}else if(!qDto.containsKey("co_id")||AOSUtils.isEmpty(co_id)){
			this.failMsg(odto, "co_id不能为空");
		}else{
			UserModel  userModel=cacheUserDataService.getUserModel(juid);
			if(null==userModel){
				this.failMsg(odto, "请先登录再操作!");
			} else{
				ChargingOrdersPO chargingOrdersPO=chargingOrdersDao.selectByKey(co_id);
				if(null==chargingOrdersPO){
					this.failMsg(odto, "充电记录不存在!");
				}else if("1".equals(chargingOrdersPO.getStatus_())||"2".equals(chargingOrdersPO.getStatus_())){
					this.failMsg(odto, "已结束充电!");
				}else if("-1".equals(chargingOrdersPO.getStatus_())){
					this.failMsg(odto, "订单已取消!");
				}else if("-2".equals(chargingOrdersPO.getStatus_())){
					this.failMsg(odto, "充电确认中!");
				}else if("-3".equals(chargingOrdersPO.getStatus_())){
					this.failMsg(odto, "不同意充电!");
				}else{
					if(stopCharging1(chargingOrdersPO.getCp_id(),odto)){
						Dto pDto=Dtos.newDto("user_id",userModel.getId_());
						List<Dto> chargingOrdersDtos=sqlDao.list("ChargingOrders.listChargingOrderssDoing", pDto);
						Dto dto=null;
						if(null!=chargingOrdersDtos&&chargingOrdersDtos.size()>0){
							dto=Dtos.newDto();
							dto=chargingOrdersDtos.get(0);
							if("0".equals(dto.getString("total_amt"))||"0.0".equals(dto.getString("total_amt"))||"0.00".equals(dto.getString("total_amt"))){
								dto.put("status_","-1");
								ChargingOrdersPO chargingOrdersPO_=new ChargingOrdersPO();
								chargingOrdersPO_.setCo_id(dto.getString("co_id"));
								chargingOrdersPO_.setStatus_("-1");
								chargingOrdersDao.updateByKey(chargingOrdersPO_);
							}else{
								MembersPO aosUserPO=membersDao.selectByKey(userModel.getId_());
								ChargingOrdersPO chargingOrdersPO_=new ChargingOrdersPO();
								if("platinum".equals(aosUserPO.getGrade_())){
									String platinum_discount=AOSCxt.getParam("platinum_discount");
									if(AOSUtils.isEmpty(platinum_discount)){
										platinum_discount="1";
									}
									chargingOrdersPO_.setDiscount(new BigDecimal(platinum_discount));
									BigDecimal real_amt=new BigDecimal(dto.getString("total_amt")).multiply(new BigDecimal(platinum_discount));
									chargingOrdersPO_.setReal_amt(real_amt);
									dto.put("real_amt",real_amt);
									dto.put("discount",platinum_discount);
								}else if("gold".equals(aosUserPO.getGrade_())){
									String gold_discount=AOSCxt.getParam("gold_discount");
									if(AOSUtils.isEmpty(gold_discount)){
										gold_discount="1";
									}
									chargingOrdersPO_.setDiscount(new BigDecimal(gold_discount));
									BigDecimal real_amt=new BigDecimal(dto.getString("total_amt")).multiply(new BigDecimal(gold_discount));
									chargingOrdersPO_.setReal_amt(real_amt);
									dto.put("real_amt",real_amt);
									dto.put("discount",gold_discount);
								}else if("silver".equals(aosUserPO.getGrade_())){
									String silver_discount=AOSCxt.getParam("silver_discount");
									if(AOSUtils.isEmpty(silver_discount)){
										silver_discount="1";
									}
									chargingOrdersPO_.setDiscount(new BigDecimal(silver_discount));
									BigDecimal real_amt=new BigDecimal(dto.getString("total_amt")).multiply(new BigDecimal(silver_discount));
									chargingOrdersPO_.setReal_amt(real_amt);
									dto.put("real_amt",real_amt);
									dto.put("discount",silver_discount);
								}
								dto.put("status_","1");
								
								chargingOrdersPO_.setCo_id(dto.getString("co_id"));
								chargingOrdersPO_.setStatus_("1");
								chargingOrdersDao.updateByKey(chargingOrdersPO_);
							}
							String status_name=cacheMasterDataService.getDicDesc("order_status_", dto.getString("status_"));
							String deduction_type_name=cacheMasterDataService.getDicDesc("deduction_type", dto.getString("deduction_type"));
							dto.put("status_name", status_name);
							dto.put("deduction_type_name", deduction_type_name);
						}
						
						odto.put(AOSCons.APPDATA_KEY, dto);
						odto.put(AOSCons.APPCODE_KEY, AOSCons.SUCCESS);
						odto.put(AOSCons.APPMSG_KEY, AOSCons.APPMSG_SUCCESS);
					}
					
				}
					
				
			}
		}
		httpModel.setOutMsg(AOSJson.toJson(odto));
	}
	public void checkPlug(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();
		String juid=qDto.getString("juid");
		String co_id=qDto.getString("co_id");
		if(!qDto.containsKey("juid")||AOSUtils.isEmpty(juid)){
			this.failMsg(odto, "juid不能为空");
		}else if(!qDto.containsKey("co_id")||AOSUtils.isEmpty(co_id)){
			this.failMsg(odto, "co_id不能为空");
		}else{
			UserModel  userModel=cacheUserDataService.getUserModel(juid);
			if(null==userModel){
				this.failMsg(odto, "请先登录再操作!");
			} else{
				ChargingOrdersPO chargingOrdersPO=chargingOrdersDao.selectByKey(co_id);
				if(null==chargingOrdersPO){
					this.failMsg(odto, "充电记录不存在!");
				}else if("1".equals(chargingOrdersPO.getStatus_())||"2".equals(chargingOrdersPO.getStatus_())){
					this.failMsg(odto, "已结束充电!");
				}else if("-1".equals(chargingOrdersPO.getStatus_())){
					this.failMsg(odto, "订单已取消!");
				}else if("-2".equals(chargingOrdersPO.getStatus_())){
					this.failMsg(odto, "充电确认中!");
				}else if("-3".equals(chargingOrdersPO.getStatus_())){
					this.failMsg(odto, "不同意充电!");
				}else{
					odto.put("status", chargingOrdersPO.getPut_gun());//返回插枪状态
					odto.put(AOSCons.APPCODE_KEY, AOSCons.SUCCESS);
					odto.put(AOSCons.APPMSG_KEY, AOSCons.APPMSG_SUCCESS);
				}
					
				
			}
		}
		httpModel.setOutMsg(AOSJson.toJson(odto));
	}
	public void getChargingPileStatus(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();
		String juid=qDto.getString("juid");
		String cp_id=qDto.getString("cp_id");
		if(!qDto.containsKey("juid")||AOSUtils.isEmpty(juid)){
			this.failMsg(odto, "juid不能为空");
		}else if(!qDto.containsKey("cp_id")||AOSUtils.isEmpty(cp_id)){
			this.failMsg(odto, "cp_id不能为空");
		}else{
			UserModel  userModel=cacheUserDataService.getUserModel(juid);
			if(null==userModel){
				this.failMsg(odto, "请先登录再操作!");
			} else{
				ChargingPilePO chargingPilePO=chargingPileDao.selectByKey(cp_id);
				if(null==chargingPilePO){
					this.failMsg(odto, "充电桩不存在!");
				}else{
					odto.put("status", chargingPilePO.getCp_status());//返回充电庄状态
					odto.put(AOSCons.APPCODE_KEY, AOSCons.SUCCESS);
					odto.put(AOSCons.APPMSG_KEY, AOSCons.APPMSG_SUCCESS);
				}
					
				
			}
		}
		httpModel.setOutMsg(AOSJson.toJson(odto));
	}
	
	/**
	 * 申请退余额
	 */
	public void applyBalance(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		Dto odto = Dtos.newDto();
		String juid=qDto.getString("juid");
		String account=qDto.getString("account");
		String name=qDto.getString("name");
		if(!qDto.containsKey("juid")||AOSUtils.isEmpty(juid)){
			this.failMsg(odto, "juid不能为空");
		}else if(!qDto.containsKey("account")||AOSUtils.isEmpty(account)){
				this.failMsg(odto, "account不能为空");
		}else if(!qDto.containsKey("name")||AOSUtils.isEmpty(name)){
			this.failMsg(odto, "name不能为空");
		}else{
			UserModel  userModel=cacheUserDataService.getUserModel(juid);
			if(null==userModel){
				this.failMsg(odto, "请先登录再操作!");
			}else{
				Dto pDto=Dtos.newDto("user_id", userModel.getId_());
				pDto.put("is_del", "0");
				pDto.put("status_", "1");
				int count_num=chargingOrdersDao.rows(pDto);
				if(count_num>0){
					odto.put(AOSCons.APPCODE_KEY, "-2");
					odto.put(AOSCons.APPMSG_KEY, "您有未支付的订单。");
				}else{
					Object gold_coins_amt=sqlDao.selectOne("OrdersPay.gold_coins_amt", Dtos.newDto("user_id",userModel.getId_()));
					BigDecimal gold_coins= new BigDecimal(gold_coins_amt.toString());
					MembersPO membersPO=membersDao.selectByKey(userModel.getId_());
					if("2".equals(membersPO.getGold_coins_status())){
						this.failMsg(odto, "您的余额退款申请正在处理中");
					}else if(gold_coins.compareTo(BigDecimal.ZERO)==1){//大于0
						DepositListPO depositListPO=new DepositListPO();
						depositListPO.setDeposit_id(AOSId.appId(SystemCons.ID.SYSTEM));
						depositListPO.setCreate_date(AOSUtils.getDateTime());
						depositListPO.setUser_id(userModel.getId_());
						depositListPO.setOper_id("1");
						depositListPO.setAccount(account);
						depositListPO.setName(name);
						depositListPO.setAmt(gold_coins);
						depositListDao.insert(depositListPO);
						
						//更新余额状态
						MembersPO membersPO_=new MembersPO();
						membersPO_.setId_(membersPO.getId_());
						membersPO_.setGold_coins_status("2");
						membersDao.updateByKey(membersPO_);
						
						odto.put(AOSCons.APPCODE_KEY, AOSCons.SUCCESS);
						odto.put(AOSCons.APPMSG_KEY, AOSCons.APPMSG_SUCCESS);
					}else{
						this.failMsg(odto, "余额为0，无法申请");
					}
				}
			}
		}
		httpModel.setOutMsg(AOSJson.toJson(odto));
	}
	
	
	
	
	
	private void initSmsConfig(){
		restAPI.init(AOSCxt.getParam("sms_url"), AOSCxt.getParam("sms_port"));
		restAPI.setAccount(AOSCxt.getParam("account_sid"), AOSCxt.getParam("auth_token"));
		restAPI.setAppId(AOSCxt.getParam("app_id"));
	}
	private void sendSms(String mobile,String templateId,String smsCode){
		this.initSmsConfig();
		HashMap<String, Object> result= restAPI.sendTemplateSMS(mobile,templateId,new String[]{smsCode,"2"});
		this.printSmsInfo(result, "");
		
	}
	private void printSmsInfo(HashMap<String, Object> result,String mobile){
		saveLogs("printSmsInfo:短信发送 手机号:"+mobile+"  SDKTestGetSubAccounts result=" + result,null);
		if("000000".equals(result.get("statusCode"))){
			//正常返回输出data包体信息（map）
			HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
			Set<String> keySet = data.keySet();
			for(String key:keySet){
				Object object = data.get(key);
				System.out.println(key +" = "+object);
			}
		}else{
			//异常返回输出错误码和错误信息
			saveLogs("printSmsInfo:错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"),null);
		}
	}
	
	private boolean sendCharging(String sg_id,String co_type,String co_num,ChargingPilePO chargingPilePO,Dto odto){
		boolean flag=false;
		//Integer.toHexString(); 0：金额，1：时间，2：度数，3：充满
		 byte[] data_out;
         String msg_welcome = Helper.fillString('0', 32*2);
          
         
		String cmd="E20409";
		if("0".equals(co_type)){//金额
			BigDecimal co_num_=new BigDecimal(co_num);
			co_num_=co_num_.divide(new BigDecimal(AOSCxt.getParam("electricity")),2, BigDecimal.ROUND_DOWN);//度数
			BigDecimal co_num_max=new BigDecimal(65535);
			
			if(co_num_.compareTo(co_num_max)==1){//度数大于65535
				this.failMsg(odto, "充电金额过大");
				saveLogs3("SC④ 充电金额过大,当前金额为:"+co_num,sg_id,"SC④");
				return false;
			}else if(co_num_.compareTo(BigDecimal.ZERO)==0){
				this.failMsg(odto, "充电金额不能为0");
				saveLogs3("SC④ 充电金额不能为0",sg_id,"SC④");
			
				return false;
			}else if(co_num_.compareTo(BigDecimal.ZERO)==-1){	
				this.failMsg(odto, "充电金额不能小于0");
				saveLogs3("SC④ 充电金额不能小于0",sg_id,"SC④");
				return false;
			}else{
				co_num=Integer.toHexString(co_num_.intValue());//转为十六进制
				co_num=String.format("%4s",co_num);
				String finalResult = co_num.replace(" ", "0");
				cmd=cmd+"00000000"+finalResult;
				msg_welcome =msg_welcome.replaceFirst("^000000000000000000", cmd);
			}
			
		}else if("1".equals(co_type)){//时间
			BigDecimal co_num_=new BigDecimal(co_num).multiply(new BigDecimal("60")).multiply(new BigDecimal("60"));//转为秒
			Long co_num_l=co_num_.longValue();
			long days = co_num_l/(60*60*24);
			long hours = (co_num_l%(60 *60 *24))/(60*60);
			long minutes = (co_num_l%(60*60))/60;
			String days_=Integer.toHexString(Integer.valueOf(String.valueOf(days)));
			String hours_=Integer.toHexString(Integer.valueOf(String.valueOf(hours)));
			String minutes_=Integer.toHexString(Integer.valueOf(String.valueOf(minutes)));
			cmd=cmd+"00"+days_+hours_+minutes_+"0000";
			msg_welcome =msg_welcome.replaceFirst("^000000000000000000", cmd);
			
		}else if("2".equals(co_type)){//度数
			BigDecimal co_num_=new BigDecimal(co_num);
			BigDecimal co_num_max=new BigDecimal(65535);
			
			if(co_num_.compareTo(co_num_max)==1){//度数大于65535
				this.failMsg(odto, "充电度数不能大于65535");
				saveLogs3("SC④ 充电度数不能大于65535",sg_id,"SC④");
				return false;
			}else if(co_num_.compareTo(BigDecimal.ZERO)==0){
				this.failMsg(odto, "充电度数不能为0");
				saveLogs3("SC④ 充电度数不能为0",sg_id,"SC④");
			
				return false;
			}else if(co_num_.compareTo(BigDecimal.ZERO)==-1){	
				this.failMsg(odto, "充电度数不能小于0");
				saveLogs3("SC④ 充电度数不能小于0",sg_id,"SC④");
				return false;
			}else{
				co_num=Integer.toHexString(Integer.valueOf(co_num));//转为十六进制
				co_num=String.format("%4s",co_num);
				String finalResult = co_num.replace(" ", "0");
				cmd=cmd+"00000000"+finalResult;
				msg_welcome =msg_welcome.replaceFirst("^000000000000000000", cmd);
			}
			
		}else if("3".equals(co_type)){//充满
			cmd=cmd+"01";
			msg_welcome =msg_welcome.replaceFirst("^00000000", cmd);
		}
		data_out= Helper.hexStringToByteArray(msg_welcome);
		try {
			Socket socket=Helper.socketMap.get(sg_id);
			if(null!=socket){
				socket.getOutputStream().write(data_out);
				System.out.println("APP发送充电请求数据:"+msg_welcome);
				saveLogs3("SC④ APP发送充电请求数据:"+msg_welcome,sg_id,"SC④");
				flag=true;
			}else{
				this.failMsg(odto, "充电桩未连接");
				System.out.println("APP发送充电请求数据:充电桩未连接"+msg_welcome);
				saveLogs3("SC④ APP发送充电请求数据:充电桩未连接",sg_id,"SC④");
			}
			
			
		} catch (IOException e) {
			if("Socket is closed".equals(e.getMessage())){
				saveLogs3("APP发送充电请求数据异常:充电桩未连接"+msg_welcome,sg_id,"SC④");
				this.failMsg(odto, "APP发送充电请求数据异常:充电桩未连接");
			}else{
				saveLogs3("APP发送充电请求数据异常:"+msg_welcome,sg_id,"SC④");
				this.failMsg(odto, "APP发送充电请求数据异常");
			}
			
			
			e.printStackTrace();
		}
		return flag;
	}
	private boolean stopCharging1(String sg_id,Dto odto){
		boolean flag=false;
		 byte[] data_out;
         String msg_welcome = Helper.fillString('0', 32*2);
		String cmd="E20803";
		msg_welcome =msg_welcome.replaceFirst("^000000", cmd);
		data_out= Helper.hexStringToByteArray(msg_welcome);
		try {
			Socket socket=Helper.socketMap.get(sg_id);
			if(null!=socket){
				socket.getOutputStream().write(data_out);
				System.out.println("APP发送停止充电数据:"+msg_welcome);
				saveLogs3("SC⑧ APP发送停止充电数据:"+msg_welcome,sg_id,"SC⑧");
				flag=true;
			}else{
				this.failMsg(odto, "充电桩未连接");
				System.out.println("APP发送停止充电数据:充电桩未连接"+msg_welcome);
				saveLogs3("SC⑧ APP发送停止充电数据:充电桩未连接"+msg_welcome,sg_id,"SC⑧");
			}
			
			
		} catch (IOException e) {
			saveLogs3("SC⑧ APP发送停止充电数据异常:"+msg_welcome,sg_id,"SC⑧");
			this.failMsg(odto, "APP发送停止充电数据异常");
			e.printStackTrace();
		}
		return flag;
	}
	
	 public void saveLogs3(String content,String cp_id){
			CommonLogsPO commonLogsPO=new CommonLogsPO();
			commonLogsPO.setLog_id(AOSId.appId(SystemCons.ID.SYSTEM));
			commonLogsPO.setCreate_date(AOSUtils.getDateTime());
			commonLogsPO.setIs_del("0");
			commonLogsPO.setContent(content);
			commonLogsPO.setOper_id(cp_id);
			commonLogsPO.setOper_name("api");
			commonLogsDao.insert(commonLogsPO);
		}
	 public void saveLogs3(String content,String cp_id,String log_type){
			CommonLogsPO commonLogsPO=new CommonLogsPO();
			commonLogsPO.setLog_id(AOSId.appId(SystemCons.ID.SYSTEM));
			commonLogsPO.setCreate_date(AOSUtils.getDateTime());
			commonLogsPO.setIs_del("0");
			commonLogsPO.setContent(content);
			commonLogsPO.setOper_id(cp_id);
			commonLogsPO.setOper_name("api");
			commonLogsPO.setLog_type(log_type);
			commonLogsDao.insert(commonLogsPO);
		}

}
