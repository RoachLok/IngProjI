package pkg2ingproyi.Model;

public class Driver extends User{
    private String adminNick;
    private Vehicle vehicle;

    Driver(String username, String password, String dni, String name, String surname, boolean isAdmin, String adminNick) {
        super(username, password, dni, name, surname, isAdmin);
        this.adminNick = adminNick;
    }

    private void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    private String getAdminNick() {
        return adminNick;
    }

    private Vehicle getVehicle() {
        return vehicle;
    }
}