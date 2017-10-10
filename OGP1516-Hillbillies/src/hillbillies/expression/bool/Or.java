package hillbillies.expression.bool;

import hillbillies.expression.Expression;
import hillbillies.expression.bool.checker.DoubleBoolChecker;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.program.Program;

public class Or extends DoubleBoolChecker {

	public Or(SourceLocation sourceLocation, Expression<Boolean> first, Expression<Boolean> second) 
			throws IllegalArgumentException {	
		super(sourceLocation, first, second);
	}

	@Override
	public Boolean evaluate(Program program) {
		return getFirst().evaluate(program) || getSecond().evaluate(program);
	}
}