package com.jiagouedu.core.task;

import com.jiagouedu.core.front.SystemManager;
import com.jiagouedu.services.manage.order.OrderService;
import com.jiagouedu.services.manage.order.bean.Order;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * 一周内部支付的订单，系统自动帮其取消
 * @author wukong 图灵学院 QQ:245553999
 *
 */
@Component
public class CancelOrderTask implements Runnable{
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(CancelOrderTask.class);
	@Autowired
	private OrderService orderService;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	@Override
	public void run() {
		while(true){
			try {
//				TimeUnit.DAYS.sleep(1);
				TimeUnit.SECONDS.sleep(Long.valueOf(SystemManager.getInstance().getProperty("task_SystemAutoNotifyTask_time")));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			logger.error("OrderCancelTask.run...");
			Order order = new Order();
			order.setStartDate(sdf.format(DateUtils.addDays(new Date(), -7)));
			List<Order> list = orderService.selectCancelList(order);
			if(list!=null){
				logger.error("list="+list.size());
				for(int i=0;i<list.size();i++){
					Order orderInfo = list.get(i);
					
					orderService.cancelOrderByID(orderInfo.getId());
				}
			}
		}
	}
	
//	public static void main(String[] args) {
//		System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(DateUtils.addDays(new Date(), -7)));
//	}

}
