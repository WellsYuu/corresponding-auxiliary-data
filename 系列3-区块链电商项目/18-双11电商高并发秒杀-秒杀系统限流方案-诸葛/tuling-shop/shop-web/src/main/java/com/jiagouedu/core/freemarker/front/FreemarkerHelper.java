package com.jiagouedu.core.freemarker.front;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import com.jiagouedu.services.front.news.NewsService;
import com.jiagouedu.services.front.news.bean.News;
import com.jiagouedu.services.front.product.ProductService;
import com.jiagouedu.services.front.product.bean.Product;
import com.jiagouedu.web.util.RequestHolder;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 生成模板的帮助类
 * 
 * @author wukong 图灵学院 QQ:245553999ei
 * 
 */
@Component
public class FreemarkerHelper {
	private static final Logger logger = LoggerFactory.getLogger(FreemarkerHelper.class);
	@Autowired
	private ProductService productService;
	@Autowired
	private NewsService newsService;
	
	/**
	 * 模板
	 */
	public static final String template_newsInfo = "newsInfo.ftl";//文章模板
	public static final String template_product = "product.ftl";//商品介绍模板

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public void setNewsService(NewsService newsService) {
		this.newsService = newsService;
	}

	/**
	 * 生成门户静态页面
	 * 
	 * @return
	 */
//	public void index(String template, String templateHtml) {
//		try {
//			// 准备数据
//			HashMap<String, Object> data = new HashMap<String, Object>();
//			data.put("indexMenuList", loadIndexMenu());
//			crateHTML(ServletActionContext.getServletContext(), data, template,
//					templateHtml);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * 加载门户菜单列表
	 * 
	 * @return
	 * @return
	 */
//	private List<IndexMenu> loadIndexMenu() {
//		IndexMenu e = new IndexMenu();
//		List<IndexMenu> indexMenuList = indexMenuService.selectList(e);
//		return indexMenuList;
//	}

	/**
	 * 生成静态页面主方法
	 * 
	 * @param context
	 *            ServletContext
	 * @param data
	 *            一个Map的数据结果集
	 * @param templatePath
	 *            ftl模版路径
	 * @param targetHtmlPath
	 *            生成静态页面的路径
	 * @throws IOException 
	 * @throws TemplateException 
	 */
	public void crateHTML(ServletContext context, Map<String, Object> data,
			String templatePath, String targetHtmlPath) throws Exception {
		if(StringUtils.isBlank(targetHtmlPath)){
			throw new NullPointerException("targetHtmlPath不能为空！");
		}
		
		Configuration freemarkerCfg = new Configuration();
		// 加载模版
		freemarkerCfg.setServletContextForTemplateLoading(context, "/template");
		freemarkerCfg.setEncoding(Locale.getDefault(), "UTF-8");
		freemarkerCfg.setDefaultEncoding("UTF-8");

		// 指定模版路径
		Template template = freemarkerCfg
				.getTemplate(templatePath, "UTF-8");
		template.setEncoding("UTF-8");
		// 静态页面路径
//			String htmlPath = context.getRealPath("/") + "/"
//					+ targetHtmlPath;
		System.out.println(targetHtmlPath);
		File htmlFile = new File(targetHtmlPath);
		Writer out = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(htmlFile), "UTF-8"));
		// 处理模版
		template.process(data, out);
		out.flush();
		out.close();
		System.out.println("生成成功");
//		try {
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.out.println("生成失败");
//		}
	}

	public boolean isEmpty(String value) {
		if (value == null || value.trim().length() == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 对系统帮助的文章静态化
	 * @throws Exception 
	 */
	public void helps() throws Exception {
		News param = new News();
		param.setType("help");
		List<News> notices = newsService.selectList(param);
		if(notices==null || notices.size()==0){
			logger.error("notices size = 0");
			return;
		}
		logger.error("notices size = " + notices.size());
		
		HashMap<String, Object> data = new HashMap<String, Object>();
		for(int i=0;i<notices.size();i++){
			News news = notices.get(i);
			if(StringUtils.isBlank(news.getContent())){
				continue;
			}
			
			data.clear();
			data.put("e", news);
			String templateHtml = RequestHolder.getSession().getServletContext().getRealPath("/")+"/jsp/helps/"+news.getId()+".jsp";
			crateHTML(RequestHolder.getSession().getServletContext(), data, template_newsInfo,templateHtml);
			logger.error("生成html页面成功！id="+news.getId());
		}
	}
	
	/**
	 * 对新闻公告的文章静态化
	 * @throws Exception 
	 */
	public void notices() throws Exception {
		News param = new News();
		param.setType("notice");
		List<News> notices = newsService.selectList(param);
		if(notices==null || notices.size()==0){
			logger.error("notices size = 0");
			return;
		}
		logger.error("notices size = " + notices.size());
		
		HashMap<String, Object> data = new HashMap<String, Object>();
		for(int i=0;i<notices.size();i++){
			News news = notices.get(i);
			if(StringUtils.isBlank(news.getContent())){
				continue;
			}
			
			data.clear();
			data.put("e", news);
			String templateHtml = RequestHolder.getSession().getServletContext().getRealPath("/")+"/jsp/notices/"+news.getId()+".jsp";
			crateHTML(RequestHolder.getSession().getServletContext(), data, template_newsInfo,templateHtml);
			logger.error("生成html页面成功！id="+news.getId());
		}
	}

	/**
	 * 对商品介绍静态化
	 * @throws Exception 
	 */
	public String products() throws Exception {
		List<Product> productList = productService.selectListProductHTML(new Product());
		if(productList==null || productList.size()==0){
			logger.error("productList size = 0");
			return null;
		}
		logger.error("productList size = " + productList.size());
		StringBuilder errorBuff = new StringBuilder();
		
		HashMap<String, Object> data = new HashMap<String, Object>();
		for(int i=0;i<productList.size();i++){
			Product p = productList.get(i);
			if(StringUtils.isBlank(p.getProductHTML())){
				continue;
			}
			
			data.clear();
			data.put("e", p);
			String templateHtml = RequestHolder.getSession().getServletContext().getRealPath("/")+"/jsp/product/"+p.getId()+".jsp";
			try {
				crateHTML(RequestHolder.getSession().getServletContext(), data, template_product,templateHtml);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("生成html页面失败！id="+p.getId());
				
				errorBuff.append(p.getId()+",");
				continue;
			}
			logger.error("生成html页面成功！id="+p.getId());
		}
		
		if(errorBuff.length()==0){
			return null;
		}
		return errorBuff.toString();
	}

	/**
	 * 静态化指定的商品
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public String staticProductByID(String id) throws Exception {
		Product p = productService.selectById(id);
		if(p==null || StringUtils.isBlank(p.getProductHTML())){
			logger.error("ERROR,not found product by id = " + id);
			throw new NullPointerException("ERROR,not found product by id = " + id);
		}
		
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.clear();
		data.put("e", p);
		String templateHtml = RequestHolder.getSession().getServletContext().getRealPath("/")+"/jsp/product/"+p.getId()+".jsp";
		crateHTML(RequestHolder.getSession().getServletContext(), data, template_product,templateHtml);
		logger.error("生成html页面成功！id="+p.getId());
		
		return "success";
	}
	
	/**
	 * 静态化指定的文章
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public String staticNewsByID(String id) throws Exception {
		if(StringUtils.isBlank(id)){
			throw new NullPointerException("id参数不能为空！");
		}
		
		News news = newsService.selectById(id);
		if(news==null || StringUtils.isBlank(news.getContent())){
			logger.error("ERROR,not found news by id = " + id);
			throw new NullPointerException("ERROR,not found news by id = " + id);
		}
		
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.clear();
		data.put("e", news);
		String templateHtml = null;
		
		if(news.getType().equals(News.news_type_help)){
			templateHtml = RequestHolder.getSession().getServletContext().getRealPath("/")+"/jsp/helps/"+news.getId()+".jsp";
		}else if(news.getType().equals(News.news_type_notice)){
			templateHtml = RequestHolder.getSession().getServletContext().getRealPath("/")+"/jsp/notices/"+news.getId()+".jsp";
		}
		crateHTML(RequestHolder.getSession().getServletContext(), data, template_newsInfo,templateHtml);
		logger.error("生成html页面成功！id="+news.getId());
		
		return "success";
	}
}
