package hillbillies.statement.action;

import hillbillies.part3.programs.SourceLocation;
import hillbillies.statement.Statement;

public abstract class Action extends Statement {
	
	public Action(SourceLocation sl) {
		super(sl);
	}
	
	public boolean isWellFormed() {
		return true;
	}

}
