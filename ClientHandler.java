package Java_chatApp;
import java.io.*;
import java.net.*;
import java.util.*;

public class ClientHandler extends Thread {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private String clientName;

    // Store all clients + for name lookup
    private static List<ClientHandler> clients = Collections.synchronizedList(new ArrayList<>());
    private static Map<String, ClientHandler> clientMap = Collections.synchronizedMap(new HashMap<>());

    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        clients.add(this);
        }   

    @Override
    public void run() {
        try {
            // Ask for unique name
            while (true) {
                out.println("Enter a username: ");
                String name = in.readLine();

                synchronized (clientMap) {
                    if (!clientMap.containsKey(name)) {
                        clientName = name;
                        clientMap.put(clientName, this);
                        break;
                    } else {
                        out.println("Username already taken, choose another.");
                    }
                }
            }

            broadcast("🆕 " + clientName + " joined the chat.");

            String message;
            while ((message = in.readLine()) != null) {

                if (message.equalsIgnoreCase("/list")) {
                    listUsers();
                    continue;
                }

                if (message.startsWith("/msg ")) {
                    privateMessage(message);
                    continue;
                }
                // Normal message broadcast
                broadcast(clientName + ": " + message);
            }

        } catch (IOException e) {
            System.out.println(clientName + " disconnected.");
        } finally {
            try {
                clients.remove(this);
                clientMap.remove(clientName);
                broadcast("❌ " + clientName + " left the chat.");
                socket.close();
            } catch (IOException ignored) {}
        }
    }

    // Broadcast to all
    private void broadcast(String message) {
        synchronized (clients) {
            for (ClientHandler client : clients) {
                client.out.println(message);
            }
        }
    }

    // /list command
    private void listUsers() {
        out.println("---- Online Users ----");
        synchronized (clientMap) {
            for (String name : clientMap.keySet()) {
                out.println("• " + name);
            }
        }
        out.println("----------------------");
    }

    // /msg <user> <message>
    private void privateMessage(String message) {
        String[] parts = message.split(" ", 3);

        if (parts.length < 3) {
            out.println("Usage → /msg <user> <message>");
            return;
        }

        String target = parts[1];
        String msgText = parts[2];

        ClientHandler receiver = clientMap.get(target);

        if (receiver == null) {
            out.println("User '" + target + "' not found.");
        } else {
            receiver.out.println("🔏 PM from " + clientName + ": " + msgText);
            out.println("You → " + target + ": " + msgText);
        }
    }
}
