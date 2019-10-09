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
		skUIManager.initialize();
		
		if(!skConnection.connect("localhost", 45678)) {
			skUIManager.getOpenController().showError();
			return;	
		}
		
		skConnection.startReceiver();
		skConnection.startSender();

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

					skUIManager.showChat();
	
					while (true) {
						if (!skUIManager.getChatController().getToSendRawMessages().isEmpty()) {
							
							String rawData = skUIManager.getChatController().getToSendRawMessages().get(0);
							skUIManager.getChatController().getToSendRawMessages().remove(0);

							String elaboratedData = elaborateRawData(rawData);
							
							System.out.println("Sending to Sender : " + elaboratedData);
							
							skConnection.addToSendData(elaboratedData);
						}

						if (!skConnection.getIncomingRawData().isEmpty()) {	
							
							skUIManager.getChatController().addMessage(skConnection.getIncomingRawData().get(0));
							
							skConnection.getIncomingRawData().remove(0);
						}
						Thread.sleep(1);
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
