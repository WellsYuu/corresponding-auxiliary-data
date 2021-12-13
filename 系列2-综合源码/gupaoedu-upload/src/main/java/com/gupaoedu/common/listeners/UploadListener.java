package com.gupaoedu.common.listeners;

import org.apache.commons.fileupload.ProgressListener;

/**
 * 文件上传进度监听
 * @author Tom
 *
 */
public class UploadListener implements ProgressListener{
	
	public Progress progress = new Progress();
	
	public UploadListener(){
		
	}
	
	public UploadListener(String progressId){
		progress.progressId = progressId;
	}
	
	/**
	 * 此方法由框架自动调用
	 */
	@Override
	/**
	 * received 已经接收到了多少
	 * length 文件的总大小
	 */
	public void update(long received, long length, int items) {
		progress.received = received;
		progress.size = length;
		
		if(received >= length){
			progress.finish = 1;
		}
	}

	
	//======  为了方便打包输出结果，所以在这里定义上传进度信息       ======
	/**
	 * 上传进度信息
	 * @author Tom
	 *
	 */
	class Progress{
		private String progressId = "";  //识别进度的ID
		private long received = 0;		
		private long size = 0;
		private long finish = 0;		 //上传是否结束
		
		public long getReceived() {
			return received;
		}
		public void setReceived(long received) {
			this.received = received;
		}
		public long getSize() {
			return size;
		}
		public void setSize(long size) {
			this.size = size;
		}
		public long getFinish() {
			return finish;
		}
		public void setFinish(long finish) {
			this.finish = finish;
		}
		public String getProgressId() {
			return progressId;
		}
		public void setProgressId(String progressId) {
			this.progressId = progressId;
		}
		
		
	}
	
}
