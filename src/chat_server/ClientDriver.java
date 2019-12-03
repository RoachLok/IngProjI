package chat_server;

import java.net.Socket;

public class ClientDriver extends Client {
    private Client admin;
    private String adminUsername;

     ClientDriver(Socket socket, String userName, String adminUsername) {
        super(socket, userName);
        this.adminUsername = adminUsername;
    }

    public void setAdmin(ClientSupervisor admin) {
         this.admin = admin;
    }

    public String getAdminName() {
         return adminUsername;
    }

    public Client getAdmin() {
        return admin;
    }
}
