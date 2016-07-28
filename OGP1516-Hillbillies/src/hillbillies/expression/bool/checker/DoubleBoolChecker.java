package hillbillies.expression.bool.checker;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.expression.Expression;
import hillbillies.part3.programs.SourceLocation;

public abstract class DoubleBoolChecker extends Expression<Boolean> {

	private Expression<Boolean> first;
	private Expression<Boolean> second;

	public DoubleBoolChecker(SourceLocation sourceLocation, 
			Expression<Boolean> first, Expression<Boolean> second) throws IllegalArgumentException {		
		super(sourceLocation);
		this.setFirst(first);
		this.setSecond(second);
	}

	@Basic @Raw
	public Expression<Boolean> getFirst() {
		return this.first;
	}

	public static boolean isValidFirst(Expression<Boolean> first) {
		return (first != null);
	}

	@Raw
	public void setFirst(Expression<Boolean> first) throws IllegalArgumentException {
		if (! isValidFirst(first))
			throw new IllegalArgumentException();
		this.first = first;
	}

	@Basic @Raw
	public Expression<Boolean> getSecond() {
		return this.second;
	}

	public static boolean isValidSecond(Expression<Boolean> second) {
		return (second != null);
	}

	@Raw
	public void setSecond(Expression<Boolean> second) throws IllegalArgumentException {
		if (! isValidSecond(second))
			throw new IllegalArgumentException();
		this.second = second;
	}
}