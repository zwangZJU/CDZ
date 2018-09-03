package dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import aos.framework.core.annotation.Dao;
import aos.framework.core.typewrap.Dto;
import po.App_versionPO;

/**
 * <b>app_version[app_version]数据访问接口</b>
 * 
 * <p>
 * 注意:此文件由AOS平台自动生成-禁止手工修改
 * </p>
 * 
 * @author Administrator
 * @date 2018-09-03 12:54:23
 */
@Dao
public interface App_versionDao {

	/**
	 * 插入一个数据持久化对象(插入字段为传入PO实体的非空属性)
	 * <p> 防止DB字段缺省值需要程序中再次赋值
	 *
	 * @param app_versionPO
	 *            要插入的数据持久化对象
	 * @return 返回影响行数
	 */
	int insert(App_versionPO app_versionPO);
	
	/**
	 * 插入一个数据持久化对象(含所有字段)
	 * 
	 * @param app_versionPO
	 *            要插入的数据持久化对象
	 * @return 返回影响行数
	 */
	int insertAll(App_versionPO app_versionPO);

	/**
	 * 根据主键修改数据持久化对象
	 * 
	 * @param app_versionPO
	 *            要修改的数据持久化对象
	 * @return int 返回影响行数
	 */
	int updateByKey(App_versionPO app_versionPO);

	/**
	 * 根据主键查询并返回数据持久化对象
	 * 
	 * @return App_versionPO
	 */
	App_versionPO selectByKey(@Param(value = "app_vesino_id") String app_vesino_id);

	/**
	 * 根据唯一组合条件查询并返回数据持久化对象
	 * 
	 * @return App_versionPO
	 */
	App_versionPO selectOne(Dto pDto);

	/**
	 * 根据Dto查询并返回数据持久化对象集合
	 * 
	 * @return List<App_versionPO>
	 */
	List<App_versionPO> list(Dto pDto);

	/**
	 * 根据Dto查询并返回分页数据持久化对象集合
	 * 
	 * @return List<App_versionPO>
	 */
	List<App_versionPO> listPage(Dto pDto);
		
	/**
	 * 根据Dto模糊查询并返回数据持久化对象集合(字符型字段模糊匹配，其余字段精确匹配)
	 * 
	 * @return List<App_versionPO>
	 */
	List<App_versionPO> like(Dto pDto);

	/**
	 * 根据Dto模糊查询并返回分页数据持久化对象集合(字符型字段模糊匹配，其余字段精确匹配)
	 * 
	 * @return List<App_versionPO>
	 */
	List<App_versionPO> likePage(Dto pDto);

	/**
	 * 根据主键删除数据持久化对象
	 *
	 * @return 影响行数
	 */
	int deleteByKey(@Param(value = "app_vesino_id") String app_vesino_id);
	
	/**
	 * 根据Dto统计行数
	 * 
	 * @param pDto
	 * @return
	 */
	int rows(Dto pDto);
	
	/**
	 * 根据数学表达式进行数学运算
	 * 
	 * @param pDto
	 * @return String
	 */
	String calc(Dto pDto);
	
}
