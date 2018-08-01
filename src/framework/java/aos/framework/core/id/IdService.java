package aos.framework.core.id;

import java.math.BigInteger;

import org.springframework.stereotype.Service;

import aos.framework.core.service.CDZBaseController;

/**
 * <b>ID生成服务</b>
 * 
 * @author xiongchun
 */
@Service
public class IdService extends CDZBaseController{

	/**
	 * 获取DB序列号的nextVal
	 * <p>注意：目前支持Oracle Sequence、Mysql 自定义函数
	 * @return
	 */
	public BigInteger nextVal(String sequenceName){
		BigInteger val = (BigInteger)sqlDao.selectOne("Id.nextVal", sequenceName);
		return val;
	}
	
}
