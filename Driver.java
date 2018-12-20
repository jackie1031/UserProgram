package UserProgram;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

/* The driver program interacts with user,
 * asking for inputs of #of variables in each state,
 * names and initial values.
 */
public class Driver {

	private static Scanner keyboard = new Scanner(System.in);
	private static int numVariablesInEachState;
	private static String[] arrayOfVariableNames;
	private static UserInputParser userInputParser;
    private static Queue<State> q;
    private static TreeSet<State> setOfUniqueStates;

    public static void main(String[] args) throws IOException {
    	userInputParser = new UserInputParser();
        q = new LinkedList<State>();
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

        // Get the info for the first state
        // Get the state name for the first state
        System.out.println("Please enter the name of state #1:");
        String stateName = keyboard.nextLine();
        State newState = new State(numVariablesInEachState, stateName);

        // Get the initial values of variables in the first state
        for (int i = 0; i < numVariablesInEachState; i++) {
            System.out.println("Please enter initial value for variable " + arrayOfVariableNames[i] + " in state " + stateName + ":");
            int initialValue = keyboard.nextInt();
            Variable newVariable = new Variable(arrayOfVariableNames[i], initialValue);
            newState.addVariableToArray(newVariable);
        }

        // Add the first state to the queue and set
        q.add(newState);
        userInputParser.addStateToMap(stateName, newState);
        userInputParser.addState(newState);

    	int stateIndex = 2;
    	int answer = 1;
        keyboard.nextLine();
    	while (!q.isEmpty()) {
            // Pop out the first state in the waiting queue
            State currState = q.poll();
            int transitionIndex = 1;
            // Ask for transitions regarding the current state
            do {
                // Get input
                System.out.println("Please enter input name for transition #" + transitionIndex + " in state " + currState.getStateName() + ":");
                String inputName = keyboard.nextLine();
                // Get output
                System.out.println("Please enter output name for transition #" + transitionIndex + " in state " + currState.getStateName() + ":");
                String outputName = keyboard.nextLine();
                // Get destination
                System.out.println("Please enter destination state name for transition #" + transitionIndex + " in state " + currState.getStateName() + ":");
                String destStateName = keyboard.nextLine();
                State destState = userInputParser.getStateByName(destStateName);

                // If we have not seen such destination state in the set
                if (destState == null) {
                    // Create such new state
                    System.out.println("Destination state does not exist, creating a new state object");
                    State newDestState = new State(numVariablesInEachState, destStateName);

                    // Get the initial values of variables in the first state
                    for (int i = 0; i < numVariablesInEachState; i++) {
                        System.out.println("Please enter initial value for variable " + arrayOfVariableNames[i] + " in state " + destStateName + ":");
                        int initialValue = keyboard.nextInt();
                        Variable newVariable = new Variable(arrayOfVariableNames[i], initialValue);
                        newDestState.addVariableToArray(newVariable);
                    }

                    // Add it to the queue as well as the set
                    q.add(newDestState);
                    userInputParser.addStateToMap(destStateName, newDestState);
                    userInputParser.addState(newDestState);

                    // Assign destState again
                    destState = newDestState;
                }

                // Adding transition
                userInputParser.addInput(inputName);
                userInputParser.addOutput(outputName);
                currState.addTransition(inputName, outputName, destState);
                System.out.println("Transition <" + inputName + "/" + outputName + ", " + destStateName +"> added to state" + currState.getStateName());

                // Recursively ask for transitions
                System.out.println("Enter another transition for state " + currState.getStateName() + "? enter 1 for yes, enter 0 for no");
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