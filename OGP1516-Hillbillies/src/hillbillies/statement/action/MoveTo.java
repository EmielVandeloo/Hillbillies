package hillbillies.statement.action;

import hillbillies.expression.position.PositionExpression;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.program.Program;
import hillbillies.world.Position;

public class MoveTo extends Action {

	private PositionExpression expression;
	
	public MoveTo(PositionExpression expression, SourceLocation sl) {
		super(sl);
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
				program.getUnit().moveTo(getExpression().evaluate());
			}
		}
	}
	
}
