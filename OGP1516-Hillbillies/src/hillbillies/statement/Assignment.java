package hillbillies.statement;

import hillbillies.expression.Expression;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.program.Program;

public class Assignment extends Statement {

	private final String name;
	private final Expression<?> expression;
	
	public Assignment(String name, Expression<?> expression, SourceLocation sl) {
		super(sl);
		this.name = name;
		this.expression = expression;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Expression<?> getExpression() {
		return this.expression;
	}
	
	@Override
	public void perform(Program program) {
		if (isToBeExecuted() && !program.hasStopped()) {
			if (program.hasTimeForStatement()) {
				Object valueToSet;
				try {
					valueToSet = getExpression().evaluate(program);
				} catch (NullPointerException exc){
					program.stop();
					return;
				}
				program.decreaseTimerOneUnit();
				program.putGlobalVariable(getName(), valueToSet);
				setToBeExecuted(false);
			} else {
				program.setTimeDepleted(true);
			}
		}
	}

}
