<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/common/tags.jsp"%>

<aos:html title="charging_pile" base="http" lib="ext">

<aos:body>
</aos:body>
<aos:onready>
	<aos:viewport layout="fit">
		<aos:gridpanel id="_datagridpanel" url="deviceService.listDevice" onrender="_datagridpanel_query" onitemdblclick="_w_update_show"  forceFit="false">
			<aos:docked>
			    			 	<aos:dockeditem text="新增" tooltip="新增"  onclick="_w_add_show" icon="add.png"/>
							    			    <aos:dockeditem text="修改" tooltip="修改"  onclick="_w_update_show" icon="edit.png"/>
												<aos:dockeditem text="删除" tooltip="删除" onclick="_delete" icon="del.png" />
												<%-- 缺少相关函数，无法实现<aos:dockeditem text="导出" tooltip="导出" onclick="fn_exportexcel()" icon="icon70.png" /> --%>
								<aos:dockeditem xtype="tbseparator" />
				                                                                      			</aos:docked>
			<aos:column type="rowno" />
			<aos:selmodel type="checkbox" mode="multi" />
		 
						      			       <aos:column header="序号" hidden="true"  dataIndex="id_"   width="255"  />
			    						      			       <aos:column header="状态" dataIndex="status"   width="255" />
			    						      			       <aos:column header="布撤防" dataIndex="arrange_withdraw"   width="255" />
			    						      			       <aos:column header="设备编号" dataIndex="device_id"   width="255" />
			    						      			       <aos:column header="用户编号" dataIndex="user_id"   width="255" />
			    						      			       <aos:column header="用户名称" dataIndex="user_name"   width="255" />
			    						      			       <aos:column header="用户地址" dataIndex="user_address"   width="400" />
			    						      			       <aos:column header="分中心" dataIndex="sub_center"   width="255" />
			    						      			       <aos:column header="交叉路" dataIndex="cross_road"   width="255" />
			    						      			       <aos:column header="用户类型" dataIndex="user_type"   width="255" />
			    						      			       <aos:column header="用户类型2" dataIndex="user_type2"   width="255" />
			    						      			       <aos:column header="主机类型" dataIndex="host_type"   width="255" />
			    						      			       <aos:column header="视频联动" dataIndex="video_linkage"   width="255" />
			    						      			       <aos:column header="负责人" dataIndex="head"   width="255" />
			    						      			       <aos:column header="负责人电话" dataIndex="head_phone"   width="255" />
			    						      			       <aos:column header="测试间隔" dataIndex="test_period"   width="255" />
			    						      			       <aos:column header="缴费截止日期" dataIndex="pay_date"   width="160" />
			    						      			       <aos:column header="保修截止日期" dataIndex="guarantee_time"   width="160" />
			    						      			       <aos:column header="核查状态" dataIndex="check_status"   width="255" />
			    						      			       <aos:column header="欠费" dataIndex="arrearage"   width="255" />
			    						      			       <aos:column header="停机" dataIndex="shut_down"   width="255" />
			    						      			       <aos:column header="镇所" dataIndex="town"   width="255" />
			    						      			       <aos:column header="镇所电话" dataIndex="town_phone"   width="255" />
			    						      			       <aos:column header="所属派出所" dataIndex="police_station"   width="255" />
			    						      			       <aos:column header="网络" dataIndex="network"   width="400" />
			    						      			       <aos:column header="派出所电话" dataIndex="police_phone"   width="255" />
			    						      			       <aos:column header="主机位置" dataIndex="host_address"   width="255" />
			    						      			       <aos:column header="安装日期" dataIndex="insatll_date"   width="160" />
			    						      			       <aos:column header="42代码" dataIndex="code42"   width="255" />
			    						      			       <aos:column header="撤防时间" dataIndex="withdraw_date"   width="160" />
			    						      			       <aos:column header="布防时间" dataIndex="arrange_date"   width="160" />
			    						      			       <aos:column header="最后来信号时间" dataIndex="last_date"   width="160" />
			    						      			       <aos:column header="施工人员" dataIndex="builders"   width="255" />
			    						      			       <aos:column header="出警单位" dataIndex="police_unit"   width="255" />
			    						      			       <aos:column header="电话" dataIndex="phone"   width="255" />
			    						      			       <aos:column header="预定撤防时间" dataIndex="prewithdraw_date"   width="160" />
			    						      			       <aos:column header="预定布防时间" dataIndex="prearrange_date"   width="160" />
			    						      			       <aos:column header="组" dataIndex="group_"   width="255" />
			    						      			       <aos:column header="录入员" dataIndex="entry_clerk"   width="255" />
			    						      			       <aos:column header="巡检人员" dataIndex="inspection_staff"   width="255" />
			    						      			       <aos:column header="停机时间" dataIndex="downtime"   width="160" />
			    						      			       <aos:column header="电话1" dataIndex="phone1"   width="255" />
			    						      			       <aos:column header="产品型号" dataIndex="product_id"   width="255" />
			    						      			       <aos:column header="出厂日期" dataIndex="production_date"   width="255" />
			    						      			    <%--    <aos:column header="负责人产品型号" dataIndex="head_product_id"   width="255" /> --%>
			    						      			       <aos:column header="入网日期" dataIndex="net_date"   width="160" />
			    						      			       <aos:column header="通讯线路" dataIndex="communication_line"   width="255" />
			    						      			       <aos:column header="编号" dataIndex="users_id"   width="255" />
			    						      			       <aos:column header="姓名" dataIndex="users_name"   width="255" />
			    						      			       <aos:column header="身份" dataIndex="users_identity"   width="255" />
			    						      			       <aos:column header="备注" dataIndex="remarks"   width="500" />
			    						      			       <aos:column header="预定布撤防时间" dataIndex="arrangeandwithdraw_time"   width="700" />
			    						      			       <aos:column header="分站" dataIndex="network_setting_substation"   width="255" />
			    						      			       <aos:column header="名称" dataIndex="network_setting_name"   width="255" />
			    						      			       <aos:column header="类型" dataIndex="network_setting_type"   width="255" />
			    						      			       <aos:column header="模板文件" dataIndex="network_setting_template"   width="255" />
			    						      			       <aos:column header="备注" dataIndex="network_setting_remarks"   width="255" />
			    						      			       <aos:column header="接收号码" dataIndex="network_setting_number"   width="255" />
			    						      			       <aos:column header="防区使用者" dataIndex="network_setting_users"   width="255" />
			    						      			       <aos:column header="报警类型" dataIndex="alarm_type"   width="1000" />
			    						      			       <aos:column header="通信格式" dataIndex="communication_format"   width="255" />
			    						      			       <aos:column header="报警声音方案" dataIndex="alarm_sound"   width="255" />
			    						      			       <aos:column header="主机报警短信号码" dataIndex="host_alarm_sms"   width="255" />
			    						      			       <aos:column header="分局号" dataIndex="substation_number"   width="255" />
			    						      			       <aos:column header="停机时间" dataIndex="shutdown_time"   width="160" />
			    						      			       <aos:column header="暂停" dataIndex="pause"   width="255" />
			    						      			       <aos:column header="故障" dataIndex="breakdown"   width="255" />
			    						      			       <aos:column header="审查确认" dataIndex="review_confirm"   width="255" />
			    						      			       <aos:column header="备注" dataIndex="management_remarks"   width="500" />
			    			 		</aos:gridpanel>
	</aos:viewport>
	
	<aos:window id="_w_add_data" title="新增设备" resizable="true" maximized="true"   height="480"   layout="fit" modal="true" center="true" maximizable="true" closable="false" >
		
		<aos:formpanel id="_f_add"  layout="column" autoScroll="true" labelWidth="100">
          <aos:hiddenfield name="id_"/>
			<aos:fieldset>
				<aos:textfield name="user_id" fieldLabel="用户编号" maxLength="255" />
			</aos:fieldset>

			<aos:fieldset title="用户数据" columnWidth="1" labelWidth="100">
				<aos:fieldset columnWidth="0.33" labelWidth="70">


					<aos:textfield name="user_name" fieldLabel="用户名称" maxLength="255" />

					<aos:textareafield name="user_address" fieldLabel="用户地址" width="232" maxLength="255" />
					<aos:textfield name="cross_road" fieldLabel="交叉路" maxLength="255" />
					<aos:textfield name="phone" fieldLabel="电话" maxLength="255" />
					<aos:textfield name="host_type" fieldLabel="主机类型" maxLength="255" />
					<aos:textfield name="host_address" fieldLabel="主机位置" maxLength="255" />
                    <aos:textfield name="communication_format" fieldLabel="通信格式" maxLength="255" />
				
				
					<aos:textfield name="code42" fieldLabel="42代码" maxLength="255" />
					<aos:textfield name="test_period" fieldLabel="测试间隔" maxLength="255" />
					<aos:textfield name="alarm_sound" fieldLabel="报警声音方案" maxLength="255" />
					<aos:textfield name="network" fieldLabel="网络" maxLength="400" />
					<aos:textfield name="host_alarm_sms" fieldLabel="主机报警短信号码" maxLength="255" />







				</aos:fieldset>
				<aos:fieldset columnWidth="0.33" labelWidth="70">

					<aos:textfield name="police_station" fieldLabel="所属派出所" maxLength="255" />
					<aos:textfield name="police_phone" fieldLabel="派出所电话" maxLength="255" />
					<aos:textfield name="user_type" fieldLabel="用户类型" maxLength="255" />
					<aos:textfield name="user_type2" fieldLabel="用户类型2" maxLength="255" />
					<aos:textfield name="head" fieldLabel="负责人" maxLength="255" />
					<aos:textfield name="head_phone" fieldLabel="负责人电话" maxLength="255" />
					<%-- <aos:textfield name="head_product_id" fieldLabel="负责人产品型号" maxLength="255" /> --%>
					<aos:textfield name="group_" fieldLabel="组" maxLength="255" />
					<aos:datetimefield name="net_date" fieldLabel="入网日期"  editable="true" />
					<aos:datetimefield name="insatll_date" fieldLabel="安装日期"  editable="true" />
					<aos:textfield name="product_id" fieldLabel="产品型号" maxLength="255" />
					<aos:textfield name="production_date" fieldLabel="出厂日期" maxLength="255" />
					<aos:textfield name="builders" fieldLabel="施工人员" maxLength="255" />
					<aos:textfield name="inspection_staff" fieldLabel="巡检人员" maxLength="255" />
					<aos:textfield name="video_linkage" fieldLabel="视频联动" maxLength="255" />



				</aos:fieldset>
				<aos:fieldset columnWidth="0.33" labelWidth="70">


				</aos:fieldset>
				<aos:fieldset>
					<aos:textfield name="town" fieldLabel="镇所" maxLength="255" />
					<aos:textfield name="town_phone" fieldLabel="镇所电话" maxLength="255" />
					<aos:textfield name="police_unit" fieldLabel="出警单位" maxLength="255" />
					<aos:textfield name="substation_number" fieldLabel="分局号" maxLength="255" />
					<aos:textfield name="phone1" fieldLabel="电话1" maxLength="255" />
					
					<aos:textfield name="communication_line" fieldLabel="通讯线路" maxLength="255" />
				</aos:fieldset>
				<aos:fieldset>
					<aos:datetimefield name="guarantee_time" fieldLabel="保修截止日期"  editable="true" />
					<aos:textfield name="check_status" fieldLabel="核查状态" maxLength="255" />
					<aos:textfield name="arrearage" fieldLabel="欠费" maxLength="255" />
					<aos:textfield name="shut_down" fieldLabel="停机" maxLength="255" />
					<aos:textfield name="sub_center" fieldLabel="分中心"  maxLength="255" />
					<aos:textfield name="entry_clerk" fieldLabel="录入员" maxLength="255" />
					<aos:datetimefield name="shutdown_time" fieldLabel="停机时间"  editable="true" />
				    <aos:textfield name="pause" fieldLabel="暂停" maxLength="255" />
				    <aos:textfield name="breakdown" fieldLabel="故障" maxLength="255" />
				    <aos:textfield name="review_confirm" fieldLabel="审查确认" maxLength="255" />
				    <aos:datetimefield name="downtime" fieldLabel="停机时间"  editable="true" />
				    <aos:textareafield name="management_remarks" fieldLabel="备注"  width="260" maxLength="500" />

				</aos:fieldset>
				<aos:fieldset title="多余">
					<aos:textfield name="status" fieldLabel="状态" maxLength="255" />
					<aos:textfield name="arrange_withdraw" fieldLabel="布撤防" maxLength="255" />
					<aos:textfield name="device_id" fieldLabel="设备编号" maxLength="255" />
					<aos:datetimefield name="withdraw_date" fieldLabel="撤防时间"  editable="true" />
					<aos:datetimefield name="arrange_date" fieldLabel="布防时间"  editable="true" />
					<aos:datetimefield name="last_date" fieldLabel="最后来信号时间"  editable="true" />

				



				
					<aos:datetimefield name="prewithdraw_date" fieldLabel="预定撤防时间"  editable="true" />
					<aos:datetimefield name="prearrange_date" fieldLabel="预定布防时间"  editable="true" />
				</aos:fieldset>
			</aos:fieldset>


			<aos:fieldset title="使用者" columnWidth="1" labelWidth="70">
				<aos:textfield name="users_id" fieldLabel="编号" maxLength="255" />
				<aos:textfield name="users_name" fieldLabel="姓名" maxLength="255" />
				<aos:textfield name="users_identity" fieldLabel="身份" maxLength="255" />
				<aos:textareafield name="remarks" fieldLabel="备注" columnWidth="1"  maxLength="500"/>

			</aos:fieldset>




			<aos:fieldset title="预定布撤防时间" columnWidth="1" labelWidth="70">

				<aos:textfield name="arrangeandwithdraw_time" fieldLabel="预定布撤防时间" maxLength="700" />
				<aos:textfield name="arrangeandwithdraw_time" fieldLabel="周一" maxLength="700" />
                <aos:textfield name="arrangeandwithdraw_time" fieldLabel="周二" maxLength="700" />

			</aos:fieldset>



			<aos:fieldset title="网络设置" columnWidth="1" labelWidth="70">


				<aos:textfield name="network_setting_substation" fieldLabel="分站" maxLength="255" />
				<aos:textfield name="network_setting_name" fieldLabel="名称" maxLength="255" />
				<aos:textfield name="network_setting_type" fieldLabel="类型" maxLength="255" />
				<aos:textfield name="network_setting_template" fieldLabel="模板文件" maxLength="255" />
				<aos:textareafield name="network_setting_remarks" fieldLabel="备注" width="232" maxLength="255" />
				<aos:textfield name="network_setting_number" fieldLabel="接收号码" maxLength="255" />
				<aos:textfield name="network_setting_users" fieldLabel="防区使用者" maxLength="255" />
				<aos:textfield name="alarm_type" fieldLabel="报警类型" maxLength="1000" />
              
				
				

			</aos:fieldset>



		
		</aos:formpanel>
		<aos:docked dock="bottom" ui="footer">
			<aos:dockeditem xtype="tbfill" />
			<aos:dockeditem onclick="_f_add_save" text="保存" icon="ok.png" />
			<aos:dockeditem onclick="#_w_add_data.hide();" text="关闭" icon="close.png" />
		</aos:docked>
	</aos:window>
	
	<aos:window id="_w_update_data" title="修改设备" width="600"   height="400"  autoScroll="true">
			<aos:formpanel id="_f_update"   width="600-20"     layout="anchor" labelWidth="70">
              <aos:hiddenfield name="id_"/>
           	   	       	        <aos:textfield name="status" fieldLabel="状态"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="arrange_withdraw" fieldLabel="布撤防"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="device_id" fieldLabel="设备编号"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="user_id" fieldLabel="用户编号"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="user_name" fieldLabel="用户名称"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="user_address" fieldLabel="用户地址"  maxLength="400"    	         />
	      	    	        	   	       	        <aos:textfield name="sub_center" fieldLabel="分中心"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="cross_road" fieldLabel="交叉路"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="user_type" fieldLabel="用户类型"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="user_type2" fieldLabel="用户类型2"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="host_type" fieldLabel="主机类型"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="video_linkage" fieldLabel="视频联动"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="head" fieldLabel="负责人"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="head_phone" fieldLabel="负责人电话"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="test_period" fieldLabel="测试间隔"  maxLength="255"    	         />
	      	    	        	      	       <aos:datefield name="pay_date" fieldLabel="缴费截止日期"   	                 format="Y-m-d 00:00:00"     editable="true"/>
	    	        	      	       <aos:datefield name="guarantee_time" fieldLabel="保修截止日期"   	                 format="Y-m-d 00:00:00"     editable="true"/>
	    	        	   	       	        <aos:textfield name="check_status" fieldLabel="核查状态"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="arrearage" fieldLabel="欠费"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="shut_down" fieldLabel="停机"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="town" fieldLabel="镇所"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="town_phone" fieldLabel="镇所电话"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="police_station" fieldLabel="所属派出所"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="network" fieldLabel="网络"  maxLength="400"    	         />
	      	    	        	   	       	        <aos:textfield name="police_phone" fieldLabel="派出所电话"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="host_address" fieldLabel="主机位置"  maxLength="255"    	         />
	      	    	        	      	       <aos:datefield name="insatll_date" fieldLabel="安装日期"   	                 format="Y-m-d 00:00:00"     editable="true"/>
	    	        	   	       	        <aos:textfield name="code42" fieldLabel="42代码"  maxLength="255"    	         />
	      	    	        	      	       <aos:datefield name="withdraw_date" fieldLabel="撤防时间"   	                 format="Y-m-d 00:00:00"     editable="true"/>
	    	        	      	       <aos:datefield name="arrange_date" fieldLabel="布防时间"   	                 format="Y-m-d 00:00:00"     editable="true"/>
	    	        	      	       <aos:datefield name="last_date" fieldLabel="最后来信号时间"   	                 format="Y-m-d 00:00:00"     editable="true"/>
	    	        	   	       	        <aos:textfield name="builders" fieldLabel="施工人员"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="police_unit" fieldLabel="出警单位"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="phone" fieldLabel="电话"  maxLength="255"    	         />
	      	    	        	      	       <aos:datefield name="prewithdraw_date" fieldLabel="预定撤防时间"   	                 format="Y-m-d 00:00:00"     editable="true"/>
	    	        	      	       <aos:datefield name="prearrange_date" fieldLabel="预定布防时间"   	                 format="Y-m-d 00:00:00"     editable="true"/>
	    	        	   	       	        <aos:textfield name="group_" fieldLabel="组"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="entry_clerk" fieldLabel="录入员"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="inspection_staff" fieldLabel="巡检人员"  maxLength="255"    	         />
	      	    	        	      	       <aos:datefield name="downtime" fieldLabel="停机时间"   	                 format="Y-m-d 00:00:00"     editable="true"/>
	    	        	   	       	        <aos:textfield name="phone1" fieldLabel="电话1"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="product_id" fieldLabel="产品型号"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="production_date" fieldLabel="出厂日期"  maxLength="255"    	         />
	      	    	        	   	       	   <%--      <aos:textfield name="head_product_id" fieldLabel="负责人产品型号"  maxLength="255"    	         /> --%>
	      	    	        	      	       <aos:datefield name="net_date" fieldLabel="入网日期"   	                 format="Y-m-d 00:00:00"     editable="true"/>
	    	        	   	       	        <aos:textfield name="communication_line" fieldLabel="通讯线路"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="users_id" fieldLabel="编号"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="users_name" fieldLabel="姓名"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="users_identity" fieldLabel="身份"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="remarks" fieldLabel="备注"  maxLength="500"    	         />
	      	    	        	   	       	        <aos:textfield name="arrangeandwithdraw_time" fieldLabel="预定布撤防时间"  maxLength="700"    	         />
	      	    	        	   	       	        <aos:textfield name="network_setting_substation" fieldLabel="分站"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="network_setting_name" fieldLabel="名称"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="network_setting_type" fieldLabel="类型"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="network_setting_template" fieldLabel="模板文件"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="network_setting_remarks" fieldLabel="备注"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="network_setting_number" fieldLabel="接收号码"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="network_setting_users" fieldLabel="防区使用者"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="alarm_type" fieldLabel="报警类型"  maxLength="1000"    	         />
	      	    	        	   	       	        <aos:textfield name="communication_format" fieldLabel="通信格式"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="alarm_sound" fieldLabel="报警声音方案"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="host_alarm_sms" fieldLabel="主机报警短信号码"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="substation_number" fieldLabel="分局号"  maxLength="255"    	         />
	      	    	        	      	       <aos:datefield name="shutdown_time" fieldLabel="停机时间"   	                 format="Y-m-d 00:00:00"     editable="true"/>
	    	        	   	       	        <aos:textfield name="pause" fieldLabel="暂停"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="breakdown" fieldLabel="故障"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="review_confirm" fieldLabel="审查确认"  maxLength="255"    	         />
	      	    	        	   	       	        <aos:textfield name="management_remarks" fieldLabel="备注"  maxLength="500"    	         />
	      	    	 		</aos:formpanel>
		<aos:docked dock="bottom" ui="footer">
			<aos:dockeditem xtype="tbfill" />
			<aos:dockeditem onclick="_f_update_save" text="保存" icon="ok.png" />
			<aos:dockeditem onclick="#_w_update_data.hide();" text="关闭" icon="close.png" />
		</aos:docked>
	</aos:window>
	
	<script type="text/javascript">
		function _datagridpanel_query() {
			var params = {
			                                                                           			  
			};
			_datagridpanel_store.getProxy().extraParams = params;
			_datagridpanel_store.loadPage(1);
		}
		function getCount() {
			var count = _datagridpanel_store.getCount();
			console.log(count);
		}
		//弹出新增窗口
		function _w_add_show(){
			AOS.reset(_f_add);
			_w_add_data.show();
		}
		//新增 设备
		function _f_add_save(){
				AOS.ajax({
				forms : _f_add,
				url : 'deviceService.saveDevice',
				ok : function(data) {
					if (data.appcode === -1) {
                        AOS.err(data.appmsg);
                    } else {
                    	_w_add_data.hide();
    					_datagridpanel_store.reload();
    					_datagridpanel_store();
    					AOS.tip(data.appmsg);
                    }
				}
			});
		}	
		
		//弹出修改窗口 设备
		function _w_update_show(){
			AOS.reset(_f_update);
			var record = AOS.selectone(_datagridpanel);
			if(record){
				_w_update_data.show();
				_f_update.loadRecord(record);
			}
		}
	    
	   //修改   设备
		function _f_update_save(){
			AOS.ajax({
				forms : _f_update,
				url : 'deviceService.updateDevice',
				ok : function(data) {
					if (data.appcode === -1) {
                        AOS.err(data.appmsg);
                    } else {
    					AOS.tip(data.appmsg);	
    					_w_update_data.hide();
    					_datagridpanel_store.reload();
                    }
				}
			}); 
		}
	   
        //删除 设备
	 	function _delete(){
	 				var selection = AOS.selection(_datagridpanel, 'id_');
	 				if(AOS.empty(selection)){
	 					AOS.tip('删除前请先选中数据。');
	 					return;
	 				}
	 				var rows = AOS.rows(_datagridpanel);
	 				var msg =  AOS.merge('确认要删除选中的{0}项目吗？', rows);
	 				AOS.confirm(msg, function(btn){
	 					if(btn === 'cancel'){
	 						AOS.tip('删除操作被取消。');
	 						return;
	 					}
	 					AOS.ajax({
	 						url : 'deviceService.deleteDevice',
	 						params:{
	 							aos_rows_: selection
	 						},
	 						ok : function(data) {
	 							if(data.appcode === -1){
	 								AOS.err(data.appmsg);
	 								return ;
	 							}
	 							AOS.tip(data.appmsg);
	 							_datagridpanel_store.reload();
	 						}
	 					});
	 				});
	 			}
        function _exportexcel(){
        	AOS.file('exportexcel.jhtml');
        }
	</script>
</aos:onready>
</aos:html>