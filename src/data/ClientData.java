package data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import domain.Client;

public class ClientData {
	private Connection connection;

	public ClientData() {
		this.connection = DBConnection.getConnection();
	}

	public LinkedList<Client> getListClientsActive(){
		LinkedList<Client> myList = new LinkedList<Client>();

		try {
			String query = "call auto_tech.list_clients();";
			CallableStatement stmt = connection.prepareCall(query);
			ResultSet result = stmt.executeQuery();

			while(result.next()) {
				Client c1 = new Client();
				c1.setId(result.getInt(1));
				c1.setIdClient(String.valueOf(result.getInt(2)));
				c1.setFullName(result.getString(3));
				c1.setPhoneNumber(result.getInt(4));
				c1.setEmail(result.getString(5));
				c1.setDirection(result.getString(6));
				c1.setState(result.getString(7));
				myList.add(c1);
			}

		} catch (SQLException e) {
			System.out.println("ClientData.getListClientsActive");
			System.out.println("Error al crear la lista "+e.getMessage());
		}
		return myList;
	}
	
	public LinkedList<Client> getListAllClients(){
		LinkedList<Client> myList = new LinkedList<Client>();

		try {
			String query = "call auto_tech.list_all_clients();";
			CallableStatement stmt = connection.prepareCall(query);
			ResultSet result = stmt.executeQuery();

			while(result.next()) {
				Client c1 = new Client();
				c1.setId(result.getInt(1));
				c1.setIdClient(String.valueOf(result.getInt(2)));
				c1.setFullName(result.getString(3));
				c1.setPhoneNumber(result.getInt(4));
				c1.setEmail(result.getString(5));
				c1.setDirection(result.getString(6));
				c1.setState(result.getString(7));
				myList.add(c1);
			}

		} catch (SQLException e) {
			System.out.println("ClientData.getListAllClients");
			System.out.println("Error al crear la lista "+e.getMessage());
		}
		return myList;
	}

	public boolean addClient(Client client) {
		boolean result = false;
		try {
			String query = "call auto_tech.add_client(?,?,?,?,?);";
			CallableStatement stmt = connection.prepareCall(query);
			stmt.setString(1, client.getIdClient());
			stmt.setString(2, client.getFullName());
			stmt.setInt(3, client.getPhoneNumber());
			stmt.setString(4, client.getEmail());
			stmt.setString(5, client.getDirection());
			stmt.executeQuery();
			result = true;
		} catch (SQLException e) {
			System.out.println("ClientData.addClient");
			System.out.println("Error al guardar Cliente: "+e.getMessage());
		}
		return result;
	}

	public boolean deleteClient(int idClient) {
		boolean find = false;
		try {
			String query = "call auto_tech.delete_client(?);";
			CallableStatement stmt = connection.prepareCall(query);
			stmt.setInt(1, idClient);
			stmt.executeQuery();
			find = true;
		}catch(SQLException e) {
			System.out.println("ClientData.deleteClient");
			System.out.println("Error al eliminar Cliente: "+e.getMessage());
		}
		return find;
	}
	
	public boolean updateClient(int idClient, String name, int phoneNumber, String email, String direction) {
		boolean result = false;
		try {
			String query = "call auto_tech.update_client(?,?,?,?,?);";
			CallableStatement stmt = connection.prepareCall(query);
			stmt.setInt(1, idClient);
			stmt.setString(2, name);
			stmt.setInt(3, phoneNumber);
			stmt.setString(4, email);
			stmt.setString(5, direction);
			stmt.executeQuery();
			result = true;
		} catch (Exception e) {
			System.out.println("ClientData.updateClient");
			System.out.println("Error al editar Cliente: "+e.getMessage());
		}
		return result;
	}
}
