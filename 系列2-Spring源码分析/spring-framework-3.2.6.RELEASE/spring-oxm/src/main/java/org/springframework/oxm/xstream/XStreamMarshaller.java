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

package org.springframework.oxm.xstream;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.ConverterMatcher;
import com.thoughtworks.xstream.converters.SingleValueConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamDriver;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.StreamException;
import com.thoughtworks.xstream.io.xml.CompactWriter;
import com.thoughtworks.xstream.io.xml.DomReader;
import com.thoughtworks.xstream.io.xml.DomWriter;
import com.thoughtworks.xstream.io.xml.QNameMap;
import com.thoughtworks.xstream.io.xml.SaxWriter;
import com.thoughtworks.xstream.io.xml.StaxReader;
import com.thoughtworks.xstream.io.xml.StaxWriter;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;
import com.thoughtworks.xstream.io.xml.XppReader;
import com.thoughtworks.xstream.mapper.CannotResolveClassException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.ext.LexicalHandler;

import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.oxm.MarshallingFailureException;
import org.springframework.oxm.UncategorizedMappingException;
import org.springframework.oxm.UnmarshallingFailureException;
import org.springframework.oxm.XmlMappingException;
import org.springframework.oxm.support.AbstractMarshaller;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.util.xml.StaxUtils;

/**
 * Implementation of the {@code Marshaller} interface for XStream.
 *
 * <p>By default, XStream does not require any further configuration and can (un)marshal
 * any class on the classpath. As such, it is <b>not recommended to use the
 * {@code XStreamMarshaller} to unmarshal XML from external sources</b> (i.e. the Web),
 * as this can result in <b>security vulnerabilities</b>. If you do use the
 * {@code XStreamMarshaller} to unmarshal external XML, set the
 * {@link #setSupportedClasses(Class[]) supportedClasses} and
 * {@link #setConverters(ConverterMatcher[]) converters} properties (possibly using a
 * {@link CatchAllConverter} as the last converter in the list) or override the
 * {@link #customizeXStream(XStream)} method to make sure it only accepts the classes
 * you want it to support.
 *
 * <p>Due to XStream's API, it is required to set the encoding used for writing to OutputStreams.
 * It defaults to {@code UTF-8}.
 *
 * <p><b>NOTE:</b> XStream is an XML serialization library, not a data binding library.
 * Therefore, it has limited namespace support. As such, it is rather unsuitable for
 * usage within Web Services.
 *
 * <p>This marshaller is compatible with XStream 1.3 and 1.4.
 *
 * @author Peter Meijer
 * @author Arjen Poutsma
 * @since 3.0
 */
public class XStreamMarshaller extends AbstractMarshaller implements InitializingBean, BeanClassLoaderAware {

	/**
	 * The default encoding used for stream access: UTF-8.
	 */
	public static final String DEFAULT_ENCODING = "UTF-8";


	private final XStream xstream = new XStream();

	private HierarchicalStreamDriver streamDriver;

	private String encoding = DEFAULT_ENCODING;

	private Class<?>[] supportedClasses;

	private ClassLoader beanClassLoader;


	/**
	 * Return the XStream instance used by this marshaller.
	 * <p><b>NOTE:</b> While this method can be overridden in Spring 3.x,
	 * it wasn't originally meant to be. As of Spring 4.0, it will be
	 * marked as final, with all of XStream 1.4's configurable strategies
	 * to be exposed on XStreamMarshaller itself.
	 */
	public XStream getXStream() {
		return this.xstream;
	}

	/**
	 * Set the XStream mode.
	 * @see XStream#XPATH_REFERENCES
	 * @see XStream#ID_REFERENCES
	 * @see XStream#NO_REFERENCES
	 */
	public void setMode(int mode) {
		getXStream().setMode(mode);
	}

	/**
	 * Set the {@code Converters} or {@code SingleValueConverters} to be registered
	 * with the {@code XStream} instance.
	 * @see Converter
	 * @see SingleValueConverter
	 */
	public void setConverters(ConverterMatcher... converters) {
		for (int i = 0; i < converters.length; i++) {
			if (converters[i] instanceof Converter) {
				getXStream().registerConverter((Converter) converters[i], i);
			}
			else if (converters[i] instanceof SingleValueConverter) {
				getXStream().registerConverter((SingleValueConverter) converters[i], i);
			}
			else {
				throw new IllegalArgumentException("Invalid ConverterMatcher [" + converters[i] + "]");
			}
		}
	}

	/**
	 * Set an alias/type map, consisting of String aliases mapped to classes.
	 * <p>Keys are aliases; values are either Class objects or String class names.
	 * @see XStream#alias(String, Class)
	 */
	public void setAliases(Map<String, ?> aliases) throws ClassNotFoundException {
		Map<String, Class<?>> classMap = toClassMap(aliases);
		for (Map.Entry<String, Class<?>> entry : classMap.entrySet()) {
			getXStream().alias(entry.getKey(), entry.getValue());
		}
	}

	/**
	 * Set the aliases by type map, consisting of String aliases mapped to classes.
	 * <p>Any class that is assignable to this type will be aliased to the same name.
	 * Keys are aliases; values are either Class objects or String class names.
	 * @see XStream#aliasType(String, Class)
	 */
	public void setAliasesByType(Map<String, ?> aliases) throws ClassNotFoundException {
		Map<String, Class<?>> classMap = toClassMap(aliases);
		for (Map.Entry<String, Class<?>> entry : classMap.entrySet()) {
			getXStream().aliasType(entry.getKey(), entry.getValue());
		}
	}

	private Map<String, Class<?>> toClassMap(Map<String, ?> map) throws ClassNotFoundException {
		Map<String, Class<?>> result = new LinkedHashMap<String, Class<?>>(map.size());
		for (Map.Entry<String, ?> entry : map.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			Class<?> type;
			if (value instanceof Class) {
				type = (Class<?>) value;
			}
			else if (value instanceof String) {
				String className = (String) value;
				type = ClassUtils.forName(className, this.beanClassLoader);
			}
			else {
				throw new IllegalArgumentException("Unknown value [" + value + "] - expected String or Class");
			}
			result.put(key, type);
		}
		return result;
	}

	/**
	 * Set a field alias/type map, consisting of field names.
	 * @see XStream#aliasField(String, Class, String)
	 */
	public void setFieldAliases(Map<String, String> aliases) throws ClassNotFoundException, NoSuchFieldException {
		for (Map.Entry<String, String> entry : aliases.entrySet()) {
			String alias = entry.getValue();
			String field = entry.getKey();
			int idx = field.lastIndexOf('.');
			if (idx != -1) {
				String className = field.substring(0, idx);
				Class<?> clazz = ClassUtils.forName(className, this.beanClassLoader);
				String fieldName = field.substring(idx + 1);
				getXStream().aliasField(alias, clazz, fieldName);
			}
			else {
				throw new IllegalArgumentException("Field name [" + field + "] does not contain '.'");
			}
		}
	}

	/**
	 * Set types to use XML attributes for.
	 * @see XStream#useAttributeFor(Class)
	 */
	public void setUseAttributeForTypes(Class<?>... types) {
		for (Class<?> type : types) {
			getXStream().useAttributeFor(type);
		}
	}

	/**
	 * Set the types to use XML attributes for. The given map can contain
	 * either {@code <String, Class>} pairs, in which case
	 * {@link XStream#useAttributeFor(String, Class)} is called.
	 * Alternatively, the map can contain {@code <Class, String>}
	 * or {@code <Class, List<String>>} pairs, which results in
	 * {@link XStream#useAttributeFor(Class, String)} calls.
	 */
	public void setUseAttributeFor(Map<?, ?> attributes) {
		for (Map.Entry<?, ?> entry : attributes.entrySet()) {
			if (entry.getKey() instanceof String) {
				if (entry.getValue() instanceof Class) {
					getXStream().useAttributeFor((String) entry.getKey(), (Class) entry.getValue());
				}
				else {
					throw new IllegalArgumentException(
							"'useAttributesFor' takes Map<String, Class> when using a map key of type String");
				}
			}
			else if (entry.getKey() instanceof Class) {
				Class<?> key = (Class<?>) entry.getKey();
				if (entry.getValue() instanceof String) {
					getXStream().useAttributeFor(key, (String) entry.getValue());
				}
				else if (entry.getValue() instanceof List) {
					List listValue = (List) entry.getValue();
					for (Object element : listValue) {
						if (element instanceof String) {
							getXStream().useAttributeFor(key, (String) element);
						}
					}
				}
				else {
					throw new IllegalArgumentException("'useAttributesFor' property takes either Map<Class, String> " +
							"or Map<Class, List<String>> when using a map key of type Class");
				}
			}
			else {
				throw new IllegalArgumentException(
						"'useAttributesFor' property takes either a map key of type String or Class");
			}
		}
	}

	/**
	 * Specify implicit collection fields, as a Map consisting of {@code Class} instances
	 * mapped to comma separated collection field names.
	 * @see XStream#addImplicitCollection(Class, String)
	 */
	public void setImplicitCollections(Map<Class<?>, String> implicitCollections) {
		for (Map.Entry<Class<?>, String> entry : implicitCollections.entrySet()) {
			String[] collectionFields = StringUtils.commaDelimitedListToStringArray(entry.getValue());
			for (String collectionField : collectionFields) {
				getXStream().addImplicitCollection(entry.getKey(), collectionField);
			}
		}
	}

	/**
	 * Specify omitted fields, as a Map consisting of {@code Class} instances
	 * mapped to comma separated field names.
	 * @see XStream#omitField(Class, String)
	 */
	public void setOmittedFields(Map<Class<?>, String> omittedFields) {
		for (Map.Entry<Class<?>, String> entry : omittedFields.entrySet()) {
			String[] fields = StringUtils.commaDelimitedListToStringArray(entry.getValue());
			for (String field : fields) {
				getXStream().omitField(entry.getKey(), field);
			}
		}
	}

	/**
	 * Set the classes for which mappings will be read from class-level annotation metadata.
	 * @see XStream#processAnnotations(Class)
	 * @deprecated in favor of {@link #setAnnotatedClasses} with varargs
	 */
	@Deprecated
	public void setAnnotatedClass(Class<?> annotatedClass) {
		Assert.notNull(annotatedClass, "'annotatedClass' must not be null");
		getXStream().processAnnotations(annotatedClass);
	}

	/**
	 * Set annotated classes for which aliases will be read from class-level annotation metadata.
	 * @see XStream#processAnnotations(Class[])
	 */
	public void setAnnotatedClasses(Class<?>... annotatedClasses) {
		Assert.notEmpty(annotatedClasses, "'annotatedClasses' must not be empty");
		getXStream().processAnnotations(annotatedClasses);
	}

	/**
	 * Activate XStream's autodetection mode.
	 * <p><b>Note</b>: Autodetection implies that the XStream instance is being configured while
	 * it is processing the XML streams, and thus introduces a potential concurrency problem.
	 * @see XStream#autodetectAnnotations(boolean)
	 */
	public void setAutodetectAnnotations(boolean autodetectAnnotations) {
		getXStream().autodetectAnnotations(autodetectAnnotations);
	}

	/**
	 * Set the XStream hierarchical stream driver to be used with stream readers and writers.
	 */
	public void setStreamDriver(HierarchicalStreamDriver streamDriver) {
		this.streamDriver = streamDriver;
	}

	/**
	 * Set the encoding to be used for stream access.
	 * @see #DEFAULT_ENCODING
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * Set the classes supported by this marshaller.
	 * <p>If this property is empty (the default), all classes are supported.
	 * @see #supports(Class)
	 */
	public void setSupportedClasses(Class<?>... supportedClasses) {
		this.supportedClasses = supportedClasses;
	}

	public void setBeanClassLoader(ClassLoader classLoader) {
		this.beanClassLoader = classLoader;
		getXStream().setClassLoader(classLoader);
	}


	public final void afterPropertiesSet() throws Exception {
		customizeXStream(getXStream());
	}

	/**
	 * Template to allow for customizing of the given {@link XStream}.
	 * <p>The default implementation is empty.
	 * @param xstream the {@code XStream} instance
	 */
	protected void customizeXStream(XStream xstream) {
	}


	public boolean supports(Class<?> clazz) {
		if (ObjectUtils.isEmpty(this.supportedClasses)) {
			return true;
		}
		else {
			for (Class<?> supportedClass : this.supportedClasses) {
				if (supportedClass.isAssignableFrom(clazz)) {
					return true;
				}
			}
			return false;
		}
	}


	// Marshalling

	@Override
	protected void marshalDomNode(Object graph, Node node) throws XmlMappingException {
		HierarchicalStreamWriter streamWriter;
		if (node instanceof Document) {
			streamWriter = new DomWriter((Document) node);
		}
		else if (node instanceof Element) {
			streamWriter = new DomWriter((Element) node, node.getOwnerDocument(), new XmlFriendlyReplacer());
		}
		else {
			throw new IllegalArgumentException("DOMResult contains neither Document nor Element");
		}
		marshal(graph, streamWriter);
	}

	@Override
	protected void marshalXmlEventWriter(Object graph, XMLEventWriter eventWriter) throws XmlMappingException {
		ContentHandler contentHandler = StaxUtils.createContentHandler(eventWriter);
		marshalSaxHandlers(graph, contentHandler, null);
	}

	@Override
	protected void marshalXmlStreamWriter(Object graph, XMLStreamWriter streamWriter) throws XmlMappingException {
		try {
			marshal(graph, new StaxWriter(new QNameMap(), streamWriter));
		}
		catch (XMLStreamException ex) {
			throw convertXStreamException(ex, true);
		}
	}

	@Override
	protected void marshalOutputStream(Object graph, OutputStream outputStream) throws XmlMappingException, IOException {
		if (this.streamDriver != null) {
			marshal(graph, this.streamDriver.createWriter(outputStream));
		}
		else {
			marshalWriter(graph, new OutputStreamWriter(outputStream, this.encoding));
		}
	}

	@Override
	protected void marshalSaxHandlers(Object graph, ContentHandler contentHandler, LexicalHandler lexicalHandler)
			throws XmlMappingException {

		SaxWriter saxWriter = new SaxWriter();
		saxWriter.setContentHandler(contentHandler);
		marshal(graph, saxWriter);
	}

	@Override
	protected void marshalWriter(Object graph, Writer writer) throws XmlMappingException, IOException {
		if (this.streamDriver != null) {
			marshal(graph, this.streamDriver.createWriter(writer));
		}
		else {
			marshal(graph, new CompactWriter(writer));
		}
	}

	/**
	 * Marshals the given graph to the given XStream HierarchicalStreamWriter.
	 * Converts exceptions using {@link #convertXStreamException}.
	 */
	private void marshal(Object graph, HierarchicalStreamWriter streamWriter) {
		try {
			getXStream().marshal(graph, streamWriter);
		}
		catch (Exception ex) {
			throw convertXStreamException(ex, true);
		}
		finally {
			try {
				streamWriter.flush();
			}
			catch (Exception ex) {
				logger.debug("Could not flush HierarchicalStreamWriter", ex);
			}
		}
	}


	// Unmarshalling

	@Override
	protected Object unmarshalDomNode(Node node) throws XmlMappingException {
		HierarchicalStreamReader streamReader;
		if (node instanceof Document) {
			streamReader = new DomReader((Document) node);
		}
		else if (node instanceof Element) {
			streamReader = new DomReader((Element) node);
		}
		else {
			throw new IllegalArgumentException("DOMSource contains neither Document nor Element");
		}
        return unmarshal(streamReader);
	}

	@Override
	protected Object unmarshalXmlEventReader(XMLEventReader eventReader) throws XmlMappingException {
		try {
			XMLStreamReader streamReader = StaxUtils.createEventStreamReader(eventReader);
			return unmarshalXmlStreamReader(streamReader);
		}
		catch (XMLStreamException ex) {
			throw convertXStreamException(ex, false);
		}
	}

	@Override
	protected Object unmarshalXmlStreamReader(XMLStreamReader streamReader) throws XmlMappingException {
        return unmarshal(new StaxReader(new QNameMap(), streamReader));
	}

	@Override
	protected Object unmarshalInputStream(InputStream inputStream) throws XmlMappingException, IOException {
        if (this.streamDriver != null) {
            return unmarshal(this.streamDriver.createReader(inputStream));
        }
        else {
		    return unmarshalReader(new InputStreamReader(inputStream, this.encoding));
        }
	}

	@Override
	protected Object unmarshalReader(Reader reader) throws XmlMappingException, IOException {
        if (this.streamDriver != null) {
            return unmarshal(this.streamDriver.createReader(reader));
        }
        else {
            return unmarshal(new XppReader(reader));
        }
	}

	@Override
	protected Object unmarshalSaxReader(XMLReader xmlReader, InputSource inputSource)
			throws XmlMappingException, IOException {

		throw new UnsupportedOperationException(
				"XStreamMarshaller does not support unmarshalling using SAX XMLReaders");
	}

    /**
     * Unmarshals the given graph to the given XStream HierarchicalStreamWriter.
     * Converts exceptions using {@link #convertXStreamException}.
     */
    private Object unmarshal(HierarchicalStreamReader streamReader) {
        try {
            return getXStream().unmarshal(streamReader);
        }
        catch (Exception ex) {
            throw convertXStreamException(ex, false);
        }
    }


    /**
     * Convert the given XStream exception to an appropriate exception from the
     * {@code org.springframework.oxm} hierarchy.
     * <p>A boolean flag is used to indicate whether this exception occurs during marshalling or
     * unmarshalling, since XStream itself does not make this distinction in its exception hierarchy.
     * @param ex XStream exception that occured
     * @param marshalling indicates whether the exception occurs during marshalling ({@code true}),
     * or unmarshalling ({@code false})
     * @return the corresponding {@code XmlMappingException}
     */
	protected XmlMappingException convertXStreamException(Exception ex, boolean marshalling) {
		if (ex instanceof StreamException || ex instanceof CannotResolveClassException ||
				ex instanceof ConversionException) {
			if (marshalling) {
				return new MarshallingFailureException("XStream marshalling exception",  ex);
			}
			else {
				return new UnmarshallingFailureException("XStream unmarshalling exception", ex);
			}
		}
		else {
			// fallback
			return new UncategorizedMappingException("Unknown XStream exception", ex);
		}
	}

}
