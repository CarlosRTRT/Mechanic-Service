package business;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import java.io.IOException;

import domain.Client;
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
		
		Client client = new Client();
		
		client.setDirection(tfDirection.getText());
		client.setEmail(tfEmail.getText());
		client.setFullName(tfFullName.getText());
		client.setId(tfId.getText());
		client.setPhoneNumber(Integer.parseInt(tfPhoneNumber.getText()));
		
		int numbOfVehicles = Integer.parseInt(tfNumOfVehicles.getText());
		
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
}
