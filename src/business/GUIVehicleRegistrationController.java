package business;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import domain.Client;
import domain.Vehicle;
import javafx.event.ActionEvent;

import javafx.scene.control.ComboBox;

public class GUIVehicleRegistrationController implements Initializable {
	@FXML
	private TextField tfLicensePlate;
	@FXML
	private TextField tfBrand;
	@FXML
	private TextField tfModel;
	@FXML
	private TextField tfOwner;
	@FXML
	private Button btnRegister;
	@FXML
	private Button btnCancel;
	@FXML
	private ComboBox<String> cbFuelType;
	@FXML
	private ComboBox<Integer> cbYear;
	
	private String fuelTypeSelected;
	private int yearSelected;
	private MyUtils utils;
	
	private Client client;
	private int numbOfVehicles;

	// Event Listener on Button[#btnRegister].onAction
	@FXML
	public void vehicleRegistration(ActionEvent event) {
		createVehicle(fuelTypeSelected, yearSelected);
		
	}
	// Event Listener on Button[#btnCancel].onAction
	@FXML
	public void cancelRegistration(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUIPrincipal.fxml"));
			Parent root = loader.load();	
			utils.changeView(btnRegister, root);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// Event Listener on ComboBox[#cbFuelType].onAction
	@FXML
	public void loadFuels(ActionEvent event) {
		String [] fuelTypes = {"Diesel", "Gasolina", "Electrico", "Hibrido"};
		cbFuelType.getItems().addAll(fuelTypes);
	}
	// Event Listener on ComboBox[#cbYear].onAction
	@FXML
	public void loadYears(ActionEvent event) {
		for(int i = 2026; i > 1959; i--) {
			cbYear.getItems().addAll(i);
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		this.utils = new MyUtils();
		loadFuels(null);
		loadYears(null);
		
		cbFuelType.setOnAction(event ->{
			fuelTypeSelected = cbFuelType.getSelectionModel().getSelectedItem().toString();
		});

		cbYear.setOnAction(event -> {
			yearSelected = cbYear.getSelectionModel().getSelectedItem();
		});	
		
	}
	

	
	public void createVehicle(String fuelTypeSelected, int yearSelected) {
		Vehicle vehicle = new Vehicle();
		
	    vehicle.setFuelType(fuelTypeSelected);
	    vehicle.setLicensePlate(tfLicensePlate.getText());
	    vehicle.setBrand(tfBrand.getText());
	    vehicle.setModel(tfModel.getText());
	    vehicle.setOwner(tfOwner.getText());
	    vehicle.setYear(yearSelected);
		
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUIOrders.fxml"));
			Parent root = loader.load();	
			GUIOrdersController ordersController = loader.getController();		
			ordersController.setClient(client);
			ordersController.setVehicle(vehicle);
			ordersController.setNumbOfVehicles(numbOfVehicles);
		
			utils.changeView(btnRegister, root);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		

		
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public int getNumbOfVehicles() {
		return numbOfVehicles;
	}
	public void setNumbOfVehicles(int numbOfVehicles) {
		this.numbOfVehicles = numbOfVehicles;
	}
}
