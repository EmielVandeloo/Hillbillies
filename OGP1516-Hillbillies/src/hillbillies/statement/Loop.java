package hillbillies.statement;

import hillbillies.expression.bool.BooleanExpression;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.program.Program;

public class Loop extends Statement {

	private BooleanExpression expression;
	private Statement body;
	private boolean performAgain = false;
	
	public Loop(BooleanExpression expression, Statement body, SourceLocation sourceLocation){
		super(sourceLocation);
		this.setExpression(expression);
		this.setBody(body);
	}
	
	
	public BooleanExpression getExpression() {
		return this.expression;
	}
	
	public void setExpression(BooleanExpression expression) {
		this.expression = expression;
	}

	public Statement getBody() {
		return this.body;
	}

	public void setBody(Statement body) {
		this.body = body;
		body.setNestingStatement(this);
	}
	
	// If one time step passed, return if the program has to continue
	// executing the loop in the next time step.
	public boolean getPerformAgain() {
		return this.performAgain;
	}

	public void setPerformAgain(boolean performAgain) {
		this.performAgain = performAgain;
	}
	
	public void perform(Program program){
		if (this.isToBeExecuted() && !program.hasStopped()) {
			if (program.hasTimeForStatement()) {				
				while (((getExpression().evaluate() == true && program.hasTimeForStatement()) && 
						isToBeExecuted() || getPerformAgain() == true) && !program.hasStopped()) {
					if (getPerformAgain() == true) {
						this.setPerformAgain(false);
					} else {
						program.decreaseTimerOneUnit();
						getBody().setToBeExecuted(true);
					}
					getBody().perform(program);
				}
				// Stopped the loop because condition yielded false.
				if (getExpression().evaluate() == false) {
					this.setToBeExecuted(false);
				} 
				// Current time step finished, but condition still true.
				else if (program.isTimeDepleted()) {
					setPerformAgain(true);
				}
				else {
					setPerformAgain(true);
					program.setTimeDepleted(true);
					this.setToBeExecuted(true);
				}
			} else {
				program.setTimeDepleted(true);
			}
		}	
	}

	@Override
	public void setToBeExecuted(boolean toBeExecuted) {
		super.setToBeExecuted(toBeExecuted);
		if (getBody() != null) {
			getBody().setToBeExecuted(toBeExecuted);
		}
	}


}
