package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import aos.framework.core.id.AOSId;
import aos.framework.core.service.CDZBaseController;
import aos.framework.core.typewrap.Dto;
import aos.framework.core.typewrap.Dtos;
import aos.framework.core.utils.AOSCxt;
import aos.framework.core.utils.AOSJson;
import aos.framework.core.utils.AOSUtils;
import aos.framework.web.router.HttpModel;
import aos.system.common.utils.SystemCons;
import dao.MessagesDao;
import po.MessagesPO;
import utils.JiguangPush;

/**
 * messages管理
 * 
 * @author duanchongfeng
 * @date 2017-06-13 20:40:40
 *
 */
@Service
public class MessagesController extends CDZBaseController {

	@Autowired
	private MessagesDao messagesDao;

	/**
	 * messages管理页面初始化
	 * 
	 * @param httpModel
	 * @return
	 */
	public void init(HttpModel httpModel) {
		httpModel.setViewPath("myproject/messages.jsp");
	}

	/**
	 * 查询messages列表
	 * 
	 * @param httpModel
	 * @return
	 */
	public void listMessages(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		List<Dto> messagesDtos = sqlDao.list("Messages.listMessagessPage", qDto);
		httpModel.setOutMsg(AOSJson.toGridJson(messagesDtos, qDto.getPageTotal()));
	}
	/**
	 * 查询messages信息
	 * 
	 * @param httpModel
	 */
	public void getMessages(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
	     MessagesPO messagesPO = messagesDao.selectByKey(inDto.getString("msg_id")); 
		httpModel.setOutMsg(AOSJson.toJson(messagesPO));
	}

	/**
	 * 保存messages
	 * 
	 * @param httpModel
	 * @return
	 */
	 @Transactional
	public void saveMessages(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		MessagesPO messagesPO = new MessagesPO();
		messagesPO.copyProperties(inDto);
		messagesPO.setMsg_id(AOSId.appId(SystemCons.ID.SYSTEM));
		messagesPO.setCreate_date(AOSUtils.getDateTime());
		messagesDao.insert(messagesPO);
		
		this.sendMsg(messagesPO);
		
		
		httpModel.setOutMsg("推送消息成功。");
	}
	public void sendMsg(MessagesPO messagesPO){
		JiguangPush jiguangPush=new JiguangPush();
		String pushUrl=AOSCxt.getParam("pushUrl");
		String sales_masterSecret=AOSCxt.getParam("sales_masterSecret");
		String sales_appKey=AOSCxt.getParam("sales_appKey");
		List<Dto> listSales = sqlDao.list("Messages.listUsers", Dtos.newDto());
		for(Dto dto:listSales){
			System.out.println(dto.getString("registration_id")+":::::registration_id");
			if(AOSUtils.isNotEmpty(dto.getString("registration_id"))){
				jiguangPush.sendMsg(dto.getString("registration_id"), messagesPO.getMsg_content(), sales_appKey, sales_masterSecret, pushUrl);
			}
		}
			
			
	}
	/**
	 * 修改messages
	 * 
	 * @param httpModel
	 * @return
	 */
	 @Transactional
	public void updateMessages(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		MessagesPO messagesPO = new MessagesPO();
		messagesPO.copyProperties(inDto);
		messagesDao.updateByKey(messagesPO);
		this.sendMsg(messagesPO);
		httpModel.setOutMsg("推送消息成功。");
	}

	/**
	 * 删除messages
	 * 
	 * @param httpModel
	 */
	@Transactional
	public void deleteMessages(HttpModel httpModel) {
		String[] selectionIds = httpModel.getInDto().getRows();
		if(null!=selectionIds&&selectionIds.length>0){
			for (String id_ : selectionIds) {
				MessagesPO messagesPO = new MessagesPO();
				messagesPO.setMsg_id(id_);
				messagesPO.setIs_del(SystemCons.IS.YES);
	            messagesDao.updateByKey(messagesPO); 
			}
		}else{
				String msg_id=httpModel.getInDto().getString("msg_id");
				MessagesPO messagesPO = new MessagesPO();
				messagesPO.setMsg_id(msg_id);
				messagesPO.setIs_del(SystemCons.IS.YES);
	            messagesDao.updateByKey(messagesPO); 
			
		}
		httpModel.setOutMsg("删除成功。");
	}

	

}
