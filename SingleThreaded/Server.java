import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

  public void run() throws IOException {
      // server socket
      int port = 8010;
      ServerSocket socket = new ServerSocket(port);
      socket.setSoTimeout(10000); // 10sec

      while(true){
        try {
          System.out.println("Server is listening on " + port);

          // ready and waiting to accept a connection
          Socket acceptedConnection = socket.accept();

          System.out.println("Connection accepted from " + acceptedConnection.getRemoteSocketAddress() + ":" + acceptedConnection.getPort());

          // server to client
          PrintWriter toClient = new PrintWriter(acceptedConnection.getOutputStream()); // text to bytes

          // client to server
          BufferedReader fromClient = new BufferedReader(new InputStreamReader(acceptedConnection.getInputStream())); // bytes to text

          // sent to the client
          toClient.println("Hello from the server!");

          // closing the streams
          toClient.close();
          fromClient.close();
          acceptedConnection.close();
          socket.close();
          System.out.println("Connection closed with " + acceptedConnection.getRemoteSocketAddress() + ":" + acceptedConnection.getPort());

        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    
  }
  
  public static void main(String[] args) {
    Server server = new Server();
    try{
      server.run();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
