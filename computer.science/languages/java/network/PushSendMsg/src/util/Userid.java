package util;

public class Userid {

	int result;
	int userId;

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Userid [result=" + result + ", userId=" + userId + "]";
	}

}
