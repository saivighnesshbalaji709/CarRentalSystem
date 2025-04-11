package dao;
import entityclasses.*;
import myexceptions.*;
import java.util.*;

public interface ICarLeaseRepository {
	
    boolean addCar(Vehicle car);
    boolean removeCar(int carID);
    HashMap<Integer, Vehicle> listAvailableCars();
    HashMap<Integer, Vehicle> listRentedCars();
    boolean findCarById(int carID) throws CarNotFoundException;

    boolean addCustomer(Customer customer);
    boolean removeCustomer(int customerID) throws CustomerNotFoundException;;
    HashMap<Integer, Customer> listCustomers();
    Customer findCustomerById(int customerID) throws Exception;

    Lease createLease(int customerID, int carID, Date startDate, Date endDate, String type) throws CarNotFoundException;
    boolean returnCar(int leaseID) throws LeaseNotFoundException;
    HashMap<Integer, Lease> listActiveLeases();
    HashMap<Integer, Lease> listLeaseHistory();

    boolean recordPayment(Lease lease, double amount);
 }
