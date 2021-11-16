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

package org.springframework.beans.factory.support;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedExceptionAction;

import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

/**
 * Simple object instantiation strategy for use in a BeanFactory.
 *
 * <p>Does not support Method Injection, although it provides hooks for subclasses
 * to override to add Method Injection support, for example by overriding methods.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @since 1.1
 */
public class SimpleInstantiationStrategy implements InstantiationStrategy {

	private static final ThreadLocal<Method> currentlyInvokedFactoryMethod = new ThreadLocal<Method>();


	/**
	 * Return the factory method currently being invoked or {@code null} if none.
	 * <p>Allows factory method implementations to determine whether the current
	 * caller is the container itself as opposed to user code.
	 */
	public static Method getCurrentlyInvokedFactoryMethod() {
		return currentlyInvokedFactoryMethod.get();
	}

	//使用初始化策略实例化Bean对象
	public Object instantiate(RootBeanDefinition beanDefinition, String beanName, BeanFactory owner) {
		// Don't override the class with CGLIB if no overrides.
		//如果Bean定义中没有方法覆盖，则就不需要CGLIB父类类的方法
		if (beanDefinition.getMethodOverrides().isEmpty()) {
			Constructor<?> constructorToUse;
			synchronized (beanDefinition.constructorArgumentLock) {
				//获取对象的构造方法或工厂方法
				constructorToUse = (Constructor<?>) beanDefinition.resolvedConstructorOrFactoryMethod;
				
				//如果没有构造方法且没有工厂方法 
				if (constructorToUse == null) {
					//使用JDK的反射机制，判断要实例化的Bean是否是接口
					final Class clazz = beanDefinition.getBeanClass();
					if (clazz.isInterface()) {
						throw new BeanInstantiationException(clazz, "Specified class is an interface");
					}
					try {
						if (System.getSecurityManager() != null) {
							//这里是一个匿名内置类，使用反射机制获取Bean的构造方法
							constructorToUse = AccessController.doPrivileged(new PrivilegedExceptionAction<Constructor>() {
								public Constructor run() throws Exception {
									return clazz.getDeclaredConstructor((Class[]) null);
								}
							});
						}
						else {
							constructorToUse =	clazz.getDeclaredConstructor((Class[]) null);
						}
						beanDefinition.resolvedConstructorOrFactoryMethod = constructorToUse;
					}
					catch (Exception ex) {
						throw new BeanInstantiationException(clazz, "No default constructor found", ex);
					}
				}
			}
			//使用BeanUtils实例化，通过反射机制调用”构造方法.newInstance(arg)”来进行实例化
			return BeanUtils.instantiateClass(constructorToUse);
		}
		else {
			// Must generate CGLIB subclass.
			//使用CGLIB来实例化对象
			return instantiateWithMethodInjection(beanDefinition, beanName, owner);
		}
	}

	/**
	 * Subclasses can override this method, which is implemented to throw
	 * UnsupportedOperationException, if they can instantiate an object with
	 * the Method Injection specified in the given RootBeanDefinition.
	 * Instantiation should use a no-arg constructor.
	 */
	protected Object instantiateWithMethodInjection(
			RootBeanDefinition beanDefinition, String beanName, BeanFactory owner) {

		throw new UnsupportedOperationException(
				"Method Injection not supported in SimpleInstantiationStrategy");
	}

	public Object instantiate(RootBeanDefinition beanDefinition, String beanName, BeanFactory owner,
			final Constructor<?> ctor, Object[] args) {

		if (beanDefinition.getMethodOverrides().isEmpty()) {
			if (System.getSecurityManager() != null) {
				// use own privileged to change accessibility (when security is on)
				AccessController.doPrivileged(new PrivilegedAction<Object>() {
					public Object run() {
						ReflectionUtils.makeAccessible(ctor);
						return null;
					}
				});
			}
			return BeanUtils.instantiateClass(ctor, args);
		}
		else {
			return instantiateWithMethodInjection(beanDefinition, beanName, owner, ctor, args);
		}
	}

	/**
	 * Subclasses can override this method, which is implemented to throw
	 * UnsupportedOperationException, if they can instantiate an object with
	 * the Method Injection specified in the given RootBeanDefinition.
	 * Instantiation should use the given constructor and parameters.
	 */
	protected Object instantiateWithMethodInjection(RootBeanDefinition beanDefinition,
			String beanName, BeanFactory owner, Constructor ctor, Object[] args) {

		throw new UnsupportedOperationException(
				"Method Injection not supported in SimpleInstantiationStrategy");
	}

	public Object instantiate(RootBeanDefinition beanDefinition, String beanName, BeanFactory owner,
			Object factoryBean, final Method factoryMethod, Object[] args) {

		try {
			if (System.getSecurityManager() != null) {
				AccessController.doPrivileged(new PrivilegedAction<Object>() {
					public Object run() {
						ReflectionUtils.makeAccessible(factoryMethod);
						return null;
					}
				});
			}
			else {
				ReflectionUtils.makeAccessible(factoryMethod);
			}

			Method priorInvokedFactoryMethod = currentlyInvokedFactoryMethod.get();
			try {
				currentlyInvokedFactoryMethod.set(factoryMethod);
				return factoryMethod.invoke(factoryBean, args);
			}
			finally {
				if (priorInvokedFactoryMethod != null) {
					currentlyInvokedFactoryMethod.set(priorInvokedFactoryMethod);
				}
				else {
					currentlyInvokedFactoryMethod.remove();
				}
			}
		}
		catch (IllegalArgumentException ex) {
			throw new BeanDefinitionStoreException(
					"Illegal arguments to factory method [" + factoryMethod + "]; " +
					"args: " + StringUtils.arrayToCommaDelimitedString(args));
		}
		catch (IllegalAccessException ex) {
			throw new BeanDefinitionStoreException(
					"Cannot access factory method [" + factoryMethod + "]; is it public?");
		}
		catch (InvocationTargetException ex) {
			throw new BeanDefinitionStoreException(
					"Factory method [" + factoryMethod + "] threw exception", ex.getTargetException());
		}
	}

}
