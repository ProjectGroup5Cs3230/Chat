
package chat;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;



public class Client implements Runnable
{
    DataOutputStream os;
   
        @Override
        public void run()
        {
             
            try 
            {
                Socket sock = new Socket("localhost", 8989);
                PrintStream pr = new PrintStream(sock.getOutputStream());
                
                InputStreamReader read = new InputStreamReader(System.in);
                BufferedReader ed = new BufferedReader(read);
               
                String temp = ed.readLine();
                pr.println(temp);
            }
            catch ( IOException e) 
            {
            // TODO auto-generated catch block
            e.printStackTrace();
        }
            }
    
   
}