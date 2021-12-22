package com.tl.transaction;


import org.apache.rocketmq.client.producer.LocalTransactionExecuter;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.common.message.Message;

/**
 * 执行本地事务
 */
public class TransactionExecuterImpl implements LocalTransactionExecuter {
	// private AtomicInteger transactionIndex = new AtomicInteger(1);

	/***
	 * 执行本地事物
	 * @param msg
	 * @param arg
	 * @return
	 */
	public LocalTransactionState executeLocalTransactionBranch(final Message msg, final Object arg) {

		System.out.println("执行本地事务msg = " + new String(msg.getBody()));
		String tags = msg.getTags();
	/*	if (tags.equals("transaction1")) {
			System.out.println("======异常  -进行ROLLBACK");
			throw new NullPointerException();
		}*/
	//ransaction1正确的
		if (tags.equals("transaction2")) {
			System.out.println("======我的操作============，失败了  -进行ROLLBACK");
			return LocalTransactionState.ROLLBACK_MESSAGE;
		}
		return LocalTransactionState.COMMIT_MESSAGE;
		//return LocalTransactionState.UNKNOW;
	}
}
