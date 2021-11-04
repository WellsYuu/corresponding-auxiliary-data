/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.catalina.storeconfig;

import java.beans.IndexedPropertyDescriptor;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.Iterator;

import org.apache.tomcat.util.IntrospectionUtils;
import org.apache.tomcat.util.descriptor.web.ResourceBase;

/**
 * StoreAppends generate really the xml tag elements
 */
public class StoreAppender {

    /**
     * The set of classes that represent persistable properties.
     */
    private static Class<?> persistables[] = { String.class, Integer.class,
            Integer.TYPE, Boolean.class, Boolean.TYPE, Byte.class, Byte.TYPE,
            Character.class, Character.TYPE, Double.class, Double.TYPE,
            Float.class, Float.TYPE, Long.class, Long.TYPE, Short.class,
            Short.TYPE, InetAddress.class };

    private int pos = 0;

    /**
     * print the closing tag
     *
     * @param aWriter
     * @param aDesc
     * @throws Exception
     */
    public void printCloseTag(PrintWriter aWriter, StoreDescription aDesc)
            throws Exception {
        aWriter.print("</");
        aWriter.print(aDesc.getTag());
        aWriter.println(">");
    }

    /**
     * print only the open tag with all attributes
     *
     * @param aWriter
     * @param indent
     * @param bean
     * @param aDesc
     * @throws Exception
     */
    public void printOpenTag(PrintWriter aWriter, int indent, Object bean,
            StoreDescription aDesc) throws Exception {
        aWriter.print("<");
        aWriter.print(aDesc.getTag());
        if (aDesc.isAttributes() && bean != null)
            printAttributes(aWriter, indent, bean, aDesc);
        aWriter.println(">");
    }

    /**
     * Print tag with all attributes
     *
     * @param aWriter
     * @param indent
     * @param bean
     * @param aDesc
     * @throws Exception
     */
    public void printTag(PrintWriter aWriter, int indent, Object bean,
            StoreDescription aDesc) throws Exception {
        aWriter.print("<");
        aWriter.print(aDesc.getTag());
        if (aDesc.isAttributes() && bean != null)
            printAttributes(aWriter, indent, bean, aDesc);
        aWriter.println("/>");
    }

    /**
     * print the value from tag as content
     *
     * @param aWriter
     * @param tag
     * @param content
     * @throws Exception
     */
    public void printTagContent(PrintWriter aWriter, String tag, String content)
            throws Exception {
        aWriter.print("<");
        aWriter.print(tag);
        aWriter.print(">");
        aWriter.print(convertStr(content));
        aWriter.print("</");
        aWriter.print(tag);
        aWriter.println(">");
    }

    /**
     * print an array of values
     *
     * @param aWriter
     * @param tag
     * @param indent
     * @param elements
     */
    public void printTagValueArray(PrintWriter aWriter, String tag, int indent,
            String[] elements) {
        if (elements != null && elements.length > 0) {
            printIndent(aWriter, indent + 2);
            aWriter.print("<");
            aWriter.print(tag);
            aWriter.print(">");
            for (int i = 0; i < elements.length; i++) {
                printIndent(aWriter, indent + 4);
                aWriter.print(elements[i]);
                if (i + 1 < elements.length)
                    aWriter.println(",");
            }
            printIndent(aWriter, indent + 2);
            aWriter.print("</");
            aWriter.print(tag);
            aWriter.println(">");
        }
    }

    /**
     * print a array of elements
     *
     * @param aWriter
     * @param tag
     * @param indent
     * @param elements
     */
    public void printTagArray(PrintWriter aWriter, String tag, int indent,
            String[] elements) throws Exception {
        if (elements != null) {
            for (int i = 0; i < elements.length; i++) {
                printIndent(aWriter, indent);
                printTagContent(aWriter, tag, elements[i]);
            }
        }
    }

    /**
     * Print some spaces
     *
     * @param aWriter
     * @param indent
     *            number of spaces
     */
    public void printIndent(PrintWriter aWriter, int indent) {
        for (int i = 0; i < indent; i++) {
            aWriter.print(' ');
        }
        pos = indent;
    }

    /**
     * Store the relevant attributes of the specified JavaBean, plus a
     * <code>className</code> attribute defining the fully qualified Java
     * class name of the bean.
     *
     * @param writer
     *            PrintWriter to which we are storing
     * @param bean
     *            Bean whose properties are to be rendered as attributes,
     *
     * @exception Exception
     *                if an exception occurs while storing
     */
    public void printAttributes(PrintWriter writer, int indent, Object bean,
            StoreDescription desc) throws Exception {

        printAttributes(writer, indent, true, bean, desc);

    }

    /**
     * Store the relevant attributes of the specified JavaBean.
     *
     * @param writer
     *            PrintWriter to which we are storing
     * @param include
     *            Should we include a <code>className</code> attribute?
     * @param bean
     *            Bean whose properties are to be rendered as attributes,
     * @param desc
     *            RegistryDescriptor from this bean
     *
     * @exception Exception
     *                if an exception occurs while storing
     */
    public void printAttributes(PrintWriter writer, int indent,
            boolean include, Object bean, StoreDescription desc)
            throws Exception {

        // Render a className attribute if requested
        if (include && desc != null && !desc.isStandard()) {
            writer.print(" className=\"");
            writer.print(bean.getClass().getName());
            writer.print("\"");
        }

        // Acquire the list of properties for this bean
        PropertyDescriptor descriptors[] = Introspector.getBeanInfo(
                bean.getClass()).getPropertyDescriptors();
        if (descriptors == null) {
            descriptors = new PropertyDescriptor[0];
        }

        // Create blank instance
        Object bean2 = defaultInstance(bean);
        for (int i = 0; i < descriptors.length; i++) {
            if (descriptors[i] instanceof IndexedPropertyDescriptor) {
                continue; // Indexed properties are not persisted
            }
            if (!isPersistable(descriptors[i].getPropertyType())
                    || (descriptors[i].getReadMethod() == null)
                    || (descriptors[i].getWriteMethod() == null)) {
                continue; // Must be a read-write primitive or String
            }
            if (desc.isTransientAttribute(descriptors[i].getName())) {
                continue; // Skip the specified exceptions
            }
            Object value = IntrospectionUtils.getProperty(bean, descriptors[i]
                    .getName());
            if (value == null) {
                continue; // Null values are not persisted
            }
            Object value2 = IntrospectionUtils.getProperty(bean2,
                    descriptors[i].getName());
            if (value.equals(value2)) {
                // The property has its default value
                continue;
            }
            printAttribute(writer, indent, bean, desc, descriptors[i].getName(), bean2, value);
        }

        if (bean instanceof ResourceBase) {
            ResourceBase resource = (ResourceBase) bean;
            for (Iterator<String> iter = resource.listProperties(); iter.hasNext();) {
                String name = iter.next();
                Object value = resource.getProperty(name);
                if (!isPersistable(value.getClass())) {
                    continue;
                }
                if (desc.isTransientAttribute(name)) {
                    continue; // Skip the specified exceptions
                }
                printValue(writer, indent, name, value);

            }
        }
    }

    /**
     * @param writer
     * @param indent
     * @param bean
     * @param desc
     * @param attributeName
     * @param bean2
     * @param value
     */
    protected void printAttribute(PrintWriter writer, int indent, Object bean, StoreDescription desc, String attributeName, Object bean2, Object value) {
        if (isPrintValue(bean, bean2, attributeName, desc))
            printValue(writer, indent, attributeName, value);
    }

    /**
     * print this Attribute value or not
     *
     * @param bean
     *            orginal bean
     * @param bean2
     *            default bean
     * @param attrName
     *            attribute name
     * @param desc
     *            StoreDescription from bean
     * @return True if it's a printing value
     */
    public boolean isPrintValue(Object bean, Object bean2, String attrName,
            StoreDescription desc) {
        boolean printValue = false;

        Object value = IntrospectionUtils.getProperty(bean, attrName);
        if (value != null) {
            Object value2 = IntrospectionUtils.getProperty(bean2, attrName);
            printValue = !value.equals(value2);

        }
        return printValue;
    }

    /**
     * generate default Instance
     *
     * @param bean
     * @return an object from same class as bean parameter
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public Object defaultInstance(Object bean) throws InstantiationException,
            IllegalAccessException {
        return bean.getClass().newInstance();
    }

    /**
     * print an attribute value
     *
     * @param writer
     * @param name
     * @param value
     */
    public void printValue(PrintWriter writer, int indent, String name,
            Object value) {
        // Convert IP addresses to strings so they will be persisted
        if (value instanceof InetAddress) {
            value = ((InetAddress) value).getHostAddress();
        }
        if (!(value instanceof String)) {
            value = value.toString();
        }
        String strValue = convertStr((String) value);
        pos = pos + name.length() + strValue.length();
        if (pos > 60) {
            writer.println();
            printIndent(writer, indent + 4);
        } else {
            writer.print(' ');
        }
        writer.print(name);
        writer.print("=\"");
        writer.print(strValue);
        writer.print("\"");
    }

    /**
     * Given a string, this method replaces all occurrences of '&lt;', '&gt;',
     * '&amp;', and '"'.
     */
    public String convertStr(String input) {

        StringBuffer filtered = new StringBuffer(input.length());
        char c;
        for (int i = 0; i < input.length(); i++) {
            c = input.charAt(i);
            if (c == '<') {
                filtered.append("&lt;");
            } else if (c == '>') {
                filtered.append("&gt;");
            } else if (c == '\'') {
                filtered.append("&apos;");
            } else if (c == '"') {
                filtered.append("&quot;");
            } else if (c == '&') {
                filtered.append("&amp;");
            } else {
                filtered.append(c);
            }
        }
        return (filtered.toString());
    }

    /**
     * Is the specified property type one for which we should generate a
     * persistence attribute?
     *
     * @param clazz
     *            Java class to be tested
     */
    protected boolean isPersistable(Class<?> clazz) {

        for (int i = 0; i < persistables.length; i++) {
            if (persistables[i] == clazz || persistables[i].isAssignableFrom(clazz)) {
                return (true);
            }
        }
        return (false);

    }
}
