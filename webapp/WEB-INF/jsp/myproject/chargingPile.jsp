<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/common/tags.jsp"%>

<aos:html title="charging_pile" base="http" lib="ext">
<aos:body>
</aos:body>
</aos:html>
<aos:onready>
	<aos:viewport layout="border">
		<aos:formpanel id="_f_query" layout="column" labelWidth="70" header="false" region="north" border="false">
			<aos:docked forceBoder="0 0 1 0">
				<aos:dockeditem xtype="tbtext" text="查询条件" />
			</aos:docked>
				<aos:textfield name="cp_no" fieldLabel="编号" columnWidth="0.25" />
				<aos:textfield name="supplier" fieldLabel="供应商"  columnWidth="0.25" />
				<aos:textfield name="addrs" fieldLabel="地址" columnWidth="0.25" />
				<aos:textfield name="cp_type" fieldLabel="型号" columnWidth="0.25" />
			<%--
			<aos:textfield name="name_" fieldLabel="持卡人姓名" columnWidth="0.25" />
			<aos:combobox name="card_type_" fieldLabel="卡类型" dicField="card_type_" columnWidth="0.25" />
			--%>
			<aos:docked dock="bottom" ui="footer" margin="0 0 8 0">
				<aos:dockeditem xtype="tbfill" />
				<aos:dockeditem xtype="button" text="查询" onclick="_g_chargingPile_query" icon="query.png" />
				<aos:dockeditem xtype="button" text="重置" onclick="AOS.reset(_f_query);" icon="refresh.png" />
				<aos:dockeditem xtype="tbfill" />
			</aos:docked>
		</aos:formpanel>

		<aos:gridpanel id="_g_chargingPile" url="chargingPileController.listChargingPile" onrender="_g_chargingPile_query"
			onitemdblclick="#_w_chargingPile2.show();" region="center">
			<aos:menu>
				<%--<aos:menuitem text="新增" onclick="#_w_chargingPile.show();AOS.reset(_f_chargingPile);" icon="add.png" />
				<aos:menuitem text="修改" onclick="_g_chargingPile_update" icon="edit.png" />
				<aos:menuitem text="删除" onclick="fn_del()" icon="del.png" />--%>
				<aos:menuitem xtype="menuseparator" />
				<aos:menuitem text="刷新" onclick="#_g_chargingPile_store.reload();" icon="refresh.png" />
			</aos:menu>
			<aos:docked forceBoder="1 0 1 0">
				<aos:dockeditem xtype="tbtext" text="充电桩信息列表" />
				<aos:dockeditem xtype="tbseparator" />
				<%--<aos:dockeditem text="新增" icon="add.png" onclick="#_w_chargingPile.show();AOS.reset(_f_chargingPile);" />
				<aos:dockeditem text="修改" icon="edit.png" onclick="_g_chargingPile_update" />--%>
				<aos:dockeditem text="批量删除" icon="del.png" onclick="_g_chargingPile_delAll" />
			</aos:docked>
			<aos:selmodel type="checkbox" mode="multi" />
			<aos:column type="rowno" />
			<aos:column header="主键" dataIndex="cp_id" hidden="true" />	
			<aos:column header="编号" dataIndex="cp_no" width="100" celltip="true"/>	
			<aos:column header="供应商" dataIndex="supplier" width="100" celltip="true"/>	
			<aos:column header="省" dataIndex="province" width="100" celltip="true"/>	
			<aos:column header="市" dataIndex="city" width="100" celltip="true"/>	
			<aos:column header="区" dataIndex="county" width="100" celltip="true"/>	
			<aos:column header="地址" dataIndex="addr" width="100" celltip="true"/>	
			<aos:column header="型号" dataIndex="cp_type" width="100" celltip="true" />	
			<aos:column header="电费" dataIndex="electricity" width="100" />	
			<aos:column header="状态" dataIndex="cp_status" width="100"  rendererField="cp_status"/>	
			<aos:column header="经度" dataIndex="lon" width="100" celltip="true"/>	
			<aos:column header="纬度" dataIndex="lat" width="100" celltip="true"/>	
			<aos:column header="创建时间" dataIndex="create_date" type="date" format="Y-m-d H:i:s" width="150" />	
			<%-- 数据字典、时间例子
			<aos:column header="卡类型" dataIndex="card_type_" rendererField="card_type_" width="60" />
			<aos:column header="出生日期" dataIndex="birthday_" type="date" format="Y-m-d H:i:s" width="100" />
			
			<aos:column header="修改" rendererFn="fn_button_render" align="center" fixedWidth="50" />--%>
			<aos:column header="删除" rendererFn="fn_button_del" align="center" fixedWidth="50" />
		</aos:gridpanel>

		<%-- 新增窗口 --%>
		<aos:window id="_w_chargingPile" title="新增账户">
			<aos:formpanel id="_f_chargingPile" width="450" layout="anchor" labelWidth="70">
			<aos:textfield name="cp_no" fieldLabel="编号" allowBlank="false" />
			<aos:textfield name="supplier" fieldLabel="供应商" allowBlank="false" />
			<aos:textfield name="province" fieldLabel="省" allowBlank="false" />
			<aos:textfield name="city" fieldLabel="市" allowBlank="false" />
			<aos:textfield name="county" fieldLabel="区" allowBlank="false" />
			<aos:textfield name="addr" fieldLabel="地址" allowBlank="false" />
			<aos:textfield name="cp_type" fieldLabel="型号" allowBlank="false" />
			<aos:textfield name="electricity" fieldLabel="充电费" allowBlank="false" />
			<aos:textfield name="cp_status" fieldLabel="状态" allowBlank="false" />
			<aos:textfield name="lon" fieldLabel="经度" allowBlank="false" />
			<aos:textfield name="lat" fieldLabel="纬度" allowBlank="false" />
			<aos:datefield name="create_date" fieldLabel="创建时间" editable="true" allowBlank="false" />	
			<aos:textfield name="is_del" fieldLabel="是否删除，0：未删除，1：删除" allowBlank="false" />
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
				<aos:dockeditem onclick="_f_chargingPile_save" text="保存" icon="ok.png" />
				<aos:dockeditem onclick="#_w_chargingPile.hide();" text="关闭" icon="close.png" />
			</aos:docked>
		</aos:window>

		<%-- 修改窗口 --%>
		<aos:window id="_w_chargingPile2" title="修改账户" onshow="_w_chargingPile2_onshow">
			<aos:formpanel id="_f_chargingPile2" width="450" layout="anchor" labelWidth="70">
				<%-- 更新时的隐藏变量(主键) --%>
				
			<aos:hiddenfield name="cp_id" fieldLabel="主键" />
			<aos:textfield name="cp_no" fieldLabel="编号" allowBlank="false" />
			<aos:textfield name="supplier" fieldLabel="供应商" allowBlank="false" />
			<aos:textfield name="province" fieldLabel="省" allowBlank="false" />
			<aos:textfield name="city" fieldLabel="市" allowBlank="false" />
			<aos:textfield name="county" fieldLabel="区" allowBlank="false" />
			<aos:textfield name="addr" fieldLabel="地址" allowBlank="false" />
			<aos:textfield name="cp_type" fieldLabel="型号" allowBlank="false" />
			<aos:textfield name="electricity" fieldLabel="充电费" allowBlank="false" />
			<aos:textfield name="cp_status" fieldLabel="状态" allowBlank="false" />
			<aos:textfield name="lon" fieldLabel="经度" allowBlank="false" />
			<aos:textfield name="lat" fieldLabel="纬度" allowBlank="false" />
			<aos:datefield name="create_date" fieldLabel="创建时间" editable="true" allowBlank="false" />	
			<aos:textfield name="is_del" fieldLabel="是否删除，0：未删除，1：删除" allowBlank="false" />
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
				<aos:dockeditem onclick="_f_chargingPile2_update" text="保存" icon="ok.png" />
				<aos:dockeditem onclick="#_w_chargingPile2.hide();" text="关闭" icon="close.png" />
			</aos:docked>
		</aos:window>

	</aos:viewport>

	<script type="text/javascript">
	
		//加载表格数据
		function _g_chargingPile_query() {
			var params = AOS.getValue('_f_query');
			_g_chargingPile_store.getProxy().extraParams = params;
			_g_chargingPile_store.loadPage(1);
		}
		
		//新增账户保存
		function _f_chargingPile_save(){
				AOS.ajax({
					forms : _f_chargingPile,
					url : 'chargingPileController.saveChargingPile',
					ok : function(data) {
						 AOS.tip(data.appmsg);
						_g_chargingPile_store.reload();
						_w_chargingPile.hide();
					}
			});
		}
		//窗口弹出事件监听
		function _w_chargingPile_onshow() {
			var record = AOS.selectone(_g_chargingPile);
            AOS.ajax({
            	params : {
            			        cp_id: record.data.cp_id
            	},
                url: 'chargingPileController.getChargingPile',
                ok: function (data) {
                	_f_chargingPile2.form.setValues(data);
                }
            });
		}
		//监听修改窗口弹出
		function _w_chargingPile2_onshow(){
			//这里演示的是直接从表格行中加载数据，也可以发一个ajax请求查询数据(见misc1.jsp有相关用法)
			var record = AOS.selectone(_g_chargingPile, true);
			_f_chargingPile2.loadRecord(record);
		}
		
		//修改账户保存
		function _f_chargingPile2_update(){
				AOS.ajax({
					forms : _f_chargingPile2,
					url : 'chargingPileController.updateChargingPile',
					ok : function(data) {
						AOS.tip(data.appmsg);
						_g_chargingPile_store.reload();
						_w_chargingPile2.hide();
					}
			});
		}
		
		 //更新
	 	function _g_chargingPile_update(){
	 		
				if(AOS.selectone(_g_chargingPile)){
					//_w_chargingPile2_onshow();
					AOS.get('_w_chargingPile2').show();
				}
				
				
		}
		
        //删除账户信息
	 	function _g_chargingPile_delAll(){
	        	var selection = AOS.selection(_g_chargingPile, 'cp_id');
				
				if(AOS.empty(selection)){
					AOS.tip('删除前请先选中数据。');
					return;
				}
				var rows = AOS.rows(_g_chargingPile);
				var msg =  AOS.merge('确认要删除选中的{0}条记录吗？', rows);
				AOS.confirm(msg, function(btn){
					if(btn === 'cancel'){
						AOS.tip('删除操作被取消。');
						return;
					}
					AOS.ajax({
						url : 'chargingPileController.deleteChargingPile',
						params:{
							aos_rows_: selection
						},
						ok : function(data) {
							AOS.tip(data.appmsg);
							_g_chargingPile_store.reload();
						}
					});
				});
			}
		
		//按钮列转换
		function fn_button_render(value, metaData, record) {
			return '<input type="button" value="修改" class="cbtn_danger" onclick="_w_chargingPile2_show();" />';
		}
		
		//按钮列转换
		function fn_button_del(value, metaData, record) {
			return '<input type="button" value="删除" class="cbtn_danger" onclick="fn_del();" />';
		}
		
	</script>
</aos:onready>

<script type="text/javascript">

function _w_chargingPile2_show(){
	AOS.get('_w_chargingPile2').show();
}




function fn_del(){
	//由于是列按钮对应的函数，下面获取对象的的写法和onready代码段里的js有些不同
	var record = AOS.selectone(AOS.get('_g_chargingPile'));
	var msg =  AOS.merge('确认要删除当前记录吗？', "");
	AOS.confirm(msg, function(btn){
		if(btn === 'cancel'){
			AOS.tip('删除操作被取消。');
			return;
		}
		AOS.ajax({
			url : 'chargingPileController.deleteChargingPile',
			params:{
	        cp_id: record.data.cp_id
			},
			ok : function(data) {
				AOS.tip(data.appmsg);
				AOS.get('_g_chargingPile').getStore().reload();
			}
		});
	});
}

</script>