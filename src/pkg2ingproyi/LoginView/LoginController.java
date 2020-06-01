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
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;
import pkg2ingproyi.Main;
import pkg2ingproyi.Util.UserUtils;
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
    private JFXPasswordField passwordField;
    @FXML
    private JFXTextField usernameField;
    @FXML
    private JFXButton loginButton;
    @FXML
    private AnchorPane canvas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                usernameField.requestFocus();
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
        usernameField.focusedProperty().addListener((ov, oldV, newV) -> {
            usernameField.setUnFocusColor(Color.valueOf("ff5b13"));
            usernameField.setFocusColor(Color.valueOf("ff5b13"));
        });
        passwordField.focusedProperty().addListener((observable) -> {
            passwordField.setUnFocusColor(Color.valueOf("ff5b13"));
            passwordField.setFocusColor(Color.valueOf("ff5b13"));
        });
    }

    private void handleUserLogin(boolean isAdmin, Stage stageUser) throws IOException {
        String fxmlPath, windowTitle;
        if(!isAdmin){                                                   //Conductor Login
            fxmlPath = "/pkg2ingproyi/DriverView/DriverView.fxml";
            windowTitle = "Safe Journey: Conductor";
        }else{                                                          //Supervisor Login
            stageUser.setResizable(true);
            stageUser.setMinHeight(600 );
            stageUser.setMinWidth (1000);
            stageUser.sizeToScene   ();
            fxmlPath = "/pkg2ingproyi/SupervisorView/SupervisorView.fxml";
            windowTitle = "Safe Journey: Supervisor";
        }

        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stageUser.setX((screenBounds.getWidth() - 1100) / 2);
        stageUser.setY((screenBounds.getHeight() - 700) / 2);

        Scene sceneDriver = new Scene(root);
        stageUser.setScene(sceneDriver);
        stageUser.setTitle(windowTitle);
        stageUser.show();
    }

    @FXML
    private void handleLoginButtonAction(ActionEvent event) throws IOException {
        /*     LOGIN ACTION     */
        String uName = usernameField.getText();
        if (UserUtils.login(uName, passwordField.getText())) {
            boolean isAdmin = UserUtils.isAdmin(uName);
            Main.appUser = UserUtils.createUser(uName, isAdmin);
            Stage stage = (Stage) loginButton.getScene().getWindow();
            handleUserLogin(isAdmin, stage);
        } else {
            passwordField.setText("");
            passwordField.setUnFocusColor(Color.DARKRED);
            passwordField.setFocusColor(Color.DARKRED);
            usernameField.setUnFocusColor(Color.DARKRED);
            usernameField.setFocusColor(Color.DARKRED);
            System.err.println("Wrong Credentials.");
            Notifications.create().title("Wrong Credentials").text("El usuario o contraseña introducidos no existen.").showError();
        }
    }

    @FXML
    private void handleCancelButtonAction(ActionEvent event) {
        System.exit(0);
    }
}
