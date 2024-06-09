package SoftwareProjectPart1;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CaseSet {
    private Case[] cases;
    private Case firstChosenCase;
    public Integer[] moneyValues;
    public Integer[] caseNumbers;
    private int numOfCases = 26;

    public CaseSet() 
    {
        //loads money and case values from local txt files
        ArrayInitializer initializer = new ArrayInitializer();
        this.moneyValues = initializer.loadMoneyValues("money_values.txt");
        this.caseNumbers = initializer.loadCaseNumbers("case_numbers.txt");

        //call initialiasation of cases
        this.cases = new Case[26];
        initialiseCases();
    }

    public Case[] getCases() 
    {
        return this.cases;
    }
    
    public Case getFirstChosenCase()
    {
        return this.firstChosenCase;
    }
    
    public int getNumOfCases()
    {
        return this.numOfCases;
    }

    public final void initialiseCases() 
    {
        //initialize and randomize the cases and money obtained from the files
        List<Integer> caseList = Arrays.asList(caseNumbers);
        Collections.shuffle(caseList); // shuffle randomly resorts list
        Integer[] shuffledCases = caseList.toArray(new Integer[0]); // back to Integer array so we can initialise cases

        List<Integer> moneyList = Arrays.asList(moneyValues);
        Collections.shuffle(moneyList);
        Integer[] shuffledMoney = moneyList.toArray(new Integer[0]);

        for (int i = 0; i < cases.length; i++) 
        {
            this.cases[i] = new Case(shuffledCases[i], shuffledMoney[i]);
        }
    }

    public void removeCase(int removeCaseNum) 
    {
        //find and remove case from the case array
        for (int i = 0; i < cases.length; i++) 
        {
            if (cases[i] != null && cases[i].getCaseNumber() == removeCaseNum) 
            {
                cases[i] = null;
                numOfCases--;
                break;
            }
        }
    }

    public Case getCase(int index) 
    {
        if (index > 0 && index <= cases.length) 
        {
            return cases[index - 1];
        } 
        else 
        {
            return null; // Handle invalid case number
        }
    }
}
