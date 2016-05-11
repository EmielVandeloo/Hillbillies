package hillbillies.expression.bool;

import hillbillies.model.Unit;

/**
 * A class of and expressions.
 * 
 * @invar  The first expression of each and expression must be a valid first expression for any
 *         and expression.
 *       | isValidFirst(getFirst())
 * @invar  The second expression of each and expression must be a valid second expression for any
 *         and expression.
 *       | isValidSecond(getSecond())
 * 
 * @author Pieter-Jan
 *
 */
public class And extends DoubleExpression {
	
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
	public And(Unit unit, BooleanExpression first, BooleanExpression second) 
			throws IllegalArgumentException {
		super(unit, first, second);
	}
	
	
	// OVERRIDE

	@Override
	public boolean getResult() {
		return (getFirst().getResult() && getSecond().getResult());
	}

}