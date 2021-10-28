package com.xiangxue.ch03.deencrpt.service;

import java.io.File;

/**
 * 动脑学院-Mark老师
 * 创建日期：2017/08/31
 * 创建时间: 11:20
 */
public interface IDemoEncryptUtil {
    void encrypt(File src, File des) throws Exception;
    byte[] decrypt(File src) throws Exception;
}
