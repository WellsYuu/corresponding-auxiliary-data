/*
 * Copyright 2002-2006 the original author or authors.
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

package org.springframework.web.portlet.context;

import javax.portlet.PortletContext;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

/**
 * ResourceLoader implementation that resolves paths as PortletContext
 * resources, for use outside a Portlet ApplicationContext (for example,
 * in a GenericPortletBean subclass).
 *
 * <p>Within a WebApplicationContext, resource paths are automatically
 * resolved as PortletContext resources by the context implementation.
 *
 * @author Juergen Hoeller
 * @author John A. Lewis
 * @since 2.0
 * @see #getResourceByPath
 * @see PortletContextResource
 * @see org.springframework.web.portlet.GenericPortletBean
 */
public class PortletContextResourceLoader extends DefaultResourceLoader {

	private final PortletContext portletContext;


	/**
	 * Create a new PortletContextResourceLoader.
	 * @param portletContext the PortletContext to load resources with
	 */
	public PortletContextResourceLoader(PortletContext portletContext) {
		this.portletContext = portletContext;
	}

	/**
	 * This implementation supports file paths beneath the root of the web application.
	 * @see PortletContextResource
	 */
	@Override
	protected Resource getResourceByPath(String path) {
		return new PortletContextResource(this.portletContext, path);
	}

}
