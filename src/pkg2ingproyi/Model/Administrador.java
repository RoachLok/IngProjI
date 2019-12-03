package pkg2ingproyi.Model;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class Administrador extends Usuario{

	private String nombre;
	private String nombreUsuario;
	private String apellido;
	private String dni;
	private ArrayList<String> mensajes;
	
	public Administrador(String nombre, String apellido, String nombreUsuario, String dni, ArrayList<String> mensajes) {
		
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
	public String contrasena() {
		String contrasena = "";
		Random rnd = new Random();
		int i;
		for(i=0; i<6; i++) {
			contrasena += (char)(rnd.nextInt(91) + 65);
		}
		return contrasena;
	}
	
	
	public void AnyadirUsuario(String nombre, String dni, String NombreUsuario, String Matricula) throws IOException {
		File fichero = new File("usuario.txt");
		BufferedWriter bw = null;
	    FileWriter fw = null;
		String contrasena = contrasena();
		String escribir = nombre +"," + apellido + "," + NombreUsuario + "," + dni + "," + Matricula + "," + contrasena;
		
		fw = new FileWriter(fichero.getAbsoluteFile(), true);
        bw = new BufferedWriter(fw);
        bw.write(escribir);
        bw.close();
	}
	
	public Boolean CrearUsuario(String nombre, String dni, String NombreUsuario, String Matricula) throws IOException {
		
		if(comprobarUsuario(dni) == false) {
			AnyadirUsuario(nombre,dni,NombreUsuario,Matricula);
			return true; /*Se le ha dado de alta en la base de datos*/
		}else {
			/*No se puede dar de alta al usuario porque ya existe*/
			return false; 
		}
	}
	
	public String CamiondelConductor(String dni) {
		Scanner entrada = null;
		String linea = null, retorno = null;
		String[] partes = null;
		File fichero = new File("camiones.txt");
		if(comprobarUsuario(dni) == false) {
			return "Error el dni no existe en la base de datos";
		}else {
			try {
				entrada = new Scanner(fichero);
				while(entrada.hasNext()) {
					linea = entrada.nextLine();
					if(linea.contains(dni)) {
						/*Ahora cogemos la linea y seleccionamos la matricula asignada al dni*/
						partes = linea.split(",");
						retorno = "El camion es: " + partes[5] + "y el conductor es: " + "Nombre: " + partes[0] + "Apellido: " + partes[1];
						}
				}
	
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return retorno;
	}
	
	
	public String ConductordelCamion(String matricula) {
				
		File fichero = new File("camiones.txt");
		String dni = null;
		String linea;
		Scanner entrada = null;
		String[] partes = null;
		if(matricula == null) return "La entrada es nula";
		try {
			entrada = new Scanner(fichero);
			while(entrada.hasNext()) {
				linea = entrada.nextLine();
				if(linea.contains(matricula)) {
					partes = linea.split(",");
					dni = partes[2];
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return dni;
	}
	
	public boolean ModificarDatosUsuario(String campo, String nuevo, String dni) throws FileNotFoundException {
		String[] cambios;
		String retorno;
		File fichero = new File("usuario.txt");
		BufferedWriter bw = null;
	    FileWriter fw = null;
	    FileReader fr = new FileReader("usuario.txt");
	    BufferedReader br = new BufferedReader(fr);
	    String linea;
		/*Buscamos si existe el dni, vemos que parte quiere, si es nombre campo 1 etc
		 * sustituimos el dato y reescribimos*/
		if(comprobarUsuario(dni) == true) {
			if(campo == "nombre") {
				try {
				    while((linea = br.readLine()) != null) {
				    	if(linea.contains(dni)) {
				    		cambios = linea.split(",");
				    		retorno = nuevo + "," + cambios[1] + "," + cambios[2] + "," + cambios[3] + "," + cambios[4] + "," + cambios[6] + "," + cambios[7];
				    		fw = new FileWriter(fichero.getAbsoluteFile(), true);
				            bw = new BufferedWriter(fw);
				            bw.write(retorno);
				    	}
				    	br.close();
				    }
				}
				catch(Exception e) {
				      System.out.println("Excepcion");
				    }
			
			} else if(campo == "Apellido") {
				try {
				    while((linea = br.readLine()) != null) {
				    	if(linea.contains(dni)) {
				    		cambios = linea.split(",");
				    		retorno = cambios[0] + "," + nuevo + "," + cambios[2] + "," + cambios[3] + "," + cambios[4] + "," + cambios[6] + "," + cambios[7];
				    		fw = new FileWriter(fichero.getAbsoluteFile(), true);
				            bw = new BufferedWriter(fw);
				            bw.write(retorno);
				    	}
				    	br.close();
				    }
				}
				catch(Exception e) {
				      System.out.println("Excepcion");
				    }
				
			} else if(campo == "NombreUsuario") {
				
			} else if(campo == "DNI") {
				
			} else if(campo == "Matricula") {
				
			} else if(campo == "Contrasena") {
			
			}
		}
			
			return true;
	}
	
	
	
	
}



