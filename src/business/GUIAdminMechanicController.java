package business;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.ArrayList;

import data.MechanicData;
import domain.Mechanic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.TableView;

public class GUIAdminMechanicController {
	@FXML
	private TextField tfId;
	@FXML
	private TextField tfFullName;
	@FXML
	private ComboBox<String> cbSpeciality;
	@FXML
	private TextField tfNumPhone;
	@FXML
	private TextField tfEmail;
	@FXML
	private TableView tvMechanics;
	@FXML
	private Button btnEdit;
	@FXML
	private Button btnDelete;
	@FXML
	private Button btnReturn;
	
	private TableColumn<Mechanic, String> tcId;
	private TableColumn<Mechanic, String> tcFullName;
	private TableColumn<Mechanic, String> tcSpecialty;
	private TableColumn<Mechanic, Integer> tcPhoneNumber;
	private TableColumn<Mechanic, String> tcEmail;
	
	private MyUtils utils;
	private ArrayList<Mechanic> mechanics = MechanicData.getList();
	
	@FXML
	private void initialize() {
		this.utils = new MyUtils();
		initTableView();
		setDataTable();
		loadMechanicData();
		
		String[] specialties = {"Motor", "Transmision", "Frenos", "Suspension", "Electricidad", "Aire Acondicionado"};
		cbSpeciality.getItems().addAll(specialties);
	}
	
	private void setDataTable() {
		ObservableList<Mechanic> observable = FXCollections.observableArrayList(mechanics);
		tvMechanics.setItems(observable);
	}
	
	private void loadMechanicData() {
		tvMechanics.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			Mechanic selectedMechanic = (Mechanic) tvMechanics.getSelectionModel().getSelectedItem();
			
			if (selectedMechanic != null) {
				tfId.setText(selectedMechanic.getId());
				tfFullName.setText(selectedMechanic.getFullName());
				cbSpeciality.setValue(selectedMechanic.getSpecialty());
				tfNumPhone.setText(String.valueOf(selectedMechanic.getPhoneNumber()));
				tfEmail.setText(selectedMechanic.getEmail());
			}
		});
	}
	
	private void initTableView() {
		this.tcId = new TableColumn<Mechanic, String>("Identificacion");
		this.tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
		
		this.tcFullName = new TableColumn<Mechanic, String>("Nombre");
		this.tcFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
		
		this.tcSpecialty = new TableColumn<Mechanic, String>("Especialidad");
		this.tcSpecialty.setCellValueFactory(new PropertyValueFactory<>("specialty"));
		
		this.tcPhoneNumber = new TableColumn<Mechanic, Integer>("Telefono");
		this.tcPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
		
		this.tcEmail = new TableColumn<Mechanic, String>("Correo Electronico");
		this.tcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		
		this.tvMechanics.getColumns().addAll(this.tcId, this.tcFullName, this.tcSpecialty, this.tcPhoneNumber, this.tcEmail);
		this.tvMechanics.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}

	// Event Listener on Button[#btnEdit].onAction
	@FXML
	public void editMechanic(ActionEvent event) {
		Mechanic selectedMechanic = (Mechanic) tvMechanics.getSelectionModel().getSelectedItem();
		
		if (selectedMechanic != null) {
			selectedMechanic.setFullName(tfFullName.getText());
			selectedMechanic.setSpecialty(cbSpeciality.getValue());
			selectedMechanic.setPhoneNumber(Integer.parseInt(tfNumPhone.getText()));
			selectedMechanic.setEmail(tfEmail.getText());
			
			MechanicData.editMechanic(selectedMechanic);
	
			mechanics = MechanicData.getList();
			setDataTable();
			LogicAlert.alertMessage("Mecanico editado exitosamente");
		}
	}
	
	// Event Listener on Button[#btnDelete].onAction
	@FXML
	public void deleteMechanic(ActionEvent event) {
		Mechanic selectedMechanic = (Mechanic) tvMechanics.getSelectionModel().getSelectedItem();
		
		if (selectedMechanic != null) {
			MechanicData.deleteService(selectedMechanic);

			mechanics = MechanicData.getList();
			setDataTable();
			
			tfId.clear();
			tfFullName.clear();
			cbSpeciality.setValue(null);
			tfNumPhone.clear();
			tfEmail.clear();
			LogicAlert.alertMessage("Mecanico eliminado exitosamente");
		}
	}
	
	// Event Listener on Button[#btnReturn].onAction
	@FXML
	public void returnMenu(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUIPrincipal.fxml"));
			Parent root = loader.load();	
			utils.changeView(btnEdit, root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}