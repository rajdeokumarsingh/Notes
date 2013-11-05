package util;

import java.util.List;

public class DevicesBean {
	int result;// ":"0","
	int records;// ":3,"
	List<DevicesInfo> devices;// :

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public int getRecords() {
		return records;
	}

	public void setRecords(int records) {
		this.records = records;
	}

	public List<DevicesInfo> getDevices() {
		return devices;
	}

	public void setDevices(List<DevicesInfo> devices) {
		this.devices = devices;
	}

	@Override
	public String toString() {
		return "DevicesBean [result=" + result + ", records=" + records
				+ ", devices=" + devices + "]";
	}

}
