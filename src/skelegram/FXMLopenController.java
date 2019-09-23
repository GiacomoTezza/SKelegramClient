package skelegram;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import static javafx.scene.input.KeyCode.ENTER;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author Tezza Giacomo
 */
public class FXMLopenController implements Initializable {
    String nick;

    @FXML
    private TextField nickInput;

    @FXML
    void handleTextField(KeyEvent event) {
        if (event.getCode() == ENTER) {
            nick = nickInput.getText();
            SKelegram.setNickname(nick);
            SKelegram.stage.setScene(SKelegram.getRootScene());
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
