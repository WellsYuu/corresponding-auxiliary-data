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

package org.springframework.web.accept;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.ServletContextAware;

/**
 * A factory providing convenient access to a {@code ContentNegotiationManager}
 * configured with one or more {@link ContentNegotiationStrategy} instances.
 *
 * <p>By default strategies for checking the extension of the request path and
 * the {@code Accept} header are registered. The path extension check will perform
 * lookups through the {@link ServletContext} and the Java Activation Framework
 * (if present) unless {@linkplain #setMediaTypes media types} are configured.
 *
 * @author Rossen Stoyanchev
 * @since 3.2
 */
public class ContentNegotiationManagerFactoryBean
		implements FactoryBean<ContentNegotiationManager>, ServletContextAware, InitializingBean {

	private boolean favorPathExtension = true;

	private boolean favorParameter = false;

	private boolean ignoreAcceptHeader = false;

	private Map<String, MediaType> mediaTypes = new HashMap<String, MediaType>();

	private Boolean useJaf;

	private String parameterName = "format";

	private MediaType defaultContentType;

	private ContentNegotiationManager contentNegotiationManager;

	private ServletContext servletContext;


	/**
	 * Indicate whether the extension of the request path should be used to determine
	 * the requested media type with the <em>highest priority</em>.
	 * <p>By default this value is set to {@code true} in which case a request
	 * for {@code /hotels.pdf} will be interpreted as a request for
	 * {@code "application/pdf"} regardless of the {@code Accept} header.
	 */
	public void setFavorPathExtension(boolean favorPathExtension) {
		this.favorPathExtension = favorPathExtension;
	}

	/**
	 * Add mappings from file extensions to media types represented as strings.
	 * <p>When this mapping is not set or when an extension is not found, the Java
	 * Action Framework, if available, may be used if enabled via
	 * {@link #setFavorPathExtension(boolean)}.
	 * @see #addMediaType(String, MediaType)
	 * @see #addMediaTypes(Map)
	 */
	public void setMediaTypes(Properties mediaTypes) {
		if (!CollectionUtils.isEmpty(mediaTypes)) {
			for (Entry<Object, Object> entry : mediaTypes.entrySet()) {
				String extension = ((String)entry.getKey()).toLowerCase(Locale.ENGLISH);
				this.mediaTypes.put(extension, MediaType.valueOf((String) entry.getValue()));
			}
		}
	}

	/**
	 * Add a mapping from a file extension to a media type.
	 * <p>If no mapping is added or when an extension is not found, the Java
	 * Action Framework, if available, may be used if enabled via
	 * {@link #setFavorPathExtension(boolean)}.
	 */
	public void addMediaType(String fileExtension, MediaType mediaType) {
		this.mediaTypes.put(fileExtension, mediaType);
	}

	/**
	 * Add mappings from file extensions to media types.
	 * <p>If no mappings are added or when an extension is not found, the Java
	 * Action Framework, if available, may be used if enabled via
	 * {@link #setFavorPathExtension(boolean)}.
	 */
	public void addMediaTypes(Map<String, MediaType> mediaTypes) {
		if (mediaTypes != null) {
			this.mediaTypes.putAll(mediaTypes);
		}
	}

	/**
	 * Indicate whether to use the Java Activation Framework as a fallback option
	 * to map from file extensions to media types. This is used only when
	 * {@link #setFavorPathExtension(boolean)} is set to {@code true}.
	 * <p>The default value is {@code true}.
	 * @see #setParameterName
	 * @see #setMediaTypes
	 */
	public void setUseJaf(boolean useJaf) {
		this.useJaf = useJaf;
	}

	/**
	 * Indicate whether a request parameter should be used to determine the
	 * requested media type with the <em>2nd highest priority</em>, i.e.
	 * after path extensions but before the {@code Accept} header.
	 * <p>The default value is {@code false}. If set to to {@code true}, a request
	 * for {@code /hotels?format=pdf} will be interpreted as a request for
	 * {@code "application/pdf"} regardless of the {@code Accept} header.
	 * <p>To use this option effectively you must also configure the MediaType
	 * type mappings via {@link #setMediaTypes(Properties)}.
	 * @see #setParameterName
	 */
	public void setFavorParameter(boolean favorParameter) {
		this.favorParameter = favorParameter;
	}

	/**
	 * Set the parameter name that can be used to determine the requested media type
	 * if the {@link #setFavorParameter} property is {@code true}.
	 * <p>The default parameter name is {@code "format"}.
	 */
	public void setParameterName(String parameterName) {
		Assert.notNull(parameterName, "parameterName is required");
		this.parameterName = parameterName;
	}

	/**
	 * Indicate whether the HTTP {@code Accept} header should be ignored altogether.
	 * If set the {@code Accept} header is checked at the
	 * <em>3rd highest priority</em>, i.e. after the request path extension and
	 * possibly a request parameter if configured.
	 * <p>By default this value is set to {@code false}.
	 */
	public void setIgnoreAcceptHeader(boolean ignoreAcceptHeader) {
		this.ignoreAcceptHeader = ignoreAcceptHeader;
	}

	/**
	 * Set the default content type.
	 * <p>This content type will be used when neither the request path extension,
	 * nor a request parameter, nor the {@code Accept} header could help
	 * determine the requested content type.
	 */
	public void setDefaultContentType(MediaType defaultContentType) {
		this.defaultContentType = defaultContentType;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}


	public void afterPropertiesSet() {
		List<ContentNegotiationStrategy> strategies = new ArrayList<ContentNegotiationStrategy>();

		if (this.favorPathExtension) {
			PathExtensionContentNegotiationStrategy strategy;
			if (this.servletContext != null) {
				strategy = new ServletPathExtensionContentNegotiationStrategy(this.servletContext, this.mediaTypes);
			} else {
				strategy = new PathExtensionContentNegotiationStrategy(this.mediaTypes);
			}
			if (this.useJaf != null) {
				strategy.setUseJaf(this.useJaf);
			}
			strategies.add(strategy);
		}

		if (this.favorParameter) {
			ParameterContentNegotiationStrategy strategy = new ParameterContentNegotiationStrategy(this.mediaTypes);
			strategy.setParameterName(this.parameterName);
			strategies.add(strategy);
		}

		if (!this.ignoreAcceptHeader) {
			strategies.add(new HeaderContentNegotiationStrategy());
		}

		if (this.defaultContentType != null) {
			strategies.add(new FixedContentNegotiationStrategy(this.defaultContentType));
		}

		this.contentNegotiationManager = new ContentNegotiationManager(strategies);
	}


	public ContentNegotiationManager getObject() {
		return this.contentNegotiationManager;
	}

	public Class<?> getObjectType() {
		return ContentNegotiationManager.class;
	}

	public boolean isSingleton() {
		return true;
	}

}
