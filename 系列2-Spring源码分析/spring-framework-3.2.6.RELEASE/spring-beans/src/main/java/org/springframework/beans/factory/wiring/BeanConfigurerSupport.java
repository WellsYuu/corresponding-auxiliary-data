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

package org.springframework.beans.factory.wiring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.BeanCurrentlyInCreationException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

/**
 * Convenient base class for bean configurers that can perform Dependency Injection
 * on objects (however they may be created). Typically subclassed by AspectJ aspects.
 *
 * <p>Subclasses may also need a custom metadata resolution strategy, in the
 * {@link BeanWiringInfoResolver} interface. The default implementation looks for
 * a bean with the same name as the fully-qualified class name. (This is the default
 * name of the bean in a Spring XML file if the '{@code id}' attribute is not used.)

 * @author Rob Harrop
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @author Adrian Colyer
 * @since 2.0
 * @see #setBeanWiringInfoResolver
 * @see ClassNameBeanWiringInfoResolver
 */
public class BeanConfigurerSupport implements BeanFactoryAware, InitializingBean, DisposableBean {

	/** Logger available to subclasses */
	protected final Log logger = LogFactory.getLog(getClass());

	private volatile BeanWiringInfoResolver beanWiringInfoResolver;

	private volatile ConfigurableListableBeanFactory beanFactory;


	/**
	 * Set the {@link BeanWiringInfoResolver} to use.
	 * <p>The default behavior is to look for a bean with the same name as the class.
	 * As an alternative, consider using annotation-driven bean wiring.
	 * @see ClassNameBeanWiringInfoResolver
	 * @see org.springframework.beans.factory.annotation.AnnotationBeanWiringInfoResolver
	 */
	public void setBeanWiringInfoResolver(BeanWiringInfoResolver beanWiringInfoResolver) {
		Assert.notNull(beanWiringInfoResolver, "BeanWiringInfoResolver must not be null");
		this.beanWiringInfoResolver = beanWiringInfoResolver;
	}

	/**
	 * Set the {@link BeanFactory} in which this aspect must configure beans.
	 */
	public void setBeanFactory(BeanFactory beanFactory) {
		if (!(beanFactory instanceof ConfigurableListableBeanFactory)) {
			throw new IllegalArgumentException(
				 "Bean configurer aspect needs to run in a ConfigurableListableBeanFactory: " + beanFactory);
		}
		this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
		if (this.beanWiringInfoResolver == null) {
			this.beanWiringInfoResolver = createDefaultBeanWiringInfoResolver();
		}
	}

	/**
	 * Create the default BeanWiringInfoResolver to be used if none was
	 * specified explicitly.
	 * <p>The default implementation builds a {@link ClassNameBeanWiringInfoResolver}.
	 * @return the default BeanWiringInfoResolver (never {@code null})
	 */
	protected BeanWiringInfoResolver createDefaultBeanWiringInfoResolver() {
		return new ClassNameBeanWiringInfoResolver();
	}

	/**
	 * Check that a {@link BeanFactory} has been set.
	 */
	public void afterPropertiesSet() {
		Assert.notNull(this.beanFactory, "BeanFactory must be set");
	}

	/**
	 * Release references to the {@link BeanFactory} and
	 * {@link BeanWiringInfoResolver} when the container is destroyed.
	 */
	public void destroy() {
		this.beanFactory = null;
		this.beanWiringInfoResolver = null;
	}


	/**
	 * Configure the bean instance.
	 * <p>Subclasses can override this to provide custom configuration logic.
	 * Typically called by an aspect, for all bean instances matched by a pointcut.
	 * @param beanInstance the bean instance to configure (must <b>not</b> be {@code null})
	 */
	public void configureBean(Object beanInstance) {
		if (this.beanFactory == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("BeanFactory has not been set on " + ClassUtils.getShortName(getClass()) + ": " +
						"Make sure this configurer runs in a Spring container. Unable to configure bean of type [" +
						ClassUtils.getDescriptiveType(beanInstance) + "]. Proceeding without injection.");
			}
			return;
		}

		BeanWiringInfo bwi = this.beanWiringInfoResolver.resolveWiringInfo(beanInstance);
		if (bwi == null) {
			// Skip the bean if no wiring info given.
			return;
		}

		try {
			if (bwi.indicatesAutowiring() ||
					(bwi.isDefaultBeanName() && !this.beanFactory.containsBean(bwi.getBeanName()))) {
				// Perform autowiring (also applying standard factory / post-processor callbacks).
				this.beanFactory.autowireBeanProperties(beanInstance, bwi.getAutowireMode(), bwi.getDependencyCheck());
				Object result = this.beanFactory.initializeBean(beanInstance, bwi.getBeanName());
				checkExposedObject(result, beanInstance);
			}
			else {
				// Perform explicit wiring based on the specified bean definition.
				Object result = this.beanFactory.configureBean(beanInstance, bwi.getBeanName());
				checkExposedObject(result, beanInstance);
			}
		}
		catch (BeanCreationException ex) {
			Throwable rootCause = ex.getMostSpecificCause();
			if (rootCause instanceof BeanCurrentlyInCreationException) {
				BeanCreationException bce = (BeanCreationException) rootCause;
				if (this.beanFactory.isCurrentlyInCreation(bce.getBeanName())) {
					if (logger.isDebugEnabled()) {
						logger.debug("Failed to create target bean '" + bce.getBeanName() +
								"' while configuring object of type [" + beanInstance.getClass().getName() +
								"] - probably due to a circular reference. This is a common startup situation " +
								"and usually not fatal. Proceeding without injection. Original exception: " + ex);
					}
					return;
				}
			}
			throw ex;
		}
	}

	private void checkExposedObject(Object exposedObject, Object originalBeanInstance) {
		if (exposedObject != originalBeanInstance) {
			throw new IllegalStateException("Post-processor tried to replace bean instance of type [" +
					originalBeanInstance.getClass().getName() + "] with (proxy) object of type [" +
					exposedObject.getClass().getName() + "] - not supported for aspect-configured classes!");
		}
	}

}
