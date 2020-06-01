package pkg2ingproyi.SupervisorView;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.sun.deploy.security.SelectableSecurityManager;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;
import javafx.util.converter.LocalDateTimeStringConverter;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.action.Action;
import org.controlsfx.control.textfield.TextFields;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import pkg2ingproyi.Main;
import pkg2ingproyi.Model.Admin;
import pkg2ingproyi.Model.Driver;
import pkg2ingproyi.Model.Service;
import pkg2ingproyi.Model.Vehicle;
import pkg2ingproyi.Util.*;
import sun.nio.ch.Net;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.text.SimpleDateFormat;

public class SupervisorViewController implements Initializable {

    /**** MAIN CONTAINER VIEW ELEMENTS *****/
    @FXML
    private JFXProgressBar progressBar;
    @FXML
    private JFXTabPane tabPane;
    private SingleSelectionModel<Tab> selectionModel;

    /**** DRIVER MENU VIEW ELEMENTS *****/
    @FXML
    private JFXListView<HBox> driverList;

    /**** DRIVER MENU VIEW ELEMENTS *****/
    @FXML
    private JFXListView<HBox> vehicleList;

    /****** ----- TREEVIEWS COMMON ----- ******/
    private ObservableList<Service> observableServices;

    /**** RESERVES VIEW ELEMENTS *****/
    @FXML
    public JFXTreeTableView<Service> reserveTreeTable;
    @FXML
    public SplitPane reserveSplitPane;
    @FXML
    public TitledPane editReserveTitlePane;
    @FXML
    public TitledPane defaultExpanded;
    @FXML
    public JFXTextField newReserveNameLbl;
    @FXML
    public JFXTextField newReserveIDLbl;
    @FXML
    public JFXTextField newReservePickupLbl;
    @FXML
    public JFXTextField newReserveArrivalLbl;
    @FXML
    public JFXTextField newReserveTransitLbl;
    @FXML
    public JFXTextField newReserveDistanceLbl;
    @FXML
    public JFXTextField newReserveClientLbl;
    @FXML
    public JFXTextField newReservePricingLbl;
    @FXML
    public JFXDatePicker newStartDate;
    @FXML
    public JFXTimePicker newStartTime;
    @FXML
    public JFXDatePicker newArriveDate;
    @FXML
    public JFXTimePicker newArriveTime;
    @FXML
    public JFXSlider newPaxSlider;
    @FXML
    public JFXTextField oldReserveNameLbl;
    @FXML
    public JFXTextField oldReserveIDLbl;
    @FXML
    public JFXTextField oldReservePickupLbl;
    @FXML
    public JFXTextField oldReserveArrivalLbl;
    @FXML
    public JFXTextField oldReserveTransitLbl;
    @FXML
    public JFXTextField oldReserveDistanceLbl;
    @FXML
    public JFXTextField oldReserveClientLbl;
    @FXML
    public JFXTextField oldReservePricingLbl;
    @FXML
    public JFXDatePicker oldStartDate;
    @FXML
    public JFXTimePicker oldStartTime;
    @FXML
    public JFXDatePicker oldArriveDate;
    @FXML
    public JFXTimePicker oldArriveTime;
    @FXML
    public JFXSlider oldPaxSlider;

    /**** SERVICES VIEW ELEMENTS ****/
    @FXML
    public JFXTreeTableView<Service> serviceTreeTable;

    /**** MONTAJES VIEW ELEMENTS ****/
    @FXML
    private JFXTreeTableView<Service> montajeTreeTable;
    @FXML
    private JFXDatePicker datePicker;
    @FXML
    public AnchorPane montajeInfoPanel;

    private String chosenDate, todayDate;

    /***** CORE CODE ELEMENTS *****/
    private final Admin admin = (Admin) Main.appUser;

    /***** TAB MANAGEMENT *****/
    private final List<String> openTabs = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /* Check if true admin before every view load. */
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
                    FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.USER_SECRET);
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

        /*** -- VEHICLE VIEW -- ***/
        if (vehicleList != null) {
            NetworkGETRequester vehicleGETRequest = new NetworkGETRequester(APIRoutes.VEHICLES,
                rawData -> {
                    Platform.runLater(() -> Notifications.create().title("Data Loaded").text("Archivos cargados desde la base de datos.").showInformation());
                    drawVehicles(new JSONCastedList<>(rawData, Vehicle.class));
                });

            vehicleGETRequest.start();
        }

        /*** -- RESERVE VIEW -- ***/
        if (reserveTreeTable != null) {
            //TextField auto completions.
            String[] locationsMap = { "ATOCHA, MADRID", "MADRID", "BARCELONA", "BARAJAS, MADRID APTO.", "EL PRAT, BARCELONA APTO.", "HOTEL B&B MADRID CENTRO",
                                      "SEVILLA", "TOLEDO", "HOTEL NOVOTEL, MADRID", "HOTEL SARDINERO, MADRID", "HOTEL ZENIT ABEBA, MADRID",
                                      "HOTEL SB GLOW, BARCELONA", "HOTEL MURMURI, BARCELONA", "HOTEL NH BARCELONA STADIUM", "HOTEL NH BARCELONA EIXAMPLE"};

            TextFields.bindAutoCompletion( newReservePickupLbl  , locationsMap );
            TextFields.bindAutoCompletion( newReservePickupLbl  , locationsMap );
            TextFields.bindAutoCompletion( newReserveArrivalLbl , locationsMap );
            TextFields.bindAutoCompletion( oldReservePickupLbl  , locationsMap );
            TextFields.bindAutoCompletion( oldReservePickupLbl  , locationsMap );
            TextFields.bindAutoCompletion( oldReserveArrivalLbl , locationsMap );
            
            String[] clientsMap = {"Will Ing Topay"};
            TextFields.bindAutoCompletion( newReserveClientLbl, clientsMap );
            TextFields.bindAutoCompletion( oldReserveClientLbl, clientsMap );

            //Service displaying.
            ArrayList<Service> displayReserves;
            displayReserves = admin.getReserves();
            initReservesTableView(displayReserves);
            reserveTreeTable.getSelectionModel().select(0);
            updateOldFields(0);
        }

        /*** -- SERVICES VIEW -- ***/
        if (serviceTreeTable != null) {
            ArrayList<Service> displayServices;
            displayServices = admin.getReserves();
            initServicesTableView(displayServices);
            reserveTreeTable.getSelectionModel().select(0);
        }

        /*** -- MONTAJE VIEW -- ***/
        if (montajeTreeTable != null) {
            //DatePicker init with current date.
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            LocalDateTime now = LocalDateTime.now();
            LocalDate localDate = LocalDate.parse(formatter.format(now), formatter);

            datePicker.setValue(localDate);
            todayDate = datePicker.getValue().toString();
            chosenDate = todayDate;

            //TreeTableView init
            observableServices = FXCollections.observableArrayList();

            JFXTreeTableColumn<Service, String> identifier = new JFXTreeTableColumn<>("ID");
            identifier.setPrefWidth(75);

            identifier.setCellValueFactory(param -> param.getValue().getValue().observableIdentifier);

            JFXTreeTableColumn<Service, String> name = new JFXTreeTableColumn<>("Servicio");
            name.setPrefWidth(300);

            name.setCellValueFactory(param -> param.getValue().getValue().observableName);

            JFXTreeTableColumn<Service, String> pickup = new JFXTreeTableColumn<>("Salida");
            pickup.setPrefWidth(90);

            pickup.setCellValueFactory(param -> param.getValue().getValue().observablePickup);

            JFXTreeTableColumn<Service, String> arrival = new JFXTreeTableColumn<>("Llegada");
            arrival.setPrefWidth(90);

            arrival.setCellValueFactory(param -> param.getValue().getValue().observableArrival);

            JFXTreeTableColumn<Service, String> startT = new JFXTreeTableColumn<>("H. Inicio");
            startT.setPrefWidth(90);

            startT.setCellValueFactory(param -> param.getValue().getValue().observableStartT);

            JFXTreeTableColumn<Service, String> endT = new JFXTreeTableColumn<>("H. Final");
            endT.setPrefWidth(90);

            endT.setCellValueFactory(param -> param.getValue().getValue().observableEndT);

            JFXTreeTableColumn<Service, String> chauffeur = new JFXTreeTableColumn<>("Conductor");
            chauffeur.setPrefWidth(100);

            chauffeur.setCellValueFactory(param -> param.getValue().getValue().observableDriverName);

            JFXTreeTableColumn<Service, String> transit = new JFXTreeTableColumn<>("Tránsito");
            transit.setPrefWidth(90);

            transit.setCellValueFactory(param -> param.getValue().getValue().observableTransit);

            JFXTreeTableColumn<Service, String> vehicleName = new JFXTreeTableColumn<>("Vehículo");
            vehicleName.setPrefWidth(100);

            vehicleName.setCellValueFactory(param -> param.getValue().getValue().observableVehicleName);


            final TreeItem<Service> root = new RecursiveTreeItem<>(observableServices, RecursiveTreeObject::getChildren);
            montajeTreeTable.getColumns().setAll(identifier, name, pickup, arrival, startT, endT, chauffeur, vehicleName, transit);
            montajeTreeTable.setRoot(root);
            montajeTreeTable.setShowRoot(false);

            updateMontajeTreeTable();

            montajeTreeTable.getSelectionModel().select(0);
        }
    }

    /*****  ------------ MAIN CONTAINER METHODS IMPLEMENTATION ------------  *****/
    private void addTab(Pane pane, String tabTitle) {
        if (openTabs.contains(tabTitle)){
            int indexTab = openTabs.indexOf(tabTitle);
            Tab tabExists = tabPane.getTabs().get(indexTab+1);
            selectionModel.select(tabExists);
        }
        else {
            Tab tab = new Tab(tabTitle);
            tab.setContent(pane);
            tabPane.getTabs().add(tab);
            openTabs.add(tabTitle);
            selectionModel.select(tab);
            int indexTabE = openTabs.indexOf(tabTitle);
            tab.setOnCloseRequest(event -> openTabs.remove(indexTabE));
        }
    }

    public void handleConductoresButton () throws IOException {
        addTab(FXMLLoader.load( getClass().getResource("visorSupervisorConductores.fxml")), "Conductores");
    }

    public void handleVehiculosButton   () throws IOException {
        addTab(FXMLLoader.load( getClass().getResource("visorSupervisorVehiculos.fxml")  ), "Coches");
    }

    public void handleReservasButton    () throws IOException {
        addTab(FXMLLoader.load( getClass().getResource("visorSupervisorReservas.fxml")   ), "Reservas");
    }

    public void handleServiciosButton   () throws IOException {
        addTab(FXMLLoader.load( getClass().getResource("visorSupervisorServicios.fxml")  ), "Servicios");
    }

    public void handleMontajeButton     () throws IOException {
        addTab(FXMLLoader.load( getClass().getResource("visorSupervisorMontaje.fxml")    ), "Montaje");
    }

    public void handleChatButton        () throws IOException {
        addTab(FXMLLoader.load( getClass().getResource("visorSupervisorChat.fxml")       ), "Chat");
    }

    public void handleSettingsButton    () throws IOException {

    }

    /*********  ------------ COMMON METHODS IMPLEMENTATION ------------  *********/

    //Launches another window.
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
//        driverNameLabel     .setText("      " + displayDriver.getName()    );
//        driverUsernameLabel .setText("      " + displayDriver.getUsername());
//        driverDNILabel      .setText("      " + displayDriver.getDni()     );
    }

    public void handleDriverListClick   () {
        updateDriverInfoPane(driverList.getSelectionModel().getSelectedIndex());
    }

    public void handleNewUserRequest    () throws IOException {
        launchStage("SupervisorNuevousuario.fxml", "Registro de Usuario");
    }

    public void handleRemoveUserRequest () {
        Notifications.create().title("Feature to be implemented").text("Esta característica aun no ha sido implementada.").showError();
    }

    /*********  ------------ VEHICLE VIEW METHODS IMPLEMENTATION ------------  *********/

    public void handleVehicleListClick   () {
        updateVehicleInfoPane(vehicleList.getSelectionModel().getSelectedIndex());
    }

    private void updateVehicleInfoPane(int selectedVehicleIndex) {
        System.out.println(selectedVehicleIndex);
    }

    private void drawVehicles(List<Vehicle> vehicles) {
        for (Vehicle vehicle : vehicles) {
            Label label = new Label("   Matrícula " + vehicle.getId() + "   --   Referencia: " + vehicle.getNick()
                        + "   --   Permiso: " + vehicle.getPermissionName() + "   --   PAX: " + vehicle.getPax() + ".");
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.BUS);
            icon.setGlyphSize(20);
            CheckBox checkBox = new CheckBox();
            Region region1 = new Region();
            HBox.setHgrow(region1, Priority.ALWAYS);

            HBox listItem = new HBox(icon, label, region1, checkBox);
            vehicleList.getItems().add(listItem);
        }
        progressBar.setVisible(false);

        vehicleList.getSelectionModel().select(0);
        updateVehicleInfoPane(0);
    }

    /*********  ------------ RESERVES VIEW METHODS IMPLEMENTATION ------------  *********/

    public void handleNewReserveRequest() {
        defaultExpanded.setExpanded(true);

        if (reserveSplitPane.getDividerPositions()[0] > 0.8) {
            reserveSplitPane.setDividerPositions(0.1f, 0.5f, 0.9f);
        } else {
            reserveSplitPane.setDividerPositions(1f, 1f, 1f);
            defaultExpanded.setExpanded(false);
            collectAndPushReserve();
        }
    }

    public boolean isValidDate(String startDateStr, String arriveDateStr) {
        if (arriveDateStr.equals(""))
            return true;

        return arriveDateStr.compareTo(startDateStr) > 0;
    }

    public void collectAndPushReserve() {
        //Check if no-null fields are null.
        if (newReserveNameLbl       .getText().equals("") ||
            newReserveArrivalLbl    .getText().equals("") ||
            newReserveIDLbl         .getText().equals("") ||
            newReservePickupLbl     .getText().equals("") ||
            newReserveClientLbl     .getText().equals("") ||
            newReservePricingLbl    .getText().equals("") ||
            newStartDate            .getValue() == null   ||
            newStartTime            .getValue() == null     )
            Notifications.create().title("Fields Missing").text("Faltan campos obligatorios.").showError();

        else {
            //Format dates from DatePicker and TimePicker
            String startDateStr  = DBUtil.fixDateFormat(newStartDate.getValue().toString() + ' ' + newStartTime.getValue().toString() + ":00");
            String arriveDateStr = "";
            if (newArriveDate.getValue() != null && newArriveTime.getValue() != null)
                DBUtil.fixDateFormat(newArriveDate.getValue().toString() + ' ' + newArriveTime.getValue().toString() + ":00");
            if (!isValidDate(startDateStr, arriveDateStr)) {
                Notifications.create().title("Bad Time Given").text("La hora de llegada no puede ser mayor a la de comienzo.").showError();
                return;
            }

            //Grab distance from field.
            int newDistance = 0;
            if (!newReserveDistanceLbl.getText().equals(""))
                newDistance = Integer.parseInt(newReserveDistanceLbl.getText());

            Service reserve = new Service
                    (
                        newReserveNameLbl       .getText(), startDateStr, arriveDateStr,
                        newReservePickupLbl     .getText(),
                        newReserveArrivalLbl    .getText(),
                        newReserveIDLbl         .getText(),
                        newReserveClientLbl     .getText(),
                        newDistance                       ,
                        admin                   .getUsername(),
                        admin                   .getDptId()
                    );
            reserve.setTransit  ( newReserveTransitLbl.getText());
            reserve.setPax      ( (int) newPaxSlider.getValue() );

            reserve.setObservable();

            NetworkPOSTRequester networkPOSTRequester = new NetworkPOSTRequester(APIRoutes.RESERVES, reserve.toJSONString(),
                    () -> Platform.runLater(() -> Notifications.create().title("Wrong DB Connection").text("Hubo un error al intentar subir los datos.").showInformation()));
            networkPOSTRequester.start();

            observableServices.add(reserve);
            final TreeItem<Service> root = new RecursiveTreeItem<>(observableServices, RecursiveTreeObject::getChildren);
            reserveTreeTable.setRoot(root);

            //Notify success and clean fields for next Reserve.
            Notifications.create().title("Reserve Successful").text("La reserva ha sido creada con éxito.").showInformation();
            handleCancelReserveRequest();
        }
    }

    //Cleans fields from "New Reserve" panel.
    public void handleCancelReserveRequest() {
        newReserveNameLbl       .setText("");
        newReservePickupLbl     .setText("");
        newReserveTransitLbl    .setText("");
        newReserveArrivalLbl    .setText("");
        newReserveIDLbl         .setText("");
        newReserveDistanceLbl   .setText("");
        newReserveClientLbl     .setText("");
        newReservePricingLbl    .setText("");
        newStartDate         .setValue(null);
        newStartTime         .setValue(null);
        newArriveDate        .setValue(null);
        newArriveTime        .setValue(null);
    }

    public void handleEditReserveRequest() {
        editReserveTitlePane.setExpanded(true);

        if (reserveSplitPane.getDividerPositions()[0] > 0.8) {
            reserveSplitPane.setDividerPositions(0.1f, 0.5f, 0.9f);
        } else {
            reserveSplitPane.setDividerPositions(1f, 1f, 1f);
            editReserveTitlePane.setExpanded(false);
        }
    }

    private void updateOldFields(int reserveIndex) {
        Service clickedReserve = observableServices.get(reserveIndex);

        oldReserveNameLbl       .setText( clickedReserve .getName()         );
        oldReservePickupLbl     .setText( clickedReserve .getPickup()       );
        oldReserveTransitLbl    .setText( clickedReserve .getTransit()      );
        oldReserveArrivalLbl    .setText( clickedReserve .getArrival()      );
        oldReserveIDLbl         .setText( clickedReserve .getIdentifier()   );
        oldReserveClientLbl     .setText( clickedReserve .getContractor()   );
        oldReserveDistanceLbl   .setText( String.valueOf( clickedReserve.getDistance()) );
        oldReservePricingLbl    .setText( String.valueOf( clickedReserve.getPricing() ) );

        oldPaxSlider.setValue(clickedReserve.getPax());

        String startStr = clickedReserve.getStartT().replace('/', '-');

        oldStartDate.setValue(LocalDate.parse(startStr.substring(0, 10), DateTimeFormatter.ISO_LOCAL_DATE));
        oldStartTime.setValue(LocalTime.parse(startStr.substring(11), DateTimeFormatter.ISO_LOCAL_TIME));


        if (clickedReserve.getEndT() != null) {
            String endStr = clickedReserve.getEndT().replace('/', '-');
            oldArriveDate.setValue(LocalDate.parse(endStr.substring(0, 10), DateTimeFormatter.ISO_LOCAL_DATE));
            oldArriveTime.setValue(LocalTime.parse(endStr.substring(11), DateTimeFormatter.ISO_LOCAL_TIME));
        }
    }

    public void handlePushEditedReserveRequest() throws InterruptedException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Editara Reserva");
        alert.setHeaderText("¿Está seguro de que desea editar la reserva?");
        alert.setContentText("Los cambios serán permanentes en la BD.");

        Optional<ButtonType> confirmButton = alert.showAndWait();
        if (confirmButton.isPresent() && confirmButton.get() == ButtonType.OK) {
            handleApplyReserveEdit(reserveTreeTable.getSelectionModel().getSelectedIndex());
        }
    }

    public void handleCancelEditReserveRequest() {
        editReserveTitlePane.setExpanded(false);

        reserveSplitPane.setDividerPositions(1f, 1f, 1f);
    }

    //int prevSelectIndex;
    public void handleReserveTreeViewClick() {
      /* ENABLE DOUBLE CLICK DISPLAYS RESERVE EDIT
        if (prevSelectIndex == reserveTreeTable.getSelectionModel().getSelectedIndex())
            handleEditReserveRequest(); //If the same reserve is clicked twice, opens the editing window.

        prevSelectIndex = reserveTreeTable.getSelectionModel().getSelectedIndex();*/

        updateOldFields(reserveTreeTable.getSelectionModel().getSelectedIndex());
    }

    public void acceptReserveAsService(int reserveIndex) {
        Service clickedReserve = observableServices.get(reserveIndex);
        if (clickedReserve.getEndT().equals("")) {
            Notifications.create().title("Reserve Missing Fields").text("").showError();
            return;
        }

        NetworkPUTRequester networkPUTRequester = new NetworkPUTRequester(APIRoutes.SERVICES, clickedReserve.getIdentifier(), "{\"STATUS\":\"2\"}",
                () -> Notifications.create().title("DB Connection Error.").text("Error al aceptar la reserva en la BD.").showError());
        networkPUTRequester.start();

        //Update TreeTable
        observableServices.remove(reserveIndex);

        final TreeItem<Service> root = new RecursiveTreeItem<>(observableServices, RecursiveTreeObject::getChildren);
        reserveTreeTable.setRoot(root);

        clickedReserve.setReserve(false);
        clickedReserve.setAccepted(true);
        Notifications.create().title("Reserve Accept Successful").text("La reserva ha sido aceptada como servicio.").showConfirm();
    }

    public void handleAcceptReserveRequest  () {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Aceptar Reserva");
        alert.setHeaderText("¿Está seguro de que desea aceptar la reserva?");
        alert.setContentText("La Reserva se confirmará y pasará a la ventana de Servicios.");

        Optional<ButtonType> confirmButton = alert.showAndWait();
        if (confirmButton.isPresent() && confirmButton.get() == ButtonType.OK) {
            acceptReserveAsService(reserveTreeTable.getSelectionModel().getSelectedIndex());
        }
    }

    public void handleApplyReserveEdit      (int reserveIndex) throws InterruptedException {
        //Check if no-null fields are null.
        if (oldReserveNameLbl       .getText().equals("") ||
            oldReserveArrivalLbl    .getText().equals("") ||
            oldReservePickupLbl     .getText().equals("") ||
            oldReserveClientLbl     .getText().equals("") ||
            oldReservePricingLbl    .getText().equals("") ||
            oldStartDate            .getValue() == null   ||
            oldStartTime            .getValue() == null     ) {
            Notifications.create().title("Fields Missing").text("Faltan campos obligatorios.").showError();
            return;
        }

        //Format dates from DatePicker and TimePicker
        String startDateStr  = DBUtil.fixDateFormat(oldStartDate.getValue().toString() + ' ' + oldStartTime.getValue().toString() + ":00");
        String arriveDateStr = "";
        if (oldArriveDate.getValue() != null && oldArriveTime.getValue() != null)
            DBUtil.fixDateFormat(oldArriveDate.getValue().toString() + ' ' + oldArriveTime.getValue().toString() + ":00");
        if (!isValidDate(startDateStr, arriveDateStr)) {
            Notifications.create().title("Bad Time Given").text("La hora de llegada no puede ser mayor a la de comienzo.").showError();
            return;
        }

        int newDistance;
        if (oldReserveDistanceLbl.getText().equals(""))
            newDistance = 0;
        else
            newDistance = Integer.parseInt(oldReserveDistanceLbl.getText());

        Service editReserve = new Service
                (
                    oldReserveNameLbl       .getText(), startDateStr, arriveDateStr,
                    oldReservePickupLbl     .getText(),
                    oldReserveArrivalLbl    .getText(),
                    oldReserveIDLbl         .getText(),
                    oldReserveClientLbl     .getText(),
                    newDistance                       ,
                    admin                   .getUsername(),
                    admin                   .getDptId()
                );
        editReserve.setTransit( oldReserveTransitLbl.getText ());
        editReserve.setPax    ( (int) oldPaxSlider  .getValue());

        //Delete old reserve from DB.
        NetworkDELETERequester networkDELETERequester = new NetworkDELETERequester(APIRoutes.SERVICES, editReserve.getIdentifier(), false,
                () -> Notifications.create().title("Wrong connection to DB").text("El elemento a editar no es accesible en la BD."));

        networkDELETERequester.start();
        networkDELETERequester.join();

        //Replace with new reserve.
        NetworkPOSTRequester networkPOSTRequester = new NetworkPOSTRequester(APIRoutes.RESERVES, editReserve.toJSONString(),
                () -> Notifications.create().title("Wrong connection to DB").text("El elemento se eliminó de la BD pero hubo un problema al reemplazarlo."));

        networkPOSTRequester.start();

        //Update TreeTable
        editReserve.setObservable();
        observableServices.set(reserveIndex, editReserve);

        final TreeItem<Service> root = new RecursiveTreeItem<>(observableServices, RecursiveTreeObject::getChildren);
        reserveTreeTable.setRoot(root);

        Notifications.create().title("Data updated in DB").text("La reserva ha sido actualizada en la BD.").showConfirm();
    }

    private void deleteReserve(int selectedIndex) {
        Service selectedReserve = observableServices.get(selectedIndex);

        NetworkDELETERequester networkDELETERequester = new NetworkDELETERequester(APIRoutes.SERVICES, selectedReserve.getIdentifier(), true,
                () -> Notifications.create().title("Wrong connection to DB").text("El elemento a editar no es accesible en la BD."));

        networkDELETERequester.start();

        //Update TreeTable
        observableServices.remove(selectedIndex);

        final TreeItem<Service> root = new RecursiveTreeItem<>(observableServices, RecursiveTreeObject::getChildren);
        reserveTreeTable.setRoot(root);
    }

    public void handleRemoveReserveRequest  () {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Eliminar Reserva");
        alert.setHeaderText("¿Está seguro de que desea borrar la reserva?");
        alert.setContentText("Se eliminará permanentemente de la BD.");

        Optional<ButtonType> confirmButton = alert.showAndWait();
        if (confirmButton.isPresent() && confirmButton.get() == ButtonType.OK) {
            deleteReserve(reserveTreeTable.getSelectionModel().getSelectedIndex());
        }
    }

    /*********  ------------ SERVICES VIEW METHODS IMPLEMENTATION ------------  *********/
    public void updateServiceInfoPane() {
        admin.getDriver(0).servicesCount();
    }

    /*********  ------------ MONTAJE VIEW METHODS IMPLEMENTATION ------------  *********/
    private void loadIntoInfoScrollPane(boolean realTime) { //Loads a view into the montajeView infoPane.
        String pane;
        if (realTime)
            pane = "supervisorMontajeRealtime.fxml";
        else
            pane = "supervisorMontajeRegisters.fxml";

        Node newLoadedPane =  null;
        try {
            newLoadedPane = FXMLLoader.load(getClass().getResource(pane));
        } catch (IOException e) {
            e.printStackTrace();
        }
        montajeInfoPanel.getChildren().setAll(newLoadedPane);
    }

    private void updateMontajeTreeTable() {
        observableServices.remove(0, observableServices.size()); //Empty current treeTable.
        for (Service aceptado : admin.getMontajes())
            if (aceptado.getStartT().substring(0, 10).equals(chosenDate.replace('-', '/'))) { //Take only date matching services.
                aceptado.setObservable();
                observableServices.add(aceptado);
            }
        if (observableServices.isEmpty())
            montajeTreeTable.setPlaceholder(new Label("No hay servicios montados para esta fecha."));

        loadIntoInfoScrollPane(chosenDate.equals(todayDate));
    } /**commit change to date**/

    public void datePickerUpdate() {
        chosenDate =  datePicker.getValue().toString();
        updateMontajeTreeTable();
    }

    public void handleMontajeTableClick() {
        loadIntoInfoScrollPane(chosenDate.equals(todayDate));
    }

    /******************************************************************************************/

    //INIT RESERVES TABLE
    void initReservesTableView(ArrayList<Service> displayReserves) {
        observableServices = FXCollections.observableArrayList();

        //ID
        JFXTreeTableColumn<Service, String> identifier = new JFXTreeTableColumn<>("ID");
        identifier.setPrefWidth(75);

        identifier.setCellValueFactory(param -> param.getValue().getValue().observableIdentifier);

        //NAME
        JFXTreeTableColumn<Service, String> name = new JFXTreeTableColumn<>("Servicio");
        name.setPrefWidth(300);

        name.setCellValueFactory(param -> param.getValue().getValue().observableName);

        //PICKUP
        JFXTreeTableColumn<Service, String> pickup = new JFXTreeTableColumn<>("Salida");
        pickup.setPrefWidth(250);

        pickup.setCellValueFactory(param -> param.getValue().getValue().observablePickup);

        //ARRIVAL
        JFXTreeTableColumn<Service, String> arrival = new JFXTreeTableColumn<>("Llegada");
        arrival.setPrefWidth(250);

        arrival.setCellValueFactory(param -> param.getValue().getValue().observableArrival);

        //START
        JFXTreeTableColumn<Service, String> startT = new JFXTreeTableColumn<>("H. Inicio");
        startT.setPrefWidth(150);

        startT.setCellValueFactory(param -> param.getValue().getValue().observableStartT);

        //ENDT
        JFXTreeTableColumn<Service, String> endT = new JFXTreeTableColumn<>("H. Final");
        endT.setPrefWidth(150);

        endT.setCellValueFactory(param -> param.getValue().getValue().observableEndT);

        //TRANSIT
        JFXTreeTableColumn<Service, String> transit = new JFXTreeTableColumn<>("Tránsito");
        transit.setPrefWidth(250);

        transit.setCellValueFactory(param -> param.getValue().getValue().observableTransit);

        //PRICING
        JFXTreeTableColumn<Service, Number> pricing = new JFXTreeTableColumn<>("Precio");
        pricing.setPrefWidth(150);

        pricing.setCellValueFactory(param -> param.getValue().getValue().observablePricing);

        //DISTANCE
        JFXTreeTableColumn<Service, Number> distance = new JFXTreeTableColumn<>("Distancia");
        distance.setPrefWidth(150);

        distance.setCellValueFactory(param -> param.getValue().getValue().observableDistance);

        //CLIENT DNI
        JFXTreeTableColumn<Service, String> client = new JFXTreeTableColumn<>("Cliente");
        client.setPrefWidth(250);

        client.setCellValueFactory(param -> param.getValue().getValue().observableContractor);

        //PAX
        JFXTreeTableColumn<Service, Number> pax = new JFXTreeTableColumn<>("PAX");
        pax.setPrefWidth(80);

        pax.setCellValueFactory(param -> param.getValue().getValue().observablePax);

        for (Service reserve : displayReserves) {
            reserve.setObservable();
            observableServices.add(reserve);
        }

        final TreeItem<Service> root = new RecursiveTreeItem<>(observableServices, RecursiveTreeObject::getChildren);
        reserveTreeTable.getColumns().setAll(identifier, name, pickup, arrival, startT, endT, pax, client, pricing, distance, transit);
        reserveTreeTable.setRoot(root);
        reserveTreeTable.setShowRoot(false);
    }

    //INIT SERVICES TABLE
    void initServicesTableView(ArrayList<Service> displayServices) {
        observableServices = FXCollections.observableArrayList();

        //ID
        JFXTreeTableColumn<Service, String> identifier = new JFXTreeTableColumn<>("ID");
        identifier.setPrefWidth(75);

        identifier.setCellValueFactory(param -> param.getValue().getValue().observableIdentifier);

        //NAME
        JFXTreeTableColumn<Service, String> name = new JFXTreeTableColumn<>("Servicio");
        name.setPrefWidth(300);

        name.setCellValueFactory(param -> param.getValue().getValue().observableName);

        //PICKUP
        JFXTreeTableColumn<Service, String> pickup = new JFXTreeTableColumn<>("Salida");
        pickup.setPrefWidth(250);

        pickup.setCellValueFactory(param -> param.getValue().getValue().observablePickup);

        //ARRIVAL
        JFXTreeTableColumn<Service, String> arrival = new JFXTreeTableColumn<>("Llegada");
        arrival.setPrefWidth(250);

        arrival.setCellValueFactory(param -> param.getValue().getValue().observableArrival);

        //START
        JFXTreeTableColumn<Service, String> startT = new JFXTreeTableColumn<>("H. Inicio");
        startT.setPrefWidth(250);

        startT.setCellValueFactory(param -> param.getValue().getValue().observableStartT);

        //ENDT
        JFXTreeTableColumn<Service, String> endT = new JFXTreeTableColumn<>("H. Final");
        endT.setPrefWidth(250);

        endT.setCellValueFactory(param -> param.getValue().getValue().observableEndT);

        //TRANSIT
        JFXTreeTableColumn<Service, String> transit = new JFXTreeTableColumn<>("Tránsito");
        transit.setPrefWidth(250);

        transit.setCellValueFactory(param -> param.getValue().getValue().observableTransit);

        //DISTANCE
        JFXTreeTableColumn<Service, Number> distance = new JFXTreeTableColumn<>("Distancia");
        distance.setPrefWidth(250);

        distance.setCellValueFactory(param -> param.getValue().getValue().observableDistance);

        //CLIENT DNI
        JFXTreeTableColumn<Service, String> clientDNI = new JFXTreeTableColumn<>("DNI Cliente");
        clientDNI.setPrefWidth(250);


        for (Service reserve : displayServices) {
            reserve.setObservable();
            observableServices.add(reserve);
        }

        final TreeItem<Service> root = new RecursiveTreeItem<Service>(observableServices, RecursiveTreeObject::getChildren);
        reserveTreeTable.getColumns().setAll(identifier, name, pickup, arrival, startT, endT, distance);
        reserveTreeTable.setRoot(root);
        reserveTreeTable.setShowRoot(false);
    }
}
