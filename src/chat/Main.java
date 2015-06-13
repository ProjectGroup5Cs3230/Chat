
package chat;

import java.util.*;


public class Main {

    public static void main(String[] args) 
	{
		
		//ChatFrame chat = new ChatFrame();
                ConnectionServer server = new ConnectionServer();
                Client connect = new Client();
                server.run();
                connect.run();
		
	}

}
    

