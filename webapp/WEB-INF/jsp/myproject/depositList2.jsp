<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/common/tags.jsp"%>

<aos:html title="deposit_list" base="http" lib="ext">
<aos:body>
</aos:body>
</aos:html>
<aos:onready>
	<aos:viewport layout="border">
		<aos:formpanel id="_f_query" layout="column" labelWidth="70" header="false" region="north" border="false">
			<aos:docked forceBoder="0 0 1 0">
				<aos:dockeditem xtype="tbtext" text="查询条件" />
			</aos:docked>
			<aos:hiddenfield name="oper_id"   value="0" />
			<aos:textfield name="account_" fieldLabel="会员手机号" columnWidth="0.25" />
			<aos:datefield id="date_start" name="date_start" fieldLabel="申请时间"  columnWidth="0.25"/>
			<aos:datefield id="date_end" name="date_end" fieldLabel="至"  columnWidth="0.25"/>
			<aos:docked dock="bottom" ui="footer" margin="0 0 8 0">
				<aos:dockeditem xtype="tbfill" />
				<aos:dockeditem xtype="button" text="查询" onclick="_g_depositList_query" icon="query.png" />
				<aos:dockeditem xtype="button" text="重置" onclick="AOS.reset(_f_query);" icon="refresh.png" />
				<aos:dockeditem xtype="tbfill" />
			</aos:docked>
		</aos:formpanel>

		<aos:gridpanel id="_g_depositList" url="depositListController.list2DepositList" onrender="_g_depositList_query"
			 region="center">
			<aos:menu>
				<%--<aos:menuitem text="新增" onclick="#_w_depositList.show();AOS.reset(_f_depositList);" icon="add.png" />
				<aos:menuitem text="修改" onclick="_g_depositList_update" icon="edit.png" />
				<aos:menuitem text="删除" onclick="fn_del()" icon="del.png" />--%>
				<aos:menuitem text="退款" onclick="_g_depositList_pay" icon="task_finish.png" />
				<aos:menuitem xtype="menuseparator" />
				<aos:menuitem text="刷新" onclick="#_g_depositList_store.reload();" icon="refresh.png" />
			</aos:menu>
			<aos:docked forceBoder="1 0 1 0">
				<aos:dockeditem xtype="tbtext" text="申请押金退款记录列表" />
				<aos:dockeditem xtype="tbseparator" />
				<%--<aos:dockeditem text="新增" icon="add.png" onclick="#_w_depositList.show();AOS.reset(_f_depositList);" />
				<aos:dockeditem text="修改" icon="edit.png" onclick="_g_depositList_update" />
				<aos:dockeditem text="批量删除" icon="del.png" onclick="_g_depositList_delAll" />--%>
				<aos:dockeditem text="退款" icon="task_finish.png" onclick="_g_depositList_pay" />
			</aos:docked>
			<aos:selmodel type="checkbox" mode="multi" />
			<aos:column type="rowno" />
			<aos:column header="主建" dataIndex="deposit_id" hidden="true" />	
			<aos:column header="会员手机号" dataIndex="b1_account_" width="100" />
			<aos:column header="押金金额" dataIndex="amt" width="80"  />	
			<aos:column header="申请时间" dataIndex="create_date" type="date" format="Y-m-d H:i:s" width="150" />		
			<aos:column header="申请状态" dataIndex="status" width="80" rendererField="depositStatus" />	
			<aos:column header="审核时间" dataIndex="audit_date" type="date" format="Y-m-d H:i:s" width="150" />	
			<aos:column header="审核人" dataIndex="b2_name_" width="100" />	
			<aos:column header="备注" dataIndex="remark" width="100" />	
			
	
			<%-- 数据字典、时间例子
			<aos:column header="卡类型" dataIndex="card_type_" rendererField="card_type_" width="60" />
			<aos:column header="出生日期" dataIndex="birthday_" type="date" format="Y-m-d H:i:s" width="100" />
			--%>
			<%--<aos:column header="修改" rendererFn="fn_button_render" align="center" fixedWidth="50" />
			<aos:column header="删除" rendererFn="fn_button_del" align="center" fixedWidth="50" />--%>
		</aos:gridpanel>

		<%-- 新增窗口 --%>
		<aos:window id="_w_depositList" title="新增账户">
			<aos:formpanel id="_f_depositList" width="450" layout="anchor" labelWidth="70">
			<aos:textfield name="user_id" fieldLabel="用户ID" allowBlank="false" />
			<aos:textfield name="amt" fieldLabel="押金金额" allowBlank="false" />
			<aos:textfield name="status" fieldLabel="0:提交申请，1：审核通过，-1：审核不通过，2：退款成功" allowBlank="false" />
			<aos:datefield name="audit_date" fieldLabel="审核时间" editable="true" allowBlank="false" />	
			<aos:textfield name="audit_id" fieldLabel="审核人" allowBlank="false" />
			<aos:datefield name="pay_date" fieldLabel="退款时间" editable="true" allowBlank="false" />	
			<aos:textfield name="pay_id" fieldLabel="放款人" allowBlank="false" />
			<aos:textfield name="remark" fieldLabel="备注" allowBlank="false" />
			<aos:datefield name="create_date" fieldLabel="创建时间" editable="true" allowBlank="false" />	
			<aos:textfield name="is_del" fieldLabel="是否删除，0：未删除，1：已删除" allowBlank="false" />
			<aos:textfield name="oper_id" fieldLabel="操作人" allowBlank="false" />
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
				<aos:dockeditem onclick="_f_depositList_save" text="保存" icon="ok.png" />
				<aos:dockeditem onclick="#_w_depositList.hide();" text="关闭" icon="close.png" />
			</aos:docked>
		</aos:window>

		<%-- 修改窗口 --%>
		<aos:window id="_w_depositList2" title="修改账户" onshow="_w_depositList2_onshow">
			<aos:formpanel id="_f_depositList2" width="450" layout="anchor" labelWidth="70">
				<%-- 更新时的隐藏变量(主键) --%>
				
			<aos:hiddenfield name="deposit_id" fieldLabel="主建" />
			<aos:textfield name="user_id" fieldLabel="用户ID" allowBlank="false" />
			<aos:textfield name="amt" fieldLabel="押金金额" allowBlank="false" />
			<aos:textfield name="status" fieldLabel="0:提交申请，1：审核通过，-1：审核不通过，2：退款成功" allowBlank="false" />
			<aos:datefield name="audit_date" fieldLabel="审核时间" editable="true" allowBlank="false" />	
			<aos:textfield name="audit_id" fieldLabel="审核人" allowBlank="false" />
			<aos:datefield name="pay_date" fieldLabel="放款时间" editable="true" allowBlank="false" />	
			<aos:textfield name="pay_id" fieldLabel="放款人" allowBlank="false" />
			<aos:textfield name="remark" fieldLabel="备注" allowBlank="false" />
			<aos:datefield name="create_date" fieldLabel="创建时间" editable="true" allowBlank="false" />	
			<aos:textfield name="is_del" fieldLabel="是否删除，0：未删除，1：已删除" allowBlank="false" />
			<aos:textfield name="oper_id" fieldLabel="操作人" allowBlank="false" />
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
				<aos:dockeditem onclick="_f_depositList2_update" text="保存" icon="ok.png" />
				<aos:dockeditem onclick="#_w_depositList2.hide();" text="关闭" icon="close.png" />
			</aos:docked>
		</aos:window>
		<aos:window id="_w_depositList3" title="审核" onshow="_w_depositList3_onshow">
			<aos:formpanel id="_f_depositList3" width="450" layout="anchor" labelWidth="70">
				<%-- 更新时的隐藏变量(主键) --%>
			<aos:hiddenfield name="deposit_id" fieldLabel="主建" />
			<aos:textfield name="user_id" fieldLabel="会员手机号" disabled="true" />
			<aos:textfield name="amt" fieldLabel="押金金额" disabled="true"  />
			<aos:datetimefield name="create_date" fieldLabel="申请时间" disabled="true" />	
			<aos:radioboxgroup fieldLabel="审核状态" columns="[100,80]" columnWidth="1" >
				<aos:radiobox name="status" boxLabel="审核通过" inputValue="1" />
				<aos:radiobox name="status" boxLabel="审核不通过" inputValue="-1" />
			</aos:radioboxgroup>
			<aos:textareafield name="remark" fieldLabel="备注" />
			</aos:formpanel>
			<aos:docked dock="bottom" ui="footer">
				<aos:dockeditem xtype="tbfill" />
				<aos:dockeditem onclick="_f_depositList3_update" text="保存" icon="ok.png" />
				<aos:dockeditem onclick="#_w_depositList3.hide();" text="关闭" icon="close.png" />
			</aos:docked>
		</aos:window>
		<aos:window id="_w_depositList4" title="退款" onshow="_w_depositList4_onshow">
			<aos:formpanel id="_f_depositList4" width="450" layout="anchor" labelWidth="70">
				<%-- 更新时的隐藏变量(主键) --%>
			<aos:hiddenfield name="deposit_id" fieldLabel="主建" />
			<aos:textfield name="user_id" fieldLabel="会员手机号" disabled="true" />
			<aos:textfield name="amt" fieldLabel="押金金额" disabled="true"  />
			<aos:datetimefield name="create_date" fieldLabel="申请时间" disabled="true" />	
			<aos:datetimefield name="audit_date" fieldLabel="审核时间" disabled="true" />
			<aos:combobox name="status" fieldLabel="审核状态" dicField="depositStatus" disabled="true"  />
			<aos:textareafield name="remark" fieldLabel="备注" disabled="true"/>
			</aos:formpanel>
			<aos:docked dock="bottom" ui="footer">
				<aos:dockeditem xtype="tbfill" />
				<aos:dockeditem onclick="_f_depositList4_update" text="退款" icon="ok.png" />
				<aos:dockeditem onclick="#_w_depositList4.hide();" text="关闭" icon="close.png" />
			</aos:docked>
		</aos:window>
		
	</aos:viewport>

	<script type="text/javascript">
	
		//加载表格数据
		function _g_depositList_query() {
			var params = AOS.getValue('_f_query');
			_g_depositList_store.getProxy().extraParams = params;
			_g_depositList_store.loadPage(1);
		}
		
		//新增账户保存
		function _f_depositList_save(){
				AOS.ajax({
					forms : _f_depositList,
					url : 'depositListController.saveDepositList',
					ok : function(data) {
						 AOS.tip(data.appmsg);
						_g_depositList_store.reload();
						_w_depositList.hide();
					}
			});
		}
		//窗口弹出事件监听
		function _w_depositList_onshow() {
			var record = AOS.selectone(_g_depositList);
            AOS.ajax({
            	params : {
            			        deposit_id: record.data.deposit_id
            	},
                url: 'depositListController.getDepositList',
                ok: function (data) {
                	_f_depositList2.form.setValues(data);
                }
            });
		}
		function _w_depositList3_onshow() {
			var record = AOS.selectone(_g_depositList);
            AOS.ajax({
            	params : {
            			        deposit_id: record.data.deposit_id
            	},
                url: 'depositListController.getDepositList',
                ok: function (data) {
                	_f_depositList3.form.setValues(data);
                	AOS.setValue('_f_depositList3.status','1');
                }
            });
		}
		function _w_depositList4_onshow() {
			var record = AOS.selectone(_g_depositList);
            AOS.ajax({
            	params : {
            	   deposit_id: record.data.deposit_id
            	},
                url: 'depositListController.getDepositList',
                ok: function (data) {
                	_f_depositList4.form.setValues(data);
                }
            });
		}
		//监听修改窗口弹出
		function _w_depositList2_onshow(){
			//这里演示的是直接从表格行中加载数据，也可以发一个ajax请求查询数据(见misc1.jsp有相关用法)
			var record = AOS.selectone(_g_depositList, true);
			_f_depositList2.loadRecord(record);
		}
		
		//修改账户保存
		function _f_depositList2_update(){
				AOS.ajax({
					forms : _f_depositList2,
					url : 'depositListController.updateDepositList',
					ok : function(data) {
						AOS.tip(data.appmsg);
						_g_depositList_store.reload();
						_w_depositList2.hide();
					}
			});
		}
		//修改账户保存
		function _f_depositList3_update(){
			//var status=AOS.getValue('_f_depositList3.status');
				AOS.ajax({
					forms : _f_depositList3,
					url : 'depositListController.auditDepositList',
					ok : function(data) {
						AOS.tip(data.appmsg);
						_g_depositList_store.reload();
						_w_depositList2.hide();
					}
			});
		}
		//修改账户保存
		function _f_depositList4_update(){
			AOS.tip('开发中....');
				/*AOS.ajax({
					forms : _f_depositList4,
					url : 'depositListController.payDepositList',
					ok : function(data) {
						AOS.tip(data.appmsg);
						_g_depositList_store.reload();
						_w_depositList2.hide();
					}
			});*/
		}
		
		 //更新
	 	function _g_depositList_update(){
	 		
				if(AOS.selectone(_g_depositList)){
					//_w_depositList2_onshow();
					AOS.get('_w_depositList2').show();
				}
				
				
		}
		function _g_depositList_audit(){
	 		
			if(AOS.selectone(_g_depositList)){
				//_w_depositList2_onshow();
				AOS.get('_w_depositList3').show();
			}
		}
		function _g_depositList_pay(){
			if(AOS.selectone(_g_depositList)){
				//_w_depositList2_onshow();
				var record = AOS.selectone(_g_depositList, true);
				if(record.data.status=='2'){
					AOS.info('当前记录已退款成功，请勿重复操作。');
					return;
				}
				var msg =  AOS.merge('确认要给会员'+record.data.b1_account_+'退还押金吗');
				AOS.confirm(msg, function(btn){
					if(btn === 'cancel'){
						AOS.tip('删除操作被取消。');
						return;
					}
					AOS.ajax({
						url : 'depositListController.depositListPay',
						params:{
							deposit_id: record.data.deposit_id
						},
						ok : function(data) {
							AOS.tip(data.appmsg);
							_g_depositList_store.reload();
						}
					});
				});
			}
		}
		
        //删除账户信息
	 	function _g_depositList_delAll(){
	        	var selection = AOS.selection(_g_depositList, 'deposit_id');
				
				if(AOS.empty(selection)){
					AOS.tip('删除前请先选中数据。');
					return;
				}
				var rows = AOS.rows(_g_depositList);
				var msg =  AOS.merge('确认要删除选中的{0}条记录吗？', rows);
				AOS.confirm(msg, function(btn){
					if(btn === 'cancel'){
						AOS.tip('删除操作被取消。');
						return;
					}
					AOS.ajax({
						url : 'depositListController.deleteDepositList',
						params:{
							aos_rows_: selection
						},
						ok : function(data) {
							AOS.tip(data.appmsg);
							_g_depositList_store.reload();
						}
					});
				});
			}
		
		//按钮列转换
		function fn_button_render(value, metaData, record) {
			return '<input type="button" value="修改" class="cbtn_danger" onclick="_w_depositList2_show();" />';
		}
		
		//按钮列转换
		function fn_button_del(value, metaData, record) {
			return '<input type="button" value="删除" class="cbtn_danger" onclick="fn_del();" />';
		}
		
	</script>
</aos:onready>

<script type="text/javascript">

function _w_depositList2_show(){
	AOS.get('_w_depositList2').show();
}




function fn_del(){
	//由于是列按钮对应的函数，下面获取对象的的写法和onready代码段里的js有些不同
	var record = AOS.selectone(AOS.get('_g_depositList'));
	var msg =  AOS.merge('确认要删除当前记录吗？', "");
	AOS.confirm(msg, function(btn){
		if(btn === 'cancel'){
			AOS.tip('删除操作被取消。');
			return;
		}
		AOS.ajax({
			url : 'depositListController.deleteDepositList',
			params:{
	        deposit_id: record.data.deposit_id
			},
			ok : function(data) {
				AOS.tip(data.appmsg);
				AOS.get('_g_depositList').getStore().reload();
			}
		});
	});
}

</script>