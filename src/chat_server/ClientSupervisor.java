package chat_server;

import java.net.Socket;
import java.util.ArrayList;

public class ClientSupervisor extends Client {
    private ArrayList<ClientDriver> drivers;

    ClientSupervisor(Socket socket, String userName) {
        super(socket, userName);
    }

    public Client getDriver(int index) {
        if (drivers.size() < index)
            return null;
        return drivers.get(index);
    }

    public void addDriver(ClientDriver newDriver) {
        drivers.add(newDriver);
    }

    public boolean deleteDriver(String driverUsername) {
        for (int i = 0; i < drivers.size(); i++){
            if (driverUsername.equals(drivers.get(i).getUsername())){
                drivers.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean deleteDriver(int index) {
        if (drivers.size() < index)
            return false;
        drivers.remove(index);
        return true;
    }

    public boolean isAdmin () {
        return true;
    }
}
