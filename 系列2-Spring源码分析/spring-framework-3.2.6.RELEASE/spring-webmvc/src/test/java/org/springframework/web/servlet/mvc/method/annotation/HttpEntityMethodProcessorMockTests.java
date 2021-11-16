/*
 * Copyright 2002-2013 the original author or authors.
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

package org.springframework.web.servlet.mvc.method.annotation;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.mock.web.test.MockHttpServletRequest;
import org.springframework.mock.web.test.MockHttpServletResponse;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.web.servlet.HandlerMapping.*;

/**
 * Test fixture for {@link HttpEntityMethodProcessor} delegating to a mock
 * {@link HttpMessageConverter}.
 *
 * <p>Also see {@link HttpEntityMethodProcessorTests}.
 *
 * @author Arjen Poutsma
 * @author Rossen Stoyanchev
 */
public class HttpEntityMethodProcessorMockTests {

	private HttpEntityMethodProcessor processor;

	private HttpMessageConverter<String> messageConverter;

	private MethodParameter paramHttpEntity;
	private MethodParameter paramResponseEntity;
	private MethodParameter paramInt;
	private MethodParameter returnTypeResponseEntity;
	private MethodParameter returnTypeHttpEntity;
	private MethodParameter returnTypeInt;
	private MethodParameter returnTypeResponseEntityProduces;

	private ModelAndViewContainer mavContainer;

	private ServletWebRequest webRequest;

	private MockHttpServletResponse servletResponse;

	private MockHttpServletRequest servletRequest;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		messageConverter = mock(HttpMessageConverter.class);
		given(messageConverter.getSupportedMediaTypes()).willReturn(Collections.singletonList(MediaType.TEXT_PLAIN));

		processor = new HttpEntityMethodProcessor(Collections.<HttpMessageConverter<?>>singletonList(messageConverter));
		reset(messageConverter);


		Method handle1 = getClass().getMethod("handle1", HttpEntity.class, ResponseEntity.class, Integer.TYPE);
		paramHttpEntity = new MethodParameter(handle1, 0);
		paramResponseEntity = new MethodParameter(handle1, 1);
		paramInt = new MethodParameter(handle1, 2);
		returnTypeResponseEntity = new MethodParameter(handle1, -1);

		returnTypeHttpEntity = new MethodParameter(getClass().getMethod("handle2", HttpEntity.class), -1);

		returnTypeInt = new MethodParameter(getClass().getMethod("handle3"), -1);

		returnTypeResponseEntityProduces = new MethodParameter(getClass().getMethod("handle4"), -1);

		mavContainer = new ModelAndViewContainer();

		servletRequest = new MockHttpServletRequest();
		servletResponse = new MockHttpServletResponse();
		webRequest = new ServletWebRequest(servletRequest, servletResponse);
	}

	@Test
	public void supportsParameter() {
		assertTrue("HttpEntity parameter not supported", processor.supportsParameter(paramHttpEntity));
		assertFalse("ResponseEntity parameter supported", processor.supportsParameter(paramResponseEntity));
		assertFalse("non-entity parameter supported", processor.supportsParameter(paramInt));
	}

	@Test
	public void supportsReturnType() {
		assertTrue("ResponseEntity return type not supported", processor.supportsReturnType(returnTypeResponseEntity));
		assertTrue("HttpEntity return type not supported", processor.supportsReturnType(returnTypeHttpEntity));
		assertFalse("non-ResponseBody return type supported", processor.supportsReturnType(returnTypeInt));
	}

	@Test
	public void resolveArgument() throws Exception {
		MediaType contentType = MediaType.TEXT_PLAIN;
		servletRequest.addHeader("Content-Type", contentType.toString());

		String body = "Foo";
		given(messageConverter.canRead(String.class, contentType)).willReturn(true);
		given(messageConverter.read(eq(String.class), isA(HttpInputMessage.class))).willReturn(body);

		Object result = processor.resolveArgument(paramHttpEntity, mavContainer, webRequest, null);

		assertTrue(result instanceof HttpEntity);
		assertFalse("The requestHandled flag shouldn't change", mavContainer.isRequestHandled());
		assertEquals("Invalid argument", body, ((HttpEntity<?>) result).getBody());
	}

	@Test(expected = HttpMediaTypeNotSupportedException.class)
	public void resolveArgumentNotReadable() throws Exception {
		MediaType contentType = MediaType.TEXT_PLAIN;
		servletRequest.addHeader("Content-Type", contentType.toString());

		given(messageConverter.getSupportedMediaTypes()).willReturn(Arrays.asList(contentType));
		given(messageConverter.canRead(String.class, contentType)).willReturn(false);

		processor.resolveArgument(paramHttpEntity, mavContainer, webRequest, null);

		fail("Expected exception");
	}

	@Test(expected = HttpMediaTypeNotSupportedException.class)
	public void resolveArgumentNoContentType() throws Exception {
		processor.resolveArgument(paramHttpEntity, mavContainer, webRequest, null);
		fail("Expected exception");
	}

	@Test
	public void handleReturnValue() throws Exception {
		String body = "Foo";
		ResponseEntity<String> returnValue = new ResponseEntity<String>(body, HttpStatus.OK);

		MediaType accepted = MediaType.TEXT_PLAIN;
		servletRequest.addHeader("Accept", accepted.toString());

		given(messageConverter.canWrite(String.class, null)).willReturn(true);
		given(messageConverter.getSupportedMediaTypes()).willReturn(Collections.singletonList(MediaType.TEXT_PLAIN));
		given(messageConverter.canWrite(String.class, accepted)).willReturn(true);

		processor.handleReturnValue(returnValue, returnTypeResponseEntity, mavContainer, webRequest);

		assertTrue(mavContainer.isRequestHandled());
		verify(messageConverter).write(eq(body), eq(accepted), isA(HttpOutputMessage.class));
	}

	@Test
	public void handleReturnValueProduces() throws Exception {
		String body = "Foo";
		ResponseEntity<String> returnValue = new ResponseEntity<String>(body, HttpStatus.OK);

		servletRequest.addHeader("Accept", "text/*");
		servletRequest.setAttribute(PRODUCIBLE_MEDIA_TYPES_ATTRIBUTE, Collections.singleton(MediaType.TEXT_HTML));

		given(messageConverter.canWrite(String.class, MediaType.TEXT_HTML)).willReturn(true);

		processor.handleReturnValue(returnValue, returnTypeResponseEntityProduces, mavContainer, webRequest);

		assertTrue(mavContainer.isRequestHandled());
		verify(messageConverter).write(eq(body), eq(MediaType.TEXT_HTML), isA(HttpOutputMessage.class));
	}

	@Test(expected = HttpMediaTypeNotAcceptableException.class)
	public void handleReturnValueNotAcceptable() throws Exception {
		String body = "Foo";
		ResponseEntity<String> returnValue = new ResponseEntity<String>(body, HttpStatus.OK);

		MediaType accepted = MediaType.APPLICATION_ATOM_XML;
		servletRequest.addHeader("Accept", accepted.toString());

		given(messageConverter.canWrite(String.class, null)).willReturn(true);
		given(messageConverter.getSupportedMediaTypes()).willReturn(Arrays.asList(MediaType.TEXT_PLAIN));
		given(messageConverter.canWrite(String.class, accepted)).willReturn(false);

		processor.handleReturnValue(returnValue, returnTypeResponseEntity, mavContainer, webRequest);

		fail("Expected exception");
	}

	@Test(expected = HttpMediaTypeNotAcceptableException.class)
	public void handleReturnValueNotAcceptableProduces() throws Exception {
		String body = "Foo";
		ResponseEntity<String> returnValue = new ResponseEntity<String>(body, HttpStatus.OK);

		MediaType accepted = MediaType.TEXT_PLAIN;
		servletRequest.addHeader("Accept", accepted.toString());

		given(messageConverter.canWrite(String.class, null)).willReturn(true);
		given(messageConverter.getSupportedMediaTypes()).willReturn(Collections.singletonList(MediaType.TEXT_PLAIN));
		given(messageConverter.canWrite(String.class, accepted)).willReturn(false);

		processor.handleReturnValue(returnValue, returnTypeResponseEntityProduces, mavContainer, webRequest);

		fail("Expected exception");
	}

	// SPR-9142

	@Test(expected=HttpMediaTypeNotAcceptableException.class)
	public void handleReturnValueNotAcceptableParseError() throws Exception {
		ResponseEntity<String> returnValue = new ResponseEntity<String>("Body", HttpStatus.ACCEPTED);
		servletRequest.addHeader("Accept", "01");

		processor.handleReturnValue(returnValue, returnTypeResponseEntity, mavContainer, webRequest);
		fail("Expected exception");
	}

	@Test
	public void responseHeaderNoBody() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.set("headerName", "headerValue");
		ResponseEntity<String> returnValue = new ResponseEntity<String>(headers, HttpStatus.ACCEPTED);

		processor.handleReturnValue(returnValue, returnTypeResponseEntity, mavContainer, webRequest);

		assertTrue(mavContainer.isRequestHandled());
		assertEquals("headerValue", servletResponse.getHeader("headerName"));
	}

	@Test
	public void responseHeaderAndBody() throws Exception {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("header", "headerValue");
		ResponseEntity<String> returnValue = new ResponseEntity<String>("body", responseHeaders, HttpStatus.ACCEPTED);

		given(messageConverter.canWrite(String.class, null)).willReturn(true);
		given(messageConverter.getSupportedMediaTypes()).willReturn(Collections.singletonList(MediaType.TEXT_PLAIN));
		given(messageConverter.canWrite(String.class, MediaType.TEXT_PLAIN)).willReturn(true);

		processor.handleReturnValue(returnValue, returnTypeResponseEntity, mavContainer, webRequest);

		ArgumentCaptor<HttpOutputMessage> outputMessage = ArgumentCaptor.forClass(HttpOutputMessage.class);
		verify(messageConverter).write(eq("body"), eq(MediaType.TEXT_PLAIN),  outputMessage.capture());
		assertTrue(mavContainer.isRequestHandled());
		assertEquals("headerValue", outputMessage.getValue().getHeaders().get("header").get(0));
	}

	public ResponseEntity<String> handle1(HttpEntity<String> httpEntity, ResponseEntity<String> responseEntity, int i) {
		return responseEntity;
	}

	public HttpEntity<?> handle2(HttpEntity<?> entity) {
		return entity;
	}

	public int handle3() {
		return 42;
	}

	@RequestMapping(produces = {"text/html", "application/xhtml+xml"})
	public ResponseEntity<String> handle4() {
		return null;
	}


}
