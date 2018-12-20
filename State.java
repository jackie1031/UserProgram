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
    //private HashMap<String, State> transitions;

// Map<String, Entry<Action, Boolean>> actionMap = new HashMap<String, Entry<Action, Boolean>>();

    private HashMap<Map.Entry<String, String>, State> transitions;
    // private Entry<String, String> inOutPair;



    public State(int arrayLength, String name) {
    	this.stateName = name;
    	this.arrayOfVariables = new Variable[arrayLength];
    	this.arraySize = arrayLength;
    	this.currArrayIndex = 0;
        //this.transitions = new HashMap< String, State>();
        this.transitions = new HashMap<Map.Entry<String, String>, State>();
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

    public void addTransition(String input, String output, State destination) {
        // this.transitions.put(input, destination);
        //Entry<String, String> actionMapEntry = actionMap.get("action_name");
        Map.Entry<String,String> entry = new AbstractMap.SimpleEntry<String, String>(input,output);
        this.transitions.put(entry,destination);
        
    }
    
    // Map<String, Entry<Action, Boolean>> actionMap = new HashMap<String, Entry<Action, Boolean>>();

// actionMap.put("action_name", new SimpleEntry(action, true));

// Entry<Action, Boolean> actionMapEntry = actionMap.get("action_name");

// if(actionMapEntry.value()) actionMapEntry.key().applyAction();


    public void printState(PrintWriter p, int flag) {

        int i = 0;
        // p.println("the size of keyset is: "+ this.transitions.keySet().size());

        if (flag == 1) {
            p.print(this.stateName);

            //for (String input : this.transitions.keySet().getKey()) {
            for (Map.Entry<String,String> oneTrans : this.transitions.keySet()) {

                String input = oneTrans.getKey();
                String output = oneTrans.getValue();

                i += 1;
                // p.println("i is " + i);
                State destState = this.transitions.get(oneTrans);
                p.print(" : " + destState.getStateName() + " ");

                if (i != this.transitions.keySet().size()) {
                    p.println("WHEN (" + input + "," + output + ")" + " COST 1");

                } else if (i == this.transitions.keySet().size()){
                    p.println("WHEN (" + input + "," + output + ")" + " COST 1" + ",");

                }

            }        
        } else {
            p.print(this.stateName);

            // for (String input : this.transitions.keySet()) {
            for (Map.Entry<String,String> oneTrans : this.transitions.keySet()) {
                String input = oneTrans.getKey();
                String output = oneTrans.getValue();
                i += 1;
                // p.println("i is " + i);
                State destState = this.transitions.get(oneTrans);
                p.print(" : " + destState.getStateName() + " ");

                p.println("WHEN (" + input + "," + output + ")" + " COST 1");

            }
        }

    }


}