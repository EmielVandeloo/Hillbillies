package hillbillies.expression.unit.checker;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.expression.Expression;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;

public abstract class UnitChecker extends Expression<Unit> {

	private Expression<Unit> expression;

	public UnitChecker(SourceLocation sourceLocation, Expression<Unit> expression) 
			throws IllegalArgumentException {	
		super(sourceLocation);
		setExpression(expression);
	}

	@Basic @Raw
	public Expression<Unit> getExpression() {
		return this.expression;
	}

	public static boolean isValidExpression(Expression<Unit> expression) {
		return (expression != null);
	}

	@Raw
	public void setExpression(Expression<Unit> expression) throws IllegalArgumentException {
		if (! isValidExpression(expression))
			throw new IllegalArgumentException();
		this.expression = expression;
	}
}