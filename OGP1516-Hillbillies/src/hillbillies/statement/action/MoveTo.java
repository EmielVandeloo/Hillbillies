package hillbillies.statement.action;

import hillbillies.expression.Expression;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.program.Program;
import hillbillies.statement.Conditional;
import hillbillies.statement.Queue;
import hillbillies.statement.repetitive.Repetitive;
import hillbillies.world.Position;

public class MoveTo extends Action {

	private Expression<Position> expression;
	private Position targetCube = null;
	private boolean toBeExecuted = true;
	
	public MoveTo(Expression<Position> expression, SourceLocation sl) {
		super(sl);
		setExpression(expression);
	}

	public Expression<Position> getExpression() {
	    return this.expression;
	}
	
	public void setExpression(Expression<Position> expression) {
		this.expression = expression;
	}
	
	@Override
	public void perform(Program program) {
		if (isToBeExecuted() && !program.hasStopped()) {
			if (program.hasTimeForStatement()) {
				program.decreaseTimerOneUnit();
				this.targetCube = getExpression().evaluate(program);
				try {
					program.getUnit().moveTo(targetCube);
					if (!program.getUnit().isMoving()) {
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
		if (program.getUnit().getPosition().equals(targetCube.getCenterPosition())) {
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
		return "Move to " + getExpression().toString();
	}
	
	@Override
	public boolean isToBeExecuted() {
		return this.toBeExecuted;
	}
	
	@Override
	public void setToBeExecuted(boolean toBeExecuted) {
		this.toBeExecuted = toBeExecuted;
	}

	@Override
	public void resetAll() {
		setToBeExecuted(true);
	}
	
}