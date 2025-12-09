package Java_chatApp;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.io.*;
public class ClientHandler extends Thread{
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private String clientName;
    private static List<ClientHandler> clients = Collections.synchronizedList(new ArrayList<>());

    public ClientHandler(Socket socket) throws Exception {
        this.socket = socket;
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        output = new PrintWriter(socket.getOutputStream(),true);
        clients.add(this);
    }

    @Override
    public void run(){
        try {
            output.println("Enter your name: ");
            clientName = input.readLine();
            broadcast("🆕 " + clientName + " joined the chat.");
            
            String message;
            while ( (message = input.readLine()) != null ){
                broadcast(clientName + ": " + message);
            }
        } catch (IOException e) {
            System.out.println("Client disconnected.");
        } finally {
            try {
                clients.remove(this);
                broadcast("❌ " + clientName + " left the chat.");
                socket.close();
            }catch (IOException igonored){}  
        }
    }
    private void broadcast(String message) {
        synchronized (clients){
            for (ClientHandler client : clients){
                client.output.println(message);
            }
        }
    }
    }



