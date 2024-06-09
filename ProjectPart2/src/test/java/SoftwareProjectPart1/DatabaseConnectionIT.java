package SoftwareProjectPart1;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class DatabaseConnectionIT {
    
    public DatabaseConnectionIT() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        System.out.println("Before all tests");
        DatabaseConnection.setupDatabase();
    }
    
    @AfterClass
    public static void tearDownClass() {
        System.out.println("After all tests");
    }
    
    @Before
    public void setUp() {
        System.out.println("Before each test");
    }
    
    @After
    public void tearDown() {
        System.out.println("After each test");
    }

    @Test
    public void testGetConnection() {
        System.out.println("Testing getConnection");
        Connection result = null;
        try {
            result = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            fail("Failed to get connection: " + e.getMessage());
        }
        assertNotNull("Connection should not be null", result);
    }

    @Test
    public void testSetupDatabase() {
        System.out.println("Testing setupDatabase");
        try {
            DatabaseConnection.setupDatabase();
            // Verify that the tables were created
            try (Connection conn = DatabaseConnection.getConnection();
                 Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery("SELECT * FROM Users");
                assertNotNull("Users table should exist", rs);
                rs = stmt.executeQuery("SELECT * FROM Scores");
                assertNotNull("Scores table should exist", rs);
            }
        } catch (SQLException e) {
            fail("Failed to set up database: " + e.getMessage());
        }
    }
}
