package business;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientAdminAutoTech {

    private String ip;
    private int port;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private Object guiController;
    
    public ClientAdminAutoTech(String ip, int port, Object guiController) {
        this.ip = ip;
        this.port = port;
        this.guiController = guiController;
        initClient();
    }
    
    private void initClient() {
        try {
            socket = new Socket(ip, port);
            System.out.println("Conectado al servidor en " + ip + ":" + port);
            
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            
            sendCommand("SET_CLIENT_TYPE@ADMIN");
            
            ClientReaderAdmin reader = new ClientReaderAdmin(in, guiController);
            reader.start();
            
        } catch (IOException e) {
            System.err.println("Error al conectar: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void getClients() {
        sendCommand("GET_CLIENTS@");
    }
    
    public void addClient(String idClient, String fullName, int phoneNumber, String email, String direction) {
        String command = String.format("ADD_CLIENT@%s@%s@%d@%s@%s", 
                                      idClient, fullName, phoneNumber, email, direction);
        sendCommand(command);
    }
    
    public void updateClient(int id, String name, int phoneNumber, String email, String direction) {
        String command = String.format("UPDATE_CLIENT@%d@%s@%d@%s@%s", 
                                      id, name, phoneNumber, email, direction);
        sendCommand(command);
    }
    
    public void deleteClient(int idClient) {
        sendCommand("DELETE_CLIENT@" + idClient);
    }
    
    public void getVehicles() {
        sendCommand("GET_VEHICLES@");
    }
    
    public void addVehicle(String plate, String brand, String model, int year, String fuelType, int idClient) {
        String command = String.format("ADD_VEHICLE@%s@%s@%s@%d@%s@%d", 
                                      plate, brand, model, year, fuelType, idClient);
        sendCommand(command);
    }
    
    public void updateVehicle(String plate, String brand, String model, int year, String fuelType) {
        String command = String.format("UPDATE_VEHICLE@%s@%s@%s@%d@%s", 
                                      plate, brand, model, year, fuelType);
        sendCommand(command);
    }
    
    public void deleteVehicle(String plate) {
        sendCommand("DELETE_VEHICLE@" + plate);
    }
    
    public void getMechanics() {
        sendCommand("GET_MECHANICS@");
    }
    
    public void addMechanic(String idMechanic, String fullName, String specialty, int phoneNumber, String email) {
        String command = String.format("ADD_MECHANIC@%s@%s@%s@%d@%s", 
                                      idMechanic, fullName, specialty, phoneNumber, email);
        sendCommand(command);
    }
    
    public void updateMechanic(int id, String fullName, String specialty, int phoneNumber, String email) {
        String command = String.format("UPDATE_MECHANIC@%d@%s@%s@%d@%s", 
                                      id, fullName, specialty, phoneNumber, email);
        sendCommand(command);
    }
    
    public void deleteMechanic(int id) {
        sendCommand("DELETE_MECHANIC@" + id);
    }
    
    public void getServices() {
        sendCommand("GET_SERVICES@");
    }
    
    public void addService(int code, String name, String description, int baseCost, int estimatedTime) {
        String command = String.format("ADD_SERVICE@%d@%s@%s@%d@%d", 
                                      code, name, description, baseCost, estimatedTime);
        sendCommand(command);
    }
    
    public void updateService(int code, String name, String description, int baseCost, int estimatedTime) {
        String command = String.format("UPDATE_SERVICE@%d@%s@%s@%d@%d", 
                                      code, name, description, baseCost, estimatedTime);
        sendCommand(command);
    }
    
    public void deleteService(int id) {
        sendCommand("DELETE_SERVICE@" + id);
    }
    
    public void getOrders() {
        sendCommand("GET_ORDERS@");
    }
    
    public void addOrder(int orderNumber, String orderState, String observations, int totalPrice, 
                        String creationDate, int idVehicle, int idMechanic, String serviceIds) {
        String command = String.format("ADD_ORDER@%d@%s@%s@%d@%s@%d@%d@%s", 
                                      orderNumber, orderState, observations, totalPrice, 
                                      creationDate, idVehicle, idMechanic, serviceIds);
        sendCommand(command);
    }
    
    public void updateOrder(int orderNumber, String orderState, String observations, int totalPrice, 
                           String creationDate, int idVehicle, int idMechanic, String serviceIds) {
        String command = String.format("UPDATE_ORDER@%d@%s@%s@%d@%s@%d@%d@%s", 
                                      orderNumber, orderState, observations, totalPrice, 
                                      creationDate, idVehicle, idMechanic, serviceIds);
        sendCommand(command);
    }
    
    public void deleteOrder(int id) {
        sendCommand("DELETE_ORDER@" + id);
    }
    
    public void uploadVehicleImage(String licensePlate, File imageFile) {
        try {
            sendCommand("UPLOAD_VEHICLE_IMAGE@" + licensePlate + "@" + imageFile.getName());
            out.writeLong(imageFile.length());
            
            FileInputStream fis = new FileInputStream(imageFile);
            byte[] buffer = new byte[4096];
            int bytesRead;
            
            while ((bytesRead = fis.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            
            out.flush();
            fis.close();
            System.out.println("Imagen enviada: " + imageFile.getName());
            
        } catch (IOException e) {
            System.err.println("Error al enviar imagen: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void getVehicleImages(String licensePlate) {
        sendCommand("GET_VEHICLE_IMAGES@" + licensePlate);
    }
    
    private void sendCommand(String command) {
        try {
            out.writeUTF(command);
            out.flush();
            System.out.println("Comando enviado: " + command);
        } catch (IOException e) {
            System.err.println("Error al enviar comando: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void disconnect() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
                System.out.println("Desconectado del servidor");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}