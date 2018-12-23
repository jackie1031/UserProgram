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

    public Set<String> getAllInputNames() {
        Set<String> allNames = new HashSet<String>(); 

        for (Map.Entry<String, String> oneTrans : transitions.keySet()) {
             String input = oneTrans.getKey();
            allNames.add(input);
        }

        return allNames;

    }


    public void addVariableToArray(Variable variable) {
    	if (currArrayIndex >= arraySize) {
    		System.out.println("The array of variable is full!");
    		return;
    	}
    	this.arrayOfVariables[currArrayIndex] = variable;
    	currArrayIndex++;
    }

    public void setArrayOfVariables(Variable[] newArrayOfVariables) {
        this.arrayOfVariables = newArrayOfVariables;
    }

    public void addTransition(String input, String output, State destination) {
        Map.Entry<String,String> entry = new AbstractMap.SimpleEntry<String, String>(input,output);
        this.transitions.put(entry,destination);
        
    }

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

    public String encodeToString() {
        String s = "";
        for (int i = 0; i < arraySize; i++) {
            s = s + arrayOfVariables[i].getVariableName() + arrayOfVariables[i].getVariableValue();
        }
        System.out.println("encodeToString::stateName: " + stateName + " encodedValue: " + s);
        return s;
    }

    public static String encodeArrayToString(Variable[] arrayOfVariables, int arraySize) {
        String s = "";
        for (int i = 0; i < arraySize; i++) {
            s = s + arrayOfVariables[i].getVariableName() + arrayOfVariables[i].getVariableValue();
        }
        System.out.println("encodeArrayToString::encodedValue: " + s);
        return s;
    }

}