package business;

import java.util.ArrayList;

import data.ServicesData;
import domain.Services;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MyUtils {
	
	public void changeView(Button btn, Parent root) {
		try {

			Scene scene = new Scene(root);
			Stage stage = new Stage();
			
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
			
			Stage temp = (Stage)btn.getScene().getWindow();
			temp.close();
			
		}catch(Exception e) {
			System.out.println("Error"+e.getMessage());
		}
	}
	
	public boolean verifyServices() {
		ArrayList<Services> services = ServicesData.getList();
		
		if(services.isEmpty()) {
			return true;
		}
		return false;
	}

}
