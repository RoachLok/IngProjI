package pkg2ingproyi.SupervisorView;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;
import java.net.URL;
import java.util.ResourceBundle;
import pkg2ingproyi.Model.Vehicle;
import pkg2ingproyi.Util.*;

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
    @FXML
    private JFXTextField id;
    @FXML
    private JFXTextField brand;
    @FXML
    private JFXTextField model;
    @FXML
    private JFXTextField consumo;
    @FXML
    private JFXTextField kilometers;
    @FXML
    private JFXTextField deposit;
    @FXML
    private JFXTextField pax;
    @FXML
    private JFXTextField builtDate;
    @FXML
    private JFXTextField AcquiredDate;
    @FXML
    private JFXTextField axis;
    @FXML
    private JFXTextField wheel;
    @FXML
    private JFXTextField vehicleType;
    @FXML
    private JFXTextField permission;
    @FXML
    private JFXTextField fuelType;
    @FXML
    private JFXTextField initialKm;
    @FXML
    private JFXTextField chasis;

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
        Vehicle nuevoVehiculo = new Vehicle(
                id.getText(),
                chasis.getText(),
                brand.getText(),
                Integer.parseInt(axis.getText()),
                Integer.parseInt(wheel.getText()),
                Integer.parseInt(pax.getText()),
                builtDate.getText(),
                AcquiredDate.getText(),
                Integer.parseInt(permission.getText()),
                model.getText(),
                vehicleType.getText(),
                fuelType.getText(),
                false,
                Integer.parseInt(deposit.getText()),
                Double.parseDouble(consumo.getText()),
                Double.parseDouble(initialKm.getText()),
                Double.parseDouble(kilometers.getText()),
                null
        );
        NetworkPOSTRequester networkPOSTRequester = new NetworkPOSTRequester(APIRoutes.VEHICLES, nuevoVehiculo.toJSONString(), () -> Platform.runLater(() -> Notifications.create().title("").text("").showInformation()));
        networkPOSTRequester.start();
    }

    public void cancelVehicleRegistration(javafx.event.ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}

