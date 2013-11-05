package testcase3_2;

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

public class PushCmd {
	private boolean showlog = false;
	private String host;

	public PushCmd(String host) {
		this.host = host;
	}

	public void test() {
		Util.Showlog(showlog, PushCmd.class.getName());
		HttpClient httpClient = new HttpClient();

		PostMethod postMethod = new PostMethod(host
				+ "rest/mdm/v1/device/push_cmd");
		NameValuePair[] data = {
				new NameValuePair("device_ids", "" + Util.mDevice_ids),
				new NameValuePair("cmd", "10105"),
				new NameValuePair("content", ""),

		};
		postMethod.setRequestBody(data);
		postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		postMethod.setRequestHeader("Authorization", Util.token);
		int statusCode;

		try {
			statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				Util.Showlog(showlog, "" + PushCmd.class.getName());
				Util.addErrorinfo(
						"" + new Exception().getStackTrace()[0].getFileName(),
						"" + postMethod.getStatusLine());

				System.err.println("Method failed: "
						+ postMethod.getStatusLine());
			} else {
				Util.Showlog(showlog, "AddUser2");
				InputStream is = postMethod.getResponseBodyAsStream();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is));
				String response = reader.readLine();
				Util.Showlog(showlog, PushCmd.class.getName() + "  " + ""
						+ response);
				if (response.contains(Util.call)) {
					Util.log(PushCmd.class.getName() + "  " + "Push命令成功");
				} else {
					Util.log(PushCmd.class.getName() + "  " + "Push命令失败");
					Util.Showlog(showlog, "AddLabel info" + response);
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
