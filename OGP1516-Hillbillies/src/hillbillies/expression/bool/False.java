package hillbillies.expression.bool;

import hillbillies.model.Unit;

public class False extends BooleanExpression {
	
	// CONSTRUCTOR

	public False(Unit unit) throws IllegalArgumentException {
		super(unit);
	}
	
	
	// OVERRIDE

	@Override
	public boolean getResult() {
		return false;
	}

}
