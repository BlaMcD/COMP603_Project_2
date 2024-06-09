package SoftwareProjectPart1;

public class Player {
    private static int userId;
    public Case chosenCase;
    int id;

    public static void setUserId(int id) 
    {
        userId = id;
    }

    public static int getUserId()
    {
        return userId;
    }

    public Case findCase(int caseNumber, Case[] cases) 
    {
        for (Case c : cases) 
        {
            if (c != null && c.getCaseNumber() == caseNumber) 
            {
                return c;
            }
        }
        return null;
    }
}
