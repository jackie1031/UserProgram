package UserProgram;

public class Variable {

    private final String variableName;
    private final int variableValue;

    public Variable(String name, int value) {
    	this.variableName = name;
    	this.variableValue = value;
    }

    public void printVariable() {
    	System.out.println("variableName: " + this.variableName + ", variableValue: " + variableValue);
    }
    
}