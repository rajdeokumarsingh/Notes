package util;

import java.util.ArrayList;

public class FeedbackInfos {

	int result;// ":"0","
	int records;// ":38,"
	ArrayList<FeedbackInfo> feedbackInfos;

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

	public ArrayList<FeedbackInfo> getFeedbackInfos() {
		return feedbackInfos;
	}

	public void setFeedbackInfos(ArrayList<FeedbackInfo> feedbackInfos) {
		this.feedbackInfos = feedbackInfos;
	}

	@Override
	public String toString() {
		return "FeedbackInfos [result=" + result + ", records=" + records
				+ ", feedbackInfos=" + feedbackInfos + "]";
	}

}
