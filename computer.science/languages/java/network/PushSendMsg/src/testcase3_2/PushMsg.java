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

public class PushMsg {
	private boolean showlog = false;
	private String host;

	public PushMsg(String host) {
		this.host = host;
	}

	public void test() {
		Util.Showlog(showlog, PushMsg.class.getName());
		HttpClient httpClient = new HttpClient();

		PostMethod postMethod = new PostMethod(host
				+ "rest/mdm/v1/device/push_msg");
		NameValuePair[] data = {
				new NameValuePair("device_ids", "" + Util.mDevice_ids),
				new NameValuePair("msg", "msgms1gmsgmsg"),
				new NameValuePair("methods", "50803"),

		};
		postMethod.setRequestBody(data);
		postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		postMethod.setRequestHeader("Authorization", Util.token);
		int statusCode;

		try {
			statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				Util.Showlog(showlog, "" + PushMsg.class.getName());
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
				Util.Showlog(showlog, PushMsg.class.getName() + "  " + ""
						+ response);
				if (response.contains(Util.call)) {
					Util.log(PushMsg.class.getName() + "  " + "Push消息成功");
				} else {
					Util.log(PushMsg.class.getName() + "  " + "Push消息失败");
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
