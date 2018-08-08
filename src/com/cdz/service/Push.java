package service;

import java.util.ArrayList;
import java.util.List;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.gexin.rp.sdk.template.style.Style0;

public class Push{
	// 采用"Java SDK 快速入门"， "第二步 获取访问凭证 "中获得的应用配置，用户可以自行替换
	private static String appId = "feD8QIf2zT60AVmLLDruf4";
	private static String appKey = "XezI8cUkHn5oguIW9NSmT8";
	private static String masterSecret = "tWxdN7DDvo5CDYjy6g7eL";

	static String CID = "54a82355b8988f1f3622361a7d234d6d";
	static String CID1 = "54a82355b8988f1f3622361a7d234d6d";
	static String CID2 = "";
	// 别名推送方式
	static String Alias = "18392888103";
	static String host = "http://sdk.open.api.igexin.com/apiex.htm";


	public static void pushToSingle() throws Exception {
		IGtPush push = new IGtPush(host, appKey, masterSecret);
		NotificationTemplate template = notificationTemplate(appId, appKey);
		/* TransmissionTemplate template = transmissionTemplate(); */
		/* NotyPopLoadTemplate template = notyPopLoadTemplate(); */
		SingleMessage message = new SingleMessage();
		message.setOffline(true);
		// 离线有效时间，单位为毫秒，可选
		message.setOfflineExpireTime(24 * 3600 * 1000);
		message.setData(template);
		// 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
		message.setPushNetWorkType(0);
		Target target = new Target();
		target.setAppId(appId);
		/* target.setClientId(CID); */
		target.setAlias(Alias);
		IPushResult ret = null;
		try {
			ret = push.pushMessageToSingle(message, target);
		} catch (RequestException e) {
			e.printStackTrace();
			ret = push.pushMessageToSingle(message, target, e.getRequestId());
		}
		if (ret != null) {
			System.out.println(ret.getResponse().toString());
		} else {
			System.out.println("服务器响应异常");
		}
	}

	public static void pushToGroup() throws Exception {
		System.setProperty("gexin_pushList_needDetails", "true");
		IGtPush push = new IGtPush(host, appKey, masterSecret);
		NotificationTemplate template = notificationTemplate(appId, appKey);
		ListMessage message = new ListMessage();
		message.setOffline(true);
		// 离线有效时间，单位为毫秒，可选
		message.setOfflineExpireTime(24 * 3600 * 1000);
		message.setData(template);
		List targets = new ArrayList();
		Target target1 = new Target();
		Target target2 = new Target();
		target1.setAppId(appId);
		target1.setClientId(CID1);
		target2.setAppId(appId);
		target2.setClientId(CID2);
		targets.add(target1);
		targets.add(target2);
		String taskId = push.getContentId(message);
		IPushResult ret = push.pushMessageToList(taskId, targets);
		System.out.println(ret.getResponse().toString());
		// 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
		message.setPushNetWorkType(0);


	}

	public static void pushToApp() throws Exception {
		IGtPush push = new IGtPush(host, appKey, masterSecret);
		NotificationTemplate template = notificationTemplate(appId, appKey);
		/* LinkTemplate template = linkTemplate(); */
		AppMessage message = new AppMessage();
		message.setOffline(true);
		// 离线有效时间，单位为毫秒，可选
		message.setOfflineExpireTime(24 * 3600 * 1000);
		message.setData(template);
		// 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
		message.setPushNetWorkType(0);
		List<String> appIdList = new ArrayList<String>();
		appIdList.add(appId);
		message.setAppIdList(appIdList);
		IPushResult ret = null;
		try {
			ret = push.pushMessageToApp(message);
		} catch (RequestException e) {
			e.printStackTrace();
			ret = push.pushMessageToApp(message);
		}
		if (ret != null) {
			System.out.println(ret.getResponse().toString());
		} else {
			System.out.println("服务器响应异常");
		}
	}

	public static NotificationTemplate notificationTemplate(String appId, String appkey) {
		NotificationTemplate template = new NotificationTemplate();
		// 设置APPID与APPKEY
		template.setAppId(appId);
		template.setAppkey(appkey);
		// 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
		template.setTransmissionType(1);
		template.setTransmissionContent("请输入您要透传的内容");
		// 设置定时展示时间
		// template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");

		Style0 style = new Style0();
		// 设置通知栏标题与内容
		style.setTitle("请输入通知栏标题");
		style.setText("请输入通知栏内容");
		// 配置通知栏图标
		style.setLogo("icon.png");// 即使不能使用某些功能，也不能删除，否则不能推送。
		// 配置通知栏网络图标
		style.setLogoUrl("");
		// 设置通知是否响铃，震动，或者可清除
		style.setRing(true);
		style.setVibrate(true);
		style.setClearable(true);
		template.setStyle(style);

		return template;
	}

	public static LinkTemplate linkTemplate() {
		LinkTemplate template = new LinkTemplate();
		// 设置APPID与APPKEY
		template.setAppId(appId);
		template.setAppkey(appKey);

		Style0 style = new Style0();
		// 设置通知栏标题与内容
		style.setTitle("请输入通知栏标题");
		style.setText("请输入通知栏内容");
		// 配置通知栏图标
		style.setLogo("push.png");
		// 配置通知栏网络图标
		style.setLogoUrl("");
		// 设置通知是否响铃，震动，或者可清除
		style.setRing(true);
		style.setVibrate(true);
		style.setClearable(true);
		template.setStyle(style);

		// 设置打开的网址地址
		template.setUrl("http://xueshu.baidu.com/");
		// 即使不能使用某些功能，也不能删除，否则不能推送。比如网址，不能为空*****************************************


		return template;
	}

	public static TransmissionTemplate transmissionTemplate() {
		TransmissionTemplate template = new TransmissionTemplate();
		template.setAppId(appId);
		template.setAppkey(appKey);
		// 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
		template.setTransmissionType(1);
		template.setTransmissionContent("请输入需要透传的内容");
		// 设置定时展示时间
		// template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");
		return template;
	}

	/*
	 * public static NotyPopLoadTemplate notyPopLoadTemplate() { NotyPopLoadTemplate
	 * template = new NotyPopLoadTemplate(); // 设置APPID与APPKEY
	 * template.setAppId(appId); template.setAppkey(appKey);
	 * 
	 * Style0 style = new Style0(); // 设置通知栏标题与内容 style.setTitle("请输入通知栏标题");
	 * style.setText("请输入通知栏内容"); // 配置通知栏图标 style.setLogo(""); // 配置通知栏网络图标
	 * style.setLogoUrl(""); // 设置通知是否响铃，震动，或者可清除 style.setRing(true);
	 * style.setVibrate(true); style.setClearable(true); template.setStyle(style);
	 * 
	 * // 设置弹框标题与内容 template.setPopTitle("智慧安防2.0"); template.setPopContent("弹框内容");
	 * // 设置弹框显示的图片 template.setPopImage(""); template.setPopButton1("下载");
	 * template.setPopButton2("取消");
	 * 
	 * // 设置下载标题 template.setLoadTitle("下载标题");
	 * 
	 * template.setLoadIcon(""); // 设置下载地址 template.setLoadUrl(
	 * "http://gdown.baidu.com/data/wisegame/80bab73f82cc29bf/shoujibaidu_16788496.apk"
	 * ); // 设置定时展示时间 // template.setDuration("2015-01-16 11:40:00",
	 * "2015-01-16 12:24:00"); return template; }
	 */
	/**
	 * @return
	 */



}