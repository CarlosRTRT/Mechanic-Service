package business;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import data.ClientData;
import data.VehicleData;
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
    private int currentVehicleCount = 0;
	
	private Client client;
	private int numbOfVehicles;
	private ArrayList<Vehicle> vehicles = VehicleData.getList();

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
		
		if(validForm()) return;//validacion del formulario
		Vehicle vehicle = new Vehicle();
		
	    vehicle.setFuelType(fuelTypeSelected);
	    vehicle.setLicensePlate(tfLicensePlate.getText());
	    vehicle.setBrand(tfBrand.getText());
	    vehicle.setModel(tfModel.getText());
	    vehicle.setOwner(tfOwner.getText());
	    vehicle.setYear(yearSelected);
	    if(existsVehicle(vehicle.getLicensePlate())) {
			LogicAlert.alertMessage("La Placa ya existe");
			return;
		}
		
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUIOrders.fxml"));
			Parent root = loader.load();	
			GUIOrdersController ordersController = loader.getController();		
			ordersController.setClient(client);
			ordersController.setVehicle(vehicle);
			ordersController.setCurrentVehicleCount(currentVehicleCount + 1);
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
	public int getCurrentVehicleCount() {
		return currentVehicleCount;
	}
	public void setCurrentVehicleCount(int currentVehicleCount) {
		this.currentVehicleCount = currentVehicleCount;
	}
	
	private boolean validForm() {
		if(tfLicensePlate.getText().isBlank() ||
				tfBrand.getText().isBlank() || tfModel.getText().isBlank() || 
				tfOwner.getText().isBlank()) {//valida que no queden datos vacios
			LogicAlert.alertMessage("No dejar datos vacios");
			return true;
		}else if(cbFuelType.getSelectionModel().isEmpty()) {
			LogicAlert.alertMessage("Debe seleccionar un tipo de combustible");
			return true;
		}else if(cbYear.getSelectionModel().isEmpty()) {
			LogicAlert.alertMessage("Debe seleccionar un año");
			return true;
		}
		return false;
	}
	
	private boolean existsVehicle(String plateSearch) {
		for(Vehicle vehicleTemp : vehicles) {
			if(vehicleTemp.getLicensePlate().equalsIgnoreCase(plateSearch)) {
				return true;
			}
		}
		return false;
	}
}
