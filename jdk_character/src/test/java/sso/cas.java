package sso;

import org.apereo.cas.client.ssl.AnyHostnameVerifier;
import org.apereo.cas.client.ssl.HttpURLConnectionFactory;
import org.apereo.cas.client.ssl.HttpsURLConnectionFactory;
import org.apereo.cas.client.validation.AbstractUrlBasedTicketValidator;
import org.apereo.cas.client.validation.Assertion;
import org.apereo.cas.client.validation.Cas10TicketValidator;
import org.apereo.cas.client.validation.Cas20ServiceTicketValidator;
import org.apereo.cas.client.validation.TicketValidationException;
import org.apereo.cas.client.validation.TicketValidator;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Properties;

public class cas {
    String serviceUrl = "http://127.0.0.1:33000/api/cas/";
    @Test
    void case1() throws TicketValidationException {
        String ticket = "ST-Z23ZgWciomFInivyFixX1UUreQNORzQK58GM71v97NIwhb7HX8uSyjZ0a7NL";
        HttpURLConnectionFactory urlConnectionFactory = new HttpsURLConnectionFactory(new AnyHostnameVerifier(), new Properties());
        TicketValidator tmpTicketValidator = new Cas10TicketValidator(serviceUrl);
        ((AbstractUrlBasedTicketValidator)tmpTicketValidator).setURLConnectionFactory(urlConnectionFactory);
        Cas20ServiceTicketValidator proxyTicketValidator = new Cas20ServiceTicketValidator(serviceUrl);
        proxyTicketValidator.setEncoding("UTF-8");
        proxyTicketValidator.setURLConnectionFactory(urlConnectionFactory);


        final Assertion assertion = proxyTicketValidator.validate(ticket, "http://127.0.0.1:30000/api/oauth/Login/cas");
        System.out.println(assertion);
    }


}
