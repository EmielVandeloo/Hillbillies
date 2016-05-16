package hillbillies.expression.bool;

import hillbillies.expression.bool.checker.PositionChecker;
import hillbillies.expression.position.PositionExpression;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.program.Program;

public class IsPassable extends PositionChecker {

	public IsPassable(SourceLocation sourceLocation, PositionExpression positionExpression) 
			throws IllegalArgumentException {
		
		super(sourceLocation, positionExpression);
	}

	@Override
	public Boolean evaluate(Program program) {
		return program.getUnit().getWorld().isPassable(getPositionExpression().evaluate(program));
	}
	
	@Override
	public String toString() {
		return "is passable: " + getPositionExpression().toString();
	}

}
