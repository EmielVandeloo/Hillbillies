package hillbillies.expression.object;

import hillbillies.expression.Expression;
import hillbillies.part3.programs.SourceLocation;

public abstract class ObjectExpression extends Expression<Object> {

	public ObjectExpression(SourceLocation sourceLocation) throws IllegalArgumentException {
		super(sourceLocation);
	}

}
