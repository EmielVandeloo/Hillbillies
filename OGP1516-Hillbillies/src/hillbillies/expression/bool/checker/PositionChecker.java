package hillbillies.expression.bool.checker;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.expression.Expression;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.world.Position;

public abstract class PositionChecker extends Expression<Boolean> {

	private Expression<Position> positionExpression;

	public PositionChecker(SourceLocation sourceLocation, Expression<Position> positionExpression) 
			throws IllegalArgumentException {	
		super(sourceLocation);
		this.setPositionExpression(positionExpression);
	}

	@Basic @Raw
	public Expression<Position> getPositionExpression() {
		return this.positionExpression;
	}

	public static boolean isValidPositionExpression(Expression<Position> positionExpression) {
		return (positionExpression != null);
	}

	@Raw
	public void setPositionExpression(Expression<Position> positionExpression) 
			throws IllegalArgumentException {		
		if (! isValidPositionExpression(positionExpression))
			throw new IllegalArgumentException();
		this.positionExpression = positionExpression;
	}
}