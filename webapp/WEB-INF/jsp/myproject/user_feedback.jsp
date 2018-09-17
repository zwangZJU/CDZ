<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/common/tags.jsp"%>
<aos:html title="user_feedback" base="http" lib="ext">
<aos:body>
</aos:body>
<aos:onready>
	<aos:viewport layout="fit">
		<aos:gridpanel id="_datagridpanel" url="user_feedbackService.listUser_feedback" onrender="_datagridpanel_query" onitemdblclick="_w_update_show"  forceFit="false">
			<aos:docked>
			    			 	<aos:dockeditem text="新增" tooltip="新增"  onclick="_w_add_show" icon="add.png"/>
							    			    <aos:dockeditem text="修改" tooltip="修改"  onclick="_w_update_show" icon="edit.png"/>
												<aos:dockeditem text="删除" tooltip="删除" onclick="_delete" icon="del.png" />
												<%-- <aos:dockeditem text="导出" tooltip="导出" onclick="_exportexcel" icon="icon70.png" /> --%>
								<aos:dockeditem xtype="tbseparator" />
				        			</aos:docked>
			<aos:column type="rowno" />
			<aos:selmodel type="checkbox" mode="multi" />
		 
						      			       <aos:column header="id" hidden="true"  dataIndex="id_"   width="255"  />
			    						      			       <aos:column header="账号（手机号）" dataIndex="account_"   width="255" />
			    						      			       <aos:column header="反馈建议" dataIndex="advice_"   width="600" />
			    						      			       <aos:column header="时间" dataIndex="time_"   width="160" />
			    						      			       <%-- <aos:column header="备用1" dataIndex="beiyong1_"   width="255" />
			    						      			       <aos:column header="备用2" dataIndex="beiyong2_"   width="255" />
			    						      			       <aos:column header="备用3" dataIndex="beiyong3_"   width="255" /> --%>
			    			 		</aos:gridpanel>
	</aos:viewport>
	
	<aos:window id="_w_add_data" title="新增用户反馈建议" width="600"   height="400"  autoScroll="true">
		<aos:formpanel id="_f_add"  width="600-20"     layout="anchor" labelWidth="70">
               <aos:hiddenfield name="id_"/>
    	         	   	       	        <aos:textfield name="account_" fieldLabel="账号（手机号）"  maxLength="255"    	         />
	      	   	 	         	   	       	        <aos:textfield name="advice_" fieldLabel="反馈建议"  maxLength="1000"    	         />
	      	   	 	         	      	    <aos:datetimefield name="time_" fieldLabel="时间"      editable="true"/>
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
	
	<aos:window id="_w_update_data" title="修改用户反馈建议" width="600"   height="400"  autoScroll="true">
			<aos:formpanel id="_f_update"   width="600-20"     layout="anchor" labelWidth="70">
              <aos:hiddenfield name="id_"/>
           	   	       	        <aos:textfield name="account_" fieldLabel="账号（手机号）"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="advice_" fieldLabel="反馈建议"  maxLength="1000"    	         />
	      	    	        	      	       <aos:datetimefield name="time_" fieldLabel="时间"    editable="true"/>
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
		//新增 用户反馈建议
		function _f_add_save(){
				AOS.ajax({
				forms : _f_add,
				url : 'user_feedbackService.saveUser_feedback',
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
		
		//弹出修改窗口 用户反馈建议
		function _w_update_show(){
			AOS.reset(_f_update);
			var record = AOS.selectone(_datagridpanel);
			if(record){
				_w_update_data.show();
				_f_update.loadRecord(record);
			}
		}
	    
	   //修改   用户反馈建议
		function _f_update_save(){
			AOS.ajax({
				forms : _f_update,
				url : 'user_feedbackService.updateUser_feedback',
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
	   
        //删除 用户反馈建议
	 	function _delete(){
	 				var selection = AOS.selection(_datagridpanel, 'id_');
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
	 						url : 'user_feedbackService.deleteUser_feedback',
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