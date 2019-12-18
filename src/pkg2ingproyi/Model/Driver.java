package pkg2ingproyi.Model;

import java.util.ArrayList;

public class Driver extends User{
    private String adminNick;
    private ArrayList<Service> services;

    public Driver(String username, String password, String name, String surname, String dni, String adminNick) {
        super(username, password, name, surname, dni);
        this.adminNick = adminNick;
        this.services = new ArrayList<>();
    }

    public Driver(String[] parsed){
        super(parsed[0], parsed[1], parsed[2], parsed[3], parsed[4]);
        this.adminNick = parsed[5];
    }

    public void addService(Service service) {
        services.add(service);
    }

    public boolean removeService(int index) {
        if (index > services.size())
            return false;

        services.remove(index);
        return true;
    }

    public int servicesCount() {
        return services.size();
    }

    public Service getService(int index) {
        if (index > services.size())
            return null;

        return services.get(index);
    }

    private String getAdminNick() {
        return adminNick;
    }

    @Override
    boolean isAdmin() {
        return false;
    }
}