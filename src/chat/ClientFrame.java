package chat;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JPanel;
import javax.swing.ScrollPaneConstants;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.JTextComponent;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JPanel;
import javax.swing.ScrollPaneConstants;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.JTextComponent;


public class ClientFrame extends JFrame
{
    private JScrollPane outputScrollPane;
    private JScrollPane inputScrollPane;
    private JTextArea chatOutput;
    private JTextArea chatInput;
    private JButton sendButton;
    private JButton connectButton;
    private String user;
    private String outMessage = "";
    public ConnectionClient connectChat;
    public ConnectionServer chat;

    public ClientFrame()
    {
        Runtime.getRuntime().addShutdownHook(new Thread(){public void run(){
        try
        {
            connectChat.endConnection();
        }
        catch(Exception e)
        {

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
            if(event.getKeyCode() == KeyEvent.VK_ENTER && event.getModifiers() == KeyEvent.CTRL_MASK)
            {



            }
            if(event.getKeyCode() == KeyEvent.VK_ENTER)
            {

            }
        }
        });

        sendButton = new JButton("Send");
        sendButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent event)
        {
            outMessage = "server:" + (chatInput.getText());

            try {
                connectChat.messageToServer(outMessage);//send string to method in clientconnect that writes to outstream
            } catch (IOException ex) {
                Logger.getLogger(ClientFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

            addTextToWindow(outMessage);

           chatInput.setText("");
        }
        });
        connectButton = new JButton("ClientConnect");
        connectButton.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e)
         {

             startChat();
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

    private void startChat()
    {

        try
        {
            connectChat = new ConnectionClient(this);
            Thread startup = new Thread(connectChat);
            startup.start();
            chatOutput.append("connected to localhost\n");

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void addTextToWindow(String text)
    {
        chatOutput.append(text + "\n");

    }
}
