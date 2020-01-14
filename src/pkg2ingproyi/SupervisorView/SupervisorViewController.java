package pkg2ingproyi.SupervisorView;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTabPane;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;
import pkg2ingproyi.Main;
import pkg2ingproyi.Model.Admin;
import pkg2ingproyi.Model.Driver;
import pkg2ingproyi.Model.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SupervisorViewController implements Initializable {

    /**** MAIN CONTAINER VIEW ELEMENTS *****/
    @FXML
    private JFXTabPane tabPane;
    private SingleSelectionModel<Tab> selectionModel;

    /**** DRIVER MENU VIEW ELEMENTS *****/
    @FXML
    private JFXListView<HBox> driverList;
    @FXML
    private Label driverNameLabel;
    @FXML
    private Label driverUsernameLabel;
    @FXML
    private Label driverDNILabel;

    /***** CORE CODE ELEMENTS *****/
    Admin admin = (Admin) Main.appUser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /** Check if true admin before every view load. **/
        if (!Main.appUser.isAdmin()) {
            Notifications.create().title("Autentication Error").text("Ha habido un problema al verificar tu usuario.").showError();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.exit(0);
            }
            System.exit(0);
        }
        /** MAIN CONTAINER **/
        if (tabPane != null) {
            tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
            selectionModel = tabPane.getSelectionModel();
        }

        /** DRIVER VIEW **/
        if (driverList != null) {
            for (int z = 0; z < 10; z++) {
                for (int i = 0; i < admin.driversCount(); i++) {
                    Driver driver = admin.getDriver(i);
                    Label label = new Label("   Conductor " + (i + 1) + "   --   Nombre: " + driver.getName() + "   --   Apellido: " + driver.getSurname() + "");
                    FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.USER_CIRCLE);
                    icon.setGlyphSize(20);
                    CheckBox chckbox = new CheckBox();
                    Region region1 = new Region();
                    HBox.setHgrow(region1, Priority.ALWAYS);


                    HBox listItem = new HBox(icon, label, region1, chckbox);
                    driverList.getItems().add(listItem);
                }
            }
            driverList.getSelectionModel().select(0);
            updateDriverInfoPane(0);
        }

        /** SERVICES VIEW **/
        //if (servicesList != null) {
            for (int i = 0; i < admin.driversCount(); i++) {
                Driver driver = admin.getDriver(i);
                for (int j = 0; j < driver.servicesCount(); j++) {
                    Service service = driver.getService(j);
                    System.out.println(service.toString());
                }
            }
      //  }
    }

    /*****  ------------ MAIN CONTAINER METHODS IMPLEMENTATION ------------  *****/
    private void addTab(Pane pane, String tabTitle) {
        Tab tab = new Tab(tabTitle);
        tab.setContent(pane);
        tabPane.getTabs().add(tab);
        selectionModel.select(tab);
    }

    public void handleConductoresButton(ActionEvent actionEvent) throws IOException {
        addTab(FXMLLoader.load(getClass().getResource("visorSupervisorConductores.fxml")), "Conductores");
    }

    public void handleVehiculosButton(ActionEvent actionEvent) throws IOException {
        addTab(FXMLLoader.load(getClass().getResource("visorSupervisorVehiculos.fxml")), "Coches");
    }

    public void handleReservasButton(ActionEvent actionEvent) throws IOException {
        addTab(FXMLLoader.load(getClass().getResource("visorSupervisorReservas.fxml")), "Reservas");
    }

    public void handleServiciosButton(ActionEvent actionEvent) throws IOException {
        addTab(FXMLLoader.load(getClass().getResource("visorSupervisorServicios.fxml")), "Servicios");
    }

    public void handleMontajeButton(ActionEvent actionEvent) throws IOException {
        addTab(FXMLLoader.load(getClass().getResource("visorSupervisorMontaje.fxml")), "Montaje");
    }

    public void handleChatButton(ActionEvent actionEvent) throws IOException {
        addTab(FXMLLoader.load(getClass().getResource("visorSupervisorChat.fxml")), "Chat");
    }

    public void handleSettingsButton(ActionEvent actionEvent) throws IOException {

    }

    /*********  ------------ COMMON METHODS IMPLEMENTATION ------------  *********/

    private void launchStage(String filePath, String stageTitle) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(filePath));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle(stageTitle);
        stage.setScene(scene);
        stage.show();
    }

    /*********  ------------ DRIVER VIEW METHODS IMPLEMENTATION ------------  *********/
    private ImageView getDriverImage(String driverUsername) throws FileNotFoundException {   //TODO Implement PFP Images
        return new ImageView(new Image(new FileInputStream("/resources/driverImages/"+driverUsername+".jpg")));
    }

    private void updateDriverInfoPane(int driverIndex) {
        Driver displayDriver = admin.getDriver(driverIndex);

        //TODO More driver fields and pfp.
        driverNameLabel     .setText("      " + displayDriver.getName()    );
        driverUsernameLabel .setText("      " + displayDriver.getUsername());
        driverDNILabel      .setText("      " + displayDriver.getDni()     );
    }

    public void handleDriverListClick(MouseEvent mouseEvent) {
        updateDriverInfoPane(driverList.getSelectionModel().getSelectedIndex());
    }

    public void handleNewUserRequest(ActionEvent actionEvent) throws IOException {
        launchStage("SupervisorNuevousuario.fxml", "Registro de Usuario");
    }

    public void handleRemoveUserRequest(ActionEvent actionEvent) {
        Notifications.create().title("Feature to be implemented").text("Esta característica aun no ha sido implementada.").showError();
    }

    /*********  ------------ SERVICES VIEW METHODS IMPLEMENTATION ------------  *********/
    public void updateServiceInfoPane() {
        admin.getDriver(0).servicesCount();

    }

    /******************************************************************************************/


}
