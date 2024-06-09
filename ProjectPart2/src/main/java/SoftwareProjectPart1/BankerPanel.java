package SoftwareProjectPart1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class BankerPanel extends JPanel {
    
    private JLabel offerLabel;
    private JButton dealButton;
    private JButton noDealButton;
    private int currentOffer;

    public BankerPanel(ActionListener dealButtonListener, ActionListener noDealButtonListener) 
    {
        setLayout(new GridLayout(4, 1, 10, 10));
        setBackground(Color.BLACK);

        //initializes all labels and buttons with corresponding listeners
        initializeOfferLabel();
        initializeDealButton(dealButtonListener);
        initializeNoDealButton(noDealButtonListener);
    }
    
    private void initializeOfferLabel()
    {
        offerLabel = new JLabel("Banker's Offer: $0", SwingConstants.CENTER);
        offerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        offerLabel.setForeground(Color.WHITE);
        add(offerLabel);   
    }
    
    private void initializeDealButton(ActionListener dealButtonListener)
    {
        //initialize the deal button with action listener
        dealButton = new JButton("Deal");
        dealButton.setFont(new Font("Arial", Font.BOLD, 16));
        dealButton.setBackground(Color.GREEN);
        dealButton.setForeground(Color.BLACK);
        dealButton.setEnabled(false);
        dealButton.addActionListener(dealButtonListener);
        add(dealButton);    
    }
    
    private void initializeNoDealButton(ActionListener noDealButtonListener)
    {
        //initialize no deal button with action listener
        noDealButton = new JButton("No Deal");
        noDealButton.setFont(new Font("Arial", Font.BOLD, 16));
        noDealButton.setBackground(Color.RED);
        noDealButton.setForeground(Color.BLACK);
        noDealButton.setEnabled(false);
        noDealButton.addActionListener(noDealButtonListener);
        add(noDealButton);
    }

    public void updateOfferLabel(int offer) 
    {
        currentOffer = offer;
        offerLabel.setText("Banker's Offer: $" + offer);
    }

    public void enableButtons() 
    {
        //enable deal or no deal buttons when deal taking place
        dealButton.setEnabled(true);
        noDealButton.setEnabled(true);
    }

    public void disableButtons()
    {
        //disable buttons when player is eliminating cases
        dealButton.setEnabled(false);
        noDealButton.setEnabled(false);
    }

    public int getCurrentOffer()
    {
        return currentOffer;
    }
}

