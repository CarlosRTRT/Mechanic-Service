package business;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MyUtils {
	
	public void changeView(Button btn, Parent root) {
		try {
			Scene scene = new Scene(root);

			scene.getStylesheets().add(
					getClass().getResource("/business/application.css").toExternalForm()
					);

			Stage stage = (Stage) btn.getScene().getWindow();
			stage.setScene(scene);
			stage.setResizable(false);

		}catch(Exception e) {
			System.out.println("Error"+e.getMessage());
		}
	}
}
