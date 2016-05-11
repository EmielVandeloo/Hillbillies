package hillbillies.expression.unit;

import hillbillies.model.Unit;

public class Any extends UnitExpression {

	public Any(Unit unit) throws IllegalArgumentException {
		super(unit);
	}

	@Override
	public Unit returnUnit() {
		// TODO getUnit().getWorld().getRandomUnit();
		return null;
	}

}