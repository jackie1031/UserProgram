package UserProgram;

import java.io.PrintWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
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
    private static PrintWriter log;
    private static boolean isReadingFromLog;

    public static void main(String[] args) throws IOException, FileNotFoundException {
        System.out.println("Loading previous inputs from the log? enter 1 for yes, enter 0 for no");
        int answer = keyboard.nextInt();
        keyboard.nextLine();
        if (answer == 1) {
            keyboard = new Scanner(new File("InputLog.txt"));
            isReadingFromLog = true;
            log = new PrintWriter(new FileWriter("InputLog.txt", true));
        } else {
            isReadingFromLog = false;
            log = new PrintWriter("InputLog.txt");
        }
    	userInputParser = new UserInputParser();
        q = new LinkedList<State>();
        String outFileName = askOutFileName();
    	takeInput();
    	printInfo(outFileName) ;
    }

    public static String askOutFileName() throws FileNotFoundException {
        System.out.println("======== Input File name ========");
        System.out.println("what's the output file name?");
        // Switch scanner if the log reaches its end.
        if (isReadingFromLog && !keyboard.hasNextLine()) {
            log.println();
            keyboard = new Scanner(System.in);
            System.out.println("Log file reaches the end, please input the answer to the previous question");
            isReadingFromLog = false;
        }
        String outFileName = keyboard.nextLine();
        if (!isReadingFromLog) {
            log.println(outFileName);
        } else {
            System.out.println("Loaded answer:" + outFileName);
        }
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


        // Switch scanner if the log reaches its end.
        if (isReadingFromLog && !keyboard.hasNextLine()) {
            log.println();
            keyboard = new Scanner(System.in);
            System.out.println("Log file reaches the end, please input the answer to the previous question");
            isReadingFromLog = false;
        }
        String response = keyboard.nextLine();
        if (!isReadingFromLog) {
            log.println(response);
        } else {
            System.out.println("Loaded answer:" + response);
        }

        System.out.println("the response is: "+ response);

        if (response.equals("y")) {
            System.out.println(" this is a fully specified FSM");
            try {
                fullySpecifiedFSM();
            } catch (Exception e) {
                log.close();
            }

        } else {
            System.out.println(" this is NOT a fully specified FSM");
            try {
                notFullyFSM();
            } catch (Exception e) {
                log.close();
            }

        }
   
    }

    private static void fullySpecifiedFSM (){
        // ask user how many variables are in each state
        System.out.println("========Now asking for information on variables========");
        System.out.println("How many variables are in each state?");

        // Switch scanner if the log reaches its end.
        if (isReadingFromLog && !keyboard.hasNextLine()) {
            log.println();
            keyboard = new Scanner(System.in);
            System.out.println("Log file reaches the end, please input the answer to the previous question");
            isReadingFromLog = false;
        }
        numVariablesInEachState = keyboard.nextInt();
        keyboard.nextLine();
        if (!isReadingFromLog) {
            log.println(numVariablesInEachState);
        } else {
            System.out.println("Loaded answer:" + numVariablesInEachState);
        }

        arrayOfVariableNames = new String[numVariablesInEachState];
        
        // ask user to input names of variables one by one
        for (int i = 0; i < numVariablesInEachState; i++) {
            System.out.println("Please enter the name of variable #" + (i+1) + ":");


            // Switch scanner if the log reaches its end.
            if (isReadingFromLog && !keyboard.hasNextLine()) {
                log.println();
                keyboard = new Scanner(System.in);
                System.out.println("Log file reaches the end, please input the answer to the previous question");
                isReadingFromLog = false;
            }
            arrayOfVariableNames[i] = keyboard.nextLine();
            if (!isReadingFromLog) {
                log.println(arrayOfVariableNames[i]);
            } else {
                System.out.println("Loaded answer:" + arrayOfVariableNames[i]);
            }

        }

        // ask user how many inputs are there
        System.out.println("========Now asking for inputs ========");
        System.out.println("How many inputs are there?");

        // Switch scanner if the log reaches its end.
        if (isReadingFromLog && !keyboard.hasNextLine()) {
            log.println();
            keyboard = new Scanner(System.in);
            System.out.println("Log file reaches the end, please input the answer to the previous question");
            isReadingFromLog = false;
        }
        int numInputs = keyboard.nextInt();
        keyboard.nextLine();
        if (!isReadingFromLog) {
            log.println(numInputs);
        } else {
            System.out.println("Loaded answer:" + numInputs);
        }

        String[] arrayOfInputs = new String[numInputs];

        // ask for inputs names and possible input values
        for (int i = 0; i < numInputs; i++) {
            System.out.println("Please enter the value of input #" + (i+1) + ":");

            // Switch scanner if the log reaches its end.
            if (isReadingFromLog && !keyboard.hasNextLine()) {
                log.println();
                keyboard = new Scanner(System.in);
                System.out.println("Log file reaches the end, please input the answer to the previous question");
                isReadingFromLog = false;
            }
            arrayOfInputs[i] = keyboard.nextLine();
            if (!isReadingFromLog) {
                log.println(arrayOfInputs[i]);
            } else {
                System.out.println("Loaded answer:" + arrayOfInputs[i]);
            }

        }

        // ask for information on states and transitions
        System.out.println("========Now asking for information on states and transitions========");
        System.out.println("(Please notice that state #1 is the initial state)");
        
        /* Get the info for the first state */
        // Get the state name for the first state
        System.out.println("Please enter the name of state #1:");


        // Switch scanner if the log reaches its end.
        if (isReadingFromLog && !keyboard.hasNextLine()) {
            log.println();
            keyboard = new Scanner(System.in);
            System.out.println("Log file reaches the end, please input the answer to the previous question");
            isReadingFromLog = false;
        }
        String stateName = keyboard.nextLine();
        if (!isReadingFromLog) {
            log.println(stateName);
        } else {
            System.out.println("Loaded answer:" + stateName);
        }

        State newState = new State(numVariablesInEachState, stateName);

        // Get the initial values of variables in the first state
        for (int i = 0; i < numVariablesInEachState; i++) {
            System.out.println("Please enter initial value for variable " + arrayOfVariableNames[i] + " in state " + stateName + ":");
            
            // Switch scanner if the log reaches its end.
            if (isReadingFromLog && !keyboard.hasNextLine()) {
                log.println();
                keyboard = new Scanner(System.in);
                System.out.println("Log file reaches the end, please input the answer to the previous question");
                isReadingFromLog = false;
            }
            int initialValue = keyboard.nextInt();
            if (!isReadingFromLog) {
                log.println(initialValue);
            } else {
                System.out.println("Loaded answer:" + initialValue);
            }

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

                // Switch scanner if the log reaches its end.
                if (isReadingFromLog && !keyboard.hasNextLine()) {
                    log.println();
                    keyboard = new Scanner(System.in);
                    System.out.println("Log file reaches the end, please input the answer to the previous question");
                    isReadingFromLog = false;
                }
                String outputName = keyboard.nextLine();
                if (!isReadingFromLog) {
                    log.println(outputName);
                } else {
                    System.out.println("Loaded answer:" + outputName);
                }

                // Get encoded destination
                System.out.println("Now asking for internal memory of destination state");
                Variable[] newArrayOfVariables = new Variable[numVariablesInEachState];
                for (int i = 0; i < numVariablesInEachState; i++) {
                    System.out.println("Please enter initial value for variable " + arrayOfVariableNames[i] + " in the destination state:");

                    // Switch scanner if the log reaches its end.
                    if (isReadingFromLog && !keyboard.hasNextLine()) {
                        log.println();
                        keyboard = new Scanner(System.in);
                        System.out.println("Log file reaches the end, please input the answer to the previous question");
                        isReadingFromLog = false;
                    }
                    int initialValue = keyboard.nextInt();
                    if (!isReadingFromLog) {
                        log.println(initialValue);
                    } else {
                        System.out.println("Loaded answer:" + initialValue);
                    }

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
                    
                    // Switch scanner if the log reaches its end.
                    if (isReadingFromLog && !keyboard.hasNextLine()) {
                        log.println();
                        keyboard = new Scanner(System.in);
                        System.out.println("Log file reaches the end, please input the answer to the previous question");
                        isReadingFromLog = false;
                    }
                    String destStateName = keyboard.nextLine();
                    if (!isReadingFromLog) {
                        log.println(destStateName);
                    } else {
                        System.out.println("Loaded answer:" + destStateName);
                    }
                    
                    // check duplicate state name!!
                    Set<String> allStateNames = userInputParser.getListOfAllStateName();
                    while(allStateNames.contains(destStateName)) {
                        System.out.println("!!!Warning!!! Duplicate state name, please enter again for such transition #" + transitionIndex + ": <" + inputName + "/" + outputName + "> in state encoded as: " + encodedDest);
                        
                        // Switch scanner if the log reaches its end.
                        if (isReadingFromLog && !keyboard.hasNextLine()) {
                            log.println();
                            keyboard = new Scanner(System.in);
                            System.out.println("Log file reaches the end, please input the answer to the previous question");
                            isReadingFromLog = false;
                        }
                        destStateName = keyboard.nextLine();
                        if (!isReadingFromLog) {
                            log.println(destStateName);
                        } else {
                            System.out.println("Loaded answer:" + destStateName);
                        }

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
        // Switch scanner if the log reaches its end.
        if (isReadingFromLog && !keyboard.hasNextLine()) {
            log.println();
            keyboard = new Scanner(System.in);
            System.out.println("Log file reaches the end, please input the answer to the previous question");
            isReadingFromLog = false;
        }
        numVariablesInEachState = keyboard.nextInt();
        keyboard.nextLine();
        if (!isReadingFromLog) {
            log.println(numVariablesInEachState);
        } else {
            System.out.println("Loaded answer:" + numVariablesInEachState);
        }
        arrayOfVariableNames = new String[numVariablesInEachState];
        
        // ask user to input names of variables one by one
        for (int i = 0; i < numVariablesInEachState; i++) {
            System.out.println("Please enter the name of variable #" + (i+1) + ":");
            // Switch scanner if the log reaches its end.
            if (isReadingFromLog && !keyboard.hasNextLine()) {
                log.println();
                keyboard = new Scanner(System.in);
                System.out.println("Log file reaches the end, please input the answer to the previous question");
                isReadingFromLog = false;
            }
            arrayOfVariableNames[i] = keyboard.nextLine();
            if (!isReadingFromLog) {
                log.println(arrayOfVariableNames[i]);
            } else {
                System.out.println("Loaded answer:" + arrayOfVariableNames[i]);
            }
        }

        // ask for information on states and transitions
        System.out.println("========Now asking for information on states and transitions========");
        System.out.println("(Please notice that state #1 is the initial state)");
        
        /* Get the info for the first state */
        // Get the state name for the first state
        System.out.println("Please enter the name of state #1:");
        // Switch scanner if the log reaches its end.
        if (isReadingFromLog && !keyboard.hasNextLine()) {
            log.println();
            keyboard = new Scanner(System.in);
            System.out.println("Log file reaches the end, please input the answer to the previous question");
            isReadingFromLog = false;
        }
        String stateName = keyboard.nextLine();
        if (!isReadingFromLog) {
            log.println(stateName);
        } else {
            System.out.println("Loaded answer:" + stateName);
        }
        State newState = new State(numVariablesInEachState, stateName);

        // Get the initial values of variables in the first state
        for (int i = 0; i < numVariablesInEachState; i++) {
            System.out.println("Please enter initial value for variable " + arrayOfVariableNames[i] + " in state " + stateName + ":");
            // Switch scanner if the log reaches its end.
            if (isReadingFromLog && !keyboard.hasNextLine()) {
                log.println();
                keyboard = new Scanner(System.in);
                System.out.println("Log file reaches the end, please input the answer to the previous question");
                isReadingFromLog = false;
            }
            int initialValue = keyboard.nextInt();
            if (!isReadingFromLog) {
                log.println(initialValue);
            } else {
                System.out.println("Loaded answer:" + initialValue);
            }
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
                
                // Switch scanner if the log reaches its end.
                if (isReadingFromLog && !keyboard.hasNextLine()) {
                    log.println();
                    keyboard = new Scanner(System.in);
                    System.out.println("Log file reaches the end, please input the answer to the previous question");
                    isReadingFromLog = false;
                }
                String inputName = keyboard.nextLine();
                if (!isReadingFromLog) {
                    log.println(inputName);
                } else {
                    System.out.println("Loaded answer:" + inputName);
                }

                // Check duplicate inputs
                Set<String> allInput = currState.getAllInputNames();

                // while (currState.transitions.KeysSet().getkey().contains(inputName)) {
                while (allInput.contains(inputName)) {
                    System.out.println("!!!Warning!!! duplicate input, enter new input name");

                    // Switch scanner if the log reaches its end.
                    if (isReadingFromLog && !keyboard.hasNextLine()) {
                        log.println();
                        keyboard = new Scanner(System.in);
                        System.out.println("Log file reaches the end, please input the answer to the previous question");
                        isReadingFromLog = false;
                    }
                    inputName = keyboard.nextLine();
                    if (!isReadingFromLog) {
                        log.println(inputName);
                    } else {
                        System.out.println("Loaded answer:" + inputName);
                    }

                }
                
                // Get output
                System.out.println("Please enter output name for transition #" + transitionIndex + " in state " + currState.getStateName() + ":");
                
                // Switch scanner if the log reaches its end.
                if (isReadingFromLog && !keyboard.hasNextLine()) {
                    log.println();
                    keyboard = new Scanner(System.in);
                    System.out.println("Log file reaches the end, please input the answer to the previous question");
                    isReadingFromLog = false;
                }
                String outputName = keyboard.nextLine();
                if (!isReadingFromLog) {
                    log.println(outputName);
                } else {
                    System.out.println("Loaded answer:" + outputName);
                }


                // Get encoded destination
                System.out.println("Now asking for internal memory of destination state");
                Variable[] newArrayOfVariables = new Variable[numVariablesInEachState];
                for (int i = 0; i < numVariablesInEachState; i++) {
                    System.out.println("Please enter initial value for variable " + arrayOfVariableNames[i] + " in the destination state:");
                    

                    // Switch scanner if the log reaches its end.
                    if (isReadingFromLog && !keyboard.hasNextLine()) {
                        log.println();
                        keyboard = new Scanner(System.in);
                        System.out.println("Log file reaches the end, please input the answer to the previous question");
                        isReadingFromLog = false;
                    }
                    int initialValue = keyboard.nextInt();
                    if (!isReadingFromLog) {
                        log.println(initialValue);
                    } else {
                        System.out.println("Loaded answer:" + initialValue);
                    }

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
                    
                    // Switch scanner if the log reaches its end.
                    if (isReadingFromLog && !keyboard.hasNextLine()) {
                        log.println();
                        keyboard = new Scanner(System.in);
                        System.out.println("Log file reaches the end, please input the answer to the previous question");
                        isReadingFromLog = false;
                    }
                    String destStateName = keyboard.nextLine();
                    if (!isReadingFromLog) {
                        log.println(destStateName);
                    } else {
                        System.out.println("Loaded answer:" + destStateName);
                    }

                    
                    // check duplicate state name!!
                    Set<String> allStateNames = userInputParser.getListOfAllStateName();
                    while(allStateNames.contains(destStateName)) {
                        System.out.println("!!!Warning!!! Duplicate state name, please enter again for such transition #" + transitionIndex + ": <" + inputName + "/" + outputName + "> in state encoded as: " + encodedDest);
                        
                        // Switch scanner if the log reaches its end.
                        if (isReadingFromLog && !keyboard.hasNextLine()) {
                            log.println();
                            keyboard = new Scanner(System.in);
                            System.out.println("Log file reaches the end, please input the answer to the previous question");
                            isReadingFromLog = false;
                        }
                        destStateName = keyboard.nextLine();
                        if (!isReadingFromLog) {
                            log.println(destStateName);
                        } else {
                            System.out.println("Loaded answer:" + destStateName);
                        }

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
                

                // Switch scanner if the log reaches its end.
                if (isReadingFromLog && !keyboard.hasNextLine()) {
                    log.println();
                    keyboard = new Scanner(System.in);
                    System.out.println("Log file reaches the end, please input the answer to the previous question");
                    isReadingFromLog = false;
                }
                answer = keyboard.nextInt();
                if (!isReadingFromLog) {
                    log.println(answer);
                } else {
                    System.out.println("Loaded answer:" + answer);
                }

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