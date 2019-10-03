package skelegram.core;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class SKelegramEntry extends Application{
	private SKelegramCore skCore;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		skCore = new SKelegramCore(primaryStage);
		skCore.initialize();
	}

}
