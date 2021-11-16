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

package org.springframework.transaction.aspectj;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import org.springframework.tests.transaction.CallCountingTransactionManager;
import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.interceptor.TransactionAttribute;

import java.lang.reflect.Method;

/**
 * @author Rod Johnson
 * @author Ramnivas Laddad
 * @author Juergen Hoeller
 */
public class TransactionAspectTests extends AbstractDependencyInjectionSpringContextTests {

	private TransactionAspectSupport transactionAspect;

	private CallCountingTransactionManager txManager;

	private TransactionalAnnotationOnlyOnClassWithNoInterface annotationOnlyOnClassWithNoInterface;

	private ClassWithProtectedAnnotatedMember beanWithAnnotatedProtectedMethod;

	private ClassWithPrivateAnnotatedMember beanWithAnnotatedPrivateMethod;

	private MethodAnnotationOnClassWithNoInterface methodAnnotationOnly = new MethodAnnotationOnClassWithNoInterface();


	public void setAnnotationOnlyOnClassWithNoInterface(
			TransactionalAnnotationOnlyOnClassWithNoInterface annotationOnlyOnClassWithNoInterface) {
		this.annotationOnlyOnClassWithNoInterface = annotationOnlyOnClassWithNoInterface;
	}

	public void setClassWithAnnotatedProtectedMethod(ClassWithProtectedAnnotatedMember aBean) {
		this.beanWithAnnotatedProtectedMethod = aBean;
	}

	public void setClassWithAnnotatedPrivateMethod(ClassWithPrivateAnnotatedMember aBean) {
		this.beanWithAnnotatedPrivateMethod = aBean;
	}

	public void setTransactionAspect(TransactionAspectSupport transactionAspect) {
		this.transactionAspect = transactionAspect;
		this.txManager = (CallCountingTransactionManager) transactionAspect.getTransactionManager();
	}

	public TransactionAspectSupport getTransactionAspect() {
		return this.transactionAspect;
	}

	@Override
	protected String getConfigPath() {
		return "TransactionAspectTests-context.xml";
	}


	public void testCommitOnAnnotatedClass() throws Throwable {
		txManager.clear();
		assertEquals(0, txManager.begun);
		annotationOnlyOnClassWithNoInterface.echo(null);
		assertEquals(1, txManager.commits);
	}

	public void testCommitOnAnnotatedProtectedMethod() throws Throwable {
		txManager.clear();
		assertEquals(0, txManager.begun);
		beanWithAnnotatedProtectedMethod.doInTransaction();
		assertEquals(1, txManager.commits);
	}

	public void testCommitOnAnnotatedPrivateMethod() throws Throwable {
		txManager.clear();
		assertEquals(0, txManager.begun);
		beanWithAnnotatedPrivateMethod.doSomething();
		assertEquals(1, txManager.commits);
	}

	public void testNoCommitOnNonAnnotatedNonPublicMethodInTransactionalType() throws Throwable {
		txManager.clear();
		assertEquals(0,txManager.begun);
		annotationOnlyOnClassWithNoInterface.nonTransactionalMethod();
		assertEquals(0,txManager.begun);
	}

	public void testCommitOnAnnotatedMethod() throws Throwable {
		txManager.clear();
		assertEquals(0, txManager.begun);
		methodAnnotationOnly.echo(null);
		assertEquals(1, txManager.commits);
	}


	public static class NotTransactional {
		public void noop() {
		}
	}

	public void testNotTransactional() throws Throwable {
		txManager.clear();
		assertEquals(0, txManager.begun);
		new NotTransactional().noop();
		assertEquals(0, txManager.begun);
	}


	public void testDefaultCommitOnAnnotatedClass() throws Throwable {
		final Exception ex = new Exception();
		try {
			testRollback(new TransactionOperationCallback() {
				public Object performTransactionalOperation() throws Throwable {
					return annotationOnlyOnClassWithNoInterface.echo(ex);
				}
			}, false);
			fail("Should have thrown Exception");
		}
		catch (Exception ex2) {
			assertSame(ex, ex2);
		}
	}

	public void testDefaultRollbackOnAnnotatedClass() throws Throwable {
		final RuntimeException ex = new RuntimeException();
		try {
			testRollback(new TransactionOperationCallback() {
				public Object performTransactionalOperation() throws Throwable {
					return annotationOnlyOnClassWithNoInterface.echo(ex);
				}
			}, true);
			fail("Should have thrown RuntimeException");
		}
		catch (RuntimeException ex2) {
			assertSame(ex, ex2);
		}
	}


	public void testDefaultCommitOnSubclassOfAnnotatedClass() throws Throwable {
		final Exception ex = new Exception();
		try {
				testRollback(new TransactionOperationCallback() {
				public Object performTransactionalOperation() throws Throwable {
					return new SubclassOfClassWithTransactionalAnnotation().echo(ex);
				}
			}, false);
			fail("Should have thrown Exception");
		}
		catch (Exception ex2) {
			assertSame(ex, ex2);
		}
	}

	public void testDefaultCommitOnSubclassOfClassWithTransactionalMethodAnnotated() throws Throwable {
		final Exception ex = new Exception();
		try {
			testRollback(new TransactionOperationCallback() {
				public Object performTransactionalOperation() throws Throwable {
					return new SubclassOfClassWithTransactionalMethodAnnotation().echo(ex);
				}
			}, false);
			fail("Should have thrown Exception");
		}
		catch (Exception ex2) {
			assertSame(ex, ex2);
		}
	}

	public void testDefaultCommitOnImplementationOfAnnotatedInterface() throws Throwable {
//		testRollback(new TransactionOperationCallback() {
//			public Object performTransactionalOperation() throws Throwable {
//				return new ImplementsAnnotatedInterface().echo(new Exception());
//			}
//		}, false);

		final Exception ex = new Exception();
		testNotTransactional(new TransactionOperationCallback() {
			public Object performTransactionalOperation() throws Throwable {
				return new ImplementsAnnotatedInterface().echo(ex);
			}
		}, ex);
	}

	/**
	 * Note: resolution does not occur. Thus we can't make a class transactional if
	 * it implements a transactionally annotated interface. This behaviour could only
	 * be changed in AbstractFallbackTransactionAttributeSource in Spring proper.
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	public void testDoesNotResolveTxAnnotationOnMethodFromClassImplementingAnnotatedInterface() throws SecurityException, NoSuchMethodException {
		AnnotationTransactionAttributeSource atas = new AnnotationTransactionAttributeSource();
		Method m = ImplementsAnnotatedInterface.class.getMethod("echo", Throwable.class);
		TransactionAttribute ta = atas.getTransactionAttribute(m, ImplementsAnnotatedInterface.class);
		assertNull(ta);
	}


	public void testDefaultRollbackOnImplementationOfAnnotatedInterface() throws Throwable {
//		testRollback(new TransactionOperationCallback() {
//			public Object performTransactionalOperation() throws Throwable {
//				return new ImplementsAnnotatedInterface().echo(new RuntimeException());
//			}
//		}, true);

		final Exception rollbackProvokingException = new RuntimeException();
		testNotTransactional(new TransactionOperationCallback() {
			public Object performTransactionalOperation() throws Throwable {
				return new ImplementsAnnotatedInterface().echo(rollbackProvokingException);
			}
		}, rollbackProvokingException);
	}


	protected void testRollback(TransactionOperationCallback toc, boolean rollback) throws Throwable {
		txManager.clear();
		assertEquals(0, txManager.begun);
		try {
			toc.performTransactionalOperation();
		}
		finally {
			assertEquals(1, txManager.begun);
			assertEquals(rollback ? 0 : 1, txManager.commits);
			assertEquals(rollback ? 1 : 0, txManager.rollbacks);
		}
	}

	protected void testNotTransactional(TransactionOperationCallback toc, Throwable expected) throws Throwable {
		txManager.clear();
		assertEquals(0, txManager.begun);
		try {
			toc.performTransactionalOperation();
		}
		catch (Throwable t) {
			if (expected == null) {
				fail("Expected " + expected);
			}
			assertSame(expected, t);
		}
		finally {
			assertEquals(0, txManager.begun);
		}
	}


	private interface TransactionOperationCallback {

		Object performTransactionalOperation() throws Throwable;
	}


	public static class SubclassOfClassWithTransactionalAnnotation extends TransactionalAnnotationOnlyOnClassWithNoInterface {
	}


	public static class SubclassOfClassWithTransactionalMethodAnnotation extends MethodAnnotationOnClassWithNoInterface {
	}


	public static class ImplementsAnnotatedInterface implements ITransactional {

		public Object echo(Throwable t) throws Throwable {
			if (t != null) {
				throw t;
			}
			return t;
		}
	}

}
