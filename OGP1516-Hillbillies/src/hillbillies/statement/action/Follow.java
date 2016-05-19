package hillbillies.statement.action;

import hillbillies.expression.Expression;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.program.Program;
import hillbillies.world.Position;

public class Follow extends Action {

	private Expression<Unit> expression;
	
	public Follow(Expression<Unit> expression, SourceLocation sl) {
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
				while (!getExpression().evaluate(program).isTerminated() && !Position.isAdjacentToOrSame(
						program.getUnit().getPosition(), getExpression().evaluate(program).getPosition()));
			}
			setToBeExecuted(false);
		}
	}
	
	@Override
	public String toString() {
		return "Follow " + getExpression().toString();
	}

	@Override
	public void resetAll() {
		setToBeExecuted(true);
	}

}
