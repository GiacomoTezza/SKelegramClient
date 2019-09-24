package skelegram;

import java.io.File;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 *
 * @author Tezza Giacomo
 */
public class SKelegram extends Application {
    private static Scene openScene;
    private static Scene rootScene;
    public static Stage stage;
    private static String nickname;
    private static ArrayList<Label> messages;
    private static FXMLrootController rootController;
    private static Client client;
    private static String payload;
    
    @Override
    public void start(Stage stage) throws Exception {
        SKelegram.stage = stage;
        SKelegram.messages = new ArrayList<>();
        
        Parent rootFXML = FXMLLoader.load(getClass().getResource("FXMLroot.fxml"));
        Parent openFXML = FXMLLoader.load(getClass().getResource("FXMLopen.fxml"));
        
        rootScene = new Scene(rootFXML);
        openScene = new Scene(openFXML);
        
//        rootScene.getStylesheets().add(new File("style.css").get);
        stage.setScene(openScene);
        stage.setTitle("SKelegram");
        stage.show();
        SKelegram.stage.setResizable(false);
        SKelegram.client = new Client("93.39.191.67", 45678);  
        
        SKelegram.addMessage("Welcome to SKelegram chatroom:\n");
        SKelegram.getClient().connect();
        
        while (true) {
            SKelegram.payload = SKelegram.getClient().receive();
            
            Task task = new Task<Void>() {
                @Override public Void call() {
                    if (SKelegram.payload != "") {
                        SKelegram.addMessage(SKelegram.payload);
                    }
                    return null;
                }
            };
            new Thread(task).start();
        }
        
        
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public static void addMessage(String payload) {
        SKelegram.getMessages().add(new Label(payload));
        SKelegram.getRootController().update();
    }
    
    /**
     * @return the nickname
     */
    public static String getNickname() {
        return nickname;
    }

    /**
     * @param nickname the nickname to set
     */
    public static void setNickname(String nickname) {
        SKelegram.nickname = nickname;
    }

    /**
     * @return the messages
     */
    public static ArrayList<Label> getMessages() {
        return messages;
    }

    /**
     * @return the rootScene
     */
    public static Scene getRootScene() {
        return rootScene;
    }
    
    /**
     *
     * @param controller
     */
    public static void setController(FXMLrootController controller) {
        SKelegram.rootController = controller;
    }
    
    /**
     * @return the rootController
     */
    public static FXMLrootController getRootController() {
        return rootController;
    }

	public static Client getClient() {
		return client;
	}
}