package business;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import javafx.event.ActionEvent;

public class GUIClientController {
	@FXML
	private TextField tfId;
	@FXML
	private Button btnAddClient;
	@FXML
	private Button btnReturn;
	@FXML
	private TextField tfFullName;
	@FXML
	private TextField tfNumOfVehicles;
	@FXML
	private TextField tfPhoneNumber;
	@FXML
	private TextField tfEmail;
	@FXML
	private TextField tfDirection;
	private MyUtils utils;
	
	@FXML
	private void initialize() {
		this.utils = new MyUtils();
	}
	
	
	//Boton agregar Cliente, envia a la GUI de registro de vehiculo
	@FXML
	public void addClient(ActionEvent event) {
		utils.changeView(btnAddClient, "/presentation/GUIVehicleRegistration.fxml");
	}
	
	//Boton Cancelar
	@FXML
	public void returnPrincipalMenu(ActionEvent event) {
		utils.changeView(btnAddClient, "/presentation/GUIPrincipal.fxml");
	}
}
