package raycasterapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RaycasterApp extends Application{
	@Override
	public void start(final Stage primaryStage) throws Exception {
		primaryStage.setTitle("Raycaster - New Scene");
		primaryStage.setResizable(false);
		FXMLLoader loader = new FXMLLoader(RaycasterApp.class.getResource("RaycasterApp.fxml"));
		Scene scene = new Scene(loader.load());
		
		RaycasterController controller = loader.getController();
		controller.setStage(primaryStage);
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(final String[] args) {
		RaycasterApp.launch(args);
	}
}