package file;

import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Index {
    @Test
    @DisplayName("测试文件夹目录生成")
    public void test() {
        String path = "D:\\Users\\JNPF\\Desktop\\project\\java_learn\\jdk_character\\src\\test\\java\\file\\001.docx";
        File file = new File(path);

        // 示例用法
        FileCryptoUtil.encryptFile(file);
        FileCryptoUtil.decryptFile(file);

    }

    @Test
    @DisplayName("测试加密")
    public void test2() throws NoSuchAlgorithmException {
        String customKeyStr = "fsafasgfdgfddsadsadsadas";
        // UTF-8编码后截取前32字节（强制对齐256bits）
        byte[] keyBytes = Arrays.copyOf(customKeyStr.getBytes(StandardCharsets.UTF_8), 32);
        System.out.println(keyBytes);
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, keyBytes);
    }
}
