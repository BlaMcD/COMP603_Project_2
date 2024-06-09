package SoftwareProjectPart1;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame implements FrameInitializer{
    private UserDAO userDAO;
    private ScoresDAO scoresDAO;
    private boolean loggedIn;

    public LoginFrame(UserDAO userDAO, ScoresDAO scoresDAO) {
        this.userDAO = userDAO;
        this.scoresDAO = scoresDAO;
        this.loggedIn = false;
        initialize();
    }

    private void initialize() {
        initializeFrame();

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField();
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        add(userLabel);
        add(userField);
        add(passLabel);
        add(passField);
        add(loginButton);
        add(registerButton);
        //action listener for the log in button
        loginButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());
            //if username and password exist then log in
            if (userDAO.loginUser(username, password)) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                loggedIn = true;
                dispose();
                showGameFrame();
            } else {
                //credentials did not exist
                JOptionPane.showMessageDialog(this, "Invalid login credentials.");
            }
        });

        registerButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());
            //cannot register if already registered
            if (userDAO.isUsernameTaken(username)) {
                JOptionPane.showMessageDialog(this, "You have registered this account.");
            } else {
                //user is now registered and needs to log in
                userDAO.registerUser(username, password, null);
                JOptionPane.showMessageDialog(this, "Registration successful!");
            }
        });
    }
    
    @Override
    public void initializeFrame()
    {
        setTitle("Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2));
    }

    private void showGameFrame() {
        //show game frame after completion of login frame
        Game game = new Game(userDAO, scoresDAO);
        GameFrame gameFrame = new GameFrame(game, userDAO, scoresDAO, this);
        game.setGameFrame(gameFrame);
        game.startGame();
        gameFrame.setVisible(true);
        gameFrame.showLeaderboard();
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }
}
