<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/common/tags.jsp"%>
<aos:html title="charging_pile" base="http" lib="ext">
<aos:body>
</aos:body>
<aos:onready>
	<aos:viewport layout="fit">
		<aos:gridpanel id="_datagridpanel" url="basic_userService.listBasic_user" onrender="_datagridpanel_query" onitemdblclick="_w_update_show"  forceFit="false">
			<aos:docked>
			    			 	<aos:dockeditem text="新增" tooltip="新增"  onclick="_w_add_show" icon="add.png"/>
							    			    <aos:dockeditem text="修改" tooltip="修改"  onclick="_w_update_show" icon="edit.png"/>
												<aos:dockeditem text="删除" tooltip="删除" onclick="_delete" icon="del.png" />
												<aos:dockeditem text="导出" tooltip="导出" onclick="_exportexcel" icon="icon70.png" />
								<aos:dockeditem xtype="tbseparator" />
				                                     			</aos:docked>
			<aos:column type="rowno" />
			<aos:selmodel type="checkbox" mode="multi" />
		 
						      			       <aos:column header="流水号" hidden="true"  dataIndex="id_"   width="100"  />
			    						      			       <aos:column header="用户登录帐号" dataIndex="account"   width="255" />
			    						      			       <aos:column header="密码" dataIndex="password"   width="255" />
			    						      			       <aos:column header="昵称" dataIndex="nickname"   width="255" />
			    						      			       <aos:column header="性别" dataIndex="sex"   width="255" />
			    						      			       <aos:column header="用户状态" dataIndex="status"   width="255" />
			    						      			       <aos:column header="用户类型" dataIndex="user_type"   width="255" />
			    						      			       <aos:column header="图片地址" dataIndex="img_url"   width="255" />
			    						      			       <aos:column header="电子邮件" dataIndex="email"   width="255" />
			    						      			       <aos:column header="手机号" dataIndex="mobile"   width="255" />
			    						      			       <aos:column header="年龄" dataIndex="age"   width="255" />
			    						      			       <aos:column header="业务对照码" dataIndex="biz_code"   width="255" />
			    						      			       <aos:column header="联系地址" dataIndex="address"   width="255" />
			    						      			       <aos:column header="介绍/备注" dataIndex="note"   width="255" />
			    						      			       <aos:column header="是否已删除" dataIndex="is_del"   width="255" />
			    						      			       <aos:column header="创建时间" dataIndex="create_at"   width="255" />
			    						      			       <aos:column header="创建人ID" dataIndex="create_by"   width="255" />
			    						      			       <aos:column header="最后登录时间" dataIndex="login_time"   width="255" />
			    						      			       <aos:column header="排序" dataIndex="sort"   width="255" />
			    						      			       <aos:column header="手机ID" dataIndex="phone_id"   width="255" />
			    						      			       <aos:column header="手机类型" dataIndex="phone_type"   width="255" />
			    						      			       <aos:column header="金币" dataIndex="gold_coins"   width="255" />
			    						      			       <aos:column header="支付密码" dataIndex="pay_password"   width="255" />
			    						      			       <aos:column header="押金" dataIndex="deposit_amt"   width="255" />
			    						      			       <aos:column header="押金支付时间" dataIndex="deposit_date"   width="255" />
			    						      			       <aos:column header="押金状态" dataIndex="deposit_status"   width="255" />
			    						      			       <aos:column header="级别" dataIndex="grade"   width="255" />
			    						      			       <aos:column header="姓名" dataIndex="name"   width="255" />
			    						      			       <aos:column header="身份证号码" dataIndex="id_card"   width="255" />
			    						      			       <aos:column header="用户头像" dataIndex="avatar"   width="255" />
			    						      			       <aos:column header="是否认证" dataIndex="is_cert"   width="255" />
			    						      			       <aos:column header="金币状态" dataIndex="gold_coins_status"   width="255" />
			    						      			       <aos:column header="设备编号" dataIndex="device_number"   width="255" />
			    						      			       <aos:column header="登录会话id" dataIndex="token"   width="255" />
			    						      			       <aos:column header="qq号码" dataIndex="qq"   width="255" />
			    						      			       <aos:column header="微信" dataIndex="wechat"   width="255" />
			    			 		</aos:gridpanel>
	</aos:viewport>
	
	<aos:window id="_w_add_data" title="新增用户信息" width="600"   height="400"  autoScroll="true">
		<aos:formpanel id="_f_add"  width="600-20"     layout="column" labelWidth="70" autoScroll="true">
               <aos:hiddenfield name="id_"/>
    	         	   	       	        <aos:textfield name="account" fieldLabel="用户登录帐号"   allowBlank="false"   maxLength="255"    	         />
	      	   	 	         	   	       	        <aos:textfield name="password" fieldLabel="密码"   allowBlank="false"   maxLength="255"    	         />
	      	   	 	         	   	       	        <aos:textfield name="nickname" fieldLabel="昵称"  maxLength="255"    	         />
	      	   	 	         	   	       	        <aos:textfield name="sex" fieldLabel="性别"  maxLength="255"    	         />
	      	   	 	         	   	       	        <aos:textfield name="status" fieldLabel="用户状态"   allowBlank="false"   maxLength="255"    	         />
	      	   	 	         	   	       	        <aos:textfield name="user_type" fieldLabel="用户类型"  maxLength="255"    	         />
	      	   	 	         	   	       	        <aos:textfield name="img_url" fieldLabel="图片地址"  maxLength="255"    	         />
	      	   	 	         	   	       	        <aos:textfield name="email" fieldLabel="电子邮件"  maxLength="255"    	         />
	      	   	 	         	   	       	        <aos:textfield name="mobile" fieldLabel="手机号"  maxLength="255"    	         />
	      	   	 	         	   	       	        <aos:textfield name="age" fieldLabel="年龄"  maxLength="255"    	         />
	      	   	 	         	   	       	        <aos:textfield name="biz_code" fieldLabel="业务对照码"  maxLength="255"    	         />
	      	   	 	         	   	       	        <aos:textfield name="address" fieldLabel="联系地址"  maxLength="255"    	         />
	      	   	 	         	   	       	        <aos:textfield name="note" fieldLabel="介绍/备注"  maxLength="255"    	         />
	      	   	 	         	   	       	        <aos:textfield name="is_del" fieldLabel="是否已删除"   allowBlank="false"   maxLength="255"    	         />
	      	   	 	         	      	    <aos:datefield name="create_at" fieldLabel="创建时间"   	                    editable="true"/>
	  	 	         	   	       	        <aos:textfield name="create_by" fieldLabel="创建人ID"  maxLength="255"    	         />
	      	   	 	         	      	    <aos:datefield name="login_time" fieldLabel="最后登录时间"   	                    editable="true"/>
	  	 	         	      	    <aos:numberfield name="sort" fieldLabel="排序"   	       allowDecimals = "false"    />
	   	 	         	   	       	        <aos:textfield name="phone_id" fieldLabel="手机ID"  maxLength="255"    	         />
	      	   	 	         	   	       	        <aos:textfield name="phone_type" fieldLabel="手机类型"  maxLength="255"    	         />
	      	   	 	         	      	    <aos:numberfield name="gold_coins" fieldLabel="金币"   	      decimalPrecision="2"    />
	   	 	         	   	       	        <aos:textfield name="pay_password" fieldLabel="支付密码"  maxLength="255"    	         />
	      	   	 	         	      	    <aos:numberfield name="deposit_amt" fieldLabel="押金"   	      decimalPrecision="2"    />
	   	 	         	      	    <aos:datefield name="deposit_date" fieldLabel="押金支付时间"   	                    editable="true"/>
	  	 	         	   	       	        <aos:textfield name="deposit_status" fieldLabel="押金状态"  maxLength="255"    	         />
	      	   	 	         	   	       	        <aos:textfield name="grade" fieldLabel="级别"  maxLength="255"    	         />
	      	   	 	         	   	       	        <aos:textfield name="name" fieldLabel="姓名"  maxLength="255"    	         />
	      	   	 	         	   	       	        <aos:textfield name="id_card" fieldLabel="身份证号码"  maxLength="255"    	         />
	      	   	 	         	   	       	        <aos:textfield name="avatar" fieldLabel="用户头像"  maxLength="255"    	         />
	      	   	 	         	   	       	        <aos:textfield name="is_cert" fieldLabel="是否认证"  maxLength="255"    	         />
	      	   	 	         	   	       	        <aos:textfield name="gold_coins_status" fieldLabel="金币状态"  maxLength="255"    	         />
	      	   	 	         	   	       	        <aos:textfield name="device_number" fieldLabel="设备编号"  maxLength="255"    	         />
	      	   	 	         	   	       	        <aos:textfield name="token" fieldLabel="登录会话id"  maxLength="255"    	         />
	      	   	 	         	   	       	        <aos:textfield name="qq" fieldLabel="qq号码"  maxLength="255"    	         />
	      	   	 	         	   	       	        <aos:textfield name="wechat" fieldLabel="微信"  maxLength="255"    	         />
	      	   	 			</aos:formpanel>
		<aos:docked dock="bottom" ui="footer">
			<aos:dockeditem xtype="tbfill" />
			<aos:dockeditem onclick="_f_add_save" text="保存" icon="ok.png" />
			<aos:dockeditem onclick="#_w_add_data.hide();" text="关闭" icon="close.png" />
		</aos:docked>
	</aos:window>
	
	<aos:window id="_w_update_data" title="修改用户信息" width="600"   height="400"  autoScroll="true">
			<aos:formpanel id="_f_update"   width="600-20"     layout="column" labelWidth="70" autoScroll="true">
              <aos:hiddenfield name="id_"/>
           	   	       	        <aos:textfield name="account" fieldLabel="用户登录帐号"   allowBlank="false"   maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="password" fieldLabel="密码"   allowBlank="false"   maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="nickname" fieldLabel="昵称"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="sex" fieldLabel="性别"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="status" fieldLabel="用户状态"   allowBlank="false"   maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="user_type" fieldLabel="用户类型"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="img_url" fieldLabel="图片地址"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="email" fieldLabel="电子邮件"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="mobile" fieldLabel="手机号"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="age" fieldLabel="年龄"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="biz_code" fieldLabel="业务对照码"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="address" fieldLabel="联系地址"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="note" fieldLabel="介绍/备注"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="is_del" fieldLabel="是否已删除"   allowBlank="false"   maxLength="255"    	         />
	      	    	        	      	       <aos:datefield name="create_at" fieldLabel="创建时间"   	                       editable="true"/>
	    	        	   	       	        <aos:textfield name="create_by" fieldLabel="创建人ID"  maxLength="255"    	         />
	      	    	        	      	       <aos:datefield name="login_time" fieldLabel="最后登录时间"   	                       editable="true"/>
	    	        	      	       <aos:numberfield name="sort" fieldLabel="排序"   	           />
	    	        	   	       	        <aos:textfield name="phone_id" fieldLabel="手机ID"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="phone_type" fieldLabel="手机类型"  maxLength="255"    	         />
	      	    	        	      	       <aos:numberfield name="gold_coins" fieldLabel="金币"   	         decimalPrecision="2"    />
	    	        	   	       	        <aos:textfield name="pay_password" fieldLabel="支付密码"  maxLength="255"    	         />
	      	    	        	      	       <aos:numberfield name="deposit_amt" fieldLabel="押金"   	         decimalPrecision="2"    />
	    	        	      	       <aos:datefield name="deposit_date" fieldLabel="押金支付时间"   	                       editable="true"/>
	    	        	   	       	        <aos:textfield name="deposit_status" fieldLabel="押金状态"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="grade" fieldLabel="级别"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="name" fieldLabel="姓名"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="id_card" fieldLabel="身份证号码"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="avatar" fieldLabel="用户头像"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="is_cert" fieldLabel="是否认证"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="gold_coins_status" fieldLabel="金币状态"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="device_number" fieldLabel="设备编号"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="token" fieldLabel="登录会话id"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="qq" fieldLabel="qq号码"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="wechat" fieldLabel="微信"  maxLength="255"    	         />
	      	    	 		</aos:formpanel>
		<aos:docked dock="bottom" ui="footer">
			<aos:dockeditem xtype="tbfill" />
			<aos:dockeditem onclick="_f_update_save" text="保存" icon="ok.png" />
			<aos:dockeditem onclick="#_w_update_data.hide();" text="关闭" icon="close.png" />
		</aos:docked>
	</aos:window>
	
	<script type="text/javascript">
	 setInterval(_datagridpanel_query,60*60*1000); 
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
		//新增 用户信息
		function _f_add_save(){
				AOS.ajax({
				forms : _f_add,
				url : 'basic_userService.saveBasic_user',
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
		
		//弹出修改窗口 用户信息
		function _w_update_show(){
			AOS.reset(_f_update);
			var record = AOS.selectone(_datagridpanel);
			if(record){
				_w_update_data.show();
				_f_update.loadRecord(record);
			}
		}
	    
	   //修改   用户信息
		function _f_update_save(){
			AOS.ajax({
				forms : _f_update,
				url : 'basic_userService.updateBasic_user',
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
	   
        //删除 用户信息
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
	 						url : 'basic_userService.deleteBasic_user',
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