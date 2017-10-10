package hillbillies.statement;

import hillbillies.part3.programs.SourceLocation;
import hillbillies.program.Program;
import hillbillies.statement.repetitive.Repetitive;

public abstract class Statement {
	
	private SourceLocation sourceLocation;
	private Statement nestingStatement = null;
	private boolean toBeExecuted = true;
	
	public Statement(SourceLocation sourceLocation){
		setSourceLocation(sourceLocation);
	}
	
	public Statement(){
		this(null);
	}
	
	public SourceLocation getSourceLocation(){
		return this.sourceLocation;
	}
	
	public void setSourceLocation(SourceLocation sourceLocation){
		this.sourceLocation = sourceLocation;
	}
	
	public boolean isToBeExecuted() {
		return this.toBeExecuted;
	}

	public void setToBeExecuted(boolean toBeExecuted) {
		this.toBeExecuted = toBeExecuted;
	}
	
	public Statement getNestingStatement() {
		return this.nestingStatement;
	}

	public void setNestingStatement(Statement nestingStatement) {
		this.nestingStatement = nestingStatement;
	}
	
	public Statement getLoopStatement() {
		Statement nestingStatement = getNestingStatement();
		if (nestingStatement == null) {
			return null;
		}
		while (!(nestingStatement instanceof Repetitive)) {
			nestingStatement = nestingStatement.getNestingStatement();
			if (nestingStatement == null) {
				return null;
			}
		}
		return nestingStatement;
	}
	
	public Statement getQueueStatement() {
		if (this instanceof Queue) {
			return this;
		}
		Statement nestingStatement = getNestingStatement();
		if (nestingStatement == null) {
			return null;
		}
		while (!(nestingStatement instanceof Queue)) {
			nestingStatement = nestingStatement.getNestingStatement();
			if (nestingStatement == null) {
				return null;
			}
		}
		return nestingStatement;
	}
	
	public boolean isPartOfQueue() {
		if (getNestingStatement() instanceof Queue) {
			return true;
		}
		return false;
	}
	
	public abstract boolean isWellFormed();
		
	public abstract void perform(Program program);
	
	public abstract void resetAll();

}
