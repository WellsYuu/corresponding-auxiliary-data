/*
 * Copyright 2002-2011 the original author or authors.
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

package org.springframework.jdbc.config;

import java.util.List;

import org.springframework.beans.BeanMetadataElement;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.jdbc.datasource.init.CompositeDatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.util.StringUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

/**
 * @author Juergen Hoeller
 * @since 3.1
 */
class DatabasePopulatorConfigUtils {

	public static void setDatabasePopulator(Element element, BeanDefinitionBuilder builder) {
		List<Element> scripts = DomUtils.getChildElementsByTagName(element, "script");
		if (scripts.size() > 0) {
			builder.addPropertyValue("databasePopulator", createDatabasePopulator(element, scripts, "INIT"));
			builder.addPropertyValue("databaseCleaner", createDatabasePopulator(element, scripts, "DESTROY"));
		}
	}

	static private BeanDefinition createDatabasePopulator(Element element, List<Element> scripts, String execution) {
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(CompositeDatabasePopulator.class);

		boolean ignoreFailedDrops = element.getAttribute("ignore-failures").equals("DROPS");
		boolean continueOnError = element.getAttribute("ignore-failures").equals("ALL");

		ManagedList<BeanMetadataElement> delegates = new ManagedList<BeanMetadataElement>();
		for (Element scriptElement : scripts) {
			String executionAttr = scriptElement.getAttribute("execution");
			if (!StringUtils.hasText(executionAttr)) {
				executionAttr = "INIT";
			}
			if (!execution.equals(executionAttr)) {
				continue;
			}
			BeanDefinitionBuilder delegate = BeanDefinitionBuilder.genericBeanDefinition(ResourceDatabasePopulator.class);
			delegate.addPropertyValue("ignoreFailedDrops", ignoreFailedDrops);
			delegate.addPropertyValue("continueOnError", continueOnError);

			// Use a factory bean for the resources so they can be given an order if a pattern is used
			BeanDefinitionBuilder resourcesFactory = BeanDefinitionBuilder.genericBeanDefinition(SortedResourcesFactoryBean.class);
			resourcesFactory.addConstructorArgValue(new TypedStringValue(scriptElement.getAttribute("location")));
			delegate.addPropertyValue("scripts", resourcesFactory.getBeanDefinition());
			if (StringUtils.hasLength(scriptElement.getAttribute("encoding"))) {
				delegate.addPropertyValue("sqlScriptEncoding", new TypedStringValue(scriptElement.getAttribute("encoding")));
			}
			if (StringUtils.hasLength(scriptElement.getAttribute("separator"))) {
				delegate.addPropertyValue("separator", new TypedStringValue(scriptElement.getAttribute("separator")));
			}
			delegates.add(delegate.getBeanDefinition());
		}
		builder.addPropertyValue("populators", delegates);

		return builder.getBeanDefinition();
	}

}
