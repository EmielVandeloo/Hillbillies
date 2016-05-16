package hillbillies.expression.position;

import hillbillies.part3.programs.SourceLocation;
import hillbillies.program.Program;
import hillbillies.world.Position;

public class Workshop extends PositionExpression {

	public Workshop(SourceLocation sourceLocation) throws IllegalArgumentException {
		super(sourceLocation);
	}

	@Override
	public Position evaluate(Program program) {
		return program.getWorld().getClosestWorkshop(program.getUnit().getPosition());
	}
	
	@Override
	public String toString() {
		return "workshop";
	}

}