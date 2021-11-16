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

package org.springframework.http.converter.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.DirectFieldAccessor;
import org.springframework.beans.FatalBeanException;
import org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.cfg.DeserializerFactoryConfig;
import com.fasterxml.jackson.databind.cfg.SerializerFactoryConfig;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers.DateDeserializer;
import com.fasterxml.jackson.databind.introspect.NopAnnotationIntrospector;
import com.fasterxml.jackson.databind.ser.std.StdJdkSerializers.ClassSerializer;

/**
 * Test cases for {@link Jackson2ObjectMapperFactoryBean} class.
 *
 * @author <a href="mailto:dmitry.katsubo@gmail.com">Dmitry Katsubo</a>
 */
public class Jackson2ObjectMapperFactoryBeanTests {

	private static final String DATE_FORMAT = "yyyy-MM-dd";

	private Jackson2ObjectMapperFactoryBean factory;

	@Before
	public void setUp() {
		factory = new Jackson2ObjectMapperFactoryBean();
	}

	@Test
	public void testSetFeaturesToEnableEmpty() {
		this.factory.setFeaturesToEnable(new Object[0]);
		this.factory.setFeaturesToDisable(new Object[0]);
	}

	@Test(expected = FatalBeanException.class)
	public void testUnknownFeature() {
		this.factory.setFeaturesToEnable(new Object[] { Boolean.TRUE });
		this.factory.afterPropertiesSet();
	}

	@Test
	public void testBooleanSetters() {
		this.factory.setAutoDetectFields(false);
		this.factory.setAutoDetectGettersSetters(false);
		this.factory.setFailOnEmptyBeans(false);
		this.factory.setIndentOutput(true);
		this.factory.afterPropertiesSet();

		ObjectMapper objectMapper = this.factory.getObject();

		assertFalse(objectMapper.getSerializationConfig().isEnabled(MapperFeature.AUTO_DETECT_FIELDS));
		assertFalse(objectMapper.getDeserializationConfig().isEnabled(MapperFeature.AUTO_DETECT_FIELDS));
		assertFalse(objectMapper.getSerializationConfig().isEnabled(MapperFeature.AUTO_DETECT_GETTERS));
		assertFalse(objectMapper.getDeserializationConfig().isEnabled(MapperFeature.AUTO_DETECT_SETTERS));
		assertFalse(objectMapper.getSerializationConfig().isEnabled(SerializationFeature.FAIL_ON_EMPTY_BEANS));
		assertTrue(objectMapper.getSerializationConfig().isEnabled(SerializationFeature.INDENT_OUTPUT));
	}

	@Test
	public void testDateTimeFormatSetter() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

		this.factory.setDateFormat(dateFormat);
		this.factory.afterPropertiesSet();

		assertEquals(dateFormat, this.factory.getObject().getSerializationConfig().getDateFormat());
		assertEquals(dateFormat, this.factory.getObject().getDeserializationConfig().getDateFormat());
	}

	@Test
	public void testSimpleDateFormatStringSetter() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

		this.factory.setSimpleDateFormat(DATE_FORMAT);
		this.factory.afterPropertiesSet();

		assertEquals(dateFormat, this.factory.getObject().getSerializationConfig().getDateFormat());
		assertEquals(dateFormat, this.factory.getObject().getDeserializationConfig().getDateFormat());
	}

	@Test
	public void testSimpleSetup() {
		this.factory.afterPropertiesSet();

		assertNotNull(this.factory.getObject());
		assertTrue(this.factory.isSingleton());
		assertEquals(ObjectMapper.class, this.factory.getObjectType());
	}

	/**
	 * TODO: Remove use of {@link DirectFieldAccessor} with getters.
	 * See <a href="https://github.com/FasterXML/jackson-databind/issues/65">issue#65</a>.
	 */
	private static final SerializerFactoryConfig getSerializerFactoryConfig(ObjectMapper objectMapper) {
		Object factoryProp = new DirectFieldAccessor(objectMapper).getPropertyValue("_serializerFactory");
		return (SerializerFactoryConfig) new DirectFieldAccessor(factoryProp).getPropertyValue("_factoryConfig");
	}

	private static final DeserializerFactoryConfig getDeserializerFactoryConfig(ObjectMapper objectMapper) {
		Object contextProp = new DirectFieldAccessor(objectMapper).getPropertyValue("_deserializationContext");
		Object factoryProp = new DirectFieldAccessor(contextProp).getPropertyValue("_factory");
		return (DeserializerFactoryConfig) new DirectFieldAccessor(factoryProp).getPropertyValue("_factoryConfig");
	}

	@Test
	public void testCompleteSetup() {
		NopAnnotationIntrospector annotationIntrospector = NopAnnotationIntrospector.instance;
		ObjectMapper objectMapper = new ObjectMapper();

		assertTrue(this.factory.isSingleton());
		assertEquals(ObjectMapper.class, this.factory.getObjectType());

		Map<Class<?>, JsonDeserializer<?>> deserializers = new HashMap<Class<?>, JsonDeserializer<?>>();
		deserializers.put(Date.class, new DateDeserializer());

		this.factory.setObjectMapper(objectMapper);
		this.factory.setSerializers(new ClassSerializer());
		this.factory.setDeserializersByType(deserializers);
		this.factory.setAnnotationIntrospector(annotationIntrospector);

		this.factory.setFeaturesToEnable(
				SerializationFeature.FAIL_ON_EMPTY_BEANS,
				DeserializationFeature.UNWRAP_ROOT_VALUE,
				JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER,
				JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS);

		this.factory.setFeaturesToDisable(
				MapperFeature.AUTO_DETECT_GETTERS,
				MapperFeature.AUTO_DETECT_FIELDS,
				JsonParser.Feature.AUTO_CLOSE_SOURCE,
				JsonGenerator.Feature.QUOTE_FIELD_NAMES);

		assertFalse(getSerializerFactoryConfig(objectMapper).hasSerializers());
		assertFalse(getDeserializerFactoryConfig(objectMapper).hasDeserializers());

		this.factory.afterPropertiesSet();

		assertTrue(objectMapper == this.factory.getObject());

		assertTrue(getSerializerFactoryConfig(objectMapper).hasSerializers());
		assertTrue(getDeserializerFactoryConfig(objectMapper).hasDeserializers());

		assertTrue(annotationIntrospector == objectMapper.getSerializationConfig().getAnnotationIntrospector());
		assertTrue(annotationIntrospector == objectMapper.getDeserializationConfig().getAnnotationIntrospector());

		assertTrue(objectMapper.getSerializationConfig().isEnabled(SerializationFeature.FAIL_ON_EMPTY_BEANS));
		assertTrue(objectMapper.getDeserializationConfig().isEnabled(DeserializationFeature.UNWRAP_ROOT_VALUE));
		assertTrue(objectMapper.getJsonFactory().isEnabled(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER));
		assertTrue(objectMapper.getJsonFactory().isEnabled(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS));

		assertFalse(objectMapper.getSerializationConfig().isEnabled(MapperFeature.AUTO_DETECT_GETTERS));
		assertFalse(objectMapper.getDeserializationConfig().isEnabled(MapperFeature.AUTO_DETECT_FIELDS));
		assertFalse(objectMapper.getJsonFactory().isEnabled(JsonParser.Feature.AUTO_CLOSE_SOURCE));
		assertFalse(objectMapper.getJsonFactory().isEnabled(JsonGenerator.Feature.QUOTE_FIELD_NAMES));
	}
}
