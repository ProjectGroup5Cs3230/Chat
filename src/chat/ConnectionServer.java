
package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;


public class ConnectionServer implements Runnable
{
    
    protected BufferedReader input;
    protected ServerSocket server;
    
    
    public ConnectionServer()
    {
    
    
    }
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
                        
                        String chatMessage = input.readLine();
                        

                        if (chatMessage.equals("exit")) 
                        {
                            clientConnection.close();
                            break;
                        }
                        
                    }
                            
                    catch (NullPointerException e)
                    {
                                    
                    }
                    	
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
            this.input.close();
            this.server.close();
            
        }catch(Exception e)
        {

        }
    }
}
