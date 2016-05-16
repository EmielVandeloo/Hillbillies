package hillbillies.expression.bool;

import hillbillies.expression.bool.checker.BoolChecker;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.program.Program;

/**
 * A class of brackets.
 * 
 * @invar  The expression of each bracket expression must be a valid expression for any
 *         bracket expression.
 *       | isValidExpression(getExpression())
 * 
 * @author  Pieter-Jan Van den Broecke: EltCw
 * 		    Emiel Vandeloo: WtkCw
 *
 */
public class Bracket extends BoolChecker {

	// CONSTRUCTOR

	/**
	 * Initialize this new bracket with given expression.
	 * 
	 * @param  unit
	 * 		   The unit for this new bracket.
	 * @param  expression
	 *         The expression for this new bracket.
	 * @effect The expression of this new bracket is set to
	 *         the given expression.
	 *       | this.setExpression(expression)
	 */
	public Bracket(SourceLocation sourceLocation, BooleanExpression expression) 
			throws IllegalArgumentException {
		
		super(sourceLocation, expression);
	}


	// OVERRIDE

	@Override
	public Boolean evaluate(Program program) {
		return getExpression().evaluate(program);
	}
	
	@Override
	public String toString() {
		return "(" + getExpression().toString() + ")";
	}

}
