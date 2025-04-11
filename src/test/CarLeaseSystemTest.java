package test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import myexceptions.CarNotFoundException;
import dao.CarLeaseRepositoryImpl;
import entityclasses.Lease;
import entityclasses.Vehicle;

class CarLeaseSystemTest {

    static CarLeaseRepositoryImpl r;

    @BeforeAll
    static void setup() throws SQLException {
        r = new CarLeaseRepositoryImpl(); 
    }

    @Test
    void testCarCreated() {
        Vehicle car = new Vehicle(21, "Honda", "Civic", 2022, 150, "available", 5, 1500);
        boolean result = r.addCar(car);
        assertTrue(result, "Car should be created successfully");
    }

    @Test
    void testLeaseCreated() throws CarNotFoundException {
        Lease lease = r.createLease(14, 18, Date.valueOf("2024-04-01"), Date.valueOf("2024-04-10"), "dailylease");
        assertNotNull(lease, "Lease should be created successfully");
    }

    @Test
    void testLeaseRetrieved() {
        HashMap<Integer, Lease> hm = r.listActiveLeases();
        assertFalse(hm.isEmpty(), "Active lease list should not be empty");
    }

    @Test
    void testLeaseCreationException() {
        assertThrows(CarNotFoundException.class, () -> {
            r.createLease(9999, 8888, Date.valueOf("2024-04-01"), Date.valueOf("2024-04-10"), "dailylease");
        }, "Exception should be thrown for invalid customer or car ID");
    }
}
