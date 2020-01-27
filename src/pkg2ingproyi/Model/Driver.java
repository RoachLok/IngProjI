package pkg2ingproyi.Model;

import java.util.ArrayList;

public class Driver extends User{
    private String adminNick;
    private ArrayList<Service> services;

    public Driver(String username, String password, String name, String surname, String dni, String adminNick) {
        super(username, password, name, surname, dni);
        this.adminNick = adminNick;
    }

    public Driver(String[] parsed){
        super(parsed[0], parsed[1], parsed[2], parsed[3], parsed[4]);
        this.adminNick = parsed[5];
    }

    public void addService(Service service) {
        if (services == null)
            services = new ArrayList<>();
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

    public String getAdminNick() {
        return adminNick;
    }

    @Override
    public ArrayList<Service> getReserves() {
        if (servicesCount() == 0)
            return null;

        ArrayList<Service> reserves = new ArrayList<>();

        for (Service service : services)
            if (service.isReserve())
                reserves.add(service);

        return reserves;
    }

    @Override
    public ArrayList<Service> getAccepted() {
        if (servicesCount() == 0)
            return null;

        ArrayList<Service> accepteds = new ArrayList<>();

        for (Service service : services)
            if (service.isAccepted())
                accepteds.add(service);

        return accepteds;
    }

    @Override
    public ArrayList<Service> getMontajes() {
        if (servicesCount() == 0)
            return null;

        ArrayList<Service> montajes = new ArrayList<>();

        for (Service service : services)
            if (service.isMontaje())
                montajes.add(service);

        return montajes;
    }

    @Override
    public boolean isAdmin() {
        return false;
    }
}