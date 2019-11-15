package pkg2ingproyi.Model;

import java.io.*;
import java.util.ArrayList;

public class Usuario {

    private String nombreUsuario;
    private String contrasena;
    private String nombre;
    private String dni;

    ArrayList<String> listademensajes = new ArrayList<>();

    /* public Usuario(ArrayList<String> mensajes){
         this.listademensajes = mensajes;
         }*/
    public Usuario() {

    }
	    
	    
	 /*   
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
	 
	 public boolean CambiarContrasena(String dni, String nueva) throws FileNotFoundException {
		 Scanner entrada = null;
		 String linea = null;
		 String[] partes = null;
		 File fichero = new File("usuario.txt");
	     
		 if(ComprobarUsuario(dni) == true) {
			 entrada = new Scanner(fichero);
	         while(entrada.hasNext()) {
	        	 linea = entrada.nextLine();
	        	 if(linea.contains(this.contrasena)) {
	        		 /*Ahora cogemos la linea y seleccionamos la matricula asignada al dni*/
	             /*    partes = linea.split(","); 
	                 partes[6] = nueva;
	                }
	            }
	        }
	        return true;
	    }
	    */

    public static boolean iniciarSesion(String usuario, String password) {
        String cadena;
        FileReader f;
        boolean permiso = false;
        try {
            File archivo = new File("resources/usuario.txt");

            f = new FileReader(archivo);
            BufferedReader b = new BufferedReader(f);
            while ((cadena = b.readLine()) != null) {
                int coma = cadena.indexOf("-");
                String usuarioB = cadena.substring(0, coma).trim();
                String contrasena = cadena.substring(coma + 1).trim();
                if (usuario.equals(usuarioB) && password.equals(contrasena)) {
                    permiso = true;
                }


            }
            b.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return permiso;


    }
}
