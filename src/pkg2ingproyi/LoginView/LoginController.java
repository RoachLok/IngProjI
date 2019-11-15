package pkg2ingproyi.LoginView;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pkg2ingproyi.Model.Usuario;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

/**
 * FXML Controller class
 *
 * @author jtabo_000
 */
public class LoginController implements Initializable {

    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXTextField username;
    @FXML
    private JFXButton loginButton;

    Preferences preference;
    Usuario user = new Usuario();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        username.requestFocus();
    }

    public void handleAdminLogin() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("SupervisorView/SupervisorMasterView.fxml"));
        Parent root = loader.load();

        Scene sceneDriver = new Scene(root);
        Stage stageDriver = new Stage();
        stageDriver.setScene(sceneDriver);
        stageDriver.show();
        stageDriver.setTitle("Safe Journey: Supervisor");
    }

    public void handleDriverLogin() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("DriverView/DriverView.fxml"));
        Parent root = loader.load();

        Scene sceneDriver = new Scene(root);
        Stage stageDriver = new Stage();
        stageDriver.setScene(sceneDriver);
        stageDriver.show();
        stageDriver.setTitle("Safe Journey: Conductor");
    }

    @FXML
    private void handleLoginButtonAction(ActionEvent event) throws IOException {
        /*     LOGIN ACTION     */
        if (user.iniciarSesion(username.getText(), password.getText())) {
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.close();
            if (username.getText().charAt(0) == 'C')
                handleDriverLogin();
            else //meter por argumento charAt(0) a handleLogin.
                handleAdminLogin();
        } else
            System.err.println("Wrong Credentials.");
    }

    @FXML
    private void handleCancelButtonAction(ActionEvent event) {
        System.exit(0);
    }

}
