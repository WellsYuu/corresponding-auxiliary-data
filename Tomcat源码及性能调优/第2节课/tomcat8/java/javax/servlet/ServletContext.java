/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package javax.servlet;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.Map;
import java.util.Set;

import javax.servlet.descriptor.JspConfigDescriptor;

/**
 * Defines a set of methods that a servlet uses to communicate with its servlet
 * container, for example, to get the MIME type of a file, dispatch requests, or
 * write to a log file.
 * <p>
 * There is one context per "web application" per Java Virtual Machine. (A
 * "web application" is a collection of servlets and content installed under a
 * specific subset of the server's URL namespace such as <code>/catalog</code>
 * and possibly installed via a <code>.war</code> file.)
 * <p>
 * In the case of a web application marked "distributed" in its deployment
 * descriptor, there will be one context instance for each virtual machine. In
 * this situation, the context cannot be used as a location to share global
 * information (because the information won't be truly global). Use an external
 * resource like a database instead.
 * <p>
 * The <code>ServletContext</code> object is contained within the
 * {@link ServletConfig} object, which the Web server provides the servlet when
 * the servlet is initialized.
 *
 * @see Servlet#getServletConfig
 * @see ServletConfig#getServletContext
 */
public interface ServletContext {

    public static final String TEMPDIR = "javax.servlet.context.tempdir";

    /**
     * @since Servlet 3.0
     */
    public static final String ORDERED_LIBS = "javax.servlet.context.orderedLibs";

    /**
     * Return the main path associated with this context.
     *
     * @return The main context path
     *
     * @since Servlet 2.5
     */
    public String getContextPath();

    /**
     * Returns a <code>ServletContext</code> object that corresponds to a
     * specified URL on the server.
     * <p>
     * This method allows servlets to gain access to the context for various
     * parts of the server, and as needed obtain {@link RequestDispatcher}
     * objects from the context. The given path must be begin with "/", is
     * interpreted relative to the server's document root and is matched against
     * the context roots of other web applications hosted on this container.
     * <p>
     * In a security conscious environment, the servlet container may return
     * <code>null</code> for a given URL.
     *
     * @param uripath
     *            a <code>String</code> specifying the context path of another
     *            web application in the container.
     * @return the <code>ServletContext</code> object that corresponds to the
     *         named URL, or null if either none exists or the container wishes
     *         to restrict this access.
     * @see RequestDispatcher
     */
    public ServletContext getContext(String uripath);

    /**
     * Returns the major version of the Java Servlet API that this servlet
     * container supports. All implementations that comply with Version 3.1 must
     * have this method return the integer 3.
     *
     * @return 3
     */
    public int getMajorVersion();

    /**
     * Returns the minor version of the Servlet API that this servlet container
     * supports. All implementations that comply with Version 3.1 must have this
     * method return the integer 1.
     *
     * @return 1
     */
    public int getMinorVersion();

    /**
     * @return TODO
     * @throws UnsupportedOperationException    If called from a
     *    {@link ServletContextListener#contextInitialized(ServletContextEvent)}
     *    method of a {@link ServletContextListener} that was not defined in a
     *    web.xml file, a web-fragment.xml file nor annotated with
     *    {@link javax.servlet.annotation.WebListener}. For example, a
     *    {@link ServletContextListener} defined in a TLD would not be able to
     *    use this method.
     *
     * @since Servlet 3.0 TODO SERVLET3 - Add comments
     */
    public int getEffectiveMajorVersion();

    /**
     * @return TODO
     * @throws UnsupportedOperationException    If called from a
     *    {@link ServletContextListener#contextInitialized(ServletContextEvent)}
     *    method of a {@link ServletContextListener} that was not defined in a
     *    web.xml file, a web-fragment.xml file nor annotated with
     *    {@link javax.servlet.annotation.WebListener}. For example, a
     *    {@link ServletContextListener} defined in a TLD would not be able to
     *    use this method.
     * @since Servlet 3.0 TODO SERVLET3 - Add comments
     */
    public int getEffectiveMinorVersion();

    /**
     * Returns the MIME type of the specified file, or <code>null</code> if the
     * MIME type is not known. The MIME type is determined by the configuration
     * of the servlet container, and may be specified in a web application
     * deployment descriptor. Common MIME types are <code>"text/html"</code> and
     * <code>"image/gif"</code>.
     *
     * @param file
     *            a <code>String</code> specifying the name of a file
     * @return a <code>String</code> specifying the file's MIME type
     */
    public String getMimeType(String file);

    /**
     * Returns a directory-like listing of all the paths to resources within the
     * web application whose longest sub-path matches the supplied path
     * argument. Paths indicating subdirectory paths end with a '/'. The
     * returned paths are all relative to the root of the web application and
     * have a leading '/'. For example, for a web application containing<br>
     * <br>
     * /welcome.html<br>
     * /catalog/index.html<br>
     * /catalog/products.html<br>
     * /catalog/offers/books.html<br>
     * /catalog/offers/music.html<br>
     * /customer/login.jsp<br>
     * /WEB-INF/web.xml<br>
     * /WEB-INF/classes/com.acme.OrderServlet.class,<br>
     * <br>
     * getResourcePaths("/") returns {"/welcome.html", "/catalog/",
     * "/customer/", "/WEB-INF/"}<br>
     * getResourcePaths("/catalog/") returns {"/catalog/index.html",
     * "/catalog/products.html", "/catalog/offers/"}.<br>
     *
     * @param path
     *            the partial path used to match the resources, which must start
     *            with a /
     * @return a Set containing the directory listing, or null if there are no
     *         resources in the web application whose path begins with the
     *         supplied path.
     * @since Servlet 2.3
     */
    public Set<String> getResourcePaths(String path);

    /**
     * Returns a URL to the resource that is mapped to a specified path. The
     * path must begin with a "/" and is interpreted as relative to the current
     * context root.
     * <p>
     * This method allows the servlet container to make a resource available to
     * servlets from any source. Resources can be located on a local or remote
     * file system, in a database, or in a <code>.war</code> file.
     * <p>
     * The servlet container must implement the URL handlers and
     * <code>URLConnection</code> objects that are necessary to access the
     * resource.
     * <p>
     * This method returns <code>null</code> if no resource is mapped to the
     * pathname.
     * <p>
     * Some containers may allow writing to the URL returned by this method
     * using the methods of the URL class.
     * <p>
     * The resource content is returned directly, so be aware that requesting a
     * <code>.jsp</code> page returns the JSP source code. Use a
     * <code>RequestDispatcher</code> instead to include results of an
     * execution.
     * <p>
     * This method has a different purpose than
     * <code>java.lang.Class.getResource</code>, which looks up resources based
     * on a class loader. This method does not use class loaders.
     *
     * @param path
     *            a <code>String</code> specifying the path to the resource
     * @return the resource located at the named path, or <code>null</code> if
     *         there is no resource at that path
     * @exception MalformedURLException
     *                if the pathname is not given in the correct form
     */
    public URL getResource(String path) throws MalformedURLException;

    /**
     * Returns the resource located at the named path as an
     * <code>InputStream</code> object.
     * <p>
     * The data in the <code>InputStream</code> can be of any type or length.
     * The path must be specified according to the rules given in
     * <code>getResource</code>. This method returns <code>null</code> if no
     * resource exists at the specified path.
     * <p>
     * Meta-information such as content length and content type that is
     * available via <code>getResource</code> method is lost when using this
     * method.
     * <p>
     * The servlet container must implement the URL handlers and
     * <code>URLConnection</code> objects necessary to access the resource.
     * <p>
     * This method is different from
     * <code>java.lang.Class.getResourceAsStream</code>, which uses a class
     * loader. This method allows servlet containers to make a resource
     * available to a servlet from any location, without using a class loader.
     *
     * @param path
     *            a <code>String</code> specifying the path to the resource
     * @return the <code>InputStream</code> returned to the servlet, or
     *         <code>null</code> if no resource exists at the specified path
     */
    public InputStream getResourceAsStream(String path);

    /**
     * Returns a {@link RequestDispatcher} object that acts as a wrapper for the
     * resource located at the given path. A <code>RequestDispatcher</code>
     * object can be used to forward a request to the resource or to include the
     * resource in a response. The resource can be dynamic or static.
     * <p>
     * The pathname must begin with a "/" and is interpreted as relative to the
     * current context root. Use <code>getContext</code> to obtain a
     * <code>RequestDispatcher</code> for resources in foreign contexts. This
     * method returns <code>null</code> if the <code>ServletContext</code>
     * cannot return a <code>RequestDispatcher</code>.
     *
     * @param path
     *            a <code>String</code> specifying the pathname to the resource
     * @return a <code>RequestDispatcher</code> object that acts as a wrapper for
     *         the resource at the specified path, or <code>null</code> if the
     *         <code>ServletContext</code> cannot return a
     *         <code>RequestDispatcher</code>
     * @see RequestDispatcher
     * @see ServletContext#getContext
     */
    public RequestDispatcher getRequestDispatcher(String path);

    /**
     * Returns a {@link RequestDispatcher} object that acts as a wrapper for the
     * named servlet.
     * <p>
     * Servlets (and JSP pages also) may be given names via server
     * administration or via a web application deployment descriptor. A servlet
     * instance can determine its name using
     * {@link ServletConfig#getServletName}.
     * <p>
     * This method returns <code>null</code> if the <code>ServletContext</code>
     * cannot return a <code>RequestDispatcher</code> for any reason.
     *
     * @param name
     *            a <code>String</code> specifying the name of a servlet to wrap
     * @return a <code>RequestDispatcher</code> object that acts as a wrapper for
     *         the named servlet, or <code>null</code> if the
     *         <code>ServletContext</code> cannot return a
     *         <code>RequestDispatcher</code>
     * @see RequestDispatcher
     * @see ServletContext#getContext
     * @see ServletConfig#getServletName
     */
    public RequestDispatcher getNamedDispatcher(String name);

    /**
     * Do not use. This method was originally defined to retrieve a servlet from
     * a <code>ServletContext</code>. In this version, this method always
     * returns <code>null</code> and remains only to preserve binary
     * compatibility. This method will be permanently removed in a future
     * version of the Java Servlet API.
     * <p>
     * In lieu of this method, servlets can share information using the
     * <code>ServletContext</code> class and can perform shared business logic
     * by invoking methods on common non-servlet classes.
     *
     * @param name Not used
     *
     * @return Always <code>null</code>
     *
     * @throws ServletException never
     *
     * @deprecated As of Java Servlet API 2.1, with no direct replacement.
     */
    @SuppressWarnings("dep-ann")
    // Spec API does not use @Deprecated
    public Servlet getServlet(String name) throws ServletException;

    /**
     * Do not use. This method was originally defined to return an
     * <code>Enumeration</code> of all the servlets known to this servlet
     * context. In this version, this method always returns an empty enumeration
     * and remains only to preserve binary compatibility. This method will be
     * permanently removed in a future version of the Java Servlet API.
     *
     * @return Always and empty Enumeration
     *
     * @deprecated As of Java Servlet API 2.0, with no replacement.
     */
    @SuppressWarnings("dep-ann")
    // Spec API does not use @Deprecated
    public Enumeration<Servlet> getServlets();

    /**
     * Do not use. This method was originally defined to return an
     * <code>Enumeration</code> of all the servlet names known to this context.
     * In this version, this method always returns an empty
     * <code>Enumeration</code> and remains only to preserve binary
     * compatibility. This method will be permanently removed in a future
     * version of the Java Servlet API.
     *
     * @return Always and empty Enumeration
     *
     * @deprecated As of Java Servlet API 2.1, with no replacement.
     */
    @SuppressWarnings("dep-ann")
    // Spec API does not use @Deprecated
    public Enumeration<String> getServletNames();

    /**
     * Writes the specified message to a servlet log file, usually an event log.
     * The name and type of the servlet log file is specific to the servlet
     * container.
     *
     * @param msg
     *            a <code>String</code> specifying the message to be written to
     *            the log file
     */
    public void log(String msg);

    /**
     * Do not use.
     * @param exception The exception to log
     * @param msg       The message to log with the exception
     * @deprecated As of Java Servlet API 2.1, use
     *             {@link #log(String message, Throwable throwable)} instead.
     *             <p>
     *             This method was originally defined to write an exception's
     *             stack trace and an explanatory error message to the servlet
     *             log file.
     */
    @SuppressWarnings("dep-ann")
    // Spec API does not use @Deprecated
    public void log(Exception exception, String msg);

    /**
     * Writes an explanatory message and a stack trace for a given
     * <code>Throwable</code> exception to the servlet log file. The name and
     * type of the servlet log file is specific to the servlet container,
     * usually an event log.
     *
     * @param message
     *            a <code>String</code> that describes the error or exception
     * @param throwable
     *            the <code>Throwable</code> error or exception
     */
    public void log(String message, Throwable throwable);

    /**
     * Returns a <code>String</code> containing the real path for a given
     * virtual path. For example, the path "/index.html" returns the absolute
     * file path on the server's filesystem would be served by a request for
     * "http://host/contextPath/index.html", where contextPath is the context
     * path of this ServletContext..
     * <p>
     * The real path returned will be in a form appropriate to the computer and
     * operating system on which the servlet container is running, including the
     * proper path separators. This method returns <code>null</code> if the
     * servlet container cannot translate the virtual path to a real path for
     * any reason (such as when the content is being made available from a
     * <code>.war</code> archive).
     *
     * @param path
     *            a <code>String</code> specifying a virtual path
     * @return a <code>String</code> specifying the real path, or null if the
     *         translation cannot be performed
     */
    public String getRealPath(String path);

    /**
     * Returns the name and version of the servlet container on which the
     * servlet is running.
     * <p>
     * The form of the returned string is
     * <i>servername</i>/<i>versionnumber</i>. For example, the JavaServer Web
     * Development Kit may return the string
     * <code>JavaServer Web Dev Kit/1.0</code>.
     * <p>
     * The servlet container may return other optional information after the
     * primary string in parentheses, for example,
     * <code>JavaServer Web Dev Kit/1.0 (JDK 1.1.6; Windows NT 4.0 x86)</code>.
     *
     * @return a <code>String</code> containing at least the servlet container
     *         name and version number
     */
    public String getServerInfo();

    /**
     * Returns a <code>String</code> containing the value of the named
     * context-wide initialization parameter, or <code>null</code> if the
     * parameter does not exist.
     * <p>
     * This method can make available configuration information useful to an
     * entire "web application". For example, it can provide a webmaster's email
     * address or the name of a system that holds critical data.
     *
     * @param name
     *            a <code>String</code> containing the name of the parameter
     *            whose value is requested
     * @return a <code>String</code> containing the value of the initialization
     *         parameter
     * @see ServletConfig#getInitParameter
     */
    public String getInitParameter(String name);

    /**
     * Returns the names of the context's initialization parameters as an
     * <code>Enumeration</code> of <code>String</code> objects, or an empty
     * <code>Enumeration</code> if the context has no initialization parameters.
     *
     * @return an <code>Enumeration</code> of <code>String</code> objects
     *         containing the names of the context's initialization parameters
     * @see ServletConfig#getInitParameter
     */

    public Enumeration<String> getInitParameterNames();

    /**
     * Set the given initialisation parameter to the given value.
     * @param name  Name of initialisation parameter
     * @param value Value for initialisation parameter
     * @return <code>true</code> if the call succeeds or <code>false</code> if
     *         the call fails because an initialisation parameter with the same
     *         name has already been set
     * @throws IllegalStateException If initialisation of this ServletContext
     *         has already completed
     * @throws UnsupportedOperationException    If called from a
     *    {@link ServletContextListener#contextInitialized(ServletContextEvent)}
     *    method of a {@link ServletContextListener} that was not defined in a
     *    web.xml file, a web-fragment.xml file nor annotated with
     *    {@link javax.servlet.annotation.WebListener}. For example, a
     *    {@link ServletContextListener} defined in a TLD would not be able to
     *    use this method.
     * @since Servlet 3.0
     */
    public boolean setInitParameter(String name, String value);

    /**
     * Returns the servlet container attribute with the given name, or
     * <code>null</code> if there is no attribute by that name. An attribute
     * allows a servlet container to give the servlet additional information not
     * already provided by this interface. See your server documentation for
     * information about its attributes. A list of supported attributes can be
     * retrieved using <code>getAttributeNames</code>.
     * <p>
     * The attribute is returned as a <code>java.lang.Object</code> or some
     * subclass. Attribute names should follow the same convention as package
     * names. The Java Servlet API specification reserves names matching
     * <code>java.*</code>, <code>javax.*</code>, and <code>sun.*</code>.
     *
     * @param name
     *            a <code>String</code> specifying the name of the attribute
     * @return an <code>Object</code> containing the value of the attribute, or
     *         <code>null</code> if no attribute exists matching the given name
     * @see ServletContext#getAttributeNames
     */
    public Object getAttribute(String name);

    /**
     * Returns an <code>Enumeration</code> containing the attribute names
     * available within this servlet context. Use the {@link #getAttribute}
     * method with an attribute name to get the value of an attribute.
     *
     * @return an <code>Enumeration</code> of attribute names
     * @see #getAttribute
     */
    public Enumeration<String> getAttributeNames();

    /**
     * Binds an object to a given attribute name in this servlet context. If the
     * name specified is already used for an attribute, this method will replace
     * the attribute with the new to the new attribute.
     * <p>
     * If listeners are configured on the <code>ServletContext</code> the
     * container notifies them accordingly.
     * <p>
     * If a null value is passed, the effect is the same as calling
     * <code>removeAttribute()</code>.
     * <p>
     * Attribute names should follow the same convention as package names. The
     * Java Servlet API specification reserves names matching
     * <code>java.*</code>, <code>javax.*</code>, and <code>sun.*</code>.
     *
     * @param name
     *            a <code>String</code> specifying the name of the attribute
     * @param object
     *            an <code>Object</code> representing the attribute to be bound
     */
    public void setAttribute(String name, Object object);

    /**
     * Removes the attribute with the given name from the servlet context. After
     * removal, subsequent calls to {@link #getAttribute} to retrieve the
     * attribute's value will return <code>null</code>.
     * <p>
     * If listeners are configured on the <code>ServletContext</code> the
     * container notifies them accordingly.
     *
     * @param name
     *            a <code>String</code> specifying the name of the attribute to
     *            be removed
     */
    public void removeAttribute(String name);

    /**
     * Returns the name of this web application corresponding to this
     * ServletContext as specified in the deployment descriptor for this web
     * application by the display-name element.
     *
     * @return The name of the web application or null if no name has been
     *         declared in the deployment descriptor.
     * @since Servlet 2.3
     */
    public String getServletContextName();

    /**
     * Register a servlet implementation for use in this ServletContext.
     * @param servletName The name of the servlet to register
     * @param className   The implementation class for the servlet
     * @return The registration object that enables further configuration
     * @throws IllegalStateException
     *             If the context has already been initialised
     * @throws UnsupportedOperationException    If called from a
     *    {@link ServletContextListener#contextInitialized(ServletContextEvent)}
     *    method of a {@link ServletContextListener} that was not defined in a
     *    web.xml file, a web-fragment.xml file nor annotated with
     *    {@link javax.servlet.annotation.WebListener}. For example, a
     *    {@link ServletContextListener} defined in a TLD would not be able to
     *    use this method.
     * @since Servlet 3.0
     */
    public ServletRegistration.Dynamic addServlet(String servletName, String className);

    /**
     * Register a servlet instance for use in this ServletContext.
     * @param servletName The name of the servlet to register
     * @param servlet     The Servlet instance to register
     * @return The registration object that enables further configuration
     * @throws IllegalStateException
     *             If the context has already been initialised
     * @throws UnsupportedOperationException    If called from a
     *    {@link ServletContextListener#contextInitialized(ServletContextEvent)}
     *    method of a {@link ServletContextListener} that was not defined in a
     *    web.xml file, a web-fragment.xml file nor annotated with
     *    {@link javax.servlet.annotation.WebListener}. For example, a
     *    {@link ServletContextListener} defined in a TLD would not be able to
     *    use this method.
     * @since Servlet 3.0
     */
    public ServletRegistration.Dynamic addServlet(String servletName, Servlet servlet);

    /**
     * Add servlet to context.
     * @param   servletName  Name of servlet to add
     * @param   servletClass Class of servlet to add
     * @return  <code>null</code> if the servlet has already been fully defined,
     *          else a {@link javax.servlet.ServletRegistration.Dynamic} object
     *          that can be used to further configure the servlet
     * @throws IllegalStateException
     *             If the context has already been initialised
     * @throws UnsupportedOperationException    If called from a
     *    {@link ServletContextListener#contextInitialized(ServletContextEvent)}
     *    method of a {@link ServletContextListener} that was not defined in a
     *    web.xml file, a web-fragment.xml file nor annotated with
     *    {@link javax.servlet.annotation.WebListener}. For example, a
     *    {@link ServletContextListener} defined in a TLD would not be able to
     *    use this method.
     * @since Servlet 3.0
     */
    public ServletRegistration.Dynamic addServlet(String servletName,
            Class<? extends Servlet> servletClass);

    /**
     * TODO SERVLET3 - Add comments
     * @param <T> TODO
     * @param c   TODO
     * @return TODO
     * @throws ServletException TODO
     * @throws UnsupportedOperationException    If called from a
     *    {@link ServletContextListener#contextInitialized(ServletContextEvent)}
     *    method of a {@link ServletContextListener} that was not defined in a
     *    web.xml file, a web-fragment.xml file nor annotated with
     *    {@link javax.servlet.annotation.WebListener}. For example, a
     *    {@link ServletContextListener} defined in a TLD would not be able to
     *    use this method.
     * @since Servlet 3.0
     */
    public <T extends Servlet> T createServlet(Class<T> c)
            throws ServletException;

    /**
     * Obtain the details of the named servlet.
     *
     * @param servletName   The name of the Servlet of interest
     *
     * @return  The registration details for the named Servlet or
     *          <code>null</code> if no Servlet has been registered with the
     *          given name
     *
     * @throws UnsupportedOperationException    If called from a
     *    {@link ServletContextListener#contextInitialized(ServletContextEvent)}
     *    method of a {@link ServletContextListener} that was not defined in a
     *    web.xml file, a web-fragment.xml file nor annotated with
     *    {@link javax.servlet.annotation.WebListener}. For example, a
     *    {@link ServletContextListener} defined in a TLD would not be able to
     *    use this method.
     *
     * @since Servlet 3.0
     */
    public ServletRegistration getServletRegistration(String servletName);

    /**
     * TODO SERVLET3 - Add comments
     * @return TODO
     * @throws UnsupportedOperationException    If called from a
     *    {@link ServletContextListener#contextInitialized(ServletContextEvent)}
     *    method of a {@link ServletContextListener} that was not defined in a
     *    web.xml file, a web-fragment.xml file nor annotated with
     *    {@link javax.servlet.annotation.WebListener}. For example, a
     *    {@link ServletContextListener} defined in a TLD would not be able to
     *    use this method.
     * @since Servlet 3.0
     */
    public Map<String, ? extends ServletRegistration> getServletRegistrations();

    /**
     * Add filter to context.
     * @param   filterName  Name of filter to add
     * @param   className Name of filter class
     * @return  <code>null</code> if the filter has already been fully defined,
     *          else a {@link javax.servlet.FilterRegistration.Dynamic} object
     *          that can be used to further configure the filter
     * @throws UnsupportedOperationException    If called from a
     *    {@link ServletContextListener#contextInitialized(ServletContextEvent)}
     *    method of a {@link ServletContextListener} that was not defined in a
     *    web.xml file, a web-fragment.xml file nor annotated with
     *    {@link javax.servlet.annotation.WebListener}. For example, a
     *    {@link ServletContextListener} defined in a TLD would not be able to
     *    use this method.
     * @throws IllegalStateException
     *             If the context has already been initialised
     * @since Servlet 3.0
     */
    public FilterRegistration.Dynamic addFilter(String filterName, String className);

    /**
     * Add filter to context.
     * @param   filterName  Name of filter to add
     * @param   filter      Filter to add
     * @return  <code>null</code> if the filter has already been fully defined,
     *          else a {@link javax.servlet.FilterRegistration.Dynamic} object
     *          that can be used to further configure the filter
     * @throws UnsupportedOperationException    If called from a
     *    {@link ServletContextListener#contextInitialized(ServletContextEvent)}
     *    method of a {@link ServletContextListener} that was not defined in a
     *    web.xml file, a web-fragment.xml file nor annotated with
     *    {@link javax.servlet.annotation.WebListener}. For example, a
     *    {@link ServletContextListener} defined in a TLD would not be able to
     *    use this method.
     * @throws IllegalStateException
     *             If the context has already been initialised
     * @since Servlet 3.0
     */
    public FilterRegistration.Dynamic addFilter(String filterName, Filter filter);

    /**
     * Add filter to context.
     * @param   filterName  Name of filter to add
     * @param   filterClass Class of filter to add
     * @return  <code>null</code> if the filter has already been fully defined,
     *          else a {@link javax.servlet.FilterRegistration.Dynamic} object
     *          that can be used to further configure the filter
     * @throws UnsupportedOperationException    If called from a
     *    {@link ServletContextListener#contextInitialized(ServletContextEvent)}
     *    method of a {@link ServletContextListener} that was not defined in a
     *    web.xml file, a web-fragment.xml file nor annotated with
     *    {@link javax.servlet.annotation.WebListener}. For example, a
     *    {@link ServletContextListener} defined in a TLD would not be able to
     *    use this method.
     * @throws IllegalStateException
     *             If the context has already been initialised
     * @since Servlet 3.0
     */
    public FilterRegistration.Dynamic addFilter(String filterName,
            Class<? extends Filter> filterClass);

    /**
     * TODO SERVLET3 - Add comments
     * @param <T> TODO
     * @param c   TODO
     * @return TODO
     * @throws UnsupportedOperationException    If called from a
     *    {@link ServletContextListener#contextInitialized(ServletContextEvent)}
     *    method of a {@link ServletContextListener} that was not defined in a
     *    web.xml file, a web-fragment.xml file nor annotated with
     *    {@link javax.servlet.annotation.WebListener}. For example, a
     *    {@link ServletContextListener} defined in a TLD would not be able to
     *    use this method.
     * @throws ServletException TODO
     * @since Servlet 3.
     */
    public <T extends Filter> T createFilter(Class<T> c) throws ServletException;

    /**
     * TODO SERVLET3 - Add comments
     * @param filterName TODO
     * @return TODO
     * @throws UnsupportedOperationException    If called from a
     *    {@link ServletContextListener#contextInitialized(ServletContextEvent)}
     *    method of a {@link ServletContextListener} that was not defined in a
     *    web.xml file, a web-fragment.xml file nor annotated with
     *    {@link javax.servlet.annotation.WebListener}. For example, a
     *    {@link ServletContextListener} defined in a TLD would not be able to
     *    use this method.
     * @since Servlet 3.0
     */
    public FilterRegistration getFilterRegistration(String filterName);

    /**
     * @return TODO
     * @throws UnsupportedOperationException    If called from a
     *    {@link ServletContextListener#contextInitialized(ServletContextEvent)}
     *    method of a {@link ServletContextListener} that was not defined in a
     *    web.xml file, a web-fragment.xml file nor annotated with
     *    {@link javax.servlet.annotation.WebListener}. For example, a
     *    {@link ServletContextListener} defined in a TLD would not be able to
     *    use this method.
     * @since Servlet 3.0 TODO SERVLET3 - Add comments
     */
    public Map<String, ? extends FilterRegistration> getFilterRegistrations();

    /**
     * @return TODO
     * @throws UnsupportedOperationException    If called from a
     *    {@link ServletContextListener#contextInitialized(ServletContextEvent)}
     *    method of a {@link ServletContextListener} that was not defined in a
     *    web.xml file, a web-fragment.xml file nor annotated with
     *    {@link javax.servlet.annotation.WebListener}. For example, a
     *    {@link ServletContextListener} defined in a TLD would not be able to
     *    use this method.
     * @since Servlet 3.0 TODO SERVLET3 - Add comments
     */
    public SessionCookieConfig getSessionCookieConfig();

    /**
     * Configures the available session tracking modes for this web application.
     * @param sessionTrackingModes The session tracking modes to use for this
     *        web application
     * @throws IllegalArgumentException
     *             If sessionTrackingModes specifies
     *             {@link SessionTrackingMode#SSL} in combination with any other
     *             {@link SessionTrackingMode}
     * @throws IllegalStateException
     *             If the context has already been initialised
     * @throws UnsupportedOperationException    If called from a
     *    {@link ServletContextListener#contextInitialized(ServletContextEvent)}
     *    method of a {@link ServletContextListener} that was not defined in a
     *    web.xml file, a web-fragment.xml file nor annotated with
     *    {@link javax.servlet.annotation.WebListener}. For example, a
     *    {@link ServletContextListener} defined in a TLD would not be able to
     *    use this method.
     * @since Servlet 3.0
     */
    public void setSessionTrackingModes(
            Set<SessionTrackingMode> sessionTrackingModes);

    /**
     * Obtains the default session tracking modes for this web application.
     * By default {@link SessionTrackingMode#URL} is always supported, {@link
     * SessionTrackingMode#COOKIE} is supported unless the <code>cookies</code>
     * attribute has been set to <code>false</code> for the context and {@link
     * SessionTrackingMode#SSL} is supported if at least one of the connectors
     * used by this context has the attribute <code>secure</code> set to
     * <code>true</code>.
     * @return The set of default session tracking modes for this web
     *         application
     * @throws UnsupportedOperationException    If called from a
     *    {@link ServletContextListener#contextInitialized(ServletContextEvent)}
     *    method of a {@link ServletContextListener} that was not defined in a
     *    web.xml file, a web-fragment.xml file nor annotated with
     *    {@link javax.servlet.annotation.WebListener}. For example, a
     *    {@link ServletContextListener} defined in a TLD would not be able to
     *    use this method.
     * @since Servlet 3.0
     */
    public Set<SessionTrackingMode> getDefaultSessionTrackingModes();

    /**
     * Obtains the currently enabled session tracking modes for this web
     * application.
     * @return The value supplied via {@link #setSessionTrackingModes(Set)} if
     *         one was previously set, else return the defaults
     * @throws UnsupportedOperationException    If called from a
     *    {@link ServletContextListener#contextInitialized(ServletContextEvent)}
     *    method of a {@link ServletContextListener} that was not defined in a
     *    web.xml file, a web-fragment.xml file nor annotated with
     *    {@link javax.servlet.annotation.WebListener}. For example, a
     *    {@link ServletContextListener} defined in a TLD would not be able to
     *    use this method.
     * @since Servlet 3.0
     */
    public Set<SessionTrackingMode> getEffectiveSessionTrackingModes();

    /**
     * TODO SERVLET3 - Add comments
     * @param className TODO
     * @throws UnsupportedOperationException    If called from a
     *    {@link ServletContextListener#contextInitialized(ServletContextEvent)}
     *    method of a {@link ServletContextListener} that was not defined in a
     *    web.xml file, a web-fragment.xml file nor annotated with
     *    {@link javax.servlet.annotation.WebListener}. For example, a
     *    {@link ServletContextListener} defined in a TLD would not be able to
     *    use this method.
     * @since Servlet 3.0
     */
    public void addListener(String className);

    /**
     * TODO SERVLET3 - Add comments
     * @param <T> TODO
     * @param t   TODO
     * @throws UnsupportedOperationException    If called from a
     *    {@link ServletContextListener#contextInitialized(ServletContextEvent)}
     *    method of a {@link ServletContextListener} that was not defined in a
     *    web.xml file, a web-fragment.xml file nor annotated with
     *    {@link javax.servlet.annotation.WebListener}. For example, a
     *    {@link ServletContextListener} defined in a TLD would not be able to
     *    use this method.
     * @since Servlet 3.0
     */
    public <T extends EventListener> void addListener(T t);

    /**
     * TODO SERVLET3 - Add comments
     * @param listenerClass TODO
     * @throws UnsupportedOperationException    If called from a
     *    {@link ServletContextListener#contextInitialized(ServletContextEvent)}
     *    method of a {@link ServletContextListener} that was not defined in a
     *    web.xml file, a web-fragment.xml file nor annotated with
     *    {@link javax.servlet.annotation.WebListener}. For example, a
     *    {@link ServletContextListener} defined in a TLD would not be able to
     *    use this method.
     * @since Servlet 3.0
     */
    public void addListener(Class<? extends EventListener> listenerClass);

    /**
     * TODO SERVLET3 - Add comments
     * @param <T> TODO
     * @param c TODO
     * @return TODO
     * @throws ServletException TODO
     * @throws UnsupportedOperationException    If called from a
     *    {@link ServletContextListener#contextInitialized(ServletContextEvent)}
     *    method of a {@link ServletContextListener} that was not defined in a
     *    web.xml file, a web-fragment.xml file nor annotated with
     *    {@link javax.servlet.annotation.WebListener}. For example, a
     *    {@link ServletContextListener} defined in a TLD would not be able to
     *    use this method.
     * @since Servlet 3.0
     */
    public <T extends EventListener> T createListener(Class<T> c)
            throws ServletException;

    /**
     * @return TODO
     * @throws UnsupportedOperationException    If called from a
     *    {@link ServletContextListener#contextInitialized(ServletContextEvent)}
     *    method of a {@link ServletContextListener} that was not defined in a
     *    web.xml file, a web-fragment.xml file nor annotated with
     *    {@link javax.servlet.annotation.WebListener}. For example, a
     *    {@link ServletContextListener} defined in a TLD would not be able to
     *    use this method.
     * @since Servlet 3.0 TODO SERVLET3 - Add comments
     */
    public JspConfigDescriptor getJspConfigDescriptor();

    /**
     * Get the web application class loader associated with this ServletContext.
     *
     * @return The associated web application class loader
     *
     * @throws UnsupportedOperationException    If called from a
     *    {@link ServletContextListener#contextInitialized(ServletContextEvent)}
     *    method of a {@link ServletContextListener} that was not defined in a
     *    web.xml file, a web-fragment.xml file nor annotated with
     *    {@link javax.servlet.annotation.WebListener}. For example, a
     *    {@link ServletContextListener} defined in a TLD would not be able to
     *    use this method.
     * @throws SecurityException if access to the class loader is prevented by a
     *         SecurityManager
     * @since Servlet 3.0
     */
    public ClassLoader getClassLoader();

    /**
     * Add to the declared roles for this ServletContext.
     * @param roleNames The roles to add
     * @throws UnsupportedOperationException    If called from a
     *    {@link ServletContextListener#contextInitialized(ServletContextEvent)}
     *    method of a {@link ServletContextListener} that was not defined in a
     *    web.xml file, a web-fragment.xml file nor annotated with
     *    {@link javax.servlet.annotation.WebListener}. For example, a
     *    {@link ServletContextListener} defined in a TLD would not be able to
     *    use this method.
     * @throws IllegalArgumentException If the list of roleNames is null or
     *         empty
     * @throws IllegalStateException If the ServletContext has already been
     *         initialised
     * @since Servlet 3.0
     */
    public void declareRoles(String... roleNames);

    /**
     * Get the primary name of the virtual host on which this context is
     * deployed. The name may or may not be a valid host name.
     *
     * @return The primary name of the virtual host on which this context is
     *         deployed
     * @since Servlet 3.1
     */
    public String getVirtualServerName();
}
