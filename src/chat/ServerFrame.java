package chat;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ServerFrame extends ChatFrame {
    private static final long serialVersionUID = -787546267482940465L;
    private String outMessage = "";
    private ConnectionServer connectChat;
    protected Logger LOGGER = Logger.getLogger(ServerFrame.class.getName());
    private FileHandler fh;

    @Override
    final protected void sendMessage() {

        outMessage = (chatInput.getText());

            try {
                connectChat.messageToClient(outMessage);//send to server method to write to outstream
                outMessage = "Server: "+outMessage;
                addTextToWindow(outMessage);
            }
            catch (IOException ex) {
                LOGGER.log(Level.SEVERE, "Unable to process message from server to client");
                outMessage = "Server: "+outMessage;
                addTextToWindow(outMessage);
                chatOutput.append("Failed to send message.\n");
            }
            catch (NullPointerException npe) {
                LOGGER.log(Level.SEVERE, "Unable to process message from server to client");
                outMessage = "Server: "+outMessage;
                addTextToWindow(outMessage);
                chatOutput.append("Failed to send message.\n");
            }

        chatInput.setText("");
    }

    @Override
    final protected void startChat() {

        try {
            connectChat = new ConnectionServer(this);
            Thread startup = new Thread(connectChat);
            startup.start();
            chatOutput.append("starting localhost\n");
        }
        catch (Exception e) {
            e.printStackTrace();
            LOGGER.log(Level.SEVERE, "Exception in thread start up");
            chatOutput.append("Failed to start local host.");
        }
    }

    public ServerFrame() throws IOException {
        log();
        Runtime.getRuntime().addShutdownHook(new Thread(){public void run(){
            try {
                connectChat.endConnection();
            }
            catch(Exception e) {
                LOGGER.log(Level.SEVERE, "Exception in ChatFrame construct",e);
            }
        }});

        this.setupFrame("Server Connect");
    }
    public void log() throws IOException
    {
        FileHandler fileHandler = new FileHandler("myLogFile.log");
        LOGGER.addHandler(fileHandler);
    
    
    }

}
