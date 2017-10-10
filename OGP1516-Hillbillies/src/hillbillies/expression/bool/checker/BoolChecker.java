package hillbillies.expression.bool.checker;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.expression.Expression;
import hillbillies.part3.programs.SourceLocation;

public abstract class BoolChecker extends Expression<Boolean> {
	
	private Expression<Boolean> expression;

	public BoolChecker(SourceLocation sourceLocation, Expression<Boolean> expression) 
			throws IllegalArgumentException {
		super(sourceLocation);
		this.setExpression(expression);
	}

	@Basic @Raw
	public Expression<Boolean> getExpression() {
		return this.expression;
	}

	public static boolean isValidExpression(Expression<Boolean> expression) {
		return (expression != null);
	}

	@Raw
	public void setExpression(Expression<Boolean> expression) throws IllegalArgumentException {
		if (! isValidExpression(expression))
			throw new IllegalArgumentException();
		this.expression = expression;
	}
}