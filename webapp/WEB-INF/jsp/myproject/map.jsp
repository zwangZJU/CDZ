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
		#l-map{height:100%;width:100%;}
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
map();

setInterval(map,20000);  
var map = new BMap.Map("l-map");
function map(){
	
Ext.Ajax.request({
					    url: '/cdz/http/do.jhtml?router=deviceService.listCoordinate&juid='+info,
					    mathod:"POST",
					    params:{version:5
					  },
					    success: function(response, opts) {
					    	
					        var obj = Ext.decode(response.responseText); 
					        var ss =obj.coor;
					        var ss2=ss[0]; 
					        var lat1=ss2.lat;
					        var lon1=ss2.lon;
					       /*  AOS.tip("777"); */
					        var num=ss2.number;
					         map.clearOverlays();  
					    	map.centerAndZoom(new BMap.Point(lon1,lat1), 13);
					    	map.enableScrollWheelZoom(true);
					    	var index = 0;
					    	var myGeo = new BMap.Geocoder();
					         var opts = {
				                      width : 250,     // 信息窗口宽度
				                      height: 100,     // 信息窗口高度
				                      title : "设备信息" , // 信息窗口标题
				                      enableMessage:true//设置允许信息窗发送短息
			                         }; 
					    	for(var i = 0; i<num; i++){
					    		var ss1=ss[i]; 
						        var lat=ss1.lat;
						        var lon=ss1.lon;
						        var point5=new BMap.Point(lon,lat);
						        var id=ss1.device_id;
						        var alarm=ss1.is_alarming;
	                            var name=ss1.user_name;
						        var phone=ss1.phone;
						        var address=ss1.user_address;
						  
						   
					    		var marker = new BMap.Marker(point5);
					    		var content ='<div >' +
			                    '设备编号:'+id+'<br/>'+'用户姓名:'+name+'<br/>'+'用户电话:'+phone+'<br/>'+'用户地址:'+address+'<br/>'+
			                  '</div>';
			              	map.addOverlay(marker);
			              	 if (alarm=="1"){
							      
			              		 marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画 *
						        }

					    	 	addClickHandler(content,marker);
					    		marker.setLabel(new BMap.Label("设备:"+(i+1),{offset:new BMap.Size(20,-10)}));
					    	
					    	}
					    	
					    	function addClickHandler(content,marker){
					    		marker.addEventListener("click",function(e){
					    			var p = e.target;
						    	 	var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat); 
						    		var infoWindow = new BMap.InfoWindow(content);  // 创建信息窗口对象 
						    		map.openInfoWindow(infoWindow,point); //开启信息窗口
					    			
					    			
					    			}
					    		);
					    	}
					    	
					    	//缩放控件********
					    	var top_right_navigation = new BMap.NavigationControl({
					    		anchor: BMAP_ANCHOR_TOP_RIGHT, type: BMAP_NAVIGATION_CONTROL_SMALL
					    				});
							map.addControl(top_right_navigation); 
					    },
					    failure: function(response, opts) {
					        AOS.tip('失败');
					        root.hide();
					    }
					});
}

   
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
