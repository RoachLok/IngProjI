package pkg2ingproyi.Model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.json.simple.JSONObject;
import pkg2ingproyi.Util.DBUtil;
import pkg2ingproyi.Util.JSONSerializable;

public class Service extends RecursiveTreeObject<Service> implements JSONSerializable {
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
    private String author;
    private String indications;
    private String dptId;
    private int    distance;
    private int    pricing;
    private int    pax;

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
    public StringProperty observableAuthor;
    public StringProperty observableIndications;
    public IntegerProperty observableDistance;
    public IntegerProperty observablePricing;
    public IntegerProperty observablePax;

    public Service(String title, String startTime, String endT, String pickup, String arrival,
                   String identifier, String contractor, int distance, String author, String dptId)
    {
        this.name           = title;
        this.startT         = startTime;
        this.endT           = endT;
        this.pickup         = pickup;
        this.arrival        = arrival;
        this.identifier     = identifier;
        this.contractor     = contractor;
        this.distance       = distance;
        this.author         = author;
        this.dptId          = dptId;
        this.isReserve      = true;
    }

    public Service(String title, String startTime, String endTime, String pickup, String transit, String arrival,
                    String driverName, String vehicleID, String identifier, String contractor, int pricing,
                        String author, int distance, String indications, int pax, int status, String dptId)
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
        this.distance       = distance;
        this.indications    = indications;
        this.pax            = pax;
        this.dptId          = dptId;

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
            observableContractor    = new SimpleStringProperty( contractor  );
            observableAuthor        = new SimpleStringProperty( author      );
            observableDistance      = new SimpleIntegerProperty( distance    );
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
        observableAuthor        = new SimpleStringProperty( author      );
        observableIndications   = new SimpleStringProperty( indications );
        observableDistance      = new SimpleIntegerProperty( distance    );
        observablePricing       = new SimpleIntegerProperty( pricing     );
        observablePax           = new SimpleIntegerProperty( pax         );
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

    public int getPricing() {
        return pricing;
    }

    public void setPricing(int pricing) {
        this.pricing = pricing;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
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

    public String getDptId() {
        return dptId;
    }

    private char getStatusAsChar() {
        if (isReserve)
            return '1';
        if (isAccepted)
            return '2';
        //isMounted
        return '3';
    }

    private void setStatusFromChar(char status) {
        switch (status) {
            case '1':
                isReserve = true;
                break;
            case '2':
                isAccepted = true;
                break;
            case '3':
                isMontaje = true;
                break;
            default:
                isReserve = true;
        }
    }

    @Override
    public String toString() {
        return "Service{" +
                "name='"            + name          + '\'' +
                ", startT='"        + startT        + '\'' +
                ", endT='"          + endT          + '\'' +
                ", pickup='"        + pickup        + '\'' +
                ", transit='"       + transit       + '\'' +
                ", arrival='"       + arrival       + '\'' +
                ", driverName='"    + driverName    + '\'' +
                ", vehicleName='"   + vehicleName   + '\'' +
                ", identifier='"    + identifier    + '\'' +
                ", contractor='"    + contractor    + '\'' +
                ", author='"        + author        + '\'' +
                ", distance='"      + distance      + '\'' +
                ", indications='"   + indications   + '\'' +
                ", pax='"           + pax           + '\'' +
                '}';
    }


    @Override
    public String toJSONString() {
        return "{\"service_id\":\""      + identifier        + '\"' +
                ",\"pickup\":\""         + pickup            + '\"' +
                ",\"arrival\":\""        + arrival           + '\"' +
                ",\"transit\":\""        + transit           + '\"' +
                ",\"pickup_time\":\""    + startT            + '\"' +
                ",\"arrival_time\":\""   + endT              + '\"' +
                ",\"pax\":"              + pax               +
                ",\"indications\":\""    + indications       + '\"' +
                ",\"status\":\""         + getStatusAsChar() + '\"' +
                ",\"title\":\""          + name              + '\"' +
                ",\"chauffeur_name\":\"" + driverName        + '\"' +
                ",\"vehicle_id\":\""     + vehicleName       + '\"' +
                ",\"client_name\":\""    + contractor        + '\"' +
                ",\"pricing\":"          + pricing           +
                ",\"author\":\""         + author            + '\"' +
                ",\"distance\":"         + distance          +
                ",\"department_id\":\""  + dptId             + '\"' +
                '}';
    }

    public Service(JSONObject object) {
        jsonParse(object);
        startT   = DBUtil.fixDateFormat(this.startT);
        if (endT != null)
            endT = DBUtil.fixDateFormat(this.endT  );
    }

    @Override
    public void jsonParse(JSONObject object) {
        this.identifier   = (String) object.get( "service_id"     );
        this.pickup       = (String) object.get( "pickup"         );
        this.arrival      = (String) object.get( "arrival"        );
        this.transit      = (String) object.get( "transit"        );
        this.startT       = (String) object.get( "pickup_time"    );
        this.endT         = (String) object.get( "arrival_time"   );
        this.indications  = (String) object.get( "indications"    );
        this.name         = (String) object.get( "title"          );
        this.driverName   = (String) object.get( "chauffeur_name" );
        this.vehicleName  = (String) object.get( "vehicle_id"     );
        this.contractor   = (String) object.get( "client_name"    );
        this.author       = (String) object.get( "author"         );
        this.dptId        = (String) object.get( "department_id"  );

        this.pax          = (int) (long) object.get( "pax"        );
        this.pricing      = (int) (long) object.get( "pricing"    );
        this.distance     = (int) (long) object.get( "distance"   );

        setStatusFromChar( ((String) object.get("status" )).charAt(0) );
    }
}
