package business;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class LogicAlert {
	public static void alertMessage(String message) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setHeaderText(message);
		alert.show();
	}
}
