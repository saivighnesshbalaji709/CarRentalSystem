package entityclasses;
import java.util.*;

public class Lease{
    private int leaseID;
    private int vehicleID;
    private int customerID;
    private Date startDate;
    private Date endDate;
    private String Type;
	public Lease() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Lease(int vehicleID, int customerID, Date startDate, Date endDate,String Type) {
		super();
		this.vehicleID = vehicleID;
		this.customerID = customerID;
		this.startDate = startDate;
		this.endDate = endDate;
		this.Type=Type;
	}
	public int getVehicleID() {
		return vehicleID;
	}
	public void setVehicleID(int vehicleID) {
		this.vehicleID = vehicleID;
	}
	public int getCustomerID() {
		return customerID;
	}
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	
	
	@Override
	public String toString() {
		return "Lease [leaseID=" + leaseID + ", vehicleID=" + vehicleID + ", customerID=" + customerID + ", startDate="
				+ startDate + ", endDate=" + endDate + "]";
	}
	public int getLeaseID() {
		return leaseID;
	}
	public void setLeaseID(int leaseID) {
		this.leaseID = leaseID;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
    
    

}