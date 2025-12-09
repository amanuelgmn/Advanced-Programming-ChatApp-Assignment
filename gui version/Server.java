package Java_chatApp;

import java.net.*;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(5000);
            System.out.println("Server is running on port 5000.");

            while (true) {
                Socket client = server.accept();
                System.out.println("New client connected!");
                ClientHandler clientHandler = new ClientHandler(client);
                clientHandler.start();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
