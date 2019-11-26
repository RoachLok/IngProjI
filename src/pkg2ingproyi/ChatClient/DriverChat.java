package pkg2ingproyi.ChatClient;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

public class DriverChat implements Initializable {

    @FXML
    private TextArea messagesArea;

    @FXML
    private TextField messageField;

    @FXML
    private Button sentMessage;

    @FXML
    private Label label;

    private String driverUsername, supervisorUsername;
    private InetAddress inetAddress;
    private int port = 56789;

    private  String driverUsernameGETUSERNAME = "Nachocalvo";
    private  String driverGETADMINNAME = "Josep";

    private MessageHandler messageHandler;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //label.setText("Chat con: "+supervisorUsername); //TODO: Style title. Picture, last connect/online, etc. Add setting for port.
        try {
            inetAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        DriverChat me = this;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                messageHandler = new MessageHandler(inetAddress.getHostAddress(), port, "Nachocalvo", driverGETADMINNAME, me);  //DEBUGGING PORPOISES:

                //Server Handshake//
                messageHandler.connectToServer();
            }
        });

        sentMessage.setOnAction((event) -> {
            messageHandler.sendMessage("MSG><"+driverUsernameGETUSERNAME+"><"+driverGETADMINNAME+"><"+messageField.getText());
        });
    }

    public void addText (String text){
        messagesArea.appendText("\n"+text);
    }
}
