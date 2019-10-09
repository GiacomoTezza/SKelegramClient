package skelegram.UI;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import static javafx.scene.input.KeyCode.ENTER;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author Tezza Giacomo
 */
public class FXMLopenController implements Initializable {
	private String nickname;
	
    @FXML
    private TextField nickInput;
    
    @FXML
    private Label errorlabel;

    @FXML
    void handleTextField(KeyEvent event) {
        if (event.getCode() == ENTER) {
        	nickname = nickInput.getText();
        	System.out.println("Entered nik -> " + nickname);
            //SKelegram.stage.setScene(SKelegram.getRootScene());
        }
    }
    
    public void showError() {
        errorlabel.setVisible(true);
        nickInput.setVisible(false);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	this.nickname = "";
        errorlabel.setVisible(false);
    }

	public String getNickname() {
		return nickname;
	}
    
}
