package business;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import java.io.IOException;

import data.ClientData;
import data.MechanicData;
import data.OrdersData;
import data.ServicesData;
import data.VehicleData;
import javafx.event.ActionEvent;

public class GUIPrincipalController {
	@FXML
	private Button btnClient;
	@FXML
	private Button btnOrders;
	@FXML
	private Button btnMechanic;
	@FXML
	private Button btnService;
	@FXML
	private Button btnClientAdmin;
	@FXML
	private Button btnVehicles;
	@FXML
	private Button btnAdminOrders;
	@FXML
	private Button btnReport;
	private MyUtils utils;
	private MechanicData mechanicData;
	private ClientData clientData;
	private VehicleData vehicleData;
	private OrdersData orderData;
	private ServicesData serviceData;
	
	
	@FXML
	private void initialize() {
		this.utils = new MyUtils();
		this.mechanicData = new MechanicData();
		this.serviceData = new ServicesData();
		this.orderData = new OrdersData();
		this.clientData = new ClientData();
		this.vehicleData = new VehicleData();
	}

	// Event Listener on Button[#btnClient].onAction
	@FXML
	public void showGUIClient(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUIClients.fxml"));
			Parent root = loader.load();	
			utils.changeView(btnClient, root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// Event Listener on Button[#btnVehicleAdministration].onAction
	@FXML
	public void showGUIVehicles(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUIVehicles.fxml"));
			Parent root = loader.load();		
			utils.changeView(btnClient, root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// Event Listener on Button[#btnMechanic].onAction
	@FXML
	public void showGUIMechanic(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUIMechanics.fxml"));
			Parent root = loader.load();	
			utils.changeView(btnMechanic, root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// Event Listener on Button[#btnService].onAction
	@FXML
	public void showGUIService(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUIService.fxml"));
			Parent root = loader.load();	
			utils.changeView(btnService, root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// Event Listener on Button[#btnOrdersAdministration].onAction
	@FXML
	public void showGUIOrders(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUIOrders.fxml"));
			Parent root = loader.load();	
			utils.changeView(btnClient, root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void showGUIAdminOrders(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUIAdminOrders.fxml"));
			Parent root = loader.load();	
			utils.changeView(btnClient, root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
