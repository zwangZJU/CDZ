package controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import aos.framework.core.service.CDZBaseController;
import aos.framework.core.typewrap.Dto;
import aos.framework.core.utils.AOSJson;
import aos.framework.core.utils.AOSPropertiesHandler;
import aos.framework.web.router.HttpModel;

/**
 * 统计
 * 
 * @author duanchongfeng
 * @date 2017-04-21 20:54:18
 *
 */
@Service
public class TongJiController extends CDZBaseController {

	

	/**
	 * 统计
	 * 
	 * @param httpModel
	 * @return
	 */
	public void init(HttpModel httpModel) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
        Calendar c = Calendar.getInstance();    
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
        String first = format.format(c.getTime());
		Calendar ca = Calendar.getInstance();    
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));  
        String last = format.format(ca.getTime());
        httpModel.setAttribute("date_start", first);
        httpModel.setAttribute("date_end", last);
		httpModel.setAttribute("juid", httpModel.getInDto().getString("juid"));
		httpModel.setAttribute("httpUrl", AOSPropertiesHandler.getProperty("httpUrl"));
		httpModel.setViewPath("myproject/tongji/tongji1.jsp");
	}
	public void huyuantongji_chart(HttpModel httpModel) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
        Calendar c = Calendar.getInstance();    
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
        String first = format.format(c.getTime());
		Calendar ca = Calendar.getInstance();    
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));  
        String last = format.format(ca.getTime());
        httpModel.setAttribute("date_start", first);
        httpModel.setAttribute("date_end", last);
		httpModel.setAttribute("juid", httpModel.getInDto().getString("juid"));
		httpModel.setViewPath("myproject/tongji/huyuantongji_chart.jsp");
	}
	
	public void listMemberssStat(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		List<Dto> ordersDtos = sqlDao.list("Members.listMemberssStatPage", qDto);
		httpModel.setOutMsg(AOSJson.toGridJson(ordersDtos, qDto.getPageTotal()));
	}
	public void listMemberssStatQuerySummary(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		//根据inDto参数去统计相关信息
		Dto outDto = sqlDao.selectDto("Members.listMemberssStatQuerySummary", inDto);
		httpModel.setOutMsg(AOSJson.toJson(outDto));
	}
	public void listMemberssStatFigure(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");  
		String date_start=inDto.getString("date_start");
		String date_end=inDto.getString("date_end");
		/*try {
			Date date_start1 = sdf.parse(date_start);
			Date date_end1 = sdf.parse(date_end);
			//int day=(int)(date_end1.getTime()-date_start1.getTime())/(1000*60*60*24);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		//根据inDto参数去统计相关信息
		List<Dto> ordersDtos = sqlDao.list("Members.listMemberssStatFigure", inDto);
		
		httpModel.setOutMsg(AOSJson.toJson(ordersDtos));
	}

	public void advert(HttpModel httpModel) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
        Calendar c = Calendar.getInstance();    
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
        String first = format.format(c.getTime());
		Calendar ca = Calendar.getInstance();    
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));  
        String last = format.format(ca.getTime());
        httpModel.setAttribute("date_start", first);
        httpModel.setAttribute("date_end", last);
		httpModel.setAttribute("juid", httpModel.getInDto().getString("juid"));
		httpModel.setAttribute("httpUrl", AOSPropertiesHandler.getProperty("httpUrl"));
		httpModel.setViewPath("myproject/tongji/advert.jsp");
	}
	public void advert_chart(HttpModel httpModel) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
        Calendar c = Calendar.getInstance();    
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
        String first = format.format(c.getTime());
		Calendar ca = Calendar.getInstance();    
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));  
        String last = format.format(ca.getTime());
        httpModel.setAttribute("date_start", first);
        httpModel.setAttribute("date_end", last);
		httpModel.setAttribute("juid", httpModel.getInDto().getString("juid"));
		httpModel.setViewPath("myproject/tongji/advert_chart.jsp");
	}
	
	public void listAdvertTrafficsStat(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		List<Dto> ordersDtos = sqlDao.list("AdvertTraffic.listAdvertTrafficsStatPage", qDto);
		httpModel.setOutMsg(AOSJson.toGridJson(ordersDtos, qDto.getPageTotal()));
	}
	public void listAdvertTrafficsStatQuerySummary(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		//根据inDto参数去统计相关信息
		Dto outDto = sqlDao.selectDto("AdvertTraffic.listAdvertTrafficsStatQuerySummary", inDto);
		httpModel.setOutMsg(AOSJson.toJson(outDto));
	}
	public void listAdvertTrafficsStatFigure(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");  
		String date_start=inDto.getString("date_start");
		String date_end=inDto.getString("date_end");
		/*try {
			Date date_start1 = sdf.parse(date_start);
			Date date_end1 = sdf.parse(date_end);
			//int day=(int)(date_end1.getTime()-date_start1.getTime())/(1000*60*60*24);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		//根据inDto参数去统计相关信息
		List<Dto> ordersDtos = sqlDao.list("AdvertTraffic.listAdvertTrafficsStatFigure", inDto);
		
		httpModel.setOutMsg(AOSJson.toJson(ordersDtos));
	}
	
	//
	public void order(HttpModel httpModel) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
        Calendar c = Calendar.getInstance();    
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
        String first = format.format(c.getTime());
		Calendar ca = Calendar.getInstance();    
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));  
        String last = format.format(ca.getTime());
        httpModel.setAttribute("date_start", first);
        httpModel.setAttribute("date_end", last);
		httpModel.setAttribute("juid", httpModel.getInDto().getString("juid"));
		httpModel.setAttribute("httpUrl", AOSPropertiesHandler.getProperty("httpUrl"));
		httpModel.setViewPath("myproject/tongji/order.jsp");
	}
	public void order_chart(HttpModel httpModel) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
        Calendar c = Calendar.getInstance();    
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
        String first = format.format(c.getTime());
		Calendar ca = Calendar.getInstance();    
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));  
        String last = format.format(ca.getTime());
        httpModel.setAttribute("date_start", first);
        httpModel.setAttribute("date_end", last);
		httpModel.setAttribute("juid", httpModel.getInDto().getString("juid"));
		httpModel.setViewPath("myproject/tongji/order_chart.jsp");
	}
	
	public void chargingOrdersStat(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		List<Dto> ordersDtos = sqlDao.list("ChargingOrders.chargingOrdersStatPage", qDto);
		httpModel.setOutMsg(AOSJson.toGridJson(ordersDtos, qDto.getPageTotal()));
	}
	public void chargingOrdersStatQuerySummary(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		//根据inDto参数去统计相关信息
		Dto outDto = sqlDao.selectDto("ChargingOrders.chargingOrdersStatQuerySummary", inDto);
		httpModel.setOutMsg(AOSJson.toJson(outDto));
	}
	public void chargingOrdersStatFigure(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");  
		String date_start=inDto.getString("date_start");
		String date_end=inDto.getString("date_end");
		/*try {
			Date date_start1 = sdf.parse(date_start);
			Date date_end1 = sdf.parse(date_end);
			int day=(int)(date_end1.getTime()-date_start1.getTime())/(1000*60*60*24);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		//根据inDto参数去统计相关信息
		List<Dto> ordersDtos = sqlDao.list("ChargingOrders.chargingOrdersStatFigure", inDto);
		
		httpModel.setOutMsg(AOSJson.toJson(ordersDtos));
	}
	public void sales(HttpModel httpModel) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
        Calendar c = Calendar.getInstance();    
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
        String first = format.format(c.getTime());
		Calendar ca = Calendar.getInstance();    
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));  
        String last = format.format(ca.getTime());
        httpModel.setAttribute("date_start", first);
        httpModel.setAttribute("date_end", last);
		httpModel.setAttribute("juid", httpModel.getInDto().getString("juid"));
		httpModel.setAttribute("httpUrl", AOSPropertiesHandler.getProperty("httpUrl"));
		httpModel.setViewPath("myproject/tongji/sales.jsp");
	}
	public void sales_chart(HttpModel httpModel) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
        Calendar c = Calendar.getInstance();    
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
        String first = format.format(c.getTime());
		Calendar ca = Calendar.getInstance();    
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));  
        String last = format.format(ca.getTime());
        httpModel.setAttribute("date_start", first);
        httpModel.setAttribute("date_end", last);
		httpModel.setAttribute("juid", httpModel.getInDto().getString("juid"));
		httpModel.setViewPath("myproject/tongji/sales_chart.jsp");
	}
	
	public void chargingOrdersSalesStat(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		List<Dto> ordersDtos = sqlDao.list("ChargingOrders.chargingOrdersSalesStatPage", qDto);
		httpModel.setOutMsg(AOSJson.toGridJson(ordersDtos, qDto.getPageTotal()));
	}
	public void chargingOrdersSalesStatQuerySummary(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		//根据inDto参数去统计相关信息
		Dto outDto = sqlDao.selectDto("ChargingOrders.chargingOrdersSalesStatQuerySummary", inDto);
		httpModel.setOutMsg(AOSJson.toJson(outDto));
	}
	public void chargingOrdersSalesStatFigure(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");  
		String date_start=inDto.getString("date_start");
		String date_end=inDto.getString("date_end");
		/*try {
			Date date_start1 = sdf.parse(date_start);
			Date date_end1 = sdf.parse(date_end);
			int day=(int)(date_end1.getTime()-date_start1.getTime())/(1000*60*60*24);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		//根据inDto参数去统计相关信息
		List<Dto> ordersDtos = sqlDao.list("ChargingOrders.chargingOrdersSalesStatFigure", inDto);
		
		httpModel.setOutMsg(AOSJson.toJson(ordersDtos));
	}
	public void vouchers(HttpModel httpModel) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
        Calendar c = Calendar.getInstance();    
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
        String first = format.format(c.getTime());
		Calendar ca = Calendar.getInstance();    
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));  
        String last = format.format(ca.getTime());
        httpModel.setAttribute("date_start", first);
        httpModel.setAttribute("date_end", last);
		httpModel.setAttribute("juid", httpModel.getInDto().getString("juid"));
		httpModel.setAttribute("httpUrl", AOSPropertiesHandler.getProperty("httpUrl"));
		httpModel.setViewPath("myproject/tongji/vouchers.jsp");
	}
	public void vouchers_chart(HttpModel httpModel) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
        Calendar c = Calendar.getInstance();    
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
        String first = format.format(c.getTime());
		Calendar ca = Calendar.getInstance();    
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));  
        String last = format.format(ca.getTime());
        httpModel.setAttribute("date_start", first);
        httpModel.setAttribute("date_end", last);
		httpModel.setAttribute("juid", httpModel.getInDto().getString("juid"));
		httpModel.setViewPath("myproject/tongji/vouchers_chart.jsp");
	}
	
	public void vouchersStat(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		List<Dto> ordersDtos = sqlDao.list("Voucher.vouchersStatPage", qDto);
		httpModel.setOutMsg(AOSJson.toGridJson(ordersDtos, qDto.getPageTotal()));
	}
	public void vouchersStatQuerySummary(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		//根据inDto参数去统计相关信息
		Dto outDto = sqlDao.selectDto("Voucher.vouchersStatQuerySummary", inDto);
		httpModel.setOutMsg(AOSJson.toJson(outDto));
	}
	public void vouchersStatFigure(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");  
		String date_start=inDto.getString("date_start");
		String date_end=inDto.getString("date_end");
		/*try {
			Date date_start1 = sdf.parse(date_start);
			Date date_end1 = sdf.parse(date_end);
			int day=(int)(date_end1.getTime()-date_start1.getTime())/(1000*60*60*24);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		//根据inDto参数去统计相关信息
		List<Dto> ordersDtos = sqlDao.list("Voucher.vouchersStatFigure", inDto);
		
		httpModel.setOutMsg(AOSJson.toJson(ordersDtos));
	}
	
}
