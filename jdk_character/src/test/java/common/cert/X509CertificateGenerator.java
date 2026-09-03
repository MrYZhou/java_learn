package common.cert;

import lombok.Data;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;

/**
 * 纯证书生成工具：生成 RSA 密钥对 + 自签名 X.509 证书，输出 PEM 格式字符串。
 * 无任何 SAML 依赖。
 */
public class X509CertificateGenerator {

    private static final String PROVIDER = BouncyCastleProvider.PROVIDER_NAME;
    private static final String KEY_ALG = "RSA";
    private static final String SIGNATURE_ALG = "SHA256WithRSA";

    static {
        if (Security.getProvider(PROVIDER) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    /**
     * 生成自签名证书（默认有效期 10 年，密钥长度 2048）
     *
     * @param commonName CN，例如 "My Company IdP"
     * @return 包含私钥 PEM、证书 PEM 的对象
     */
    public static KeyCertData generate(String commonName) throws Exception {
        return generate(commonName, 2048, 10);
    }

    /**
     * 生成自签名证书
     *
     * @param commonName    CN 名称
     * @param keySize       RSA 密钥长度（如 2048, 4096）
     * @param validityYears 有效期（年）
     * @return 证书数据
     */
    public static KeyCertData generate(String commonName, int keySize, int validityYears) throws Exception {
        String subjectDN = "CN=" + commonName;
        // 1. 生成密钥对
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance(KEY_ALG, PROVIDER);
        keyGen.initialize(keySize, new SecureRandom());
        KeyPair keyPair = keyGen.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

        // 2. 构建证书
        X500Name issuer = new X500Name(subjectDN);
        X500Name subject = new X500Name(subjectDN);
        BigInteger serial = new BigInteger(64, new SecureRandom());
        LocalDate startDate = LocalDate.now().minusDays(1);
        LocalDate endDate = startDate.plusYears(validityYears);
        Date notBefore = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date notAfter = Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        JcaX509v3CertificateBuilder certBuilder = new JcaX509v3CertificateBuilder(
                issuer, serial, notBefore, notAfter, subject, publicKey);
        // 标记为 CA（如果需要）
        certBuilder.addExtension(Extension.basicConstraints, true, new BasicConstraints(true));

        ContentSigner signer = new JcaContentSignerBuilder(SIGNATURE_ALG)
                .setProvider(PROVIDER)
                .build(privateKey);

        X509Certificate certificate = new JcaX509CertificateConverter()
                .setProvider(PROVIDER)
                .getCertificate(certBuilder.build(signer));

        // 3. 转为 PEM 字符串
        String privateKeyPem = toPem(privateKey, "RSA PRIVATE KEY");
        String publicKeyPem = toPem(publicKey, "RSA PUBLIC KEY");
        String certificatePem = toPem(certificate, "CERTIFICATE");

        return new KeyCertData(privateKey, publicKey, certificate,
                privateKeyPem, publicKeyPem, certificatePem);
    }

    // 将 Key 对象转为 PEM 字符串
    private static String toPem(Key key, String type) throws Exception {
        StringWriter sw = new StringWriter();
        try (PemWriter pw = new PemWriter(sw)) {
            pw.writeObject(new PemObject(type, key.getEncoded()));
        }
        return sw.toString();
    }

    // 将 X509Certificate 转为 PEM 字符串
    private static String toPem(X509Certificate cert, String type) throws Exception {
        StringWriter sw = new StringWriter();
        try (PemWriter pw = new PemWriter(sw)) {
            pw.writeObject(new PemObject(type, cert.getEncoded()));
        }
        return sw.toString();
    }

    /**
     * 从 PEM 字符串加载 X509Certificate 对象
     *
     * @param pem 包含头尾的证书 PEM，例如 "-----BEGIN CERTIFICATE-----\n...\n-----END CERTIFICATE-----"
     * @return X509Certificate 对象
     */
    public static X509Certificate loadCertificateFromPem(String pem) throws Exception {
        String base64 = pem
                .replace("-----BEGIN CERTIFICATE-----", "")
                .replace("-----END CERTIFICATE-----", "")
                .replaceAll("\\s", "");
        byte[] decoded = Base64.getDecoder().decode(base64);
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        return (X509Certificate) cf.generateCertificate(new ByteArrayInputStream(decoded));
    }

    /**
     * 从 PEM 字符串加载 PrivateKey 对象（PKCS#8 格式）
     */
    public static PrivateKey loadPrivateKeyFromPem(String pem) throws Exception {
        String base64 = pem
                .replace("-----BEGIN RSA PRIVATE KEY-----", "")
                .replace("-----END RSA PRIVATE KEY-----", "")
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");
        byte[] encoded = Base64.getDecoder().decode(base64);
        KeyFactory kf = KeyFactory.getInstance(KEY_ALG);
        // 先尝试 PKCS8
        try {
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(encoded);
            return kf.generatePrivate(spec);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * 数据载体（只保留 PEM 字符串，也保留 Java 对象供需要时使用）
     */
    @Data
    public static class KeyCertData {
        private final PrivateKey privateKey;
        private final PublicKey publicKey;
        private final X509Certificate certificate;
        private final String privateKeyPem;
        private final String publicKeyPem;
        private final String certificatePem;
    }
}
