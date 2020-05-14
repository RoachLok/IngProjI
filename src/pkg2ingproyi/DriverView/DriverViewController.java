/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ingproyi.DriverView;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.Notifications;
import pkg2ingproyi.Main;
import pkg2ingproyi.Model.Driver;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author jtabo_000
 */
public class DriverViewController implements Initializable {


    @FXML
    private JMenuBar menuBar;
    @FXML
    private AnchorPane driverSVHolder;
    @FXML
    private Node newLoadedPane = null;

    Driver driver = (Driver) Main.appUser;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (Main.appUser.isAdmin()) {
            Notifications.create().title("Autentication Error").text("Ha habido un problema al verificar tu usuario.").showError();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.exit(0);
            }
            System.exit(0);
        }

        try {
            newLoadedPane = (Node)FXMLLoader.load(getClass().getResource("visorConductorHome.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        driverSVHolder.getChildren().setAll(newLoadedPane);
    }

    @FXML
    private void handleDriverShowHomeAction(Event event) {
        try {
            newLoadedPane = (Node)FXMLLoader.load(getClass().getResource("visorConductorHome.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        driverSVHolder.getChildren().setAll(newLoadedPane);
    }

    @FXML
    private void handleDriverShowServicesAction(Event event) {
        try {
            newLoadedPane = (Node)FXMLLoader.load(getClass().getResource("visorConductorServices.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        driverSVHolder.getChildren().setAll(newLoadedPane);
    }

    @FXML
    private void handleDriverShowIncidencesAction(Event event) {
        try {
            newLoadedPane = (Node)FXMLLoader.load(getClass().getResource("visorConductorIncidences.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        driverSVHolder.getChildren().setAll(newLoadedPane);
    }


    @FXML
    private void handleDriverShowMessagesAction(Event event) {
        try {
            newLoadedPane = (Node)FXMLLoader.load(getClass().getResource("visorConductorMessages.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        driverSVHolder.getChildren().setAll(newLoadedPane);
    }
}
