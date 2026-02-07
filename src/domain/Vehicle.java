package domain;

import java.util.ArrayList;
import java.util.List;

public class Vehicle {
	private int id;
	private String licensePlate; // UNICO
	private String brand;
	private String model;
	private int year;
	private String fuelType; //(gasolina, diésel, eléctrico, híbrido)
	private int owner;
	private String nameClient;
	private List<Orders> order;	
	private String state;
	
	public Vehicle() {
		this.order = new ArrayList<Orders>();
	}
	
	public Vehicle(int id, String licensePlate, String brand, String model, int year, String fuelType, int owner) {
		this.id = id;
		this.licensePlate = licensePlate;
		this.brand = brand;
		this.model = model;
		this.year = year;
		this.fuelType = fuelType;
		this.owner = owner;
		this.order = new ArrayList<>();
		this.state = "Activo";
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getFuelType() {
		return fuelType;
	}

	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}

	public int getOwner() {
		return owner;
	}

	public void setOwner(int owner) {
		this.owner = owner;
	}

	@Override
	public String toString() {
		return "Vehicle [licensePlate=" + licensePlate + ", brand=" + brand + ", model=" + model + ", year=" + year + ", fuelType="
				+ fuelType + ", owner=" + owner + "]";
	}

	public List<Orders> getOrder() {
		return order;
	}

	public void setOrder(List<Orders> order) {
		this.order = order;
	}
	
	public void addOrder(Orders order) {
	    if (this.order == null) {
	        this.order = new ArrayList<>();
	    }
		this.order.add(order);
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

	public String getNameClient() {
		return nameClient;
	}

	public void setNameClient(String nameClient) {
		this.nameClient = nameClient;
	}
}
