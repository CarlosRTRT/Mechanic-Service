package data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import domain.Client;
import domain.Vehicle;

public class VehicleData {
	private Connection connection;
	
	public VehicleData() {
		this.connection = DBConnection.getConnection();
	}
	
	public LinkedList<Vehicle> getListVehiclesActive(){
		LinkedList<Vehicle> myList = new LinkedList<Vehicle>();
		try {
			String query = "call auto_tech.list_vehicles();";
			CallableStatement stmt = connection.prepareCall(query);
			ResultSet result = stmt.executeQuery();
			while(result.next()) {
				Vehicle v1 = new Vehicle();
				v1.setId(result.getInt(1));
				v1.setLicensePlate(result.getString(2));
				v1.setBrand(result.getString(3));
				v1.setModel(result.getString(4));
				v1.setYear(result.getInt(5));
				v1.setFuelType(result.getString(6));
				v1.setOwner(result.getInt(7));
				v1.setState(result.getString(8));
				myList.add(v1);
			}
		} catch (SQLException e) {
			System.out.println("VehicleData.getListVehicles");
			System.out.println("Error al crear la lista "+e.getMessage());
		}
		return myList;
	}
	
	public LinkedList<Vehicle> getListVehicles(){
		LinkedList<Vehicle> myList = new LinkedList<Vehicle>();
		try {
			String query = "call auto_tech.list_all_vehicles();";
			CallableStatement stmt = connection.prepareCall(query);
			ResultSet result = stmt.executeQuery();
			while(result.next()) {
				Vehicle v1 = new Vehicle();
				v1.setId(result.getInt(1));
				v1.setLicensePlate(result.getString(2));
				v1.setBrand(result.getString(3));
				v1.setModel(result.getString(4));
				v1.setYear(result.getInt(5));
				v1.setFuelType(result.getString(6));
				v1.setOwner(result.getInt(7));
				v1.setState(result.getString(8));
				myList.add(v1);
			}
		} catch (SQLException e) {
			System.out.println("VehicleData.getListVehicles");
			System.out.println("Error al crear la lista "+e.getMessage());
		}
		return myList;
	}
	
	public boolean addVehicle(Vehicle vehicle) {
		boolean result = false;
		try {
			String query = "call auto_tech.add_vehicle(?,?,?,?,?,?);";
			CallableStatement stmt = connection.prepareCall(query);
			stmt.setString(1, vehicle.getLicensePlate());
			stmt.setString(2, vehicle.getBrand());
			stmt.setString(3, vehicle.getModel());
			stmt.setInt(4, vehicle.getYear());
			stmt.setString(5, vehicle.getFuelType());
			stmt.setInt(6, vehicle.getOwner());
			stmt.execute();
			result = true;
		} catch (SQLException e) {
			System.out.println("VehicleData.addVehicle");
			System.out.println("Error al guardar Vehiculo: "+e.getMessage());
		}
		return result;
	}
	
	public boolean updateVehicle(Vehicle plate, String brand, String model, int year, String fuelType) {
		boolean result = false;
		try {
			String query = "call auto_tech.update_vehicle(?,?,?,?,?);";
			CallableStatement stmt = connection.prepareCall(query);
			stmt.setString(1, plate.getLicensePlate());
			stmt.setString(2, brand);
			stmt.setString(3, model);
			stmt.setInt(4, year);
			stmt.setString(5, fuelType);
			stmt.execute();
			result = true;
		} catch (Exception e) {
			System.out.println("VehicleData.updateVehicle");
			System.out.println("Error al editar Vehiculo: "+e.getMessage());
		}
		return result;
	}
	
	public boolean deleteVehicle(Vehicle plate) {
		boolean find = false;
		try {
			String query = "call auto_tech.delete_vehicle(?);";
			CallableStatement stmt = connection.prepareCall(query);
			stmt.setInt(1, plate.getId());
			stmt.execute();
			find = true;
		}catch(SQLException e) {
			System.out.println("VehicleData.deleteVehicle");
			System.out.println("Error al eliminar Vehiculo: "+e.getMessage());
		}
		return find;
	}
}