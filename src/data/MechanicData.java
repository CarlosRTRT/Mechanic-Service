package data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import domain.Mechanic;

public class MechanicData {
	private Connection connection;

	public MechanicData() {
		this.connection = DBConnection.getConnection();
	}

	public LinkedList<Mechanic> getListMechanics(){
		LinkedList<Mechanic> myList = new LinkedList<Mechanic>();

		try {
			String query = "call auto_tech.list_Mechanics();";
			CallableStatement stmt = connection.prepareCall(query);
			ResultSet result = stmt.executeQuery();

			while(result.next()) {
				Mechanic m1 = new Mechanic();
				m1.setId(result.getInt(1));
				m1.setIdMechanic(result.getString(2));
				m1.setFullName(result.getString(3));
				m1.setSpecialty(result.getString(4));
				m1.setPhoneNumber(result.getInt(5));
				m1.setEmail(result.getString(6));
				m1.setState(result.getString(7));
				myList.add(m1);
			}

		} catch (SQLException e) {
			System.out.println("MechanicData.getListMechanic");
			System.out.println("Error al crear la lista "+e.getMessage());
		}
		return myList;
	}

	public boolean addMechanic(Mechanic mechanic) {
		boolean result = false;
		try {
			String query = "call auto_tech.add_mechanic(?,?,?,?,?);";
			CallableStatement stmt = connection.prepareCall(query);
			stmt.setInt(1, Integer.parseInt(mechanic.getIdMechanic()));
			stmt.setString(2, mechanic.getFullName());
			stmt.setString(3, mechanic.getSpecialty());
			stmt.setInt(4, mechanic.getPhoneNumber());
			stmt.setString(5, mechanic.getEmail());
			stmt.executeQuery();
			result = true;
		} catch (SQLException e) {
			System.out.println("MechanicData.addMechanic");
			System.out.println("Error al guardar Mechanic: "+e.getMessage());
		}
		return result;
	}

	public boolean deleteMechanic(int idMechanic) {
		boolean find = false;
		try {
			String query = "call auto_tech.delete_mechanic(?);";
			CallableStatement stmt = connection.prepareCall(query);
			stmt.setInt(1, idMechanic);
			stmt.execute();
			find = true;
		}catch(SQLException e) {
			System.out.println("MechanicData.deleteMechanic");
			System.out.println("Error al eliminar Mechanic: "+e.getMessage());
		}
		return find;
	}
	
	public boolean updateMechanic(int idMechanic, String name, String specialty, int phoneNumber, String email) {
		boolean result = false;
		try {
			String query = "call auto_tech.update_mechanic(?,?,?,?,?);";
			CallableStatement stmt = connection.prepareCall(query);
			stmt.setInt(1, idMechanic);
			stmt.setString(2, name);
			stmt.setString(3, specialty);
			stmt.setInt(4, phoneNumber);
			stmt.setString(5, email);
			stmt.execute();
			result = true;
		} catch (Exception e) {
			System.out.println("MechanicData.updateMechanic");
			System.out.println("Error al editar Mechanic: "+e.getMessage());
		}
		return result;
	}
	
	public Mechanic getMechanic(String idMechanic) {
		LinkedList<Mechanic> mechanics = getListMechanics();
		for(Mechanic tempMechanic : mechanics) {
			if(tempMechanic.getIdMechanic().equalsIgnoreCase(idMechanic)) {
				return tempMechanic;
			}
		}
		return null;
	}
}