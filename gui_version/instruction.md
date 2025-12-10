# Java Chat Application (GUI Version)

This is the GUI version of the Java Chat Application. It provides a graphical user interface for a more user-friendly experience.

## How to Run

1.  **Compile the code:**
    Open a terminal and navigate to the `gui_version` directory of the project. Then, compile all the `.java` files:
    ```bash
    javac *.java
    ```

2.  **Start the server:**
    In the same terminal, start the server:
    ```bash
    java Server
    ```
    The server will start listening on port 5000.

3.  **Start the GUI client:**
    Open a new terminal window and navigate to the `gui_version` directory. Then, run the `ChatGUI` class:
    ```bash
    java ChatGUI
    ```
    This will open the chat window.

## How to Use

1.  **Enter a username:**
    In the top-left text field, enter your desired username.

2.  **Connect to the server:**
    Click the "Connect" button to connect to the chat server.

3.  **Send messages:**
    Once connected, you can type your messages in the bottom text field and click "Send" or press `Enter` to send them.

4.  **Chat commands:**
    The same commands from the command-line version are available:

    *   `/list`: Show a list of all online users.
    *   `/msg <username> <message>`: Send a private message to a specific user.
    
    **JavaFX is required inorder to run this GUI version.**
