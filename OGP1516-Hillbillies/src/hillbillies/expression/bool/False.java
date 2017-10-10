package hillbillies.expression.bool;

import hillbillies.expression.Expression;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.program.Program;

public class False extends Expression<Boolean> {

	public False(SourceLocation sourceLocation) throws IllegalArgumentException {
		super(sourceLocation);
	}

	@Override
	public Boolean evaluate(Program program) {
		return false;
	}
	
	@Override
	public String toString() {
		return "false";
	}
}