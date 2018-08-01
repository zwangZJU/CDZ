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
			    <aos:formpanel  title="hshhshhs" >
			        <aos:textfield name="user_name" fieldLabel="用户名称" maxLength="255" />

					<aos:textareafield name="user_address" fieldLabel="用户地址" width="232" maxLength="255" />
					<aos:textfield name="cross_road" fieldLabel="交叉路" maxLength="255" />
					<aos:textfield name="host_phone" fieldLabel="主机电话" maxLength="255" />
					<aos:textfield name="host_type" fieldLabel="主机类型" maxLength="255" />
					<aos:textfield name="host_address" fieldLabel="主机位置" maxLength="255" />
                    <aos:textfield name="communication_format" fieldLabel="通信格式" maxLength="255" />
				
				
			    </aos:formpanel>
			    
			      
				
			</aos:tab>
			<aos:tab title="统计列表" id="_tab_org">
				<aos:gridpanel id="_g_orders" url="tongJiController.chargingOrdersSalesStat" onrender="g_org_query" forceFit="true" features="summary">
					<aos:column type="rowno" />
					<aos:column header="日期" dataIndex="create_date"  width="120" />
					<aos:column header="总金额" dataIndex="total_amt" width="100" summaryRenderer="function(){return  '<span style=color:red>总金额 ' + summary.total_amt + ' </span>'}"/>
					<aos:column header="抵扣金额" dataIndex="deduction_amt" width="100" summaryRenderer="function(){return  '<span style=color:red>抵扣金额 ' + summary.deduction_amt + ' </span>'}"/>
					<aos:column header="实收金额" dataIndex="real_amt" width="100" summaryRenderer="function(){return  '<span style=color:red>实收金额 ' + summary.real_amt + ' </span>'}"/>
				</aos:gridpanel>
			</aos:tab>

			
		</aos:tabpanel>
	</aos:viewport>
	<script type="text/javascript">
		//全量统计+表格查询
		var summary = {
				total_amt: 0,
				runing: 0,
				real_amt: 0
		}
		//加载部门结构表格数据
		function g_org_query() {
			var params = AOS.getValue('f_query');
			if(Ext.getCmp('f_query').getForm().isValid()){
				AOS.ajax({
					wait : false, //防止出现2个遮罩层。(ajax和表格load)
					params : params,
					url : 'tongJiController.chargingOrdersSalesStatQuerySummary',
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