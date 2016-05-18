package hillbillies.expression.bool;

import hillbillies.expression.Expression;
import hillbillies.part3.programs.SourceLocation;

public abstract class BooleanExpression extends Expression<Boolean> {

	private boolean hasEvaluatedToTrue = false;
	
	public BooleanExpression(SourceLocation sourceLocation) throws IllegalArgumentException {
		super(sourceLocation);
	}
	
	public boolean HasEvaluatedToTrue() {
		return this.hasEvaluatedToTrue;
	}
	
	public void setHasEvaluatedToTrue(boolean hasEvaluatedToTrue) {
		this.hasEvaluatedToTrue = hasEvaluatedToTrue;
	}

}