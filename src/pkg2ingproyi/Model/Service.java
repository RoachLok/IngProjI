package pkg2ingproyi.Model;

public class Service {
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

    public Service(String title, String startTime, String pickup, String arrival, String identifier,
                    String author)
    {
        this.name           = title;
        this.startT         = startTime;
        this.pickup         = pickup;
        this.arrival        = arrival;
        this.identifier     = identifier;
        this.author         = author;
    }

    public Service(String title, String startTime, String endTime, String pickup, String transit, String arrival,
                    String driverName, String vehicleID, String identifier, String contractor, String author)
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
        this.author         = author;
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
