package domain;

public class Mechanic {
	private String id; // GENERADO AUTOMATICAMENTE
	private String fullName;
	private String specialty; // (mecánica general, frenos, electricidad, motor, etc.).
	private int phoneNumber;
	private String email;
	
	public Mechanic() {
		
	}
	
	public Mechanic(String id, String fullName, String specialty, int phoneNumber, String email) {
		this.id = id;
		this.fullName = fullName;
		this.specialty = specialty;
		this.phoneNumber = phoneNumber;
		this.email = email;
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

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
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

	@Override
	public String toString() {
		return "Mechanic [id=" + id + ", fullName=" + fullName + ", specialty=" + specialty + ", phoneNumber="
				+ phoneNumber + ", email=" + email + "]";
	}
	
	public String toStringForCb() {
		return "Nombre completo: " + fullName + ", Especialidad: " + specialty;
	}
}
