package com.xiangxue.ch03.deencrpt.service;

import java.io.*;

/**
 * 动脑学院-Mark老师
 * 创建日期：2017/08/31
 * 创建时间: 11:20
 */
public class DemoXorEncrpt implements IDemoEncryptUtil {

    private void xor(InputStream in, OutputStream out)  throws Exception{
        int ch;
        while (-1 != (ch = in.read())){
            ch = ch^ 0xff;
            out.write(ch);
        }
    }

    @Override
    public void encrypt(File src, File des) throws Exception {
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(des);

        xor(in,out);

        in.close();
        out.close();
    }

    @Override
    public byte[] decrypt(File src) throws Exception {
        InputStream in = new FileInputStream(src);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        xor(in,bos);
        byte[] data = bos.toByteArray();;
        return data;
    }


}
