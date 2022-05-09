package com.dabai.proxy.utils;


import org.apache.http.util.TextUtils;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.HashMap;

/**
 * AES加密工具
 */
public class AesUtil {

    public static final String AES = "AES";
    public static final String AES_MODE = "AES/CBC/PKCS5Padding";

    private static byte[] encrypt(byte[] sSrc, String key, String iv, int ciperMode) {
        try {
            byte[] sKey = key.getBytes();
            byte[] sIv = iv.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(sKey, AES);
            Cipher cipher = Cipher.getInstance(AES_MODE);
            IvParameterSpec ivSpec = new IvParameterSpec(sIv);
            cipher.init(ciperMode, skeySpec,ivSpec);
            try {
                return cipher.doFinal(sSrc);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }




//    // 加密
    public static byte[] encrypt(byte[] sSrc, int ciperMode, HashMap<String,String>params) {
        try {
              String s = String.valueOf(System.currentTimeMillis());
              String md5 = digest(s);
              String mykey = md5.substring(0,16);
              String myIv = md5.substring(16,32);
              params.put("key",mykey);
              params.put("iv",myIv);
              return encrypt(sSrc,mykey,myIv,ciperMode);
        } catch (Exception ex) {
            return null;
        }
    }

    public static String decrypt(String serverText,String key,String iv){
        if (TextUtils.isEmpty(serverText)){
            return null;
        }

        byte[] decode = null;
        try {
            decode = Base64.decodeBase64(serverText);
            byte[] encrypt = encrypt(decode, key, iv, Cipher.DECRYPT_MODE);
            return new String(encrypt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*对密码进行加密
     *参数：密码
     *返回：密文
     */
    public static String digest(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] bytes = digest.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                int c = b & 0xff; //负数转换成正数
                String result = Integer.toHexString(c); //把十进制的数转换成十六进制的书
                if(result.length()<2){
                    sb.append(0); //让十六进制全部都是两位数
                }
                sb.append(result);
            }
            return sb.toString(); //返回加密后的密文
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

}
