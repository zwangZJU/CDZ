<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/common/tags.jsp"%>

<aos:html title="首页" base="http" lib="ext">
<aos:body backGround="true">
</aos:body>
</aos:html>

<aos:onready>
	<aos:viewport  layout="fit" autoScroll="true">
		<aos:panel border="false" >
			<aos:layout type="vbox" align="stretch" />
			<aos:panel layout="fit">
				<aos:formpanel id="f_query1" layout="column" labelWidth="70" header="false" border="false">
					<aos:docked forceBoder="1 0 1 0">
						<aos:dockeditem xtype="tbtext" text="会员数量统计" />
					</aos:docked>
					<aos:textfield  fieldLabel="总数" value="${hy_total}"  readOnly="true" columnWidth="0.2" />
					<aos:textfield  fieldLabel="白金会员" value="${hy_bjj}" readOnly="true" columnWidth="0.2" />
					<aos:textfield  fieldLabel="金牌会员" value="${hy_jj}" readOnly="true" columnWidth="0.2" />
					<aos:textfield  fieldLabel="银牌会员" value="${hy_yj}" readOnly="true" columnWidth="0.2" />
					<aos:textfield  fieldLabel="普通会员" value="${hy_pt}" readOnly="true" columnWidth="0.2" />
				</aos:formpanel>
			</aos:panel>
			<aos:panel layout="fit">
				<aos:formpanel id="f_query" layout="column" labelWidth="70" header="false" border="false">
					<aos:docked forceBoder="1 0 1 0">
						<aos:dockeditem xtype="tbtext" text="充电桩数量统计" />
					</aos:docked>
					<aos:textfield  fieldLabel="总数" value="${cp_total}"  readOnly="true" columnWidth="0.25" />
					<aos:textfield  fieldLabel="可用" value="${keyong}" readOnly="true" columnWidth="0.25" />
					<aos:textfield  fieldLabel="占用" value="${zhanyong}" readOnly="true" columnWidth="0.25" />
					<aos:textfield  fieldLabel="故障数" value="${guzhang}" readOnly="true" columnWidth="0.25" />
				</aos:formpanel>
			</aos:panel>
			<aos:panel >
			    <%-- 这是一个水平盒子布局的例子 --%>
				<aos:layout type="hbox" align="stretch" />
				<aos:formpanel layout="column" labelWidth="70" header="false" border="true" flex="1" margin="5" padding="5 0 0 5">
					<aos:docked forceBoder="1 1 0 1">
						<aos:dockeditem xtype="tbfill" />
						<aos:dockeditem xtype="tbtext" text="订单数量总计" />
						<aos:dockeditem xtype="tbfill" />
					</aos:docked>
					<aos:textfield  fieldLabel="订单总数" value="${t_o_total}" readOnly="true" columnWidth="1" />
					<aos:textfield  fieldLabel="待付款" value="${t_o_dfk}" readOnly="true" columnWidth="1" />
					<aos:textfield  fieldLabel="已付款" value="${t_o_yfk}" readOnly="true" columnWidth="1" />
				</aos:formpanel>
				<aos:formpanel layout="column" labelWidth="70" header="false" border="true" flex="1" margin="5" padding="5 0 0 0">
					<aos:docked forceBoder="1 1 0 1">
						<aos:dockeditem xtype="tbfill" />
						<aos:dockeditem xtype="tbtext" text="今天[${day}]订单数量" />
						<aos:dockeditem xtype="tbfill" />
					</aos:docked>
					<aos:textfield  fieldLabel="订单总数" value="${d_o_total}" readOnly="true" columnWidth="1" />
					<aos:textfield  fieldLabel="待付款" value="${d_o_dfk}" readOnly="true" columnWidth="1" />
					<aos:textfield  fieldLabel="已付款" value="${d_o_yfk}" readOnly="true" columnWidth="1" />
				</aos:formpanel>
				<aos:formpanel layout="column" labelWidth="70" header="false" border="true" flex="1" margin="5" padding="5 0 0 0">
					<aos:docked forceBoder="1 1 0 1">
						<aos:dockeditem xtype="tbfill" />
						<aos:dockeditem xtype="tbtext" text="本周[${zhou_start}至${zhou_end}]订单数量" />
						<aos:dockeditem xtype="tbfill" />
					</aos:docked>
					<aos:textfield  fieldLabel="订单总数" value="${z_o_total}" readOnly="true" columnWidth="1" />
					<aos:textfield  fieldLabel="待付款" value="${z_o_dfk}" readOnly="true" columnWidth="1" />
					<aos:textfield  fieldLabel="已付款" value="${z_o_yfk}" readOnly="true" columnWidth="1" />
				</aos:formpanel>
				<aos:formpanel layout="column" labelWidth="70" header="false" border="true" flex="1" margin="5" padding="5 5 0 0">
					<aos:docked forceBoder="1 1 0 1">
						<aos:dockeditem xtype="tbfill" />
						<aos:dockeditem xtype="tbtext" text="本月[${month_start}至${month_end}]订单数量" />
						<aos:dockeditem xtype="tbfill" />
					</aos:docked>
					<aos:textfield  fieldLabel="订单总数" value="${m_o_total}" readOnly="true" columnWidth="1" />
					<aos:textfield  fieldLabel="待付款" value="${m_o_dfk}" readOnly="true" columnWidth="1" />
					<aos:textfield  fieldLabel="已付款" value="${m_o_yfk}" readOnly="true" columnWidth="1" />
				</aos:formpanel>
			</aos:panel>
		</aos:panel>
		
	</aos:viewport>
	
</aos:onready>