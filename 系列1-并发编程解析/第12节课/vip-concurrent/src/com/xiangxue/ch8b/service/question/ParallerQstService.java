package com.xiangxue.ch8b.service.question;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import com.xiangxue.ch8b.assist.Consts;
import com.xiangxue.ch8b.assist.SL_QuestionBank;
import com.xiangxue.ch8b.vo.QuestionInCacheVo;
import com.xiangxue.ch8b.vo.QuestionInDBVo;
import com.xiangxue.ch8b.vo.TaskResultVo;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：并发处理题目的服务
 */
public class ParallerQstService {
	
	//已处理题目的缓存
	private static ConcurrentHashMap<Integer, QuestionInCacheVo> questionCache 
	  = new ConcurrentHashMap<>();
	
	//正在处理题目的缓存
	private static ConcurrentHashMap<Integer, Future<QuestionInCacheVo>> 
		processingQuestionCache = new ConcurrentHashMap<>();
	
	private static ExecutorService makeQuestionService 
	= Executors.newFixedThreadPool(Consts.CPU_COUNT*2); 
	
	public static TaskResultVo makeQuestion(Integer questionId) {
		QuestionInCacheVo qstCacheVo = questionCache.get(questionId);
		if(null==qstCacheVo) {
			System.out.println("......题目["+questionId+"]在缓存中不存在，"
					+ "准备启动任务.");
			return new TaskResultVo(getQstFuture(questionId));
		}else {
			//拿摘要
			String questionSha = SL_QuestionBank.getSha(questionId);
			if(questionSha.equals(qstCacheVo.getQuestionSha())) {
				System.out.println("......题目["+questionId+"]在缓存中已存在，且未变化.");
				return new TaskResultVo(qstCacheVo.getQuestionDetail());
			}else {
				System.out.println("......题目["+questionId+"]在缓存中已存在，"
						+ "但是发生了变化，更新缓冲.");
				return new TaskResultVo(getQstFuture(questionId));
			}
		}
	}
	
	private static Future<QuestionInCacheVo> getQstFuture(Integer questionId){
		Future<QuestionInCacheVo> questionFuture 
			= processingQuestionCache.get(questionId);
		try {
			if(questionFuture==null) {
				QuestionInDBVo qstDbVo = SL_QuestionBank.getQuetion(questionId);
				QuestionTask questionTask = new QuestionTask(qstDbVo,questionId);
				/*不靠谱的，无法避免两个线程处理同一个题目
				questionFuture = makeQuestionService.submit(questionTask);
				processingQuestionCache.putIfAbsent(questionId, questionFuture);
				改成
				processingQuestionCache.putIfAbsent(questionId, questionFuture);
				questionFuture = makeQuestionService.submit(questionTask);
				也不行
				*/
				FutureTask<QuestionInCacheVo> ft 
					= new FutureTask<QuestionInCacheVo>(questionTask);
				questionFuture = processingQuestionCache.putIfAbsent(questionId, 
						ft);
				if(questionFuture==null) {
					//先在map中占位
					questionFuture = ft;
					makeQuestionService.execute(ft);
					System.out.println("成功启动了题目["+questionId+"]的计算任务，请等待完成>>>>>>>>");
				}else {
					System.out.println("<<<<<<<<<<<有其他线程刚刚启动了题目["+questionId
							+"]的计算任务，本任务无需开启！");
				}
			}else {
				 System.out.println("题目[]已存在计算任务，无需重新生成.");
			}
		} catch (Exception e) {
			processingQuestionCache.remove(questionId);
			e.printStackTrace();
			throw e;
			
		}
		return questionFuture;
	}
	
	
	//解析题目的任务类
	private static class QuestionTask implements Callable<QuestionInCacheVo>{
		
		private QuestionInDBVo qstDbVo;
		private Integer questionId;
		
		public QuestionTask(QuestionInDBVo qstDbVo, Integer questionId) {
			super();
			this.qstDbVo = qstDbVo;
			this.questionId = questionId;
		}

		@Override
		public QuestionInCacheVo call() throws Exception {
			try {
				String qstDetail = BaseQuestionProcessor.makeQuestion(questionId,
						SL_QuestionBank.getQuetion(questionId).getDetail());
				String questionSha = qstDbVo.getSha();
				QuestionInCacheVo qstCache = new QuestionInCacheVo(qstDetail, questionSha);
				questionCache.put(questionId, qstCache);
				return qstCache;
			} finally {
				//不管生成题目的任务正常与否，这个任务都要从正在处理题目的缓存中移除
				processingQuestionCache.remove(questionId);
			}
		}
		
	}

}
