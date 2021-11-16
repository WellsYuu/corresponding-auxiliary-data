/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.scheduling.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.Future;

import org.junit.Before;
import org.junit.Test;

import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.tests.Assume;
import org.springframework.tests.TestGroup;

import static org.junit.Assert.*;

/**
 * @author Juergen Hoeller
 * @author Chris Beams
 */
public class AsyncExecutionTests {

	private static String originalThreadName;

	private static int listenerCalled = 0;

	private static int listenerConstructed = 0;

	@Before
	public void setUp() {
		Assume.group(TestGroup.PERFORMANCE);
	}

	@Test
	public void asyncMethods() throws Exception {
		originalThreadName = Thread.currentThread().getName();
		GenericApplicationContext context = new GenericApplicationContext();
		context.registerBeanDefinition("asyncTest", new RootBeanDefinition(AsyncMethodBean.class));
		context.registerBeanDefinition("autoProxyCreator", new RootBeanDefinition(DefaultAdvisorAutoProxyCreator.class));
		context.registerBeanDefinition("asyncAdvisor", new RootBeanDefinition(AsyncAnnotationAdvisor.class));
		context.refresh();
		AsyncMethodBean asyncTest = context.getBean("asyncTest", AsyncMethodBean.class);
		asyncTest.doNothing(5);
		asyncTest.doSomething(10);
		Future<String> future = asyncTest.returnSomething(20);
		assertEquals("20", future.get());
	}

	@Test
	public void asyncMethodsThroughInterface() throws Exception {
		originalThreadName = Thread.currentThread().getName();
		GenericApplicationContext context = new GenericApplicationContext();
		context.registerBeanDefinition("asyncTest", new RootBeanDefinition(SimpleAsyncMethodBean.class));
		context.registerBeanDefinition("autoProxyCreator", new RootBeanDefinition(DefaultAdvisorAutoProxyCreator.class));
		context.registerBeanDefinition("asyncAdvisor", new RootBeanDefinition(AsyncAnnotationAdvisor.class));
		context.refresh();
		SimpleInterface asyncTest = context.getBean("asyncTest", SimpleInterface.class);
		asyncTest.doNothing(5);
		asyncTest.doSomething(10);
		Future<String> future = asyncTest.returnSomething(20);
		assertEquals("20", future.get());
	}

	@Test
	public void asyncMethodsWithQualifier() throws Exception {
		originalThreadName = Thread.currentThread().getName();
		GenericApplicationContext context = new GenericApplicationContext();
		context.registerBeanDefinition("asyncTest", new RootBeanDefinition(AsyncMethodWithQualifierBean.class));
		context.registerBeanDefinition("autoProxyCreator", new RootBeanDefinition(DefaultAdvisorAutoProxyCreator.class));
		context.registerBeanDefinition("asyncAdvisor", new RootBeanDefinition(AsyncAnnotationAdvisor.class));
		context.registerBeanDefinition("e0", new RootBeanDefinition(ThreadPoolTaskExecutor.class));
		context.registerBeanDefinition("e1", new RootBeanDefinition(ThreadPoolTaskExecutor.class));
		context.registerBeanDefinition("e2", new RootBeanDefinition(ThreadPoolTaskExecutor.class));
		context.refresh();
		AsyncMethodWithQualifierBean asyncTest = context.getBean("asyncTest", AsyncMethodWithQualifierBean.class);
		asyncTest.doNothing(5);
		asyncTest.doSomething(10);
		Future<String> future = asyncTest.returnSomething(20);
		assertEquals("20", future.get());
		Future<String> future2 = asyncTest.returnSomething2(30);
		assertEquals("30", future2.get());
	}

	@Test
	public void asyncMethodsWithQualifierThroughInterface() throws Exception {
		originalThreadName = Thread.currentThread().getName();
		GenericApplicationContext context = new GenericApplicationContext();
		context.registerBeanDefinition("asyncTest", new RootBeanDefinition(SimpleAsyncMethodWithQualifierBean.class));
		context.registerBeanDefinition("autoProxyCreator", new RootBeanDefinition(DefaultAdvisorAutoProxyCreator.class));
		context.registerBeanDefinition("asyncAdvisor", new RootBeanDefinition(AsyncAnnotationAdvisor.class));
		context.registerBeanDefinition("e0", new RootBeanDefinition(ThreadPoolTaskExecutor.class));
		context.registerBeanDefinition("e1", new RootBeanDefinition(ThreadPoolTaskExecutor.class));
		context.registerBeanDefinition("e2", new RootBeanDefinition(ThreadPoolTaskExecutor.class));
		context.refresh();
		SimpleInterface asyncTest = context.getBean("asyncTest", SimpleInterface.class);
		asyncTest.doNothing(5);
		asyncTest.doSomething(10);
		Future<String> future = asyncTest.returnSomething(20);
		assertEquals("20", future.get());
		Future<String> future2 = asyncTest.returnSomething2(30);
		assertEquals("30", future2.get());
	}

	@Test
	public void asyncClass() throws Exception {
		originalThreadName = Thread.currentThread().getName();
		GenericApplicationContext context = new GenericApplicationContext();
		context.registerBeanDefinition("asyncTest", new RootBeanDefinition(AsyncClassBean.class));
		context.registerBeanDefinition("autoProxyCreator", new RootBeanDefinition(DefaultAdvisorAutoProxyCreator.class));
		context.registerBeanDefinition("asyncAdvisor", new RootBeanDefinition(AsyncAnnotationAdvisor.class));
		context.refresh();
		AsyncClassBean asyncTest = context.getBean("asyncTest", AsyncClassBean.class);
		asyncTest.doSomething(10);
		Future<String> future = asyncTest.returnSomething(20);
		assertEquals("20", future.get());
	}

	@Test
	public void asyncInterface() throws Exception {
		originalThreadName = Thread.currentThread().getName();
		GenericApplicationContext context = new GenericApplicationContext();
		context.registerBeanDefinition("asyncTest", new RootBeanDefinition(AsyncInterfaceBean.class));
		context.registerBeanDefinition("autoProxyCreator", new RootBeanDefinition(DefaultAdvisorAutoProxyCreator.class));
		context.registerBeanDefinition("asyncAdvisor", new RootBeanDefinition(AsyncAnnotationAdvisor.class));
		context.refresh();
		AsyncInterface asyncTest = context.getBean("asyncTest", AsyncInterface.class);
		asyncTest.doSomething(10);
		Future<String> future = asyncTest.returnSomething(20);
		assertEquals("20", future.get());
	}

	@Test
	public void asyncMethodsInInterface() throws Exception {
		originalThreadName = Thread.currentThread().getName();
		GenericApplicationContext context = new GenericApplicationContext();
		context.registerBeanDefinition("asyncTest", new RootBeanDefinition(AsyncMethodsInterfaceBean.class));
		context.registerBeanDefinition("autoProxyCreator", new RootBeanDefinition(DefaultAdvisorAutoProxyCreator.class));
		context.registerBeanDefinition("asyncAdvisor", new RootBeanDefinition(AsyncAnnotationAdvisor.class));
		context.refresh();
		AsyncMethodsInterface asyncTest = context.getBean("asyncTest", AsyncMethodsInterface.class);
		asyncTest.doNothing(5);
		asyncTest.doSomething(10);
		Future<String> future = asyncTest.returnSomething(20);
		assertEquals("20", future.get());
	}

	@Test
	public void asyncMethodListener() throws Exception {
		originalThreadName = Thread.currentThread().getName();
		listenerCalled = 0;
		GenericApplicationContext context = new GenericApplicationContext();
		context.registerBeanDefinition("asyncTest", new RootBeanDefinition(AsyncMethodListener.class));
		context.registerBeanDefinition("autoProxyCreator", new RootBeanDefinition(DefaultAdvisorAutoProxyCreator.class));
		context.registerBeanDefinition("asyncAdvisor", new RootBeanDefinition(AsyncAnnotationAdvisor.class));
		context.refresh();
		Thread.sleep(1000);
		assertEquals(1, listenerCalled);
	}

	@Test
	public void asyncClassListener() throws Exception {
		originalThreadName = Thread.currentThread().getName();
		listenerCalled = 0;
		listenerConstructed = 0;
		GenericApplicationContext context = new GenericApplicationContext();
		context.registerBeanDefinition("asyncTest", new RootBeanDefinition(AsyncClassListener.class));
		context.registerBeanDefinition("autoProxyCreator", new RootBeanDefinition(DefaultAdvisorAutoProxyCreator.class));
		context.registerBeanDefinition("asyncAdvisor", new RootBeanDefinition(AsyncAnnotationAdvisor.class));
		context.refresh();
		context.close();
		Thread.sleep(1000);
		assertEquals(2, listenerCalled);
		assertEquals(1, listenerConstructed);
	}

	@Test
	public void asyncPrototypeClassListener() throws Exception {
		originalThreadName = Thread.currentThread().getName();
		listenerCalled = 0;
		listenerConstructed = 0;
		GenericApplicationContext context = new GenericApplicationContext();
		RootBeanDefinition listenerDef = new RootBeanDefinition(AsyncClassListener.class);
		listenerDef.setScope(RootBeanDefinition.SCOPE_PROTOTYPE);
		context.registerBeanDefinition("asyncTest", listenerDef);
		context.registerBeanDefinition("autoProxyCreator", new RootBeanDefinition(DefaultAdvisorAutoProxyCreator.class));
		context.registerBeanDefinition("asyncAdvisor", new RootBeanDefinition(AsyncAnnotationAdvisor.class));
		context.refresh();
		context.close();
		Thread.sleep(1000);
		assertEquals(2, listenerCalled);
		assertEquals(2, listenerConstructed);
	}


	public interface SimpleInterface {

		void doNothing(int i);

		void doSomething(int i);

		Future<String> returnSomething(int i);

		Future<String> returnSomething2(int i);
	}


	public static class AsyncMethodBean {

		public void doNothing(int i) {
			assertTrue(Thread.currentThread().getName().equals(originalThreadName));
		}

		@Async
		public void doSomething(int i) {
			assertTrue(!Thread.currentThread().getName().equals(originalThreadName));
		}

		@Async
		public Future<String> returnSomething(int i) {
			assertTrue(!Thread.currentThread().getName().equals(originalThreadName));
			return new AsyncResult<String>(Integer.toString(i));
		}
	}


	public static class SimpleAsyncMethodBean extends AsyncMethodBean implements SimpleInterface {

		@Override
		public Future<String> returnSomething2(int i) {
			throw new UnsupportedOperationException();
		}
	}


	@Async("e0")
	public static class AsyncMethodWithQualifierBean {

		public void doNothing(int i) {
			assertTrue(Thread.currentThread().getName().equals(originalThreadName));
		}

		@Async("e1")
		public void doSomething(int i) {
			assertTrue(!Thread.currentThread().getName().equals(originalThreadName));
			assertTrue(Thread.currentThread().getName().startsWith("e1-"));
		}

		@MyAsync
		public Future<String> returnSomething(int i) {
			assertTrue(!Thread.currentThread().getName().equals(originalThreadName));
			assertTrue(Thread.currentThread().getName().startsWith("e2-"));
			return new AsyncResult<String>(Integer.toString(i));
		}

		public Future<String> returnSomething2(int i) {
			assertTrue(!Thread.currentThread().getName().equals(originalThreadName));
			assertTrue(Thread.currentThread().getName().startsWith("e0-"));
			return new AsyncResult<String>(Integer.toString(i));
		}
	}


	public static class SimpleAsyncMethodWithQualifierBean extends AsyncMethodWithQualifierBean implements SimpleInterface {
	}


	@Async("e2")
	@Retention(RetentionPolicy.RUNTIME)
	public @interface MyAsync {
	}


	@Async
	public static class AsyncClassBean {

		public void doSomething(int i) {
			assertTrue(!Thread.currentThread().getName().equals(originalThreadName));
		}

		public Future<String> returnSomething(int i) {
			assertTrue(!Thread.currentThread().getName().equals(originalThreadName));
			return new AsyncResult<String>(Integer.toString(i));
		}
	}


	@Async
	public interface AsyncInterface {

		void doSomething(int i);

		Future<String> returnSomething(int i);
	}


	public static class AsyncInterfaceBean implements AsyncInterface {

		@Override
		public void doSomething(int i) {
			assertTrue(!Thread.currentThread().getName().equals(originalThreadName));
		}

		@Override
		public Future<String> returnSomething(int i) {
			assertTrue(!Thread.currentThread().getName().equals(originalThreadName));
			return new AsyncResult<String>(Integer.toString(i));
		}
	}


	public interface AsyncMethodsInterface {

		void doNothing(int i);

		@Async
		void doSomething(int i);

		@Async
		Future<String> returnSomething(int i);
	}


	public static class AsyncMethodsInterfaceBean implements AsyncMethodsInterface {

		@Override
		public void doNothing(int i) {
			assertTrue(Thread.currentThread().getName().equals(originalThreadName));
		}

		@Override
		public void doSomething(int i) {
			assertTrue(!Thread.currentThread().getName().equals(originalThreadName));
		}

		@Override
		public Future<String> returnSomething(int i) {
			assertTrue(!Thread.currentThread().getName().equals(originalThreadName));
			return new AsyncResult<String>(Integer.toString(i));
		}
	}


	public static class AsyncMethodListener implements ApplicationListener<ApplicationEvent> {

		@Override
		@Async
		public void onApplicationEvent(ApplicationEvent event) {
			listenerCalled++;
			assertTrue(!Thread.currentThread().getName().equals(originalThreadName));
		}
	}


	@Async
	public static class AsyncClassListener implements ApplicationListener<ApplicationEvent> {

		public AsyncClassListener() {
			listenerConstructed++;
		}

		@Override
		public void onApplicationEvent(ApplicationEvent event) {
			listenerCalled++;
			assertTrue(!Thread.currentThread().getName().equals(originalThreadName));
		}
	}

}
