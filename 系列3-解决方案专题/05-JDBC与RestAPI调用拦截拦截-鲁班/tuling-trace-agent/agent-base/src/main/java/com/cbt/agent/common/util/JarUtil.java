package com.cbt.agent.common.util;

import sun.net.www.protocol.jar.*;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.*;
import java.net.JarURLConnection;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


/**
 * Created by tommy on 16/11/4.
 */
public final class JarUtil {

    private static final Method FIND_METHOD;

    private static final Method ADDURL_METHOD;

    static {
        try {
            FIND_METHOD = ClassLoader.class.getDeclaredMethod("findLoadedClass", String.class);
            ADDURL_METHOD = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
        } catch (NoSuchMethodException | SecurityException e) {
            throw new RuntimeException("Agent Source initialize fail", e);
        }
    }

    public static Set<URL> findSources(ClassLoader loader, final String parentDir, final String suffix) throws Exception {
        URL rootUrl = loader.getResource(parentDir);
        Set<URL> result = new HashSet<>();
        if (rootUrl == null) {
            return result;
        } else if ("jar".equals(rootUrl.getProtocol())) {
            JarURLConnection jarURLConnection = (JarURLConnection) rootUrl.openConnection();
            JarFile jarFile = jarURLConnection.getJarFile();
            Enumeration<JarEntry> jarEntries = jarFile.entries();
            while (jarEntries.hasMoreElements()) {
                JarEntry jarEntry = jarEntries.nextElement();
                String jarEntryName = jarEntry.getName();
                if (jarEntryName.startsWith(parentDir) && jarEntryName.endsWith(suffix)) {
                    result.add(new URL(rootUrl.toString()
                            + jarEntryName.substring(parentDir.length())));
                }
            }
        } else if ("file".equals(rootUrl.getProtocol())) {
            File root = new File(rootUrl.getPath());
            File[] list = root.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    return pathname.getName().endsWith(suffix);
                }
            });
            for (File file : list) {
                result.add(file.toURI().toURL());
            }
        } else {
            new Exception(String.format("findSources not suport by %s url Protocol", rootUrl.getProtocol())).printStackTrace();
        }
        return result;
    }

    public static JarFile retrieve(URL url) throws IOException, PrivilegedActionException {
        final InputStream inputStream = url.openConnection().getInputStream();
        JarFile result = (JarFile) AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public JarFile run() throws IOException {
                Path tempPath = Files.createTempFile("jar_cache", (String) null, new FileAttribute[0]);
                try {
                    Files.copy(inputStream, tempPath, new CopyOption[]{StandardCopyOption.REPLACE_EXISTING});
                    URLJarFile jarFile = new URLJarFile(tempPath.toFile(), null);
                    tempPath.toFile().deleteOnExit();
                    return jarFile;
                } catch (Throwable throwable) {
                    try {
                        Files.delete(tempPath);
                    } catch (IOException e) {
                        throwable.addSuppressed(e);
                    }
                    throw throwable;
                }
            }
        });
        return result;
    }

    public static File toTempFile(URL url) throws IOException, PrivilegedActionException {
        final InputStream inputStream = url.openConnection().getInputStream();
        File result = (File) AccessController.doPrivileged(new PrivilegedExceptionAction<File>() {
            public File run() throws IOException {
                Path tempPath = Files.createTempFile("jar_cache", (String) null, new FileAttribute[0]);
                try {
                    Files.copy(inputStream, tempPath, new CopyOption[]{StandardCopyOption.REPLACE_EXISTING});
                    tempPath.toFile().deleteOnExit();
                    return tempPath.toFile();
                } catch (Throwable throwable) {
                    try {
                        Files.delete(tempPath);
                    } catch (IOException e) {
                        throwable.addSuppressed(e);
                    }
                    throw throwable;
                }
            }
        });
        return result;
    }

    public static void addUrl(final URL url, final URLClassLoader loader) throws PrivilegedActionException, IOException {
        AccessController.doPrivileged(new PrivilegedExceptionAction<Object>() {
                @Override
            public Object run() throws Exception {
                ADDURL_METHOD.setAccessible(true);
                if (url.getProtocol().equals("file")) {
                    File f=new File(url.toURI());
                    if(!f.exists()){
                        throw new IOException("not found "+url);
                    }
                    ADDURL_METHOD.invoke(loader, url);
                } else {
                    File tempFile = toTempFile(url);
                    ADDURL_METHOD.invoke(loader, tempFile.toURI().toURL());
                }
                return null;
            }
        });

    }


    public static void main(String[] args) throws Exception {
        Set<URL> set = JarUtil.findSources(JarUtil.class.getClassLoader(), "Cbt_collects_lib", ".jar");
        System.out.println(Arrays.toString(set.toArray()));
        JarFile jarFile = JarUtil.retrieve(new File("/Users/tommy/git/cbt-agent/out/cbt-agent-base.jar").toURI().toURL());
        System.out.println(jarFile.getName());

    }
}
