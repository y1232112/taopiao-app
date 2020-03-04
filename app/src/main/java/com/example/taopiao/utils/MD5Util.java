package com.example.taopiao.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
    private static final String TAG="MD5Util";
    /**
     * MD5加码生成32位码
     *
     */
    public static String stringMD5(String string) throws NoSuchAlgorithmException {
        MessageDigest md5=null;
        md5=MessageDigest.getInstance("MD5");
        char[] chars=string.toCharArray();
        byte[] bytes=new byte[chars.length];
        for(int i=0;i<chars.length;i++){
            bytes[i]= (byte) chars[i];
        }
        byte[] md5bytes=md5.digest(bytes);
        StringBuffer henChar=new StringBuffer();
        for(int i=0;i<md5bytes.length;i++){
           int value=((int)md5bytes[i])&0xff;
           if(value<16) henChar.append("0");
           henChar.append(Integer.toHexString(value));
        }
        return henChar.toString();
    }
    /**
     * 一次加密两次解密
     */
    public static String convertMD5(String string){
        char[] chars=string.toCharArray();
        for(int i=0;i<chars.length;i++){
            chars[i]=(char)(chars[i]^'t');
        }
        String value=new String(chars);
        return value;
    }
}
