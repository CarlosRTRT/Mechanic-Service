package business;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.ArrayList;

import data.MechanicData;
import data.OrdersData;
import data.ServicesData;
import domain.Mechanic;
import domain.Orders;
import domain.Services;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.ComboBox;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class GUIAdminOrderController {
	@FXML
	private TextField tfNumOfOrder;
	@FXML
	private DatePicker dpCreationDate;
	@FXML
	private ComboBox<Mechanic> cbMechanicSelected;
	@FXML
	private TableView tvOrders;
	@FXML
	private TableView tvServices;
	@FXML
	private ComboBox cbServiceSelected;
	@FXML
	private TextField tfTotalPrice;
	@FXML
	private Button btnRemoveService;
	@FXML
	private TextField tfOservation;
	@FXML
	private Button btnReturn;
	@FXML
	private ComboBox<String> cbStateOrder;
	@FXML
	private Button btnEdit;
	@FXML
	private Button btnDelete;
	
	private TableColumn<Orders, Integer> tcOrderNumber;
	private TableColumn<Orders, String> tcCreationDate;
	private TableColumn<Orders, String> tcOrderState;
	private TableColumn<Orders, String> tcObservations;
	private TableColumn<Orders, Integer> tcTotalPrice;
	
	private TableColumn<Services, Integer> tcServiceCode;
	private TableColumn<Services, String> tcServiceName;
	private TableColumn<Services, String> tcDescription;
	private TableColumn<Services, Integer> tcBaseCost;
	private TableColumn<Services, Integer> tcEstimatedTime;

	private MyUtils utils;
	private ArrayList<Orders> orders = (ArrayList<Orders>) OrdersData.getList();
	private ArrayList<Services> services = ServicesData.getList();
	private ArrayList<Services> servicesOfOrderSelected;
	ArrayList<Services> servicesOnTable;
	Orders orderSelectedOnTf;
	private Orders originalOrderSnapshot;

    private boolean isProcessingServiceSelection = false;
	private int price;


	
	@FXML
	private void initialize() {
		this.utils = new MyUtils();
		this.servicesOnTable = new ArrayList<>();
		initTableView();
		String [] orderStates = {"Registrada", "En proceso", "Completada", "Entregada", "Cancelada"};
		cbStateOrder.getItems().addAll(orderStates);
		setDataTable();
	    loadOrderData();
		setCbMechanics();
		cbServiceSelected.setOnAction(e ->{
	        if (!isProcessingServiceSelection) { 
	            addServiceToTable();
	        }
		});
	}
	
	private void setCbMechanics() {
		ArrayList<Mechanic> mechanics = MechanicData.getList();
		
		for(Mechanic mechanicsTemp : mechanics) {
			cbMechanicSelected.getItems().addAll(mechanicsTemp);
		}

	}
	
	private void fillCbServices() {
	    cbServiceSelected.getItems().clear();
	    
	    if(servicesOfOrderSelected != null) {
	        for(Services serviceTemp : services) {
	            boolean yaEstaEnLaOrden = false;
	            
	            for(Services serviceOnTable: servicesOfOrderSelected) {
	                if(serviceTemp.getServiceCode() == serviceOnTable.getServiceCode()) {
	                    yaEstaEnLaOrden = true;
	                    break;
	                }
	            }
	            
	            if(!yaEstaEnLaOrden) {
	                cbServiceSelected.getItems().add(serviceTemp.getServiceName());
	            }
	        }
	    }
	}
	
	public void addServiceToTable() {
	    String serviceSelected = (String) cbServiceSelected.getSelectionModel().getSelectedItem();
	    
	    if (serviceSelected == null || serviceSelected.isEmpty()) {
	        System.out.println("Seleccion vacia");
	        return; 
	    }
	    
	    isProcessingServiceSelection = true;
	    
	    for(Services serviceTemp : services) {
	        if(serviceTemp.getServiceName().equals(serviceSelected)) {
	            servicesOnTable.add(serviceTemp);
	            price += serviceTemp.getBaseCost();
	            tfTotalPrice.setText(String.valueOf(price));

	            ObservableList<Services> observable = FXCollections.observableArrayList(servicesOnTable);
	            tvServices.setItems(observable);

	            final String serviceName = serviceSelected;
	            Platform.runLater(() -> {
	                cbServiceSelected.getItems().remove(serviceName);
	                cbServiceSelected.getSelectionModel().clearSelection();
	                isProcessingServiceSelection = false;
	            });
	            
	            break;
	        }
	    }
	}

	private void setDataTable() {
		ObservableList<Orders> observable = FXCollections.observableArrayList(orders);
		tvOrders.setItems(observable);
		
	}

	private void initTableView() {
		this.tcOrderNumber = new TableColumn<Orders, Integer>("Num Orden");
		this.tcOrderNumber.setCellValueFactory(new PropertyValueFactory<>("orderNumber"));
		
		this.tcCreationDate = new TableColumn<Orders, String>("Fecha Creacion");
		this.tcCreationDate.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
		
		this.tcOrderState = new TableColumn<Orders, String>("Estado");
		this.tcOrderState.setCellValueFactory(new PropertyValueFactory<>("orderState"));
		
		this.tcObservations = new TableColumn<Orders, String>("Observaciones");
		this.tcObservations.setCellValueFactory(new PropertyValueFactory<>("observations"));
		
		this.tcTotalPrice = new TableColumn<Orders, Integer>("Precio Total");
		this.tcTotalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
		
		this.tvOrders.getColumns().addAll(this.tcOrderNumber, this.tcCreationDate, this.tcOrderState, this.tcObservations, this.tcTotalPrice);
		this.tvOrders.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
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
	
	private void loadOrderData() {
	    tvOrders.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
	        Orders selectedOrder = (Orders) tvOrders.getSelectionModel().getSelectedItem();
	        
	        if (selectedOrder != null) {

	            // campos
	            tfNumOfOrder.setText(String.valueOf(selectedOrder.getOrderNumber()));
	            dpCreationDate.setValue(selectedOrder.getCreationDate());
	            cbMechanicSelected.setValue(selectedOrder.getMechanic());
	            cbStateOrder.setValue(selectedOrder.getOrderState());
	            tfOservation.setText(selectedOrder.getObservations());
	            tfTotalPrice.setText(String.valueOf(selectedOrder.getTotalPrice()));

	            // servicios
	            servicesOfOrderSelected = new ArrayList<>(selectedOrder.getServices());
	            servicesOnTable = new ArrayList<>(servicesOfOrderSelected);

	            tvServices.setItems(FXCollections.observableArrayList(servicesOnTable));
	            price = selectedOrder.getTotalPrice();

	            orderSelectedOnTf = selectedOrder;

	            originalOrderSnapshot = new Orders(
	            	    selectedOrder.getOrderNumber(),
	            	    selectedOrder.getCreationDate(),
	            	    selectedOrder.getOrderState(),
	            	    selectedOrder.getMechanic(),
	            	    new ArrayList<>(selectedOrder.getServices()),
	            	    selectedOrder.getObservations(),
	            	    selectedOrder.getTotalPrice()
	            	);
	        
	            fillCbServices();
	        }

	    });
	}
	
	@FXML
	public void removeService(ActionEvent event) {
	    Services selectedService = (Services) tvServices.getSelectionModel().getSelectedItem();
	    
	    if (selectedService != null) {
	        for(int i = 0; i < servicesOnTable.size(); i++) {
	            if(servicesOnTable.get(i).getServiceCode() == selectedService.getServiceCode()) {
	                servicesOnTable.remove(i);
	                price -= selectedService.getBaseCost();
	                tfTotalPrice.setText(String.valueOf(price));
	                break;
	            }
	        }
	        
	        // Actualizar la tabla
	        ObservableList<Services> observable = FXCollections.observableArrayList(servicesOnTable);
	        tvServices.setItems(observable);
	        
	        // Agregar el servicio de vuelta al ComboBox
	        cbServiceSelected.getItems().add(selectedService.getServiceName());
	    }
	}
	// Event Listener on Button[#btnCancel].onAction
	@FXML
	public void returnMenu(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUIPrincipal.fxml"));
			Parent root = loader.load();	
			utils.changeView(btnEdit, root);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// Event Listener on Button[#btnEdit].onAction
	@FXML
	public void editOrder(ActionEvent event) {
	    if(selectOrder()) return;
	    Orders selectedOrder = (Orders) tvOrders.getSelectionModel().getSelectedItem();
	    
	    if (selectedOrder != null) {
	        // Validar que haya cambios antes de editar
	        if(!hasChanges()) {
	            LogicAlert.alertMessage("No se han realizado cambios en la orden");
	            return;
	        }
	        
	        selectedOrder.setCreationDate(dpCreationDate.getValue());
	        selectedOrder.setMechanic((cbMechanicSelected.getValue()));
	        selectedOrder.setOrderState(cbStateOrder.getValue());
	        selectedOrder.setObservations(tfOservation.getText());
	        selectedOrder.setTotalPrice(Integer.parseInt(tfTotalPrice.getText()));
	        selectedOrder.setServices(servicesOnTable);
	       
	        OrdersData.editOrder(selectedOrder, 0);
	        
	        orders = (ArrayList<Orders>) OrdersData.getList();
	        LogicAlert.alertMessage("Orden Modificada Exitosamente");
	        
	        tfNumOfOrder.clear();
	        dpCreationDate.setValue(null);
	        cbMechanicSelected.setValue(null);
	        cbStateOrder.setValue(null);
	        tfOservation.clear();
	        tfTotalPrice.clear();
	        
	        tvServices.setItems(FXCollections.observableArrayList());
	        
	        setDataTable();
	        
	    }
	}
	// Event Listener on Button[#btnDelete].onAction
	@FXML
	public void deleteOrder(ActionEvent event) {
		if(selectOrder()) return;//valida que se haya escogido una orden en la lista para borrarla
	    Orders selectedOrder = (Orders) tvOrders.getSelectionModel().getSelectedItem();
	    boolean isInProcess = selectedOrder.getOrderState().equalsIgnoreCase("En proceso");//si la orden esta En proceso devuelve true
	    
	    if(isInProcess) {//no deja eliminar ordenes En proceso
	    	LogicAlert.alertMessage("No se Pueden Eliminar Ordenes en Proceso");
	    	return;
	    }
	    
	    if (selectedOrder != null) {
	        OrdersData.deleteOrder(selectedOrder);

	        orders = (ArrayList<Orders>) OrdersData.getList();
	        LogicAlert.alertMessage("Orden Eliminada Exitosamente");
	        }
	    	
	        setDataTable();
	        
	        tfNumOfOrder.clear();
	        dpCreationDate.setValue(null);
	        cbMechanicSelected.setValue(null);
	        cbStateOrder.setValue(null);
	        tfOservation.clear();
	        tfTotalPrice.clear();

	        tvServices.setItems(FXCollections.observableArrayList());
	    }
	
	private boolean selectOrder() {
		if(tvOrders.getSelectionModel().isEmpty()) {
			LogicAlert.alertMessage("Debe seleccionar una Orden en la Lista");
			return true;
		}
		return false;
	}
	private boolean hasChanges() {
	    if (originalOrderSnapshot == null) {
	        return false;
	    }

	    if (!dpCreationDate.getValue().equals(originalOrderSnapshot.getCreationDate())) {
	        return true;
	    }

	    if (cbMechanicSelected.getValue() == null || 
	        !cbMechanicSelected.getValue().equals(originalOrderSnapshot.getMechanic())) {
	        return true;
	    }

	    if (!cbStateOrder.getValue().equals(originalOrderSnapshot.getOrderState())) {
	        return true;
	    }

	    String currentObservations = tfOservation.getText() == null ? "" : tfOservation.getText();
	    String originalObservations = originalOrderSnapshot.getObservations() == null ? "" : originalOrderSnapshot.getObservations();
	    if (!currentObservations.equals(originalObservations)) {
	        return true;
	    }
	    
	    if (price != originalOrderSnapshot.getTotalPrice()) {
	        return true;
	    }
	    
	    if (servicesOnTable.size() != originalOrderSnapshot.getServices().size()) {
	        return true;
	    }
	    
	    for (Services currentService : servicesOnTable) {
	        boolean found = false;
	        for (Services originalService : originalOrderSnapshot.getServices()) {
	            if (currentService.getServiceCode() == originalService.getServiceCode()) {
	                found = true;
	                break;
	            }
	        }
	        if (!found) {
	            return true;
	        }
	    }
	    
	    return false;
	}
}
