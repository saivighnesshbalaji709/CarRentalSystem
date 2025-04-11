package entityclasses;
import java.util.*;

public class Vehicle {
	private int vehicleID; 
	private String make; 
	private String model; 
	private int year; 
	private double dailyrate;
	private String status;
	private int passengercapacity; 
	private double enginecapacity;
	
	public Vehicle() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Vehicle(int vehicleID, String make, String model, int year, double dailyrate, String status,
			int passengercapacity, double enginecapacity) {
		super();
		this.vehicleID = vehicleID;
		this.make = make;
		this.model = model;
		this.year = year;
		this.dailyrate = dailyrate;
		this.status = status;
		this.passengercapacity = passengercapacity;
		this.enginecapacity = enginecapacity;
	}

	public int getVehicleID() {
		return vehicleID;
	}

	public void setVehicleID(int vehicleID) {
		this.vehicleID = vehicleID;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public double getDailyrate() {
		return dailyrate;
	}

	public void setDailyrate(double dailyrate) {
		this.dailyrate = dailyrate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getPassengercapacity() {
		return passengercapacity;
	}

	public void setPassengercapacity(int passengercapacity) {
		this.passengercapacity = passengercapacity;
	}

	public double getEnginecapacity() {
		return enginecapacity;
	}

	public void setEnginecapacity(double enginecapacity) {
		this.enginecapacity = enginecapacity;
	}

	@Override
	public String toString() {
		return "Vehicle [vehicleID=" + vehicleID + ", make=" + make + ", model=" + model + ", year=" + year
				+ ", dailyrate=" + dailyrate + ", status=" + status + ", passengercapacity=" + passengercapacity
				+ ", enginecapacity=" + enginecapacity + "]";
	} 

}