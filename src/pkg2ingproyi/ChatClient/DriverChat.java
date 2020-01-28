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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import pkg2ingproyi.Main;
import pkg2ingproyi.Model.Driver;
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
    public Label receiverName;
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

    private Driver driver;
    private InetAddress inetAddress;
    private int port = 56789;
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private MessageHandler messageHandler;

    @Override
    public void initialize(URL location, ResourceBundle resources) { //TODO: Style title. Picture, last connect/online, etc. Add setting for port.
        driver = (Driver) Main.appUser;

        receiverName    .setText     (driver.getAdminNick());
        messagesArea    .setEditable (false);
        downloadingIcon .setVisible  (false);
        isConnectedIcon .setVisible  (false);

        try {
            inetAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        DriverChat thisDriverChat = this;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                messageHandler = new MessageHandler(inetAddress.getHostAddress(), port, driver.getUsername(), driver.getAdminNick(), thisDriverChat);
                //Server Handshake//
                if (messageHandler.connectToServer()) {
                    isNotConnectedIcon  .setVisible(false);
                    isConnectedIcon     .setVisible(true );
                }

                //Handle ENTER_KEY presses.
                parentAnchorPane.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        if (event.getCode() == KeyCode.ENTER) {
                            try {
                                handleSendButtonAction(new ActionEvent());
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
                handleSendButtonAction(new ActionEvent());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void handleSendButtonAction(ActionEvent event) throws IOException {
        String text = messageField.getText();
        if (text == null)
            return;
        messageHandler.sendMessage("MSG><" + driver.getUsername() + "><" + driver.getAdminNick() + "><" + text);
        addText(messageField.getText(), true);
        messageField.clear();
    }

    void addText(String text, boolean isSent) {
        LocalDateTime currentTime = LocalDateTime.now();
        if (!isSent) {
            messagesArea.appendText("\n[" + dtf.format(currentTime) + ']' + ' ' + driver.getAdminNick() + " envió: " + text + '.');
        } else {
            messagesArea.appendText("\n[" + dtf.format(currentTime) + ']' + " Has enviado: " + text + '.');
        }
    }

    public void handleQueryButtonAction(ActionEvent actionEvent) {
        System.err.println("Previous message query to be implemented");
    }
}
