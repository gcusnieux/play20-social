package client;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class ClientUtils {

	public static void nnPrsoxy() {
		System.setProperty("http.proxySet", "true");
		System.setProperty("http.proxyHost", "192.168.128.3");
		System.setProperty("http.proxyPort", "8080");
		System.setProperty("https.proxySet", "true");
		System.setProperty("https.proxyHost", "192.168.128.3");
		System.setProperty("https.proxyPort", "8070");
		System.setProperty("javax.net.ssl.trustStore", "conf/certificate.jks");
		System.setProperty("javax.net.ssl.trustStorePassword", "secret");
		System.setProperty("javax.net.ssl.trustStoreType", "jks");

		HostnameVerifier hostnameVerifier = new HostnameVerifier() {

			public boolean verify(String urlHostName, SSLSession session) {
				return true;
			}
		};
		HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);

		TrustManager[] trustAllCerts = new TrustManager[]{ new X509TrustManager() {

			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}
		} };
		try {
			SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

			Authenticator.setDefault(new Authenticator() {

				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("gcusnieux", "qzmpngnu".toCharArray());
				}
			});
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
