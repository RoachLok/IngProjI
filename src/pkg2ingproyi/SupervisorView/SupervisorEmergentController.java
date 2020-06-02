package pkg2ingproyi.SupervisorView;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import pkg2ingproyi.Main;
import pkg2ingproyi.Model.Driver;
import pkg2ingproyi.Model.Vehicle;
import pkg2ingproyi.Util.*;

public class SupervisorEmergentController implements Initializable {
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

    public void handleVehicleRegistration(javafx.event.ActionEvent actionEvent) {
        Notifications.create().title("Feature to be implemented").text("Esta característica aun no ha sido implementada.").showError();
    }

    public void cancelVehicleRegistration(javafx.event.ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
