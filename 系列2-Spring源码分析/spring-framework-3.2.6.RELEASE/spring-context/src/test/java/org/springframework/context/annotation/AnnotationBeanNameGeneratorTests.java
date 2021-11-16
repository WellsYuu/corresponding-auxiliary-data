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

package org.springframework.context.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.Test;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.SimpleBeanDefinitionRegistry;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import example.scannable.DefaultNamedComponent;
import static org.junit.Assert.*;

/**
 * @author Rick Evans
 * @author Juergen Hoeller
 * @author Mark Fisher
 * @author Chris Beams
 */
public class AnnotationBeanNameGeneratorTests {

	private AnnotationBeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator();


	@Test
	public void testGenerateBeanNameWithNamedComponent() {
		BeanDefinitionRegistry registry = new SimpleBeanDefinitionRegistry();
		AnnotatedBeanDefinition bd = new AnnotatedGenericBeanDefinition(ComponentWithName.class);
		String beanName = this.beanNameGenerator.generateBeanName(bd, registry);
		assertNotNull("The generated beanName must *never* be null.", beanName);
		assertTrue("The generated beanName must *never* be blank.", StringUtils.hasText(beanName));
		assertEquals("walden", beanName);
	}

	@Test
	public void testGenerateBeanNameWithDefaultNamedComponent() {
		BeanDefinitionRegistry registry = new SimpleBeanDefinitionRegistry();
		AnnotatedBeanDefinition bd = new AnnotatedGenericBeanDefinition(DefaultNamedComponent.class);
		String beanName = this.beanNameGenerator.generateBeanName(bd, registry);
		assertNotNull("The generated beanName must *never* be null.", beanName);
		assertTrue("The generated beanName must *never* be blank.", StringUtils.hasText(beanName));
		assertEquals("thoreau", beanName);
	}

	@Test
	public void testGenerateBeanNameWithNamedComponentWhereTheNameIsBlank() {
		BeanDefinitionRegistry registry = new SimpleBeanDefinitionRegistry();
		AnnotatedBeanDefinition bd = new AnnotatedGenericBeanDefinition(ComponentWithBlankName.class);
		String beanName = this.beanNameGenerator.generateBeanName(bd, registry);
		assertNotNull("The generated beanName must *never* be null.", beanName);
		assertTrue("The generated beanName must *never* be blank.", StringUtils.hasText(beanName));
		String expectedGeneratedBeanName = this.beanNameGenerator.buildDefaultBeanName(bd);
		assertEquals(expectedGeneratedBeanName, beanName);
	}

	@Test
	public void testGenerateBeanNameWithAnonymousComponentYieldsGeneratedBeanName() {
		BeanDefinitionRegistry registry = new SimpleBeanDefinitionRegistry();
		AnnotatedBeanDefinition bd = new AnnotatedGenericBeanDefinition(AnonymousComponent.class);
		String beanName = this.beanNameGenerator.generateBeanName(bd, registry);
		assertNotNull("The generated beanName must *never* be null.", beanName);
		assertTrue("The generated beanName must *never* be blank.", StringUtils.hasText(beanName));
		String expectedGeneratedBeanName = this.beanNameGenerator.buildDefaultBeanName(bd);
		assertEquals(expectedGeneratedBeanName, beanName);
	}

	@Test
	public void testGenerateBeanNameFromMetaComponentWithStringValue() {
		BeanDefinitionRegistry registry = new SimpleBeanDefinitionRegistry();
		AnnotatedBeanDefinition bd = new AnnotatedGenericBeanDefinition(ComponentFromStringMeta.class);
		String beanName = this.beanNameGenerator.generateBeanName(bd, registry);
		assertEquals("henry", beanName);
	}

	@Test
	public void testGenerateBeanNameFromMetaComponentWithNonStringValue() {
		BeanDefinitionRegistry registry = new SimpleBeanDefinitionRegistry();
		AnnotatedBeanDefinition bd = new AnnotatedGenericBeanDefinition(ComponentFromNonStringMeta.class);
		String beanName = this.beanNameGenerator.generateBeanName(bd, registry);
		assertEquals("annotationBeanNameGeneratorTests.ComponentFromNonStringMeta", beanName);
	}


	@Component("walden")
	private static class ComponentWithName {
	}


	@Component(" ")
	private static class ComponentWithBlankName {
	}


	@Component
	private static class AnonymousComponent {
	}

	@Service("henry")
	private static class ComponentFromStringMeta {
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	@Component
	public @interface NonStringMetaComponent {
		long value();
	}

	@NonStringMetaComponent(123)
	private static class ComponentFromNonStringMeta {
	}

}
