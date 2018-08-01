<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/common/tags.jsp"%>

<aos:html title="广告表" base="http" lib="ext">
<aos:body>
<div id="_div_photo" class="x-hidden" align="center">
        <img id="_img_photo" class="app_cursor_pointer" src="" width="200"
            height="200">
</div>
</aos:body>
</aos:html>
<aos:onready>
	<aos:viewport layout="border">
		<aos:formpanel id="_f_query" layout="column" labelWidth="70" header="false" region="north" border="false">
			<aos:docked forceBoder="0 0 1 0">
				<aos:dockeditem xtype="tbtext" text="查询条件" />
			</aos:docked>
			<aos:textfield name="name" fieldLabel="名称 " columnWidth="0.25" />
			<aos:combobox name="status" fieldLabel="状态" dicField="advert_status" columnWidth="0.25" />
			<%--
			<aos:combobox name="card_type_" fieldLabel="卡类型" dicField="card_type_" columnWidth="0.25" />
			--%>
			<aos:docked dock="bottom" ui="footer" margin="0 0 8 0">
				<aos:dockeditem xtype="tbfill" />
				<aos:dockeditem xtype="button" text="查询" onclick="_g_advert_query" icon="query.png" />
				<aos:dockeditem xtype="button" text="重置" onclick="AOS.reset(_f_query);" icon="refresh.png" />
				<aos:dockeditem xtype="tbfill" />
			</aos:docked>
		</aos:formpanel>

		<aos:gridpanel id="_g_advert" url="advertController.listAdvert" onrender="_g_advert_query"
			onitemdblclick="#_w_advert2.show();" region="center">
			<aos:menu>
				<aos:menuitem text="新增" onclick="#_w_advert.show();AOS.reset(_f_advert);" icon="add.png" />
				<aos:menuitem text="修改" onclick="_g_advert_update" icon="edit.png" />
				<aos:menuitem text="删除" onclick="fn_del()" icon="del.png" />
				<aos:menuitem xtype="menuseparator" />
				<aos:menuitem text="刷新" onclick="#_g_advert_store.reload();" icon="refresh.png" />
			</aos:menu>
			<aos:docked forceBoder="1 0 1 0">
				<aos:dockeditem xtype="tbtext" text="广告信息列表" />
				<aos:dockeditem xtype="tbseparator" />
				<aos:dockeditem text="新增" icon="add.png" onclick="#_w_advert.show();AOS.reset(_f_advert);" />
				<aos:dockeditem text="修改" icon="edit.png" onclick="_g_advert_update" />
				<aos:dockeditem text="批量删除" icon="del.png" onclick="_g_advert_delAll" />
			</aos:docked>
			<aos:selmodel type="checkbox" mode="multi" />
			<aos:column type="rowno" />
			<aos:column header="主键" dataIndex="advert_id" hidden="true" />	
			<aos:column header="名称" dataIndex="name" width="100" celltip="true"/>	
			<aos:column header="状态" dataIndex="status" rendererField="advert_status" width="60" />
			<aos:column header="图片" dataIndex="img_url" width="100" rendererFn="fn_image_render"/>
			<aos:column header="排序" dataIndex="sort" width="100" />	
			<aos:column header="创建时间" dataIndex="create_date" type="date" format="Y-m-d H:i:s" width="100" />	
	
			<%-- 数据字典、时间例子
			
			<aos:column header="出生日期" dataIndex="birthday_" type="date" format="Y-m-d H:i:s" width="100" />
			--%>
			<aos:column header="修改" rendererFn="fn_button_render" align="center" fixedWidth="50" />
			<aos:column header="删除" rendererFn="fn_button_del" align="center" fixedWidth="50" />
		</aos:gridpanel>

		<%-- 新增窗口 --%>
		<aos:window id="_w_advert" title="新增广告">
			<aos:formpanel id="_f_advert" width="450" layout="anchor" labelWidth="90" fileUpload="true">
			<aos:textfield name="name" fieldLabel="名称" allowBlank="false" />
			<aos:combobox name="status" fieldLabel="状态" dicField="advert_status" value="0" allowBlank="false" />
			<aos:filefield name="img_url" fieldLabel="图片"  allowBlank="false"  />
			<aos:numberfield name="sort" minValue="1" maxValue="999" fieldLabel="排序" value="1" allowBlank="false" />
			</aos:formpanel>
			<aos:docked dock="bottom" ui="footer">
				<aos:dockeditem xtype="tbfill" />
				<aos:dockeditem onclick="_f_advert_save" text="保存" icon="ok.png" />
				<aos:dockeditem onclick="#_w_advert.hide();" text="关闭" icon="close.png" />
			</aos:docked>
		</aos:window>

		<%-- 修改窗口 --%>
		<aos:window id="_w_advert2" title="修改广告" onshow="_w_advert_onshow">
			<aos:formpanel id="_f_advert2" width="450" layout="anchor" labelWidth="90" fileUpload="true">
				<%-- 更新时的隐藏变量(主键) --%>
				
			<aos:hiddenfield name="advert_id" fieldLabel="主键" />
			<aos:textfield name="name" fieldLabel="名称" allowBlank="false" />
			<aos:combobox name="status" fieldLabel="状态" dicField="advert_status" value="0" allowBlank="false" />
			<aos:filefield name="img_url" fieldLabel="图片"  allowBlank="true"  />
			<aos:fieldset title="图片" labelWidth="70" columnWidth="0.35" contentEl="_div_photo" height="210" />
			<aos:numberfield name="sort" minValue="1" maxValue="999" fieldLabel="排序" allowBlank="false" />
			</aos:formpanel>
			<aos:docked dock="bottom" ui="footer">
				<aos:dockeditem xtype="tbfill" />
				<aos:dockeditem onclick="_f_advert2_update" text="保存" icon="ok.png" />
				<aos:dockeditem onclick="#_w_advert2.hide();" text="关闭" icon="close.png" />
			</aos:docked>
		</aos:window>

	</aos:viewport>

	<script type="text/javascript">
	
		//加载表格数据
		function _g_advert_query() {
			var params = AOS.getValue('_f_query');
			_g_advert_store.getProxy().extraParams = params;
			_g_advert_store.loadPage(1);
		}
		
		//新增账户保存
		function _f_advert_save(){
			Ext.getCmp('_f_advert').getForm().submit({       
	            url:'${cxt}/http/do.jhtml?router=advertController.saveAdvert&juid=${juid}', 
	            method:'post',	
	            success:function(form, action){   
	            	Ext.Msg.alert('成功信息',action.result.appmsg);  
	               	_g_advert_store.reload();
					_w_advert.hide();     
	            },
	            failure:function(form, action){       
	            	 Ext.Msg.alert('失败信息', action.result.appmsg); 
	              
	             }   
	  
	          });
		}
		//窗口弹出事件监听
		function _w_advert_onshow() {
			var record = AOS.selectone(_g_advert);
            AOS.ajax({
            	params : {
            			        advert_id: record.data.advert_id
            	},
                url: 'advertController.getAdvert',
                ok: function (data) {
                	_f_advert2.form.setValues(data);
                	document.getElementById("_img_photo").src="${cxt}/myupload"+data.img_url;
                }
            });
		}
		//监听修改窗口弹出
		function _w_advert2_onshow(){
			//这里演示的是直接从表格行中加载数据，也可以发一个ajax请求查询数据(见misc1.jsp有相关用法)
			var record = AOS.selectone(_g_advert, true);
			_f_advert2.loadRecord(record);
			 
		}
		
		//修改账户保存
		function _f_advert2_update(){
			Ext.getCmp('_f_advert2').getForm().submit({       
	            url:'${cxt}/http/do.jhtml?router=advertController.updateAdvert&juid=${juid}', 
	            method:'post',	
	            success:function(form, action){   
	            	Ext.Msg.alert('成功信息',action.result.appmsg);  
	               	_g_advert_store.reload();
	               	_w_advert2.hide();     
	            },
	            failure:function(form, action){       
	            	 Ext.Msg.alert('失败信息', action.result.appmsg); 
	              
	             }   
	  
	          });
		}
		
		 //更新
	 	function _g_advert_update(){
	 		
				if(AOS.selectone(_g_advert)){
					//_w_advert2_onshow();
					AOS.get('_w_advert2').show();
				}
				
				
		}
		
        //删除账户信息
	 	function _g_advert_delAll(){
	        	var selection = AOS.selection(_g_advert, 'advert_id');
				
				if(AOS.empty(selection)){
					AOS.tip('删除前请先选中数据。');
					return;
				}
				var rows = AOS.rows(_g_advert);
				var msg =  AOS.merge('确认要删除选中的{0}条记录吗？', rows);
				AOS.confirm(msg, function(btn){
					if(btn === 'cancel'){
						AOS.tip('删除操作被取消。');
						return;
					}
					AOS.ajax({
						url : 'advertController.deleteAdvert',
						params:{
							aos_rows_: selection
						},
						ok : function(data) {
							AOS.tip(data.appmsg);
							_g_advert_store.reload();
						}
					});
				});
			}
		
		//按钮列转换
		function fn_button_render(value, metaData, record) {
			return '<input type="button" value="修改" class="cbtn_danger" onclick="_w_advert2_show();" />';
		}
		
		//按钮列转换
		function fn_button_del(value, metaData, record) {
			return '<input type="button" value="删除" class="cbtn_danger" onclick="fn_del();" />';
		}
		
	</script>
	function fn_image_render(value, metaData, record) {
			value="${cxt}/myupload"+value;
			return '<img  src="'+value+'" width="200" height="200">';
		}
</aos:onready>

<script type="text/javascript">

function _w_advert2_show(){
	AOS.get('_w_advert2').show();
}




function fn_del(){
	//由于是列按钮对应的函数，下面获取对象的的写法和onready代码段里的js有些不同
	var record = AOS.selectone(AOS.get('_g_advert'));
	var msg =  AOS.merge('确认要删除当前记录吗？', "");
	AOS.confirm(msg, function(btn){
		if(btn === 'cancel'){
			AOS.tip('删除操作被取消。');
			return;
		}
		AOS.ajax({
			url : 'advertController.deleteAdvert',
			params:{
	        advert_id: record.data.advert_id
			},
			ok : function(data) {
				AOS.tip(data.appmsg);
				AOS.get('_g_advert').getStore().reload();
			}
		});
	});
}

</script>