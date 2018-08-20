<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/common/tags.jsp"%>
<aos:html title="camera" base="http" lib="ext">
<aos:body>
</aos:body>
<aos:onready>
	<aos:viewport layout="fit">
		<aos:gridpanel id="_datagridpanel" url="cameraService.listCamera" onrender="_datagridpanel_query" onitemdblclick="_w_update_show"  forceFit="false">
			<aos:docked>
			    			 	<aos:dockeditem text="新增" tooltip="新增"  onclick="_w_add_show" icon="add.png"/>
							    			    <aos:dockeditem text="修改" tooltip="修改"  onclick="_w_update_show" icon="edit.png"/>
												<aos:dockeditem text="删除" tooltip="删除" onclick="_delete" icon="del.png" />
												<aos:dockeditem text="" tooltip="导出"  />
								<aos:dockeditem xtype="tbseparator" />
				              			</aos:docked>
			<aos:column type="rowno" />
			<aos:selmodel type="checkbox" mode="multi" />
		 
						      			       <aos:column header="序号" dataIndex="camera_id"   width="255" />
			    						      			       <aos:column header="设备id" dataIndex="device_id"   width="255" />
			    						      			       <aos:column header="摄像头序列号" dataIndex="camera_serial"   width="255" />
			    						      			       <aos:column header="设备通道号" dataIndex="camera_no"   width="255" />
			    						      			       <aos:column header="视频当前时间" dataIndex="osd_"   width="160" />
			    						      			       <aos:column header="访问令牌" dataIndex="access_token"   width="255" />
			    						      			       <aos:column header="验证码" dataIndex="verification_code"   width="255" />
			    						      			       <aos:column header="摄像头型号" dataIndex="camera_type"   width="255" />
			    						      			       <aos:column header="标签" dataIndex="camera_label"   width="255" />
			    						      			       <aos:column header="云服务" dataIndex="cloud_service"   width="255" />
			    						      			       <aos:column header="备用1" dataIndex="beiyong1_"   width="255" />
			    						      			       <aos:column header="备用2" dataIndex="beiyong2_"   width="255" />
			    						      			       <aos:column header="备用3" dataIndex="baiyong3_"   width="255" />
			    			 		</aos:gridpanel>
	</aos:viewport>
	
	<aos:window id="_w_add_data" title="新增摄像头" width="600"   height="400"  autoScroll="true">
		<aos:formpanel id="_f_add"  width="600-20"     layout="anchor" labelWidth="70">
         	   	       	        <aos:textfield name="camera_id" fieldLabel="序号"   allowBlank="false"   maxLength="255"    	         />
	      	   	 	         	   	       	        <aos:textfield name="device_id" fieldLabel="设备id"  maxLength="255"    	         />
	      	   	 	         	   	       	        <aos:textfield name="camera_serial" fieldLabel="摄像头序列号"  maxLength="255"    	         />
	      	   	 	         	   	       	        <aos:textfield name="camera_no" fieldLabel="设备通道号"  maxLength="255"    	         />
	      	   	 	         	      	    <aos:datefield name="osd_" fieldLabel="视频当前时间"   	              format="Y-m-d 00:00:00"     editable="true"/>
	  	 	         	   	       	        <aos:textfield name="access_token" fieldLabel="访问令牌"  maxLength="255"    	         />
	      	   	 	         	   	       	        <aos:textfield name="verification_code" fieldLabel="验证码"  maxLength="255"    	         />
	      	   	 	         	   	       	        <aos:textfield name="camera_type" fieldLabel="摄像头型号"  maxLength="255"    	         />
	      	   	 	         	   	       	        <aos:textfield name="camera_label" fieldLabel="标签"  maxLength="255"    	         />
	      	   	 	         	   	       	        <aos:textfield name="cloud_service" fieldLabel="云服务"  maxLength="255"    	         />
	      	   	 	         	   	       	        <aos:textfield name="beiyong1_" fieldLabel="备用1"  maxLength="255"    	         />
	      	   	 	         	   	       	        <aos:textfield name="beiyong2_" fieldLabel="备用2"  maxLength="255"    	         />
	      	   	 	         	   	       	        <aos:textfield name="baiyong3_" fieldLabel="备用3"  maxLength="255"    	         />
	      	   	 			</aos:formpanel>
		<aos:docked dock="bottom" ui="footer">
			<aos:dockeditem xtype="tbfill" />
			<aos:dockeditem onclick="_f_add_save" text="保存" icon="ok.png" />
			<aos:dockeditem onclick="#_w_add_data.hide();" text="关闭" icon="close.png" />
		</aos:docked>
	</aos:window>
	
	<aos:window id="_w_update_data" title="修改摄像头" width="600"   height="400"  autoScroll="true">
			<aos:formpanel id="_f_update"   width="600-20"     layout="anchor" labelWidth="70">
        	   	       	        <aos:textfield name="camera_id" fieldLabel="序号"   allowBlank="false"   maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="device_id" fieldLabel="设备id"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="camera_serial" fieldLabel="摄像头序列号"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="camera_no" fieldLabel="设备通道号"  maxLength="255"    	         />
	      	    	        	      	       <aos:datefield name="osd_" fieldLabel="视频当前时间"   	                 format="Y-m-d 00:00:00"     editable="true"/>
	    	        	   	       	        <aos:textfield name="access_token" fieldLabel="访问令牌"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="verification_code" fieldLabel="验证码"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="camera_type" fieldLabel="摄像头型号"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="camera_label" fieldLabel="标签"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="cloud_service" fieldLabel="云服务"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="beiyong1_" fieldLabel="备用1"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="beiyong2_" fieldLabel="备用2"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="baiyong3_" fieldLabel="备用3"  maxLength="255"    	         />
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
		//新增 摄像头
		function _f_add_save(){
				AOS.ajax({
				forms : _f_add,
				url : 'cameraService.saveCamera',
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
		
		//弹出修改窗口 摄像头
		function _w_update_show(){
			AOS.reset(_f_update);
			var record = AOS.selectone(_datagridpanel);
			if(record){
				_w_update_data.show();
				_f_update.loadRecord(record);
			}
		}
	    
	   //修改   摄像头
		function _f_update_save(){
			AOS.ajax({
				forms : _f_update,
				url : 'cameraService.updateCamera',
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
	   
        //删除 摄像头
	 	function _delete(){
	 				var selection = AOS.selection(_datagridpanel, 'camera_id');
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
	 						url : 'cameraService.deleteCamera',
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
	</script>
</aos:onready>
</aos:html>