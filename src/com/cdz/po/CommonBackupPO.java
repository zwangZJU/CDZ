package po;

import aos.framework.core.typewrap.PO;
import java.util.Date;

/**
 * <b>common_backup[common_backup]数据持久化对象</b>
 * <p>
 * 注意:此文件由AOS平台自动生成-禁止手工修改。
 * </p>
 * 
 * @author duanchongfeng
 * @date 2017-06-30 09:37:15
 */
public class CommonBackupPO extends PO {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private String backup_id;
	
	/**
	 * 文件名称
	 */
	private String file_name;
	
	/**
	 * 文件路径
	 */
	private String file_path;
	
	/**
	 * 创建时间
	 */
	private Date create_date;
	
	/**
	 * 是否删除，0：未删除，1：删除
	 */
	private String is_del;
	
	/**
	 * 操作人ID
	 */
	private String oper_id;
	
	/**
	 * 最近恢复时间
	 */
	private Date restore_date;
	

	/**
	 * 主键
	 * 
	 * @return backup_id
	 */
	public String getBackup_id() {
		return backup_id;
	}
	
	/**
	 * 文件名称
	 * 
	 * @return file_name
	 */
	public String getFile_name() {
		return file_name;
	}
	
	/**
	 * 文件路径
	 * 
	 * @return file_path
	 */
	public String getFile_path() {
		return file_path;
	}
	
	/**
	 * 创建时间
	 * 
	 * @return create_date
	 */
	public Date getCreate_date() {
		return create_date;
	}
	
	/**
	 * 是否删除，0：未删除，1：删除
	 * 
	 * @return is_del
	 */
	public String getIs_del() {
		return is_del;
	}
	
	/**
	 * 操作人ID
	 * 
	 * @return oper_id
	 */
	public String getOper_id() {
		return oper_id;
	}
	
	/**
	 * 最近恢复时间
	 * 
	 * @return restore_date
	 */
	public Date getRestore_date() {
		return restore_date;
	}
	

	/**
	 * 主键
	 * 
	 * @param backup_id
	 */
	public void setBackup_id(String backup_id) {
		this.backup_id = backup_id;
	}
	
	/**
	 * 文件名称
	 * 
	 * @param file_name
	 */
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	
	/**
	 * 文件路径
	 * 
	 * @param file_path
	 */
	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}
	
	/**
	 * 创建时间
	 * 
	 * @param create_date
	 */
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	
	/**
	 * 是否删除，0：未删除，1：删除
	 * 
	 * @param is_del
	 */
	public void setIs_del(String is_del) {
		this.is_del = is_del;
	}
	
	/**
	 * 操作人ID
	 * 
	 * @param oper_id
	 */
	public void setOper_id(String oper_id) {
		this.oper_id = oper_id;
	}
	
	/**
	 * 最近恢复时间
	 * 
	 * @param restore_date
	 */
	public void setRestore_date(Date restore_date) {
		this.restore_date = restore_date;
	}
	

}