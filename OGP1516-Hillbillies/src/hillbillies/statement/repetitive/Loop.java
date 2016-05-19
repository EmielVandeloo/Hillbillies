package hillbillies.statement.repetitive;

import hillbillies.expression.Expression;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.program.Program;
import hillbillies.statement.Queue;
import hillbillies.statement.Statement;

public class Loop extends Repetitive {

	private Expression<Boolean> expression;
	private Statement body;
	private boolean toBeExecuted = true;
	private boolean performAgain = false;
	
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
					setPerformAgain(true);
					getBody().setNestingStatement(this);
					getBody().perform(program);
				} else if (performAgain()) {
					getBody().setNestingStatement(this);
					getBody().perform(program);
					if (!program.getUnit().cannotStartAction()) {
						setPerformAgain(false);
					}
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
//		if (isPartOfQueue() && toBeExecuted == false) {
//			((Queue) getQueueStatement()).setIndex(((Queue) getQueueStatement()).getIndex()+1);
//		}
		if (getBody() != null) {
			getBody().setToBeExecuted(toBeExecuted);
		}
	}
	
	public boolean performAgain() {
		return this.performAgain;
	}
	
	public void setPerformAgain(boolean performAgain) {
		this.performAgain = performAgain;
	}

	@Override
	public void resetAll() {
		setPerformAgain(false);
		setToBeExecuted(true);
		getBody().resetAll();
	}

}
