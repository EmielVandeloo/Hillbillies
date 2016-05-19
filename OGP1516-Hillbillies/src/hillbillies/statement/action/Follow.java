package hillbillies.statement.action;

import hillbillies.expression.Expression;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.program.Program;
import hillbillies.statement.Conditional;
import hillbillies.statement.Queue;
import hillbillies.statement.repetitive.Repetitive;
import hillbillies.world.Position;

public class Follow extends Action {

	private Expression<Unit> expression;
	private Unit unitToFollow = null;
	Position positionToMoveTo = null;
	
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
				if (unitToFollow == null) {
					unitToFollow = getExpression().evaluate(program);
				}
				positionToMoveTo = unitToFollow.getPosition().getCenterPosition();
				System.out.println("Starting to follow!");
				program.getUnit().moveTo(positionToMoveTo);
				setToBeExecuted(false);
			}
		}
		if (program.getUnit().getPosition().equals(positionToMoveTo)) {
			System.out.println("Checking if enemy is here...");
			if (program.getUnit().getPosition().equals(unitToFollow.getPosition())) {
				System.out.println("Yes! Finished following!");
				if (isPartOfQueue()) {
					((Queue) getQueueStatement()).setIndex(((Queue) getQueueStatement()).getIndex()+1);
				} else {
					if (getNestingStatement() instanceof Repetitive) {
						getNestingStatement().resetAll();
					} else if (getNestingStatement() instanceof Conditional) {
						getNestingStatement().setToBeExecuted(false);
					}
				}
			} else {
				System.out.println("Unit not here, follow him!");
				setToBeExecuted(true);
				if (isPartOfQueue()) {
					// Do nothing
				} else {
					if (getNestingStatement() instanceof Repetitive) {
						setToBeExecuted(true);
						getNestingStatement().resetAll();
					} else if (getNestingStatement() instanceof Conditional) {
						getNestingStatement().setToBeExecuted(true);
					}
				}
			}
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
