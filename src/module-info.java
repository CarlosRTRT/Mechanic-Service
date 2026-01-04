module Proyecto {
	requires javafx.controls;
	requires com.fasterxml.jackson.databind;
	requires com.fasterxml.jackson.datatype.jsr310;
	requires javafx.graphics;
	requires javafx.fxml;
	
	opens business to javafx.graphics, javafx.fxml;
}
