package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RaycasterApp extends Application{
	@Override
	public void start(final Stage primaryStage) throws Exception {
		primaryStage.setTitle("Raycaster");
		primaryStage.setScene(new Scene(FXMLLoader.load(RaycasterApp.class.getResource("RaycasterApp.fxml"))));
		primaryStage.show();
	}

	public static void main(final String[] args) {
		RaycasterApp.launch(args);
	}
}