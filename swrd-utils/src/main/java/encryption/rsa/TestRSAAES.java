package encryption.rsa;

import encryption.rsa.AESUtil;
import encryption.rsa.RSAUtil;

import javax.crypto.SecretKey;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public class TestRSAAES {
    //RSA与AES的结合。
    //客户端用公钥加密AES秘钥，AES秘钥加密实际内容；
    //服务端用私钥解密AES秘钥，AES秘钥解密实际内容
    public static void main(String[] args) throws Exception {
        //===============生成公钥和私钥，公钥传给客户端，私钥服务端保留==================
        //生成RSA公钥和私钥，并Base64编码，生成一次以后，就写死在配置文件或代码中，下次不再重新生成
        KeyPair keyPair = RSAUtil.getKeyPair();
        String publicKeyStr = RSAUtil.getPublicKey(keyPair);
        String privateKeyStr = RSAUtil.getPrivateKey(keyPair);
        System.out.println("RSA公钥Base64编码:" + publicKeyStr);
        System.out.println("RSA私钥Base64编码:" + privateKeyStr);

        //=================客户端=================
        //hello, i am infi, good night!  需要加密的实际内容
        String message = "hello, i am infi, good night!";

        //将Base64编码后的公钥转换成PublicKey对象
        PublicKey publicKey = RSAUtil.string2PublicKey(publicKeyStr);

        //生成AES秘钥，并Base64编码
        String aesKeyStr = AESUtil.genKeyAES();
        System.out.println("AES秘钥Base64编码:" + aesKeyStr);

        //用公钥加密AES秘钥
        byte[] publicEncrypt = RSAUtil.publicEncrypt(aesKeyStr.getBytes(), publicKey);

        //公钥加密AES秘钥后的内容Base64编码
        String publicEncryptStr = RSAUtil.byte2Base64(publicEncrypt);
        System.out.println("公钥加密AES秘钥并Base64编码的结果：" + publicEncryptStr);

        //将Base64编码后的AES秘钥转换成SecretKey对象
        SecretKey aesKey = AESUtil.loadKeyAES(aesKeyStr);

        //用AES秘钥加密实际的内容
        byte[] encryptAES = AESUtil.encryptAES(message.getBytes(), aesKey);

        //AES秘钥加密后的内容Base64编码
        String encryptAESStr = AESUtil.byte2Base64(encryptAES);
        System.out.println("AES秘钥加密实际的内容并Base64编码的结果：" + encryptAESStr);


        //##############	网络上传输的内容有Base64编码后的公钥加密AES秘钥的结果 和 Base64编码后的AES秘钥加密实际内容的结果   #################
        //##############	即publicEncryptStr和encryptAESStr	###################


        //===================服务端================
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
    }
}
