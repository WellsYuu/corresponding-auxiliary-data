/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.tomcat;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;

/**
 * This interface is implemented by clients of the {@link JarScanner} to enable
 * them to receive notification of a discovered JAR.
 */
public interface JarScannerCallback {

    /**
     * A JAR was found and may be accessed for further processing via the
     * provided URL connection. The caller is responsible for closing the JAR.
     *
     * @param urlConn    The connection to the identified JAR
     * @param webappPath The path, if any, to the JAR within the web application
     * @param isWebapp   Indicates if the JAR was found within a web
     *                       application. If <code>false</code> the JAR should
     *                       be treated as being provided by the container
     *
     * @deprecated Use {@link #scan(Jar, String, boolean)} instead. Because the
     *             urlConn parameter could refer directly to a JAR or to a JAR
     *             as an entry in a WAR, it required further processing that
     *             included obtaining the original URL. It is simpler to provide
     *             the URL to start with.
     *             This method will be removed in Tomcat 8.5.x onwards.
     */
    @Deprecated
    public void scan(JarURLConnection urlConn, String webappPath, boolean isWebapp)
            throws IOException;

    /**
     * A JAR was found and may be accessed for further processing via the
     * provided URL connection.
     *
     * @param jar        The JAR to process
     * @param webappPath The path, if any, to the JAR within the web application
     * @param isWebapp   Indicates if the JAR was found within a web
     *                       application. If <code>false</code> the JAR should
     *                       be treated as being provided by the container
     *
     * @throws IOException if an I/O error occurs while scanning the JAR
     */
    public void scan(Jar jar, String webappPath, boolean isWebapp)
            throws IOException;

    /**
     * A directory was found that is to be treated as an unpacked JAR. The
     * directory may be accessed for further processing via the provided file.
     *
     * @param file       The directory containing the unpacked JAR.
     * @param webappPath The path, if any, to the file within the web
     *                       application
     * @param isWebapp   Indicates if the JAR was found within a web
     *                       application. If <code>false</code> the JAR should
     *                       be treated as being provided by the container
     */
    public void scan(File file, String webappPath, boolean isWebapp) throws IOException;

    /**
     * A directory structure was found within the web application at
     * /WEB-INF/classes that should be handled as an unpacked JAR. Note that all
     * resource access must be via the ServletContext to ensure that any
     * additional resources are visible.
     */
    public void scanWebInfClasses() throws IOException;
}
