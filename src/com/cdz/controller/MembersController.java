package controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import aos.framework.core.cache.CacheMasterDataService;
import aos.framework.core.id.AOSId;
import aos.framework.core.service.CDZBaseController;
import aos.framework.core.typewrap.Dto;
import aos.framework.core.typewrap.Dtos;
import aos.framework.core.utils.AOSCodec;
import aos.framework.core.utils.AOSCxt;
import aos.framework.core.utils.AOSJson;
import aos.framework.core.utils.AOSUtils;
import aos.framework.web.router.HttpModel;
import aos.system.common.utils.SystemCons;
import dao.ActivityDao;
import dao.MembersDao;
import dao.VoucherDao;
import po.ActivityPO;
import po.MembersPO;
import po.VoucherPO;
import utils.ExcelUtils;

/**
 * members管理
 * 
 * @author duanchongfeng
 * @date 2017-07-06 20:19:04
 *
 */
@Service
public class MembersController extends CDZBaseController {

	@Autowired
	private MembersDao membersDao;
	@Autowired
	private ActivityDao activityDao;
	
	@Autowired
	private VoucherDao voucherDao;
	
	@Autowired
	private CacheMasterDataService cacheMasterDataService;

	/**
	 * members管理页面初始化
	 * 
	 * @param httpModel
	 * @return
	 */
	public void init(HttpModel httpModel) {
		httpModel.setAttribute("juid", httpModel.getInDto().getString("juid"));
		httpModel.setViewPath("myproject/members.jsp");
	}

	/**
	 * 查询members列表
	 * 
	 * @param httpModel
	 * @return
	 */
	public void listMembers(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		List<Dto> membersDtos = sqlDao.list("Members.listMemberssPage", qDto);
		for(Dto dto:membersDtos){
			Object gold_coins_amt=sqlDao.selectOne("OrdersPay.gold_coins_amt", Dtos.newDto("user_id",dto.getString("id_")));
			dto.put("gold_coins", gold_coins_amt);
		}
		httpModel.setOutMsg(AOSJson.toGridJson(membersDtos, qDto.getPageTotal()));
	}
	/**
	 * 查询members信息
	 * 
	 * @param httpModel
	 */
	public void getMembers(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
	     MembersPO membersPO = membersDao.selectByKey(inDto.getString("id_")); 
		httpModel.setOutMsg(AOSJson.toJson(membersPO));
	}

	/**
	 * 保存members
	 * 
	 * @param httpModel
	 * @return
	 */
	 @Transactional
	public void saveMembers(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		MembersPO membersPO = new MembersPO();
		membersPO.copyProperties(inDto);
		membersPO.setId_(AOSId.appId(SystemCons.ID.SYSTEM));
		membersPO.setCreate_time_(AOSUtils.getDateTime());
		membersDao.insert(membersPO);
		httpModel.setOutMsg("新增成功。");
	}

	/**
	 * 修改members
	 * 
	 * @param httpModel
	 * @return
	 */
	 @Transactional
	public void updateMembers(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		MembersPO membersPO = new MembersPO();
		membersPO.copyProperties(inDto);
		membersDao.updateByKey(membersPO);
		httpModel.setOutMsg("修改成功。");
	}
	 @Transactional
	public void okCertMembers(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		MembersPO membersPO = new MembersPO();
		String id_=inDto.getString("id_");
		MembersPO membersPO_=membersDao.selectByKey(id_);
		if(membersPO_.getIs_cert().equals("1")){
			httpModel.setOutMsg("当前会员已认证通过，请勿重复认证。");
		}else if(AOSUtils.isEmpty(membersPO_.getUser_name())||AOSUtils.isEmpty(membersPO_.getId_card())||AOSUtils.isEmpty(membersPO_.getVehicle_license())||AOSUtils.isEmpty(membersPO_.getDriver_license())){
			httpModel.setOutMsg("认证信息不完整，操作失败。");
		}else{
			membersPO.setId_(id_);
			membersPO.setIs_cert("1");
			membersDao.updateByKey(membersPO);
			//认证送优惠券
			Dto pDto_=Dtos.newDto("is_del", "0");//
			pDto_.put("status_", "1");
			pDto_.put("type_", "5");
			List<ActivityPO> list=activityDao.list(pDto_);
			if(null!=list&&list.size()>0){
				ActivityPO activityPO=list.get(0);
				if(null!=activityPO){
					for(int i=0;i<activityPO.getAr_num().intValue();i++){
						VoucherPO voucherPO=new VoucherPO();
						voucherPO.setActivity_id(activityPO.getActivity_id());
						//voucherPO.setOrder_id(order_id);
						//voucherPO.setCond_value(activityRulePO.getCond_value());
						voucherPO.setIs_del("0");
						voucherPO.setDealer_id(membersPO.getId_());
						voucherPO.setDirection("0");
						voucherPO.setVoucher_amt(activityPO.getAmount());
						voucherPO.setVoucher_id(AOSId.appId(SystemCons.ID.SYSTEM));
						voucherPO.setCreate_date(AOSUtils.getDateTime());
						voucherPO.setEffec_date(AOSUtils.dateAdd(AOSUtils.getCurDayTimestamp(), Calendar.MONTH, Integer.valueOf(AOSCxt.getParam("voucherValidity"))));
						voucherDao.insert(voucherPO);
					}
				}
			}
			
			
			httpModel.setOutMsg("操作成功。");
		}
		
	}
	 @Transactional
	public void noCertMembers(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		MembersPO membersPO = new MembersPO();
		String id_=httpModel.getInDto().getString("id_");
		if(AOSUtils.isEmpty(inDto.getString("user_name"))||AOSUtils.isEmpty(inDto.getString("id_card"))||AOSUtils.isEmpty(inDto.getString("vehicle_license"))||AOSUtils.isEmpty(inDto.getString("driver_license"))){
			httpModel.setOutMsg("认证信息不完整，操作失败。");
		}else{
			membersPO.setId_(id_);
			membersPO.setIs_cert("-1");
			membersDao.updateByKey(membersPO);
			httpModel.setOutMsg("操作成功。");
		}
	}
	 
	 /**
	 * 重置用户密码
	 * 
	 * @param httpModel
	 */
	@Transactional
	public void resetPassword(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		String[] selectionIds = inDto.getRows();
		for (String id_ : selectionIds) {
			MembersPO membersPO = new MembersPO();
			membersPO.setId_(id_);
			membersPO.setPassword_(AOSCodec.password(inDto.getString("password_")));
			membersDao.updateByKey(membersPO);
		}
		httpModel.setOutMsg("用户密码重置成功。");
	}
	 
	/**
	 * 删除members
	 * 
	 * @param httpModel
	 */
	@Transactional
	public void deleteMembers(HttpModel httpModel) {
		String[] selectionIds = httpModel.getInDto().getRows();
		if(null!=selectionIds&&selectionIds.length>0){
			for (String id_ : selectionIds) {
				MembersPO membersPO = new MembersPO();
				membersPO.setId_(id_);
				membersPO.setIs_del_(SystemCons.IS.YES);
	            membersDao.updateByKey(membersPO); 
			}
		}else{
				String id_=httpModel.getInDto().getString("id_");
				MembersPO membersPO = new MembersPO();
				membersPO.setId_(id_);
				membersPO.setIs_del_(SystemCons.IS.YES);
	            membersDao.updateByKey(membersPO); 
			
		}
		httpModel.setOutMsg("删除成功。");
	}
	@Transactional
	public void stopMembers(HttpModel httpModel) {
		String[] selectionIds = httpModel.getInDto().getRows();
		if(null!=selectionIds&&selectionIds.length>0){
			for (String id_ : selectionIds) {
				MembersPO membersPO = new MembersPO();
				membersPO.setId_(id_);
				membersPO.setStatus_("2");
	            membersDao.updateByKey(membersPO); 
			}
		}else{
				String id_=httpModel.getInDto().getString("id_");
				MembersPO membersPO = new MembersPO();
				membersPO.setId_(id_);
				membersPO.setStatus_("2");
	            membersDao.updateByKey(membersPO); 
			
		}
		httpModel.setOutMsg("禁用成功。");
	}
	@Transactional
	public void okMembers(HttpModel httpModel) {
		String[] selectionIds = httpModel.getInDto().getRows();
		if(null!=selectionIds&&selectionIds.length>0){
			for (String id_ : selectionIds) {
				MembersPO membersPO = new MembersPO();
				membersPO.setId_(id_);
				membersPO.setStatus_("1");
	            membersDao.updateByKey(membersPO); 
			}
		}else{
				String id_=httpModel.getInDto().getString("id_");
				MembersPO membersPO = new MembersPO();
				membersPO.setId_(id_);
				membersPO.setStatus_("1");
	            membersDao.updateByKey(membersPO); 
			
		}
		httpModel.setOutMsg("启用成功。");
	}

	public void exportExcel(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		HttpServletResponse response = httpModel.getResponse();
		String filename = AOSUtils.encodeChineseDownloadFileName(httpModel.getRequest().getHeader("USER-AGENT"),
				"会员导出列表.xlsx");
		response.setHeader("Content-Disposition", "attachment; filename=" + filename + ";");
		String path = httpModel.getRequest().getServletContext().getRealPath("/");
		File templetFile = new File(path + "/templet/members.xlsx");
		BufferedInputStream in;
		ServletOutputStream os = null;
		try {
			in = new BufferedInputStream(new FileInputStream(templetFile));
			ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
			byte[] temp = new byte[1024];
			int size = 0;
			while ((size = in.read(temp)) != -1) {
				out.write(temp, 0, size);
			}
			in.close();
			os = response.getOutputStream();
			
			List<String[]> datas = new ArrayList<String[]>();
			/*String[] s = new String[127];
			for (int j = 0; j < 10; j++) {
				for (int i = 0; i < s.length; i++) {
					s[i] = i + "数据";
				}
				datas.add(s);
			}*/
			List<Dto> membersDtos = sqlDao.list("Members.listMemberss", qDto);
			for(Dto dto:membersDtos){
				String[] s = new String[15];
				s[0]=dto.getString("account_");
				s[1]=dto.getString("name_");
				s[2]=cacheMasterDataService.getDicDesc("user_status_", dto.getString("status_"));
				s[3]=dto.getString("create_time_");
				s[4]=dto.getString("login_time_");
				s[5]=dto.getString("gold_coins");
				s[6]=dto.getString("deposit_amt");
				s[7]=dto.getString("deposit_date");
				s[8]=cacheMasterDataService.getDicDesc("deposit_status", dto.getString("deposit_status"));
				s[9]=cacheMasterDataService.getDicDesc("grade_", dto.getString("grade_"));
				s[10]=cacheMasterDataService.getDicDesc("is_cert", dto.getString("is_cert"));
				s[11]=dto.getString("user_name");
				s[12]=dto.getString("id_card");
				s[13]=dto.getString("vehicle_license");
				s[14]=dto.getString("driver_license");
				datas.add(s);
			}
			ExcelUtils.exportExcel(1, templetFile, datas, os, 15);
			os.write(out.toByteArray());
			os.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
