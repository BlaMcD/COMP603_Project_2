package SoftwareProjectPart1;

public class Player 
{
    public Case chosenCase;    
    
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
}
