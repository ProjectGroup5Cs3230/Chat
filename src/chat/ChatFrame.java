package chat;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.JTextComponent;

abstract public class ChatFrame extends JFrame {

	private static final long serialVersionUID = -4234270203676660298L;
	private JScrollPane outputScrollPane;
    private JScrollPane inputScrollPane;
    protected JTextArea chatOutput;
    protected JTextArea chatInput;
    private JButton sendButton;
    private JPanel panel;
    private JButton connectButton;

    final protected void moveCursorToEnd(JTextComponent textComponent) {
        textComponent.setCaretPosition(textComponent.getDocument().getLength());
    }

    protected abstract void sendMessage();

    protected abstract void startChat();

    protected void setupFrame(String connectButtonText) {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(350, 450));

        panel = new JPanel();
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

        connectButton = new JButton(connectButtonText);
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                    startChat(); //start thread
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
