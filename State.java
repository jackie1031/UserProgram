package UserProgram;

import java.io.PrintWriter;

import java.util.HashMap;
import java.util.Map;

/* Each state object holds its state name
 * and a list of variables, which are
 * <variableName, variableValue> pairs.
 */
public class State {

	private String stateName;
    public Variable[] arrayOfVariables;
    private int currArrayIndex;
    private int arraySize;
    private HashMap<String, State> transitions;


    public State(int arrayLength, String name) {
    	this.stateName = name;
    	this.arrayOfVariables = new Variable[arrayLength];
    	this.arraySize = arrayLength;
    	this.currArrayIndex = 0;
        this.transitions = new HashMap<String, State>();
    }

    public String getStateName() {
        return this.stateName;
    }

    public void addVariableToArray(Variable variable) {
    	if (currArrayIndex >= arraySize) {
    		System.out.println("The array of variable is full!");
    		return;
    	}
    	this.arrayOfVariables[currArrayIndex] = variable;
    	currArrayIndex++;
    }

    public void addTransition(String input, State destination) {
        this.transitions.put(input, destination);
    }
    
    public void printState(PrintWriter p) {
        for (String input : this.transitions.keySet()) {
            State destState = this.transitions.get(input);

            // Print CURR : DEST
            p.print(this.stateName);
            // for (int i = 0; i < arraySize; i++) {
            //     p.print(this.arrayOfVariables[i].getVariableValue());
            //     if (i < arraySize - 1) {
            //         p.print(",");
            //     }
            // }

            p.print(" : " + destState.getStateName() + " ");
            // for (int i = 0; i < arraySize; i++) {
            //     p.print(destState.arrayOfVariables[i].getVariableValue());
            //     if (i < arraySize - 1) {
            //         p.print(",");
            //     }
            // }
            // p.print(")");
            p.print(" WHEN ");

            // Print the transition
            // TODO: add output!!!
            // TODO: add comma at the end!!
            p.println("(" + input + "," + "out1" + ")" + " COST 1");
        }
    }
}