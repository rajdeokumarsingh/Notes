package util;

public class Users {

	String username;
	String password;
	String email;
	String type;
	String firstName;
	String lastName;
	String fullName;
	String roles;
	String phone;
	Devices devices;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Devices getDevices() {
		return devices;
	}

	public void setDevices(Devices devices) {
		this.devices = devices;
	}

	@Override
	public String toString() {
		return "Users [username=" + username + ", password=" + password
				+ ", email=" + email + ", type=" + type + ", firstName="
				+ firstName + ", lastName=" + lastName + ", fullName="
				+ fullName + ", roles=" + roles + ", phone=" + phone
				+ ", devices=" + devices + "]";
	}

	public Users(String username, String password, String email, String type,
			String firstName, String lastName, String fullName, String roles,
			String phone, Devices devices) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.type = type;
		this.firstName = firstName;
		this.lastName = lastName;
		this.fullName = fullName;
		this.roles = roles;
		this.phone = phone;
		this.devices = devices;
	}

}
