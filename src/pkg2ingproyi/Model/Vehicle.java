package pkg2ingproyi.Model;

public class Vehicle {
	private String 	id;
	private String 	bodywork;
	private String 	chassis;
	private int		pax;
	private String 	date;
	private String 	nick;
	private int 	permission;
	private double  fuelTank;
	private double  literPerKm;
	private double 	initialKm;
	private double	currentKm;

	public Vehicle(String id, int pax, int permission, String bodywork, String chassis) {
		this.id 		= id;
		this.pax		= pax;
		this.permission = permission;
		this.bodywork	= bodywork;
		this.chassis	= chassis;
	}

	public Vehicle(String id, String bodyWork, String chassis, int pax, String date, int permission, String nick,
				   	double fuelTank, double literPerKm, double initialKm, double currentKm) {
		this.id 		= id;
		this.bodywork 	= bodyWork;
		this.chassis	= chassis;
		this.pax		= pax;
		this.date		= date;
		this.nick		= nick;
		this.permission = permission;
		this.fuelTank	= fuelTank;
		this.literPerKm = literPerKm;
		this.initialKm	= initialKm;
		this.currentKm	= currentKm;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBodywork() {
		return bodywork;
	}

	public void setBodywork(String bodywork) {
		this.bodywork = bodywork;
	}

	public String getChassis() {
		return chassis;
	}

	public void setChassis(String chassis) {
		this.chassis = chassis;
	}

	public int getPax() {
		return pax;
	}

	public void setPax(int pax) {
		this.pax = pax;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public int getPermission() {
		return permission;
	}

	public void setPermission(int permission) {
		this.permission = permission;
	}

	public double getFuelTank() {
		return fuelTank;
	}

	public void setFuelTank(double fuelTank) {
		this.fuelTank = fuelTank;
	}

	public double getLiterPerKm() {
		return literPerKm;
	}

	public void setLiterPerKm(double literPerKm) {
		this.literPerKm = literPerKm;
	}

	public double getInitialKm() {
		return initialKm;
	}

	public void setInitialKm(double initialKm) {
		this.initialKm = initialKm;
	}

	public double getCurrentKm() {
		return currentKm;
	}

	public void setCurrentKm(double currentKm) {
		this.currentKm = currentKm;
	}

	public String getPermissionName() {
		switch (permission) {
			case 1:
				return "AM";
			case 2:
				return "A1";
			case 3:
				return "A2";
			case 4:
				return "A";
			case 5:
				return "B1";
			case 6:
				return "B";
			case 7:
				return "C1";
			case 8:
				return "C";
			case 9:
				return "D1";
			case 10:
				return "D";
			case 11:
				return "BE";
			case 12:
				return "C1E";
			case 13:
				return "CE";
			case 14:
				return "D1E";
			case 15:
				return "DE";
			default:
				return "";
		}
	}

	public double kmDone() {
		return currentKm - initialKm;
	}
}