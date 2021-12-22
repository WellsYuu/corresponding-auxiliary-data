package test.base.util;

import sun.net.www.protocol.jar.URLJarFile;

import java.io.IOException;
import java.net.URL;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;

/**
 * Created by tommy on 16/10/11.
 */
public class tempTest {
    public static void main(String[] args) throws IOException {
        Path path = Files.createTempFile("jar_cache", null, new FileAttribute[0]);

        URL url = new URL("http://localhost:8080/sockjs/cbt-agent-base.js");

        Files.copy(url.openStream(), path, new CopyOption[]{StandardCopyOption.REPLACE_EXISTING});
        path.toUri().toURL();

        URLJarFile var2 = new URLJarFile(path.toFile(), null);

        path.toFile().deleteOnExit();
        System.out.println(path.toString());
    }
}
