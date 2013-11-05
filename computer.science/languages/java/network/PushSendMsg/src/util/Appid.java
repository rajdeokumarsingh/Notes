package util;

public class Appid {

	int result;
	int appId;

	public Appid(int result, int appId) {
		super();
		this.result = result;
		this.appId = appId;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	@Override
	public String toString() {
		return "appid [result=" + result + ", appId=" + appId + "]";
	}

}
