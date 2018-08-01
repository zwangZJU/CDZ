<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/common/tags.jsp"%>

<aos:html title="members" base="http" lib="ext">
<aos:body>
<div id="_div_photo" class="x-hidden" align="center">
        <img id="_img_photo" class="app_cursor_pointer" src="" width="200"
            height="150">
</div>
<div id="_div_photo1" class="x-hidden" align="center">
        <img id="_img_photo1" class="app_cursor_pointer" src="" width="200"
            height="150">
</div>
<div id="_div_photo3" class="x-hidden" align="center">
        <img id="_img_photo3" class="app_cursor_pointer" src="" width="200"
            height="150">
</div>
<div id="_div_photo4" class="x-hidden" align="center">
        <img id="_img_photo4" class="app_cursor_pointer" src="" width="200"
            height="150">
</div>
</aos:body>
</aos:html>
<aos:onready>
	<aos:viewport layout="border">
		<aos:formpanel id="_f_query" layout="column" labelWidth="70" header="false" region="north" border="false">
			<aos:docked forceBoder="0 0 1 0">
				<aos:dockeditem xtype="tbtext" text="查询条件" />
			</aos:docked>
			<aos:textfield name="account_" fieldLabel="会员手机号" columnWidth="0.25" />
			<aos:textfield name="name_" fieldLabel="昵称" columnWidth="0.25" />
			<aos:textfield name="user_name" fieldLabel="姓名" columnWidth="0.25" />
			<aos:textfield name="id_card" fieldLabel="身份证号码" columnWidth="0.25" />
			<aos:combobox name="status_" fieldLabel="会员状态" dicField="user_status_" columnWidth="0.25" />
			<aos:combobox name="grade_" fieldLabel="会员级别" dicField="grade_" columnWidth="0.25" />
			<aos:combobox name="deposit_status" fieldLabel="押金状态" dicField="deposit_status" columnWidth="0.25" />
			<aos:combobox name="is_cert" fieldLabel="是否认证" dicField="is_cert" columnWidth="0.25" />
			<%--
			<aos:textfield name="name_" fieldLabel="持卡人姓名" columnWidth="0.25" />
			<aos:combobox name="card_type_" fieldLabel="卡类型" dicField="card_type_" columnWidth="0.25" />
			--%>
			<aos:docked dock="bottom" ui="footer" margin="0 0 8 0">
				<aos:dockeditem xtype="tbfill" />
				<aos:dockeditem xtype="button" text="查询" onclick="_g_members_query" icon="query.png" />
				<aos:dockeditem xtype="button" text="重置" onclick="AOS.reset(_f_query);" icon="refresh.png" />
				<aos:dockeditem xtype="button" text="导出" onclick="fn_export_excel" icon="icon70.png" />
				<aos:dockeditem xtype="tbfill" />
			</aos:docked>
		</aos:formpanel>

		<aos:gridpanel id="_g_members" url="membersController.listMembers" forceFit="false" onrender="_g_members_query"
			onitemdblclick="#_w_members2.show();" region="center">
			<aos:menu>
				<aos:menuitem text="查看会员信息" onclick="_g_members_update" icon="icon153.png" />
				<aos:menuitem text="禁用" onclick="fn_del()" icon="stop.gif" />
				<aos:menuitem text="启用" onclick="fn_del()" icon="ok.png" />
				<aos:menuitem text="重置密码" onclick="_w_pwd_show" icon="key.png" />
				<aos:menuitem xtype="menuseparator" />
				<aos:menuitem text="刷新" onclick="#_g_members_store.reload();" icon="refresh.png" />
			</aos:menu>
			<aos:docked forceBoder="1 0 1 0">
				<aos:dockeditem xtype="tbtext" text="会员信息列表" />
				<aos:dockeditem xtype="tbseparator" />
				<aos:dockeditem text="查看会员信息" icon="icon153.png" onclick="_g_members_update" />
				<aos:dockeditem text="禁用" icon="stop.gif" onclick="_g_members_stopAll" />
				<aos:dockeditem text="启用" icon="ok.png" onclick="_g_members_okAll" />
				<aos:dockeditem text="认证" icon="own.png" onclick="_g_members_update1" />
				<aos:dockeditem text="重置密码" onclick="_w_pwd_show" icon="key.png" />
			</aos:docked>
			<aos:selmodel type="checkbox" mode="multi" />
			<aos:column type="rowno" />
			<aos:column header="流水号" dataIndex="id_" hidden="true" />
			<aos:column header="流水号" dataIndex="vehicle_img" hidden="true" />
			<aos:column header="流水号" dataIndex="driver_img" hidden="true" />	
			<aos:column header="会员手机号" dataIndex="account_" width="100" celltip="true"/>	
			<aos:column header="昵称" dataIndex="name_" width="100" celltip="true"/>	
			<aos:column header="用户状态" dataIndex="status_" rendererField="user_status_"  width="80" />	
			<aos:column header="注册时间" dataIndex="create_time_" type="date" format="Y-m-d H:i:s" width="150" />	
			<aos:column header="最后登录时间" dataIndex="login_time_" type="date" format="Y-m-d H:i:s" width="150" />	
			<aos:column header="金币" dataIndex="gold_coins" width="60" />	
			<aos:column header="押金" dataIndex="deposit_amt" width="60" />	
			<%--<aos:column header="押金支付时间" dataIndex="deposit_date" type="date" format="Y-m-d H:i:s" width="100" />	--%>
			<aos:column header="押金状态" dataIndex="deposit_status" rendererField="deposit_status" width="80" />	
			<aos:column header="级别" dataIndex="grade_" width="80" rendererField="grade_"/>	
			<aos:column header="是否认证" dataIndex="is_cert" rendererField="is_cert" width="80" />
			<aos:column header="姓名" dataIndex="user_name" width="100" celltip="true"/>	
			<aos:column header="身份证号码" dataIndex="id_card" width="100" celltip="true"/>	
			<aos:column header="行驶证号" dataIndex="vehicle_license" width="100" celltip="true" />	
			<aos:column header="驾驶证号" dataIndex="driver_license" width="100" celltip="true"/>	
				
	
			<%-- 数据字典、时间例子
			<aos:column header="卡类型" dataIndex="card_type_" rendererField="card_type_" width="60" />
			<aos:column header="出生日期" dataIndex="birthday_" type="date" format="Y-m-d H:i:s" width="100" />
			
			<aos:column header="修改" rendererFn="fn_button_render" align="center" fixedWidth="50" />
			<aos:column header="删除" rendererFn="fn_button_del" align="center" fixedWidth="50" />--%>
		</aos:gridpanel>

		<%-- 新增窗口 --%>
		<aos:window id="_w_members" title="新增账户">
			<aos:formpanel id="_f_members" width="450" layout="anchor" labelWidth="70">
			<aos:textfield name="account_" fieldLabel="用户登录帐号" allowBlank="false" />
			<aos:textfield name="password_" fieldLabel="密码" allowBlank="false" />
			<aos:textfield name="name_" fieldLabel="昵称" allowBlank="false" />
			<aos:textfield name="sex_" fieldLabel="性别" allowBlank="false" />
			<aos:textfield name="status_" fieldLabel="用户状态（是否屏蔽）" allowBlank="false" />
			<aos:textfield name="type_" fieldLabel="用户类型用户类型，1：管理员,2：销售员" allowBlank="false" />
			<aos:textfield name="org_id_" fieldLabel="图片地址" allowBlank="false" />
			<aos:textfield name="email_" fieldLabel="电子邮件" allowBlank="false" />
			<aos:textfield name="mobile_" fieldLabel="手机号" allowBlank="false" />
			<aos:textfield name="idno_" fieldLabel="身份证号" allowBlank="false" />
			<aos:textfield name="skin_" fieldLabel="用户界面皮肤" allowBlank="false" />
			<aos:textfield name="biz_code_" fieldLabel="业务对照码" allowBlank="false" />
			<aos:textfield name="address_" fieldLabel="联系地址" allowBlank="false" />
			<aos:textfield name="remark_" fieldLabel="介绍/备注" allowBlank="false" />
			<aos:textfield name="is_del_" fieldLabel="是否已删除" allowBlank="false" />
			<aos:datefield name="create_time_" fieldLabel="创建时间" editable="true" allowBlank="false" />	
			<aos:textfield name="create_by_" fieldLabel="创建人ID" allowBlank="false" />
			<aos:datefield name="login_time_" fieldLabel="最后登录时间" editable="true" allowBlank="false" />	
			<aos:textfield name="sort_" fieldLabel="排序" allowBlank="false" />
			<aos:textfield name="registration_id" fieldLabel="设备ID" allowBlank="false" />
			<aos:textfield name="registration_type" fieldLabel="设备类型" allowBlank="false" />
			<aos:textfield name="is_huanxin" fieldLabel="是否注册环信" allowBlank="false" />
			<aos:textfield name="gold_coins" fieldLabel="金币" allowBlank="false" />
			<aos:textfield name="pay_password" fieldLabel="支付密码" allowBlank="false" />
			<aos:textfield name="deposit_amt" fieldLabel="押金" allowBlank="false" />
			<aos:datefield name="deposit_date" fieldLabel="押金支付时间" editable="true" allowBlank="false" />	
			<aos:textfield name="deposit_status" fieldLabel="押金状态，0:未交，1：已交，-1：已退" allowBlank="false" />
			<aos:textfield name="grade_" fieldLabel="级别" allowBlank="false" />
			<aos:textfield name="user_name" fieldLabel="姓名" allowBlank="false" />
			<aos:textfield name="id_card" fieldLabel="身份证号码" allowBlank="false" />
			<aos:textfield name="vehicle_license" fieldLabel="行驶证号" allowBlank="false" />
			<aos:textfield name="driver_license" fieldLabel="驾驶证号" allowBlank="false" />
			<aos:textfield name="vehicle_img" fieldLabel="行驶证图片" allowBlank="false" />
			<aos:textfield name="driver_img" fieldLabel="驾驶证图片" allowBlank="false" />
			<aos:textfield name="user_img" fieldLabel="用户头像" allowBlank="false" />
			<aos:textfield name="is_cert" fieldLabel="是否认证" allowBlank="false" />
				<%-- 数据字典、时间例子
				<aos:datefield name="birthday_" fieldLabel="出生日期" editable="false" allowBlank="false" />
				<aos:textfield name="card_id_" fieldLabel="信用卡号" allowBlank="false" />
				<aos:combobox name="card_type_" fieldLabel="卡类型" dicField="card_type_" value="1" allowBlank="false" />
				<aos:numberfield name="credit_line_" minValue="500" maxValue="999999" fieldLabel="信用额度" allowBlank="false" />
				<aos:textareafield name="address_" fieldLabel="持卡人住址" />
				--%>
			</aos:formpanel>
			<aos:docked dock="bottom" ui="footer">
				<aos:dockeditem xtype="tbfill" />
				<aos:dockeditem onclick="_f_members_save" text="保存" icon="ok.png" />
				<aos:dockeditem onclick="#_w_members.hide();" text="关闭" icon="close.png" />
			</aos:docked>
		</aos:window>
		<aos:window id="_w_pwd" title="重置密码">
			<aos:formpanel id="_f_pwd" width="400" layout="anchor" labelWidth="75">
				<aos:hiddenfield name="aos_rows_" fieldLabel="已选中的用户ID集合" />
				<aos:textfield name="password_" fieldLabel="新密码" allowBlank="false" inputType="password" maxLength="20" />
				<aos:textfield name="password_b_" fieldLabel="确认新密码" allowBlank="false" inputType="password" maxLength="20" />
			</aos:formpanel>
			<aos:docked dock="bottom" ui="footer">
				<aos:dockeditem xtype="tbfill" />
				<aos:dockeditem onclick="_f_pwd_save" text="保存" icon="ok.png" />
				<aos:dockeditem onclick="#_w_pwd.hide();" text="关闭" icon="close.png" />
			</aos:docked>
		</aos:window>
		<%-- 修改窗口 --%>
		<aos:window id="_w_members2" title="查看会员信息" onshow="_w_members2_onshow">
			<aos:formpanel id="_f_members2" width="650" layout="column" labelWidth="90">
				<%-- 更新时的隐藏变量(主键) --%>
				
			<aos:hiddenfield name="id_" fieldLabel="流水号" />
			<aos:textfield name="account_" fieldLabel="会员手机号" disabled="true"  columnWidth="0.5" />
			<aos:textfield name="name_" fieldLabel="昵称" disabled="true" columnWidth="0.5" />
			<aos:combobox name="status_" fieldLabel="会员状态" dicField="user_status_" disabled="true"  columnWidth="0.5" />
			<aos:textfield name="gold_coins" fieldLabel="金币" disabled="true"  columnWidth="0.5"/>
			<aos:textfield name="deposit_amt" fieldLabel="押金" disabled="true" columnWidth="0.5" />
			<aos:datetimefield name="deposit_date" fieldLabel="押金支付时间" disabled="true" columnWidth="0.5" />	
			<aos:combobox name="deposit_status" fieldLabel="押金状态" dicField="deposit_status" disabled="true" columnWidth="0.5" />
			<aos:combobox name="grade_" fieldLabel="会员级别" dicField="grade_" disabled="true" columnWidth="0.5" />
			<aos:datetimefield name="create_time_" fieldLabel="注册时间" disabled="true" columnWidth="0.5"/>	
			<aos:datetimefield name="login_time_" fieldLabel="最后登录时间" disabled="true"  columnWidth="0.5"/>
			<aos:textfield name="user_name" fieldLabel="姓名" disabled="true"  columnWidth="0.5"/>
			<aos:textfield name="id_card" fieldLabel="身份证号码" disabled="true"  columnWidth="0.5"/>
			<aos:textfield name="vehicle_license" fieldLabel="行驶证号" disabled="true"  columnWidth="0.5"/>
			<aos:textfield name="driver_license" fieldLabel="驾驶证号" disabled="true"  columnWidth="0.5"/>
			<aos:combobox name="is_cert" fieldLabel="是否认证" dicField="is_cert" disabled="true" columnWidth="1" />
			<aos:fieldset title="行驶证图片" labelWidth="70" columnWidth="0.5" contentEl="_div_photo" height="180" />
			<aos:fieldset title="驾驶证图片" labelWidth="70" columnWidth="0.5" contentEl="_div_photo1" height="180" />
			</aos:formpanel>
			<aos:docked dock="bottom" ui="footer">
				<aos:dockeditem xtype="tbfill" />
				<%--<aos:dockeditem onclick="_f_members2_update" text="保存" icon="ok.png" />--%>
				<aos:dockeditem onclick="#_w_members2.hide();" text="关闭" icon="close.png" />
			</aos:docked>
		</aos:window>
		<aos:window id="_w_members3" title="认证会员信息" onshow="_w_members3_onshow">
			<aos:formpanel id="_f_members3" width="650" layout="column" labelWidth="90">
				<%-- 更新时的隐藏变量(主键) --%>
				
			<aos:hiddenfield name="id_" fieldLabel="流水号" />
			<aos:textfield name="account_" fieldLabel="会员手机号" disabled="true"  columnWidth="0.5" />
			<aos:textfield name="name_" fieldLabel="昵称" disabled="true" columnWidth="0.5" />
			<aos:combobox name="status_" fieldLabel="会员状态" dicField="user_status_" disabled="true"  columnWidth="0.5" />
			<aos:textfield name="gold_coins" fieldLabel="金币" disabled="true"  columnWidth="0.5"/>
			<aos:textfield name="deposit_amt" fieldLabel="押金" disabled="true" columnWidth="0.5" />
			<aos:datetimefield name="deposit_date" fieldLabel="押金支付时间" disabled="true" columnWidth="0.5" />	
			<aos:combobox name="deposit_status" fieldLabel="押金状态" dicField="deposit_status" disabled="true" columnWidth="0.5" />
			<aos:combobox name="grade_" fieldLabel="会员级别" dicField="grade_" disabled="true" columnWidth="0.5" />
			<aos:datetimefield name="create_time_" fieldLabel="注册时间" disabled="true" columnWidth="0.5"/>	
			<aos:datetimefield name="login_time_" fieldLabel="最后登录时间" disabled="true"  columnWidth="0.5"/>
			<aos:textfield name="user_name" fieldLabel="姓名" disabled="true"  columnWidth="0.5"/>
			<aos:textfield name="id_card" fieldLabel="身份证号码" disabled="true"  columnWidth="0.5"/>
			<aos:textfield name="vehicle_license" fieldLabel="行驶证号" disabled="true"  columnWidth="0.5"/>
			<aos:textfield name="driver_license" fieldLabel="驾驶证号" disabled="true"  columnWidth="0.5"/>
			<aos:combobox name="is_cert" fieldLabel="是否认证" dicField="is_cert" disabled="true" columnWidth="1" />
			<aos:fieldset title="行驶证图片" labelWidth="70" columnWidth="0.5" contentEl="_div_photo3" height="180" />
			<aos:fieldset title="驾驶证图片" labelWidth="70" columnWidth="0.5" contentEl="_div_photo4" height="180" />
			</aos:formpanel>
			<aos:docked dock="bottom" ui="footer">
				<aos:dockeditem xtype="tbfill" />
				<aos:dockeditem onclick="_f_members3_okupdate" text="认证通过" icon="ok.png" />
				<aos:dockeditem onclick="_f_members3_noupdate" text="认证不通过" icon="del2.png" />
				<aos:dockeditem onclick="#_w_members3.hide();" text="关闭" icon="close.png" />
			</aos:docked>
		</aos:window>
	</aos:viewport>

	<script type="text/javascript">
	
		//加载表格数据
		function _g_members_query() {
			var params = AOS.getValue('_f_query');
			_g_members_store.getProxy().extraParams = params;
			_g_members_store.loadPage(1);
		}
		
		//新增账户保存
		function _f_members_save(){
				AOS.ajax({
					forms : _f_members,
					url : 'membersController.saveMembers',
					ok : function(data) {
						 AOS.tip(data.appmsg);
						_g_members_store.reload();
						_w_members.hide();
					}
			});
		}
		//窗口弹出事件监听
		function _w_members_onshow() {
			var record = AOS.selectone(_g_members);
            AOS.ajax({
            	params : {
            			        id_: record.data.id_
            	},
                url: 'membersController.getMembers',
                ok: function (data) {
                	_f_members2.form.setValues(data);
                	 document.getElementById("_img_photo").src="${cxt}/myupload"+data.vehicle_img;
                	 document.getElementById("_img_photo1").src="${cxt}/myupload"+data.driver_img;
                }
            });
		}
		//监听修改窗口弹出
		function _w_members2_onshow(){
			//这里演示的是直接从表格行中加载数据，也可以发一个ajax请求查询数据(见misc1.jsp有相关用法)
			var record = AOS.selectone(_g_members, true);
			_f_members2.loadRecord(record);
			 document.getElementById("_img_photo").src="${cxt}/myupload"+record.data.vehicle_img;
        	 document.getElementById("_img_photo1").src="${cxt}/myupload"+record.data.driver_img;
		}
		//监听修改窗口弹出
		function _w_members3_onshow(){
			//这里演示的是直接从表格行中加载数据，也可以发一个ajax请求查询数据(见misc1.jsp有相关用法)
			var record = AOS.selectone(_g_members, true);
			_f_members3.loadRecord(record);
			 document.getElementById("_img_photo3").src="${cxt}/myupload"+record.data.vehicle_img;
        	 document.getElementById("_img_photo4").src="${cxt}/myupload"+record.data.driver_img;
		}
		//修改账户保存
		function _f_members2_update(){
				AOS.ajax({
					forms : _f_members2,
					url : 'membersController.updateMembers',
					ok : function(data) {
						AOS.tip(data.appmsg);
						_g_members_store.reload();
						_w_members2.hide();
					}
			});
		}
		//修改账户保存
		function _f_members3_okupdate(){
				AOS.ajax({
					forms : _f_members3,
					url : 'membersController.okCertMembers',
					ok : function(data) {
						AOS.tip(data.appmsg);
						_g_members_store.reload();
						_w_members3.hide();
					}
			});
		}
		
		function fn_export_excel(){
			//juid需要再这个页面的初始化方法中赋值,这里才引用得到
			//httpModel.setAttribute("juid", httpModel.getInDto().getString("juid"));
			var params = AOS.getValue('_f_query');
			var params_url="";
			for(var v in params){
				params_url+="&"+v+"="+params[v];
			}
			AOS.file('do.jhtml?router=membersController.exportExcel&juid=${juid}'+params_url);
		}
		
		function _f_members3_noupdate(){
			AOS.ajax({
					forms : _f_members3,
					url : 'membersController.noCertMembers',
					ok : function(data) {
						AOS.tip(data.appmsg);
						_g_members_store.reload();
						_w_members3.hide();
					}
			});
		}
		 //更新
	 	function _g_members_update(){
	 		
				if(AOS.selectone(_g_members)){
					//_w_members2_onshow();
					AOS.get('_w_members2').show();
				}
				
				
		}
		function _g_members_update1(){
	 		
			if(AOS.selectone(_g_members)){
				//_w_members2_onshow();
				AOS.get('_w_members3').show();
			}
			
			
		}
		
        //删除账户信息
	 	function _g_members_delAll(){
	        	var selection = AOS.selection(_g_members, 'id_');
				
				if(AOS.empty(selection)){
					AOS.tip('删除前请先选中数据。');
					return;
				}
				var rows = AOS.rows(_g_members);
				var msg =  AOS.merge('确认要删除选中的{0}条记录吗？', rows);
				AOS.confirm(msg, function(btn){
					if(btn === 'cancel'){
						AOS.tip('删除操作被取消。');
						return;
					}
					AOS.ajax({
						url : 'membersController.deleteMembers',
						params:{
							aos_rows_: selection
						},
						ok : function(data) {
							AOS.tip(data.appmsg);
							_g_members_store.reload();
						}
					});
				});
			}
	 	//禁用账户信息
	 	function _g_members_stopAll(){
	        	var selection = AOS.selection(_g_members, 'id_');
				
				if(AOS.empty(selection)){
					AOS.tip('禁用前请先选中数据。');
					return;
				}
				var rows = AOS.rows(_g_members);
				var msg =  AOS.merge('确认要禁用选中的{0}条记录吗？', rows);
				AOS.confirm(msg, function(btn){
					if(btn === 'cancel'){
						AOS.tip('禁用操作被取消。');
						return;
					}
					AOS.ajax({
						url : 'membersController.stopMembers',
						params:{
							aos_rows_: selection
						},
						ok : function(data) {
							AOS.tip(data.appmsg);
							_g_members_store.reload();
						}
					});
				});
			}
	 	//启用账户信息
	 	function _g_members_okAll(){
	        	var selection = AOS.selection(_g_members, 'id_');
				
				if(AOS.empty(selection)){
					AOS.tip('启用前请先选中数据。');
					return;
				}
				var rows = AOS.rows(_g_members);
				var msg =  AOS.merge('确认要启用选中的{0}条记录吗？', rows);
				AOS.confirm(msg, function(btn){
					if(btn === 'cancel'){
						AOS.tip('启用操作被取消。');
						return;
					}
					AOS.ajax({
						url : 'membersController.okMembers',
						params:{
							aos_rows_: selection
						},
						ok : function(data) {
							AOS.tip(data.appmsg);
							_g_members_store.reload();
						}
					});
				});
			}
		//按钮列转换
		function fn_button_render(value, metaData, record) {
			return '<input type="button" value="修改" class="cbtn_danger" onclick="_w_members2_show();" />';
		}
		
		//按钮列转换
		function fn_button_del(value, metaData, record) {
			return '<input type="button" value="删除" class="cbtn_danger" onclick="fn_del();" />';
		}
		//弹出密码重置窗口
        function _w_pwd_show() {
            AOS.reset(_f_pwd);
            var selection = AOS.selection(_g_members, 'id_');
            if (AOS.empty(selection)) {
                AOS.tip('请先选中需要重置密码的会员。');
                return;
            }
            AOS.setValue('_f_pwd.aos_rows_', selection);
            _w_pwd.show();
        }
      //重置密码
        function _f_pwd_save() {
            if (AOS.getValue('_f_pwd.password_') !== AOS.getValue('_f_pwd.password_b_')) {
            	var msg = '两次输入密码不一致, 请重新输入。';
            	AOS.get('_f_pwd.password_b_').markInvalid(msg);
            	AOS.err(msg);
                return;
            }
            var msg = AOS.merge('确认要重置选中[{0}]个会员的密码吗？', AOS.rows(_g_dealer));
            AOS.confirm(msg, function (btn) {
                if (btn === 'cancel') {
                    AOS.tip('重置密码操作被取消。');
                    return;
                }
                AOS.ajax({
                    forms: _f_pwd,
                    url: 'membersController.resetPassword',
                    ok: function (data) {
                        _w_pwd.hide();
                        AOS.tip(data.appmsg);
                    }
                });
            });
        }
	</script>
</aos:onready>

<script type="text/javascript">

function _w_members2_show(){
	AOS.get('_w_members2').show();
}




function fn_del(){
	//由于是列按钮对应的函数，下面获取对象的的写法和onready代码段里的js有些不同
	var record = AOS.selectone(AOS.get('_g_members'));
	var msg =  AOS.merge('确认要删除当前记录吗？', "");
	AOS.confirm(msg, function(btn){
		if(btn === 'cancel'){
			AOS.tip('删除操作被取消。');
			return;
		}
		AOS.ajax({
			url : 'membersController.deleteMembers',
			params:{
	        id_: record.data.id_
			},
			ok : function(data) {
				AOS.tip(data.appmsg);
				AOS.get('_g_members').getStore().reload();
			}
		});
	});
}
function fn_stop(){
	//由于是列按钮对应的函数，下面获取对象的的写法和onready代码段里的js有些不同
	var record = AOS.selectone(AOS.get('_g_members'));
	var msg =  AOS.merge('确认要禁用当前记录吗？', "");
	AOS.confirm(msg, function(btn){
		if(btn === 'cancel'){
			AOS.tip('禁用操作被取消。');
			return;
		}
		AOS.ajax({
			url : 'membersController.stopMembers',
			params:{
	        id_: record.data.id_
			},
			ok : function(data) {
				AOS.tip(data.appmsg);
				AOS.get('_g_members').getStore().reload();
			}
		});
	});
}
function fn_ok(){
	//由于是列按钮对应的函数，下面获取对象的的写法和onready代码段里的js有些不同
	var record = AOS.selectone(AOS.get('_g_members'));
	var msg =  AOS.merge('确认要启用当前记录吗？', "");
	AOS.confirm(msg, function(btn){
		if(btn === 'cancel'){
			AOS.tip('启用操作被取消。');
			return;
		}
		AOS.ajax({
			url : 'membersController.okMembers',
			params:{
	        id_: record.data.id_
			},
			ok : function(data) {
				AOS.tip(data.appmsg);
				AOS.get('_g_members').getStore().reload();
			}
		});
	});
}
</script>