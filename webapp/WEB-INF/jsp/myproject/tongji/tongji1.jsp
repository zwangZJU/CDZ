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
				<aos:iframe id="huyuantongji_chart"  src="${httpUrl}/http/do.jhtml?router=tongJiController.huyuantongji_chart&juid=${juid}"/>
			</aos:tab>
			<aos:tab title="统计列表" id="_tab_org">
				<aos:gridpanel id="_g_orders" url="tongJiController.listMemberssStat" onrender="g_org_query" forceFit="true" features="summary">
					<aos:column type="rowno" />
					<aos:column header="部门流水号" dataIndex="id_" width="150" hidden="true"/>
					<aos:column header="手机号" dataIndex="account_" width="100" summaryRenderer="function(){return  '<span style=color:red>新增会员数量 ' + summary.count + ' </span>'}"/>
					<aos:column header="押金" dataIndex="deposit_amt" width="80" />
					<aos:column header="金币" dataIndex="gold_coins"  width="80" />
					<aos:column header="姓名" dataIndex="user_name"  width="100"  />
					<aos:column header="身份证号" dataIndex="id_card" width="100" />
					<aos:column header="会员等级" dataIndex="grade_" rendererField="grade_" width="80" />
					<aos:column header="注册时间" dataIndex="create_time_" width="150"  type="date" format="Y-m-d H:i:s"/>
				</aos:gridpanel>
			</aos:tab>

			
		</aos:tabpanel>
	</aos:viewport>
	<script type="text/javascript">
		//全量统计+表格查询
		var summary = {
				count: 0
		}
		//加载部门结构表格数据
		function g_org_query() {
			var params = AOS.getValue('f_query');
			if(Ext.getCmp('f_query').getForm().isValid()){
				AOS.ajax({
					wait : false, //防止出现2个遮罩层。(ajax和表格load)
					params : params,
					url : 'tongJiController.listMemberssStatQuerySummary',
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