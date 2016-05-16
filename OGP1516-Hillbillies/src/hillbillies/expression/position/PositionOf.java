package hillbillies.expression.position;

import hillbillies.expression.position.checker.UnitChecker;
import hillbillies.expression.unit.UnitExpression;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.program.Program;
import hillbillies.world.Position;

public class PositionOf extends UnitChecker {

	public PositionOf(SourceLocation sourceLocation, UnitExpression unitExpression) 
			throws IllegalArgumentException {
		
		super(sourceLocation, unitExpression);
	}
	
	@Override
	public Position evaluate(Program program) {
		return getUnitExpression().evaluate(program).getPosition();
	}

	@Override
	public String toString() {
		return "PositionOf []";
	}

}
