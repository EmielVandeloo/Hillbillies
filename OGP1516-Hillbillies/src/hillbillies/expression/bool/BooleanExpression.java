package hillbillies.expression.bool;


import hillbillies.expression.Expression;
import hillbillies.part3.programs.SourceLocation;

public abstract class BooleanExpression extends Expression<Boolean> {
	
	public BooleanExpression(SourceLocation sl) throws IllegalArgumentException {
		super(sl);
	}

	public boolean getResult() {
		// TODO Auto-generated method stub
		return false;
	}

}