package business;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDate;
import java.util.LinkedList;

import data.ClientData;
import data.MechanicData;
import data.OrdersData;
import data.ServicesData;
import data.VehicleData;
import domain.Client;
import domain.Mechanic;
import domain.Orders;
import domain.Services;
import domain.Vehicle;

public class ClientHandlerAutoTech extends Thread {

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private ServerAutoTech server;
    private ClientData clientData;
    private VehicleData vehicleData;
    private MechanicData mechanicData;
    private ServicesData servicesData;
    private OrdersData ordersData;
    
    // Ruta física donde se guardan las imágenes
    private static final String UPLOAD_PATH = "C:/xampp/htdocs/autotech/vehicle_images/";
    
    private boolean isConsultaClient = false;
    private String watchingPlate = null;
    
    public ClientHandlerAutoTech(Socket socket, ServerAutoTech server,
                                  ClientData clientData, VehicleData vehicleData,
                                  MechanicData mechanicData, ServicesData servicesData,
                                  OrdersData ordersData) {
        this.socket = socket;
        this.server = server;
        this.clientData = clientData;
        this.vehicleData = vehicleData;
        this.mechanicData = mechanicData;
        this.servicesData = servicesData;
        this.ordersData = ordersData;
    }
    
    @Override
    public void run() {
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            sendMessage("CONNECT_OK@Conexion establecida");
            
            while (true) {
                String command = in.readUTF();
                System.out.println("Comando: " + command);
                processCommand(command);
            }
            
        } catch (IOException e) {
            System.out.println("Cliente desconectado");
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            server.removeClient(this);
        }
    }
    
    private void processCommand(String command) {
        String[] parts = command.split("@");
        String action = parts[0];
        
        try {
            switch (action) {
                case "SET_CLIENT_TYPE":
                    if (parts.length > 1) {
                        isConsultaClient = parts[1].equals("CONSULTA");
                        sendMessage("CLIENT_TYPE_SET@" + parts[1]);
                    }
                    break;
                
                case "GET_CLIENTS":
                    sendClientsList();
                    break;
                    
                case "ADD_CLIENT":
                    addClient(parts);
                    break;
                    
                case "UPDATE_CLIENT":
                    updateClient(parts);
                    break;
                    
                case "DELETE_CLIENT":
                    deleteClient(parts[1]);
                    break;
                
                case "GET_VEHICLES":
                    sendVehiclesList();
                    break;
                    
                case "ADD_VEHICLE":
                    addVehicle(parts);
                    break;
                    
                case "UPDATE_VEHICLE":
                    updateVehicle(parts);
                    break;
                    
                case "DELETE_VEHICLE":
                    deleteVehicle(parts[1]);
                    break;
                
                case "GET_MECHANICS":
                    sendMechanicsList();
                    break;
                    
                case "ADD_MECHANIC":
                    addMechanic(parts);
                    break;
                    
                case "UPDATE_MECHANIC":
                    updateMechanic(parts);
                    break;
                    
                case "DELETE_MECHANIC":
                    deleteMechanic(parts[1]);
                    break;
                
                case "GET_SERVICES":
                    sendServicesList();
                    break;
                    
                case "ADD_SERVICE":
                    addService(parts);
                    break;
                    
                case "UPDATE_SERVICE":
                    updateService(parts);
                    break;
                    
                case "DELETE_SERVICE":
                    deleteService(parts[1]);
                    break;
                
                case "GET_ORDERS":
                    sendOrdersList();
                    break;
                    
                case "ADD_ORDER":
                    addOrder(parts);
                    break;
                    
                case "UPDATE_ORDER":
                    updateOrder(parts);
                    break;
                    
                case "DELETE_ORDER":
                    deleteOrder(parts[1]);
                    break;
                
                case "GET_ORDER_BY_PLATE":
                    sendOrderByPlate(parts[1]);
                    watchingPlate = parts[1];
                    break;
                
                case "UPLOAD_VEHICLE_IMAGE":
                    if (parts.length > 2) {
                        receiveVehicleImage(parts[1], parts[2]);
                    }
                    break;
                    
                case "GET_VEHICLE_IMAGES":
                    if (parts.length > 1) {
                        sendVehicleImages(parts[1]);
                    }
                    break;
                
                default:
                    sendMessage("ERROR@Comando no reconocido");
                    break;
            }
        } catch (Exception e) {
            sendMessage("ERROR@" + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void sendClientsList() {
        LinkedList<Client> clients = clientData.getListClientsActive();
        String response = "CLIENTS_LIST@";
        
        for (Client client : clients) {
            response += client.getId() + "&" + 
                       client.getIdClient() + "&" + 
                       client.getFullName() + "&" + 
                       client.getPhoneNumber() + "&" + 
                       client.getEmail() + "&" + 
                       client.getDirection() + "&" +
                       client.getState() + "|";
        }
        
        sendMessage(response);
    }
    
    private void addClient(String[] parts) {
        Client client = new Client(
            0, 
            parts[1], 
            parts[2], 
            Integer.parseInt(parts[3]), 
            parts[4], 
            parts[5]
        );
        
        if (clientData.addClient(client)) {
            sendMessage("CLIENT_ADDED@Cliente agregado");
            server.broadcastMessage("REFRESH_CLIENTS@");
        } else {
            sendMessage("ERROR@No se pudo agregar el cliente");
        }
    }
    
    private void updateClient(String[] parts) {
        if (clientData.updateClient(
            Integer.parseInt(parts[1]), 
            parts[2], 
            Integer.parseInt(parts[3]), 
            parts[4], 
            parts[5]
        )) {
            sendMessage("CLIENT_UPDATED@Cliente actualizado");
            server.broadcastMessage("REFRESH_CLIENTS@");
        } else {
            sendMessage("ERROR@No se pudo actualizar el cliente");
        }
    }
    
    private void deleteClient(String id) {
        if (clientData.deleteClient(Integer.parseInt(id))) {
            sendMessage("CLIENT_DELETED@Cliente eliminado");
            server.broadcastMessage("REFRESH_CLIENTS@");
        } else {
            sendMessage("ERROR@No se pudo eliminar el cliente");
        }
    }
    
    private void sendVehiclesList() {
        LinkedList<Vehicle> vehicles = vehicleData.getListVehicles();
        String response = "VEHICLES_LIST@";
        
        for (Vehicle vehicle : vehicles) {
            response += vehicle.getId() + "&" + 
                       vehicle.getLicensePlate() + "&" + 
                       vehicle.getBrand() + "&" + 
                       vehicle.getModel() + "&" + 
                       vehicle.getYear() + "&" + 
                       vehicle.getFuelType() + "&" + 
                       vehicle.getOwner() + "&" +
                       vehicle.getState() + "|";
        }
        
        sendMessage(response);
    }
    
    private void addVehicle(String[] parts) {
        Vehicle vehicle = new Vehicle(
            0, 
            parts[1], 
            parts[2], 
            parts[3], 
            Integer.parseInt(parts[4]), 
            parts[5], 
            Integer.parseInt(parts[6])
        );
        
        if (vehicleData.addVehicle(vehicle)) {
            sendMessage("VEHICLE_ADDED@Vehiculo agregado");
            server.broadcastMessage("REFRESH_VEHICLES@");
        } else {
            sendMessage("ERROR@No se pudo agregar el vehiculo");
        }
    }
    
    private void updateVehicle(String[] parts) {
        Vehicle vehicle = new Vehicle();
        vehicle.setLicensePlate(parts[1]);
        
        if (vehicleData.updateVehicle(
            vehicle, 
            parts[2], 
            parts[3], 
            Integer.parseInt(parts[4]), 
            parts[5]
        )) {
            sendMessage("VEHICLE_UPDATED@Vehiculo actualizado");
            server.broadcastMessage("REFRESH_VEHICLES@");
        } else {
            sendMessage("ERROR@No se pudo actualizar el vehiculo");
        }
    }
    
    private void deleteVehicle(String plate) {
        Vehicle vehicle = new Vehicle();
        vehicle.setLicensePlate(plate);
        
        if (vehicleData.deleteVehicle(vehicle)) {
            sendMessage("VEHICLE_DELETED@Vehiculo eliminado");
            server.broadcastMessage("REFRESH_VEHICLES@");
        } else {
            sendMessage("ERROR@No se pudo eliminar el vehiculo");
        }
    }
    
    private void sendMechanicsList() {
        LinkedList<Mechanic> mechanics = mechanicData.getListMechanics();
        String response = "MECHANICS_LIST@";
        
        for (Mechanic mechanic : mechanics) {
            response += mechanic.getId() + "&" + 
                       mechanic.getIdMechanic() + "&" + 
                       mechanic.getFullName() + "&" + 
                       mechanic.getSpecialty() + "&" + 
                       mechanic.getPhoneNumber() + "&" + 
                       mechanic.getEmail() + "&" +
                       mechanic.getState() + "|";
        }
        
        sendMessage(response);
    }
    
    private void addMechanic(String[] parts) {
        Mechanic mechanic = new Mechanic(
            0, 
            parts[1], 
            parts[2], 
            parts[3], 
            Integer.parseInt(parts[4]), 
            parts[5]
        );
        
        if (mechanicData.addMechanic(mechanic)) {
            sendMessage("MECHANIC_ADDED@Mecanico agregado");
            server.broadcastMessage("REFRESH_MECHANICS@");
        } else {
            sendMessage("ERROR@No se pudo agregar el mecanico");
        }
    }
    
    private void updateMechanic(String[] parts) {
        if (mechanicData.updateMechanic(
            Integer.parseInt(parts[1]), 
            parts[2], 
            parts[3], 
            Integer.parseInt(parts[4]), 
            parts[5]
        )) {
            sendMessage("MECHANIC_UPDATED@Mecanico actualizado");
            server.broadcastMessage("REFRESH_MECHANICS@");
        } else {
            sendMessage("ERROR@No se pudo actualizar el mecanico");
        }
    }
    
    private void deleteMechanic(String id) {
        if (mechanicData.deleteMechanic(Integer.parseInt(id))) {
            sendMessage("MECHANIC_DELETED@Mecanico eliminado");
            server.broadcastMessage("REFRESH_MECHANICS@");
        } else {
            sendMessage("ERROR@No se pudo eliminar el mecanico");
        }
    }
    
    private void sendServicesList() {
        LinkedList<Services> services = servicesData.getListServices();
        String response = "SERVICES_LIST@";
        
        for (Services service : services) {
            response += service.getId() + "&" + 
                       service.getServiceCode() + "&" + 
                       service.getServiceName() + "&" + 
                       service.getDescription() + "&" + 
                       service.getBaseCost() + "&" + 
                       service.getEstimatedTime() + "&" +
                       service.getState() + "|";
        }
        
        sendMessage(response);
    }
    
    private void addService(String[] parts) {
        Services service = new Services(
            0, 
            Integer.parseInt(parts[1]), 
            parts[2], 
            parts[3], 
            Integer.parseInt(parts[4]), 
            Integer.parseInt(parts[5])
        );
        
        if (servicesData.addService(service)) {
            sendMessage("SERVICE_ADDED@Servicio agregado");
            server.broadcastMessage("REFRESH_SERVICES@");
        } else {
            sendMessage("ERROR@No se pudo agregar el servicio");
        }
    }
    
    private void updateService(String[] parts) {
        if (servicesData.updateService(
            Integer.parseInt(parts[1]), 
            parts[2], 
            parts[3], 
            Integer.parseInt(parts[4]), 
            Integer.parseInt(parts[5])
        )) {
            sendMessage("SERVICE_UPDATED@Servicio actualizado");
            server.broadcastMessage("REFRESH_SERVICES@");
        } else {
            sendMessage("ERROR@No se pudo actualizar el servicio");
        }
    }
    
    private void deleteService(String id) {
        if (servicesData.deleteService(Integer.parseInt(id))) {
            sendMessage("SERVICE_DELETED@Servicio eliminado");
            server.broadcastMessage("REFRESH_SERVICES@");
        } else {
            sendMessage("ERROR@No se pudo eliminar el servicio");
        }
    }
    
    private void sendOrdersList() {
        LinkedList<Orders> orders = ordersData.getListOrders();
        String response = "ORDERS_LIST@";
        
        for (Orders order : orders) {
            response += order.getId() + "&" + 
                       order.getOrderNumber() + "&" + 
                       order.getCreationDate() + "&" + 
                       order.getOrderState() + "&" + 
                       order.getObservations() + "&" + 
                       order.getTotalPrice() + "&" + 
                       order.getIdVehicle() + "&" + 
                       order.getIdMechanic() + "|";
        }
        
        sendMessage(response);
    }
    
    private void addOrder(String[] parts) {
        Orders order = new Orders(
            0, 
            Integer.parseInt(parts[1]), 
            LocalDate.parse(parts[5]), 
            parts[2], 
            Integer.parseInt(parts[7]), 
            parts[3], 
            Integer.parseInt(parts[4]), 
            Integer.parseInt(parts[6])
        );
        
        int idOrder = ordersData.addOrders(order);
        
        if (idOrder > 0) {
            if (parts.length > 8 && !parts[8].isEmpty()) {
                String[] serviceIds = parts[8].split(",");
                for (String serviceId : serviceIds) {
                    servicesData.addServiceToOrder(idOrder, Integer.parseInt(serviceId));
                }
            }
            
            sendMessage("ORDER_ADDED@Orden agregada");
            server.broadcastMessage("REFRESH_ORDERS@");
            
            Vehicle vehicle = getVehicleById(Integer.parseInt(parts[6]));
            if (vehicle != null) {
                server.notifyClientsByPlate(vehicle.getLicensePlate());
            }
        } else {
            sendMessage("ERROR@No se pudo agregar la orden");
        }
    }
    
    private void updateOrder(String[] parts) {
        if (ordersData.updateOrder(
            Integer.parseInt(parts[1]), 
            parts[2], 
            parts[3], 
            Integer.parseInt(parts[4]), 
            LocalDate.parse(parts[5]), 
            Integer.parseInt(parts[6]), 
            Integer.parseInt(parts[7])
        )) {
            LinkedList<Orders> orders = ordersData.getListOrders();
            int idOrder = 0;
            for (Orders o : orders) {
                if (o.getOrderNumber() == Integer.parseInt(parts[1])) {
                    idOrder = o.getId();
                    break;
                }
            }
            
            if (idOrder > 0 && parts.length > 8) {
                servicesData.deleteServicesToOrder(idOrder);
                if (!parts[8].isEmpty()) {
                    String[] serviceIds = parts[8].split(",");
                    for (String serviceId : serviceIds) {
                        servicesData.addServiceToOrder(idOrder, Integer.parseInt(serviceId));
                    }
                }
            }
            
            sendMessage("ORDER_UPDATED@Orden actualizada");
            server.broadcastMessage("REFRESH_ORDERS@");
            
            Vehicle vehicle = getVehicleById(Integer.parseInt(parts[6]));
            if (vehicle != null) {
                server.notifyClientsByPlate(vehicle.getLicensePlate());
            }
        } else {
            sendMessage("ERROR@No se pudo actualizar la orden");
        }
    }
    
    private void deleteOrder(String id) {
        if (ordersData.deleteOrder(Integer.parseInt(id))) {
            sendMessage("ORDER_DELETED@Orden eliminada");
            server.broadcastMessage("REFRESH_ORDERS@");
        } else {
            sendMessage("ERROR@No se pudo eliminar la orden");
        }
    }
    
    private void sendOrderByPlate(String licensePlate) {
        Vehicle vehicle = null;
        LinkedList<Vehicle> vehicles = vehicleData.getListVehicles();
        for (Vehicle v : vehicles) {
            if (v.getLicensePlate().equalsIgnoreCase(licensePlate)) {
                vehicle = v;
                break;
            }
        }
        
        if (vehicle == null) {
            sendMessage("ERROR@Vehiculo no encontrado");
            return;
        }
        
        LinkedList<Orders> allOrders = ordersData.getListOrders();
        Orders latestOrder = null;
        
        for (Orders order : allOrders) {
            if (order.getIdVehicle() == vehicle.getId()) {
                if (latestOrder == null || order.getCreationDate().isAfter(latestOrder.getCreationDate())) {
                    latestOrder = order;
                }
            }
        }
        
        if (latestOrder == null) {
            sendMessage("ERROR@No hay ordenes para este vehiculo");
            return;
        }
        
        LinkedList<Services> services = ordersData.listServicesByOrder(latestOrder.getId());
        String servicesStr = "";
        for (Services s : services) {
            servicesStr += s.getServiceName() + ",";
        }
        
        // CORRECCIÓN: Buscar mecánico por ID numérico de la BD
        Mechanic mechanic = getMechanicById(latestOrder.getIdMechanic());
        String mechanicName = mechanic != null ? mechanic.getFullName() : "No asignado";
        
        System.out.println("Mecánico encontrado: " + mechanicName + " (ID: " + latestOrder.getIdMechanic() + ")");
        
        String response = "ORDER_DATA@" + latestOrder.getOrderNumber() + "@" + 
                         latestOrder.getOrderState() + "@" + latestOrder.getObservations() + "@" + 
                         latestOrder.getTotalPrice() + "@" + latestOrder.getCreationDate() + "@" + 
                         mechanicName + "@" + servicesStr + "@" + 
                         vehicle.getBrand() + " " + vehicle.getModel() + " " + vehicle.getYear();
        
        sendMessage(response);
    }
    
    private void receiveVehicleImage(String licensePlate, String fileName) {
        try {
            long fileSize = in.readLong();
            String vehicleDir = UPLOAD_PATH + licensePlate + "/";
            File dir = new File(vehicleDir);
            dir.mkdirs();
            
            File file = new File(vehicleDir + fileName);
            FileOutputStream fos = new FileOutputStream(file);
            
            byte[] buffer = new byte[4096];
            long totalRead = 0;
            int bytesRead;
            
            while (totalRead < fileSize && (bytesRead = in.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
                totalRead += bytesRead;
            }
            
            fos.close();
            System.out.println("Imagen guardada: " + file.getAbsolutePath());
            sendMessage("IMAGE_UPLOADED@Imagen guardada");
            server.notifyClientsByPlate(licensePlate);
            
        } catch (IOException e) {
            sendMessage("ERROR@" + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void sendVehicleImages(String licensePlate) {
        String vehicleDir = UPLOAD_PATH + licensePlate + "/";
        File dir = new File(vehicleDir);
        String response = "VEHICLE_IMAGES@";
        
        System.out.println("Buscando imagenes en: " + vehicleDir);
        
        if (dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null && files.length > 0) {
                for (File file : files) {
                    if (file.isFile()) {
                        // CAMBIO CRÍTICO: Enviar ruta FÍSICA del archivo
                        String absolutePath = file.getAbsolutePath();
                        response += file.getName() + "&" + absolutePath + "|";
                        System.out.println("Enviando imagen: " + absolutePath);
                    }
                }
            } else {
                System.out.println("No se encontraron archivos en el directorio");
            }
        } else {
            System.out.println("El directorio no existe: " + vehicleDir);
        }
        
        sendMessage(response);
    }
    
    public void sendMessage(String message) {
        try {
            out.writeUTF(message);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private Vehicle getVehicleById(int idVehicle) {
        LinkedList<Vehicle> vehicles = vehicleData.getListVehicles();
        for (Vehicle v : vehicles) {
            if (v.getId() == idVehicle) {
                return v;
            }
        }
        return null;
    }
    
    private Mechanic getMechanicById(int idMechanic) {
        LinkedList<Mechanic> mechanics = mechanicData.getListMechanics();
        for (Mechanic m : mechanics) {
            if (m.getId() == idMechanic) {
                return m;
            }
        }
        return null;
    }
    
    public boolean isConsultaClient() {
        return isConsultaClient;
    }
    
    public String getWatchingPlate() {
        return watchingPlate;
    }
}