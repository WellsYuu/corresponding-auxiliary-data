package com.gupaoedu.core.mvc.action;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import com.gupaoedu.common.utils.WebUtil;

/**
 * ����Action�࣬ԭ��������Action���̳д���
 * @author Tom
 *
 */
public class BaseAction {
	
	/**
	 * ������ת����
	 * 
	 * @param viewName ��ͼ��
	 * @param modelMap �����б�
	 * @param request 
	 * @return
	 */
	protected ModelAndView jumpToView(String viewName, ModelMap modelMap,HttpServletRequest request) {
		return jumpToView(viewName,modelMap);
	}
	
	/**
	 * ������ת����
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
	 * ������ת����
	 * @param viewName
	 * @param modelMap
	 * @return
	 */
	protected ModelAndView jumpToView(String viewName) {
		return jumpToView(viewName,null);
	}
	
	
	/**
	 * ���õ����ļ���ͷ��Ϣ
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
	 * ���� jsonp ����
	 * @param request
	 * @param jsonStr
	 * @return
	 */
	protected ModelAndView callBackForJsonp(HttpServletRequest request,HttpServletResponse response,String jsonStr){
		WebUtil.outJsonpToView(request, response,jsonStr);
		return null;
	}
	
	
	/**
	 * �����Ϣ�����ڽ�����JSON����Ϊ��������⣩
	 * 
	 * @param msg���������
	 * @return
	 */
	protected ModelAndView outToView(HttpServletRequest request,HttpServletResponse response,String msg) {
		WebUtil.outHtmlToView(request, response,msg);
		return null;
	}
	
	
	
	/**
     * ��ÿͻ�����ʵip
     */
	protected  String getIpAddr(HttpServletRequest request) {    
       return WebUtil.getIpAddr(request);
	}

	
	/**
	 * ��ȡdomain
	 * @param request
	 * @return
	 */
	protected String getDomain(HttpServletRequest request){
		return WebUtil.getDomain(request);
	}
	
}
