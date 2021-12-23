package com.edu.example.service.impl;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.edu.example.bean.Order;
import com.edu.example.bean.OrderApply;
import com.edu.example.common.constants.Constant;
import com.edu.example.common.util.RespEntity;
import com.edu.example.dao.OrderApplyMapper;
import com.edu.example.dao.OrderMapper;
import com.edu.example.service.BankService;
import com.edu.example.service.OrderService;
@Service
public class OrderServiceImpl implements OrderService{
	private static Logger logger = LogManager.getLogger(OrderServiceImpl.class.getName());
	@Resource
	private OrderMapper orderMapper;
	@Resource
	private BankService BankService;
	@Resource
	private OrderApplyMapper orderApplyMapper;
	@Resource
	private TransactionTemplate transactionTemplate;
	
	
	/**
	 * 订单支付
	 * 
	 * 锁：
	 * 1. redis
	 * 2. 数据库锁
	 * 		悲观锁：for update
	 * 		乐观锁:
	 * 		1.基于版本号的乐观锁
	 * 		2.基于状态机的乐观锁
	 * 			update table set status=3 where id=? and status=0
	 */ 
	@Transactional(propagation=Propagation.NEVER)
	@Override
	public void pay(Order order){
		try {
			Integer applyId = transactionTemplate.execute(new TransactionCallback<Integer>() {
				@Override
				public Integer doInTransaction(TransactionStatus status) {
					// 基于状态机的乐观锁
					// update table set status=3 where id=? and status=0
					// 把状态变成处理中
					boolean lockStatus = 1==orderMapper.updateStatusByPrimaryKeyAndStatus(order.getId(), 
							Constant.ORDER_STATUS_DEALING, Constant.ORDER_STATUS_DEFAULT);
					if(lockStatus){
						OrderApply orderApply = new OrderApply();
						orderApply.setOrderId(order.getId() + "");
						orderApply.setStatus(1);// 假设为1,表示未处理状态
						orderApplyMapper.insert(orderApply);
						
						// 返回流水号，提供给后面使用
						return orderApply.getId();
					}
					return -1;// 如果没有锁到的话，返回-1
				}
			});
			// 说明获取到锁了
			if(applyId > 0){
				// 远程调用银行的扣款接口
				Order orderToBank = new Order();
				orderToBank.setId(applyId);// 使用订单申请流水号作为调用银行扣款的订单id
				orderToBank.setAmount(order.getAmount());
				
				RespEntity<Object> respEntity = BankService.outMoney(order);
				// 不适用于交易系统
				/*if(respEntity != null && respEntity.getKey().equals("0000")){
					order.setStatus(Constant.ORDER_STATUS_SUCCESS);// 成功
				}else{
					order.setStatus(Constant.ORDER_STATUS_FAIL);// 失败
				}*/
				// 替代方案
				if(respEntity != null){
					if(respEntity.getKey().equals("0000")){
						order.setStatus(Constant.ORDER_STATUS_SUCCESS);// 成功 1
					}else{
						order.setStatus(Constant.ORDER_STATUS_FAIL);// 失败 2
					}
					// 编程式事务，必须在一个事物中运行
					transactionTemplate.execute(new TransactionCallback<Object>() {
						@Override
						public Object doInTransaction(TransactionStatus status) {
							
							OrderApply orderApply = new OrderApply();
							orderApply.setId(applyId);
							orderApply.setStatus(order.getStatus());// 跟用户订单的状态一直
							orderApplyMapper.updateByPrimaryKeySelective(orderApply);
							
							orderMapper.updateByPrimaryKeySelective(order);
							return null;
						}
					});
				}
				
			}else{
				logger.error("锁失败了");
			}
		} catch (Exception e) {
			logger.error("下单失败",e);
		}
	}
	
	
	
	
	@Override
	public Order selectById(int id) {
		return this.orderMapper.selectById(id);
	}
	@Override
	public void deleteById(int id) {
		this.orderMapper.deleteById(id);
	}
	
	@Transactional
	@Override
	public void insertOrder(Order order) {
		orderMapper.insert(order);
	}
}
