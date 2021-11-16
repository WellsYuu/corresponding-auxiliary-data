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

package org.springframework.context.annotation;

import java.util.Map;

import javax.management.MBeanServer;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.jmx.export.annotation.AnnotationMBeanExporter;
import org.springframework.jmx.support.RegistrationPolicy;
import org.springframework.jmx.support.WebSphereMBeanServerFactoryBean;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

/**
 * {@code @Configuration} class that registers a {@link AnnotationMBeanExporter} bean.
 *
 * <p>This configuration class is automatically imported when using the @{@link
 * EnableMBeanExport} annotation. See its Javadoc for complete usage details.
 *
 * @author Phillip Webb
 * @author Chris Beams
 * @since 3.2
 * @see EnableMBeanExport
 */
@Configuration
public class MBeanExportConfiguration implements ImportAware, BeanFactoryAware {

	private static final String MBEAN_EXPORTER_BEAN_NAME = "mbeanExporter";

	private AnnotationAttributes attributes;

	private BeanFactory beanFactory;


	public void setImportMetadata(AnnotationMetadata importMetadata) {
		Map<String, Object> map = importMetadata.getAnnotationAttributes(EnableMBeanExport.class.getName());
		this.attributes = AnnotationAttributes.fromMap(map);
		Assert.notNull(this.attributes, "@EnableMBeanExport is not present on " +
				"importing class " + importMetadata.getClassName());
	}

	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	@Bean(name=MBEAN_EXPORTER_BEAN_NAME)
	@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
	public AnnotationMBeanExporter mbeanExporter() {
		AnnotationMBeanExporter exporter = new AnnotationMBeanExporter();
		setupDomain(exporter);
		setupServer(exporter);
		setupRegistrationPolicy(exporter);
		return exporter;
	}

	private void setupDomain(AnnotationMBeanExporter exporter) {
		String defaultDomain = this.attributes.getString("defaultDomain");
		if (StringUtils.hasText(defaultDomain)) {
			exporter.setDefaultDomain(defaultDomain);
		}
	}

	private void setupServer(AnnotationMBeanExporter exporter) {
		String server = this.attributes.getString("server");
		if (StringUtils.hasText(server)) {
			exporter.setServer(this.beanFactory.getBean(server, MBeanServer.class));
		}
		else {
			SpecificPlatform specificPlatform = SpecificPlatform.get();
			if(specificPlatform != null) {
				exporter.setServer(specificPlatform.getMBeanServer());
			}
		}
	}

	private void setupRegistrationPolicy(AnnotationMBeanExporter exporter) {
		RegistrationPolicy registrationPolicy = this.attributes.getEnum("registration");
		exporter.setRegistrationPolicy(registrationPolicy);
	}


	private static enum SpecificPlatform {

		WEBLOGIC("weblogic.management.Helper") {
			@Override
			public FactoryBean<?> getMBeanServerFactory() {
				JndiObjectFactoryBean factory = new JndiObjectFactoryBean();
				factory.setJndiName("java:comp/env/jmx/runtime");
				return factory;
			}
		},

		WEBSPHERE("com.ibm.websphere.management.AdminServiceFactory") {
			@Override
			public FactoryBean<MBeanServer> getMBeanServerFactory() {
				return new WebSphereMBeanServerFactoryBean();
			}
		};

		private final String identifyingClass;

		private SpecificPlatform(String identifyingClass) {
			this.identifyingClass = identifyingClass;
		}

		public MBeanServer getMBeanServer() {
			Object server;
			try {
				server = getMBeanServerFactory().getObject();
				Assert.isInstanceOf(MBeanServer.class, server);
				return (MBeanServer) server;
			} catch (Exception ex) {
				throw new IllegalStateException(ex);
			}
		}

		protected abstract FactoryBean<?> getMBeanServerFactory();

		public static SpecificPlatform get() {
			ClassLoader classLoader = MBeanExportConfiguration.class.getClassLoader();
			for (SpecificPlatform environment : values()) {
				if(ClassUtils.isPresent(environment.identifyingClass, classLoader)) {
					return environment;
				}
			}
			return null;
		}
	}
}
