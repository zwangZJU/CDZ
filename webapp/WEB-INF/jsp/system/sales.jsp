<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/common/tags.jsp"%>

<aos:html title="销售员管理" base="http" lib="ext">
	<aos:body>
	</aos:body>
</aos:html>

<aos:onready>
	<aos:viewport layout="border">
		<aos:gridpanel id="_g_user" url="userService.listSalesUsers" region="center" onrender="_g_user_query" bodyBorder="1 0 1 0"
			onitemdblclick="#_w_user_u.show();">
			<aos:docked forceBoder="0 0 1 0">
				<aos:dockeditem text="新增" onclick="#_w_user.show();" icon="add.png" />
				<aos:dockeditem text="修改" onclick="_g_user_update" icon="edit.png" />
				<aos:dockeditem text="删除" onclick="_g_user_del" icon="del.png" />
				<aos:dockeditem xtype="tbseparator" />
				<aos:dockeditem text="重置密码" onclick="_w_pwd_show" icon="key.png" />
				<aos:dockeditem text="注册环信" onclick="register_huanxin" icon="edit.png" />
				<aos:dockeditem xtype="tbseparator" />
				<aos:triggerfield emptyText="销售员账号" id="id_na_" onenterkey="_g_user_query" trigger1Cls="x-form-search-trigger" 
					onTrigger1Click="_g_user_query" width="180" />
			</aos:docked>
			<aos:menu>
				<aos:menuitem xtype="menuseparator" />
				<aos:menuitem text="新增用户" onclick="#_w_user.show();" icon="add.png" />
				<aos:menuitem text="修改用户" onclick="_g_user_update" icon="edit.png" />
				<aos:menuitem text="删除用户" onclick="_g_user_del" icon="del.png" />
				<aos:menuitem xtype="menuseparator" />
				<aos:menuitem text="重置密码" onclick="_w_pwd_show" icon="key.png" />
				<aos:menuitem xtype="menuseparator" />
				<aos:menuitem text="刷新" onclick="#_g_user_store.reload();" icon="refresh.png" />
			</aos:menu>
			<aos:selmodel type="checkbox" mode="multi" />
			<aos:column type="rowno" />
			<aos:column header="用户流水号" dataIndex="id_" fixedWidth="150" hidden="true" />
			<aos:column header="销售员账号" dataIndex="account_" width="120" celltip="true"/>
			<aos:column header="名称" dataIndex="name_" width="120" celltip="true"/>
			<aos:column header="最后登录时间" dataIndex="login_time_" width="150"  celltip="true"/>
			<aos:column header="状态" dataIndex="status_" width="100"  rendererFn="fn_col_type_" />
			<aos:column header="新建时间" dataIndex="create_time_" width="150"  celltip="true"/>
			<aos:column header="是否注册环信" dataIndex="is_huanxin" width="100"  rendererField="is_huanxin" />
			<aos:column header="修改" rendererFn="fn_button_render" align="center" fixedWidth="50" />
			<aos:column header="删除" rendererFn="fn_button_del" align="center" fixedWidth="50" />
		</aos:gridpanel>
	</aos:viewport>

	<aos:window id="_w_user" title="添加销售员" onshow="_w_user_onshow">
		<aos:formpanel id="_f_user" width="650" labelWidth="65">
				<aos:textfield name="account_" fieldLabel="手机号" emptyText="请输入手机号码" regex="/^[1][3578][0-9]{9}$/" regexText="请输入正确的手机号"  allowBlank="false" maxLength="20"  columnWidth="1" />
				<aos:textfield name="name_" fieldLabel="名称" allowBlank="false" columnWidth="1"/>
				<aos:textfield name="password_" fieldLabel="密码" inputType="password" allowBlank="false" maxLength="20"  columnWidth="1" />
				<aos:textfield name="password_b_" fieldLabel="确认密码" inputType="password" allowBlank="false" maxLength="20"  columnWidth="1" />
				<aos:combobox name="status_" fieldLabel="状态" allowBlank="false" value="1" dicField="user_status_" columnWidth="1" />
		</aos:formpanel>
		<aos:docked dock="bottom" ui="footer">
			<aos:dockeditem xtype="tbfill" />
			<aos:dockeditem onclick="_f_user_save" text="保存" icon="ok.png" />
			<aos:dockeditem onclick="#_w_user.hide();" text="关闭" icon="close.png" />
		</aos:docked>
	</aos:window>


	<aos:window id="_w_user_u" title="编辑销售员" onshow="_w_user_u_onshow">
		<aos:formpanel id="_f_user_u" width="650" labelWidth="65">
			<aos:hiddenfield fieldLabel="用户流水号" name="id_" />
			<aos:textfield name="account_" fieldLabel="手机号" allowBlank="false" regex="/^[1][3578][0-9]{9}$/" regexText="请输入正确的手机号" maxLength="20"  columnWidth="1" />
			<aos:textfield name="name_" fieldLabel="名称" allowBlank="false" columnWidth="1"/>
			<aos:textfield name="password_" fieldLabel="密码" inputType="password" allowBlank="false" maxLength="20"  columnWidth="1" />
			<aos:textfield name="password_b_" fieldLabel="确认密码" inputType="password" allowBlank="false" maxLength="20"  columnWidth="1" />
			<aos:combobox name="status_" fieldLabel="状态" allowBlank="false" value="1" dicField="user_status_" columnWidth="1" />
		</aos:formpanel>
		<aos:docked dock="bottom" ui="footer">
			<aos:dockeditem xtype="tbfill" />
			<aos:dockeditem onclick="_f_user_u_save" text="保存" icon="ok.png" />
			<aos:dockeditem onclick="#_w_user_u.hide();" text="关闭" icon="close.png" />
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
	
	

	<script type="text/javascript">

		//保存用户角色关联信息
	    function _user_role_save() {
	        var rows = AOS.rows(_g_role);
	        if (rows === 0) {
	            AOS.tip('操作被取消，请先选中要关联的角色。');
	            return;
	        }
	        var record = AOS.selectone(_g_user);
	        var params = {
	            aos_rows_: AOS.selection(_g_role, 'id_'),
	            user_id_: record.data.id_
	        };
	        AOS.ajax({
	            params: params,
	            url: 'userService.saveUserRoleGrantInfo',
	            ok: function (data) {
	                AOS.tip(data.appmsg);
	                _g_role_store.reload();
	                _g_role_selected_query();
	            }
	        });
	    }
	
	    //取消用户角色关联
	    function _user_role_cancel() {
	        var rows = AOS.rows(_g_role_selected);
	        if (rows === 0) {
	            AOS.tip('操作被取消，请先选中要撤消的角色。');
	            return;
	        }
	        AOS.ajax({
	            url: 'userService.delUserRoleGrantInfo',
	            params: {
	                aos_rows_: AOS.selection(_g_role_selected, 'user_role_id_')
	            },
	            ok: function (data) {
	                AOS.tip(data.appmsg);
	                _g_role_selected_store.reload();
	                _g_role_store.reload();
	            }
	        });
	    }
	 	 //按钮列转换
		function fn_button_render(value, metaData, record) {
			return '<input type="button" value="修改" class="cbtn_danger" onclick="_w_testUser2_show();" />';
		}
		
		//按钮列转换
		function fn_button_del(value, metaData, record) {
			return '<input type="button" value="删除" class="cbtn_danger" onclick="fn_del();" />';
		}
		
	   
	    
        //查询选择角色窗口角色列表
        function _g_role_query() {
            var params = {
                name_: id_role_name_.getValue(),
                user_id_: AOS.selectone(_g_user).data.id_
            };
            _g_role_store.getProxy().extraParams = params;
            _g_role_store.loadPage(1);
        }

        //查询选择角色窗口角色列表(已选择)
        function _g_role_selected_query() {
            var params = {
                name_: id_role_name_selected_.getValue(),
                user_id_: AOS.selectone(_g_user).data.id_
            };
            _g_role_selected_store.getProxy().extraParams = params;
            _g_role_selected_store.loadPage(1);
        }
    
		
	
	    //刷新所属部门选择树
	    function _t_org_find_load() {
	        var refreshnode = AOS.selectone(_t_org_find);
	        if (!refreshnode) {
	            refreshnode = _t_org_find.getRootNode();
	        }
	        if (refreshnode.isLeaf()) {
	            refreshnode = refreshnode.parentNode;
	        }
	        _t_org_find_store.load({
	            node: refreshnode,
	            callback: function () {
	                //收缩再展开才能在刷新时正确显示expanded=true的节点的子节点
	                refreshnode.collapse();
	                refreshnode.expand();
	            }
	        });
	    }
	
	    //所属组织树节点双击事件
	    function _t_org_find_select() {
	        var record = AOS.selectone(_t_org_find);
	        AOS.setValue('_f_user_u.org_id_', record.raw.id);
	        AOS.setValue('_f_user_u.org_name_', record.raw.text);
	        _w_org_find.hide();
	    }
	
	   //弹出部门选择出口
	   function _w_org_find_show(){
		   _w_org_find.show();
	   }
	
		//查询部门列表
		function _g_user_query() {
			var params = {
				na_ : id_na_.getValue()
			};
			
			_g_user_store.getProxy().extraParams = params;
			_g_user_store.loadPage(1);
		}
		
		
		
	    //弹出新增人员窗口
		function _w_user_onshow(){
			AOS.reset(_f_user);
		}
			
		//刷新部门树
		function _t_org_refresh() {
			_t_org_store.load({
				node : AOS.selectone(_t_org),
				callback : function() {
					//收缩再展开才能在刷新时正确显示expanded=true的节点的子节点
					refreshnode.collapse();
					refreshnode.expand();
					_t_org.getSelectionModel().select(refreshnode);
				}
			});
		}
	    
		//新增用户保存
		function _f_user_save(){
				AOS.ajax({
					forms : _f_user,
					url : 'userService.saveSalesUser',
					ok : function(data) {
						if(data.appcode=='1'){
							AOS.tip(data.appmsg);
							_w_user.hide();
							_g_user_store.reload();
						}else{
							AOS.get('_f_user.account_').markInvalid(data.appmsg);
							AOS.err(data.appmsg);
						}
					}
			});
		}	
		
	    //监听修改用户窗口弹出事件
		function _w_user_u_onshow(){
			AOS.reset(_f_user_u);
			var record = AOS.selectone(_g_user);
			AOS.ajax({
				params:{
					id_: record.data.id_
				},
                url: 'userService.getUser',
                ok: function (data) {
                	AOS.setValues(_f_user_u, data);
                }
            });
		}	
		
		//修改用户保存
		function _f_user_u_save(){
				AOS.ajax({
				forms : _f_user_u,
				url : 'userService.updateSalesUser',
				ok : function(data) {
					if(data.appcode == '1'){
						_w_user_u.hide();
						_g_user_store.reload();
						AOS.tip(data.appmsg);
					}else{
						AOS.get('_f_user_u.account_').markInvalid(data.appmsg);
						AOS.err(data.appmsg);
					}
				}
			});
		}			
 		
		 //更新
	 	function _g_user_update(){
	 		
				if(AOS.selectone(_g_user)){
					AOS.get('_w_user_u').show();
				}
		}
        //删除用户
	 	function _g_user_del(){
				var selection = AOS.selection(_g_user, 'id_');
				if(AOS.empty(selection)){
					AOS.tip('删除前请先选中数据。');
					return;
				}
				var rows = AOS.rows(_g_user);
				var msg =  AOS.merge('确认要删除选中的{0}个销售员吗？', rows);
				AOS.confirm(msg, function(btn){
					if(btn === 'cancel'){
						AOS.tip('删除操作被取消。');
						return;
					}
					AOS.ajax({
						url : 'userService.deleteSalesUser',
						params:{
							aos_rows_: selection
						},
						ok : function(data) {
							AOS.tip(data.appmsg);
							_g_user_store.reload();
						}
					});
				});
			}
	 	
	 	function register_huanxin(){
			var selection = AOS.selection(_g_user, 'id_');
			if(AOS.empty(selection)){
				AOS.tip('注册环信前请先选中数据。');
				return;
			}
			var rows = AOS.rows(_g_user);
			var msg =  AOS.merge('确认要注册选中的{0}个销售员环信吗？', rows);
			AOS.confirm(msg, function(btn){
				if(btn === 'cancel'){
					AOS.tip('注册环信操作被取消。');
					return;
				}
				AOS.ajax({
					url : 'userService.registerHuanxin',
					params:{
						aos_rows_: selection
					},
					ok : function(data) {
						AOS.tip(data.appmsg);
						_g_user_store.reload();
					}
				});
			});
		}
        //弹出密码重置窗口
        function _w_pwd_show() {
            AOS.reset(_f_pwd);
            var selection = AOS.selection(_g_user, 'id_');
            if (AOS.empty(selection)) {
                AOS.tip('请先选中需要重置密码的销售员。');
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
            var msg = AOS.merge('确认要重置选中[{0}]个销售员的密码吗？', AOS.rows(_g_user));
            AOS.confirm(msg, function (btn) {
                if (btn === 'cancel') {
                    AOS.tip('重置密码操作被取消。');
                    return;
                }
                AOS.ajax({
                    forms: _f_pwd,
                    url: 'userService.resetPassword',
                    ok: function (data) {
                        _w_pwd.hide();
                        AOS.tip(data.appmsg);
                    }
                });
            });
        }
        
      //列渲染
		function fn_col_type_(value, metaData, record, rowIndex, colIndex) {
    	  	var cls = "tip_danger";
			if (value ==1 ) {
				cls = "tip_info";
				value="启用";
			}else{
				value="禁用";
			}
			return AOS.merge('<span class="{0}">{1}</span>', cls, value);
		}
	</script>
</aos:onready>

<script type="text/javascript">
	
	
	function _w_testUser2_show(){
		AOS.get('_w_user_u').show();
	}

	function fn_del(){
		//由于是列按钮对应的函数，下面获取对象的的写法和onready代码段里的js有些不同
		var record = AOS.selectone(AOS.get('_g_user'));
		var msg =  AOS.merge('您确定要删除吗？', "");
		AOS.confirm(msg, function(btn){
			if(btn === 'cancel'){
				AOS.tip('删除操作被取消。');
				return;
			}
			AOS.ajax({
				url : 'userService.deleteSalesUser',
				params:{
					id_: record.data.id_
				},
				ok : function(data) {
					AOS.tip(data.appmsg);
					AOS.get('_g_user').getStore().reload();
				}
			});
		});
	}
</script>
