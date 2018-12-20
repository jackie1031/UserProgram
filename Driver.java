package UserProgram;

import java.io.IOException;
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

    public static void main(String[] args) throws IOException {
    	userInputParser = new UserInputParser();
        String outFileName = askOutFileName();
    	takeInput();
    	printInfo(outFileName) ;
    }

    public static String askOutFileName() {
        System.out.println("======== Input File name ========");
        System.out.println("what's the output file name?");
        String outFileName = keyboard.nextLine();
        return outFileName;

    }

    /* Step 1: ask user how many variables are in each state
     * Step 2: ask user to input each name of the variable
     * Step 3: looping to ask user input state names and 
     * 		   values of variables that the state contains
 	 */
    public static void takeInput() {

        //ask user to put in the output.txt file name.


    	// ask user how many variables are in each state
        System.out.println("========Now asking information on variables========");
    	System.out.println("How many variables are in each state?");
    	numVariablesInEachState = keyboard.nextInt();
    	keyboard.nextLine();
    	arrayOfVariableNames = new String[numVariablesInEachState];
    	
    	// ask user to input names of variables one by one
    	for (int i = 0; i < numVariablesInEachState; i++) {
    		System.out.println("Please enter the name of variable #" + (i+1) + ":");
    		arrayOfVariableNames[i] = keyboard.nextLine();
    	}

        System.out.println("========Now asking information on states========");
        System.out.println("(Please notice that state #1 is the initial state)");

    	int stateIndex = 1;
    	int answer = 1;
    	do {
    		System.out.println("Please enter the name of state #" + stateIndex + ":");
    		String stateName = keyboard.nextLine();
    		State newState = new State(numVariablesInEachState, stateName);
            //userInputParser.getStateByName(stateName) : new State(numVariablesInEachState, stateName);

    		// Get the initial values of each state
  			for (int i = 0; i < numVariablesInEachState; i++) {
    			System.out.println("Please enter initial value for variable " + arrayOfVariableNames[i] + " in state " + stateName + ":");
    			int initialValue = keyboard.nextInt();
    			Variable newVariable = new Variable(arrayOfVariableNames[i], initialValue);
    			newState.addVariableToArray(newVariable);
    		}

            // Add the state to the map
    		userInputParser.addState(newState);
            userInputParser.addStateToMap(stateName, newState);

    		// Gives user a chance to stop adding new states
    		System.out.println("Enter another state? enter 1 for yes, enter 0 for no");
    		answer = keyboard.nextInt();
    		keyboard.nextLine();
            stateIndex++;
    	} while (answer != 0);

        System.out.println("========Now asking for all possible transitions========");
        // Ask user to enter possible transitions from each state
        int transitionIndex = 1;

        // loop over queue
        for (State state : userInputParser.getListOfStates()) {
            transitionIndex = 1;
            do {
                // Get input
                System.out.println("Please enter input name for transition #" + transitionIndex + " in state " + state.getStateName() + ":");
                String inputName = keyboard.nextLine();
                // Get output
                System.out.println("Please enter output name for transition #" + transitionIndex + " in state " + state.getStateName() + ":");
                String outputName = keyboard.nextLine();
                // Get destination
                System.out.println("Please enter destination state name for transition #" + transitionIndex + " in state " + state.getStateName() + ":");
                String destStateName = keyboard.nextLine();
                State destState = userInputParser.getStateByName(destStateName);
                if (destState == null) {
                    System.out.println("Destination state does not exist, transition <" + inputName + ", " + destStateName +"> not added");
                } else {
                    userInputParser.addInput(inputName);
                    userInputParser.addOutput(outputName);
                    state.addTransition(inputName, destState);
                    System.out.println("Transition <" + inputName + "/" + outputName + ", " + destStateName +"> added to state" + state.getStateName());
                }

                System.out.println("Enter another transition for state " + state.getStateName() + "? enter 1 for yes, enter 0 for no");
                answer = keyboard.nextInt();
                keyboard.nextLine();
                transitionIndex++;
            } while (answer != 0);
        }
    }

    public static void printInfo(String outputFile) throws IOException {
    	System.out.println("========Printing all the states========");
    	userInputParser.printListOfStates(outputFile);
    }
    
}