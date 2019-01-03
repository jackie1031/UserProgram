package UserProgram;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.*; 


/* The driver program interacts with user,
 * asking for inputs of #of variables in each state,
 * names and initial values as well as all possible transitions
 */
public class Driver {

	private static Scanner keyboard = new Scanner(System.in);
	private static int numVariablesInEachState;
	private static String[] arrayOfVariableNames;
	private static UserInputParser userInputParser;
    private static Queue<State> q;

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

    /* For each state:
     *  Step 1: ask user how many variables are in each state
     *  Step 2: ask user to input each name of the variable
     *  Step 3: looping to ask user input state names and 
     * 		   values of variables that the state contains
     *
     * First ask user information of the first state and add the first state to the queue
     * Then recursively pop out the head of the queue, asking for details of the transitions
     * for the state:
     *  1: input/output pair of each transition
     *  2: destnation state of each correcponding transition
     *      a. if the destnation state does not exist, create one (ask info for the state in
     *         the order of Step1 - Step3) and add it to the queue + unique set of states.
     *         then add the transition between current state and destination state
     *      b. if the destnation state already exists in the unique set of states, just add
     *         the transition between current state and destination state 
 	 */
    public static void takeInput() {

        System.out.println("========Ask whether a fully specified FSM========");
        System.out.println("Is this a fully specified FSM? (y/n)");
        String response = keyboard.nextLine();
        System.out.println("the response is: "+ response);

        if (response.equals("y")) {
            System.out.println(" this is a fully specified FSM");
            fullySpecifiedFSM();

        } else {
            System.out.println(" this is NOT a fully specified FSM");
            notFullyFSM();

        }
   
    }

    private static void fullySpecifiedFSM (){
        // ask user how many variables are in each state
        System.out.println("========Now asking for information on variables========");
        System.out.println("How many variables are in each state?");
        numVariablesInEachState = keyboard.nextInt();
        keyboard.nextLine();
        arrayOfVariableNames = new String[numVariablesInEachState];
        
        // ask user to input names of variables one by one
        for (int i = 0; i < numVariablesInEachState; i++) {
            System.out.println("Please enter the name of variable #" + (i+1) + ":");
            arrayOfVariableNames[i] = keyboard.nextLine();
        }

        // ask user how many inputs are there
        System.out.println("========Now asking for inputs ========");
        System.out.println("How many inputs are there?");
        int numInputs = keyboard.nextInt();
        keyboard.nextLine();
        String[] arrayOfInputs = new String[numInputs];

        // ask for inputs names and possible input values
        for (int i = 0; i < numInputs; i++) {
            System.out.println("Please enter the value of input #" + (i+1) + ":");
            arrayOfInputs[i] = keyboard.nextLine();
        }

        // ask for information on states and transitions
        System.out.println("========Now asking for information on states and transitions========");
        System.out.println("(Please notice that state #1 is the initial state)");
        
        /* Get the info for the first state */
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
        userInputParser.addStateToMap(newState.encodeToString(), newState);
        userInputParser.addState(newState);

        int stateIndex = 2;
        int answer = 1;
        keyboard.nextLine();

        // Main loop
        while (!q.isEmpty()) {
            // Pop out the first state in the waiting queue
            State currState = q.poll();
            // Ask for transitions regarding the current state
            for (int transitionIndex = 0; transitionIndex < numInputs; transitionIndex++) {

                // Get input
                System.out.println("The input name for transition #" + (transitionIndex+1) + " in state " + currState.getStateName() + "is: " + arrayOfInputs[transitionIndex]);
                String inputName = arrayOfInputs[transitionIndex];

                // Skip this transition if such transition does not exist
                // System.out.println("Does such transition corresponding to input " + arrayOfInputs[transitionIndex] + " exist for state " + currState.getStateName() + "? enter 1 for yes, enter 0 for no");
                // answer = keyboard.nextInt();
                // keyboard.nextLine();
                // if (answer == 0) {
                //     continue;
                // }

                // No need to check duplicate inputs here
                
                // Get output
                System.out.println("Given input " + arrayOfInputs[transitionIndex] +", please enter its output name for transition #" + (transitionIndex+1) + " in state " + currState.getStateName() 
                    +  ":");
                String outputName = keyboard.nextLine();

                // Get encoded destination
                System.out.println("Now asking for internal memory of destination state");
                Variable[] newArrayOfVariables = new Variable[numVariablesInEachState];
                for (int i = 0; i < numVariablesInEachState; i++) {
                    System.out.println("Please enter initial value for variable " + arrayOfVariableNames[i] + " in the destination state:");
                    int initialValue = keyboard.nextInt();
                    Variable newVariable = new Variable(arrayOfVariableNames[i], initialValue);
                    newArrayOfVariables[i] = newVariable;
                }

                String encodedDest = State.encodeArrayToString(newArrayOfVariables, numVariablesInEachState);
                keyboard.nextLine();

                // Get destination
                State destState = userInputParser.getStateByName(encodedDest);


                // if input & destination state the same?


                // If we have not seen such destination state in the set
                if (destState == null) {
                    // Create such new state
                    System.out.println("Destination state does not exist, creating a new state object");
                    // Get the state name
                    System.out.println("Please enter destination state name for such transition #" + transitionIndex + ": <" + inputName + "/" + outputName + "> in state encoded as: " + encodedDest);
                    String destStateName = keyboard.nextLine();
                    
                    // check duplicate state name!!
                    Set<String> allStateNames = userInputParser.getListOfAllStateName();
                    while(allStateNames.contains(destStateName)) {
                        System.out.println("!!!Warning!!! Duplicate state name, please enter again for such transition #" + transitionIndex + ": <" + inputName + "/" + outputName + "> in state encoded as: " + encodedDest);
                        destStateName = keyboard.nextLine();

                    }

                    State newDestState = new State(numVariablesInEachState, destStateName);

                    // Assign the array to the destination state
                    newDestState.setArrayOfVariables(newArrayOfVariables);

                    // Add it to the queue as well as the set
                    q.add(newDestState);
                    userInputParser.addStateToMap(encodedDest, newDestState);
                    userInputParser.addState(newDestState);

                    // Assign destState again
                    destState = newDestState;
                }

                // Adding transition
                userInputParser.addInput(inputName);
                userInputParser.addOutput(outputName);
                currState.addTransition(inputName, outputName, destState);
                System.out.println("Transition <" + inputName + "/" + outputName + "---> " + destState.encodeToString() +"> added to state" + currState.getStateName());
            }
        }  
    }

    private static void notFullyFSM (){
        // ask user how many variables are in each state
        System.out.println("========Now asking for information on variables========");
        System.out.println("How many variables are in each state?");
        numVariablesInEachState = keyboard.nextInt();
        keyboard.nextLine();
        arrayOfVariableNames = new String[numVariablesInEachState];
        
        // ask user to input names of variables one by one
        for (int i = 0; i < numVariablesInEachState; i++) {
            System.out.println("Please enter the name of variable #" + (i+1) + ":");
            arrayOfVariableNames[i] = keyboard.nextLine();
        }

        // ask for information on states and transitions
        System.out.println("========Now asking for information on states and transitions========");
        System.out.println("(Please notice that state #1 is the initial state)");
        
        /* Get the info for the first state */
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
        userInputParser.addStateToMap(newState.encodeToString(), newState);
        userInputParser.addState(newState);

        int stateIndex = 2;
        int answer = 1;
        keyboard.nextLine();

        // Main loop
        while (!q.isEmpty()) {
            // Pop out the first state in the waiting queue
            State currState = q.poll();
            int transitionIndex = 1;
            // Ask for transitions regarding the current state
            do {
                // Get input
                System.out.println("Please enter input name for transition #" + transitionIndex + " in state " + currState.getStateName() + ":");
                String inputName = keyboard.nextLine();

                // Check duplicate inputs
                Set<String> allInput = currState.getAllInputNames();

                // while (currState.transitions.KeysSet().getkey().contains(inputName)) {
                while (allInput.contains(inputName)) {
                    System.out.println("!!!Warning!!! duplicate input, enter new input name");
                    inputName = keyboard.nextLine();
                }
                
                // Get output
                System.out.println("Please enter output name for transition #" + transitionIndex + " in state " + currState.getStateName() + ":");
                String outputName = keyboard.nextLine();

                // Get encoded destination
                System.out.println("Now asking for internal memory of destination state");
                Variable[] newArrayOfVariables = new Variable[numVariablesInEachState];
                for (int i = 0; i < numVariablesInEachState; i++) {
                    System.out.println("Please enter initial value for variable " + arrayOfVariableNames[i] + " in the destination state:");
                    int initialValue = keyboard.nextInt();
                    Variable newVariable = new Variable(arrayOfVariableNames[i], initialValue);
                    newArrayOfVariables[i] = newVariable;
                }

                String encodedDest = State.encodeArrayToString(newArrayOfVariables, numVariablesInEachState);
                keyboard.nextLine();

                // Get destination
                State destState = userInputParser.getStateByName(encodedDest);


                // if input & destination state the same?


                // If we have not seen such destination state in the set
                if (destState == null) {
                    // Create such new state
                    System.out.println("Destination state does not exist, creating a new state object");
                    // Get the state name
                    System.out.println("Please enter destination state name for such transition #" + transitionIndex + ": <" + inputName + "/" + outputName + "> in state encoded as: " + encodedDest);
                    String destStateName = keyboard.nextLine();
                    
                    // check duplicate state name!!
                    Set<String> allStateNames = userInputParser.getListOfAllStateName();
                    while(allStateNames.contains(destStateName)) {
                        System.out.println("!!!Warning!!! Duplicate state name, please enter again for such transition #" + transitionIndex + ": <" + inputName + "/" + outputName + "> in state encoded as: " + encodedDest);
                        destStateName = keyboard.nextLine();

                    }

                    State newDestState = new State(numVariablesInEachState, destStateName);

                    // Assign the array to the destination state
                    newDestState.setArrayOfVariables(newArrayOfVariables);

                    // Add it to the queue as well as the set
                    q.add(newDestState);
                    userInputParser.addStateToMap(encodedDest, newDestState);
                    userInputParser.addState(newDestState);

                    // Assign destState again
                    destState = newDestState;
                }

                // Adding transition
                userInputParser.addInput(inputName);
                userInputParser.addOutput(outputName);
                currState.addTransition(inputName, outputName, destState);
                System.out.println("Transition <" + inputName + "/" + outputName + "---> " + destState.encodeToString() +"> added to state" + currState.getStateName());

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