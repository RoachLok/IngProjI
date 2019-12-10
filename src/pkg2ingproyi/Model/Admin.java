package pkg2ingproyi.Model;

import java.util.ArrayList;

class Admin extends User {
    private ArrayList<Driver> drivers;

    public Admin(String username, String password, String name, String surname, String dni, ArrayList<Driver> drivers) {
        super(username, password, name, surname, dni);
        this.drivers = drivers;
    }

    public Admin(String[] parsed, ArrayList<Driver> drivers) {
        super(parsed[0], parsed[1], parsed[2], parsed[3], parsed[4]);
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
    boolean isAdmin() {
        return true;
    }
}
