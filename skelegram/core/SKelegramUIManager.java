package skelegram.core;

import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import skelegram.UI.FXMLopenController;
import skelegram.UI.FXMLchatController;

public class SKelegramUIManager {
	private Scene openScene;
	private Scene chatScene;
	private Stage stage;
	private FXMLchatController chatController;
	private FXMLopenController openController;
	private Thread mainUIThread;

	private ArrayList<String> messages;

	private String nickname;

	public SKelegramUIManager(Stage stage) {
		this.stage = stage;
		this.messages = new ArrayList<String>();
	}

	public void initialize() {
		FXMLLoader openfxmlLoader = new FXMLLoader(getClass().getResource("/skelegram/UI/FXMLopen.fxml"));
		FXMLLoader chatfxmlLoader = new FXMLLoader(getClass().getResource("/skelegram/UI/FXMLchat.fxml"));

		Parent openFX;
		Parent rootFX;

		try {
			openFX = openfxmlLoader.load();
			rootFX = chatfxmlLoader.load();

			openScene = new Scene(openFX);
			chatScene = new Scene(rootFX);
		} catch (IOException e) {
			System.out.println("Cannot load FXML files !");
		}

		openController = openfxmlLoader.getController();
		chatController = chatfxmlLoader.getController();

		openScene.getStylesheets().add(getClass().getResource("/skelegram/UI/style.css").toExternalForm());
		chatScene.getStylesheets().add(getClass().getResource("/skelegram/UI/style.css").toExternalForm());

		stage.setScene(openScene);
		stage.setTitle("SKelegram");
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/skelegram/media/icon.png")));

		stage.show();
	}

	public FXMLchatController getChatController() {
		return chatController;
	}

	public FXMLopenController getOpenController() {
		return openController;
	}

	public String getNickname() {
		return nickname;
	}

	public ArrayList<String> getMessages(String message) {
		return messages;
	}

	public void showChat() {
		Platform.runLater(()->{
			stage.setScene(chatScene);
		});
	}

}
