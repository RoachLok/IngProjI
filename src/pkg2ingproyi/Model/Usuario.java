package pkg2ingproyi.Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Usuario {

    private String nombre;
    private String nombreUsuario;
    private String apellido;
    private String contrasena;
    private String dni;
    ArrayList<String> mensajes = new ArrayList<>();

    public Usuario(String nombre, String apellido, String dni, String contrasena, String nombreUsuario){
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.contrasena = contrasena;
        this.nombreUsuario = nombreUsuario;
        this.mensajes = mensajes;
    }

    public String getnombreUsuario(){
        return nombreUsuario;
    }
    public String getcontrasena(){
        return contrasena;
    }
    public String getnombre(){
        return nombre;
    }
    public String getdni(){
        return dni;
    }

    public boolean comprobarUsuario(String dni) {
        File fichero = new File("E:\\Users\\jtabo\\IdeaProjects\\IngProjI\\src\\resources\\usuario.txt");
        String linea;
        Scanner entrada = null;
        try {
            entrada = new Scanner(fichero);
            while(entrada.hasNext()) {
                linea = entrada.nextLine();
                if(linea.contains(dni)) {
                    /*Esto quiere decir que el dni ya existe en la base de datos*/
                    entrada.close();
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean CambiarContrasena(String dni, String nueva) throws FileNotFoundException {
        Scanner entrada = null;
        String linea = null;
        String[] partes = null;
        File fichero = new File("E:\\Users\\jtabo\\IdeaProjects\\IngProjI\\src\\resources\\usuario.txt");

        if(comprobarUsuario(dni)) {
            entrada = new Scanner(fichero);
            while(entrada.hasNext()) {
                linea = entrada.nextLine();
                if(linea.contains(this.contrasena)) {
                    /*Ahora cogemos la linea y seleccionamos la matricula asignada al dni*/
                    partes = linea.split(",");
                    partes[6] = nueva;
                }
            }
        }
        return true;
    }


    public boolean iniciarSesion(String usuario, String password) {
        Scanner entrada = null;
        String linea = null;
        String[] partes = null;
        File fichero = new File("E:\\Users\\jtabo\\IdeaProjects\\IngProjI\\src\\resources\\usuario.txt");
        if(usuario == null || password == null) {
            return false;
        }

        if(comprobarUsuario(usuario)) {
            try {
                entrada = new Scanner(fichero);
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


    public boolean isAdmin(String nombreUsuario) throws FileNotFoundException {
        FileReader fr = new FileReader("E:\\Users\\jtabo\\IdeaProjects\\IngProjI\\src\\resources\\usuario.txt");
        BufferedReader br = new BufferedReader(fr);
        String linea;
        String[] campos;
        if(this.comprobarUsuario(nombreUsuario)) {
            try {
                while((linea = br.readLine()) != null) {
                    if(linea.contains(nombreUsuario)) {
                        campos = linea.split(",");
                        if(campos[6].equals("true")) {
                            br.close();
                            return true;
                        }
                    }
                }
            }
            catch(Exception e) {
                System.out.println("Excepcion");
            }
        }
        return false;
    }
}