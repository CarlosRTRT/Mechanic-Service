package domain;

public class Mechanic {
	private int id;
	private String idMechanic; // GENERADO AUTOMATICAMENTE
	private String fullName;
	private String specialty; // (mecánica general, frenos, electricidad, motor, etc.).
	private int phoneNumber;
	private String email;
	private String state;
	
	public Mechanic() {
		
	}
	
	public Mechanic(int id, String idMechanic, String fullName, String specialty, int phoneNumber, String email) {
		this.id = id;
		this.idMechanic = idMechanic;
		this.fullName = fullName;
		this.specialty = specialty;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.state = "Activo";
	}

	public String getIdMechanic() {
		return idMechanic;
	}

	public void setIdMechanic(String idMechanic) {
		this.idMechanic = idMechanic;
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
		return  fullName + ", " + specialty;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
