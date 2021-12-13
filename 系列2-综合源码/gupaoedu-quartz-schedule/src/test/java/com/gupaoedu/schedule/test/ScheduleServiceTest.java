package com.gupaoedu.schedule.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gupaoedu.schedule.entity.Task;
import com.gupaoedu.schedule.service.IScheduleService;



@ContextConfiguration(locations = {"classpath*:application-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class ScheduleServiceTest {

	@Autowired IScheduleService scheduleService;
	
	@Test
	public void testCreate(){
		try {
			Task task = scheduleService.createTask("动态创建的任务", "com.gupaoedu.timer.AnnotationTimer", "a", "0/5 * * * * ?");
			
			System.out.println("创建一个任务，任务池中的任务数量：" + scheduleService.getAllTask().size());
			
			Thread.sleep(1000 * 10);
			
			
			
			scheduleService.pauseTask(task.getId());
			
			System.out.println("暂停任务\"" + task.getName() + "\"，任务池中的任务数量：" + scheduleService.getAllTask().size());
			
			Thread.sleep(1000 * 10);
			
			
			
			scheduleService.restartTask(task.getId());
			
			System.out.println("重新启动任务\"" + task.getName() + "\"，任务池中的任务数量：" + scheduleService.getAllTask().size());
			
			Thread.sleep(1000 * 10);
			
			
			
			scheduleService.modifyTaskCron(task.getId(), "0/20 * * * * ?");
			
			System.out.println("修改任务\"" + task.getName() + "\"，任务池中的任务数量：" + scheduleService.getAllTask().size());
			
			Thread.sleep(1000 * 30);
			
			
			
			scheduleService.removeTask(task.getId());
			
			System.out.println("删除任务\"" + task.getName() + "\"，任务池中的任务数量：" + scheduleService.getAllTask().size());
			
			Thread.sleep(1000 * 60 * 10);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
