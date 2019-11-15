package pkg2ingproyi.Model;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class Administrador {

    private String nombre;
    private String nombreUsuario;
    private String apellido;
    private String dni;
    private ArrayList<String> mensajes;

    public Administrador(String nombre, String apellido, String nombreUsuario, String dni, ArrayList<String> mensajes) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.nombreUsuario = nombreUsuario;
        this.mensajes = mensajes;
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getApellido() {
        return this.apellido;
    }

    public String getnombreUsuario() {
        return this.nombreUsuario;
    }

    public String getDNI() {
        return this.dni;
    }

    public ArrayList<String> getMensajes() {
        return this.mensajes;
    }

    /*Esta funcion crea una contrase�a random de 6 caracteres*/
    private String contrasena() {
        String contrasena = "";
        Random rnd = new Random();
        int i;
        for (i = 0; i < 6; i++) {
            contrasena += (char) (rnd.nextInt(91) + 65);
        }
        return contrasena;
    }

    /*Si devuelve true es que no esta en la base de datos
     * si devuelve false es que no existe*/
    private Boolean ComprobarUsuario(String dni) {
        File fichero = new File("usuario.txt");
        String linea;
        Scanner entrada;
        try {
            entrada = new Scanner(fichero);
            while (entrada.hasNext()) {
                linea = entrada.nextLine();
                if (linea.contains(dni)) {
                    /*Esto quiere decir que el dni ya existe en la base de datos*/
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void AnyadirUsuario(String nombre, String dni, String NombreUsuario, String Matricula) throws IOException {
        File fichero = new File("usuario.txt");
        BufferedWriter bw;
        FileWriter fw;
        String contrasena = contrasena();
        String escribir = nombre + "," + apellido + "," + NombreUsuario + "," + dni + "," + Matricula + "," + contrasena;

        fw = new FileWriter(fichero.getAbsoluteFile(), true);
        bw = new BufferedWriter(fw);
        bw.write(escribir);
        bw.close();
    }

    public Boolean CrearUsuario(String nombre, String dni, String NombreUsuario, String Matricula) throws IOException {

        if (!ComprobarUsuario(dni)) {
            AnyadirUsuario(nombre, dni, NombreUsuario, Matricula);
            return true; /*Se le ha dado de alta en la base de datos*/
        } else {
            /*No se puede dar de alta al usuario porque ya existe*/
            return false;
        }
    }

    public String CamiondelConductor(String dni) {
        Scanner entrada;
        String linea, retorno = null;
        String[] partes;
        File fichero = new File("camiones.txt");
        if (!ComprobarUsuario(dni)) {
            return "Error el dni no existe en la base de datos";
        } else {
            try {
                entrada = new Scanner(fichero);
                while (entrada.hasNext()) {
                    linea = entrada.nextLine();
                    if (linea.contains(dni)) {
                        /*Ahora cogemos la linea y seleccionamos la matricula asignada al dni*/
                        partes = linea.split(",");
                        retorno = "El camion es: " + partes[5] + "y el conductor es: " + "Nombre: " + partes[1] + "Apellido: " + partes[2];
                    }
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return retorno;
    }
}



