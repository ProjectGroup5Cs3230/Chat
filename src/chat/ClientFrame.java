package chat;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ScrollPaneConstants;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.JTextComponent;


public class ClientFrame extends JFrame {

    private static final long serialVersionUID = 574163885414434105L;
    private JScrollPane outputScrollPane;
    private JScrollPane inputScrollPane;
    private JTextArea chatOutput;
    private JTextArea chatInput;
    private JButton sendButton;
    private JButton connectButton;
    //private String user;
    private String outMessage = "";
    public ConnectionClient connectChat;

    private void moveCursorToEnd(JTextComponent textComponent) {
        textComponent.setCaretPosition(textComponent.getDocument().getLength());
    }

    private void sendMessage() {
        outMessage = (chatInput.getText());

            try {
                connectChat.messageToServer(outMessage);//send string to method in clientconnect that writes to outstream
                outMessage = "Client: "+outMessage;
                addTextToWindow(outMessage);
            }
            catch (IOException ex) {
                Logger.getLogger(ClientFrame.class.getName()).log(Level.SEVERE, null, ex);
                outMessage = "Client: "+outMessage;
                addTextToWindow(outMessage);
                chatOutput.append("Failed to send message.\n");

            }
            catch (NullPointerException npe)
            {
                Logger.getLogger(ClientFrame.class.getName()).log(Level.SEVERE, null, npe);
                outMessage = "Client: "+outMessage;
                addTextToWindow(outMessage);
                chatOutput.append("Failed to send message.\n");
            }


        chatInput.setText("");
    }

    private void startChat() {
        try {
            connectChat = new ConnectionClient(this);
            Thread startup = new Thread(connectChat);
            startup.start();
            chatOutput.append("connected to localhost\n");
        }
        catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(ClientFrame.class.getName()).log(Level.SEVERE, null, e);
            chatOutput.append("Could not connect to local host.");
        }
    }

    public ClientFrame() {
        Runtime.getRuntime().addShutdownHook(new Thread(){public void run(){
            try {
                connectChat.endConnection();
            }
            catch(Exception e)
            {
                Logger.getLogger(ClientFrame.class.getName()).log(Level.SEVERE, null, e);
            }
        }});

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(350, 450));

        final JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(325, 400));

        chatOutput = new JTextArea();
        chatOutput.setEditable(false);

        outputScrollPane = new JScrollPane(chatOutput);
        outputScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        outputScrollPane.setPreferredSize(new Dimension(300, 300));

        chatInput = new JTextArea();
        inputScrollPane = new JScrollPane(chatInput);
        inputScrollPane.setPreferredSize(new Dimension(300, 50));

        chatInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent event) {
                if(event.getKeyCode() == KeyEvent.VK_ENTER && event.getModifiers() == KeyEvent.CTRL_MASK) {
                    sendMessage();
                }
                if(event.getKeyCode() == KeyEvent.VK_ENTER) {
                    // It already adds newline when pressed.  Left here in case
                    // additional functionality required.
                }
            }
        });

        sendButton = new JButton("Send");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                sendMessage();
            }
        });

        connectButton = new JButton("Client Connect");
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startChat();//start thread
            }
        });

        panel.add(outputScrollPane);
        panel.add(inputScrollPane);
        panel.add(sendButton);
        panel.add(connectButton);
        add(panel);

        setVisible(true);

        chatInput.requestFocus();
    }

    public void addTextToWindow(String text) {
        chatOutput.append(text + "\n");
        moveCursorToEnd(chatOutput);
        chatInput.setText("");
        chatInput.requestFocus();
    }
}
