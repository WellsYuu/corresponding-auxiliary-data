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

package org.springframework.web.portlet.context;

import javax.portlet.PortletContext;

import org.springframework.beans.factory.Aware;

/**
 * Interface to be implemented by any object that wishes to be notified
 * of the PortletContext (typically determined by the PortletApplicationContext)
 * that it runs in.
 *
 * @author Juergen Hoeller
 * @author William G. Thompson, Jr.
 * @author Chris Beams
 * @since 2.0
 * @see PortletConfigAware
 */
public interface PortletContextAware extends Aware {

	/**
	 * Set the PortletContext that this object runs in.
	 * <p>Invoked after population of normal bean properties but before an init
	 * callback like InitializingBean's afterPropertiesSet or a custom init-method.
	 * Invoked after ApplicationContextAware's setApplicationContext.
	 * @param portletContext PortletContext object to be used by this object
	 */
	void setPortletContext(PortletContext portletContext);

}
