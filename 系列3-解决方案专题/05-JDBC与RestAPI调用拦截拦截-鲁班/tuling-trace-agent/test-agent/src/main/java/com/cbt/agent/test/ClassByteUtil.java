package com.cbt.agent.test;/**
 * Created by Administrator on 2018/6/19.
 */

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.util.TraceClassVisitor;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * @author Tommy
 *         Created by Tommy on 2018/6/19
 **/
public class ClassByteUtil {

    public static void main(String[] args) throws IOException {
        String path = "/" + LubanServiceImpl.class.getName().replaceAll("[.]", "/") + ".class";
        InputStream stream = ClassByteUtil.class.getResourceAsStream(path);
        ClassReader reader = new ClassReader(stream);
        reader.accept(new TraceClassVisitor(new PrintWriter(System.out)), ClassReader.SKIP_FRAMES);
    }
}
