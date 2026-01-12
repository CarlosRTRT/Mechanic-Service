package business;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;

import data.ClientData;
import data.ServicesData;
import domain.Client;
import domain.Services;
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
	private ArrayList<Client> clients = ClientData.getList();
	
	@FXML
	private void initialize() {
		this.utils = new MyUtils();
	}
	
	//Boton agregar Cliente, envia a la GUI de registro de vehiculo
	@FXML
	public void addClient(ActionEvent event) {
		
		if(validForm())return;//Valida los datos del formulario
		Client client = new Client();
		client.setDirection(tfDirection.getText());
		client.setEmail(tfEmail.getText());
		client.setFullName(tfFullName.getText());
		client.setId(tfId.getText());
		client.setPhoneNumber(Integer.parseInt(tfPhoneNumber.getText()));
		
		int numbOfVehicles = Integer.parseInt(tfNumOfVehicles.getText());
		if(existsClient(client.getId())) {
			LogicAlert.alertMessage("La identificacion ya existe");
			return;
		}
		
		try {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUIVehicleRegistration.fxml"));
		
		Parent root = loader.load();
		
		GUIVehicleRegistrationController vehicleController = loader.getController();
		
		vehicleController.setClient(client);
		vehicleController.setNumbOfVehicles(numbOfVehicles);
		
		utils.changeView(btnAddClient, root);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("No fue posible crear el cliente");
		}

		

	}
	
	//Boton Cancelar
	@FXML
	public void returnPrincipalMenu(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUIPrincipal.fxml"));
			Parent root = loader.load();	
			utils.changeView(btnReturn, root);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private boolean validForm() {
		if(tfFullName.getText().isBlank() || tfDirection.getText().isBlank() || 
				tfEmail.getText().isBlank()) {//valida que no queden datos vacios
			LogicAlert.alertMessage("No dejar datos vacios");
			return true;
		}else if(!tfId.getText().matches("\\d{9}")) {
			//valida que la identificacion sean solo 9 numeros
			LogicAlert.alertMessage("La identificacion deben ser 9 digitos numericos");
			return true;
		}else if(!tfPhoneNumber.getText().matches("\\d+")) {//valida que el numero de telefono sean solo numeros
			LogicAlert.alertMessage("El numero de telefono deben ser solo numeros");
			return true;
		}else if(!tfNumOfVehicles.getText().matches("\\d+")) {//valida que la cantidad de vehiculos debe ser solo numeros
			LogicAlert.alertMessage("Cantidad de vehiculos debe ser un numero");
			return true;
		}
		return false;
	}
	
	private boolean existsClient(String idSearch) {
		for(Client clientTemp : clients) {
			if(clientTemp.getId().equalsIgnoreCase(idSearch)) {
				return true;
			}
		}
		return false;
	}
}
