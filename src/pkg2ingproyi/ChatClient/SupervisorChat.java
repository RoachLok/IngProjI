package pkg2ingproyi.ChatClient;

import com.jfoenix.controls.JFXComboBox;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import pkg2ingproyi.Model.Administrador;
import pkg2ingproyi.Model.Usuario;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;



public class SupervisorChat implements Initializable {
    @FXML
    private TextArea messagesArea;

    @FXML
    private TextField messageField;

    @FXML
    private Button sendMessage;

    @FXML
    private Label label;
    @FXML
    private JFXComboBox<String> comboConductores;



    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");


    private String driverUsername, supervisorUsername;
    private InetAddress inetAddress;
    private int port = 56789;

    private  String driverUsernameGETUSERNAME = "Nachocalvo";
    private  String driverGETADMINNAME = "Joset";

    private Administrador supervisor;

    private MessageHandler messageHandler;




    //TODO CONTINUAR CON cosas jfxlistview
	    /*@FXML
	    private JFXListView<Usuario> lvConductores;
	    ObservableList<Usuario> listView=FXCollections.observableArrayList(supervisor.getConductores());
	    Image profile= new Image("C:\\Users\\manpa\\Proyecto Ingenieria\\ProyectoIngenieria1\\src\\resources\\icons\\usericon.png");
    	Label nombre= new Label("");
    	Label estado= new Label("Conectado");
    	ImageView imageView= new ImageView(profile);
    	Button botonChat= new Button();
	    static class Cell extends ListCell<String>{
	    	HBox box= new HBox();
	    	Image profile= new Image("C:\\Users\\manpa\\Proyecto Ingenieria\\ProyectoIngenieria1\\src\\resources\\icons\\usericon.png");
	    	Label nombre= new Label("");
	    	Label estado= new Label("Conectado");
	    	ImageView imageView= new ImageView(profile);
	    	Button botonChat= new Button();



	    	public  Cell() {
	    		super();
	    		box.getChildren().addAll(imageView,nombre,estado,botonChat);
	    		botonChat.setOnAction(e->System.out.println("chat cambiado"));
	    		botonChat.setOnAction(e->addEventFilter();


	    	}
	    }
	    */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //label.setText("Chat con: "+supervisorUsername); //TODO: Style title. Picture, last connect/online, etc. Add setting for port.
        ArrayList<String>mensajes= new ArrayList<>();
        ArrayList<Usuario> conductores= new ArrayList<>();
        supervisor=new Administrador("Jose","Taborda","Joset","05450270Y",mensajes,conductores);
        ArrayList<String> nombreCond= new ArrayList<String>();

        messagesArea.setEditable(false);


        for (int i = 0; i < supervisor.numConductores(); i++) {
            nombreCond.add(supervisor.getConductor(i).getnombreUsuario());

        }
        ObservableList<String> options =FXCollections.observableArrayList(nombreCond);
        final ComboBox comboBox = new ComboBox(options);

        //lvConductores.setItems(listView);

        supervisor.addConductor(new Usuario("Nacho", "Marica", "05450355Z", "abcdef", "Nachocalvo"));
        try {
            inetAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        SupervisorChat me = this;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                messageHandler = new MessageHandler(inetAddress.getHostAddress(), port, driverGETADMINNAME,".", me);  //DEBUGGING PORPOISES:

                //Server Handshake//
                messageHandler.connectToServer();

                sendMessage.setOnAction((event) -> {
                    messageHandler.sendMessage("MSG><"+driverGETADMINNAME+"><"+driverUsernameGETUSERNAME+"><"+messageField.getText());
                    addText(messageField.getText(), true);
                    messageField.clear();
                });
            }
        });


    }



    void addText(String text, boolean isSent){
        LocalDateTime currentTime = LocalDateTime.now();
        if (!isSent){
            messagesArea.appendText("\n["+dtf.format(currentTime)+']'+' '+driverGETADMINNAME+" envió: "+text+'.');
        }else{
            messagesArea.appendText("\n["+dtf.format(currentTime)+']'+" Has enviado: "+text+'.');
        }
    }


}

