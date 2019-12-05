package pkg2ingproyi.Model;

import java.util.ArrayList;

class Admin extends User {
    private ArrayList<Driver> drivers;

    Admin(String username, String password, String dni, String name, String surname, boolean isAdmin) {
        super(username, password, dni, name, surname, isAdmin);
    }
    
    public void addConductor(Driver conductor) {
        drivers.add(conductor);
    }

    public boolean removeConductor(int indice) {
        if (indice > drivers.size()) {
            return false;
        }
        drivers.remove(indice);
        return true;
    }

    public int numConductores() {
        return drivers.size();
    }

    public Driver getConductor(int indice) {
        if (indice > drivers.size()) {
            return null;
        }
        return drivers.get(indice);
    }


}
