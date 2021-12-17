/**
 * 2012-7-8
 * jqsl2012@163.com
 */
package com.jiagouedu.services.front.news.impl;

import com.jiagouedu.core.ServersManager;
import com.jiagouedu.services.front.news.NewsService;
import com.jiagouedu.services.front.news.bean.News;
import com.jiagouedu.services.front.news.dao.NewsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author wukong 图灵学院 QQ:245553999
 */
@Service
public class NewsServiceImpl extends ServersManager<News, NewsDao> implements
		NewsService {
    @Resource(name = "newsDao")
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
	public List<String> selectAllMd5() {
		// TODO Auto-generated method stub
		return dao.selectAllMd5();
	}

	@Override
	public void updateInBlackList(String[] ids) {
		// TODO Auto-generated method stub
//		if(ids==null || ids.length==0){
//			return;
//		}
//		
//		for(int i=0;i<ids.length;i++){
//			String e = ids[i];
//			newsDao.updateInBlackList(e);
//		}
	}

	@Override
	public void sync(String[] ids, int status) {
//		if(ids==null || ids.length==0){
//			return;
//		}
//		
//		for(int i=0;i<ids.length;i++){
//			News news = new News();
//			news.setId(ids[i]);
////			news.setStatus(status);
//			newsDao.sync(news);
//		}
	}

	@Override
	public List<News> selectNoticeList(News news) {
		return dao.selectNoticeList(news);
	}

	@Override
	public News selectSimpleOne(News news) {
		return dao.selectSimpleOne(news);
	}

}
