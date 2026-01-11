module Proyecto {
	requires javafx.controls;
	requires com.fasterxml.jackson.databind;
	requires com.fasterxml.jackson.datatype.jsr310;
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.base;
	
	opens business to javafx.graphics, javafx.fxml;
	opens domain to com.fasterxml.jackson.databind, javafx.base;
}
