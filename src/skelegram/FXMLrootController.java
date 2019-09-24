package skelegram;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
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
public class FXMLrootController implements Initializable {
    String nick;
    String msg;
    String payload;
    int pLenght;
    ArrayList messages;
    
    @FXML
    private TextField input;
    
    @FXML
    private VBox msgbox;

    @FXML
    void handleTextField(KeyEvent event) throws IOException {
        if (event.getCode() == ENTER) {
            nick = SKelegram.getNickname();
            msg = input.getText();
            input.setText("");
            payload = nick + ": " + msg + "\n&(end)&";
            pLenght = payload.length();
            SKelegram.getClient().send(payload);
        }
    }
    
    public void update() {
        if (msgbox.getChildren().size() >= 50) {
            for (int j = 0; j <= msgbox.getChildren().size() - 50; j++) {
                msgbox.getChildren().remove(msgbox.getChildren().get(0));
            }
        }
        for (int i = 0; i < SKelegram.getMessages().size(); i++) {
            msgbox.getChildren().add(SKelegram.getMessages().get(i));
            SKelegram.getMessages().remove(i);
        }

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SKelegram.setController(this);
    }    
    
}
