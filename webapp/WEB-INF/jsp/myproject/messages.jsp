<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/common/tags.jsp"%>

<aos:html title="messages" base="http" lib="ext">
<aos:body>
</aos:body>
</aos:html>
<aos:onready>
	<aos:viewport layout="border">
		<aos:formpanel id="_f_query" layout="column" labelWidth="70" header="false" region="north" border="false">
			<aos:docked forceBoder="0 0 1 0">
				<aos:dockeditem xtype="tbtext" text="查询条件" />
			</aos:docked>
			<aos:textfield name="msg_content" fieldLabel="内容" columnWidth="0.25" />
			<aos:docked dock="bottom" ui="footer" margin="0 0 8 0">
				<aos:dockeditem xtype="tbfill" />
				<aos:dockeditem xtype="button" text="查询" onclick="_g_messages_query" icon="query.png" />
				<aos:dockeditem xtype="button" text="重置" onclick="AOS.reset(_f_query);" icon="refresh.png" />
				<aos:dockeditem xtype="tbfill" />
			</aos:docked>
		</aos:formpanel>

		<aos:gridpanel id="_g_messages" url="messagesController.listMessages" onrender="_g_messages_query"
			onitemdblclick="#_w_messages2.show();" region="center">
			<aos:menu>
				<aos:menuitem text="新增" onclick="#_w_messages.show();AOS.reset(_f_messages);" icon="add.png" />
				<aos:menuitem text="修改" onclick="_g_messages_update" icon="edit.png" />
				<aos:menuitem text="删除" onclick="fn_del()" icon="del.png" />
				<aos:menuitem xtype="menuseparator" />
				<aos:menuitem text="刷新" onclick="#_g_messages_store.reload();" icon="refresh.png" />
			</aos:menu>
			<aos:docked forceBoder="1 0 1 0">
				<aos:dockeditem xtype="tbtext" text="系统推送消息列表" />
				<aos:dockeditem xtype="tbseparator" />
				<aos:dockeditem text="新增" icon="add.png" onclick="#_w_messages.show();AOS.reset(_f_messages);" />
				<aos:dockeditem text="修改" icon="edit.png" onclick="_g_messages_update" />
				<aos:dockeditem text="批量删除" icon="del.png" onclick="_g_messages_delAll" />
			</aos:docked>
			<aos:selmodel type="checkbox" mode="multi" />
			<aos:column type="rowno" />
			<aos:column header="主键" dataIndex="msg_id" hidden="true" />	
			<aos:column header="内容" dataIndex="msg_content" width="150" celltip="true"/>	
			<aos:column header="推送时间" dataIndex="create_date" type="date" format="Y-m-d H:i:s" width="120" />	
			<aos:column header="修改" rendererFn="fn_button_render" align="center" fixedWidth="50" />
			<aos:column header="删除" rendererFn="fn_button_del" align="center" fixedWidth="50" />
		</aos:gridpanel>

		<%-- 新增窗口 --%>
		<aos:window id="_w_messages" title="新增推送消息">
			<aos:formpanel id="_f_messages" width="450" layout="anchor" labelWidth="70">
			<aos:textareafield name="msg_content" fieldLabel="内容" allowBlank="false"/>
			</aos:formpanel>
			<aos:docked dock="bottom" ui="footer">
				<aos:dockeditem xtype="tbfill" />
				<aos:dockeditem onclick="_f_messages_save" text="保存" icon="ok.png" />
				<aos:dockeditem onclick="#_w_messages.hide();" text="关闭" icon="close.png" />
			</aos:docked>
		</aos:window>

		<%-- 修改窗口 --%>
		<aos:window id="_w_messages2" title="修改推送消息并重新推送" onshow="_w_messages_onshow">
			<aos:formpanel id="_f_messages2" width="450" layout="anchor" labelWidth="70">
				<%-- 更新时的隐藏变量(主键) --%>
				
			<aos:hiddenfield name="msg_id" fieldLabel="主键" />
			<aos:textareafield name="msg_content" fieldLabel="内容" allowBlank="false"/>
			</aos:formpanel>
			<aos:docked dock="bottom" ui="footer">
				<aos:dockeditem xtype="tbfill" />
				<aos:dockeditem onclick="_f_messages2_update" text="保存" icon="ok.png" />
				<aos:dockeditem onclick="#_w_messages2.hide();" text="关闭" icon="close.png" />
			</aos:docked>
		</aos:window>

	</aos:viewport>

	<script type="text/javascript">
	
		//加载表格数据
		function _g_messages_query() {
			var params = AOS.getValue('_f_query');
			_g_messages_store.getProxy().extraParams = params;
			_g_messages_store.loadPage(1);
		}
		
		//新增账户保存
		function _f_messages_save(){
				AOS.ajax({
					forms : _f_messages,
					url : 'messagesController.saveMessages',
					ok : function(data) {
						 AOS.tip(data.appmsg);
						_g_messages_store.reload();
						_w_messages.hide();
					}
			});
		}
		//窗口弹出事件监听
		function _w_messages_onshow() {
			var record = AOS.selectone(_g_messages);
            AOS.ajax({
            	params : {
            			        msg_id: record.data.msg_id
            	},
                url: 'messagesController.getMessages',
                ok: function (data) {
                	_f_messages2.form.setValues(data);
                }
            });
		}
		//监听修改窗口弹出
		function _w_messages2_onshow(){
			//这里演示的是直接从表格行中加载数据，也可以发一个ajax请求查询数据(见misc1.jsp有相关用法)
			var record = AOS.selectone(_g_messages, true);
			_f_messages2.loadRecord(record);
		}
		
		//修改账户保存
		function _f_messages2_update(){
				AOS.ajax({
					forms : _f_messages2,
					url : 'messagesController.updateMessages',
					ok : function(data) {
						AOS.tip(data.appmsg);
						_g_messages_store.reload();
						_w_messages2.hide();
					}
			});
		}
		
		 //更新
	 	function _g_messages_update(){
	 		
				if(AOS.selectone(_g_messages)){
					//_w_messages2_onshow();
					AOS.get('_w_messages2').show();
				}
				
				
		}
		
        //删除账户信息
	 	function _g_messages_delAll(){
	        	var selection = AOS.selection(_g_messages, 'msg_id');
				
				if(AOS.empty(selection)){
					AOS.tip('删除前请先选中数据。');
					return;
				}
				var rows = AOS.rows(_g_messages);
				var msg =  AOS.merge('确认要删除选中的{0}条记录吗？', rows);
				AOS.confirm(msg, function(btn){
					if(btn === 'cancel'){
						AOS.tip('删除操作被取消。');
						return;
					}
					AOS.ajax({
						url : 'messagesController.deleteMessages',
						params:{
							aos_rows_: selection
						},
						ok : function(data) {
							AOS.tip(data.appmsg);
							_g_messages_store.reload();
						}
					});
				});
			}
		
		//按钮列转换
		function fn_button_render(value, metaData, record) {
			return '<input type="button" value="修改" class="cbtn_danger" onclick="_w_messages2_show();" />';
		}
		
		//按钮列转换
		function fn_button_del(value, metaData, record) {
			return '<input type="button" value="删除" class="cbtn_danger" onclick="fn_del();" />';
		}
		
	</script>
</aos:onready>

<script type="text/javascript">

function _w_messages2_show(){
	AOS.get('_w_messages2').show();
}




function fn_del(){
	//由于是列按钮对应的函数，下面获取对象的的写法和onready代码段里的js有些不同
	var record = AOS.selectone(AOS.get('_g_messages'));
	var msg =  AOS.merge('确认要删除当前记录吗？', "");
	AOS.confirm(msg, function(btn){
		if(btn === 'cancel'){
			AOS.tip('删除操作被取消。');
			return;
		}
		AOS.ajax({
			url : 'messagesController.deleteMessages',
			params:{
	        msg_id: record.data.msg_id
			},
			ok : function(data) {
				AOS.tip(data.appmsg);
				AOS.get('_g_messages').getStore().reload();
			}
		});
	});
}

</script>