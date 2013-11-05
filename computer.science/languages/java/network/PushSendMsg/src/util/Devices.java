package util;

public class Devices {

	String username;
	String deviceUuid;
	String iosUdid;
	String type;
	String os;
	String securityInfo;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDeviceUuid() {
		return deviceUuid;
	}

	public void setDeviceUuid(String deviceUuid) {
		this.deviceUuid = deviceUuid;
	}

	public String getIosUdid() {
		return iosUdid;
	}

	public void setIosUdid(String iosUdid) {
		this.iosUdid = iosUdid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getSecurityInfo() {
		return securityInfo;
	}

	public void setSecurityInfo(String securityInfo) {
		this.securityInfo = securityInfo;
	}

	@Override
	public String toString() {
		return "Devices [username=" + username + ", deviceUuid=" + deviceUuid
				+ ", iosUdid=" + iosUdid + ", type=" + type + ", os=" + os
				+ ", securityInfo=" + securityInfo + "]";
	}

	public Devices(String username, String deviceUuid, String iosUdid,
			String type, String os, String securityInfo) {
		super();
		this.username = username;
		this.deviceUuid = deviceUuid;
		this.iosUdid = iosUdid;
		this.type = type;
		this.os = os;
		this.securityInfo = securityInfo;
	}

}
