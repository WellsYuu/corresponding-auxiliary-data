package com.xiangxue.ch03.deencrpt;

import java.io.*;

public class XorEncrpt{

    private void xor(InputStream in, OutputStream out)  throws Exception{
        int ch;
        while (-1 != (ch = in.read())){
            ch = ch^ 0xff;
            out.write(ch);
        }
    }

    public void encrypt(File src, File des) throws Exception {
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(des);

        xor(in,out);

        in.close();
        out.close();
    }

    public byte[] decrypt(File src) throws Exception {
        InputStream in = new FileInputStream(src);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        xor(in,bos);
        byte[] data = bos.toByteArray();;
        return data;
    }
    
    public static void main(String[] args) throws Exception {
        File src = new File("D:\\XiangXue\\vipLesson\\JVM\\code\\src\\"
        		+ "vip-jvm\\bin\\com\\xiangxue\\ch03\\deencrpt\\DemoUserSrc.class");
        File dest = new File("D:\\XiangXue\\vipLesson\\JVM\\code\\src"
        		+ "\\vip-jvm\\bin\\com\\xiangxue\\ch03\\deencrpt\\DemoUser.class");
        XorEncrpt demoEncryptUtil = new XorEncrpt();
        demoEncryptUtil.encrypt(src,dest);
        System.out.println("加密完成！");
    }


}
