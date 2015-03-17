package com.redfire.nutrieduc.restclient;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
* Factory of {@link CloseableHttpClient} 
* @author thiagolenz
* @since 25/08/2014
*/
public class HttpClientFactory {
	static Map<String, SSLConnectionSocketFactory> loadedHosts = new HashMap<String, SSLConnectionSocketFactory>();

	public CloseableHttpClient createCloseableHttpClient (String url) throws Exception {
		if (url.contains("https"))
			return createCloseableHttpClientHTTPS(url);
		return HttpClients.createDefault();
	}

	private CloseableHttpClient createCloseableHttpClientHTTPS (String url) throws Exception {
		String host = defineHost(url);
		SSLConnectionSocketFactory sslsf  = loadedHosts.get(host);
		if (sslsf == null) 
			sslsf = loadCertificate(host);
		
		CloseableHttpClient httpClient = HttpClients.custom()
				.setSSLSocketFactory(sslsf)
				.build();
		return httpClient;
	}

	private SSLConnectionSocketFactory loadCertificate(String host) throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException, KeyManagementException {
		KeyStore trustStore = createTrustStore(host);
		SSLConnectionSocketFactory sslsf = createSSLFactory(trustStore);
		loadedHosts.put(host, sslsf);
		return sslsf;
	}

	private SSLConnectionSocketFactory createSSLFactory(KeyStore trustStore)
			throws NoSuchAlgorithmException, KeyManagementException,
			KeyStoreException {
		
		SSLContext sslcontext = SSLContexts.custom()
				.loadTrustMaterial(trustStore, new TrustSelfSignedStrategy())
				.build();
		
		return new SSLConnectionSocketFactory(
				sslcontext,
				new String[] { "TLSv1" },
				null,
				SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
	}

	private KeyStore createTrustStore(String host) throws KeyStoreException,
			IOException, NoSuchAlgorithmException, CertificateException {
		KeyStore trustStore  = KeyStore.getInstance(KeyStore.getDefaultType());
		InputStream is = RestClient.class.getClassLoader().getResourceAsStream("keystore/"+ host +".keystore");
		Properties prop = new Properties();
		prop.load(RestClient.class.getClassLoader().getResourceAsStream("keystore/keyStorePassword.properties"));
		try {
			trustStore.load(is, prop.get(host).toString().toCharArray());
		} finally {
			is.close();
		}
		return trustStore;
	}

	private String defineHost (String url) {
		String host = url.substring(url.indexOf("//")+2, url.length());
		host = host.substring(0, host.indexOf(":"));
		return host;
	}
}
