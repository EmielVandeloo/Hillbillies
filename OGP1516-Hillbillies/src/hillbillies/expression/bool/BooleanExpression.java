package hillbillies.expression.bool;

import hillbillies.expression.Expression;
import hillbillies.model.Unit;

public abstract class BooleanExpression extends Expression {
	
	// CONSTRUCTOR
	
	public BooleanExpression(Unit unit) throws IllegalArgumentException {
		super(unit);
	}
	
	
	// ABSTRACT METHODS

	public abstract boolean getResult();

}