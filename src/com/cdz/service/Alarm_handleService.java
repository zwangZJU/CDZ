/**
 * 
 */
package service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.cloopen.rest.sdk.CCPRestSmsSDK;

import aos.framework.core.cache.CacheMasterDataService;
import aos.framework.core.id.AOSId;
import aos.framework.core.service.CDZBaseController;
import aos.framework.core.typewrap.Dto;
import aos.framework.core.typewrap.Dtos;
import aos.framework.core.utils.AOSCodec;
import aos.framework.core.utils.AOSCxt;
import aos.framework.core.utils.AOSJson;
import aos.framework.core.utils.AOSUtils;
import aos.framework.dao.AosParamsDao;
import aos.framework.dao.AosUserDao;
import aos.framework.web.router.HttpModel;
import aos.system.common.utils.SystemCons;
import aos.system.modules.cache.CacheUserDataService;
import controller.MultiServerThread;
import dao.ActivityDao;
import dao.ActivityRuleDao;
import dao.AdvertDao;
import dao.AdvertTrafficDao;
import dao.Alarm_logDao;
import dao.ArticleDao;
import dao.Basic_userDao;
import dao.ChargingOrdersDao;
import dao.ChargingPileDao;
import dao.CommonCommentDao;
import dao.DepositListDao;
import dao.DeviceDao;
import dao.MembersDao;
import dao.MessagesDao;
import dao.OrdersPayDao;
import dao.RegionDao;
import dao.Repair_logDao;
import dao.SubscribeDao;
import dao.VoucherDao;
import po.Alarm_logPO;
import po.Basic_userPO;
import po.ChargingPilePO;
import po.CommonLogsPO;
import po.DevicePO;
import po.Repair_logPO;
import utils.Helper;


/**
 * @author Administrator
 *cacheUserDataService.getDealerDTO(juid)
 */
@Service
public class Alarm_handleService extends CDZBaseController {
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
	
	private Socket socket = null;
	
	private String ascii="";
	
	private String ascii1="";
	
	private int j=0;
	
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
	Basic_userDao basic_userDao;
	@Autowired
	DepositListDao depositListDao;
	@Autowired
	SubscribeDao subscribeDao;
	@Autowired
	DeviceDao deviceDao;
	@Autowired
	Repair_logDao repair_logDao;
	@Autowired
	Alarm_logDao alarm_logDao;
	
	public void update_Alarm_log_and_device(HttpModel httpModel) 
	{
		System.out.println("update_Alarm_log_and_device success");
		
	}
	
	public void selectAllVersionNum(HttpModel httpModel)
	{
		System.out.println("selectAllVersionNum success");
	}
	
	public void upgradeOne(HttpModel httpModel)
	{
		System.out.println("upgradeOne success");
	}
	
	

}