/**
 *
 * Contains classes defining the application context subinterface
 * for UI applications. The theme feature is added here.
 *
 * <ul>
 *   <li>If no {@code UiApplicationContextUtils.THEME_SOURCE_BEAN_NAME}
 * bean is available in the context or parent context, a default {@code ResourceBundleThemeSource}
 * will be created for requested themes. In this case, the base name of the property file will match
 * with the theme name.</li>
 *   <li>If the bean is available in the context or parent context, a {@code basenamePrefix} can be
 *   set before the theme name for locating the property files like this:
 *   <br>{@code
 *   	&lt;bean id="themeSource" class="org.springframework.ui.context.support.ResourceBundleThemeSource"&gt;
 * 	<br>	&lt;property name="basenamePrefix"&gt;&lt;value&gt;theme.&lt;/value&gt;&lt;/property&gt;
 * 	<br>&lt;/bean&gt;
 *   }
 *   <br> in this case, the themes resource bundles will be named {@code theme.<theme_name>XXX.properties}.
 *   </li>
 *   <li>This can be defined at application level and/or at servlet level for web applications.</li>
 *   <li>Normal i18n features of Resource Bundles are available. So a theme message can be dependant
 *   of both theme and locale.</li>
 *   <li>If messages in the resource bundles are in fact paths to resources(css, images, ...), make sure these resources
 *   are directly available for the user and not, for example, under the WEB-INF directory.</li>
 *  </ul>
 *
 * <br>Web packages add the resolution and the setting of the user current theme.
 *
 */
package org.springframework.ui.context;

