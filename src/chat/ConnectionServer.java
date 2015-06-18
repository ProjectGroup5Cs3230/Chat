package chat;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class ConnectionServer implements Runnable
{
    public ServerSocket server;
    public BufferedWriter serverMessage;
    public DataOutputStream output;
    public DataInputStream input;
    public ChatFrame sFrame;

    public ConnectionServer(ChatFrame serverFrame)
    {
        //server construct
    	sFrame = serverFrame;
    }
    @Override
    public void run()
    {
        try
        {
            server = new ServerSocket(8989);

            while(true)
            {
                Socket clientConnection = server.accept();

                //input = new BufferedReader (new InputStreamReader(clientConnection.getInputStream()));
                //serverMessage = new BufferedWriter(new OutputStreamWriter(clientConnection.getOutputStream()));
                this.input = new DataInputStream(clientConnection.getInputStream());
                this.output = new DataOutputStream(clientConnection.getOutputStream());

                while (clientConnection.isConnected() && !clientConnection.isClosed())
                {
                    try
                    {

                        String chatMessage = input.readUTF(); //read incoming message from client outUTF
                        //System.out.println("ConnectionServer");
                        //System.out.println(chatMessage);
                        String[] parts = chatMessage.split(":");
						
                        if (parts[1].equals("exit"))
                        {
                            endConnection();
                            clientConnection.close();
                            break;
                        }
                        else
                        {
                            sFrame.addTextToWindow(chatMessage);//send to method in server frame to append to output

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
            input.close();
            server.close();

        }catch(Exception e)
        {

        }
    }
    public void messageToClient(String message) throws IOException
    {
        try
        {

            output.writeUTF(message);//send message out to client
            output.flush();

        }
        catch(IOException e)
        {
            throw e;
        }

    }

}
