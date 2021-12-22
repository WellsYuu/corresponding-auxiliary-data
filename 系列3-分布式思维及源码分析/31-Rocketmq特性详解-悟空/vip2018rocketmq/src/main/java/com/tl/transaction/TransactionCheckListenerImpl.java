package com.tl.transaction;


import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionCheckListener;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * 未决事务，服务器回查客户端
 */
public class TransactionCheckListenerImpl implements TransactionCheckListener {
	// private AtomicInteger transactionIndex = new AtomicInteger(0);

	//在这里，我们可以根据由MQ回传的key去数据库查询，这条数据到底是成功了还是失败了。
	public LocalTransactionState checkLocalTransactionState(MessageExt msg) {
		System.out.println("未决事务，服务器回查客户端msg =" + new String(msg.getBody().toString()));
		// return LocalTransactionState.ROLLBACK_MESSAGE;

		return LocalTransactionState.COMMIT_MESSAGE;

		// return LocalTransactionState.UNKNOW;
	}
}