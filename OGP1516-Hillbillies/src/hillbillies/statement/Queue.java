package hillbillies.statement;

import java.util.ArrayList;
import java.util.List;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.program.Program;
import hillbillies.statement.repetitive.Repetitive;

public class Queue extends Statement {

	private List<Statement> statements = new ArrayList<>();
	private boolean toBeExecuted = true;
	private int index = 0;

	public Queue(List<Statement> statements, SourceLocation sl) throws IllegalArgumentException {
		super(sl);
		setStatements(statements);
	}
	
	public List<Statement> getStatements() {
		return this.statements;
	}
	
	public void setStatements(List<Statement> statements){
		this.statements = statements;
		if (statements != null) {
			for (Statement statement : statements) {
				statement.setNestingStatement(this);
			}
		}
	}

	@Override
	public void perform(Program program) {
		if (isToBeExecuted() && !program.hasStopped()) {
			getStatements().get(getIndex()).perform(program);
			if (getIndex() == getStatements().size()) {
				setToBeExecuted(false);
				if (getNestingStatement() instanceof Conditional) {
					getNestingStatement().setToBeExecuted(false);
				} else if (getNestingStatement() instanceof Repetitive) {
					getNestingStatement().resetAll();
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
	}
	
	@Override
	public String toString() {
		String string = new String();
		string += "Queue: /n";
		for (Statement statement : getStatements()) {
			string += statement.toString() + "/n";
		}
		return string;
	}
	
	public int getIndex() {
		return this.index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	@Override
	public void resetAll() {
		setToBeExecuted(true);
		setIndex(0);
		for (Statement statement : getStatements()) {
			statement.resetAll();
		}
	}
	
	@Override
	public boolean isWellFormed() {
		for (Statement statement : getStatements()) {
			if (!statement.isWellFormed()) {
				return false;
			}
		}
		return true;
	}
	
}


