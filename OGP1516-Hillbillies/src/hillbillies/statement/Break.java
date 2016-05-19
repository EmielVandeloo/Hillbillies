package hillbillies.statement;

import hillbillies.part3.programs.SourceLocation;
import hillbillies.program.Program;

public class Break extends Statement {

	public Break(SourceLocation sl) throws IllegalArgumentException {
		super(sl);
	}

	@Override
	public void perform(Program program) {
		if (isToBeExecuted() && !program.hasStopped()) {
			if (program.hasTimeForStatement()) {
				program.decreaseTimerOneUnit();
				getLoopStatement().setToBeExecuted(false);
				
			} else {
				program.setTimeDepleted(true);
			}
		}
	}
	
	@Override
	public String toString() {
		return "Break loop " + getLoopStatement().toString();
	}

	@Override
	public void resetAll() {
		setToBeExecuted(true);
	}
	
}
