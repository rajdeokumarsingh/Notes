package openssl.java.client;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * This example demonstrates how to create secure connections with a custom SSL
 * context.
 */
public class PekallSslClient {

    public final static void main(String[] args) throws Exception {
        ignoreServerCert();
    }

    private static void ignoreServerCert() throws KeyManagementException, NoSuchAlgorithmException,
            KeyStoreException, IOException, CertificateException, UnrecoverableKeyException {
        String keystorePassword = "pekallsu";

        final HttpParams httpParams = new BasicHttpParams();

        // load the keystore containing the client certificate - keystore type is probably jks or pkcs12
        final KeyStore keystore = KeyStore.getInstance("pkcs12");
//        FileInputStream keystoreInput = new FileInputStream(new File("/home/jiangrui/certificate/client/zwm2.p12"));
        FileInputStream keystoreInput = new FileInputStream(new File("/home/jiangrui/certificate/pekall.ca/client.p12"));
        keystore.load(keystoreInput, keystorePassword.toCharArray());

        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
        keyManagerFactory.init(keystore, keystorePassword.toCharArray());

        SSLContext sslContext = SSLContext.getInstance("SSL");

        // set up a TrustManager that trusts everything
        sslContext.init(keyManagerFactory.getKeyManagers(),
                new TrustManager[] { new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                System.out.println("getAcceptedIssuers =============");
                return null;
            }

            public void checkClientTrusted(X509Certificate[] certs,
                                           String authType) {
                System.out.println("checkClientTrusted =============");
            }

            public void checkServerTrusted(X509Certificate[] certs,
                                           String authType) {
                System.out.println("checkServerTrusted =============");
            }
        } }, new SecureRandom());

        SSLSocketFactory sf = new SSLSocketFactory(sslContext);
        Scheme httpsScheme = new Scheme("https", 443, sf);
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(httpsScheme);

        // apache HttpClient version >4.2 should use BasicClientConnectionManager
        ClientConnectionManager cm = new SingleClientConnManager(schemeRegistry);
        DefaultHttpClient httpClient = new DefaultHttpClient(cm);

        try {
            HttpGet httpget = new HttpGet("https://192.168.10.55/ssl_test/zjipst_cert");
            System.out.println("executing request" + httpget.getRequestLine());

            CloseableHttpResponse response = httpClient.execute(httpget);
            try {
                HttpEntity entity = response.getEntity();

                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                if (entity != null) {
                    System.out.println("Response content length: " + entity.getContentLength());
                }

                System.out.println(getResponseEntity(response));
                // EntityUtils.consume(entity);
            } finally {
                response.close();
            }
        } finally {
            httpClient.close();
        }
    }

    public static String getResponseEntity(HttpResponse response) throws IOException {
        InputStream in = response.getEntity().getContent();

        StringBuilder sb = new StringBuilder();
        int count = 0;
        do {
            byte[] bytes = new byte[40960];
            count = in.read(bytes);
            if (count <= 0) {
                break;
            }
            sb.append(new String(bytes, 0, count, "utf-8"));
        } while (count > 0);
        return sb.toString();
    }
}
