package hillbillies.statement.action;

import hillbillies.expression.Expression;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.program.Program;

public class Attack extends Action {

	private Expression<Unit> expression;
	
	public Attack(Expression<Unit> expression, SourceLocation sl) {
		super(sl);
		setExpression(expression);
	}
	
	public Expression<Unit> getExpression() {
		return this.expression;
	}
	
	public void setExpression(Expression<Unit> expression) {
		this.expression = expression;
	}

	@Override
	public void perform(Program program) {
		if (isToBeExecuted() && !program.hasStopped()) {
			if (program.hasTimeForStatement()) {
				program.decreaseTimerOneUnit();
				program.getUnit().attack(getExpression().evaluate(program));
			}
			setToBeExecuted(false);
		}
	}

	@Override
	public String toString() {
		return "Attack " + getExpression().toString();
	}

	@Override
	public void resetAll() {
		setToBeExecuted(true);
	}

}
