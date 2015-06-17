
package chat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionClient implements Runnable
{
    protected BufferedReader input;
    protected BufferedWriter serverMessage;
    protected Socket serverConnection;
    
     public ConnectionClient()
    {
    
        //server contruct
    }
    
    
    
    @Override
    public void run() 
    {
        
        try 
        {
            serverConnection = new Socket("localhost",8989);
               
            this.input = new BufferedReader (new InputStreamReader(serverConnection.getInputStream()));
                      
        }        
                            
        catch (NullPointerException e)
        {

        } 
        catch (IOException ex) 
        {
            Logger.getLogger(ConnectionClient.class.getName()).log(Level.SEVERE, null, ex);
        }
          
    }
    
    public void endConnection()
    {
        try
        {
            this.input.close();
            this.serverConnection.close();
            
        }catch(Exception e)
        {

        }
    }
}
