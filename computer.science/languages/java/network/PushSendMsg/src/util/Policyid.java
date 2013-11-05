package util;

public class Policyid {

	int result;
	int policyId;

	public Policyid(int result, int appId) {
		super();
		this.result = result;
		this.policyId = appId;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public int getPolicyId() {
		return policyId;
	}

	public void setPolicyId(int policyId) {
		this.policyId = policyId;
	}

	@Override
	public String toString() {
		return "Policyid [result=" + result + ", policyId=" + policyId + "]";
	}

}
