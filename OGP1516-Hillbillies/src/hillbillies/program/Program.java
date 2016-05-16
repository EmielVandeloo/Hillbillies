package hillbillies.program;

import java.util.HashMap;
import java.util.Map;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.statement.Statement;

public class Program {
	
	private Statement mainStatement;	
	private Map<String, Object> globalVariables = new HashMap<>();
	private boolean hasStopped = false;
	private double timer = Double.MAX_VALUE;
	private boolean timeDepleted = false;
	public double TIME_UNIT = 0.001;
	private Unit unit;
	
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
	
	public Unit getUnit() {
		return this.unit;
	}
	
	public void setUnit(Unit unit) {
		this.unit = unit;
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
		try {
			for (Map.Entry<String, Object> entry : globalVariables.entrySet()) {
			putGlobalVariable(entry.getKey(), entry.getValue());
			}
		} catch (NullPointerException e) {}
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
	
	public World getWorld() {
		return getUnit().getWorld();
	}
	
}
