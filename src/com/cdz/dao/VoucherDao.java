package dao;

import java.util.List;
import java.math.BigDecimal;

import org.apache.ibatis.annotations.Param;

import aos.framework.core.annotation.Dao;
import aos.framework.core.typewrap.Dto;
import po.VoucherPO;

/**
 * <b>代金券表[voucher]数据访问接口</b>
 * 
 * <p>
 * 注意:此文件由AOS平台自动生成-禁止手工修改
 * </p>
 * 
 * @author duanchongfeng
 * @date 2017-04-19 20:27:11
 */
@Dao
public interface VoucherDao {

	/**
	 * 插入一个数据持久化对象(插入字段为传入PO实体的非空属性)
	 * <p> 防止DB字段缺省值需要程序中再次赋值
	 *
	 * @param voucherPO
	 *            要插入的数据持久化对象
	 * @return 返回影响行数
	 */
	int insert(VoucherPO voucherPO);
	
	/**
	 * 插入一个数据持久化对象(含所有字段)
	 * 
	 * @param voucherPO
	 *            要插入的数据持久化对象
	 * @return 返回影响行数
	 */
	int insertAll(VoucherPO voucherPO);

	/**
	 * 根据主键修改数据持久化对象
	 * 
	 * @param voucherPO
	 *            要修改的数据持久化对象
	 * @return int 返回影响行数
	 */
	int updateByKey(VoucherPO voucherPO);

	/**
	 * 根据主键查询并返回数据持久化对象
	 * 
	 * @return VoucherPO
	 */
	VoucherPO selectByKey(@Param(value = "voucher_id") String voucher_id);

	/**
	 * 根据唯一组合条件查询并返回数据持久化对象
	 * 
	 * @return VoucherPO
	 */
	VoucherPO selectOne(Dto pDto);

	/**
	 * 根据Dto查询并返回数据持久化对象集合
	 * 
	 * @return List<VoucherPO>
	 */
	List<VoucherPO> list(Dto pDto);

	/**
	 * 根据Dto查询并返回分页数据持久化对象集合
	 * 
	 * @return List<VoucherPO>
	 */
	List<VoucherPO> listPage(Dto pDto);
		
	/**
	 * 根据Dto模糊查询并返回数据持久化对象集合(字符型字段模糊匹配，其余字段精确匹配)
	 * 
	 * @return List<VoucherPO>
	 */
	List<VoucherPO> like(Dto pDto);

	/**
	 * 根据Dto模糊查询并返回分页数据持久化对象集合(字符型字段模糊匹配，其余字段精确匹配)
	 * 
	 * @return List<VoucherPO>
	 */
	List<VoucherPO> likePage(Dto pDto);

	/**
	 * 根据主键删除数据持久化对象
	 *
	 * @return 影响行数
	 */
	int deleteByKey(@Param(value = "voucher_id") String voucher_id);
	
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
