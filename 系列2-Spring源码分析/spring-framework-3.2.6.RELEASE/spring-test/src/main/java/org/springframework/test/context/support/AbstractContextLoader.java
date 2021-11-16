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

package org.springframework.test.context.support;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.GenericTypeResolver;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.test.context.ContextConfigurationAttributes;
import org.springframework.test.context.ContextLoader;
import org.springframework.test.context.MergedContextConfiguration;
import org.springframework.test.context.SmartContextLoader;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

/**
 * Abstract application context loader that provides a basis for all concrete
 * implementations of the {@link ContextLoader} SPI. Provides a
 * <em>Template Method</em> based approach for {@link #processLocations processing}
 * resource locations.
 *
 * <p>As of Spring 3.1, {@code AbstractContextLoader} also provides a basis
 * for all concrete implementations of the {@link SmartContextLoader} SPI. For
 * backwards compatibility with the {@code ContextLoader} SPI,
 * {@link #processContextConfiguration(ContextConfigurationAttributes)} delegates
 * to {@link #processLocations(Class, String...)}.
 *
 * @author Sam Brannen
 * @author Juergen Hoeller
 * @since 2.5
 * @see #generateDefaultLocations
 * @see #modifyLocations
 */
public abstract class AbstractContextLoader implements SmartContextLoader {

	private static final Log logger = LogFactory.getLog(AbstractContextLoader.class);

	private static final String[] EMPTY_STRING_ARRAY = new String[0];
	private static final String SLASH = "/";


	// --- SmartContextLoader -----------------------------------------------

	/**
	 * For backwards compatibility with the {@link ContextLoader} SPI, the
	 * default implementation simply delegates to {@link #processLocations(Class, String...)},
	 * passing it the {@link ContextConfigurationAttributes#getDeclaringClass()
	 * declaring class} and {@link ContextConfigurationAttributes#getLocations()
	 * resource locations} retrieved from the supplied
	 * {@link ContextConfigurationAttributes configuration attributes}. The
	 * processed locations are then
	 * {@link ContextConfigurationAttributes#setLocations(String[]) set} in
	 * the supplied configuration attributes.
	 *
	 * <p>Can be overridden in subclasses &mdash; for example, to process
	 * annotated classes instead of resource locations.
	 *
	 * @since 3.1
	 * @see #processLocations(Class, String...)
	 */
	public void processContextConfiguration(ContextConfigurationAttributes configAttributes) {
		String[] processedLocations = processLocations(configAttributes.getDeclaringClass(),
			configAttributes.getLocations());
		configAttributes.setLocations(processedLocations);
	}

	/**
	 * Prepare the {@link ConfigurableApplicationContext} created by this
	 * {@code SmartContextLoader} <i>before</i> bean definitions are read.
	 *
	 * <p>The default implementation:
	 * <ul>
	 * <li>Sets the <em>active bean definition profiles</em> from the supplied
	 * {@code MergedContextConfiguration} in the
	 * {@link org.springframework.core.env.Environment Environment} of the context.</li>
	 * <li>Determines what (if any) context initializer classes have been supplied
	 * via the {@code MergedContextConfiguration} and
	 * {@linkplain ApplicationContextInitializer#initialize invokes each} with the
	 * given application context.</li>
	 * </ul>
	 *
	 * <p>Any {@code ApplicationContextInitializers} implementing
	 * {@link org.springframework.core.Ordered Ordered} or marked with {@link
	 * org.springframework.core.annotation.Order @Order} will be sorted appropriately.
	 *
	 * @param context the newly created application context
	 * @param mergedConfig the merged context configuration
	 * @see ApplicationContextInitializer#initialize(ConfigurableApplicationContext)
	 * @see #loadContext(MergedContextConfiguration)
	 * @see ConfigurableApplicationContext#setId
	 * @since 3.2
	 */
	@SuppressWarnings("unchecked")
	protected void prepareContext(ConfigurableApplicationContext context, MergedContextConfiguration mergedConfig) {
		context.getEnvironment().setActiveProfiles(mergedConfig.getActiveProfiles());

		Set<Class<? extends ApplicationContextInitializer<? extends ConfigurableApplicationContext>>> initializerClasses =
				mergedConfig.getContextInitializerClasses();
		if (initializerClasses.isEmpty()) {
			// no ApplicationContextInitializers have been declared -> nothing to do
			return;
		}

		final List<ApplicationContextInitializer<ConfigurableApplicationContext>> initializerInstances = new ArrayList<ApplicationContextInitializer<ConfigurableApplicationContext>>();
		final Class<?> contextClass = context.getClass();

		for (Class<? extends ApplicationContextInitializer<? extends ConfigurableApplicationContext>> initializerClass : initializerClasses) {
			Class<?> initializerContextClass = GenericTypeResolver.resolveTypeArgument(initializerClass,
				ApplicationContextInitializer.class);
			Assert.isAssignable(initializerContextClass, contextClass, String.format(
				"Could not add context initializer [%s] since its generic parameter [%s] "
						+ "is not assignable from the type of application context used by this "
						+ "context loader [%s]: ", initializerClass.getName(), initializerContextClass.getName(),
				contextClass.getName()));
			initializerInstances.add((ApplicationContextInitializer<ConfigurableApplicationContext>) BeanUtils.instantiateClass(initializerClass));
		}

		AnnotationAwareOrderComparator.sort(initializerInstances);
		for (ApplicationContextInitializer<ConfigurableApplicationContext> initializer : initializerInstances) {
			initializer.initialize(context);
		}
	}

	// --- ContextLoader -------------------------------------------------------

	/**
	 * If the supplied {@code locations} are {@code null} or
	 * <em>empty</em> and {@link #isGenerateDefaultLocations()} returns
	 * {@code true}, default locations will be
	 * {@link #generateDefaultLocations(Class) generated} for the specified
	 * {@link Class class} and the configured
	 * {@link #getResourceSuffix() resource suffix}; otherwise, the supplied
	 * {@code locations} will be {@link #modifyLocations modified} if
	 * necessary and returned.
	 *
	 * @param clazz the class with which the locations are associated: to be
	 * used when generating default locations
	 * @param locations the unmodified locations to use for loading the
	 * application context (can be {@code null} or empty)
	 * @return a processed array of application context resource locations
	 * @since 2.5
	 * @see #isGenerateDefaultLocations()
	 * @see #generateDefaultLocations(Class)
	 * @see #modifyLocations(Class, String...)
	 * @see org.springframework.test.context.ContextLoader#processLocations(Class, String...)
	 * @see #processContextConfiguration(ContextConfigurationAttributes)
	 */
	public final String[] processLocations(Class<?> clazz, String... locations) {
		return (ObjectUtils.isEmpty(locations) && isGenerateDefaultLocations()) ? generateDefaultLocations(clazz)
				: modifyLocations(clazz, locations);
	}

	/**
	 * Generate the default classpath resource locations array based on the
	 * supplied class.
	 *
	 * <p>For example, if the supplied class is {@code com.example.MyTest},
	 * the generated locations will contain a single string with a value of
	 * &quot;classpath:/com/example/MyTest{@code <suffix>}&quot;,
	 * where {@code <suffix>} is the value of the
	 * {@link #getResourceSuffix() resource suffix} string.
	 *
	 * <p>As of Spring 3.1, the implementation of this method adheres to the
	 * contract defined in the {@link SmartContextLoader} SPI. Specifically,
	 * this method will <em>preemptively</em> verify that the generated default
	 * location actually exists. If it does not exist, this method will log a
	 * warning and return an empty array.
	 *
	 * <p>Subclasses can override this method to implement a different
	 * <em>default location generation</em> strategy.
	 *
	 * @param clazz the class for which the default locations are to be generated
	 * @return an array of default application context resource locations
	 * @since 2.5
	 * @see #getResourceSuffix()
	 */
	protected String[] generateDefaultLocations(Class<?> clazz) {
		Assert.notNull(clazz, "Class must not be null");
		String suffix = getResourceSuffix();
		Assert.hasText(suffix, "Resource suffix must not be empty");
		String resourcePath = SLASH + ClassUtils.convertClassNameToResourcePath(clazz.getName()) + suffix;
		String prefixedResourcePath = ResourceUtils.CLASSPATH_URL_PREFIX + resourcePath;
		ClassPathResource classPathResource = new ClassPathResource(resourcePath, clazz);

		if (classPathResource.exists()) {
			if (logger.isInfoEnabled()) {
				logger.info(String.format("Detected default resource location \"%s\" for test class [%s].",
					prefixedResourcePath, clazz.getName()));
			}
			return new String[] { prefixedResourcePath };
		}

		// else
		if (logger.isInfoEnabled()) {
			logger.info(String.format("Could not detect default resource locations for test class [%s]: "
					+ "%s does not exist.", clazz.getName(), classPathResource));
		}
		return EMPTY_STRING_ARRAY;
	}

	/**
	 * Generate a modified version of the supplied locations array and return it.
	 *
	 * <p>A plain path &mdash; for example, &quot;context.xml&quot; &mdash; will
	 * be treated as a classpath resource that is relative to the package in which
	 * the specified class is defined. A path starting with a slash is treated
	 * as an absolute classpath location, for example:
	 * &quot;/org/springframework/whatever/foo.xml&quot;. A path which
	 * references a URL (e.g., a path prefixed with
	 * {@link ResourceUtils#CLASSPATH_URL_PREFIX classpath:},
	 * {@link ResourceUtils#FILE_URL_PREFIX file:}, {@code http:},
	 * etc.) will be added to the results unchanged.
	 *
	 * <p>Subclasses can override this method to implement a different
	 * <em>location modification</em> strategy.
	 *
	 * @param clazz the class with which the locations are associated
	 * @param locations the resource locations to be modified
	 * @return an array of modified application context resource locations
	 * @since 2.5
	 */
	protected String[] modifyLocations(Class<?> clazz, String... locations) {
		String[] modifiedLocations = new String[locations.length];
		for (int i = 0; i < locations.length; i++) {
			String path = locations[i];
			if (path.startsWith(SLASH)) {
				modifiedLocations[i] = ResourceUtils.CLASSPATH_URL_PREFIX + path;
			} else if (!ResourcePatternUtils.isUrl(path)) {
				modifiedLocations[i] = ResourceUtils.CLASSPATH_URL_PREFIX + SLASH
						+ StringUtils.cleanPath(ClassUtils.classPackageAsResourcePath(clazz) + SLASH + path);
			} else {
				modifiedLocations[i] = StringUtils.cleanPath(path);
			}
		}
		return modifiedLocations;
	}

	/**
	 * Determine whether or not <em>default</em> resource locations should be
	 * generated if the {@code locations} provided to
	 * {@link #processLocations(Class, String...)} are {@code null} or empty.
	 *
	 * <p>As of Spring 3.1, the semantics of this method have been overloaded
	 * to include detection of either default resource locations or default
	 * configuration classes. Consequently, this method can also be used to
	 * determine whether or not <em>default</em> configuration classes should be
	 * detected if the {@code classes} present in the
	 * {@link ContextConfigurationAttributes configuration attributes} supplied
	 * to {@link #processContextConfiguration(ContextConfigurationAttributes)}
	 * are {@code null} or empty.
	 *
	 * <p>Can be overridden by subclasses to change the default behavior.
	 *
	 * @return always {@code true} by default
	 * @since 2.5
	 */
	protected boolean isGenerateDefaultLocations() {
		return true;
	}

	/**
	 * Get the suffix to append to {@link ApplicationContext} resource locations
	 * when generating default locations.
	 *
	 * <p>Must be implemented by subclasses.
	 *
	 * @return the resource suffix; should not be {@code null} or empty
	 * @since 2.5
	 * @see #generateDefaultLocations(Class)
	 */
	protected abstract String getResourceSuffix();

}
