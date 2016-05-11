package hillbillies.expression.position;

import hillbillies.expression.Expression;
import hillbillies.model.Unit;

public abstract class PositionExpression extends Expression {
	
	// CONSTRUCTOR
	
	public PositionExpression(Unit unit) throws IllegalArgumentException {
		super(unit);
	}
	
	
	// ABSTRACT METHODS

	public abstract boolean returnPosition();

}