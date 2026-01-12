package data;

import java.util.ArrayList;
import java.util.List;

import business.LogicAlert;
import domain.Client;
import domain.Orders;
import domain.Vehicle;

public class OrdersData {
	
	public static void saveOrderIntoVehicle(List<Orders> order) {
		try {
			List<Client> clients = ClientData.getList();
			
			Client last = clients.getLast();
			List<Vehicle> vehicles = last.getVehicles();
			Vehicle lastVehicle = vehicles.getLast();
			
			lastVehicle.setOrder(order);
			
			ClientData.editClient(last);
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Orders.saveOrderIntoVehicle");
		}
	}
	
	public static void editOrder(Orders order, int cost) {
		List<Client> clients = ClientData.getList();
		ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
		
		for(Client client: clients) {
			for(Vehicle tempVehicle : client.getVehicles()) {
				vehicles.add(tempVehicle);	
				}
			for(Vehicle vehiclesTemp : vehicles)
			for(Orders tempOrder : vehiclesTemp.getOrder()) {
				if(tempOrder.getOrderNumber() == order.getOrderNumber()) {
					
					tempOrder.setMechanic(order.getMechanic());
					tempOrder.setObservations(order.getObservations());
					tempOrder.setOrderState(order.getOrderState());
					tempOrder.setTotalPrice(order.getTotalPrice() - cost);
					tempOrder.setServices(order.getServices());
					
					ClientData.editClient(client);
					return;
					}
			}
		}
	}
	
	public static void deleteOrder(Orders order) {
		List<Client> clients = ClientData.getList();
		
		for(Client client: clients) {
			for(Vehicle vehicle : client.getVehicles()) {
				List<Orders> orders = vehicle.getOrder();
				for(int i = 0; i < orders.size(); i++) {
				if(orders.get(i).getOrderNumber() == order.getOrderNumber()) {
					orders.remove(i);
					ClientData.editClient(client);
					return;
				}
			}

			}
		}
	}
	
	public static void addAnotherOrder(Orders order, Vehicle vehicle) {
		
		ArrayList<Vehicle> vehicles = VehicleData.getList();
		
		for(Vehicle tempVehicle : vehicles) {
				if(tempVehicle.getLicensePlate().equals(vehicle.getLicensePlate())) {
		            if(tempVehicle.getOrder() == null) {
		                tempVehicle.setOrder(new ArrayList<>());
		            }
					tempVehicle.addOrder(order);
					VehicleData.editVehicle(tempVehicle);
					return;
				}
		}
	}
	
	public static List<Orders> getList() {
	    var allOrders = new ArrayList<Orders>();
	    var clients = ClientData.getList();
	    
	    for (Client client : clients) {
	        for (Vehicle vehicle : client.getVehicles()) {
	            ArrayList<Orders> orders = (ArrayList<Orders>) vehicle.getOrder();
	            if (orders != null) {
	                allOrders.addAll(orders);
	            }
	        }
	    }
	    
	    return allOrders;
	}
}
