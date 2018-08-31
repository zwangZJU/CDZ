<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/common/tags.jsp"%>
<aos:html title="alarm_log" base="http" lib="ext">

<script type="text/javascript">
/* var audio = document.getElementById("bgMusic"); */

//播放(继续播放)
 


    
</script>   
<body onload = sendRequest()>
 <audio id="bgMusic">
    <source  src="http://118.126.95.215:9090/cdz/static/music/alarm.wav" >
    <source  src="hangge.ogg" type="audio/ogg">
</audio> 

</body>
<aos:onready>
	<aos:viewport layout="border">
	<aos:formpanel id="_f_query" layout="column" labelWidth="70" header="false" region="north" >
			<aos:docked forceBoder="0 0 1 0">
				<aos:dockeditem xtype="tbtext" text="查询条件" />
			</aos:docked>
			 <aos:textfield   name="alarm_id" fieldLabel="报警序号" columnWidth="0.2"     maxLength="255"    	         />
	      	 <aos:textfield   name="device_id" fieldLabel="设备id" columnWidth="0.2"    maxLength="255"    	         />
	      	 <aos:textfield name="user_phone" fieldLabel="用户手机号" columnWidth="0.2" maxLength="255"    	         />
	      	    <aos:textfield name="handler_" fieldLabel="处理者" columnWidth="0.2" maxLength="255"    	         />
	      	   	
	      	   	<aos:textfield name="handler_phone" fieldLabel="处理者电话" columnWidth="0.2" maxLength="255"    	         />
			 <aos:datefield id="date_start" name="date_start" fieldLabel="报警时间"  columnWidth="0.25"/>
			<aos:datefield id="date_end" name="date_end" fieldLabel="至"  columnWidth="0.25"/>
			<aos:docked dock="bottom" ui="footer" margin="0 0 8 0">
				<aos:dockeditem xtype="tbfill" />
				<aos:dockeditem xtype="button" text="查询" onclick="_datagridpanel_query" icon="query.png" />
				<aos:dockeditem xtype="button" text="重置" onclick="AOS.reset(_f_query);" icon="refresh.png" />
				<aos:dockeditem xtype="button" text="导出" onclick="fn_export_excel" icon="icon70.png" />
				<aos:dockeditem xtype="tbfill" />
			</aos:docked>
		</aos:formpanel>
		
		<aos:gridpanel id="_datagridpanel" url="alarm_logService.listAlarm_log" onrender="_datagridpanel_query" onitemdblclick="_w_update_show_all"  region="center" forceFit="false">
			<aos:docked>
			    			 	<aos:dockeditem text="新增" tooltip="新增"  onclick="_w_add_show" icon="add.png"/>
							    			    <aos:dockeditem text="修改" tooltip="修改"  onclick="_w_update_show" icon="edit.png"/>
												<aos:dockeditem text="删除" tooltip="删除" onclick="_delete" icon="del.png" />
												<aos:dockeditem text="导出" tooltip="导出" onclick="fnnoti1()" icon="icon70.png" /> 
												<aos:dockeditem text="多个接警" tooltip="多个接警" onclick="receive_alarm_many()" icon="icon70.png" />
												<aos:dockeditem text="全屏显示" tooltip="全屏显示" onclick="winopen" icon="max.png" />
												<%-- <aos:dockeditem text="导出" tooltip="导出" onclick="_exportexcel" icon="icon70.png" /> --%>
												<%-- <aos:dockeditem text="操作" tooltip="操作" onclick="_f_role_u_save" icon="icon70.png" /> --%>
								<aos:dockeditem xtype="tbseparator" />
				               			</aos:docked>
			<aos:column type="rowno" />
			<aos:selmodel type="checkbox" mode="multi" />
			   <aos:column header="实时视频"  align="center"  rendererFn="fn_video" width="80" />
		 										<aos:column header="操作"  align="center" dataIndex="process" rendererFn="fn_balance_render7" width="80" />
						      			       <aos:column header="报警序号" dataIndex="alarm_id"   width="100" />
			    						      			       <aos:column header="设备id" dataIndex="device_id"   width="100" />
			    						      			       <aos:column header="用户手机号" dataIndex="user_phone"   width="100" />
			    						      			       <aos:column header="报警时间" dataIndex="alarm_time"   width="100" />
			    						      			       <aos:column header="出警时间" dataIndex="response_time"  width="100" />
			    						      			       <aos:column header="报警方式" dataIndex="type_"   width="100" />
			    						      			       <aos:column header="处理者" dataIndex="handler_"   width="100" />
			    						      			       <aos:column header="处理者电话" dataIndex="handler_phone"   width="100" />
			    						      			       <aos:column header="报警原因" dataIndex="reason_"   width="100" />
			    						      			       <aos:column header="报警解除" dataIndex="alarm_release"   width="100" />
			    						      			       <aos:column header="取消报警" dataIndex="is_cancel"   width="100" />
			    						      			       <aos:column header="警情代码" dataIndex="alert_code"   width="100" />
			    						      			       <%-- <aos:column header="是否接警和处理" dataIndex="process"   width="255" />  --%>
			    						      			       <%-- <aos:column header="防区号" dataIndex="defence"   width="100" /> --%>
			    			 		</aos:gridpanel>
	</aos:viewport>
	
	<aos:window id="_w_role_u" title="接警">
		<aos:formpanel id="_f_role_u" width="500" layout="anchor" labelWidth="65">
			<aos:hiddenfield name="alarm_id" />
			<aos:textfield name="handler_" fieldLabel="处理者" allowBlank="false" maxLength="50" />
		</aos:formpanel>
		<aos:docked dock="bottom" ui="footer">
			<aos:dockeditem xtype="tbfill" />
			<aos:dockeditem onclick="_f_role_u_save" text="保存" icon="ok.png" />
			<aos:dockeditem onclick="#_w_role_u.hide();" text="关闭" icon="close.png" />
		</aos:docked>
	</aos:window>
	
	<aos:window id="_w_add_data" title="新增报警日志" width="600"   height="400"  autoScroll="true">
		<aos:formpanel id="_f_add"  width="600-20"     layout="anchor" labelWidth="70">
         	   	       	        <aos:textfield name="alarm_id" fieldLabel="报警序号"   allowBlank="false"   maxLength="255"    	         />
	      	   	 	         	   	       	        <aos:textfield name="device_id" fieldLabel="设备id"   allowBlank="false"   maxLength="255"    	         />
	      	   	 	         	   	       	        <aos:textfield name="user_phone" fieldLabel="用户手机号"  maxLength="255"    	         />
	      	   	 	         	      	    <aos:datefield name="alarm_time" fieldLabel="报警时间"   	              format="Y-m-d 00:00:00"     editable="true"/>
	  	 	         	      	    <aos:datefield name="response_time" fieldLabel="出警时间"   	              format="Y-m-d 00:00:00"     editable="true"/>
	  	 	         	   	       	        <aos:textfield name="type_" fieldLabel="报警方式"  maxLength="255"    	         />
	      	   	 	         	   	       	        <aos:textfield name="handler_" fieldLabel="处理者"  maxLength="255"    	         />
	      	   	 	         	   	       	        <aos:textfield name="handler_phone" fieldLabel="处理者电话"  maxLength="255"    	         />
	      	   	 	         	   	       	        <aos:textfield name="reason_" fieldLabel="报警原因"  maxLength="255"    	         />
	      	   	 	         	   	       	        <aos:textfield name="alarm_release" fieldLabel="报警解除"  maxLength="255"    	         />
	      	   	 	         	   	       	        <aos:textfield name="is_cancel" fieldLabel="取消报警"  maxLength="255"    	         />
	      	   	 	         	   	       	        <aos:textfield name="alert_code" fieldLabel="警情代码"  maxLength="255"    	         />
	      	   	 	         	   	       	        <aos:textfield name="process" fieldLabel="是否接警和处理"  maxLength="255"    	         />
	      	   	 	         	   	       	        <aos:textfield name="defence" fieldLabel="防区号"  maxLength="255"    	         />
	      	   	 			</aos:formpanel>
		<aos:docked dock="bottom" ui="footer">
			<aos:dockeditem xtype="tbfill" />
			<aos:dockeditem onclick="_f_add_save" text="保存" icon="ok.png" />
			<aos:dockeditem onclick="#_w_add_data.hide();" text="关闭" icon="close.png" />
		</aos:docked>
	</aos:window>
	
	<aos:window id="_w_update_data" title="修改报警日志" width="600"   height="400"  autoScroll="true">
			<aos:formpanel id="_f_update"   width="600-20"     layout="anchor" labelWidth="70">
        	   	       	        <aos:textfield name="alarm_id" fieldLabel="报警序号"   allowBlank="false"   maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="device_id" fieldLabel="设备id"   allowBlank="false"   maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="user_phone" fieldLabel="用户手机号"  maxLength="255"    	         />
	      	    	        	      	       <aos:datefield name="alarm_time" fieldLabel="报警时间"   	                 format="Y-m-d 00:00:00"     editable="true"/>
	    	        	      	       <aos:datefield name="response_time" fieldLabel="出警时间"   	                 format="Y-m-d 00:00:00"     editable="true"/>
	    	        	   	       	        <aos:textfield name="type_" fieldLabel="报警方式"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="handler_" fieldLabel="处理者"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="handler_phone" fieldLabel="处理者电话"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="reason_" fieldLabel="报警原因"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="alarm_release" fieldLabel="报警解除"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="is_cancel" fieldLabel="取消报警"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="alert_code" fieldLabel="警情代码"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="process" fieldLabel="是否接警和处理"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="defence" fieldLabel="防区号"  maxLength="255"    	         />
	      	    	 		</aos:formpanel>
		<aos:docked dock="bottom" ui="footer">
			<aos:dockeditem xtype="tbfill" />
			<aos:dockeditem onclick="_f_update_save" text="保存" icon="ok.png" />
			<aos:dockeditem onclick="#_w_update_data.hide();" text="关闭" icon="close.png" />
		</aos:docked>
	</aos:window>
	
	<script type="text/javascript">
	
	function winopen(){
		
		 
		
		  var info = Ext.util.Cookies.get('juid'); 
		var targeturl="http://localhost:9090/cdz/http/do.jhtml?router=alarm_logService.initAlarm&juid="+info;
		
		 window.open(targeturl,"","fullscreen=1,menubar=0,toolbar=0,directories=0,location=0,status=0,scrollbars=0");  
		//window.close();
		/* fkey(); */
	
		
	} 
	 var info = Ext.util.Cookies.get('juid'); 
	 
	 sendRequest();
	 
	var XMLHttpReq;
	  //创建XMLHttpRequest对象       
	  function createXMLHttpRequest() {
	  if(window.XMLHttpRequest) { //Mozilla 浏览器
	   XMLHttpReq = new XMLHttpRequest();
	  }
	  else if (window.ActiveXObject) { // IE浏览器
	   try {
	    XMLHttpReq = new ActiveXObject("Msxml2.XMLHTTP");
	   } catch (e) {
	    try {
	     XMLHttpReq = new ActiveXObject("Microsoft.XMLHTTP");
	    } catch (e) {}
	   }
	  }
	 }
	 //发送请求函数
	 function sendRequest() {
	  var info = Ext.util.Cookies.get('juid');
	  createXMLHttpRequest();
	  //var url = "do.jhtml?router=alarm_logService.updateWebpage&juid=a28937399c4243838e22941929e4e464";
	  var url = "do.jhtml?router=alarm_logService.updateWebpage&juid="+info;
	  XMLHttpReq.open("GET", url, true);
	  XMLHttpReq.onreadystatechange = processResponse;//指定响应函数
	  XMLHttpReq.send(null);  // 发送请求
	 }
	 // 处理返回信息函数
	    function processResponse() {
	     if (XMLHttpReq.readyState == 4) { // 判断对象状态
	         if (XMLHttpReq.status == 200) { // 信息已经成功返回，开始处理信息
	        	// var name = XMLHttpReq.responseXML.getElementsByTagName("name")[0].firstChild.nodeValue;
	        	    //alert(XMLHttpReq.responseText);
	        	    var a = XMLHttpReq.responseText;
				    if(a == "1"){
				    	/* audio.play(); */
				    	DisplayHot();
				    	/*  */
				    	//setTimeout("window.location.reload()",2000);
				    	setTimeout(_datagridpanel_query,2000);
				    	//setInterval(_datagridpanel_query,2000);
				    }
				    setTimeout(sendRequest, 4000);
				  
				   
	            } else { //页面不正常
	                window.alert("您所请求的页面有异常。");
	            }
	        }
	    }
	 
	    function _datagridpanel_query() {
			var params = AOS.getValue('_f_query');
			_datagridpanel_store.getProxy().extraParams = params;
			_datagridpanel_store.loadPage(1);
		}
	    
	    function DisplayHot() {
	     //var name = XMLHttpReq.responseXML.getElementsByTagName("name")[0].firstChild.nodeValue;
	     //var count = XMLHttpReq.responseXML.getElementsByTagName("count")[0].firstChild.nodeValue;
	 
	           //document.getElementById("cheh").innerHTML = "T-"+name+"次列车"; 
	    //document.getElementById("price").innerHTML = count+"元"; 
	    
	    //var text = Ext.decode(response.responseText);
				        // process server response here
				        //var value1=text["user_address"];
				   
		//Ext.getCmp("a").setValue(text.data);
		
	    var audio = document.getElementById("bgMusic");
	    audio.play();
	    var msg1 = 'AOS应用基础平台基于JavaEE技术体系，以“标准功能可复用、通用模块可配置、行业需求快速开发、异构系统无缝集成”为目标，为软件开发团队提供高效可控、随需应变、快速实现业务需求的全栈式技术解决方案。帮助企业落实IT策略、屏蔽技术壁垒，快速实现业务愿景。使其获得更低成本、更高质量、更快交付业务和运维支持的核心技术竞争力。';
	    	Ext.create(
					'widget.uxNotification',
					{
						position : 'br',
						title : '<span class="app-container-title-normal"><i class="fa fa-bell-o"></i> 通知</span>',
						closable : false,
						autoCloseDelay : 2000,
						slideInDuration : 200,
						useXAxis : false,
						width : 400,
						html : msg1
					}).show();
	    }
	
	
	
		function _datagridpanel_query() {
			var params = AOS.getValue('_f_query');
			_datagridpanel_store.getProxy().extraParams = params;
			_datagridpanel_store.loadPage(1);
		}
		function getCount() {
			var count = _datagridpanel_store.getCount();
			console.log(count);
		}
		//弹出新增窗口
		function _w_add_show(){
			AOS.reset(_f_add);
			_w_add_data.show();
		}
		//新增 报警日志
		function _f_add_save(){
				AOS.ajax({
				forms : _f_add,
				url : 'alarm_logService.saveAlarm_log',
				ok : function(data) {
					if (data.appcode === -1) {
                        AOS.err(data.appmsg);
                    } else {
                    	_w_add_data.hide();
    					_datagridpanel_store.reload();
    					_datagridpanel_store();
    					AOS.tip(data.appmsg);
                    }
				}
			});
		}	
		
		//弹出修改窗口 报警日志
		function _w_update_show(){
			AOS.reset(_f_update);
			var record = AOS.selectone(_datagridpanel);
			if(record){
				_w_update_data.show();
				_f_update.loadRecord(record);
			}
		}
		
		/*function _w_update_show_all()
		{
			 AOS.ajax({
	                forms: _f_role_u,
	                //url: 'alarm_handleService.update_Alarm_log_and_device',
	                url:'alarm_logService.updateAlarm_log3',
	                ok: function (data) {
	                   // _w_role_u.hide();
	                   // _datagridpanel_store.reload();
	                   // AOS.tip(data.appmsg);
	                	_w_update_data.show();
	    				_f_update.loadRecord(data.appmsg);
	                }
	            });
		}*/
		
		function _w_update_show_all()
		{
			var value  = AOS.selection(_datagridpanel, 'process');
			if(value == "0,")
			{
			var alarm_id = AOS.selection(_datagridpanel, 'alarm_id');

			//var info = Ext.util.Cookies.get('juid');
			Ext.Ajax.request({
			    url: '/cdz/http/do.jhtml?router=alarm_logService.receive_alarmAlarm_log',
			    params: {
			        id: alarm_id
	    		},
			    success: function(response){
			        var text = Ext.decode(response.responseText);
			        // process server response here
			        //var value1=text["user_address"];
			   		if(text.device_id == null)
		        		Ext.getCmp("e").setValue("无设备");
		        	else
		        		Ext.getCmp("e").setValue(text.device_id);
			        //Ext.getCmp("a").setValue(text.user_id);
			        Ext.getCmp("b").setValue(text.user_name);
			        Ext.getCmp("c").setValue(text.user_address);
			        Ext.getCmp("d").setValue(text.user_phone);
			      
			        Ext.getCmp("f").setValue(text.alarm_time);
			        Ext.getCmp("g").setValue(text.handler_);
			        Ext.getCmp("h").setValue(text.handler_phone);
			        
			        
			        //Ext.getCmp("i").setValue(text.alarm_time);
			    	}
			});
			
			    var productForm = Ext.create("Ext.form.Panel", {
			    	id:"receiveAlarmSave",
			        title: "信息表",
			        width: 300,
			        height: 400,
			        frame: true,
			         fieldDefaults: {
			            labelSeparator: ":",
			            labelWidth: 80,
			            width: 250,
			            margin:5
			        }, 
			        renderTo: Ext.getBody(),
			         items: [
			        	 //{ fieldLabel: "用户编号", id:"a",xtype: "textfield", name: "user_id"  },
			        	 { fieldLabel: "用户名称", id:"b",xtype: "textfield", name: "user_name"},
			        	 { fieldLabel: "用户地址", id:"c",xtype: "textfield", name: "user_address"  },
			        	 { fieldLabel: "用户手机号", id:"d",xtype: "textfield", name: "user_phone" },
			        	 { fieldLabel: "设备编号", id:"e",xtype: "textfield", name: "device_id" },
			        	 { fieldLabel: "报警时间", id:"f",xtype: "textfield", name: "alarm_time" },
			        	 { fieldLabel: "处理人", id:"g",xtype: "textfield", name: "handler_" },
			        	 { fieldLabel: "处理人手机号", id:"h",xtype: "textfield", name: "handler_phone" }
			        	 //{ fieldLabel: "接警人", value: "/" }
			            //{ fieldLabel: "产品名称", id:"a",xtype: "textfield", name: "productName", value: "U盘" },
			            //{ fieldLabel: "金额", xtype: "numberfield", name: "price", value: 100 },
			            //{ fieldLabel: "生产日期", xtype: "datefield", format: "Y-m-d", name: "date", value: new Date() },
			            //{ xtype: "hidden", name: "productId", value: "001" },
			            //{ fieldLabel: "产品简介", name: "introduction", xtype: "textarea" }
			        ] 
			       /*  buttons: [
			            { text: "加载简介", handler: loadIntroduction }
			        ] */
			    });
			    
			
				var root = new Ext.Window({
					
					title:"报警处理",
					width:600,
					height:400,
					frame:false,
					items:[productForm],	
					/*
					bbar:['->',
					{text:"升级", frame:false,handler:function(){
						AOS.notice("提示!","确定要将系统升级到该版本吗?",function(){				
						var vn = combox.getRawValue();
						Ext.Ajax.request({
						    url: '/cdz/http/do.jhtml?router=upgradeHardwareController.upgradeAll&juid='+info,
						    mathod:"POST",
						    params:{version:vn,
						    	aos_rows_: selection},
						    success: function(response, opts) {
						        var obj = Ext.decode(response.responseText);
						        AOS.tip(obj.appmsg);
						        root.hide();
						    },
						    failure: function(response, opts) {
						        AOS.tip('升级失败');
						        root.hide();
						    }
						});
						
						},function(){});
					}},
						{text:"取消", frame:false,handler:function(){AOS.tip("取消升级");root.hide();}}*/
							

					resizable:false,
					closable:true,
					draggable:false,
					modal:true,
					
					buttons: [
				        { xtype: "button", text: "确定", 
				        	handler: function () { 
				        		//receive_alarm_save();
				        		Ext.Ajax.request({
				    			    url: '/cdz/http/do.jhtml?router=alarm_logService.receiveAlarmSave',
				    			    params: {
				    			        id: alarm_id
				    	    		},
				    			    success: function(response){
				    			    	//alert("在接警");
				    			        //var text = Ext.decode(response.responseText);
				    			        //Ext.getCmp("x").setValue(text.is_alarming);
				    			    	//if("x" == "0")
				    			    	//	alert("接警成功");
				    			    	//else
				    			    	//	alert("接警失败");
				    			    	
				    			    	if(response.appcode === -1){
			 								AOS.err(response.appmsg);
			 								return ;
			 							}
				    			    	window.location.reload();
			 							AOS.tip("接警成功");
			 							//_datagridpanel_store.reload();
			 							
				    			    	}
				    			});
				        		this.up("window").close();
				        	} },	
				        { xtype: "button", text: "取消", handler: function () { this.up("window").close(); } }
				    ]
					
				});
				root.show();
			}
		}
		
		
	    
	   //修改   报警日志
		function _f_update_save(){
			AOS.ajax({
				forms : _f_update,
				url : 'alarm_logService.updateAlarm_log',
				ok : function(data) {
					if (data.appcode === -1) {
                        AOS.err(data.appmsg);
                    } else {
    					AOS.tip(data.appmsg);	
    					_w_update_data.hide();
    					_datagridpanel_store.reload();
                    }
				}
			}); 
		}
	   
        //删除 报警日志
	 	function _delete(){
	 				var selection = AOS.selection(_datagridpanel, 'alarm_id');
	 				if(AOS.empty(selection)){
	 					AOS.tip('删除前请先选中数据。');
	 					return;
	 				}
	 				var rows = AOS.rows(_datagridpanel);
	 				var msg =  AOS.merge('确认要删除选中的{0}项目吗？', rows);
	 				AOS.confirm(msg, function(btn){
	 					if(btn === 'cancel'){
	 						AOS.tip('删除操作被取消。');
	 						return;
	 					}
	 					AOS.ajax({
	 						url : 'alarm_logService.deleteAlarm_log',
	 						params:{
	 							aos_rows_: selection
	 						},
	 						ok : function(data) {
	 							if(data.appcode === -1){
	 								AOS.err(data.appmsg);
	 								return ;
	 							}
	 							AOS.tip(data.appmsg);
	 							_datagridpanel_store.reload();
	 						}
	 					});
	 				});
	 			}
        function _exportexcel(){
        	AOS.file('exportexcel.jhtml');
        }
        
      //处理者保存
       function _f_role_u_save() {
            AOS.ajax({
                forms: _f_role_u,
                //url: 'alarm_handleService.update_Alarm_log_and_device',
                url:'alarm_logService.updateAlarm_log2',
                ok: function (data) {
                    _w_role_u.hide();
                    _datagridpanel_store.reload();
                    AOS.tip(data.appmsg);
                }
            });
    	   //AOS.combox(AOS.selectone(AOS.get('_datagridpanel')));
        }
      
      //多个同时接警
      function receive_alarm_many()
      {
    	  var selection = AOS.selection(_datagridpanel, 'alarm_id');
			if(AOS.empty(selection)){
				AOS.tip('多个接警请先选中数据。');
				return;
			}
			var rows = AOS.rows(_datagridpanel);
			var msg =  AOS.merge('确认要接警选中的{0}项目吗？', rows);
			AOS.confirm(msg, function(btn){
				if(btn === 'cancel'){
					AOS.tip('多个接警操作被取消。');
					return;
				}
				AOS.ajax({
					url : 'alarm_logService.receiveAlarmMany',
					params:{
						aos_rows_: selection
					},
					ok : function(data) {
						if(data.appcode === -1){
							AOS.err(data.appmsg);
							return ;
						}
						window.location.reload();
						AOS.tip("多个接警成功");
						
					}
				});
			});
      }


        //按钮列转换,这个是不变颜色的接警 按钮
    	function fn_button_render(value, metaData, record) {
    		 return '<input type="button" value="接警" class="cbtn_danger" onclick="_w_update_show_all_button();" />'; 
        	/*return '<input type="button" value="接警" class="cbtn_danger" onclick="_f_role_u_save();" />'; */
        	//return '<input type="text" value="接警" class="cbtn_danger" onclick="_w_role_u_show();"
    	}
        
        var msg1 = 'AOS应用基础平台基于JavaEE技术体系，以“标准功能可复用、通用模块可配置、行业需求快速开发、异构系统无缝集成”为目标，为软件开发团队提供高效可控、随需应变、快速实现业务需求的全栈式技术解决方案。帮助企业落实IT策略、屏蔽技术壁垒，快速实现业务愿景。使其获得更低成本、更高质量、更快交付业务和运维支持的核心技术竞争力。';
    	
        function fnnoti1() {
    		Ext.create(
    						'widget.uxNotification',
    						{
    							position : 'br',
    							title : '<span class="app-container-title-normal"><i class="fa fa-bell-o"></i> 通知</span>',
    							closable : false,
    							autoCloseDelay : 2000,
    							slideInDuration : 200,
    							useXAxis : false,
    							width : 400,
    							html : msg1
    						}).show();
    	}
        
		function fn_export_excel(){
			var params = AOS.getValue('_f_query');
			var params_url="";
			for(var v in params){
				params_url+="&"+v+"="+params[v];
			}
			AOS.file('/cdz/http/do.jhtml?router=alarm_logService.exportExcel&juid='+info+params_url);	 
		}
        
	</script>
</aos:onready>
<script src="https://open.ys7.com/sdk/js/1.3/ezuikit.js"></script>
<script type="text/javascript">
	function _w_role_u_show(){
		 var record = AOS.selectone(AOS.get('_datagridpanel'));
         if (record) {
        	 AOS.get('_w_role_u').show();
        	 AOS.get('_f_role_u').loadRecord(record);
         }
	}
	
	function _w_update_show_all_button()
	{
		var record = AOS.selectone(AOS.get('_datagridpanel'));

		//var info = Ext.util.Cookies.get('juid');
		Ext.Ajax.request({
		    url: '/cdz/http/do.jhtml?router=alarm_logService.receive_alarmAlarm_log',
		    params: {
		        id: record.data.alarm_id
    		},
		    success: function(response){
		        var text = Ext.decode(response.responseText);
		        // process server response here
		        //var value1=text["user_address"];
		   
		        if(text.device_id == null)
		        	Ext.getCmp("e").setValue("无设备");
		        else
		        	Ext.getCmp("e").setValue(text.device_id);
		        //Ext.getCmp("a").setValue(text.user_id);
		        Ext.getCmp("b").setValue(text.user_name);
		        Ext.getCmp("c").setValue(text.user_address);
		        Ext.getCmp("d").setValue(text.user_phone);
		        
		        Ext.getCmp("f").setValue(text.alarm_time);
		        Ext.getCmp("g").setValue(text.handler_);
		        Ext.getCmp("h").setValue(text.handler_phone);
		        //Ext.getCmp("i").setValue(text.alarm_time);
		        
	
		    	}
		});
		
		    var productForm = Ext.create("Ext.form.Panel", {
		        title: "信息表",
		        width: 300,
		        height: 400,
		        frame: true,
		         fieldDefaults: {
		            labelSeparator: ":",
		            labelWidth: 80,
		            width: 250,
		            margin:5
		        }, 
		        renderTo: Ext.getBody(),
		         items: [
		        	 //{ fieldLabel: "用户编号", id:"a",xtype: "textfield", name: "user_id", value: "/" },
		        	 { fieldLabel: "用户名称", id:"b",xtype: "textfield", name: "user_name", value: "/" },
		        	 { fieldLabel: "用户地址", id:"c",xtype: "textfield", name: "user_address", value: "/" },
		        	 { fieldLabel: "用户手机号", id:"d",xtype: "textfield", name: "user_phone", value: "/" },
		        	 { fieldLabel: "设备编号", id:"e",xtype: "textfield", name: "device_id", value: "/" },
		        	 { fieldLabel: "报警时间", id:"f",xtype: "textfield", name: "alarm_time", value: "/" },
		        	 { fieldLabel: "处理人", id:"g",xtype: "textfield", name: "handler_", value: "/" },
		        	 { fieldLabel: "处理人手机号", id:"h",xtype: "textfield", name: "handler_phone", value: "/" }
		        	 //{ fieldLabel: "接警人", value: "/" }
		            //{ fieldLabel: "产品名称", id:"a",xtype: "textfield", name: "productName", value: "U盘" },
		            //{ fieldLabel: "金额", xtype: "numberfield", name: "price", value: 100 },
		            //{ fieldLabel: "生产日期", xtype: "datefield", format: "Y-m-d", name: "date", value: new Date() },
		            //{ xtype: "hidden", name: "productId", value: "001" },
		            //{ fieldLabel: "产品简介", name: "introduction", xtype: "textarea" }
		        ] 
		       /*  buttons: [
		            { text: "加载简介", handler: loadIntroduction }
		        ] */
		    });
		    
		
			var root = new Ext.Window({
				title:"报警处理",
				width:600,
				height:400,
				frame:false,
				items:[productForm],	

				resizable:false,
				closable:true,
				draggable:false,
				modal:true,
				
				buttons: [
					{ xtype: "button", text: "确定", 
			        	handler: function () { 
			        		//receive_alarm_save();
			        		
			        		Ext.Ajax.request({
			    			    url: '/cdz/http/do.jhtml?router=alarm_logService.receiveAlarmSave',
			    			    params: {
			    			        id: record.data.alarm_id
			    	    		},
			    			    success: function(response){
			    			    	//alert("在接警");
			    			        //var text = Ext.decode(response.responseText);
			    			        //Ext.getCmp("x").setValue(text.is_alarming);
			    			    	//if("x" == "0")
			    			    	//	alert("接警成功");
			    			    	//else
			    			    	//	alert("接警失败");
			    			    	
			    			    	//if(response.appcode === -1){
		 							//	AOS.err(response.appmsg);
		 							//	return ;
		 							//}
			    			    	//_datagridpanel_store.reload();
			    			    	window.location.reload();
		 							AOS.tip("接警成功");
		 							//_w_add_data.hide();
		 	    					
		 							
			    			    	}
			    			});
			        		this.up("window").close();
			        	} },	
			        { xtype: "button", text: "取消", handler: function () { this.up("window").close(); } }
			    ]
				
			});
			root.show();
	}

	function fn_balance_render7(value, metaData, record, rowIndex, colIndex,
			store) {
		
		
	    
	    if ( value==0) {
	    	
	    	
	    	 return '<input type="button" value="未接警" class="cbtn_danger" onclick="_w_update_show_all_button();"  />'; 
	 	 
			/*  metaData.style = 'background-color:#990099'; 
			return value; */
		} else {
	/* 		metaData.style = 'background-color:#0099CC';  */
			
			 return '<input type="button" value="已接警" class="cbtn" onclick=""  />'; 
		}
		
	  
	}
	
	
	function fn_video(value, metaData, record, rowIndex, colIndex,
			store) {
		
		
	    	 return '<input type="button" value="查看视频"  onclick="videoPlay();"  />'; 
	 	 
		
	}
	
function videoPlay() {
		
		var record = AOS.selectone(AOS.get('_datagridpanel'));
        var id= record.data.device_id;
	
	    if(id == null){
	    	 AOS.tip("一键报警，无摄像头");
        	
  }else{
    	 
       Ext.Ajax.request({
			    url: '/cdz/http/do.jhtml?router=cameraService.listUrl',
			    params: {
			        id: record.data.device_id
	    		},
			    success: function(response){
			    	
			    	var obj = Ext.decode(response.responseText); 
			    	var url = obj.data; 
			    	var num=url[0].num;
			    	if(num==0){
			    		AOS.tip("该用户没有摄像头");
			    	}
			    	var rtmp = new Array(10);
			    	var hls = new Array(10);
			    	for(var i = 0; i<num; i++){
			         rtmp[i] =url[i].rtmp;
			         hls[i] =url[i].hls;
			       
			    	}
			    	/*  rtmp[7] ="rtmp://rtmp.open.ys7.com/openlive/e69dd707d3614f1cb60a7d07efe32394.hd"; */
			    var video1=	new Ext.Panel({  
	                     xtype : 'panel',  
	                   id : 'playerPanel1',  
	                   html : '<video id="myPlayer1" poster="" controls playsInline webkit-playsinline autoplay width="160" height="105">'  
	                           + '<source src='+rtmp[0]+' type="" />' +  
	                        '</video>'  
	                     });
			    var video2=	new Ext.Panel({  
                    xtype : 'panel',  
                  id : 'playerPanel2',  
                  html : '<video id="myPlayer2" poster="" controls playsInline webkit-playsinline autoplay width="160" height="105">'  
                          + '<source src='+rtmp[1]+' type="" />' +  
                       '</video>'  
                    });	     
	       
			    var video3=	new Ext.Panel({  
                    xtype : 'panel',  
                  id : 'playerPanel3',  
                  html : '<video id="myPlayer3" poster="" controls playsInline webkit-playsinline autoplay width="160" height="105">'  
                          + '<source src='+rtmp[2]+' type="" />' +  
                       '</video>'  
                    });	     
			    var video4=	new Ext.Panel({  
                    xtype : 'panel',  
                  id : 'playerPanel4',  
                  html : '<video id="myPlayer4" poster="" controls playsInline webkit-playsinline autoplay width="160" height="105">'  
                          + '<source src='+rtmp[3]+' type="" />' +  
                       '</video>'  
                    });	     
			    var video5=	new Ext.Panel({  
                    xtype : 'panel',  
                  id : 'playerPanel5',  
                  html : '<video id="myPlayer5" poster="" controls playsInline webkit-playsinline autoplay width="160" height="105">'  
                          + '<source src='+rtmp[4]+' type="" />' +  
                       '</video>'  
                    });
		    var video6=	new Ext.Panel({  
               xtype : 'panel',  
             id : 'playerPanel6',  
             html : '<video id="myPlayer6" poster="" controls playsInline webkit-playsinline autoplay width="160" height="105">'  
                     + '<source src='+rtmp[5]+' type="" />' +  
                  '</video>'  
               });	     
      
		    var video7=	new Ext.Panel({  
               xtype : 'panel',  
             id : 'playerPanel7',  
             html : '<video id="myPlayer7" poster="" controls playsInline webkit-playsinline autoplay width="160" height="105">'  
                     + '<source src='+rtmp[6]+' type="" />' +  
                  '</video>'  
               });	     
		    var video8=	new Ext.Panel({  
               xtype : 'panel',  
             id : 'playerPanel8',  
             html : '<video id="myPlayer8" poster="" controls playsInline webkit-playsinline autoplay width="160" height="105">'  
                     + '<source src='+rtmp[7]+' type="" />' +  
                  '</video>'  
               });	 
		    var video9=	new Ext.Panel({  
	               xtype : 'panel',  
	             id : 'playerPanel9',  
	             html : '<video id="myPlayer9" poster="" controls playsInline webkit-playsinline autoplay width="160" height="105">'  
	                     + '<source src='+rtmp[8]+' type="" />' +  
	                  '</video>'  
	               });	  
      
    	       
    	           var dplayer = new Ext.Window({  
                   layout : 'fit',  
                   title:"实时视频",
                    width:500,
                    height:400,
                    modal : true,  
                    layout:"column",
                   frame: true,  
                   autoHeight:true,  
                    items : [video1,video2,video3,video4,video5,video6,video7,video8,video9]  
                    });  
    	   
                   dplayer.show();  
                   var player1 = new EZUIPlayer('myPlayer1');
                   var player2 = new EZUIPlayer('myPlayer2');
                   var player3 = new EZUIPlayer('myPlayer3');
                   var player4 = new EZUIPlayer('myPlayer4');
                   var player5 = new EZUIPlayer('myPlayer5');
                   var player6 = new EZUIPlayer('myPlayer6');
                   var player7 = new EZUIPlayer('myPlayer7');
                   var player8 = new EZUIPlayer('myPlayer8');
                   var player9 = new EZUIPlayer('myPlayer9');
                   player.on('error', function(){
                   console.log('error');
                     });
                  player.on('play', function(){
                  console.log('play');
                });
                  
                   player.on('pause', function(){
                   console.log('pause');
                 });
           
           
           
			 },
			    failure: function(response, opts) {
			        AOS.tip('失败');
			        root.hide();
			    }
		});      
	}
		}
</script>
</aos:html>