package hillbillies.expression.unit;

import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.program.Program;

public class Any extends UnitExpression {

	public Any(SourceLocation sourceLocation) throws IllegalArgumentException {
		super(sourceLocation);
	}

	@Override
	public Unit evaluate(Program program) {		
		return program.getWorld().getRandomUnit(program.getUnit());
	}
	
	@Override
	public String toString() {
		return "any";
	}

}