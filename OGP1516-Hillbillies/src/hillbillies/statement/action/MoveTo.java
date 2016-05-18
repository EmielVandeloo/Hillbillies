package hillbillies.statement.action;

import hillbillies.expression.position.PositionExpression;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.program.Program;
import hillbillies.statement.Queue;
import hillbillies.world.Position;

public class MoveTo extends Action {

	private PositionExpression expression;
	private Position targetCube;
	private boolean toBeExecuted = true;
	
	public MoveTo(PositionExpression expression, SourceLocation sl) {
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
				this.targetCube = getExpression().evaluate(program);
				if (targetCube == null) {
					program.interrupt();
				}
				program.decreaseTimerOneUnit();
				System.out.println("Starting to move!");
				program.getUnit().moveTo(targetCube);
				setToBeExecuted(false);	
			}
		}
		if (program.getUnit().getPosition().equals(targetCube.getCenterPosition())) {
			System.out.println("Finished moving!");
			if (isPartOfQueue()) {
				((Queue) getQueueStatement()).setIndex(((Queue) getQueueStatement()).getIndex()+1);
			} else {
				try {
					getNestingStatement().setToBeExecuted(false);
				} catch (NullPointerException e) {}
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