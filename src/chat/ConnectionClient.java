package chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ConnectionClient implements Runnable {

    private Socket serverConnection;
    private DataOutputStream output;
    private DataInputStream input;
    private ClientFrame cFrame;
    private final static Logger LOGGER = Logger.getLogger(ConnectionClient.class.getName());

    public ConnectionClient(ClientFrame clientFrame) {
        //server contruct
        cFrame = clientFrame;
    }

    @Override
    public void run() {
        try {
            serverConnection = new Socket("localhost",8989);

            this.input = new DataInputStream(serverConnection.getInputStream());
            this.output = new DataOutputStream(serverConnection.getOutputStream());

            while(serverConnection.isConnected() && !serverConnection.isClosed()) {
                try {
                    String incomingMessage = input.readUTF();//read message from server

                    if (incomingMessage.equals("exit")) {
                        endConnection();
                        serverConnection.close();
                        break;
                    }
                    else {
                        incomingMessage = "Server:"+incomingMessage;
                        cFrame.addTextToWindow(incomingMessage);//send to method in client frame to append to chatoutput
                    }
                }
                catch(NullPointerException e) {

                }
            }
        }
        catch (NullPointerException e) {

        }
        catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Unable to connect to server",ex);
        }

    }

    public void endConnection() {
        try {
            this.input.close();


        }
        catch(Exception e) {
            LOGGER.log(Level.SEVERE, "Unable to close input stream",e);
        }
        try {
            this.serverConnection.close();

        }
        catch(Exception e) {
            LOGGER.log(Level.SEVERE, "Unable to disconnect from server",e);
        }
    }

    public void messageToServer(String message) throws IOException {
        try {
            output.writeUTF(message);//send message out to server
            output.flush();
        }
        catch(IOException e) {
            throw e;
        }
    }
}
