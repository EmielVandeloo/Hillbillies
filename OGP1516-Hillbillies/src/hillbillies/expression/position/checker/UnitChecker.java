package hillbillies.expression.position.checker;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.expression.Expression;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.world.Position;

public abstract class UnitChecker extends Expression<Position> {

	private Expression<Unit> unitExpression;	

	public UnitChecker(SourceLocation sourceLocation, Expression<Unit> unitExpression) 
			throws IllegalArgumentException {		
		super(sourceLocation);
		setUnitExpression(unitExpression);
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