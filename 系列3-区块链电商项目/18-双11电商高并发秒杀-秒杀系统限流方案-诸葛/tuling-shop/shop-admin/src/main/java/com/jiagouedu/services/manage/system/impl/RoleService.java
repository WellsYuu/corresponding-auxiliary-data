package com.jiagouedu.services.manage.system.impl;

import com.jiagouedu.core.Services;
import com.jiagouedu.core.dao.BaseDao;
import com.jiagouedu.core.dao.page.PagerModel;
import com.jiagouedu.core.system.bean.Privilege;
import com.jiagouedu.core.system.bean.Role;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author wukong 图灵学院 QQ:245553999 角色业务逻辑实现类
 */
@Service
public class RoleService implements Services<Role> {
    @Resource
	private BaseDao dao;
    @Resource
	private PrivilegeService privilegeService;

	public void setDao(BaseDao dao) {
		this.dao = dao;
	}

	public PagerModel selectPageList(Role role) {
		return dao.selectPageList("role.selectPageList",
				"role.selectPageCount", role);
	}

	public void setPrivilegeService(PrivilegeService privilegeService) {
		this.privilegeService = privilegeService;
	}

	public List selectList(Role role) {
		return dao.selectList("role.selectList", role);
	}

	public Role selectOne(Role role) {
		return (Role) dao.selectOne("role.selectOne", role);
	}

	public int insert(Role role) {
		return dao.insert("role.insert", role);
	}

	/**
	 * 删除指定角色以及该角色下的所有权限
	 * 
	 * @param role
	 */
	public int delete(Role role) {
		// 删除角色
		dao.delete("role.delete", role);
		// 删除角色对应的权限
		privilegeService.deleteByRole(role);
		return 0;
	}

	public int update(Role role) {
		return dao.update("role.update", role);
	}

	/**
	 * 编辑角色
	 * 
	 * @param role
	 * @throws Exception
	 */
	public void editRole(Role role, String insertOrUpdate) throws Exception {
		int insertRole = 0;
		Privilege privilege = new Privilege();
		if (insertOrUpdate.equals("1")) {
			// 新增角色
			insertRole = insert(role);
		} else {
			// 修改角色
			insertRole = update(role);
			// 删除角色的所有权限
			privilege.setRid(String.valueOf(insertRole));
			privilegeService.delete(privilege);
		}

		// 赋予权限
		if (role.getPrivileges() == null
				|| role.getPrivileges().trim().equals(""))
			return;

		String[] pArr = role.getPrivileges().split(",");
		for (int i = 0; i < pArr.length; i++) {
			privilege.clear();

			privilege.setMid(pArr[i]);
			privilege.setRid(String.valueOf(insertRole));
			privilegeService.insert(privilege);
		}
	}

	/**
	 * 批量删除角色
	 * 
	 * @param ids
	 */
	public int deletes(String[] ids) {
		Role role = new Role();
		for (int i = 0; i < ids.length; i++) {
			role.setId(ids[i]);
			delete(role);
			role.clear();
		}
		return 0;
	}

	@Override
	public Role selectById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
