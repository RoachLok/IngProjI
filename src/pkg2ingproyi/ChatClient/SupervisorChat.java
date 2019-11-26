package pkg2ingproyi.ChatClient;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class SupervisorChat implements Initializable {
    @FXML
    public static ListView<String> driverListView;
    @FXML
    public Label topLabel;
    @FXML
    public Button sendMessage;
    @FXML
    public TextField messageField;
    @FXML
    public AnchorPane chatAnchorPane;
    @FXML
    public TextArea textArea1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        driverListView.getItems().add("Hello there");
    }
}
