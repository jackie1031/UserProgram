package UserProgram;

import java.io.PrintWriter;

import java.util.HashMap;
import java.util.Map;
import java.io.*; 
import java.util.*; 

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
    
    public void printState(PrintWriter p, int flag) {
        // Set<String> list = this.transitions.keySet();
        // Iterator<String> iterator = list.iterator();

        // while (iterator.hasNext()) {
        //     String name = iterator.next();
        //     State destState = this.transitions.get(name);
        //     p.print(this.stateName);
        //     p.print(" : " + destState.getStateName() + " ");
        //     p.print(" WHEN ");
        //     if (iterator.hasNext()) {
        //         p.println("(" + name + "," + "out1" + ")" + " COST 1" + ",");
        //     } else {
        //         p.println("(" + name + "," + "out1" + ")" + " COST 1");

        //     }

        // }

        int i = 0;
        // p.println("the size of keyset is: "+ this.transitions.keySet().size());

        if (flag == 1) {
            p.print(this.stateName);

            for (String input : this.transitions.keySet()) {
                i += 1;
                // p.println("i is " + i);
                State destState = this.transitions.get(input);
                p.print(" : " + destState.getStateName() + " ");

                if (i != this.transitions.keySet().size()) {
                    p.println("(" + input + "," + "out1" + ")" + " COST 1");

                } else if (i == this.transitions.keySet().size()){
                    p.println("(" + input + "," + "out1" + ")" + " COST 1" + ",");

                }
                // Print CURR : DEST
                // p.print(this.stateName);
                // p.print(" : " + destState.getStateName() + " ");
                // p.print(" WHEN ");

            }        
        } else {
            p.print(this.stateName);

            for (String input : this.transitions.keySet()) {
                i += 1;
                // p.println("i is " + i);
                State destState = this.transitions.get(input);
                p.print(" : " + destState.getStateName() + " ");

                p.println("(" + input + "," + "out1" + ")" + " COST 1");

            }
        }

    }


}