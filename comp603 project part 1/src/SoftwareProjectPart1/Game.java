package SoftwareProjectPart1;

import java.util.Scanner;

public class Game {
    
    private CaseSet caseSet;
    private Banker banker;
    private Player player;
    private boolean gameOver;

    public Game()
    {
        this.caseSet = new CaseSet();
        this.banker = new Banker();
        this.player = new Player();
        this.gameOver = false;
    }

    public void startGame() 
    {
        System.out.println("Welcome to Deal or No Deal!");
        
        int eliminateCases = 6;
        caseSet.displayCases(); // Display available cases for player to choose
        caseSet.firstChosenCase = selectCase(); // Player selects their case
           
        while(!gameOver && caseSet.numOfCases>1)
        {
            playRound(eliminateCases);
            if(eliminateCases>1)
            {
                eliminateCases--;
            }
        }
        if(!gameOver)
        {
            finalRound();
        }
    }

    public void playRound(int n) 
    {
        caseSet.displayCases();
        System.out.println("Now choose "+n+" cases to eliminate\n");
        for(int i=1; i<=n; i++)
        {
            selectCase();
            caseSet.displayCases();
            System.out.println((n-i)+" cases left to go");
        }
        //Banker makes offer and offer decided by player
        int offer = banker.makeOffer(caseSet.getCases(),player.chosenCase);
        boolean isAccepted = player.decideDeal(offer); 

        if(isAccepted)
        {
            endGame(offer);
        }
    }
    
    public void finalRound() 
    {
        // Happens only if two cases left, the player's case and another case
        Scanner scan = new Scanner(System.in);
        Case lastCase = null; // Initialize lastCase to null
        Case[] cases = caseSet.getCases();

        for (int i = 0; i < cases.length; i++) 
        {
            if (cases[i] != null) 
            {
                lastCase = cases[i]; // Assign the non-null case to lastCase
            }
        }

        System.out.println("Do you want to keep case " + player.chosenCase.getCaseNumber() + "? (y/n)");
        String answer = scan.next();
        if(answer.equals("n"))
        {
            player.chosenCase = lastCase;
        }
        player.chosenCase.open();
    }

    public Case selectCase() 
    {
        // Logic to handle the player selecting a case at the beginning of the game
        Case currentCase = player.chooseCase(caseSet);
        currentCase.open();
        return currentCase;
    }

    public void endGame(int offer) 
    {
        // Logic to end the game
        System.out.println("Congratulations! You have won $"+offer);
        gameOver = true; // Set game over flag to true
    }

}
