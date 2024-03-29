package skelegram;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Usefull function for implementing socket
 * @author Tezza Giacomo
 */
public class Client{
    private String host;
    private int port;
    
    private ArrayList<String> messages;
    
    private Socket clientSocket;
    private DataOutputStream output;
    private BufferedReader input;
    
    /**
     * Constructor for localhost
     */
    public Client() {
        this("127.0.0.1", 45678);
    }
    
    /**
     * Basic constructor
     * @param host
     * @param port
     */
    public Client(String host, int port) {
        this.host = host;
        this.port = port;
        this.messages = new ArrayList<>();
    }
    
    /**
     * Function that create a socket connection and 
     * open the input and output stream
     * @return if there were error in connection (-1) or (0) if all gone right
     * 
     */
    public int connect(){
        try {
            clientSocket = new Socket(this.getHost(), this.getPort());
            output = new DataOutputStream(clientSocket.getOutputStream());
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            return 0;
        } catch (IOException ex) {
            return -1;
        }
    }
    
    /**
     * Function that use the socket to send a string
     * @param payload
     */
    public void send(String payload){
        try {
            output.writeBytes(payload);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            close();
        }
    }
    
    /**
     * Function that recive a string from the socket
     * @return the string recived
     */
    public String receive() {
        String k = "";
        
        try {
            if (input.ready()){
                k = input.readLine();
                System.out.println(k);
            }
        } catch (IOException ex) {
            System.out.println(ex);
            close();
        }
        return k;
    }
    
    /**
     * Function that close the connection of the socket
     */
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
