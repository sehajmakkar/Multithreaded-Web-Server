import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class Server {

  public Consumer<Socket> getConsumer(){
    // lambda
    return (clientSocket) -> {
      try {

        // server to client
        PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream());

        // client to server
        BufferedReader fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        // sent to the client
        toClient.println("Hello from the server!");

        // closing the streams
        toClient.close();
        fromClient.close();
        clientSocket.close();
        System.out.println("Connection closed with " + clientSocket.getRemoteSocketAddress() + ":" + clientSocket.getPort());

      } catch (Exception e) {
        e.printStackTrace();
      }
    };
  }

  public static void main(String[] args) {
    int port = 8010;
    Server server = new Server();
    try{
      ServerSocket serverSocket = new ServerSocket(port);
      serverSocket.setSoTimeout(10000); // 10 seconds timeout
      System.out.println("Server is listening on port " + port);

      while(true){
        Socket acceptedConnection = serverSocket.accept();
        Thread thread = new Thread(() -> server.getConsumer().accept(acceptedConnection));
        thread.start();

      }

    } catch(Exception e) {
      e.printStackTrace();
    }
  }
  
}
