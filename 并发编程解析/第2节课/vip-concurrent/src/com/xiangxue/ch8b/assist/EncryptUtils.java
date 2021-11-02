package com.xiangxue.ch8b.assist;

import org.apache.commons.lang.StringUtils;

import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：摘要的工具类
 */
public class EncryptUtils {
    /**
     *
     * @param strSrc 需要被加密的字符串
     * @param encName 加密方式，有 MD5、SHA-1和SHA-256 这三种加密方式
     * @return 返回加密后的字符串
     */
    private static String EncryptStr(String strSrc, String encName) {
        MessageDigest md = null;
        String strDes = null;

        byte[] bt = strSrc.getBytes();
        try {
            if (encName == null || encName.equals("")) {
                encName = "MD5";
            }
            md = MessageDigest.getInstance(encName);
            md.update(bt);
            strDes = bytes2Hex(md.digest()); // to HexString
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Invalid algorithm.");
            return null;
        }
        return strDes;
    }

    /**
     *
     * @param str 需要被加密的字符串
     * @return 对字符串str进行MD5加密后，将加密字符串返回
     *
     */
    public static String EncryptByMD5(String str) {
        return EncryptStr(str, "MD5");
    }

    /**
     * 该方法主要用于验证学生端的md5密码
     *
     * @param s
     *       the string want to encode
     * @return
     *       the encoded String
     */
    public static String to_MD5(String s) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            byte[] strTemp = s.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     *
     * @param str 需要被加密的字符串
     * @return 对字符串str进行SHA-1加密后，将加密字符串返回
     *
     */
    public static String EncryptBySHA1(String str) {
        return EncryptStr(str, "SHA-1");
    }

    /**
     *
     * @param str 需要被加密的字符串
     * @return 对字符串str进行SHA-256加密后，将加密字符串返回
     *
     */
    public static String EncryptBySHA256(String str) {
        return EncryptStr(str, "SHA-256");
    }

    /**
     *
     * @param bts
     * @return
     */
    private static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }

    /**
     *
     * @param str
     * @param key
     * @return
     */
    public static String union(String str,String key) {
        int strLen = str.length();
        int keyLen = key.length();
        Character[] s = new Character[strLen+keyLen];

        boolean flag= true;
        int strIdx=0;
        int keyIdx=0;
        for(int i=0;i<s.length;i++){
            if(flag){
                if(strIdx<strLen){
                    s[i] = str.charAt(strIdx);
                    strIdx++;
                }
                if(keyIdx<keyLen){
                    flag=false;
                }

            }else{
                if(keyIdx<keyLen){
                    s[i]=key.charAt(keyIdx);
                    keyIdx++;
                }
                if(strIdx<strLen){
                    flag=true;
                }

            }
        }
        return StringUtils.join(s);
    }

    /**
     *  加密str
     *
     * @param str 要加密的字符串
     * @param key 加密的key
     * @return
     */
    public static String encrypt(String str,String key){

        if( str==null || str.length()==0 || StringUtils.isBlank(key)){
            return encrypt(str);
        }

        return encrypt(union(str, key));

    }




    /**
     * 先将str进行一次MD5加密，加密后再取加密后的字符串的第1、3、5个字符追加到加密串，再拿这个加密串进行加密
     * @param str
     * @return
     * @throws NoSuchAlgorithmException
     * @throws DigestException
     */
    public static String encrypt(String str)   {
        String encryptStr = EncryptByMD5(str);
        if(encryptStr!=null ){
            encryptStr = encryptStr + encryptStr.charAt(0)+encryptStr.charAt(2)+encryptStr.charAt(4);
            encryptStr = EncryptByMD5(encryptStr);
        }
        return encryptStr;
    }
}
