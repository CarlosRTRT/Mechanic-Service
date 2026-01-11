package data;

import java.util.ArrayList;
import java.util.List;

import business.LogicAlert;
import domain.Client;
import domain.Vehicle;

public class VehicleData {
	
	public static void saveVehicleIntoClient(Vehicle vehicle, String clientId) {
	    try {
	        List<Client> clients = ClientData.getList();
	        
	        Client clienteEncontrado = null;
	        for (Client client : clients) {
	            if (client.getId().equals(clientId)) {
	                clienteEncontrado = client;
	                break;
	            }
	        }
	        
	        if (clienteEncontrado == null) {
	            System.out.println("Cliente no encontrado con ID: " + clientId);
	            return;
	        }
	        
	        clienteEncontrado.addVehicle(vehicle);
	        
	        ClientData.editClient(clienteEncontrado);
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("Vehicle.saveVehicleIntoClient");
	    }
	}
	
	public static void editVehicle(Vehicle vehicle) {
		List<Client> clients = ClientData.getList();
		
		for(Client client: clients) {
			for(Vehicle tempVehicle : client.getVehicles()) {
				if(tempVehicle.getLicensePlate().equals(vehicle.getLicensePlate())) {
					tempVehicle.setBrand(vehicle.getBrand());
					tempVehicle.setFuelType(vehicle.getFuelType());
					tempVehicle.setLicensePlate(vehicle.getLicensePlate());
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
				if(vehicles.get(i).getLicensePlate().equals(vehicle.getLicensePlate())) {
					vehicles.remove(i);
					ClientData.editClient(client);
					return;
				}
			}
		}
	}
	
	public static ArrayList<Vehicle> getList(){
		ArrayList<Client> clients = ClientData.getList();
		ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
		
		for(Client clientsTemp : clients) {
			for(Vehicle vehiclesTemp : clientsTemp.getVehicles()) {
				vehicles.add(vehiclesTemp);
			}
		}
		return vehicles;
	}

}
