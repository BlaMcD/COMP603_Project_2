package SoftwareProjectPart1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CasePanel extends JPanel {
    private JButton[] caseButtons;

    public CasePanel(ActionListener caseButtonListener) 
    {
        setLayout(new GridLayout(5, 5, 10, 10));
        setBackground(Color.DARK_GRAY);

        caseButtons = new JButton[26];
        for (int i = 0; i < caseButtons.length; i++) 
        {
            caseButtons[i] = new JButton(String.valueOf(i + 1));
            caseButtons[i].setFont(new Font("Arial", Font.BOLD, 16));
            caseButtons[i].setBackground(Color.ORANGE);
            caseButtons[i].setForeground(Color.BLACK);
            caseButtons[i].addActionListener(caseButtonListener);
            add(caseButtons[i]);
        }
    }

    public void disableButton(int index) 
    {
        caseButtons[index].setEnabled(false);
    }
    
    public void disableAllButtons()
    {
        for (JButton button : caseButtons)
        {
            button.setEnabled(false);
        }
    }
    
    public void enableAllButtons()
    {
        for (JButton button : caseButtons)
        {
            button.setEnabled(true);
        }
    }
}

