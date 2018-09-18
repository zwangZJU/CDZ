<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/common/tags.jsp"%>

<aos:html title="${app_title_}" base="http" lib="ext,buttons,filter">
<link rel="stylesheet" type="text/css" href="${cxt}/static/css/modules/index.css" />
<style type="text/css">
.north_el {
	height: 60px;
	background-image:
		url('${cxt}/static/image/background/index/${north_back_img_}');
}

.main_text {
	color: ${main_text_color_}
}

.nav_text {
	color: ${nav_text_color_}
}
#loading-mask {
	z-index: 20000;
	left: 0px;
	top: 0px;
	position: absolute;
	width: 100%;
	height: 100%;
	opacity: 0.5;
	filter: alpha(opacity = 50);
	background-color: ${south_back_color_};
}
</style>
<aos:body>
	<%-- 显示loading --%>
	<div id="loading-mask"></div>
	<div id="loading">
		<c:choose>
			<c:when test="${page_load_gif_ == 'run.gif'}">
				<img src="${cxt}/static/image/gif/pageload/${page_load_gif_}">
			</c:when>
			<c:otherwise>
				<img width="100" height="100" src="${cxt}/static/image/gif/pageload/${page_load_gif_}">
			</c:otherwise>
		</c:choose>
	</div>
	<%-- Center导航 --%>
	<div id="_div_center">
		<iframe id="_id_tab_welcome.frame" src="do.jhtml?router=homeService.initPortal&juid=${juid}"></iframe>
	</div>
	<%-- Banner导航 --%>
	<div id="_id_north_el" class="x-hidden north_el">
		<table style="width: 100%">
			<tr>
				<td width="300px"><img src="${cxt}/static/image/logo/left-logo.png"></td>
				<td align="left"><c:if test="${navDto.is_show_top_nav_ == '1' }">
						<c:if test="${navDto.nav_button_style_ == 'tight' }">
							<div class="button-group">
								<c:forEach var="card" items="${cardDtos}">
									<button id="_id_nav_${card.id_}" type="button" onclick="_fn_nav_btn_click('${card.id_}');"
										class="${navDto.nav_bar_style}">
										<c:if test="${card.vector_ != null}">
											<i id="_id_icon_${card.id_}" class="fa ${card.vector_}"></i>
										</c:if>
										${card.name_}
									</button>
								</c:forEach>
							</div>
						</c:if>
						<c:if test="${navDto.nav_button_style_ == 'standalone' }">
							<c:forEach var="card" items="${cardDtos}">
								<a id="_id_nav_${card.id_}" onclick="_fn_nav_btn_click('${card.id_}');" class="${navDto.nav_bar_style}"> <c:if
										test="${card.vector_ != null}">
										<i id="_id_icon_${card.id_}" class="fa ${card.vector_}"></i>
									</c:if> ${card.name_}
								</a>
							</c:forEach>
						</c:if>
					</c:if></td>
				<td align="right" style="padding: 5px;" width="260px">
					<table style="border-spacing: 3px;">
						<tr align="right">
							<td colspan="4" class="main_text" style="padding-right: 12px;"><nobr>
									${date_} ${week_} <span id="rTime"></span>
								</nobr></td>
						<tr>
						<tr align="right">
							<td><!--a class="${navDto.right_button_style}" onmouseout="_btn_onmouseout(this)" onclick="_fn_show_myinfo()"
								onmouseover="_btn_onmouseover(this);"><i class="fa fa-github-square"></i> 个人设置</a--> <a
								class="${navDto.right_button_style}" onmouseout="_btn_onmouseout(this)" onclick="_fn_show_mypwd()"
								onmouseover="_btn_onmouseover(this);"><i class="fa fa-key"></i> 修改密码</a> <a class="${navDto.right_button_style}"
								onclick="_fn_logout()" onmouseout="_btn_onmouseout(this)" onmouseover="_btn_onmouseover(this);"><i
									class="fa fa-power-off"></i> 退出</a></td>
						<tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
	<div id="_id_south_el" class="x-hidden">
		<div class="south_el_left main_text">
			<i class="fa fa-pagelines"></i> ${welcome_msg_}, ${user_name_}. 
		</div>
		<div class="south_el_right main_text">
			<i class="fa fa-copyright"></i> ${copyright_}
		</div>
	</div>
</aos:body>
</aos:html>

<aos:onready ux="iframe">
	<aos:viewport layout="border" id="_test">
		<aos:panel id="_north" region="north" contentEl="_id_north_el" height="60" maxHeight="60" minHeight="60"
			border="false" header="false" collapsible="true" collapseMode="mini" split="true">
		</aos:panel>

		<aos:panel id="_south" region="south" contentEl="_id_south_el" height="18" border="false" header="false"
			bodyStyle="backgroundColor:'${south_back_color_}'">
		</aos:panel>

		<aos:tabpanel id="_west" region="west" activeTab="0" plain="true" tabBarHeight="30" bodyBorder="0 1 1 1" split="true"
			maxWidth="300" border="true" minWidth="220" width="240" collapsible="true" collapseMode="mini" header="false">
			<aos:tab id="_sys_nav" title="系统导航" layout="accordion" animate="false">
				<aos:docked forceBoder="0 0 1 0">
					<aos:triggerfield id="id_filter" emptyText="过滤功能菜单..." trigger1Cls="x-form-search-trigger"
						onchang="fn_find_modules" onTrigger1Click="fn_find_modules" width="185" />
					<aos:dockeditem xtype="tbfill" />
					<aos:dockeditem text="" tooltip="更多选型" icon="icon141.png">
						<aos:menu>
							<!--aos:menuitem text="个人设置" icon="user20.png" onclick="_fn_show_myinfo" /-->
							<aos:menuitem text="修改密码" icon="key.png" onclick="_fn_show_mypwd" />
							<aos:menuitem xtype="menuseparator" />
							<%-- <aos:menuitem text="锁定离开" icon="lock.png" onclick="fn_find_modules" /> --%>
							<aos:menuitem text="安全退出" icon="close2.png" onclick="_fn_logout" />
						</aos:menu>
					</aos:dockeditem>
				</aos:docked>
				<c:forEach var="card" items="${cardDtos}">
					<aos:treepanel id="_id_card_${card.id_}" onitemclick="fn_node_click" onexpand="_fn_card_onexpand"
						oncollapse="fn_find_modules" icon="${card.icon_name_}" title="${card.name_}" onrender="_fn_render" rootVisible="false"
						rootId="${card.cascade_id_}" url="homeService.getCardTree" nodeParam="cascade_id_">
					</aos:treepanel>
				</c:forEach>
			</aos:tab>
		</aos:tabpanel>
		<aos:tabpanel id="_tabs" region="center" activeTab="0" plain="true" tabBarHeight="30" bodyBorder="0 1 1 1">
			<aos:plugins>
				<aos:tabCloseMenu extraItemsTail1="最大化 还原:fn_collapse_expand:shape_move_back.png"
					extraItemsTail2="刷新:fn_reload:refresh2.png" />
				<aos:tabReorderer />
			</aos:plugins>
			<aos:tab id="_id_tab_welcome" reorderable="false" title="${welcome_page_title_}" contentEl="_div_center" />
		</aos:tabpanel>
	</aos:viewport>

	<aos:window id="_w_myinfo" title="我的个人设置" iconVec="fa-github-square" iconVecSize="15" resizable="false"
		onshow="_w_myinfo_onshow">
		<aos:formpanel id="_f_myinfo" width="650" labelWidth="65">
			<aos:fieldset title="基础信息" labelWidth="75" columnWidth="1">
				<aos:textfield name="org_name_" fieldLabel="所属部门" readOnly="true" columnWidth="0.5" />
				<aos:textfield name="name_" fieldLabel="姓名" allowBlank="false" maxLength="20" columnWidth="0.49" />
				<aos:combobox name="sex_" fieldLabel="性别" allowBlank="false" dicField="sex_" value="3" columnWidth="0.5" />
				<aos:textfield name="email_" fieldLabel="电子邮件" vtype="email" maxLength="50" columnWidth="0.49" />
				<aos:textfield name="mobile_" fieldLabel="联系电话" maxLength="50" columnWidth="0.5" />
				<aos:textfield name="idno_" fieldLabel="身份证号" maxLength="50" columnWidth="0.49" />
				<aos:textfield name="address_" fieldLabel="联系地址" maxLength="500" columnWidth="0.99" />
				<aos:textareafield name="remark_" fieldLabel="备注" maxLength="4000" height="60" columnWidth="0.99" />
			</aos:fieldset>
			<aos:fieldset title="配置信息" labelWidth="75" columnWidth="1">
				<aos:combobox name="skin_" fieldLabel="界面皮肤" allowBlank="false" dicField="skin_" value="black" columnWidth="0.5" />
				<aos:textfield name="biz_code_" fieldLabel="扩展码" maxLength="200" readOnly="true" columnWidth="0.49" />
			</aos:fieldset>
		</aos:formpanel>
		<aos:docked dock="bottom" ui="footer">
			<aos:dockeditem xtype="tbfill" />
			<aos:dockeditem onclick="_f_myinfo_save" text="保存" icon="ok.png" />
			<aos:dockeditem onclick="#_w_myinfo.hide();" text="关闭" icon="close.png" />
		</aos:docked>
	</aos:window>

	<aos:window id="_w_mypwd" title="修改密码" iconVec="fa-key" iconVecSize="15" resizable="false"
		onshow="#AOS.reset(_f_mypwd);">
		<aos:formpanel id="_f_mypwd" width="400" labelWidth="75">
			<aos:textfield name="password_" fieldLabel="原始密码" inputType="password" allowBlank="false" maxLength="20"
				columnWidth="0.99" />
			<aos:textfield name="new_password_" fieldLabel="新的密码" inputType="password" allowBlank="false" maxLength="20"
				columnWidth="0.99" />
			<aos:textfield name="confirm_new_password_" fieldLabel="重复新密码" inputType="password" allowBlank="false" maxLength="20"
				columnWidth="0.99" />
		</aos:formpanel>
		<aos:docked dock="bottom" ui="footer">
			<aos:dockeditem xtype="tbfill" />
			<aos:dockeditem onclick="_f_mypwd_save" text="确认" icon="ok.png" />
			<aos:dockeditem onclick="#_w_mypwd.hide();" text="取消" icon="close.png" />
		</aos:docked>
	</aos:window>
	
	
	<aos:window id="_w_mypwd2" title="修改密码" iconVec="fa-key" iconVecSize="15" resizable="false"
		onshow="#AOS.reset(_f_mypwd2);">
		<aos:formpanel id="_f_mypwd2" width="400" labelWidth="75">
			<aos:textfield name="account_" id="account_" fieldLabel="账号"  allowBlank="false" maxLength="20"
				columnWidth="0.99" />
			<aos:textfield name="smscode_" fieldLabel="验证码"  allowBlank="false" maxLength="20"
				columnWidth="0.7" />
			<aos:button text="获取验证码" id="btn_code"  margin="0 0 0 2" style="color:'red'" onclick="_f_mypwd_code" columnWidth="0.29"/>	
			<aos:textfield name="new_password_" fieldLabel="新的密码" inputType="password" allowBlank="false" maxLength="20"
				columnWidth="0.99" />
			<aos:textfield name="confirm_new_password_" fieldLabel="重复新密码" inputType="password" allowBlank="false" maxLength="20"
				columnWidth="0.99" />
		</aos:formpanel>
		<aos:docked dock="bottom" ui="footer">
			<aos:dockeditem xtype="tbfill" />
			<aos:dockeditem onclick="_f_mypwd_save2" text="确认" icon="ok.png" />
			<aos:dockeditem onclick="#_w_mypwd2.hide();" text="取消" icon="close.png" />
		</aos:docked>
	</aos:window>
	<%-- 修改窗口 --%>
		<aos:window id="_w_articleType2" title="完善个人资料" >
			<aos:formpanel id="articleForm" width="500" layout="anchor" labelWidth="130">
				<%-- 更新时的隐藏变量(主键) --%>
			<aos:docked forceBoder="1 0 1 0">
				<aos:dockeditem xtype="tbtext" style="color:'red'"  text="说明：银行卡类型、银行卡卡号 保存后无法更改，如需修改，请联系平台方。"  />
			</aos:docked>
			<aos:filefield name="image_url" fieldLabel="头像"  allowBlank="true"  />
			<aos:textfield name="name_" fieldLabel="昵     称" allowBlank="false" emptyText="昵称不超过12个字" maxLength="12"/>
			<aos:textareafield name="remark_" fieldLabel="简     介" allowBlank="false" emptyText="简介不得超过75个字。" maxLength="75"/>
			<aos:combobox name="card_type" fieldLabel="银行卡类型" dicField="banktype"  allowBlank="false" />
			<aos:textfield name="card_no" fieldLabel="银行卡卡号" allowBlank="false" />
			</aos:formpanel>
			<aos:docked dock="bottom" ui="footer">
				<aos:dockeditem xtype="tbfill" />
				<aos:dockeditem onclick="_f_articleType2_update" text="保存" icon="ok.png" />
				<aos:dockeditem onclick="#_w_articleType2.hide();" text="关闭" icon="close.png" />
			</aos:docked>
		</aos:window>
	<script type="text/javascript">
	    //快速过滤
		function fn_find_modules(){
			<c:forEach var="card" items="${cardDtos}">
			    	Ext.getCmp('_id_card_${card.id_}').filterByText(id_filter.getValue());
			</c:forEach>
		}
		
		//获取我的个人设置信息
		function _w_myinfo_onshow(){
			AOS.ajax({
				url : 'homeService.getUser',
				ok : function(data) {
					_f_myinfo.form.setValues(data);
				}
			});
		}
		
		//修改我的个人设置信息
		function _f_myinfo_save(){
			AOS.ajax({
				forms : _f_myinfo,
				url : 'homeService.updateMyInfo',
				ok : function(data) {
					AOS.tip(data.appmsg);
					_w_myinfo.hide();
					if(data.is_skin_changed_ == '1'){
						AOS.job(function(){
							location.reload();
						},200)
					}
				}
			});
		}
		
		//修改我的密码
		function _f_mypwd_save(){
			AOS.ajax({
				forms : _f_mypwd,
				url : 'homeService.updateMyPwd',
				ok : function(data) {
					if(data.appcode == '1'){
						AOS.tip(data.appmsg);
						_w_mypwd.hide();
					}else{
						AOS.err(data.appmsg);
					}
				}
			});
		}
		//修改我的密码
		function _f_mypwd_save2(){
			AOS.ajax({
				forms : _f_mypwd2,
				url : 'homeService.updateMyPwd2',
				ok : function(data) {
					if(data.appcode == '1'){
						AOS.tip(data.appmsg);
						_w_mypwd2.hide();
					}else{
						AOS.err(data.appmsg);
					}
				}
			});
		}
		var countdown=60;
		function _f_mypwd_code(){
			var account_=Ext.getCmp("account_").getValue();
			if(account_==""){
				 Ext.Msg.alert('提示信息', "账号不能为空！"); 
			}else{
				var obj={};
				obj.account_=account_;
				AOS.ajax({
					params : obj,
					url : 'homeService.getSMSCode',
					ok : function(data) {
						if (data.appcode == '1') {//成功
							settime();
  						}else{
  							Ext.Msg.alert('提示信息',data.appmsg); 
  						}
					}
				});
			}
			
		} 
			
		function settime() {
		    if (countdown == 0) {   
		    	Ext.getCmp("btn_code").setDisabled(false);     
		    	Ext.getCmp("btn_code").setText("获取验证码"); 
		        countdown = 60;   
		        return;  
		    } else {   
		    	Ext.getCmp("btn_code").setDisabled(true);   
		    	Ext.getCmp("btn_code").setText("重新发送(" + countdown + "秒)"); 
		        countdown--;   
		    } 
		    setTimeout(function() { settime()},1000);
	    }
	    //刷新当前活动卡片
	    function fn_reload(){
	    	var cur_tab_id = _tabs.getLayout().activeItem.id;
	    	if(cur_tab_id === '_id_tab_welcome'){
	    		Ext.get('_id_tab_welcome.frame').dom.contentWindow.location.reload();
	    	}else{
	    		Ext.getCmp(cur_tab_id + '.iframe').load();
	    	}
	    }
	    
	    //最大化 还原
	    function fn_collapse_expand(){
	    	_west.toggleCollapse();
	    	_north.toggleCollapse();
	    }
		
		//响应卡片展开事件
		function _fn_card_onexpand(me, eOpts) {
			var id_ = me.id.substr(9)
			_fn_nav_btn_click(id_);
		}

		function _fn_render(){
			//window.setTimeout(update_num,4000); 
			
		}
		
		//响应导航菜单树节点单击事件
		function fn_node_click(view, record, item, index, e) {
			//菜单节点所属的那个卡片标识，也是当前菜单树的根节点
			var root_id_ = record.getPath().split('/')[2];
			var text=record.raw.text;
			var url =  record.raw.a;
			if (!Ext.isEmpty(url)) {
				
				fnaddtab(url,text,record.raw.id,  root_id_);
				
			}else{
				if(record.raw.leaf){
					 AOS.tip('没有配置菜单的请求地址。');
				}
			}
		}
		
		//响应快捷菜单单击事件
		function fn_quick_click(id, name, url) {
			fnaddtab(id, name, url);
		}
		
		_west.setActiveTab(0);
		
		var type_="${type_}";
		var card_type="${card_type}";
		if(type_==2&&(typeof(card_type) == "undefined"||card_type=="")){
			_w_articleType2.show();
		}
		function _f_articleType2_update(){
			Ext.getCmp('articleForm').getForm().submit({       
	            url:'${cxt}/http/do.jhtml?router=userExpertsService.updatePerson&juid=${juid}', 
	            method:'post',	
	            success:function(form, action){   
	            	Ext.Msg.alert('成功信息',action.result.appmsg); 
	            	_w_articleType2.hide(); 
	            	fnaddtab("userExpertsService.initPersonInfo","个人资料","1702070919430299",  "1608211848140002");
	            },
	            failure:function(form, action){       
	            	 Ext.Msg.alert('失败信息', action.result.appmsg); 
	              
	             }   
	  
	          });
			
		}
		//
		
	</script>
</aos:onready>

<script type="text/javascript">
	//打开菜单功能页面
	function fnaddtab(url, menuname, module_id_, root_id_) {
		if (Ext.isEmpty(url)) {
			return;
		}
		var id = "_id_tab_" + module_id_;
		url = url.indexOf('http://') === 0 ? url : '${cxt}/http/do.jhtml?router=' + url;
		var index = url.indexOf('?');
		//一级菜单的主页面所属的页面元素其page_id_同module_id_。
		url = url + (index === -1 ? '?' : '&') + 'juid=${juid}' + '&aos_module_id_=' + module_id_;
		var _tabs = Ext.getCmp('_tabs');
		var tab = _tabs.getComponent(id);
		var tempflag = 0;
		if (!tab) {
			var iframe = Ext.create('AOS.ux.IFrame', {
				id : id + '.iframe',
				mask : true,
				layout : 'fit',
				//这个参数仅起到将iframe组件自带的mask调节到相对居中位置的作用
				height : document.body.clientHeight - 200,
				loadMask : '${page_load_msg_}'
			});
			tab = _tabs.add({
				id : id,
				module_id_: module_id_, //供Tab与导航树逆向联动使用。
				root_id_: root_id_, //菜单节点所属的那个卡片标识，也是当前菜单树的根节点。供Tab与导航树逆向联动使用。
				title : '<span class="app-container-title-normal">' + menuname + '</span>',
				closable : true,
				layout : 'fit',
				items : [ iframe ]
			});
			tab.on('activate', function() {
				//防止已打开的功能页面切回时再次加载
				if (tempflag === 0) {
					iframe.load(url);
					tempflag = 1;
				}
				//切换的时候和导航树保持同步
				fn_sync_tab_tree(tab);
			});
		}
		//激活新增Tab
		_tabs.setActiveTab(tab);
	}
	
	//切换卡片的时候和导航树保持同步
	function fn_sync_tab_tree(tab){
		if(AOS.empty(tab.root_id_) || AOS.empty(tab.module_id_)){
			return;
		}
		var _sys_nav_tab = Ext.getCmp('_sys_nav');
		if(!_sys_nav_tab.isVisible()){
			return;
		}
		var expanded_tree = Ext.getCmp('_id_card_' + tab.root_id_);
		var expanded_tree_store = expanded_tree.getStore();
		var cur_node = expanded_tree_store.getById(tab.module_id_);
		if(AOS.empty(cur_node)) return;
		//如果节点所属卡片不可见，则使之可见
		_fn_nav_left_mode1(tab.root_id_);
		//如果节点不可见，则使之可见
		if(!cur_node.isVisible()) expanded_tree.expandPath(cur_node.getPath());
		var sm = expanded_tree.getSelectionModel();
		sm.select(cur_node);
	}

	//当前已按下的导航按钮
	var _g_visited_domid = '';
	//横向导航和左侧导航的互动
	function _fn_nav_btn_click(id_) {
		//重定位当前卡片位
		var _sys_nav = Ext.getCmp('_sys_nav');
		if(!_sys_nav.isVisible()){
			Ext.getCmp('_west').setActiveTab(_sys_nav);
		}
		var domid = '_id_nav_' + id_;
		var _dom_obj = Ext.getDom(domid);
		if (!AOS.empty(_g_visited_domid)) {
			var _dom_visited = Ext.getDom(_g_visited_domid);
			_dom_visited.className = '${navDto.nav_bar_style}';
			//停止ICON转动
			if(Ext.get(_dom_visited).down('i')) Ext.get(_dom_visited).down('i').removeCls('fa-spin');
		}
		//ICON转动
		if(Ext.get(_dom_obj).down('i')) Ext.get(_dom_obj).down('i').addCls('fa-spin');
		_dom_obj.className = '${navDto.nav_bar_style_visited}';
		_g_visited_domid = domid;
		_fn_nav_left_mode1(id_);
	}

	//当导航模式为1时，水平和左侧导航的互动模式
	function _fn_nav_left_mode1(id_) {
		var cmpid = '_id_card_' + id_;
		var _cmp_card = Ext.getCmp(cmpid);
		if (_cmp_card.getCollapsed()) {
			_cmp_card.expand(true)
		}
	}

	//移除首页正在加载的缓冲div
	Ext.EventManager.on(window, 'load', function() {
		AOS.job(function() {
			Ext.get('loading').fadeOut({
				duration : 500, //遮罩渐渐消失
				remove : true
			});
			Ext.get('loading-mask').fadeOut({
				duration : 500,
				remove : true
			});
		}, 0); //做这个延时，只是为在Dom加载很快的时候GIF动画效果更稍微显著一点

	});

	//注销
	function _fn_logout() {
		AOS.confirm('确认退出当前账号？', function(btn) {
			if (btn === 'cancel') {
				AOS.tip('操作被取消。');
				return;
			}
			AOS.mask('正在退出, 请稍候...');
			AOS.ajax({
				url : 'homeService.logout',
				wait : false,
				ok : function(data) {
					AOS.unmask();
					window.location.href = 'do.jhtml?router=homeService.initLogin';
				}
			});
		});
	}

	//打开我的个人设置
	function _fn_show_myinfo() {
		Ext.getCmp('_w_myinfo').show();
	}
	
	//打开修改密码窗口
	function _fn_show_mypwd(){
		var type_="${type_}";
		if(type_=="1"){
			Ext.getCmp('_w_mypwd').show();
		}else{
			Ext.getCmp('_w_mypwd2').show();
		}
		
	}

	//按钮矢量图标动画控制
	function _btn_onmouseout(me) {
		//停止ICON转动
		Ext.get(me).down('i').removeCls('fa-spin');
	}

	//按钮矢量图标动画控制
	function _btn_onmouseover(me) {
		//ICON转动
		Ext.get(me).down('i').addCls('fa-spin');
	}

	//显示系统时钟
	function showTime() {
		Ext.get('rTime').update(Ext.Date.format(new Date(), 'H:i:s'));
	}

	//加载完毕执行函数
	window.onload = function() {
		showTime();
		AOS.task(showTime, 1000);
		//页面加载完毕后选中第一个导航按钮
		if (!AOS.empty('${first_card_id_}')) {
			var nav_tab_index_ = '0';
			if(nav_tab_index_ === '0'){
				_fn_nav_btn_click('${first_card_id_}');
			}
		}
	}
	
	
</script>