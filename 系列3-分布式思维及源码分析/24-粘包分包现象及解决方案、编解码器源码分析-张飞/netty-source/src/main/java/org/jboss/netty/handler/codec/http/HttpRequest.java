/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package org.jboss.netty.handler.codec.http;

/**
 * An HTTP request.
 *
 * <h3>Accessing Query Parameters and Cookie</h3>
 * <p>
 * Unlike the Servlet API, a query string is constructed and decomposed by
 * {@link QueryStringEncoder} and {@link QueryStringDecoder}.
 *
 * {@link org.jboss.netty.handler.codec.http.cookie.Cookie} support is also provided
 * separately via {@link org.jboss.netty.handler.codec.http.cookie.ServerCookieDecoder},
 * {@link org.jboss.netty.handler.codec.http.cookie.ClientCookieDecoder},
 * {@link org.jboss.netty.handler.codec.http.cookie.ServerCookieEncoder},
 * and {@link org.jboss.netty.handler.codec.http.cookie.ClientCookieEncoder}.
 *
 * @see HttpResponse
 * @see org.jboss.netty.handler.codec.http.cookie.ServerCookieDecoder
 * @see org.jboss.netty.handler.codec.http.cookie.ClientCookieDecoder
 * @see org.jboss.netty.handler.codec.http.cookie.ServerCookieEncoder
 * @see org.jboss.netty.handler.codec.http.cookie.ClientCookieEncoder
 */
public interface HttpRequest extends HttpMessage {

    /**
     * Returns the method of this request.
     */
    HttpMethod getMethod();

    /**
     * Sets the method of this request.
     */
    void setMethod(HttpMethod method);

    /**
     * Returns the URI (or path) of this request.
     */
    String getUri();

    /**
     * Sets the URI (or path) of this request.
     */
    void setUri(String uri);
}
