package hillbillies.statement;

import hillbillies.expression.Expression;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.program.Program;

public class Conditional extends Statement {

	private Expression<Boolean> expression;
	private Statement ifStatement;
	private Statement elseStatement;
	private boolean toBeExecuted = true;
	private boolean performAgain = false;
	
	public Conditional(Expression<Boolean> expression, Statement ifStatement, Statement elseStatement, SourceLocation sl) 
			throws IllegalArgumentException {
		super(sl);
		setExpression(expression);
		if (ifStatement == null) {
			setIfStatement(new Void(sl));
		} else {
			setIfStatement(ifStatement);
		}
		if (elseStatement == null) {
			setElseStatement(new Void(sl));
		} else {
			setElseStatement(elseStatement);
		}
	}
	
	public Conditional(Expression<Boolean> expression, Statement ifStatement, SourceLocation sl) 
			throws IllegalArgumentException {
		this(expression, ifStatement, new Void(sl), sl);
	}

	public Expression<Boolean> getExpression() {
		return this.expression;
	}
	
	public void setExpression(Expression<Boolean> expression) {
		this.expression = expression;
	}

	public Statement getIfStatement() {
		return this.ifStatement;
	}
	
	public void setIfStatement(Statement ifStatement) {
		this.ifStatement = ifStatement;
		ifStatement.setNestingStatement(this);
	}
	
	public Statement getElseStatement() {
		return this.elseStatement;
	}
	
	public void setElseStatement(Statement elseStatement) {
		this.elseStatement = elseStatement;
		elseStatement.setNestingStatement(this);
	}

	@Override
	public void perform(Program program) {
		if (isToBeExecuted() && !program.hasStopped()) {
			if (program.hasTimeForStatement()) {
				program.decreaseTimerOneUnit();
				if (getExpression().evaluate(program) == true) {
					setPerformAgain(true);
					getIfStatement().perform(program);
					getElseStatement().setToBeExecuted(false);
				} else if (performAgain()) {
					getIfStatement().perform(program);
					getElseStatement().setToBeExecuted(false);		
					if (!program.getUnit().cannotStartAction()) {
						setPerformAgain(false);
					}
				} else {
					getElseStatement().perform(program);
					getIfStatement().setToBeExecuted(false);
				}
			} else {
				program.setTimeDepleted(true);
			}
		}
	}
	
	public boolean performAgain() {
		return this.performAgain;
	}
	
	public void setPerformAgain(boolean performAgain) {
		this.performAgain = performAgain;
	}

	@Override
	public void setToBeExecuted(boolean toBeExecuted) {
		this.toBeExecuted = toBeExecuted;
		setPerformAgain(false);
		if (isPartOfQueue() && toBeExecuted == false) {
			((Queue) getQueueStatement()).setIndex(((Queue) getQueueStatement()).getIndex()+1);
		}
		getIfStatement().setToBeExecuted(toBeExecuted);
		getElseStatement().setToBeExecuted(toBeExecuted);
	}
	
	@Override
	public boolean isToBeExecuted() {
		return this.toBeExecuted;
	}
	
	@Override
	public void resetAll() {
		setToBeExecuted(true);
		setPerformAgain(false);
		getIfStatement().resetAll();
		getElseStatement().resetAll();
	}
	
	@Override
	public boolean isWellFormed() {
		if (!getExpression().isWellFormed()) {
			return false;
		}
		if (!getIfStatement().isWellFormed()) {
			return false;
		}
		if (!getElseStatement().isWellFormed()) {
			return false;
		}
		return true;
	}

}
