package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	private final static String DATABASE = "auto_tech";
	private final static String USER = "root";
	private final static String PASSWORD = "";
	private final static String HOST = "localhost";
	private final static int PORT = 3306;
	private final static String URL = "jdbc:mysql://"+HOST+":"+PORT+"/"+DATABASE;
	
	private static Connection connection;
	
	public static Connection getConnection() {
		
		//singleton: Si la conexion esta abierta, la retorna sino la inicializa
		if(connection != null) return connection;
	
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("No se encontro la clase com.mysql.cj.jdbc.Driver "+e.getMessage());
		}
		
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println("Se establecio conexion a BD: " + DATABASE);
		} catch (SQLException e) {
			System.err.println("Error al establecer conexion a BD");

		}
		return connection;
	}
}