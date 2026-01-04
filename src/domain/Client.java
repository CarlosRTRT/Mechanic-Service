package domain;

public class Client {
	private String id; // ES UNICO
	private String fullName;
	private int phoneNumber;
	private String email;
	private String direction;
	
	public Client(String id, String fullName, int phoneNumber, String email, String direction) {
		this.id = id;
		this.fullName = fullName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.direction = direction;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public int getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	@Override
	public String toString() {
		return "Client [id=" + id + ", fullName=" + fullName + ", phoneNumber=" + phoneNumber + ", email=" + email
				+ ", direction=" + direction + "]";
	}
	
	
}
