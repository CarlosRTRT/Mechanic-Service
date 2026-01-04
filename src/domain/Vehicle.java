package domain;

public class Vehicle {
	private String licensePlate; // UNICO
	private String brand;
	private String model;
	private int year;
	private String fuelType; //(gasolina, diésel, eléctrico, híbrido)
	private String owner;
	
	public Vehicle(String licensePlate, String marca, String model, int year, String fuelType, String owner) {
		this.licensePlate = licensePlate;
		this.brand = marca;
		this.model = model;
		this.year = year;
		this.fuelType = fuelType;
		this.owner = owner;
	}

	public String getlicensePlate() {
		return licensePlate;
	}

	public void setlicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public String getMarca() {
		return brand;
	}

	public void setMarca(String marca) {
		this.brand = marca;
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
		return "Vehicle [licensePlate=" + licensePlate + ", marca=" + brand + ", model=" + model + ", year=" + year + ", fuelType="
				+ fuelType + ", owner=" + owner + "]";
	}
	
}
