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
				<aos:iframe id="huyuantongji_chart"  src="${httpUrl}/http/do.jhtml?router=tongJiController.vouchers_chart&juid=${juid}"/>
			</aos:tab>
			<aos:tab title="统计列表" id="_tab_org">
				<aos:gridpanel id="_g_orders" url="tongJiController.vouchersStat" onrender="g_org_query" forceFit="true" features="summary">
					<aos:column type="rowno" />
					<aos:column header="日期" dataIndex="create_date"  width="120" />
					<aos:column header="发放总张数" dataIndex="num" width="100" summaryRenderer="function(){return  '<span style=color:red>发放总张数' + summary.num + ' </span>'}"/>
					<aos:column header="发放总金额" dataIndex="voucher_amt" width="100" summaryRenderer="function(){return  '<span style=color:red>发放总金额 ' + summary.voucher_amt + ' </span>'}"/>
					<aos:column header="已用张数" dataIndex="yiyong" width="100" summaryRenderer="function(){return  '<span style=color:red>已用张数 ' + summary.yiyong + ' </span>'}"/>
					<aos:column header="过期张数" dataIndex="gouqi" width="100" summaryRenderer="function(){return  '<span style=color:red>过期张数 ' + summary.gouqi + ' </span>'}"/>
				</aos:gridpanel>
			</aos:tab>

			
		</aos:tabpanel>
	</aos:viewport>
	<script type="text/javascript">
		//全量统计+表格查询
		var summary = {
				num: 0,
				voucher_amt: 0,
				yiyong: 0,
				gouqi:0
		}
		//加载部门结构表格数据
		function g_org_query() {
			var params = AOS.getValue('f_query');
			if(Ext.getCmp('f_query').getForm().isValid()){
				AOS.ajax({
					wait : false, //防止出现2个遮罩层。(ajax和表格load)
					params : params,
					url : 'tongJiController.vouchersStatQuerySummary',
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