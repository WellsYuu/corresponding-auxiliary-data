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

package org.springframework.web.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * A generic composite servlet {@link Filter} that just delegates its behaviour to a chain (list) of user supplied
 * filters, achieving the functionality of a {@link FilterChain}, but conveniently using only {@link Filter} instances.
 * This is useful for filters that require dependency injection, and can therefore be set up in a Spring application
 * context. Typically this composite would be used in conjunction with {@link DelegatingFilterProxy}, so that it can be
 * declared in Spring but applied to a servlet context.
 *
 * @since 3.1
 *
 * @author Dave Syer
 *
 */
public class CompositeFilter implements Filter {

	private List<? extends Filter> filters = new ArrayList<Filter>();

	public void setFilters(List<? extends Filter> filters) {
		this.filters = new ArrayList<Filter>(filters);
	}

	/**
	 * Clean up all the filters supplied, calling each one's destroy method in turn, but in reverse order.
	 *
	 * @see Filter#init(FilterConfig)
	 */
	public void destroy() {
		for (int i = filters.size(); i-- > 0;) {
			Filter filter = filters.get(i);
			filter.destroy();
		}
	}

	/**
	 * Initialize all the filters, calling each one's init method in turn in the order supplied.
	 *
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig config) throws ServletException {
		for (Filter filter : filters) {
			filter.init(config);
		}
	}

	/**
	 * Forms a temporary chain from the list of delegate filters supplied ({@link #setFilters(List)}) and executes them
	 * in order. Each filter delegates to the next one in the list, achieving the normal behaviour of a
	 * {@link FilterChain}, despite the fact that this is a {@link Filter}.
	 *
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		new VirtualFilterChain(chain, filters).doFilter(request, response);
	}

	private static class VirtualFilterChain implements FilterChain {
		private final FilterChain originalChain;
		private final List<? extends Filter> additionalFilters;
		private int currentPosition = 0;

		private VirtualFilterChain(FilterChain chain, List<? extends Filter> additionalFilters) {
			this.originalChain = chain;
			this.additionalFilters = additionalFilters;
		}

		public void doFilter(final ServletRequest request, final ServletResponse response) throws IOException,
				ServletException {
			if (currentPosition == additionalFilters.size()) {
				originalChain.doFilter(request, response);
			} else {
				currentPosition++;
				Filter nextFilter = additionalFilters.get(currentPosition - 1);
				nextFilter.doFilter(request, response, this);
			}
		}

	}

}
