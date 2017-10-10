package hillbillies.expression.bool.checker;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.expression.Expression;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;

public abstract class UnitChecker extends Expression<Boolean> {

	private Expression<Unit> unitExpression;

	public UnitChecker(SourceLocation sourceLocation, Expression<Unit> unitExpression) 
			throws IllegalArgumentException {	
		super(sourceLocation);
		this.setUnitExpression(unitExpression);
	}

	@Basic @Raw
	public Expression<Unit> getUnitExpression() {
		return this.unitExpression;
	}

	public static boolean isValidUnitExpression(Expression<Unit> unitExpression) {
		return (unitExpression != null);
	}

	@Raw
	public void setUnitExpression(Expression<Unit> unitExpression) throws IllegalArgumentException {
		if (! isValidUnitExpression(unitExpression))
			throw new IllegalArgumentException();
		this.unitExpression = unitExpression;
	}
}