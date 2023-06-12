/**
 * 
 */
package com.cisco.te.shieldeye.sdavc.client;


import javax.net.ssl.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.slf4j.LoggerFactory;

/**
 * Update HttpsURLConnection defaults parameters to trust every server
 *
 * @author yurpopov
 *
 */
public abstract class HttpsUriConnectionTrustAllServers {// NOSONAR

    static {
        try {
            setHttpsConnectionDefaults();
        } catch (Exception e) {
            LoggerFactory.getLogger( HttpsUriConnectionTrustAllServers.class)
            .error("Got exception in enableSSLSocket. Problem to enable trust all certificate", e);
        }
    }

    /**
     * set Https URI connection default parameters
     */
    private static void setHttpsConnectionDefaults() throws KeyManagementException, NoSuchAlgorithmException {
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });

        SSLContext context = SSLContext.getInstance("TLS");
        context.init(null, new X509TrustManager[] { new X509TrustManager() {
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }

            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                // always trust
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                // always trust
            }
        } }, new SecureRandom());

        HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
    }
}
