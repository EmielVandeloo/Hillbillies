package hillbillies.expression.object;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.program.Program;

/**
 * @invar  The variable name of each read variable must be a valid variable name for any
 *         read variable.
 *       | isValidVariableName(getVariableName())
 */
public class ReadVariable extends ObjectExpression {
	
	// FIELDS

	/**
	 * Variable registering the variable name of this read variable.
	 */
	private String variableName;
	
	
	// CONSTRUCTOR

	/**
	 * Initialize this new read variable with given variable name
	 * and source location.
	 *
	 * @param  variableName
	 *         The variable name for this new read variable.
	 * @effect The variable name of this new read variable is set to
	 *         the given variable name.
	 *       | this.setVariableName(variableName)
	 */
	public ReadVariable(SourceLocation sourceLocation, String variableName) 
			throws IllegalArgumentException {
		
		super(sourceLocation);
		this.setVariableName(variableName);
	}
	
	
	// GETTERS AND SETTERS

	/**
	 * Return the variable name of this read variable.
	 */
	@Basic @Raw
	public String getVariableName() {
		return this.variableName;
	}

	/**
	 * Check whether the given variable name is a valid variable name for
	 * any read variable.
	 *  
	 * @param  variable name
	 *         The variable name to check.
	 * @return 
	 *       | result == (variableName != null)
	 */
	public static boolean isValidVariableName(String variableName) {
		return (variableName != null);
	}

	/**
	 * Set the variable name of this read variable to the given variable name.
	 * 
	 * @param  variableName
	 *         The new variable name for this read variable.
	 * @post   The variable name of this new read variable is equal to
	 *         the given variable name.
	 *       | new.getVariableName() == variableName
	 * @throws IllegalArgumentException
	 *         The given variable name is not a valid variable name for any
	 *         read variable.
	 *       | ! isValidVariableName(getVariableName())
	 */
	@Raw
	public void setVariableName(String variableName) throws IllegalArgumentException {
		if (! isValidVariableName(variableName))
			throw new IllegalArgumentException();
		this.variableName = variableName;
	}
	
	
	// OVERRIDE

	@Override
	public Object evaluate(Program program) {
		return program.getGlobalVariables().get(variableName);
	}

	@Override
	public String toString() {
		return "readVariable: '" + getVariableName() + "'";
	}
	
}
