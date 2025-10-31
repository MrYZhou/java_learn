package common;

import com.google.common.base.Charsets;
import com.google.common.io.BaseEncoding;
import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.Test;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;


public class AesUtil {
    private static final String ALGORITHM = "AES";
    private static final Key KEY = new SecretKeySpec(Md5Util.getStringMd5("EY8WePvjM5GGwQzn").getBytes(), ALGORITHM);

    // 加密
    public static String encrypt(String data) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, KEY);
        byte[] encrypted = cipher.doFinal(data.getBytes(Charsets.UTF_8));
        return BaseEncoding.base64().encode(encrypted);
    }

    // 解密
    public static String decrypt(String encryptedData) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, KEY);
        byte[] decoded = BaseEncoding.base64().decode(encryptedData);
        byte[] decrypted = cipher.doFinal(decoded);
        return new String(decrypted, Charsets.UTF_8);
    }
    @Test
    public void test() throws Exception {
       String encrypt = "3zoM+Sqa2sXLKGC0LK3O1hyGYPpzNcmm4idkVhV1qMAoMLDr6elMJdGAY5z5aj1rhCgcgEff/MCOaaeJCDx6pb18qEYCrr8YinYNB4ZTV0v5I6xpWHpNro6L37lDLDDJHP9ns0bjfbHgC2ChvGeFqJ/I0+sjqVmjxYAJ/h9RPYY=";
//        String encrypt = encrypt(key);
//        System.out.println(encrypt);
//        Base64.encodeBase64String

        System.out.println(decrypt(encrypt));
    }
}
