package hillbillies.expression.bool;

import hillbillies.expression.bool.checker.BoolChecker;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.program.Program;

/**
 * A class to invert a boolean expression.
 * 
 * @invar  The expression of each invertion must be a valid expression for any
 *         invertion.
 *       | isValidExpression(getExpression())
 * 
 * @author  Pieter-Jan Van den Broecke: EltCw
 * 		    Emiel Vandeloo: WtkCw
 */
public class Invert extends BoolChecker {
	
	// CONSTRUCTOR

	/**
	 * Initialize this new invertion with given expression.
	 *
	 * @param  expression
	 *         The expression for this new invertion.
	 * @effect The expression of this new invertion is set to
	 *         the given expression.
	 *       | this.setExpression(expression)
	 */
	public Invert(SourceLocation sourceLocation, BooleanExpression expression) 
			throws IllegalArgumentException {
		
		super(sourceLocation, expression);
	}
	
	
	// OVERRIDE

	@Override
	public Boolean evaluate(Program program) {
		return ! getExpression().evaluate(program);
	}
	
	@Override
	public String toString() {
		return "(not " + getExpression().toString() + ")";
	}

}
