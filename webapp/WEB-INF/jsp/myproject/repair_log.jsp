<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/common/tags.jsp"%>
<aos:html title="repair_log" base="http" lib="ext">
<aos:body>
</aos:body>
<aos:onready>
	<aos:viewport layout="border">
	    <aos:formpanel id="_f_query" layout="column" labelWidth="70" header="false" region="north" >
			<aos:docked forceBoder="0 0 1 0">
				<aos:dockeditem xtype="tbtext" text="查询条件" />
			</aos:docked>
			 <aos:textfield   name="repair_id" fieldLabel="报修序号" columnWidth="0.2"     maxLength="255"    	         />
	      	 <aos:textfield   name="device_id" fieldLabel="设备id" columnWidth="0.2"    maxLength="255"    	         />
	      	 <aos:textfield name="user_phone" fieldLabel="用户手机号" columnWidth="0.2" maxLength="255"    	         />
	      	    <aos:textfield name="handler_" fieldLabel="处理者" columnWidth="0.2" maxLength="255"    	         />
	      	   	<aos:textfield name="handler_phone" fieldLabel="处理者电话" columnWidth="0.2" maxLength="255"    	         />
			<aos:docked dock="bottom" ui="footer" margin="0 0 8 0">
				<aos:dockeditem xtype="tbfill" />
				<aos:dockeditem xtype="button" text="查询" onclick="_datagridpanel_query" icon="query.png" />
				<aos:dockeditem xtype="button" text="重置" onclick="AOS.reset(_f_query);" icon="refresh.png" />
				<aos:dockeditem xtype="button" text="导出" onclick="fn_export_excel" icon="icon70.png" />
				<aos:dockeditem xtype="tbfill" />
			</aos:docked>
		</aos:formpanel>
		<aos:gridpanel id="_datagridpanel" url="repair_logService.listRepair_log" onrender="_datagridpanel_query" onitemdblclick="_w_update_show" region="center" forceFit="false">
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

			    						      			       <aos:column header="接单处理" dataIndex="processing_state"  rendererFn="fn_balance_render7" width="70" />
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
	<aos:window id="_w_jiedan_u" title="接单">
		<aos:formpanel id="_f_jiedan_u" width="500" layout="anchor" labelWidth="65">
			<aos:hiddenfield name="repair_id" />
			 <aos:textfield name="repair_content" fieldLabel="报修内容"  maxLength="255"    	         />
			
			   
			    <aos:combobox fieldLabel="处理状态" name="processing_state" editable="false" allowBlank="false" emptyText="是否确认接单？" columnWidth="0.5">
						<aos:option value="1" display="确认接单" />
					</aos:combobox> 
			  <%--  <aos:combobox name="handler_phone" fieldLabel="处理者电话"  url="basic_userService.listHandler"    	         /> --%>
	      	   <%--  <aos:textfield name="handler_" fieldLabel="处理者"  maxLength="255"    	         />   --%>
	      	   
	      	   <aos:combobox  fieldLabel="处理者" name="handler_phone"  columnWidth="0.5"
						url="basic_userService.listHandler1" />
		
		</aos:formpanel>
		<aos:docked dock="bottom" ui="footer">
			<aos:dockeditem xtype="tbfill" />
			<aos:dockeditem onclick="_f_jiedan_u_save" text="保存" icon="ok.png" />
			<aos:dockeditem onclick="#_w_jiedan_u.hide();" text="关闭" icon="close.png" />
		</aos:docked>
	</aos:window>
	
	<script type="text/javascript">
	//刷新
	setInterval(_datagridpanel_query,60*60*1000);
	
	 var info = Ext.util.Cookies.get('juid'); 
	/*  setInterval(get_num,3000);  */
	 
	 var num2=0;
	var num3;
	setInterval(is_load,60*1000);
	
	 function is_load() {
		 var rr=get_num();
		 num3=rr.length;
		
		if (num3>num2){
			num2=num3;
			_datagridpanel_query();
		}
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
        
        function fn_button_render(value, metaData, record) {
    		return '<input type="button" value="接单" class="cbtn_danger" onclick="_w_jiedan_u_show();" />';
    	}
        
        function _f_jiedan_u_save() {
            AOS.ajax({
                forms: _f_jiedan_u,
                url: 'repair_logService.updateRepair_log2',
                 ok: function (data) {
                    _w_jiedan_u.hide();
                    _datagridpanel_store.reload();
                    AOS.tip(data.appmsg);
                } 
            });
        }
        
        
function fn_export_excel(){
	 		
	 		
			var params = AOS.getValue('_f_query');
			var params_url="";
			for(var v in params){
				
				params_url+="&"+v+"="+params[v];
				
			}
			
			AOS.file('/cdz/http/do.jhtml?router=repair_logService.exportExcel&juid='+info+params_url);
		
			
			 
		}
	</script>
</aos:onready>

<script type="text/javascript">
function fn_balance_render7(value, metaData, record, rowIndex, colIndex,
		store) {
	
	
    
    if ( value==0) {
    	
    	
    	 return '<input type="button" value="未接单" class="cbtn_danger" onclick="_w_jiedan_u_show();"  />'; 
 	 
		/*  metaData.style = 'background-color:#990099'; 
		return value; */
	} else {
/* 		metaData.style = 'background-color:#0099CC';  */
	
		 return '<input type="button" value="已接单" class="cbtn" onclick=""  />'; 
	}
	
  
}


  function _w_jiedan_u_show(){
	  
	
	  var record = AOS.selectone(AOS.get('_datagridpanel'));
    if (record) { 
	 
  	  AOS.get('_w_jiedan_u').show(); 
  	  AOS.get('_f_jiedan_u').loadRecord(record); 
  /* 	 id_list_store.load();    */
 
   } 
} 
 
  function get_num(){
	 
  
	  var result;	
	  var info = Ext.util.Cookies.get('juid'); 
	  Ext.Ajax.request({
	  	url: '/cdz/http/do.jhtml?router=repair_logService.listrepair3&juid='+info,
	      async:false,		
	      mathod:"POST",
	     
	      params:{version:5
	    },

	      success: function(response, opts) {
	      	  

	          var obj = Ext.decode(response.responseText); 
	          var ss =obj.coor;
	         /*  var ss2=ss[0]; 
	          var lat1=ss2.lat;*/
	         result=ss;
	        return result;
	      },
	      failure: function(response, opts) {
	          AOS.tip('失败');
	          root.hide();
	          
	      }
	     
	  });

	
	  return result;
	  }
  
 
</script>
</aos:html>