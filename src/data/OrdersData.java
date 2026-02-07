package data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;

import domain.Orders;
import domain.Services;
import domain.Vehicle;

public class OrdersData {
	private Connection connection;
	
	public OrdersData() {
		this.connection = DBConnection.getConnection();
	}
	
	public int addOrders(Orders order) {
		int idOrder = 0;
		try {
			String query = "call auto_tech.add_order(?,?,?,?,?,?,?,?);";
			CallableStatement stmt = connection.prepareCall(query);
			stmt.setInt(1, order.getOrderNumber());
			stmt.setString(2, order.getOrderState());
			stmt.setString(3, order.getObservations());
			stmt.setInt(4, order.getTotalPrice());
			stmt.setDate(5, Date.valueOf(order.getCreationDate()));
			stmt.setInt(6, order.getIdVehicle());
			stmt.setInt(7, order.getIdMechanic());
			stmt.registerOutParameter(8, Types.INTEGER);
			stmt.execute();
			idOrder = stmt.getInt(8);
		} catch (SQLException e) {
			System.out.println("OrdersData.addOrder");
			System.out.println("Error al guardar Orden: "+e.getMessage());
		}
		return idOrder;
	}
	
	public boolean updateOrder(int idOrder, String orderState, String observations, int totalPrice, LocalDate 
			creationDate, int idVehicle, int idMechanic) {
		boolean find = false;
		try {
			String query = "call auto_tech.update_order(?,?,?,?,?,?,?);";
			CallableStatement stmt = connection.prepareCall(query);
			stmt.setInt(1, idOrder);
			stmt.setString(2, orderState);
			stmt.setString(3, observations);
			stmt.setInt(4, totalPrice);
			stmt.setDate(5, Date.valueOf(creationDate));
			stmt.setInt(6, idVehicle);
			stmt.setInt(7, idMechanic);
			stmt.execute();
			find = true;
		} catch (SQLException e) {
			System.out.println("OrdersData.updateOrder");
			System.out.println("Error "+e.getMessage());
		}
		return find;
	}
	
	public boolean deleteOrder(int idOrder) {
		boolean find = false;
		try {
			String query = "call auto_tech.delete_order(?);";
			CallableStatement stmt = connection.prepareCall(query);
			stmt.setInt(1, idOrder);
			stmt.execute();
			find = true;
		}catch(SQLException e) {
			System.out.println("OrderData.deleteOrder");
			System.out.println("Error al eliminar Orden: "+e.getMessage());
		}
		return find;
	}
	
	public LinkedList<Orders> getListOrders(){
		LinkedList<Orders> myList = new LinkedList<Orders>();
		try {
			String query = "call auto_tech.list_orders();";
			CallableStatement stmt = connection.prepareCall(query);
			ResultSet result = stmt.executeQuery();

			while(result.next()) {
				Orders o1 = new Orders();
				o1.setId(result.getInt(1));
				o1.setOrderNumber(result.getInt(2));
				o1.setOrderState(result.getString(3));
				o1.setObservations(result.getString(4));
				o1.setTotalPrice(result.getInt(5));
				o1.setCreationDate(LocalDate.parse(result.getString(6)));
				o1.setIdVehicle(result.getInt(7));
				o1.setIdMechanic(result.getInt(8));
				myList.add(o1);
			}

		} catch (SQLException e) {
			System.out.println("OrdersData.getListOrders");
			System.out.println("Error al crear la lista "+e.getMessage());
		}
		return myList;
	}
	
	public LinkedList<Services> listServicesByOrder(int idOrder){
		LinkedList<Services> myList = new LinkedList<Services>();
		try {
			String query = "call auto_tech.list_services_by_order(?);";
			CallableStatement stmt = connection.prepareCall(query);
			stmt.setInt(1, idOrder);
			ResultSet result = stmt.executeQuery();

			while(result.next()) {
				Services s1 = new Services();
				s1.setId(result.getInt(1));
				s1.setServiceCode(result.getInt(2));
				s1.setServiceName(result.getString(3));
				s1.setDescription(result.getString(4));
				s1.setBaseCost(result.getInt(5));
				s1.setEstimatedTime(result.getInt(6));
				myList.add(s1);
			}

		} catch (SQLException e) {
			System.out.println("OrdersData.listServicesByOrder");
			System.out.println("Error al crear la lista "+e.getMessage());
		}
		return myList;
	}
	
	public void addOrderToVehicle(Vehicle vehicle, ArrayList<Orders> orders) {
		for(Orders tempOrder : orders) {
			vehicle.getOrder().add(tempOrder);
		}
	}
}
