package hillbillies.expression.bool;

import hillbillies.part3.programs.SourceLocation;
import hillbillies.program.Program;

public class True extends BooleanExpression {

	public True(SourceLocation sourceLocation) throws IllegalArgumentException {
		super(sourceLocation);
	}

	@Override
	public Boolean evaluate(Program program) {
		return true;
	}

}