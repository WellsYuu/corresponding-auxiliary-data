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

package org.springframework.jms.core;

import javax.jms.JMSException;
import javax.jms.QueueBrowser;
import javax.jms.Session;

/**
 * Callback for browsing the messages in a JMS queue.
 *
 * <p>To be used with JmsTemplate's callback methods that take a BrowserCallback
 * argument, often implemented as an anonymous inner class.
 *
 * @author Juergen Hoeller
 * @since 2.5.1
 * @see JmsTemplate#browse(BrowserCallback)
 * @see JmsTemplate#browseSelected(String, BrowserCallback)
 */
public interface BrowserCallback<T> {

	/**
	 * Perform operations on the given {@link javax.jms.Session} and {@link javax.jms.QueueBrowser}.
	 * <p>The message producer is not associated with any destination.
	 * @param session the JMS {@code Session} object to use
	 * @param browser the JMS {@code QueueBrowser} object to use
	 * @return a result object from working with the {@code Session}, if any (can be {@code null})
	 * @throws javax.jms.JMSException if thrown by JMS API methods
	 */
	T doInJms(Session session, QueueBrowser browser) throws JMSException;

}
