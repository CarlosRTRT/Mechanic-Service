package business;

import java.util.LinkedList;

import data.ServicesData;
import domain.Services;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MyUtils {
	private ServicesData serviceData;
	
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
	/*
	public boolean verifyServices() {
		LinkedList<Services> services = serviceData.getListServices();
		if(services.isEmpty()) {
			return true;
		}
		return false;
	}
*/
}
