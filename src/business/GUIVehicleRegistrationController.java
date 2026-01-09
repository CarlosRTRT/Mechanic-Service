package business;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

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

	// Event Listener on Button[#btnRegister].onAction
	@FXML
	public void vehicleRegistration(ActionEvent event) {
		createVehicle(fuelTypeSelected, yearSelected);
		utils.changeView(btnCancel, "/presentation/GUIOrders.fxml");
	}
	// Event Listener on Button[#btnCancel].onAction
	@FXML
	public void cancelRegistration(ActionEvent event) {
		utils.changeView(btnRegister, "/presentation/GUIPrincipal.fxml");
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
		vehicle.setlicensePlate(tfLicensePlate.getText());
		vehicle.setBrand(tfBrand.getText());
		vehicle.setModel(tfModel.getText());
		vehicle.setOwner(tfOwner.getText());
		vehicle.setYear(yearSelected);
		
		
	}
}
