/**
 * 2012-7-8
 * jqsl2012@163.com
 */
package com.jiagouedu.services.manage.news.impl;

import com.jiagouedu.core.ServersManager;
import com.jiagouedu.services.manage.news.NewsService;
import com.jiagouedu.services.manage.news.bean.News;
import com.jiagouedu.services.manage.news.dao.NewsDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author wukong 图灵学院 QQ:245553999
 */
@Service("newsServiceManage")
public class NewsServiceImpl extends ServersManager<News, NewsDao> implements
        NewsService {
    @Resource(name = "newsDaoManage")
    @Override
    public void setDao(NewsDao newsDao) {
        this.dao = newsDao;
    }
	/**
	 * @param e
	 */
	public List<News> selecIndexNews(News e) {
		return dao.selecIndexNews(e);
	}

	@Override
	public void updateStatus(String[] ids, String status) {
		if(ids==null || ids.length==0){
			return;
		}
		
		for(int i=0;i<ids.length;i++){
			News news = new News();
			news.setId(ids[i]);
			news.setStatus(status);
			dao.sync(news);
		}
//		throw new NullPointerException();
	}

	@Override
	public void updateDownOrUp(News news) {
		dao.updateDownOrUp(news);
	}

	@Override
	public int selectCount(News news) {
		return dao.selectCount(news);
	}

}
