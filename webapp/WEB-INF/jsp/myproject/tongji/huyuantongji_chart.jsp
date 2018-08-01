<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
 <%
 pageContext.setAttribute("ctx", pageContext.getServletContext().getContextPath());
%>
	<script src="${ctx}/static/jqplot/jquery.min.js"></script>
	<script src="${ctx}/static/jqplot/jquery.jqplot.min.js"></script>
	<script src="${ctx}/static/jqplot/plugins/jqplot.highlighter.js"></script>
	<script src="${ctx}/static/jqplot/plugins/jqplot.cursor.js"></script>
	<script src="${ctx}/static/jqplot/plugins/jqplot.canvasTextRenderer.js"></script>
	<script src="${ctx}/static/jqplot/plugins/jqplot.canvasAxisTickRenderer.js"></script>
	<script src="${ctx}/static/jqplot/plugins/jqplot.categoryAxisRenderer.js"></script>
	<script src="${ctx}/static/jqplot/plugins/jqplot.pointLabels.js"></script>
	<script src="${ctx}/static/jqplot/plugins/jqplot.dateAxisRenderer.js"></script>
	<script src="${ctx}/static/jqplot/plugins/jqplot.barRenderer.js"></script>
	<link href="${ctx}/static/jqplot/jquery.jqplot.min.css" rel="stylesheet" />

</head>
<body>
 <div id="UserChart" style=" width:1000px; height:400px;"></div>
 <script class="code" type="text/javascript">
 var lineMemory =null;
 function test(date_start,date_end){
	 	var cosPoints = []; 
	    var cosPoints1 = [];
			$.post("${ctx}/http/do.jhtml?router=tongJiController.listMemberssStatFigure&juid=${juid}",
		  {
				date_start:date_start,
				date_end:date_end
		  },
		  function(datas,status){
			  datas=jQuery.parseJSON(datas);
			  //alert(JSON.stringify(datas));
			  for(i=0;i<datas.length;i++){
				  var data=datas[i];
				  cosPoints1.push([data.create_time, data.num]);
			  }
			  cosPoints.push(cosPoints1);
			  jqplot_fn(cosPoints,date_start,date_end);
		  });
}
$(document).ready(function(){
	var cosPoints = []; 
    var cosPoints1 = [];
    var date_start="${date_start}";
    var date_end="${date_end}";
		$.post("${ctx}/http/do.jhtml?router=tongJiController.listMemberssStatFigure&juid=${juid}",
	  {
			date_start:"${date_start}",
			date_end:"${date_end}"
	  },
	  function(datas,status){
		  datas=jQuery.parseJSON(datas);
		  for(i=0;i<datas.length;i++){
			  var data=datas[i];
			  cosPoints1.push([data.create_time, data.num]);
		  }
		  cosPoints.push(cosPoints1);
		  jqplot_fn(cosPoints,date_start,date_end);
	  });
	  
	});
function jqplot_fn(array,date_start,date_end){
	if(lineMemory==null){
		lineMemory=$.jqplot('UserChart',
				array,
	            {
	                //图标标题
	                title: {
	                    text: date_start+'至'+date_end+'新注册会员',
	                    show: true,
	                    fontFamily: "Microsoft Yahei",
	                    fontSize: "15pt",//pt磅，em相对字体大小
	                    textColor: "#000"
	                },
	                //提示栏显示配置，通常在右上角显示
	                legend: {
	                    show: true,
	                    location: 'ne',// 提示栏信息显示位置（英文方向的首写字母）: n, ne, e, se, s, sw, w, nw.
	                    placement: 'outside'
	                },
	                //提示栏显示信息配置，多个数据分类需配置多个
	                series: [{
	                    label: '平台会员',//按data先后顺序显示每种分类名称
	                    markerOptions:
	                        {
	                            size: 6,
	                            style: 'circle'
	                        }
	                }
	                ],
	                //节点数值提示，需要引用jqplot.pointLabels.js
	                seriesDefaults:{
	                    pointLabels: { show: true, ypadding: -1 } //数据点标签
	                },
	                //鼠标放在节点时突出显示当前结点,需引用jqplot.highlighter.js
	                highlighter: {
	                    show: true,
	                    sizeAdjust: 6,//当鼠标移动到数据点上时，数据点扩大的增量
	                    fadeTooltip: true,//设置提示信息栏出现和消失的方式（是否淡入淡出）
	                    lineWidthAdjust: 2.5,   //当鼠标移动到放大的数据点上时，设置增大的数据点的宽度
	                    tooltipOffset: 2,       // 提示信息栏据被高亮显示的数据点的偏移位置，以像素计
	                    tooltipLocation: 'nw' // 提示信息显示位置（英文方向的首写字母）: n, ne, e, se, s, sw, w, nw.
	                },
	                //鼠标在图标中的提示位置信息，需引用jqplot.cursor.js
	                cursor: {
	                    show: true,
	                    showTooltip: true,// 是否显示提示信息栏
	                    followMouse: false,     //光标的提示信息栏是否随光标（鼠标）一起移动
	                    tooltipLocation: 'se', // 光标提示信息栏的位置设置。如果followMouse=true,那么该位置为
	                    //提示信息栏相对于光标的位置。否则，为光标提示信息栏在图标中的位置
	                    // 该属性可选值： n, ne, e, se, etc.
	                    tooltipOffset: 6,     //提示信息栏距鼠标(followMouse=true)或坐标轴（followMouse=false）的位置
	                },
	                //设置X,Y轴默认加载方式
	                axesDefaults: {
	                    tickRenderer: $.jqplot.CanvasAxisTickRenderer,// 设置横（纵）轴上数据加载的渲染器，需引用jqplot.canvasAxisTickRenderer.js
	                    tickOptions: {
	                        angle: 270,  //倾斜角度，需引用jqplot.canvasTextRenderer.js
	                        fontSize: '10pt'
	                    }
	                },
	                axes: {
	                    xaxis: {
	                        label: "日期",  //x轴显示标题
	                        renderer: $.jqplot.CategoryAxisRenderer, //x轴绘制方式
	                        ticks: [], //设置横（纵）坐标的刻度上的值，可为该ticks数组中的值// a 1D [val1, val2, ...], or 2D [[val, label], [val, label], ...]
	                        tickOptions: {
	                            labelPosition: 'end',//start，middle，auto，end
	                        }
	                    },
	                    yaxis: {
	                        label: "会员数量", // y轴显示标题
	                        min: 0,//Y轴最小值
	                        tickOptions: { formatString: '%.0f', fontSize: '10pt' }//带有两位浮点小数
	                    }
	                }
	            });
	}else{
		if(array==''){
			lineMemory.destroy();
			$("#UserChart").html("没有查询到数据，请重新查询。");
		}else{
		lineMemory.init('UserChart',
				array,
	            {
	                //图标标题
	                title: {
	                    text: date_start+'至'+date_end+'新注册会员',
	                    show: true,
	                    fontFamily: "Microsoft Yahei",
	                    fontSize: "15pt",//pt磅，em相对字体大小
	                    textColor: "#000"
	                },
	                //提示栏显示配置，通常在右上角显示
	                legend: {
	                    show: true,
	                    location: 'ne',// 提示栏信息显示位置（英文方向的首写字母）: n, ne, e, se, s, sw, w, nw.
	                    placement: 'outside'
	                },
	                //提示栏显示信息配置，多个数据分类需配置多个
	                series: [{
	                    label: '平台会员',//按data先后顺序显示每种分类名称
	                    markerOptions:
	                        {
	                            size: 6,
	                            style: 'circle'
	                        }
	                }
	                ],
	                //节点数值提示，需要引用jqplot.pointLabels.js
	                seriesDefaults:{
	                    pointLabels: { show: true, ypadding: -1 } //数据点标签
	                },
	                //鼠标放在节点时突出显示当前结点,需引用jqplot.highlighter.js
	                highlighter: {
	                    show: true,
	                    sizeAdjust: 6,//当鼠标移动到数据点上时，数据点扩大的增量
	                    fadeTooltip: true,//设置提示信息栏出现和消失的方式（是否淡入淡出）
	                    lineWidthAdjust: 2.5,   //当鼠标移动到放大的数据点上时，设置增大的数据点的宽度
	                    tooltipOffset: 2,       // 提示信息栏据被高亮显示的数据点的偏移位置，以像素计
	                    tooltipLocation: 'nw' // 提示信息显示位置（英文方向的首写字母）: n, ne, e, se, s, sw, w, nw.
	                },
	                //鼠标在图标中的提示位置信息，需引用jqplot.cursor.js
	                cursor: {
	                    show: true,
	                    showTooltip: true,// 是否显示提示信息栏
	                    followMouse: false,     //光标的提示信息栏是否随光标（鼠标）一起移动
	                    tooltipLocation: 'se', // 光标提示信息栏的位置设置。如果followMouse=true,那么该位置为
	                    //提示信息栏相对于光标的位置。否则，为光标提示信息栏在图标中的位置
	                    // 该属性可选值： n, ne, e, se, etc.
	                    tooltipOffset: 6,     //提示信息栏距鼠标(followMouse=true)或坐标轴（followMouse=false）的位置
	                },
	                //设置X,Y轴默认加载方式
	                axesDefaults: {
	                    tickRenderer: $.jqplot.CanvasAxisTickRenderer,// 设置横（纵）轴上数据加载的渲染器，需引用jqplot.canvasAxisTickRenderer.js
	                    tickOptions: {
	                        angle: 270,  //倾斜角度，需引用jqplot.canvasTextRenderer.js
	                        fontSize: '10pt'
	                    }
	                },
	                axes: {
	                    xaxis: {
	                        label: "日期",  //x轴显示标题
	                        renderer: $.jqplot.CategoryAxisRenderer, //x轴绘制方式
	                        ticks: [], //设置横（纵）坐标的刻度上的值，可为该ticks数组中的值// a 1D [val1, val2, ...], or 2D [[val, label], [val, label], ...]
	                        tickOptions: {
	                            labelPosition: 'end',//start，middle，auto，end
	                        }
	                    },
	                    yaxis: {
	                        label: "会员数量", // y轴显示标题
	                        min: 0,//Y轴最小值
	                        tickOptions: { formatString: '%.0f', fontSize: '10pt' }//带有两位浮点小数
	                    }
	                }
	            });
		lineMemory.replot(); 
		}
	}
}	
</script>
</body>
</html>