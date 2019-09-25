package skelegram;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Main class of the app
 * @author Tezza Giacomo
 */
public class SKelegram extends Application {
    private static Scene openScene;
    private static Scene rootScene;
    public static Stage stage;
    private static String nickname;
    private static FXMLrootController rootController;
    private static FXMLopenController openController;
    private static Client client;
    
    /**
     * JavaFx main function
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        SKelegram.stage = stage;
        
        Parent rootFXML = FXMLLoader.load(getClass().getResource("FXMLroot.fxml"));
        Parent openFXML = FXMLLoader.load(getClass().getResource("FXMLopen.fxml"));
        
        rootScene = new Scene(rootFXML);
        openScene = new Scene(openFXML);
        
        rootScene.getStylesheets().add(getClass().getResource("style.css").toExternalFo‌​rm());
        openScene.getStylesheets().add(getClass().getResource("style.css").toExternalFo‌​rm());
        
        stage.setScene(openScene);
        stage.setTitle("SKelegram");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("media/icon.png")));
        
        stage.show();
        SKelegram.stage.setResizable(false);
        SKelegram.client = new Client("93.39.191.67", 45678);
        
        // Checking if the client has connected to the server
        if(SKelegram.getClient().connect() != 0) {
            openController.showError();
        }
    }

    /**
     * Main function that launch the JavaFX app
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
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
    
    /**
     * @return the openController
     */
    public static FXMLopenController getOpenController() {
        return openController;
    }

    /**
     * @param aOpenController the openController to set
     */
    public static void setOpenController(FXMLopenController aOpenController) {
        openController = aOpenController;
    }

    /**
     *
     * @return the client
     */
    public static Client getClient() {
            return client;
    }
}