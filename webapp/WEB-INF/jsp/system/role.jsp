<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/common/tags.jsp"%>

<aos:html title="角色管理" base="http" lib="ext">
<aos:body>
</aos:body>
</aos:html>

<aos:onready>
	<aos:viewport layout="fit">
		<aos:gridpanel id="_g_role" url="roleService.listRole" onrender="_g_role_query" forceFit="true"
			onitemdblclick="_w_role_u_show">
			<aos:menu>
				<aos:menuitem text="新增" onclick="#_w_role.show();" icon="add.png" />
				<aos:menuitem text="修改" onclick="_w_role_u_show" icon="edit.png" />
				<aos:menuitem text="删除" onclick="_g_role_del" icon="del.png" />
				<aos:menuitem xtype="menuseparator" />
				<aos:menuitem text="刷新" onclick="#_g_role_store.reload();" icon="refresh.png" />
			</aos:menu>
			<aos:docked forceBoder="0 0 1 0">
				<aos:dockeditem onclick="#_w_role.show();" text="新增" icon="add.png" />
				<aos:dockeditem onclick="_w_role_u_show" text="修改" icon="edit.png" />
				<aos:dockeditem onclick="_g_role_del" text="删除" icon="del.png" />
				<aos:dockeditem xtype="tbseparator" />
				<aos:triggerfield emptyText="角色名称" id="id_name_" onenterkey="_g_role_query" trigger1Cls="x-form-search-trigger"
					onTrigger1Click="_g_role_query" width="180" />
			</aos:docked>
			<aos:selmodel type="checkbox" mode="multi" />
			<aos:column type="rowno" />
			<aos:column header="流水号" dataIndex="id_" fixedWidth="150" hidden="true" />
			<aos:column header="角色名称" dataIndex="name_" celltip="true" width="150" />
			<aos:column header="备注" dataIndex="remark_" celltip="true" width="200" />
			<aos:column header="创建时间" dataIndex="create_time_" width="150" />
			<aos:column header="授权" rendererFn="_btn_grant_render" align="center" width="100" />
			<aos:column header="选择用户" rendererFn="_btn_user_render" align="center" width="100" />
			<aos:column header="修改" rendererFn="fn_button_render" align="center" width="80" />
			<aos:column header="删除" rendererFn="fn_button_del" align="center" width="80" />
		</aos:gridpanel>
	</aos:viewport>

	<aos:window id="_w_role" title="新增角色" onshow="AOS.reset(_f_role);">
		<aos:formpanel id="_f_role" width="500" layout="anchor" labelWidth="65">
			<aos:textfield name="name_" fieldLabel="角色名称" allowBlank="false" maxLength="50" />
			<aos:textareafield name="remark_" fieldLabel="备注" maxLength="4000" />
		</aos:formpanel>
		<aos:docked dock="bottom" ui="footer">
			<aos:dockeditem xtype="tbfill" />
			<aos:dockeditem onclick="_f_role_save" text="保存" icon="ok.png" />
			<aos:dockeditem onclick="#_w_role.hide();" text="关闭" icon="close.png" />
		</aos:docked>
	</aos:window>
	<aos:window id="_w_role_u" title="修改角色">
		<aos:formpanel id="_f_role_u" width="500" layout="anchor" labelWidth="65">
			<aos:hiddenfield name="id_" />
			<aos:textfield name="name_" fieldLabel="角色名称" allowBlank="false" maxLength="50" />
			<aos:textareafield name="remark_" fieldLabel="备注" maxLength="4000" />
		</aos:formpanel>
		<aos:docked dock="bottom" ui="footer">
			<aos:dockeditem xtype="tbfill" />
			<aos:dockeditem onclick="_f_role_u_save" text="保存" icon="ok.png" />
			<aos:dockeditem onclick="#_w_role_u.hide();" text="关闭" icon="close.png" />
		</aos:docked>
	</aos:window>

	<aos:window id="_w_role_module" title="授权" width="600" height="-10" layout="border" onshow="_w_role_module_onshow">
		<aos:treepanel id="_t_module" rootText="所有权限[导航菜单]" width="300" headerBorder="0 1 0 0" bodyBorder="0 1 0 0"
			url="roleService.getTreeData" singleClick="false" rootIcon="icon140.png" cascade="true" rootExpanded="false"
			rootVisible="true" region="center">
			<aos:menu>
				<aos:menuitem text="刷新" onclick="_t_module_load" icon="refresh.png" />
			</aos:menu>
		</aos:treepanel>
		<aos:treepanel id="_t_module_admin" rootText="管理权限[授权时待选菜单]" width="300" bodyBorder="0 0 0 0"
			url="roleService.getAdminTreeData" singleClick="false" rootIcon="icon146.png" cascade="true" rootExpanded="false"
			rootVisible="true" region="east">
			<aos:menu>
				<aos:menuitem text="刷新" onclick="_t_module_admin_load" icon="refresh.png" />
			</aos:menu>
		</aos:treepanel>
		<aos:docked dock="bottom" ui="footer">
			<aos:dockeditem xtype="tbfill" />
			<aos:dockeditem onclick="_role_module_save" text="保存" icon="ok.png" />
			<aos:dockeditem onclick="#_w_role_module.hide();" text="关闭" icon="close.png" />
		</aos:docked>
	</aos:window>

	<aos:window id="_w_role_user" title="选择用户" width="600" height="-10" layout="border" onshow="_w_role_user_onshow">
		<aos:gridpanel id="_g_user" region="center" bodyBorder="1 0 1 0" forceFit="false" url="roleService.listUsers"
			displayInfo="false">
			<aos:docked forceBoder="0 0 1 0">
				<aos:dockeditem xtype="tbtext" text="待选用户列表" />
				<aos:dockeditem xtype="tbfill" />
				<aos:triggerfield emptyText="管理员账号" name="hotkey" id="id_na_" onenterkey="_g_user_query"
					trigger1Cls="x-form-search-trigger" onTrigger1Click="_g_user_query" width="180" />
			</aos:docked>
			<aos:menu>
				<aos:menuitem onclick="_role_user_save" text="选中授权" icon="ok1.png" />
				<aos:menuitem xtype="menuseparator" />
				<aos:menuitem text="刷新" onclick="_g_user_store.reload();" icon="refresh.png" />
			</aos:menu>
			<aos:selmodel type="checkbox" mode="simple" />
			<aos:column type="rowno" />
			<aos:column header="用户流水号" dataIndex="id_" hidden="true" />
			<aos:column header="管理员账号" dataIndex="account_" width="150" />
		</aos:gridpanel>

		<aos:panel region="east" width="350">
			<aos:layout type="hbox" align="stretch" />
			<aos:panel bodyBorder="0 1 0 1" bodyPadding="3" width="65">
				<aos:layout type="vbox" align="center" pack="center" />
				<aos:button onclick="_role_user_save" text="选中" tooltip="选中授权" iconVec="fa-angle-double-right" iconVecAlign="right"
					iconVecSize="16" />
				<aos:button onclick="_role_user_cancel" margin="20 0 0 0" text="撤消" tooltip="撤消授权" iconVec="fa-angle-double-left"
					iconVecSize="16" />
			</aos:panel>
			<aos:gridpanel id="_g_user_selected" forceFit="false" url="roleService.listSelectedUsers" width="340"
				bodyBorder="1 0 1 0" displayInfo="false">
				<aos:docked forceBoder="0 0 1 0">
					<aos:dockeditem xtype="tbtext" text="已选用户列表" />
					<aos:dockeditem xtype="tbfill" />
					<aos:triggerfield emptyText="管理员账号" id="id_na_selected_" onenterkey="_g_user_selected_query"
						trigger1Cls="x-form-search-trigger" onTrigger1Click="_g_user_selected_query" width="180" />
				</aos:docked>
				<aos:menu>
					<aos:menuitem onclick="_role_user_cancel" text="撤消授权" icon="del.png" />
					<aos:menuitem xtype="menuseparator" />
					<aos:menuitem text="刷新" onclick="#_g_user_selected_store.reload();" icon="refresh.png" />
				</aos:menu>
				<aos:selmodel type="checkbox" mode="simple" />
				<aos:column type="rowno" />
				<aos:column header="授权流水号" dataIndex="user_role_id_" hidden="true" />
				<aos:column header="管理员账号号" dataIndex="account_" width="150" />
			</aos:gridpanel>
		</aos:panel>

		<aos:docked dock="bottom" ui="footer">
			<aos:dockeditem xtype="tbfill" />
			<aos:dockeditem onclick="#_w_role_user.hide();" text="关闭" icon="close.png" />
		</aos:docked>
	</aos:window>

	<script type="text/javascript">
		//保存角色用户授权信息
		function _role_user_save(){
			var rows = AOS.rows(_g_user);
			if(rows == 0){
				AOS.tip('操作被取消，请先选中要授权的用户。');
				return;
			}
			var record = AOS.selectone(_g_role);
			var params = {
				aos_rows_ : AOS.selection(_g_user, 'id_'),
				role_id_ : record.data.id_
			};
			AOS.ajax({
					params:params,
					url : 'roleService.saveRoleUserGrantInfo',
					ok : function(data) {
						AOS.tip(data.appmsg);
						_g_user_store.reload();
						_g_user_selected_query();
					 }
	  		});
		}
		
		//取消角色用户授权
	 	function _role_user_cancel(){
					var rows = AOS.rows(_g_user_selected);
					if(rows === 0){
						AOS.tip('操作被取消，请先选中要撤消授权的用户。');
						return;
					}
				AOS.ajax({
					url : 'roleService.deleteRoleUserGrantInfo',
					params:{
						aos_rows_: AOS.selection(_g_user_selected, 'user_role_id_')
					},
					ok : function(data) {
						AOS.tip(data.appmsg);
						_g_user_selected_store.reload();
						_g_user_store.reload();
					}
				});
	 		}
	
			//监听角色选择用户窗口
			function _w_role_user_onshow(){
				var record = AOS.selectone(_g_role);
				var title = AOS.merge('选择用户 [{0}]', record.data.name_);
				_w_role_user.setTitle(AOS.title(title));
				//_t_org_role.getRootNode().expand();
				_g_user_query();
				_g_user_selected_query();
			}
			
			//查询已授权用户列表
			function _g_user_selected_query(){
		    	var params = {
		    			role_id_ : AOS.selectone(_g_role).data.id_,
		    			na_ : id_na_selected_.getValue()
		  			};
		    	_g_user_selected_store.getProxy().extraParams = params;
		    	_g_user_selected_store.loadPage(1);				
			}
			
			//查询角色用户授权窗口用户列表
			function _g_user_query() {
		    	var params = {
		    			role_id_ : AOS.selectone(_g_role).data.id_,
		    			na_ : id_na_.getValue()
		  			};
		    	
				_g_user_store.getProxy().extraParams = params;
				_g_user_store.loadPage(1);
			}
	
		   var init_grant_rows;
		   var init_admin_rows;
		   
		   //保存角色菜单授权数据
		   function _role_module_save(){
			  var record = AOS.selectone(_g_role);
			  var params = {
					role_id_ : record.data.id_,
					save_biz_ : true,
					save_admin_: true
				};
			  var grant_rows = AOS.selection(_t_module, 'id');
			  if(init_grant_rows != grant_rows){
				  params.grant_rows = grant_rows;
			  }
			  var admin_rows = AOS.selection(_t_module_admin, 'id');
			  if(init_admin_rows != admin_rows){
				  params.admin_rows = admin_rows;
			  }
			  if(record.data.id_ == '${super_role_id}'){
				  if(init_grant_rows == grant_rows){
					  AOS.tip("授权数据无变化，不需要保存。");
					  return;
				  }
				  //角色管理菜单为必选的内置菜单，不能取消授权
				  if(grant_rows.indexOf('${role_module_id_}') == -1){
					  AOS.warn("操作被取消。【角色管理】是超级用户角色内置必选菜单,不能取消授权。请重新选择。");
					  return;
				  }
			  }else{
				  if(init_grant_rows == grant_rows){
					  params.save_biz_=false;
				  }
                  if(init_admin_rows == admin_rows){
                	  params.save_admin_=false;
				  }
				  if(!params.save_biz_ && !params.save_admin_){
					  AOS.tip("授权数据无变化，不需要保存。");
					  return;
				  }
			  }
			AOS.ajax({
					params:params,
					url : 'roleService.saveGrantInfo',
					ok : function(data) {
						AOS.tip(data.appmsg);
						_t_module_load(true);
					 }
	  		});
			}
		
			//监听菜单授权窗口弹出事件
			function _w_role_module_onshow(){
				var record = AOS.selectone(_g_role);
				_t_module_load(true);
				var title = AOS.merge('授权 [{0}]', record.data.name_);
	    		_w_role_module.setTitle(AOS.title(title));
				if(record.data.id_ == '${super_role_id}'){
					_t_module_admin.hide();
					_w_role_module.setWidth(330);
					Ext.util.CSS.createStyleSheet('#_t_module_header {border-right-width: 0px !important;}');
					Ext.util.CSS.createStyleSheet('#_t_module-body {border-right-width: 0px !important;}');
				}else{
					_t_module_admin.show();
					_w_role_module.setWidth(600);
					Ext.util.CSS.createStyleSheet('#_t_module_header {border-right-width: 1px !important;}');
					Ext.util.CSS.createStyleSheet('#_t_module-body {border-right-width: 1px !important;}');
				}
				_w_role_module.center();
			}
			
			//加载业务经办授权菜单树
			function _t_module_load(flag) {
				refreshnode = _t_module.getRootNode();
				var record = AOS.selectone(_g_role);
				var params = {
						role_id_ : record.data.id_,
						grant_type_ : '1'
				};
				_t_module_store.getProxy().extraParams = params;
				_t_module_store.load({
					callback : function() {
						//收缩再展开才能在刷新时正确显示expanded=true的节点的子节点
						refreshnode.collapse();
						refreshnode.expand();
						init_grant_rows = AOS.selection(_t_module, 'id');
						if(record.data.id_ != '${super_role_id}'){
							//防止两棵树同时加载显示有问题
							if(flag) _t_module_admin_load();
						}
					}
				});
			}
			
			//加载管理权限授权菜单树
			function _t_module_admin_load() {
				refreshnode = _t_module_admin.getRootNode();
				var record = AOS.selectone(_g_role);
				var params = {
						role_id_ : record.data.id_,
						grant_type_ : '2'
				};
				_t_module_admin_store.getProxy().extraParams = params;
				_t_module_admin_store.load({
					callback : function() {
						//收缩再展开才能在刷新时正确显示expanded=true的节点的子节点
						refreshnode.collapse();
						refreshnode.expand();
						init_admin_rows = AOS.selection(_t_module_admin, 'id');
					}
				});
			}
	
	       //选择用户按钮列转换
			function _btn_user_render(value, metaData, record, rowIndex, colIndex,store) {
	    	   if(record.data.id_ == '${super_role_id}'){
	    		   return '--';
	    	   }else{
	    		   return '<input type="button" value="选择用户" class="cbtn" onclick="_btn_user_onclick();" />';
	    	   }
			}
			//授权按钮列转换
			function _btn_grant_render(value, metaData, record, rowIndex, colIndex,store) {
				return '<input type="button" value="授权" class="cbtn_danger" onclick="_btn_grant_onclick();" />';
			}

            //弹出修改角色窗口
            function _w_role_u_show() {
                var record = AOS.selectone(_g_role);
                if (record) {
                    _w_role_u.show();
                    _f_role_u.loadRecord(record);
                }
            }

            //查询角色列表
            function _g_role_query() {
                var params = {
                    name_: id_name_.getValue()
                };
                _g_role_store.getProxy().extraParams = params;
                _g_role_store.loadPage(1);
            }

            //删除角色
            function _g_role_del() {
                var selectionIds = AOS.selection(_g_role, 'id_');
                if (AOS.empty(selectionIds)) {
                    AOS.tip('删除前请先选中数据。');
                    return;
                }
                var rows = AOS.rows(_g_role);
                var msg = AOS.merge('确认要删除选中的[{0}]条数据吗？', rows);
                AOS.confirm(msg, function (btn) {
                    if (btn === 'cancel') {
                        AOS.tip('删除操作被取消。');
                        return;
                    }
                    AOS.ajax({
                        url: 'roleService.deleteRole',
                        params: {
                            aos_rows_: selectionIds
                        },
                        ok: function (data) {
                            AOS.tip(data.appmsg);
                            _g_role_store.reload();
                        }
                    });
                });

            }

            //新增角色保存
            function _f_role_save() {
                AOS.ajax({
                    forms: _f_role,
                    url: 'roleService.saveRole',
                    ok: function (data) {
                        _w_role.hide();
                        _g_role_store.reload();
                        AOS.tip(data.appmsg);
                    }
                });
            }

            //修改角色保存
            function _f_role_u_save() {
                AOS.ajax({
                    forms: _f_role_u,
                    url: 'roleService.updateRole',
                    ok: function (data) {
                        _w_role_u.hide();
                        _g_role_store.reload();
                        AOS.tip(data.appmsg);
                    }
                });
            }
          //按钮列转换
        	function fn_button_render(value, metaData, record) {
        		return '<input type="button" value="修改" class="cbtn_danger" onclick="_w_role_u_show();" />';
        	}
        	
        	//按钮列转换
        	function fn_button_del(value, metaData, record) {
        		return '<input type="button" value="删除" class="cbtn_danger" onclick="fn_del();" />';
        	}
        </script>
</aos:onready>

<script type="text/javascript">
	function _w_role_u_show(){
		 var record = AOS.selectone(AOS.get('_g_role'));
         if (record) {
        	 AOS.get('_w_role_u').show();
        	 AOS.get('_f_role_u').loadRecord(record);
         }
	}
	function fn_del(){
		//由于是列按钮对应的函数，下面获取对象的的写法和onready代码段里的js有些不同
		var record = AOS.selectone(AOS.get('_g_role'));
		var msg =  AOS.merge('您确定要删除吗？', "");
		AOS.confirm(msg, function(btn){
			if(btn === 'cancel'){
				AOS.tip('删除操作被取消。');
				return;
			}
			AOS.ajax({
				url : 'roleService.deleteRole',
				params:{
					id_: record.data.id_
				},
				ok : function(data) {
					AOS.tip(data.appmsg);
					AOS.get('_g_role').getStore().reload();
				}
			});
		});
	}
	
   function _btn_grant_onclick(){
	   Ext.getCmp('_w_role_module').show();
   }
   function _btn_user_onclick(){
	   Ext.getCmp('_w_role_user').show();
   }
</script>