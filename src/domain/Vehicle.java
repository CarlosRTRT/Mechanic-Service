package domain;

import java.util.ArrayList;
import java.util.List;

public class Vehicle {
	private String licensePlate; // UNICO
	private String brand;
	private String model;
	private int year;
	private String fuelType; //(gasolina, diésel, eléctrico, híbrido)
	private String owner;
	private List<Orders> order;	
	
	
	public Vehicle() {
		// TODO Auto-generated constructor stub
	}
	
	public Vehicle(String licensePlate, String brand, String model, int year, String fuelType, String owner) {
		this.licensePlate = licensePlate;
		this.brand = brand;
		this.model = model;
		this.year = year;
		this.fuelType = fuelType;
		this.owner = owner;
		this.order = new ArrayList<>();
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

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
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
	
}
