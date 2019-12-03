package pkg2ingproyi.LoginView;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pkg2ingproyi.Model.Usuario;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
    @FXML
    private AnchorPane canvas;

    Usuario user = new Usuario("tas", "String apellido", "String dni", "String contrasena", "String nombreUsuario");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                username.requestFocus();
                canvas.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
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
    }

    public void handleUserLogin(boolean isAdmin, Stage stageUser) throws IOException {
        String fxmlPath, windowTitle;
        if(!isAdmin){                                                   //Conductor Login
            fxmlPath = "../DriverView/DriverView.fxml";
            windowTitle = "Safe Journey: Conductor";
        }else{                                                          //Supervisor Login
            fxmlPath = "../SupervisorView/SupervisorMasterView.fxml";
            windowTitle = "Safe Journey: Supervisor";
        }
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlPath));
        Parent root = loader.load();

        Scene sceneDriver = new Scene(root);
        stageUser.setScene(sceneDriver);
        stageUser.setTitle(windowTitle);
        stageUser.show();
    }

    @FXML
    private void handleLoginButtonAction(ActionEvent event) throws IOException {
        /*     LOGIN ACTION     */
        String uName = username.getText();
        if (user.iniciarSesion(uName, password.getText())) {
            Stage stage = (Stage) loginButton.getScene().getWindow();
            handleUserLogin(user.isAdmin(uName), stage);
        } else //TODO ANIMATION FOR WRONG CREDENTIALS
            System.err.println("Wrong Credentials.");
    }

    @FXML
    private void handleCancelButtonAction(ActionEvent event) {
        System.exit(0);
    }
}
