package util;

public class Policy {
	String name;//: "默认Android策略",
	String id;//: 1,
	String priority;//: 0,
	String content;//: null,
	String description;//: "默认android策略",
	String status;//: 50402,
	String jcontent;//: null,
	String updateTime;//: 1375842214000,
	String deleted;//: 0,
	String isDefault;//: 1,
	String nameId;//: 4,
	String policyType;//: 51201,
	String special;//: 0
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getJcontent() {
		return jcontent;
	}
	public void setJcontent(String jcontent) {
		this.jcontent = jcontent;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	public String getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
	public String getNameId() {
		return nameId;
	}
	public void setNameId(String nameId) {
		this.nameId = nameId;
	}
	public String getPolicyType() {
		return policyType;
	}
	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}
	public String getSpecial() {
		return special;
	}
	public void setSpecial(String special) {
		this.special = special;
	}
	@Override
	public String toString() {
		return "Policy [name=" + name + ", id=" + id + ", priority=" + priority
				+ ", content=" + content + ", description=" + description
				+ ", status=" + status + ", jcontent=" + jcontent
				+ ", updateTime=" + updateTime + ", deleted=" + deleted
				+ ", isDefault=" + isDefault + ", nameId=" + nameId
				+ ", policyType=" + policyType + ", special=" + special + "]";
	}
	
	
}
