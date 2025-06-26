package file;


import cn.hutool.core.io.FileUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;

import java.io.File;

public class FileCryptoUtil {
    static AES aes = null;

    static {
        byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();
        aes = SecureUtil.aes(key);
    }

    // 加密方法
    public static void encryptFile(File file) {
        byte[] fileBytes = FileUtil.readBytes(file);
        byte[] encrypted = aes.encrypt(fileBytes);
        FileUtil.writeBytes(encrypted, file);
    }
    public static byte[] encryptFile(byte[] fileBytes) {
        return aes.encrypt(fileBytes);
    }
//    public static byte[] encryptFile(MultipartFile multipartFile) {
//        return aes.encrypt(fileBytes);
//    }

    // 解密方法
    public static void decryptFile(File file) {
        byte[] fileBytes = FileUtil.readBytes(file);
        byte[] decrypted = aes.decrypt(fileBytes);
        FileUtil.writeBytes(decrypted, file);
    }
    public static byte[] decryptFile(byte[] fileBytes) {
        return aes.decrypt(fileBytes);
    }

}
