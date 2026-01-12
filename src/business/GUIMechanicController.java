package business;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Random;

import data.MechanicData;
import domain.Mechanic;
import javafx.event.ActionEvent;

import javafx.scene.control.ComboBox;

public class GUIMechanicController {
	@FXML
	private TextField tfFullName;
	@FXML
	private TextField tfPhoneNumber;
	@FXML
	private TextField tfEmail;
	@FXML
	private Button btnAddMechanic;
	@FXML
	private Button btnReturn;
	@FXML
	private Button btnShowListMechanic;
	@FXML
	private ComboBox<String> cbSpecialty;
	
	private MyUtils utils;
	
	@FXML
	private void initialize() {
		this.utils = new MyUtils();
		String[] specialties = {"Motor", "Transmision", "Frenos", "Suspension", "Electricidad", "Aire Acondicionado"};
		cbSpecialty.getItems().addAll(specialties);
	}

	// Event Listener on Button[#btnAddMechanic].onAction
	@FXML
	public void addMechanic(ActionEvent event) {
		if(validForm())return;//valida el formulario
		
		 Random random = new Random();
		 
		int numero = random.nextInt(10000);
		String id = String.valueOf("MEC-"+numero); // Genera un ID único
		String fullName = tfFullName.getText();
		int phoneNumber = Integer.parseInt(tfPhoneNumber.getText());
		String email = tfEmail.getText();
		String specialty = cbSpecialty.getValue();
		
		Mechanic mechanic = new Mechanic(id, fullName, specialty, phoneNumber, email);
		
		MechanicData.saveMechanic(mechanic);

		tfFullName.clear();
		tfPhoneNumber.clear();
		tfEmail.clear();
		cbSpecialty.setValue(null);
		
		LogicAlert.alertMessage("Mecanico creado exitosamente");
	}
	
	// Event Listener on Button[#btnEditMechanic].onAction
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
	
	// Event Listener on Button[#btnShowListMechanic].onAction
	@FXML
	public void showListMechanic(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUIAdminMechanic.fxml"));
			Parent root = loader.load();	
			utils.changeView(btnAddMechanic, root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private boolean validForm() {
		if(tfFullName.getText().isBlank() || tfPhoneNumber.getText().isBlank() || tfEmail.getText().isBlank()) {//valida que no queden datos vacios
			LogicAlert.alertMessage("No dejar datos vacios");
			return true;
		}else if(!tfPhoneNumber.getText().matches("\\d+")) {//valida que el numero de telefono sean solo numeros
			LogicAlert.alertMessage("El numero de telefono debe ser solo numeros");
			return true;
		}else if(cbSpecialty.getSelectionModel().isEmpty()) {//valida que se seleccione una especialidad
			LogicAlert.alertMessage("Debe seleccionar una especialidad");
			return true;
		}
		return false;
	}
}