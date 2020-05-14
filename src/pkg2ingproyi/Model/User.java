package pkg2ingproyi.Model;

import java.util.ArrayList;

public abstract class User {
    private String username, password, dni, name, surname, address, phonenumber, phonenumber2, department_id;
    private boolean isAdmin;

    public User(String username, String password, String name, String surname, String dni, String address, String phonenumber, String phonenumber2, String department_id){
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.dni = dni;
        this.address = address;
        this.phonenumber = phonenumber;
        this.phonenumber2 = phonenumber2;
        this.department_id = department_id;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setaddress(String address) {
        this.address = address;
    }

    public void setphonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public void setphonenumber2(String phonenumber2) {
        this.phonenumber2 = phonenumber2;
    }

    public void setDepartmentID(String department_id) {
        this.department_id = department_id;
    }


    public String getaddress() {
        return address;
    }

    public String getphonenumber() {
        return phonenumber;
    }

    public String getphonenumber2() {
        return phonenumber2;
    }

    public String getDepartment_id() {
        return department_id;
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
