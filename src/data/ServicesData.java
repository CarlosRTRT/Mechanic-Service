package data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import domain.Orders;
import domain.Services;

public class ServicesData {
	private Connection connection;

	public ServicesData() {
		this.connection = DBConnection.getConnection();
	}

	public LinkedList<Services> getListServicesActive(){
		LinkedList<Services> myList = new LinkedList<Services>();
		try {
			String query = "call auto_tech.list_services_active();";
			CallableStatement stmt = connection.prepareCall(query);
			ResultSet result = stmt.executeQuery();

			while(result.next()) {
				Services s1 = new Services();
				s1.setId(result.getInt(1));
				s1.setServiceCode(result.getInt(2));
				s1.setServiceName(result.getString(3));
				s1.setDescription(result.getString(4));
				s1.setBaseCost(result.getInt(5));
				s1.setEstimatedTime(result.getInt(6));
				s1.setState(result.getString(7));
				myList.add(s1);
			}

		} catch (SQLException e) {
			System.out.println("ServicesData.getListServices");
			System.out.println("Error al crear la lista "+e.getMessage());
		}
		return myList;
	}
	
	public LinkedList<Services> getListServices(){
		LinkedList<Services> myList = new LinkedList<Services>();
		try {
			String query = "call auto_tech.list_all_services();";
			CallableStatement stmt = connection.prepareCall(query);
			ResultSet result = stmt.executeQuery();

			while(result.next()) {
				Services s1 = new Services();
				s1.setId(result.getInt(1));
				s1.setServiceCode(result.getInt(2));
				s1.setServiceName(result.getString(3));
				s1.setDescription(result.getString(4));
				s1.setBaseCost(result.getInt(5));
				s1.setEstimatedTime(result.getInt(6));
				s1.setState(result.getString(7));
				myList.add(s1);
			}

		} catch (SQLException e) {
			System.out.println("ServicesData.getListServices");
			System.out.println("Error al crear la lista "+e.getMessage());
		}
		return myList;
	}

	public boolean addService(Services service) {
		boolean result = false;
		try {
			String query = "call auto_tech.add_service(?,?,?,?,?);";
			CallableStatement stmt = connection.prepareCall(query);
			stmt.setInt(1, service.getServiceCode());
			stmt.setString(2, service.getServiceName());
			stmt.setString(3, service.getDescription());
			stmt.setInt(4, service.getBaseCost());
			stmt.setInt(5, service.getEstimatedTime());
			stmt.executeQuery();
			result = true;
		} catch (SQLException e) {
			System.out.println("ServicesData.addService");
			System.out.println("Error al guardar Service: "+e.getMessage());
		}
		return result;
	}

	public boolean deleteService(int idService) {
		boolean find = false;
		try {
			String query = "call auto_tech.delete_service(?);";
			CallableStatement stmt = connection.prepareCall(query);
			stmt.setInt(1, idService);
			stmt.execute();
			find = true;
		}catch(SQLException e) {
			System.out.println("ServicesData.deleteService");
			System.out.println("Error al eliminar Service: "+e.getMessage());
		}
		return find;
	}
	
	public boolean updateService(int codeService, String name, String descripcion, int baseCost, int estimatedTime) {
		boolean result = false;
		try {
			String query = "call auto_tech.update_service(?,?,?,?,?);";
			CallableStatement stmt = connection.prepareCall(query);
			stmt.setInt(1, codeService);
			stmt.setString(2, name);
			stmt.setString(3, descripcion);
			stmt.setInt(4, baseCost);
			stmt.setInt(5, estimatedTime);
			stmt.executeQuery();
			result = true;
		} catch (Exception e) {
			System.out.println("ServicesData.updateService");
			System.out.println("Error al editar Service: "+e.getMessage());
		}
		return result;
	}
	
	public boolean addServiceToOrder(int idOrder, int idService) {
		boolean result = false;
		try {
			String query = "call auto_tech.add_services_to_order(?,?);";
			CallableStatement stmt = connection.prepareCall(query);
			stmt.setInt(1, idOrder);
			stmt.setInt(2, idService);
			stmt.execute();
			result = true;
		} catch (SQLException e) {
			System.out.println("ServicesData.addServiceToOrder");
			System.out.println("Error al guardar: "+e.getMessage());
		}
		return result;
	}
	
	public boolean deleteServicesToOrder(int idOrder) {
		boolean result = false;
		try {
			String query = "call auto_tech.delete_services_from_order(?);";
			CallableStatement stmt = connection.prepareCall(query);
			stmt.setInt(1, idOrder);
			stmt.execute();
			result = true;
		} catch (Exception e) {
			System.out.println("ServicesData.deleteservicesfromorder");
			System.out.println("Error al eliminar: "+e.getMessage());
		}
		return result;
	}
}
