package business;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

import data.MechanicData;
import domain.Mechanic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.input.MouseEvent;

import javafx.scene.control.TableView;

public class GUIMechanicsController {
	@FXML
	private TextField tfFullName;
	@FXML
	private TextField tfPhoneNumber;
	@FXML
	private TextField tfEmail;
	@FXML
	private Button btnAddMechanic;
	@FXML
	private Button btnEdit;
	@FXML
	private ComboBox<String> cbSpecialty;
	@FXML
	private Button btnReturn;
	@FXML
	private Button btnDelete;
	@FXML
	private TableView<Mechanic> tvMechanics;
	
	private TableColumn<Mechanic, String> tcId;
	private TableColumn<Mechanic, String> tcFullName;
	private TableColumn<Mechanic, String> tcSpecialty;
	private TableColumn<Mechanic, Integer> tcPhoneNumber;
	private TableColumn<Mechanic, String> tcEmail;
	

	private MyUtils utils;
	private MechanicData mechanicData;
	
	@FXML
	private void initialize() {
		this.utils = new MyUtils();
		this.mechanicData = new MechanicData();
		
		initTableView();
		setDataTable();
		fillCbSpecialtys();
	}

	// Event Listener on Button[#btnAddMechanic].onAction
	@FXML
	public void addMechanic(ActionEvent event) {
		if(validForm())return;//valida el formulario
		Random random = new Random();
		int numero = random.nextInt(10000);
		String idMechanic = String.valueOf(numero); // Genera un ID único
		String fullName = tfFullName.getText();
		int phoneNumber = Integer.parseInt(tfPhoneNumber.getText());
		String email = tfEmail.getText();
		String specialty = cbSpecialty.getValue();
		Mechanic mechanic = new Mechanic(0,idMechanic, fullName, specialty, phoneNumber, email);
		if(mechanicData.addMechanic(mechanic)) {
			LogicAlert.alertMessage("Mecanico creado exitosamente");
			clearForm();
			setDataTable();
		}
	}
	// Event Listener on Button[#btnEdit].onAction
	@FXML
	public void edit(ActionEvent event) {
		Mechanic mechanic = mechanicSelectedTV();
		if(mechanic == null) {
			LogicAlert.alertMessage("Debe Seleccionar un Mecanico en la Tabla");
			return;
		}
		if(validForm())return;
		String fullName = tfFullName.getText();
		int phoneNumber = Integer.parseInt(tfPhoneNumber.getText());
		String email = tfEmail.getText();
		String specialty = cbSpecialty.getValue();
		if(mechanicData.updateMechanic(mechanic.getId(), fullName, specialty, phoneNumber, email)) {
			LogicAlert.alertMessage("Mecanico Actualizado Correctamente!!");
			clearForm();
			setDataTable();
		}
	}
	// Event Listener on Button[#btnReturn].onAction
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
	// Event Listener on Button[#btnDelete].onAction
	@FXML
	public void delete(ActionEvent event) {
		Mechanic mechanic = mechanicSelectedTV();
		if(mechanic == null) {
			LogicAlert.alertMessage("Debe Seleccionar un Mecanico en la Tabla");
			return;
		}
		if(mechanicData.deleteMechanic(mechanic.getId())) {
			LogicAlert.alertMessage("Mecanico Eliminado Correctamente!!");
			clearForm();
			setDataTable();
		}
	}
	// Event Listener on TableView[#tvMechanics].onMouseClicked
	@FXML
	public void selectMechanic(MouseEvent event) {
		Mechanic mechanic = mechanicSelectedTV();
		if(mechanic != null) {
			tfFullName.setText(mechanic.getFullName());
			tfPhoneNumber.setText(String.valueOf(mechanic.getPhoneNumber()));
			tfEmail.setText(mechanic.getEmail());
			cbSpecialty.setValue(mechanic.getSpecialty());
		}
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

	private void setDataTable() {
		LinkedList<Mechanic> mechanics = mechanicData.getListMechanics();
		ObservableList<Mechanic> observable = FXCollections.observableArrayList(mechanics);
		tvMechanics.setItems(observable);
	}
	
	public void fillCbSpecialtys() {
		String[] specialties = {"Motor", "Transmision", "Frenos", "Suspension", "Electricidad", "Aire Acondicionado"};
		cbSpecialty.getItems().addAll(specialties);
	}
	
	public Mechanic mechanicSelectedTV() {
		Mechanic mechanic = tvMechanics.getSelectionModel().getSelectedItem();
		return mechanic;
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
	
	public void clearForm() {
		tfFullName.clear();
		tfEmail.clear();
		tfPhoneNumber.clear();
		cbSpecialty.setValue(null);
	}
}
