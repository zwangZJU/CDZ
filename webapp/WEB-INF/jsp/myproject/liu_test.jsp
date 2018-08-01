<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/common/tags.jsp"%>
<aos:html title="charging_pile" base="http" lib="ext">
<aos:body>
</aos:body>
<aos:onready>
	<aos:viewport layout="fit">
		<aos:gridpanel id="_datagridpanel" url="liu_testController.listLiu_test" onrender="_datagridpanel_query" onitemdblclick="_w_update_show"  forceFit="false">
			<aos:docked>
			    			 	<aos:dockeditem text="新增" tooltip="新增"  onclick="_w_add_show" icon="add.png"/>
							    			    <aos:dockeditem text="修改" tooltip="修改"  onclick="_w_update_show" icon="edit.png"/>
												<aos:dockeditem text="删除" tooltip="删除" onclick="_delete" icon="del.png" />
												<aos:dockeditem text="导出" tooltip="导出" onclick="_exportexcel" icon="icon70.png" />
								<aos:dockeditem xtype="tbseparator" />
				     			</aos:docked>
			<aos:column type="rowno" />
			<aos:selmodel type="checkbox" mode="multi" />
		 
						      			       <aos:column header="ID" hidden="true"  dataIndex="id_"   width="100"  />
			    						      			       <aos:column header="姓名" dataIndex="name_"   width="100" />
			    						      			       <aos:column header="地址" dataIndex="address_"   width="100" />
			    						      			       <aos:column header="性别" dataIndex="sex_"   width="100" />
			    			 		</aos:gridpanel>
	</aos:viewport>
	
	<aos:window id="_w_add_data" title="新增刘" width="600"   height="400"  autoScroll="true">
		<aos:formpanel id="_f_add"  width="600-20"     layout="anchor" labelWidth="70">
               <aos:hiddenfield name="id_"/>
    	         	   	       	        <aos:textfield name="name_" fieldLabel="姓名"  maxLength="100"    	         />
	      	   	 	         	   	       	        <aos:textfield name="address_" fieldLabel="地址"  maxLength="100"    	         />
	      	   	 	         	   	       	        <aos:textfield name="sex_" fieldLabel="性别"  maxLength="2"    	         />
	      	   	 			</aos:formpanel>
		<aos:docked dock="bottom" ui="footer">
			<aos:dockeditem xtype="tbfill" />
			<aos:dockeditem onclick="_f_add_save" text="保存" icon="ok.png" />
			<aos:dockeditem onclick="#_w_add_data.hide();" text="关闭" icon="close.png" />
		</aos:docked>
	</aos:window>
	
	<aos:window id="_w_update_data" title="修改刘" width="600"   height="400"  autoScroll="true">
			<aos:formpanel id="_f_update"   width="600-20"     layout="anchor" labelWidth="70">
              <aos:hiddenfield name="id_"/>
           	   	       	        <aos:textfield name="name_" fieldLabel="姓名"  maxLength="100"    	         />
	      	    	        	   	       	        <aos:textfield name="address_" fieldLabel="地址"  maxLength="100"    	         />
	      	    	        	   	       	        <aos:textfield name="sex_" fieldLabel="性别"  maxLength="2"    	         />
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
		//新增 刘
		function _f_add_save(){
				AOS.ajax({
				forms : _f_add,
				url : 'liu_testController.saveLiu_test',
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
		
		//弹出修改窗口 刘
		function _w_update_show(){
			AOS.reset(_f_update);
			var record = AOS.selectone(_datagridpanel);
			if(record){
				_w_update_data.show();
				_f_update.loadRecord(record);
			}
		}
	    
	   //修改   刘
		function _f_update_save(){
			AOS.ajax({
				forms : _f_update,
				url : 'liu_testController.updateLiutest',
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
	   
        //删除 刘
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
	 						url : 'liu_testController.deleteLiutest',
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