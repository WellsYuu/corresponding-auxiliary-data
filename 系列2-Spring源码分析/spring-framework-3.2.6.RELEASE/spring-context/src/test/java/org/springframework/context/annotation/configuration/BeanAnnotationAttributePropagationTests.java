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

package org.springframework.context.annotation.configuration;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.DependsOn;

/**
 * Unit tests proving that the various attributes available via the {@link Bean}
 * annotation are correctly reflected in the {@link BeanDefinition} created when
 * processing the {@link Configuration} class.
 *
 * <p>Also includes tests proving that using {@link Lazy} and {@link Primary}
 * annotations in conjunction with Bean propagate their respective metadata
 * correctly into the resulting BeanDefinition
 *
 * @author Chris Beams
 */
public class BeanAnnotationAttributePropagationTests {

	@Test
	public void initMethodMetadataIsPropagated() {
		@Configuration class Config {
			@Bean(initMethod="start") Object foo() { return null; }
		}

		assertEquals("init method name was not propagated",
				"start", beanDef(Config.class).getInitMethodName());
	}

	@Test
	public void destroyMethodMetadataIsPropagated() {
		@Configuration class Config {
			@Bean(destroyMethod="destroy") Object foo() { return null; }
		}

		assertEquals("destroy method name was not propagated",
				"destroy", beanDef(Config.class).getDestroyMethodName());
	}

	@Test
	public void dependsOnMetadataIsPropagated() {
		@Configuration class Config {
			@Bean() @DependsOn({"bar", "baz"}) Object foo() { return null; }
		}

		assertArrayEquals("dependsOn metadata was not propagated",
				new String[] {"bar", "baz"}, beanDef(Config.class).getDependsOn());
	}

	@Test
	public void primaryMetadataIsPropagated() {
		@Configuration class Config {
			@Primary @Bean
			Object foo() { return null; }
		}

		assertTrue("primary metadata was not propagated",
				beanDef(Config.class).isPrimary());
	}

	@Test
	public void primaryMetadataIsFalseByDefault() {
		@Configuration class Config {
			@Bean Object foo() { return null; }
		}

		assertFalse("@Bean methods should be non-primary by default",
				beanDef(Config.class).isPrimary());
	}

	@Test
	public void lazyMetadataIsPropagated() {
		@Configuration class Config {
			@Lazy @Bean
			Object foo() { return null; }
		}

		assertTrue("lazy metadata was not propagated",
				beanDef(Config.class).isLazyInit());
	}

	@Test
	public void lazyMetadataIsFalseByDefault() {
		@Configuration class Config {
			@Bean Object foo() { return null; }
		}

		assertFalse("@Bean methods should be non-lazy by default",
				beanDef(Config.class).isLazyInit());
	}

	@Test
	public void defaultLazyConfigurationPropagatesToIndividualBeans() {
		@Lazy @Configuration class Config {
			@Bean Object foo() { return null; }
		}

		assertTrue("@Bean methods declared in a @Lazy @Configuration should be lazily instantiated",
				beanDef(Config.class).isLazyInit());
	}

	@Test
	public void eagerBeanOverridesDefaultLazyConfiguration() {
		@Lazy @Configuration class Config {
			@Lazy(false) @Bean Object foo() { return null; }
		}

		assertFalse("@Lazy(false) @Bean methods declared in a @Lazy @Configuration should be eagerly instantiated",
				beanDef(Config.class).isLazyInit());
	}

	@Test
	public void eagerConfigurationProducesEagerBeanDefinitions() {
		@Lazy(false) @Configuration class Config { // will probably never happen, doesn't make much sense
			@Bean Object foo() { return null; }
		}

		assertFalse("@Lazy(false) @Configuration should produce eager bean definitions",
				beanDef(Config.class).isLazyInit());
	}

	private AbstractBeanDefinition beanDef(Class<?> configClass) {
		DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
		factory.registerBeanDefinition("config", new RootBeanDefinition(configClass));
		ConfigurationClassPostProcessor pp = new ConfigurationClassPostProcessor();
		pp.postProcessBeanFactory(factory);
		return (AbstractBeanDefinition) factory.getBeanDefinition("foo");
	}

}
