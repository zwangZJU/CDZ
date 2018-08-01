package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import aos.framework.core.id.AOSId;
import aos.framework.core.utils.AOSUtils;
import aos.framework.core.service.CDZBaseController;
import aos.framework.core.typewrap.Dto;
import aos.framework.core.utils.AOSJson;
import aos.framework.web.router.HttpModel;
import aos.system.common.utils.SystemCons;
import dao.ArticleDao;
import po.ArticlePO;

/**
 * 文章表管理
 * 
 * @author duanchongfeng
 * @date 2017-04-21 20:54:18
 *
 */
@Service
public class ArticleController extends CDZBaseController {

	@Autowired
	private ArticleDao articleDao;

	/**
	 * 文章表管理页面初始化
	 * 
	 * @param httpModel
	 * @return
	 */
	public void init(HttpModel httpModel) {
		httpModel.setAttribute("juid", httpModel.getInDto().getString("juid"));
		httpModel.setViewPath("myproject/article.jsp");
	}
	public void test(HttpModel httpModel) {
		httpModel.setAttribute("juid", httpModel.getInDto().getString("juid"));
		httpModel.setViewPath("myproject/test/test2.jsp");
	}
	public void share(HttpModel httpModel) {
		httpModel.setAttribute("juid", httpModel.getInDto().getString("juid"));
		httpModel.setViewPath("myproject/share/share.html");
	}
	/**
	 * 查询文章表列表
	 * 
	 * @param httpModel
	 * @return
	 */
	public void listArticle(HttpModel httpModel) {
		Dto qDto = httpModel.getInDto();
		List<Dto> articleDtos = sqlDao.list("Article.listArticlesPage", qDto);
		httpModel.setOutMsg(AOSJson.toGridJson(articleDtos, qDto.getPageTotal()));
	}
	/**
	 * 查询文章表信息
	 * 
	 * @param httpModel
	 */
	public void getArticle(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
	     ArticlePO articlePO = articleDao.selectByKey(inDto.getString("article_id")); 
		httpModel.setOutMsg(AOSJson.toJson(articlePO));
	}

	/**
	 * 保存文章表
	 * 
	 * @param httpModel
	 * @return
	 */
	 @Transactional
	public void saveArticle(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		ArticlePO articlePO = new ArticlePO();
		articlePO.copyProperties(inDto);
		articlePO.setArticle_id(AOSId.appId(SystemCons.ID.SYSTEM));
		articlePO.setCreate_date(AOSUtils.getDateTime());
		articleDao.insert(articlePO);
		httpModel.setOutMsg("新增成功。");
	}

	/**
	 * 修改文章表
	 * 
	 * @param httpModel
	 * @return
	 */
	 @Transactional
	public void updateArticle(HttpModel httpModel) {
		Dto inDto = httpModel.getInDto();
		ArticlePO articlePO = new ArticlePO();
		articlePO.copyProperties(inDto);
		articleDao.updateByKey(articlePO);
		httpModel.setOutMsg("修改成功。");
	}

	/**
	 * 删除文章表
	 * 
	 * @param httpModel
	 */
	@Transactional
	public void deleteArticle(HttpModel httpModel) {
		String[] selectionIds = httpModel.getInDto().getRows();
		if(null!=selectionIds&&selectionIds.length>0){
			for (String id_ : selectionIds) {
				ArticlePO articlePO = new ArticlePO();
				articlePO.setArticle_id(id_);
				articlePO.setIs_del(SystemCons.IS.YES);
	            articleDao.updateByKey(articlePO); 
			}
		}else{
				String article_id=httpModel.getInDto().getString("article_id");
				ArticlePO articlePO = new ArticlePO();
				articlePO.setArticle_id(article_id);
				articlePO.setIs_del(SystemCons.IS.YES);
	            articleDao.updateByKey(articlePO); 
			
		}
		httpModel.setOutMsg("删除成功。");
	}

	

}
