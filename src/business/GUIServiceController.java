package business;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;

import data.ServicesData;
import domain.Services;
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
	private Button btnShowListService;
	@FXML
	private Button btnReturnMenu;
	
	private MyUtils utils;
	private ArrayList<Services> services = ServicesData.getList();
	
	@FXML
	private void initialize() {
		this.utils = new MyUtils();
	}
	
	// Event Listener on Button[#btnAddService].onAction
	@FXML
	public void addService(ActionEvent event) {	
		if(validForm()) return;
		Services service = new Services();
		
		service.setServiceCode(Integer.parseInt(tfServiceCode.getText()));
		service.setServiceName(tfServiceName.getText());
		service.setDescription(tfDescription.getText());
		service.setBaseCost(Integer.parseInt(tfBaseCost.getText()));
		service.setEstimatedTime(Integer.parseInt(tfEstimatedTime.getText()));
		
		if(existsService(service.getServiceCode())) {
			LogicAlert.alertMessage("El codigo de servicio ya existe");
			return;
		}
		ServicesData.saveService(service);

		tfServiceCode.clear();
		tfServiceName.clear();
		tfDescription.clear();
		tfBaseCost.clear();
		tfEstimatedTime.clear();
		
		LogicAlert.alertMessage("Servicio creado exitosamente!!");
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
		for(Services serviceTemp : services) {
			if(serviceTemp.getServiceCode() == codeSearch) {
				return true;
			}
		}
		return false;
	}
}