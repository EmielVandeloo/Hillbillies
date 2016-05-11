package hillbillies.expression.bool;

import hillbillies.model.Unit;

/**
 * A class of brackets.
 * 
 * @invar  The expression of each bracket expression must be a valid expression for any
 *         bracket expression.
 *       | isValidExpression(getExpression())
 * 
 * @author Pieter-Jan
 *
 */
public class Bracket extends SingleExpression {

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
	public Bracket(Unit unit, BooleanExpression expression) throws IllegalArgumentException {
		super(unit, expression);
	}


	// OVERRIDE

	@Override
	public boolean getResult() {
		// TODO Nog niet zeker van deze uitwerking.
		return getExpression().getResult();
	}

}
