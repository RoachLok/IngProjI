package chat_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerClientHandler implements Runnable {
    private Main server;
    private Socket socket;
    private Thread thread;
    private BufferedReader dataIn;
    private PrintWriter dataOut;
    private String text;
    private String username;


    ServerClientHandler(Main server, Socket clientSocket) {
        this.server = server;
        this.socket = clientSocket;
        try {
            dataIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            dataOut = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        int timeoutCounter = 0;
        while (thread != null) {
            try {
                String textIn = dataIn.readLine();        //TODO Change for switch.
                if (textIn.startsWith("SYN")) {
                    if (timeoutCounter < 3) {
                        timeoutCounter++;
                        dataOut.println("SYN-ACK><Hey there!");
                    }else {
                        server.sendMessage(socket, "FIN. USER TIMED-OUT", true);
                        disconnectUser();
                    }
                } else if (textIn.startsWith("ACKMSG")){                                                        //Message was read.
                    server.markAsRead(textIn.substring(6));

                } else if (textIn.startsWith("ACK")) {                                                          //Client has handshaked
                                                                                                                //Sample admin message: ACKMyname><.     Notice admin's admin is .
                                                                                                                //Sample driver message: ACKMyname><MyAdminName
                    String[] parsedField = textIn.substring(5).split("><");
                    server.addClient(socket, parsedField[0], parsedField[1]);

                } else if (textIn.startsWith("MSG")) {                                                          //User sending message to user.
                    server.sendMessage(socket, textIn.substring(5), false);
                } else if (textIn.startsWith("DL")) {
                    server.resendPastMessages(socket, textIn.substring(4));
                } else if (textIn.startsWith("FIN"))                                                            //User disconnecting.
                    disconnectUser();

            } catch (IOException e) {
                e.printStackTrace();
                disconnectUser();
            }
        }

        //server.textArea.appendText("\nCommunication stopped for Client:(" + userName + ")");
    }

    public void disconnectUser() {
        server.textArea.appendText("\nDisconnecting Client: (" + username + ")...");
        server.removeClient(socket, username);
        stopConnection();
    }

    public void stopConnection() {
        thread = null;

        // Close toClient
        dataOut.close();

        // Close fromClient
        try {
            dataIn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Close the socket
        try {
            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}


