package pkg2ingproyi.SupervisorView;

import com.jfoenix.controls.JFXTabPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SupervisorViewController implements Initializable {

    @FXML
    private JFXTabPane tabPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private void addTab(Pane pane, String tabTitle) {
        Tab tab = new Tab(tabTitle);
        tab.setContent(pane);
        tabPane.getTabs().add(tab);
    }

    public void handleConductoresButton(ActionEvent actionEvent) {
    }

    public void handleVehiculosButton(ActionEvent actionEvent) {
    }

    public void handleReservasButton(ActionEvent actionEvent) {
    }

    public void handleServiciosButton(ActionEvent actionEvent) {
    }

    public void handleMontajeButton(ActionEvent actionEvent) {
    }

    public void handleChatButton(ActionEvent actionEvent) throws IOException {
        addTab(FXMLLoader.load(getClass().getResource("visorSupervisorChat.fxml")), "Chat");
    }

    public void handleSettingsButton(ActionEvent actionEvent) {
    }
}
