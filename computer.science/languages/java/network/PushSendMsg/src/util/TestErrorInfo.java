package util;

public class TestErrorInfo {

	String testNUm;// ":"1 ", "
	String errorInfo;// ":

	public String getTestNUm() {
		return testNUm;
	}

	public void setTestNUm(String testNUm) {
		this.testNUm = testNUm;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}

	@Override
	public String toString() {
		return "TestErrorInfo [testNUm=" + testNUm + ", errorInfo=" + errorInfo
				+ "]";
	}

	public TestErrorInfo(String testNUm, String errorInfo) {
		super();
		this.testNUm = testNUm;
		this.errorInfo = errorInfo;
	}

}
