package skelegram;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
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
    ArrayList<String> messages;
    
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
        System.out.println("Update-Start");
        if (this.messages.size() >= 50) {
            System.out.println("Overflow");
            for (int j = 0; j <= this.messages.size() - 50; j++) {
                this.messages.remove(this.messages.get(0));
                System.out.println("Deleted");
            }
        }
        getMsgbox().getChildren().clear();
        for (String message : this.messages) {
            Label labelmsg = new Label(message);
            labelmsg.setWrapText(true);
            labelmsg.setId("Message");
            getMsgbox().getChildren().add(labelmsg);
        }
        System.out.println("Update-end\n\n");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SKelegram.setController(this);
        this.messages = new ArrayList<>();
        
        Task task1 = new Task() {
            
            @Override public Void call() {
                messages.add("Welcome to SKelegram chatroom:\n");
                update();
                
                while (true) {
                    if (SKelegram.getClient().incomingMessages.size() > 0) {
                        System.out.println("Arrivato: " + SKelegram.getClient().incomingMessages.get(0));
                        messages.add(SKelegram.getClient().incomingMessages.get(0));
                        SKelegram.getClient().incomingMessages.remove(0);
                        update();
                        System.out.println("Updatato");
                    }
                }
            }
        };
        
        Task task2 = new Task() {
            
            @Override public Void call() {
                while (true) {
                    System.out.println("ricevo..");
                    SKelegram.getClient().receive();
                }
            }
        };
        
        Thread thread1 = new Thread(task1);
        Thread thread2 = new Thread(task2);
        thread1.setDaemon(true);
        thread2.setDaemon(true);
        thread2.start();
        thread1.start();
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
