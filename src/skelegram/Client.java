package skelegram;

import com.sun.org.apache.bcel.internal.classfile.Utility;
import static com.sun.org.apache.bcel.internal.classfile.Utility.replace;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Label;

/**
 *
 * @author Tezza Giacomo
 */
public class Client extends Thread{
    private String host;
    private int port;
    
    private ArrayList<String> messages;
    
    private Socket clientSocket;
    private DataOutputStream output;
    private BufferedReader input;
    
    public Client() {
        this("127.0.0.1", 45678);
    }
    
    public Client(String host, int port) {
        this.host = host;
        this.port = port;
        this.messages = new ArrayList<>();
    }
    
    @Override
    public void run() {
        System.out.println("Ciao dal tuo thread di quartiere");
        connect();
        
        while (true) {
            String payload = receive();
            SKelegram.addMessage(payload);
            //SKelegram.getRootController().update();
        }
    }
    
    public void connect(){
        try {
            clientSocket = new Socket(this.getHost(), this.getPort());
            output = new DataOutputStream(clientSocket.getOutputStream());
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            System.out.println("Connesso!");
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void send(String payload){
        try {
            output.writeBytes(payload);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            close();
        }
    }
    
    public String receive() {
        String k = "";
        
        try {
            while (!input.ready()){
            }
            k = input.readLine();
            System.out.println(k);
        } catch (IOException ex) {
            System.out.println(ex);
            close();
        }
        k = k.replace("&(end)&", "");
        return k;
    }
    
    public void close() {
        try {
            clientSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * @param host the host to set
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * @return the messages
     */
    public ArrayList<String> getMessages() {
        return messages;
    }
}
