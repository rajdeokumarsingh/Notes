package testcase3_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import util.Util;

public class LoginCase {
	private boolean showlog = false;
	private String host;

	public LoginCase(String host) {
		this.host = host;
	}

	public void test() {
		Util.Showlog(showlog, "LoginCase");
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(host
				+ "rest/mdm/v1/user/login_mdm");
		NameValuePair[] data = { new NameValuePair("username", "admin"),
				new NameValuePair("password", "admin") };
		postMethod.setRequestBody(data);
		postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		int statusCode;

		try {
			statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				Util.Showlog(showlog, "LoginCase1");
				System.err.println("Method failed: "
						+ postMethod.getStatusLine());
				Util.addErrorinfo(
						"" + new Exception().getStackTrace()[0].getFileName(),
						"" + postMethod.getStatusLine());

			} else {
				Util.Showlog(showlog, "LoginCase2");
				InputStream is = postMethod.getResponseBodyAsStream();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is));
				String response = reader.readLine();
				if (response.contains("\"result\":0")) {
					int start = response.indexOf("\"token\":\"")
							+ "\"token\":\"".length();
					int end = response.indexOf("\"", start);
					Util.token = response.substring(start, end);
					//Util.log("Util.tokenUtil.token  "+Util.token);
					Util.log(LoginCase.class.getName() + "  " + "登录成功");
				} else {
					Util.log(LoginCase.class.getName() + "  " + "登录失败");
					Util.Showlog(showlog, "LoginCase  error" + response);
					Util.addErrorinfo(
							""
									+ new Exception().getStackTrace()[0]
											.getFileName(), "" + response);
				}
			}
			postMethod.releaseConnection();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
