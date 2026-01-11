package business;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import data.ClientData;
import data.MechanicData;
import data.ServicesData;
import data.VehicleData;
import domain.Client;
import domain.Mechanic;
import domain.Orders;
import domain.Services;
import domain.Vehicle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.ComboBox;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class GUIAsignMoreOrdersController {
	@FXML
	private TextField tfNumOfOrder;
	@FXML
	private DatePicker dpCreationDate;
	@FXML
	private TextField tfOrderState;
	@FXML
	private ComboBox<Mechanic> cbMechanicSelected;
	@FXML
	private TableView<Vehicle> tvVehicle;
	@FXML
	private TableView<Services> tvServices;
	@FXML
	private ComboBox<String> cbServiceSelected;
	@FXML
	private TextField tfTotalPrice;
	@FXML
	private Button btnRemoveService;
	@FXML
	private TextField tfOservation;
	@FXML
	private Button btnRegisterOrder;
	@FXML
	private Button btnCancel;
	
	private MyUtils utils;
	
	private Client client;
	private Vehicle vehicle;
	private int numbOfVehicles;

	ArrayList<Services> servicesOnTable;
	private ArrayList<Services> services;
	//columnas de Vehiculo
	
	private TableColumn<Vehicle, String> tcLicensePlate;
	private TableColumn<Vehicle, String> tcBrand;
	private TableColumn<Vehicle, String> tcModel;
	private TableColumn<Vehicle, Integer> tcYear;
	private TableColumn<Vehicle, String> tcFuelType;
	private TableColumn<Vehicle, String> tcOwner;
	
	//columnas de servicios
	
	private TableColumn<Services, String> tcServiceCode;
	private TableColumn<Services, String> tcServiceName;
	private TableColumn<Services, String> tcDescription;
	private TableColumn<Services, Integer> tcBaseCost;
	private TableColumn<Services, Integer> tcEstimatedTime;
	
	@FXML
	private void initialize() {	
		this.services = ServicesData.getList();
		this.servicesOnTable = new ArrayList<Services>();
		this.utils = new MyUtils();
		tfOrderState.setText("Por registrar");
		fillCbMechanics();
		fillCbServices();
		initTableViewVehicle();
		initTableViewServices();
		setDataTableViewServices();
		setCbMechanics();
		
		cbServiceSelected.setOnAction(e ->{
			addServiceToTable();
		});
		
	}

	private void setCbMechanics() {
		ArrayList<Mechanic> mechanics = MechanicData.getList();
		
		for(Mechanic mechanicsTemp : mechanics) {
			cbMechanicSelected.getItems().addAll(mechanicsTemp);
		}

	}

	private void fillCbServices() {
		for(Services serviceTemp : services) {
			cbServiceSelected.getItems().add(serviceTemp.getServiceName());
		}
		

	}
	
	public void addServiceToTable() {
		String serviceSelected = cbServiceSelected.getSelectionModel().getSelectedItem();
		
	    if (serviceSelected == null || serviceSelected.isEmpty()) {
	        System.out.println("Selección vacía, ignorando...");
	        return; // Salir si no hay selección válida
	    }
		
		for(Services serviceTemp : services) {
			if(serviceTemp.getServiceName().equals(serviceSelected)) {
				servicesOnTable.add(serviceTemp);
				
                final String serviceName = serviceSelected;
                Platform.runLater(() -> {
                    cbServiceSelected.getItems().remove(serviceName);
                    cbServiceSelected.getSelectionModel().clearSelection();
                });
				
                setDataTableViewServices();
                break;
			}
		}
	}
	
	public void removeServiceOnTable() {
		Services serviceSelected = tvServices.getSelectionModel().getSelectedItem();
		
	    if (serviceSelected == null) {
	        return;
	    }
	    
		cbServiceSelected.getItems().add(serviceSelected.getServiceName());
		
		for(int i = 0; i < servicesOnTable.size(); i++) {
			if(servicesOnTable.get(i).equals(serviceSelected)) {
				servicesOnTable.remove(serviceSelected);
				break;
			}
		}
		
		setDataTableViewServices();
	}

	private void fillCbMechanics() {
		// TODO Auto-generated method stub
		
	}

	private void setDataTableViewServices() {
		
		ObservableList<Services> observable = FXCollections.observableArrayList(servicesOnTable);
		tvServices.setItems(observable);
	}

	private void initTableViewServices() {
		tcServiceCode = new TableColumn<Services, String>("Codigo de servicio");
		tcServiceCode.setCellValueFactory(new PropertyValueFactory<>("serviceCode"));
		tcServiceName = new TableColumn<Services, String>("Nombre");
		tcServiceName.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
		tcDescription = new TableColumn<Services, String>("Descripcion");
		tcDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
		tcBaseCost = new TableColumn<Services, Integer>("Costo base");
		tcBaseCost.setCellValueFactory(new PropertyValueFactory<>("baseCost"));
		tcEstimatedTime = new TableColumn<Services, Integer>("Tiempo estimado");
		tcEstimatedTime.setCellValueFactory(new PropertyValueFactory<>("estimatedTime"));	
		
		tvServices.getColumns().addAll(tcServiceCode, tcServiceName, tcDescription, tcBaseCost, tcEstimatedTime);
		tvServices.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}

	private void initTableViewVehicle() {
	    System.out.println("Inicializando tabla de vehículos");
	    
	    tcLicensePlate = new TableColumn<Vehicle, String>("Placa");
	    tcLicensePlate.setCellValueFactory(new PropertyValueFactory<>("licensePlate"));
	    tcLicensePlate.setMinWidth(100);
	    
	    tcBrand = new TableColumn<Vehicle, String>("Marca");
	    tcBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
	    tcBrand.setMinWidth(100);
	    
	    tcModel = new TableColumn<Vehicle, String>("Modelo");
	    tcModel.setCellValueFactory(new PropertyValueFactory<>("model"));
	    tcModel.setMinWidth(100);
	    
	    tcYear = new TableColumn<Vehicle, Integer>("Año");
	    tcYear.setCellValueFactory(new PropertyValueFactory<>("year"));
	    tcYear.setMinWidth(80);
	    
	    tcFuelType = new TableColumn<Vehicle, String>("Tipo de combustible");
	    tcFuelType.setCellValueFactory(new PropertyValueFactory<>("fuelType"));
	    tcFuelType.setMinWidth(140);
	    
	    tcOwner = new TableColumn<Vehicle, String>("Propietario");
	    tcOwner.setCellValueFactory(new PropertyValueFactory<>("owner"));
	    tcOwner.setMinWidth(100);
	    
	    tvVehicle.getColumns().clear(); // Limpiar columnas existentes primero
	    tvVehicle.getColumns().addAll(tcLicensePlate, tcBrand, tcModel, tcYear, tcFuelType, tcOwner);
	    tvVehicle.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	    
	    System.out.println("Columnas agregadas: " + tvVehicle.getColumns().size());
	    System.out.println("TableView: " + tvVehicle);
	}
	private void setDataTableOfVehicle() {
	    System.out.println("setDataTableOfVehicle llamado");
	    if (vehicle != null) {
	        System.out.println("Vehículo no es null: " + vehicle.toString());
	        ObservableList<Vehicle> observable = FXCollections.observableArrayList(vehicle);
	        System.out.println("Items en observable: " + observable.size());
	        tvVehicle.setItems(observable);
	        System.out.println("Items asignados a la tabla");
	    } else {
	        System.out.println("ERROR: vehicle es NULL");
	    }
	}

	// Event Listener on Button[#btnRemoveService].onAction
	@FXML
	public void removeService(ActionEvent event) {
		removeServiceOnTable();
	}
	// Event Listener on Button[#btnRegisterOrder].onAction
	@FXML
	public void addOrder(ActionEvent event) {

		
		
	}
	// Event Listener on Button[#btnCancel].onAction
	@FXML
	public void cancelRegister(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUIPrincipal.fxml"));
			Parent root = loader.load();	
			utils.changeView(btnCancel, root);
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

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
	    this.vehicle = vehicle;
	    System.out.println("setVehicle llamado con: " + (vehicle != null ? vehicle.toString() : "NULL"));
	    setDataTableOfVehicle();
	}

	public int getNumbOfVehicles() {
		return numbOfVehicles;
	}

	public void setNumbOfVehicles(int numbOfVehicles) {
		this.numbOfVehicles = numbOfVehicles;
	}
}