package hillbillies.statement;

import hillbillies.expression.Expression;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.program.Program;

public class Loop extends Statement {

	private Expression<Boolean> expression;
	private Statement body;
	private boolean toBeExecuted = true;
	
	public Loop(Expression<Boolean> expression, Statement body, SourceLocation sl){
		super(sl);
		setExpression(expression);
		setBody(body);
	}
	
	
	public Expression<Boolean> getExpression() {
		return this.expression;
	}
	
	public void setExpression(Expression<Boolean> expression) {
		this.expression = expression;
	}

	public Statement getBody() {
		return this.body;
	}

	public void setBody(Statement body) {
		this.body = body;
		body.setNestingStatement(this);
	}
	
	public void perform(Program program){
		if (isToBeExecuted() && !program.hasStopped()) {
			if (program.hasTimeForStatement()) {				
				program.decreaseTimerOneUnit();
				if (getExpression().evaluate(program) == true) {
					getBody().resetAll();
					getBody().setNestingStatement(this);
					getBody().perform(program);
				} else {
					setToBeExecuted(false);
					if (isPartOfQueue()) {
						((Queue) getQueueStatement()).setIndex(((Queue) getQueueStatement()).getIndex()+1);
					}
				}
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
		if (getBody() != null) {
			getBody().setToBeExecuted(toBeExecuted);
		}
	}


	@Override
	public void resetAll() {
		setToBeExecuted(true);
		getBody().resetAll();
	}

}
