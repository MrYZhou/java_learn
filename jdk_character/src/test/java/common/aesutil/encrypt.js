const forge = require("node-forge")

class AesGcmUtil {
    constructor() {
        this.GCM_IV_LENGTH = 12 // GCM推荐12字节IV
        this.GCM_TAG_LENGTH = 128 // 认证标签长度（比特）
    }

    // 生成随机密钥（AES-256）
    generateKey() {
        return forge.random.getBytesSync(32) // 32字节 = 256比特
    }

    // 生成随机IV
    generateIV() {
        return forge.random.getBytesSync(this.GCM_IV_LENGTH)
    }

    // 加密方法
    encrypt(iv, plaintext, key) {
        const cipher = forge.cipher.createCipher("AES-GCM", key)
        cipher.start({
            iv: iv,
            tagLength: this.GCM_TAG_LENGTH,
        })
        cipher.update(forge.util.createBuffer(plaintext, "utf8"))

        cipher.finish()

        const encrypted = cipher.output.getBytes()
        const tag = cipher.mode.tag.getBytes()

        // 将认证标签附加到密文后面
        console.log(encrypted, 2, tag)
        return encrypted + tag
    }

    // 解密方法
    decrypt(iv, ciphertextWithTag, key) {
        // 分离密文和认证标签
        const tagLength = this.GCM_TAG_LENGTH / 8 // 标签字节长度
        const ciphertext = ciphertextWithTag.slice(0, -tagLength)
        const tag = ciphertextWithTag.slice(-tagLength)

        const decipher = forge.cipher.createDecipher("AES-GCM", key)
        decipher.start({
            iv: iv,
            tag: forge.util.createBuffer(tag),
            tagLength: this.GCM_TAG_LENGTH,
        })
        decipher.update(forge.util.createBuffer(ciphertext))
        const result = decipher.finish()

        if (!result) {
            throw new Error("解密失败：认证标签验证失败")
        }

        return decipher.output.getBytes()
    }

    // Base64编码工具方法
    bytesToBase64(bytes) {
        return forge.util.encode64(bytes)
    }

    // Base64解码工具方法
    base64ToBytes(base64) {
        return forge.util.decode64(base64)
    }

    // 示例用法
    static main() {
        const util = new AesGcmUtil()

        try {
            // 1. 生成密钥和IV
            let key = util.generateKey()
            const iv = util.generateIV()
            const plaintext = "Hello, 百度Comate!"

            console.log("原始明文:", plaintext)
            console.log("密钥长度:", key.length, "字节")
            console.log("IV长度:", iv.length, "字节")

            // 2. 加密
            const ciphertextWithTag = util.encrypt(iv, plaintext, key)
            console.log("密钥 (Base64):", util.bytesToBase64(key))
            console.log("IV (Base64):", util.bytesToBase64(iv))
            console.log("密文 (Base64):", util.bytesToBase64(ciphertextWithTag))

            // 3. 解密
            const decryptedBytes = util.decrypt(iv, ciphertextWithTag, key)
            const decryptedText = forge.util.decodeUtf8(decryptedBytes)
            console.log("解密结果:", decryptedText)

            // 验证加解密一致性
            console.log("加解密验证:", plaintext === decryptedText ? "成功" : "失败")
        } catch (error) {
            console.error("加解密过程出错:", error.message)
        }
    }
}

// 如果直接运行此文件，则执行示例
if (require.main === module) {
    AesGcmUtil.main()
}

module.exports = AesGcmUtil
