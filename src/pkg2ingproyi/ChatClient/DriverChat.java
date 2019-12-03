package pkg2ingproyi.ChatClient;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXTextArea;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class DriverChat implements Initializable {

    @FXML
    public JFXTextArea messagesArea;
    @FXML
    private JFXListView messagesList;
    @FXML
    private JFXButton queryOldMessage;
    @FXML
    private FontAwesomeIconView isConnectedIcon;
    @FXML
    private FontAwesomeIconView isNotConnectedIcon;
    @FXML
    private JFXButton settingsButton;
    @FXML
    private FontAwesomeIconView downloadingIcon;
    @FXML
    private AnchorPane parentAnchorPane;
    @FXML
    private TextField messageField;
    @FXML
    private Button sendMessage;

    private JFXPopup jfxPopup;

    private String driverUsername, supervisorUsername;
    private InetAddress inetAddress;
    private int port = 56789;
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    //TODO Get methods to be implemented in user classes
    private String driverUsernameGETUSERNAME = "Nachocalvo";
    private static String driverGETADMINNAME = "Joset";

    private MessageHandler messageHandler;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO: Style title. Picture, last connect/online, etc. Add setting for port.
        downloadingIcon.setVisible(false);
        isConnectedIcon.setVisible(false);
        messagesArea.setEditable(false);

        jfxPopup = new JFXPopup(settingsButton);
        settingsButton.setOnAction((event) -> {
            // jfxPopup.show(mymenu?, PopupVPosition.TOP, PopupHPosition.LEFT)
            System.err.println("To be implemented");
        });

        try {
            inetAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        DriverChat thisDriverChat = this;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                messageHandler = new MessageHandler(inetAddress.getHostAddress(), port, "Nachocalvo", driverGETADMINNAME, thisDriverChat);  //DEBUGGING PORPOISES - User methods to be created.
                //Server Handshake//
                if (messageHandler.connectToServer()) {
                    isNotConnectedIcon.setVisible(false);
                    isConnectedIcon.setVisible(true);
                    //sendMessage.setVisible(true);
                }

                parentAnchorPane.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        if (event.getCode() == KeyCode.ENTER) {
                            try {
                                handleLoginButtonAction(new ActionEvent());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

            }
        });

        sendMessage.setOnAction((event) -> {
            try {
                handleLoginButtonAction(new ActionEvent());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void handleLoginButtonAction(ActionEvent event) throws IOException {
        String text = messageField.getText();
        if (text == null)
            return;
        messageHandler.sendMessage("MSG><" + driverUsernameGETUSERNAME + "><" + driverGETADMINNAME + "><" + text);
        addText(messageField.getText(), true);
        messageField.clear();
    }

    void addText(String text, boolean isSent) {
        LocalDateTime currentTime = LocalDateTime.now();
        if (!isSent) {
            messagesArea.appendText("\n[" + dtf.format(currentTime) + ']' + ' ' + driverGETADMINNAME + " envió: " + text + '.');
        } else {
            messagesArea.appendText("\n[" + dtf.format(currentTime) + ']' + " Has enviado: " + text + '.');
        }
    }

    public void handleQueryButtonAction(ActionEvent actionEvent) {
        System.err.println("Previous message query to be implemented");
    }
}
