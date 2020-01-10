package pkg2ingproyi;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
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
        primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setTitle("Safe Journey Login");

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>()
        {
            public void handle(WindowEvent e){
                try {
                    Platform.exit();
                }
                catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

    }

    public static void main(String[] args) {
        launch(args);
    }

}
