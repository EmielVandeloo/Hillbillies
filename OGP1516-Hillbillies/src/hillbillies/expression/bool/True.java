package hillbillies.expression.bool;

import hillbillies.model.Unit;

public class True extends BooleanExpression {
	
	// CONSTRUCTOR

	public True(Unit unit) throws IllegalArgumentException {
		super(unit);
	}
	
	
	// OVERRIDE

	@Override
	public boolean getResult() {
		return true;
	}

}