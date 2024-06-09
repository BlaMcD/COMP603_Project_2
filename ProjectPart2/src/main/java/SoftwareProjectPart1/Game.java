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
    private ScoresDAO scoresDAO;

    // Constructor to accept DAOs
    public Game(UserDAO userDAO, ScoresDAO scoresDAO) {
        this.caseSet = new CaseSet();
        this.banker = new Banker();
        this.player = new Player();
        this.gameOver = false;
        this.isDealing = false;
        this.startEliminateCases = 6;
        this.eliminateCases = startEliminateCases;
        this.scoresDAO = scoresDAO;
    }

    // Method to set the GameFrame instance
    public void setGameFrame(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
    }

    public void startGame() {
        // Ensure gameFrame is properly initialized before starting the game
        if (gameFrame == null) {
            gameFrame = new GameFrame(this);
        }
        playRound();
    }

    public void playRound() {
        if (gameFrame != null) {
            gameFrame.setPrompt("Choose " + eliminateCases + " cases to eliminate");
        }
    }

    public void caseChosen(int caseIndex) {
        Case currentCase = player.findCase(caseIndex, caseSet.getCases());

        if (currentCase == null) {
            return;
        }

        if (player.chosenCase == null) {
            //the chosen case will become the players case if players case null
            player.chosenCase = currentCase;
            caseSet.removeCase(caseIndex);

            if (gameFrame != null) {
                //sets the case to players chosen case and continues the game
                gameFrame.setChosenCaseLabel(player.chosenCase.getCaseNumber());
                gameFrame.disableCaseButton(caseIndex - 1);
                gameFrame.setPrompt("Now choose " + eliminateCases + " cases to eliminate");
            }
        } else {
            currentCase.open();
            //remove case from cases list and update the game frame
            caseSet.removeCase(caseIndex);
            if (gameFrame != null) {
                gameFrame.updateMoneyLabel(currentCase.getMoney());
                gameFrame.disableCaseButton(caseIndex - 1);
            }
            eliminateCases--;
            playRound();
            if (eliminateCases == 0) {
                //bankers turn to make an offer to player
                handleDeal();
                if (caseSet.getNumOfCases() == 1) {
                    finalRound();
                }
            }
        }
    }

    public void handleDeal()
    {
        //method to handle the dealers deal
        this.isDealing = true;
        int offer = banker.makeOffer(caseSet.getCases(), player.chosenCase);
        if (gameFrame != null) 
        {
            gameFrame.updateBankerOffer(offer);
            gameFrame.enableDealButtons();
            gameFrame.setPrompt("Do you want to take the offer?"); //prompt user to take offer
        }
    }

    public void acceptDeal(int offer) 
    {
        //method to accept deal
        gameOver = true;
        scoresDAO.recordScore(Player.getUserId(), offer);
        JOptionPane.showMessageDialog(gameFrame, "Congratulations! You have accepted the offer and won $" + offer);
        System.exit(0);
    }

    public void rejectDeal() 
    {
        //handles rejection of deal
        if (gameFrame != null) 
        {
            gameFrame.disableDealButtons();
        }
        startEliminateCases = (startEliminateCases > 1) ? (startEliminateCases - 1) : 1;
        eliminateCases = startEliminateCases;
        playRound();//continue playing the round
    }

    public void finalRound() 
    {
        gameOver = true;
        //this method was given by chatGPT
        SwingUtilities.invokeLater(() -> {
            if (gameFrame != null) {
                gameFrame.dispose();
            }
            Case lastCase = null;
            for (Case currentCase : caseSet.getCases()) {
                if (currentCase != null) {
                    lastCase = currentCase;
                    break;
                }
            }
            if (lastCase != null) {
                int finalScore = lastCase.getMoney();
                scoresDAO.recordScore(Player.getUserId(), finalScore);
                FinalFrame finalFrame = new FinalFrame(player.chosenCase, lastCase);
                finalFrame.setVisible(true);
            }
        });
    }
}
