package hillbillies.expression.position;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hillbillies.expression.Expression;
import hillbillies.expression.position.checker.PositionChecker;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.program.Program;
import hillbillies.world.Position;

public class NextTo extends PositionChecker {

	public NextTo(SourceLocation sourceLocation, Expression<Position> positionExpression) 
			throws IllegalArgumentException {
		
		super(sourceLocation, positionExpression);
	}

	@Override
	public Position evaluate(Program program) {
		List<Position> allNeighbours = new ArrayList<>();
		for (Position position : program.getWorld().getAllNeighbours(getPositionExpression().evaluate(program))) {
			if (program.getUnit().canStandOn(position)) {
				allNeighbours.add(position);
			}
		}
		if (!allNeighbours.isEmpty()) {
			return allNeighbours.get(new Random().nextInt(allNeighbours.size()));
		}
		return null;
	}

	@Override
	public String toString() {
		return "next to";
	}

}
