package hillbillies.expression.bool;

import hillbillies.expression.bool.checker.DoubleBoolChecker;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.program.Program;

/**
 * A class of and expressions.
 * 
 * @author  Pieter-Jan Van den Broecke: EltCw
 * 		    Emiel Vandeloo: WtkCw
 */
public class And extends DoubleBoolChecker {
	
	// CONSTRUCTOR

	/**
	 * Initialize this new and expression with given first- and second expression.
	 *
	 * @param  unit
	 *		   The unit for this new and expression.
	 * @param  first
	 *         The first expression for this new and expression.
	 * @param  second
	 *         The second expression for this new and expression.
	 * @effect The first expression of this new and expression is set to
	 *         the given first expression.
	 *       | this.setFirst(first)
	 * @effect The second expression of this new and expression is set to
	 *         the given second expression.
	 *       | this.setSecond(second)
	 */
	public And(SourceLocation sourceLocation, BooleanExpression first, BooleanExpression second) 
			throws IllegalArgumentException {
		
		super(sourceLocation, first, second);
	}
	
	
	// OVERRIDE

	@Override
	public Boolean evaluate(Program program) {
		return getFirst().evaluate(program) && getSecond().evaluate(program);
	}
	
	@Override
	public String toString() {
		return "(" + getFirst().toString() + " and " + getSecond().toString() + ")";
	}

}