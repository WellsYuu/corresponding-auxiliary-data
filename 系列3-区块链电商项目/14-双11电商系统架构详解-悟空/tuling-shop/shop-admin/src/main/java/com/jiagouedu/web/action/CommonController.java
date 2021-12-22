package com.jiagouedu.web.action;

import com.jiagouedu.core.front.SystemManager;
import com.jiagouedu.core.util.ImageUtils;
import com.jiagouedu.services.common.SystemSetting;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

/**
 * @author wukong 图灵学院 QQ:245553999
 * @date 16/2/15 16:29
 * Email: dinguangx@163.com
 */
@Controller
@RequestMapping("/common")
public class CommonController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("uploadify")
    @ResponseBody
    public String uploadify(@RequestParam("Filedata") MultipartFile filedata,
                            @RequestParam(required = false, defaultValue = "1") String thumbnail) {
        boolean createThumbnail = "1".equals(thumbnail);
        SystemSetting systemSetting = SystemManager.getInstance().getSystemSetting();
        //文件保存目录路径
        String savePath = SystemManager.getInstance().getProperty("file.upload.path");
        //文件保存目录URL
        String saveUrl = systemSetting.getImageRootPath();

//定义允许上传的文件扩展名
        HashMap<String, String> extMap = new HashMap<String, String>();
        extMap.put("image", "gif,jpg,jpeg,png,bmp");
        extMap.put("flash", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
        extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
        extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");

//最大文件大小
        long maxSize = 1000000;

//检查目录
        File uploadDir = new File(savePath);
        if (!uploadDir.isDirectory()) {
            return (getError("上传目录不存在。"));
        }
//检查目录写权限
        if (!uploadDir.canWrite()) {
            return (getError("上传目录没有写权限。"));
        }
        String dirName = "image";
        if (!extMap.containsKey(dirName)) {
            return (getError("目录名不正确。"));
        }
//创建文件夹
        savePath += dirName + "/";
        saveUrl += dirName + "/";
        String relativePath = dirName + "/";
        File saveDirFile = new File(savePath);
        if (!saveDirFile.exists()) {
            saveDirFile.mkdirs();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String ymd = sdf.format(new Date());
        savePath += ymd + "/";
        saveUrl += ymd + "/";
        relativePath += ymd + "/";
        File dirFile = new File(savePath);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }

        String fileName = filedata.getOriginalFilename();
        //检查扩展名
        String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        if (!Arrays.<String>asList(extMap.get(dirName).split(",")).contains(fileExt)) {
            return (getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。"));
        }

        String newFileName1 = null;//小图
        String newFileName2 = null;//中图
        String newFileName3 = null;//大图 ，也是原图
        String newFileName0 = String.valueOf(System.currentTimeMillis());
        logger.debug("newFileName0=" + newFileName0);
        newFileName1 = newFileName0 + "_1";
        newFileName2 = newFileName0 + "_2";
        newFileName3 = newFileName0 + "_3." + fileExt;
        logger.debug("newFileName1=" + newFileName1 + ",newFileName2=" + newFileName2 + ",newFileName3=" + newFileName3);

        File uploadedFile3 = new File(savePath, newFileName3);
        try {
            filedata.transferTo(uploadedFile3);
            if (createThumbnail) {
                File uploadedFile1 = new File(savePath, newFileName1 + "." + fileExt);
                File uploadedFile2 = new File(savePath, newFileName2 + "." + fileExt);

                ImageUtils.ratioZoom2(uploadedFile3, uploadedFile1, Double.valueOf(SystemManager.getInstance().getProperty("product_image_1_w")));
                ImageUtils.ratioZoom2(uploadedFile3, uploadedFile2, Double.valueOf(SystemManager.getInstance().getProperty("product_image_2_w")));
            }
        } catch (Exception e) {
            logger.error("上传文件异常：" + e.getMessage());
            return (getError("上传文件失败。"));
        }

        JSONObject obj = new JSONObject();
        obj.put("error", 0);
        obj.put("filePath", relativePath + newFileName3);
        return (obj.toString());
    }

    private String getError(String msg) {

        JSONObject obj = new JSONObject();
        obj.put("error", 1);
        obj.put("msg", msg);
        return (obj.toString());
    }
}
