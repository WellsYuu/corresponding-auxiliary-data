package com.gupaoedu.common.resolvers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.gupaoedu.common.listeners.UploadListener;


/**
 * 文件上传进度响应
 * @author Tom
 *
 */
public class UploadResolver extends CommonsMultipartResolver {
	
	@Override
	public MultipartParsingResult parseRequest(HttpServletRequest request) throws MultipartException {
		
		MultiValueMap<String, MultipartFile> multipartFiles = new LinkedMultiValueMap<String, MultipartFile>();
		Map<String, String[]> multipartParameters = new HashMap<String, String[]>();
		Map<String, String> multipartParameterContentTypes = new HashMap<String, String>();
		MultipartParsingResult empty = new MultipartParsingResult(multipartFiles, multipartParameters, multipartParameterContentTypes);
		
		if(isMultipart(request)){
			String progressId = request.getParameter("X-Progress-ID");
			String encoding = determineEncoding(request);
			FileUpload fileUpload = prepareFileUpload(encoding);
			UploadListener listener = new UploadListener(progressId);
			fileUpload.setProgressListener(listener);
			request.getSession().setAttribute("process_" + progressId, listener);
			try {
				List<FileItem> fileItems = ((ServletFileUpload) fileUpload).parseRequest(request);
				return parseFileItems(fileItems, encoding);
			}catch(Exception e){
				return empty;
			}
//			catch (FileUploadBase.SizeLimitExceededException ex) {
//				throw new MaxUploadSizeExceededException(fileUpload.getSizeMax(), ex);
//			}
//			catch (FileUploadException ex) {
//				throw new MultipartException("无法解析此请求", ex);
//			}
		}else{
			return empty;
		}
	}
	
}
