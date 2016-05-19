package hillbillies.expression.position;

import hillbillies.expression.Expression;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.program.Program;
import hillbillies.world.Position;

public class Selected extends Expression<Position> {

	public Selected(SourceLocation sourceLocation) throws IllegalArgumentException {
		super(sourceLocation);
	}

	@Override
	public Position evaluate(Program program) {
		return program.getUnit().getTask().getSelectedPosition().getCenterPosition();
	}

	@Override
	public String toString() {
		return "selected";
	}

}
