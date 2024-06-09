package SoftwareProjectPart1;

public class Case{
    private int caseNumber;
    private int money;

    public Case(int caseNumber, int money) 
    {
        this.caseNumber = caseNumber;
        this.money = money;
    }

    public int getCaseNumber() 
    {
        return caseNumber;
    }

    public int getMoney() 
    {
        return money;
    }

    public void open() 
    {
        System.out.println("\nCase number " + caseNumber + " had..... $" + money);
    }
}
