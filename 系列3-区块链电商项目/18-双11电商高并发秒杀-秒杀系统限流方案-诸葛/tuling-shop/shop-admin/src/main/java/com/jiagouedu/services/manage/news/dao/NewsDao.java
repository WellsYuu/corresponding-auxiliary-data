/**
 * 2012-7-8
 * jqsl2012@163.com
 */
package com.jiagouedu.services.manage.news.dao;

import com.jiagouedu.core.DaoManager;
import com.jiagouedu.services.manage.news.bean.News;

import java.util.List;


/**
 * @author wukong 图灵学院 QQ:245553999
 * @param <T>
 */
public interface NewsDao extends DaoManager<News> {

	/**
	 * @param e
	 * @return
	 */
	List<News> selecIndexNews(News e);

	/**
	 * @param news
	 */
	void sync(News news);

	void updateDownOrUp(News news);

	int selectCount(News news);

}
