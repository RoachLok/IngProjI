package pkg2ingproyi.SupervisorView;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.util.Callback;
import org.controlsfx.control.Notifications;
import pkg2ingproyi.Main;
import pkg2ingproyi.Model.Admin;
import pkg2ingproyi.Model.Driver;
import pkg2ingproyi.Model.Service;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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

    /****** ----- TREEVIEWS COMMON ----- ******/
    private ObservableList<Service> observableServices;

    /**** RESERVES VIEW ELEMENTS *****/
    @FXML
    public JFXTreeTableView<Service> reserveTreeTable;
    @FXML
    public SplitPane reserveSplitPane;
    @FXML
    public TitledPane defaultExpanded;
    @FXML
    public JFXTextField newReserveNameLbl;
    @FXML
    public JFXTextField newReserveIDLbl;
    @FXML
    public JFXTextField newReserveTransitLbl;
    @FXML
    public JFXTextField newReservePickupLbl;
    @FXML
    public JFXTextField newReserveArrivalLbl;
    @FXML
    public JFXTextField newReserveStartTLbl;
    @FXML
    public JFXTextField newReserveEndTLbl;

    /**** SERVICES VIEW ELEMENTS ****/

    @FXML
    public JFXTreeTableView<Service> serviceTreeTable;

    /**** MONTAJES VIEW ELEMENTS ****/
    @FXML
    private JFXTreeTableView<Service> montajeTreeTable;
    @FXML
    private JFXDatePicker datePicker;

    /***** CORE CODE ELEMENTS *****/
    private Admin admin = (Admin) Main.appUser;
    private ArrayList<Service> displayServices;

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
        /***  -- MAIN CONTAINER -- ***/
        if (tabPane != null) {
            tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
            selectionModel = tabPane.getSelectionModel();
        }

        /*** -- DRIVER VIEW -- ***/
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

        /*** -- RESERVE VIEW -- ***/
        if (reserveTreeTable != null) {
            displayServices = admin.getReserves();
            initReservesTableView(displayServices);
            reserveTreeTable.getSelectionModel().select(0);
        }

        /*** -- SERVICES VIEW -- ***/

        if (serviceTreeTable != null) {
            displayServices = admin.getReserves();
            initReservesTableView(displayServices);
            reserveTreeTable.getSelectionModel().select(0);
        }

        /*** -- MONTAJE VIEW -- ***/
        observableServices = FXCollections.observableArrayList();

        if (montajeTreeTable != null) {
            JFXTreeTableColumn<Service, String> identifier = new JFXTreeTableColumn("ID");
            identifier.setPrefWidth(75);

            identifier.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Service, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Service, String> param) {
                    return param.getValue().getValue().observableIdentifier;
                }
            });

            JFXTreeTableColumn<Service, String> name = new JFXTreeTableColumn("Servicio");
            name.setPrefWidth(300);

            name.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Service, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Service, String> param) {
                    return param.getValue().getValue().observableName;
                }
            });

            JFXTreeTableColumn<Service, String> pickup = new JFXTreeTableColumn("Salida");
            pickup.setPrefWidth(250);

            pickup.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Service, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Service, String> param) {
                    return param.getValue().getValue().observablePickup;
                }
            });

            JFXTreeTableColumn<Service, String> arrival = new JFXTreeTableColumn("Llegada");
            arrival.setPrefWidth(250);

            arrival.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Service, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Service, String> param) {
                    return param.getValue().getValue().observableArrival;
                }
            });

            JFXTreeTableColumn<Service, String> startT = new JFXTreeTableColumn("H. Inicio");
            startT.setPrefWidth(250);

            startT.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Service, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Service, String> param) {
                    return param.getValue().getValue().observableStartT;
                }
            });

            JFXTreeTableColumn<Service, String> endT = new JFXTreeTableColumn("H. Final");
            endT.setPrefWidth(250);

            endT.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Service, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Service, String> param) {
                    return param.getValue().getValue().observableEndT;
                }
            });

            JFXTreeTableColumn<Service, String> chauffeur = new JFXTreeTableColumn("Conductor");
            chauffeur.setPrefWidth(100);

            chauffeur.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Service, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Service, String> param) {
                    return param.getValue().getValue().observableDriverName;
                }
            });

            JFXTreeTableColumn<Service, String> transit = new JFXTreeTableColumn("Tránsito");
            transit.setPrefWidth(250);

            transit.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Service, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Service, String> param) {
                    return param.getValue().getValue().observableTransit;
                }
            });

            JFXTreeTableColumn<Service, String> vehicleName = new JFXTreeTableColumn("Vehículo");
            vehicleName.setPrefWidth(100);

            vehicleName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Service, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Service, String> param) {
                    return param.getValue().getValue().observableVehicleName;
                }
            });

            displayServices = admin.getMontajes();

            for (Service montado : displayServices) {
                montado.setObservable();
                observableServices.add(montado);
            }

            final TreeItem<Service> root = new RecursiveTreeItem<Service>(observableServices, RecursiveTreeObject::getChildren);
            montajeTreeTable.getColumns().setAll(identifier, name, pickup, arrival, startT, endT, chauffeur, vehicleName, transit);
            montajeTreeTable.setRoot(root);
            montajeTreeTable.setShowRoot(false);

            montajeTreeTable.getSelectionModel().select(0);
        }
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

    /*********  ------------ RESERVES VIEW METHODS IMPLEMENTATION ------------  *********/

    public void handleNewServiceRequest(ActionEvent actionEvent) throws IOException {
        defaultExpanded.setExpanded(true);

        if (reserveSplitPane.getDividerPositions()[0] > 0.8) {
            reserveSplitPane.setDividerPositions(0.1f, 0.6f, 0.9f);
        } else {
            reserveSplitPane.setDividerPositions(1f, 1f, 1f);
            handleNewReserveRequest(actionEvent);
        }
    }

    public void handleNewReserveRequest(ActionEvent actionEvent) throws IOException {
        if (newReserveNameLbl       .getText().equals("") ||
            newReserveStartTLbl     .getText().equals("") ||
            newReserveEndTLbl       .getText().equals("") ||
            newReservePickupLbl     .getText().equals("") ||
            newReserveTransitLbl    .getText().equals("") ||
            newReserveArrivalLbl    .getText().equals("") ||
            newReserveIDLbl         .getText().equals("")   )
            Notifications.create().title("Fields Missing").text("Faltan campos obligatorios.").showError();
        else {
            Service reserve = new Service
                    (
                        newReserveNameLbl       .getText(),
                        newReserveStartTLbl     .getText(),
                        newReserveEndTLbl       .getText(),
                        newReservePickupLbl     .getText(),
                        newReserveTransitLbl    .getText(),
                        newReserveArrivalLbl    .getText(),
                        newReserveIDLbl         .getText(),
                        admin                   .getUsername()
                    );
            reserve.setObservable();

            observableServices.add(reserve);
            final TreeItem<Service> root = new RecursiveTreeItem<Service>(observableServices, RecursiveTreeObject::getChildren);
            reserveTreeTable.setRoot(root);

            Notifications.create().title("Reserve Successful").text("La reserva ha sido creada con éxito.").showInformation();
            handleCancelReserveRequest(actionEvent);
        }
    }

    public void handleCancelReserveRequest(ActionEvent actionEvent) throws IOException {
        newReserveNameLbl       .setText("");
        newReserveStartTLbl     .setText("");
        newReserveEndTLbl       .setText("");
        newReservePickupLbl     .setText("");
        newReserveTransitLbl    .setText("");
        newReserveArrivalLbl    .setText("");
        newReserveIDLbl         .setText("");
    }

    private void updateOldFields(int reserveIndex) {
        Service clickedReserve = observableServices.get(reserveIndex);

        //TODO Update oldReserve fields.

    }

    public void handleReserveTreeViewClick(MouseEvent mouseEvent) {
        updateOldFields(reserveTreeTable.getSelectionModel().getSelectedIndex());
    }

    public void acceptReserveAsService(int reserveIndex) {
        Service clickedReserve = observableServices.get(reserveIndex);
        observableServices.remove(reserveIndex);

        final TreeItem<Service> root = new RecursiveTreeItem<Service>(observableServices, RecursiveTreeObject::getChildren);
        reserveTreeTable.setRoot(root);

        clickedReserve.setReserve(false);
        clickedReserve.setAccepted(true);
        Notifications.create().title("Reserve Accept Successful").text("La reserva ha sido aceptada como servicio.").showInformation();
    }

    public void handleAcceptReserveRequest(ActionEvent actionEvent) throws IOException {
        acceptReserveAsService(reserveTreeTable.getSelectionModel().getSelectedIndex());
    }

    public void handleApplyReserveEdit(ActionEvent actionEvent) throws IOException {
        Notifications.create().title("Feature to be implemented").text("Esta característica aun no ha sido implementada.").showError();
    }

    public void handleRemoveReserveRequest(ActionEvent actionEvent) {
        Notifications.create().title("Feature to be implemented").text("Esta característica aun no ha sido implementada.").showError();
    }

    /*********  ------------ SERVICES VIEW METHODS IMPLEMENTATION ------------  *********/
    public void updateServiceInfoPane() {
        admin.getDriver(0).servicesCount();
    }


    /*********  ------------ MONTAJE VIEW METHODS IMPLEMENTATION ------------  *********/
    public void handleMontajeTableClick(MouseEvent mouseEvent) {
        updateMontajeInfoPane(montajeTreeTable.getSelectionModel().getSelectedIndex());
    }

    private void updateMontajeInfoPane(int tableSelectedIndex) {
        Service displayService = displayServices.get(tableSelectedIndex);

        //TODO Date detector, update gauges on montajeViewPane.

    }

    //TODO implement date checker.

    /******************************************************************************************/
    void initReservesTableView (ArrayList<Service> displayServices) {
        observableServices = FXCollections.observableArrayList();

        //ID
        JFXTreeTableColumn<Service, String> identifier = new JFXTreeTableColumn("ID");
        identifier.setPrefWidth(75);

        identifier.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Service, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Service, String> param) {
                return param.getValue().getValue().observableIdentifier;
            }
        });
        //NAME
        JFXTreeTableColumn<Service, String> name = new JFXTreeTableColumn("Servicio");
        name.setPrefWidth(300);

        name.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Service, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Service, String> param) {
                return param.getValue().getValue().observableName;
            }
        });
        //PICKUP
        JFXTreeTableColumn<Service, String> pickup = new JFXTreeTableColumn("Salida");
        pickup.setPrefWidth(250);

        pickup.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Service, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Service, String> param) {
                return param.getValue().getValue().observablePickup;
            }
        });
        //ARRIVAL
        JFXTreeTableColumn<Service, String> arrival = new JFXTreeTableColumn("Llegada");
        arrival.setPrefWidth(250);

        arrival.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Service, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Service, String> param) {
                return param.getValue().getValue().observableArrival;
            }
        });
        //START
        JFXTreeTableColumn<Service, String> startT = new JFXTreeTableColumn("H. Inicio");
        startT.setPrefWidth(250);

        startT.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Service, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Service, String> param) {
                return param.getValue().getValue().observableStartT;
            }
        });
        //ENDT
        JFXTreeTableColumn<Service, String> endT = new JFXTreeTableColumn("H. Final");
        endT.setPrefWidth(250);

        endT.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Service, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Service, String> param) {
                return param.getValue().getValue().observableEndT;
            }
        });
        //TRANSIT
        JFXTreeTableColumn<Service, String> transit = new JFXTreeTableColumn("Tránsito");
        transit.setPrefWidth(250);

        transit.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Service, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Service, String> param) {
                return param.getValue().getValue().observableTransit;
            }
        });

        for (Service reserve : displayServices) {
            reserve.setObservable();
            observableServices.add(reserve);
        }

        final TreeItem<Service> root = new RecursiveTreeItem<Service>(observableServices, RecursiveTreeObject::getChildren);
        reserveTreeTable.getColumns().setAll(identifier, name, pickup, arrival, startT, endT);
        reserveTreeTable.setRoot(root);
        reserveTreeTable.setShowRoot(false);
    }
}
