package hillbillies.statement.action;

import hillbillies.expression.Expression;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.program.Program;
import hillbillies.statement.Conditional;
import hillbillies.statement.Queue;
import hillbillies.statement.repetitive.Repetitive;

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
				Unit unit = getExpression().evaluate(program);
				try {
					program.getUnit().attack(unit);
					if (!program.getUnit().isAttacking()) {
						program.interrupt();
					}
				} catch (IllegalArgumentException e) {
					program.interrupt();
				}
				setToBeExecuted(false);
			} else {
				program.setTimeDepleted(true);
			}
		}
		if (!program.getUnit().isAttacking()) {
			if (isPartOfQueue()) {
				((Queue) getQueueStatement()).setIndex(((Queue) getQueueStatement()).getIndex()+1);
			} else {
				if (getNestingStatement() instanceof Repetitive) {
					getNestingStatement().resetAll();
				} else if (getNestingStatement() instanceof Conditional) {
					getNestingStatement().setToBeExecuted(false);
				}
			}
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
