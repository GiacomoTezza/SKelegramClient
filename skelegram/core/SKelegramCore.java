package skelegram.core;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.stage.Stage;

public class SKelegramCore {
	private SKelegramConnection skConnection;
	private SKelegramUIManager skUIManager;
	private String registeredNickname;

	private Thread coreThread;

	public SKelegramCore(Stage stage) {
		skConnection = new SKelegramConnection();
		skUIManager = new SKelegramUIManager(stage);
	}

	public void initialize() {
		skConnection.connect("localhost", 45678);
		skConnection.startReceiver();
		skConnection.startSender();

		skUIManager.initialize();

		Task coreTask = new Task() {
			@Override
			public Void call() {
				try {
					System.out.println("Starting Core...");

					// WAITING for user nickname input

					while (skUIManager.getOpenController().getNickname() == "") {
						Thread.sleep(10);
		
					}

					registeredNickname = skUIManager.getOpenController().getNickname();

					System.out.println("Registered Nickname : " + registeredNickname);

					Platform.runLater(() -> {
						skUIManager.showChat();
					});

					while (true) {
						if (!skUIManager.getChatController().getToSendRawMessages().isEmpty()) {
							
							String rawData = skUIManager.getChatController().getToSendRawMessages().get(0);
							skUIManager.getChatController().getToSendRawMessages().remove(0);

							String elaboratedData = elaborateRawData(rawData);

							skConnection.getToSendData().add(elaboratedData);
						}

						if (!skConnection.getIncomingRawData().isEmpty()) {	
							skUIManager.getChatController().addMessage(skConnection.getIncomingRawData().get(0));
							skConnection.getIncomingRawData().remove(0);
						}
						Thread.sleep(10);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;

			}
		};

		coreThread = new Thread(coreTask);
		coreThread.setDaemon(true);
		coreThread.start();
	}

	private String elaborateRawData(String rawData) {
		return registeredNickname + " : " + rawData + "&(end)&";
	}

}