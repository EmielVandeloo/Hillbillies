package hillbillies.expression.position;

import hillbillies.part3.programs.SourceLocation;
import hillbillies.program.Program;
import hillbillies.world.Position;

public class Here extends PositionExpression {

	public Here(SourceLocation sourceLocation) throws IllegalArgumentException {
		super(sourceLocation);
	}

	@Override
	public Position evaluate(Program program) {
		return program.getUnit().getPosition();
	}
	
	@Override
	public String toString() {
		return "here";
	}
	
}
