/*
 * Copyright 2002-2012 the original author or authors.
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

package org.springframework.test.context;

import static org.junit.Assert.*;

import java.util.Comparator;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.SpringRunnerContextCacheTests.OrderedMethodsSpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

/**
 * JUnit 4 based unit test which verifies correct {@link ContextCache
 * application context caching} in conjunction with the
 * {@link SpringJUnit4ClassRunner} and the {@link DirtiesContext
 * &#064;DirtiesContext} annotation at the method level.
 *
 * @author Sam Brannen
 * @author Juergen Hoeller
 * @since 2.5
 * @see ContextCacheTests
 */
@RunWith(OrderedMethodsSpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class })
@ContextConfiguration("junit4/SpringJUnit4ClassRunnerAppCtxTests-context.xml")
public class SpringRunnerContextCacheTests {

	private static ApplicationContext dirtiedApplicationContext;

	@Autowired
	protected ApplicationContext applicationContext;


	/**
	 * Asserts the statistics of the context cache in {@link TestContextManager}.
	 *
	 * @param usageScenario the scenario in which the statistics are used
	 * @param expectedSize the expected number of contexts in the cache
	 * @param expectedHitCount the expected hit count
	 * @param expectedMissCount the expected miss count
	 */
	private static final void assertContextCacheStatistics(String usageScenario, int expectedSize,
			int expectedHitCount, int expectedMissCount) {
		assertContextCacheStatistics(TestContextManager.contextCache, usageScenario, expectedSize, expectedHitCount,
			expectedMissCount);
	}

	/**
	 * Asserts the statistics of the supplied context cache.
	 *
	 * @param contextCache the cache to assert against
	 * @param usageScenario the scenario in which the statistics are used
	 * @param expectedSize the expected number of contexts in the cache
	 * @param expectedHitCount the expected hit count
	 * @param expectedMissCount the expected miss count
	 */
	public static final void assertContextCacheStatistics(ContextCache contextCache, String usageScenario,
			int expectedSize, int expectedHitCount, int expectedMissCount) {

		assertEquals("Verifying number of contexts in cache (" + usageScenario + ").", expectedSize,
			contextCache.size());
		assertEquals("Verifying number of cache hits (" + usageScenario + ").", expectedHitCount,
			contextCache.getHitCount());
		assertEquals("Verifying number of cache misses (" + usageScenario + ").", expectedMissCount,
			contextCache.getMissCount());
	}

	@BeforeClass
	public static void verifyInitialCacheState() {
		dirtiedApplicationContext = null;
		ContextCache contextCache = TestContextManager.contextCache;
		contextCache.clear();
		contextCache.clearStatistics();
		assertContextCacheStatistics("BeforeClass", 0, 0, 0);
	}

	@AfterClass
	public static void verifyFinalCacheState() {
		assertContextCacheStatistics("AfterClass", 1, 1, 2);
	}

	@Test
	@DirtiesContext
	public void dirtyContext() {
		assertContextCacheStatistics("dirtyContext()", 1, 0, 1);
		assertNotNull("The application context should have been autowired.", this.applicationContext);
		SpringRunnerContextCacheTests.dirtiedApplicationContext = this.applicationContext;
	}

	@Test
	public void verifyContextDirty() {
		assertContextCacheStatistics("verifyContextWasDirtied()", 1, 0, 2);
		assertNotNull("The application context should have been autowired.", this.applicationContext);
		assertNotSame("The application context should have been 'dirtied'.",
			SpringRunnerContextCacheTests.dirtiedApplicationContext, this.applicationContext);
		SpringRunnerContextCacheTests.dirtiedApplicationContext = this.applicationContext;
	}

	@Test
	public void verifyContextNotDirty() {
		assertContextCacheStatistics("verifyContextWasNotDirtied()", 1, 1, 2);
		assertNotNull("The application context should have been autowired.", this.applicationContext);
		assertSame("The application context should NOT have been 'dirtied'.",
			SpringRunnerContextCacheTests.dirtiedApplicationContext, this.applicationContext);
	}


	/**
	 * @since 3.2
	 */
	public static class OrderedMethodsSpringJUnit4ClassRunner extends SpringJUnit4ClassRunner {

		public OrderedMethodsSpringJUnit4ClassRunner(Class<?> clazz) throws InitializationError {
			super(clazz);
		}

		@Override
		protected List<FrameworkMethod> computeTestMethods() {
			List<FrameworkMethod> testMethods = super.computeTestMethods();

			java.util.Collections.sort(testMethods, new Comparator<FrameworkMethod>() {

				@Override
				public int compare(FrameworkMethod method1, FrameworkMethod method2) {
					return method1.getName().compareTo(method2.getName());
				}
			});

			return testMethods;
		}

	}

}
