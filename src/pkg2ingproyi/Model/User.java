package pkg2ingproyi.Model;

abstract class User {
    private String username, password, dni, name, surname;
    private boolean isAdmin;

    User(String username, String password, String dni, String name, String surname, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.dni = dni;
        this.name = name;
        this.surname = surname;
        this.isAdmin = isAdmin;
    }

    String getUsername() {
        return username;
    }

    String getPassword() {
        return password;
    }

    String getDni() {
        return dni;
    }

    String getName() {
        return name;
    }

    String getSurname() {
        return surname;
    }

    boolean isAdmin() {
        return isAdmin;
    }
}
