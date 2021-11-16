/*
 * Copyright 2002-2010 the original author or authors.
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

package org.springframework.http.converter.xml;

import java.io.IOException;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.MarshalException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.transform.Result;
import javax.xml.transform.Source;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.ClassUtils;

/**
 * Implementation of {@link org.springframework.http.converter.HttpMessageConverter HttpMessageConverter} that can read
 * and write XML using JAXB2.
 *
 * <p>This converter can read classes annotated with {@link XmlRootElement} and {@link XmlType}, and write classes
 * annotated with with {@link XmlRootElement}, or subclasses thereof.
 *
 * @author Arjen Poutsma
 * @since 3.0
 */
public class Jaxb2RootElementHttpMessageConverter extends AbstractJaxb2HttpMessageConverter<Object> {

	@Override
	public boolean canRead(Class<?> clazz, MediaType mediaType) {
		return (clazz.isAnnotationPresent(XmlRootElement.class) || clazz.isAnnotationPresent(XmlType.class)) &&
				canRead(mediaType);
	}

	@Override
	public boolean canWrite(Class<?> clazz, MediaType mediaType) {
		return AnnotationUtils.findAnnotation(clazz, XmlRootElement.class) != null && canWrite(mediaType);
	}

	@Override
	protected boolean supports(Class<?> clazz) {
		// should not be called, since we override canRead/Write
		throw new UnsupportedOperationException();
	}

	@Override
	protected Object readFromSource(Class<?> clazz, HttpHeaders headers, Source source) throws IOException {
		try {
			Unmarshaller unmarshaller = createUnmarshaller(clazz);
			if (clazz.isAnnotationPresent(XmlRootElement.class)) {
				return unmarshaller.unmarshal(source);
			}
			else {
				JAXBElement jaxbElement = unmarshaller.unmarshal(source, clazz);
				return jaxbElement.getValue();
			}
		}
		catch (UnmarshalException ex) {
			throw new HttpMessageNotReadableException("Could not unmarshal to [" + clazz + "]: " + ex.getMessage(), ex);

		}
		catch (JAXBException ex) {
			throw new HttpMessageConversionException("Could not instantiate JAXBContext: " + ex.getMessage(), ex);
		}
	}

	@Override
	protected void writeToResult(Object o, HttpHeaders headers, Result result) throws IOException {
		try {
			Class clazz = ClassUtils.getUserClass(o);
			Marshaller marshaller = createMarshaller(clazz);
			setCharset(headers.getContentType(), marshaller);
			marshaller.marshal(o, result);
		}
		catch (MarshalException ex) {
			throw new HttpMessageNotWritableException("Could not marshal [" + o + "]: " + ex.getMessage(), ex);
		}
		catch (JAXBException ex) {
			throw new HttpMessageConversionException("Could not instantiate JAXBContext: " + ex.getMessage(), ex);
		}
	}

	private void setCharset(MediaType contentType, Marshaller marshaller) throws PropertyException {
		if (contentType != null && contentType.getCharSet() != null) {
			marshaller.setProperty(Marshaller.JAXB_ENCODING, contentType.getCharSet().name());
		}
	}

}
