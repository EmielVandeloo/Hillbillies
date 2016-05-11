package hillbillies.expression.bool;

import hillbillies.model.Unit;

public class Or extends DoubleExpression {
	
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
	public Or(Unit unit, BooleanExpression first, BooleanExpression second) 
			throws IllegalArgumentException {
		super(unit, first, second);
	}
	
	
	// OVERRIDE

	@Override
	public boolean getResult() {
		return (getFirst().getResult() || getSecond().getResult());
	}

}
