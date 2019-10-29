package pkg2ingproyi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
    * @author jtabo_000
 */
public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {      
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("/LoginView/Login.fxml"));

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Safe Journey Login");

    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
