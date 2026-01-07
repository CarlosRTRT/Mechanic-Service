package data;

import domain.Vehicle;

public class VehicleData {
	
	private final String filePath = "data.json";
	
	private JsonUtils<Vehicle> jsonUtils = new JsonUtils<Vehicle>(this.filePath);
	
	public VehicleData() {
		
	}
	
	public static void save(Vehicle vehicle) {
		try {
		//	jsonUtils.saveElement(vehicle);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
