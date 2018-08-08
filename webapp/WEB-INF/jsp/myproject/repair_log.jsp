<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/common/tags.jsp"%>
<aos:html title="repair_log" base="http" lib="ext">
<aos:body>
</aos:body>
<aos:onready>
	<aos:viewport layout="fit">
		<aos:gridpanel id="_datagridpanel" url="repair_logService.listRepair_log" onrender="_datagridpanel_query" onitemdblclick="_w_update_show"  forceFit="false">
			<aos:docked>
			    			 	<aos:dockeditem text="新增" tooltip="新增"  onclick="_w_add_show" icon="add.png"/>
							    			    <aos:dockeditem text="修改" tooltip="修改"  onclick="_w_update_show" icon="edit.png"/>
												<aos:dockeditem text="删除" tooltip="删除" onclick="_delete" icon="del.png" />
												<%-- <aos:dockeditem text="导出" tooltip="导出" onclick="_exportexcel" icon="icon70.png" /> --%>
								<aos:dockeditem xtype="tbseparator" />
				                			</aos:docked>
			<aos:column type="rowno" />
			<aos:selmodel type="checkbox" mode="multi" />
		 
						      			       <aos:column header="报修序号" dataIndex="repair_id"   width="70" />
			    						      			       <aos:column header="设备id" dataIndex="device_id"   width="100" />
			    						      			       <aos:column header="用户手机号" dataIndex="user_phone"   width="100" />
			    						      			       <aos:column header="报修内容" dataIndex="repair_content"   width="160" />
			    						      			       <aos:column header="报修时间" dataIndex="repair_time"   width="150" />
			    						      			       <aos:column header="修复时间" dataIndex="renovate_time"   width="150" />
			    						      			       <aos:column header="处理状态" dataIndex="processing_state"   width="70" />
			    						      			       <aos:column header="状态信息" dataIndex="state_info"   width="70" />
			    						      			       <aos:column header="处理者" dataIndex="handler_"   width="70" />
			    						      			       <aos:column header="处理者电话" dataIndex="handler_phone"   width="100" />
			    						      			       <aos:column header="故障原因" dataIndex="error_reason"   width="70" />
			    						      			      <aos:column header="修理完成" dataIndex="is_completed"   width="70" />
			    						      			     <%--   <aos:column header="备用1" dataIndex="beiyong1_"   width="255" />
			    						      			       <aos:column header="备用2" dataIndex="beiyong2_"   width="255" />
			    						      			       <aos:column header="备用3" dataIndex="baiyong3_"   width="255" />  --%>
			    			 		</aos:gridpanel>
	</aos:viewport>
	
	<aos:window id="_w_add_data" title="新增报修日志" width="600"   height="400"  autoScroll="true">
		<aos:formpanel id="_f_add"  width="600-20"  autoScroll="true"   layout="anchor" labelWidth="70">
         	   	       	        <aos:textfield name="repair_id" fieldLabel="报修序号"   allowBlank="false"   maxLength="255"    	         />
	      	   	 	         	   	       	        <aos:textfield name="device_id" fieldLabel="设备id"   allowBlank="false"   maxLength="255"    	         />
	      	   	 	         	   	       	        <aos:textfield name="user_phone" fieldLabel="用户手机号"  maxLength="255"    	         />
	      	   	 	         	   	       	        <aos:textfield name="repair_content" fieldLabel="报修内容"  maxLength="255"    	         />
	      	   	 	         	      	             <aos:datetimefield name="repair_time" fieldLabel="报修时间"     editable="true"/>
	  	 	         	      	                     <aos:datetimefield name="renovate_time" fieldLabel="修复时间"     editable="true"/>
	  	 	         	   	       	                 <aos:textfield name="processing_state" fieldLabel="处理状态"  maxLength="255"    	         />
	      	   	 	         	   	       	        <aos:textfield name="state_info" fieldLabel="状态信息"  maxLength="255"    	         />
	      	   	 	         	   	       	        <aos:textfield name="handler_" fieldLabel="处理者"  maxLength="255"    	         />
	      	   	 	         	   	       	        <aos:textfield name="handler_phone" fieldLabel="处理者电话"  maxLength="255"    	         />
	      	   	 	         	   	       	        <aos:textfield name="error_reason" fieldLabel="故障原因"  maxLength="255"    	         />
	      	   	 	         	   	       	        <aos:textfield name="is_completed" fieldLabel="修理完成"  maxLength="255"    	         />
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
	
	<aos:window id="_w_update_data" title="修改报修日志" width="600"   height="400"  autoScroll="true">
			<aos:formpanel id="_f_update"   width="600-20"  autoScroll="true"   layout="anchor" labelWidth="70">
        	   	       	        <aos:textfield name="repair_id" fieldLabel="报修序号"   allowBlank="false"   maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="device_id" fieldLabel="设备id"   allowBlank="false"   maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="user_phone" fieldLabel="用户手机号"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="repair_content" fieldLabel="报修内容"  maxLength="255"    	         />
	      	    	        	      	       <aos:datetimefield name="repair_time" fieldLabel="报修时间"   	                   editable="true"/>
	    	        	      	       <aos:datetimefield name="renovate_time" fieldLabel="修复时间"   	                  editable="true"/>
	    	        	   	       	        <aos:textfield name="processing_state" fieldLabel="处理状态"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="state_info" fieldLabel="状态信息"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="handler_" fieldLabel="处理者"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="handler_phone" fieldLabel="处理者电话"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="error_reason" fieldLabel="故障原因"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="is_completed" fieldLabel="修理完成"  maxLength="255"    	         />
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
		//新增 报修日志
		function _f_add_save(){
				AOS.ajax({
				forms : _f_add,
				url : 'repair_logService.saveRepair_log',
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
		
		//弹出修改窗口 报修日志
		function _w_update_show(){
			AOS.reset(_f_update);
			var record = AOS.selectone(_datagridpanel);
			if(record){
				_w_update_data.show();
				_f_update.loadRecord(record);
			}
		}
	    
	   //修改   报修日志
		function _f_update_save(){
			AOS.ajax({
				forms : _f_update,
				url : 'repair_logService.updateRepair_log',
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
	   
        //删除 报修日志
	 	function _delete(){
	 				var selection = AOS.selection(_datagridpanel, 'repair_id');
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
	 						url : 'repair_logService.deleteRepair_log',
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