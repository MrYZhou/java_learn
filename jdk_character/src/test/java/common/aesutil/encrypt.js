const forge = require("node-forge")

class AesGcmUtil {
    constructor() {
        this.GCM_IV_LENGTH = 12 // GCM推荐12字节IV
        this.GCM_TAG_LENGTH = 128 // 认证标签长度（比特）
        this.key = forge.util.encodeUtf8("EY8WePvjM5GGwQzn")
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
    encrypt(plaintext) {
        const iv = this.generateIV()
        const cipher = forge.cipher.createCipher("AES-GCM", this.key)
        cipher.start({
            iv: iv,
            tagLength: this.GCM_TAG_LENGTH,
        })
        cipher.update(forge.util.createBuffer(plaintext, "utf8"))

        cipher.finish()

        const encrypted = cipher.output.getBytes()
        const tag = cipher.mode.tag.getBytes()

        // 将iv+密钥+认证标签组成密文
        return this.hexEncode(iv + encrypted + tag)
    }

    decrypt(ciphertext) {
        // 先把hex格式解密
        let fullEncryptedData = this.hexDecode(ciphertext)

        // 分离IV (前12字节)
        const iv = fullEncryptedData.slice(0, this.GCM_IV_LENGTH)
        const ciphertextWithTag = fullEncryptedData.slice(this.GCM_IV_LENGTH)

        // 执行解密
        const decryptedBytes = this.decryptMe(iv, ciphertextWithTag, this.key)

        // 转换为字符串
        const decryptedText = forge.util.decodeUtf8(decryptedBytes)

        return decryptedText
    }

    // 解密方法
    decryptMe(iv, ciphertextWithTag, key) {
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
            throw new Error(
                "Decryption failed: Authentication tag verification failed"
            )
        }

        return decipher.output.getBytes()
    }
    hexEncode(binaryData) {
        return forge.util.bytesToHex(binaryData)
    }
    hexDecode(encodedData) {
        return forge.util.hexToBytes(encodedData)
    }
    static testEncrypt() {
        const util = new AesGcmUtil()
        try {
            const plaintext = "Hello, Comate!"
            // 2. 加密
            const ciphertextWithTag = util.encrypt(plaintext)
            // 3. 解密
            const decryptedBytes = util.decrypt(ciphertextWithTag)
            console.log(decryptedBytes)
        } catch (error) {
            console.error("decrypt error:", error.message)
        }
    }
}

AesGcmUtil.testEncrypt()
