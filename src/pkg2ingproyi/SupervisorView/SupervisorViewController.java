package pkg2ingproyi.SupervisorView;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
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
import org.controlsfx.control.Notifications;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import pkg2ingproyi.Main;
import pkg2ingproyi.Model.Admin;
import pkg2ingproyi.Model.Driver;
import pkg2ingproyi.Model.Service;
import pkg2ingproyi.Model.Vehicle;
import pkg2ingproyi.Util.*;

import javax.management.NotificationListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    @FXML
    public JFXTextField newReserveDistanceLbl;
    @FXML
    public JFXTextField newReserveClientDNILbl;
    @FXML
    public JFXTextField oldReserveNameLbl;
    @FXML
    public JFXTextField oldReserveIDLbl;
    @FXML
    public JFXTextField oldReservePickupLbl;
    @FXML
    public JFXTextField oldReserveTransitLbl;
    @FXML
    public JFXTextField oldReserveArrivalLbl;

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
            NetworkHandler networkHandler = new NetworkHandler(APIRoutes.VEHICLES, new OnDataReceivedListener() {
                @Override
                public void dataReceived(String rawData) throws Exception {
                    JSONParser jsonParser           = new JSONParser();
                    JSONObject vehicleJSONObject    = (JSONObject) jsonParser.parse(rawData);
                    JSONArray vehicleJSONArray      = (JSONArray ) vehicleJSONObject.get("items");

                    ArrayList<Vehicle> parsedVehicles = new JSONCastedList<>(vehicleJSONArray, Vehicle.class);
                    Platform.runLater(() -> Notifications.create().title("Data Loaded").text("Archivos cargados desde la base de datos.").showInformation());

                    drawVehicles(parsedVehicles);
                }

                @Override
                public void onDataFail() {
                    Platform.runLater(() -> Notifications.create().title("Data Receive Error").text("¿Hay conexión con la BD?").showError());
                }
            });
            networkHandler.start();
        }

        /*** -- RESERVE VIEW -- ***/
        if (reserveTreeTable != null) {
            ArrayList<Service> displayReserves;
            displayReserves = admin.getReserves();
            initReservesTableView(displayReserves);
            reserveTreeTable.getSelectionModel().select(0);
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

    private void drawVehicles(List<Vehicle> vehicles) {
        progressBar.setVisible(false);
        //TODO Draw vehicles in the listview nicely.
    }

    /*********  ------------ RESERVES VIEW METHODS IMPLEMENTATION ------------  *********/

    public void handleNewServiceRequest() {
        defaultExpanded.setExpanded(true);

        if (reserveSplitPane.getDividerPositions()[0] > 0.8) {
            reserveSplitPane.setDividerPositions(0.1f, 0.6f, 0.9f);
        } else {
            reserveSplitPane.setDividerPositions(1f, 1f, 1f);
            handleNewReserveRequest();
        }
    }

    public boolean isValidDate(String fecha){
        SimpleDateFormat sdfrmt = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        sdfrmt.setLenient(false);

        try {
            Date javaDate = sdfrmt.parse(fecha);
        } catch (java.text.ParseException e) {
            Notifications.create().title("Invalid date hour").text("Fecha hora inválida").showError();
            return true;
        }
        /* Return true if date format is valid */
        return false;
    }

    public void handleNewReserveRequest() {
        if (newReserveNameLbl       .getText().equals("") ||
            newReserveArrivalLbl    .getText().equals("") ||
            newReserveIDLbl         .getText().equals("") ||
            newReserveStartTLbl     .getText().equals("") ||
            newReserveEndTLbl       .getText().equals("") ||
            isValidDate(newReserveStartTLbl .getText()  ) ||
            isValidDate(newReserveEndTLbl   .getText()  )   )
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
                        newReserveDistanceLbl   .getText(),
                        newReserveClientDNILbl  .getText(),
                        admin                   .getUsername()
                    );
            reserve.setObservable();

            observableServices.add(reserve);
            final TreeItem<Service> root = new RecursiveTreeItem<>(observableServices, RecursiveTreeObject::getChildren);
            reserveTreeTable.setRoot(root);

            Notifications.create().title("Reserve Successful").text("La reserva ha sido creada con éxito.").showInformation();
            handleCancelReserveRequest();
        }
    }

    public void handleCancelReserveRequest() {
        newReserveNameLbl       .setText("");
        newReserveStartTLbl     .setText("");
        newReserveEndTLbl       .setText("");
        newReservePickupLbl     .setText("");
        newReserveTransitLbl    .setText("");
        newReserveArrivalLbl    .setText("");
        newReserveIDLbl         .setText("");
        newReserveDistanceLbl   .setText("");
        newReserveClientDNILbl  .setText("");
    }

    private void updateOldFields(int reserveIndex) {
        Service clickedReserve = observableServices.get(reserveIndex);

        // TODO update old fields
    }

    public void handleReserveTreeViewClick() {
        updateOldFields(reserveTreeTable.getSelectionModel().getSelectedIndex());
    }

    public void acceptReserveAsService(int reserveIndex) {
        if (newReserveNameLbl       .getText().equals("") ||
            newReserveArrivalLbl    .getText().equals("") ||
            newReserveIDLbl         .getText().equals("") ||
            newReserveStartTLbl     .getText().equals("") ||
            newReservePickupLbl     .getText().equals("") ||
            newReserveTransitLbl    .getText().equals("") ||
            newReserveDistanceLbl   .getText().equals("") ||
            newReserveClientDNILbl  .getText().equals("")  )
            Notifications.create().title("Cannot accept reserve").text("Faltan campos obligatorios o no se cumplen ciertas condiciones.").showError();
        else {
            Service clickedReserve = observableServices.get(reserveIndex);
            observableServices.remove(reserveIndex);

            final TreeItem<Service> root = new RecursiveTreeItem<>(observableServices, RecursiveTreeObject::getChildren);
            reserveTreeTable.setRoot(root);

            clickedReserve.setReserve(false);
            clickedReserve.setAccepted(true);
            Notifications.create().title("Reserve Accept Successful").text("La reserva ha sido aceptada como servicio.").showInformation();
        }
    }

    public void handleAcceptReserveRequest  () {
        acceptReserveAsService(reserveTreeTable.getSelectionModel().getSelectedIndex());
    }

    public void handleApplyReserveEdit      () {
        Notifications.create().title("Feature to be implemented").text("Esta característica aun no ha sido implementada.").showError();

    }

    public void handleRemoveReserveRequest  () {
        Notifications.create().title("Feature to be implemented").text("Esta característica aun no ha sido implementada.").showError();
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
            if (aceptado.getStartT().substring(1, 11).equals(chosenDate)) { //Take only date matching services.
                aceptado.setObservable();
                observableServices.add(aceptado);
            }
        if (observableServices.isEmpty())
            montajeTreeTable.setPlaceholder(new Label("No hay servicios montados para esta fecha."));

        loadIntoInfoScrollPane(chosenDate.equals(todayDate));
    }

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
        JFXTreeTableColumn<Service, String> distance = new JFXTreeTableColumn<>("Distancia");
        distance.setPrefWidth(250);

        distance.setCellValueFactory(param -> param.getValue().getValue().observableDistance);

        //CLIENT DNI
        JFXTreeTableColumn<Service, String> clientDNI = new JFXTreeTableColumn<>("DNI Cliente");
        clientDNI.setPrefWidth(250);

        clientDNI.setCellValueFactory(param -> param.getValue().getValue().observableDNI);

        for (Service reserve : displayReserves) {
            reserve.setObservable();
            observableServices.add(reserve);
        }

        final TreeItem<Service> root = new RecursiveTreeItem<>(observableServices, RecursiveTreeObject::getChildren);
        reserveTreeTable.getColumns().setAll(identifier, name, pickup, arrival, startT, endT, distance, clientDNI);
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
        JFXTreeTableColumn<Service, String> distance = new JFXTreeTableColumn<>("Distancia");
        distance.setPrefWidth(250);

        distance.setCellValueFactory(param -> param.getValue().getValue().observableDistance);

        //CLIENT DNI
        JFXTreeTableColumn<Service, String> clientDNI = new JFXTreeTableColumn<>("DNI Cliente");
        clientDNI.setPrefWidth(250);

        clientDNI.setCellValueFactory(param -> param.getValue().getValue().observableDNI);

        for (Service reserve : displayServices) {
            reserve.setObservable();
            observableServices.add(reserve);
        }

        final TreeItem<Service> root = new RecursiveTreeItem<Service>(observableServices, RecursiveTreeObject::getChildren);
        reserveTreeTable.getColumns().setAll(identifier, name, pickup, arrival, startT, endT, distance, clientDNI);
        reserveTreeTable.setRoot(root);
        reserveTreeTable.setShowRoot(false);
    }
}
