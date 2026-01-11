package data;

import java.util.List;

import business.LogicAlert;
import domain.Client;
import domain.Orders;
import domain.Vehicle;

public class OrdersData {
	
	public static void saveOrderIntoVehicle(List<Orders> order ) {
		try {
			List<Client> clients = ClientData.getList();
			
			Client last = clients.getLast();
			List<Vehicle> vehicles = last.getVehicles();
			Vehicle lastVehicle = vehicles.getLast();
			
			lastVehicle.setOrder(order);
			
			
			ClientData.editClient(last);
			
			LogicAlert.alertMessage("Orden Agregada a vehiculo exitosamente");
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Orders.saveOrderIntoVehicle");
		}
	}
	
	public static void editOrder(Orders order) {
		List<Client> clients = ClientData.getList();
		
		for(Client client: clients) {
			for(Vehicle tempVehicle : client.getVehicles()) {
				for(Orders tempOrder : tempVehicle.getOrder()) {
					if(tempOrder.getOrderNumber() == order.getOrderNumber()) {
						
						tempOrder.setMechanic(order.getMechanic());
						tempOrder.setObservations(order.getObservations());
						tempOrder.setOrderState(order.getOrderState());
						tempOrder.setTotalPrice(order.getTotalPrice());
						
						ClientData.editClient(client);
						return;
					}
					
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
}
