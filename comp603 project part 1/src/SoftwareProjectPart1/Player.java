package SoftwareProjectPart1;

public class Player 
{
    public Case chosenCase;    
    
    public Case findCase(int caseIndex, Case[] cases) {
        for (Case c : cases) {
            if (c != null && c.getCaseNumber() == caseIndex) {
                return c;
            }
        }
        return null;
    }
}
