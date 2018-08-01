<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/common/tags.jsp"%>

<aos:html title="文章表" base="http" lib="ext">
<aos:body>
</aos:body>
</aos:html>
<aos:onready>
	<aos:viewport layout="border">
		<aos:formpanel id="_f_query" layout="column" labelWidth="70" header="false" region="north" border="false">
			<aos:docked forceBoder="0 0 1 0">
				<aos:dockeditem xtype="tbtext" text="查询条件" />
			</aos:docked>
			
			<aos:textfield name="title" fieldLabel="标题" columnWidth="0.25" />
			<%--<aos:combobox name="card_type_" fieldLabel="卡类型" dicField="card_type_" columnWidth="0.25" />
			--%>
			<aos:docked dock="bottom" ui="footer" margin="0 0 8 0">
				<aos:dockeditem xtype="tbfill" />
				<aos:dockeditem xtype="button" text="查询" onclick="_g_article_query" icon="query.png" />
				<aos:dockeditem xtype="button" text="重置" onclick="AOS.reset(_f_query);" icon="refresh.png" />
				<aos:dockeditem xtype="tbfill" />
			</aos:docked>
		</aos:formpanel>

		<aos:gridpanel id="_g_article" url="articleController.listArticle" onrender="_g_article_query"
			 region="center">
			<aos:menu>
				<%--<aos:menuitem text="新增" onclick="#_w_article.show();AOS.reset(_f_article);" icon="add.png" />--%>
				<aos:menuitem text="修改" onclick="edit_windows()" icon="edit.png" />
				<aos:menuitem text="图表" onclick="edit_windows1()" icon="edit.png" />
				<%--<aos:menuitem text="删除" onclick="fn_del()" icon="del.png" />--%>
				<aos:menuitem xtype="menuseparator" />
				<aos:menuitem text="刷新" onclick="#_g_article_store.reload();" icon="refresh.png" />
			</aos:menu>
			<aos:docked forceBoder="1 0 1 0">
				<aos:dockeditem xtype="tbtext" text="文章表信息" />
				<aos:dockeditem xtype="tbseparator" />
				<%--<aos:dockeditem text="新增" icon="add.png" onclick="#_w_article.show();AOS.reset(_f_article);" />--%>
				<aos:dockeditem text="修改" icon="edit.png" onclick="edit_windows()" />
				<%--<aos:dockeditem text="批量删除" icon="del.png" onclick="_g_article_delAll" />--%>
			</aos:docked>
			<aos:selmodel type="checkbox" mode="multi" />
			<aos:column type="rowno" />
			<aos:column header="主键" dataIndex="article_id" hidden="true" />	
			<aos:column header="标题" dataIndex="title" width="100" />	
			<aos:column header="发布时间" dataIndex="publish_date" type="date" format="Y-m-d H:i:s" width="100" />	
			<aos:column header="创建时间" dataIndex="create_date" type="date" format="Y-m-d H:i:s" width="100" />	
	
			<%-- 数据字典、时间例子
			<aos:column header="卡类型" dataIndex="card_type_" rendererField="card_type_" width="60" />
			<aos:column header="出生日期" dataIndex="birthday_" type="date" format="Y-m-d H:i:s" width="100" />
			--%>
			<aos:column header="修改" rendererFn="fn_button_render" align="center" fixedWidth="50" />
			<%--<aos:column header="删除" rendererFn="fn_button_del" align="center" fixedWidth="50" />--%>
		</aos:gridpanel>

		<%-- 新增窗口 --%>
		<aos:window id="_w_article" title="新增账户">
			<aos:formpanel id="_f_article" width="450" layout="anchor" labelWidth="70">
			<aos:textfield name="title" fieldLabel="标题" allowBlank="false" />
			<aos:textfield name="type_" fieldLabel="类型" allowBlank="false" />
			<aos:textfield name="content" fieldLabel="内容" allowBlank="false" />
			<aos:textfield name="status_" fieldLabel="状态，0：新建，1：发布" allowBlank="false" />
			<aos:datefield name="publish_date" fieldLabel="发布时间" editable="true" allowBlank="false" />	
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
				<aos:dockeditem onclick="_f_article_save" text="保存" icon="ok.png" />
				<aos:dockeditem onclick="#_w_article.hide();" text="关闭" icon="close.png" />
			</aos:docked>
		</aos:window>

		<%-- 修改窗口 --%>
		<aos:window id="_w_article2" title="修改账户" onshow="_w_article2_onshow">
			<aos:formpanel id="_f_article2" width="450" layout="anchor" labelWidth="70">
				<%-- 更新时的隐藏变量(主键) --%>
				
			<aos:hiddenfield name="article_id" fieldLabel="主键" />
			<aos:textfield name="title" fieldLabel="标题" allowBlank="false" />
			<aos:textfield name="type_" fieldLabel="类型" allowBlank="false" />
			<aos:textfield name="content" fieldLabel="内容" allowBlank="false" />
			<aos:textfield name="status_" fieldLabel="状态，0：新建，1：发布" allowBlank="false" />
			<aos:datefield name="publish_date" fieldLabel="发布时间" editable="true" allowBlank="false" />	
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
				<aos:dockeditem onclick="_f_article2_update" text="保存" icon="ok.png" />
				<aos:dockeditem onclick="#_w_article2.hide();" text="关闭" icon="close.png" />
			</aos:docked>
		</aos:window>

	</aos:viewport>

	<script type="text/javascript">
	
		//加载表格数据
		function _g_article_query() {
			var params = AOS.getValue('_f_query');
			_g_article_store.getProxy().extraParams = params;
			_g_article_store.loadPage(1);
		}
		
		//新增账户保存
		function _f_article_save(){
				AOS.ajax({
					forms : _f_article,
					url : 'articleController.saveArticle',
					ok : function(data) {
						 AOS.tip(data.appmsg);
						_g_article_store.reload();
						_w_article.hide();
					}
			});
		}
		//窗口弹出事件监听
		function _w_article_onshow() {
			var record = AOS.selectone(_g_article);
            AOS.ajax({
            	params : {
            			        article_id: record.data.article_id
            	},
                url: 'articleController.getArticle',
                ok: function (data) {
                	_f_article2.form.setValues(data);
                }
            });
		}
		//监听修改窗口弹出
		function _w_article2_onshow(){
			//这里演示的是直接从表格行中加载数据，也可以发一个ajax请求查询数据(见misc1.jsp有相关用法)
			var record = AOS.selectone(_g_article, true);
			_f_article2.loadRecord(record);
		}
		
		//修改账户保存
		function _f_article2_update(){
				AOS.ajax({
					forms : _f_article2,
					url : 'articleController.updateArticle',
					ok : function(data) {
						AOS.tip(data.appmsg);
						_g_article_store.reload();
						_w_article2.hide();
					}
			});
		}
		
		 //更新
	 	function _g_article_update(){
	 		
				if(AOS.selectone(_g_article)){
					//_w_article2_onshow();
					AOS.get('_w_article2').show();
				}
		}
		function edit_windows(){
	 		
			if(AOS.selectone(_g_article)){
				add_windows(1)
			}
		}
function edit_windows1(){
	 		
				add_windows1(1)
		}
        //删除账户信息
	 	function _g_article_delAll(){
	        	var selection = AOS.selection(_g_article, 'article_id');
				
				if(AOS.empty(selection)){
					AOS.tip('删除前请先选中数据。');
					return;
				}
				var rows = AOS.rows(_g_article);
				var msg =  AOS.merge('确认要删除选中的{0}条记录吗？', rows);
				AOS.confirm(msg, function(btn){
					if(btn === 'cancel'){
						AOS.tip('删除操作被取消。');
						return;
					}
					AOS.ajax({
						url : 'articleController.deleteArticle',
						params:{
							aos_rows_: selection
						},
						ok : function(data) {
							AOS.tip(data.appmsg);
							_g_article_store.reload();
						}
					});
				});
			}
		
		//按钮列转换
		function fn_button_render(value, metaData, record) {
			return '<input type="button" value="修改" class="cbtn_danger" onclick="_w_article2_show();" />';
		}
		
		//按钮列转换
		function fn_button_del(value, metaData, record) {
			return '<input type="button" value="删除" class="cbtn_danger" onclick="fn_del();" />';
		}
		function add_windows(flag) {
			 Ext.widget('window', {
		       title: '系统文章',
		       width: '100%',
		       height: '100%',
		       bodyStyle:"background-color:#fff;padding:5px 5px 0",
		       autoShow: true,
		       autoScroll: true,
		       listeners: {
		       	show:function(){
		       		if(flag&&flag==1){
		       				var record = AOS.selectone(AOS.get('_g_article'));
			       			AOS.ajax({
			                   	params : { article_id: record.data.article_id},
			                       url: 'articleController.getArticle',
			                       ok: function (data) {
			                    	   var form=Ext.getCmp('articleForm').getForm();
			                    	   form.setValues(data);
			                       }
			                   });
		       			
		       		}
		               
		       }},
		       items: [
					        {
					            xtype: "form",
					            id:'articleForm',
					            defaultType: 'textfield',
					            defaults: {
					                anchor: '100%',
					            },
					            fieldDefaults: {
					                labelWidth: 80,
					                labelAlign: "left",
					                flex: 1,
					                margin: 5
					            },
					            items: [
					                {
					                    xtype: "container",
					                    layout: "hbox",
					                    items: [
					                        { fieldLabel: '内容',name: "content",id: "content",xtype: 'ueditor',height: 300,ueditorConfig: { } }
					                       
					                    ]
					                }
					            ]
					        }
					    ],
		       buttons: [{
		           text: '保存',
		           handler: function () { 
		               	var this_=this;
		               	var form=Ext.getCmp('articleForm').getForm();
		               	var url='articleController.saveArticle';
		               	if(flag&&flag==1){
		               		url='articleController.updateArticle';
		               	}
		               	if(form.isValid()){
		               		var parms=form.getValues();
		               		AOS.ajax({
		                		params : parms,
		    					url : url,
		    					ok : function(data) {
		    						 AOS.tip(data.appmsg);
		    						 _g_article_store.reload();
		    						this_.up('window').close();
		    					}
		                	});
		               	}
		               	
		           	
		           }
		       },{
		           text:'取消',
		           handler:function(){
		               this.up('window').close();
		           }
		       }]
		   });
			 
		}
		function add_windows1() {
			 Ext.widget('window', {
		       title: '系统文章',
		       width: '100%',
		       height: '100%',
		       bodyStyle:"background-color:#fff;padding:5px 5px 0",
		       autoShow: true,
		       autoScroll: true,
		       listeners: {
		       	show:function(){
		       		
		               
		       }},
			  items: [{  title: '弹出的窗口', 
                  header:false, 
                  html : '<iframe style="border-top-width: 0px; border-left-width: 0px; border-bottom-width: 0px; width:100%; height: 100%; border-right-width: 0px" src=http://localhost:9090/xhm/http/do.jhtml?router=articleController.test&juid=${juid} frameborder="0" width="100%" scrolling="no" height="100%"></iframe>', 
                  border:false 
              }]
		   });
			 
		}
	</script>
</aos:onready>

<script type="text/javascript">

function _w_article2_show(){
	add_windows(1);
}

function add_windows(flag) {
	 Ext.widget('window', {
      title: '系统文章',
      width: '100%',
      height: '100%',
      bodyStyle:"background-color:#fff;padding:5px 5px 0",
      autoShow: true,
      autoScroll: true,
      listeners: {
      	show:function(){
      		if(flag&&flag==1){
      				var record = AOS.selectone(AOS.get('_g_article'));
	       			AOS.ajax({
	                   	params : { article_id: record.data.article_id},
	                       url: 'articleController.getArticle',
	                       ok: function (data) {
	                    	   var form=Ext.getCmp('articleForm').getForm();
	                    	   form.setValues(data);
	                       }
	                   });
      			
      		}
              
      }},
      items: [
			        {
			            xtype: "form",
			            id:'articleForm',
			            defaultType: 'textfield',
			            defaults: {
			                anchor: '100%',
			            },
			            fieldDefaults: {
			                labelWidth: 80,
			                labelAlign: "left",
			                flex: 1,
			                margin: 5
			            },
			            items: [
			                    { xtype: "hiddenfield", name: "article_id"},
			                {
			                    xtype: "container",
			                    layout: "hbox",
			                    items: [
			                        { fieldLabel: '内容',name: "content",id: "content",xtype: 'ueditor',height: 300,ueditorConfig: { } }
			                       
			                    ]
			                }
			            ]
			        }
			    ],
      buttons: [{
          text: '保存',
          handler: function () { 
              	var this_=this;
              	var form=Ext.getCmp('articleForm').getForm();
              	var url='articleController.saveArticle';
              	if(flag&&flag==1){
              		url='articleController.updateArticle';
              	}
              	if(form.isValid()){
              		var parms=form.getValues();
              		AOS.ajax({
               		params : parms,
   					url : url,
   					ok : function(data) {
   						 AOS.tip(data.appmsg);
   						AOS.get('_g_article').getStore().reload();
   						this_.up('window').close();
   					}
               	});
              	}
              	
          	
          }
      },{
          text:'取消',
          handler:function(){
              this.up('window').close();
          }
      }]
  });
	 
}


function fn_del(){
	//由于是列按钮对应的函数，下面获取对象的的写法和onready代码段里的js有些不同
	var record = AOS.selectone(AOS.get('_g_article'));
	var msg =  AOS.merge('确认要删除当前记录吗？', "");
	AOS.confirm(msg, function(btn){
		if(btn === 'cancel'){
			AOS.tip('删除操作被取消。');
			return;
		}
		AOS.ajax({
			url : 'articleController.deleteArticle',
			params:{
	        article_id: record.data.article_id
			},
			ok : function(data) {
				AOS.tip(data.appmsg);
				AOS.get('_g_article').getStore().reload();
			}
		});
	});
}

</script>