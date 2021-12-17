package com.jiagouedu.web.action.manage.system;

import com.jiagouedu.core.ManageContainer;
import com.jiagouedu.core.Services;
import com.jiagouedu.core.dao.page.PagerModel;
import com.jiagouedu.core.oscache.ManageCache;
import com.jiagouedu.core.system.bean.Menu;
import com.jiagouedu.core.system.bean.MenuItem;
import com.jiagouedu.core.system.bean.User;
import com.jiagouedu.core.util.AddressUtils;
import com.jiagouedu.core.util.MD5;
import com.jiagouedu.services.manage.system.impl.MenuService;
import com.jiagouedu.services.manage.system.impl.RoleService;
import com.jiagouedu.services.manage.system.impl.UserService;
import com.jiagouedu.services.manage.systemlog.SystemlogService;
import com.jiagouedu.services.manage.systemlog.bean.Systemlog;
import com.jiagouedu.web.action.BaseController;
import com.jiagouedu.web.util.LoginUserHolder;
import com.jiagouedu.web.util.RequestHolder;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * 后台用户管理
 * @author wukong 图灵学院 QQ:245553999
 * @author wukong 图灵学院 QQ:245553999
 *
 */
@Controller
@RequestMapping("/manage/user")
public class UserAction extends BaseController<User> {
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UserAction.class);

	private static final long serialVersionUID = 1L;

    private static final String page_input = "/manage/system/login";
    private static final String page_home = "/manage/system/home";
    private static final String page_toList = "/manage/system/user/userList";
    private static final String page_toAdd = "/manage/system/user/editUser";
    private static final String page_toEdit = "/manage/system/user/editUser";
    private static final String page_toChangePwd = "/manage/system/user/toChangePwd";
    private static final String page_changePwd_result = "/manage/system/user/changePwd";
    private static final String page_show = "/manage/system/user/show";
    private static final String page_initManageIndex = page_home;
    public UserAction() {
        super.page_toEdit = page_toEdit;
        super.page_toList = page_toList;
        super.page_toAdd = page_toAdd;
    }
    @Autowired
	private UserService userService;
    @Autowired
	private RoleService roleService;
	@Autowired
	private MenuService menuService;
    @Resource(name = "systemlogServiceManage")
	private SystemlogService systemlogService;
    @Resource
	private ManageCache manageCache;

    @Override
    public Services<User> getService() {
        return userService;
    }

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public ManageCache getManageCache() {
		return manageCache;
	}

	public void setManageCache(ManageCache manageCache) {
		this.manageCache = manageCache;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	@Override
	public void insertAfter(User e) {
		e.clear();
	}
	public void setSystemlogService(SystemlogService systemlogService) {
		this.systemlogService = systemlogService;
	}
	
//	@Override
//	public void prepare() throws Exception {
//		if(this.e==null){
//			this.e = new User();
//		}
//
//		super.initPageSelect();
//	}
	@RequestMapping("loadData")
	@ResponseBody
	@Override
	public PagerModel loadData(HttpServletRequest request, User e){
		int offset = 0;
		int pageSize = 10;
		if (request.getParameter("start") != null) {
			offset = Integer
					.parseInt(request.getParameter("start"));
		}
		if (request.getParameter("length") != null) {
			pageSize = Integer
					.parseInt(request.getParameter("length"));
		}
		if (offset < 0)
			offset = 0;
		if(pageSize < 0){
			pageSize = 10;
		}
		e.setOffset(offset);
		e.setPageSize(pageSize);
		PagerModel pager = userService.selectPageList(e);
		pager.setRecordsTotal(pager.getTotal());
		pager.setRecordsFiltered(pager.getTotal());
		return pager;
	}
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login(@ModelAttribute("e") User e, HttpSession session){
		if (session.getAttribute(ManageContainer.manage_session_user_info) != null) {
			return "redirect:/manage/user/home";
		}
        return page_input;
    }

	/**
	 * 后台登录
	 * @return
	 * @throws Exception
	 */
    @RequestMapping(value = "login", method = RequestMethod.POST)
	public String login(HttpSession session,@ModelAttribute("e") User e, ModelMap model) throws Exception {
		String errorMsg;
		if (session.getAttribute(ManageContainer.manage_session_user_info) != null) {
			return "redirect:/manage/user/home";
		}

		if (StringUtils.isBlank(e.getUsername()) || StringUtils.isBlank(e.getPassword())){
			model.addAttribute("errorMsg", "账户和密码不能为空!");
			return page_input;
		}
		logger.info("用户登录:{}", e.getUsername());
		e.setPassword(MD5.md5(e.getPassword()));
		User u = ((UserService)getService()).login(e);
		if (u == null) {
			errorMsg = "登陆失败，账户或密码错误！";
			logger.error("登陆失败，账户或密码错误,{}", e.getUsername());
			model.addAttribute("errorMsg", errorMsg);
			return page_input;
		}else if(!u.getStatus().equals(User.user_status_y)){
			errorMsg = "帐号已被禁用，请联系管理员!";
			logger.error("帐号已被禁用，请联系管理员,{}", u.getUsername());
			model.addAttribute("errorMsg", errorMsg);
			return page_input;
		}
		u.setUsername(e.getUsername());
		session.setAttribute(ManageContainer.manage_session_user_info, u);
		
		//解析用户的数据库权限，以后可以进行DB权限限制
		if(StringUtils.isNotBlank(u.getRole_dbPrivilege())){
			String[] dbPriArr = u.getRole_dbPrivilege().split(",");
			if(u.getDbPrivilegeMap()==null){
				u.setDbPrivilegeMap(new HashMap<String, String>());
			}else{
				u.getDbPrivilegeMap().clear();
			}
			
			if(dbPriArr.length!=0){
				for(int i=0;i<dbPriArr.length;i++){
					u.getDbPrivilegeMap().put(dbPriArr[i], dbPriArr[i]);
				}
			}
		}
		//用户可访问的菜单写入session
		Collection<MenuItem> userMenus = loadMenus(u);
		session.setAttribute("userMenus", userMenus);
		try {
			loginLog(u,"login");
		} catch (Exception ex) {
            ex.printStackTrace();
		}
		
		return "redirect:/manage/user/home";
	}

	private Collection<MenuItem> loadMenus(User u) {
		/*
		 * 首先，加载顶级目录或页面菜单
		 */
		Map<String, String> param = new HashMap<String, String>();
		if (u != null && u.getRid() != null) {
			param.put("rid", u.getRid());//角色ID
		}
//		param.put("pid", pid);//菜单父ID
		List<Menu> menus = menuService.selectList(param);
		//创建菜单集合
		LinkedHashMap<String, MenuItem> root = new LinkedHashMap<String, MenuItem>();
		//循环添加菜单到菜单集合
		for (Menu menu : menus) {
			MenuItem item = new MenuItem(menu.getName(), null);
			item.setId(menu.getId());
			item.setPid(menu.getPid());
			item.setMenuType(menu);
//			if(item.getType().equals(MenuType.page)){
//				item.setIcon("http://127.0.0.1:8082/myshop/resource/images/letter.gif");
//			}
			item.setUrl(StringUtils.trimToEmpty(menu.getUrl()));
			if(item.isRootMenu()) {
				root.put(item.getId(), item);
			}
		}
		for (Menu menu : menus) {
			MenuItem item = new MenuItem(menu.getName(), null);
			item.setId(menu.getId());
			item.setPid(menu.getPid());
			item.setMenuType(menu);
//			if(item.getType().equals(MenuType.page)){
//				item.setIcon("http://127.0.0.1:8082/myshop/resource/images/letter.gif");
//			}
			item.setUrl(StringUtils.trimToEmpty(menu.getUrl()));
			if(!item.isRootMenu() && !item.isButton()) {
				MenuItem parentItem = root.get(item.getPid());
				if(parentItem != null) {
					parentItem.addClild(item);
				} else {
					logger.warn("菜单项{}({})没有对应的父级菜单", item.getName(), item.getId());
				}
			}
		}
		return root.values();
	}
    @RequestMapping("home")
    public String home(){
        if(LoginUserHolder.getLoginUser() == null){
            return "redirect:/manage/user/login";
        }
        return page_home;
    }
	
	private void loginLog(User u,String log) {
		Systemlog systemlog = new Systemlog();
		systemlog.setTitle(log);
		systemlog.setContent(log);
		systemlog.setAccount(u.getUsername());
		systemlog.setType(1);
		systemlog.setLoginIP(AddressUtils.getIp(RequestHolder.getRequest()));
		
		String address = null;
		if(!systemlog.getLoginIP().equals("127.0.0.1") && !systemlog.getLoginIP().equals("localhost")){
			//获取指定IP的区域位置
			try {
				address = AddressUtils.getAddresses("ip=" + systemlog.getLoginIP(), "utf-8");
			} catch (Exception e) {
				e.printStackTrace();
			}
			systemlog.setLoginArea(address);
			
			//异地登陆的判断方法为：先比较本次登陆和上次登陆的区域位置，如果不一致，说明是异地登陆；如果获取不到区域，则比较IP地址，如果IP地址和上次的不一致，则是异地登陆
			Systemlog firstSystemlog = systemlogService.selectFirstOne(u.getUsername());
			if(firstSystemlog!=null){
				if(StringUtils.isNotBlank(address) && StringUtils.isNotBlank(firstSystemlog.getLoginArea())){
					if(!address.equals(firstSystemlog.getLoginArea())){
						systemlog.setDiffAreaLogin(Systemlog.systemlog_diffAreaLogin_y);
					}
				}else if(StringUtils.isNotBlank(systemlog.getLoginIP()) && StringUtils.isNotBlank(firstSystemlog.getLoginIP())){
					if(!systemlog.getLoginIP().equals(firstSystemlog.getLoginIP())){
						systemlog.setDiffAreaLogin(Systemlog.systemlog_diffAreaLogin_y);
					}
				}
			}
		}
		
		systemlogService.insert(systemlog);
	}
	
	/**
	 * 添加用户
	 */
    @Override
    @RequestMapping("insert")
	public String insert(HttpServletRequest request, @ModelAttribute("e") User user, RedirectAttributes flushAttrs) throws Exception {
		return save0(user, flushAttrs);
	}

	/**
	 * 修改用户信息
	 */
    @Override
    @RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(HttpServletRequest request, @ModelAttribute("e") User user, RedirectAttributes flushAttrs) throws Exception {
		return save0(user, flushAttrs);
	}

	private String save0(User e, RedirectAttributes flushAttrs) throws Exception {
		logger.error("save0..."+e.getPassword()+","+e.getNewpassword2());
		
		if(StringUtils.isBlank(e.getId())){//添加
			if(StringUtils.isBlank(e.getPassword()) || StringUtils.isBlank(e.getNewpassword2())){
				flushAttrs.addFlashAttribute("errorMsg", "输入的密码不符合要求！");
				return "redirect:toEdit?id=" + e.getId();
			}
			
			if(!e.getPassword().equals(e.getNewpassword2())){
				flushAttrs.addFlashAttribute("errorMsg", "两次输入的密码不一致！");
				return "redirect:toEdit?id=" + e.getId();
			}
			
			User user = (User)RequestHolder.getSession().getAttribute(ManageContainer.manage_session_user_info);
			e.setCreateAccount(user.getUsername());
			if(StringUtils.isBlank(e.getStatus())){
				e.setStatus(User.user_status_y);
			}
			e.setPassword(MD5.md5(e.getPassword()));
			getService().insert(e);
		}else{//修改
			
			//当前登录用户是admin，才能修改admin的信息，其他用户修改admin信息都属于非法操作。
			User user = (User)RequestHolder.getSession().getAttribute(ManageContainer.manage_session_user_info);
			if(!user.getUsername().equals("admin") && e.getUsername().equals("admin")){
//				throw new RuntimeException("操作非法！");
				logger.warn("非admin用户正在尝试修改admin用户信息，{}", user.getUsername());
				flushAttrs.addFlashAttribute("errorMsg", "非法操作！");
				return "redirect:toEdit?id=" + e.getId();
			}
			
			if(StringUtils.isBlank(e.getPassword()) && StringUtils.isBlank(e.getNewpassword2())){
				//不修改密码
				e.setPassword(null);
			}else{
				//修改密码
				if(!e.getPassword().equals(e.getNewpassword2())){
					flushAttrs.addFlashAttribute("errorMsg", "两次输入的密码不一致！");
					return "redirect:toEdit?id=" + e.getId();
				}
				e.setPassword(MD5.md5(e.getPassword()));
			}
			
			e.setUpdateAccount(user.getUsername());
			getService().update(e);
		}
		flushAttrs.addFlashAttribute("message", "操作成功!");
		return "redirect:back";
	}

    /**
     * 注销登录
     * @return
     * @throws Exception
     */
    @RequestMapping("logout")
	public String logout(@ModelAttribute("e") User e) throws Exception {
        HttpSession session = RequestHolder.getSession();
        if(session != null) {
            User u = LoginUserHolder.getLoginUser();
            if(u!=null && StringUtils.isNotBlank(u.getUsername())){
                loginLog(u,"loginOut");
            }
            session.setAttribute(ManageContainer.manage_session_user_info, null);
            session.setAttribute(ManageContainer.resource_menus, null);
            session.setAttribute(ManageContainer.user_resource_menus_button, null);
        }
		e.clear();
		return page_input;
	}

	/**
	 * ajax验证输入的字符的唯一性
	 * @return
	 * @throws IOException
	 */
    @RequestMapping("unique")
    @ResponseBody
	public String unique(@ModelAttribute("e") User e, HttpServletResponse response) throws IOException{
		logger.error("验证输入的字符的唯一性"+e);
        response.setCharacterEncoding("utf-8");
		if(StringUtils.isNotBlank(e.getNickname())){//验证昵称是否被占用
			logger.error("验证昵称是否被占用");
			User user = new User();
			user.setNickname(e.getNickname());

//				if(userService.selectCount(e)>0){
//					getResponse().getWriter().write("{\"error\":\"昵称已经被占用!\"}");
//				}else{
//					getResponse().getWriter().write("{\"ok\":\"昵称可以使用!\"}");
//				}

			user = userService.selectOneByCondition(user);

			if(user==null){
				//数据库中部存在此编码
				return "{\"ok\":\"昵称可以使用!\"}";
			}else{
				if(StringUtils.isNotBlank(e.getId()) && e.getId().equals(user.getId())){
					//update操作，又是根据自己的编码来查询的，所以当然可以使用啦
					return "{\"ok\":\"昵称可以使用!\"}";
				}else {
					//当前为insert操作，但是编码已经存在，则只可能是别的记录的编码
					return "{\"error\":\"昵称已经存在!\"}";
				}
			}
		}else if(StringUtils.isNotBlank(e.getUsername())){//验证用户名是否被占用
			logger.error("验证账号是否被占用");
			User user = new User();
			user.setUsername(e.getUsername());
			if(userService.selectCount(user)>0){
				return "{\"error\":\"账号已经被占用!\"}";
			}else{
				return "{\"ok\":\"账号可以使用!\"}";
			}
		}
		return null;
	}
//	@Override
//	protected void toEditBefore(User e) {
//		String id = getRequest().getParameter("id");
//		if (id!=null) {
//			e.clear();
//			e.setId(id);
//			e = getServer().selectOne(e);
//		}else{
//			e.clear();
//		}
//	}
	
	/**
	 * 转到修改密码页面
	 * @return
	 */
    @RequestMapping("toChangePwd")
	public String toChangePwd(HttpSession session, @ModelAttribute("e") User e){
		User u = (User) session.getAttribute(ManageContainer.manage_session_user_info);
		e.setId(u.getId());
		return page_toChangePwd;
	}

	@RequestMapping("changePwd")
	public String changePwd(){
		return page_changePwd_result;
	}
	
	/**
	 * 修改密码
	 * @return
	 */
    @RequestMapping(value = "updateChangePwd", method = RequestMethod.POST)
	public String updateChangePwd(@ModelAttribute("e") User e, RedirectAttributes flushAttrs) {
//		String errorMsg = "两次输入的密码不一致，修改密码失败!";
		if(StringUtils.isBlank(e.getNewpassword()) || StringUtils.isBlank(e.getNewpassword2())){
			addMessage(flushAttrs, "密码不能为空！");
			return "redirect:toChangePwd";
		}
		
		if(!e.getNewpassword().equals(e.getNewpassword2())){
			addError(flushAttrs, "两次输入的密码不一致！");
			return "redirect:toChangePwd";
		}
		
//		errorMsg = "旧密码输入错误，修改密码失败!";
		
		User u = (User) RequestHolder.getSession().getAttribute(ManageContainer.manage_session_user_info);
		e.setPassword(MD5.md5(e.getPassword()));
		if(!e.getPassword().equals(u.getPassword())){//用户输入的旧密码和数据库中的密码一致
			addError(flushAttrs, "原密码不正确！");
			return "redirect:toChangePwd";
		}
		
		//修改密码
		e.setPassword(MD5.md5(e.getNewpassword()));
		this.getService().update(e);
		//更新session中的用户信息
		u = userService.selectById(u.getId());
		RequestHolder.getSession().setAttribute(ManageContainer.manage_session_user_info, u);
		addMessage(flushAttrs, "密码修改成功!");
		return "redirect:changePwd";
	}
	

    @RequestMapping("toAdd")
	public String toAdd(@ModelAttribute("e")User user, ModelMap model) throws Exception {
        model.addAttribute("roleList", roleService.selectList(null));
		return page_toAdd;
	}
	@Override
	protected void selectListAfter(PagerModel pager) {
		pager.setPagerUrl("selectList");
	}
	
	/**
	 * 编辑用户
	 */
    @RequestMapping("toEdit")
	public String toEdit(@ModelAttribute("e") User e, ModelMap model) throws Exception {
        model.addAttribute("roleList", roleService.selectList(null));

		e = getService().selectOne(e);
        model.addAttribute("e", e);
//		if(getRequest().getParameter("id")==null){
//			e.clear();
//		}else{
//			e.setId(getRequest().getParameter("id"));
//			e = getServer().selectOne(e);
//		}
		
		return page_toEdit;
	}
	
	/**
	 * 查看管理人员信息
	 * @return
	 */
    @RequestMapping("show")
	public String show(@ModelAttribute("e") User e, String account, ModelMap model){
		if(StringUtils.isBlank(account)){
			throw new NullPointerException("非法请求！");
		}
		
		e.setUsername(account);
		e = getService().selectOne(e);
		model.addAttribute("e", e);
		return page_show;
	}

	/**
	 * 用户修改密码--验证旧密码是否正确
	 * @return
	 */
    @RequestMapping("checkOldPassword")
    @ResponseBody
	public String checkOldPassword(@ModelAttribute("e")User e, HttpSession session) throws Exception{
		logger.error("checkOldPassword.."+e.getPassword());
		if(StringUtils.isBlank(e.getPassword())){
			return "{\"error\":\"旧密码不能为空!\"}";
		}else{
			//检查旧密码输入的是否正确
			User user = (User)session.getAttribute(ManageContainer.manage_session_user_info);
			String oldPass = MD5.md5(e.getPassword());
			if(user.getPassword().equals(oldPass)){
				return "{\"ok\":\"旧密码输入正确!\"}";
			}else{
				return "{\"error\":\"旧密码输入错误!\"}";
			}
		}
	}

	/**
	 * 加载后台首页数据
	 * @return
	 */
    @RequestMapping("initManageIndex")
	public String initManageIndex(){
		//店主每次登陆后台都需要加载综合统计数据？！还是说每次都触发加载，但是到底加载不加载具体看系统的加载策略？！
		manageCache.loadOrdersReport();
		return page_initManageIndex;
	}
    @Override
    public String deletes(HttpServletRequest request, String[] ids, @ModelAttribute("e") User e, RedirectAttributes flushAttrs) throws Exception{
        throw new RuntimeException("not support");
    }
}
