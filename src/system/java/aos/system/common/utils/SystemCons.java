package aos.system.common.utils;

import aos.framework.core.utils.AOSCons;

/**
 * 系统常量表
 * 
 * @author xiongchun
 *
 */
public class SystemCons extends AOSCons{
	
	/**
	 * 生成SEQ时的业务类型
	 */
	public static final class ID{
		//系统管理业务统一使用一个ID标识
		public static final String SYSTEM = "system_";
	}
	
	/**
	 * 授权类型
	 */
	public static final class GRANT_TYPE_{
		//业务办理权限
		public static final String BIZ = "1";
		//管理授权权限
		public static final String ADMIN = "2";
	}
	
	/**
	 * 用户状态
	 */
	public static final class USER_STATUS{
		//正常
		public static final String NORMAL = "1";
		//锁定
		public static final String LOCK = "2";
		//停用
		public static final String DISABLE = "3";
	}
	
	/**
	 * 图标类型
	 */
	public static final class ICON_TYPE{
		//小图标
		public static final String SMALL = "1";
		//大图标
		public static final String BIG = "2";
		//字体图标
		public static final String FONT = "3";
	}
	
	/**
	 * Redis命令数据类型
	 */
	public static final class CMD_TYPE{
		//String
	    public static final String STRING = "1";
		//MAP
		public static final String MAP = "2";
		//LIST
		public static final String LIST = "3";
		//SET
		public static final String SET = "4";
	}
	
	/**
	 * 树组件的更节点的父节点ID
	 */
	public static  final String ROOTNODE_PARENT_ID = "0";
	
	/**
	 * 超级管理员流水号
	 */
	public static  final String SUPER_USER_ID = "9999999999999999";
	
	/**
	 * 超级管理员角色流水号
	 */
	public static  final String SUPER_ROLE_ID = "9999999999999999";
	
	/**
	 * 角色管理菜单流水号
	 */
	public static  final String ROLE_MODULE_id_ = "1609251718170099";
	
	
}
