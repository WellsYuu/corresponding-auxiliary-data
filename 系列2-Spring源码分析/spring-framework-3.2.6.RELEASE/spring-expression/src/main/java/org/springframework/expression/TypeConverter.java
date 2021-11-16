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

package org.springframework.expression;

import org.springframework.core.convert.TypeDescriptor;

/**
 * A type converter can convert values between different types encountered
 * during expression evaluation. This is an SPI for the expression parser;
 * see {@link org.springframework.core.convert.ConversionService} for the
 * primary user API to Spring's conversion facilities.
 *
 * @author Andy Clement
 * @author Juergen Hoeller
 * @since 3.0
 */
public interface TypeConverter {

	/**
	 * Return true if the type converter can convert the specified type to the desired target type.
	 * @param sourceType a type descriptor that describes the source type
	 * @param targetType a type descriptor that describes the requested result type
	 * @return true if that conversion can be performed
	 */
	boolean canConvert(TypeDescriptor sourceType, TypeDescriptor targetType);

	/**
	 * Convert (may coerce) a value from one type to another, for example from a boolean to a string.
	 * The typeDescriptor parameter enables support for typed collections - if the caller really wishes they
	 * can have a List&lt;Integer&gt; for example, rather than simply a List.
	 * @param value the value to be converted
	 * @param sourceType a type descriptor that supplies extra information about the source object
	 * @param targetType a type descriptor that supplies extra information about the requested result type
	 * @return the converted value
	 * @throws EvaluationException if conversion is not possible
	 */
	Object convertValue(Object value, TypeDescriptor sourceType, TypeDescriptor targetType);

}
