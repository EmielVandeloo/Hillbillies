package hillbillies.expression.unit;

import hillbillies.expression.Expression;
import hillbillies.model.Unit;

public abstract class UnitExpression extends Expression {
	
	// CONSTRUCTOR
	
	public UnitExpression(Unit unit) throws IllegalArgumentException {
		super(unit);
	}
	
	
	// ABSTRACT METHODS

	public abstract Unit returnUnit();

}