package hillbillies.statement.action;

import hillbillies.expression.unit.UnitExpression;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.program.Program;
import hillbillies.world.Position;

public class Follow extends Action {

	private UnitExpression expression;
	
	public Follow(UnitExpression expression, SourceLocation sl) {
		super(sl);
		setExpression(expression);
	}

	public UnitExpression getExpression() {
		return this.expression;
	}
	
	public void setExpression(UnitExpression expression) {
		this.expression = expression;
	}
	
	@Override
	public void perform(Program program) {
		if (isToBeExecuted() && !program.hasStopped()) {
			if (program.hasTimeForStatement()) {
				program.decreaseTimerOneUnit();
				while (!getExpression().evaluate().isTerminated() && !Position.isAdjacentToOrSame(
						program.getUnit().getPosition(), getExpression().evaluate().getPosition()));
			}
		}
	}

}
