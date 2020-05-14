package pkg2ingproyi.Model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Connection;

public class Service extends RecursiveTreeObject<Service> {
    private String name;
    private String startT;
    private String endT;
    private String pickup;
    private String transit;
    private String arrival;
    private String driverName;
    private String vehicleName;
    private String identifier;
    private String contractor;
    private String pricing;
    private String author;

    private boolean isReserve;
    private boolean isAccepted;
    private boolean isMontaje;

    public StringProperty observableName;
    public StringProperty observableStartT;
    public StringProperty observableEndT;
    public StringProperty observablePickup;
    public StringProperty observableTransit;
    public StringProperty observableArrival;
    public StringProperty observableDriverName;
    public StringProperty observableVehicleName;
    public StringProperty observableIdentifier;
    public StringProperty observableContractor;
    public StringProperty observablePricing;
    public StringProperty observableAuthor;

    public Service(String title, String startTime, String endT, String pickup, String transit, String arrival,
                   String identifier, String author)
    {
        this.name           = title;
        this.startT         = startTime;
        this.endT           = endT;
        this.pickup         = pickup;
        this.arrival        = arrival;
        this.identifier     = identifier;
        this.author         = author;
        this.isReserve      = true;
    }

    /*public boolean deleteServiceBD(String identifier) {
        /*Conectamos a la base de datos*/
        /*Connection con  = connectDatabase();
        String st = con.createStatement();
        String query = "DELETE FROM servicios where identifier =" + identifier;
        st.executeStatement(query);
        //Si hago una consulta a por el servicio eliminado y no sale return true
        /*Arreglar la conexion a la base de datos*/
        /*return true;
    }*/

    /*public boolean addServiceBD() {
        /*Conectamos a la base de datos*/
        /*Connection con  = connectDatabase();
        String st = con.createStatement();
        String query = "INSERT INTO servicios values(?,?,?,?,?,?);
        st.executeStatement(query);
        //Si devuelve lo que yo quiero true.
        /*Arreglar la conexion a la base de datos*/

        /*return true;
    }*/

    public Service(String title, String startTime, String endTime, String pickup, String transit, String arrival,
                    String driverName, String vehicleID, String identifier, String contractor, String pricing,
                        String author, int status)
    {
        this.name           = title;
        this.startT         = startTime;
        this.endT           = endTime;
        this.pickup         = pickup;
        this.transit        = transit;
        this.arrival        = arrival;
        this.driverName     = driverName;
        this.vehicleName    = vehicleID;
        this.identifier     = identifier;
        this.contractor     = contractor;
        this.pricing        = pricing;
        this.author         = author;

        switch (status) {
            case 1:
                isReserve = true;
                break;
            case 2:
                isAccepted = true;
                break;
            case 3:
                isMontaje = true;
                break;
            default:
                isReserve = true;
        }
    }

    public void setObservable() {
        if  (name == null)
            return;

        if (isReserve) {
            observableName          = new SimpleStringProperty( name        );
            observableStartT        = new SimpleStringProperty( startT      );
            observableEndT          = new SimpleStringProperty( endT        );
            observablePickup        = new SimpleStringProperty( pickup      );
            observableTransit       = new SimpleStringProperty( transit     );
            observableArrival       = new SimpleStringProperty( arrival     );
            observableIdentifier    = new SimpleStringProperty( identifier  );
            observableAuthor        = new SimpleStringProperty( author      );
            return;
        }

        observableName          = new SimpleStringProperty( name        );
        observableStartT        = new SimpleStringProperty( startT      );
        observableEndT          = new SimpleStringProperty( endT        );
        observablePickup        = new SimpleStringProperty( pickup      );
        observableTransit       = new SimpleStringProperty( transit     );
        observableArrival       = new SimpleStringProperty( arrival     );
        observableDriverName    = new SimpleStringProperty( driverName  );
        observableVehicleName   = new SimpleStringProperty( vehicleName );
        observableIdentifier    = new SimpleStringProperty( identifier  );
        observableContractor    = new SimpleStringProperty( contractor  );
        observablePricing       = new SimpleStringProperty( pricing     );
        observableAuthor        = new SimpleStringProperty( author      );
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartT() {
        return startT;
    }

    public void setStartT(String startT) {
        this.startT = startT;
    }

    public String getEndT() {
        return endT;
    }

    public void setEndT(String endT) {
        this.endT = endT;
    }

    public String getPickup() {
        return pickup;
    }

    public void setPickup(String pickup) {
        this.pickup = pickup;
    }

    public String getTransit() {
        return transit;
    }

    public void setTransit(String transit) {
        this.transit = transit;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getPricing() {
        return pricing;
    }

    public void setPricing(String pricing) {
        this.pricing = pricing;
    }

    public void setReserve(boolean isReserve) {
        this.isReserve = isReserve;
    }

    public boolean isReserve() {
        return this.isReserve;
    }

    public void setAccepted(boolean isService) {
        this.isAccepted = isService;
    }

    public boolean isAccepted() {
        return this.isAccepted;
    }

    public void setMontaje(boolean isMontaje) {
        this.isMontaje = isMontaje;
    }

    public boolean isMontaje() {
        return this.isMontaje;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getContractor() {
        return contractor;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public String toString() {
        return "Service{" +
                "name='" + name + '\'' +
                ", startT='" + startT + '\'' +
                ", endT='" + endT + '\'' +
                ", pickup='" + pickup + '\'' +
                ", transit='" + transit + '\'' +
                ", arrival='" + arrival + '\'' +
                ", driverName='" + driverName + '\'' +
                ", vehicleName='" + vehicleName + '\'' +
                ", identifier='" + identifier + '\'' +
                ", contractor='" + contractor + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
