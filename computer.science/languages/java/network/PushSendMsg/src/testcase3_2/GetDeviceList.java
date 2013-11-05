package testcase3_2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import util.DevicesBean;
import util.Util;

import com.google.gson.Gson;

public class GetDeviceList {
	private boolean showlog = false;
	private String host;

	public GetDeviceList(String host) {
		this.host = host;
	}

	public void test() {
		Util.Showlog(showlog, "" + GetDeviceList.class.getName());
		HttpClient httpClient = new HttpClient();
		GetMethod getMethod = new GetMethod(host
				+ "rest/mdm/v1/device/get_device_list?start_index=1&count=200");
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		getMethod.setRequestHeader("Authorization", Util.token);
		int statusCode;

		try {
			statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				Util.Showlog(showlog, GetDeviceList.class.getName() + "1");
				Util.addErrorinfo(
						"" + new Exception().getStackTrace()[0].getFileName(),
						"" + getMethod.getStatusLine());

				System.err.println("Method failed: "
						+ getMethod.getStatusLine());
			} else {
				Util.Showlog(showlog, GetDeviceList.class.getName() + "2");
				InputStream is = getMethod.getResponseBodyAsStream();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is));
				String response = reader.readLine();
				Util.Showlog(showlog, GetDeviceList.class.getName() + "  "
						+ response);
				if (response.contains(Util.call)) {
					Util.log(GetDeviceList.class.getName() + "  " + "获取设备列表成功");
					Gson gson = new Gson();
					DevicesBean mDevicesBean = gson.fromJson("" + response,
							DevicesBean.class);
					Util.devices = mDevicesBean.getDevices();
					for (int i = 0; i < Util.devices.size(); i++) {
						Util.log("111111    "
								+ Util.devices.get(i).getId()
								+ "\n");
					}
					Util.Showlog(showlog, "mDevicesBean" + mDevicesBean);
					for (int i = 0; i < mDevicesBean.getDevices().size(); i++) {

						if (mDevicesBean.getDevices().get(i).getDeviceUuid()
								.length() > "sdsdsdsdsddswdsdsdsss".length()) {
							Util.mDevice_ids = mDevicesBean.getDevices().get(i)
									.getId();
							Util.mDevice_UUid = mDevicesBean.getDevices()
									.get(i).getIosUdid();
							Util.Showlog(showlog, "  mIOS_Device_uuid  "
									+ Util.mIOS_Device_uuid
									+ "  mDevice_ids   " + Util.mDevice_ids
									+ " mDevice_UUid " + Util.mDevice_UUid);
							break;
						}

					}
				} else {
					Util.log(GetDeviceList.class.getName() + "  " + "获取设备列表失败");
					Util.Showlog(showlog, "GetlUserInfoList  error" + response);
					Util.addErrorinfo(
							""
									+ new Exception().getStackTrace()[0]
											.getFileName(), "" + response);
				}
			}
			getMethod.releaseConnection();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
