<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/common/tags.jsp"%>

<aos:html title="活动表" base="http" lib="ext">
<aos:body>
</aos:body>
</aos:html>
<aos:onready>
	<aos:viewport layout="border" id="viewport">
		<aos:formpanel id="_f_query" layout="column" labelWidth="70" header="false" region="north" border="false">
			<aos:docked forceBoder="0 0 1 0">
				<aos:dockeditem xtype="tbtext" text="查询条件" />
			</aos:docked>
			<aos:hiddenfield name="type_"  fieldLabel="活动ID" value="4" />
			<aos:textfield name="name_" fieldLabel="奖励名称" columnWidth="0.25" />
			<aos:combobox name="status_" fieldLabel="奖励状态" dicField="activity_status" columnWidth="0.25" />
			<aos:dockeditem xtype="button" text="查询" onclick="_g_activity_query" icon="query.png" />
			<aos:dockeditem xtype="button" text="重置" onclick="AOS.reset(_f_query);" icon="refresh.png" />
		</aos:formpanel>

		<aos:gridpanel id="_g_activity" url="activityController.listActivity" onrender="_g_activity_query"
			onitemdblclick="#_w_activity2.show();" region="center" onload="_g_activity_load" onitemclick="query_activity_rule">
			<aos:menu>
				<aos:menuitem text="新增" onclick="#_w_activity.show();AOS.reset(_f_activity);" icon="add.png" />
				<aos:menuitem text="修改" onclick="_g_activity_update" icon="edit.png" />
				<aos:menuitem text="删除" onclick="fn_del()" icon="del.png" />
				<aos:menuitem xtype="menuseparator" />
				<aos:menuitem text="刷新" onclick="#_g_activity_store.reload();" icon="refresh.png" />
			</aos:menu>
			<aos:docked forceBoder="1 0 1 0">
				<aos:dockeditem xtype="tbtext" text="奖励信息列表" />
				<aos:dockeditem xtype="tbseparator" />
				<aos:dockeditem text="新增" icon="add.png" onclick="#_w_activity.show();AOS.reset(_f_activity);" />
				<aos:dockeditem text="修改" icon="edit.png" onclick="_g_activity_update" />
				<%--<aos:dockeditem text="批量删除" icon="del.png" onclick="_g_activity_delAll" />--%>
			</aos:docked>
			<aos:column type="rowno" />
			<aos:column header="主键" dataIndex="activity_id" hidden="true" />	
			<aos:column header="奖励名称" dataIndex="name_" width="80" celltip="true"/>	
			<aos:column header="奖励类型" dataIndex="type_" rendererField="activity_type"  width="80" />	
			<%--aos:column header="短名称" dataIndex="shortname" width="100" celltip="true"/>	
			<aos:column header="跳转地址" dataIndex="url" width="100" celltip="true"/>	
			<<aos:column header="图片" dataIndex="img_url" width="100" rendererFn="fn_image_render"/>	--%>
			<aos:column header="开始时间" dataIndex="start_date" type="date" format="Y-m-d H:i:s" width="100" />	
			<aos:column header="结束时间" dataIndex="end_date" type="date" format="Y-m-d H:i:s" width="100" />	
			<aos:column header="优惠券金额" dataIndex="amount" width="70"  celltip="true"/>	
			<aos:column header="优惠券数量" dataIndex="ar_num" width="70"  celltip="true"/>
			<aos:column header="奖励描述" dataIndex="desc_" width="100" celltip="true"/>	
			<aos:column header="奖励状态" dataIndex="status_" width="50" rendererField="activity_status" />	
			<aos:column header="创建时间" dataIndex="create_date" type="date" format="Y-m-d H:i:s" width="100" />	
			<%-- 数据字典、时间例子
			<aos:column header="卡类型" dataIndex="card_type_" rendererField="card_type_" width="60" />
			<aos:column header="出生日期" dataIndex="birthday_" type="date" format="Y-m-d H:i:s" width="100" />
			--%>
			<aos:column header="修改" rendererFn="fn_button_render" align="center" fixedWidth="50" />
			<aos:column header="删除" rendererFn="fn_button_del" align="center" fixedWidth="50" />
		</aos:gridpanel>
		<%--<aos:panel region="south" height="200" collapsible="true">
		<aos:gridpanel id="_g_activityRule" url="activityRuleController.listActivityRule" onrender="_g_activityRule_query"
			onitemdblclick="#_w_activityRule2.show();" >
			<aos:menu>
				<aos:menuitem text="新增" onclick="_g_activityRule_add" icon="add.png" />
				<aos:menuitem text="修改" onclick="_g_activityRule_update" icon="edit.png" />
				<aos:menuitem text="删除" onclick="fn_del()" icon="del.png" />
				<aos:menuitem xtype="menuseparator" />
				<aos:menuitem text="刷新" onclick="#_g_activityRule_store.reload();" icon="refresh.png" />
			</aos:menu>
			<aos:docked forceBoder="1 0 1 0">
				<aos:dockeditem xtype="tbtext" text="奖励规则列表" />
				<aos:dockeditem xtype="tbseparator" />
				<aos:dockeditem text="新增" icon="add.png" onclick="_g_activityRule_add" />
				<aos:dockeditem text="修改" icon="edit.png" onclick="_g_activityRule_update" />
			</aos:docked>
			<aos:column type="rowno" />
			<aos:column header="主键" dataIndex="ar_id" hidden="true" />	
			<aos:column header="奖励名称" dataIndex="name_" width="100"  celltip="true"/>	
			<aos:column header="条件" dataIndex="symbol" width="100" rendererField="rule_symbol"/>
			<aos:column header="充值金额" dataIndex="param" width="100" />
			<aos:column header="会员类型" dataIndex="expression" width="50" rendererField="grade_" />	
			<aos:column header="描述" dataIndex="desc_" width="200"  celltip="true"/>	
	
			<aos:column header="修改" rendererFn="fn_button_activityRule_render" align="center" fixedWidth="50" />
			<aos:column header="删除" rendererFn="fn_button_activityRule_del" align="center" fixedWidth="50" />
		</aos:gridpanel>
		</aos:panel>--%>
		<%-- 新增窗口 --%>
		<aos:window id="_w_activityRule" title="新增奖励规则">
			<aos:formpanel id="_f_activityRule" width="450" layout="anchor" labelWidth="100">
			<aos:hiddenfield name="activity_id" id="activity_id_" fieldLabel="活动ID" />
			<aos:textfield name="name_" fieldLabel="规则名称" allowBlank="false" />
			<aos:combobox name="symbol" fieldLabel="条件" dicField="rule_symbol" value=">=" allowBlank="false" />
			<aos:numberfield name="param" minValue="1" maxValue="9999999999" fieldLabel="充值金额"  allowBlank="false" />
			<aos:combobox fieldLabel="会员类型" name="expression" value="" allowBlank="false">
				<aos:option value="silver" display="银牌会员" />
				<aos:option value="gold" display="金牌会员" />
				<aos:option value="platinum" display="白金会员" />
			</aos:combobox>
			<aos:textfield name="desc_" fieldLabel="描述" allowBlank="true" />
			</aos:formpanel>
			<aos:docked dock="bottom" ui="footer">
				<aos:dockeditem xtype="tbfill" />
				<aos:dockeditem onclick="_f_activityRule_save" text="保存" icon="ok.png" />
				<aos:dockeditem onclick="#_w_activityRule.hide();" text="关闭" icon="close.png" />
			</aos:docked>
		</aos:window>

		<%-- 修改窗口 --%>
		<aos:window id="_w_activityRule2" title="修改奖励规格" onshow="_w_activityRule_onshow">
			<aos:formpanel id="_f_activityRule2" width="450" layout="anchor" labelWidth="100">
				<%-- 更新时的隐藏变量(主键) --%>
				
			<aos:hiddenfield name="ar_id" fieldLabel="主键" />
			<aos:hiddenfield name="activity_id"  fieldLabel="活动ID" />
			<aos:textfield name="name_" fieldLabel="规则名称" allowBlank="false" />
			<aos:combobox name="symbol" fieldLabel="条件" dicField="rule_symbol"  allowBlank="false" />
			<aos:numberfield name="param" minValue="1" maxValue="9999999999" fieldLabel="充值金额"  allowBlank="false" />
			<aos:combobox fieldLabel="会员类型" name="expression" value="" allowBlank="false">
				<aos:option value="silver" display="银牌会员" />
				<aos:option value="gold" display="金牌会员" />
				<aos:option value="platinum" display="白金会员" />
			</aos:combobox>
			<aos:textfield name="desc_" fieldLabel="描述" allowBlank="true" />
			</aos:formpanel>
			<aos:docked dock="bottom" ui="footer">
				<aos:dockeditem xtype="tbfill" />
				<aos:dockeditem onclick="_f_activityRule2_update" text="保存" icon="ok.png" />
				<aos:dockeditem onclick="#_w_activityRule2.hide();" text="关闭" icon="close.png" />
			</aos:docked>
		</aos:window>
		<%-- 新增窗口 --%>
		<aos:window id="_w_activity" title="新增分享送优惠券设置"  layout="fit" autoScroll="true">
			<aos:formpanel id="_f_activity" width="450" layout="anchor" labelWidth="80" fileUpload="true">
			<aos:hiddenfield name="type_" fieldLabel="奖励类型" value="4" />
			<aos:textfield name="name_" fieldLabel="奖励名称" allowBlank="false" />
			<aos:datetimefield name="start_date" fieldLabel="开始时间" editable="true" allowBlank="false" />	
			<aos:datetimefield name="end_date" fieldLabel="结束时间" editable="true" allowBlank="false" />	
			<aos:numberfield name="amount"  minValue="0.01" maxValue="9999999999"  fieldLabel="优惠券金额"  allowBlank="false" />
			<aos:numberfield name="ar_num"  minValue="1" maxValue="9999999999" fieldLabel="优惠券数量"  allowBlank="false" />
			<aos:combobox name="status_" fieldLabel="奖励状态" value="1" dicField="activity_status"  allowBlank="false" />
			<aos:textareafield name="desc_" fieldLabel="奖励描述" />
			</aos:formpanel>
			<aos:docked dock="bottom" ui="footer">
				<aos:dockeditem xtype="tbfill" />
				<aos:dockeditem onclick="_f_activity_save" text="保存" icon="ok.png" />
				<aos:dockeditem onclick="#_w_activity.hide();" text="关闭" icon="close.png" />
			</aos:docked>
		</aos:window>

		<%-- 修改窗口 --%>
		<aos:window id="_w_activity2" title="修改分享送优惠券设置" onshow="_w_activity_onshow" layout="fit" autoScroll="true" >
			<aos:formpanel id="_f_activity2" width="450" layout="anchor" labelWidth="80" fileUpload="true" autoScroll="true">
				<%-- 更新时的隐藏变量(主键) --%>
			<aos:hiddenfield name="activity_id" fieldLabel="主键" />
			<aos:hiddenfield name="type_"  fieldLabel="类型" />
			<aos:textfield name="name_" fieldLabel="奖励名称" allowBlank="false" />
			<aos:datetimefield name="start_date" fieldLabel="开始时间" editable="true" allowBlank="false" />	
			<aos:datetimefield name="end_date" fieldLabel="结束时间" editable="true" allowBlank="false" />	
			<aos:numberfield name="amount"  minValue="0.01" maxValue="9999999999"  fieldLabel="优惠券金额"  allowBlank="false" />
			<aos:numberfield name="ar_num"  minValue="1" maxValue="9999999999" fieldLabel="优惠券数量"  allowBlank="false" />
			<aos:combobox name="status_" fieldLabel="奖励状态" dicField="activity_status"  allowBlank="false" />
			<aos:textareafield name="desc_" fieldLabel="奖励描述" />
			</aos:formpanel>
			<aos:docked dock="bottom" ui="footer">
				<aos:dockeditem xtype="tbfill" />
				<aos:dockeditem onclick="_f_activity2_update" text="保存" icon="ok.png" />
				<aos:dockeditem onclick="#_w_activity2.hide();" text="关闭" icon="close.png" />
			</aos:docked>
		</aos:window>
		<aos:window id="_w_articleTypeList" title="待选商品列表" autoScroll="false" layout="border" onshow="_w_articleTypeList_onshow">
			<aos:formpanel id="_f_queryList" layout="column"  labelWidth="70" header="false"   border="false"  region="north" >
				<aos:docked forceBoder="0 0 1 0">
					<aos:dockeditem xtype="tbtext" text="查询条件" />
				</aos:docked>
				<aos:hiddenfield name="activity_id" id="activity_id" />
				<aos:textfield name="title" fieldLabel="标题" columnWidth="0.25" />
				<aos:combobox name="status_" fieldLabel="状态" dicField="goods_status" columnWidth="0.25" />
				<aos:docked dock="bottom" ui="footer" margin="0 0 8 0">
					<aos:dockeditem xtype="tbfill" />
					<aos:dockeditem xtype="button" text="查询" onclick="_g_article_query" icon="query.png" />
					<aos:dockeditem xtype="button" text="重置" onclick="AOS.reset(_f_queryList);" icon="refresh.png" />
					<aos:dockeditem xtype="tbfill" />
				</aos:docked>
			</aos:formpanel>
			<aos:gridpanel id="_g_article" url="goodsService.listActivityGoods" onrender="_g_article_query"
				 region="center" >
				<aos:docked forceBoder="1 0 1 0">
					<aos:dockeditem xtype="tbtext" text="待选商品列表" />
					<aos:dockeditem text="添加" icon="add.png" onclick="_g_goods_addAll" />
				</aos:docked>
				<aos:selmodel type="checkbox" mode="multi" />
				<aos:column header="主键" dataIndex="goods_id" hidden="true" />	
				<aos:column header="标题" dataIndex="title" width="100" celltip="true" locked="false" />	
				<aos:column header="短标题" dataIndex="shorttitle" celltip="true" width="100" />	
				<aos:column header="短描述" dataIndex="shortdesc" celltip="true" width="100" />	
				<aos:column header="库存" dataIndex="inventory" celltip="true" width="100" />
				<aos:column header="安全库存" dataIndex="safety" width="100" />
				<aos:column header="价格1" dataIndex="price1" width="100" />	
				<aos:column header="价格2" dataIndex="price2" width="100" />	
				<aos:column header="价格3" dataIndex="price3" width="100" />	
				<aos:column header="价格4" dataIndex="price4" width="100" />	
				<aos:column header="价格5" dataIndex="price5" width="100" />	
				<aos:column header="是否推荐" dataIndex="is_reco" width="100" rendererField="goods_reco"/>	
			</aos:gridpanel>
		</aos:window>
	</aos:viewport>

	<script type="text/javascript">
	
	var grid_selected_index=0;
	function query_activity_rule(){
		var records = _g_activity.getSelectionModel().getSelection();
		grid_selected_index = _g_activity.getStore().indexOf(records[0]);
		_g_activityRule_store.getProxy().extraParams = {activity_id:records[0].data.activity_id};
		_g_activityRule_store.loadPage(1);
		
	}
	function _g_activity_load(){
		_g_activity.getSelectionModel().select(grid_selected_index,true);
		var records = _g_activity.getSelectionModel().getSelection();
		/*if(records){
			_g_activityRule_store.getProxy().extraParams = {activity_id:records[0].data.activity_id};
		}else{
			_g_activityRule_store.getProxy().extraParams = {activity_id:-1};
		}
		_g_activityRule_store.loadPage(1);*/
		
	}
	
	
		function _w_articleTypeList_onshow(){
			_g_article_query();
			
			
		}
		//加载表格数据
		function _g_article_query() {
			var params = AOS.getValue('_f_queryList');
			//invite
			var activity_id=Ext.getCmp("activity_id").getValue();
			params.activity_id=activity_id;
			_g_article_store.getProxy().extraParams = params;
			_g_article_store.loadPage(1);
			
		}
	
		function _g_activity_goods(){
			if(AOS.selectone(_g_activity)){
				var record = AOS.selectone(_g_activity, true);
				fn_g_activity_goods(record.data.activity_id,record.data.name_);
			}
		}
	
		function fn_g_activity_goods(goods_id,name_){
			Ext.getCmp("activity_id").setValue(goods_id);
			AOS.get('_w_articleTypeList').setWidth(Ext.getCmp("viewport").getWidth());
			AOS.get('_w_articleTypeList').setHeight(Ext.getCmp("viewport").getHeight());
			AOS.get('_w_articleTypeList').setTitle(name_+" 待选商品列表");
			AOS.get('_w_articleTypeList').show();
			Ext.getDom('_f_queryList').style.top=0;
			Ext.getDom('_f_queryList').style.left=0;
			
			
			
		}
	
		//加载表格数据
		function _g_activity_query() {
			var params = AOS.getValue('_f_query');
			_g_activity_store.getProxy().extraParams = params;
			_g_activity_store.loadPage(1);
		}
		
		//新增账户保存
		function _f_activity_save(){
			Ext.getCmp('_f_activity').getForm().submit({       
	            url:'${cxt}/http/do.jhtml?router=activityController.saveActivity&juid=${juid}', 
	            method:'post',	
	            success:function(form, action){
	            	if(action.result.appcode=='1'){
		            	Ext.Msg.alert('成功信息',action.result.appmsg);  
		               	_g_activity_store.reload();
						_w_activity.hide();
	            	}else{
	               		AOS.info(action.result.appmsg);
	               	}	
	            },
	            failure:function(form, action){       
	            	 Ext.Msg.alert('失败信息', action.result.appmsg); 
	              
	             }   
	  
	          });
			
				
		}
		//窗口弹出事件监听
		function _w_activity_onshow() {
			var record = AOS.selectone(_g_activity);
            AOS.ajax({
            	params : {
            			        activity_id: record.data.activity_id
            	},
                url: 'activityController.getActivity',
                ok: function (data) {
                	_f_activity2.form.setValues(data);
                	 document.getElementById("_img_photo").src="${cxt}/myupload"+data.img_url;
                }
            });
		}
		//监听修改窗口弹出
		function _w_activity2_onshow(){
			//这里演示的是直接从表格行中加载数据，也可以发一个ajax请求查询数据(见misc1.jsp有相关用法)
			var record = AOS.selectone(_g_activity, true);
			_f_activity2.loadRecord(record);
		}
		
		//修改账户保存
		function _f_activity2_update(){
			Ext.getCmp('_f_activity2').getForm().submit({       
	            url:'${cxt}/http/do.jhtml?router=activityController.updateActivity&juid=${juid}', 
	            method:'post',	
	            success:function(form, action){ 
	            	if(action.result.appcode=='1'){
		            	Ext.Msg.alert('成功信息',action.result.appmsg);  
		               	_g_activity_store.reload();
		               	_w_activity2.hide();
	               	}else{
	               		AOS.info(action.result.appmsg);
	               	 	_g_activity_store.reload();
		               	_w_activity2.hide();
	               	}    
	            },
	            failure:function(form, action){       
	            	 Ext.Msg.alert('失败信息', action.result.appmsg); 
	              
	             }   
	  
	          });
				
		}
		
		 //更新
	 	function _g_activity_update(){
	 		
				if(AOS.selectone(_g_activity)){
					//_w_activity2_onshow();
					AOS.get('_w_activity2').show();
				}
				
				
		}
		
	 	function _g_goods_addAll(){
        	var selection = AOS.selection(_g_article, 'goods_id');
			
			if(AOS.empty(selection)){
				AOS.tip('添加商品前请先选中数据。');
				return;
			}
			var rows = AOS.rows(_g_article);
			var msg =  AOS.merge('确认要添加选中的{0}条记录吗？', rows);
			var activity_id=Ext.getCmp("activity_id").getValue();
			AOS.confirm(msg, function(btn){
				if(btn === 'cancel'){
					AOS.tip('添加操作被取消。');
					return;
				}
				AOS.ajax({
					url : 'activityGoodsService.saveActivityGoods',
					params:{
						aos_rows_: selection,
						activity_id:activity_id
					},
					ok : function(data) {
						AOS.tip(data.appmsg);
						_g_article_store.reload();
					}
				});
			});
		}
		 
		 
        //删除账户信息
	 	function _g_activity_delAll(){
	        	var selection = AOS.selection(_g_activity, 'activity_id');
				
				if(AOS.empty(selection)){
					AOS.tip('删除前请先选中数据。');
					return;
				}
				var rows = AOS.rows(_g_activity);
				var msg =  AOS.merge('确认要删除选中的{0}条记录吗？', rows);
				AOS.confirm(msg, function(btn){
					if(btn === 'cancel'){
						AOS.tip('删除操作被取消。');
						return;
					}
					AOS.ajax({
						url : 'activityController.deleteActivity',
						params:{
							aos_rows_: selection
						},
						ok : function(data) {
							AOS.tip(data.appmsg);
							_g_activity_store.reload();
						}
					});
				});
			}
		
		//按钮列转换
		function fn_button_render(value, metaData, record) {
			return '<input type="button" value="修改" class="cbtn_danger" onclick="_w_activity2_show();" />';
		}
		
		//按钮列转换
		function fn_button_del(value, metaData, record) {
			return '<input type="button" value="删除" class="cbtn_danger" onclick="fn_del();" />';
		}
		
		//活动规则
		
		//加载表格数据
		function _g_activityRule_query() {
			var params = AOS.getValue('_f_query');
			_g_activityRule_store.getProxy().extraParams = {activity_id:-1};
			_g_activityRule_store.loadPage(1);
		}
		
		 //新增
	 	function _g_activityRule_add(){
	 		    var rows = AOS.rows(_g_activity);
				if(rows==1){
					var record = AOS.selectone(_g_activity, true);
					var type_=record.data.type_;
					AOS.get('_w_activityRule').show();
					/*if(type_==3){
						Ext.getCmp("cond_value").show();
						Ext.getCmp("award_amt").setFieldLabel("赠送券金额");
					}else{
						Ext.getCmp("cond_value").hide();
						if(type_==2){
							Ext.getCmp("award_amt").setFieldLabel("折扣值");
						}else{
							Ext.getCmp("award_amt").setFieldLabel("减免金额");
						}
					}*/
					AOS.reset(_f_activityRule);
					Ext.getCmp("activity_id_").setValue(record.data.activity_id);
				}else{
					AOS.info('请先选择一条活动记录。');
				}
				
				
		}
		
		
		//新增账户保存
		function _f_activityRule_save(){
				AOS.ajax({
					forms : _f_activityRule,
					url : 'activityRuleController.saveActivityRule',
					ok : function(data) {
						if(data.appcode=='1'){
							 AOS.tip(data.appmsg);
							_g_activityRule_store.reload();
							_w_activityRule.hide();
						}else{
							 AOS.info(data.appmsg);
						 }
					}
			});
		}
		//窗口弹出事件监听
		function _w_activityRule_onshow() {
			var record = AOS.selectone(_g_activityRule);
            AOS.ajax({
            	params : {
            			        ar_id: record.data.ar_id
            	},
                url: 'activityRuleController.getActivityRule',
                ok: function (data) {
                	_f_activityRule2.form.setValues(data);
                	var record_ = AOS.selectone(_g_activity, true);
					var type_=record_.data.type_;
        			/*if(type_==3){
        				Ext.getCmp("cond_value_").show();
        				Ext.getCmp("award_amt_").setFieldLabel("赠送券金额");
        			}else{
        				Ext.getCmp("cond_value_").hide();
        				if(type_==2){
        					Ext.getCmp("award_amt_").setFieldLabel("折扣值");
        				}else{
        					Ext.getCmp("award_amt_").setFieldLabel("减免金额");
        				}
        			}*/
                }
            });
		}
		//监听修改窗口弹出
		function _w_activityRule2_onshow(){
			//这里演示的是直接从表格行中加载数据，也可以发一个ajax请求查询数据(见misc1.jsp有相关用法)
			var record = AOS.selectone(_g_activityRule, true);
			_f_activityRule2.loadRecord(record);
		}
		
		//修改账户保存
		function _f_activityRule2_update(){
				AOS.ajax({
					forms : _f_activityRule2,
					url : 'activityRuleController.updateActivityRule',
					ok : function(data) {
						if(data.appcode=='1'){
							AOS.tip(data.appmsg);
							_g_activityRule_store.reload();
							_w_activityRule2.hide();
						}else{
							 AOS.info(data.appmsg);
						 }
					}
			});
		}
		
		 //更新
	 	function _g_activityRule_update(){
	 		
				if(AOS.selectone(_g_activityRule)){
					//_w_activityRule2_onshow();
					AOS.get('_w_activityRule2').show();
					
				}
				
				
		}
		
        //删除账户信息
	 	function _g_activityRule_delAll(){
	        	var selection = AOS.selection(_g_activityRule, 'ar_id');
				
				if(AOS.empty(selection)){
					AOS.tip('删除前请先选中数据。');
					return;
				}
				var rows = AOS.rows(_g_activityRule);
				var msg =  AOS.merge('确认要删除选中的{0}条记录吗？', rows);
				AOS.confirm(msg, function(btn){
					if(btn === 'cancel'){
						AOS.tip('删除操作被取消。');
						return;
					}
					AOS.ajax({
						url : 'activityRuleController.deleteActivityRule',
						params:{
							aos_rows_: selection
						},
						ok : function(data) {
							AOS.tip(data.appmsg);
							_g_activityRule_store.reload();
						}
					});
				});
			}
		
		//按钮列转换
		function fn_button_activityRule_render(value, metaData, record) {
			return '<input type="button" value="修改" class="cbtn_danger" onclick="_w_activityRule2_show();" />';
		}
		
		//按钮列转换
		function fn_button_activityRule_del(value, metaData, record) {
			return '<input type="button" value="删除" class="cbtn_danger" onclick="fn_activityRule_del();" />';
		}
		
	</script>
	function fn_image_render(value, metaData, record) {
			value="${cxt}/myupload"+value;
			return '<img  src="'+value+'">';
		}
</aos:onready>

<script type="text/javascript">

function _w_activity2_show(){
	AOS.get('_w_activity2').show();
}




function fn_del(){
	//由于是列按钮对应的函数，下面获取对象的的写法和onready代码段里的js有些不同
	var record = AOS.selectone(AOS.get('_g_activity'));
	var msg =  AOS.merge('确认要删除当前记录吗？', "");
	AOS.confirm(msg, function(btn){
		if(btn === 'cancel'){
			AOS.tip('删除操作被取消。');
			return;
		}
		AOS.ajax({
			url : 'activityController.deleteActivity',
			params:{
	        activity_id: record.data.activity_id
			},
			ok : function(data) {
				AOS.tip(data.appmsg);
				AOS.get('_g_activity').getStore().reload();
			}
		});
	});
}

function _w_activityRule2_show(){
	AOS.get('_w_activityRule2').show();
}




function fn_activityRule_del(){
	//由于是列按钮对应的函数，下面获取对象的的写法和onready代码段里的js有些不同
	var record = AOS.selectone(AOS.get('_g_activityRule'));
	var msg =  AOS.merge('确认要删除当前记录吗？', "");
	AOS.confirm(msg, function(btn){
		if(btn === 'cancel'){
			AOS.tip('删除操作被取消。');
			return;
		}
		AOS.ajax({
			url : 'activityRuleController.deleteActivityRule',
			params:{
	        ar_id: record.data.ar_id
			},
			ok : function(data) {
				AOS.tip(data.appmsg);
				AOS.get('_g_activityRule').getStore().reload();
			}
		});
	});
}
</script>