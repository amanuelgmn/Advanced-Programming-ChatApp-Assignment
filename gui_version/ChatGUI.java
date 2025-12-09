package Java_chatApp.gui_version;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class ChatGUI extends Application {

    private TextArea chatArea;
    private TextField inputField;
    private TextField nameField;
    private Button sendButton;
    private Button connectButton;

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    private boolean connected = false;

    @Override
    public void start(Stage stage) {
        chatArea = new TextArea();
        chatArea.setEditable(false);

        inputField = new TextField();
        inputField.setPromptText("Type a message...");

        nameField = new TextField();
        nameField.setPromptText("Enter Username");

        sendButton = new Button("Send");
        sendButton.setDisable(true);
        sendButton.setOnAction(e -> sendMessage());

        connectButton = new Button("Connect");
        connectButton.setOnAction(e -> connectToServer());

        HBox topBar = new HBox(10, nameField, connectButton);
        HBox bottomBar = new HBox(10, inputField, sendButton);

        BorderPane root = new BorderPane();
        root.setTop(topBar);
        root.setCenter(chatArea);
        root.setBottom(bottomBar);

        Scene scene = new Scene(root, 500, 400);
        stage.setScene(scene);
        stage.setTitle("JavaFX Chat Client");
        stage.show();
    }

    private void connectToServer() {
        if (connected) return;

        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            appendText("⚠ Enter username first.");
            return;
        }

        try {
            socket = new Socket("localhost", 5000);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            appendText("✔ Connected to server!");
            connected = true;

            sendButton.setDisable(false);
            connectButton.setDisable(true);

            // Send username to server
            new Thread(() -> {
                try {
                    in.readLine(); // server asks for name
                    out.println(name);

                    String msg;
                    while ((msg = in.readLine()) != null) {
                        appendText(msg);
                    }

                } catch (IOException e) {
                    appendText("❌ Disconnected.");
                }
            }).start();

        } catch (Exception e) {
            appendText("❌ Error connecting to server.");
        }
    }

    private void sendMessage() {
        String message = inputField.getText().trim();
        if (!message.isEmpty()) {
            out.println(message);  // Send to server
            inputField.clear();
        }
    }

    private void appendText(String text) {
        Platform.runLater(() -> chatArea.appendText(text + "\n"));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
