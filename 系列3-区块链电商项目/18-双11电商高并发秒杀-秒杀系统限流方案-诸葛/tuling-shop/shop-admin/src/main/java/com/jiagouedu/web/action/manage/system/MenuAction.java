package com.jiagouedu.web.action.manage.system;

import com.jiagouedu.core.ManageContainer;
import com.jiagouedu.core.Services;
import com.jiagouedu.core.dao.page.PagerModel;
import com.jiagouedu.core.system.bean.*;
import com.jiagouedu.services.manage.system.impl.MenuService;
import com.jiagouedu.services.manage.system.impl.PrivilegeService;
import com.jiagouedu.web.action.BaseController;
import com.jiagouedu.web.util.LoginUserHolder;
import com.jiagouedu.web.util.RequestHolder;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;


/**
 * 资源管理
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/manage/menu/")
public class MenuAction extends BaseController<Menu> {
	private static final long serialVersionUID = 1L;
    @Autowired
	private MenuService menuService;
    @Autowired
	private PrivilegeService privilegeService;
//	private static final String str = "../";
	private static final Logger log = LoggerFactory.getLogger(MenuAction.class);
    private static final String page_toList = "/manage/system/menu/menuList";
    private static final String page_toEdit = "/manage/system/menu/editMenu";
    private static final String page_addOrUpdate = "/manage/system/menu/addOrUpdate";

    private MenuAction() {
        super.page_toList = page_toList;
        super.page_toEdit = page_toEdit;
        super.page_toAdd = page_toEdit;
    }
    public PrivilegeService getPrivilegeService() {
		return privilegeService;
	}

	public void setPrivilegeService(PrivilegeService privilegeService) {
		this.privilegeService = privilegeService;
	}

    @Override
    public Services<Menu> getService() {
        return menuService;
    }

    @Override
	protected void selectListAfter(PagerModel pager) {
		pager.setPagerUrl("selectList");
	}
	/**
	 * 转到 添加/修改菜单 页面
	 * @return
	 * @throws Exception
	 */
    @RequestMapping("toAddOrUpdate")
	public String toAddOrUpdate(@ModelAttribute("e") Menu menu, ModelMap model) throws Exception{
//		System.out.println(menu!=null?menu.getId():"null");
//		System.out.println("==="+getRequest().getParameter("id"));
//		menu.setId(getRequest().getParameter("id"));
		menu = menuService.selectOne(menu);
        model.addAttribute("e", menu);
		return page_addOrUpdate;
//		return toList;
	}
	/**
	 * 添加/修改菜单
	 * 修改选中的菜单，为该菜单添加子菜单
	 * @return
	 * @throws Exception
	 */
    @RequestMapping(value = "addOrUpdate", method = RequestMethod.POST)
    @ResponseBody
	public String addOrUpdate(HttpServletRequest request) throws Exception{
		//选中菜单的信息
		String updateP = request.getParameter("updateP");
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String orderNum = request.getParameter("orderNum");
		String type = request.getParameter("type");
		
		//要添加的子菜单
		String url = request.getParameter("url");
		String n_name = request.getParameter("n_name");
		String n_url = request.getParameter("n_url");
		String parentOrChild = request.getParameter("parentOrChild");
		String n_orderNum = request.getParameter("n_orderNum");
		String n_type = request.getParameter("n_type");
		
		Menu itemMenu = null;
		if(n_name!=null && !n_name.trim().equals("")){
			itemMenu = new Menu();
			//添加子菜单
			if(parentOrChild.equals("0")){//顶级模块
				itemMenu.setPid("0");
				itemMenu.setType(MenuType.module.toString());
			} else if(parentOrChild.equals("1")){//顶级页面
				itemMenu.setPid("0");
				itemMenu.setType(MenuType.page.toString());
			} else if(parentOrChild.equals("2")){//子模块
				itemMenu.setPid(id);
				itemMenu.setType(MenuType.module.toString());
			} else if(parentOrChild.equals("3")){//子页面
				itemMenu.setPid(id);
				itemMenu.setType(MenuType.page.toString());
			} else if(parentOrChild.equals("4")){   //功能
				itemMenu.setPid(id);
				itemMenu.setType(MenuType.button.toString());
			} else {
				throw new IllegalAccessException("添加菜单异常。");
			}
			itemMenu.setName(n_name);
			itemMenu.setUrl(n_url);
			itemMenu.setOrderNum(Integer.valueOf(n_orderNum));
			itemMenu.setType(n_type);
		}
		//修改父菜单
		Menu m = new Menu();
		m.setId(id);
		m.setName(name);
		m.setUrl(url);
		m.setOrderNum(Integer.valueOf(orderNum));
		m.setType(type);
		
		menuService.addOrUpdate(updateP,m, itemMenu);
		
		return "ok";
	}
	
	//加载指定角色的全部菜单
    @RequestMapping("selectJsonMenu")
    @ResponseBody
	public String selectJsonMenu(HttpSession session) throws Exception {
		Object menusJson = session.getAttribute(ManageContainer.resource_menus);
		if(menusJson!=null){
			return menusJson.toString();
		}else{
			User u = LoginUserHolder.getLoginUser();
			List<MenuItem> root = loadMenus(u,"0",null);
			String jsonMenus = writeMenus(root);
			session.setAttribute(ManageContainer.resource_menus, jsonMenus);
			
			//找出用户具有的功能，并且存放到session中，以方便后期的功能权限检查
//			if(root!=null){
//				for(int i=0;i<root.size();i++){
//					MenuItem item = root.get(i);
////					if(item.get)
//				}
////				getSession().setAttribute(ManageContainer.user_resource_menus_button,null);
//			}
            return jsonMenus;
		}
	}
	
	/**
	 * 添加用户资源功能到session，为后面权限功能检查做铺垫
	 */
	private void addUserResourceMenusButton(String button){
		log.debug("addUserResourceMenusButton.button="+button);
        HttpSession session = RequestHolder.getSession();
		Map<String,String> buttons = (Map<String, String>) session.getAttribute(ManageContainer.user_resource_menus_button);
		if(buttons==null){
			buttons = new HashMap<String, String>();//TreeMap<String, String>();
            session.setAttribute(ManageContainer.user_resource_menus_button, buttons);
		}
		buttons.put(button, button);
	}
	
	/**
	 * 从PID=0开始加载菜单资源
	 * 获取指定节点的全部子菜单（包括当前菜单节点）
	 * @return
	 * @throws Exception
	 */
    @RequestMapping("getMenusByPid")
    @ResponseBody
	public String getMenusByPid(HttpServletRequest request) throws Exception {
		String pid = request.getParameter("pid");
		if(pid==null || pid.trim().equals(""))
			pid = "0";
		String id = request.getParameter("id");
		List<MenuItem> menus = menuService.loadMenus(null, pid, "#");
		
		// 加载全部的菜单
		if(id!=null){
			// 加载指定角色的权限
			Privilege privilege = new Privilege();
			privilege.setRid(id);
			List<Privilege> rolePs = privilegeService.selectList(privilege);
			
			// 拿角色拥有的菜单和全部的菜单做比对，进行勾选
			for (int i = 0; i < rolePs.size(); i++) {
				Privilege p = rolePs.get(i);
				eeee(p, menus);
			}
		}
		return writeMenus(menus);
	}
	
	/**
	 * 角色权限和资源菜单进行对比，使checkbox选中
	 * @param p
	 * @param menus
	 */
	private void eeee(Privilege p, List<MenuItem> menus){
		for (int j = 0; j < menus.size(); j++) {
			MenuItem menu = menus.get(j);
			if (p.getMid().equals(menu.getId())) {
				menu.setChecked(true);
				return;
			}else{
				if(menu.getChildren()!=null && menu.getChildren().size()>0)
					eeee(p, menu.getChildren());
			}
		}
	}
	
	//输出菜单到页面
	private String writeMenus(List<MenuItem> root) throws IOException{
		JSONArray json = JSONArray.fromObject(root);
//		System.out.println(json.toString());
		String jsonStr = json.toString();
		try {
			return jsonStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonStr;
	}

	/**
	 * 根据角色ID，加载用户菜单资源
	 * @param u
	 * @param pid
	 * @param url
	 * @return
	 */
	private List<MenuItem> loadMenus(User u,String pid,String url) {
		/*
		 * 首先，加载顶级目录或页面菜单 
		 */
		Map<String,String> param = new HashMap<String, String>();
		if(u!=null && u.getRid()!=null){
			param.put("rid", u.getRid());//角色ID
		}
		param.put("pid", pid);//菜单父ID
		List<Menu> menus = menuService.selectList(param);
		//创建菜单集合
		List<MenuItem> root = new ArrayList<MenuItem>();
		//循环添加菜单到菜单集合
		for (Iterator<Menu> it = menus.iterator(); it.hasNext();) {
			Menu entry = it.next();
			MenuItem item = new MenuItem(entry.getName(), null);
			item.setId(entry.getId());
			item.setPid(entry.getPid());
			item.setMenuType(entry);
//			if(item.getType().equals(MenuType.page)){
//				item.setIcon("http://127.0.0.1:8082/myshop/resource/images/letter.gif");
//			}
			if(url!=null){
				item.setUrl(url);
			}else{
				item.setUrl(entry.getUrl());
			}
			root.add(item);
		}
		
		/*
		 * 其次，加载子菜单 或 按钮功能
		 */
		for (int i = 0; i < root.size(); i++) {
			MenuItem item = root.get(i);
			if(!item.isButton()){
				Menu mm = new Menu();
				mm.setPid(item.getId());
				loadChildrenByPid(root.get(i), mm,url,u);
			}else{
				//addUserResourceMenusButton(item.getUrl());
			}
		}

		return root;
	}

	// 根据父ID加载子节点
	private void loadChildrenByPid(MenuItem item, Menu menu, String url, User u) {
		Map<String,String> param = new HashMap<String, String>();
		
		if(u!=null && u.getRid()!=null)
			param.put("rid", u.getRid());
		param.put("pid", menu.getPid());
		//加载菜单节点
		List<Menu> data = menuService.selectList(param);
		if(data==null || data.size()==0){
			return;
		}
		if(item.getChildren()==null)item.setChildren(new ArrayList<MenuItem>());
		//创建菜单节点
		for (int i = 0; i < data.size(); i++) {
			Menu entry = data.get(i);
			
			MenuItem addItem = new MenuItem(entry.getName(), null);
			addItem.setId(entry.getId());
			addItem.setPid(entry.getPid());
			addItem.setMenuType(entry);
			String url0 = null;
			if(url!=null){
				addItem.setUrl(url);
				url0 = url;
			}else{
				addItem.setUrl(entry.getUrl());
				url0 = entry.getUrl();
			}
//			System.out.println("entry.getType()="+entry.getType()+",MenuType.button="+MenuType.button);
			if(entry.getType().equals("button")){
				addUserResourceMenusButton(url0);
			}else{
				item.getChildren().add(addItem);
			}
		}
		//根据菜单节点进行递归加载
		for (int i = 0; i < item.getChildren().size(); i++) {
			MenuItem childItem = item.getChildren().get(i);
			if(!childItem.isButton()){
				Menu itemMenu = new Menu();
				itemMenu.setPid(childItem.getId());
//				itemMenu.setMenuType(entry);
				loadChildrenByPid(childItem, itemMenu,url,u);
			}
		}
	}

    @RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(@ModelAttribute("e") Menu menu) throws Exception {
		if (menu.getId() == null || menu.getId().equals("")) {
			if (menu.getUrl() == null) {
				menu.setUrl("");
			}
			menuService.insert(menu);
		} else {
			menuService.update(menu);
		}
		return selectList(RequestHolder.getRequest(), menu);
	}

	/**
	 * 删除菜单
	 * @return
	 * @throws Exception
	 */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
	public String delete(HttpServletRequest request) throws Exception {
		
		String ids = request.getParameter("ids");
		if(ids==null || ids.trim().equals(""))
				throw new Exception("删除菜单异常！");
		
		this.menuService.deletes(ids,request.getParameter("deleteParent"));
		
		//删除成功返回1
		return "1";
	}

	public MenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

	@Override
	public void insertAfter(Menu e) {
		// TODO Auto-generated method stub
		e.clear();
	}
	
}
