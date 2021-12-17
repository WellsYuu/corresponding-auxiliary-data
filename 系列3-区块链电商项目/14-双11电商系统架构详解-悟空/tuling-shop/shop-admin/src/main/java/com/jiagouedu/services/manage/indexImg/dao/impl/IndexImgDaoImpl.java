/**
 * 2012-7-8
 * jqsl2012@163.com
 */
package com.jiagouedu.services.manage.indexImg.dao.impl;

import com.jiagouedu.core.dao.BaseDao;
import com.jiagouedu.core.dao.page.PagerModel;
import com.jiagouedu.services.manage.indexImg.bean.IndexImg;
import com.jiagouedu.services.manage.indexImg.dao.IndexImgDao;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author wukong 图灵学院 QQ:245553999
 */
@Repository("indexImgDaoManage")
public class IndexImgDaoImpl implements IndexImgDao {

    @Resource
	private BaseDao dao;

	public void setDao(BaseDao dao) {
		this.dao = dao;
	}

	public PagerModel selectPageList(IndexImg e) {
		return dao.selectPageList("manage.indexImg.selectPageList",
				"manage.indexImg.selectPageCount", e);
	}

	public List selectList(IndexImg e) {
		return dao.selectList("manage.indexImg.selectList", e);
	}

	public IndexImg selectOne(IndexImg e) {
		return (IndexImg) dao.selectOne("manage.indexImg.selectOne", e);
	}

	public int delete(IndexImg e) {
		return dao.delete("manage.indexImg.delete", e);
	}

	public int update(IndexImg e) {
		return  dao.update("manage.indexImg.update", e);
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
		return  dao.insert("manage.indexImg.insert", e);
	}

	/**
	 * @param bInfo
	 */
	public List<IndexImg> getLoseList(IndexImg bInfo) {
		return dao.selectList("manage.indexImg.getLoseList", bInfo);
	}
	
	@Override
	public int deleteById(int id) {
		return dao.delete("manage.indexImg.deleteById",id);
	}

	@Override
	public List<IndexImg> getImgsShowToIndex(int i) {
		return dao.selectList("manage.indexImg.getImgsShowToIndex",i);
	}
	public IndexImg selectById(String id) {
		return (IndexImg) dao.selectOne(id);
	}
}
