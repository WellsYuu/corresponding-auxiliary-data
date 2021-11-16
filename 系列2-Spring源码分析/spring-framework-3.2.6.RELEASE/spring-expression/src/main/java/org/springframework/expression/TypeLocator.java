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

package org.springframework.expression;

/**
 * Implementors of this interface are expected to be able to locate types. They may use custom classloaders
 * or the and deal with common package prefixes (java.lang, etc) however they wish. See
 * {@link org.springframework.expression.spel.support.StandardTypeLocator} for an example implementation.
 *
 * @author Andy Clement
 * @since 3.0
 */
public interface TypeLocator {

	/**
	 * Find a type by name. The name may or may not be fully qualified (eg. String or java.lang.String)
	 * @param typename the type to be located
	 * @return the class object representing that type
	 * @throws EvaluationException if there is a problem finding it
	 */
	Class<?> findType(String typename) throws EvaluationException;

}
