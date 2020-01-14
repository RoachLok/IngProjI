package pkg2ingproyi.ChatClient;

import javafx.application.Platform;
import org.controlsfx.control.Notifications;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class MessageHandler implements Runnable{
    private Socket socket;
    private Thread thread;
    private BufferedReader dataIn;
    private PrintWriter dataOut;
    private String username;
    private String host;
    private String adminUsername;
    private int port;
    private DriverChat driverChat;
    private SupervisorChat supChat;

    MessageHandler(String host, int port, String username, String adminUsername, DriverChat driverChat) { //DriverChat constructor
        this.host = host;
        this.port = port;
        this.username = username;
        this.adminUsername = adminUsername;
        this.driverChat = driverChat;
    }

    MessageHandler(String host, int port, String username, SupervisorChat supChat) { //AdminChat constructor
        this.host = host;
        this.port = port;
        this.username = username;
        this.adminUsername = ".";
        this.supChat = supChat;
    }

    boolean connectToServer() {
        try {
            //Init socket and data interfaces.
            socket = new Socket(host, port);
            dataOut = new PrintWriter(socket.getOutputStream(), true);
            dataIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //Thread start
            thread = new Thread(this);
            thread.start();

            //Server Hello
            sendMessage("SYN><Hello Server!");

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

    void sendMessage(String message) {
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

    @Override
    public void run() {
            while (thread != null) {
                try {
                    String textIn = dataIn.readLine();

                    if (textIn.equals("SYN-ACK><Hey there!")) {
                        sendMessage("ACK><"+username+"><"+adminUsername);
                        System.out.println("Connected to server.");
                        //TODO Mark server as online. Is my admin online? / are my users online? / disablebutton.

                    } else if (textIn.startsWith("MSG")) {
                        String[] parsedField = textIn.substring(5).split("><");
                        if (adminUsername.equals("."))
                            supChat.addText(parsedField[1], false);
                        else
                            driverChat.addText(parsedField[1], false);
                        notifyReading(parsedField[0]);

                    } else if (textIn.startsWith("FIN")) {
                        disconnectFromServer();

                    } else if (textIn.startsWith("ACKMSG")) {  //Diferenciar entre sueprvisor y conductor

                        System.out.println(textIn.substring(6)+" ha leído tu mensaje.");
                        //TODO MARK MESSAGE AS READ
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    thread = null;
                }
            }
    }
    private void notifyReading(String from) {
        sendMessage("ACKMSG"+from);
    }
}
