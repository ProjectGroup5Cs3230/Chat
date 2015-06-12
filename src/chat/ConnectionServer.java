package chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ConnectionServer implements Runnable {
	
	@Override
	public void run() {
		try {
			ServerSocket server = new ServerSocket(8989);
			Socket clientConnection = server.accept();

			Scanner input = new Scanner(clientConnection.getInputStream());
			String chatMessage;
			while (clientConnection.isConnected() && !clientConnection.isClosed()) {
				chatMessage = input.next();
				System.out.println(chatMessage);

				if (chatMessage.equals("exit")) {
					clientConnection.close();
					break;
				}
			}

			server.close();
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
