package com.jiagouedu.services.front.orderpay.dao.impl;import com.jiagouedu.core.dao.BaseDao;import com.jiagouedu.core.dao.page.PagerModel;import com.jiagouedu.services.front.orderpay.bean.Orderpay;import com.jiagouedu.services.front.orderpay.dao.OrderpayDao;import org.springframework.stereotype.Repository;import javax.annotation.Resource;import java.util.List;@Repository("orderpayDaoFront")public class OrderpayDaoImpl implements OrderpayDao {    @Resource	private BaseDao dao;	public void setDao(BaseDao dao) {		this.dao = dao;	}	public PagerModel selectPageList(Orderpay e) {		return dao.selectPageList("front.orderpay.selectPageList",				"front.orderpay.selectPageCount", e);	}	public List selectList(Orderpay e) {		return dao.selectList("front.orderpay.selectList", e);	}	public Orderpay selectOne(Orderpay e) {		return (Orderpay) dao.selectOne("front.orderpay.selectOne", e);	}	public int delete(Orderpay e) {		return dao.delete("front.orderpay.delete", e);	}	public int update(Orderpay e) {		return dao.update("front.orderpay.update", e);	}	public int deletes(String[] ids) {		Orderpay e = new Orderpay();		for (int i = 0; i < ids.length; i++) {			e.setId(ids[i]);			delete(e);		}		return 0;	}	public int insert(Orderpay e) {		return dao.insert("front.orderpay.insert", e);	}	public int deleteById(int id) {		return dao.delete("front.orderpay.deleteById", id);	}	@Override	public Orderpay selectById(String id) {		return (Orderpay) dao.selectOne("front.orderpay.selectById", id);	}}