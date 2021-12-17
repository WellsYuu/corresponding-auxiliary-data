/**
 * 2012-7-8
 * jqsl2012@163.com
 */
package com.jiagouedu.web.action.manage.news;

import com.jiagouedu.core.dao.page.PagerModel;
import com.jiagouedu.core.front.SystemManager;
import com.jiagouedu.core.system.bean.User;
import com.jiagouedu.services.manage.catalog.CatalogService;
import com.jiagouedu.services.manage.indexImg.IndexImgService;
import com.jiagouedu.services.manage.news.NewsService;
import com.jiagouedu.services.manage.news.bean.News;
import com.jiagouedu.web.action.BaseController;
import com.jiagouedu.web.util.LoginUserHolder;
import com.jiagouedu.web.util.RequestHolder;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;


/**
 * 文章管理
 * @author wukong 图灵学院 QQ:245553999
 * @author wukong 图灵学院 QQ:245553999
 * 
 */
@Controller
@RequestMapping("/manage/news/")
public class NewsAction extends BaseController<News> {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(NewsAction.class);
    private static final String page_toList = "/manage/news/newsList";
    private static final String page_toEdit = "/manage/news/newsEdit";
    private static final String page_toAdd = "/manage/news/newsEdit";
    private NewsAction() {
        super.page_toList = page_toList;
        super.page_toAdd = page_toAdd;
        super.page_toEdit = page_toEdit;
    }
    @Autowired
	private NewsService newsService;
    @Autowired
	private IndexImgService indexImgService;
    @Autowired
	private CatalogService catalogService;

//	private String type;//文章类型。通知：notice；帮助：help
	
	private List<News> news;// 门户新闻列表
	
//	public String getType() {
//		return type;
//	}
//
//	public void setType(String type) {
//		this.type = type;
//	}

	public CatalogService getCatalogService() {
		return catalogService;
	}

	public void setCatalogService(CatalogService catalogService) {
		this.catalogService = catalogService;
	}

    @Autowired
	public NewsService getService() {
		return newsService;
	}

	public void setNewsService(NewsService newsService) {
		this.newsService = newsService;
	}

	public IndexImgService getIndexImgService() {
		return indexImgService;
	}

	public void setIndexImgService(IndexImgService indexImgService) {
		this.indexImgService = indexImgService;
	}

	public List<News> getNews() {
		return news;
	}

	public void setNews(List<News> news) {
		this.news = news;
	}

	@Override
	public void insertAfter(News e) {
		e.clear();
		
//		String type = e.getType();
//		e.clear();
//		e.setType(type);
	}
	
	/**
	 * 新增或者修改后文章的状态要重新设置为未审核状态
	 */
	@Override
    @RequestMapping(value = "insert", method = RequestMethod.POST)
	public String insert(HttpServletRequest request, News e, RedirectAttributes flushAttrs) throws Exception {
		logger.error("NewsAction code = " + e.getCode());
		User user = LoginUserHolder.getLoginUser();
		e.setCreateAccount(user.getUsername());
		e.setStatus(News.news_status_n);//未审核
		
		getService().insert(e);
		
//		getSession().setAttribute("insertOrUpdateMsg", "添加成功！");
//		getResponse().sendRedirect(getEditUrl(e.getId()));
		return "redirect:toEdit2?id="+e.getId();
	}
	
	/**
	 * 修改文章
	 */
	@Override
    @RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(HttpServletRequest request, News e, RedirectAttributes flushAttrs) throws Exception {
		logger.error("NewsAction code = ");
		logger.error("NewsAction code = " + e.getCode()+",id="+e.getId());
//		getE().setStatus(News.news_status_n);//未审核
		
		getService().update(e);
		
//		getSession().setAttribute("insertOrUpdateMsg", "更新成功！");
//		getResponse().sendRedirect(getEditUrl(e.getId()));
		return "redirect:toEdit2?id="+e.getId();
	}
	
	//列表页面点击 编辑商品
    @RequestMapping(value = "toEdit")
	public String toEdit(News e, ModelMap model) throws Exception {
//		getSession().setAttribute("insertOrUpdateMsg", "");
		return toEdit0(e, model);
	}
	
	/**
	 * 添加或编辑商品后程序回转编辑
	 * @return
	 * @throws Exception
	 */
    @RequestMapping(value = "toEdit2")
	public String toEdit2(News e, ModelMap model) throws Exception {
		return toEdit0(e, model);
	}

    @RequestMapping(value = "toEdit0")
	private String toEdit0(News e, ModelMap model) throws Exception {
        model.addAttribute("catalogsArticle", SystemManager.getInstance().getCatalogsArticle());
		return super.toEdit(e, model);
	}
	
//	/**
//	 * 审核文章，审核通过后文章会显示在门户上
//	 */
//	public String shenhe() throws Exception {
//		String id = e.getId();
//		e.clear();
//		e.setId(id);
//		e.setStatus(News.news_status_y);//已审核
//		return super.update();
//	}
	
	/**
	 * 设置为自己
	 */
//	@Deprecated
//	private void settyMy() {
//		User user = (User) getSession().getAttribute(ManageContainer.manage_session_user_info);
//		if(!user.getRid().equals("1")){
//			//只针对非管理员,管理员可以看到所有的文章
//			getE().setCreateAccount(user.getUsername());
//		}
//	}

	/**
	 * 同步缓存内的新闻
	 * 审核通过，记录将会出现在门户上
	 * @return
	 * @throws Exception
	 */
    @RequestMapping(value = "updateStatusY", method = RequestMethod.POST)
	public String updateStatusY(String[] ids, String type, RedirectAttributes flushAttrs) throws Exception {
		newsService.updateStatus(ids, News.news_status_y);
		addMessage(flushAttrs, "操作成功!");
		return "redirect:selectList?type=" + type;
	}
	/**
	 * 审核未通过,记录将不会出现在门户上
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "updateStatusN", method = RequestMethod.POST)
	public String updateStatusN(String[] ids, String type, RedirectAttributes flushAttrs) throws Exception {
		newsService.updateStatus(ids, News.news_status_n);
		addMessage(flushAttrs, "操作成功!");
		return "redirect:selectList?type=" + type;
	}

	/**
	 * 显示指定的文章
	 * @return
	 * @throws Exception
	 */
    @RequestMapping(value = "up")
	public String up(News e, RedirectAttributes flushAttrs) throws Exception {
		return updateDownOrUp0(e, News.news_status_y, flushAttrs);
	}

	/**
	 * 不显示指定的文章
	 * @return
	 * @throws Exception
	 */
    @RequestMapping(value = "down")
	public String down(News e, RedirectAttributes flushAttrs) throws Exception {
		return updateDownOrUp0(e, News.news_status_n, flushAttrs);
	}
	
	private String updateDownOrUp0(News e, String status, RedirectAttributes flushAttrs) throws Exception {
		if(StringUtils.isBlank(e.getId())){
			throw new NullPointerException("参数不能为空！");
		}
		
		News news = new News();
		news.setId(e.getId());
		news.setStatus(status);
		newsService.updateDownOrUp(news);
		addMessage(flushAttrs, "更新成功!");
		return "redirect:toEdit2?id="+e.getId();
	}

//	@Override
//	public String selectList() throws Exception {
////		logger.error("NewsAction.selectList.type="+type);
//		super.selectList();
//		return toList;
//	}
	
//	@Override
//	protected void setParamWhenInitQuery(News e) {
//		super.setParamWhenInitQuery(e);
//		String type = RequestHolder.getRequest().getParameter("type");
//		if(StringUtils.isNotBlank(type)){
//			e.setType(type);
//		}
//	}
	
	/**
	 * 公共的分页方法
	 * 
	 * @return
	 * @throws Exception
	 */
//	public String selectList0() throws Exception {
//		/**
//		 * 由于prepare方法不具备一致性，加此代码解决init=y查询的时候条件不被清除干净的BUG
//		 */
//		this.initPageSelect();
//		e.setType(this.type);/////////////////
//		
//		int offset = 0;//分页偏移量
//		if (getRequest().getParameter("pager.offset") != null) {
//			offset = Integer
//					.parseInt(getRequest().getParameter("pager.offset"));
//		}
//		if (offset < 0)
//			offset = 0;
//		((PagerModel) getE()).setOffset(offset);
//		pager = getServer().selectPageList(getE());
//		if(pager==null)pager = new PagerModel();
//		// 计算总页数
//		pager.setPagerSize((pager.getTotal() + pager.getPageSize() - 1)
//				/ pager.getPageSize());
//		
//		selectListAfter();
//		
//		return toList;
//	}
	
	/**
	 * 检查文章code的唯一性
	 * @return
	 * @throws IOException
	 */
    @RequestMapping(value = "unique")
    @ResponseBody
	public String unique(News e) throws IOException{
		
		logger.error("检查文章code的唯一性");
		if(StringUtils.isBlank(e.getCode())){
			throw new NullPointerException("参数不能为空！");
		}
//		logger.error("wait...10s");
//		try {
//			Thread.sleep(10*1000L);
//		} catch (InterruptedException e1) {
//			e1.printStackTrace();
//		}
		
		int c = newsService.selectCount(e);
//		getResponse().setCharacterEncoding("utf-8");
		if(StringUtils.isBlank(e.getId())){
			if(c==0){
				return "{\"ok\":\"文章code可以使用!\"}";
			}else{
				return "{\"error\":\"文章code已经被占用!\"}";
			}
		}else{
			News news = newsService.selectById(e.getId());
			if(news.getCode().equals(e.getCode()) || c==0){
				return "{\"ok\":\"文章code可以使用!\"}";
			}else{
				return "{\"error\":\"文章code已经被占用!\"}";
			}
		}
		
//		return null;
	}

//	@Override
//	public String deletes() throws Exception {
////		return super.deletes();
//		logger.error("1..type="+e.getType());
//		getServer().deletes(getIds());
//		logger.error("2..type="+e.getType());
//		return selectList();
//	}
	
	@Override
    @RequestMapping(value = "toAdd")
	public String toAdd(News e, ModelMap model) throws Exception {
		String type = e.getType();
		e.clear();
		e.setType(type);
        model.addAttribute("e", e);
        model.addAttribute("catalogsArticle", SystemManager.getInstance().getCatalogsArticle());
		return page_toAdd;
	}
	
	@Override
	protected void selectListAfter(PagerModel pager) {
		pager.setPagerUrl("selectList");
        RequestHolder.getRequest().setAttribute("catalogsArticle", SystemManager.getInstance().getCatalogsArticle());
	}
	
}
