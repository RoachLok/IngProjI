package pkg2ingproyi.ChatClient;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import pkg2ingproyi.Main;
import pkg2ingproyi.Model.Admin;
import pkg2ingproyi.Model.Driver;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class SupervisorChat implements Initializable {
    @FXML
    private Label               receiverNameLabel;
    @FXML
    private Label               statusLabel;
    @FXML
    private JFXButton           settingsButton;
    @FXML
    private JFXButton           queryOldMessage;
    @FXML
    private JFXButton           sendMessage;
    @FXML
    private TextField           messageField;
    @FXML
    private JFXTextArea         messagesArea;
    @FXML
    private JFXListView<HBox>   chatDriverList;
    @FXML
    private FontAwesomeIconView downloadingIcon;
    @FXML
    private FontAwesomeIconView isConnectedIcon;
    @FXML
    private FontAwesomeIconView isNotConnectedIcon;

    private Admin supervisor;
    private int port = 56789;
    String receiverName = "...";
    private InetAddress inetAddress;
    private MessageHandler messageHandler;
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    @Override
    public void initialize(URL location, ResourceBundle resources) { //TODO: Style title. Picture, last connect/online, etc. Add setting for port.
        supervisor = (Admin) Main.appUser;

        messagesArea    .setEditable (false);
        downloadingIcon .setVisible  (false);
        isConnectedIcon .setVisible  (false);

        /**  -------------- Drivers Chat List Initialization -------------- **/

        for (int i = 0; i < supervisor.driversCount(); i++) { //Creates and fills an HBox containing the driver's info.
            Driver driver = supervisor.getDriver(i);
            Label label = new Label(" -  " + driver.getUsername() + "  - ");
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.USER_SECRET);
            icon.setGlyphSize(20);
            Region region1 = new Region();
            HBox.setHgrow(region1, Priority.ALWAYS);

            HBox listItem = new HBox(icon, region1 , label);
            chatDriverList.getItems().add(listItem);
        }
        //Select first driver as chat driver.
        chatDriverList.getSelectionModel().select(0);



        try {
            inetAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        SupervisorChat thisSupervisorChat = this;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                messageHandler = new MessageHandler(inetAddress.getHostAddress(), port, supervisor.getUsername(), thisSupervisorChat);
                //Server Handshake//
                if (messageHandler.connectToServer()) {
                    isNotConnectedIcon  .setVisible(false);
                    isConnectedIcon     .setVisible(true );
                    updateCurrentChatDriver(0);
                }

                //Handle ENTER_KEY presses.
                sendMessage.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
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
        if (text.equals(""))
            return;
        messageHandler.sendMessage("MSG><" + supervisor.getUsername() + "><" + receiverName + "><" + text);
        addText(messageField.getText(), true, "");
        messageField.clear();
    }

    private void updateCurrentChatDriver(int driverIndex) {
        Driver displayDriver = supervisor.getDriver(driverIndex);

        receiverNameLabel.setText(receiverName = displayDriver.getUsername());
        handleQueryButtonAction();
    }

    public void handleDriverListClick(MouseEvent mouseEvent) {
        updateCurrentChatDriver(chatDriverList.getSelectionModel().getSelectedIndex());
    }


    void addText(String text, boolean isSent, String driverName) {
        LocalDateTime currentTime = LocalDateTime.now();
        String driverInTheChat = supervisor.getDriver(chatDriverList.getSelectionModel().getSelectedIndex()).getUsername();

        if (!isSent){
            if (!driverInTheChat.equals(driverName))
                return;
            messagesArea.appendText("\n["+dtf.format(currentTime)+']'+' '
                        + driverInTheChat
                        + " envió: "+text+'.');
        }else{
            messagesArea.appendText("\n["+dtf.format(currentTime)+']'+" Has enviado: "+text+'.');
        }
    }

    public void handleQueryButtonAction() {
        messagesArea.clear();
        int selectedDriverIndex = chatDriverList.getSelectionModel().getSelectedIndex();
        String driverInTheChat = supervisor.getDriver(selectedDriverIndex).getUsername();

        messageHandler.sendMessage("DL><" + supervisor.getUsername() + '-' + driverInTheChat + ".log");
    }
}

