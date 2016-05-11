package hillbillies.expression.unit;

import hillbillies.model.Unit;

public class This extends UnitExpression {

	// CONSTRUCTOR
	
	public This(Unit unit) throws IllegalArgumentException {
		super(unit);
	}
	

	// OVERRIDE
	
	@Override
	public Unit returnUnit() {
		return getUnit();
	}

}