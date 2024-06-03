package SoftwareProjectPart1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FinalFrame extends JFrame {
    private Case chosenCase;
    private Case lastCase;
    private JButton chosenCaseButton;
    private JButton lastCaseButton;

    public FinalFrame(Case chosenCase, Case lastCase) {
        this.chosenCase = chosenCase;
        this.lastCase = lastCase;

        setTitle("Final Round");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 2));

        chosenCaseButton = new JButton("Your Case");
        lastCaseButton = new JButton("Last Case");

        chosenCaseButton.addActionListener(new CaseButtonListener(chosenCase.getMoney()));
        lastCaseButton.addActionListener(new CaseButtonListener(lastCase.getMoney()));

        add(chosenCaseButton);
        add(lastCaseButton);
    }

    private class CaseButtonListener implements ActionListener {
        private int money;

        public CaseButtonListener(int money) {
            this.money = money;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(FinalFrame.this, "Congratulations! You have won $" + money);
            dispose();
        }
    }
}

