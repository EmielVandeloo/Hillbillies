package hillbillies.statement;

import hillbillies.part3.programs.SourceLocation;
import hillbillies.program.Program;

public class Void extends Statement {

	private boolean toBeExecuted = true;
	
	public Void(SourceLocation sl) {
		super(sl);
	}

	@Override
	public void perform(Program program) {
		setToBeExecuted(false);
		if (isPartOfQueue()) {
			((Queue) getQueueStatement()).setIndex(((Queue) getQueueStatement()).getIndex()+1);
		} else {
			try {
				getNestingStatement().setToBeExecuted(false);
			} catch (NullPointerException e) {}
		}
	}
	
	@Override
	public String toString() {
		return "Do nothing";
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
	public void resetAll() {
		setToBeExecuted(true);
	}
	
}
