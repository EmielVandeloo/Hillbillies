package hillbillies.expression.bool;

import hillbillies.expression.Expression;
import hillbillies.expression.bool.checker.PositionChecker;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.program.Program;
import hillbillies.world.Position;

public class IsSolid extends PositionChecker {

	public IsSolid(SourceLocation sourceLocation, Expression<Position> positionExpression) 
			throws IllegalArgumentException {
		
		super(sourceLocation, positionExpression);
	}

	@Override
	public Boolean evaluate(Program program) {
		return ! program.getUnit().getWorld().isPassable(getPositionExpression().evaluate(program));
	}
	
	@Override
	public String toString() {
		return "is solid: " + getPositionExpression().toString();
	}
	
}