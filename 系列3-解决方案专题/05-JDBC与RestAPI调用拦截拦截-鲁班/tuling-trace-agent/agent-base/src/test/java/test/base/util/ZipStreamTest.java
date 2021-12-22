package test.base.util;

import java.io.*;
import java.util.zip.*;

public class ZipStreamTest {
    public static void main(String[] args) {
        //zip();
        uzip();

    }
    public static void zip(){
        try {
            final int BUFFER = 2048;
            BufferedInputStream origin = null;
            FileOutputStream dest = new
                    FileOutputStream("/Users/tommy/git/cbt-agent/bootstrap/target/agent1.0.3.zip");
            CheckedOutputStream checksum = new
                    CheckedOutputStream(dest, new Adler32());
            ZipOutputStream out = new
                    ZipOutputStream(new
                    BufferedOutputStream(checksum));
            //out.setMethod(ZipOutputStream.DEFLATED);
            byte data[] = new byte[BUFFER];
            // get a list of files from current directory
            File f = new File("/Users/tommy/git/cbt-agent/bootstrap/target/test/");
            File files[] = f.listFiles();
            for (int i=0; i <f.listFiles().length; i++) {
                System.out.println("Adding: "+files[i]);


               if(files[i].isFile()) {
                   ZipEntry entry = new ZipEntry(files[i].getName());
                   out.putNextEntry(entry);
                   FileInputStream fi = new FileInputStream(files[i]);
                   origin = new
                           BufferedInputStream(fi, BUFFER);
                   int count;
                   while ((count = origin.read(data, 0,
                           BUFFER)) != -1) {
                       out.write(data, 0, count);
                   }
                   origin.close();
               }else{
                   ZipEntry entry = new ZipEntry(files[i].getName()+"/");
                   out.putNextEntry(entry);
               }
            }
            out.close();
            System.out.println("checksum: "+checksum.getChecksum().getValue());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public static void uzip() {
        try {
            final int BUFFER = 2048;
            BufferedOutputStream dest = null;
            FileInputStream fis = new
                    FileInputStream("/Users/tommy/git/cbt-agent/agent-base/target/cbt-agent-base.jar");
            CheckedInputStream checksum = new
                    CheckedInputStream(fis, new Adler32());
            ZipInputStream zis = new
                    ZipInputStream(new
                    BufferedInputStream(checksum));
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                System.out.println("Extracting: " + entry);
                int count;
                byte data[] = new byte[BUFFER];
                // write the files to the disk
                File file = new File("/Users/tommy/git/cbt-agent/bootstrap/target/test/", entry.getName());
                if (file.exists()) {
                    continue;
                }
                if (entry.isDirectory())
                    file.mkdirs();
                else {
                    if (!file.getParentFile().exists())
                        file.getParentFile().mkdirs();
                    file.createNewFile();
                    FileOutputStream fos = new
                            FileOutputStream(file);
                    dest = new BufferedOutputStream(fos,
                            BUFFER);
                    while ((count = zis.read(data, 0,
                            BUFFER)) != -1) {
                        dest.write(data, 0, count);
                    }
                    dest.flush();
                    dest.close();
                }


            }
            zis.close();
            System.out.println("Checksum: "
                    + checksum.getChecksum().getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}