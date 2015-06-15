package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;


public class ConnectionServer implements Runnable 
{
    protected ChatFrame frame;
    protected BufferedReader input;
    protected ServerSocket server;
    
    @Override
    public void run()
    {
        try 
        {
            this.server = new ServerSocket(8989);
            while(true)
            {
                Socket clientConnection = server.accept();
                this.input = new BufferedReader (new InputStreamReader(clientConnection.getInputStream()));
                      
                while (clientConnection.isConnected() && !clientConnection.isClosed()) 
                {
                    try
                    {
                        frame.connected();
                        String chatMessage = input.readLine();
                        System.out.println(chatMessage);

                        if (chatMessage.equals("exit")) 
                        {
                            clientConnection.close();
                            break;
                        }
                        if(!chatMessage.equals(null))
                        {
                            frame.ServerText(chatMessage); 
                        }
                    }
                            
                    catch (NullPointerException e)
                    {
                                    
                    }
                    server.close();
                    input.close();	
                }
            }
                
        }	
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
    public void endConnection()
    {
        try
        {
            server.close();
            input.close();
        }
        catch(Exception e)
        {

        }
    }
}
