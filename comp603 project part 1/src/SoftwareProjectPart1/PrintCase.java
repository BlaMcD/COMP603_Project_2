package SoftwareProjectPart1;

public class PrintCase {//will get rid of this class when implement GUI
    
    public void outputCases(Case[] cases, Case[] originalCases, Integer[] moneyValues, Case firstChosenCase)
    {
        printCaseNumbers(cases, firstChosenCase);
        printMoney(cases, originalCases, moneyValues, firstChosenCase);
    }
    
    private void printCaseNumbers(Case[] cases, Case firstChosenCase) 
    {
        System.out.println("\nCurrent cases:");
        System.out.println("┌--------------------------------┐");
        for (int i = 0; i < cases.length; i++) 
        {
            if (cases[i] != null) 
            {
                System.out.printf("│ %-2d │", cases[i].getCaseNumber());
            } 
            else 
            {
                System.out.print("│    │");
            }
            if ((i + 1) % 6 == 0) 
            {
                System.out.println();
                System.out.println("├--------------------------------┤");
            }
        }
        System.out.println();
        System.out.println("└--------------------------------┘\n");
        
        if(firstChosenCase != null)
        {
            System.out.println("Your case: ");
            System.out.println("┌----┐");
            System.out.printf("│ %-2d │", firstChosenCase.getCaseNumber());
            System.out.println();
            System.out.println("└----┘\n");
        }
    }
    
    private void printMoney(Case[] cases, Case[] originalCases, Integer[] moneyValues, Case firstChosenCase) 
    {
        
        System.out.println("Money values:");
        System.out.println("$-----------------------------------------------------------------------$");
        for (int i = 0; i < originalCases.length; i++) 
        {
            int moneyValue = moneyValues[i];
            int index = -1; // Initialize index to -1 to indicate not found
            // Find the index of the case with the corresponding money value
            for (int j = 0; j < originalCases.length; j++) 
            {
                if (moneyValue == originalCases[j].getMoney()) 
                {
                    index = j;
                    break;
                }
            }
            
            if (cases[index] == null && !originalCases[index].equals(firstChosenCase)) 
            {
                System.out.print("|          |");
            } 
            else
            {
                System.out.printf("| $%-8d|", originalCases[index].getMoney());
            }
            if ((i + 1) % 6 == 0) 
            {
                System.out.println("|");
            }           
        }
        System.out.println();
        System.out.println("$-----------------------------------------------------------------------$");
    }
}
