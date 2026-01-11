package business;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import domain.Client;

import java.io.IOException;
import java.util.ArrayList;

import data.ClientData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.TableView;

public class GUIAdminClientController {
	@FXML
	private TextField tfId;
	@FXML
	private TextField tfFullName;
	@FXML
	private TextField tfPhoneNumber;
	@FXML
	private TextField tfEmail;
	@FXML
	private TextField tfDirection;
	@FXML
	private TableView tvClients;
	@FXML
	private Button btnEdit;
	@FXML
	private Button btnDelete;
	@FXML
	private Button btnReturn;
	
	private TableColumn<Client, String> tcId;
	private TableColumn<Client, String> tcFullName;
	private TableColumn<Client, Integer> tcPhoneNumber;
	private TableColumn<Client, String> tcEmail;
	private TableColumn<Client, String> tcDirection;
	
	private MyUtils utils;
	private ArrayList<Client> clients = ClientData.getList();
	
	private Client clientSelected;
	@FXML
	private void initialize() {
		this.utils = new MyUtils();
		initTableView();
		setDataOnTableView();
	}
	
	private void setDataOnTableView() {
		
		   if(clients != null) {
		        
			ObservableList<Client> observable = FXCollections.observableArrayList(clients);
			tvClients.setItems(observable);
			
			tvClients.setOnMouseClicked(e ->{
			clientSelected = (Client) tvClients.getSelectionModel().getSelectedItem();
			
			tfDirection.setText(clientSelected.getDirection());
			tfEmail.setText(clientSelected.getDirection());
			tfFullName.setText(clientSelected.getFullName());
			tfId.setText(clientSelected.getId());
			tfPhoneNumber.setText(String.valueOf(clientSelected.getPhoneNumber()));
			});
		}
	}

	private void initTableView() {
		this.tcId = new TableColumn<Client, String>("Identificacion");
		this.tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
		
		this.tcFullName = new TableColumn<Client, String>("Nombre");
		this.tcFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
		
		this.tcPhoneNumber = new TableColumn<Client, Integer>("Telefono");
		this.tcPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
		
		this.tcEmail = new TableColumn<Client, String>("Correo Electronico");
		this.tcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		
		this.tcDirection = new TableColumn<Client, String>("Direccion");
		this.tcDirection.setCellValueFactory(new PropertyValueFactory<>("direction"));
		
		this.tvClients.getColumns().addAll(this.tcId, this.tcFullName, this.tcPhoneNumber, this.tcEmail, this.tcDirection);
		this.tvClients.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
	}

	// Event Listener on Button[#btnEdit].onAction
	@FXML
	public void editClient(ActionEvent event) {
		if(selectClient()) return;//valida que se haya seleccionado un cliente en el table view al tocar el boton de editar
		if(clients != null && clientSelected != null) {
				Client clientUpdated = new Client();
				
		        clientUpdated.setId(tfId.getText());
		        clientUpdated.setFullName(tfFullName.getText());
		        clientUpdated.setPhoneNumber(Integer.parseInt(tfPhoneNumber.getText()));
		        clientUpdated.setEmail(tfEmail.getText());
		        clientUpdated.setDirection(tfDirection.getText());
		        clientUpdated.setVehicles(clientSelected.getVehicles());
			
				ClientData.editClient(clientUpdated);
				LogicAlert.alertMessage("Cliente Modificado Exitosamente");
				
				clients = ClientData.getList();
				setDataOnTableView();
				clearData();
		}
	}
	// Event Listener on Button[#btnDelete].onAction
	@FXML
	public void deleteClient(ActionEvent event) {
		
		if(selectClient()) return;//valida que se haya seleccionado un cliente en el table view al tocar el boton de eliminar
		if(clients != null && clientSelected != null) {

		
			ClientData.deleteClient(clientSelected, clientSelected.getId());
			LogicAlert.alertMessage("Cliente Eliminado Exitosamente");
			
			clients = ClientData.getList();
			setDataOnTableView();
			clearData();
	}
	}
	@FXML
	public void returnMenu(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUIPrincipal.fxml"));
			Parent root = loader.load();	
			utils.changeView(btnReturn, root);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private boolean selectClient() {
		if(tvClients.getSelectionModel().isEmpty()) {
			LogicAlert.alertMessage("Debe seleccionar un cliente en la Lista");
			return true;
		}
		return false;
	}
	
	private void clearData() {
		tfId.clear();
		tfFullName.clear();
		tfDirection.clear();
		tfEmail.clear();
		tfPhoneNumber.clear();
	}
}
