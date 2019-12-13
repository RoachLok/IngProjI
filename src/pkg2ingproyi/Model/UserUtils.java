package pkg2ingproyi.Model;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class UserUtils {

    private static boolean comprobarUsuario(String dni, File file) throws FileNotFoundException {
        Scanner entrada = new Scanner(file);
        String linea;
        while(entrada.hasNext()) {
            linea = entrada.nextLine();
            if(linea.contains(dni)) {
                /*Esto quiere decir que el dni ya existe en la base de datos*/
                entrada.close();
                return true;
            }
        }
        return false;
    }

    public static boolean iniciarSesion(String usuario, String password) throws FileNotFoundException {
        if(usuario == null || password == null)
            return false;

        Scanner entrada = new Scanner(readUsersFile());
        while(entrada.hasNext())
            if(entrada.nextLine().startsWith(usuario+','+password)) {
                entrada.close();
                return true;
            }

        return false;
    }

    public static boolean isAdmin(String username) throws FileNotFoundException {
        if (username == null)
            return false;

        String holder;
        Scanner scIn = new Scanner(readUsersFile());
        while(scIn.hasNext())
            if((holder = scIn.nextLine()).startsWith(username)){
                scIn.close();
                return holder.endsWith(",");
            }

        return false;
    }

    private static File readUsersFile() {
        return new File("src/resources/users.txt").getAbsoluteFile();
    }

    public static User createUser(String username, boolean isAdmin) throws FileNotFoundException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(readUsersFile()));
        String line;
        try {
            while((line = bufferedReader.readLine()) != null)
                if(line.contains(username))
                    if (!isAdmin)
                        return new Driver(line.split(","));
                    else
                        return initAdmin(line, username);
            bufferedReader.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object readJson(String filename) throws Exception {
        FileReader reader = new FileReader(filename);
        JSONParser jsonParser = new JSONParser();
        return jsonParser.parse(reader);
    }

    private static Service initService() throws Exception {
        JSONObject jsonObject = (JSONObject) readJson("services.json");
        return new Service(
                (String) jsonObject.get("title"),
                (String) jsonObject.get("startTime"),
                (String) jsonObject.get("endTime"),
                (String) jsonObject.get("pickup"),
                (String) jsonObject.get("transit"),
                (String) jsonObject.get("arrival"),
                (String) jsonObject.get("driverName"),
                (String) jsonObject.get("vehicleID"));
    }


    private static Driver initDriver(String line, String username) {
        ArrayList<Service> services;

        
        return new Driver (line.split(","));
    }

    private static Admin initAdmin(String line, String username) throws FileNotFoundException {
        String holder;
        ArrayList<Driver> drivers = null;

        Scanner scInput = new Scanner(readUsersFile());
        while(scInput.hasNext())
            if((holder = scInput.nextLine()).endsWith(username))
                drivers.add(new Driver(holder.split(",")));

        return new Admin(line.split(","), drivers);
    }
}
