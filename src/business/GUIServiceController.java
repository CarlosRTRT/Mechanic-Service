package business;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.LinkedList;

import data.ServicesData;
import domain.Services;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

public class GUIServiceController {
	@FXML
	private TextField tfServiceCode;
	@FXML
	private TextField tfServiceName;
	@FXML
	private TextField tfDescription;
	@FXML
	private TextField tfBaseCost;
	@FXML
	private TextField tfEstimatedTime;
	@FXML
	private Button btnAddService;
	@FXML
	private Button btnEditService;
	@FXML
	private Button btnDeleteService;
	@FXML
	private TableView<Services> tvServices;
	@FXML
	private Button btnReturnMenu;
	
	private TableColumn<Services, Integer> tcServiceCode;
	private TableColumn<Services, String> tcServiceName;
	private TableColumn<Services, String> tcDescription;
	private TableColumn<Services, Integer> tcBaseCost;
	private TableColumn<Services, Integer> tcEstimatedTime;
	
	private MyUtils utils;
	private ServicesData serviceData;
	
	@FXML
	private void initialize() {
		this.serviceData = new ServicesData();
		this.utils = new MyUtils();
		
		initTableView();
		setDataTableServices();
	}
	
	// Event Listener on Button[#btnAddService].onAction
	@FXML
	public void addService(ActionEvent event) {	
		if(validForm()) return;
		int code = Integer.parseInt(tfServiceCode.getText());
		String name = tfServiceName.getText();
		String description = tfDescription.getText();
		int baseCost = Integer.parseInt(tfBaseCost.getText());
		int estimatedTime = Integer.parseInt(tfEstimatedTime.getText());
		Services service = new Services(0, code, name, description, baseCost, estimatedTime);
		if(existsService(service.getServiceCode())) {
			LogicAlert.alertMessage("El codigo de servicio ya existe");
			return;
		}
		if(serviceData.addService(service)) {
			LogicAlert.alertMessage("Servicio Agregado Exitosamente!!");
			clearForm();
			setDataTableServices();
		}
	}
	
	@FXML
	public void editService(ActionEvent event) {
		Services service = serviceSelectedTV();
		if(service == null) {
			LogicAlert.alertMessage("Debe Seleccionar un Servicio en la Tabla");
			return;
		}
		if(validForm()) return;
		int code = Integer.parseInt(tfServiceCode.getText());
		String name = tfServiceName.getText();
		String description = tfDescription.getText();
		int baseCost = Integer.parseInt(tfBaseCost.getText());
		int estimatedTime = Integer.parseInt(tfEstimatedTime.getText());
		if(serviceData.updateService(code, name, description, baseCost, estimatedTime)) {
			LogicAlert.alertMessage("Servicio Actualizado Exitosamente!!");
			clearForm();
			setDataTableServices();
		}
	}
	
	@FXML
	public void deleteService(ActionEvent event) {
		Services service = serviceSelectedTV();
		if(service == null) {
			LogicAlert.alertMessage("Debe Seleccionar un Servicio en la Tabla");
			return;
		}else if(serviceData.deleteService(service.getId())) {
			LogicAlert.alertMessage("Servicio Eliminado Correctamente!!");
			clearForm();
			setDataTableServices();
		}
	}
	
	private void initTableView() {
		this.tcServiceCode = new TableColumn<Services, Integer>("Codigo");
		this.tcServiceCode.setCellValueFactory(new PropertyValueFactory<>("serviceCode"));
		
		this.tcServiceName = new TableColumn<Services, String>("Nombre");
		this.tcServiceName.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
		
		this.tcDescription = new TableColumn<Services, String>("Descripcion");
		this.tcDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
		
		this.tcBaseCost = new TableColumn<Services, Integer>("Costo Base");
		this.tcBaseCost.setCellValueFactory(new PropertyValueFactory<>("baseCost"));
		
		this.tcEstimatedTime = new TableColumn<Services, Integer>("Tiempo Estimado(Horas)");
		this.tcEstimatedTime.setCellValueFactory(new PropertyValueFactory<>("estimatedTime"));
		
		this.tvServices.getColumns().addAll(this.tcServiceCode, this.tcServiceName, this.tcDescription, this.tcBaseCost, this.tcEstimatedTime);
		this.tvServices.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}
	
	private void setDataTableServices() {
		LinkedList<Services> services = serviceData.getListServicesActive();
		ObservableList<Services> observable = FXCollections.observableArrayList(services);
		tvServices.setItems(observable);
	}
	
	@FXML
	public void selectService(MouseEvent event) {
		Services service = serviceSelectedTV();
		if(service != null) {
			tfBaseCost.setText(String.valueOf(service.getBaseCost()));
			tfEstimatedTime.setText(String.valueOf(service.getEstimatedTime()));
			tfDescription.setText(service.getDescription());
			tfServiceName.setText(service.getServiceName());
			tfServiceCode.setText(String.valueOf(service.getServiceCode()));
		}
	}
	
	// Event Listener on Button[#btnShowListService].onAction
	@FXML
	public void showListService(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUIAdminService.fxml"));
			Parent root = loader.load();	
			utils.changeView(btnAddService, root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Event Listener on Button[#btnReturnMenu].onAction
	@FXML
	public void returnMenu(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUIPrincipal.fxml"));
			Parent root = loader.load();	
			utils.changeView(btnReturnMenu, root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private boolean validForm() {
		if(tfServiceName.getText().isBlank() || tfDescription.getText().isBlank()) {//valida que no queden datos vacios
			LogicAlert.alertMessage("No dejar datos vacios");
			return true;
		}else if(!tfServiceCode.getText().matches("\\d{4}")) {
			//valida que el codigo sean solo 4 numeros
			LogicAlert.alertMessage("El codigo deben ser 4 digitos numericos");
			return true;
		}else if(!tfBaseCost.getText().matches("\\d+")) {//valida que el costo base sean solo numeros
			LogicAlert.alertMessage("El costo base deben ser solo numeros");
			return true;
		}else if(!tfEstimatedTime.getText().matches("\\d+")) {//valida que el tiempo estimado sean solo numeros
			LogicAlert.alertMessage("El tiempo estimado deben ser solo numeros");
			return true;
		}
		return false;
	}
	
	private boolean existsService(int codeSearch) {
		LinkedList<Services> services = serviceData.getListServices();
		for(Services serviceTemp : services) {
			if(serviceTemp.getServiceCode() == codeSearch) {
				return true;
			}
		}
		return false;
	}
	
	private Services serviceSelectedTV() {
		Services tempService = this.tvServices.getSelectionModel().getSelectedItem();
		return tempService;
	}
	
	public void clearForm() {
		tfServiceCode.clear();
		tfServiceName.clear();
		tfEstimatedTime.clear();
		tfBaseCost.clear();
		tfDescription.clear();
	}
}