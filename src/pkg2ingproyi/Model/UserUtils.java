package pkg2ingproyi.Model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.net.HttpURLConnection;
import java.net.URL;

import java.io.FileNotFoundException;
import java.io.IOException;


public class UserUtils{

    static boolean[] loginDetails = {false, false};

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
    private static Login parseloginJsonObject(JSONObject object) {
        return  new Login (  /*resolver el que sea abstracta la clase*/
                (String) object.get("username"),
                (String)  object.get("password"),
                (String)object.get("isAdmin" ));
    }


    public static boolean[] login(String username, String password) {
        if (username.equals("") || password.equals("")) {
            return loginDetails;
        }
        try {
        URL loginURL = new URL("https://pu2ie1oa0lzqala-sjpruebas.adb.eu-frankfurt-1.oraclecloudapps.com/ords/admin/login/?q={%22$and%22:[{%22username%22:%22" + username + "%22},{%22password%22:%22" + password + "%22}]}");
        HttpURLConnection loginConection = (HttpURLConnection) loginURL.openConnection();
        loginConection.setRequestMethod("GET");
        loginConection.connect();

            /*Comprobamos si nos hemos conectado correctamente*/
            if (loginConection.getResponseCode() != 200) {
                System.err.print("Wrong connection");
            } else {
                Scanner sc = new Scanner(loginURL.openStream());
                StringBuilder loginJSONString;
                loginJSONString = new StringBuilder();
                while (sc.hasNext()) {
                    loginJSONString.append(sc.nextLine());
                }
                System.out.println(loginJSONString);

                //Parse String into a JSONArray.
                JSONParser jsonParser = new JSONParser();
                JSONObject loginJSONObject = (JSONObject) jsonParser.parse(loginJSONString.toString());
                loginDetails[0] = 1== (int)(long) loginJSONObject.get("count");
                System.out.println(loginJSONObject);
                loginDetails[1] = 'Y' ==  ((String) (((JSONObject) ((JSONArray)(loginJSONObject.get("items"))).get(0)).get("isadmin"))).charAt(0);
                return loginDetails; //CLARIFICATION: "items" is the name of the object containing our desired items in the oracleCloud api
            }

        }  catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return loginDetails;
    }


    public static boolean isAdmin(String username) throws FileNotFoundException {
        if (username == null)
            return false;
        return loginDetails[1];
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
                (String) object.get("title"     ),
                (String) object.get("startTime" ),
                (String) object.get("endTime"   ),
                (String) object.get("pickup"    ),
                (String) object.get("transit"   ),
                (String) object.get("arrival"   ),
                (String) object.get("driverName"),
                (String) object.get("vehicleID" ),
                (String) object.get("identifier"),
                (String) object.get("contractor"),
                (String) object.get("pricing"   ),
                (String) object.get("author"    ),
        (int)   (long)   object.get("status"    ));
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
        ArrayList<Driver> drivers = new ArrayList<>();

        Scanner scInput = new Scanner(readUsersFile());
        while(scInput.hasNext())
            if((holder = scInput.nextLine()).endsWith(username))
                drivers.add(initDriver(holder, holder.split(",")[0]));

        return new Admin(line.split(","), drivers);
    }
}
