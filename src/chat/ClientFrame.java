package chat;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ClientFrame extends ChatFrame {
    private static final long serialVersionUID = 574163885414434105L;
    private String outMessage = "";
    private ConnectionClient connectChat;
    private final static Logger LOGGER = Logger.getLogger(ClientFrame.class.getName());
    private FileHandler fh;

    private void log() throws IOException {
        fh = new FileHandler("myLogFile.log");
        LOGGER.addHandler(fh);
    }

    @Override
    final protected void sendMessage() {

        outMessage = (chatInput.getText());

            try {
                connectChat.messageToServer(outMessage);//send string to method in clientconnect that writes to outstream
                outMessage = "Client: "+outMessage;
                addTextToWindow(outMessage);
            }
            catch (IOException ex) {
                LOGGER.log(Level.SEVERE, "IO Error in ClientFrame sendMesage",ex);
                outMessage = "Client: "+outMessage;
                addTextToWindow(outMessage);
                chatOutput.append("Failed to send message.\n");
            }
            catch (NullPointerException npe) {
                LOGGER.log(Level.SEVERE, "NPE in ClientFrame sendMessage",npe);
                outMessage = "Client: "+outMessage;
                addTextToWindow(outMessage);
                chatOutput.append("Failed to send message.\n");
            }

        chatInput.setText("");
    }

    @Override
    final protected void startChat() {
        try {
            connectChat = new ConnectionClient(this);
            Thread startup = new Thread(connectChat);
            startup.start();
            chatOutput.append("connected to localhost\n");
        }
        catch (Exception e) {
            e.printStackTrace();
            LOGGER.log(Level.SEVERE, "Exception in thread start",e);
            chatOutput.append("Could not connect to local host.");
        }
    }

    public ClientFrame() throws IOException {
        log();
        Runtime.getRuntime().addShutdownHook(new Thread(){public void run(){
            try {
                connectChat.endConnection();
            }
            catch(Exception e) {
                LOGGER.log(Level.SEVERE, "Exception in ClientFrame construct",e);
            }
        }});

        this.setupFrame("Client Connect");
    }

}
