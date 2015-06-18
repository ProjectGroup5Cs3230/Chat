
package chat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ConnectionClient implements Runnable
{
    //protected BufferedReader input;
   // protected BufferedWriter clientMessage;
    protected Socket serverConnection;
    public clientFrame cFrame;
    public DataOutputStream output;
    public DataInputStream input;
    
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
            
            
            //input = new BufferedReader (new InputStreamReader(serverConnection.getInputStream()));
            //clientMessage = new BufferedWriter(new OutputStreamWriter(serverConnection.getOutputStream()));   
            this.input = new DataInputStream(serverConnection.getInputStream());
            this.output = new DataOutputStream(serverConnection.getOutputStream());   
            while(serverConnection.isConnected() && !serverConnection.isClosed())
            {

                try
                {

                    String incomingMessage = input.readUTF();//read message from server


                    if (incomingMessage.equals("exit")) 
                    {
                        serverConnection.close();
                        break;
                    }
                    else
                    {
                       cFrame.addTextToWindow(incomingMessage);//send to method in client frame to append to chatoutput
                    }

                }
                catch(NullPointerException e)
                {

                }

            }    
            
            
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
    public void messageToServer(String message) throws IOException
    {
        try
        {
            
            output.writeUTF(message);//send message out to server
            
        }
        catch(IOException e)
        {
            throw e;
        }
    }
    
}
