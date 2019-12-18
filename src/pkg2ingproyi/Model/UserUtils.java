package pkg2ingproyi.Model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
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
                if(line.startsWith(username))
                    if (!isAdmin)
                        return initDriver(line, username);
                    else
                        return initAdmin(line, username);
            bufferedReader.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static JSONArray readJson(String filepath) throws Exception {
        FileReader reader = new FileReader(filepath);
        JSONParser jsonParser = new JSONParser();
        return (JSONArray) jsonParser.parse(reader);
    }

    private static Service parseJsonObject(JSONObject object) {
        return new Service(
                (String) object.get("title"),
                (String) object.get("startTime"),
                (String) object.get("endTime"),
                (String) object.get("pickup"),
                (String) object.get("transit"),
                (String) object.get("arrival"),
                (String) object.get("driverName"),
                (String) object.get("vehicleID"),
                (String) object.get("identifier"),
                (String) object.get("author"));
    }

    private static List<Service> initServices() throws Exception {
        List<Service> services = new ArrayList<>();
        JSONArray jsonArray = readJson("src/resources/services.json");
        for (Object o : jsonArray)
            services.add(parseJsonObject((JSONObject) o));
        return services;
    }

    private static Driver initDriver(String line, String username) throws Exception {
        Driver driver = new Driver(line.split(","));
        for (Service service : initServices())
            if (service.getDriverName().equals(username))
                driver.addService(service);

        return driver;
    }

    private static Admin initAdmin(String line, String username) throws Exception {
        String holder;
        ArrayList<Driver> drivers = null;

        Scanner scInput = new Scanner(readUsersFile());
        while(scInput.hasNext())
            if((holder = scInput.nextLine()).endsWith(username)) {
                assert false;
                drivers.add(initDriver(holder, holder.split(",")[0]));
            }

        return new Admin(line.split(","), drivers);
    }
}
