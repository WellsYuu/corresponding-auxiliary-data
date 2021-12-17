/**
 * 2012-7-8
 * jqsl2012@163.com
 */
package com.jiagouedu.web.action.manage.indexImg;

import com.jiagouedu.core.Services;
import com.jiagouedu.core.dao.page.PagerModel;
import com.jiagouedu.services.manage.indexImg.IndexImgService;
import com.jiagouedu.services.manage.indexImg.bean.IndexImg;
import com.jiagouedu.web.action.BaseController;
import com.jiagouedu.web.util.RequestHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * 滚动图片
 * 
 * @author wukong 图灵学院 QQ:245553999
 * @author wukong 图灵学院 QQ:245553999
 * 
 */
@Controller
@RequestMapping("/manage/indexImg/")
public class IndexImgAction extends BaseController<IndexImg> {
	private static final long serialVersionUID = 1L;
	private static final String page_toList = "/manage/indexImg/indexImgList";
	private static final String page_toEdit = "/manage/indexImg/indexImgEdit";
	private static final String page_toAdd = "/manage/indexImg/indexImgEdit";
	private IndexImgAction() {
		super.page_toList = page_toList;
		super.page_toAdd = page_toAdd;
		super.page_toEdit = page_toEdit;
	}
	@Autowired
	private IndexImgService imgService;

	@Override
	public Services<IndexImg> getService() {
		return imgService;
	}

	@Override
	public void insertAfter(IndexImg e) {
		e.clear();
	}

	@Override
	protected void selectListAfter(PagerModel pager) {
		pager.setPagerUrl("selectList");
	}

	//上传文件
	@Deprecated
	private void uploadImage(MultipartFile image, IndexImg e) throws IOException{
		if(image==null){
			return;
		}
		String imageName = String.valueOf(System.currentTimeMillis()) + ".jpg";
		String realpath = RequestHolder.getSession().getServletContext().getRealPath("/indexImg/");
		// D:\apache-tomcat-6.0.18\webapps\struts2_upload\images
		logger.info("realpath: " + realpath);
		if (image != null) {
			File savefile = new File(new File(realpath), imageName);
			if (!savefile.getParentFile().exists()) {
				savefile.getParentFile().mkdirs();
			}
			image.transferTo(savefile);
//			FileUtils.copyFile(image, savefile);
//			ActionContext.getContext().put("message", "文件上传成功");
		}
//		SystemInfo sInfo = SystemSingle.getInstance().getSystemInfo();
//		String url = sInfo.getWww_ip() + "/file/img/" + imageName;
		String url = "/indexImg/" + imageName;
		e.setPicture(url);
		image = null;
	}
	
	/**
	 * 同步缓存
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("syncCache")
	public String syncCache(HttpServletRequest request, IndexImg img) throws Exception{
//		SystemSingle.getInstance().sync(Container.imgList);
		return super.selectList(request, img);
	}
}
