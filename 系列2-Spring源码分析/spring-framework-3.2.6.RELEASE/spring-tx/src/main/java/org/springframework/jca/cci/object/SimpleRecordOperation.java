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

package org.springframework.jca.cci.object;

import javax.resource.cci.ConnectionFactory;
import javax.resource.cci.InteractionSpec;
import javax.resource.cci.Record;

import org.springframework.dao.DataAccessException;

/**
 * EIS operation object that accepts a passed-in CCI input Record
 * and returns a corresponding CCI output Record.
 *
 * @author Juergen Hoeller
 * @since 1.2
 */
public class SimpleRecordOperation extends EisOperation {

	/**
	 * Constructor that allows use as a JavaBean.
	 */
	public SimpleRecordOperation() {
	}

	/**
	 * Convenient constructor with ConnectionFactory and specifications
	 * (connection and interaction).
	 * @param connectionFactory ConnectionFactory to use to obtain connections
	 */
	public SimpleRecordOperation(ConnectionFactory connectionFactory, InteractionSpec interactionSpec) {
		getCciTemplate().setConnectionFactory(connectionFactory);
		setInteractionSpec(interactionSpec);
	}

	/**
	 * Execute the CCI interaction encapsulated by this operation object.
	 * <p>This method will call CCI's {@code Interaction.execute} variant
	 * that returns an output Record.
	 * @param inputRecord the input record
	 * @return the output record
	 * @throws DataAccessException if there is any problem
	 * @see javax.resource.cci.Interaction#execute(javax.resource.cci.InteractionSpec, Record)
	 */
	public Record execute(Record inputRecord) throws DataAccessException {
		return getCciTemplate().execute(getInteractionSpec(), inputRecord);
	}

	/**
	 * Execute the CCI interaction encapsulated by this operation object.
	 * <p>This method will call CCI's {@code Interaction.execute} variant
	 * with a passed-in output Record.
	 * @param inputRecord the input record
	 * @param outputRecord the output record
	 * @throws DataAccessException if there is any problem
	 * @see javax.resource.cci.Interaction#execute(javax.resource.cci.InteractionSpec, Record, Record)
	 */
	public void execute(Record inputRecord, Record outputRecord) throws DataAccessException {
		getCciTemplate().execute(getInteractionSpec(), inputRecord, outputRecord);
	}

}
