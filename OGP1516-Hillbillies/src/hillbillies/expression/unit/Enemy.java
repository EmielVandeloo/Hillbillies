package hillbillies.expression.unit;

import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.program.Program;

public class Enemy extends UnitExpression {

	public Enemy(SourceLocation sourceLocation) throws IllegalArgumentException {
		super(sourceLocation);
	}

	@Override
	public Unit evaluate(Program program) {
		return program.getUnit().getFaction().getClosestEnemy(program.getUnit());
	}
	
	@Override
	public String toString() {
		return "enemy";
	}

}