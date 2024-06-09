package SoftwareProjectPart1;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserDAOIT {

    private static UserDAO userDAO;

    @BeforeClass
    public static void setUpClass() {
        DatabaseConnection.setupDatabase();
        userDAO = new UserDAO();
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testRegisterUser() {
        String username = "testUserRegister";
        String password = "testPassRegister";
        userDAO.registerUser(username, password, "testregister@example.com");
        boolean loginResult = userDAO.loginUser(username, password);
        assertTrue("User should be registered successfully", loginResult);
    }

    @Test
    public void testLoginUser() {
        String username = "testUserLogin";
        String password = "testPassLogin";
        userDAO.registerUser(username, password, "testlogin@example.com");
        boolean loginResult = userDAO.loginUser(username, password);
        assertTrue("Login should be successful", loginResult);
    }
}
