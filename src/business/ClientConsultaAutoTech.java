package business;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientConsultaAutoTech {

    private String ip;
    private int port;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private GUIConsultaController guiControllerConsulta;
    
    public ClientConsultaAutoTech(String ip, int port, GUIConsultaController guiControllerConsulta) {
        this.ip = ip;
        this.port = port;
        this.guiControllerConsulta = guiControllerConsulta;
        initClient();
    }
    
    private void initClient() {
        try {
            socket = new Socket(ip, port);
            System.out.println("Conectado al servidor");
            
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            
            sendCommand("SET_CLIENT_TYPE@CONSULTA");
            
            ClientReaderConsulta reader = new ClientReaderConsulta(in, guiControllerConsulta);
            reader.start();
            
        } catch (IOException e) {
            System.err.println("Error al conectar: " + e.getMessage());
        }
    }
    
    public void consultOrderByPlate(String licensePlate) {
        sendCommand("GET_ORDER_BY_PLATE@" + licensePlate);
    }
    
    public void getVehicleImages(String licensePlate) {
        sendCommand("GET_VEHICLE_IMAGES@" + licensePlate);
    }
    
    private void sendCommand(String command) {
        try {
            out.writeUTF(command);
            out.flush();
        } catch (IOException e) {
            System.err.println("Error al enviar comando: " + e.getMessage());
        }
    }
    
    public void disconnect() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
                System.out.println("Desconectado");
            }
        } catch (IOException e) {
            System.err.println("Error al desconectar: " + e.getMessage());
        }
    }
}