package SoftwareProjectPart1;

import javax.swing.*;

public class Game {
    public CaseSet caseSet;
    public Banker banker;
    public Player player;
    public boolean gameOver;
    public GameFrame gameFrame;
    public boolean isDealing;
    public int eliminateCases;
    public int startEliminateCases;

    public Game()
    {
        this.caseSet = new CaseSet();
        this.banker = new Banker();
        this.player = new Player();
        this.gameOver = false;
        this.isDealing = false;
        this.startEliminateCases = 6;
        this.eliminateCases = startEliminateCases;
    }

    public void startGame()
    {
        gameFrame = new GameFrame(this);
    }

    public void playRound() 
    {
        gameFrame.setPrompt("Choose " + eliminateCases + " cases to eliminate");
    }

    public void caseChosen(int caseIndex) 
    {
        Case currentCase = player.findCase(caseIndex, caseSet.cases);
        
        if (player.chosenCase == null) 
        {
            player.chosenCase = currentCase;
            caseSet.removeCase(caseIndex);
            gameFrame.setChosenCaseLabel(player.chosenCase.getCaseNumber());
            gameFrame.disableCaseButton(caseIndex-1);
            gameFrame.setPrompt("Now choose " + eliminateCases + " cases to eliminate");
        } 
        else 
        {
            currentCase.open();
            caseSet.removeCase(caseIndex);
            gameFrame.updateMoneyLabel(currentCase.getMoney());
            gameFrame.disableCaseButton(caseIndex-1);
            eliminateCases--;
            playRound();
            if (eliminateCases == 0) 
            {
                handleDeal();
                if(caseSet.numOfCases == 1)
                {
                    finalRound();
                }
            }
        }
    }

    public void handleDeal()
    {
        this.isDealing = true;
        int offer = banker.makeOffer(caseSet.cases, player.chosenCase);
        gameFrame.updateBankerOffer(offer);
        gameFrame.enableDealButtons();
        gameFrame.setPrompt("Do you want to take the offer?");  
    }
    
    public void acceptDeal(int offer) 
    {
        gameOver = true;
        JOptionPane.showMessageDialog(gameFrame, "Congratulations! You have accepted the offer and won $" + offer);
        System.exit(0);
    }

    public void rejectDeal() 
    {
        gameFrame.disableDealButtons();
        startEliminateCases = (startEliminateCases > 1) ? (startEliminateCases-1) : 1;
        eliminateCases = startEliminateCases;
        playRound();
    }

    public void finalRound()
    {
        gameOver = true;
        SwingUtilities.invokeLater(() -> {
            gameFrame.dispose();
            Case lastCase = null;
            for (Case currentCase : caseSet.cases) 
            {
                if (currentCase != null) 
                {
                    lastCase = currentCase;
                    break;
                }
            }
            if (lastCase != null) 
            {
                FinalFrame finalFrame = new FinalFrame(player.chosenCase, lastCase);
                finalFrame.setVisible(true);
            }
        });
    }
}

