package pkg2ingproyi.SupervisorView;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;
import java.net.URL;
import java.util.ResourceBundle;

public class SupervisorEmergentController implements Initializable {

    @FXML
    private JFXTextField usernameField;
    @FXML
    private JFXTextField passwordField;
    @FXML
    private JFXTextField nameField;
    @FXML
    private JFXTextField adminField;
    @FXML
    private JFXTextField surnameField;
    @FXML
    private JFXTextField dniField;
    @FXML
    private JFXButton cancelButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void handleUserRegistration(javafx.event.ActionEvent actionEvent) {
        Notifications.create().title("Feature to be implemented").text("Esta característica aun no ha sido implementada.").showError();
    }

    public void cancelUserRegistration(javafx.event.ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
