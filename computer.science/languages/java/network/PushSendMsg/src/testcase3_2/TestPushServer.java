package testcase3_2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import util.Util;

public class TestPushServer {
	private String device_id;
	private String devUUID;
	public static String METHOD_TYPE_GET = "GET";
	public static String METHOD_TYPE_POST = "POST";

	public TestPushServer(String device_id ,String devUUID) {

		this.device_id = device_id;
		this.devUUID  = devUUID;
	}

	public void test() {
		String pushCmdUrl = "http://192.168.10.234:8080/PushServer/rest/push/sendMessage";
		String methodType = METHOD_TYPE_POST;
		Map<String, String> headerMap = null;
		String token = "YzAyMTBkYWNiNmE4ZDlhZjc5MjA3ZGY2MTMwY2EzNjA=";
		if (token != null && !token.equals("")) {
			headerMap = new HashMap<String, String>();
			headerMap.put("Authorization", token);
		}
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("device_id", "" + device_id);
		paraMap.put("admin_token", /* ConfigurationUtil.getAdmin_token() */
				"123456");
		paraMap.put("message", devUUID);

		String response = doRequest(pushCmdUrl, headerMap, paraMap, methodType);
		Util.log("" + response);// response;
	}

	public static String doRequest(String url, Map<String, String> headerMap,
			Map<String, String> paramsMap, String methodType) {
		String result = null;

		if (methodType == null || methodType.trim().equals("")) {
			methodType = METHOD_TYPE_GET;
		} else if (methodType.trim().equals(METHOD_TYPE_GET)) {
			methodType = METHOD_TYPE_GET;
		} else if (methodType.trim().equals(METHOD_TYPE_POST)) {// post请求
			methodType = METHOD_TYPE_POST;
		} else {
			methodType = METHOD_TYPE_GET;
		}
		// http Header
		List<Header> headers = null;
		if (headerMap != null && headerMap.size() > 0) {
			headers = new ArrayList<Header>();
			for (Iterator<String> it = headerMap.keySet().iterator(); it
					.hasNext();) {
				String key = (String) it.next();
				String value = (String) headerMap.get(key);
				Header header = new Header(key, value);
				headers.add(header);
			}
		}

		// 参数
		NameValuePair[] params = null;
		String urlParam = "";
		if (paramsMap == null || paramsMap.size() <= 0) {
			params = new NameValuePair[0];
		} else {
			params = new NameValuePair[paramsMap.size()];
			int i = 0;
			for (Iterator<String> it = paramsMap.keySet().iterator(); it
					.hasNext(); i++) {
				String key = (String) it.next();
				String value = (String) paramsMap.get(key);
				NameValuePair pa = null;
				try {
					// pa = new NameValuePair(key,URLEncoder.encode(value,
					// "UTF-8"));
					pa = new NameValuePair(key, value);
					if (i == 0) {
						// urlParam+="?"+key+"=" + URLEncoder.encode(value,
						// "UTF-8");
						urlParam += "?" + key + "=" + value;
					} else {
						// urlParam+="&"+key+"=" + URLEncoder.encode(value,
						// "UTF-8");
						urlParam += "&" + key + "=" + value;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				params[i] = pa;
			}
		}

		HttpClient http = new HttpClient();
		http.getHttpConnectionManager().getParams().setConnectionTimeout(20000);// 超时时间,20S
		http.getHttpConnectionManager().getParams().setSoTimeout(20000);

		GetMethod getMethod = null;
		PostMethod postMethod = null;
		Util.log("客户端----请求url：" + url + "  params: " + urlParam);

		if (methodType == METHOD_TYPE_GET) {
			getMethod = new GetMethod(url);
			getMethod.setRequestHeader("Content-type",
					"text/plain;charset=UTF-8");
			if (headers != null && headers.size() > 0) {
				for (Header header : headers) {
					getMethod.setRequestHeader(header);
				}

			}
			if (params != null) {
				getMethod.setQueryString(params);

			}
		} else {
			postMethod = new PostMethod(url);
			postMethod.setRequestHeader("Content-type",
					"text/plain;charset=UTF-8");
			if (headers != null && headers.size() > 0) {
				for (Header header : headers) {
					postMethod.setRequestHeader(header);
				}

			}
			// 参数
			if (params != null) {
				postMethod.setRequestBody(params);
			}
		}// end post

		int ret = 0;
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			if (methodType == METHOD_TYPE_GET) {
				ret = http.executeMethod(getMethod);

			} else {
				ret = http.executeMethod(postMethod);
			}
			if (ret == HttpStatus.SC_OK) {
				StringBuffer buf = new StringBuffer("");
				if (methodType == METHOD_TYPE_GET) {
					if (getMethod.getResponseBodyAsStream() != null) {
						is = getMethod.getResponseBodyAsStream();
					}
				} else {
					if (postMethod.getResponseBodyAsStream() != null) {
						is = postMethod.getResponseBodyAsStream();
					}
				}
				if (is != null) {
					isr = new InputStreamReader(is, "UTF-8");
					br = new BufferedReader(isr);
					String temp = null;
					while ((temp = br.readLine()) != null) {
						buf.append(temp);
					}
					result = buf.toString();
				}
			}

		} catch (IOException e) {
			result = null;
			Util.log("\"客户端----请求异常，请联系管理员。\"" + e);
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (isr != null) {
					isr.close();
				}
				if (is != null) {
					is.close();
				}
				if (postMethod != null) {
					postMethod.releaseConnection();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Util.log("客户端----httpclient使用方法：" + methodType + ",并收到响应信息：" + result);
		// logger.info(result);
		return result;
	}

}
