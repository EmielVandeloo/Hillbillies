package hillbillies.statement;

import hillbillies.expression.bool.BooleanExpression;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.program.Program;

public class Conditional extends Statement {

	private BooleanExpression expression;
	private Statement ifStatement;
	private Statement elseStatement;
	
	public Conditional(BooleanExpression expression, Statement ifStatement, Statement elseStatement, SourceLocation sl) 
			throws IllegalArgumentException {
		super(sl);
		setExpression(expression);
		setIfStatement(ifStatement);
		setElseStatement(elseStatement);
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
	public void perform(Program program){
		if (isToBeExecuted() && !program.hasStopped()) {
			if (program.hasTimeForStatement()) {
				program.decreaseTimerOneUnit();
				if (getExpression().evaluate() == true) {
					getIfStatement().perform(program);
				} else if (getElseStatement() != null) {
					getElseStatement().perform(program);
				}
				setToBeExecuted(false);
			} else {
				program.setTimeDepleted(true);
			}
		}
	}

	@Override
	public void setToBeExecuted(boolean toBeExecuted) {
		super.setToBeExecuted(toBeExecuted);
		if (getIfStatement() != null) {
			getIfStatement().setToBeExecuted(toBeExecuted);
		}
		if(getElseStatement() != null) {
			getElseStatement().setToBeExecuted(toBeExecuted);
		}
	}

}
