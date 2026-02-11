package business;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.LinkedList;

import domain.Client;
import domain.Mechanic;
import domain.Orders;
import domain.Services;
import domain.Vehicle;
import javafx.application.Platform;

public class ClientReaderAdmin extends Thread {

    private DataInputStream in;
    private Object guiController;
    
    public ClientReaderAdmin(DataInputStream in, Object guiController) {
        this.in = in;
        this.guiController = guiController;
    }
    
    @Override
    public void run() {
        try {
            while (true) {
                String serverMessage = in.readUTF();
                System.out.println("Servidor: " + serverMessage);
                processResponse(serverMessage);
            }
        } catch (IOException e) {
            System.err.println("Conexion perdida");
            e.printStackTrace();
        }
    }
    
    private void processResponse(String serverMessage) {
        String[] parts = serverMessage.split("@");
        String responseType = parts[0];
        
        switch (responseType) {
            case "CONNECT_OK":
                System.out.println("Conexion establecida");
                break;
                
            case "CLIENT_TYPE_SET":
                System.out.println("Tipo de cliente: " + parts[1]);
                break;
                
            case "CLIENTS_LIST":
                handleClientsList(parts);
                break;
                
            case "CLIENT_ADDED":
            case "CLIENT_UPDATED":
            case "CLIENT_DELETED":
                showSuccessMessage(parts[1]);
                break;
                
            case "REFRESH_CLIENTS":
                System.out.println("Actualizando clientes...");
                break;
                
            case "VEHICLES_LIST":
                handleVehiclesList(parts);
                break;
                
            case "VEHICLE_ADDED":
            case "VEHICLE_UPDATED":
            case "VEHICLE_DELETED":
                showSuccessMessage(parts[1]);
                break;
                
            case "REFRESH_VEHICLES":
                System.out.println("Actualizando vehiculos...");
                break;
                
            case "MECHANICS_LIST":
                handleMechanicsList(parts);
                break;
                
            case "MECHANIC_ADDED":
            case "MECHANIC_UPDATED":
            case "MECHANIC_DELETED":
                showSuccessMessage(parts[1]);
                break;
                
            case "REFRESH_MECHANICS":
                System.out.println("Actualizando mecanicos...");
                break;
                
            case "SERVICES_LIST":
                handleServicesList(parts);
                break;
                
            case "SERVICE_ADDED":
            case "SERVICE_UPDATED":
            case "SERVICE_DELETED":
                showSuccessMessage(parts[1]);
                break;
                
            case "REFRESH_SERVICES":
                System.out.println("Actualizando servicios...");
                break;
                
            case "ORDERS_LIST":
                handleOrdersList(parts);
                break;
                
            case "ORDER_ADDED":
            case "ORDER_UPDATED":
            case "ORDER_DELETED":
                showSuccessMessage(parts[1]);
                break;
                
            case "REFRESH_ORDERS":
                System.out.println("Actualizando ordenes...");
                break;
                
            case "IMAGE_UPLOADED":
                showSuccessMessage(parts[1]);
                break;
                
            case "VEHICLE_IMAGES":
                handleVehicleImages(parts);
                break;
                
            case "ERROR":
                showErrorMessage(parts[1]);
                break;
                
            default:
                System.out.println("Respuesta no reconocida: " + responseType);
                break;
        }
    }
    
    private void handleClientsList(String[] parts) {
        if (parts.length < 2) return;
        
        LinkedList<Client> clients = new LinkedList<>();
        
        for (int i = 1; i < parts.length; i++) {
            String[] data = parts[i].split("\\|");
            for (String clientData : data) {
                String[] fields = clientData.split("&");
                if (fields.length == 7) {
                    Client client = new Client();
                    client.setId(Integer.parseInt(fields[0]));
                    client.setIdClient(fields[1]);
                    client.setFullName(fields[2]);
                    client.setPhoneNumber(Integer.parseInt(fields[3]));
                    client.setEmail(fields[4]);
                    client.setDirection(fields[5]);
                    client.setState(fields[6]);
                    clients.add(client);
                }
            }
        }
        
        System.out.println("Recibidos " + clients.size() + " clientes");
    }
    
    private void handleVehiclesList(String[] parts) {
        if (parts.length < 2) return;
        
        LinkedList<Vehicle> vehicles = new LinkedList<>();
        
        for (int i = 1; i < parts.length; i++) {
            String[] data = parts[i].split("\\|");
            for (String vehicleData : data) {
                String[] fields = vehicleData.split("&");
                if (fields.length == 8) {
                    Vehicle vehicle = new Vehicle();
                    vehicle.setId(Integer.parseInt(fields[0]));
                    vehicle.setLicensePlate(fields[1]);
                    vehicle.setBrand(fields[2]);
                    vehicle.setModel(fields[3]);
                    vehicle.setYear(Integer.parseInt(fields[4]));
                    vehicle.setFuelType(fields[5]);
                    vehicle.setOwner(Integer.parseInt(fields[6]));
                    vehicle.setState(fields[7]);
                    vehicles.add(vehicle);
                }
            }
        }
        
        System.out.println("Recibidos " + vehicles.size() + " vehiculos");
    }
    
    private void handleMechanicsList(String[] parts) {
        if (parts.length < 2) return;
        
        LinkedList<Mechanic> mechanics = new LinkedList<>();
        
        for (int i = 1; i < parts.length; i++) {
            String[] data = parts[i].split("\\|");
            for (String mechanicData : data) {
                String[] fields = mechanicData.split("&");
                if (fields.length == 7) {
                    Mechanic mechanic = new Mechanic();
                    mechanic.setId(Integer.parseInt(fields[0]));
                    mechanic.setIdMechanic(fields[1]);
                    mechanic.setFullName(fields[2]);
                    mechanic.setSpecialty(fields[3]);
                    mechanic.setPhoneNumber(Integer.parseInt(fields[4]));
                    mechanic.setEmail(fields[5]);
                    mechanic.setState(fields[6]);
                    mechanics.add(mechanic);
                }
            }
        }
        
        System.out.println("Recibidos " + mechanics.size() + " mecanicos");
    }
    
    private void handleServicesList(String[] parts) {
        if (parts.length < 2) return;
        
        LinkedList<Services> services = new LinkedList<>();
        
        for (int i = 1; i < parts.length; i++) {
            String[] data = parts[i].split("\\|");
            for (String serviceData : data) {
                String[] fields = serviceData.split("&");
                if (fields.length == 7) {
                    Services service = new Services();
                    service.setId(Integer.parseInt(fields[0]));
                    service.setServiceCode(Integer.parseInt(fields[1]));
                    service.setServiceName(fields[2]);
                    service.setDescription(fields[3]);
                    service.setBaseCost(Integer.parseInt(fields[4]));
                    service.setEstimatedTime(Integer.parseInt(fields[5]));
                    service.setState(fields[6]);
                    services.add(service);
                }
            }
        }
        
        System.out.println("Recibidos " + services.size() + " servicios");
    }
    
    private void handleOrdersList(String[] parts) {
        if (parts.length < 2) return;
        
        LinkedList<Orders> orders = new LinkedList<>();
        
        for (int i = 1; i < parts.length; i++) {
            String[] data = parts[i].split("\\|");
            for (String orderData : data) {
                String[] fields = orderData.split("&");
                if (fields.length == 8) {
                    Orders order = new Orders();
                    order.setId(Integer.parseInt(fields[0]));
                    order.setOrderNumber(Integer.parseInt(fields[1]));
                    order.setCreationDate(java.time.LocalDate.parse(fields[2]));
                    order.setOrderState(fields[3]);
                    order.setObservations(fields[4]);
                    order.setTotalPrice(Integer.parseInt(fields[5]));
                    order.setIdVehicle(Integer.parseInt(fields[6]));
                    order.setIdMechanic(Integer.parseInt(fields[7]));
                    orders.add(order);
                }
            }
        }
        
        System.out.println("Recibidas " + orders.size() + " ordenes");
    }
    
    private void handleVehicleImages(String[] parts) {
        System.out.println("Imagenes del vehiculo recibidas");
    }
    
    private void showSuccessMessage(String message) {
        Platform.runLater(() -> {
            System.out.println(message);
        });
    }
    
    private void showErrorMessage(String message) {
        Platform.runLater(() -> {
            System.err.println("Error: " + message);
        });
    }
}