package pkg2ingproyi.Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class UserUtils {

    private static boolean comprobarUsuario(String dni, File file) throws FileNotFoundException {
        Scanner entrada = new Scanner(file);
        String linea;
        while(entrada.hasNext()) {
            linea = entrada.nextLine();
            if(linea.contains(dni)) {
                /*Esto quiere decir que el dni ya existe en l   a base de datos*/
                entrada.close();
                return true;
            }
        }
        return false;
    }

    public static boolean iniciarSesion(String usuario, String password) throws FileNotFoundException {
        Scanner entrada;
        String linea;
        String[] partes;
        File file = readUsersFile();

        if(usuario == null || password == null) {
            return false;
        }

        if(comprobarUsuario(usuario, file)) {
            try {
                entrada = new Scanner(file);
                while(entrada.hasNext()) {
                    linea = entrada.nextLine();
                    if(linea.contains(usuario)) {
                        partes = linea.split(",");
                        if(partes[5].equals(password)) {
                            entrada.close();
                            return true;
                        }
                    }
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean isAdmin(String username) throws FileNotFoundException {
        File file = readUsersFile();
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String linea;
        String[] campos;
        if(comprobarUsuario(username, file)) {
            try {
                while((linea = br.readLine()) != null) {
                    if(linea.contains(username)) {
                        campos = linea.split(",");
                        if(campos[6].equals("true")) {
                            br.close();
                            return true;
                        }
                    }
                }
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private static File readUsersFile() {
        return new File("src/resources/usuario.txt").getAbsoluteFile();
    }

    public static User createUser(String username, String password, boolean isAdmin) {
        if (!isAdmin) {

            Driver driver = new Driver(username, password, "dni", "name", "surname", false, "adminNick");
            return driver;
        }

    }
}
