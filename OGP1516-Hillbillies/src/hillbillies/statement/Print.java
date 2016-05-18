package hillbillies.statement;

import hillbillies.expression.Expression;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.program.Program;

public class Print extends Statement {
	
	private Expression<?> expression;
	
	public Print(Expression<?> value, SourceLocation sl) throws IllegalArgumentException {
		super(sl);
		this.expression = value;
	}

	public Expression<?> getExpression() {
		return this.expression;
	}
	
	public void setExpression(Expression<?> expr) {
		this.expression = expr;
	}
	
	@Override
	public void perform(Program program) {
		if (isToBeExecuted() && !program.hasStopped()) {
			if (program.hasTimeForStatement()) {
				program.decreaseTimerOneUnit();
				System.out.println(getExpression().evaluate(program).toString());
				this.setToBeExecuted(false);
			} else {
				program.setTimeDepleted(true);
			}
		}
	}

	@Override
	public void resetAll() {
		setToBeExecuted(true);
	}
	
}
