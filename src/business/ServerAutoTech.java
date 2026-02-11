package business;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

import data.ClientData;
import data.MechanicData;
import data.OrdersData;
import data.ServicesData;
import data.VehicleData;

public class ServerAutoTech {
    
    private final int PORT = 5000;
    private LinkedList<ClientHandlerAutoTech> clientsHandlerArray;
    private ClientData clientData;
    private VehicleData vehicleData;
    private MechanicData mechanicData;
    private ServicesData servicesData;
    private OrdersData ordersData;
    
    public ServerAutoTech() {
        this.clientData = new ClientData();
        this.vehicleData = new VehicleData();
        this.mechanicData = new MechanicData();
        this.servicesData = new ServicesData();
        this.ordersData = new OrdersData();
        this.clientsHandlerArray = new LinkedList<ClientHandlerAutoTech>();
        initServer();
    }
    
    private void initServer() {
        try {
            ServerSocket server = new ServerSocket(PORT);
            System.out.println("Servidor iniciado en puerto " + PORT);
            
            while (true) {
                Socket socket = server.accept();
                System.out.println("Cliente conectado: " + socket.getInetAddress());
                
                ClientHandlerAutoTech clientHandler = new ClientHandlerAutoTech(
                    socket, this, clientData, vehicleData, mechanicData, servicesData, ordersData
                );
                
                clientHandler.start();
                this.clientsHandlerArray.add(clientHandler);
                System.out.println("Total clientes: " + clientsHandlerArray.size());
            }
            
        } catch (IOException e) {
            System.err.println("Error al iniciar servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void broadcastMessage(String message) {
        System.out.println("Enviando mensaje a " + clientsHandlerArray.size() + " clientes");
        for (ClientHandlerAutoTech client : clientsHandlerArray) {
            client.sendMessage(message);
        }
    }
    
    public void notifyClientsByPlate(String licensePlate) {
        String message = "UPDATE_ORDER@" + licensePlate;
        for (ClientHandlerAutoTech client : clientsHandlerArray) {
            if (client.isConsultaClient() && client.getWatchingPlate() != null 
                && client.getWatchingPlate().equals(licensePlate)) {
                client.sendMessage(message);
            }
        }
    }
    
    public void removeClient(ClientHandlerAutoTech client) {
        clientsHandlerArray.remove(client);
        System.out.println("Cliente desconectado. Total: " + clientsHandlerArray.size());
    }
    
    public static void main(String[] args) {
        new ServerAutoTech();
    }
}