package SoftwareProjectPart1;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ScoresDAOIT {

    private static ScoresDAO scoresDAO;
    private static UserDAO userDAO;
    private static int testUserId;

    @BeforeClass
    public static void setUpClass() {
        DatabaseConnection.setupDatabase();
        scoresDAO = new ScoresDAO();
        userDAO = new UserDAO();
        
        // Register a test user and get the user ID
        String username = "testUser";
        String password = "testPass";
        userDAO.registerUser(username, password, null);
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id FROM Users WHERE username = '" + username + "'")) {
            if (rs.next()) {
                testUserId = rs.getInt("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    public void testRecordScore() {
        int score = 1000;
        scoresDAO.recordScore(testUserId, score);

        // Verify the score was recorded
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT score FROM Scores WHERE user_id = " + testUserId)) {

            assertTrue("Score should be recorded in the database", rs.next());
            int recordedScore = rs.getInt("score");
            assertEquals("Recorded score should match the input score", score, recordedScore);
        } catch (Exception e) {
            fail("Failed to verify recorded score: " + e.getMessage());
        }
    }
}
