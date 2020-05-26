package pkg2ingproyi.Util;

import javafx.application.Platform;
import org.controlsfx.control.Notifications;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import pkg2ingproyi.Model.*;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.URL;
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

    public static boolean login(String usuario, String password) throws FileNotFoundException {
        if(usuario.equals("") || password.equals(""))
            return false;

        Scanner inText = new Scanner(readUsersFile());
        while(inText.hasNext())
            if(inText.nextLine().startsWith(usuario+','+password+',')) {
                inText.close();
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

    private static List<Service> initServices() {
        try {
            URL url = new URL("https://jj82bm9wo382qd8-sj3.adb.eu-amsterdam-1.oraclecloudapps.com/ords/admin/service/?q={%22department_id%22:%22"+ "TESTDPT" +"%22}");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            //Check if connection was stabilised correctly.
            if (urlConnection.getResponseCode() != 200) {
                System.err.println("Wrong Connection.");
            } else {
                //Proceed only if correct connection with url.

                //Read URL and pass to String.
                Scanner sc = new Scanner(url.openStream());
                StringBuilder rawData = new StringBuilder();
                while (sc.hasNext())
                    rawData.append(sc.nextLine());

                //Pass data to listener.
                JSONParser jsonParser           = new JSONParser();
                JSONObject serviceJSONObject    = (JSONObject) jsonParser.parse(rawData.toString());
                JSONArray serviceJSONArray      = (JSONArray ) serviceJSONObject.get("items");

                return new JSONCastedList<>(serviceJSONArray, Service.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static Driver initDriver(String line, String username) {
        Driver driver = new Driver(line.split(","), null);
        for (Service service : initServices())
            if (service.getDriverName().equals(username))
                driver.addService(service);

        return driver;
    }

    private static Admin initAdmin(String line, String username) throws Exception {
        String holder;
        ArrayList<Driver> drivers = new ArrayList<>();

        Scanner scInput = new Scanner(readUsersFile());
        while(scInput.hasNext())
            if((holder = scInput.nextLine()).endsWith(username))
                drivers.add(initDriver(holder, holder.split(",")[0]));

        return new Admin(line.split(","), drivers);
    }
}
