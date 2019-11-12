package pkg2ingproyi.LoginView;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

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

    Preferences preference;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
    }

   /* public void handleUserLogin() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));

        userStage.setTitle("FXML Main");
        userStage.setScene(new Scene(root, 300, 275));
        userStage.show();
    }

    @FXML
    private void handleLoginButtonAction(ActionEvent event) {
        /*     LOGIN ACTION
        Usuario user = new Usuario();
        if (user.iniciarSesion(username.getText(),password.getText())) handleUserLogin();
        else{
            System.err.println("Wrong credentials.");
        }
    }*/

    @FXML
    private void handleCancelButtonAction(ActionEvent event) {
        System.exit(0);
    }
    
}
