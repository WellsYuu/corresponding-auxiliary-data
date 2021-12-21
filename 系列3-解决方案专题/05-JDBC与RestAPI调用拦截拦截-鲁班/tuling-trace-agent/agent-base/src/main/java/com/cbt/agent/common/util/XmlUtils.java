package com.cbt.agent.common.util;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class XmlUtils {
    public static Document parseToDoc(String fileName) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(false);
            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.setErrorHandler(new ErrorHandler() {
                @Override
                public void warning(SAXParseException exception) throws SAXException {
                    exception.printStackTrace();
                }

                @Override
                public void fatalError(SAXParseException exception) throws SAXException {
                    exception.printStackTrace();
                }

                @Override
                public void error(SAXParseException exception) throws SAXException {
                    exception.printStackTrace();
                }
            });

            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            Document document = builder.parse(loader.getResourceAsStream(fileName));
            return document;
        } catch (Exception e) {
            throw new RuntimeException("parse xml failed!", e);
        }
    }

    public static boolean isEmpty(Object str) {
        return (str == null || "".equals(str));
    }
}
