package pkg2ingproyi.Model;

import java.util.ArrayList;

public abstract class User {
    private String username, password, dni, name, surname;

    User(String username, String password, String name, String surname, String dni) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.dni = dni;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDni() {
        return dni;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public abstract ArrayList<Service> getReserves();

    public abstract ArrayList<Service> getAccepted();

    public abstract ArrayList<Service> getMontajes();

    public abstract boolean isAdmin();
}
