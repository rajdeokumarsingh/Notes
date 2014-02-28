package com.java.examples.net.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

public class H02HttpsConnection {
	public static void testHttpGet() throws IOException {
		
		HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(
                "http://192.168.10.43:8080/PushServer/rest/push/assignServer?device_id=test0001");
//		HttpGet httpGet = new HttpGet("https://mail.pekall.com/zimbra/");

		HttpResponse response1 = httpclient.execute(httpGet);

		// The underlying HTTP connection is still held by the response object
		// to allow the response content to be streamed directly from the
		// network socket.
		// In order to ensure correct deallocation of system resources
		// the user MUST either fully consume the response content or abort
		// request
		// execution by calling HttpGet#releaseConnection().

		try {
			System.out.println("status line");
			System.out.println(response1.getStatusLine());

			Header[] headers = response1.getAllHeaders();
			System.out.println("headers:");
			for (Header h : headers) {
				System.out.println(h.toString());
			}

			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					response1.getEntity().getContent()));

			String line = "";
			System.out.println("entity:");
			while ((line = rd.readLine()) != null) {
				System.out.println(line);
			}

			HttpEntity entity1 = response1.getEntity();
			// do something useful with the response body
			// and ensure it is fully consumed
            // FIXME:
			// EntityUtils.consume(entity1);
		} finally {
            // FIXME:
			// httpGet.releaseConnection();
		}
	}

	private static HttpClient createHttpsClient() {
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("https", SSLSocketFactory
				.getSocketFactory(), 443));

		HttpParams params = new BasicHttpParams();

		SingleClientConnManager mgr = new SingleClientConnManager(params,
				schemeRegistry);

		HttpClient httpclient = new DefaultHttpClient(mgr, params);
		return httpclient;
	}

	/**
	 * @param args
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public static void main(String[] args) throws ClientProtocolException,
			IOException {
		testHttpGet();
	}

}
