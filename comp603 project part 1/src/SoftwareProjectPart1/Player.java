package SoftwareProjectPart1;

import java.util.InputMismatchException;

public class Player 
{
    public Case chosenCase;    
    
    /*public Case chooseCase(CaseSet caseSet) 
    {
        Case selectedCase = null;

        while (selectedCase == null) 
        {
            try 
            {
                selectedCase = findCase(caseNum, caseSet.getCases());
                if (selectedCase == null) 
                {
                    System.out.println("Case does not exist, choose from the remaining cases.");
                } 
                else 
                {
                    caseSet.removeCase(caseNum);
                    if (chosenCase == null) 
                    {
                        chosenCase = selectedCase;
                    }
                }
            } catch (InputMismatchException e) 
            {
                userInterface.invalidInput();
            }
        }

        return selectedCase;
    }*/
    
    public Case findCase(int caseNumber, Case[] cases)
    {
        for(int i=0; i<cases.length; i++)
        {
            if(cases[i] != null && cases[i].getCaseNumber() == caseNumber)
            {
                return cases[i];
            }
        }
        return null;
    }
    
    /*public boolean decideDeal(int offer) 
    {
        System.out.println("Banker's offer: $"+offer);
        String response = userInterface.promptForDeal();
        return response.equals("y");
    }*/
    
}
