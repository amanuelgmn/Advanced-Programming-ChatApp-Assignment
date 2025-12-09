package Java_chatApp.gui_version;
import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost",5000);
            System.out.println("connected to chat server.");

            BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
            //thread to listen to incoming messages
            new Thread(() -> {
                try {
                    String msg;
                    while ((msg = in.readLine()) != null){
                        System.out.println(msg);
                    } 
                }catch (IOException ignored){}
            }).start();

            //main thread sends messages
            String userInput;
            while((userInput = inputReader.readLine()) != null ){
                out.println(userInput);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

// Commands the user can use:
//
// /list                  -> show all online users
// /msg <username> <message>  -> send private message
//
// Example:
// /list
// /msg Amanuel hey bro!how are you!