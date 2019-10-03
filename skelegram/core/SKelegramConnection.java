package skelegram.core;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.concurrent.Task;
import javafx.scene.control.Label;

public class SKelegramConnection {
	private Socket clientSocket;
	private String host;
	private int port;
	
	Thread senderThread;
	Thread receiverThread;	

	private ArrayList<String> incomingRawData;
	private ArrayList<String> toSendData;

	public SKelegramConnection() {
		this.incomingRawData = new ArrayList<String>();
		this.toSendData = new ArrayList<String>();
	}

	public boolean connect(String host, int port) {
		this.host = host;
		this.port = port;

		try {
			clientSocket = new Socket(this.host, this.port);
		} catch (IOException e) {
			System.out.println("Cannot conect to the server !");
			return false;
		}

		return true;
	}
	
	public void startReceiver() {
		Task receiverTask = new Task() {
			@Override
			public Void call() {
				try {
					BufferedReader clientSocketInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
					String rawInput;
					while (true) {
						if (clientSocketInput.ready()) {
							rawInput = clientSocketInput.readLine();
							System.out.println("Raw data from server : " + rawInput);
							incomingRawData.add(0,rawInput);
						}
					}
				} catch (Exception e) {
					System.out.println("Cannot start Receiver!");
				}
		
				return null;
			}
		};
		
		receiverThread = new Thread(receiverTask);
		receiverThread.setDaemon(true);
		receiverThread.start();
	}

	public void startSender() {
		Task senderTask = new Task() {
			@Override
			public Void call() {
				try {
					DataOutputStream clientSocketWriter = new DataOutputStream(clientSocket.getOutputStream());

					while (true) {
						if (!toSendData.isEmpty()) {
							String toSend = toSendData.get(0);
							clientSocketWriter.writeBytes(toSend);
							toSendData.remove(0);
						}
						Thread.sleep(10);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
		
				return null;
			}
		};
		
		senderThread = new Thread(senderTask);
		senderThread.setDaemon(true);
		senderThread.start();
	}

	public ArrayList<String> getIncomingRawData() {
		return incomingRawData;
	}

	public ArrayList<String> getToSendData() {
		return toSendData;
	}
}
