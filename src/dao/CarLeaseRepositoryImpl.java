package dao;
import myexceptions.*;
import entityclasses.*;
import util.DBConnUtil;
import java.sql.*;
import java.util.*;
import java.util.Date;

public class CarLeaseRepositoryImpl implements ICarLeaseRepository {
    private Connection con;
    private Map<Integer, List<Payment>> customerPayments = new HashMap<>();

    public CarLeaseRepositoryImpl() throws SQLException {
        con = DBConnUtil.getconnectiondb();
    }

    @Override
    public boolean addCar(Vehicle car) {
        boolean addc = false;
        try {
            PreparedStatement ps = con.prepareStatement("insert into vehicle values (?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setInt(1, car.getVehicleID());
            ps.setString(2, car.getMake());
            ps.setString(3, car.getModel());
            ps.setInt(4, car.getYear());
            ps.setDouble(5, car.getDailyrate());
            ps.setString(6, car.getStatus());
            ps.setInt(7, car.getPassengercapacity());
            ps.setDouble(8, car.getEnginecapacity());
            ps.executeUpdate();
            addc = true;
        } catch (SQLException e) {
            System.out.println("Could not add the car.");
            e.printStackTrace();
        }
        return addc;
    }

    @Override
    public boolean removeCar(int carID) {
        boolean removec = false;
        try {
            PreparedStatement ps = con.prepareStatement("delete from vehicle where vehicleid = ?");
            ps.setInt(1, carID);
            int r = ps.executeUpdate();
            if (r > 0) {
                removec = true;
            }
        } catch (SQLException e) {
            System.out.println("Could not remove the car.");
            e.printStackTrace();
        }
        return removec;
    }

    @Override
    public HashMap<Integer, Vehicle> listAvailableCars() {
        HashMap<Integer, Vehicle> availableCars = new HashMap<>();
        try {
            PreparedStatement ps = con.prepareStatement("select * from vehicle where status = 'available'");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Vehicle v = new Vehicle(
                    rs.getInt("vehicleid"),
                    rs.getString("make"),
                    rs.getString("model"),
                    rs.getInt("year"),
                    rs.getDouble("dailyrate"),
                    rs.getString("status"),
                    rs.getInt("passengercapacity"),
                    rs.getDouble("enginecapacity")
                );
                availableCars.put(v.getVehicleID(), v);
            }
        } catch (SQLException e) {
            System.out.println("Could not list available cars.");
            e.printStackTrace();
        }
        return availableCars;
    }

    @Override
    public HashMap<Integer, Vehicle> listRentedCars() {
        HashMap<Integer, Vehicle> rentedCars = new HashMap<>();
        try {
            PreparedStatement ps = con.prepareStatement("select * from vehicle where status = 'rented'");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Vehicle v = new Vehicle(
                    rs.getInt("vehicleid"),
                    rs.getString("make"),
                    rs.getString("model"),
                    rs.getInt("year"),
                    rs.getDouble("dailyrate"),
                    rs.getString("status"),
                    rs.getInt("passengercapacity"),
                    rs.getDouble("enginecapacity")
                );
                rentedCars.put(v.getVehicleID(), v);
            }
        } catch (SQLException e) {
            System.out.println("Could not list rented cars.");
            e.printStackTrace();
        }
        return rentedCars;
    }

    @Override
    public boolean findCarById(int carID) throws CarNotFoundException {
        boolean found = false;
        try {
            PreparedStatement ps = con.prepareStatement("select * from vehicle where vehicleid = ?");
            ps.setInt(1, carID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
            else {
            	throw new CarNotFoundException("No vehicle found with this id");
            }
        } catch (SQLException e) {
            System.out.println("Problem finding car by ID: ");
            
        }
        return found;
    }

    @Override
    public boolean addCustomer(Customer customer) {
        boolean added = false;
        try {
            PreparedStatement ps = con.prepareStatement("insert into customer values (?, ?, ?, ?, ?)");
            ps.setInt(1, customer.getCustomerID());
            ps.setString(2, customer.getFirstName());
            ps.setString(3, customer.getLastName());
            ps.setString(4, customer.getEmail());
            ps.setString(5, customer.getPhoneNumber());
            ps.executeUpdate();
            added = true;
        } catch (SQLException e) {
            System.out.println("Could not add customer.");
            e.printStackTrace();
        }
        return added;
    }

    @Override
    public boolean removeCustomer(int customerID) {
        boolean removed = false;
        try {
            PreparedStatement ps = con.prepareStatement("delete from customer where customerid = ?");
            ps.setInt(1, customerID);
            int r = ps.executeUpdate();
            if (r > 0) {
                removed = true;
            }
        } catch (SQLException e) {
            System.out.println("Unable to remove customer");
            e.printStackTrace();
        }
        return removed;
    }

    @Override
    public HashMap<Integer, Customer> listCustomers() {
        HashMap<Integer, Customer> customers = new HashMap<>();
        try {
            PreparedStatement ps = con.prepareStatement("select * from customer");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Customer c = new Customer(
                    rs.getInt("customerid"),
                    rs.getString("firstname"),
                    rs.getString("lastname"),
                    rs.getString("email"),
                    rs.getString("phonenumber")
                );
                customers.put(c.getCustomerID(), c);
            }
        } catch (SQLException e) {
            System.out.println("Problem listing customers: ");
            e.printStackTrace();
        }
        return customers;
    }

    @Override
    public Customer findCustomerById(int customerID) throws CustomerNotFoundException{
        Customer customer = null;
        try {
            PreparedStatement ps = con.prepareStatement("select * from customer where customerid = ?");
            ps.setInt(1, customerID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                customer = new Customer(
                    rs.getInt("customerid"),
                    rs.getString("firstname"),
                    rs.getString("lastname"),
                    rs.getString("email"),
                    rs.getString("phonenumber")
                );
            }
            else {
            	throw new CustomerNotFoundException("No customer found with this id");
            }
        } catch (SQLException e) {
            System.out.println("Unable to find customer by ID");
            
        }
        return customer;
    }


    @Override
    public Lease createLease(int customerID, int carID, Date startDate, Date endDate, String type) throws CarNotFoundException {
        Lease lease = null;
        try {
            PreparedStatement ps = con.prepareStatement(
                "insert into lease(customerid, vehicleid, startdate, enddate,type) value (?,?,?,?,?)",
                Statement.RETURN_GENERATED_KEYS 
            );
            ps.setInt(1, customerID);
            ps.setInt(2, carID);
            ps.setDate(3, new java.sql.Date(startDate.getTime()));
            ps.setDate(4, new java.sql.Date(endDate.getTime()));
            ps.setString(5, type);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int leaseID = rs.getInt(1);
                lease = new Lease(carID, customerID, startDate, endDate, type);
                lease.setLeaseID(leaseID);
                PreparedStatement updatePs = con.prepareStatement(
                    "update vehicle set status = 'rented' where vehicleid = ?"
                );
                updatePs.setInt(1, carID);
                updatePs.executeUpdate();
            }
        }
           catch (SQLIntegrityConstraintViolationException e) {
                throw new CarNotFoundException("Car or customer not found with given ID");
            }
        
        catch (SQLException e) {
            System.out.println("Unable to creating lease");
            e.printStackTrace();
        }
        return lease;
    }

    @Override
    public boolean returnCar(int leaseID) throws LeaseNotFoundException{
        boolean returned = false;
        try {
            PreparedStatement getPs = con.prepareStatement(
                "select vehicleid from lease where leaseid = ?"
            );
            getPs.setInt(1, leaseID);
            ResultSet rs = getPs.executeQuery();
            
            if (rs.next()) {
                int vehicleID = rs.getInt("vehicleid");
                PreparedStatement deletePs = con.prepareStatement(
                    "delete from lease where leaseid = ?"
                );
                deletePs.setInt(1, leaseID);
                deletePs.executeUpdate();
                PreparedStatement updatePs = con.prepareStatement(
                    "update vehicle set status = 'available' WHERE vehicleid = ?"
                );
                updatePs.setInt(1, vehicleID);
                updatePs.executeUpdate();
                
                returned = true;
            }
            else {
            	throw new LeaseNotFoundException("No lease found with this id");
            }
        } catch (SQLException e) {
        	System.out.println("Error while returning car");
            
        }
        return returned;
    }

    @Override
    public HashMap<Integer, Lease> listActiveLeases() {
        HashMap<Integer, Lease> activeLeases = new HashMap<>();
        try {
            PreparedStatement ps = con.prepareStatement("select * from lease");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Lease lease = new Lease(
                    rs.getInt("vehicleid"),
                    rs.getInt("customerid"),
                    rs.getDate("startdate"),
                    rs.getDate("enddate"),
                    rs.getString("type")
                );
                lease.setLeaseID(rs.getInt("leaseid"));
                activeLeases.put(lease.getLeaseID(), lease);
            }
        } catch (SQLException e) {
            System.out.println("unable to list active leases");
            e.printStackTrace();
        }
        return activeLeases;
    }

    @Override
    public HashMap<Integer, Lease> listLeaseHistory() {
        return listActiveLeases(); 
    }

    @Override
    public boolean recordPayment(Lease lease, double amount) {
        boolean recorded = false;
        try {
            PreparedStatement ps = con.prepareStatement(
                "insert into payment(leaseid, paymentdate, amount) values (?, ?, ?)"
            );
            ps.setInt(1, lease.getLeaseID());
            ps.setDate(2, new java.sql.Date(System.currentTimeMillis())); 
            ps.setDouble(3, amount);
            ps.executeUpdate();
            recorded = true;
        } catch (SQLException e) {
            System.out.println("Unable to record payment");
            e.printStackTrace();
        }
        return recorded;
    }
    @Override
    public List<Double> getPaymentHistoryForCustomer(int customerID) {
        List<Double> history = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement("select amount from payment where leaseid in (select leaseid from lease where customerid = ?)");   
            ps.setInt(1, customerID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                history.add(rs.getDouble("amount"));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving payment history for customer.");
            e.printStackTrace();
        }
        return history;
    }

    @Override
    public double calculateTotalRevenue() {
        double total = 0;
        try {
            PreparedStatement ps = con.prepareStatement("SELECT SUM(amount) AS total FROM payment");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getDouble("total");
            }
        } catch (SQLException e) {
            System.out.println("Unable to calculate total revenue");
            e.printStackTrace();
        }
        return total;
    }
    public void close() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("Error in closing the connection");
            e.printStackTrace();
        }
    }

	
	

}
