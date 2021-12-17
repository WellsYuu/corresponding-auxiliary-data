package com.jiagouedu.core.filter;

import javax.servlet.*;
import java.io.IOException;

public class EncodeFilter implements Filter {

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		chain.doFilter(request, response);
	}

	public void init(FilterConfig arg0) throws ServletException {

	}

}
