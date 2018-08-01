<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/common/tags.jsp"%>

<aos:html title="common_comment" base="http" lib="ext">
<aos:body>
<div id="_div_photo" class="x-hidden" align="center">
        <img id="_img_photo" class="app_cursor_pointer" src="" width="220"
            height="250">
</div>
</aos:body>
</aos:html>
<aos:onready>
	<aos:viewport layout="border">
		<aos:formpanel id="_f_query" layout="column" labelWidth="70" header="false" region="north" border="false">
			<aos:docked forceBoder="0 0 1 0">
				<aos:dockeditem xtype="tbtext" text="查询条件" />
			</aos:docked>
			<%--
			<aos:textfield name="name_" fieldLabel="持卡人姓名" columnWidth="0.25" />
			<aos:combobox name="card_type_" fieldLabel="卡类型" dicField="card_type_" columnWidth="0.25" />
			--%>
			<aos:textfield name="account_" fieldLabel="会员手机号" columnWidth="0.25" />
			<aos:textfield name="comment_" fieldLabel="投诉内容" columnWidth="0.25" />
			<aos:datefield id="date_start" name="date_start" fieldLabel="投诉时间"  columnWidth="0.25"/>
			<aos:datefield id="date_end" name="date_end" fieldLabel="至"  columnWidth="0.25"/>
			<aos:docked dock="bottom" ui="footer" margin="0 0 8 0">
				<aos:dockeditem xtype="tbfill" />
				<aos:dockeditem xtype="button" text="查询" onclick="_g_commonComment_query" icon="query.png" />
				<aos:dockeditem xtype="button" text="重置" onclick="AOS.reset(_f_query);" icon="refresh.png" />
				<aos:dockeditem xtype="tbfill" />
			</aos:docked>
		</aos:formpanel>

		<aos:gridpanel id="_g_commonComment" url="commonCommentController.listCommonComment" onrender="_g_commonComment_query"
			onitemdblclick="#_w_commonComment2.show();" region="center">
			<aos:menu>
				<%--<aos:menuitem text="新增" onclick="#_w_commonComment.show();AOS.reset(_f_commonComment);" icon="add.png" />
				<aos:menuitem text="修改" onclick="_g_commonComment_update" icon="edit.png" />--%>
				<aos:menuitem text="删除" onclick="fn_del()" icon="del.png" />
				<aos:menuitem xtype="menuseparator" />
				<aos:menuitem text="刷新" onclick="#_g_commonComment_store.reload();" icon="refresh.png" />
			</aos:menu>
			<aos:docked forceBoder="1 0 1 0">
				<aos:dockeditem xtype="tbtext" text="会员投诉建议信息列表" />
				<aos:dockeditem xtype="tbseparator" />
				<%--<aos:dockeditem text="新增" icon="add.png" onclick="#_w_commonComment.show();AOS.reset(_f_commonComment);" />
				<aos:dockeditem text="修改" icon="edit.png" onclick="_g_commonComment_update" />--%>
				<aos:dockeditem text="批量删除" icon="del.png" onclick="_g_commonComment_delAll" />
			</aos:docked>
			<aos:selmodel type="checkbox" mode="multi" />
			<aos:column type="rowno" />
			<aos:column header="主键" dataIndex="cc_id" hidden="true" />	
			<aos:column header="会员手机号" dataIndex="b1_account_" width="100" celltip="true"/>	
			<aos:column header="投诉内容" dataIndex="comment_" width="150" celltip="true" />	
			<aos:column header="投诉时间" dataIndex="create_date" type="date" format="Y-m-d H:i:s" width="100" />	
			<%--<aos:column header="发布时间" dataIndex="publish_date" type="date" format="Y-m-d H:i:s" width="100" />	
			<aos:column header="状态,0:新建，1：发布，2：不发布" dataIndex="status_" width="100" />	
			<aos:column header="发布人ID" dataIndex="oper_id" width="100" />--%>	
			<aos:column header="图片" dataIndex="img_url1" width="100" rendererFn="fn_image_render"/>	
			<%--<aos:column header="图片2" dataIndex="img_url2" width="100" />	
			<aos:column header="图片3" dataIndex="img_url3" width="100" />	
			<aos:column header="图片4" dataIndex="img_url4" width="100" />	
			<aos:column header="图片5" dataIndex="img_url5" width="100" />	
			<aos:column header="图片6" dataIndex="img_url6" width="100" />	--%>
	
			<%-- 数据字典、时间例子
			<aos:column header="卡类型" dataIndex="card_type_" rendererField="card_type_" width="60" />
			<aos:column header="出生日期" dataIndex="birthday_" type="date" format="Y-m-d H:i:s" width="100" />
			<aos:column header="修改" rendererFn="fn_button_render" align="center" fixedWidth="50" />--%>
			<aos:column header="删除" rendererFn="fn_button_del" align="center" fixedWidth="50" />
		</aos:gridpanel>

		<%-- 新增窗口 --%>
		<aos:window id="_w_commonComment" title="新增账户">
			<aos:formpanel id="_f_commonComment" width="450" layout="anchor" labelWidth="70">
			<aos:textfield name="user_id" fieldLabel="用户ID" allowBlank="false" />
			<aos:textfield name="comment_" fieldLabel="投诉内容" allowBlank="false" />
			<aos:textfield name="status_" fieldLabel="状态,0:新建，1：发布，2：不发布" allowBlank="false" />
			<aos:datefield name="create_date" fieldLabel="创建时间" editable="true" allowBlank="false" />	
			<aos:datefield name="publish_date" fieldLabel="发布时间" editable="true" allowBlank="false" />	
			<aos:textfield name="oper_id" fieldLabel="发布人ID" allowBlank="false" />
			<aos:textfield name="img_url1" fieldLabel="图片地址1" allowBlank="false" />
			<aos:textfield name="img_url2" fieldLabel="图片地址2" allowBlank="false" />
			<aos:textfield name="img_url3" fieldLabel="图片地址3" allowBlank="false" />
			<aos:textfield name="img_url4" fieldLabel="图片地址4" allowBlank="false" />
			<aos:textfield name="img_url5" fieldLabel="图片地址5" allowBlank="false" />
			<aos:textfield name="img_url6" fieldLabel="图片地址6" allowBlank="false" />
			<aos:textfield name="is_del" fieldLabel="是否删除，0：未删除，1：删除" allowBlank="false" />
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
				<aos:dockeditem onclick="_f_commonComment_save" text="保存" icon="ok.png" />
				<aos:dockeditem onclick="#_w_commonComment.hide();" text="关闭" icon="close.png" />
			</aos:docked>
		</aos:window>

		<%-- 修改窗口 --%>
		<aos:window id="_w_commonComment2" title="查看详细" onshow="_w_commonComment_onshow">
			<aos:formpanel id="_f_commonComment2" width="450" layout="anchor" labelWidth="70">
				<%-- 更新时的隐藏变量(主键) --%>
				
			<aos:hiddenfield name="cc_id" fieldLabel="主键" />
			
			<aos:textfield name="comment_" fieldLabel="建议内容" allowBlank="false" />
			<%--
			<aos:textfield name="user_id" fieldLabel="用户ID" allowBlank="false" />
			<aos:textfield name="status_" fieldLabel="状态,0:新建，1：发布，2：不发布" allowBlank="false" />
			<aos:datefield name="create_date" fieldLabel="创建时间" editable="true" allowBlank="false" />	
			<aos:datefield name="publish_date" fieldLabel="发布时间" editable="true" allowBlank="false" />	
			<aos:textfield name="oper_id" fieldLabel="发布人ID" allowBlank="false" /> --%>
			<aos:fieldset title="图片" labelWidth="70" columnWidth="0.35" contentEl="_div_photo" height="300" />
			<%--<aos:textfield name="img_url1" fieldLabel="图片地址1" allowBlank="false" />
			<aos:textfield name="img_url2" fieldLabel="图片地址2" allowBlank="false" />
			<aos:textfield name="img_url3" fieldLabel="图片地址3" allowBlank="false" />
			<aos:textfield name="img_url4" fieldLabel="图片地址4" allowBlank="false" />
			<aos:textfield name="img_url5" fieldLabel="图片地址5" allowBlank="false" />
			<aos:textfield name="img_url6" fieldLabel="图片地址6" allowBlank="false" />
			<aos:textfield name="is_del" fieldLabel="是否删除，0：未删除，1：删除" allowBlank="false" />--%>
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
				<%--<aos:dockeditem onclick="_f_commonComment2_update" text="保存" icon="ok.png" />--%>
				<aos:dockeditem onclick="#_w_commonComment2.hide();" text="关闭" icon="close.png" />
			</aos:docked>
		</aos:window>

	</aos:viewport>

	<script type="text/javascript">
		function fn_image_render(value, metaData, record) {
			value="${cxt}/myupload"+value;
			return '<img  src="'+value+'" width="200" height="200">';
		}
		//加载表格数据
		function _g_commonComment_query() {
			var params = AOS.getValue('_f_query');
			_g_commonComment_store.getProxy().extraParams = params;
			_g_commonComment_store.loadPage(1);
		}
		
		//新增账户保存
		function _f_commonComment_save(){
				AOS.ajax({
					forms : _f_commonComment,
					url : 'commonCommentController.saveCommonComment',
					ok : function(data) {
						 AOS.tip(data.appmsg);
						_g_commonComment_store.reload();
						_w_commonComment.hide();
					}
			});
		}
		//窗口弹出事件监听
		function _w_commonComment_onshow() {
			var record = AOS.selectone(_g_commonComment);
            AOS.ajax({
            	params : {
            			        cc_id: record.data.cc_id
            	},
                url: 'commonCommentController.getCommonComment',
                ok: function (data) {
                	_f_commonComment2.form.setValues(data);
                	document.getElementById("_img_photo").src="${cxt}/myupload"+data.img_url1;
                }
            });
		}
		//监听修改窗口弹出
		function _w_commonComment2_onshow(){
			//这里演示的是直接从表格行中加载数据，也可以发一个ajax请求查询数据(见misc1.jsp有相关用法)
			var record = AOS.selectone(_g_commonComment, true);
			_f_commonComment2.loadRecord(record);
		}
		
		//修改账户保存
		function _f_commonComment2_update(){
				AOS.ajax({
					forms : _f_commonComment2,
					url : 'commonCommentController.updateCommonComment',
					ok : function(data) {
						AOS.tip(data.appmsg);
						_g_commonComment_store.reload();
						_w_commonComment2.hide();
					}
			});
		}
		
		 //更新
	 	function _g_commonComment_update(){
	 		
				if(AOS.selectone(_g_commonComment)){
					//_w_commonComment2_onshow();
					AOS.get('_w_commonComment2').show();
				}
				
				
		}
		
        //删除账户信息
	 	function _g_commonComment_delAll(){
	        	var selection = AOS.selection(_g_commonComment, 'cc_id');
				
				if(AOS.empty(selection)){
					AOS.tip('删除前请先选中数据。');
					return;
				}
				var rows = AOS.rows(_g_commonComment);
				var msg =  AOS.merge('确认要删除选中的{0}条记录吗？', rows);
				AOS.confirm(msg, function(btn){
					if(btn === 'cancel'){
						AOS.tip('删除操作被取消。');
						return;
					}
					AOS.ajax({
						url : 'commonCommentController.deleteCommonComment',
						params:{
							aos_rows_: selection
						},
						ok : function(data) {
							AOS.tip(data.appmsg);
							_g_commonComment_store.reload();
						}
					});
				});
			}
		
		//按钮列转换
		function fn_button_render(value, metaData, record) {
			return '<input type="button" value="修改" class="cbtn_danger" onclick="_w_commonComment2_show();" />';
		}
		
		//按钮列转换
		function fn_button_del(value, metaData, record) {
			return '<input type="button" value="删除" class="cbtn_danger" onclick="fn_del();" />';
		}
		
	</script>
</aos:onready>

<script type="text/javascript">

function _w_commonComment2_show(){
	AOS.get('_w_commonComment2').show();
}




function fn_del(){
	//由于是列按钮对应的函数，下面获取对象的的写法和onready代码段里的js有些不同
	var record = AOS.selectone(AOS.get('_g_commonComment'));
	var msg =  AOS.merge('确认要删除当前记录吗？', "");
	AOS.confirm(msg, function(btn){
		if(btn === 'cancel'){
			AOS.tip('删除操作被取消。');
			return;
		}
		AOS.ajax({
			url : 'commonCommentController.deleteCommonComment',
			params:{
	        cc_id: record.data.cc_id
			},
			ok : function(data) {
				AOS.tip(data.appmsg);
				AOS.get('_g_commonComment').getStore().reload();
			}
		});
	});
}

</script>