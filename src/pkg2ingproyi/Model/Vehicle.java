package pkg2ingproyi.Model;

import java.sql.Date;

public class Vehicle {
	private String 	id;
	private String 	bodywork;
	private String 	frame;
	private int		axisCount;
	private int		wheel_count;
	private int		paxCapacity;
	private Date 	buildDate;
	private Date 	acquireDate;
	private String 	nick;
	private String  vehicleType;
	private String	fuelType;
	private boolean adblue;
	private int 	permission;
	private int  	fuelTank;
	private double  literPerKm;
	private double 	initialKm;
	private double	currentKm;
	private String  departmentId;


	public Vehicle(String id, int paxCapacity, int permission, String bodywork, String frame) {
		this.id 			= id;
		this.paxCapacity 	= paxCapacity;
		this.permission 	= permission;
		this.bodywork		= bodywork;
		this.frame			= frame;
	}

	public Vehicle(String id, String bodyWork, String frame, int axisCount, int wheel_count, int paxCapacity,
				   Date buildDate, Date acquireDate, int permission, String nick, String vehicleType, String fuelType,
				   boolean adblue, int fuelTank, double literPerKm, double initialKm, double currentKm,
				   	String departmentId) {
		this.id 			= id;
		this.bodywork 		= bodyWork;
		this.frame			= frame;
		this.axisCount		= axisCount;
		this.wheel_count  	= wheel_count;
		this.paxCapacity	= paxCapacity;
		this.buildDate		= buildDate;
		this.acquireDate	= acquireDate;
		this.nick			= nick;
		this.vehicleType	= vehicleType;
		this.fuelType		= fuelType;
		this.adblue			= adblue;
		this.permission 	= permission;
		this.fuelTank		= fuelTank;
		this.literPerKm 	= literPerKm;
		this.initialKm		= initialKm;
		this.currentKm		= currentKm;
		this.departmentId	= departmentId;
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
		return frame;
	}

	public void setChassis(String frame) {
		this.frame = frame;
	}

	public int getAxisCount() {
		return axisCount;
	}

	public void setAxisCount(int axisCount) {
		this.axisCount = axisCount;
	}

	public int getWheel_count() {
		return wheel_count;
	}

	public void setWheel_count(int wheel_count) {
		this.wheel_count = wheel_count;
	}

	public int getPax() {
		return paxCapacity;
	}

	public void setPax(int pax) {
		this.paxCapacity = pax;
	}

	public Date getBuildDate() {
		return buildDate;
	}

	public void setBuildDate(Date buildDate) {
		this.buildDate = buildDate;
	}

	public Date getAcquireDate() {
		return acquireDate;
	}

	public void setAcquireDate(Date acquireDate) {
		this.acquireDate = acquireDate;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getFuelType() {
		return fuelType;
	}

	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}

	public boolean hasAdBlue() {
		return adblue;
	}

	public void setAdblue (boolean hasAdblue) {
		this.adblue = hasAdblue;
	}

	public int getPermission() {
		return permission;
	}

	public void setPermission(int permission) {
		this.permission = permission;
	}

	public int getFuelTank() {
		return fuelTank;
	}

	public void setFuelTank(int fuelTank) {
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

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
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