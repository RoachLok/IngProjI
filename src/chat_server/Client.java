package chat_server;

import java.net.Socket;

class Client {
    private Socket socket;
    private String username;

    Client (Socket socket, String userName) {
        setSocket(socket);
        setUsername(userName);
    }

    String getUsername() {
        return username;
    }

    private void setUsername(String username) {
        this.username = username;
    }

    Socket getSocket() {
        return socket;
    }

    private void setSocket(Socket socket) {
        this.socket = socket;
    }

}
