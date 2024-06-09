
package SoftwareProjectPart1;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame implements FrameInitializer{
    public JPanel chosenCasePanel;
    public JLabel chosenCaseLabel;
    private JLabel promptLabel;
    private CasePanel casePanel;
    private MoneyPanel moneyPanel;
    private BankerPanel bankerPanel;
    private Game currentGame;
    private UserDAO userDAO;
    private ScoresDAO scoresDAO;
    private LoginFrame loginFrame;
    private JButton restartButton;

    public GameFrame(Game currentGame) {
        this.currentGame = currentGame;
        initialize();
    }

    public GameFrame(Game currentGame, UserDAO userDAO, ScoresDAO scoresDAO, LoginFrame loginFrame) {
        DatabaseConnection.setupDatabase();
        this.currentGame = currentGame;
        this.userDAO = userDAO;
        this.scoresDAO = scoresDAO;
        this.loginFrame = loginFrame;
        initialize();
    }

    private void initialize() {
        initializeFrame();
        setTitle();

        // Money panel on the left
        moneyPanel = new MoneyPanel(currentGame.caseSet.moneyValues);
        add(moneyPanel, BorderLayout.WEST);

        // Cases panel in the center
        casePanel = new CasePanel(new CaseButtonListener());
        add(casePanel, BorderLayout.CENTER);

        // Banker panel on the right
        bankerPanel = new BankerPanel(new DealButtonListener(), new NoDealButtonListener());
        add(bankerPanel, BorderLayout.EAST);

        // Chosen case panel at the bottom
        chosenCasePanel = new JPanel();
        chosenCasePanel.setLayout(new BorderLayout());
        chosenCaseLabel = new JLabel("Your Case: ");
        chosenCaseLabel.setFont(new Font("Arial", Font.BOLD, 16));
        chosenCasePanel.add(chosenCaseLabel, BorderLayout.WEST);

        promptLabel = new JLabel("Choose a case to start the game", SwingConstants.CENTER);
        promptLabel.setFont(new Font("Arial", Font.BOLD, 18));
        promptLabel.setForeground(Color.BLACK);
        chosenCasePanel.add(promptLabel, BorderLayout.CENTER);

        // Restart button
        restartButton = new JButton("Restart Game");
        restartButton.setFont(new Font("Arial", Font.BOLD, 16));
        restartButton.addActionListener(new RestartButtonListener());
        chosenCasePanel.add(restartButton, BorderLayout.EAST);

        add(chosenCasePanel, BorderLayout.SOUTH);

        setVisible(true);
    }
    
    @Override
    public void initializeFrame()
    {
        setTitle("DEAL OR NO DEAL");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setResizable(false);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.BLACK);
    }
    
    private void setTitle()
    {
        //Set the title label for the game frame
        JLabel titleLabel = new JLabel("DEAL OR NO DEAL", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.YELLOW);
        add(titleLabel, BorderLayout.NORTH);
    }

    public void setPrompt(String prompt) {
        promptLabel.setText(prompt);
    }

    public void setChosenCaseLabel(int caseNumber) {
        chosenCaseLabel.setText("Your Case: " + caseNumber);
    }

    public void updateMoneyLabel(int moneyValue) {
        moneyPanel.markOffMoney(moneyValue);
    }
    
    //below methods are for hanndling enabling/disabling of game frame buttons
    public void disableCaseButton(int index) {
        casePanel.disableButton(index);
    }

    public void updateBankerOffer(int offer) {
        bankerPanel.updateOfferLabel(offer);
    }

    public void enableDealButtons() {
        bankerPanel.enableButtons();
    }

    public void disableAllCaseButtons() {
        casePanel.disableAllButtons();
    }

    public void enableAllCaseButtons() {
        casePanel.enableAllButtons();
    }

    public void disableDealButtons() {
        bankerPanel.disableButtons();
    }

    private class CaseButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!loginFrame.isLoggedIn()) {
                JOptionPane.showMessageDialog(GameFrame.this, "Please log in first!");
                return;
            }
            if (!currentGame.isDealing) {
                //calls choose case which will handle the case chosing logic
                JButton button = (JButton) e.getSource();
                int caseIndex = Integer.parseInt(button.getText());
                currentGame.caseChosen(caseIndex);
            }
        }
    }

    private class DealButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!loginFrame.isLoggedIn()) {
                JOptionPane.showMessageDialog(GameFrame.this, "Please log in first!");
                return;
            }
            //calls logic to handle accepting the deal
            int offer = bankerPanel.getCurrentOffer();
            currentGame.acceptDeal(offer);
        }
    }

    private class NoDealButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!loginFrame.isLoggedIn()) {
                JOptionPane.showMessageDialog(GameFrame.this, "Please log in first!");
                return;
            }
            //handles rejection logic of game frame
            currentGame.rejectDeal();
            currentGame.isDealing = false;
        }
    }

    private class RestartButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            SwingUtilities.invokeLater(() -> {
                //restart the game by making a new game object
                Game newGame = new Game(userDAO, scoresDAO);
                GameFrame newGameFrame = new GameFrame(newGame, userDAO, scoresDAO, loginFrame);
                newGame.setGameFrame(newGameFrame);
                newGame.startGame();
            });
        }
    }

    public void showLeaderboard() {
        SwingUtilities.invokeLater(() -> {
            //logic for showing the leaderboard
            LeaderboardFrame leaderboardFrame = new LeaderboardFrame(scoresDAO);
            leaderboardFrame.setVisible(true);
        });
    }
}
