package pkg2ingproyi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pkg2ingproyi.Model.User;

/**
 * @author jtabo_000
 */
public class Main extends Application {
    public static User appUser;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("LoginView/Login.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, Color.RED);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Safe Journey Login");
    }

    public static void main(String[] args) {
        launch(args);
    }

}
