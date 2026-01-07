package business;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MyUtils {
	
	public void changeView(Button btn, String path) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
			Parent root = loader.load();
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

}
