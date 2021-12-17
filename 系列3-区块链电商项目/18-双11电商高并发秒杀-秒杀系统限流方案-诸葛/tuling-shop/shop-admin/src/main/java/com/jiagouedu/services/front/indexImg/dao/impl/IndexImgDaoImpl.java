/**
 * 2012-7-8
 * jqsl2012@163.com
 */
package com.jiagouedu.services.front.indexImg.dao.impl;

import com.jiagouedu.core.dao.BaseDao;
import com.jiagouedu.core.dao.page.PagerModel;
import com.jiagouedu.services.front.indexImg.bean.IndexImg;
import com.jiagouedu.services.front.indexImg.dao.IndexImgDao;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author wukong 图灵学院 QQ:245553999
 */
@Repository("indexImgDaoFront")
public class IndexImgDaoImpl implements IndexImgDao {
    @Resource
	private BaseDao dao;

	public void setDao(BaseDao dao) {
		this.dao = dao;
	}

	public PagerModel selectPageList(IndexImg e) {
		return dao.selectPageList("front.indexImg.selectPageList",
				"front.indexImg.selectPageCount", e);
	}

	public List selectList(IndexImg e) {
		return dao.selectList("front.indexImg.selectList", e);
	}

	public IndexImg selectOne(IndexImg e) {
		return (IndexImg) dao.selectOne("front.indexImg.selectOne", e);
	}

	public int delete(IndexImg e) {
		return dao.delete("front.indexImg.delete", e);
	}

	public int update(IndexImg e) {
		return  dao.update("front.indexImg.update", e);
	}

	/**
	 * 批量删除用户
	 * 
	 * @param ids
	 */
	public int deletes(String[] ids) {
		IndexImg e = new IndexImg();
		for (int i = 0; i < ids.length; i++) {
			e.setId(ids[i]);
			delete(e);
		}
		return 0;
	}

	public int insert(IndexImg e) {
		return  dao.insert("front.indexImg.insert", e);
	}

	/**
	 * @param bInfo
	 */
	public List<IndexImg> getLoseList(IndexImg bInfo) {
		return dao.selectList("front.indexImg.getLoseList", bInfo);
	}
	
	@Override
	public int deleteById(int id) {
		return dao.delete("front.indexImg.deleteById",id);
	}

	@Override
	public List<IndexImg> getImgsShowToIndex(int i) {
		return dao.selectList("front.indexImg.getImgsShowToIndex",i);
	}
	
	public IndexImg selectById(String id) {
		return (IndexImg) dao.selectOne("account.selectById", id);
	}
}
