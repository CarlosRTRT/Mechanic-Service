package business;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import data.MechanicData;
import data.OrdersData;
import data.ServicesData;
import data.VehicleData;
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

public class GUIOrdersController {
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
	private Button btnReturn;
	@FXML
	private Button btnEditOrder;
	@FXML
	private Button btnDeleteOrder;
	@FXML
	private Button btnAddPhotos;
	
	private MyUtils utils;
	private ServicesData serviceData;
	private MechanicData mechanicData;
	private VehicleData vehicleData;
	private OrdersData orderData;
	
	private int price;
    private boolean isProcessingServiceSelection = false;

	ArrayList<Services> servicesOnTable;
	
	// Columnas de Vehículo
	private TableColumn<Vehicle, String> tcLicensePlate;
	private TableColumn<Vehicle, String> tcBrand;
	private TableColumn<Vehicle, String> tcModel;
	private TableColumn<Vehicle, Integer> tcYear;
	private TableColumn<Vehicle, String> tcFuelType;
	private TableColumn<Vehicle, String> tcState;
	
	// Columnas de servicios
	private TableColumn<Services, String> tcServiceCode;
	private TableColumn<Services, String> tcServiceName;
	private TableColumn<Services, String> tcDescription;
	private TableColumn<Services, Integer> tcBaseCost;
	private TableColumn<Services, Integer> tcEstimatedTime;
	private int numero;
	
	@FXML
	private void initialize() {	
		this.orderData = new OrdersData();
		this.vehicleData = new VehicleData();
		this.serviceData = new ServicesData();
		this.mechanicData = new MechanicData();
		this.servicesOnTable = new ArrayList<Services>();
		this.utils = new MyUtils();
		tfOrderState.setText("Por registrar");
		tfTotalPrice.setText(String.valueOf("$"+price));
		Random random = new Random();
		numero = random.nextInt(1000);
		tfNumOfOrder.setText("ORDER-"+String.valueOf(numero));
		fillCbServices();
		initTableViewVehicle();
		initTableViewServices();
		setDataTableViewServices();
		setCbMechanics();
		setDataTableOfVehicle();
		cbServiceSelected.setOnAction(e ->{
	        if (!isProcessingServiceSelection) { 
	            addServiceToTable();
	        }
		});
	}

	private void setCbMechanics() {
		LinkedList<Mechanic> mechanics = mechanicData.getListMechanics();
		for(Mechanic mechanicsTemp : mechanics) {
			cbMechanicSelected.getItems().addAll(mechanicsTemp);
		}
	}

	private void fillCbServices() {
		LinkedList<Services> services = serviceData.getListServicesActive();
		for(Services serviceTemp : services) {
			cbServiceSelected.getItems().add(serviceTemp.getServiceName());
		}
	}
	
	public void addServiceToTable() {
		LinkedList<Services> services = serviceData.getListServicesActive();
		String serviceSelected = cbServiceSelected.getSelectionModel().getSelectedItem();
		
	    if (serviceSelected == null || serviceSelected.isEmpty()) {
	        return; 
	    }
		
	    isProcessingServiceSelection = true;
	    
		for(Services serviceTemp : services) {
			if(serviceTemp.getServiceName().equals(serviceSelected)) {
				servicesOnTable.add(serviceTemp);
				price += serviceTemp.getBaseCost();
				tfTotalPrice.setText(String.valueOf(price));

                final String serviceName = serviceSelected;
                Platform.runLater(() -> {
                    cbServiceSelected.getItems().remove(serviceName);
                    cbServiceSelected.getSelectionModel().clearSelection();
                    isProcessingServiceSelection = false;
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
		price -= serviceSelected.getBaseCost();
		tfTotalPrice.setText(String.valueOf(price));
		setDataTableViewServices();
	}

	private void setDataTableViewServices() {
		ObservableList<Services> observable = FXCollections.observableArrayList(servicesOnTable);
		tvServices.setItems(observable);
	}

	private void initTableViewServices() {
		tcServiceCode = new TableColumn<Services, String>("CODIGO");
		tcServiceCode.setCellValueFactory(new PropertyValueFactory<>("serviceCode"));
		tcServiceName = new TableColumn<Services, String>("NOMBRE");
		tcServiceName.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
		tcDescription = new TableColumn<Services, String>("DESCRIPCIÓN");
		tcDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
		tcBaseCost = new TableColumn<Services, Integer>("COSTO");
		tcBaseCost.setCellValueFactory(new PropertyValueFactory<>("baseCost"));
		tcEstimatedTime = new TableColumn<Services, Integer>("TIEMPO ESTIMADO");
		tcEstimatedTime.setCellValueFactory(new PropertyValueFactory<>("estimatedTime"));	
		
		tvServices.getColumns().addAll(tcServiceCode, tcServiceName, tcDescription, tcBaseCost, tcEstimatedTime);
		tvServices.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}

	private void initTableViewVehicle() {
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
	    
	    tcState = new TableColumn<Vehicle, String>("ESTADO");
	    tcState.setCellValueFactory(new PropertyValueFactory<>("state"));
	    tcState.setMinWidth(140);
	    
	    tvVehicle.getColumns().addAll(tcLicensePlate, tcBrand, tcModel, tcYear, tcFuelType, tcState);
	    tvVehicle.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}
	
	private void setDataTableOfVehicle() {
		LinkedList<Vehicle> vehicles = vehicleData.getListVehiclesActive();
		ObservableList<Vehicle> observable = FXCollections.observableArrayList(vehicles);
		tvVehicle.setItems(observable);
	}

	// Event Listener on Button[#btnRemoveService].onAction
	@FXML
	public void removeService(ActionEvent event) {
		removeServiceOnTable();
	}
	
	// Event Listener on Button[#btnRegisterOrder].onAction
	@FXML
	public void addOrder(ActionEvent event) {
		if(validForm()) {
			return;
		} else {
			Mechanic mechanic = cbMechanicSelected.getSelectionModel().getSelectedItem();
			Vehicle vehicle = this.tvVehicle.getSelectionModel().getSelectedItem();
			int orderNumber = numero;
			String orderState = "Registrada";
			LocalDate creationDate = dpCreationDate.getValue();
			String observations = tfOservation.getText();
			int totalPrice = Integer.parseInt(tfTotalPrice.getText());
			int idVehicle = vehicle.getId();
			int idMechanic = mechanic.getId();
			Orders order = new Orders(0, orderNumber, creationDate, orderState, idMechanic, observations,
					totalPrice, idVehicle);
			int idOrder = orderData.addOrders(order);
			for(Services tempService : servicesOnTable) {
				serviceData.addServiceToOrder(idOrder, tempService.getId());
			}
			LogicAlert.alertMessage("Orden Agregada Correctamente!!");
			clearForm();
			setDataTableViewServices();
			fillCbServices();
		}
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUIPrincipal.fxml"));
			Parent root = loader.load();	
			utils.changeView(btnReturn, root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void addPhotos(ActionEvent event) {
		Vehicle selectedVehicle = tvVehicle.getSelectionModel().getSelectedItem();
		
		if (selectedVehicle == null) {
			LogicAlert.alertMessage("Debe seleccionar un vehiculo");
			return;
		}
		
		String licensePlate = selectedVehicle.getLicensePlate();
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Agregar Fotos");
		
		fileChooser.getExtensionFilters().addAll(
			new FileChooser.ExtensionFilter("Imagenes", "*.png", "*.jpg", "*.jpeg", "*.gif"),
			new FileChooser.ExtensionFilter("PNG", "*.png"),
			new FileChooser.ExtensionFilter("JPEG", "*.jpg", "*.jpeg"),
			new FileChooser.ExtensionFilter("Todos", "*.*")
		);
		
		List<File> files = fileChooser.showOpenMultipleDialog(btnAddPhotos.getScene().getWindow());
		
		if (files != null && !files.isEmpty()) {
			uploadImages(licensePlate, files);
		}
	}
	
	private void uploadImages(String licensePlate, List<File> files) {
		try {
			ClientAdminAutoTech client = getAdminClient();
			int uploadedCount = 0;
			
			for (File imageFile : files) {
				if (imageFile.length() > 5 * 1024 * 1024) {
					LogicAlert.alertMessage("Imagen muy grande: " + imageFile.getName());
					continue;
				}
				client.uploadVehicleImage(licensePlate, imageFile);
				uploadedCount++;
			}
			
			if (uploadedCount > 0) {
				LogicAlert.alertMessage("Se subieron " + uploadedCount + " imagen(es)");
			}
			
		} catch (Exception e) {
			LogicAlert.alertMessage("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	private ClientAdminAutoTech getAdminClient() {
		return Main.getAdminClient();
	}
	
	// Event Listener on Button[#btnCancel].onAction
	@FXML
	public void returnMenu(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUIPrincipal.fxml"));
			Parent root = loader.load();	
			utils.changeView(btnReturn, root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean validForm() {
		if(dpCreationDate.getValue() == null) {
			LogicAlert.alertMessage("Debe seleccionar una fecha");
			return true;
		} else if(cbMechanicSelected.getSelectionModel().isEmpty()) {
			LogicAlert.alertMessage("Debe seleccionar un mecánico");
			return true;
		} else if(tfOservation.getText().isBlank()) {
			LogicAlert.alertMessage("Debe dejar una observación");
			return true;
		} else if(tvServices.getItems().isEmpty()) {
			LogicAlert.alertMessage("Debe elegirse al menos un servicio");
			return true;
		} else if(dpCreationDate.getValue().isAfter(LocalDate.now())) {
			LogicAlert.alertMessage("La fecha no puede ser mayor al día actual");
			return true;
		} else if(tvVehicle.getSelectionModel().isEmpty()) {
			LogicAlert.alertMessage("Debe Seleccionar un Vehículo en la Tabla");
			return true;
		}
		return false;
	}
	
	private void clearForm() {
		tfNumOfOrder.clear();
		tfOservation.clear();
		tfTotalPrice.clear();
		cbMechanicSelected.setValue(null);
		cbServiceSelected.setValue(null);
		servicesOnTable.clear();
		tvVehicle.getSelectionModel().clearSelection();
	}
}