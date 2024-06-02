package SoftwareProjectPart1;

import java.util.Scanner;

public class ConsoleUserInterface implements UserInterface {//will remove when implementing GUI
    
    private final Scanner scanner;

    public ConsoleUserInterface() 
    {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public int promptForCase() 
    {
        System.out.println("Which case would you like to choose? ");
        return scanner.nextInt();
    }

    @Override
    public String promptForDeal() 
    {
        System.out.println("Do you accept the deal? (y/n): ");
        String answer = scanner.next();
        while(!answer.equals("y") && !answer.equals("n"))
        {
            invalidInput();
            System.out.println("Do you accept the deal? (y/n): ");
            answer = scanner.next();           
        }
        return answer;
    }
    
    @Override
    public void invalidInput()
    {
        System.out.println("Invalid input, try again");
        scanner.next(); // Clear the invalid input
    }

}
