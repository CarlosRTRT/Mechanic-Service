package business;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.LinkedList;

import data.ClientData;
import data.VehicleData;
import domain.Client;
import domain.Vehicle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class GUIVehiclesController {
	@FXML
	private TextField tfLicensePlate;
	@FXML
	private TextField tfBrand;
	@FXML
	private TextField tfModel;
	@FXML
	private Label lbClient;
	@FXML
	private Button btnAddVehicle;
	@FXML
	private ComboBox<String> cbFuelType;
	@FXML
	private ComboBox<Integer> cbYear;
	@FXML
	private Button btnReturn;
	@FXML
	private ComboBox<Client> cbClients;
	@FXML
	private Button btnEditVehicle;
	@FXML
	private Button btnDeleteVehicle;
	@FXML
	private TableView<Vehicle> tvVehicles;
	
	private TableColumn<Vehicle, String> tcLicensePlate;
	private TableColumn<Vehicle, String> tcBrand;
	private TableColumn<Vehicle, String> tcModel;
	private TableColumn<Vehicle, Integer> tcYear;
	private TableColumn<Vehicle, String> tcFuelType;
	
	private MyUtils utils;
	private VehicleData vehicleData;
	private ClientData clientData;
	
	@FXML
	private void initialize() {
		this.utils = new MyUtils();
		this.vehicleData = new VehicleData();
		this.clientData = new ClientData();
		
		loadComboBox();
		initTableView();
		showVehicles();
	}

	// Event Listener on Button[#btnAddVehicle].onAction
	@FXML
	public void addVehicle(ActionEvent event) {
		if(validForm()) return;//validacion del formulario
		String plate = tfLicensePlate.getText();
		String brand = tfBrand.getText();
		String model = tfModel.getText();
		int year = cbYear.getSelectionModel().getSelectedItem();
		String fuelType = cbFuelType.getSelectionModel().getSelectedItem();
		Client client = cbClients.getSelectionModel().getSelectedItem();
		int idClient = client.getId();
		Vehicle vehicle = new Vehicle(0, plate, brand, model, year, fuelType, idClient);
	    if(existsVehicle(vehicle.getLicensePlate())) {
			LogicAlert.alertMessage("La Placa ya existe");
			return;
		}else if(vehicleData.addVehicle(vehicle)) {
			LogicAlert.alertMessage("Vehiculo Agregado Exitosamente!!");
			showVehicles();
			clearForm();
		}
	}
	
	public void loadComboBox() {
		String [] fuelTypes = {"Diésel", "Gasolina", "Eléctrico", "Híbrido"};
		cbFuelType.getItems().addAll(fuelTypes);
		
		for(int i = 2026; i > 1959; i--) {
			cbYear.getItems().addAll(i);
		}
		
		LinkedList<Client> clients = clientData.getListClientsActive();
		cbClients.getItems().addAll(clients);
	}
	
	// Event Listener on Button[#btnReturn].onAction
	@FXML
	public void returnMenu(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUIPrincipal.fxml"));
			Parent root = loader.load();		
			utils.changeView(btnAddVehicle, root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// Event Listener on Button[#btnEditVehicle].onAction
	@FXML
	public void editVehicle(ActionEvent event) {
		Vehicle vehicle = vehicleSelectedTV();
		if(vehicle == null) {
			LogicAlert.alertMessage("Debe Seleccionar un Vehiculo en la Tabla");
			return;
		}
		if(validForm())return;
		String newBrand = tfBrand.getText();
		String newModel = tfModel.getText();
		int newYear = cbYear.getSelectionModel().getSelectedItem();
		String newFuelType = cbFuelType.getSelectionModel().getSelectedItem();
		if(vehicleData.updateVehicle(vehicle, newBrand, newModel, newYear, newFuelType)) {
			LogicAlert.alertMessage("Vehiculo Actualizado Correctamente");
			btnReturn.setText("Volver al Menú Principal");
			tfLicensePlate.setEditable(true);
			cbClients.setMouseTransparent(false);
			cbClients.setFocusTraversable(true);
			clearForm();
			showVehicles();
		}
	}
	// Event Listener on Button[#btnDeleteVehicle].onAction
	@FXML
	public void deleteVehicle(ActionEvent event) {
		Vehicle vehicle = vehicleSelectedTV();
		if(vehicle == null) {
			LogicAlert.alertMessage("Debe Seleccionar un Vehiculo en la Tabla");
			return;
		}else if(vehicleData.deleteVehicle(vehicle)) {
			LogicAlert.alertMessage("Vehiculo Eliminado Correctamente");
			btnReturn.setText("Volver al Menú Principal");
			tfLicensePlate.setEditable(true);
			cbClients.setMouseTransparent(false);
			cbClients.setFocusTraversable(true);
			clearForm();
			showVehicles();
		}
	}
	// Event Listener on TableView[#tvClients].onMouseClicked
	@FXML
	public void vehicleSelected(MouseEvent event) {
		Vehicle vehicle = vehicleSelectedTV();
		if(vehicle != null) {
			tfLicensePlate.setEditable(false);
			btnReturn.setText("Cancelar");
			cbClients.setMouseTransparent(true);
			cbClients.setFocusTraversable(false);
			tfLicensePlate.setText(vehicle.getLicensePlate());
			tfBrand.setText(vehicle.getBrand());
			tfModel.setText(vehicle.getModel());
			cbFuelType.setValue(vehicle.getFuelType());
			cbYear.setValue(vehicle.getYear());
			int idClient = vehicle.getOwner();
			Client client = searchForClientVehicle(idClient);
			if(client != null) cbClients.setValue(client);
		}
	}
	
	private void initTableView() {
		this.tcLicensePlate = new TableColumn<Vehicle, String>("Placa");
		this.tcLicensePlate.setCellValueFactory(new PropertyValueFactory<>("licensePlate"));
		
		this.tcBrand = new TableColumn<Vehicle, String>("Marca");
		this.tcBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
		
		this.tcModel = new TableColumn<Vehicle, String>("Modelo");
		this.tcModel.setCellValueFactory(new PropertyValueFactory<>("model"));
		
		this.tcYear = new TableColumn<Vehicle, Integer>("Año");
		this.tcYear.setCellValueFactory(new PropertyValueFactory<>("year"));
		
		this.tcFuelType = new TableColumn<Vehicle, String>("Tipo Combustible");
		this.tcFuelType.setCellValueFactory(new PropertyValueFactory<>("fuelType"));
		
		this.tvVehicles.getColumns().addAll(this.tcLicensePlate, this.tcBrand, this.tcModel, this.tcYear, this.tcFuelType);
		this.tvVehicles.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}
	
	private void showVehicles() {
		LinkedList<Vehicle> vehicles = vehicleData.getListVehiclesActive();
		ObservableList<Vehicle> observable = FXCollections.observableArrayList(vehicles);
		tvVehicles.setItems(observable);
	}
	
	private Vehicle vehicleSelectedTV() {
		Vehicle tempVehicle = this.tvVehicles.getSelectionModel().getSelectedItem();
		return tempVehicle;
	}
	
	private Client searchForClientVehicle(int idClient) {
		LinkedList<Client> clients = clientData.getListAllClients();
		for(Client tempClient : clients) {
			if(tempClient.getId() == idClient) {
				if(tempClient.getState().equalsIgnoreCase("Activo")) {
					this.lbClient.setText("CLIENTE ACTIVO");
				}
				if(tempClient.getState().equalsIgnoreCase("Inactivo")) {
					this.lbClient.setText("⚠CLIENTE INACTIVO⚠");
				}
				return tempClient;
			}
		}
		return null;
	}
	
	private boolean validForm() {
		if(tfLicensePlate.getText().isBlank() ||
				tfBrand.getText().isBlank() || tfModel.getText().isBlank()) {//valida que no queden datos vacios
			LogicAlert.alertMessage("No Dejar Datos Vacios");
			return true;
		}else if(cbFuelType.getSelectionModel().isEmpty()) {
			LogicAlert.alertMessage("Debe Seleccionar un Tipo de Combustible");
			return true;
		}else if(cbYear.getSelectionModel().isEmpty()) {
			LogicAlert.alertMessage("Debe Seleccionar un Año");
			return true;
		}else if(cbClients.getValue() == null) {
			LogicAlert.alertMessage("Debe Seleccionar un Cliente");
			return true;
		}
		return false;
	}
	
	private boolean existsVehicle(String plateSearch) {
		LinkedList<Vehicle> vehicles = vehicleData.getListVehicles();
		for(Vehicle vehicleTemp : vehicles) {
			if(vehicleTemp.getLicensePlate().equalsIgnoreCase(plateSearch)) {
				return true;
			}
		}
		return false;
	}
	
	private void clearForm() {
		tfLicensePlate.clear();
		tfBrand.clear();
		tfModel.clear();
		cbFuelType.setValue(null);
		cbFuelType.setPromptText("Seleccione un tipo de combustible");
		cbYear.setValue(null);
		cbYear.setPromptText("Seleccione el año:");
		cbClients.setValue(null);
		cbClients.setPromptText("Seleccionar Cliente:");
	}
}
