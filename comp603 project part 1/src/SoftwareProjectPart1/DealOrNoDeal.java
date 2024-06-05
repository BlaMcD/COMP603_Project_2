package SoftwareProjectPart1;

public class DealOrNoDeal {
    public static void main(String[] args) {
        // Set up the database
        DatabaseConnection.setupDatabase();

        // Create instances of DAOs
        UserDAO userDAO = new UserDAO();
        ScoresDAO scoresDAO = new ScoresDAO();

        // Create a single instance of the Game and pass the DAOs to it
        Game game = new Game(userDAO, scoresDAO);
        
        // Create the GameFrame and pass the game instance to it
        GameFrame gameFrame = new GameFrame(game, userDAO, scoresDAO);
        
        // Link the GameFrame to the Game instance
        game.setGameFrame(gameFrame);

        // Start the game
        game.startGame();
        
        // Optionally, show the login screen
        gameFrame.showLoginScreen();
    }
}


//      Fix the issue where it does not correctly go to the final frame
//      Add a beginning frame which appears at the start of the program
//      Add additional features to improve GUI clearness
//      Fix the classes so they have no code smells and follow SOLID principles
//      After this, the database must be implemented
//      For database, some good ideas would be high score, most money won, etc. This can be displayed on the GUI once implemented
//      After the database has been implemented, then the validation must be implemented
