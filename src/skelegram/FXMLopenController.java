package skelegram;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private Label errorlabel;
    
    @FXML
    private ImageView image;

    @FXML
    void handleTextField(KeyEvent event) {
        if (event.getCode() == ENTER) {
            nick = nickInput.getText();
            SKelegram.setNickname(nick);
            SKelegram.stage.setScene(SKelegram.getRootScene());
        }
    }
    
    void showError() {
        errorlabel.setVisible(true);
        nickInput.setVisible(false);
        image.setImage(new Image(getClass().getResourceAsStream("media/offline.png")));
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SKelegram.setOpenController(this);
        errorlabel.setVisible(false);
    }
    
}
