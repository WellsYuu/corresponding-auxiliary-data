package com.tuling.teach.spring;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.fastjson.JSON;

@WebServlet("/_plugin")
public class SpringPluginManagerControl extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3052232615130444332L;
	private WebApplicationContext context;
	private SpringPluginFactory pluginFactory;

	@Override
	public void init() throws ServletException {
		super.init();
		context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		pluginFactory = context.getBean(SpringPluginFactory.class);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");
		if ("list".equals(action)) {
			getHavePlugins(req, resp);
		} else if ("active".equals(action)) {
			activePlugin(req, resp);
		} else if ("disable".equals(action)) {
			disablePlugin(req, resp);
		} else if ("site".equals(action)) {
			openPluginSite(req, resp);
		} else if ("install".equals(action)) {
			installPlugin(req, resp);
		}else if("uninstall".equals(action)){
			uninstallPlugin(req, resp);
		}
	}

	private void uninstallPlugin(HttpServletRequest req, HttpServletResponse resp) {
		// TODO Auto-generated method stub
		
	}

	// 获取已安装插件列表
	public void getHavePlugins(HttpServletRequest req, HttpServletResponse resp) {
		req.setAttribute("havePlugins", pluginFactory.getPluginList());
		try {
			req.getRequestDispatcher("/pluginManager.jsp").forward(req, resp);
		} catch (ServletException e) {
			e.printStackTrace();
			// 异常堆栈打印到WEB页
			try {
				e.printStackTrace(new PrintStream(resp.getOutputStream()));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 启用指定插件
	public void activePlugin(HttpServletRequest req, HttpServletResponse resp) {
		pluginFactory.activePlugin(req.getParameter("id"));
		try {
			resp.getWriter().append("active succeed!!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 禁用指定插件
	public void disablePlugin(HttpServletRequest req, HttpServletResponse resp) {
		pluginFactory.disablePlugin(req.getParameter("id"));
		try {
			resp.getWriter().append("disable succeed!!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 打开插件站点
	public void openPluginSite(HttpServletRequest req, HttpServletResponse resp) {
		try {
			PluginSite site = getSite(req.getParameter("url"));
			req.setAttribute("site", site);
			req.setAttribute("siteUrl", req.getParameter("url"));
			req.getRequestDispatcher("/pluginSite.jsp").forward(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private PluginSite getSite(String urlValue) throws Exception {
		URL url = new URL(urlValue);
		InputStream input = url.openStream();
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		copy(input, output);
		String jsonValue = output.toString("UTF-8");
		return JSON.parseObject(jsonValue, PluginSite.class);
	}

	// 安装指定插件
	public void installPlugin(HttpServletRequest req, HttpServletResponse resp) {
		try {
			PluginSite site = getSite(req.getParameter("url"));
			String id = req.getParameter("id");
			boolean sucess = false;
			for (PluginConfig config : site.getConfigs()) {
				if (config.getId().equals(id)) {
					pluginFactory.installPlugin(config, true);
					sucess = true;
					break;
				}
			}
			if (sucess) {
				resp.getWriter().append("install succeed!!");
			} else {
				resp.getWriter().append("install fail!!");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				e.printStackTrace(new PrintStream(resp.getOutputStream()));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	private static long copy(InputStream source, OutputStream sink) throws IOException {
		long nread = 0L;
		byte[] buf = new byte[8192];
		int n;
		while ((n = source.read(buf)) > 0) {
			sink.write(buf, 0, n);
			nread += n;
		}
		return nread;
	}

}
