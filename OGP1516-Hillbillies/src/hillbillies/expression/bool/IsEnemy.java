package hillbillies.expression.bool;

import hillbillies.expression.Expression;
import hillbillies.expression.bool.checker.UnitChecker;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.program.Program;

public class IsEnemy extends UnitChecker {

	public IsEnemy(SourceLocation sourceLocation, Expression<Unit> unitExpression) 
			throws IllegalArgumentException {	
		super(sourceLocation, unitExpression);
	}

	@Override
	public Boolean evaluate(Program program) {
		return ! program.getUnit().getFaction()
				.equals(getUnitExpression().evaluate(program).getFaction());
	}
	
	@Override
	public String toString() {
		return getUnitExpression().toString() + " is enemy";
	}
}