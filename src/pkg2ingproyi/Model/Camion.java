package pkg2ingproyi.Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Camion {
	private String Matricula;
	private String DNI;
	
	public Camion(String matricula, String dni) {
		this.Matricula = matricula;
		this.DNI = dni;
	}
	
	public String getMatricula() {
		return this.Matricula;
	}
	
	public String getDNI() {
		return this.DNI;
	}
	
	public Boolean comprobarMatricula(String matricula) {
		File fichero = new File("camiones.txt");
		String linea;
		Scanner entrada = null;
		try {
			entrada = new Scanner(fichero);
			while(entrada.hasNext()) {
				linea = entrada.nextLine();
				if(linea.contains(matricula)) {
					entrada.close();
					return true; /*La matricula existe en la base de datos*/
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public String consultarVelocidad(String matricula) {
		Scanner entrada = null;
		String linea = null;
		String[] partes = null;
		File fichero = new File("camiones.txt");
		if(this.comprobarMatricula(matricula) == true) {
			try {
				entrada = new Scanner(fichero);
				while(entrada.hasNext()) {
					linea = entrada.nextLine();
					if(linea.contains(matricula)) {
						partes = linea.split(",");
						entrada.close();
						return partes[3] + "Km/h";
						}
				}
	
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} 
			
		}
		return "-1";
	}
	
	public String consultarTiempo(String matricula) {
		Scanner entrada = null;
		String linea = null, devolver = null;
		String[] partes = null;
		File fichero = new File("camiones.txt");
		if(this.comprobarMatricula(matricula) == true) {
			try {
				entrada = new Scanner(fichero);
				while(entrada.hasNext()) {
					linea = entrada.nextLine();
					if(linea.contains(matricula)) {
						partes = linea.split(",");
						entrada.close();
						devolver += "El camion esta " + partes[2] + ", lleva " + partes[4] + "parado y " + partes[5] + "en movimiento./n";
						return devolver;
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}  
		}
		return "Error a la hora de la consulta";
	}
	
	/*A partir de la matricula del camion encuentra el dni del conductor*/
	public String consultarConductor(String matricula) {
		Scanner entrada = null;
		String linea = null, dni = null, devolver = null;
		String[] partes = null;
		File fichero = new File("camiones.txt");
		File fichero2 = new File("usuario.txt");
		if(this.comprobarMatricula(matricula) == true) { /*cogemos el dni*/
			try {
				entrada = new Scanner(fichero);
				while(entrada.hasNext()) {
					linea = entrada.nextLine();
					if(linea.contains(matricula)) {
						partes = linea.split(",");
						entrada.close();
						dni = partes[1];
						
						linea = null;
						entrada = new Scanner(fichero2);
						while(entrada.hasNext()) {
							linea = entrada.nextLine();
							if(linea.contains(dni)) {
								partes = linea.split(",");
								entrada.close();
								devolver += partes[0] + " " + partes[1] + " " + partes[3];
								return devolver;
								}	
						}
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}  
			
		}
		return "Error a la hora de buscar el conductor";
	}
	
}
