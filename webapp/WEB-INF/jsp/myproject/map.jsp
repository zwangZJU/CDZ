<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/common/tags.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>

<aos:html title="repair_log" base="http" lib="ext">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf8" />
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<title>设备地图</title>
	<style type="text/css">
		body, html{width: 100%;height: 100%;margin:0;font-family:"微软雅黑";}
		#l-map{height:500px;width:100%;}
		#r-result{width:100%; font-size:14px;line-height:20px;}
	</style>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=ThTvAqi8T9m1Y9sKYtoAUi65eTQmRa7j"></script>
</head>
<body>
	<div id="l-map"></div>
	<div id="r-result">
		<!-- <input type="button" value="批量反地址解析+商圈" onclick="bdGEO(0)" /> -->
		<div id="result"></div>
	</div>
</body>
</html>
<script type="text/javascript">

var info = Ext.util.Cookies.get('juid'); 

Ext.Ajax.request({
					    url: '/cdz/http/do.jhtml?router=deviceService.listCoordinate&juid='+info,
					    mathod:"POST",
					    params:{version:5
					  },
					    success: function(response, opts) {
					        var obj = Ext.decode(response.responseText); 
					     
					  
					        var ss =obj.coor;
					        var ss1=ss[0]; 
					        var lat=ss1.lat;
					        var lon=ss1.lon;
					        var num=ss1.number;
					        /*  var num1 = Integer.parseInt(num);  */
					        
					        var map = new BMap.Map("l-map");
					      
					    	map.centerAndZoom(new BMap.Point(121.56251,29.91279), 10);
					    	map.enableScrollWheelZoom(true);
					    	var index = 0;
					    	var myGeo = new BMap.Geocoder();
					    /* 	var adds = [
					    		/* new BMap.Point(116.307852,40.057031),
					    		new BMap.Point(116.313082,40.047674),
					    		new BMap.Point(116.328749,40.026922),
					    		new BMap.Point(116.347571,39.988698),
					    		new BMap.Point(116.316163,39.997753),
					    		new BMap.Point(116.345867,39.998333),
					    		new BMap.Point(116.403472,39.999411),
					    		new BMap.Point(116.307901,40.05901) */
					    	/* ];  */
					    	 
					    	for(var i = 0; i<num; i++){
					    		
					    		var ss1=ss[i]; 
						        var lat=ss1.lat;
						        var lon=ss1.lon;
						       
					    		var marker = new BMap.Marker(new BMap.Point(lon,lat));
					    		map.addOverlay(marker);
					    		marker.setLabel(new BMap.Label("设备:"+(i+1),{offset:new BMap.Size(20,-10)}));
					    	}
					      
					    
					
					      
					    
					      /*   var last=JSON.stringify(obj); */
					       
					     /*    Ext.getCmp(aa即为id)。setValue即为setxxx(obj.xxx);//将得到的后台数据，处理，根据id和属性放到jsp中 */
					       
					  /*       root.hide(); */
					    },
					    failure: function(response, opts) {
					        AOS.tip('失败');
					        root.hide();
					    }
					});

   
	// 百度地图API功能
	
	function bdGEO(){	
		var pt = adds[index];
		geocodeSearch(pt);
		index++;
	}
	function geocodeSearch(pt){
		if(index < adds.length-1){
			setTimeout(window.bdGEO,400);
		} 
		myGeo.getLocation(pt, function(rs){
			var addComp = rs.addressComponents;
			document.getElementById("result").innerHTML += index + ". " +adds[index-1].lng + "," + adds[index-1].lat + "："  + "商圈(" + rs.business + ")  结构化数据(" + addComp.province + ", " + addComp.city + ", " + addComp.district + ", " + addComp.street + ", " + addComp.streetNumber + ")<br/><br/>";
		});
	}
	
	
</script>
</aos:html>
