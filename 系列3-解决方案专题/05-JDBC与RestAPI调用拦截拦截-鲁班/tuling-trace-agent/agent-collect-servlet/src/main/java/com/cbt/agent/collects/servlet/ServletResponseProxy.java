package com.cbt.agent.collects.servlet;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.*;

/**
 * Created by tommy on 16/9/17.
 */
public class ServletResponseProxy extends HttpServletResponseWrapper {
    private ServletOutputStreamWrapper outputStream;
    private PrintWriter printWriter;
    StringWriter copyWriter;
    ByteArrayOutputStream copyOutput ;
    /**
     * Constructs a response adaptor wrapping the given response.
     *
     * @param response
     * @throws IllegalArgumentException if the response is null
     */
    public ServletResponseProxy(HttpServletResponse response) {
        super(response);
    }


    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (outputStream == null) {
            outputStream = new ServletOutputStreamWrapper(super.getOutputStream());
            copyOutput = new ByteArrayOutputStream();
        }
        return outputStream;
//        return super.getOutputStream();
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if (printWriter == null) {
            printWriter = new PrintWriter(new ServletPrintWriterWrapper(super.getWriter()));
            copyWriter = new StringWriter();
        }
        return printWriter;
    }

    public StringWriter getCopyWriter() {
        return copyWriter;
    }

    public ByteArrayOutputStream getCopyOutput() {
        return copyOutput;
    }

    public void clearCopyOut(){
        copyWriter=null;
        copyOutput=null;
    }

    class ServletOutputStreamWrapper extends ServletOutputStream {
        private final ServletOutputStream target;

        final int maxSize = 1024 * 30;

        ServletOutputStreamWrapper(ServletOutputStream target) {
            this.target = target;
        }

        @Override
        public boolean isReady() {
            return target.isReady();
        }

        @Override
        public void setWriteListener(WriteListener writeListener) {
            target.setWriteListener(writeListener);
        }

        @Override
        public void write(int b) throws IOException {
            target.write(b);
            if (copyOutput.size() < maxSize)
                copyOutput.write(b);
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            target.write(b, off, len);
            if (copyOutput.size() < maxSize)
                copyOutput.write(b, off, len);
        }

        @Override
        public void print(String s) throws IOException {
            target.print(s);
        }

        @Override
        public void print(boolean b) throws IOException {
            target.print(b);
        }

        @Override
        public void print(char c) throws IOException {
            target.print(c);
        }

        @Override
        public void print(int i) throws IOException {
            target.print(i);
        }

        @Override
        public void print(long l) throws IOException {
            target.print(l);
        }

        @Override
        public void print(float f) throws IOException {
            target.print(f);
        }

        @Override
        public void print(double d) throws IOException {
            target.print(d);
        }

        @Override
        public void println() throws IOException {
            target.println();
        }

        @Override
        public void println(String s) throws IOException {
            target.println(s);
        }

        @Override
        public void println(boolean b) throws IOException {
            target.println(b);
        }

        @Override
        public void println(char c) throws IOException {
            target.println(c);
        }

        @Override
        public void println(int i) throws IOException {
            target.println(i);
        }

        @Override
        public void println(long l) throws IOException {
            target.println(l);
        }

        @Override
        public void println(float f) throws IOException {
            target.println(f);
        }

        @Override
        public void println(double d) throws IOException {
            target.println(d);
        }
    }

    class ServletPrintWriterWrapper extends BufferedWriter {
        final int maxSize = 1024 * 10;

        public ServletPrintWriterWrapper(Writer out) {
            super(out);
        }

        @Override
        public void write(int c) throws IOException {
            super.write(c);
            if (copyWriter.getBuffer().length() < maxSize)
                copyWriter.write(c);
        }

        @Override
        public void write(char[] cbuf, int off, int len) throws IOException {
            super.write(cbuf, off, len);
            if (copyWriter.getBuffer().length() < maxSize)
                copyWriter.write(cbuf, off, len);
        }

        @Override
        public void write(String s, int off, int len) throws IOException {
            super.write(s, off, len);
            if (copyWriter.getBuffer().length() < maxSize)
                copyWriter.write(s, off, len);
        }
    }
}
