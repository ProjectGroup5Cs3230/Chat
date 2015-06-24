package chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ConnectionServer implements Runnable {

    private ServerSocket server;
    private DataOutputStream output;
    private DataInputStream input;
    private ServerFrame sFrame;
    private final static Logger LOGGER = Logger.getLogger(ConnectionServer.class.getName());
    public ConnectionServer(ServerFrame serverFrame) {
        //server construct
        sFrame = serverFrame;
    }

    @Override
    public void run() {
        try {
            FileHandler fileHandler = new FileHandler("myLogFile.log");
            LOGGER.addHandler(fileHandler);

            server = new ServerSocket(8989);

            while(true) {
                Socket clientConnection = server.accept();

                //input = new BufferedReader (new InputStreamReader(clientConnection.getInputStream()));
                //serverMessage = new BufferedWriter(new OutputStreamWriter(clientConnection.getOutputStream()));
                this.input = new DataInputStream(clientConnection.getInputStream());
                this.output = new DataOutputStream(clientConnection.getOutputStream());

                while (clientConnection.isConnected() && !clientConnection.isClosed()) {

                    try {
                        String chatMessage = input.readUTF(); //read incoming message from client outUTF

                        if (chatMessage.equals("exit")) {
                            endConnection();
                            clientConnection.close();
                            break;
                        }
                        else {
                            chatMessage = "Client:"+chatMessage;
                            sFrame.addTextToWindow(chatMessage);//send to method in server frame to append to output
                        }
                    }
                    catch (NullPointerException e) {
                    }
                        LOGGER.log(Level.INFO, "NullPointerException in client connect");
                }
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void endConnection()
    {
    try {
            this.input.close();

        }
        catch(Exception e) {
            LOGGER.log(Level.SEVERE, "Unable to close input stream",e);
        }
        try {
            this.server.close();

        }
        catch(Exception e) {
            LOGGER.log(Level.SEVERE, "Unable to disconnect from server",e);
        }
    }
    public void messageToClient(String message) throws IOException {
        try {
            output.writeUTF(message);//send message out to client
            output.flush();
        }
        catch(IOException e) {
            LOGGER.log(Level.SEVERE, "Unable to send message to client");
            throw e;
        }
    }
}
