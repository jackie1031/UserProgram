package UserProgram;

import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;


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

	public void addStateToMap(String stateName, State state) {
		this.stateNameToState.put(stateName, state);
	}

	public State getStateByName(String stateName) {
		return this.stateNameToState.get(stateName);
	}

    public void printListOfStates() throws IOException {

    	PrintWriter p = new PrintWriter("output.txt");
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
 		for (State s : listOfStates) {
 			s.printState(p);
    	}

    	// CLOSE FILE
    	p.println("EOF");
    	p.close();
    }
    
}