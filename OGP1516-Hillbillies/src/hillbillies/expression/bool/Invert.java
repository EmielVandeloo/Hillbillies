package hillbillies.expression.bool;

import hillbillies.model.Unit;

/**
 * A class to invert a boolean expression.
 * 
 * @invar  The expression of each invertion must be a valid expression for any
 *         invertion.
 *       | isValidExpression(getExpression())
 * 
 * @author Pieter-Jan
 *
 */
public class Invert extends SingleExpression {
	
	// CONSTRUCTOR

	/**
	 * Initialize this new invertion with given expression.
	 *
	 * @param  unit
	 * 		   The unit for this new invertion.
	 * @param  expression
	 *         The expression for this new invertion.
	 * @effect The expression of this new invertion is set to
	 *         the given expression.
	 *       | this.setExpression(expression)
	 */
	public Invert(Unit unit, BooleanExpression expression) throws IllegalArgumentException {
		super(unit, expression);
	}
	
	
	// OVERRIDE

	@Override
	public boolean getResult() {
		return ! getExpression().getResult();
	}

}
