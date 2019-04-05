/*
 * @author halworsen
 *
 */
module raycaster {
	requires javafx.base;
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires java.desktop;
	requires junit;
	requires gson;
	requires java.sql;
	
	exports maths;
	exports render;

	opens raycasterapp;
}
