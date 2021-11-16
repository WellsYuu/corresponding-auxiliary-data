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

package org.springframework.aop.aspectj.autoproxy;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Adrian Colyer
 * @author Chris Beams
 */
public final class AnnotationBindingTests {

	private AnnotatedTestBean testBean;

	@Before
	public void setUp() {
		ClassPathXmlApplicationContext ctx =
			new ClassPathXmlApplicationContext(getClass().getSimpleName() + "-context.xml", getClass());

		testBean = (AnnotatedTestBean) ctx.getBean("testBean");
	}

	@Test
	public void testAnnotationBindingInAroundAdvice() {
		assertEquals("this value", testBean.doThis());
		assertEquals("that value", testBean.doThat());
	}

	@Test
	public void testNoMatchingWithoutAnnotationPresent() {
		assertEquals("doTheOther", testBean.doTheOther());
	}

}
