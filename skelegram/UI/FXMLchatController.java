package skelegram.UI;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import static javafx.scene.input.KeyCode.ENTER;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Tezza Giacomo
 */
public class FXMLchatController implements Initializable {
    String nick;
    
    String payload;
    int pLenght;
    
    ArrayList<String> toSendRawMessages;
    
    @FXML
    private TextField input;
    
    @FXML
    private VBox msgbox;

    @FXML
    void handleTextField(KeyEvent event) throws IOException {
    	String msg;
    	
       
    	if (event.getCode() == ENTER) {
            msg = this.input.getText();
            this.input.setText("");
            getToSendRawMessages().add(msg);
        }
    }
    
    public void addMessage(String message) {
    	Platform.runLater(()->{
    		System.out.println("Adding message : " + message);
    		msgbox.getChildren().add(new Label(message));
    	});
    	
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.toSendRawMessages = new ArrayList<>();
    }

	public ArrayList<String> getToSendRawMessages() {
		return toSendRawMessages;
	}    
    
}
