package business;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

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
	
	@FXML
	private void initialize() {
		this.utils = new MyUtils();
	}
	
	// Event Listener on Button[#btnAddService].onAction
	@FXML
	public void addService(ActionEvent event) {		
		Services service = new Services();
		
		service.setServiceCode(Integer.parseInt(tfServiceCode.getText()));
		service.setServiceName(tfServiceName.getText());
		service.setDescription(tfDescription.getText());
		service.setBaseCost(Integer.parseInt(tfBaseCost.getText()));
		service.setEstimatedTime(Integer.parseInt(tfEstimatedTime.getText()));
		

		
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
}