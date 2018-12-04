package UserProgram;

import java.util.ArrayList;
import java.util.List;

/* States with initial values are stored in a list
 * Each state contains an array of variables
 * Each variable contains a name and value
 * So the data structure is a list of states
 * of array of variables.
 */
public class UserInputParser {
	private List<State> listOfStates;

	public UserInputParser() {
		this.listOfStates = new ArrayList<State>();
	}

	public void addState(State newState) {
		this.listOfStates.add(newState);
	}

    public void printListOfStates() {
    	for (State s : listOfStates) {
    		s.printState();
    	}
    }
    
}