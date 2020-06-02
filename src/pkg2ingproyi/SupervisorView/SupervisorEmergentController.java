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
            Driver newDriver = new Driver (
                usernameField.getText(),
                passwordField.getText(),
                nameField.getText(),
                surnameField.getText(),
                dniField.getText(),
                adminField.getText(),
                new ArrayList<>()
            );

    }

    public void cancelUserRegistration(javafx.event.ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void handleVehicleRegistration(javafx.event.ActionEvent actionEvent) {
        int newaxis = 0;
        int newwheel = 0;
        int newpax = 0;
        int newpermission = 0;
        int newdeposit = 0;
        double newconsumo = 0;
        double newinitialKm = 0;
        double newkilometers = 0;
        if (axis.getText() != null){ newaxis = Integer.parseInt(axis.getText());}
        if (wheel.getText() != null){newwheel = Integer.parseInt(wheel.getText());}
        if (pax.getText() != null){newpax = Integer.parseInt(pax.getText());}
        if (pax.getText() != null){newpermission = Integer.parseInt(permission.getText());}
        if (pax.getText() != null){newdeposit = Integer.parseInt(deposit.getText());}
        if (pax.getText() != null){newconsumo = Double.parseDouble(consumo.getText());}
        if (pax.getText() != null){newinitialKm = Double.parseDouble(initialKm.getText());}
        if (pax.getText() != null){newkilometers= Double.parseDouble(kilometers.getText());}


        Vehicle nuevoVehiculo = new Vehicle(
                id.getText(),
                chasis.getText(),
                brand.getText(),
                newaxis,
                newwheel,
                newpax,
                builtDate.getText(),
                AcquiredDate.getText(),
                newpermission,
                model.getText(),
                vehicleType.getText(),
                fuelType.getText(),
                false,
                newdeposit,
                newconsumo,
                newinitialKm,
                newkilometers,
                Main.appUser.getDptId()
        );
        NetworkPOSTRequester networkPOSTRequester = new NetworkPOSTRequester(APIRoutes.VEHICLES, nuevoVehiculo.toJSONString(), () -> Platform.runLater(() -> Notifications.create().title("").text("").showInformation()));
        networkPOSTRequester.start();
    }

    public void cancelVehicleRegistration(javafx.event.ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}

