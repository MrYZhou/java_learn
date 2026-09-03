package common.sjcl;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;
import java.util.Base64;

/**
 * SJCL GCM 加密解密工具类
 */
public class SJCLGCMUtil {

    private static final String ALGORITHM = "AES/GCM/NoPadding";
    private static final int TAG_LENGTH_BIT = 128; // 认证标签长度 128 位
    private static String securityKey = "EY8WePvjM5GGwQzn";
    /**
     * 解密前端 SJCL 加密的数据
     *
     * @param hexCiphertext 前端加密后的 Hex 字符串
     * @return 解密后的明文
     */
    public static String decrypt(String hexCiphertext) {
        try {
            // 1. 将 Hex 字符串转换回 JSON 字符串
            String jsonCiphertext = hexToUtf8(hexCiphertext);
            JSONObject jsonObject = JSONObject.parseObject(jsonCiphertext);

            String ivBase64 = jsonObject.getString("iv");
            String saltBase64 = jsonObject.getString("salt");
            String ctBase64 = jsonObject.getString("ct");
            int iter = jsonObject.getIntValue("iter"); // 默认值 1000
            int ks = jsonObject.getIntValue("ks");      // 默认值 256
            // int ts = jsonObject.getIntValue("ts", 128);   // 认证标签长度

            // 3. Base64 解码
            byte[] iv = Base64.getDecoder().decode(ivBase64);
            byte[] salt = Base64.getDecoder().decode(saltBase64);
            byte[] ciphertext = Base64.getDecoder().decode(ctBase64);

            // 4. 使用 PBKDF2 派生密钥（与 SJCL 保持一致）
            SecretKey secretKey = deriveKey(salt, iter, ks);

            // 5. 使用 AES-GCM 解密
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(TAG_LENGTH_BIT, iv);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmParameterSpec);

            byte[] decryptedBytes = cipher.doFinal(ciphertext);
            return new String(decryptedBytes, StandardCharsets.UTF_8);

        } catch (Exception e) {
            System.err.println("decrypt error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 使用 PBKDF2 派生密钥（与 SJCL 算法保持一致）
     */
    private static SecretKey deriveKey(byte[] salt, int iterations, int keySize)
            throws Exception {
        // SJCL 使用 PBKDF2-HMAC-SHA256
        KeySpec keySpec = new PBEKeySpec(
                securityKey.toCharArray(),
                salt,
                iterations,
                keySize
        );

        // 使用 Bouncy Castle 或 JDK 的 PBKDF2WithHmacSHA256
        javax.crypto.SecretKeyFactory factory;
        try {
            factory = javax.crypto.SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        } catch (Exception e) {
            // 如果 JDK 不支持，可以回退到其他实现
            System.out.println("PBKDF2WithHmacSHA256 unable，use PBKDF2WithHmacSHA1");
            factory = javax.crypto.SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        }

        byte[] keyBytes = factory.generateSecret(keySpec).getEncoded();
        return new SecretKeySpec(keyBytes, "AES");
    }

    /**
     * 将 Hex 字符串转换为 UTF-8 字符串
     */
    private static String hexToUtf8(String hexString) throws Exception {
        byte[] bytes = Hex.decodeHex(hexString);
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
