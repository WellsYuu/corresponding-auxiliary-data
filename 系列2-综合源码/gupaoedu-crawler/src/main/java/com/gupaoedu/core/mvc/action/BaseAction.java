package com.gupaoedu.core.mvc.action;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import com.gupaoedu.common.utils.WebUtil;

/**
 * 基础Action类，原则上所有Action都继承此类
 * @author Tom
 *
 */
public class BaseAction {
	
	/**
	 * 公共跳转方法
	 * 
	 * @param viewName 视图名
	 * @param modelMap 参数列表
	 * @param request 
	 * @return
	 */
	protected ModelAndView jumpToView(String viewName, ModelMap modelMap,HttpServletRequest request) {
		return jumpToView(viewName,modelMap);
	}
	
	/**
	 * 公共跳转方法
	 * @param viewName
	 * @param modelMap
	 * @return
	 */
	protected ModelAndView jumpToView(String viewName, ModelMap modelMap) {
		if(modelMap == null) {
			return new ModelAndView(viewName);
		}
		return new ModelAndView(viewName, modelMap);
	}
	
	/**
	 * 公共跳转方法
	 * @param viewName
	 * @param modelMap
	 * @return
	 */
	protected ModelAndView jumpToView(String viewName) {
		return jumpToView(viewName,null);
	}
	
	
	/**
	 * 设置导出文件的头信息
	 * @param res
	 * @param filename
	 */
	protected void setExportHeader(HttpServletResponse res, String filename){
		try{
			MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
			res.setHeader("Content-Disposition", "attachment;filename=\"" + new String(filename.getBytes("gb2312"), "ISO8859-1") + "\"");
			res.setHeader("Content-Type", mimeTypesMap.getContentType(filename));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 返回 jsonp 处理
	 * @param request
	 * @param jsonStr
	 * @return
	 */
	protected ModelAndView callBackForJsonp(HttpServletRequest request,HttpServletResponse response,String jsonStr){
		WebUtil.outJsonpToView(request, response,jsonStr);
		return null;
	}
	
	
	/**
	 * 输出信息（用于解决输出JSON中文为乱码的问题）
	 * 
	 * @param msg（输出对象）
	 * @return
	 */
	protected ModelAndView outToView(HttpServletRequest request,HttpServletResponse response,String msg) {
		WebUtil.outHtmlToView(request, response,msg);
		return null;
	}
	
	
	
	/**
     * 获得客户端真实ip
     */
	protected  String getIpAddr(HttpServletRequest request) {    
       return WebUtil.getIpAddr(request);
	}

	
	/**
	 * 获取domain
	 * @param request
	 * @return
	 */
	protected String getDomain(HttpServletRequest request){
		return WebUtil.getDomain(request);
	}
	
}
