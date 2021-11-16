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

package org.springframework.mock.web.test;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.el.ELException;
import javax.servlet.jsp.el.Expression;
import javax.servlet.jsp.el.ExpressionEvaluator;
import javax.servlet.jsp.el.FunctionMapper;
import javax.servlet.jsp.el.VariableResolver;

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

/**
 * Mock implementation of the JSP 2.0 {@link javax.servlet.jsp.el.ExpressionEvaluator}
 * interface, delegating to the Jakarta JSTL ExpressionEvaluatorManager.
 *
 * <p>Used for testing the web framework; only necessary for testing
 * applications when testing custom JSP tags.
 *
 * <p>Note that the Jakarta JSTL implementation (jstl.jar, standard.jar)
 * has to be available on the class path to use this expression evaluator.
 *
 * @author Juergen Hoeller
 * @since 1.1.5
 * @see org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager
 */
@Deprecated
public class MockExpressionEvaluator extends ExpressionEvaluator {

	private final PageContext pageContext;


	/**
	 * Create a new MockExpressionEvaluator for the given PageContext.
	 * @param pageContext the JSP PageContext to run in
	 */
	public MockExpressionEvaluator(PageContext pageContext) {
		this.pageContext = pageContext;
	}

	@Override
	public Expression parseExpression(
			final String expression, final Class expectedType, final FunctionMapper functionMapper)
			throws ELException {

		return new Expression() {
			@Override
			public Object evaluate(VariableResolver variableResolver) throws ELException {
				return doEvaluate(expression, expectedType, functionMapper);
			}
		};
	}

	@Override
	public Object evaluate(
			String expression, Class expectedType, VariableResolver variableResolver, FunctionMapper functionMapper)
			throws ELException {

		if (variableResolver != null) {
			throw new IllegalArgumentException("Custom VariableResolver not supported");
		}
		return doEvaluate(expression, expectedType, functionMapper);
	}

	protected Object doEvaluate(
			String expression, Class expectedType, FunctionMapper functionMapper)
			throws ELException {

		if (functionMapper != null) {
			throw new IllegalArgumentException("Custom FunctionMapper not supported");
		}
		try {
			return ExpressionEvaluatorManager.evaluate("JSP EL expression", expression, expectedType, this.pageContext);
		}
		catch (JspException ex) {
			throw new ELException("Parsing of JSP EL expression \"" + expression + "\" failed", ex);
		}
	}

}
