package hillbillies.expression.unit;

import hillbillies.expression.Expression;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.program.Program;

public class Friend extends Expression<Unit> {

	public Friend(SourceLocation sourceLocation) throws IllegalArgumentException {
		super(sourceLocation);
	}

	@Override
	public Unit evaluate(Program program) {
		Unit unit = program.getUnit();
		return unit.getFaction().getClosestMember(program.getUnit());
	}
	
	@Override
	public String toString() {
		return "friend";
	}

}