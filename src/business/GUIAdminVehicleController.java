package business;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.ArrayList;

import data.ClientData;
import data.OrdersData;
import data.VehicleData;
import domain.Client;
import domain.Orders;
import domain.Vehicle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class GUIAdminVehicleController {
	@FXML
	private TextField tfLicensePlate;
	@FXML
	private TextField tfBrand;
	@FXML
	private TextField tfModel;
	@FXML
	private TextField tfOwner;
	@FXML
	private ComboBox<String> cbFuelType;
	@FXML
	private ComboBox<Integer> cbYear;
	@FXML
	private TableView tvVehicles;
	@FXML
	private Button btnEdit;
	@FXML
	private Button btnDelete;
	@FXML
	private Button btnReturn;
	@FXML
	private Button btnAddNewOrder;
	
	private TableColumn<Vehicle, String> tcLicensePlate;
	private TableColumn<Vehicle, String> tcBrand;
	private TableColumn<Vehicle, String> tcModel;
	private TableColumn<Vehicle, Integer> tcYear;
	private TableColumn<Vehicle, String> tcFuelType;
	private TableColumn<Vehicle, String> tcOwner;
	
	private ArrayList<Vehicle> vehicles = VehicleData.getList();
	private Vehicle vehicleSelected;
	
	private MyUtils utils;
	
	@FXML
	private void initialize() {
		this.utils = new MyUtils();
		initTableView();
		setDataVehicleOfTable();
	}
	
	private void setDataVehicleOfTable() {
	    if (vehicles != null) {

	        ObservableList<Vehicle> observable = FXCollections.observableArrayList(vehicles);
	        tvVehicles.setItems(observable);

	        tvVehicles.setOnMouseClicked(e -> {
	            vehicleSelected = (Vehicle) tvVehicles.getSelectionModel().getSelectedItem();
	            if(vehicleSelected != null) {
	                fillFields(vehicleSelected);
	            }
	        });
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
		
		this.tcOwner = new TableColumn<Vehicle, String>("Dueño");
		this.tcOwner.setCellValueFactory(new PropertyValueFactory<>("owner"));
		
		this.tvVehicles.getColumns().addAll(this.tcLicensePlate, this.tcBrand, this.tcModel, this.tcYear, this.tcFuelType, this.tcOwner);
		this.tvVehicles.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
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
	// Event Listener on Button[#btnEdit].onAction
	@FXML
	public void editVehicle(ActionEvent event) {
		
		if(selectVehicle()) return;//valida que se haya elegido un vehiculo en la lista para poder editarlo
	    Vehicle vehicleSelected = (Vehicle) tvVehicles.getSelectionModel().getSelectedItem();
	    if (vehicles != null && vehicleSelected != null) {

	        Vehicle vehicleUpdated = new Vehicle();

	        vehicleUpdated.setLicensePlate(tfLicensePlate.getText());
	        vehicleUpdated.setBrand(tfBrand.getText());
	        vehicleUpdated.setModel(tfModel.getText());
	        vehicleUpdated.setOwner(tfOwner.getText());
	        vehicleUpdated.setFuelType(cbFuelType.getValue().toString());
	        vehicleUpdated.setYear((int) cbYear.getValue());
	        vehicleUpdated.setOrder(vehicleSelected.getOrder());

	        VehicleData.editVehicle(vehicleUpdated);

	        LogicAlert.alertMessage("Vehículo modificado exitosamente");

	        vehicles = VehicleData.getList();
	        setDataVehicleOfTable();
	        clearData();
	    }
	}
	// Event Listener on Button[#btnAddNewOrder].onAction
	@FXML
	public void addNewOrder(ActionEvent event) {
		if(selectVehicle()) return;//valida que se haya elegido un vehiculo en la lista para poder agregarle una orden nueva
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUIAsignMoreOrders.fxml"));
			Parent root = loader.load();	
			
			GUIAsignMoreOrdersController controller = loader.getController();
			
			controller.setVehicle(vehicleSelected);
			
			utils.changeView(btnAddNewOrder, root);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// Event Listener on Button[#btnDelete].onAction
	@FXML
	public void deleteVehicle(ActionEvent event) {

	    if (selectVehicle()) return;

	    if (vehicleSelected != null) {

	        for (Orders order : vehicleSelected.getOrder()) {
	            if ("En proceso".equalsIgnoreCase(order.getOrderState())) {
	                LogicAlert.alertMessage("No se pueden eliminar vehículos con órdenes en proceso");
	                return;
	            }
	        }

	        VehicleData.deleteVehicle(vehicleSelected);
	        LogicAlert.alertMessage("Vehículo Eliminado Exitosamente");

	        vehicles = VehicleData.getList();
	        setDataVehicleOfTable();
	        clearData();
	    }
	}

	// Event Listener on Button[#btnReturn].onAction
	@FXML
	public void returnMenu(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUIPrincipal.fxml"));
			Parent root = loader.load();	
			utils.changeView(btnEdit, root);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void fillFields(Vehicle vehicle) {
	    tfLicensePlate.setText(vehicle.getLicensePlate());
	    tfBrand.setText(vehicle.getBrand());
	    tfModel.setText(vehicle.getModel());
	    tfOwner.setText(vehicle.getOwner());

	    cbFuelType.setValue(vehicle.getFuelType());
	    cbYear.setValue(vehicle.getYear());
	}
	
	private boolean selectVehicle() {
		if(tvVehicles.getSelectionModel().isEmpty()) {//valida que haya un vehiculo seleccionado en la lista
			LogicAlert.alertMessage("Debe seleccionar un Vehiculo en la Lista");
			return true;
		}
		return false;
	}
	
	private void clearData() {
		tfLicensePlate.clear();
		tfBrand.clear();
		tfModel.clear();
		tfOwner.clear();
		cbFuelType.getSelectionModel().clearSelection();
		cbYear.getSelectionModel().clearSelection();
	}

}
