package business;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.LinkedList;

import data.ClientData;
import domain.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.input.MouseEvent;

import javafx.scene.control.TableView;

public class GUIClientsController {
	@FXML
	private TextField tfId;
	@FXML
	private Button btnAddClient;
	@FXML
	private TextField tfFullName;
	@FXML
	private TextField tfPhoneNumber;
	@FXML
	private TextField tfEmail;
	@FXML
	private TextField tfDirection;
	@FXML
	private Button btnReturn;
	@FXML
	private Button btnEditClient;
	@FXML
	private Button btnDeleteClient;
	@FXML
	private TableView<Client> tvClients;
	
	private TableColumn<Client, String> tcId;
	private TableColumn<Client, String> tcFullName;
	private TableColumn<Client, Integer> tcPhoneNumber;
	private TableColumn<Client, String> tcEmail;
	private TableColumn<Client, String> tcDirection;
	
	private MyUtils utils;
	private ClientData clientData;
	
	@FXML
	private void initialize() {
		this.utils = new MyUtils();
		this.clientData = new ClientData();
		initTableView();
		showClients();
	}

	// Event Listener on Button[#btnAddClient].onAction
	@FXML
	public void addClient(ActionEvent event) {
		if(validForm())return;//Valida los datos del formulario
		String idClient = tfId.getText();
		String name = tfFullName.getText();
		int phone = Integer.parseInt(tfPhoneNumber.getText());
		String email = tfEmail.getText();
		String direction = tfDirection.getText();
		Client client = new Client(0, idClient, name, phone, email, direction);
		if(existsClient(client.getIdClient())) {
			LogicAlert.alertMessage("La Identificacion ya Existe");
			return;
		}else if(clientData.addClient(client)) {
			LogicAlert.alertMessage("Cliente agregado exitosamente!!");
			showClients();
			clearForm();
		}
	}
	// Event Listener on Button[#btnReturn].onAction
	@FXML
	public void returnPrincipalMenu(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUIPrincipal.fxml"));
			Parent root = loader.load();		
			utils.changeView(btnAddClient, root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// Event Listener on Button[#btnEditClient].onAction
	@FXML
	public void editClient(ActionEvent event) {
		Client client = clientSelectedTV();
		if(client == null) {
			LogicAlert.alertMessage("Debe Seleccionar un Cliente en la Tabla");
			return;
		}
		if(validForm())return;
		int idClient = Integer.parseInt(tfId.getText());
		String newName = tfFullName.getText();
		String newEmail = tfEmail.getText();
		String newDirection = tfDirection.getText();
		int newPhoneNumber = Integer.parseInt(tfPhoneNumber.getText());
		if(clientData.updateClient(idClient, newName, newPhoneNumber, newEmail, newDirection)) {
			LogicAlert.alertMessage("Cliente Actualizado Correctamente!!");
			btnReturn.setText("Volver al Menú Principal");
			tfId.setEditable(true);
			clearForm();
			showClients();
		}
	}
	// Event Listener on Button[#btnDeleteClient].onAction
	@FXML
	public void deleteClient(ActionEvent event) {
		Client client = clientSelectedTV();
		if(client == null) {
			LogicAlert.alertMessage("Debe Seleccionar un Cliente en la Tabla");
			return;
		}else if(clientData.deleteClient(Integer.parseInt(client.getIdClient()))) {
			LogicAlert.alertMessage("Cliente Eliminado Correctamente!!");
			btnReturn.setText("Volver al Menú Principal");
			tfId.setEditable(true);
			clearForm();
			showClients();
		}
	}
	// Event Listener on TableView[#tvClients].onMouseClicked
	@FXML
	public void clientSelected(MouseEvent event) {
		Client client = clientSelectedTV();
		if(client != null) {
			tfId.setEditable(false);
			btnReturn.setText("Cancelar");
			tfId.setText(client.getIdClient());
			tfFullName.setText(client.getFullName());
			tfDirection.setText(client.getDirection());
			tfEmail.setText(client.getEmail());
			tfPhoneNumber.setText(String.valueOf(client.getPhoneNumber()));
		}
	}
	
	private void initTableView() {
		this.tcId = new TableColumn<Client, String>("CEDULA");
		this.tcId.setCellValueFactory(new PropertyValueFactory<>("idClient"));
		
		this.tcFullName = new TableColumn<Client, String>("NOMBRE");
		this.tcFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
		
		this.tcPhoneNumber = new TableColumn<Client, Integer>("TELÉFONO");
		this.tcPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
		
		this.tcEmail = new TableColumn<Client, String>("EMAIL");
		this.tcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		
		this.tcDirection = new TableColumn<Client, String>("DIRECCIÓN");
		this.tcDirection.setCellValueFactory(new PropertyValueFactory<>("direction"));
		
		this.tvClients.getColumns().addAll(this.tcId, this.tcFullName, this.tcPhoneNumber, this.tcEmail, this.tcDirection);
		this.tvClients.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}
	
	private void showClients() {
		LinkedList<Client> clients = clientData.getListClientsActive();
		   if(clients != null) {
			ObservableList<Client> observable = FXCollections.observableArrayList(clients);
			tvClients.setItems(observable);
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
		}
		return false;
	}
	
	private boolean existsClient(String idSearch) {
		LinkedList<Client> clients = clientData.getListClientsActive();
		for(Client clientTemp : clients) {
			if(clientTemp.getIdClient().equalsIgnoreCase(idSearch)) {
				return true;
			}
		}
		return false;
	}
	
	public void clearForm() {
		tfId.clear();
		tfFullName.clear();
		tfEmail.clear();
		tfDirection.clear();
		tfPhoneNumber.clear();
	}
	
	private Client clientSelectedTV() {
		Client tempClient = this.tvClients.getSelectionModel().getSelectedItem();
		return tempClient;
	}
}
