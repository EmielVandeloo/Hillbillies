package hillbillies.expression.unit;

import hillbillies.expression.Expression;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.program.Program;

public class This extends Expression<Unit> {

	public This(SourceLocation sourceLocation) throws IllegalArgumentException {
		super(sourceLocation);
	}

	@Override
	public Unit evaluate(Program program) {
		return program.getUnit();
	}
	
	@Override
	public String toString() {
		return "this";
	}

}