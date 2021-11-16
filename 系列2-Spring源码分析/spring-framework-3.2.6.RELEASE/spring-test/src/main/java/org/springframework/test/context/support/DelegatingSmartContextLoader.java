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

package org.springframework.test.context.support;

import org.springframework.test.context.SmartContextLoader;

/**
 * {@code DelegatingSmartContextLoader} is a concrete implementation of
 * {@link AbstractDelegatingSmartContextLoader} that delegates to a
 * {@link GenericXmlContextLoader} and an {@link AnnotationConfigContextLoader}.
 *
 * @author Sam Brannen
 * @since 3.1
 * @see SmartContextLoader
 * @see AbstractDelegatingSmartContextLoader
 * @see GenericXmlContextLoader
 * @see AnnotationConfigContextLoader
 */
public class DelegatingSmartContextLoader extends AbstractDelegatingSmartContextLoader {

	private final SmartContextLoader xmlLoader = new GenericXmlContextLoader();
	private final SmartContextLoader annotationConfigLoader = new AnnotationConfigContextLoader();


	protected SmartContextLoader getXmlLoader() {
		return this.xmlLoader;
	}

	protected SmartContextLoader getAnnotationConfigLoader() {
		return this.annotationConfigLoader;
	}

}
