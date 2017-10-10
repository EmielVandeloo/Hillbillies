package hillbillies.expression.object;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.expression.Expression;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.program.Program;

public class ReadVariable<E> extends Expression<E> {

	private String variableName;
	
	public ReadVariable(SourceLocation sourceLocation, String variableName) 
			throws IllegalArgumentException {
		super(sourceLocation);
		this.setVariableName(variableName);
	}

	@Basic @Raw
	public String getVariableName() {
		return this.variableName;
	}

	public static boolean isValidVariableName(String variableName) {
		return (variableName != null);
	}

	@Raw
	public void setVariableName(String variableName) throws IllegalArgumentException {
		if (! isValidVariableName(variableName))
			throw new IllegalArgumentException();
		this.variableName = variableName;
	}

	@SuppressWarnings("unchecked")
	@Override
	public E evaluate(Program program) {
		return (E) program.getGlobalVariables().get(variableName);
	}

	@Override
	public String toString() {
		return "readVariable: '" + getVariableName() + "'";
	}
}