package business;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
	
	private static ClientAdminAutoTech adminClient;
	private static final String SERVER_IP = "localhost";
	private static final int SERVER_PORT = 5000;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			System.out.println("Conectando al servidor...");
			adminClient = new ClientAdminAutoTech(SERVER_IP, SERVER_PORT, null);
			Thread.sleep(500);
			System.out.println("Conexion establecida");
			
			Parent root = FXMLLoader.load(getClass().getResource("/presentation/GUIPrincipal.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("AutoTech - Sistema de Gestion");
			primaryStage.setResizable(false);
			primaryStage.show();
			System.out.println("Interfaz cargada");
			
		} catch(Exception e) {
			System.err.println("Error al iniciar:");
			e.printStackTrace();
			
			javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
				javafx.scene.control.Alert.AlertType.ERROR
			);
			alert.setTitle("Error de Conexion");
			alert.setHeaderText("No se pudo conectar con el servidor");
			alert.setContentText("Verifique que el servidor este en " + SERVER_IP + ":" + SERVER_PORT);
			alert.showAndWait();
			System.exit(1);
		}
	}
	
	public static ClientAdminAutoTech getAdminClient() {
		if (adminClient == null) {
			System.err.println("Cliente no inicializado");
			throw new IllegalStateException("El cliente no ha sido inicializado");
		}
		return adminClient;
	}
	
	public static boolean isClientConnected() {
		return adminClient != null;
	}
	
	public static void reconnectClient() {
		try {
			System.out.println("Intentando reconectar...");
			adminClient = new ClientAdminAutoTech(SERVER_IP, SERVER_PORT, null);
			System.out.println("Reconexion exitosa");
		} catch (Exception e) {
			System.err.println("Error al reconectar: " + e.getMessage());
		}
	}
	
	@Override
	public void stop() {
		System.out.println("Cerrando aplicacion...");
		if (adminClient != null) {
			adminClient.disconnect();
		}
		System.out.println("Aplicacion cerrada");
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}