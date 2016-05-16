package hillbillies.expression.position;

import hillbillies.expression.position.checker.PositionChecker;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.program.Program;
import hillbillies.world.Position;

public class NextTo extends PositionChecker {

	public NextTo(SourceLocation sourceLocation, PositionExpression positionExpression) 
			throws IllegalArgumentException {
		
		super(sourceLocation, positionExpression);
	}

	@Override
	public Position evaluate(Program program) {
		return program.getWorld().getRandomNeighbouringPosition(getPositionExpression().evaluate(program));
	}

	@Override
	public String toString() {
		return "next to";
	}

}
