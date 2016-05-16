package hillbillies.expression.unit;

import hillbillies.expression.Expression;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;

public abstract class UnitExpression extends Expression<Unit> {

	public UnitExpression(SourceLocation sourceLocation) throws IllegalArgumentException {
		super(sourceLocation);
	}	
	
}