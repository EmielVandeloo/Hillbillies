package hillbillies.statement.action;

import hillbillies.expression.position.PositionExpression;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.program.Program;

public class Work extends Action {

	private PositionExpression expression;
	
	public Work(PositionExpression expression, SourceLocation sl) {
		super(sl);
		setExpression(expression);
	}
	
	public PositionExpression getExpression() {
		return this.expression;
	}
	
	public void setExpression(PositionExpression expression) {
		this.expression = expression;
	}

	@Override
	public void perform(Program program) {
		if (isToBeExecuted() && !program.hasStopped()) {
			if (program.hasTimeForStatement()) {
				program.decreaseTimerOneUnit();
				program.getUnit().workAt((int) getExpression().evaluate(program).x(), (int) getExpression().
						evaluate(program).y(), (int) getExpression().evaluate(program).z());
			}
			setToBeExecuted(false);
		}
	}

}
