<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/common/tags.jsp"%>
<aos:html title="常用布局二" base="http" lib="ext">
<aos:body>
</aos:body>
</aos:html>

<aos:onready>
	<aos:viewport layout="border">
		<aos:formpanel id="f_query" layout="column" labelWidth="70" region="north" border="false">
			<aos:docked forceBoder="0 0 1 0">
				<aos:dockeditem xtype="tbtext" text="查询条件" />
			</aos:docked>
			<aos:datefield id="date_start" name="date_start" fieldLabel="统计时间" value="${date_start}" allowBlank="false"  columnWidth="0.25"/>
			<aos:datefield id="date_end" name="date_end" fieldLabel="至" value="${date_end}" allowBlank="false"  columnWidth="0.25"/>
			<aos:docked dock="bottom" ui="footer" margin="0 0 8 0">
				<aos:dockeditem xtype="tbfill" />
				<aos:dockeditem xtype="button" text="查询" onclick="g_org_query" icon="query.png" />
				<aos:dockeditem xtype="button" text="重置" onclick="#AOS.reset(f_query);" icon="refresh.png" />
				<aos:dockeditem xtype="tbfill" />
			</aos:docked>
		</aos:formpanel>

		<aos:tabpanel id="tabpanel" region="center" bodyBorder="0 0 0 0" >
			<aos:tab title="统计趋势图" >
				<aos:iframe id="huyuantongji_chart"  src="${httpUrl}/http/do.jhtml?router=tongJiController.order_chart&juid=${juid}"/>
			</aos:tab>
			<aos:tab title="统计列表" id="_tab_org">
				<aos:gridpanel id="_g_orders" url="tongJiController.chargingOrdersStat" onrender="g_org_query" forceFit="true" features="summary">
					<aos:column type="rowno" />
					<aos:column header="日期" dataIndex="create_date"  width="120" />
					<aos:column header="总订单数" dataIndex="o_num" width="100" summaryRenderer="function(){return  '<span style=color:red>总订单数 ' + summary.o_num + ' </span>'}"/>
					<aos:column header="待支付数" dataIndex="runing" width="100" summaryRenderer="function(){return  '<span style=color:red>待支付数 ' + summary.runing + ' </span>'}"/>
					<aos:column header="已支付数" dataIndex="ok" width="100" summaryRenderer="function(){return  '<span style=color:red>已支付数 ' + summary.ok + ' </span>'}"/>
					<aos:column header="取消订单数" dataIndex="cancel" width="100" summaryRenderer="function(){return  '<span style=color:red>取消订单数 ' + summary.cancel + ' </span>'}"/>
				</aos:gridpanel>
			</aos:tab>

			
		</aos:tabpanel>
	</aos:viewport>
	<script type="text/javascript">
		//全量统计+表格查询
		var summary = {
				o_num: 0,
				runing: 0,
				ok: 0,
				cancel: 0
		}
		//加载部门结构表格数据
		function g_org_query() {
			var params = AOS.getValue('f_query');
			if(Ext.getCmp('f_query').getForm().isValid()){
				AOS.ajax({
					wait : false, //防止出现2个遮罩层。(ajax和表格load)
					params : params,
					url : 'tongJiController.chargingOrdersStatQuerySummary',
					ok : function(data) {
						summary = data;
						_g_orders_store.getProxy().extraParams = params;
						_g_orders_store.loadPage(1);
					}
				});
				if(window.frames["huyuantongji_chart-frame"]){
					window.frames["huyuantongji_chart-frame"].test(params.date_start,params.date_end);
				}
			}
		}
		
		
	</script>
</aos:onready>

<script type="text/javascript">
	
</script>