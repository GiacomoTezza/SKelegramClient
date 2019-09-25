package skelegram;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
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
public class FXMLrootController implements Initializable {
    String nick;
    String msg;
    String payload;
    int pLenght;
    ArrayList<Label> messages;
    
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
        getMsgbox().getChildren().clear();
        for (int i = 0; i < messages.size(); i++) {
            getMsgbox().getChildren().add(messages.get(i));
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SKelegram.setController(this);
        this.messages = new ArrayList<>();
        
        Task task = new Task() {
            @Override public Void call() {
                Label welcome = new Label("Welcome to SKelegram chatroom:\n");
                welcome.setWrapText(true);
                welcome.setId("Message");
                messages.add(welcome);
                update();
                while (true) {
                    String payload = SKelegram.getClient().receive();
                    if (!"".equals(payload)) {
                        Label msg = new Label(payload);
                        msg.setWrapText(true);
                        msg.setId("Message");
                        messages.add(msg);
                        update();
                    }
                }
            }
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
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
