import java.net.*;
import java.io.*;
  
public class Client extends Thread
{
    // initialize socket and input output streams
    private Socket socket            = null;
    private DataInputStream  input   = null;
    private DataInputStream in       =  null;
    private DataOutputStream out     = null;
    private String address;
    private int port;
    Game game;
  
    // constructor to put ip address and port
    public Client(String address, int port)
    {
        this.address = address;
        this.port = port;
        start();
    }

	@Override
	public void run() {
		// establish a connection
        try
        {
            socket = new Socket(address, port);
            System.out.println("Connected");
            game = new Game("Client",socket);
  
            // takes input from terminal
            input  = new DataInputStream(System.in);
  
            // sends output to the socket
            out    = new DataOutputStream(socket.getOutputStream());
            
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        }
        catch(UnknownHostException u)
        {
            System.out.println(u);
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
  
        // string to read message from input
        String goingMsg = "";
  
        // keep reading until "Over" is input
        while (!goingMsg.equals("Over"))
        {
            try
            {
                
            	
            	
                goingMsg = input.readLine();
                out.writeUTF(goingMsg);
                out.flush();
                
                String comingMsg= in.readUTF();
                
                if(!comingMsg.equals("")) {
                	game.addMessage(comingMsg, "Server");
                }
                
                
                
                
                //System.out.println("Server says: "+in.readUTF());
            }
            catch(IOException i)
            {
                System.out.println(i);
            }
        }
  
        // close the connection
        try
        {
            input.close();
            out.close();
            socket.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
	}
  
    
}