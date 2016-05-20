package hillbillies.expression.bool;

import hillbillies.expression.Expression;
import hillbillies.expression.bool.checker.BoolChecker;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.program.Program;

public class Invert extends BoolChecker {

	public Invert(SourceLocation sourceLocation, Expression<Boolean> expression) 
			throws IllegalArgumentException {
		
		super(sourceLocation, expression);
	}

	@Override
	public Boolean evaluate(Program program) {
		return ! getExpression().evaluate(program);
	}
	
	@Override
	public String toString() {
		return "(not " + getExpression().toString() + ")";
	}

}
