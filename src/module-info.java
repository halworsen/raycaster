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
	
	exports maths;
	exports render;

	opens raycasterapp;
}
