package pkg2ingproyi.SupervisorView;

public class Medicion {
	private String tipo;
	private String service_id;
	private double valor;
	private String timestamp;
	public Medicion(String tipo, String service_id, double valor, String timestamp) {
		super();
		this.tipo = tipo;
		this.service_id = service_id;
		this.valor = valor;
		this.timestamp = timestamp;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getService_id() {
		return service_id;
	}
	public void setService_id(String service_id) {
		this.service_id = service_id;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
}
