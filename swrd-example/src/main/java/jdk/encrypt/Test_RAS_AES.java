package jdk.encrypt;

import encryption.rsa.RSA_AES_Util;

public class Test_RAS_AES {
    static String publicKeyStr="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCgXUno3SkhXdtfZHxeJqyweeIcrapKKAMic1" +
            "6eaUOb2c/P+mcrqM2/DnCsF/t7T4Er3UrwqBuEkuuCxy/1OlCkDY01cP8K1HgnhX2JngMIdfKz" +
            "sqFK92v0GPpeRdAjXSxJU3RqfzmZSsYQ1g0qLIO1fQJi5+K1VIx4C9dnWnWlpwIDAQAB";

    static String privateKeyStr="MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAKBdSejdKSFd219kfF4mrLB54h" +
            "ytqkooAyJzXp5pQ5vZz8/6Zyuozb8OcKwX+3tPgSvdSvCoG4SS64LHL/U6UKQNjTVw/wrUeCeF" +
            "fYmeAwh18rOyoUr3a/QY+l5F0CNdLElTdGp/OZlKxhDWDSosg7V9AmLn4rVUjHgL12dadaWnAg" +
            "MBAAECgYAQ4aEtLVLuG0ZDpX1eH+4f6cS+xh1eDxY9Yy27uVMPrf8tYqvHsc5u5WNBColDstvj" +
            "NTl1Wg0DiL/YFYXb59ajM7jOPaFnzZ31RmyYQvWxfVBkCtKUy/V8V9NYH2qdgcFko7KBzSlDOd" +
            "VYOo5s6p6i2zQ/jnwpqoAUNdC2ZOaHoQJBANdrOsAzjgPf1Hgp/ej6jbi6IlhAu/4eDMQ9hpq7" +
            "gC0fX+4i+DZ6NtKBojIFLLxpqS0EHN6xwmIQ5WOsMwTZA/cCQQC+kwIPBbEr9fxuN5QvHcMY4R" +
            "gBe7KdC9efjn95Z3/1peN0kElxHhEgyQy4MWu3vAnxjXDK+TV+A4sETtG4BZ/RAkAoBNyqPimg" +
            "4KEpHav07y8K5VFiEcya3dxDTKbH8hNSzqRUqhxwim3K15hZMIrqCbsLCNuQJ3fUHg1vYdssiG" +
            "BrAkAXHiIrzFb3rDlY7WkOZh/ajsOoumaqYltfwZt5ELMXC26SH0apcCpHvdvLJEOa4DaclLnQ" +
            "BXcVuDxO43jRR+1hAkAmPUQb/4GUmMH2XDPlaDJRW8VcljcLZoHSoxCvmXau8yJ9iyO6FhZ0xd" +
            "DBGBSS+0Pl8RsKxhe4qI0ZIuk8iJv9";

    public static void main(String[] args) throws Exception {
        String message="i am a good man";

        System.out.println("加密前的内容: "+message);

        String[] messages = RSA_AES_Util.encrypt(message, publicKeyStr);
        System.out.println("加密后的内容："+messages[0]);

        String decrypt = RSA_AES_Util.decrypt(privateKeyStr, messages[1], messages[0]);
        System.out.println("解密后的内容: "+decrypt);

    }
}
