package UserProgram;

import java.util.Scanner;

/* The driver program interacts with user,
 * asking for inputs of #of variables in each state,
 * names and initial values.
 */
public class Driver {

	private static Scanner keyboard = new Scanner(System.in);
	private static int numVariablesInEachState;
	private static String[] arrayOfVariableNames;
	private static UserInputParser userInputParser;

    public static void main(String[] args) {
    	userInputParser = new UserInputParser();
    	takeInput();
    	printInfo();
    }

    /* Step 1: ask user how many variables are in each state
     * Step 2: ask user to input each name of the variable
     * Step 3: looping to ask user input state names and 
     * 		   values of variables that the state contains
 	 */
    public static void takeInput() {
    	// ask user how many variables are in each state
    	System.out.println("How many variables are in each state?");
    	numVariablesInEachState = keyboard.nextInt();
    	keyboard.nextLine();
    	arrayOfVariableNames = new String[numVariablesInEachState];
    	
    	// ask user to input names of variables one by one
    	for (int i = 0; i < numVariablesInEachState; i++) {
    		System.out.println("Please enter the name of variable #" + (i+1) + ":");
    		arrayOfVariableNames[i] = keyboard.nextLine();
    	}

    	int stateIndex = 1;
    	int answer = 1;
    	do {
    		System.out.println("Please enter the name of state #" + stateIndex + ":");
    		String stateName = keyboard.nextLine();;
    		State newState = new State(numVariablesInEachState, stateName);

    		// Get the initial values of each state
  			for (int i = 0; i < numVariablesInEachState; i++) {
    			System.out.println("Please enter initial value for variable " + arrayOfVariableNames[i] + " in state " + stateName + ":");
    			int initialValue = keyboard.nextInt();
    			Variable newVariable = new Variable(arrayOfVariableNames[i], initialValue);
    			newState.addVariableToArray(newVariable);
    		}
    		userInputParser.addState(newState);

    		// Gives user a chance to stop adding new states
    		System.out.println("Enter another state? enter 1 for yes, enter 0 for no");
    		stateIndex++;
    		answer = keyboard.nextInt();
    		keyboard.nextLine();
    	} while (answer != 0);
    }

    public static void printInfo() {
    	System.out.println("========Printing all the states========");
    	userInputParser.printListOfStates();
    }
    
}