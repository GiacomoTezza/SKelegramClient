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
            msg = getInput().getText();
            getInput().setText("");
            payload = nick + ": " + msg + "\n&(end)&";
            pLenght = payload.length();
            SKelegram.getClient().send(payload);
        }
    }
    
    public void update() {
        if (getMsgbox().getChildren().size() >= 50) {
            for (int j = 0; j <= getMsgbox().getChildren().size() - 50; j++) {
                getMsgbox().getChildren().remove(getMsgbox().getChildren().get(0));
            }
        }
        System.out.println("INIZIO");
        getMsgbox().getChildren().clear();
        System.out.println("PULITO");
        for (int i = 0; i < SKelegram.getMessages().size(); i++) {
            System.out.println("AGGIUNGO " + i);
            getMsgbox().getChildren().add(SKelegram.getMessages().get(i));
            System.out.println("AGGIUNTO " + i);
//            SKelegram.getMessages().remove(SKelegram.getMessages().get(i));
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SKelegram.setController(this);
    }    

    /**
     * @return the msgbox
     */
    public VBox getMsgbox() {
        return msgbox;
    }

    /**
     * @return the input
     */
    public TextField getInput() {
        return input;
    }
    
}
