package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
//import java.util.Scanner;

public class ConnectionServer implements Runnable {
	
	@Override
	public void run() {
		try {
			ServerSocket server = new ServerSocket(8989);
			Socket clientConnection = server.accept();
                        
			BufferedReader input = new BufferedReader (new InputStreamReader(clientConnection.getInputStream()));
			//String chatMessage;
			while (clientConnection.isConnected() && !clientConnection.isClosed()) {
                                
				String chatMessage = input.readLine();
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
