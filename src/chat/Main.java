
package chat;

import java.util.*;


public class Main {

    public static void main(String[] args) 
	{
		
            ChatFrame chat = new ChatFrame();
            final ConnectionServer server = new ConnectionServer();
            Runtime.getRuntime().addShutdownHook(new Thread(){public void run(){
                    
                }});
            Thread listener = new Thread(server);
            listener.start();
               
		
	}

}
    

