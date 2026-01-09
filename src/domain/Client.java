package domain;

import java.util.ArrayList;
import java.util.List;

public class Client {
	private String id; // ES UNICO
	private String fullName;
	private int phoneNumber;
	private String email;
	private String direction;
	private List<Vehicle> vehicles;
	
	public Client(String id, String fullName, int phoneNumber, String email, String direction) {
		this.id = id;
		this.fullName = fullName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.direction = direction;
		this.vehicles = new ArrayList<>();
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

	public List<Vehicle> getVehicles() {
		return vehicles;
	}

	public void setVehicles(List<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}
	
	public void addVehicle(Vehicle vehicle) {
		this.vehicles.add(vehicle);
	}
	
	public void deleteVehicle(Vehicle vehicle) {
		this.vehicles.remove(vehicle);
	}
	
	
}
