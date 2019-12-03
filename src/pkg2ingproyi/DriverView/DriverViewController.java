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

    private Node newLoadedPane = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
    private void handleDriverShowMessagesAction(Event event) {
        try {
            newLoadedPane = (Node)FXMLLoader.load(getClass().getResource("visorConductorMessages.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        driverSVHolder.getChildren().setAll(newLoadedPane);
    }
}
