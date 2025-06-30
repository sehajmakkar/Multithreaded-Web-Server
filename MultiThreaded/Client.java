import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

  public Runnable getRunnable() {
    return new Runnable() {
      @Override
      public void run(){
        int port = 8010;

        try {
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

        } catch (IOException e) {
          e.getStackTrace();
        }

      }
    };
  }

  public static void main(String[] args) {
    Client client = new Client();
    for(int i = 0; i < 100; i++){
      try{
        Thread thread = new Thread(client.getRunnable());
        thread.start();
      } catch(Exception e) {
        e.printStackTrace();
      }
    }
  }
  
}
