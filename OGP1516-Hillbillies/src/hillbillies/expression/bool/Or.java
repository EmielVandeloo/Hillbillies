package hillbillies.expression.bool;

import hillbillies.expression.Expression;
import hillbillies.expression.bool.checker.DoubleBoolChecker;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.program.Program;


/**
 * A class of or-expressions.
 * 
 * @author  Pieter-Jan Van den Broecke: EltCw
 * 		    Emiel Vandeloo: WtkCw
 */
public class Or extends DoubleBoolChecker {
	
	// CONSTRUCTOR

	/**
	 * Initialize this new or expression with given first- and second expression.
	 *
	 * @param  first
	 *         The first expression for this new or expression.
	 * @param  second
	 *         The second expression for this new or expression.
	 * @effect The first expression of this new or expression is set to
	 *         the given first expression.
	 *       | this.setFirst(first)
	 * @effect The second expression of this new or expression is set to
	 *         the given second expression.
	 *       | this.setSecond(second)
	 */
	public Or(SourceLocation sourceLocation, Expression<Boolean> first, Expression<Boolean> second) 
			throws IllegalArgumentException {
		
		super(sourceLocation, first, second);
	}
	
	
	// OVERRIDE

	@Override
	public Boolean evaluate(Program program) {
		return getFirst().evaluate(program) || getSecond().evaluate(program);
	}

}