package UserProgram;

/* States with initial values are stored in a list
 * Each state contains an array of variables
 * Each variable contains a name and value
 * So the data structure is a list of states
 * of array of variables.
 */
public class UserInputParser {
	//private List 
	public UserInputParser() {

	}

    public static void called() {
        System.out.println("Hello World!");
        Variable v1 = new Variable("x", 2);
        Variable v2 = new Variable("y", 2);
        Variable v3 = new Variable("z", 2);
        State s = new State(3, "Esthter");
        s.addVariableToArray(v1);
        s.addVariableToArray(v2);
        s.addVariableToArray(v3);
        s.printState();
    }
    
}