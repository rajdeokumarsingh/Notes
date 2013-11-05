package util;

public class ErrorCode {

	int result;// ":"1 ", "
	int error;// ":

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public int getError() {
		return error;
	}

	public void setError(int error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return "Error [result=" + result + ", error=" + error + "]";
	}

}
