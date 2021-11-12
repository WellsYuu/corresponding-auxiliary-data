package com.xiangxue.ch03.deencrpt.Encrpt;


import java.io.File;

import com.xiangxue.ch03.deencrpt.service.DemoXorEncrpt;
import com.xiangxue.ch03.deencrpt.service.IDemoEncryptUtil;

public class DemoEncrptClass {
    public static void main(String[] args) throws Exception {
        File src = new File("D:\\Test\\encrpt\\src\\Target.class");
        File dest = new File("D:\\Test\\encrpt\\dest\\Target.class");
        IDemoEncryptUtil demoEncryptUtil = new DemoXorEncrpt();
        demoEncryptUtil.encrypt(src,dest);
        System.out.println("加密完成！");
    }
}
