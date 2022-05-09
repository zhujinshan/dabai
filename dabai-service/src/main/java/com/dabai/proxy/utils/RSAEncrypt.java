package com.dabai.proxy.utils;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.HashMap;

/**
 * rsa加密类
 */
public class RSAEncrypt {

    /**
     * 生成时间戳秒
     * @param date
     * @return
     */
    public static int getSecondTimestamp(Date date){
        if (null == date) {
            return 0;
        }
        String timestamp = String.valueOf(date.getTime());
        int length = timestamp.length();
        if (length > 3) {
            return Integer.valueOf(timestamp.substring(0,length-3));
        } else {
            return 0;
        }
    }
    /**
     * 字符串转PublicKey对象
      * @param key
     * @return
     * @throws Exception
     */
   public static PublicKey getPublicKey(String key) throws Exception {
       byte[] publicBytes = Base64.decodeBase64(key);
       X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
       KeyFactory keyFactory = KeyFactory.getInstance("RSA");
       PublicKey pubKey = keyFactory.generatePublic(keySpec);
       return pubKey;
   }

    /**
     * 字符串转PrivateKey对象
     * @param key
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String key) throws Exception {
        byte[] publicBytes = Base64.decodeBase64(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(publicBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    /**
     * PublicKey对象转字符串
     * @param publicKey
     * @return
     */
    public static String getPublicKeyString(PublicKey publicKey){
        String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
        return publicKeyString;
    }

    /**
     * PrivateKey对象转字符串
     * @param privateKey
     * @return
     */
    public static String getPrivateKeyString(PrivateKey privateKey){
        String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));
        return privateKeyString;
    }

    /**
     * 随机生成密钥对
     * @throws NoSuchAlgorithmException
     */
    public static HashMap<Integer,String> genKeyPair() throws NoSuchAlgorithmException {
        HashMap<Integer, String> keyMap = new HashMap<Integer, String>();
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        // 初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize(1024,new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥



        String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
        // 得到私钥字符串
        String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));

        System.out.println("publicKey:"+publicKeyString);
        System.out.println("privateKey:"+privateKeyString);
        // 将公钥和私钥保存到Map
        keyMap.put(0,publicKeyString);  //0表示公钥
        keyMap.put(1,privateKeyString);  //1表示私钥
        return keyMap;
    }


    /**
     * RSA公钥加密
     *
     * @param str
     *            加密字符串
     * @param publicKey
     *            公钥
     * @return 密文
     * @throws Exception
     *             加密过程中的异常信息
     */
    public static String encrypt( String str, String publicKey ) throws Exception{
        //base64编码的公钥
        byte[] decoded = Base64.decodeBase64(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        return encrypt(str,pubKey);
    }

    /**
     * RSA公钥加密
     * @param str
     * @param pubKey
     * @return
     * @throws Exception
     */
    public static String encrypt(String str,RSAPublicKey pubKey) throws Exception{
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        String outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
        return outStr;
    }


    /**
     * RSA私钥解密
     *
     * @param str
     *            加密字符串
     * @param privateKey
     *            私钥
     * @return 铭文
     * @throws Exception
     *             解密过程中的异常信息
     */
    public static String decrypt(String str, String privateKey) throws Exception{
        //base64编码的私钥
        byte[] decoded = Base64.decodeBase64(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        return decrypt(str,priKey);
    }

    /**
     * 私钥解密
     * @param str
     * @param priKey
     * @return
     * @throws Exception
     */
    public static String decrypt(String str,RSAPrivateKey priKey) throws Exception{
        //64位解码加密后的字符串
        byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));

        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        String outStr = new String(cipher.doFinal(inputByte));
        return outStr;
    }

    /**
     * 私钥签名
     * @param plainText
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String sign(String plainText,String privateKey) throws Exception{
        //base64编码的私钥
        byte[] decoded = Base64.decodeBase64(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        return sign(plainText,priKey);
    }

    /**
     * 私钥签名
     * @param plainText
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String sign(String plainText, PrivateKey privateKey) throws Exception {
        //Signature privateSignature = Signature.getInstance("SHA256withRSA");
        Signature privateSignature = Signature.getInstance("SHA1withRSA");
        privateSignature.initSign(privateKey);
        privateSignature.update(plainText.getBytes("UTF-8"));
        byte[] signature = privateSignature.sign();
        return Base64.encodeBase64String(signature);
    }

    /**
     * 公钥校验
     * @param plainText
     * @param signature
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static boolean verify(String plainText,String signature,String publicKey) throws Exception{
        //base64编码的私钥
        byte[] decoded = Base64.decodeBase64(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        return verify(plainText,signature,pubKey);
    }

    /**
     * 公钥校验
     * @param plainText
     * @param signature
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static boolean verify(String plainText, String signature, PublicKey publicKey) throws Exception {
        Signature publicSignature = Signature.getInstance("SHA1withRSA");
        publicSignature.initVerify(publicKey);
        publicSignature.update(plainText.getBytes("UTF-8"));

        byte[] signatureBytes = Base64.decodeBase64(signature);
        return publicSignature.verify(signatureBytes);
    }



}
