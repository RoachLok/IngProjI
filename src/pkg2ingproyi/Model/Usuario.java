package pkg2ingproyi.Model;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Usuario {

	private String nombre;
	private String nombreUsuario;
	private String apellido;
	private String contrasena;
	private String dni;	    
	 ArrayList<String> mensajes = new ArrayList<>();
	    
	 public Usuario(String nombre, String apellido, String dni, String contrasena, String nombreUsuario, ArrayList<String> mensajes){
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
			File fichero = new File("usuario.txt");
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
		 File fichero = new File("usuario.txt");
	     
		 if(comprobarUsuario(dni) == true) {
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
		File fichero = new File("camiones.txt");
		if(usuario == null || password == null) {
			return false;
		}
		
		if(comprobarUsuario(usuario) == true) {
			try {
				entrada = new Scanner(fichero);
				while(entrada.hasNext()) {
					linea = entrada.nextLine();
					if(linea.contains(usuario)) {
						partes = linea.split(",");
						if(partes[5] == password) {
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
		FileReader fr = new FileReader("usuario.txt");
	    BufferedReader br = new BufferedReader(fr);
	    String linea;
		String[] campos;
		if(this.comprobarUsuario(nombreUsuario) == true) {
			try {
			    while((linea = br.readLine()) != null) {
			    	if(linea.contains(nombreUsuario)) {
			    		campos = linea.split(",");
			    		if(campos[6] == "true")
			    			br.close();
			    			return true;
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
