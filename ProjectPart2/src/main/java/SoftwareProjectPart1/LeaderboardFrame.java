package SoftwareProjectPart1;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class LeaderboardFrame extends JFrame implements FrameInitializer{
    private ScoresDAO scoresDAO;

    public LeaderboardFrame(ScoresDAO scoresDAO) {
        this.scoresDAO = scoresDAO;
        initialize();
    }

    private void initialize() {
        initializeFrame();

        JLabel titleLabel = new JLabel("Leaderboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        String[] columnNames = {"Rank", "Player"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        handleTopPlayers(model);

        JScrollPane scrollPane = new JScrollPane(initializeTable(model));
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }
    
    @Override
    public void initializeFrame()
    {
        setTitle("Leaderboard");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
    }
    
    private void handleTopPlayers(DefaultTableModel model)
    {
        List<String> topPlayers = scoresDAO.getTopPlayers();
        int rank = 1;
        for (String player : topPlayers) {
            String[] parts = player.split(" - ", 2);
            String username = parts.length > 0 ? parts[0] : "Unknown";
            String score = parts.length > 1 ? parts[1] : "0";
            model.addRow(new Object[]{rank, username, score});
            rank++;
        }
    }
    
    private JTable initializeTable(DefaultTableModel model)
    {
        JTable leaderboardTable = new JTable(model);
        leaderboardTable.setFillsViewportHeight(true);
        leaderboardTable.setEnabled(false);
        leaderboardTable.setFont(new Font("Arial", Font.PLAIN, 16));
        leaderboardTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        return leaderboardTable;
    }
}
