# Java Chat Application

This is a simple command-line chat application built in Java. It allows multiple clients to connect to a central server and chat with each other.

## Features

*   **Public messages:** Send messages to all connected users.
*   **Private messages:** Send private messages to a specific user.
*   **List users:** See a list of all online users.

## How to Run

1.  **Compile the code:**
    Open a terminal and navigate to the root directory of the project. Then, compile all the `.java` files:
    ```bash
    javac *.java
    ```

2.  **Start the server:**
    In the same terminal, start the server:
    ```bash
    java Server
    ```
    The server will start listening on port 5000.

3.  **Start a client:**
    Open a new terminal window and start a client:
    ```bash
    java Client
    ```
    You can open multiple terminal windows and run the `java Client` command in each to have multiple clients.

## How to Use

1.  **Enter a username:**
    When you first connect, you will be prompted to enter a unique username.

2.  **Send messages:**
    Simply type your message and press `Enter` to send it to everyone in the chat.

3.  **Commands:**
    The following commands are available:

    *   `/list`: Show a list of all online users.
    *   `/msg <username> <message>`: Send a private message to a specific user.

    **Example:**
    ```
    /msg Amanuel Hello, how are you?
    ```
    This will send the message "Hello, how are you?" to the user named "Amanuel".

## GUI Version

For a more user-friendly experience, a GUI version of the chat application is available. You can find the instructions on how to run and use the GUI version here:

[GUI Version Instructions](./gui_version/instruction.md)
