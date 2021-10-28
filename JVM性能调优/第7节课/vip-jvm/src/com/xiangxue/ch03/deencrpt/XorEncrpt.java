package com.xiangxue.ch03.deencrpt;

import java.io.*;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：加密和解密的服务类
 */
public class XorEncrpt{

	//异或操作,可以进行加密和解密
    private void xor(InputStream in, OutputStream out)  throws Exception{
        int ch;
        while (-1 != (ch = in.read())){
            ch = ch^ 0xff;
            out.write(ch);
        }
    }

    //加密方法
    public void encrypt(File src, File des) throws Exception {
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(des);

        xor(in,out);

        in.close();
        out.close();
    }

    //解密方法
    //加密后的class文件
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
