package encryption.rsa;

import javax.crypto.SecretKey;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * xinghonglin
 */
public class RSA_AES_Util {

    public static String[] encrypt(String message,String publicKeyStr) throws Exception {
        //将Base64编码后的公钥转换成PublicKey对象
        PublicKey publicKey = RSAUtil.string2PublicKey(publicKeyStr);

        //生成AES秘钥，并Base64编码
        String aesKeyStr = AESUtil.genKeyAES();

        //用公钥加密AES秘钥
        byte[] publicEncrypt = RSAUtil.publicEncrypt(aesKeyStr.getBytes(), publicKey);

        //公钥加密AES秘钥后的内容Base64编码
        String publicEncryptStr = RSAUtil.byte2Base64(publicEncrypt);

        //将Base64编码后的AES秘钥转换成SecretKey对象
        SecretKey aesKey = AESUtil.loadKeyAES(aesKeyStr);

        //用AES秘钥加密实际的内容
        byte[] encryptAES = AESUtil.encryptAES(message.getBytes(), aesKey);

        //AES秘钥加密后的内容Base64编码
        String encryptAESStr = AESUtil.byte2Base64(encryptAES);
        String[] result = new String[2];
        result[0]=encryptAESStr;
        result[1]=publicEncryptStr;
        return result;
    }


    public static String decrypt(String privateKeyStr,String publicEncryptStr,String encryptAESStr) throws Exception {
        //将Base64编码后的私钥转换成PrivateKey对象
        PrivateKey privateKey = RSAUtil.string2PrivateKey(privateKeyStr);
        //公钥加密AES秘钥后的内容(Base64编码)，进行Base64解码
        byte[] publicEncrypt2 = RSAUtil.base642Byte(publicEncryptStr);
        //用私钥解密,得到aesKey
        byte[] aesKeyStrBytes = RSAUtil.privateDecrypt(publicEncrypt2, privateKey);
        //解密后的aesKey
        String aesKeyStr2 = new String(aesKeyStrBytes);
        System.out.println("解密后的aesKey(Base64编码): " + aesKeyStr2);

        //将Base64编码后的AES秘钥转换成SecretKey对象
        SecretKey aesKey2 = AESUtil.loadKeyAES(aesKeyStr2);
        //AES秘钥加密后的内容(Base64编码)，进行Base64解码
        byte[] encryptAES2 = AESUtil.base642Byte(encryptAESStr);
        //用AES秘钥解密实际的内容
        byte[] decryptAES = AESUtil.decryptAES(encryptAES2, aesKey2);
        //解密后的实际内容
        System.out.println("解密后的实际内容: " + new String(decryptAES));

        return new String(decryptAES);
    }



}
