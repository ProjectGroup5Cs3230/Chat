
package chat;

import java.io.BufferedReader;
import java.io.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;



public class Client implements Runnable
{
    protected Socket sock;
    protected BufferedReader ed;
    protected InputStreamReader read;
    protected String temp;  
        @Override
        public void run()
        {
             
            try 
            {
                this.sock = new Socket("localhost", 8989);
                PrintStream pr = new PrintStream(sock.getOutputStream());
                
                this.read = new InputStreamReader(System.in);
                this.ed = new BufferedReader(read);
               
                this.temp = ed.readLine();
                pr.println(temp);
            }
            catch ( IOException e) 
            {
            // TODO auto-generated catch block
            e.printStackTrace();
        }
            }
    
   
}