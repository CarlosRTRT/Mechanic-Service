package business;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import java.io.File;

public class GUIConsultaController {

    @FXML
    private TextField tfIp;
    
    @FXML
    private TextField tfPlaca;
    
    @FXML
    private Button btnConectar;
    
    @FXML
    private Button btnConsultar;
    
    @FXML
    private VBox vboxOrderInfo;
    
    @FXML
    private Label lbOrderNumber;
    
    @FXML
    private Label lbState;
    
    @FXML
    private Label lbVehicleInfo;
    
    @FXML
    private Label lbMechanic;
    
    @FXML
    private Label lbDate;
    
    @FXML
    private Label lbServices;
    
    @FXML
    private Label lbObservations;
    
    @FXML
    private Label lbTotalPrice;
    
    @FXML
    private HBox hboxImages;
    
    private ClientConsultaAutoTech client;
    private static final String DEFAULT_IP = "localhost";
    private static final int DEFAULT_PORT = 5000;
    
    @FXML
    private void initialize() {
        tfIp.setText(DEFAULT_IP);
        btnConsultar.setDisable(true);
        tfPlaca.setDisable(true);
        vboxOrderInfo.setVisible(false);
    }
    
    @FXML
    public void conectar(ActionEvent event) {
        String ip = tfIp.getText();
        int port = DEFAULT_PORT;
        
        if (ip.isEmpty()) {
            LogicAlert.alertMessage("Debe ingresar la IP del servidor");
            return;
        }
        
        try {
            client = new ClientConsultaAutoTech(ip, port, this);
            btnConectar.setDisable(true);
            tfIp.setDisable(true);
            btnConsultar.setDisable(false);
            tfPlaca.setDisable(false);
            tfPlaca.requestFocus();
            
        } catch (Exception e) {
            LogicAlert.alertMessage("Error al conectar: " + e.getMessage());
        }
    }
    
    @FXML
    public void consultar(ActionEvent event) {
        String placa = tfPlaca.getText().trim().toUpperCase();
        
        if (placa.isEmpty()) {
            LogicAlert.alertMessage("Debe ingresar la placa del vehiculo");
            return;
        }
        
        if (client == null) {
            LogicAlert.alertMessage("Debe conectarse al servidor primero");
            return;
        }
        
        clearOrderDisplay();
        System.out.println("Consultando placa: " + placa);
        client.consultOrderByPlate(placa);
        client.getVehicleImages(placa);
    }
    
    public void updateOrderDisplay(int orderNumber, String state, String observations, 
                                   int totalPrice, String creationDate, String mechanicName, 
                                   String services, String vehicleInfo) {
        
        vboxOrderInfo.setVisible(true);
        
        lbOrderNumber.setText("Orden #" + orderNumber);
        lbState.setText("Estado: " + state);
        lbVehicleInfo.setText("Vehiculo: " + vehicleInfo);
        lbMechanic.setText("Mecanico: " + mechanicName);
        lbDate.setText("Fecha: " + creationDate);
        lbServices.setText("Servicios: " + services);
        lbObservations.setText("Observaciones: " + observations);
        lbTotalPrice.setText("Total: " + totalPrice);
        
        String color = getColorForState(state);
        vboxOrderInfo.setStyle("-fx-background-color: " + color + ";");
    }
    
    public void addImage(String imagePath) {
        try {
            Image image = null;
            
            if (imagePath.startsWith("http://") || imagePath.startsWith("https://")) {
                image = new Image(imagePath, true);
            } else {
                File imageFile = new File(imagePath);
                if (imageFile.exists()) {
                    image = new Image(imageFile.toURI().toString());
                } else {
                    image = new Image("file:///" + imagePath.replace("\\", "/"));
                }
            }
            
            if (image != null && !image.isError()) {
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(300);
                imageView.setFitHeight(300);
                imageView.setPreserveRatio(true);
                
                hboxImages.getChildren().add(imageView);
            } else {
                System.err.println("Error al cargar imagen: " + imagePath);
            }
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    private void clearOrderDisplay() {
        vboxOrderInfo.setVisible(false);
        hboxImages.getChildren().clear();
        
        lbOrderNumber.setText("");
        lbState.setText("");
        lbVehicleInfo.setText("");
        lbMechanic.setText("");
        lbDate.setText("");
        lbServices.setText("");
        lbObservations.setText("");
        lbTotalPrice.setText("");
    }
    
    private String getColorForState(String state) {
        switch (state.toLowerCase()) {
            case "en proceso":
                return "#87CEEB";
            case "completada":
                return "#90EE90";
            case "cancelada":
                return "#FF6B6B";
            case "registrada":
                return "#FFE4B5";
            default:
                return "#FFFFFF";
        }
    }
    
    public void enableSearchFields() {
        btnConsultar.setDisable(false);
        tfPlaca.setDisable(false);
    }
}