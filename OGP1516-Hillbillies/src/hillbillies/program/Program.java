package hillbillies.program;

import java.util.HashMap;
import java.util.Map;
import hillbillies.statement.Break;
import hillbillies.statement.Conditional;
import hillbillies.statement.Loop;
import hillbillies.statement.Queue;
import hillbillies.statement.Statement;

public class Program {
	
	private Statement mainStatement;	
	private Map<String, Object> globalVariables = new HashMap<>();
	private boolean hasStopped = false;
	private double timer = Double.MAX_VALUE;
	private boolean timeDepleted = false;
	public double TIME_UNIT = 0.001;
	
	public Program(Statement mainStatement, Map<String, Object> globalVariables){
		setGlobalVariables(globalVariables);
		setMainStatement(mainStatement);
	}
	
	public Program() {
		this(null, new HashMap<String, Object>());
	}
	
	public Statement getMainStatement() {
		return this.mainStatement;
	}
	
	private void setMainStatement(Statement mainStatement) {
		this.mainStatement = mainStatement;
	}
	
	public double getTimer() {
		return this.timer;
	}

	public void setTimer(double timer) {
		this.timer = timer;
	}
	
	public void decreaseTimerOneUnit() {
		setTimer(getTimer() - TIME_UNIT);
	}
	
	public boolean hasTimeForStatement() {
		return getTimer() > 0;
	}
	
	public Map<String, Object> getGlobalVariables() {
		return new HashMap<String, Object>(this.globalVariables);
	}
	
	public void setGlobalVariables(Map<String, Object> globalVariables) {
		for (Map.Entry<String, Object> entry : globalVariables.entrySet()){
			putGlobalVariable(entry.getKey(), entry.getValue());
		}
	}
	
	public void putGlobalVariable(String string, Object obj) {
		this.globalVariables.put(string, obj);
	}
	
	public void execute(double deltaTime) {
		if (!hasStopped()) {
			setTimer(deltaTime);
			getMainStatement().perform(this);
			if (!isTimeDepleted()) {
				getMainStatement().setToBeExecuted(true);
			} else {
				setTimeDepleted(false);
			}
		}
	}
	
	public void stop() {
		this.hasStopped = true;
	}
	
	public boolean hasStopped() {
		return this.hasStopped;
	}
	
	public boolean isTimeDepleted() {
		return this.timeDepleted;
	}

	public void setTimeDepleted(boolean timeDepleted) {
		this.timeDepleted = timeDepleted;
	}
	
	// TODO Well-formedness of Expressions
	public boolean isWellFormed(Statement statement) {
		if (statement instanceof Queue) {
			for (Statement subStatement : ((Queue) statement).getStatements()) {
				if (!isWellFormed(subStatement)){
					return false;
				}
			}
			return true;
		} else if (statement instanceof Conditional) {
			return (isWellFormed(((Conditional) statement).getIfStatement()) && 
					isWellFormed(((Conditional) statement).getElseStatement()));
		} else if (statement instanceof Loop) {
			return isWellFormed(((Loop) statement).getBody());
		} else if (statement instanceof Break) {
			if (statement.getLoopStatement() instanceof Loop) {
				return true;
			} else {
				return false;
			}
		} else {
			return true;
		}
	}
	
	public boolean isWellFormed() {
		return isWellFormed(getMainStatement());
	}
	
}
