/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ingproyi.DriverView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import javax.swing.*;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void handleDriverShowHomeAction(ActionEvent event) {
        //jp.add(visorConductorHome.fxml);
        /* Invoke visorConductorHome.fxml
           Add visorConductorHome root to anchorpane of parent Root??
         */
    }

    @FXML
    private void handleDriverShowServicesAction(ActionEvent event) {
    }

    @FXML
    private void handleDriverShowMesagesAction(ActionEvent event) {
    }

}
