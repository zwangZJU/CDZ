/**
 * 
 */
package utils;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleInsets;

import aos.framework.core.id.AOSId;
import aos.system.common.utils.SystemCons;


public class LineChart {
	 private static XYDataset createDataset() {
	        TimeSeries s1 = new TimeSeries("", Day.class);
	        
	        s1.add(new Day(3, 2, 2017), 150);
	        s1.add(new Day(4, 2, 2017), 300);
	        s1.add(new Day(5, 2, 2017), 500);
	        s1.add(new Day(6, 2, 2017), 800);
	        s1.add(new Day(3, 1, 2017), 900);
	        s1.add(new Day(4, 1, 2017), 0);
	        s1.add(new Day(5, 1, 2017), 200);
	        s1.add(new Day(6, 1, 2017), 0);

	       /* TimeSeries s2 = new TimeSeries("居民", Month.class);
	        s2.add(new Month(12, 2005), 0.13);
	        s2.add(new Month(3, 2006), 0.14);
	        s2.add(new Month(6, 2006), 0.15);
	        s2.add(new Month(9, 2006), 0.175);
	        s2.add(new Month(12, 2006), 0.19);
	        s2.add(new Month(3, 2007), 0.22);
	        s2.add(new Month(6, 2007), 0.24);
	        s2.add(new Month(9, 2007), 0.31);*/

	        TimeSeriesCollection dataset = new TimeSeriesCollection();
	        dataset.addSeries(s1);
	        //dataset.addSeries(s2);

	        return dataset;
	    }
	 
	 private static JFreeChart createChart(XYDataset dataset,String title,String xTitle,String yTitle)
	    {
		   //创建主题样式  
		   StandardChartTheme standardChartTheme=new StandardChartTheme("CN");  
		   //设置标题字体  
		   standardChartTheme.setExtraLargeFont(new Font("隶书",Font.BOLD,20));  
		   //设置图例的字体  
		   standardChartTheme.setRegularFont(new Font("宋书",Font.PLAIN,15));  
		   //设置轴向的字体  
		   standardChartTheme.setLargeFont(new Font("宋书",Font.PLAIN,15));  
		   //应用主题样式  
		   ChartFactory.setChartTheme(standardChartTheme); 
		   
		 	JFreeChart jfreechart = ChartFactory.createTimeSeriesChart(
		 			title,          //报表标题
		 			xTitle,                                 //x轴标签 
		 			yTitle,                               //y轴标签 
	                dataset,                               //数据集 
	                true,                                  //是否显示图例
	                true,                                  //是否有工具条提示
	                false                                  //是否有链接
	                );
	        jfreechart.setBackgroundPaint(Color.white);
	        /*jfreechart.addSubtitle(new TextTitle("------《2008年银行业行业报告》",
	                new Font("Dialog", Font.ITALIC, 10)));*/
	        
	        XYPlot plot = (XYPlot) jfreechart.getPlot();
	        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
	        XYItemRenderer r = plot.getRenderer();
	        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
	        renderer.setBaseShapesVisible(true);
	       
	        // series 点（即数据点）可见
	        renderer.setBaseLinesVisible(true);
	        // series 点（即数据点）间有连线可见 显示折点数据
	        renderer.setBaseItemLabelGenerator(new StandardXYItemLabelGenerator());
	        renderer.setBaseItemLabelsVisible(true);

	        
	        
	        DateAxis axis = (DateAxis) plot.getDomainAxis();
	        axis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM-dd"));
	        axis.setVerticalTickLabels(true);
	        axis.setTickUnit(new DateTickUnit(DateTickUnit.DAY,1));
	        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
	        rangeAxis.setNumberFormatOverride(new DecimalFormat("0.0"));
	        
	        
	        


	        return jfreechart;
	    }
	 
	  public static String generateLineChart(HttpServletRequest request,XYDataset xyDataset,String title,String xTitle,String yTitle)
	    {
	        String filename = null;
	        
	        JFreeChart chart = createChart(xyDataset, title, xTitle, yTitle);
	        String savePath = request.getServletContext().getRealPath("/myupload");
	        filename=AOSId.appId(SystemCons.ID.SYSTEM)+".png";
	        try {
				ChartUtilities.saveChartAsPNG(new File(savePath+"/"+filename),chart, 800, 400);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
	        return filename;
	    }
}
