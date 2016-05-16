package hillbillies.expression.position;

import hillbillies.expression.Expression;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.world.Position;

public abstract class PositionExpression extends Expression<Position> {

	public PositionExpression(SourceLocation sourceLocation) throws IllegalArgumentException {
		super(sourceLocation);
	}
	
}