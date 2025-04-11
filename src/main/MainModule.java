package main;

import java.sql.Date;
import java.util.*;

import dao.CarLeaseRepositoryImpl;
import dao.ICarLeaseRepository;
import entityclasses.Customer;
import entityclasses.Lease;
import entityclasses.Vehicle;
import myexceptions.*;

public class MainModule {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ICarLeaseRepository repo = null;

        try {
            try {
                repo = new CarLeaseRepositoryImpl();
            } catch (Exception e) {
                System.out.println("Failed to connect to database!");
                return;
            }

            while (true) {
                System.out.println("Welcome to Car Rental System!");
                System.out.println("1. Add Car");
                System.out.println("2. Remove Car");
                System.out.println("3. Check Car by ID");
                System.out.println("4. List Available Cars");
                System.out.println("5. List Rented Cars");
                System.out.println("6. Add Customer");
                System.out.println("7. Remove Customer");
                System.out.println("8. Find Customer by ID");
                System.out.println("9. List Customers");
                System.out.println("10. Create Lease");
                System.out.println("11. Return Car");
                System.out.println("12. List Active Leases");
                System.out.println("13. List Lease History");
                System.out.println("14. Record Payment");
                System.out.println("15. Exit");
                System.out.print("Enter your choice: ");

                int choice = sc.nextInt();
                sc.nextLine(); 

                try {
                    switch (choice) {
                        case 1: 
                            System.out.print("Vehicle ID: ");
                            int id = sc.nextInt();
                            sc.nextLine();
                            System.out.print("Make: ");
                            String make = sc.nextLine();
                            System.out.print("Model: ");
                            String model = sc.nextLine();
                            System.out.print("Year: ");
                            int year = sc.nextInt();
                            System.out.print("Daily Rate: ");
                            double rate = sc.nextDouble();
                            sc.nextLine();
                            System.out.print("Status (available/rented): ");
                            String status = sc.nextLine();
                            System.out.print("Passenger Capacity: ");
                            int capacity = sc.nextInt();
                            System.out.print("Engine Capacity: ");
                            double engine = sc.nextDouble();

                            Vehicle car = new Vehicle(id, make, model, year, rate, status, capacity, engine);
                            if (repo.addCar(car)) {
                                System.out.println("Car added successfully.");
                            } else {
                                System.out.println("Failed to add car.");
                            }
                            break;

                        case 2: 
                            System.out.print("Enter Vehicle ID to remove: ");
                            int carIdToRemove = sc.nextInt();
                            if (repo.removeCar(carIdToRemove)) {
                                System.out.println("Car removed successfully.");
                            } else {
                                System.out.println("Failed to remove car or car not found.");
                            }
                            break;

                        case 3: 
                            System.out.print("Enter Vehicle ID to find: ");
                            int carIdToFind = sc.nextInt();
                            sc.nextLine();
                            try {
                                if (repo.findCarById(carIdToFind)) {
                                    System.out.println("CarID found in the system.");
                                } else {
                                    System.out.println("Car not found.");
                                }
                            } catch (CarNotFoundException e) {
                                System.out.println("❗ Error finding car: ");
                                e.printStackTrace();
                            }
                            break;
                        case 4: 
                            HashMap<Integer, Vehicle> availableCars = repo.listAvailableCars();
                            System.out.println("Available Cars (" + availableCars.size() + "):");
                            for (Vehicle v : availableCars.values()) {
                                System.out.println(v);
                            }
                            break;

                        case 5: 
                            HashMap<Integer, Vehicle> rentedCars = repo.listRentedCars();
                            System.out.println("Rented Cars (" + rentedCars.size() + "):");
                            for (Vehicle v : rentedCars.values()) {
                                System.out.println(v);
                            }
                            break;

                        case 6: 
                            System.out.print("Customer ID: ");
                            int cid = sc.nextInt();
                            sc.nextLine();
                            System.out.print("First Name: ");
                            String fname = sc.nextLine();
                            System.out.print("Last Name: ");
                            String lname = sc.nextLine();
                            System.out.print("Email: ");
                            String email = sc.nextLine();
                            System.out.print("Phone Number: ");
                            String phone = sc.nextLine();
                            sc.nextLine();

                            Customer cust = new Customer(cid, fname, lname, email, phone);
                            if (repo.addCustomer(cust)) {
                                System.out.println("Customer added successfully.");
                            } else {
                                System.out.println("Failed to add customer.");
                            }
                            break;

                        case 7 : 
                            System.out.print("Enter Customer ID to remove: ");
                            int customerIdToRemove = sc.nextInt();
                            if (repo.removeCustomer(customerIdToRemove)) {
                                System.out.println("Customer removed successfully.");
                            } else {
                                System.out.println("Failed to remove customer or customer not found.");
                            }
                            break;
                        
                        case 8: 
                            System.out.print("Enter Customer ID: ");
                            int searchCustId = sc.nextInt();
                            sc.nextLine();
                            try {
                                Customer foundCustomer = repo.findCustomerById(searchCustId);
                                if (foundCustomer != null) {
                                    System.out.println("Customer Found!");
                                    System.out.println(foundCustomer);
                                } else {
                                    System.out.println("Customer not found.");
                                }
                            } catch (CustomerNotFoundException e) {
                                System.out.println("❗ Error finding customer!");
                                e.printStackTrace();
                            }
                            break;


                        case 9: 
                            HashMap<Integer, Customer> customers = repo.listCustomers();
                            System.out.println("Customers (" + customers.size() + "):");
                            for (Customer c : customers.values()) {
                                System.out.println(c);
                            }
                            break;

                        case 10: 
                            System.out.print("Customer ID: ");
                            int leaseCustId = sc.nextInt();
                            System.out.print("Vehicle ID: ");
                            int vehicleId = sc.nextInt();
                            sc.nextLine();
                            System.out.print("Start Date (yyyy-mm-dd): ");
                            Date start = Date.valueOf(sc.nextLine());
                            System.out.print("End Date (yyyy-mm-dd): ");
                            Date end = Date.valueOf(sc.nextLine());
                            System.out.print("Type: (Dailylease/monthlylease) ");
                            String type = sc.nextLine();

                            Lease lease = repo.createLease(leaseCustId, vehicleId, start, end, type);
                            if (lease != null) {
                                System.out.println("Lease created successfully. Lease ID: " + lease.getLeaseID());
                            } else {
                                System.out.println("Failed to create lease.");
                            }
                            break;

                        case 11: 
                            System.out.print("Enter Lease ID to return: ");
                            int returnLeaseId = sc.nextInt();
                            sc.nextLine();
                            if (repo.returnCar(returnLeaseId)) {
                                System.out.println("Car returned successfully.");
                            } else {
                                System.out.println("Failed to return car.");
                            }
                            break;

                        case 12:
                            HashMap<Integer, Lease> activeLeases = repo.listActiveLeases();
                            System.out.println("Active Leases (" + activeLeases.size() + "):");
                            for (Lease l : activeLeases.values()) {
                                System.out.println(l);
                            }
                            break;
                            
                        case 13: 
                            HashMap<Integer, Lease> leaseHistory = repo.listLeaseHistory();
                            System.out.println("Lease History (" + leaseHistory.size() + "):");
                            for (Lease l : leaseHistory.values()) {
                                System.out.println(l);
                            }
                            break;    

                        case 14: 
                            System.out.print("Lease ID: ");
                            int paymentLeaseId = sc.nextInt();
                            System.out.print("Amount: ");
                            double amount = sc.nextDouble();
                            sc.nextLine();
                            
                            HashMap<Integer, Lease> leases = repo.listActiveLeases();
                            Lease leaseToPay = leases.get(paymentLeaseId);
                            
                            if (leaseToPay != null) {
                                if (repo.recordPayment(leaseToPay, amount)) {
                                    System.out.println("Payment recorded successfully.");
                                } else {
                                    System.out.println("Failed to record payment.");
                                }
                            } else {
                                System.out.println("Lease not found.");
                            }
                            break;

                        case 15: 
                            System.out.println("Thank you. Visit again!");
                

                        default:
                            System.out.println("Invalid choice. Try again with a valid choice");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter the correct data.");
                    sc.nextLine(); 
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } finally {
            if (sc != null) {
                sc.close();
            }
        }
    }
}