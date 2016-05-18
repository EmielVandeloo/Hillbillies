package hillbillies.statement;

import hillbillies.part3.programs.SourceLocation;
import hillbillies.program.Program;

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
		while (!(nestingStatement instanceof Loop)) {
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
	
	// TODO Well-formedness of Expressions
	public boolean isWellFormed() {
		if (this instanceof Queue) {
			for (Statement subStatement : ((Queue) this).getStatements()) {
				if (!subStatement.isWellFormed()){
					return false;
				}
			}
			return true;
		} else if (this instanceof Conditional) {
			return (((Conditional) this).getIfStatement().isWellFormed() && 
					((Conditional) this).getElseStatement().isWellFormed());
		} else if (this instanceof Loop) {
			return ((Loop) this).getBody().isWellFormed();
		} else if (this instanceof Break) {
			if (getLoopStatement() instanceof Loop) {
				return true;
			} else {
				return false;
			}
		} else {
			return true;
		}
	}
		
	public abstract void perform(Program program);
	
	public abstract void resetAll();

}
