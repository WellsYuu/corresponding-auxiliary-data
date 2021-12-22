/**
 * 2012-7-8
 * jqsl2012@163.com
 */
package com.jiagouedu.services.manage.indexImg.dao;

import com.jiagouedu.core.DaoManager;
import com.jiagouedu.services.manage.indexImg.bean.IndexImg;

import java.util.List;


public interface IndexImgDao extends DaoManager<IndexImg> {

	/**
	 * @param i
	 * @return
	 */
	List<IndexImg> getImgsShowToIndex(int i);

}
