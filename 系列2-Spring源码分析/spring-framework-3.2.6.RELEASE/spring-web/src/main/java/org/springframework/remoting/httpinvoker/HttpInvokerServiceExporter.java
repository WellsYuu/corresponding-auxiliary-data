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

package org.springframework.remoting.httpinvoker;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.remoting.rmi.RemoteInvocationSerializingExporter;
import org.springframework.remoting.support.RemoteInvocation;
import org.springframework.remoting.support.RemoteInvocationResult;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.util.NestedServletException;

/**
 * Servlet-API-based HTTP request handler that exports the specified service bean
 * as HTTP invoker service endpoint, accessible via an HTTP invoker proxy.
 *
 * <p><b>Note:</b> Spring also provides an alternative version of this exporter,
 * for Sun's JRE 1.6 HTTP server: {@link SimpleHttpInvokerServiceExporter}.
 *
 * <p>Deserializes remote invocation objects and serializes remote invocation
 * result objects. Uses Java serialization just like RMI, but provides the
 * same ease of setup as Caucho's HTTP-based Hessian and Burlap protocols.
 *
 * <p><b>HTTP invoker is the recommended protocol for Java-to-Java remoting.</b>
 * It is more powerful and more extensible than Hessian and Burlap, at the
 * expense of being tied to Java. Nevertheless, it is as easy to set up as
 * Hessian and Burlap, which is its main advantage compared to RMI.
 *
 * @author Juergen Hoeller
 * @since 1.1
 * @see HttpInvokerClientInterceptor
 * @see HttpInvokerProxyFactoryBean
 * @see org.springframework.remoting.rmi.RmiServiceExporter
 * @see org.springframework.remoting.caucho.HessianServiceExporter
 * @see org.springframework.remoting.caucho.BurlapServiceExporter
 */
public class HttpInvokerServiceExporter extends RemoteInvocationSerializingExporter
		implements HttpRequestHandler {

	/**
	 * Reads a remote invocation from the request, executes it,
	 * and writes the remote invocation result to the response.
	 * @see #readRemoteInvocation(HttpServletRequest)
	 * @see #invokeAndCreateResult(org.springframework.remoting.support.RemoteInvocation, Object)
	 * @see #writeRemoteInvocationResult(HttpServletRequest, HttpServletResponse, RemoteInvocationResult)
	 */
	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			RemoteInvocation invocation = readRemoteInvocation(request);
			RemoteInvocationResult result = invokeAndCreateResult(invocation, getProxy());
			writeRemoteInvocationResult(request, response, result);
		}
		catch (ClassNotFoundException ex) {
			throw new NestedServletException("Class not found during deserialization", ex);
		}
	}

	/**
	 * Read a RemoteInvocation from the given HTTP request.
	 * <p>Delegates to
	 * {@link #readRemoteInvocation(javax.servlet.http.HttpServletRequest, java.io.InputStream)}
	 * with the
	 * {@link javax.servlet.ServletRequest#getInputStream() servlet request's input stream}.
	 * @param request current HTTP request
	 * @return the RemoteInvocation object
	 * @throws IOException in case of I/O failure
	 * @throws ClassNotFoundException if thrown by deserialization
	 */
	protected RemoteInvocation readRemoteInvocation(HttpServletRequest request)
			throws IOException, ClassNotFoundException {

		return readRemoteInvocation(request, request.getInputStream());
	}

	/**
	 * Deserialize a RemoteInvocation object from the given InputStream.
	 * <p>Gives {@link #decorateInputStream} a chance to decorate the stream
	 * first (for example, for custom encryption or compression). Creates a
	 * {@link org.springframework.remoting.rmi.CodebaseAwareObjectInputStream}
	 * and calls {@link #doReadRemoteInvocation} to actually read the object.
	 * <p>Can be overridden for custom serialization of the invocation.
	 * @param request current HTTP request
	 * @param is the InputStream to read from
	 * @return the RemoteInvocation object
	 * @throws IOException in case of I/O failure
	 * @throws ClassNotFoundException if thrown during deserialization
	 */
	protected RemoteInvocation readRemoteInvocation(HttpServletRequest request, InputStream is)
			throws IOException, ClassNotFoundException {

		ObjectInputStream ois = createObjectInputStream(decorateInputStream(request, is));
		try {
			return doReadRemoteInvocation(ois);
		}
		finally {
			ois.close();
		}
	}

	/**
	 * Return the InputStream to use for reading remote invocations,
	 * potentially decorating the given original InputStream.
	 * <p>The default implementation returns the given stream as-is.
	 * Can be overridden, for example, for custom encryption or compression.
	 * @param request current HTTP request
	 * @param is the original InputStream
	 * @return the potentially decorated InputStream
	 * @throws IOException in case of I/O failure
	 */
	protected InputStream decorateInputStream(HttpServletRequest request, InputStream is) throws IOException {
		return is;
	}

	/**
	 * Write the given RemoteInvocationResult to the given HTTP response.
	 * @param request current HTTP request
	 * @param response current HTTP response
	 * @param result the RemoteInvocationResult object
	 * @throws IOException in case of I/O failure
	 */
	protected void writeRemoteInvocationResult(
			HttpServletRequest request, HttpServletResponse response, RemoteInvocationResult result)
			throws IOException {

		response.setContentType(getContentType());
		writeRemoteInvocationResult(request, response, result, response.getOutputStream());
	}

	/**
	 * Serialize the given RemoteInvocation to the given OutputStream.
	 * <p>The default implementation gives {@link #decorateOutputStream} a chance
	 * to decorate the stream first (for example, for custom encryption or compression).
	 * Creates an {@link java.io.ObjectOutputStream} for the final stream and calls
	 * {@link #doWriteRemoteInvocationResult} to actually write the object.
	 * <p>Can be overridden for custom serialization of the invocation.
	 * @param request current HTTP request
	 * @param response current HTTP response
	 * @param result the RemoteInvocationResult object
	 * @param os the OutputStream to write to
	 * @throws IOException in case of I/O failure
	 * @see #decorateOutputStream
	 * @see #doWriteRemoteInvocationResult
	 */
	protected void writeRemoteInvocationResult(
			HttpServletRequest request, HttpServletResponse response, RemoteInvocationResult result, OutputStream os)
			throws IOException {

		ObjectOutputStream oos = createObjectOutputStream(decorateOutputStream(request, response, os));
		try {
			doWriteRemoteInvocationResult(result, oos);
		}
		finally {
			oos.close();
		}
	}

	/**
	 * Return the OutputStream to use for writing remote invocation results,
	 * potentially decorating the given original OutputStream.
	 * <p>The default implementation returns the given stream as-is.
	 * Can be overridden, for example, for custom encryption or compression.
	 * @param request current HTTP request
	 * @param response current HTTP response
	 * @param os the original OutputStream
	 * @return the potentially decorated OutputStream
	 * @throws IOException in case of I/O failure
	 */
	protected OutputStream decorateOutputStream(
			HttpServletRequest request, HttpServletResponse response, OutputStream os) throws IOException {

		return os;
	}

}
