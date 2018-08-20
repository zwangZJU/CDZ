<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/common/tags.jsp"%>
<aos:html title="alarm_log" base="http" lib="ext">
<head>
 <script type="text/javascript">
 /*
 Ext.onReady(function ()
		 {
		     //初始化提示
		     Ext.QuickTips.init();
		     var productForm = Ext.create("Ext.form.Panel", {
		         title: "表单加载示例",
		         width: 300,
		         frame: true,
		         fieldDefaults: {
		             labelSeparator: ":",
		             labelWidth: 80,
		             width: 250,
		             margin:5
		         },
		         renderTo: Ext.getBody(),
		         items: [
		             { fieldLabel: "产品名称", xtype: "textfield", name: "productName", value: "U盘" },
		             { fieldLabel: "金额", xtype: "numberfield", name: "price", value: 100 },
		             { fieldLabel: "生产日期", xtype: "datefield", format: "Y-m-d", name: "date", value: new Date() },
		             { xtype: "hidden", name: "productId", value: "001" },
		             { fieldLabel: "产品简介", name: "introduction", xtype: "textarea" }
		         ],
		         buttons: [
		             { text: "加载简介", handler: loadIntroduction }
		         ]
		     });
		     */
		     var store = Ext.create('Ext.data.Store', {
		    	   id : 'statesid',
		    	   fields: ['abbr', 'name'],
		    	   data : [
		    	      {"abbr":"HTML", "name":"HTML"},
		    	      {"abbr":"CSS", "name":"CSS"},
		    	      {"abbr":"JS", "name":"JavaScript"}
		    	   ]
		    	});
 </script>
</head>
<aos:body>
</aos:body>
<aos:onready>
	<aos:viewport layout="fit">
		<aos:gridpanel id="_datagridpanel" url="alarm_logService.listAlarm_log" onrender="_datagridpanel_query" onitemdblclick="_w_update_show_all"  forceFit="false">
			<aos:docked>
			    			 	<aos:dockeditem text="新增" tooltip="新增"  onclick="_w_add_show" icon="add.png"/>
							    			    <aos:dockeditem text="修改" tooltip="修改"  onclick="_w_update_show" icon="edit.png"/>
												<aos:dockeditem text="删除" tooltip="删除" onclick="_delete" icon="del.png" />
												<aos:dockeditem text="导出" tooltip="导出" onclick="_exportexcel" icon="icon70.png" />
												<%-- <aos:dockeditem text="操作" tooltip="操作" onclick="_f_role_u_save" icon="icon70.png" /> --%>
								<aos:dockeditem xtype="tbseparator" />
				               			</aos:docked>
			<aos:column type="rowno" />
			<aos:selmodel type="checkbox" mode="multi" />
		 
						      			       <aos:column header="报警序号" dataIndex="alarm_id"   width="255" />
			    						      			       <aos:column header="设备id" dataIndex="device_id"   width="255" />
			    						      			       <aos:column header="用户手机号" dataIndex="user_phone"   width="255" />
			    						      			       <aos:column header="报警时间" dataIndex="alarm_time"   width="160" />
			    						      			       <aos:column header="出警时间" dataIndex="response_time"   width="160" />
			    						      			       <aos:column header="报警方式" dataIndex="type_"   width="255" />
			    						      			       <aos:column header="处理者" dataIndex="handler_"   width="255" />
			    						      			       <aos:column header="处理者电话" dataIndex="handler_phone"   width="255" />
			    						      			       <aos:column header="报警原因" dataIndex="reason_"   width="255" />
			    						      			       <aos:column header="报警解除" dataIndex="alarm_release"   width="255" />
			    						      			       <aos:column header="取消报警" dataIndex="is_cancel"   width="255" />
			    						      			       <aos:column header="备用1" dataIndex="beiyong1_"   width="255" />
			    						      			       <aos:column header="备用2" dataIndex="beiyong2_"   width="255" />
			    						      			       <aos:column header="备用3" dataIndex="beiyong3_"   width="255" />
			    						      			       <aos:column header="操作" rendererFn="fn_button_render" align="center" width="80" />
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
	      	   	 	         	   	       	        <aos:textfield name="beiyong1_" fieldLabel="备用1"  maxLength="255"    	         />
	      	   	 	         	   	       	        <aos:textfield name="beiyong2_" fieldLabel="备用2"  maxLength="255"    	         />
	      	   	 	         	   	       	        <aos:textfield name="beiyong3_" fieldLabel="备用3"  maxLength="255"    	         />
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
	      	    	        	   	       	        <aos:textfield name="beiyong1_" fieldLabel="备用1"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="beiyong2_" fieldLabel="备用2"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="beiyong3_" fieldLabel="备用3"  maxLength="255"    	         />
	      	    	 		</aos:formpanel>
		<aos:docked dock="bottom" ui="footer">
			<aos:dockeditem xtype="tbfill" />
			<aos:dockeditem onclick="_f_update_save" text="保存" icon="ok.png" />
			<aos:dockeditem onclick="#_w_update_data.hide();" text="关闭" icon="close.png" />
		</aos:docked>
	</aos:window>
	
	<script type="text/javascript">
		function _datagridpanel_query() {
			var params = {
			                    			  
			};
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
			var alarm_id = AOS.selection(_datagridpanel, 'alarm_id');
			
			//var info = Ext.util.Cookies.get('juid');
			Ext.Ajax.request({
			    url: '/cdz/http/do.jhtml?router=alarm_logService.updateAlarm_log3',
			    params: {
			        id: alarm_id
	    		},
			    success: function(response){
			        var text = Ext.decode(response.responseText);
			        // process server response here
			        //var value1=text["user_address"];
			   
			        Ext.getCmp("a").setValue(text.user_id);
			        Ext.getCmp("b").setValue(text.user_name);
			        Ext.getCmp("c").setValue(text.user_address);
			        Ext.getCmp("d").setValue(text.user_phone);
			        Ext.getCmp("e").setValue(text.device_id);
			        Ext.getCmp("f").setValue(text.alarm_time);
			        
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
			        	 { fieldLabel: "用户编号", id:"a",xtype: "textfield", name: "user_id", value: "/" },
			        	 { fieldLabel: "用户名称", id:"b",xtype: "textfield", name: "user_name", value: "/" },
			        	 { fieldLabel: "用户地址", id:"c",xtype: "textfield", name: "user_address", value: "/" },
			        	 { fieldLabel: "用户手机号", id:"d",xtype: "textfield", name: "user_phone", value: "/" },
			        	 { fieldLabel: "设备编号", id:"e",xtype: "textfield", name: "device_id", value: "/" },
			        	 { fieldLabel: "报警时间", id:"f",xtype: "textfield", name: "alarm_time", value: "/" }
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
				        { xtype: "button", text: "确定", handler: function () { this.up("window").close(); } },
				        { xtype: "button", text: "取消", handler: function () { this.up("window").close(); } }
				    ]
					
				});
				root.show();
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
      
     // 给选中的充电桩升级系统
       // 给选中的充电桩升级系统
       /*
function _f_role_u_save(){
	var info = Ext.util.Cookies.get('juid');
	var versionStore = Ext.create("Ext.data.Store", {
		fields : ["Version", "Value"],
		autoLoad: true,
		proxy: {
			type: "ajax",
			actionMethods: { read: "POST" },
			url: '/cdz/http/do.jhtml?router=Alarm_handleService.selectAllVersionNum&juid='+info,
			reader: {
				type: "json",
				root: "root"
			},
			writer:{
            	type:'json'
            }
		}
	});
	
	var combox = new Ext.form.ComboBox({
				xtype : "combobox",
				margin : "18 0 0 0",
				name : "version_num",
				fieldLabel : "版本",
				columnWidth : 1,
				labelAlign : 'right',
				labelWidth : 60,
				id : "version_num",
				store : versionStore,
				editable : false,
				displayField : "Value",
				valueField : "Value",
				emptyText : "--请选择版本--",
				queryMode : "local",
				style : 'padding-top:20px;margin-left:30px;'
			});
	
		 
		var root = new Ext.Window({
			title:"选择升级版本",
			width:270,
			height:120,
			frame:false,
			items:[combox],			
			bbar:['->',
			{text:"升级", frame:false,handler:function(){
				AOS.notice("提示!","确定要将系统升级到该版本吗?",function(){				
				var vn = combox.getRawValue();
				Ext.Ajax.request({
				    url: '/cdz/http/do.jhtml?router=Alarm_handleService.upgradeOne&juid='+info,
				    mathod:"POST",
				    params:{//version:vn,    
				    		//cp_id: AOS.selectone(AOS.get('_datagridpanel').data.cp_id,
				    		//cp_status: AOS.selectone(AOS.get('_datagridpanel').data.cp_status},
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
				{text:"取消", frame:false,handler:function(){AOS.tip("取消升级");root.hide();}}
			],
			resizable:false,
			closable:true,
			draggable:false,
			modal:true
			
		});
		root.show();
}
        */


        
        //按钮列转换
    	function fn_button_render(value, metaData, record) {
    		 return '<input type="button" value="接警" class="cbtn_danger" onclick="_w_role_u_show();" />'; 
        	/*return '<input type="button" value="接警" class="cbtn_danger" onclick="_f_role_u_save();" />'; */
    	}
	</script>
</aos:onready>

<script type="text/javascript">
	function _w_role_u_show(){
		 var record = AOS.selectone(AOS.get('_datagridpanel'));
         if (record) {
        	 AOS.get('_w_role_u').show();
        	 AOS.get('_f_role_u').loadRecord(record);
         }
	}
	

</script>
</aos:html>