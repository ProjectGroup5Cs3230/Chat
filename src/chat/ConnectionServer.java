
package chat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class ConnectionServer implements Runnable
{
    
    protected BufferedReader input;
    protected ServerSocket server;
    protected BufferedWriter serverMessage;
    protected Socket clientConnection;
    
    public ConnectionServer()
    {
    
        //server contruct
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
    public void messageToServer(String message) throws IOException
    {
        try
        {
            serverMessage = new BufferedWriter(new OutputStreamWriter(clientConnection.getOutputStream()));
            serverMessage.write(message);
            serverMessage.flush();
            
        }
        catch(IOException e)
        {
            throw e;
        }

    }

}
