package hillbillies.statement;

import hillbillies.expression.bool.BooleanExpression;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.program.Program;

public class Conditional extends Statement {

	private BooleanExpression expression;
	private Statement ifStatement;
	private Statement elseStatement;
	private boolean toBeExecuted = true;
	
	public Conditional(BooleanExpression expression, Statement ifStatement, Statement elseStatement, SourceLocation sl) 
			throws IllegalArgumentException {
		super(sl);
		setExpression(expression);
		if (ifStatement != null) {
			setIfStatement(ifStatement);
		} else {
			setIfStatement(new Void(sl));
		}
		if (elseStatement != null) {
			setElseStatement(elseStatement);
		} else {
			setElseStatement(new Void(sl));
		}
	}
	
	public Conditional(BooleanExpression expression, Statement ifStatement, SourceLocation sl) 
			throws IllegalArgumentException {
		this(expression, ifStatement, new Void(sl), sl);
	}

	public BooleanExpression getExpression() {
		return this.expression;
	}
	
	public void setExpression(BooleanExpression expression) {
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
					getExpression().setHasEvaluatedToTrue(true);
					getIfStatement().setNestingStatement(this);
					getIfStatement().perform(program);
					getElseStatement().setToBeExecuted(false);
				} else if (getExpression().HasEvaluatedToTrue()) {
					getIfStatement().setNestingStatement(this);
					getIfStatement().perform(program);
					getElseStatement().setToBeExecuted(false);
					if (!program.getUnit().cannotStartAction()) {
						getExpression().setHasEvaluatedToTrue(false);
					}
				} else {
					getElseStatement().setNestingStatement(this);
					getElseStatement().perform(program);
					getIfStatement().setToBeExecuted(false);
				}
			} else {
				program.setTimeDepleted(true);
			}
		}
	}

	@Override
	public void setToBeExecuted(boolean toBeExecuted) {
		this.toBeExecuted = toBeExecuted;
		getExpression().setHasEvaluatedToTrue(false);
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
		getIfStatement().resetAll();
		getElseStatement().resetAll();
	}

}
