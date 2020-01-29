package chat_server;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Main extends Application implements Runnable, Initializable {

    private Thread thread;
    private ServerSocket serverSocket;
    private int serverPort = 56789;
    private InetAddress inetAddress;
    private PrintWriter writer;

    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private LocalDateTime currentTime;

    private ArrayList<Client> clientList;
    private ArrayList<String> messageList;

    @FXML
    private Button startServer;
    @FXML
    private Button stopServer;
    @FXML
    TextArea textArea;
    @FXML
    private TextField portField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        portField.disableProperty().bind(startServer.disabledProperty());
        stopServer.disableProperty().bind(startServer.disabledProperty().not());

        startServer.setOnAction((event) -> {  //https://stackoverflow.com/questions/33986917/javafx-setonaction-not-applicable
            serverPort = Integer.parseInt(portField.getText());
            if (serverPort < 65536)
                serverStart();
            else {
                currentTime = LocalDateTime.now();
                textArea.appendText("\n["+dtf.format(currentTime)+']'+" ERROR. Invalid port value. Port valid range: 0 - 65535.");
            }
        });
        stopServer.setOnAction((event) -> {
            serverStop();
        });

        textArea.setWrapText(true);
        textArea.setEditable(false);
        currentTime = LocalDateTime.now();
        textArea.setText('['+dtf.format(currentTime)+']'+" READY. Waiting for user action.");
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("ServerView.fxml"));
        primaryStage.setTitle("SafeJourney Chat Server");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @Override
    public void run() {
        while(thread != null){
            try{
                Socket socket = serverSocket.accept();
                new ServerClientHandler (this, socket);
                Platform.runLater(() -> {
                    textArea.appendText("\n["+dtf.format(currentTime)+']'+" INFO. New Client Connected.");
                });
                Thread.sleep(200);
            }catch(Exception e){
                e.printStackTrace();
                serverStop();
            }
        }
    }

    private void serverStart(){
        try {
            serverSocket = new ServerSocket(serverPort);
            inetAddress = InetAddress.getLocalHost();
        } catch (IOException e) {
            e.printStackTrace();
        }
        clientList = new ArrayList<Client>();
        messageList = new ArrayList<>();

        thread = new Thread(this);
        thread.start();

        //Disable "Start Server" button.
        startServer.setDisable(true);
        currentTime = LocalDateTime.now();
        textArea.setText('['+dtf.format(currentTime)+']'+" RUNNING. Server launched in localhost: "+inetAddress.getHostAddress()+ ". Waiting for client.");
    }

    private void serverStop() {
        if (serverSocket != null) {
            for (Client client : clientList) sendMessage(client.getSocket(), "FIN. SERVER CLOSED.", false);
            thread = null;
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                clientList = null;
                messageList = null;
            }
            currentTime = LocalDateTime.now();
            textArea.appendText("\n["+dtf.format(currentTime)+']'+" STOPPED. Server stopped by user action.");
            //Enable "Start Server" button
            startServer.setDisable(false);
        }
    }

    void sendMessage(Socket clientSocket, String message, boolean sendRaw) {
        if (clientSocket != null && !clientSocket.isClosed()) {                                                     //User disconnected/doesn't exist?
            String[] parsedField = message.split("><");                                         //Message example: MSG><FROM><FOR><MSGCONTENT
                                                                                                                    //parse[0] = from; parse[1] = for; parse[2] = msg;
            for (Client destinatary : clientList) {
                if (destinatary.getUsername().equals(parsedField[1])) {                                             //Client "online".
                    try {
                        writer = new PrintWriter(destinatary.getSocket().getOutputStream(), true);
                        writer.println("MSG><"+parsedField[0]+"><"+parsedField[2]);
                        break;
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        writer.close();
                    }
                }
            }
            if (sendRaw)
                return;
            //Print log in server
            currentTime = LocalDateTime.now();
            textArea.appendText("\n["+dtf.format(currentTime)+']'+" INFO. User -> "+parsedField[0]+" sent message '"+parsedField[2]+"' to -> "+ parsedField[1]+".");
            //Store message for message history.
            storeMessage(parsedField[0], parsedField[1], parsedField[2], "MSG><"+parsedField[0]+"><"+parsedField[2]);
        }
    }

    private void storeMessage(String sender, String destinatary, String messageContent, String sentString) {
        /*** IMPLEMENT JSON ***/
        // All stored messages should be marked as not read.
        currentTime = LocalDateTime.now();
        textArea.appendText("\n["+dtf.format(currentTime)+']'+" INFO. "+sender+"'s message to -> "+destinatary+" was stored.");

        String fileName = "";

        for (Client client : clientList)
            if (client.getUsername().equals(sender))
                if (client.isAdmin())
                    fileName = sender + '-' + destinatary + ".log";
                else
                    fileName = destinatary + "-" + sender + ".log";
        try {
            FileWriter fileWriter = new FileWriter("src/chat_server/chat_logs/" + fileName, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter writer = new PrintWriter(bufferedWriter);
            writer.println(sentString);
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            writer.close();
        }
    }

    void markAsRead(String receiver){
        /*** Mark reviever messages as read ***/
        currentTime = LocalDateTime.now();
        textArea.appendText("\n["+dtf.format(currentTime)+']'+" INFO. "+receiver+" read his message.");
    }

    void addClient(Socket socket, String username, String adminUsername) {
        for (Client client : clientList) { //Checks if client is already in
            if (client.getUsername().equals(username))
                return;
        }
        if (adminUsername.charAt(0) == '.') {
            clientList.add(new ClientSupervisor(socket, username));
            return;
        }
        clientList.add(new ClientDriver(socket, username, adminUsername));
    }

    void removeClient(Socket socket, String username) {
        for (int i = 0; i < clientList.size(); i++){
            if (clientList.get(i).getUsername().equals(username)) {
                textArea.appendText("\nUser "+clientList.get(i).getUsername()+" disconnected. ");
                clientList.remove(i);
                textArea.appendText('('+clientList.size()+')'+" users left.");
                return;
            }
        }
    }

    void resendPastMessages(Socket clientSocket, String fileName) {
        try {
            File chatLog = new File("src/chat_server/chat_logs/" + fileName);

            BufferedReader br = new BufferedReader(new FileReader(chatLog));
            String line;
            while ((line = br.readLine()) != null)
                sendMessage(clientSocket, line, true);

            br.close();
        } catch (IOException e) {
            System.err.println("File not existing yet.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }


}
