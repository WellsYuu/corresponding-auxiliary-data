/*
 * Copyright 2002-2009 the original author or authors.
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

package org.springframework.scheduling.support;

import java.util.Date;

import org.springframework.scheduling.TriggerContext;

/**
 * Simple data holder implementation of the {@link TriggerContext} interface.
 *
 * @author Juergen Hoeller
 * @since 3.0
 */
public class SimpleTriggerContext implements TriggerContext {

	private volatile Date lastScheduledExecutionTime;

	private volatile Date lastActualExecutionTime;

	private volatile Date lastCompletionTime;


	/**
	 * Update this holder's state with the latest time values.
 	 * @param lastScheduledExecutionTime last <i>scheduled</i> execution time
	 * @param lastActualExecutionTime last <i>actual</i> execution time
	 * @param lastCompletionTime last completion time
	 */
	public void update(Date lastScheduledExecutionTime, Date lastActualExecutionTime, Date lastCompletionTime) {
		this.lastScheduledExecutionTime = lastScheduledExecutionTime;
		this.lastActualExecutionTime = lastActualExecutionTime;
		this.lastCompletionTime = lastCompletionTime;
	}


	public Date lastScheduledExecutionTime() {
		return this.lastScheduledExecutionTime;
	}

	public Date lastActualExecutionTime() {
		return this.lastActualExecutionTime;
	}

	public Date lastCompletionTime() {
		return this.lastCompletionTime;
	}

}
