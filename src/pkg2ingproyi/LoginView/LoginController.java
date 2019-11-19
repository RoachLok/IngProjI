package pkg2ingproyi.LoginView;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
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
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                username.requestFocus();
            }
        });
    }

    public void handleUserLogin(char userType, Stage stageDriver) throws IOException {
        String fxmlPath, windowTitle;
        if(userType=='C'){  //Conductor Login
            fxmlPath = "../DriverView/DriverView.fxml";
            windowTitle = "Safe Journey: Conductor";
        }else{  //Supervisor Login
            fxmlPath = "../SupervisorView/SupervisorMasterView.fxml";
            windowTitle = "Safe Journey: Supervisor";
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlPath));
        Parent root = loader.load();

        Scene sceneDriver = new Scene(root);
        stageDriver.setScene(sceneDriver);
        stageDriver.setTitle(windowTitle);
        stageDriver.show();

    }

    @FXML
    private void handleLoginButtonAction(ActionEvent event) throws IOException {
        /*     LOGIN ACTION     */
        if (user.iniciarSesion(username.getText(), password.getText())) {
            Stage stage = (Stage) loginButton.getScene().getWindow();
            handleUserLogin(username.getText().charAt(0), stage);
          //  handleUserLogin('C', stage);
        } else
            System.err.println("Wrong Credentials.");
    }

    @FXML
    private void handleCancelButtonAction(ActionEvent event) {  System.exit(0); }

}
