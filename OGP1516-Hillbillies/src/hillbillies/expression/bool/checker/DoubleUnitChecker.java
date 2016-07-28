package hillbillies.expression.bool.checker;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.expression.Expression;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;

public abstract class DoubleUnitChecker extends Expression<Boolean> {

	private Expression<Unit> first;
	private Expression<Unit> second;

	public DoubleUnitChecker(SourceLocation sourceLocation, Expression<Unit> first, Expression<Unit> second) 
			throws IllegalArgumentException {	
		super(sourceLocation);
		this.setFirst(first);
		this.setSecond(second);
	}

	@Basic @Raw
	public Expression<Unit> getFirst() {
		return this.first;
	}

	public static boolean isValidFirst(Expression<Unit> first) {
		return (first != null);
	}

	@Raw
	public void setFirst(Expression<Unit> first) throws IllegalArgumentException {
		if (! isValidFirst(first))
			throw new IllegalArgumentException();
		this.first = first;
	}

	@Basic @Raw
	public Expression<Unit> getSecond() {
		return this.second;
	}

	public static boolean isValidSecond(Expression<Unit> second) {
		return (second != null);
	}

	@Raw
	public void setSecond(Expression<Unit> second) throws IllegalArgumentException {
		if (! isValidSecond(second))
			throw new IllegalArgumentException();
		this.second = second;
	}
}