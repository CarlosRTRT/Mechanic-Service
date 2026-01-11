package business;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.util.ArrayList;

import data.ClientData;
import data.OrdersData;
import domain.Client;
import domain.Orders;
import domain.Services;
import domain.Vehicle;
import javafx.event.ActionEvent;

import javafx.scene.control.TextArea;

import javafx.scene.control.ComboBox;

public class GUIReportsController {
	@FXML
	private TextArea taResults;
	@FXML
	private ComboBox<String> cbData;
	@FXML
	private TextField tfData;
	@FXML
	private Button btnSearch;
	
	private ArrayList<Client> clients = ClientData.getList();

	@FXML
	private void initialize() {
	    String[] reportTypes = {
	        "Ordenes por vehiculo (placa)",
	        "Ordenes por mecanico",
	        "Servicios por vehiculo (placa)",
	        "Vehiculos con ordenes en proceso",
	        "Ordenes por rango de fechas",
	        "Clientes con gasto mayor a"
	    };
	    cbData.getItems().addAll(reportTypes);

	    cbData.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
	        if (newVal != null) {
	            switch (newVal) {
	                case "Ordenes por vehiculo (placa)":
	                    tfData.setPromptText("Ingrese la placa (ej: ABC123)");
	                    tfData.setDisable(false);
	                    break;
	                case "Ordenes por mecanico":
	                    tfData.setPromptText("Ingrese el nombre del mecanico");
	                    tfData.setDisable(false);
	                    break;
	                case "Servicios por vehiculo (placa)":
	                    tfData.setPromptText("Ingrese la placa");
	                    tfData.setDisable(false);
	                    break;
	                case "Vehiculos con ordenes en proceso":
	                    tfData.setPromptText("No requiere datos");
	                    tfData.setDisable(true);
	                    tfData.clear();
	                    break;
	                case "Ordenes por rango de fechas":
	                    tfData.setPromptText("Formato: 2024-01-01,2024-12-31");
	                    tfData.setDisable(false);
	                    break;
	                case "Clientes con gasto mayor a":
	                    tfData.setPromptText("Ingrese el monto (ej: 5000)");
	                    tfData.setDisable(false);
	                    break;
	            }
	        }
	    });
	}

	@FXML
	public void search(ActionEvent event) {
	    String selectedReport = cbData.getValue();
	    String searchData = tfData.getText();
	    
	    if (selectedReport == null) {
	        taResults.setText("Por favor seleccione un tipo de reporte.");
	        return;
	    }
	    
	    taResults.clear();
	    
	    switch (selectedReport) {
	        case "Ordenes por vehiculo (placa)":
	            if (searchData.isEmpty()) {
	                taResults.setText("Por favor ingrese una placa.");
	                return;
	            }
	            searchOrdersByVehicle(searchData);
	            break;
	        case "Ordenes por mecanico":
	            if (searchData.isEmpty()) {
	                taResults.setText("Por favor ingrese el nombre del mecanico.");
	                return;
	            }
	            searchOrdersByMechanic(searchData);
	            break;
	        case "Servicios por vehiculo (placa)":
	            if (searchData.isEmpty()) {
	                taResults.setText("Por favor ingrese una placa.");
	                return;
	            }
	            searchServicesByVehicle(searchData);
	            break;
	        case "Vehiculos con ordenes en proceso":
	            searchVehiclesInProcess();
	            break;
	        case "Ordenes por rango de fechas":
	            if (searchData.isEmpty()) {
	                taResults.setText("Por favor ingrese el rango de fechas (formato: 2024-01-01,2024-12-31).");
	                return;
	            }
	            searchOrdersByDateRange(searchData);
	            break;
	        case "Clientes con gasto mayor a":
	            if (searchData.isEmpty()) {
	                taResults.setText("Por favor ingrese el monto minimo.");
	                return;
	            }
	            searchClientsByTotalSpent(searchData);
	            break;
	    }
	}
	
	private void searchOrdersByVehicle(String licensePlate) {
		StringBuilder result = new StringBuilder();
		result.append("ORDENES PARA VEHICULO: ").append(licensePlate).append(" ===\n\n");
		
		boolean found = false;
		int totalCost = 0;
		
		for (Client client : clients) {
			for (Vehicle vehicle : client.getVehicles()) {
				if (vehicle.getLicensePlate().equalsIgnoreCase(licensePlate)) {
					found = true;
					result.append("Cliente: ").append(client.getFullName()).append("\n");
					result.append("Vehiculo: ").append(vehicle.getBrand()).append(" ")
						  .append(vehicle.getModel()).append(" (").append(vehicle.getYear()).append(")\n\n");
					
					ArrayList<Orders> orders = (ArrayList<Orders>) vehicle.getOrder();
					if (orders != null && !orders.isEmpty()) {
						for (Orders order : orders) {
							result.append("Orden #").append(order.getOrderNumber()).append("\n");
							result.append("  Estado: ").append(order.getOrderState()).append("\n");
							result.append("  Fecha: ").append(order.getCreationDate()).append("\n");
							result.append("  Mecanico: ").append(order.getMechanic()).append("\n");
							result.append("  Costo Total: $").append(order.getTotalPrice()).append("\n");
							result.append("  Observaciones: ").append(order.getObservations()).append("\n\n");
							
							totalCost += order.getTotalPrice();
						}
						result.append("COSTO TOTAL ACUMULADO: $").append(totalCost).append("\n");
					} else {
						result.append("No hay ordenes registradas para este vehiculo.\n");
					}
				}
			}
		}
		
		if (!found) {
			result.append("No se encontro ningun vehiculo con la placa: ").append(licensePlate);
		}
		
		taResults.setText(result.toString());
	}
	
	private void searchOrdersByMechanic(String mechanicName) {
		StringBuilder result = new StringBuilder();
		result.append("ORDENES ASIGNADAS A: ").append(mechanicName).append(" ===\n\n");
		
		boolean found = false;
		
		for (Client client : clients) {
			for (Vehicle vehicle : client.getVehicles()) {
				ArrayList<Orders> orders = (ArrayList<Orders>) vehicle.getOrder();
				if (orders != null) {
					for (Orders order : orders) {
						if (order.getMechanic() != null && order.getMechanic().toString().contains(mechanicName)) {
							found = true;
							result.append("Orden #").append(order.getOrderNumber()).append("\n");
							result.append("  Cliente: ").append(client.getFullName()).append("\n");
							result.append("  Vehiculo: ").append(vehicle.getLicensePlate()).append(" - ")
								  .append(vehicle.getBrand()).append(" ").append(vehicle.getModel()).append("\n");
							result.append("  Estado: ").append(order.getOrderState()).append("\n");
							result.append("  Fecha: ").append(order.getCreationDate()).append("\n");
							result.append("  Costo: $").append(order.getTotalPrice()).append("\n\n");
						}
					}
				}
			}
		}
		
		if (!found) {
			result.append("No se encontraron ordenes para el mecanico: ").append(mechanicName);
		}
		
		taResults.setText(result.toString());
	}
	
	private void searchServicesByVehicle(String licensePlate) {
		StringBuilder result = new StringBuilder();
		result.append("HISTORIAL DE SERVICIOS PARA: ").append(licensePlate).append(" ===\n\n");
		
		boolean found = false;
		int totalSpent = 0;
		
		for (Client client : clients) {
			for (Vehicle vehicle : client.getVehicles()) {
				if (vehicle.getLicensePlate().equalsIgnoreCase(licensePlate)) {
					found = true;
					result.append("Cliente: ").append(client.getFullName()).append("\n");
					result.append("Vehiculo: ").append(vehicle.getBrand()).append(" ")
						  .append(vehicle.getModel()).append("\n\n");
					
					ArrayList<Orders> orders = (ArrayList<Orders>) vehicle.getOrder();
					if (orders != null && !orders.isEmpty()) {
						for (Orders order : orders) {
							result.append("Orden #").append(order.getOrderNumber())
								  .append(" (").append(order.getCreationDate()).append(")\n");
							
							ArrayList<Services> services = (ArrayList<Services>) order.getServices();
							if (services != null) {
								for (Services service : services) {
									result.append("  - ").append(service.getServiceName())
										  .append(" ($").append(service.getBaseCost()).append(")\n");
									totalSpent += service.getBaseCost();
								}
							}
							result.append("\n");
						}
						result.append("GASTO TOTAL EN SERVICIOS: $").append(totalSpent).append("\n");
					} else {
						result.append("No hay servicios registrados para este vehiculo.\n");
					}
				}
			}
		}
		
		if (!found) {
			result.append("No se encontro ningun vehiculo con la placa: ").append(licensePlate);
		}
		
		taResults.setText(result.toString());
	}
	
	private void searchVehiclesInProcess() {
		StringBuilder result = new StringBuilder();
		result.append("VEHICULOS CON ORDENES EN PROCESO\n\n");
		
		boolean found = false;
		
		for (Client client : clients) {
			for (Vehicle vehicle : client.getVehicles()) {
				ArrayList<Orders> orders = (ArrayList<Orders>) vehicle.getOrder();
				if (orders != null) {
					for (Orders order : orders) {
						if ("En proceso".equalsIgnoreCase(order.getOrderState())) {
							found = true;
							result.append("Placa: ").append(vehicle.getLicensePlate()).append("\n");
							result.append("Vehiculo: ").append(vehicle.getBrand()).append(" ")
								  .append(vehicle.getModel()).append("\n");
							result.append("Cliente: ").append(client.getFullName()).append("\n");
							result.append("Orden #").append(order.getOrderNumber()).append("\n");
							result.append("Mecanico: ").append(order.getMechanic()).append("\n");
							result.append("Fecha: ").append(order.getCreationDate()).append("\n\n");
							break;
						}
					}
				}
			}
		}
		
		if (!found) {
			result.append("No hay vehiculos con ordenes en proceso.");
		}
		
		taResults.setText(result.toString());
	}
	
	private void searchOrdersByDateRange(String dateRange) {
		StringBuilder result = new StringBuilder();
		result.append("ORDENES EN RANGO DE FECHAS \n");
		result.append("Formato esperado: YYYY-MM-DD,YYYY-MM-DD\n\n");
		
		String[] dates = dateRange.split(",");
		if (dates.length != 2) {
			taResults.setText("Error: Ingrese el rango de fechas en formato YYYY-MM-DD,YYYY-MM-DD");
			return;
		}
		
		try {
			LocalDate startDate = LocalDate.parse(dates[0].trim());
			LocalDate endDate = LocalDate.parse(dates[1].trim());
			
			boolean found = false;
			
			for (Client client : clients) {
				for (Vehicle vehicle : client.getVehicles()) {
					ArrayList<Orders> orders = (ArrayList<Orders>) vehicle.getOrder();
					if (orders != null) {
						for (Orders order : orders) {
							LocalDate orderDate = order.getCreationDate();
							if ((orderDate.isEqual(startDate) || orderDate.isAfter(startDate)) && 
								(orderDate.isEqual(endDate) || orderDate.isBefore(endDate))) {
								found = true;
								result.append("Orden #").append(order.getOrderNumber()).append("\n");
								result.append("  Fecha: ").append(order.getCreationDate()).append("\n");
								result.append("  Cliente: ").append(client.getFullName()).append("\n");
								result.append("  Vehiculo: ").append(vehicle.getLicensePlate()).append("\n");
								result.append("  Estado: ").append(order.getOrderState()).append("\n");
								result.append("  Costo: $").append(order.getTotalPrice()).append("\n\n");
							}
						}
					}
				}
			}
			
			if (!found) {
				result.append("No se encontraron ordenes en el rango de fechas especificado.");
			}
			
		} catch (Exception e) {
			result.append("Error al procesar las fechas. Use el formato: YYYY-MM-DD,YYYY-MM-DD");
		}
		
		taResults.setText(result.toString());
	}
	
	private void searchClientsByTotalSpent(String minAmount) {
		StringBuilder result = new StringBuilder();
		result.append("CLIENTES CON GASTO MAYOR A $").append(minAmount).append(" \n\n");
		
		try {
			int minimumAmount = Integer.parseInt(minAmount);
			boolean found = false;
			
			for (Client client : clients) {
				int totalSpent = 0;
				
				for (Vehicle vehicle : client.getVehicles()) {
					ArrayList<Orders> orders = (ArrayList<Orders>) vehicle.getOrder();
					if (orders != null) {
						for (Orders order : orders) {
							totalSpent += order.getTotalPrice();
						}
					}
				}
				
				if (totalSpent > minimumAmount) {
					found = true;
					result.append("Cliente: ").append(client.getFullName()).append("\n");
					result.append("  Email: ").append(client.getEmail()).append("\n");
					result.append("  Telefono: ").append(client.getPhoneNumber()).append("\n");
					result.append("  GASTO TOTAL: $").append(totalSpent).append("\n");
					result.append("  Vehiculos registrados: ").append(client.getVehicles().size()).append("\n\n");
				}
			}
			
			if (!found) {
				result.append("No se encontraron clientes con gasto mayor a $").append(minimumAmount);
			}
			
		} catch (NumberFormatException e) {
			result.append("Error: Ingrese un monto valido (solo numeros).");
		}
		
		taResults.setText(result.toString());
	}
}