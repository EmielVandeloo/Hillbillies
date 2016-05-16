package hillbillies.statement;

import java.util.ArrayList;
import java.util.List;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.program.Program;

public class Queue extends Statement {

	private List<Statement> statements = new ArrayList<>();

	public Queue(List<Statement> statements, SourceLocation sl) throws IllegalArgumentException {
		super(sl);
		this.statements = statements;
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
	public void perform(Program program){
		if (isToBeExecuted() && !program.hasStopped()) {
			if (getStatements() != null) {
				for (Statement statement : getStatements()) {
					if (program.hasTimeForStatement()) {
						statement.perform(program);
					}
				}
			} else {
				program.setTimeDepleted(true);
			}
		}
	}
	
	@Override
	public void setToBeExecuted(boolean toBeExecuted) {
		super.setToBeExecuted(toBeExecuted);
		if (getStatements() != null) {
			for (Statement statement : getStatements()) {
				if (statement != null) {
					statement.setToBeExecuted(toBeExecuted);
				}
			}
		}
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

}
