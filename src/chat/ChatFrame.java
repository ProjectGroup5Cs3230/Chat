
package chat;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ScrollPaneConstants;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.JTextComponent;

public class ChatFrame extends JFrame{
    
    private static final long serialVersionUID = -787546267482940465L;
	private JScrollPane outputScrollPane;
	private JScrollPane inputScrollPane;
	private JTextArea chatOutput;
	private JTextArea chatInput;
	private JButton sendButton;
	
	private void moveCursorToEnd(JTextComponent textComponent) {
		textComponent.setCaretPosition(textComponent.getDocument().getLength());
	}
	
	private void submitInput() {
		chatOutput.append(chatInput.getText() + "\n");
		moveCursorToEnd(chatOutput);
		chatInput.setText("");
		chatInput.requestFocus();
	}
			
	public ChatFrame() {
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
		chatInput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent event) {
				if(event.getKeyCode() == KeyEvent.VK_ENTER && event.getModifiers() == KeyEvent.CTRL_MASK)
				{
					submitInput();
				}
				if(event.getKeyCode() == KeyEvent.VK_ENTER)
				{
					chatInput.append("");
				}
			}
		});
		
		inputScrollPane = new JScrollPane(chatInput);
		inputScrollPane.setPreferredSize(new Dimension(300, 50));
		
		sendButton = new JButton("Send");
		sendButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				submitInput();
			}
		});
		
		panel.add(outputScrollPane);
		panel.add(inputScrollPane);
		panel.add(sendButton);
		add(panel);
		
		setVisible(true);
		
		chatInput.requestFocus();
	}
	
	public void output(String output) {
		chatOutput.append(output + "\n");
		moveCursorToEnd(chatOutput);
		chatInput.requestFocus();
		moveCursorToEnd(chatInput);
	}
}
    
    

