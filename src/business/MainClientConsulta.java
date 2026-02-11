package business;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainClientConsulta extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        try {
            System.out.println("AutoTech - Cliente de Consulta");
            
            Parent root = FXMLLoader.load(getClass().getResource("/presentation/GUIConsulta.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("AutoTech - Consulta de Ordenes");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
            
        } catch(Exception e) {
            System.err.println("Error al iniciar: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}