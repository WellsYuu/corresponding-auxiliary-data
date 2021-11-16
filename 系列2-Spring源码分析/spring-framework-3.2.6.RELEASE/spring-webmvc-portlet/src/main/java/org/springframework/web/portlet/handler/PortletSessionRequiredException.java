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

package org.springframework.web.portlet.handler;

import javax.portlet.PortletException;

/**
 * Exception thrown when a portlet content generator requires a pre-existing session.
 *
 * @author John A. Lewis
 * @since 2.0
 * @see org.springframework.web.portlet.handler.PortletContentGenerator
 */
@SuppressWarnings("serial")
public class PortletSessionRequiredException extends PortletException {

	/**
	 * Create a new PortletSessionRequiredException.
	 * @param msg the detail message
	 */
	public PortletSessionRequiredException(String msg) {
		super(msg);
	}

}
