/**
 * 
 */
package utils;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import po.ChargingOrdersPO;
import po.CommonBackupPO;
import po.SubscribePO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import aos.framework.core.dao.SqlDao;
import aos.framework.core.id.AOSId;
import aos.framework.core.typewrap.Dto;
import aos.framework.core.typewrap.Dtos;
import aos.framework.core.utils.AOSCxt;
import aos.framework.core.utils.AOSPropertiesHandler;
import aos.framework.core.utils.AOSUtils;
import aos.framework.dao.AosDicDao;
import aos.framework.dao.AosUserDao;
import aos.system.common.utils.SystemCons;
import dao.ChargingOrdersDao;
import dao.CommonBackupDao;
import dao.SubscribeDao;



@Service
public class TaskJob {
	
	@Autowired
	protected SqlDao sqlDao;
	@Autowired
	private AosUserDao aosUserDao;
	
	@Autowired
	private AosDicDao aosDicDao;
	@Autowired
	private SubscribeDao subscribeDao;
	
	@Autowired
	private CommonBackupDao commonBackupDao;
	@Autowired
	private ChargingOrdersDao chargingOrdersDao;
	
	
	public void job1() {  
       System.out.println("任务进行中。。。");  
    }
	
	public void addBackup(){
		 try {
	            Runtime rt = Runtime.getRuntime();
	            // 调用 调用mysql的安装目录的命令
	            Process child = rt
	                    .exec(AOSPropertiesHandler.getProperty("mysqldumpPath"));
	            // 设置导出编码为utf-8。这里必须是utf-8
	            // 把进程执行中的控制台输出信息写入.sql文件，即生成了备份文件。注：如果不对控制台信息进行读出，则会导致进程堵塞无法运行
	            InputStream in = child.getInputStream();// 控制台的输出信息作为输入流
	 
	            InputStreamReader xx = new InputStreamReader(in, "utf-8");
	            // 设置输出流编码为utf-8。这里必须是utf-8，否则从流中读入的是乱码
	 
	            String inStr;
	            StringBuffer sb = new StringBuffer("");
	            String outStr;
	            // 组合控制台输出信息字符串
	            BufferedReader br = new BufferedReader(xx);
	            while ((inStr = br.readLine()) != null) {
	                sb.append(inStr + "\r\n");
	            }
	            outStr = sb.toString();
	            String fileName=AOSUtils.getDateStr("yyyyMMddHHmmss")+".sql";
	            // 要用来做导入用的sql目标文件：
	            FileOutputStream fout = new FileOutputStream(AOSPropertiesHandler.getProperty("mysqlBackUpFilePath")+"\\"+fileName);
	            OutputStreamWriter writer = new OutputStreamWriter(fout, "utf-8");
	            writer.write(outStr);
	            writer.flush();
	            in.close();
	            xx.close();
	            br.close();
	            writer.close();
	            fout.close();
	            CommonBackupPO commonBackupPO = new CommonBackupPO();
	    		//commonBackupPO.copyProperties(inDto);
	    		commonBackupPO.setFile_name(fileName);
	    		commonBackupPO.setFile_path(AOSPropertiesHandler.getProperty("mysqlBackUpFilePath"));
	    		commonBackupPO.setBackup_id(AOSId.appId(SystemCons.ID.SYSTEM));
	    		commonBackupPO.setCreate_date(AOSUtils.getDateTime());
	    		commonBackupPO.setIs_del("0");
	    		commonBackupPO.setOper_id("系统备份");
	    		commonBackupDao.insert(commonBackupPO);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		
	}
	/**
	 * 取消预约
	 */
	public void cancelSubcribe(){
		
		Dto pDto=Dtos.newDto("is_del","0");
		pDto.put("status", "0");
		pDto.put("overdue","true");
		List<SubscribePO> list= subscribeDao.list(pDto);
		for(SubscribePO po:list){
			SubscribePO po_=new SubscribePO();
			po_.setS_id(po.getS_id());
			po_.setStatus("2");
			subscribeDao.updateByKey(po_);
		}
		
	}
	/**
	 * 检测优惠券是否过期
	 */
	@Transactional
	public void checkVoucher(){
		Dto pDto=Dtos.newDto("is_del","0");
		sqlDao.update("Voucher.checkUpdateVouchersList", pDto);
	}
	
	/**
	 * 当订单超过5分钟没有数据更新，则取消或完成订单
	 * @param args
	 */
	@Transactional
	public void checkListChargingOrderss(){
		String checkOrderDate=AOSCxt.getParam("checkOrderDate");//
		int checkOrderDate_=5;
		if(AOSUtils.isNotEmpty(checkOrderDate)){
			checkOrderDate_=Integer.valueOf(checkOrderDate);
		}
		List<Dto> checkListChargingOrderss=sqlDao.list("ChargingOrders.checkListChargingOrderss", Dtos.newDto("checkOrderDate",checkOrderDate_));
		for(Dto dto:checkListChargingOrderss){
			if("-2".equals(dto.getString("status_"))){
				ChargingOrdersPO chargingOrdersPO_=new ChargingOrdersPO();
				chargingOrdersPO_.setCo_id(dto.getString("co_id"));
				chargingOrdersPO_.setStatus_("-1");
				chargingOrdersDao.updateByKey(chargingOrdersPO_);
			}else if("0".equals(dto.getString("status_"))){
				if("0".equals(dto.getString("total_amt"))||"0.0".equals(dto.getString("total_amt"))||"0.00".equals(dto.getString("total_amt"))){
					ChargingOrdersPO chargingOrdersPO_=new ChargingOrdersPO();
					chargingOrdersPO_.setCo_id(dto.getString("co_id"));
					chargingOrdersPO_.setStatus_("-1");
					chargingOrdersDao.updateByKey(chargingOrdersPO_);
				}else{
					ChargingOrdersPO chargingOrdersPO_=new ChargingOrdersPO();
					chargingOrdersPO_.setCo_id(dto.getString("co_id"));
					chargingOrdersPO_.setStatus_("1");
					chargingOrdersDao.updateByKey(chargingOrdersPO_);
				}
			}
		}
	}
	public static void main(String[] args) {
		//System.out.println(AOSUtils.getDateStr(AOSUtils.dateAdd(new Date(), Calendar.DATE, -1), "yyyy-MM-dd"));
		//new TaskJob().footballMatchJob();
	}
	public static String getfirstDayofMonth(){
	    String firstDayofMonth;
	    Calendar calendar = Calendar.getInstance();
	    calendar.add(Calendar.MONTH, -1);
	    calendar.set(Calendar.DAY_OF_MONTH, 1);
	    firstDayofMonth = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
	    return firstDayofMonth+" 00:00:00";
	}
	public static String getlastDayofMonth(){
	    String lastDayofMonth;
	    Calendar calendar = Calendar.getInstance();
	    calendar.set(Calendar.DAY_OF_MONTH, 1); 
	    calendar.add(Calendar.DATE, -1);
	    lastDayofMonth = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
	    return lastDayofMonth+" 23:59:59";
	}
	public static String getCurDay(){
	    String lastDayofMonth;
	    Calendar calendar = Calendar.getInstance();
	    lastDayofMonth = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
	    return lastDayofMonth+" 23:59:59";
	}
	public static String getPreMonth(){
	    String preMonth;
	    Calendar calendar = Calendar.getInstance();
	    calendar.set(Calendar.DAY_OF_MONTH, 1); 
	    calendar.add(Calendar.DATE, -1);
	    preMonth = new SimpleDateFormat("yyyy-MM").format(calendar.getTime());
	    return preMonth;
	}
	public static String getPreMonth2(){
	    String preMonth;
	    Calendar calendar = Calendar.getInstance();
	    calendar.set(Calendar.DAY_OF_MONTH, 1); 
	    calendar.add(Calendar.DATE, -1);
	    preMonth = new SimpleDateFormat("yyyyMM").format(calendar.getTime());
	    return preMonth;
	}
	
	 public static Map<String, Object> jsonToMap(Object jsonObj) {
	        JSONObject jsonObject = JSONObject.fromObject(jsonObj);
	        Map<String, Object> map = (Map)jsonObject;
	        return map;
    }
	public static List jsonToList(Object jsonObj) {
		JSONArray jsonarray = JSONArray.fromObject(jsonObj); 
		List list = (List)JSONArray.toList(jsonarray);  
	        return list;
	 }
}
