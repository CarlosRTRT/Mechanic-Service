package domain;

import java.util.ArrayList;
import java.util.List;

public class Client {
	private int id;
	private String idClient; // ES UNICO
	private String fullName;
	private int phoneNumber;
	private String email;
	private String direction;
	private List<Vehicle> vehicles;
	private String state;
	
	public Client(int id, String idClient, String fullName, int phoneNumber, String email, String direction) {
		this.id = id;
		this.idClient = idClient;
		this.fullName = fullName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.direction = direction;
		this.vehicles = new ArrayList<Vehicle>();
		this.state = "Activo";
	}

	public Client() {
		this.vehicles = new ArrayList<Vehicle>();
	}

	public String getIdClient() {
		return idClient;
	}

	public void setIdClient(String idClient) {
		this.idClient = idClient;
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
		return "Cedula: "+idClient + " Nombre: " + fullName + " "+state;
	}
	
	

	public List<Vehicle> getVehicles() {
		return vehicles;
	}

	public void setVehicles(List<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}
	
	public void addVehicle(Vehicle vehicle) {
	    if (this.vehicles == null) {
	        this.vehicles = new ArrayList<>();
	    }
		this.vehicles.add(vehicle);
	}
	
	public void deleteVehicle(Vehicle vehicle) {
		this.vehicles.remove(vehicle);
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
