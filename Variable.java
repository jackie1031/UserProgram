package UserProgram;

import java.io.PrintWriter;

public class Variable {

    private final String variableName;
    private final int variableValue;

    public Variable(String name, int value) {
    	this.variableName = name;
    	this.variableValue = value;
    }

    public String getVariableName() {
    	return this.variableName;
    }

    public int getVariableValue() {
    	return this.variableValue;
    }

    public void printVariable(PrintWriter p) {
    	p.println("    variableName: " + this.variableName + ", variableValue: " + variableValue);
    }
    
}