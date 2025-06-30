import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
  public void run() throws UnknownHostException, IOException{
    // client socket
    int port = 8010;
    InetAddress address = InetAddress.getByName("localhost");
    Socket socket = new Socket(address, port);

    // client to server
    PrintWriter toServer = new PrintWriter(socket.getOutputStream());

    // server to client.
    BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

    String serverMessage = fromServer.readLine();
    System.out.println("Response from server: " + serverMessage);

    // close the streams
    toServer.close();
    fromServer.close();
    socket.close();
    System.out.println("Connection closed with " + socket.getRemoteSocketAddress() + ":" + socket.getPort());
    
  }

  public static void main(String[] args) {
    try{
      Client client = new Client();
      client.run();

    }catch(Exception e) {
      e.printStackTrace();
    }
  }
}
