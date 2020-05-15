package pkg2ingproyi.Model;

import java.util.ArrayList;

public class Admin extends User {
    private ArrayList<Driver> drivers;

    public Admin(String username, String password, String name, String surname, String dni, ArrayList<Driver> drivers) {
        super(username, password, name, surname, dni, null);
        this.drivers = drivers;
    }

    public Admin(String[] parsed, ArrayList<Driver> drivers) {
        super(parsed[0], parsed[1], parsed[2], parsed[3], parsed[4], null);
        this.drivers = drivers;
    }
    
    public void addDriver(Driver driver) {
        drivers.add(driver);
    }

    public boolean removeDriver(int index) {
        if (index > drivers.size())
            return false;

        drivers.remove(index);
        return true;
    }

    public int driversCount() {
        return drivers.size();
    }

    public Driver getDriver(int index) {
        if (index > drivers.size())
            return null;

        return drivers.get(index);
    }

    @Override
    public ArrayList<Service> getReserves() {
        if (driversCount() == 0)
            return null;

        ArrayList<Service> reserves = new ArrayList<>();

        for (Driver driver : drivers)
            reserves.addAll(driver.getReserves());

        return reserves;
    }

    @Override
    public ArrayList<Service> getAccepted() {
        if (driversCount() == 0)
            return null;

        ArrayList<Service> aceptados = new ArrayList<>();

        for (Driver driver : drivers)
            aceptados.addAll(driver.getAccepted());

        return aceptados;
    }

    @Override
    public ArrayList<Service> getMontajes() {
        if (driversCount() == 0)
            return null;

        ArrayList<Service> montajes = new ArrayList<>();

        for (Driver driver : drivers)
            montajes.addAll(driver.getMontajes());

        return montajes;
    }

    @Override
    public boolean isAdmin() {
        return true;
    }
}
