<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/common/tags.jsp"%>

<aos:html title="charging_orders" base="http" lib="ext">
<aos:body>
</aos:body>
</aos:html>
<aos:onready>
	<aos:viewport layout="border">
		<aos:formpanel id="_f_query" layout="column" labelWidth="70" header="false" region="north" border="false">
			<aos:docked forceBoder="0 0 1 0">
				<aos:dockeditem xtype="tbtext" text="查询条件" />
			</aos:docked>
			<aos:textfield name="account_" fieldLabel="会员手机号" columnWidth="0.25" />
			<aos:textfield name="cp_no" fieldLabel="充电桩编号" columnWidth="0.25" />
			<aos:combobox name="status_" fieldLabel="状态" dicField="order_status_" columnWidth="0.25" />
			<aos:combobox name="co_type" fieldLabel="充电类型" dicField="co_type" columnWidth="0.25" />
			<aos:combobox name="deduction_type" fieldLabel="抵扣类型" dicField="deduction_type" columnWidth="0.25" />
			<aos:datefield id="date_start" name="date_start" fieldLabel="开始时间"  columnWidth="0.25"/>
			<aos:datefield id="date_end" name="date_end" fieldLabel="至"  columnWidth="0.25"/>
			<%--
			<aos:textfield name="name_" fieldLabel="持卡人姓名" columnWidth="0.25" />
			<aos:combobox name="card_type_" fieldLabel="卡类型" dicField="card_type_" columnWidth="0.25" />
			--%>
			<aos:docked dock="bottom" ui="footer" margin="0 0 8 0">
				<aos:dockeditem xtype="tbfill" />
				<aos:dockeditem xtype="button" text="查询" onclick="_g_chargingOrders_query" icon="query.png" />
				<aos:dockeditem xtype="button" text="重置" onclick="AOS.reset(_f_query);" icon="refresh.png" />
				<aos:dockeditem xtype="button" text="导出" onclick="fn_export_excel" icon="icon70.png" />
				<aos:dockeditem xtype="tbfill" />
			</aos:docked>
		</aos:formpanel>

		<aos:gridpanel id="_g_chargingOrders" url="chargingOrdersController.listChargingOrders" onrender="_g_chargingOrders_query"
			 region="center">
			<%--<aos:menu>
				<aos:menuitem text="新增" onclick="#_w_chargingOrders.show();AOS.reset(_f_chargingOrders);" icon="add.png" />
				<aos:menuitem text="修改" onclick="_g_chargingOrders_update" icon="edit.png" />
				<aos:menuitem text="删除" onclick="fn_del()" icon="del.png" />
				<aos:menuitem xtype="menuseparator" />
				<aos:menuitem text="刷新" onclick="#_g_chargingOrders_store.reload();" icon="refresh.png" />
			</aos:menu>--%>
			<aos:docked forceBoder="1 0 1 0">
				<aos:dockeditem xtype="tbtext" text="充电订单信息列表" />
				<aos:dockeditem xtype="tbseparator" />
				<%--<aos:dockeditem text="新增" icon="add.png" onclick="#_w_chargingOrders.show();AOS.reset(_f_chargingOrders);" />
				<aos:dockeditem text="修改" icon="edit.png" onclick="_g_chargingOrders_update" />
				<aos:dockeditem text="批量删除" icon="del.png" onclick="_g_chargingOrders_delAll" />--%>
			</aos:docked>
			<aos:selmodel type="checkbox" mode="multi" />
			<aos:column type="rowno" />
			<aos:column header="co_id" dataIndex="co_id" hidden="true" />	
			<aos:column header="会员手机号" dataIndex="b1_account_" width="100" celltip="true"/>	
			<aos:column header="充电桩编号" dataIndex="cp_no" width="100" />	
			<aos:column header="已充金额" dataIndex="amounted" width="70" />	
			<aos:column header="已充时间" dataIndex="dateed" width="70" />	
			<aos:column header="已充电量" dataIndex="electricity" width="70" />	
			<aos:column header="剩余时间" dataIndex="rest_date" width="70" />	
			<aos:column header="总金额" dataIndex="total_amt" width="80" />	
			<aos:column header="抵扣金额" dataIndex="deduction_amt" width="80" />	
			<aos:column header="实付金额" dataIndex="real_amt" width="80" />	
			<aos:column header="状态" dataIndex="status_" rendererField="order_status_" width="80" />	
			<aos:column header="充电类型" dataIndex="co_type" rendererField="co_type" width="80" />	
			<aos:column header="数量" dataIndex="co_num" width="80" />	
			<aos:column header="抵扣类型" dataIndex="deduction_type" rendererField="deduction_type" width="100" />	
			<aos:column header="创建时间" dataIndex="create_date" type="date" format="Y-m-d H:i:s" width="150" />	
	
			<%-- 数据字典、时间例子
			<aos:column header="卡类型" dataIndex="card_type_" rendererField="card_type_" width="60" />
			<aos:column header="出生日期" dataIndex="birthday_" type="date" format="Y-m-d H:i:s" width="100" />
			<aos:column header="修改" rendererFn="fn_button_render" align="center" fixedWidth="50" />--%>
			<aos:column header="删除" rendererFn="fn_button_del" align="center" fixedWidth="50" />
		</aos:gridpanel>

		<%-- 新增窗口 --%>
		<aos:window id="_w_chargingOrders" title="新增账户">
			<aos:formpanel id="_f_chargingOrders" width="450" layout="anchor" labelWidth="70">
			<aos:textfield name="user_id" fieldLabel="用户ID" allowBlank="false" />
			<aos:textfield name="cp_id" fieldLabel="充电桩ID" allowBlank="false" />
			<aos:textfield name="amounted" fieldLabel="已充金额" allowBlank="false" />
			<aos:textfield name="dateed" fieldLabel="已充时间" allowBlank="false" />
			<aos:textfield name="electricity" fieldLabel="已充电量" allowBlank="false" />
			<aos:textfield name="rest_date" fieldLabel="剩余时间" allowBlank="false" />
			<aos:textfield name="total_amt" fieldLabel="总金额" allowBlank="false" />
			<aos:textfield name="deduction_amt" fieldLabel="抵扣金额" allowBlank="false" />
			<aos:textfield name="real_amt" fieldLabel="实付金额" allowBlank="false" />
			<aos:textfield name="status_" fieldLabel="状态，0：新增，1：支付成功，2：支付失败，-1：取消" allowBlank="false" />
			<aos:textfield name="co_type" fieldLabel="计算类型，0：金额，1：时间，2：度数，3：充满" allowBlank="false" />
			<aos:textfield name="co_num" fieldLabel="数量" allowBlank="false" />
			<aos:textfield name="deduction_type" fieldLabel="抵扣类型，0：折扣，1：优惠券" allowBlank="false" />
			<aos:textfield name="is_del" fieldLabel="是否删除，0：未删除，1：删除" allowBlank="false" />
			<aos:datefield name="create_date" fieldLabel="创建时间" editable="true" allowBlank="false" />	
			<aos:textfield name="oper_id" fieldLabel="操作人ID" allowBlank="false" />
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
				<aos:dockeditem onclick="_f_chargingOrders_save" text="保存" icon="ok.png" />
				<aos:dockeditem onclick="#_w_chargingOrders.hide();" text="关闭" icon="close.png" />
			</aos:docked>
		</aos:window>

		<%-- 修改窗口 --%>
		<aos:window id="_w_chargingOrders2" title="修改账户" onshow="_w_chargingOrders2_onshow">
			<aos:formpanel id="_f_chargingOrders2" width="450" layout="anchor" labelWidth="70">
				<%-- 更新时的隐藏变量(主键) --%>
				
			<aos:hiddenfield name="co_id" fieldLabel="co_id" />
			<aos:textfield name="user_id" fieldLabel="用户ID" allowBlank="false" />
			<aos:textfield name="cp_id" fieldLabel="充电桩ID" allowBlank="false" />
			<aos:textfield name="amounted" fieldLabel="已充金额" allowBlank="false" />
			<aos:textfield name="dateed" fieldLabel="已充时间" allowBlank="false" />
			<aos:textfield name="electricity" fieldLabel="已充电量" allowBlank="false" />
			<aos:textfield name="rest_date" fieldLabel="剩余时间" allowBlank="false" />
			<aos:textfield name="total_amt" fieldLabel="总金额" allowBlank="false" />
			<aos:textfield name="deduction_amt" fieldLabel="抵扣金额" allowBlank="false" />
			<aos:textfield name="real_amt" fieldLabel="实付金额" allowBlank="false" />
			<aos:textfield name="status_" fieldLabel="状态，0：新增，1：支付成功，2：支付失败，-1：取消" allowBlank="false" />
			<aos:textfield name="co_type" fieldLabel="计算类型，0：金额，1：时间，2：度数，3：充满" allowBlank="false" />
			<aos:textfield name="co_num" fieldLabel="数量" allowBlank="false" />
			<aos:textfield name="deduction_type" fieldLabel="抵扣类型，0：折扣，1：优惠券" allowBlank="false" />
			<aos:textfield name="is_del" fieldLabel="是否删除，0：未删除，1：删除" allowBlank="false" />
			<aos:datefield name="create_date" fieldLabel="创建时间" editable="true" allowBlank="false" />	
			<aos:textfield name="oper_id" fieldLabel="操作人ID" allowBlank="false" />
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
				<aos:dockeditem onclick="_f_chargingOrders2_update" text="保存" icon="ok.png" />
				<aos:dockeditem onclick="#_w_chargingOrders2.hide();" text="关闭" icon="close.png" />
			</aos:docked>
		</aos:window>

	</aos:viewport>

	<script type="text/javascript">
	
		//加载表格数据
		function _g_chargingOrders_query() {
			var params = AOS.getValue('_f_query');
			_g_chargingOrders_store.getProxy().extraParams = params;
			_g_chargingOrders_store.loadPage(1);
		}
		
		//新增账户保存
		function _f_chargingOrders_save(){
				AOS.ajax({
					forms : _f_chargingOrders,
					url : 'chargingOrdersController.saveChargingOrders',
					ok : function(data) {
						 AOS.tip(data.appmsg);
						_g_chargingOrders_store.reload();
						_w_chargingOrders.hide();
					}
			});
		}
		//窗口弹出事件监听
		function _w_chargingOrders_onshow() {
			var record = AOS.selectone(_g_chargingOrders);
            AOS.ajax({
            	params : {
            			        co_id: record.data.co_id
            	},
                url: 'chargingOrdersController.getChargingOrders',
                ok: function (data) {
                	_f_chargingOrders2.form.setValues(data);
                }
            });
		}
		//监听修改窗口弹出
		function _w_chargingOrders2_onshow(){
			//这里演示的是直接从表格行中加载数据，也可以发一个ajax请求查询数据(见misc1.jsp有相关用法)
			var record = AOS.selectone(_g_chargingOrders, true);
			_f_chargingOrders2.loadRecord(record);
		}
		
		//修改账户保存
		function _f_chargingOrders2_update(){
				AOS.ajax({
					forms : _f_chargingOrders2,
					url : 'chargingOrdersController.updateChargingOrders',
					ok : function(data) {
						AOS.tip(data.appmsg);
						_g_chargingOrders_store.reload();
						_w_chargingOrders2.hide();
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
			AOS.file('do.jhtml?router=chargingOrdersController.exportExcel&juid=${juid}'+params_url);
		}
		
		 //更新
	 	function _g_chargingOrders_update(){
	 		
				if(AOS.selectone(_g_chargingOrders)){
					//_w_chargingOrders2_onshow();
					AOS.get('_w_chargingOrders2').show();
				}
				
				
		}
		
        //删除账户信息
	 	function _g_chargingOrders_delAll(){
	        	var selection = AOS.selection(_g_chargingOrders, 'co_id');
				
				if(AOS.empty(selection)){
					AOS.tip('删除前请先选中数据。');
					return;
				}
				var rows = AOS.rows(_g_chargingOrders);
				var msg =  AOS.merge('确认要删除选中的{0}条记录吗？', rows);
				AOS.confirm(msg, function(btn){
					if(btn === 'cancel'){
						AOS.tip('删除操作被取消。');
						return;
					}
					AOS.ajax({
						url : 'chargingOrdersController.deleteChargingOrders',
						params:{
							aos_rows_: selection
						},
						ok : function(data) {
							AOS.tip(data.appmsg);
							_g_chargingOrders_store.reload();
						}
					});
				});
			}
		
		//按钮列转换
		function fn_button_render(value, metaData, record) {
			return '<input type="button" value="修改" class="cbtn_danger" onclick="_w_chargingOrders2_show();" />';
		}
		
		//按钮列转换
		function fn_button_del(value, metaData, record) {
			return '<input type="button" value="删除" class="cbtn_danger" onclick="fn_del();" />';
		}
		
	</script>
</aos:onready>

<script type="text/javascript">

function _w_chargingOrders2_show(){
	AOS.get('_w_chargingOrders2').show();
}




function fn_del(){
	//由于是列按钮对应的函数，下面获取对象的的写法和onready代码段里的js有些不同
	var record = AOS.selectone(AOS.get('_g_chargingOrders'));
	var msg =  AOS.merge('确认要删除当前记录吗？', "");
	AOS.confirm(msg, function(btn){
		if(btn === 'cancel'){
			AOS.tip('删除操作被取消。');
			return;
		}
		AOS.ajax({
			url : 'chargingOrdersController.deleteChargingOrders',
			params:{
	        co_id: record.data.co_id
			},
			ok : function(data) {
				AOS.tip(data.appmsg);
				AOS.get('_g_chargingOrders').getStore().reload();
			}
		});
	});
}

</script>