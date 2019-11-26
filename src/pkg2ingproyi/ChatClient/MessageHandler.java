package pkg2ingproyi.ChatClient;

import javafx.application.Platform;
import org.controlsfx.control.Notifications;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class MessageHandler implements Runnable{
    private Socket socket;
    private Thread thread;
    private BufferedReader dataIn;
    private PrintWriter dataOut;
    private String username;
    private String host;
    private String adminUsername;
    private DriverChat chatWindow;
    private int port;


    MessageHandler(String host, int port, String username, String adminUsername, DriverChat chatWindow) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.adminUsername = adminUsername;
        this.chatWindow = chatWindow;
    }

    public boolean connectToServer() { //TODO HANDSHAKE
        try {
            //Init socket and data interfaces.
            socket = new Socket(host, port);
            dataOut = new PrintWriter(socket.getOutputStream(), true);
            dataIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //Thread start
            thread = new Thread(this);
            thread.start();

            //Server Handshake
            int timeoutServer = 0;
            while (timeoutServer < 4) {
                sendMessage("SYN><Hello Server!");
                if (dataIn.readLine().equals("SYN-ACK><Hey There!")) {
                    sendMessage("ACK><"+username+"><"+adminUsername);
                } else
                    timeoutServer++;
            }

        } catch (UnknownHostException e) {
            Platform.runLater(() -> {
                Notifications.create().title("Unknown Host").text("Can't find specified host:\n[" + e.getMessage() + "]").showError();
            });
            return false;
        } catch (IOException e) {
            Platform.runLater(() -> {
                Notifications.create().title(e.getMessage()).text("Can't connect to server:\n[" + host + "]").showError();
            });
            e.printStackTrace();
            return false;
        }
        return true;

    }

    public void sendMessage(String message) {
        dataOut.println(message);
    }

    public String getUsername() {
        return username;
    }

    public String getAdminUsername() {
        return adminUsername;
    }

    private void disconnectFromServer() {
        thread = null;
        // Close data interfaces
        dataOut.close();
        try {
            dataIn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Close socket
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String textIn;
    @Override
    public void run() {
        while (thread != null) {
            try {
                textIn = dataIn.readLine();
                if (textIn.startsWith("MSG")) {
                    String[] parsedField = textIn.substring(5).split("><");
                    chatWindow.addText(parsedField[1]);

                } else if (textIn.startsWith("FIN")) {
                    disconnectFromServer();
                } else if (textIn.startsWith("ACKMSG")){  //Diferenciar entre sueprvisor y conductor
                    String[] parsedField = textIn.substring(8).split("><");
                    // MARK MESSAGE AS READ

                }
            } catch (IOException e) {
                e.printStackTrace();
                thread = null;
            }
        }
    }
}
