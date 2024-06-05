package SoftwareProjectPart1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame {
    public JPanel chosenCasePanel;
    public JLabel chosenCaseLabel;
    private JLabel promptLabel;
    private CasePanel casePanel;
    private MoneyPanel moneyPanel;
    private BankerPanel bankerPanel;
    private Game currentGame;

    public GameFrame(Game currentGame) {
        DatabaseConnection.setupDatabase();
        this.currentGame = currentGame;

        setTitle("DEAL OR NO DEAL");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setResizable(false);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.BLACK);

        // Title
        JLabel titleLabel = new JLabel("DEAL OR NO DEAL", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.YELLOW);
        add(titleLabel, BorderLayout.NORTH);

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

        add(chosenCasePanel, BorderLayout.SOUTH);

        setVisible(true);
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
            if (!currentGame.isDealing) {
                JButton button = (JButton) e.getSource();
                int caseIndex = Integer.parseInt(button.getText());
                currentGame.caseChosen(caseIndex);
            }
        }
    }

    private class DealButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int offer = bankerPanel.getCurrentOffer();
            currentGame.acceptDeal(offer);
        }
    }

    private class NoDealButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            currentGame.rejectDeal();
            currentGame.isDealing = false;
        }
    }

    public void showLoginScreen() {
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setSize(400, 300);
        loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        loginFrame.setLayout(new GridLayout(3, 2));

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField();
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        loginFrame.add(userLabel);
        loginFrame.add(userField);
        loginFrame.add(passLabel);
        loginFrame.add(passField);
        loginFrame.add(loginButton);
        loginFrame.add(registerButton);

        loginFrame.setVisible(true);

        loginButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());
            if (new UserDAO().loginUser(username, password)) {
                JOptionPane.showMessageDialog(loginFrame, "Login successful!");
                loginFrame.dispose();
                // Call any method if you want to display something after login
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Invalid login credentials.");
            }
        });

        registerButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());
            new UserDAO().registerUser(username, password, null);
            JOptionPane.showMessageDialog(loginFrame, "Registration successful!");
        });
    }
}
