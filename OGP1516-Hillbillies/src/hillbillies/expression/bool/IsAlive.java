package hillbillies.expression.bool;

import hillbillies.expression.bool.checker.UnitChecker;
import hillbillies.expression.unit.UnitExpression;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.program.Program;

public class IsAlive extends UnitChecker {

	public IsAlive(SourceLocation sourceLocation, UnitExpression unitExpression) 
			throws IllegalArgumentException {
		
		super(sourceLocation, unitExpression);
	}

	@Override
	public Boolean evaluate(Program program) {
		return ! getUnitExpression().evaluate(program).isTerminated();
	}
	
	@Override
	public String toString() {
		return getUnitExpression().toString() + " is alive";
	}

}