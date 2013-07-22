package com.java.examples.net.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class H01QuickStart {

	/**
	 * @param args
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public static void main(String[] args) throws ClientProtocolException,
			IOException {
//		 testHttpGet();
//		testHttpPost();
		
		testHttpPost2();
	}

	private static void testHttpPost2() {
		HttpClient client = new DefaultHttpClient();
	    HttpPost post = new HttpPost("http://vogellac2dm.appspot.com/register");
	    try {
	      List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
	      nameValuePairs.add(new BasicNameValuePair("registrationid",
	          "123456789"));
	      post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	 
	      HttpResponse response = client.execute(post);
	      BufferedReader rd = new BufferedReader(new 
	    		  InputStreamReader(response.getEntity().getContent()));
	      String line = "";
	      while ((line = rd.readLine()) != null) {
	        System.out.println(line);
	      }

	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	}

	private static void testHttpPost()
			throws UnsupportedEncodingException, IOException,
			ClientProtocolException {
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(
				"https://www.google.com/accounts/ClientLogin");

		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
			nameValuePairs.add(new BasicNameValuePair("Email",
					"jiangrui.tr2008@gmail.com"));
			nameValuePairs.add(new BasicNameValuePair("Password", "hwrhwsgg0)"));
			nameValuePairs.add(new BasicNameValuePair("accountType", "GOOGLE"));
			nameValuePairs.add(new BasicNameValuePair("source",
					"Google-cURL-Example"));
			nameValuePairs.add(new BasicNameValuePair("service", "ac2dm"));

			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = client.execute(post);
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));

			String line = "";
			while ((line = rd.readLine()) != null) {
				System.out.println(line);
				if (line.startsWith("Auth=")) {
					String key = line.substring(5);
					// Do something with the key
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void testHttpGet() throws IOException,
			ClientProtocolException {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet("http://www.baidu.com");

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

}
