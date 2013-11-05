package util;

public class FeedbackInfo {

	String deviceUuid;
	String feedbackType;
	String content;
	String faceImg;
	String id;// 0":39,"content":"??feedBack??",
	String time;// ":1378278020000,"
	String updateTime;// ":1378278020000,"deviceUuid":null,"
	String deviceId;// ":129,"faceImg":null,"feedbackType":51401

	public String getDeviceUuid() {
		return deviceUuid;
	}

	public void setDeviceUuid(String deviceUuid) {
		this.deviceUuid = deviceUuid;
	}

	public String getFeedbackType() {
		return feedbackType;
	}

	public void setFeedbackType(String feedbackType) {
		this.feedbackType = feedbackType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFaceImg() {
		return faceImg;
	}

	public void setFaceImg(String faceImg) {
		this.faceImg = faceImg;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	@Override
	public String toString() {
		return "FeedbackInfo [deviceUuid=" + deviceUuid + ", feedbackType="
				+ feedbackType + ", content=" + content + ", faceImg="
				+ faceImg + ", id=" + id + ", time=" + time + ", updateTime="
				+ updateTime + ", deviceId=" + deviceId + "]";
	}

}
