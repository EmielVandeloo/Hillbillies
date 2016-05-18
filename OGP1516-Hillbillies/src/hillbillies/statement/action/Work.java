package hillbillies.statement.action;

import hillbillies.expression.position.PositionExpression;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.program.Program;
import hillbillies.statement.Queue;
import hillbillies.world.Position;

public class Work extends Action {

	private PositionExpression expression;
	private boolean toBeExecuted = true;
	
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
		System.out.println("Work");
		if (isToBeExecuted() && !program.hasStopped()) {
			if (program.hasTimeForStatement()) {
				program.decreaseTimerOneUnit();
				Position pos = getExpression().evaluate(program);
				System.out.println("Starting to work!");
				program.getUnit().workAt((int) pos.x(), (int) pos.y(), (int) pos.z());
				setToBeExecuted(false);
			}
		}
		if (!program.getUnit().isWorking()) {		
			System.out.println("Finished working!");
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