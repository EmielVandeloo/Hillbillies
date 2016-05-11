package hillbillies.expression.unit;

import hillbillies.model.Unit;

public class Friend extends UnitExpression {

	public Friend(Unit unit) throws IllegalArgumentException {
		super(unit);
	}

	@Override
	public Unit returnUnit() {
		// TODO getUnit().getFaction().getRandomFriend();
		return null;
	}

}