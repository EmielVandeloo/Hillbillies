package hillbillies.expression.bool;

import hillbillies.expression.bool.checker.UnitChecker;
import hillbillies.expression.unit.UnitExpression;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.program.Program;

public class IsFriend extends UnitChecker {

	// CONSTRUCTOR

	public IsFriend(SourceLocation sourceLocation, UnitExpression unitExpression) 
			throws IllegalArgumentException {
		
		super(sourceLocation, unitExpression);
	}


	// OVERRIDE

	@Override
	public Boolean evaluate(Program program) {
		return program.getUnit().getFaction()
				.equals(getUnitExpression().evaluate(program).getFaction());
	}
	
	@Override
	public String toString() {
		return getUnitExpression().toString() + " is friend";
	}

}