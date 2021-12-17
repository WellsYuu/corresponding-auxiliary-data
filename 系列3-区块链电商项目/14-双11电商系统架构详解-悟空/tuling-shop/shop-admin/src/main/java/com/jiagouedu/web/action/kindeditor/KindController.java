package com.jiagouedu.web.action.kindeditor;


import com.jiagouedu.core.front.SystemManager;
import com.jiagouedu.core.util.ImageUtils;
import com.jiagouedu.services.common.SystemSetting;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by dylan on 15-5-21.
 */
@Controller
@RequestMapping("editor")
public class KindController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("upload")
    @ResponseBody
    public String uploadFile(@RequestParam(required = true, value = "imgFile") MultipartFile file,
                             @RequestParam(required = false) String dir, HttpSession session) {
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

        session.setAttribute("ajax_upload", 1);
//检查目录
        File uploadDir = new File(savePath);
        if (!uploadDir.isDirectory()) {
            return (getError("上传目录不存在。"));
        }
//检查目录写权限
        if (!uploadDir.canWrite()) {
            return (getError("上传目录没有写权限。"));
        }

        String dirName = dir == null ? "image" : dir.trim().toLowerCase();
        if (!extMap.containsKey(dirName)) {
            return (getError("目录名不正确。"));
        }
//创建文件夹
        savePath += dirName + "/";
        saveUrl += dirName + "/";
        File saveDirFile = new File(savePath);
        if (!saveDirFile.exists()) {
            saveDirFile.mkdirs();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String ymd = sdf.format(new Date());
        savePath += ymd + "/";
        saveUrl += ymd + "/";
        File dirFile = new File(savePath);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }

        String fileName = file.getOriginalFilename();
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
            file.transferTo(uploadedFile3);
            File uploadedFile1 = new File(savePath, newFileName1 + "." + fileExt);
            File uploadedFile2 = new File(savePath, newFileName2 + "." + fileExt);

            ImageUtils.ratioZoom2(uploadedFile3, uploadedFile1, Double.valueOf(SystemManager.getInstance().getProperty("product_image_1_w")));
            ImageUtils.ratioZoom2(uploadedFile3, uploadedFile2, Double.valueOf(SystemManager.getInstance().getProperty("product_image_2_w")));
        } catch (Exception e) {
            logger.error("上传文件异常：" + e.getMessage());
            return (getError("上传文件失败。"));
        }

        JSONObject obj = new JSONObject();
        obj.put("error", 0);
        obj.put("url", saveUrl + newFileName3);
        return (obj.toString());
    }


    private String getError(String message) {
        JSONObject obj = new JSONObject();
        obj.put("error", 1);
        obj.put("message", message);
        return obj.toString();
    }

    @RequestMapping("fileManager")
    @ResponseBody
    public String fileManager(@RequestParam(value = "dir") String dirName, @RequestParam(required = false) String path,
                              @RequestParam(required = false, defaultValue = "name") String order) {
        SystemSetting systemSetting = SystemManager.getInstance().getSystemSetting();
        String rootPath = SystemManager.getInstance().getProperty("file.upload.path");
        String rootUrl = systemSetting.getImageRootPath();
        //图片扩展名
        String[] fileTypes = new String[]{"gif", "jpg", "jpeg", "png", "bmp"};

        if (StringUtils.isNotBlank(dirName)) {
            if (!Arrays.<String>asList(new String[]{"image", "flash", "media", "file"}).contains(dirName)) {
                return ("Invalid Directory name.");
            }
            rootPath += dirName + "/";
            rootUrl += dirName + "/";
            File saveDirFile = new File(rootPath);
            if (!saveDirFile.exists()) {
                saveDirFile.mkdirs();
            }
        }
        //根据path参数，设置各路径和URL
        path = path != null ? path.trim() : "";
        String currentPath = rootPath + path;
        String currentUrl = rootUrl + path;
        String currentDirPath = path;
        String moveupDirPath = "";
        if (!"".equals(path)) {
            String str = currentDirPath.substring(0, currentDirPath.length() - 1);
            moveupDirPath = str.lastIndexOf("/") >= 0 ? str.substring(0, str.lastIndexOf("/") + 1) : "";
        }

        //排序形式，name or size or type
        order = order != null ? order.trim().toLowerCase() : "name";

        //不允许使用..移动到上一级目录
        if (path.indexOf("..") >= 0) {
            return ("Access is not allowed.");
        }
        //最后一个字符不是/
        if (!"".equals(path) && !path.endsWith("/")) {
            path += "/";
        }
        //目录不存在或不是目录
        File currentPathFile = new File(currentPath);
        if (!currentPathFile.isDirectory()) {
            return ("Directory does not exist.");
        }

        //遍历目录取的文件信息
        List<Hashtable> fileList = new ArrayList<Hashtable>();
        Map<String, String> addFileMap = new HashMap<String, String>();
        logger.debug("currentPathFile.listFiles=" + currentPathFile);
        logger.debug("currentPathFile.listFiles=" + currentPathFile.listFiles());
        if (currentPathFile.listFiles() != null) {
            for (File file : currentPathFile.listFiles()) {
                Hashtable<String, Object> hash = new Hashtable<String, Object>();
                String fileName = file.getName();
                logger.debug("fileName=" + fileName);
                if (file.isDirectory()) {
                    hash.put("is_dir", true);
                    hash.put("has_file", (file.listFiles() != null));
                    hash.put("filesize", 0L);
                    hash.put("is_photo", false);
                    hash.put("filetype", "");
                } else if (file.isFile()) {
                    String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

                    String _fileName = fileName.substring(0, fileName.lastIndexOf("."));
                    //String fileNameA = _fileName.split("_")[1];
                    //logger.debug("fileNameA="+fileNameA);

	 				/* if(fileNameA.equals("1") || fileNameA.equals("2")){
                     //if(addFileMap.get(fileNameA)!=null){
	 					continue;
	 				} */
                    //addFileMap.put(fileNameA, fileNameA);

                    hash.put("is_dir", false);
                    hash.put("has_file", false);
                    hash.put("filesize", file.length());
                    hash.put("is_photo", Arrays.<String>asList(fileTypes).contains(fileExt));
                    hash.put("filetype", fileExt);
                }
                hash.put("filename", fileName);
                hash.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file.lastModified()));
                fileList.add(hash);
            }
        }

        if ("size".equals(order)) {
            Collections.sort(fileList, new SizeComparator());
        } else if ("type".equals(order)) {
            Collections.sort(fileList, new TypeComparator());
        } else {
            Collections.sort(fileList, new NameComparator());
        }
        JSONObject result = new JSONObject();
        result.put("moveup_dir_path", moveupDirPath);
        result.put("current_dir_path", currentDirPath);
        result.put("current_url", currentUrl);
        result.put("total_count", fileList.size());
        result.put("file_list", fileList);

        logger.debug("json=" + result.toString());
        return (result.toString());
    }

    public class NameComparator implements Comparator {
        public int compare(Object a, Object b) {
            Hashtable hashA = (Hashtable) a;
            Hashtable hashB = (Hashtable) b;
            if (((Boolean) hashA.get("is_dir")) && !((Boolean) hashB.get("is_dir"))) {
                return -1;
            } else if (!((Boolean) hashA.get("is_dir")) && ((Boolean) hashB.get("is_dir"))) {
                return 1;
            } else {
                return ((String) hashA.get("filename")).compareTo((String) hashB.get("filename"));
            }
        }
    }

    public class SizeComparator implements Comparator {
        public int compare(Object a, Object b) {
            Hashtable hashA = (Hashtable) a;
            Hashtable hashB = (Hashtable) b;
            if (((Boolean) hashA.get("is_dir")) && !((Boolean) hashB.get("is_dir"))) {
                return -1;
            } else if (!((Boolean) hashA.get("is_dir")) && ((Boolean) hashB.get("is_dir"))) {
                return 1;
            } else {
                if (((Long) hashA.get("filesize")) > ((Long) hashB.get("filesize"))) {
                    return 1;
                } else if (((Long) hashA.get("filesize")) < ((Long) hashB.get("filesize"))) {
                    return -1;
                } else {
                    return 0;
                }
            }
        }
    }

    public class TypeComparator implements Comparator {
        public int compare(Object a, Object b) {
            Hashtable hashA = (Hashtable) a;
            Hashtable hashB = (Hashtable) b;
            if (((Boolean) hashA.get("is_dir")) && !((Boolean) hashB.get("is_dir"))) {
                return -1;
            } else if (!((Boolean) hashA.get("is_dir")) && ((Boolean) hashB.get("is_dir"))) {
                return 1;
            } else {
                return ((String) hashA.get("filetype")).compareTo((String) hashB.get("filetype"));
            }
        }
    }
}
