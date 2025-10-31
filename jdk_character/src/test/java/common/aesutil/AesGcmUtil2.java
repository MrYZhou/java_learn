package common.aesutil;

import org.junit.jupiter.api.Test;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

public class AesGcmUtil2 {
    private static final int GCM_IV_LENGTH = 12; // GCM推荐12字节IV
    private static final int GCM_TAG_LENGTH = 128; // 认证标签长度（比特）

    // 生成随机密钥（AES-256）
    public static SecretKey generateKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256); // 可选128/192/256
        return keyGen.generateKey();
    }

    // 生成随机IV
    public static byte[] generateIV() {
        byte[] iv = new byte[GCM_IV_LENGTH];
        new SecureRandom().nextBytes(iv);
        return iv;
    }

    // 加密方法
    public static byte[] encrypt(byte[] iv, String plaintext, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        cipher.init(Cipher.ENCRYPT_MODE, key, spec);
        return cipher.doFinal(plaintext.getBytes("UTF-8"));
    }

    // 解密方法
    public static String decrypt(byte[] iv, byte[] ciphertext, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        cipher.init(Cipher.DECRYPT_MODE, key, spec);
        byte[] decrypted = cipher.doFinal(ciphertext);
        return new String(decrypted, "UTF-8");
    }

    // 示例用法
    public static void main(String[] args) throws Exception {
        // 1. 生成密钥和IV
        SecretKey key = generateKey();
        byte[] iv = generateIV();
        String plaintext = "Hello, 百度Comate!";

        // 2. 加密
        byte[] ciphertext = encrypt(iv, plaintext, key);
        System.out.println("密钥 (Base64): " + Base64.getEncoder().encodeToString(key.getEncoded()));
        System.out.println("IV (Base64): " + Base64.getEncoder().encodeToString(iv));
        System.out.println("密文 (Base64): " + Base64.getEncoder().encodeToString(ciphertext));

        // 3. 解密
        String decryptedText = decrypt(iv, ciphertext, key);
        System.out.println("解密结果: " + decryptedText);
    }


    public static SecretKey getSecretKeyToModel(String base64Key) {
        byte[] decodedKey = Base64.getDecoder().decode(base64Key);
        return new SecretKeySpec(decodedKey, "AES"); // AES-256需32字节
    }



    @Test
    public void test() throws Exception {
        byte[] iv = Base64.getDecoder().decode("jJnW1gKz6GjoBl88");
        byte[] ciphertext = Base64.getDecoder().decode("82Ly0bvmHdxUg6wrwjat5jmAdDINmhuHJM6099aIa8hB3zUR");
        SecretKey key = getSecretKeyToModel("uFUFzkTlU7cSkuylyzDaC31znBKnTVGb67w+TPjQLzA=");
        String decryptedText = decrypt(iv, ciphertext, key);
        System.out.println("解密结果: " + decryptedText);

    }

}
