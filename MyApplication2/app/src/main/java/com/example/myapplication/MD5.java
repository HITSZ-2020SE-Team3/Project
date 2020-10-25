package com.example.myapplication;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
    // 加密算法: 把任意长度的字符串变成固定长度（通常是128位）的16进制字符串
    public static String md5(String text) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("md5");
            // 数组 byte[] result -> digest.digest( );  文本 text.getBytes();
            byte[] result = digest.digest(text.getBytes());
            //创建StringBuilder对象 然后建议StringBuffer，安全性高
            StringBuffer sb = new StringBuffer();
            // result数组，digest.digest ( ); -> text.getBytes();
            for (byte b : result){  // 循环数组byte[] result
                int number = b & 0xff;
                // number值 转换 字符串 Integer.toHexString( );
                String hex = Integer.toHexString(number);
                if (hex.length() == 1){
                    sb.append("0"+hex);
                }else {
                    sb.append(hex);
                }
            }
            //sb StringBuffer sb = new StringBuffer();对象实例化
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            //发送异常return空字符串
            return "";
        }
    }
}
