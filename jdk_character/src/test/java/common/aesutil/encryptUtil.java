package common.aesutil;

import org.junit.jupiter.api.Test;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

public class encryptUtil {
    private static final int GCM_IV_LENGTH = 12; // GCM推荐12字节IV
    private static final int GCM_TAG_LENGTH = 128; // 认证标签长度（比特）
    private static SecretKey gcmSecretKey =  new SecretKeySpec("EY8WePvjM5GGwQzn".getBytes(), "AES"); // 密钥
    // 生成随机IV
    public static byte[] generateIV() {
        byte[] iv = new byte[GCM_IV_LENGTH];
        new SecureRandom().nextBytes(iv);
        return iv;
    }
    // 加密方法
    /**
     *
     * @param ivBytes
     * @param contentBytes encrypt content
     * @return encrypt result byte
     * @throws Exception
     */
    public static byte[] encrypt(byte[] ivBytes, byte[] contentBytes) throws Exception {
        return encrypt(ivBytes, contentBytes,gcmSecretKey);
    }
    public static byte[] encrypt(byte[] iv, byte[] contentBytes, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        cipher.init(Cipher.ENCRYPT_MODE, key, spec);
        byte[] bytes = cipher.doFinal(contentBytes);

        // 合并 IV 和密文 (GCM认证标签已包含在cipherText中)
        byte[] encryptedData = new byte[iv.length + bytes.length];
        System.arraycopy(iv, 0, encryptedData, 0, iv.length);
        System.arraycopy(bytes, 0, encryptedData, iv.length, bytes.length);
        return encryptedData;
    }
    // 解密方法
    public static byte[] decrypt(byte[] ciphertext) throws Exception {
        return decrypt(ciphertext,gcmSecretKey);
    }
    public static byte[] decrypt(byte[] ciphertext, SecretKey key) throws Exception {
        // 分离IV (前12字节)
        byte[] iv = new byte[GCM_IV_LENGTH];
        System.arraycopy(ciphertext, 0, iv, 0, iv.length);

        // 分离密文 (剩余部分)
        byte[] cipherTextWithTag = new byte[ciphertext.length - GCM_IV_LENGTH];
        System.arraycopy(ciphertext, GCM_IV_LENGTH, cipherTextWithTag, 0, cipherTextWithTag.length);

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        cipher.init(Cipher.DECRYPT_MODE, key, spec);
        return cipher.doFinal(cipherTextWithTag);
    }
}
