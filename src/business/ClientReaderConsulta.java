package business;

import java.io.DataInputStream;
import java.io.IOException;

import javafx.application.Platform;

public class ClientReaderConsulta extends Thread {

    private DataInputStream in;
    private GUIConsultaController guiControllerConsulta;
    
    public ClientReaderConsulta(DataInputStream in, GUIConsultaController guiControllerConsulta) {
        this.in = in;
        this.guiControllerConsulta = guiControllerConsulta;
    }
    
    @Override
    public void run() {
        try {
            while (true) {
                String serverMessage = in.readUTF();
                processResponse(serverMessage);
            }
        } catch (IOException e) {
            System.err.println("Conexion perdida con el servidor");
        }
    }
    
    private void processResponse(String serverMessage) {
        String[] parts = serverMessage.split("@");
        String responseType = parts[0];
        
        switch (responseType) {
            case "CONNECT_OK":
                showConnectionSuccess();
                break;
                
            case "CLIENT_TYPE_SET":
                break;
                
            case "ORDER_DATA":
                handleOrderData(parts);
                break;
                
            case "UPDATE_ORDER":
                System.out.println("Orden actualizada");
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
    
    private void handleOrderData(String[] parts) {
        if (parts.length < 9) {
            showErrorMessage("Datos incompletos");
            return;
        }
        
        int orderNumber = Integer.parseInt(parts[1]);
        String state = parts[2];
        String observations = parts[3];
        int totalPrice = Integer.parseInt(parts[4]);
        String creationDate = parts[5];
        String mechanicName = parts[6];
        String services = parts[7];
        String vehicleInfo = parts[8];
        
        System.out.println("Orden #" + orderNumber + " | Estado: " + state);
        
        Platform.runLater(() -> {
            if (guiControllerConsulta != null) {
                guiControllerConsulta.updateOrderDisplay(
                    orderNumber, state, observations, totalPrice, creationDate, 
                    mechanicName, services, vehicleInfo
                );
            }
        });
    }
    
    private void handleVehicleImages(String[] parts) {
        Platform.runLater(() -> {
            if (guiControllerConsulta == null) {
                System.out.println("Controlador no inicializado");
                return;
            }
            
            if (parts.length < 2) {
                System.out.println("Sin imagenes disponibles");
                return;
            }
            
            int imageCount = 0;
            for (int i = 1; i < parts.length; i++) {
                String[] imageData = parts[i].split("\\|");
                for (String image : imageData) {
                    if (!image.isEmpty()) {
                        String[] imageFields = image.split("&");
                        if (imageFields.length == 2) {
                            String imagePath = imageFields[1];
                            guiControllerConsulta.addImage(imagePath);
                            imageCount++;
                        }
                    }
                }
            }
            
            if (imageCount > 0) {
                System.out.println(imageCount + " imagen cargadas");
            }
        });
    }
    
    private void showConnectionSuccess() {
        Platform.runLater(() -> {
            if (guiControllerConsulta != null) {
                guiControllerConsulta.enableSearchFields();
            }
        });
    }
    
    private void showErrorMessage(String message) {
        Platform.runLater(() -> {
            System.err.println("Error: " + message);
            LogicAlert.alertMessage("Error: " + message);
        });
    }
}