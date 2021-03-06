package UserProgram;

import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.*;


/* States with initial values are stored in a list
 * Each state contains an array of variables
 * Each variable contains a name and value
 * So the data structure is a list of states
 * of array of variables.
 */
public class UserInputParser {
	private List<State> listOfStates;
	private HashMap<String, State> stateNameToState;
	private TreeSet<String> inputs;
	private TreeSet<String> outputs;

	public UserInputParser() {
		this.listOfStates = new ArrayList<State>();
		this.stateNameToState = new HashMap<String, State>();
		this.inputs = new TreeSet<String>();
		this.outputs = new TreeSet<String>();
	}

	public void addState(State newState) {
		this.listOfStates.add(newState);
	}

	public void addInput(String input) {
		this.inputs.add(input);
	}

	public void addOutput(String output) {
		this.outputs.add(output);
	}

	public List<State> getListOfStates() {
		return this.listOfStates;
	}

    public Set<String> getListOfAllStateName() {
        Set<String> allNames = new HashSet<String>(); 

        for (int i = 0; i < listOfStates.size(); i++) {
            allNames.add(listOfStates.get(i).getStateName());
            // System.out.println(listOfStates.get(i));
        }

        return allNames;

    }

	public void addStateToMap(String stateName, State state) {
		this.stateNameToState.put(stateName, state);
	}

	public State getStateByName(String stateName) {
		return this.stateNameToState.get(stateName);
	}

    public void printListOfStates(String outFileName) throws IOException {

    	PrintWriter p = new PrintWriter(outFileName);

    	// INPUTS
    	p.print("INPUTS ");
    	for (String s : inputs) {
    		p.print(s);
    		if (s != inputs.last()) {
    			p.print(", ");
    		}
    	}
    	p.println(";");

    	// OUTPUTS
    	p.print("OUTPUTS ");
    	for (String s : outputs) {
    		p.print(s);
    		if (s != outputs.last()) {
    			p.print(", ");
    		}
    	}
    	p.println(";");

    	// INITIAL STATE and OTHER STATES
    	p.println("INITIAL_STATE " + listOfStates.get(0).getStateName() + ";");
    	// OTHER STATES
    	p.print("OTHER_STATES ");
    	for (State s : listOfStates) {
    		//s.printState(p);
    		if (s == listOfStates.get(0)) {
    			continue;
    		}
    		p.print(s.getStateName());
    		if (s != listOfStates.get(listOfStates.size() - 1)) {
    			p.print(", ");
    		}
    	}
    	p.println(";");
 
 		// TRANSITIONS
 		p.print("TRANSITIONS ");  
        int numOfStates = 0; 
 		for (State s : listOfStates) {
            numOfStates += 1;
            if (numOfStates != listOfStates.size()) {
                s.printState(p,1);

            } else {
                s.printState(p,0);

            }
    	}

    	// CLOSE FILE
    	p.println("EOF");
    	p.close();
    }
    
}