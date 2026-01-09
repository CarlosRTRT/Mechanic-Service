package data;

import java.util.List;

import business.LogicAlert;
import domain.Client;
import domain.Vehicle;

public class VehicleData {
	
	public static void saveVehicleIntoClient(Vehicle vehicle) {
		try {
			List<Client> clients = ClientData.getList();
			
			Client ultimo = clients.getLast();
			
			ultimo.addVehicle(vehicle);
			
			ClientData.editClient(ultimo);
			
			LogicAlert.alertMessage("Vehiculo Agregado a cliente exitosamente");
			
		} catch (Exception e) {
			e.getStackTrace();
			System.out.println("Vehicle.saveVehicleIntoClient");
		}
	}
	
	public static void editVehicle(Vehicle vehicle) {
		List<Client> clients = ClientData.getList();
		
		for(Client client: clients) {
			for(Vehicle tempVehicle : client.getVehicles()) {
				if(tempVehicle.getlicensePlate().equals(vehicle.getlicensePlate())) {
					tempVehicle.setBrand(vehicle.getBrand());
					tempVehicle.setFuelType(vehicle.getFuelType());
					tempVehicle.setlicensePlate(vehicle.getlicensePlate());
					tempVehicle.setModel(vehicle.getModel());
					tempVehicle.setOrder(vehicle.getOrder());
					tempVehicle.setOwner(vehicle.getOwner());
					tempVehicle.setYear(vehicle.getYear());
					ClientData.editClient(client);
					return;
				}
			}
		}
	}
	
	public static void deleteVehicle(Vehicle vehicle) {
		List<Client> clients = ClientData.getList();
		
		for(Client client: clients) {
			List<Vehicle> vehicles = client.getVehicles();

			for(int i = 0; i < vehicles.size(); i++) {
				if(vehicles.get(i).getlicensePlate().equals(vehicle.getlicensePlate())) {
					vehicles.remove(i);
					ClientData.editClient(client);
					return;
				}
			}
		}
	}

}
