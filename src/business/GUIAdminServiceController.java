package business;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.ArrayList;

import data.ServicesData;
import domain.Services;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.TableView;

public class GUIAdminServiceController {
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
	private TableView tvServices;
	@FXML
	private Button btnEdit;
	@FXML
	private Button btnDelete;
	@FXML
	private Button btnReturn;
	
	private TableColumn<Services, Integer> tcServiceCode;
	private TableColumn<Services, String> tcServiceName;
	private TableColumn<Services, String> tcDescription;
	private TableColumn<Services, Integer> tcBaseCost;
	private TableColumn<Services, Integer> tcEstimatedTime;
	
	private MyUtils utils;
	private ArrayList<Services> services = ServicesData.getList();
	
	@FXML
	private void initialize() {
		this.utils = new MyUtils();
		initTableView();
		setDataTable();
		loadServiceData();
	}
	
	private void setDataTable() {
		ObservableList<Services> observable = FXCollections.observableArrayList(services);
		tvServices.setItems(observable);
	}
	
	private void loadServiceData() {
		tvServices.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			Services selectedService = (Services) tvServices.getSelectionModel().getSelectedItem();
			
			if (selectedService != null) {
				tfServiceCode.setText(String.valueOf(selectedService.getServiceCode()));
				tfServiceName.setText(selectedService.getServiceName());
				tfDescription.setText(selectedService.getDescription());
				tfBaseCost.setText(String.valueOf(selectedService.getBaseCost()));
				tfEstimatedTime.setText(String.valueOf(selectedService.getEstimatedTime()));
			}
		});
	}
	
	private void initTableView() {
		this.tcServiceCode = new TableColumn<Services, Integer>("Codigo");
		this.tcServiceCode.setCellValueFactory(new PropertyValueFactory<>("serviceCode"));
		
		this.tcServiceName = new TableColumn<Services, String>("Nombre");
		this.tcServiceName.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
		
		this.tcDescription = new TableColumn<Services, String>("Descripcion");
		this.tcDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
		
		this.tcBaseCost = new TableColumn<Services, Integer>("Costo Base");
		this.tcBaseCost.setCellValueFactory(new PropertyValueFactory<>("baseCost"));
		
		this.tcEstimatedTime = new TableColumn<Services, Integer>("Tiempo Estimado(Horas)");
		this.tcEstimatedTime.setCellValueFactory(new PropertyValueFactory<>("estimatedTime"));
		
		this.tvServices.getColumns().addAll(this.tcServiceCode, this.tcServiceName, this.tcDescription, this.tcBaseCost, this.tcEstimatedTime);
		this.tvServices.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}

	// Event Listener on Button[#btnEdit].onAction
	@FXML
	public void editService(ActionEvent event) {
		Services selectedService = (Services) tvServices.getSelectionModel().getSelectedItem();
		
		if (selectedService != null) {
			selectedService.setServiceName(tfServiceName.getText());
			selectedService.setDescription(tfDescription.getText());
			selectedService.setBaseCost(Integer.parseInt(tfBaseCost.getText()));
			selectedService.setEstimatedTime(Integer.parseInt(tfEstimatedTime.getText()));
			
			ServicesData.editService(selectedService);
			
			services = ServicesData.getList();
			setDataTable();
		}
	}
	
	// Event Listener on Button[#btnDelete].onAction
	@FXML
	public void deleteService(ActionEvent event) {
		Services selectedService = (Services) tvServices.getSelectionModel().getSelectedItem();
		
		if (selectedService != null) {
			ServicesData.deleteService(selectedService);

			services = ServicesData.getList();
			setDataTable();
			
			tfServiceCode.clear();
			tfServiceName.clear();
			tfDescription.clear();
			tfBaseCost.clear();
			tfEstimatedTime.clear();
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
