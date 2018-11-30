package UserProgram;

public class State {

	private String stateName;
    private Variable[] arrayOfVariables;
    private int currArrayIndex;
    private int arraySize;

    public State(int arrayLength, String name) {
    	this.stateName = name;
    	this.arrayOfVariables = new Variable[arrayLength];
    	this.arraySize = arrayLength;
    	this.currArrayIndex = 0;
    }

    public void addVariableToArray(Variable variable) {
    	if (currArrayIndex >= arraySize) {
    		System.out.println("The array of variable is full!");
    		return;
    	}
    	this.arrayOfVariables[currArrayIndex] = variable;
    	currArrayIndex++;
    }
    
    public void printState() {
    	System.out.println("The state name is: " + this.stateName);
    	for (int i = 0; i < arraySize; i++) {
    		this.arrayOfVariables[i].printVariable();
    	}
    }
}