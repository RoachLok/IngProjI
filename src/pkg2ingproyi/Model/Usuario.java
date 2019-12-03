package pkg2ingproyi.Model;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.FileWriter;
import java.util.Date;
import java.util.Iterator;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Usuario {

	private String nombre;
	private String nombreUsuario;
	private String apellido;
	private String contrasena;
	private String dni;
	private Usuario Admin;
	 ArrayList<Mensaje> mensajes = new ArrayList<>();
	    
	 public Usuario(String nombre, String apellido, String dni, String contrasena, String nombreUsuario){
		 this.nombre = nombre;
		 this.apellido = apellido;
		 this.dni = dni;
		 this.contrasena = contrasena;
		 this.nombreUsuario = nombreUsuario;
		 this.mensajes = new ArrayList<Mensaje>();
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
	 public String getApellido() {
		 return this.apellido;
	 }
	 public String getdni(){
		 return dni;
	    }
	 public ArrayList<Mensaje> getMensajes() {
		 return mensajes;
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
	
	public boolean enviarMensaje(Usuario receptor, String mensaje) {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		JSONObject obj = new JSONObject();
		Mensaje men = new Mensaje(this, receptor, mensaje);
		if(mensaje == null)
			return false;
		if(this.comprobarUsuario(receptor.getdni()) == true) {
			/*Existe ese usuario entonces se puede enviar*/
			receptor.getMensajes().add(men);
			/*Ahora se añade al fichero global que contiene todos los mensajes con JSON*/
			obj.put("Emisor", this.getdni());
			obj.put("Receptor", receptor.getdni());
			obj.put("Mensaje", mensaje);
			obj.put("Leido", false);
			obj.put("TimeStamp", dateFormat.format(date));
			try {
				FileWriter file = new FileWriter("mensajes.txt");
				file.write(obj.toJSONString());
				file.flush();
				file.close();
			} catch (IOException e) {
				return false;
			}
		}
		return true;
	}
	
	
	public boolean mensajeLeido(Usuario envia, Usuario recibe, String cadena) {
		/*Cuando el usuario lo lea se pasa el campo leido del json a true y ya*/
		 try (Reader reader = new FileReader("mensajes.json")) {

	            JSONObject jsonObject = (JSONObject) parser.parse(reader);
	       
	            String emisor = (String) jsonObject.get("Emisor");
	            String receptor= (String) jsonObject.get("Receptor");
	            String mensaje = (String) jsonObject.get("Mensaje");
	            boolean leido = (boolean) jsonObject.get("leido");
	            String horaenviado = (String) jsonObject.get("TimeStamp");
	            // loop array
	            JSONArray msg = (JSONArray) jsonObject.get("messages");
	            Iterator<String> iterator = msg.iterator();
	            while (iterator.hasNext()) {
	                System.out.println(iterator.next());
	            }

	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
		
		return false;
	}
	
}
