package pkg2ingproyi.Model;

public class Mensaje {

	private Usuario receptor;
	private Usuario emisario;
	private String mensaje;
	
	public Mensaje(Usuario emisario, Usuario receptor, String mensaje) {
		this.emisario = emisario;
		this.receptor = receptor;
		this.mensaje = mensaje;
	}

	public Usuario getReceptor() {
		return receptor;
	}

	public void setReceptor(Usuario receptor) {
		this.receptor = receptor;
	}

	public Usuario getEmisario() {
		return emisario;
	}

	public void setEmisario(Usuario emisario) {
		this.emisario = emisario;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	
}
