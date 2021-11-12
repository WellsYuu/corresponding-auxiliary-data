package com.xiangxue.ch8b;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.xiangxue.ch8b.assist.Consts;
import com.xiangxue.ch8b.assist.CreatePendingDocs;
import com.xiangxue.ch8b.assist.SL_QuestionBank;
import com.xiangxue.ch8b.service.ProduceDocService;
import com.xiangxue.ch8b.vo.SrcDocVo;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：rpc服务端，采用生产者消费者模式，生产者消费者还会级联
 */
public class RpcModeWeb {
	
	//负责生成文档
	private static ExecutorService docMakeService 
		= Executors.newFixedThreadPool(Consts.CPU_COUNT*2); 
	
	//负责上传文档
	private static ExecutorService docUploadService 
		= Executors.newFixedThreadPool(Consts.CPU_COUNT*2);
	
	private static CompletionService<String> docCs
	    = new ExecutorCompletionService<>(docMakeService); 
	
	private static CompletionService<String> docUploadCs
    = new ExecutorCompletionService<>(docUploadService);
	
	public static void main(String[] args) throws InterruptedException, 
	ExecutionException {
        System.out.println("题库开始初始化...........");
        SL_QuestionBank.initBank();
        System.out.println("题库初始化完成。");
        
        //创建两个待处理文档
        List<SrcDocVo> docList = CreatePendingDocs.makePendingDoc(60);
        long startTotal = System.currentTimeMillis();
        
        for(SrcDocVo doc:docList){
        	docCs.submit(new MakeDocTask(doc));
        }
        for(SrcDocVo doc:docList){
        	Future<String> future = docCs.take();
        	docUploadCs.submit(new UploadDocTask(future.get()));
        }

        //在实际的业务过程中可以不要，主要为了取得时间
        for(SrcDocVo doc:docList){
        	docUploadCs.take().get();
        }
        
        System.out.println("------------共耗时："
        		+(System.currentTimeMillis()-startTotal)+"ms-------------");
	}
	
	//生成文档的任务
	private static class MakeDocTask implements Callable<String>{
		
		private SrcDocVo pendingDocVo;
		
		public MakeDocTask(SrcDocVo pendingDocVo) {
			super();
			this.pendingDocVo = pendingDocVo;
		}

		@Override
		public String call() throws Exception {
			long start = System.currentTimeMillis();
            //String localName = ProduceDocService.makeDoc(pendingDocVo);
			String localName = ProduceDocService.makeDocAsyn(pendingDocVo);
            System.out.println("文档"+localName+"生成耗时："
            		+(System.currentTimeMillis()-start)+"ms");
			return localName;
		}
	}
	
	//上传文档的任务
	private static class UploadDocTask implements Callable<String>{
		
		private String filePath;
		
		public UploadDocTask(String filePath) {
			super();
			this.filePath = filePath;
		}

		@Override
		public String call() throws Exception {
			long start = System.currentTimeMillis();
			String remoteUrl = ProduceDocService.upLoadDoc(filePath);
            System.out.println("已上传至["+remoteUrl+"]耗时："
            		+(System.currentTimeMillis()-start)+"ms");
			return remoteUrl;
		}
	}
	
	

}
