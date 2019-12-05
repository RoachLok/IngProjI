package pkg2ingproyi.Model;

public class Mensaje {

	private Driver receptor;
	private Driver emisario;
	private String mensaje;
	
	public Mensaje(Driver emisario, Driver receptor, String mensaje) {
		this.emisario = emisario;
		this.receptor = receptor;
		this.mensaje = mensaje;
	}

	public Driver getReceptor() {
		return receptor;
	}

	public void setReceptor(Driver receptor) {
		this.receptor = receptor;
	}

	public Driver getEmisario() {
		return emisario;
	}

	public void setEmisario(Driver emisario) {
		this.emisario = emisario;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	
}
